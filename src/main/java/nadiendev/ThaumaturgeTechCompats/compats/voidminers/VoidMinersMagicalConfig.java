package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class VoidMinersMagicalConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-voidminers-magical.toml";
    public static final ModConfigSpec SPEC;

    private static final float MIN_TEXT_BRIGHTNESS = 0.8F;

    private static final String COLOR_PATTERN = "#[0-9a-fA-F]{6}";
    private static final int DEFAULT_INTERVAL = 20;
    private static final int DEFAULT_DURATION = 400;

    private static final Map<VoidMinersMagicalTier, ModConfigSpec.ConfigValue<String>> COLORS = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.IntValue> MINER_UNITS = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.DoubleValue> MINER_RADIUS = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.IntValue> MINER_DURATION = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.DoubleValue> MINER_SPEED = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.DoubleValue> MINER_PRODUCTION = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.IntValue> MINER_TIER = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.IntValue> SOLAR_UNITS = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.DoubleValue> SOLAR_RADIUS = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.LongValue> SOLAR_GENERATION = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, ModConfigSpec.LongValue> SOLAR_CAPACITY = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, Map<MagicalUpgrade, ModConfigSpec.DoubleValue>> UPGRADES = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final ModConfigSpec.IntValue MINER_INTERVAL;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> MINER_SOURCES;
    private static final ModConfigSpec.IntValue SOLAR_INTERVAL;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> SOLAR_SOURCES;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("colors");
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            COLORS.put(tier, builder.comment("Hex color of the " + tier.english() + " tier: frames, structure, beam and names. Changing it applies when resources reload (F3+T).")
                    .define(tier.id(), tier.defaultColor(), value -> value instanceof String text && text.matches(COLOR_PATTERN)));
        }
        builder.pop();
        builder.push("magical_miner");
        MINER_INTERVAL = builder.comment("Ticks of mining work that every payment to the power source covers. When the payment fails the miner stops until the next attempt.")
                .defineInRange("interval", DEFAULT_INTERVAL, 1, 72_000);
        MINER_SOURCES = builder.comment("Power sources the miners can use. Valid values: jar_node, flux_jar, rift_jar, rift, world_node. Hungry nodes are never a valid source.")
                .defineListAllowEmpty("sources", allKinds(), () -> "jar_node", value -> value instanceof String id && NodeSourceKind.byId(id) != null);
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            builder.push(tier.id());
            MINER_UNITS.put(tier, builder.comment("Units taken from the power source on every payment.")
                    .defineInRange("units", tier.defaultUnits(), 1, 100_000));
            MINER_RADIUS.put(tier, builder.comment("Radius in blocks in which the miner looks for a power source.")
                    .defineInRange("radius", tier.defaultRadius(), 1.0D, 64.0D));
            MINER_DURATION.put(tier, builder.comment("Ticks that one mining operation takes before the speed multiplier and the speed modifiers of the structure.")
                    .defineInRange("duration_ticks", DEFAULT_DURATION, 1, 10_000_000));
            MINER_SPEED.put(tier, builder.comment("Speed multiplier. The mining time is divided by this value and by the speed modifiers of the structure.")
                    .defineInRange("speed_multiplier", tier.defaultSpeed(), 0.01D, 1_000.0D));
            MINER_PRODUCTION.put(tier, builder.comment("Multiplier of the items of every mining operation, on top of the item modifiers of the structure.")
                    .defineInRange("production_multiplier", tier.defaultProduction(), 0.01D, 1_000.0D));
            MINER_TIER.put(tier, builder.comment("Void Miners tier whose mining recipes this miner runs, from 1 (Rubetine) to 9 (Ultimate). Needs a game restart.")
                    .defineInRange("mining_tier", tier.defaultMinerTier(), 1, 9));
            builder.pop();
        }
        builder.pop();
        builder.push("magical_solar_panel");
        SOLAR_INTERVAL = builder.comment("Ticks of generation that every payment to the power source covers. When the payment fails the panel stops until the next attempt.")
                .defineInRange("interval", DEFAULT_INTERVAL, 1, 72_000);
        SOLAR_SOURCES = builder.comment("Power sources the panels can use. Valid values: jar_node, flux_jar, rift_jar, rift, world_node. Hungry nodes are never a valid source.")
                .defineListAllowEmpty("sources", allKinds(), () -> "jar_node", value -> value instanceof String id && NodeSourceKind.byId(id) != null);
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            builder.push(tier.id());
            SOLAR_UNITS.put(tier, builder.comment("Units taken from the power source on every payment.")
                    .defineInRange("units", tier.defaultUnits(), 1, 100_000));
            SOLAR_RADIUS.put(tier, builder.comment("Radius in blocks in which the panel looks for a power source.")
                    .defineInRange("radius", tier.defaultRadius(), 1.0D, 64.0D));
            SOLAR_GENERATION.put(tier, builder.comment("FE per tick generated while the panel has a power source, before the efficiency modifiers of the structure. It does not depend on the sun or the weather.")
                    .defineInRange("generation_per_tick", tier.defaultGeneration(), 1L, 1_000_000_000_000L));
            SOLAR_CAPACITY.put(tier, builder.comment("FE the panel can store.")
                    .defineInRange("energy_capacity", tier.defaultCapacity(), 1L, Long.MAX_VALUE));
            builder.pop();
        }
        builder.pop();
        builder.push("upgrades");
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            builder.push(tier.id());
            Map<MagicalUpgrade, ModConfigSpec.DoubleValue> values = new EnumMap<>(MagicalUpgrade.class);
            for (MagicalUpgrade upgrade : MagicalUpgrade.values()) {
                values.put(upgrade, builder.comment("Multiplier of the " + tier.english() + " " + upgrade.english().toLowerCase() + " upgrade. Every upgrade of a structure multiplies the previous ones. Cost multipliers below 1 make the structure cheaper to run.")
                        .defineInRange(upgrade.id() + "_" + upgrade.configKey(), upgrade.defaultValue(tier), 0.01D, 1_000.0D));
            }
            UPGRADES.put(tier, values);
            builder.pop();
        }
        builder.pop();
        SPEC = builder.build();
    }

    public static float upgrade(VoidMinersMagicalTier tier, MagicalUpgrade upgrade) {
        ModConfigSpec.DoubleValue value = UPGRADES.get(tier).get(upgrade);
        return (SPEC.isLoaded() ? value.get() : value.getDefault()).floatValue();
    }

    private VoidMinersMagicalConfig() {
    }

    private static List<String> allKinds() {
        List<String> kinds = new ArrayList<>();
        for (NodeSourceKind kind : NodeSourceKind.values()) {
            kinds.add(kind.id());
        }
        return kinds;
    }

    private static Set<NodeSourceKind> kinds(ModConfigSpec.ConfigValue<List<? extends String>> value) {
        Set<NodeSourceKind> kinds = EnumSet.noneOf(NodeSourceKind.class);
        if (!SPEC.isLoaded()) {
            kinds.addAll(EnumSet.allOf(NodeSourceKind.class));
            return kinds;
        }
        for (String id : value.get()) {
            NodeSourceKind kind = NodeSourceKind.byId(id);
            if (kind != null) {
                kinds.add(kind);
            }
        }
        return kinds;
    }

    public static String hex(VoidMinersMagicalTier tier) {
        ModConfigSpec.ConfigValue<String> value = COLORS.get(tier);
        return SPEC.isLoaded() ? value.get() : tier.defaultColor();
    }

    public static int rgb(VoidMinersMagicalTier tier) {
        return Integer.parseInt(hex(tier).substring(1), 16);
    }

    public static int textRgb(VoidMinersMagicalTier tier) {
        int rgb = rgb(tier);
        float[] hsb = Color.RGBtoHSB((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255, null);
        return Color.HSBtoRGB(hsb[0], hsb[1], Math.max(hsb[2], MIN_TEXT_BRIGHTNESS)) & 0xFFFFFF;
    }

    public static int argb(VoidMinersMagicalTier tier) {
        return 0xFF000000 | rgb(tier);
    }

    public static int minerInterval() {
        return SPEC.isLoaded() ? MINER_INTERVAL.get() : MINER_INTERVAL.getDefault();
    }

    public static Set<NodeSourceKind> minerSources() {
        return kinds(MINER_SOURCES);
    }

    public static int minerUnits(VoidMinersMagicalTier tier) {
        ModConfigSpec.IntValue value = MINER_UNITS.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static double minerRadius(VoidMinersMagicalTier tier) {
        ModConfigSpec.DoubleValue value = MINER_RADIUS.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int minerDuration(VoidMinersMagicalTier tier) {
        ModConfigSpec.IntValue value = MINER_DURATION.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static float minerSpeed(VoidMinersMagicalTier tier) {
        ModConfigSpec.DoubleValue value = MINER_SPEED.get(tier);
        return (SPEC.isLoaded() ? value.get() : value.getDefault()).floatValue();
    }

    public static float minerProduction(VoidMinersMagicalTier tier) {
        ModConfigSpec.DoubleValue value = MINER_PRODUCTION.get(tier);
        return (SPEC.isLoaded() ? value.get() : value.getDefault()).floatValue();
    }

    public static int minerTier(VoidMinersMagicalTier tier) {
        ModConfigSpec.IntValue value = MINER_TIER.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int solarInterval() {
        return SPEC.isLoaded() ? SOLAR_INTERVAL.get() : SOLAR_INTERVAL.getDefault();
    }

    public static Set<NodeSourceKind> solarSources() {
        return kinds(SOLAR_SOURCES);
    }

    public static int solarUnits(VoidMinersMagicalTier tier) {
        ModConfigSpec.IntValue value = SOLAR_UNITS.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static double solarRadius(VoidMinersMagicalTier tier) {
        ModConfigSpec.DoubleValue value = SOLAR_RADIUS.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static long solarGeneration(VoidMinersMagicalTier tier) {
        ModConfigSpec.LongValue value = SOLAR_GENERATION.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static long solarCapacity(VoidMinersMagicalTier tier) {
        ModConfigSpec.LongValue value = SOLAR_CAPACITY.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
