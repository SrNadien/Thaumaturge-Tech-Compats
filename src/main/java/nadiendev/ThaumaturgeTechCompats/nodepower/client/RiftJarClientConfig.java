package nadiendev.ThaumaturgeTechCompats.nodepower.client;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class RiftJarClientConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-riftjar-client.toml";
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue ENABLED;
    private static final ModConfigSpec.DoubleValue MAX_SIZE;
    private static final ModConfigSpec.DoubleValue EMPTY_SPARK_SIZE;
    private static final ModConfigSpec.DoubleValue GROWTH_CURVE;
    private static final ModConfigSpec.DoubleValue GROW_SPEED;
    private static final ModConfigSpec.DoubleValue THICKNESS;
    private static final ModConfigSpec.DoubleValue SWAY;
    private static final ModConfigSpec.DoubleValue THROB;
    private static final ModConfigSpec.DoubleValue SPIN_DEGREES_PER_SECOND;
    private static final ModConfigSpec.DoubleValue TILT_DEGREES;
    private static final ModConfigSpec.DoubleValue HEIGHT;
    private static final ModConfigSpec.DoubleValue BOB_AMPLITUDE;
    private static final ModConfigSpec.DoubleValue BOB_SPEED;
    private static final ModConfigSpec.DoubleValue GLOW_INTENSITY;
    private static final ModConfigSpec.DoubleValue ITEM_FILL;
    private static final ModConfigSpec.BooleanValue ITEM_USE_ENDER_SHADER;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("rift_jar");
        ENABLED = builder.comment("Draw the flux rift inside the Rift Jar, in the world and as an item.")
                .define("enabled", true);
        MAX_SIZE = builder.comment("Radius in blocks of the sphere that contains the rift when the jar is full. The glass interior has a radius of about 0.31.")
                .defineInRange("max_size", 0.22D, 0.02D, 0.3D);
        EMPTY_SPARK_SIZE = builder.comment("Fraction of max_size drawn as a spark while the jar holds no power. 0 draws nothing.")
                .defineInRange("empty_spark_size", 0.35D, 0.0D, 1.0D);
        GROWTH_CURVE = builder.comment("Exponent applied to the fill fraction before it becomes a size. Below 1 the rift looks bigger at low fill levels.")
                .defineInRange("growth_curve", 0.5D, 0.1D, 4.0D);
        GROW_SPEED = builder.comment("How fast the drawn size follows the fill level, per second. Higher is snappier.")
                .defineInRange("grow_speed", 4.0D, 0.1D, 60.0D);
        THICKNESS = builder.comment("Radius of the thickest part of the rift, relative to max_size.")
                .defineInRange("thickness", 0.1D, 0.01D, 0.5D);
        SWAY = builder.comment("Multiplier of the wobble of the rift.")
                .defineInRange("sway", 1.0D, 0.0D, 5.0D);
        THROB = builder.comment("Multiplier of the pulsing of the thickness of the rift.")
                .defineInRange("throb", 1.0D, 0.0D, 5.0D);
        SPIN_DEGREES_PER_SECOND = builder.comment("Degrees per second the rift turns around the vertical axis. Negative turns the other way.")
                .defineInRange("spin_degrees_per_second", 14.0D, -720.0D, 720.0D);
        TILT_DEGREES = builder.comment("Degrees the rift is tilted away from lying flat. 90 leaves its long axis vertical on average.")
                .defineInRange("tilt_degrees", 65.0D, -90.0D, 90.0D);
        HEIGHT = builder.comment("Height in blocks, from the base of the jar, of the centre of the rift.")
                .defineInRange("height", 0.38D, 0.0D, 0.75D);
        BOB_AMPLITUDE = builder.comment("Blocks the rift floats up and down. 0 keeps it still.")
                .defineInRange("bob_amplitude", 0.012D, 0.0D, 0.1D);
        BOB_SPEED = builder.comment("Radians per tick of the floating movement.")
                .defineInRange("bob_speed", 0.07D, 0.0D, 1.0D);
        GLOW_INTENSITY = builder.comment("Strength of the glow around the rift. 0 removes it.")
                .defineInRange("glow_intensity", 1.0D, 0.0D, 4.0D);
        ITEM_FILL = builder.comment("Fill fraction used to size the rift when the jar is shown as an item.")
                .defineInRange("item_fill", 0.7D, 0.0D, 1.0D);
        ITEM_USE_ENDER_SHADER = builder.comment("Draw the item outside the inventory with the same starfield shader as the entity. It can look wrong with some shader packs. The inventory always uses plain colours.")
                .define("item_use_ender_shader", false);
        builder.pop();
        SPEC = builder.build();
    }

    private RiftJarClientConfig() {
    }

    public static boolean enabled() {
        return SPEC.isLoaded() ? ENABLED.get() : ENABLED.getDefault();
    }

    public static float maxSize() {
        return number(MAX_SIZE);
    }

    public static float emptySparkSize() {
        return number(EMPTY_SPARK_SIZE);
    }

    public static float growthCurve() {
        return number(GROWTH_CURVE);
    }

    public static float growSpeed() {
        return number(GROW_SPEED);
    }

    public static float thickness() {
        return number(THICKNESS);
    }

    public static float sway() {
        return number(SWAY);
    }

    public static float throb() {
        return number(THROB);
    }

    public static float spinDegreesPerSecond() {
        return number(SPIN_DEGREES_PER_SECOND);
    }

    public static float tiltDegrees() {
        return number(TILT_DEGREES);
    }

    public static float height() {
        return number(HEIGHT);
    }

    public static float bobAmplitude() {
        return number(BOB_AMPLITUDE);
    }

    public static float bobSpeed() {
        return number(BOB_SPEED);
    }

    public static float glowIntensity() {
        return number(GLOW_INTENSITY);
    }

    public static float itemFill() {
        return number(ITEM_FILL);
    }

    public static boolean itemUseEnderShader() {
        return SPEC.isLoaded() ? ITEM_USE_ENDER_SHADER.get() : ITEM_USE_ENDER_SHADER.getDefault();
    }

    private static float number(ModConfigSpec.DoubleValue value) {
        double number = SPEC.isLoaded() ? value.get() : value.getDefault();
        return (float) number;
    }
}
