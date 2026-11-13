package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.flux.SimpleFluxStorage;

import java.util.function.LongSupplier;

public class FieldFluxStorage extends SimpleFluxStorage {
    public FieldFluxStorage(LongSupplier capacity) {
        super(capacity);
    }

    @Override
    public long extract(long amount, boolean simulate) {
        return 0L;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    public long consume(long amount) {
        return super.extract(amount, false);
    }
}
