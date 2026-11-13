package nadiendev.ThaumaturgeTechCompats.flux.charger;

import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EnvironmentalChargerBlockEntity extends BlockEntity {
    private static final long ACTIVE_GRACE = 20L;

    private final ChargerFluxStorage storage = new ChargerFluxStorage(this::capacity, this::setChanged);
    private int timer;
    private long lastCharged = Long.MIN_VALUE / 2;

    public EnvironmentalChargerBlockEntity(BlockPos pos, BlockState state) {
        super(ChargerRegistry.ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EnvironmentalChargerBlockEntity charger) {
        if (!(level instanceof ServerLevel serverLevel) || !ChargerConfig.enabled(charger.tier())) {
            return;
        }
        charger.timer++;
        if (charger.timer < ChargerConfig.interval()) {
            return;
        }
        charger.timer = 0;
        charger.pass(serverLevel, pos, state);
    }

    public FluxTier tier() {
        return getBlockState().getBlock() instanceof EnvironmentalChargerBlock block ? block.tier() : FluxTier.THAUMIUM;
    }

    public ChargerFluxStorage storage() {
        return storage;
    }

    public long capacity() {
        return ChargerConfig.capacity(tier());
    }

    public ChargerStatus status(Level level) {
        if (storage.stored() <= 0L) {
            return ChargerStatus.EMPTY;
        }
        return isActive(level) ? ChargerStatus.CHARGING : ChargerStatus.IDLE;
    }

    private boolean isActive(Level level) {
        return level.getGameTime() - lastCharged <= ACTIVE_GRACE;
    }

    private void pass(ServerLevel level, BlockPos pos, BlockState state) {
        FluxTier tier = tier();
        if (storage.stored() > 0L) {
            long perItem = FluxConfig.chargerRate(tier) * ChargerConfig.interval();
            double radius = FluxConfig.chargerRadius(tier);
            Vec3 center = pos.getCenter();
            AABB area = new AABB(pos).inflate(radius);
            List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, area,
                    player -> player.isAlive() && !player.isSpectator() && player.distanceToSqr(center) <= radius * radius);
            for (ServerPlayer player : players) {
                if (chargeInventory(level, player, perItem)) {
                    lastCharged = level.getGameTime();
                }
            }
        }
        boolean active = isActive(level);
        if (state.getValue(EnvironmentalChargerBlock.ACTIVE) != active) {
            level.setBlock(pos, state.setValue(EnvironmentalChargerBlock.ACTIVE, active), 3);
        }
    }

    private boolean chargeInventory(ServerLevel level, ServerPlayer player, long perItem) {
        Inventory inventory = player.getInventory();
        boolean charged = false;
        for (int slot = 0; slot < inventory.getContainerSize() && storage.stored() > 0L; slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (ChargerRecharge.charge(stack, storage, perItem) > 0L) {
                charged = true;
            }
        }
        if (charged && ChargerConfig.particles()) {
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, player.getX(), player.getY() + player.getBbHeight() * 0.5D, player.getZ(), 3, 0.3D, 0.5D, 0.3D, 0.02D);
        }
        return charged;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        CompoundTag tag = new CompoundTag();
        storage.save(tag);
        output.store("data", CompoundTag.CODEC, tag);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        CompoundTag tag = input.read("data", CompoundTag.CODEC).orElseGet(CompoundTag::new);
        storage.load(tag);
    }
}
