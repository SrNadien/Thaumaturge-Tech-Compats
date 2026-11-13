package nadiendev.ThaumaturgeTechCompats.auraconverter;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class AuraConverterBlockEntity extends BlockEntity {
    private static final float EPSILON = 0.0001F;
    private static final double RATE_SMOOTHING = 0.2D;

    private final AuraConverterTier tier;
    private final IEnergyStorage storage = new Storage();
    private long energy;
    private long outputTick = Long.MIN_VALUE;
    private long outputUsed;
    private long outputAccumulated;
    private long producedThisTick;
    private double producedRate;
    private double outputRate;
    private AuraConverterStatus status = AuraConverterStatus.NO_VIS;

    public AuraConverterBlockEntity(BlockPos pos, BlockState state) {
        super(AuraConverterRegistry.ENTITY.get(), pos, state);
        this.tier = ((AuraConverterBlock) state.getBlock()).tier();
    }

    public IEnergyStorage energyStorage() {
        return storage;
    }

    public AuraConverterTier tier() {
        return tier;
    }

    public long energy() {
        return energy;
    }

    public long capacity() {
        return AuraConverterConfig.buffer(tier);
    }

    public long producedRate() {
        return Math.round(producedRate);
    }

    public long outputRate() {
        return Math.round(outputRate);
    }

    public AuraConverterStatus status() {
        return status;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AuraConverterBlockEntity converter) {
        converter.producedThisTick = 0L;
        if (AuraConverterConfig.enabled(converter.tier)) {
            converter.status = converter.convert(level, pos);
        } else {
            converter.status = AuraConverterStatus.DISABLED;
        }
        setActive(level, pos, converter.status == AuraConverterStatus.RUNNING);
        converter.push(level, pos);
        converter.sampleRates();
    }

    private void sampleRates() {
        long output = outputAccumulated;
        outputAccumulated = 0L;
        producedRate += (producedThisTick - producedRate) * RATE_SMOOTHING;
        outputRate += (output - outputRate) * RATE_SMOOTHING;
        if (producedThisTick == 0L && producedRate < 0.5D) {
            producedRate = 0.0D;
        }
        if (output == 0L && outputRate < 0.5D) {
            outputRate = 0.0D;
        }
    }

    private AuraConverterStatus convert(Level level, BlockPos pos) {
        if (AuraConverterConfig.redstonePauses() && level.hasNeighborSignal(pos)) {
            return AuraConverterStatus.PAUSED;
        }
        long space = capacity() - energy;
        long fePerVis = AuraConverterConfig.fePerVis(tier);
        double visForSpace = (double) space / fePerVis;
        if (space <= 0L || visForSpace < EPSILON) {
            return AuraConverterStatus.FULL;
        }
        float floor = AuraHelper.getAuraBase(level, pos) * AuraConverterConfig.minAuraFraction();
        float amount = (float) Math.min(visForSpace, AuraHelper.getVis(level, pos) - floor);
        if (amount < EPSILON) {
            return AuraConverterStatus.NO_VIS;
        }
        float granted = ChunkBudget.reserve(level, pos, amount);
        if (granted < EPSILON) {
            ChunkBudget.release(level, pos, granted);
            return AuraConverterStatus.CHUNK_LIMIT;
        }
        float drained = 0.0F;
        if (AuraHelper.drainVis(level, pos, granted, true) >= EPSILON) {
            drained = AuraHelper.drainVis(level, pos, granted, false);
        }
        ChunkBudget.release(level, pos, granted - drained);
        if (drained < EPSILON) {
            return AuraConverterStatus.NO_VIS;
        }
        long produced = Math.min(space, Math.round((double) drained * fePerVis));
        if (produced <= 0L) {
            return AuraConverterStatus.NO_VIS;
        }
        energy += produced;
        producedThisTick = produced;
        float flux = AuraConverterConfig.fluxPerVis() * drained;
        if (flux > 0.0F) {
            AuraHelper.polluteAura(level, pos, flux, false);
        }
        setChanged();
        return AuraConverterStatus.RUNNING;
    }

    private void push(Level level, BlockPos pos) {
        if (energy <= 0L) {
            return;
        }
        for (Direction direction : Direction.values()) {
            long available = Math.min(energy, remainingOutput());
            if (available <= 0L) {
                return;
            }
            BlockPos neighbour = pos.relative(direction);
            if (!level.isLoaded(neighbour)) {
                continue;
            }
            IEnergyStorage target = level.getCapability(Capabilities.EnergyStorage.BLOCK, neighbour, direction.getOpposite());
            if (target == null || !target.canReceive()) {
                continue;
            }
            int accepted = target.receiveEnergy((int) Math.min(available, Integer.MAX_VALUE), false);
            if (accepted > 0) {
                energy -= accepted;
                outputUsed += accepted;
                outputAccumulated += accepted;
                setChanged();
            }
        }
    }

    private long remainingOutput() {
        long now = level == null ? 0L : level.getGameTime();
        if (now != outputTick) {
            outputTick = now;
            outputUsed = 0L;
        }
        return Math.max(0L, AuraConverterConfig.maxOutput(tier) - outputUsed);
    }

    private static void setActive(Level level, BlockPos pos, boolean active) {
        BlockState state = level.getBlockState(pos);
        if (state.getValue(AuraConverterBlock.ACTIVE) != active) {
            level.setBlock(pos, state.setValue(AuraConverterBlock.ACTIVE, active), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putLong("energy", energy);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        energy = Math.max(0L, Math.min(tag.getLong("energy"), AuraConverterConfig.buffer(tier)));
    }

    private final class Storage implements IEnergyStorage {
        @Override
        public int receiveEnergy(int toReceive, boolean simulate) {
            return 0;
        }

        @Override
        public int extractEnergy(int toExtract, boolean simulate) {
            long extracted = Math.min(Math.min(toExtract, energy), remainingOutput());
            if (extracted <= 0L) {
                return 0;
            }
            if (!simulate) {
                energy -= extracted;
                outputUsed += extracted;
                outputAccumulated += extracted;
                setChanged();
            }
            return (int) extracted;
        }

        @Override
        public int getEnergyStored() {
            return (int) Math.min(energy, Integer.MAX_VALUE);
        }

        @Override
        public int getMaxEnergyStored() {
            return (int) Math.min(capacity(), Integer.MAX_VALUE);
        }

        @Override
        public boolean canExtract() {
            return true;
        }

        @Override
        public boolean canReceive() {
            return false;
        }
    }
}
