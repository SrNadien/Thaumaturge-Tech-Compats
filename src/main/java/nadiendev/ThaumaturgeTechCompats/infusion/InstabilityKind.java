package nadiendev.ThaumaturgeTechCompats.infusion;

public enum InstabilityKind {
    SOLAR_FIRE(MatrixVariant.MATRIX, "solar_fire", "fire", 3),
    SOLAR_LIGHTNING(MatrixVariant.MATRIX, "solar_lightning", "lightning", 2),
    SOLAR_BURN(MatrixVariant.MATRIX, "solar_burn", "burn", 3),
    NUCLEAR_RADIATION(MatrixVariant.NEUTRONIUM, "nuclear_radiation", "radiation", 3),
    NUCLEAR_CONTAMINATION(MatrixVariant.NEUTRONIUM, "nuclear_contamination", "contamination", 2),
    NUCLEAR_MELTDOWN(MatrixVariant.NEUTRONIUM, "nuclear_meltdown", "meltdown", 1),
    COSMIC_FLUX(MatrixVariant.INFINITY, "cosmic_flux", "flux", 3),
    COSMIC_WARP(MatrixVariant.INFINITY, "cosmic_warp", "warp", 2),
    COSMIC_SUMMON(MatrixVariant.INFINITY, "cosmic_summon", "summon", 2),
    COSMIC_TELEPORT(MatrixVariant.INFINITY, "cosmic_teleport", "teleport", 2);

    private final MatrixVariant variant;
    private final String registryName;
    private final String configName;
    private final int weight;

    InstabilityKind(MatrixVariant variant, String registryName, String configName, int weight) {
        this.variant = variant;
        this.registryName = registryName;
        this.configName = configName;
        this.weight = weight;
    }

    public MatrixVariant variant() {
        return variant;
    }

    public String registryName() {
        return registryName;
    }

    public String configName() {
        return configName;
    }

    public int weight() {
        return weight;
    }
}
