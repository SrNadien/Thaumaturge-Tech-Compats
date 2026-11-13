package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import net.minecraft.world.item.Rarity;

public enum MagicalUneartherTier {
    THAUMIUM("thaumium", MagicalUneartherIntegration.TOGGLE_THAUMIUM, "thaumaturge:plate_thaumium", "thaumaturge:mechanism_simple",
            "Thaumium", "taumio", 2.0D, 2, 8.0D, 40, Rarity.COMMON),
    BRASS("alchemical_brass", MagicalUneartherIntegration.TOGGLE_BRASS, "thaumaturge:plate_brass", "thaumaturge:mechanism_simple",
            "Alchemical Brass", "latón alquímico", 3.0D, 3, 12.0D, 70, Rarity.UNCOMMON),
    VOID("void_metal", MagicalUneartherIntegration.TOGGLE_VOID, "thaumaturge:plate_void", "thaumaturge:mechanism_complex",
            "Void Metal", "metal del vacío", 5.0D, 4, 16.0D, 110, Rarity.RARE);

    private final String id;
    private final String toggle;
    private final String plate;
    private final String mechanism;
    private final String english;
    private final String spanish;
    private final double multiplier;
    private final int cost;
    private final double radius;
    private final int vis;
    private final Rarity rarity;

    MagicalUneartherTier(String id, String toggle, String plate, String mechanism, String english, String spanish,
                         double multiplier, int cost, double radius, int vis, Rarity rarity) {
        this.id = id;
        this.toggle = toggle;
        this.plate = plate;
        this.mechanism = mechanism;
        this.english = english;
        this.spanish = spanish;
        this.multiplier = multiplier;
        this.cost = cost;
        this.radius = radius;
        this.vis = vis;
        this.rarity = rarity;
    }

    public String id() {
        return id;
    }

    public String blockName() {
        return id + "_unearther";
    }

    public String entryName() {
        return "ftbunearthed_magical_" + id;
    }

    public String toggle() {
        return toggle;
    }

    public String plate() {
        return plate;
    }

    public String mechanism() {
        return mechanism;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public double defaultMultiplier() {
        return multiplier;
    }

    public int defaultCost() {
        return cost;
    }

    public double defaultRadius() {
        return radius;
    }

    public int vis() {
        return vis;
    }

    public Rarity rarity() {
        return rarity;
    }

    public MagicalUneartherTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }
}
