package nadiendev.ThaumaturgeTechCompats.braincharger;

import nadiendev.ThaumaturgeTechCompats.tiercontent.Tier;

public enum BoostTier {
    MATRIX(Tier.MATRIX, 4, 30, 2),
    NEUTRONIUM(Tier.NEUTRONIUM, 6, 60, 3),
    INFINITY(Tier.INFINITY, 10, 90, 4);

    private final Tier tier;
    private final int defaultFormulas;
    private final int defaultDiscount;
    private final int defaultRadius;

    BoostTier(Tier tier, int defaultFormulas, int defaultDiscount, int defaultRadius) {
        this.tier = tier;
        this.defaultFormulas = defaultFormulas;
        this.defaultDiscount = defaultDiscount;
        this.defaultRadius = defaultRadius;
    }

    public Tier tier() {
        return tier;
    }

    public String id() {
        return tier.id();
    }

    public String boxName() {
        return tier.id() + "_brain_box";
    }

    public String chargerName() {
        return tier.id() + "_workbench_charger";
    }

    public int defaultFormulas() {
        return defaultFormulas;
    }

    public int defaultDiscount() {
        return defaultDiscount;
    }

    public int defaultRadius() {
        return defaultRadius;
    }

    public BoostTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }
}
