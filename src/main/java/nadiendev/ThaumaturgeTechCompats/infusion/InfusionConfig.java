package nadiendev.ThaumaturgeTechCompats.infusion;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InfusionConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-infusion.toml";
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue TIER_RULE;

    private static final List<String> DEFAULT_SUMMONS = List.of("minecraft:endermite", "minecraft:vex", "minecraft:silverfish");
    private static final Map<String, ModConfigSpec.DoubleValue> NUMBERS = new HashMap<>();
    private static final Map<String, ModConfigSpec.IntValue> INTEGERS = new HashMap<>();
    private static final Map<String, ModConfigSpec.BooleanValue> FLAGS = new HashMap<>();
    private static ModConfigSpec.ConfigValue<List<? extends String>> summons;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("tier_rule");
        TIER_RULE = builder.comment("Everything of the Avaritia tiers that is made by infusion can only be infused on an altar whose center is the Runic Matrix of its own tier, so it suffers that matrix's instability. This is the master switch of that rule.")
                .define("enabled", true);
        for (MatrixVariant variant : MatrixVariant.values()) {
            flag(builder, variant.id(), "require_own_matrix", true,
                    "Items of this tier that are made by infusion need the Runic Matrix of this tier at the center of the altar. The matrices and the infusion stones keep following the tier before them.");
        }
        builder.pop();

        builder.push("matrix");
        for (MatrixVariant variant : MatrixVariant.values()) {
            String id = variant.id();
            builder.push(id);
            number(builder, id, "instability_multiplier", variant.defaultLossMultiplier(), 0.0D, 1000.0D,
                    "Multiplier of the stability this matrix loses every cycle. The faster it loses stability, the sooner the instability of the craft in progress starts to trigger events. 1 is the loss of the Runic Matrix.");
            integer(builder, id, "essentia_multiplier", variant.defaultEssentiaMultiplier(), 1, 10000,
                    "Every unit of essentia that enters the matrix counts as this many units of the recipe. 1 is what the Runic Matrix does.");
            flag(builder, id, "effects_enabled", true, "Turns every instability event of this matrix on or off.");
            number(builder, id, "intensity_stability_scale", 25.0D, 1.0D, 1000.0D,
                    "Every this many points of stability below zero add one more level of intensity to the events of this matrix.");
            integer(builder, id, "effect_radius", variant.defaultRadius(), 1, 64, "Radius in blocks around the matrix reached by its instability events.");
            switch (variant) {
                case MATRIX -> solar(builder, id);
                case NEUTRONIUM -> nuclear(builder, id);
                default -> cosmic(builder, id);
            }
            builder.pop();
        }
        builder.pop();

        builder.push("stones");
        for (MatrixVariant variant : MatrixVariant.values()) {
            builder.push(variant.id());
            for (StoneKind kind : StoneKind.values()) {
                String prefix = variant.id() + "." + kind.id();
                builder.push(kind.id());
                number(builder, prefix, "cost_modifier", variant.defaultStoneCost(kind), -1.0D, 1.0D,
                        "Added to the cost multiplier of the altar for every stone of this kind under a pillar. The Infusion Cost Stone gives -0.02 and the Infusion Speed Stone gives 0.01. The altar never goes below 0.5.");
                integer(builder, prefix, "cycle_time_modifier", variant.defaultStoneCycle(kind), -10, 10,
                        "Added to the cycle time of the altar for every stone of this kind under a pillar. The Infusion Cost Stone gives 1 and the Infusion Speed Stone gives -1.");
                number(builder, prefix, "stability_modifier", 0.0D, -10.0D, 10.0D,
                        "Added to the stability the altar recovers for every stone of this kind. Matrix variants ignore it, since they have no stability recovery.");
                builder.pop();
            }
            builder.pop();
        }
        builder.pop();

        builder.push("altar");
        for (MatrixVariant variant : MatrixVariant.values()) {
            builder.push(variant.id());
            for (AltarPart part : AltarPart.values()) {
                if (!part.modifies()) {
                    continue;
                }
                AltarDefaults defaults = variant.defaultAltar(part);
                String prefix = variant.id() + "." + part.id();
                boolean pillar = part == AltarPart.PILLAR;
                builder.push(part.id());
                number(builder, prefix, "cost_modifier", defaults.cost(), -1.0D, 1.0D,
                        pillar
                                ? "Added to the cost multiplier of the altar when the four pillars are of this tier. The Ancient Infusion Pillar gives -0.1 and the Eldritch one gives 0.05. The altar never goes below 0.5."
                                : "Added to the cost multiplier of the altar for every pedestal of this tier around it. The Ancient Pedestal gives -0.01. The altar never goes below 0.5.");
                integer(builder, prefix, "cycle_time_modifier", defaults.cycleTime(), -10, 10,
                        pillar
                                ? "Added to the cycle time of the altar when the four pillars are of this tier. The Ancient Infusion Pillar gives -1 and the Eldritch one gives -3."
                                : "Added to the cycle time of the altar for every pedestal of this tier around it.");
                number(builder, prefix, "stability_modifier", defaults.stability(), -10.0D, 10.0D,
                        "Added to the stability the altar recovers. Matrix variants ignore it, since they have no stability recovery, so it only matters with the plain Runic Matrix.");
                builder.pop();
            }
            builder.pop();
        }
        builder.pop();

        SPEC = builder.build();
    }

    private InfusionConfig() {
    }

    private static void solar(ModConfigSpec.Builder builder, String id) {
        number(builder, id, "day_multiplier", 2.0D, 1.0D, 100.0D, "Extra multiplier of the instability while it is day. 2 means twice as unstable by day.");
        flag(builder, id, "day_requires_sky", false, "When on, the day multiplier only applies if the matrix can see the sky.");
        flag(builder, id, "fire_enabled", true, "Solar flares set the ground around the altar on fire.");
        number(builder, id, "fire_chance", 0.6D, 0.0D, 1.0D, "Chance that an event sets fires.");
        integer(builder, id, "fire_attempts", 4, 1, 64, "Fires tried per event, multiplied by the intensity.");
        flag(builder, id, "lightning_enabled", true, "Solar flares call lightning near the altar.");
        number(builder, id, "lightning_chance", 0.25D, 0.0D, 1.0D, "Chance that an event calls lightning.");
        flag(builder, id, "burn_enabled", true, "Solar flares burn everything alive near the altar.");
        number(builder, id, "burn_chance", 0.7D, 0.0D, 1.0D, "Chance that an event burns the creatures around.");
        integer(builder, id, "burn_seconds", 4, 1, 600, "Seconds on fire per event, multiplied by the intensity.");
        number(builder, id, "burn_damage", 2.0D, 0.0D, 1000.0D, "Fire damage per event, multiplied by the intensity.");
    }

    private static void nuclear(ModConfigSpec.Builder builder, String id) {
        flag(builder, id, "radiation_enabled", true, "The altar gives off radiation that hurts and weakens everything alive near it.");
        number(builder, id, "radiation_chance", 0.8D, 0.0D, 1.0D, "Chance that an event gives off radiation.");
        number(builder, id, "radiation_damage", 2.0D, 0.0D, 1000.0D, "Radiation damage per event, multiplied by the intensity.");
        integer(builder, id, "radiation_effect_seconds", 10, 1, 3600, "Seconds of weakness, hunger and wither per event, multiplied by the intensity.");
        flag(builder, id, "meltdown_enabled", true, "The altar can melt down and explode.");
        number(builder, id, "meltdown_chance", 0.08D, 0.0D, 1.0D, "Chance that an event ends in an explosion.");
        number(builder, id, "meltdown_power", 3.0D, 0.0D, 50.0D, "Explosion power, multiplied by the intensity up to three times.");
        flag(builder, id, "meltdown_breaks_blocks", true, "When off, the explosion hurts but does not break blocks.");
        flag(builder, id, "contamination_enabled", true, "The altar pours flux into the aura around it.");
        number(builder, id, "contamination_chance", 0.5D, 0.0D, 1.0D, "Chance that an event pours flux.");
        number(builder, id, "contamination_flux", 8.0D, 0.0D, 100000.0D, "Flux poured per event, multiplied by the intensity.");
    }

    private static void cosmic(ModConfigSpec.Builder builder, String id) {
        flag(builder, id, "flux_enabled", true, "Cosmic tears pour flux into the aura around the altar.");
        number(builder, id, "flux_chance", 0.7D, 0.0D, 1.0D, "Chance that an event pours flux.");
        number(builder, id, "flux_amount", 15.0D, 0.0D, 100000.0D, "Flux poured per event, multiplied by the intensity.");
        flag(builder, id, "warp_enabled", true, "The cosmos gives warp to the players near the altar.");
        number(builder, id, "warp_chance", 0.6D, 0.0D, 1.0D, "Chance that an event gives warp.");
        integer(builder, id, "warp_amount", 2, 1, 1000, "Warp given per event, multiplied by the intensity.");
        number(builder, id, "warp_normal_chance", 0.2D, 0.0D, 1.0D, "Chance that the warp is the kind that fades slowly instead of the temporary kind.");
        flag(builder, id, "summon_enabled", true, "Creatures from beyond come through next to the altar.");
        number(builder, id, "summon_chance", 0.4D, 0.0D, 1.0D, "Chance that an event summons creatures.");
        integer(builder, id, "summon_count", 3, 1, 64, "Creatures summoned per event, multiplied by the intensity up to three times.");
        summons = builder.comment("Ids of the creatures the cosmic instability can summon around the altar.")
                .defineListAllowEmpty("summon_entities", DEFAULT_SUMMONS, () -> "minecraft:endermite", value -> value instanceof String);
        flag(builder, id, "teleport_enabled", true, "Everything alive near the altar is thrown somewhere else.");
        number(builder, id, "teleport_chance", 0.5D, 0.0D, 1.0D, "Chance that an event teleports the creatures around.");
        integer(builder, id, "teleport_distance", 16, 1, 256, "Furthest distance in blocks a creature is thrown.");
    }

    private static void number(ModConfigSpec.Builder builder, String prefix, String name, double fallback, double min, double max, String comment) {
        NUMBERS.put(prefix + "." + name, builder.comment(comment).defineInRange(name, fallback, min, max));
    }

    private static void integer(ModConfigSpec.Builder builder, String prefix, String name, int fallback, int min, int max, String comment) {
        INTEGERS.put(prefix + "." + name, builder.comment(comment).defineInRange(name, fallback, min, max));
    }

    private static void flag(ModConfigSpec.Builder builder, String prefix, String name, boolean fallback, String comment) {
        FLAGS.put(prefix + "." + name, builder.comment(comment).define(name, fallback));
    }

    public static boolean tierRuleEnabled(MatrixVariant variant) {
        return (SPEC.isLoaded() ? TIER_RULE.get() : TIER_RULE.getDefault()) && flag(variant, "require_own_matrix");
    }

    public static double number(MatrixVariant variant, String name) {
        ModConfigSpec.DoubleValue value = NUMBERS.get(variant.id() + "." + name);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int integer(MatrixVariant variant, String name) {
        ModConfigSpec.IntValue value = INTEGERS.get(variant.id() + "." + name);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static boolean flag(MatrixVariant variant, String name) {
        ModConfigSpec.BooleanValue value = FLAGS.get(variant.id() + "." + name);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static double stoneNumber(MatrixVariant variant, StoneKind kind, String name) {
        ModConfigSpec.DoubleValue value = NUMBERS.get(variant.id() + "." + kind.id() + "." + name);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int stoneInteger(MatrixVariant variant, StoneKind kind, String name) {
        ModConfigSpec.IntValue value = INTEGERS.get(variant.id() + "." + kind.id() + "." + name);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static double altarNumber(MatrixVariant variant, AltarPart part, String name) {
        ModConfigSpec.DoubleValue value = NUMBERS.get(variant.id() + "." + part.id() + "." + name);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int altarInteger(MatrixVariant variant, AltarPart part, String name) {
        ModConfigSpec.IntValue value = INTEGERS.get(variant.id() + "." + part.id() + "." + name);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static List<? extends String> summonEntities() {
        return SPEC.isLoaded() ? summons.get() : summons.getDefault();
    }
}
