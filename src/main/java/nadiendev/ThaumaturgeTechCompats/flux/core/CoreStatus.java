package nadiendev.ThaumaturgeTechCompats.flux.core;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.nbt.CompoundTag;

public record CoreStatus(FluxTier tier, boolean formed, int missing, long stored, long capacity, long baseCapacity,
                         int cells, long cellCapacity, int stabilizers, int points, int required, boolean overloaded) {
    private static final String TIER = "Tier";
    private static final String FORMED = "Formed";
    private static final String MISSING = "Missing";
    private static final String STORED = "Stored";
    private static final String CAPACITY = "Capacity";
    private static final String BASE = "Base";
    private static final String CELLS = "Cells";
    private static final String CELL_CAPACITY = "CellCapacity";
    private static final String STABILIZERS = "Stabilizers";
    private static final String POINTS = "Points";
    private static final String REQUIRED = "Required";
    private static final String OVERLOADED = "Overloaded";

    public float fraction() {
        return capacity <= 0L ? 0.0F : (float) ((double) stored / (double) capacity);
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString(TIER, tier.id());
        tag.putBoolean(FORMED, formed);
        tag.putInt(MISSING, missing);
        tag.putLong(STORED, stored);
        tag.putLong(CAPACITY, capacity);
        tag.putLong(BASE, baseCapacity);
        tag.putInt(CELLS, cells);
        tag.putLong(CELL_CAPACITY, cellCapacity);
        tag.putInt(STABILIZERS, stabilizers);
        tag.putInt(POINTS, points);
        tag.putInt(REQUIRED, required);
        tag.putBoolean(OVERLOADED, overloaded);
        return tag;
    }

    public static CoreStatus fromTag(CompoundTag tag) {
        FluxTier tier = FluxTier.THAUMIUM;
        String id = tag.getString(TIER);
        for (FluxTier candidate : FluxTier.values()) {
            if (candidate.id().equals(id)) {
                tier = candidate;
            }
        }
        return new CoreStatus(tier, tag.getBoolean(FORMED), tag.getInt(MISSING), tag.getLong(STORED), tag.getLong(CAPACITY),
                tag.getLong(BASE), tag.getInt(CELLS), tag.getLong(CELL_CAPACITY), tag.getInt(STABILIZERS), tag.getInt(POINTS),
                tag.getInt(REQUIRED), tag.getBoolean(OVERLOADED));
    }
}
