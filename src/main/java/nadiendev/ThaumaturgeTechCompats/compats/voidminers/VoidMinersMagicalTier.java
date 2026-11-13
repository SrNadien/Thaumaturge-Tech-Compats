package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import net.minecraft.world.item.Rarity;

public enum VoidMinersMagicalTier {
    THAUMIUM("thaumium", "Thaumium", "taumio", "thaumaturge:plate_thaumium", "thaumaturge:mechanism_simple", "#c685ff",
            3, 1.5D, 1.5D, 1, 8.0D, 1_000L, 25_000_000L, Rarity.UNCOMMON),
    ALCHEMICAL_BRASS("alchemical_brass", "Alchemical Brass", "latón alquímico", "thaumaturge:plate_brass", "thaumaturge:mechanism_simple", "#edb11a",
            5, 3.0D, 2.0D, 2, 12.0D, 2_500L, 100_000_000L, Rarity.RARE),
    VOID_METAL("void_metal", "Void Metal", "metal del vacío", "thaumaturge:plate_void", "thaumaturge:mechanism_complex", "#2b085c",
            7, 5.0D, 3.0D, 4, 16.0D, 6_000L, 500_000_000L, Rarity.EPIC);

    private final String id;
    private final String english;
    private final String spanish;
    private final String plate;
    private final String mechanism;
    private final String color;
    private final int minerTier;
    private final double speed;
    private final double production;
    private final int units;
    private final double radius;
    private final long generation;
    private final long capacity;
    private final Rarity rarity;

    VoidMinersMagicalTier(String id, String english, String spanish, String plate, String mechanism, String color,
                          int minerTier, double speed, double production, int units, double radius,
                          long generation, long capacity, Rarity rarity) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.plate = plate;
        this.mechanism = mechanism;
        this.color = color;
        this.minerTier = minerTier;
        this.speed = speed;
        this.production = production;
        this.units = units;
        this.radius = radius;
        this.generation = generation;
        this.capacity = capacity;
        this.rarity = rarity;
    }

    public String id() {
        return id;
    }

    public String minerBlock() {
        return id + "_miner";
    }

    public String solarBlock() {
        return id + "_solar_panel";
    }

    public String minerFrameBlock() {
        return id + "_miner_frame";
    }

    public String solarFrameBlock() {
        return id + "_solar_frame";
    }

    public String minerToggle() {
        return VoidMinersMagicalIntegration.TOGGLE_MINER + "_" + id;
    }

    public String solarToggle() {
        return VoidMinersMagicalIntegration.TOGGLE_SOLAR + "_" + id;
    }

    public String minerEntry() {
        return minerToggle();
    }

    public String solarEntry() {
        return solarToggle();
    }

    public String minerUpgradesToggle() {
        return VoidMinersMagicalIntegration.TOGGLE_MINER + "_upgrades_" + id;
    }

    public String solarUpgradesToggle() {
        return VoidMinersMagicalIntegration.TOGGLE_SOLAR + "_upgrades_" + id;
    }

    public String upgradesEntry(boolean solar) {
        return solar ? solarUpgradesToggle() : minerUpgradesToggle();
    }

    public String minerStructure() {
        return "miner_" + id;
    }

    public String solarStructure() {
        return "solar_" + id;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public String plate() {
        return plate;
    }

    public String mechanism() {
        return mechanism;
    }

    public String defaultColor() {
        return color;
    }

    public int defaultMinerTier() {
        return minerTier;
    }

    public double defaultSpeed() {
        return speed;
    }

    public double defaultProduction() {
        return production;
    }

    public int defaultUnits() {
        return units;
    }

    public double defaultRadius() {
        return radius;
    }

    public long defaultGeneration() {
        return generation;
    }

    public long defaultCapacity() {
        return capacity;
    }

    public Rarity rarity() {
        return rarity;
    }

    public VoidMinersMagicalTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }

    public boolean last() {
        return ordinal() == values().length - 1;
    }
}
