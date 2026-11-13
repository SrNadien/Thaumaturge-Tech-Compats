package nadiendev.ThaumaturgeTechCompats.compats.oritech;

public enum OritechTier {
    BRASS("brass", "thaumaturge:plate_brass", "thaumaturge:mechanism_simple", 100, 2,
            0.4D, 1.1D, 0.6D, 10_000_000L, 10_000L,
            "Brass", "de latón"),
    THAUMIUM("thaumium", "thaumaturge:plate_thaumium", "thaumaturge:mechanism_complex", 250, 4,
            0.2D, 1.0D, 0.4D, 100_000_000L, 100_000L,
            "Thaumium", "de taumio"),
    VOID("void", "thaumaturge:plate_void", "thaumaturge:mechanism_complex", 500, 6,
            0.1D, 0.9D, 0.2D, 1_000_000_000L, 1_000_000L,
            "Void Metal", "de metal del vacío");

    private final String id;
    private final String plate;
    private final String mechanism;
    private final int vis;
    private final int crystals;
    private final double speedAddonSpeed;
    private final double speedAddonEnergy;
    private final double efficiencyAddonEnergy;
    private final long capacitorCapacity;
    private final long capacitorInsert;
    private final String englishName;
    private final String spanishSuffix;

    OritechTier(String id, String plate, String mechanism, int vis, int crystals, double speedAddonSpeed, double speedAddonEnergy, double efficiencyAddonEnergy, long capacitorCapacity, long capacitorInsert, String englishName, String spanishSuffix) {
        this.id = id;
        this.plate = plate;
        this.mechanism = mechanism;
        this.vis = vis;
        this.crystals = crystals;
        this.speedAddonSpeed = speedAddonSpeed;
        this.speedAddonEnergy = speedAddonEnergy;
        this.efficiencyAddonEnergy = efficiencyAddonEnergy;
        this.capacitorCapacity = capacitorCapacity;
        this.capacitorInsert = capacitorInsert;
        this.englishName = englishName;
        this.spanishSuffix = spanishSuffix;
    }

    public String id() {
        return id;
    }

    public String plate() {
        return plate;
    }

    public String mechanism() {
        return mechanism;
    }

    public OritechTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }

    public int vis() {
        return vis;
    }

    public int crystals() {
        return crystals;
    }

    public double speedAddonSpeed() {
        return speedAddonSpeed;
    }

    public double speedAddonEnergy() {
        return speedAddonEnergy;
    }

    public double efficiencyAddonEnergy() {
        return efficiencyAddonEnergy;
    }

    public long capacitorCapacity() {
        return capacitorCapacity;
    }

    public long capacitorInsert() {
        return capacitorInsert;
    }

    public String englishName() {
        return englishName;
    }

    public String spanishSuffix() {
        return spanishSuffix;
    }
}
