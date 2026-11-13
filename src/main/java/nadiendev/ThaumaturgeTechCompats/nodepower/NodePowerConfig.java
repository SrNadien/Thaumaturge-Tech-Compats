package nadiendev.ThaumaturgeTechCompats.nodepower;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class NodePowerConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-nodepower.toml";
    public static final ModConfigSpec SPEC;

    private static final List<String> DEFAULT_ORDER = List.of("jar_node", "flux_jar", "rift_jar", "rift", "world_node");
    private static final int DEFAULT_FLUX_FILL_INTERVAL = 20;
    private static final double DEFAULT_RIFT_SHRINK_CHANCE = 0.33D;
    private static final int DEFAULT_RIFT_MIN_SIZE = 1;
    private static final double DEFAULT_NODE_CONSUME_RATIO = 0.0D;
    private static final double DEFAULT_RIFT_JAR_CAPACITY = 200.0D;
    private static final double DEFAULT_RIFT_JAR_CAPTURE_RADIUS = 4.0D;
    private static final int DEFAULT_RIFT_JAR_CAPTURE_INTERVAL = 40;
    private static final double DEFAULT_RIFT_JAR_POWER_PER_SIZE = 20.0D;
    private static final double DEFAULT_RIFT_JAR_RELEASE_RATIO = 0.05D;

    private static final ModConfigSpec.ConfigValue<List<? extends String>> ORDER;
    private static final Map<FluxJarTier, ModConfigSpec.DoubleValue> FLUX_JAR_CAPACITIES = new EnumMap<>(FluxJarTier.class);
    private static final Map<FluxJarTier, ModConfigSpec.DoubleValue> FLUX_JAR_FILL_AMOUNTS = new EnumMap<>(FluxJarTier.class);
    private static final ModConfigSpec.IntValue FLUX_JAR_FILL_INTERVAL;
    private static final ModConfigSpec.DoubleValue RIFT_SHRINK_CHANCE;
    private static final ModConfigSpec.IntValue RIFT_MIN_SIZE;
    private static final ModConfigSpec.DoubleValue NODE_CONSUME_RATIO;
    private static final ModConfigSpec.DoubleValue RIFT_JAR_CAPACITY;
    private static final ModConfigSpec.DoubleValue RIFT_JAR_CAPTURE_RADIUS;
    private static final ModConfigSpec.IntValue RIFT_JAR_CAPTURE_INTERVAL;
    private static final ModConfigSpec.DoubleValue RIFT_JAR_POWER_PER_SIZE;
    private static final ModConfigSpec.DoubleValue RIFT_JAR_RELEASE_RATIO;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("sources");
        ORDER = builder.comment("Order in which a machine looks for a power source. A machine uses only the first one it finds. Valid values: jar_node, flux_jar, rift_jar, rift, world_node. Hungry nodes are never a valid source.")
                .defineListAllowEmpty("order", DEFAULT_ORDER, () -> "jar_node", value -> value instanceof String id && NodeSourceKind.byId(id) != null);
        builder.pop();
        builder.push("nodes");
        NODE_CONSUME_RATIO = builder.comment("Fraction of the power a machine draws from a node or a jarred node that is really removed from its aspects. 0 makes nodes an inexhaustible power source, 1 makes them a tank that empties.")
                .defineInRange("consume_ratio", DEFAULT_NODE_CONSUME_RATIO, 0.0D, 1.0D);
        builder.pop();
        builder.push("flux_jar");
        for (FluxJarTier tier : FluxJarTier.values()) {
            FLUX_JAR_CAPACITIES.put(tier, builder.comment("Units of flux the " + tier.blockName() + " can hold.")
                    .defineInRange(tier.blockName() + "_capacity", tier.defaultCapacity(), 1.0D, 1_000_000.0D));
            FLUX_JAR_FILL_AMOUNTS.put(tier, builder.comment("Flux the " + tier.blockName() + " pulls from the aura of its chunk on every fill.")
                    .defineInRange(tier.blockName() + "_fill_amount", tier.defaultFillAmount(), 0.001D, 10_000.0D));
        }
        FLUX_JAR_FILL_INTERVAL = builder.comment("Ticks between two fills of any Flux Jar.")
                .defineInRange("fill_interval", DEFAULT_FLUX_FILL_INTERVAL, 1, 72_000);
        builder.pop();
        builder.push("rift");
        RIFT_SHRINK_CHANCE = builder.comment("Chance that a flux rift shrinks by one point for every unit of power taken from it.")
                .defineInRange("shrink_chance", DEFAULT_RIFT_SHRINK_CHANCE, 0.0D, 1.0D);
        RIFT_MIN_SIZE = builder.comment("A flux rift at or below this size stops being a power source.")
                .defineInRange("min_size", DEFAULT_RIFT_MIN_SIZE, 1, 100);
        builder.pop();
        builder.push("rift_jar");
        RIFT_JAR_CAPACITY = builder.comment("Units of power the Rift Jar can hold.")
                .defineInRange("capacity", DEFAULT_RIFT_JAR_CAPACITY, 1.0D, 1_000_000.0D);
        RIFT_JAR_CAPTURE_RADIUS = builder.comment("Blocks around the Rift Jar in which it absorbs a flux rift.")
                .defineInRange("capture_radius", DEFAULT_RIFT_JAR_CAPTURE_RADIUS, 1.0D, 32.0D);
        RIFT_JAR_CAPTURE_INTERVAL = builder.comment("Ticks between two absorptions of the Rift Jar. Every absorption shrinks the rift by one point.")
                .defineInRange("capture_interval", DEFAULT_RIFT_JAR_CAPTURE_INTERVAL, 1, 72_000);
        RIFT_JAR_POWER_PER_SIZE = builder.comment("Units of power the Rift Jar gains for every point of size it takes from a rift.")
                .defineInRange("power_per_size", DEFAULT_RIFT_JAR_POWER_PER_SIZE, 0.001D, 100_000.0D);
        RIFT_JAR_RELEASE_RATIO = builder.comment("Flux poured into the aura for every unit of power left in a Rift Jar that is broken.")
                .defineInRange("release_ratio", DEFAULT_RIFT_JAR_RELEASE_RATIO, 0.0D, 1_000.0D);
        builder.pop();
        SPEC = builder.build();
    }

    private NodePowerConfig() {
    }

    public static List<NodeSourceKind> order() {
        List<? extends String> ids = SPEC.isLoaded() ? ORDER.get() : DEFAULT_ORDER;
        List<NodeSourceKind> order = new ArrayList<>();
        for (String id : ids) {
            NodeSourceKind kind = NodeSourceKind.byId(id);
            if (kind != null && !order.contains(kind)) {
                order.add(kind);
            }
        }
        return order;
    }

    public static float fluxJarCapacity(FluxJarTier tier) {
        return (float) (SPEC.isLoaded() ? FLUX_JAR_CAPACITIES.get(tier).get() : tier.defaultCapacity());
    }

    public static float fluxJarFillAmount(FluxJarTier tier) {
        return (float) (SPEC.isLoaded() ? FLUX_JAR_FILL_AMOUNTS.get(tier).get() : tier.defaultFillAmount());
    }

    public static int fluxJarFillInterval() {
        return SPEC.isLoaded() ? FLUX_JAR_FILL_INTERVAL.get() : DEFAULT_FLUX_FILL_INTERVAL;
    }

    public static float riftShrinkChance() {
        return (float) (SPEC.isLoaded() ? RIFT_SHRINK_CHANCE.get() : DEFAULT_RIFT_SHRINK_CHANCE);
    }

    public static float nodeConsumeRatio() {
        return (float) (SPEC.isLoaded() ? NODE_CONSUME_RATIO.get() : DEFAULT_NODE_CONSUME_RATIO);
    }

    public static int riftMinSize() {
        return SPEC.isLoaded() ? RIFT_MIN_SIZE.get() : DEFAULT_RIFT_MIN_SIZE;
    }

    public static float riftJarCapacity() {
        return (float) (SPEC.isLoaded() ? RIFT_JAR_CAPACITY.get() : DEFAULT_RIFT_JAR_CAPACITY);
    }

    public static double riftJarCaptureRadius() {
        return SPEC.isLoaded() ? RIFT_JAR_CAPTURE_RADIUS.get() : DEFAULT_RIFT_JAR_CAPTURE_RADIUS;
    }

    public static int riftJarCaptureInterval() {
        return SPEC.isLoaded() ? RIFT_JAR_CAPTURE_INTERVAL.get() : DEFAULT_RIFT_JAR_CAPTURE_INTERVAL;
    }

    public static float riftJarPowerPerSize() {
        return (float) (SPEC.isLoaded() ? RIFT_JAR_POWER_PER_SIZE.get() : DEFAULT_RIFT_JAR_POWER_PER_SIZE);
    }

    public static float riftJarReleaseRatio() {
        return (float) (SPEC.isLoaded() ? RIFT_JAR_RELEASE_RATIO.get() : DEFAULT_RIFT_JAR_RELEASE_RATIO);
    }
}
