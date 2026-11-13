package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class PearlConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-planetary-pearl.toml";
    public static final ModConfigSpec SPEC;
    public static final int MAX_PLANETS = 12;

    private static final int[] PLANETS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 10, 11, 12, 12};
    private static final double[] DAMAGE = {1.0D, 1.5D, 2.0D, 3.0D, 3.5D, 5.0D, 6.0D, 7.0D, 9.0D, 11.0D, 14.0D, 18.0D, 25.0D, 40.0D};
    private static final int[] INTERVAL = {30, 28, 26, 24, 22, 20, 18, 16, 14, 12, 10, 8, 6, 4};
    private static final int[] DURATION = {100, 120, 140, 160, 180, 240, 240, 280, 320, 360, 400, 440, 600, 1200};
    private static final double[] RADIUS = {1.5D, 1.6D, 1.8D, 2.0D, 2.2D, 2.4D, 2.6D, 2.8D, 3.0D, 3.2D, 3.4D, 3.6D, 4.0D, 4.5D};
    private static final int[] COOLDOWN = {60, 56, 52, 48, 44, 40, 36, 32, 30, 28, 26, 24, 20, 10};
    private static final int[] DURABILITY = {3, 5, 8, 12, 16, 0, 32, 64, 0, 128, 256, 512, 0, 0};
    private static final double[] BOSS_CAP = {0.5D, 0.75D, 1.0D, 1.5D, 1.5D, 5.0D, 3.0D, 3.5D, 9.0D, 5.0D, 6.0D, 8.0D, 25.0D, 40.0D};

    private record TierValues(
            ModConfigSpec.IntValue planets,
            ModConfigSpec.DoubleValue damage,
            ModConfigSpec.IntValue interval,
            ModConfigSpec.IntValue duration,
            ModConfigSpec.DoubleValue radius,
            ModConfigSpec.IntValue cooldown,
            ModConfigSpec.DoubleValue bossCap,
            ModConfigSpec.IntValue durability) {
    }

    private static final Map<PlanetaryTier, TierValues> TIERS = new EnumMap<>(PlanetaryTier.class);

    private static final ModConfigSpec.BooleanValue ENABLED;
    private static final ModConfigSpec.BooleanValue INCLUDE_THROWER;
    private static final ModConfigSpec.DoubleValue SEARCH_RADIUS;
    private static final ModConfigSpec.BooleanValue HIT_PLAYERS;
    private static final ModConfigSpec.BooleanValue SPARE_PETS;
    private static final ModConfigSpec.BooleanValue RESET_INVULNERABILITY;
    private static final ModConfigSpec.DoubleValue KNOCKBACK_SCALE;
    private static final ModConfigSpec.DoubleValue POINT_HIT_RADIUS;
    private static final ModConfigSpec.IntValue MAX_ACTIVE;
    private static final ModConfigSpec.BooleanValue EXECUTE_BOSSES;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> EXTRA_BOSSES;
    private static final ModConfigSpec.IntValue TRAIL_PARTICLES;
    private static final ModConfigSpec.BooleanValue HIT_PARTICLES;
    private static final ModConfigSpec.BooleanValue SOUNDS;
    private static final ModConfigSpec.DoubleValue SOUND_VOLUME;
    private static final ModConfigSpec.DoubleValue ORBIT_SPEED;
    private static final ModConfigSpec.DoubleValue TINT_STRENGTH;
    private static final ModConfigSpec.DoubleValue PLANET_SCALE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("general");
        ENABLED = builder.comment("Turns the Planetary Endest Pearls off. They stay registered but do nothing when used.")
                .define("enabled", true);
        INCLUDE_THROWER = builder.comment("The planets may also pick the thrower as the creature to orbit and hurt.")
                .define("include_thrower", false);
        SEARCH_RADIUS = builder.comment("Radius in blocks around a block impact in which the closest creature is picked to be orbited.")
                .defineInRange("search_radius", 6.0D, 0.5D, 64.0D);
        HIT_PLAYERS = builder.comment("The planets hurt other players.")
                .define("hit_players", true);
        SPARE_PETS = builder.comment("The planets never pick the tamed animals of the thrower.")
                .define("spare_pets", true);
        RESET_INVULNERABILITY = builder.comment("Every planet hit ignores the short invulnerability a creature gets after being hurt, so many planets can hurt together.")
                .define("reset_invulnerability", true);
        KNOCKBACK_SCALE = builder.comment("Fraction of the normal knockback a planet hit applies.")
                .defineInRange("knockback_scale", 0.1D, 0.0D, 1.0D);
        POINT_HIT_RADIUS = builder.comment("Radius in blocks around a planet in which it hurts creatures when it orbits a point instead of a creature.")
                .defineInRange("point_hit_radius", 1.2D, 0.1D, 8.0D);
        MAX_ACTIVE = builder.comment("Most planets that can exist in a dimension at once. A throw is cut short when the limit is reached.")
                .defineInRange("max_active_planets", 256, 1, 100_000);
        EXECUTE_BOSSES = builder.comment("Tiers that kill bosses finish a creature that survives a hit that should have been lethal, such as a boss that is protected while it spawns.")
                .define("execute_bosses", true);
        EXTRA_BOSSES = builder.comment("Entity ids that count as bosses besides the c:bosses tag, the Wither and the Ender Dragon.")
                .defineListAllowEmpty("extra_boss_entities", List.of(), () -> "minecraft:warden", element -> element instanceof String);
        builder.pop();
        builder.push("visuals");
        TRAIL_PARTICLES = builder.comment("Soft trail particles every planet leaves every tick. 0 turns the trail off.")
                .defineInRange("trail_particles", 1, 0, 8);
        HIT_PARTICLES = builder.comment("Particles when a planet hits.")
                .define("hit_particles", true);
        SOUNDS = builder.comment("Sounds when planets appear, hit and fade.")
                .define("sounds", true);
        SOUND_VOLUME = builder.comment("Volume of the planet sounds.")
                .defineInRange("sound_volume", 0.6D, 0.0D, 4.0D);
        ORBIT_SPEED = builder.comment("Multiplier of the orbital speed of the planets.")
                .defineInRange("orbit_speed", 1.0D, 0.1D, 8.0D);
        TINT_STRENGTH = builder.comment("How much the colour of the tier tints the planets. 0 keeps the texture of every planet.")
                .defineInRange("tint_strength", 0.55D, 0.0D, 1.0D);
        PLANET_SCALE = builder.comment("Size multiplier of the planets when they are drawn.")
                .defineInRange("planet_scale", 1.0D, 0.1D, 4.0D);
        builder.pop();
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            int i = tier.ordinal();
            builder.push(tier.id());
            ModConfigSpec.IntValue planets = builder.comment("Planets a throw summons.")
                    .defineInRange("planets", PLANETS[i], 1, MAX_PLANETS);
            ModConfigSpec.DoubleValue damage = builder.comment("Damage of every planet hit.")
                    .defineInRange("damage", DAMAGE[i], 0.0D, 100_000.0D);
            ModConfigSpec.IntValue interval = builder.comment("Ticks between two hits of the same planet.")
                    .defineInRange("interval", INTERVAL[i], 1, 1_200);
            ModConfigSpec.IntValue duration = builder.comment("Ticks the planets stay in orbit.")
                    .defineInRange("duration", DURATION[i], 20, 72_000);
            ModConfigSpec.DoubleValue radius = builder.comment("Radius in blocks of the orbits, counted from the body of the creature.")
                    .defineInRange("orbit_radius", RADIUS[i], 0.5D, 32.0D);
            ModConfigSpec.IntValue cooldown = builder.comment("Ticks before the pearl can be thrown again.")
                    .defineInRange("cooldown", COOLDOWN[i], 0, 72_000);
            ModConfigSpec.DoubleValue bossCap = builder.comment("Most damage a planet hit does to a boss. Tiers that kill bosses ignore it.")
                    .defineInRange("boss_damage_cap", BOSS_CAP[i], 0.0D, 100_000.0D);
            ModConfigSpec.IntValue durability = tier.unbreakable() ? null : builder.comment("Throws the pearl survives. Every throw wears one point and the pearl breaks with the last one.")
                    .defineInRange("durability", DURABILITY[i], 1, 100_000);
            TIERS.put(tier, new TierValues(planets, damage, interval, duration, radius, cooldown, bossCap, durability));
            builder.pop();
        }
        SPEC = builder.build();
    }

    private PearlConfig() {
    }

    private static <T> T get(ModConfigSpec.ConfigValue<T> value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static boolean enabled() {
        return get(ENABLED);
    }

    public static boolean tierEnabled(PlanetaryTier tier) {
        if (!enabled()) {
            return false;
        }
        for (PlanetaryTier step = tier; step != null; step = step.previous()) {
            if (!CompatToggles.enabled(step.pearlToggle())) {
                return false;
            }
        }
        return true;
    }

    public static boolean includeThrower() {
        return get(INCLUDE_THROWER);
    }

    public static double searchRadius() {
        return get(SEARCH_RADIUS);
    }

    public static boolean hitPlayers() {
        return get(HIT_PLAYERS);
    }

    public static boolean sparePets() {
        return get(SPARE_PETS);
    }

    public static boolean resetInvulnerability() {
        return get(RESET_INVULNERABILITY);
    }

    public static double knockbackScale() {
        return get(KNOCKBACK_SCALE);
    }

    public static double pointHitRadius() {
        return get(POINT_HIT_RADIUS);
    }

    public static int maxActive() {
        return get(MAX_ACTIVE);
    }

    public static boolean executeBosses() {
        return get(EXECUTE_BOSSES);
    }

    public static List<? extends String> extraBosses() {
        return get(EXTRA_BOSSES);
    }

    public static int trailParticles() {
        return get(TRAIL_PARTICLES);
    }

    public static boolean hitParticles() {
        return get(HIT_PARTICLES);
    }

    public static boolean sounds() {
        return get(SOUNDS);
    }

    public static float soundVolume() {
        return get(SOUND_VOLUME).floatValue();
    }

    public static double orbitSpeed() {
        return get(ORBIT_SPEED);
    }

    public static float tintStrength() {
        return get(TINT_STRENGTH).floatValue();
    }

    public static float planetScale() {
        return get(PLANET_SCALE).floatValue();
    }

    public static int planets(PlanetaryTier tier) {
        return Math.min(MAX_PLANETS, get(TIERS.get(tier).planets()));
    }

    public static double damage(PlanetaryTier tier) {
        return get(TIERS.get(tier).damage());
    }

    public static int interval(PlanetaryTier tier) {
        return get(TIERS.get(tier).interval());
    }

    public static int duration(PlanetaryTier tier) {
        return get(TIERS.get(tier).duration());
    }

    public static double radius(PlanetaryTier tier) {
        return get(TIERS.get(tier).radius());
    }

    public static int cooldown(PlanetaryTier tier) {
        return get(TIERS.get(tier).cooldown());
    }

    public static double bossCap(PlanetaryTier tier) {
        return get(TIERS.get(tier).bossCap());
    }

    public static int durability(PlanetaryTier tier) {
        ModConfigSpec.IntValue value = TIERS.get(tier).durability();
        return value == null ? 0 : get(value);
    }
}
