package nadiendev.ThaumaturgeTechCompats.purity;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public final class PurityConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-purity.toml";
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue ENABLED;
    private static final ModConfigSpec.DoubleValue RADIUS;
    private static final ModConfigSpec.DoubleValue CURE_CHANCE;
    private static final ModConfigSpec.BooleanValue CURE_PETS;
    private static final ModConfigSpec.BooleanValue CURE_PEACEFUL;
    private static final ModConfigSpec.BooleanValue CURE_MONSTERS;
    private static final ModConfigSpec.BooleanValue CURE_PLAYERS;
    private static final ModConfigSpec.BooleanValue REMOVE_FLUX_EFFECTS;
    private static final ModConfigSpec.DoubleValue HEAL_AMOUNT;
    private static final ModConfigSpec.IntValue USE_COOLDOWN;
    private static final ModConfigSpec.BooleanValue PARTICLES;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> BLACKLIST;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("purification_potion");
        ENABLED = builder.comment("Lets the Purification Splash Potion cure anything when it breaks.")
                .define("enabled", true);
        RADIUS = builder.comment("Horizontal radius, in blocks, of the area the potion purifies.")
                .defineInRange("radius", 4.0D, 0.5D, 32.0D);
        CURE_CHANCE = builder.comment("Chance, from 0 to 1, that each infected creature in the area is cured.")
                .defineInRange("cure_chance", 1.0D, 0.0D, 1.0D);
        CURE_PETS = builder.comment("Cures tamed creatures that have an owner.")
                .define("cure_pets", true);
        CURE_PEACEFUL = builder.comment("Cures animals, villagers and any other creature that is not hostile and not a pet.")
                .define("cure_peaceful_mobs", true);
        CURE_MONSTERS = builder.comment("Cures hostile creatures.")
                .define("cure_monsters", true);
        CURE_PLAYERS = builder.comment("Removes the flux effects from players in the area.")
                .define("cure_players", true);
        REMOVE_FLUX_EFFECTS = builder.comment("Also removes the flux taint effect, so a creature cannot be infected when it is about to die.")
                .define("remove_flux_effects", true);
        HEAL_AMOUNT = builder.comment("Health points restored to a creature when it is cured. 0 disables it.")
                .defineInRange("heal_amount", 4.0D, 0.0D, 1024.0D);
        USE_COOLDOWN = builder.comment("Ticks a player has to wait before throwing another potion. 0 disables it.")
                .defineInRange("use_cooldown_ticks", 0, 0, 72000);
        PARTICLES = builder.comment("Shows happy villager particles over every cured creature.")
                .define("particles", true);
        BLACKLIST = builder.comment("Entity type ids that the potion never cures, for example minecraft:cow.")
                .defineListAllowEmpty("blacklist", List.of(), () -> "minecraft:pig", value -> value instanceof String);
        builder.pop();
        SPEC = builder.build();
    }

    private PurityConfig() {
    }

    public static boolean enabled() {
        return (!SPEC.isLoaded() || ENABLED.get()) && CompatToggles.enabled(CompatToggles.PURIFICATION_POTION);
    }

    public static double radius() {
        return SPEC.isLoaded() ? RADIUS.get() : RADIUS.getDefault();
    }

    public static double cureChance() {
        return SPEC.isLoaded() ? CURE_CHANCE.get() : CURE_CHANCE.getDefault();
    }

    public static boolean curePets() {
        return !SPEC.isLoaded() || CURE_PETS.get();
    }

    public static boolean curePeaceful() {
        return !SPEC.isLoaded() || CURE_PEACEFUL.get();
    }

    public static boolean cureMonsters() {
        return !SPEC.isLoaded() || CURE_MONSTERS.get();
    }

    public static boolean curePlayers() {
        return !SPEC.isLoaded() || CURE_PLAYERS.get();
    }

    public static boolean removeFluxEffects() {
        return !SPEC.isLoaded() || REMOVE_FLUX_EFFECTS.get();
    }

    public static float healAmount() {
        double amount = SPEC.isLoaded() ? HEAL_AMOUNT.get() : HEAL_AMOUNT.getDefault();
        return (float) amount;
    }

    public static int useCooldown() {
        return SPEC.isLoaded() ? USE_COOLDOWN.get() : USE_COOLDOWN.getDefault();
    }

    public static boolean particles() {
        return !SPEC.isLoaded() || PARTICLES.get();
    }

    public static List<? extends String> blacklist() {
        return SPEC.isLoaded() ? BLACKLIST.get() : List.of();
    }
}
