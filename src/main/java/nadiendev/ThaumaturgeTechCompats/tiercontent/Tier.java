package nadiendev.ThaumaturgeTechCompats.tiercontent;

public enum Tier {
    MATRIX("matrix", "avaritia:crystal_matrix_ingot"),
    NEUTRONIUM("neutronium", "avaritia:neutron_ingot"),
    INFINITY("infinity", "avaritia:infinity_ingot");

    private final String id;
    private final String ingot;

    Tier(String id, String ingot) {
        this.id = id;
        this.ingot = ingot;
    }

    public String id() {
        return id;
    }

    public String ingot() {
        return ingot;
    }

    public String plateName() {
        return id + "_plate";
    }
}
