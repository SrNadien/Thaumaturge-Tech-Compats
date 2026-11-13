package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor;

import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class ThaummotorConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-thaummotor.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<NodeSourceKind, ModConfigSpec.DoubleValue> MULTIPLIERS = new EnumMap<>(NodeSourceKind.class);
    private static final ModConfigSpec.IntValue CHECK_INTERVAL;
    private static final ModConfigSpec.IntValue DRAIN_UNITS;
    private static final ModConfigSpec.DoubleValue RADIUS;
    private static final ModConfigSpec.IntValue SPEED;
    private static final ModConfigSpec.IntValue MAX_SPEED;
    private static final ModConfigSpec.DoubleValue BASE_CAPACITY;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("power");
        CHECK_INTERVAL = builder.comment("Ticks between two moments in which the Thaummotor looks for its power source and takes power from it. The Thaummotor keeps generating until the next check.")
                .defineInRange("check_interval_ticks", 20, 1, 12_000);
        DRAIN_UNITS = builder.comment("Units of power taken from the source on every check. If the source cannot give this much, the Thaummotor stops generating.")
                .defineInRange("drain_units", 1, 1, 1_000);
        RADIUS = builder.comment("Distance in blocks within which the Thaummotor looks for a power source. A hungry node is never a valid source.")
                .defineInRange("source_radius", 8.0D, 1.0D, 64.0D);
        builder.pop();
        builder.push("kinetics");
        SPEED = builder.comment("Default rotation speed in RPM of a new Thaummotor and the reference speed its stress capacity is calculated for. The speed of each Thaummotor can then be changed with its selector.")
                .defineInRange("speed_rpm", 64, 1, 256);
        MAX_SPEED = builder.comment("Highest rotation speed in RPM that the selector of a Thaummotor allows, in both directions.")
                .defineInRange("max_speed_rpm", 256, 1, 256);
        BASE_CAPACITY = builder.comment("Stress capacity in su that the Thaummotor provides at its speed, before the multiplier of its power source.")
                .defineInRange("base_stress_capacity", 1024.0D, 1.0D, 10_000_000.0D);
        builder.push("source_multipliers");
        for (NodeSourceKind kind : NodeSourceKind.values()) {
            MULTIPLIERS.put(kind, builder.comment("Multiplier of the stress capacity while the power source is: " + kind.id() + ".")
                    .defineInRange(kind.id(), defaultMultiplier(kind), 0.0D, 1_000.0D));
        }
        builder.pop();
        builder.pop();
        SPEC = builder.build();
    }

    private ThaummotorConfig() {
    }

    private static double defaultMultiplier(NodeSourceKind kind) {
        return switch (kind) {
            case JAR_NODE -> 1.0D;
            case FLUX_JAR -> 1.5D;
            case RIFT_JAR -> 2.5D;
            case RIFT -> 3.0D;
            case WORLD_NODE -> 2.0D;
        };
    }

    public static int checkInterval() {
        return SPEC.isLoaded() ? CHECK_INTERVAL.get() : CHECK_INTERVAL.getDefault();
    }

    public static int drainUnits() {
        return SPEC.isLoaded() ? DRAIN_UNITS.get() : DRAIN_UNITS.getDefault();
    }

    public static double radius() {
        return SPEC.isLoaded() ? RADIUS.get() : RADIUS.getDefault();
    }

    public static int maxSpeed() {
        return SPEC.isLoaded() ? MAX_SPEED.get() : MAX_SPEED.getDefault();
    }

    public static int speed() {
        return SPEC.isLoaded() ? SPEED.get() : SPEED.getDefault();
    }

    public static double baseCapacity() {
        return SPEC.isLoaded() ? BASE_CAPACITY.get() : BASE_CAPACITY.getDefault();
    }

    public static double multiplier(NodeSourceKind kind) {
        ModConfigSpec.DoubleValue value = MULTIPLIERS.get(kind);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static float capacity(NodeSourceKind kind) {
        return (float) (baseCapacity() * multiplier(kind));
    }
}
