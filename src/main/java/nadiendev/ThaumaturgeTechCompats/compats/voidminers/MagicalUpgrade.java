package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import java.util.ArrayList;
import java.util.List;

public enum MagicalUpgrade {
    SPEED("speed", false, "speed", "speed_multiplier", "Speed", "velocidad", "voidminersremastered:rubetine_speed_modifier",
            new double[]{1.15D, 1.35D, 1.7D}),
    PRODUCTION("production", false, "item", "production_multiplier", "Production", "producción", "voidminersremastered:rubetine_item_modifier",
            new double[]{1.1D, 1.25D, 1.5D}),
    EFFICIENCY("efficiency", false, "energy", "power_cost_multiplier", "Efficiency", "eficiencia", "voidminersremastered:rubetine_energy_modifier",
            new double[]{0.9D, 0.75D, 0.55D}),
    GENERATION("generation", true, "energy", "generation_multiplier", "Generation", "generación", "voidminersremastered:solar_rubetine_efficiency_modifier",
            new double[]{1.15D, 1.35D, 1.7D}),
    SAVING("saving", true, "speed", "power_cost_multiplier", "Saving", "ahorro", "voidminersremastered:solar_rubetine_weather_modifier",
            new double[]{0.9D, 0.75D, 0.55D});

    private final String id;
    private final boolean solar;
    private final String icon;
    private final String configKey;
    private final String english;
    private final String spanish;
    private final String base;
    private final double[] defaults;

    MagicalUpgrade(String id, boolean solar, String icon, String configKey, String english, String spanish, String base, double[] defaults) {
        this.id = id;
        this.solar = solar;
        this.icon = icon;
        this.configKey = configKey;
        this.english = english;
        this.spanish = spanish;
        this.base = base;
        this.defaults = defaults;
    }

    public static List<MagicalUpgrade> of(boolean solar) {
        List<MagicalUpgrade> upgrades = new ArrayList<>();
        for (MagicalUpgrade upgrade : values()) {
            if (upgrade.solar == solar) {
                upgrades.add(upgrade);
            }
        }
        return upgrades;
    }

    public String id() {
        return id;
    }

    public boolean solar() {
        return solar;
    }

    public String icon() {
        return icon;
    }

    public String configKey() {
        return configKey;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public String base() {
        return base;
    }

    public double defaultValue(VoidMinersMagicalTier tier) {
        return defaults[tier.ordinal()];
    }

    public String blockName(VoidMinersMagicalTier tier) {
        return tier.id() + "_" + (solar ? "solar_" : "") + id + "_upgrade";
    }

    public String tooltipKey() {
        return VoidMinersMagicalTranslations.TOOLTIP + "upgrade." + (solar ? "solar_" : "") + id;
    }
}
