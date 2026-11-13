package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

public enum AddonKind {
    SPEED("speed", "Speed", "velocidad", 1.0, 64.0, 4.0, 6.0, 8.0),
    EFFICIENCY("efficiency", "Efficiency", "eficiencia", 0.05, 1.0, 0.7, 0.55, 0.4),
    PROCESSING("processing", "Processing", "procesamiento", 1.0, 64.0, 4.0, 6.0, 8.0),
    RANGE("range", "Range", "rango", 0.0, 64.0, 12.0, 14.0, 16.0);

    private final String id;
    private final String english;
    private final String spanish;
    private final double min;
    private final double max;
    private final double[] defaults;

    AddonKind(String id, String english, String spanish, double min, double max, double... defaults) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.min = min;
        this.max = max;
        this.defaults = defaults;
    }

    public String id() {
        return id;
    }

    public String augment() {
        return english;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public double defaultValue(AddonTier tier) {
        return defaults[tier.ordinal()];
    }
}
