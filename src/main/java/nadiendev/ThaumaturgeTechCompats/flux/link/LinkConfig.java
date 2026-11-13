package nadiendev.ThaumaturgeTechCompats.flux.link;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class LinkConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-flux-link.toml";
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.IntValue TRANSFER_INTERVAL;
    private static final ModConfigSpec.IntValue BUFFER_TICKS;
    private static final ModConfigSpec.LongValue EXTRACTOR_CAPACITY;
    private static final ModConfigSpec.LongValue EXTRACTOR_PUSH_RATE;
    private static final ModConfigSpec.BooleanValue EXTRACTOR_PARTICLES;
    private static final ModConfigSpec.BooleanValue BEAM_ENABLED;
    private static final ModConfigSpec.BooleanValue BEAM_IDLE;
    private static final ModConfigSpec.IntValue BEAM_INTERVAL;
    private static final ModConfigSpec.DoubleValue BEAM_POINTS_PER_BLOCK;
    private static final ModConfigSpec.IntValue BEAM_MAX_POINTS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("transmission_link");
        TRANSFER_INTERVAL = builder.comment("Ticks between two transfers of a Transmission Link. Every transfer moves the rate of the tier multiplied by this number, so a higher value costs less performance and moves the same flux.")
                .defineInRange("transfer_interval", 1, 1, 20);
        BUFFER_TICKS = builder.comment("Ticks of transfer a Transmission Link can hold in its internal buffer. The buffer holds the rate of the tier multiplied by this number.")
                .defineInRange("buffer_ticks", 20, 1, 72_000);
        builder.pop();
        builder.push("rift_extractor");
        EXTRACTOR_CAPACITY = builder.comment("Dimensional Flux a Rift Extractor can hold. It stops extracting while there is no room for one more extraction.")
                .defineInRange("capacity", 50_000L, 1L, Long.MAX_VALUE / 4);
        EXTRACTOR_PUSH_RATE = builder.comment("Dimensional Flux a Rift Extractor pushes every tick to the storages and Transmission Links around it.")
                .defineInRange("push_rate_per_tick", 2_000L, 1L, Long.MAX_VALUE / 4);
        EXTRACTOR_PARTICLES = builder.comment("A Rift Extractor sends a few particles from the rift to itself on every extraction.")
                .define("particles", true);
        builder.pop();
        builder.push("beam");
        BEAM_ENABLED = builder.comment("Linked Transmission Links draw a beam of particles between them. The server sends it, so it also costs bandwidth: turn it off on busy servers.")
                .define("enabled", true);
        BEAM_IDLE = builder.comment("Draw the beam even while no flux is moving between the two links.")
                .define("idle", false);
        BEAM_INTERVAL = builder.comment("Ticks between two beams.")
                .defineInRange("interval", 10, 1, 200);
        BEAM_POINTS_PER_BLOCK = builder.comment("Particles of the beam for every block of distance.")
                .defineInRange("points_per_block", 0.5D, 0.05D, 8.0D);
        BEAM_MAX_POINTS = builder.comment("Most particles a single beam can have, whatever the distance.")
                .defineInRange("max_points", 24, 2, 256);
        builder.pop();
        SPEC = builder.build();
    }

    private LinkConfig() {
    }

    public static int transferInterval() {
        return SPEC.isLoaded() ? TRANSFER_INTERVAL.get() : 1;
    }

    public static int bufferTicks() {
        return SPEC.isLoaded() ? BUFFER_TICKS.get() : 20;
    }

    public static long extractorCapacity() {
        return SPEC.isLoaded() ? EXTRACTOR_CAPACITY.get() : 50_000L;
    }

    public static long extractorPushRate() {
        return SPEC.isLoaded() ? EXTRACTOR_PUSH_RATE.get() : 2_000L;
    }

    public static boolean extractorParticles() {
        return !SPEC.isLoaded() || EXTRACTOR_PARTICLES.get();
    }

    public static boolean beamEnabled() {
        return !SPEC.isLoaded() || BEAM_ENABLED.get();
    }

    public static boolean beamIdle() {
        return SPEC.isLoaded() && BEAM_IDLE.get();
    }

    public static int beamInterval() {
        return SPEC.isLoaded() ? BEAM_INTERVAL.get() : 10;
    }

    public static double beamPointsPerBlock() {
        return SPEC.isLoaded() ? BEAM_POINTS_PER_BLOCK.get() : 0.5D;
    }

    public static int beamMaxPoints() {
        return SPEC.isLoaded() ? BEAM_MAX_POINTS.get() : 24;
    }
}
