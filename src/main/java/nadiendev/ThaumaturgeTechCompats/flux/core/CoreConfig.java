package nadiendev.ThaumaturgeTechCompats.flux.core;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerTier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class CoreConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-flux-core.toml";
    public static final ModConfigSpec SPEC;

    private static final long MAX = Long.MAX_VALUE / 4;

    private static final Map<FluxTier, ModConfigSpec.LongValue> CELL_CAPACITY = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.IntValue> STABILIZER_POINTS = new EnumMap<>(FluxTier.class);
    private static final Map<StabilizerTier, ModConfigSpec.IntValue> STABILIZER_TIER_WEIGHT = new EnumMap<>(StabilizerTier.class);
    private static final ModConfigSpec.IntValue SCAN_INTERVAL;
    private static final ModConfigSpec.IntValue CELL_RADIUS;
    private static final ModConfigSpec.IntValue STABILIZER_RADIUS;
    private static final ModConfigSpec.IntValue MAX_CELLS;
    private static final ModConfigSpec.BooleanValue REQUIRE_RING;
    private static final ModConfigSpec.BooleanValue REQUIRE_CAPS;
    private static final ModConfigSpec.BooleanValue ACCEPT_HIGHER_TIER;
    private static final ModConfigSpec.BooleanValue SPILL_EXCESS;
    private static final ModConfigSpec.LongValue DF_PER_FLUX;
    private static final ModConfigSpec.IntValue WEIGHT_BASIC;
    private static final ModConfigSpec.IntValue WEIGHT_ADVANCED;
    private static final ModConfigSpec.BooleanValue OVERLOAD_ENABLED;
    private static final ModConfigSpec.DoubleValue OVERLOAD_THRESHOLD;
    private static final ModConfigSpec.IntValue OVERLOAD_INTERVAL;
    private static final ModConfigSpec.DoubleValue OVERLOAD_FLUX;
    private static final ModConfigSpec.DoubleValue OVERLOAD_LEAK_PERCENT;
    private static final ModConfigSpec.BooleanValue OVERLOAD_PARTICLES;
    private static final ModConfigSpec.BooleanValue OVERLOAD_WARN;
    private static final ModConfigSpec.BooleanValue DAMAGE_ENABLED;
    private static final ModConfigSpec.DoubleValue DAMAGE_AMOUNT;
    private static final ModConfigSpec.IntValue DAMAGE_RADIUS;
    private static final ModConfigSpec.BooleanValue EXPLOSION_ENABLED;
    private static final ModConfigSpec.DoubleValue EXPLOSION_THRESHOLD;
    private static final ModConfigSpec.IntValue EXPLOSION_PULSES;
    private static final ModConfigSpec.DoubleValue EXPLOSION_POWER;
    private static final ModConfigSpec.BooleanValue EXPLOSION_BREAKS_BLOCKS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("structure");
        SCAN_INTERVAL = builder.comment("Ticks between two scans of the structure, the pedestals and the stabilizers around a Storage Core.")
                .defineInRange("scan_interval", 40, 5, 1200);
        REQUIRE_RING = builder.comment("The core needs a ring of eight metal blocks of its tier around it, on its own level.")
                .define("require_ring", true);
        REQUIRE_CAPS = builder.comment("The core needs a metal block of its tier right above it and another right below it.")
                .define("require_caps", true);
        ACCEPT_HIGHER_TIER = builder.comment("Metal blocks of a higher tier also count for the structure of a core.")
                .define("accept_higher_tier_blocks", true);
        builder.pop();

        builder.push("cells");
        CELL_RADIUS = builder.comment("Blocks, as the side of a cube around the core, in which a pedestal holding a Capacity Cell counts.")
                .defineInRange("radius", 6, 1, 16);
        MAX_CELLS = builder.comment("Most Capacity Cells a single core takes into account.")
                .defineInRange("max_cells", 16, 1, 256);
        for (FluxTier tier : FluxTier.values()) {
            builder.push(tier.id());
            CELL_CAPACITY.put(tier, builder.comment("Dimensional Flux a Capacity Cell of this tier adds to the capacity of the core.")
                    .defineInRange("capacity", defaultCellCapacity(tier), 1L, MAX));
            builder.pop();
        }
        SPILL_EXCESS = builder.comment("When the capacity of a core drops under what it holds, the excess is released as flux into the aura. When false the excess is lost.")
                .define("spill_excess_as_flux", true);
        DF_PER_FLUX = builder.comment("Dimensional Flux that make one point of flux in the aura when a core releases them.")
                .defineInRange("df_per_flux", 100_000L, 1L, MAX);
        builder.pop();

        builder.push("stabilizers");
        STABILIZER_RADIUS = builder.comment("Blocks, as the side of a cube around the core, in which a node stabilizer counts.")
                .defineInRange("radius", 8, 1, 16);
        for (FluxTier tier : FluxTier.values()) {
            builder.push(tier.id());
            STABILIZER_POINTS.put(tier, builder.comment("Stabilizer points a core of this tier needs when it is completely full. It needs a share of them as full as it is.")
                    .defineInRange("points_for_full", defaultStabilizerPoints(tier), 0, 10_000));
            builder.pop();
        }
        builder.push("weights");
        WEIGHT_BASIC = builder.comment("Stabilizer points of a Thaumaturge node stabilizer.")
                .defineInRange("node_stabilizer", 1, 0, 1000);
        WEIGHT_ADVANCED = builder.comment("Stabilizer points of an advanced Thaumaturge node stabilizer.")
                .defineInRange("advanced_node_stabilizer", 2, 0, 1000);
        for (StabilizerTier tier : StabilizerTier.values()) {
            STABILIZER_TIER_WEIGHT.put(tier, builder.comment("Stabilizer points of a " + tier.id() + " node stabilizer.")
                    .defineInRange(tier.id() + "_node_stabilizer", defaultWeight(tier), 0, 1000));
        }
        builder.pop();
        builder.pop();

        builder.push("overload");
        OVERLOAD_ENABLED = builder.comment("A core that is too full for the stabilizers around it overloads. When false it never does.")
                .define("enabled", true);
        OVERLOAD_THRESHOLD = builder.comment("Share of its capacity, from 0 to 1, above which a core without enough stabilizers overloads.")
                .defineInRange("threshold", 0.5D, 0.0D, 1.0D);
        OVERLOAD_INTERVAL = builder.comment("Ticks between two pulses of an overloaded core.")
                .defineInRange("interval", 40, 1, 12_000);
        OVERLOAD_FLUX = builder.comment("Flux released into the aura by every pulse of an overload, at the worst lack of stabilizers. It shrinks as the lack does.")
                .defineInRange("flux_per_pulse", 2.0D, 0.0D, 10_000.0D);
        OVERLOAD_LEAK_PERCENT = builder.comment("Percent of the capacity that leaks out of the core on every pulse, at the worst lack of stabilizers. It shrinks as the lack does.")
                .defineInRange("leak_percent", 0.25D, 0.0D, 100.0D);
        OVERLOAD_PARTICLES = builder.comment("An overloaded core throws sparks on every pulse.")
                .define("particles", true);
        OVERLOAD_WARN = builder.comment("Players near an overloaded core get a warning above the hotbar on every pulse.")
                .define("warn_players", true);
        DAMAGE_ENABLED = builder.comment("Every pulse of an overload hurts the living things around the core.")
                .define("damage_enabled", true);
        DAMAGE_AMOUNT = builder.comment("Magic damage dealt by a pulse at the worst lack of stabilizers. It shrinks as the lack does.")
                .defineInRange("damage", 1.0D, 0.0D, 1000.0D);
        DAMAGE_RADIUS = builder.comment("Blocks around the core in which a pulse hurts and warns.")
                .defineInRange("damage_radius", 5, 1, 64);
        EXPLOSION_ENABLED = builder.comment("A core that keeps overloading while almost full explodes. Off by default.")
                .define("explosion_enabled", false);
        EXPLOSION_THRESHOLD = builder.comment("Share of its capacity, from 0 to 1, that a core must hold for the explosion to be possible.")
                .defineInRange("explosion_threshold", 0.95D, 0.0D, 1.0D);
        EXPLOSION_PULSES = builder.comment("Pulses in a row that a core must overload before it explodes.")
                .defineInRange("explosion_pulses", 10, 1, 10_000);
        EXPLOSION_POWER = builder.comment("Power of the explosion.")
                .defineInRange("explosion_power", 3.0D, 0.1D, 64.0D);
        EXPLOSION_BREAKS_BLOCKS = builder.comment("The explosion breaks blocks. When false it only hurts.")
                .define("explosion_breaks_blocks", true);
        builder.pop();

        SPEC = builder.build();
    }

    private CoreConfig() {
    }

    private static long defaultCellCapacity(FluxTier tier) {
        return Math.max(1L, tier.defaultCoreCapacity() / 2L);
    }

    private static int defaultStabilizerPoints(FluxTier tier) {
        return 4 + 4 * tier.ordinal();
    }

    private static int defaultWeight(StabilizerTier tier) {
        return switch (tier) {
            case MATRIX -> 3;
            case NEUTRONIUM -> 4;
            case INFINITY -> 6;
        };
    }

    private static boolean bool(ModConfigSpec.BooleanValue value, boolean fallback) {
        return SPEC.isLoaded() ? value.get() : fallback;
    }

    private static int integer(ModConfigSpec.IntValue value, int fallback) {
        return SPEC.isLoaded() ? value.get() : fallback;
    }

    private static long number(ModConfigSpec.LongValue value, long fallback) {
        return SPEC.isLoaded() ? value.get() : fallback;
    }

    private static double decimal(ModConfigSpec.DoubleValue value, double fallback) {
        return SPEC.isLoaded() ? value.get() : fallback;
    }

    public static int scanInterval() {
        return integer(SCAN_INTERVAL, 40);
    }

    public static boolean requireRing() {
        return bool(REQUIRE_RING, true);
    }

    public static boolean requireCaps() {
        return bool(REQUIRE_CAPS, true);
    }

    public static boolean acceptHigherTier() {
        return bool(ACCEPT_HIGHER_TIER, true);
    }

    public static int cellRadius() {
        return integer(CELL_RADIUS, 6);
    }

    public static int maxCells() {
        return integer(MAX_CELLS, 16);
    }

    public static long cellCapacity(FluxTier tier) {
        return number(CELL_CAPACITY.get(tier), defaultCellCapacity(tier));
    }

    public static boolean spillExcess() {
        return bool(SPILL_EXCESS, true);
    }

    public static long dfPerFlux() {
        return number(DF_PER_FLUX, 100_000L);
    }

    public static int stabilizerRadius() {
        return integer(STABILIZER_RADIUS, 8);
    }

    public static int stabilizerPoints(FluxTier tier) {
        return integer(STABILIZER_POINTS.get(tier), defaultStabilizerPoints(tier));
    }

    public static int weightBasic() {
        return integer(WEIGHT_BASIC, 1);
    }

    public static int weightAdvanced() {
        return integer(WEIGHT_ADVANCED, 2);
    }

    public static int weight(StabilizerTier tier) {
        return integer(STABILIZER_TIER_WEIGHT.get(tier), defaultWeight(tier));
    }

    public static boolean overloadEnabled() {
        return bool(OVERLOAD_ENABLED, true);
    }

    public static double overloadThreshold() {
        return decimal(OVERLOAD_THRESHOLD, 0.5D);
    }

    public static int overloadInterval() {
        return integer(OVERLOAD_INTERVAL, 40);
    }

    public static double overloadFlux() {
        return decimal(OVERLOAD_FLUX, 2.0D);
    }

    public static double leakPercent() {
        return decimal(OVERLOAD_LEAK_PERCENT, 0.25D);
    }

    public static boolean overloadParticles() {
        return bool(OVERLOAD_PARTICLES, true);
    }

    public static boolean overloadWarn() {
        return bool(OVERLOAD_WARN, true);
    }

    public static boolean damageEnabled() {
        return bool(DAMAGE_ENABLED, true);
    }

    public static double damageAmount() {
        return decimal(DAMAGE_AMOUNT, 1.0D);
    }

    public static int damageRadius() {
        return integer(DAMAGE_RADIUS, 5);
    }

    public static boolean explosionEnabled() {
        return bool(EXPLOSION_ENABLED, false);
    }

    public static double explosionThreshold() {
        return decimal(EXPLOSION_THRESHOLD, 0.95D);
    }

    public static int explosionPulses() {
        return integer(EXPLOSION_PULSES, 10);
    }

    public static double explosionPower() {
        return decimal(EXPLOSION_POWER, 3.0D);
    }

    public static boolean explosionBreaksBlocks() {
        return bool(EXPLOSION_BREAKS_BLOCKS, true);
    }
}
