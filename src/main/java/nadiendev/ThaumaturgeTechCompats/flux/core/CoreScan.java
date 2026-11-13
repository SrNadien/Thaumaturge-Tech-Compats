package nadiendev.ThaumaturgeTechCompats.flux.core;

public record CoreScan(boolean formed, int missing, int cells, long cellCapacity, int stabilizers, int points) {
    public static final CoreScan EMPTY = new CoreScan(false, 0, 0, 0L, 0, 0);
}
