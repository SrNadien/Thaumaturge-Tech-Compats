package nadiendev.ThaumaturgeTechCompats.braincharger;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class BrainChargerConfig {
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue BOXES_ENABLED;
    private static final ModConfigSpec.BooleanValue CHARGERS_ENABLED;
    private static final Map<BoostTier, ModConfigSpec.IntValue> FORMULAS = new EnumMap<>(BoostTier.class);
    private static final Map<BoostTier, ModConfigSpec.IntValue> DISCOUNTS = new EnumMap<>(BoostTier.class);
    private static final Map<BoostTier, ModConfigSpec.IntValue> RADII = new EnumMap<>(BoostTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("mnemonic_matrices");
        BOXES_ENABLED = builder.comment("Lets the Avaritia mnemonic matrices raise the number of formulas a Thaumatorium can work on at once.")
                .define("enabled", true);
        for (BoostTier tier : BoostTier.values()) {
            FORMULAS.put(tier, builder.comment("Simultaneous formulas added by one " + tier.boxName() + ". The Thaumaturge mnemonic matrix adds 2. Odd values are rounded up to the next even number.")
                    .defineInRange(tier.id() + "_formulas", tier.defaultFormulas(), 1, 1000));
        }
        builder.pop();

        builder.push("workbench_chargers");
        CHARGERS_ENABLED = builder.comment("Lets the Avaritia workbench chargers use their own range and vis discount.")
                .define("enabled", true);
        for (BoostTier tier : BoostTier.values()) {
            DISCOUNTS.put(tier, builder.comment("Percentage of the vis cost of an arcane craft that the " + tier.chargerName() + " does not charge.")
                    .defineInRange(tier.id() + "_discount_percent", tier.defaultDiscount(), 0, 100));
            RADII.put(tier, builder.comment("Radius in chunks around the workbench from which the " + tier.chargerName() + " draws vis. The Thaumaturge charger uses 1.")
                    .defineInRange(tier.id() + "_radius_chunks", tier.defaultRadius(), 1, 8));
        }
        builder.pop();

        SPEC = builder.build();
    }

    private BrainChargerConfig() {
    }

    public static boolean boxesEnabled() {
        return !SPEC.isLoaded() || BOXES_ENABLED.get();
    }

    public static boolean chargersEnabled() {
        return !SPEC.isLoaded() || CHARGERS_ENABLED.get();
    }

    public static int formulas(BoostTier tier) {
        return SPEC.isLoaded() ? FORMULAS.get(tier).get() : tier.defaultFormulas();
    }

    public static int discountPercent(BoostTier tier) {
        return SPEC.isLoaded() ? DISCOUNTS.get(tier).get() : tier.defaultDiscount();
    }

    public static int radius(BoostTier tier) {
        return SPEC.isLoaded() ? RADII.get(tier).get() : tier.defaultRadius();
    }
}
