package nadiendev.ThaumaturgeTechCompats.flux;

import net.minecraft.nbt.CompoundTag;

import java.util.function.LongSupplier;

public class SimpleFluxStorage implements IFluxStorage {
    private static final String KEY = "dimensional_flux";

    private final LongSupplier capacity;
    private long stored;

    public SimpleFluxStorage(LongSupplier capacity) {
        this.capacity = capacity;
    }

    @Override
    public long insert(long amount, boolean simulate) {
        long accepted = Math.max(0L, Math.min(amount, capacity.getAsLong() - stored));
        if (!simulate) {
            stored += accepted;
        }
        return accepted;
    }

    @Override
    public long extract(long amount, boolean simulate) {
        long taken = Math.max(0L, Math.min(amount, stored));
        if (!simulate) {
            stored -= taken;
        }
        return taken;
    }

    @Override
    public long stored() {
        return Math.min(stored, capacity.getAsLong());
    }

    @Override
    public long capacity() {
        return capacity.getAsLong();
    }

    public void set(long value) {
        stored = Math.max(0L, value);
    }

    public void save(CompoundTag tag) {
        tag.putLong(KEY, stored);
    }

    public void load(CompoundTag tag) {
        stored = tag.getLong(KEY);
    }
}
