package nadiendev.ThaumaturgeTechCompats.flux.charger;

import nadiendev.ThaumaturgeTechCompats.flux.SimpleFluxStorage;

import java.util.function.LongSupplier;

public class ChargerFluxStorage extends SimpleFluxStorage {
    private final Runnable onChange;

    public ChargerFluxStorage(LongSupplier capacity, Runnable onChange) {
        super(capacity);
        this.onChange = onChange;
    }

    @Override
    public long insert(long amount, boolean simulate) {
        long accepted = super.insert(amount, simulate);
        if (!simulate && accepted > 0L) {
            onChange.run();
        }
        return accepted;
    }

    @Override
    public long extract(long amount, boolean simulate) {
        long taken = super.extract(amount, simulate);
        if (!simulate && taken > 0L) {
            onChange.run();
        }
        return taken;
    }

    @Override
    public boolean canExtract() {
        return false;
    }
}
