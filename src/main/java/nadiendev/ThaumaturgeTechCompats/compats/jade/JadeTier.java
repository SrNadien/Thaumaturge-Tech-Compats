package nadiendev.ThaumaturgeTechCompats.compats.jade;

public enum JadeTier {
    MATRIX("matrix", 0x55FFFF),
    NEUTRONIUM("neutronium", 0x8FA3B8),
    INFINITY("infinity", 0xB266FF);

    private final String id;
    private final int color;

    JadeTier(String id, int color) {
        this.id = id;
        this.color = color;
    }

    public String id() {
        return id;
    }

    public int color() {
        return color;
    }

    public String translationKey() {
        return "jade.thaumaturgetechcompats.tier." + id;
    }

    public static JadeTier byOrdinal(int ordinal) {
        JadeTier[] tiers = values();
        return ordinal >= 0 && ordinal < tiers.length ? tiers[ordinal] : null;
    }
}
