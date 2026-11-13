package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ForceCraftConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-forcecraft.toml";
    public static final ModConfigSpec SPEC;

    private static final List<String> DEFAULT_BLOCKED = List.of(
            "thaumaturge:eldritch_hierophant",
            "thaumaturge:eldritch_warden",
            "thaumaturge:cultist_leader",
            "thaumaturge:taint_seed",
            "thaumaturge:taint_seed_prime");

    private static final ModConfigSpec.BooleanValue REPAIR_ENABLED;
    private static final ModConfigSpec.IntValue REPAIR_INTERVAL;
    private static final ModConfigSpec.IntValue REPAIR_POINTS;
    private static final ModConfigSpec.DoubleValue REPAIR_VIS_PER_POINT;
    private static final ModConfigSpec.BooleanValue REPAIR_WHOLE_INVENTORY;
    private static final ModConfigSpec.BooleanValue REPAIR_RESPECT_PRESERVATION;
    private static final ModConfigSpec.BooleanValue REPAIR_TOOLTIP;
    private static final ModConfigSpec.BooleanValue FLASK_ENABLED;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> FLASK_BLOCKED;

    private static Set<ResourceLocation> blockedCache = Set.of();
    private static List<? extends String> blockedSource = List.of();

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("repair");
        REPAIR_ENABLED = builder.comment("Items with the Repair infusion repair themselves with vis from the aura. Off keeps the infusion but nothing repairs.")
                .define("enabled", true);
        REPAIR_INTERVAL = builder.comment("Ticks between two repair pulses on a player.")
                .defineInRange("interval_ticks", 100, 1, 72000);
        REPAIR_POINTS = builder.comment("Durability points repaired on every pulse, for each item.")
                .defineInRange("points_per_pulse", 1, 1, 1000);
        REPAIR_VIS_PER_POINT = builder.comment("Vis drained from the aura of the chunk for every durability point repaired. 0 makes repairing free.")
                .defineInRange("vis_per_point", 0.5D, 0.0D, 10000.0D);
        REPAIR_WHOLE_INVENTORY = builder.comment("Repairs every infused item in the inventory. Off repairs only armor, the main hand and the off hand.")
                .define("whole_inventory", true);
        REPAIR_RESPECT_PRESERVATION = builder.comment("Does not drain the aura while Thaumaturge asks to preserve it.")
                .define("respect_aura_preservation", true);
        REPAIR_TOOLTIP = builder.comment("Shows a line in the tooltip of the items with the Repair infusion.")
                .define("tooltip", true);
        builder.pop();

        builder.push("flask");
        FLASK_ENABLED = builder.comment("The Force Flask ignores the entities of the list below. Mobs of Thaumaturge not listed are captured like any other mob.")
                .define("enabled", true);
        FLASK_BLOCKED = builder.comment("Entities the Force Flask cannot capture, on top of the forcecraft:flask_blacklist tag.")
                .defineListAllowEmpty("blocked_entities", DEFAULT_BLOCKED, () -> "thaumaturge:example", ForceCraftConfig::validId);
        builder.pop();

        SPEC = builder.build();
    }

    private ForceCraftConfig() {
    }

    private static boolean validId(Object value) {
        return value instanceof String text && ResourceLocation.tryParse(text) != null;
    }

    private static <T> T read(ModConfigSpec.ConfigValue<T> value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static boolean repairEnabled() {
        return CompatToggles.enabled(CompatToggles.FORCECRAFT)
                && CompatToggles.enabled(CompatToggles.FORCECRAFT_REPAIR)
                && read(REPAIR_ENABLED);
    }

    public static int repairInterval() {
        return read(REPAIR_INTERVAL);
    }

    public static int repairPoints() {
        return read(REPAIR_POINTS);
    }

    public static float repairVisPerPoint() {
        return read(REPAIR_VIS_PER_POINT).floatValue();
    }

    public static boolean repairWholeInventory() {
        return read(REPAIR_WHOLE_INVENTORY);
    }

    public static boolean repairRespectPreservation() {
        return read(REPAIR_RESPECT_PRESERVATION);
    }

    public static boolean repairTooltip() {
        return read(REPAIR_TOOLTIP);
    }

    public static boolean flaskEnabled() {
        return CompatToggles.enabled(CompatToggles.FORCECRAFT)
                && CompatToggles.enabled(CompatToggles.FORCECRAFT_FLASK)
                && read(FLASK_ENABLED);
    }

    public static boolean flaskBlocks(ResourceLocation entity) {
        List<? extends String> source = read(FLASK_BLOCKED);
        if (source != blockedSource) {
            Set<ResourceLocation> parsed = new HashSet<>();
            for (String text : source) {
                ResourceLocation id = ResourceLocation.tryParse(text);
                if (id != null) {
                    parsed.add(id);
                }
            }
            blockedCache = parsed;
            blockedSource = source;
        }
        return blockedCache.contains(entity);
    }
}
