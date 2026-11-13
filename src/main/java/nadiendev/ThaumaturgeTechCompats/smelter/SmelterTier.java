package nadiendev.ThaumaturgeTechCompats.smelter;

public enum SmelterTier {
    MATRIX("matrix", "avaritia:crystal_matrix_ingot", "thaumaturge:smelter_void", 8, 0.97F, 3.0F, 30.0F, 4, 1250),
    NEUTRONIUM("neutronium", "avaritia:neutron_ingot", "thaumaturgetechcompats:matrix_smelter", 5, 0.985F, 4.0F, 60.0F, 5, 2000),
    INFINITY("infinity", "avaritia:infinity_ingot", "thaumaturgetechcompats:neutronium_smelter", 2, 1.0F, 5.0F, 120.0F, 6, 3000);

    private final String id;
    private final String ingot;
    private final String previous;
    private final int smeltInterval;
    private final float efficiency;
    private final float hardness;
    private final float resistance;
    private final int ignisCrystals;
    private final int vis;

    SmelterTier(String id, String ingot, String previous, int smeltInterval, float efficiency, float hardness, float resistance, int ignisCrystals, int vis) {
        this.id = id;
        this.ingot = ingot;
        this.previous = previous;
        this.smeltInterval = smeltInterval;
        this.efficiency = efficiency;
        this.hardness = hardness;
        this.resistance = resistance;
        this.ignisCrystals = ignisCrystals;
        this.vis = vis;
    }

    public String id() {
        return id;
    }

    public String blockName() {
        return id + "_smelter";
    }

    public String ingot() {
        return ingot;
    }

    public String previous() {
        return previous;
    }

    public int smeltInterval() {
        return smeltInterval;
    }

    public float efficiency() {
        return efficiency;
    }

    public float hardness() {
        return hardness;
    }

    public float resistance() {
        return resistance;
    }

    public int ignisCrystals() {
        return ignisCrystals;
    }

    public int vis() {
        return vis;
    }
}
