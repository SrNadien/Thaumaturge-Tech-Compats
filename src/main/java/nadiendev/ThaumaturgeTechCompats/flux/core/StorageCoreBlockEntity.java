package nadiendev.ThaumaturgeTechCompats.flux.core;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import nadiendev.ThaumaturgeTechCompats.flux.FluxComponents;
import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class StorageCoreBlockEntity extends BlockEntity {
    private static final String STORED_KEY = "dimensional_flux";
    private static final String FORMED_KEY = "formed";
    private static final String CELLS_KEY = "cells";
    private static final String CELL_CAPACITY_KEY = "cell_capacity";
    private static final String PULSES_KEY = "overload_pulses";

    private final CoreStorage storage = new CoreStorage();
    private long stored;
    private boolean formed;
    private int cells;
    private long cellCapacity;
    private int missing;
    private int stabilizers;
    private int points;
    private boolean overloaded;
    private int pulses;
    private int scanTimer = 1_000_000;
    private int overloadTimer;

    public StorageCoreBlockEntity(BlockPos pos, BlockState state) {
        super(CoreRegistry.CORE_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, StorageCoreBlockEntity core) {
        if (!(level instanceof ServerLevel serverLevel) || !core.enabled()) {
            return;
        }
        core.scanTimer++;
        if (core.scanTimer >= CoreConfig.scanInterval()) {
            core.refresh();
        }
        core.enforce(serverLevel);
    }

    public FluxTier tier() {
        return getBlockState().getBlock() instanceof StorageCoreBlock block ? block.tier() : FluxTier.THAUMIUM;
    }

    public IFluxStorage storage() {
        return storage;
    }

    public boolean enabled() {
        return CoreIntegration.enabled(tier());
    }

    public long capacity() {
        return Math.min(Long.MAX_VALUE / 2L, FluxConfig.coreCapacity(tier()) + cellCapacity);
    }

    public boolean formed() {
        return formed;
    }

    private boolean usable() {
        return formed && enabled();
    }

    public void refresh() {
        scanTimer = 0;
        if (level == null || level.isClientSide()) {
            return;
        }
        CoreScan scan = enabled() ? CoreStructure.scan(level, worldPosition, tier()) : CoreScan.EMPTY;
        boolean changed = scan.formed() != formed || scan.cells() != cells || scan.cellCapacity() != cellCapacity;
        formed = scan.formed();
        missing = scan.missing();
        cells = scan.cells();
        cellCapacity = scan.cellCapacity();
        stabilizers = scan.stabilizers();
        points = scan.points();
        long capacity = capacity();
        if (stored > capacity) {
            long excess = stored - capacity;
            stored = capacity;
            if (CoreConfig.spillExcess()) {
                spill(excess);
            }
            changed = true;
        }
        BlockState state = getBlockState();
        if (state.hasProperty(StorageCoreBlock.FORMED) && state.getValue(StorageCoreBlock.FORMED) != formed) {
            level.setBlock(worldPosition, state.setValue(StorageCoreBlock.FORMED, formed), 3);
        }
        if (changed) {
            setChanged();
        }
    }

    private void spill(long amount) {
        if (level == null || amount <= 0L) {
            return;
        }
        float flux = (float) ((double) amount / (double) CoreConfig.dfPerFlux());
        if (flux > 0.0F) {
            AuraHelper.addFlux(level, worldPosition, flux);
        }
    }

    public int required() {
        long capacity = capacity();
        double fraction = capacity <= 0L ? 0.0D : (double) stored / (double) capacity;
        return (int) Math.ceil(fraction * CoreConfig.stabilizerPoints(tier()));
    }

    public CoreStatus status() {
        return new CoreStatus(tier(), formed, missing, Math.min(stored, capacity()), capacity(), FluxConfig.coreCapacity(tier()),
                cells, cellCapacity, stabilizers, points, required(), overloaded);
    }

    private void enforce(ServerLevel level) {
        if (!formed || !CoreConfig.overloadEnabled()) {
            calm();
            return;
        }
        long capacity = capacity();
        double fraction = capacity <= 0L ? 0.0D : (double) stored / (double) capacity;
        int required = required();
        boolean unsafe = fraction > CoreConfig.overloadThreshold() && required > 0 && points < required;
        if (!unsafe) {
            calm();
            return;
        }
        overloaded = true;
        overloadTimer++;
        if (overloadTimer < CoreConfig.overloadInterval()) {
            return;
        }
        overloadTimer = 0;
        pulse(level, fraction, 1.0D - (double) points / (double) required);
    }

    private void calm() {
        overloaded = false;
        overloadTimer = 0;
        pulses = 0;
    }

    private void pulse(ServerLevel level, double fraction, double deficit) {
        pulses++;
        double x = worldPosition.getX() + 0.5D;
        double y = worldPosition.getY() + 0.5D;
        double z = worldPosition.getZ() + 0.5D;

        float flux = (float) (CoreConfig.overloadFlux() * deficit);
        if (flux > 0.0F) {
            AuraHelper.addFlux(level, worldPosition, flux);
        }
        long leak = Math.min(stored, (long) Math.ceil(capacity() * CoreConfig.leakPercent() / 100.0D * deficit));
        if (leak > 0L) {
            stored -= leak;
            setChanged();
        }
        if (CoreConfig.overloadParticles()) {
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z, 24, 0.7D, 0.7D, 0.7D, 0.08D);
        }
        level.playSound(null, worldPosition, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.6F, 1.4F);
        double radius = CoreConfig.damageRadius();
        if (CoreConfig.damageEnabled()) {
            float damage = (float) (CoreConfig.damageAmount() * deficit);
            if (damage > 0.0F) {
                for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, new AABB(worldPosition).inflate(radius))) {
                    entity.hurt(level.damageSources().magic(), damage);
                }
            }
        }
        if (CoreConfig.overloadWarn()) {
            Component warning = Component.translatable(CoreTranslations.WARNING, getBlockState().getBlock().getName());
            for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, new AABB(worldPosition).inflate(radius * 2.0D))) {
                player.sendOverlayMessage(warning);
            }
        }
        if (CoreConfig.explosionEnabled() && fraction >= CoreConfig.explosionThreshold() && pulses >= CoreConfig.explosionPulses()) {
            long lost = stored;
            stored = 0L;
            pulses = 0;
            setChanged();
            spill(lost);
            level.explode(null, x, y, z, (float) CoreConfig.explosionPower(),
                    CoreConfig.explosionBreaksBlocks() ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        CompoundTag tag = new CompoundTag();
        tag.putLong(STORED_KEY, stored);
        tag.putBoolean(FORMED_KEY, formed);
        tag.putInt(CELLS_KEY, cells);
        tag.putLong(CELL_CAPACITY_KEY, cellCapacity);
        tag.putInt(PULSES_KEY, pulses);
        output.store("data", CompoundTag.CODEC, tag);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        CompoundTag tag = input.read("data", CompoundTag.CODEC).orElseGet(CompoundTag::new);
        stored = Math.max(0L, tag.getLongOr(STORED_KEY, 0L));
        formed = tag.getBooleanOr(FORMED_KEY, false);
        cells = tag.getIntOr(CELLS_KEY, 0);
        cellCapacity = tag.getLongOr(CELL_CAPACITY_KEY, 0L);
        pulses = tag.getIntOr(PULSES_KEY, 0);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(FluxComponents.STORED.get(), stored);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter input) {
        super.applyImplicitComponents(input);
        Long value = input.get(FluxComponents.STORED.get());
        if (value != null) {
            stored = Math.max(0L, value);
        }
    }

    private final class CoreStorage implements IFluxStorage {
        @Override
        public long insert(long amount, boolean simulate) {
            if (!usable()) {
                return 0L;
            }
            long accepted = Math.max(0L, Math.min(amount, capacity() - stored));
            if (!simulate && accepted > 0L) {
                stored += accepted;
                setChanged();
            }
            return accepted;
        }

        @Override
        public long extract(long amount, boolean simulate) {
            if (!usable()) {
                return 0L;
            }
            long taken = Math.max(0L, Math.min(amount, stored));
            if (!simulate && taken > 0L) {
                stored -= taken;
                setChanged();
            }
            return taken;
        }

        @Override
        public long stored() {
            return Math.min(stored, capacity());
        }

        @Override
        public long capacity() {
            return StorageCoreBlockEntity.this.capacity();
        }

        @Override
        public boolean canInsert() {
            return usable();
        }

        @Override
        public boolean canExtract() {
            return usable();
        }
    }
}
