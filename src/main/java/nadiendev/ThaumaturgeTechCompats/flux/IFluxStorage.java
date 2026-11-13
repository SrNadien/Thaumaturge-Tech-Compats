package nadiendev.ThaumaturgeTechCompats.flux;

public interface IFluxStorage {
    long insert(long amount, boolean simulate);

    long extract(long amount, boolean simulate);

    long stored();

    long capacity();

    default boolean canInsert() {
        return true;
    }

    default boolean canExtract() {
        return true;
    }
}
