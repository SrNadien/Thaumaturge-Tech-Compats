package nadiendev.ThaumaturgeTechCompats.device;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class DeviceConfig {
    public static final ModConfigSpec SPEC;

    private static final int[] FEEDING_FACTORS = {50, 200, 1000};
    private static final int[] CHARGE_SPEEDS = {2, 4, 10};
    private static final int[] DECAY_DIVISORS = {2, 4, 10};
    private static final int[] FURNACE_SPEEDS = {2, 4, 10};
    private static final int[] FURNACE_SAVINGS = {25, 50, 90};

    private static final ModConfigSpec.BooleanValue STABILIZER_ENABLED;
    private static final ModConfigSpec.BooleanValue TRANSDUCER_ENABLED;
    private static final ModConfigSpec.BooleanValue FURNACE_ENABLED;

    private static final Map<DeviceTier, ModConfigSpec.IntValue> FEEDING = new EnumMap<>(DeviceTier.class);
    private static final Map<DeviceTier, ModConfigSpec.IntValue> CHARGE = new EnumMap<>(DeviceTier.class);
    private static final Map<DeviceTier, ModConfigSpec.IntValue> DECAY = new EnumMap<>(DeviceTier.class);
    private static final Map<DeviceTier, ModConfigSpec.IntValue> SPEED = new EnumMap<>(DeviceTier.class);
    private static final Map<DeviceTier, ModConfigSpec.IntValue> SAVINGS = new EnumMap<>(DeviceTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("node_stabilizers");
        STABILIZER_ENABLED = builder.comment("Gives the Avaritia node stabilizers their own lock levels. When false they behave exactly like the Advanced Node Stabilizer.")
                .define("enabled", true);
        for (DeviceTier tier : DeviceTier.values()) {
            FEEDING.put(tier, builder.comment("How many times slower a node above the " + tier.id() + " stabilizer feeds on the aura. The basic stabilizer uses 2 and the advanced one 20.")
                    .defineInRange(tier.id() + "_feeding_interval_factor", FEEDING_FACTORS[tier.ordinal()], 1, 1_000_000));
        }
        builder.pop();

        builder.push("node_transducers");
        TRANSDUCER_ENABLED = builder.comment("Gives the Avaritia node transducers their faster charging. When false they behave exactly like the Node Transducer.")
                .define("enabled", true);
        for (DeviceTier tier : DeviceTier.values()) {
            CHARGE.put(tier, builder.comment("Charge points the " + tier.id() + " transducer adds each tick instead of 1. A node is energized at 1000 points.")
                    .defineInRange(tier.id() + "_charge_per_tick", CHARGE_SPEEDS[tier.ordinal()], 1, 1000));
            DECAY.put(tier, builder.comment("The " + tier.id() + " transducer only loses a charge point once every this many ticks while it has no redstone signal.")
                    .defineInRange(tier.id() + "_decay_interval", DECAY_DIVISORS[tier.ordinal()], 1, 1000));
        }
        builder.pop();

        builder.push("alchemical_furnaces");
        FURNACE_ENABLED = builder.comment("Gives the Avaritia alchemical furnaces their speed and savings. When false they behave exactly like the Advanced Alchemical Furnace.")
                .define("enabled", true);
        for (DeviceTier tier : DeviceTier.values()) {
            SPEED.put(tier, builder.comment("The pause between two items of the " + tier.id() + " furnace is divided by this number.")
                    .defineInRange(tier.id() + "_speed", FURNACE_SPEEDS[tier.ordinal()], 1, 1000));
            SAVINGS.put(tier, builder.comment("Percentage of heat, perditio and aqua the " + tier.id() + " furnace gives back after smelting an item.")
                    .defineInRange(tier.id() + "_savings_percent", FURNACE_SAVINGS[tier.ordinal()], 0, 100));
        }
        builder.pop();

        SPEC = builder.build();
    }

    private DeviceConfig() {
    }

    public static boolean stabilizersEnabled() {
        return !SPEC.isLoaded() || STABILIZER_ENABLED.get();
    }

    public static boolean transducersEnabled() {
        return !SPEC.isLoaded() || TRANSDUCER_ENABLED.get();
    }

    public static boolean furnacesEnabled() {
        return !SPEC.isLoaded() || FURNACE_ENABLED.get();
    }

    public static int feedingFactor(DeviceTier tier) {
        return SPEC.isLoaded() ? FEEDING.get(tier).get() : FEEDING_FACTORS[tier.ordinal()];
    }

    public static int chargePerTick(DeviceTier tier) {
        return SPEC.isLoaded() ? CHARGE.get(tier).get() : CHARGE_SPEEDS[tier.ordinal()];
    }

    public static int decayInterval(DeviceTier tier) {
        return SPEC.isLoaded() ? DECAY.get(tier).get() : DECAY_DIVISORS[tier.ordinal()];
    }

    public static int furnaceSpeed(DeviceTier tier) {
        return SPEC.isLoaded() ? SPEED.get(tier).get() : FURNACE_SPEEDS[tier.ordinal()];
    }

    public static int furnaceSavings(DeviceTier tier) {
        return SPEC.isLoaded() ? SAVINGS.get(tier).get() : FURNACE_SAVINGS[tier.ordinal()];
    }
}
