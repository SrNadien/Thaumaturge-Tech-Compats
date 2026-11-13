package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MagicalUneartherConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-ftbunearthed-magical.toml";
    public static final ModConfigSpec SPEC;

    private static final int DEFAULT_INTERVAL = 20;
    private static final boolean DEFAULT_STOP_WITHOUT_SOURCE = false;

    private static final ModConfigSpec.IntValue INTERVAL;
    private static final ModConfigSpec.BooleanValue STOP_WITHOUT_SOURCE;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> SOURCES;
    private static final Map<MagicalUneartherTier, ModConfigSpec.DoubleValue> MULTIPLIER = new EnumMap<>(MagicalUneartherTier.class);
    private static final Map<MagicalUneartherTier, ModConfigSpec.IntValue> COST = new EnumMap<>(MagicalUneartherTier.class);
    private static final Map<MagicalUneartherTier, ModConfigSpec.DoubleValue> RADIUS = new EnumMap<>(MagicalUneartherTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("general");
        INTERVAL = builder.comment("Ticks between two power checks while the machine is working. Every successful check spends the cost of its tier.")
                .defineInRange("interval", DEFAULT_INTERVAL, 1, 72_000);
        STOP_WITHOUT_SOURCE = builder.comment("If true the machine stops while it has no power source. If false it keeps working at the normal speed of an Unearther.")
                .define("stop_without_source", DEFAULT_STOP_WITHOUT_SOURCE);
        List<String> kinds = new ArrayList<>();
        for (NodeSourceKind kind : NodeSourceKind.values()) {
            kinds.add(kind.id());
        }
        SOURCES = builder.comment("Power sources the magical Unearthers can use. Valid values: jar_node, flux_jar, rift, world_node. Hungry nodes are never a valid source.")
                .defineListAllowEmpty("sources", kinds, () -> "jar_node", value -> value instanceof String id && NodeSourceKind.byId(id) != null);
        builder.pop();
        for (MagicalUneartherTier tier : MagicalUneartherTier.values()) {
            builder.push(tier.id());
            MULTIPLIER.put(tier, builder.comment("Speed multiplier while the machine has power. 1.0 is the normal speed of an Unearther.")
                    .defineInRange("speed_multiplier", tier.defaultMultiplier(), 1.0D, 100.0D));
            COST.put(tier, builder.comment("Units taken from the power source on every check.")
                    .defineInRange("cost", tier.defaultCost(), 1, 100_000));
            RADIUS.put(tier, builder.comment("Radius in blocks in which the machine looks for a power source.")
                    .defineInRange("radius", tier.defaultRadius(), 1.0D, 64.0D));
            builder.pop();
        }
        SPEC = builder.build();
    }

    private MagicalUneartherConfig() {
    }

    public static int interval() {
        return SPEC.isLoaded() ? INTERVAL.get() : DEFAULT_INTERVAL;
    }

    public static boolean stopWithoutSource() {
        return SPEC.isLoaded() ? STOP_WITHOUT_SOURCE.get() : DEFAULT_STOP_WITHOUT_SOURCE;
    }

    public static Set<NodeSourceKind> sources() {
        Set<NodeSourceKind> kinds = EnumSet.noneOf(NodeSourceKind.class);
        if (!SPEC.isLoaded()) {
            kinds.addAll(EnumSet.allOf(NodeSourceKind.class));
            return kinds;
        }
        for (String id : SOURCES.get()) {
            NodeSourceKind kind = NodeSourceKind.byId(id);
            if (kind != null) {
                kinds.add(kind);
            }
        }
        return kinds;
    }

    public static double multiplier(MagicalUneartherTier tier) {
        ModConfigSpec.DoubleValue value = MULTIPLIER.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int speedBoost(MagicalUneartherTier tier) {
        return Math.max(0, (int) Math.round((multiplier(tier) - 1.0D) * 100.0D));
    }

    public static int cost(MagicalUneartherTier tier) {
        ModConfigSpec.IntValue value = COST.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static double radius(MagicalUneartherTier tier) {
        ModConfigSpec.DoubleValue value = RADIUS.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
