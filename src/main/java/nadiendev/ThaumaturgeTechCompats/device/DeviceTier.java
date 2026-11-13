package nadiendev.ThaumaturgeTechCompats.device;

public enum DeviceTier {
    MATRIX("matrix", "avaritia:crystal_matrix_ingot"),
    NEUTRONIUM("neutronium", "avaritia:neutron_ingot"),
    INFINITY("infinity", "avaritia:infinity_ingot");

    private final String id;
    private final String ingot;

    DeviceTier(String id, String ingot) {
        this.id = id;
        this.ingot = ingot;
    }

    public String id() {
        return id;
    }

    public String ingot() {
        return ingot;
    }

    public String plate() {
        return DeviceJson.MOD + ":" + id + "_plate";
    }

    public int lockLevel() {
        return 3 + ordinal();
    }

    public static DeviceTier byLock(int lock) {
        int index = lock - 3;
        DeviceTier[] tiers = values();
        return index >= 0 && index < tiers.length ? tiers[index] : null;
    }
}
