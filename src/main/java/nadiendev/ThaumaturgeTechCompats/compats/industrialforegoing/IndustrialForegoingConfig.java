package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class IndustrialForegoingConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-industrial-foregoing.toml";
    public static final int UNITS_PER_VIS = 1000;
    public static final ModConfigSpec SPEC;

    private static final Map<LaserMachine, ModConfigSpec.BooleanValue> ENABLED = new EnumMap<>(LaserMachine.class);
    private static final ModConfigSpec.DoubleValue OPERATION_VIS;
    private static final ModConfigSpec.DoubleValue BUFFER_VIS;
    private static final ModConfigSpec.IntValue INTERVAL;
    private static final ModConfigSpec.BooleanValue USE_AURA;
    private static final ModConfigSpec.BooleanValue USE_NODES;
    private static final ModConfigSpec.DoubleValue NODE_RADIUS;
    private static final ModConfigSpec.IntValue NODE_UNITS;
    private static final ModConfigSpec.BooleanValue REFILL_FROM_AURA_WHEN_IDLE;
    private static final ModConfigSpec.IntValue ORE_MAX_PROGRESS;
    private static final ModConfigSpec.IntValue ORE_CATALYST_MODIFIER;
    private static final ModConfigSpec.IntValue FLUID_MAX_PROGRESS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push(LaserMachine.DRILL.blockName());
        ENABLED.put(LaserMachine.DRILL, builder.comment("Turns the Magical Laser Drill on or off.")
                .define("enabled", true));
        OPERATION_VIS = builder.comment("Vis the drill spends on every operation. The Laser Drill of Industrial Foregoing spends 1000 FE per operation and one vis is worth 1000 units here.")
                .defineInRange("operation_vis", 1.0D, 0.0D, 1000.0D);
        BUFFER_VIS = builder.comment("Vis the drill can hold inside. It is the purple bar of its screen.")
                .defineInRange("buffer_vis", 10.0D, 0.001D, 100_000.0D);
        INTERVAL = builder.comment("Ticks the drill takes to do one operation. Industrial Foregoing's Laser Drill uses 50.")
                .defineInRange("operation_interval_ticks", 50, 1, 12000);
        USE_AURA = builder.comment("The drill fills its buffer with the vis of the aura of its own chunk.")
                .define("use_aura", true);
        USE_NODES = builder.comment("When the aura cannot pay an operation, the drill takes power from a node, a jarred node, a Flux Jar, a Rift Jar or a flux rift within reach. A hungry node is never a valid source.")
                .define("use_nodes", true);
        NODE_RADIUS = builder.comment("Distance in blocks within which the drill looks for a node or any other power source.")
                .defineInRange("node_radius", 8.0D, 1.0D, 64.0D);
        NODE_UNITS = builder.comment("Units of power taken from a node source to pay one whole operation.")
                .defineInRange("node_units_per_operation", 1, 1, 1000);
        REFILL_FROM_AURA_WHEN_IDLE = builder.comment("The drill keeps its buffer full from the aura even while it has no laser base in its area.")
                .define("refill_when_idle", false);
        builder.pop();

        builder.push(LaserMachine.ORE_BASE.blockName());
        ENABLED.put(LaserMachine.ORE_BASE, builder.comment("Turns the Magical Ore Laser Base on or off.")
                .define("enabled", true));
        ORE_MAX_PROGRESS = builder.comment("Steps of work the base needs to produce one item. Industrial Foregoing's Ore Laser Base uses 100.")
                .defineInRange("max_progress", 100, 1, 100_000);
        ORE_CATALYST_MODIFIER = builder.comment("Weight added to an ore for every lens of its color placed in the base. Industrial Foregoing's Ore Laser Base uses 8.")
                .defineInRange("lens_weight_bonus", 8, 0, 10_000);
        builder.pop();

        builder.push(LaserMachine.FLUID_BASE.blockName());
        ENABLED.put(LaserMachine.FLUID_BASE, builder.comment("Turns the Magical Fluid Laser Base on or off.")
                .define("enabled", true));
        FLUID_MAX_PROGRESS = builder.comment("Steps of work the base needs to produce one batch of fluid. Industrial Foregoing's Fluid Laser Base uses 20.")
                .defineInRange("max_progress", 20, 1, 100_000);
        builder.pop();

        SPEC = builder.build();
    }

    private IndustrialForegoingConfig() {
    }

    public static boolean enabled(LaserMachine machine) {
        return CompatToggles.enabled(CompatToggles.INDUSTRIAL_FOREGOING)
                && CompatToggles.enabled(CompatToggles.MAGIC_LASER_DRILL)
                && (!SPEC.isLoaded() || ENABLED.get(machine).get());
    }

    public static float operationVis() {
        return SPEC.isLoaded() ? OPERATION_VIS.get().floatValue() : OPERATION_VIS.getDefault().floatValue();
    }

    public static int operationUnits() {
        return Math.round(operationVis() * UNITS_PER_VIS);
    }

    public static int bufferUnits() {
        double vis = SPEC.isLoaded() ? BUFFER_VIS.get() : BUFFER_VIS.getDefault();
        int units = (int) Math.max(1.0D, Math.min(Integer.MAX_VALUE, Math.round(vis * UNITS_PER_VIS)));
        return Math.max(units, operationUnits());
    }

    public static int interval() {
        return SPEC.isLoaded() ? INTERVAL.get() : INTERVAL.getDefault();
    }

    public static boolean useAura() {
        return !SPEC.isLoaded() || USE_AURA.get();
    }

    public static boolean useNodes() {
        return !SPEC.isLoaded() || USE_NODES.get();
    }

    public static double nodeRadius() {
        return SPEC.isLoaded() ? NODE_RADIUS.get() : NODE_RADIUS.getDefault();
    }

    public static int nodeUnits() {
        return SPEC.isLoaded() ? NODE_UNITS.get() : NODE_UNITS.getDefault();
    }

    public static boolean refillWhenIdle() {
        return SPEC.isLoaded() && REFILL_FROM_AURA_WHEN_IDLE.get();
    }

    public static int oreMaxProgress() {
        return SPEC.isLoaded() ? ORE_MAX_PROGRESS.get() : ORE_MAX_PROGRESS.getDefault();
    }

    public static int oreLensBonus() {
        return SPEC.isLoaded() ? ORE_CATALYST_MODIFIER.get() : ORE_CATALYST_MODIFIER.getDefault();
    }

    public static int fluidMaxProgress() {
        return SPEC.isLoaded() ? FLUID_MAX_PROGRESS.get() : FLUID_MAX_PROGRESS.getDefault();
    }
}
