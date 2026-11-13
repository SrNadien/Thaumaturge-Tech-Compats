package nadiendev.ThaumaturgeTechCompats.flux.gear;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class GearConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-flux-gear.toml";
    public static final ModConfigSpec SPEC;

    private static final long DEFAULT_SIPHON_CAPACITY = 2_000_000L;
    private static final long DEFAULT_SIPHON_COST = 2_000L;
    private static final int DEFAULT_SIPHON_INTERVAL = 5;
    private static final double DEFAULT_SIPHON_RADIUS = 8.0D;
    private static final double DEFAULT_SIPHON_SPEED = 0.5D;
    private static final double DEFAULT_SIPHON_FLUX_PER_SECOND = 0.5D;
    private static final long DEFAULT_SIPHON_FLUX_RATIO = 2_000L;
    private static final long DEFAULT_CANNON_CAPACITY = 5_000_000L;
    private static final long DEFAULT_CANNON_COST = 1_000_000L;
    private static final double DEFAULT_CANNON_DAMAGE = 30.0D;
    private static final double DEFAULT_CANNON_RANGE = 64.0D;
    private static final int DEFAULT_CANNON_COOLDOWN = 30;
    private static final int DEFAULT_CANNON_CHARGE = 15;
    private static final double DEFAULT_CANNON_SPLASH_RADIUS = 3.0D;
    private static final double DEFAULT_CANNON_SPLASH_FRACTION = 0.5D;
    private static final double DEFAULT_CANNON_KNOCKBACK = 1.0D;

    private static final ModConfigSpec.LongValue SIPHON_CAPACITY;
    private static final ModConfigSpec.LongValue SIPHON_COST;
    private static final ModConfigSpec.IntValue SIPHON_INTERVAL;
    private static final ModConfigSpec.DoubleValue SIPHON_RADIUS;
    private static final ModConfigSpec.DoubleValue SIPHON_SPEED;
    private static final ModConfigSpec.BooleanValue SIPHON_ITEMS;
    private static final ModConfigSpec.BooleanValue SIPHON_EXPERIENCE;
    private static final ModConfigSpec.BooleanValue SIPHON_RESIDUAL;
    private static final ModConfigSpec.DoubleValue SIPHON_FLUX_PER_SECOND;
    private static final ModConfigSpec.LongValue SIPHON_FLUX_RATIO;
    private static final ModConfigSpec.LongValue CANNON_CAPACITY;
    private static final ModConfigSpec.LongValue CANNON_COST;
    private static final ModConfigSpec.DoubleValue CANNON_DAMAGE;
    private static final ModConfigSpec.DoubleValue CANNON_RANGE;
    private static final ModConfigSpec.IntValue CANNON_COOLDOWN;
    private static final ModConfigSpec.IntValue CANNON_CHARGE;
    private static final ModConfigSpec.DoubleValue CANNON_SPLASH_RADIUS;
    private static final ModConfigSpec.DoubleValue CANNON_SPLASH_FRACTION;
    private static final ModConfigSpec.DoubleValue CANNON_KNOCKBACK;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("portable_void_siphon");
        SIPHON_CAPACITY = builder.comment("Dimensional Flux a Portable Void Siphon can hold.")
                .defineInRange("capacity", DEFAULT_SIPHON_CAPACITY, 1L, Long.MAX_VALUE / 4);
        SIPHON_COST = builder.comment("Dimensional Flux the siphon uses every second while it is held down.")
                .defineInRange("cost_per_second", DEFAULT_SIPHON_COST, 1L, Long.MAX_VALUE / 4);
        SIPHON_INTERVAL = builder.comment("Ticks between two payments of the cost and two draws of residual flux.")
                .defineInRange("interval", DEFAULT_SIPHON_INTERVAL, 1, 20);
        SIPHON_RADIUS = builder.comment("Radius in blocks in which the siphon pulls items and experience towards the player.")
                .defineInRange("radius", DEFAULT_SIPHON_RADIUS, 1.0D, 64.0D);
        SIPHON_SPEED = builder.comment("Blocks per tick at which pulled items and experience travel.")
                .defineInRange("pull_speed", DEFAULT_SIPHON_SPEED, 0.05D, 4.0D);
        SIPHON_ITEMS = builder.comment("The siphon pulls the items lying on the ground.")
                .define("absorb_items", true);
        SIPHON_EXPERIENCE = builder.comment("The siphon pulls experience orbs.")
                .define("absorb_experience", true);
        SIPHON_RESIDUAL = builder.comment("While held down, the siphon draws the flux of the aura of the chunk and turns it into its own Dimensional Flux, which also cleans the aura a little.")
                .define("residual_enabled", true);
        SIPHON_FLUX_PER_SECOND = builder.comment("Most aura flux the siphon draws every second.")
                .defineInRange("residual_flux_per_second", DEFAULT_SIPHON_FLUX_PER_SECOND, 0.0D, 1_000.0D);
        SIPHON_FLUX_RATIO = builder.comment("Dimensional Flux the siphon gets for every unit of aura flux it draws.")
                .defineInRange("residual_df_per_flux", DEFAULT_SIPHON_FLUX_RATIO, 1L, 1_000_000_000L);
        builder.pop();
        builder.push("discharge_cannon");
        CANNON_CAPACITY = builder.comment("Dimensional Flux a Discharge Cannon can hold.")
                .defineInRange("capacity", DEFAULT_CANNON_CAPACITY, 1L, Long.MAX_VALUE / 4);
        CANNON_COST = builder.comment("Dimensional Flux every shot costs.")
                .defineInRange("cost_per_shot", DEFAULT_CANNON_COST, 1L, Long.MAX_VALUE / 4);
        CANNON_DAMAGE = builder.comment("Damage of a shot to the target it hits.")
                .defineInRange("damage", DEFAULT_CANNON_DAMAGE, 0.0D, 10_000.0D);
        CANNON_RANGE = builder.comment("Blocks a shot travels at most.")
                .defineInRange("range", DEFAULT_CANNON_RANGE, 1.0D, 256.0D);
        CANNON_COOLDOWN = builder.comment("Ticks before the cannon can be used again after a shot.")
                .defineInRange("cooldown", DEFAULT_CANNON_COOLDOWN, 0, 72_000);
        CANNON_CHARGE = builder.comment("Ticks the use button must be held before releasing it fires a shot.")
                .defineInRange("charge_ticks", DEFAULT_CANNON_CHARGE, 0, 200);
        CANNON_SPLASH_RADIUS = builder.comment("Radius in blocks around the impact in which other creatures are also hurt. 0 turns the area damage off.")
                .defineInRange("splash_radius", DEFAULT_CANNON_SPLASH_RADIUS, 0.0D, 32.0D);
        CANNON_SPLASH_FRACTION = builder.comment("Fraction of the damage that creatures around the impact take.")
                .defineInRange("splash_fraction", DEFAULT_CANNON_SPLASH_FRACTION, 0.0D, 1.0D);
        CANNON_KNOCKBACK = builder.comment("Knockback of the creature that is hit.")
                .defineInRange("knockback", DEFAULT_CANNON_KNOCKBACK, 0.0D, 10.0D);
        builder.pop();
        SPEC = builder.build();
    }

    private GearConfig() {
    }

    private static boolean tierEnabled(FluxTier tier) {
        for (FluxTier step = tier; step != null; step = step.previous()) {
            if (!CompatToggles.enabled(step.toggle())) {
                return false;
            }
        }
        return true;
    }

    public static boolean siphonEnabled() {
        return tierEnabled(GearIntegration.SIPHON_TIER) && CompatToggles.enabled(GearIntegration.SIPHON_TOGGLE);
    }

    public static boolean cannonEnabled() {
        return tierEnabled(GearIntegration.CANNON_TIER) && CompatToggles.enabled(GearIntegration.CANNON_TOGGLE);
    }

    public static long siphonCapacity() {
        return SPEC.isLoaded() ? SIPHON_CAPACITY.get() : DEFAULT_SIPHON_CAPACITY;
    }

    public static long siphonCostPerSecond() {
        return SPEC.isLoaded() ? SIPHON_COST.get() : DEFAULT_SIPHON_COST;
    }

    public static int siphonInterval() {
        return SPEC.isLoaded() ? SIPHON_INTERVAL.get() : DEFAULT_SIPHON_INTERVAL;
    }

    public static double siphonRadius() {
        return SPEC.isLoaded() ? SIPHON_RADIUS.get() : DEFAULT_SIPHON_RADIUS;
    }

    public static double siphonSpeed() {
        return SPEC.isLoaded() ? SIPHON_SPEED.get() : DEFAULT_SIPHON_SPEED;
    }

    public static boolean siphonItems() {
        return !SPEC.isLoaded() || SIPHON_ITEMS.get();
    }

    public static boolean siphonExperience() {
        return !SPEC.isLoaded() || SIPHON_EXPERIENCE.get();
    }

    public static boolean siphonResidual() {
        return !SPEC.isLoaded() || SIPHON_RESIDUAL.get();
    }

    public static double siphonFluxPerSecond() {
        return SPEC.isLoaded() ? SIPHON_FLUX_PER_SECOND.get() : DEFAULT_SIPHON_FLUX_PER_SECOND;
    }

    public static long siphonFluxRatio() {
        return SPEC.isLoaded() ? SIPHON_FLUX_RATIO.get() : DEFAULT_SIPHON_FLUX_RATIO;
    }

    public static long cannonCapacity() {
        return SPEC.isLoaded() ? CANNON_CAPACITY.get() : DEFAULT_CANNON_CAPACITY;
    }

    public static long cannonCost() {
        return SPEC.isLoaded() ? CANNON_COST.get() : DEFAULT_CANNON_COST;
    }

    public static double cannonDamage() {
        return SPEC.isLoaded() ? CANNON_DAMAGE.get() : DEFAULT_CANNON_DAMAGE;
    }

    public static double cannonRange() {
        return SPEC.isLoaded() ? CANNON_RANGE.get() : DEFAULT_CANNON_RANGE;
    }

    public static int cannonCooldown() {
        return SPEC.isLoaded() ? CANNON_COOLDOWN.get() : DEFAULT_CANNON_COOLDOWN;
    }

    public static int cannonChargeTicks() {
        return SPEC.isLoaded() ? CANNON_CHARGE.get() : DEFAULT_CANNON_CHARGE;
    }

    public static double cannonSplashRadius() {
        return SPEC.isLoaded() ? CANNON_SPLASH_RADIUS.get() : DEFAULT_CANNON_SPLASH_RADIUS;
    }

    public static double cannonSplashFraction() {
        return SPEC.isLoaded() ? CANNON_SPLASH_FRACTION.get() : DEFAULT_CANNON_SPLASH_FRACTION;
    }

    public static double cannonKnockback() {
        return SPEC.isLoaded() ? CANNON_KNOCKBACK.get() : DEFAULT_CANNON_KNOCKBACK;
    }
}
