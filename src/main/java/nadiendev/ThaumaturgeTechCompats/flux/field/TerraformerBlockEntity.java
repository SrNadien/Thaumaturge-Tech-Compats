package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TerraformerBlockEntity extends BlockEntity {
    private static final String BAIT_KEY = "bait";
    private static final String PROGRESS_KEY = "progress";
    private static final int QUART = 4;

    private final FluxTier tier;
    private final FieldFluxStorage storage;
    private ResourceLocation bait;
    private int progress;
    private int timer;
    private long age;
    private long[] cells = new long[0];
    private int cellsRadius = -1;
    private Status status = Status.NO_BAIT;

    public enum Status {
        NO_BAIT("no_bait"),
        INVALID_BAIT("invalid_bait"),
        WORKING("working"),
        WAITING_FLUX("waiting_flux"),
        DONE("done"),
        DISABLED("disabled"),
        BLOCKED_DIMENSION("blocked_dimension");

        private final String id;

        Status(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }
    }

    public TerraformerBlockEntity(BlockPos pos, BlockState state) {
        super(FieldRegistry.TERRAFORMER_ENTITY.get(), pos, state);
        this.tier = ((TerraformerBlock) state.getBlock()).tier();
        this.storage = new FieldFluxStorage(() -> FieldConfig.terraformerCapacity(tier));
    }

    public FluxTier tier() {
        return tier;
    }

    public IFluxStorage storage() {
        return storage;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        storage.save(tag);
        tag.putInt(PROGRESS_KEY, progress);
        if (bait != null) {
            tag.putString(BAIT_KEY, bait.toString());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        storage.load(tag);
        progress = tag.getInt(PROGRESS_KEY);
        bait = tag.contains(BAIT_KEY) ? ResourceLocation.tryParse(tag.getString(BAIT_KEY)) : null;
    }

    public void loadBait(Player player, ItemStack stack) {
        Optional<ResourceLocation> biome = TerraformerBaits.biomeFor(stack);
        if (biome.isEmpty()) {
            return;
        }
        ResourceLocation item = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (item.equals(bait)) {
            player.displayClientMessage(Component.translatable(FieldTranslations.TERRAFORMER_SAME).withStyle(ChatFormatting.YELLOW), true);
            return;
        }
        returnBait(player);
        bait = item;
        progress = 0;
        timer = 0;
        stack.consume(1, player);
        setChanged();
        player.displayClientMessage(Component.translatable(FieldTranslations.TERRAFORMER_LOADED, stack.getItem().getDescription(), biomeName(biome.get())).withStyle(ChatFormatting.GREEN), true);
    }

    public void removeBait(Player player) {
        if (bait == null) {
            report(player);
            return;
        }
        returnBait(player);
        bait = null;
        progress = 0;
        setChanged();
        player.displayClientMessage(Component.translatable(FieldTranslations.TERRAFORMER_REMOVED).withStyle(ChatFormatting.YELLOW), true);
    }

    public void dropBait() {
        if (bait != null && level != null && !level.isClientSide) {
            Containers.dropItemStack(level, worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, new ItemStack(BuiltInRegistries.ITEM.get(bait)));
            bait = null;
        }
    }

    private void returnBait(Player player) {
        if (bait == null) {
            return;
        }
        Item item = BuiltInRegistries.ITEM.get(bait);
        if (item != Items.AIR) {
            player.getInventory().placeItemBackInInventory(new ItemStack(item));
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TerraformerBlockEntity terraformer) {
        if (level instanceof ServerLevel server) {
            terraformer.tick(server);
        }
    }

    private void tick(ServerLevel level) {
        age++;
        if (age % 20L == 0L) {
            setChanged();
        }
        status = work(level);
    }

    private Status work(ServerLevel level) {
        if (!FieldIntegration.enabled(tier) || !FieldConfig.terraformerEnabled()) {
            return Status.DISABLED;
        }
        if (bait == null) {
            return Status.NO_BAIT;
        }
        if (TerraformerBaits.dimensionBlocked(level.dimension().location())) {
            return Status.BLOCKED_DIMENSION;
        }
        Optional<ResourceLocation> biomeId = TerraformerBaits.biomeFor(bait);
        if (biomeId.isEmpty() || TerraformerBaits.biomeBlocked(biomeId.get())) {
            return Status.INVALID_BAIT;
        }
        Optional<Holder.Reference<Biome>> target = TerraformerBaits.resolve(level, biomeId.get());
        if (target.isEmpty()) {
            return Status.INVALID_BAIT;
        }
        ensureCells();
        if (progress >= cells.length) {
            return Status.DONE;
        }
        if (++timer < FieldConfig.terraformerInterval()) {
            return status == Status.WAITING_FLUX ? Status.WAITING_FLUX : Status.WORKING;
        }
        timer = 0;
        return cycle(level, target.get());
    }

    private Status cycle(ServerLevel level, Holder<Biome> target) {
        long cost = FieldConfig.terraformerCost(tier);
        int budget = FieldConfig.terraformerColumns(tier);
        int examined = FieldConfig.terraformerMaxScanned();
        int quartY = QuartPos.fromBlock(worldPosition.getY());
        Map<ChunkPos, Set<Long>> batches = new HashMap<>();
        Status result = Status.WORKING;
        while (progress < cells.length && budget > 0 && examined > 0) {
            examined--;
            long key = cells[progress];
            int quartX = BiomePainter.quartX(key);
            int quartZ = BiomePainter.quartZ(key);
            LevelChunk chunk = level.getChunkSource().getChunkNow(SectionPos.blockToSectionCoord(QuartPos.toBlock(quartX)), SectionPos.blockToSectionCoord(QuartPos.toBlock(quartZ)));
            if (chunk == null) {
                progress++;
                continue;
            }
            Holder<Biome> current = chunk.getNoiseBiome(quartX, quartY, quartZ);
            boolean same = current.value() == target.value();
            boolean blocked = current.unwrapKey().map(biomeKey -> TerraformerBaits.biomeBlocked(biomeKey.location())).orElse(false);
            if (same || blocked) {
                progress++;
                continue;
            }
            if (storage.stored() < cost) {
                result = Status.WAITING_FLUX;
                break;
            }
            storage.consume(cost);
            batches.computeIfAbsent(chunk.getPos(), pos -> new HashSet<>()).add(key);
            budget--;
            progress++;
        }
        if (!batches.isEmpty()) {
            BiomePainter.paint(level, target, batches);
            setChanged();
            if (FieldConfig.terraformerParticles()) {
                level.sendParticles(ParticleTypes.HAPPY_VILLAGER, worldPosition.getX() + 0.5D, worldPosition.getY() + 1.2D, worldPosition.getZ() + 0.5D, 8, 0.4D, 0.3D, 0.4D, 0.0D);
            }
        }
        if (progress >= cells.length && result != Status.WAITING_FLUX) {
            setChanged();
            return Status.DONE;
        }
        return result;
    }

    private void ensureCells() {
        int radius = FieldConfig.terraformerRadius(tier);
        if (radius == cellsRadius) {
            return;
        }
        double centerX = worldPosition.getX() + 0.5D;
        double centerZ = worldPosition.getZ() + 0.5D;
        double radiusSq = (double) radius * radius;
        List<long[]> found = new ArrayList<>();
        for (int quartX = QuartPos.fromBlock(worldPosition.getX() - radius); quartX <= QuartPos.fromBlock(worldPosition.getX() + radius); quartX++) {
            for (int quartZ = QuartPos.fromBlock(worldPosition.getZ() - radius); quartZ <= QuartPos.fromBlock(worldPosition.getZ() + radius); quartZ++) {
                double dx = QuartPos.toBlock(quartX) + QUART * 0.5D - centerX;
                double dz = QuartPos.toBlock(quartZ) + QUART * 0.5D - centerZ;
                double distance = dx * dx + dz * dz;
                if (distance <= radiusSq) {
                    found.add(new long[]{BiomePainter.pack(quartX, quartZ), Double.doubleToLongBits(distance)});
                }
            }
        }
        found.sort(Comparator.comparingDouble(entry -> Double.longBitsToDouble(entry[1])));
        cells = found.stream().mapToLong(entry -> entry[0]).toArray();
        cellsRadius = radius;
        progress = Math.min(progress, cells.length);
    }

    private static Component biomeName(ResourceLocation biome) {
        return Component.translatable("biome." + biome.getNamespace() + "." + biome.getPath());
    }

    public void report(Player player) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        player.displayClientMessage(getBlockState().getBlock().getName().withStyle(ChatFormatting.LIGHT_PURPLE), false);
        Status shown = status;
        ChatFormatting color = switch (shown) {
            case WORKING, DONE -> ChatFormatting.GREEN;
            case WAITING_FLUX, NO_BAIT -> ChatFormatting.YELLOW;
            default -> ChatFormatting.RED;
        };
        player.displayClientMessage(Component.translatable(FieldTranslations.terraformerState(shown.id())).withStyle(color), false);
        if (bait == null) {
            player.displayClientMessage(Component.translatable(FieldTranslations.TERRAFORMER_NO_BAIT).withStyle(ChatFormatting.GRAY), false);
        } else {
            Item item = BuiltInRegistries.ITEM.get(bait);
            Component biome = TerraformerBaits.biomeFor(bait).<Component>map(TerraformerBlockEntity::biomeName).orElse(Component.translatable(FieldTranslations.TERRAFORMER_UNKNOWN_BIOME));
            player.displayClientMessage(Component.translatable(FieldTranslations.TERRAFORMER_BAIT, item.getDescription(), biome).withStyle(ChatFormatting.GRAY), false);
            ensureCells();
            int total = cells.length;
            int percent = total == 0 ? 100 : (int) (100L * Math.min(progress, total) / total);
            player.displayClientMessage(Component.translatable(FieldTranslations.TERRAFORMER_PROGRESS, format.format(Math.min(progress, total)), format.format(total), percent).withStyle(ChatFormatting.GRAY), false);
        }
        player.displayClientMessage(Component.translatable(FieldTranslations.TERRAFORMER_RADIUS, FieldConfig.terraformerRadius(tier)).withStyle(ChatFormatting.GRAY), false);
        player.displayClientMessage(Component.translatable(FieldTranslations.FLUX, format.format(storage.stored()), format.format(storage.capacity())).withStyle(ChatFormatting.GRAY), false);
        player.displayClientMessage(Component.translatable(FieldTranslations.TERRAFORMER_COST, format.format(FieldConfig.terraformerCost(tier))).withStyle(ChatFormatting.GRAY), false);
    }
}
