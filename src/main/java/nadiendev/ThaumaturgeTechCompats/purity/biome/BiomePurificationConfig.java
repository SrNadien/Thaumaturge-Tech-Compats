package nadiendev.ThaumaturgeTechCompats.purity.biome;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.purity.PurityToggles;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class BiomePurificationConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-purity-biome.toml";
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue ENABLED;
    private static final ModConfigSpec.IntValue RADIUS;
    private static final ModConfigSpec.IntValue VERTICAL_RADIUS;
    private static final ModConfigSpec.BooleanValue RESTORE_BIOME;
    private static final ModConfigSpec.BooleanValue RESTORE_NATURAL_BIOME;
    private static final ModConfigSpec.BooleanValue CLEAN_BLOCKS;
    private static final ModConfigSpec.DoubleValue BLOCK_CLEAN_CHANCE;
    private static final ModConfigSpec.BooleanValue CLEAN_PHYSICAL_FLUX;
    private static final ModConfigSpec.BooleanValue REMOVE_SEEDS;
    private static final ModConfigSpec.BooleanValue REMOVE_PRIME_SEEDS;
    private static final ModConfigSpec.DoubleValue ECOLOGY_CLEAN_AMOUNT;
    private static final ModConfigSpec.DoubleValue FLUX_DRAIN_PER_CHUNK;
    private static final ModConfigSpec.IntValue USE_COOLDOWN;
    private static final ModConfigSpec.BooleanValue PARTICLES;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("biome_purification_potion");
        ENABLED = builder.comment("Lets the Biome Purification Potion clean the land when it breaks.")
                .define("enabled", true);
        RADIUS = builder.comment("Horizontal radius, in blocks, of the area the potion cleans.")
                .defineInRange("radius", 12, 1, 48);
        VERTICAL_RADIUS = builder.comment("Vertical radius, in blocks, of the area the potion cleans.")
                .defineInRange("vertical_radius", 8, 1, 32);
        RESTORE_BIOME = builder.comment("Restores the biome of every tainted column in the area. A column that taint painted over goes back to the biome the world generated; a column of natural tainted land takes the nearest clean biome, or plains if there is none.")
                .define("restore_biome", true);
        RESTORE_NATURAL_BIOME = builder.comment("Also restores columns of natural tainted land. Off restores only columns that taint painted over a different biome.")
                .define("restore_natural_tainted_biome", true);
        CLEAN_BLOCKS = builder.comment("Makes every tainted block in the area decay the way it does when taint dies: soil and geysers become dirt, porous rock becomes porous stone, and fibres, crust, logs, growths and spore stalks disappear.")
                .define("clean_blocks", true);
        BLOCK_CLEAN_CHANCE = builder.comment("Chance, from 0 to 1, that each tainted block in the area is cleaned.")
                .defineInRange("block_clean_chance", 1.0D, 0.0D, 1.0D);
        CLEAN_PHYSICAL_FLUX = builder.comment("Removes flux goo and flux gas from the area.")
                .define("clean_physical_flux", true);
        REMOVE_SEEDS = builder.comment("Destroys the taint seeds in the area, which otherwise grow the taint back.")
                .define("remove_taint_seeds", true);
        REMOVE_PRIME_SEEDS = builder.comment("Also destroys prime taint seeds.")
                .define("remove_prime_taint_seeds", false);
        ECOLOGY_CLEAN_AMOUNT = builder.comment("Taint saturation, from 0 to 1, removed from every chunk the area touches. A chunk counts as tainted above 0.2. 0 disables it.")
                .defineInRange("ecology_clean_amount", 1.0D, 0.0D, 1.0D);
        FLUX_DRAIN_PER_CHUNK = builder.comment("Flux removed from the aura of every chunk the area touches. 0 disables it.")
                .defineInRange("aura_flux_drain_per_chunk", 15.0D, 0.0D, 10000.0D);
        USE_COOLDOWN = builder.comment("Ticks a player has to wait before throwing another potion. 0 disables it.")
                .defineInRange("use_cooldown_ticks", 40, 0, 72000);
        PARTICLES = builder.comment("Shows happy villager particles over the cleaned area.")
                .define("particles", true);
        builder.pop();
        SPEC = builder.build();
    }

    private BiomePurificationConfig() {
    }

    public static boolean enabled() {
        return (!SPEC.isLoaded() || ENABLED.get())
                && CompatToggles.enabled(CompatToggles.PURIFICATION_POTION)
                && CompatToggles.enabled(PurityToggles.BIOME_POTION);
    }

    public static int radius() {
        return SPEC.isLoaded() ? RADIUS.get() : RADIUS.getDefault();
    }

    public static int verticalRadius() {
        return SPEC.isLoaded() ? VERTICAL_RADIUS.get() : VERTICAL_RADIUS.getDefault();
    }

    public static boolean restoreBiome() {
        return !SPEC.isLoaded() || RESTORE_BIOME.get();
    }

    public static boolean restoreNaturalBiome() {
        return !SPEC.isLoaded() || RESTORE_NATURAL_BIOME.get();
    }

    public static boolean cleanBlocks() {
        return !SPEC.isLoaded() || CLEAN_BLOCKS.get();
    }

    public static double blockCleanChance() {
        return SPEC.isLoaded() ? BLOCK_CLEAN_CHANCE.get() : BLOCK_CLEAN_CHANCE.getDefault();
    }

    public static boolean cleanPhysicalFlux() {
        return !SPEC.isLoaded() || CLEAN_PHYSICAL_FLUX.get();
    }

    public static boolean removeSeeds() {
        return !SPEC.isLoaded() || REMOVE_SEEDS.get();
    }

    public static boolean removePrimeSeeds() {
        return SPEC.isLoaded() ? REMOVE_PRIME_SEEDS.get() : REMOVE_PRIME_SEEDS.getDefault();
    }

    public static float ecologyCleanAmount() {
        double amount = SPEC.isLoaded() ? ECOLOGY_CLEAN_AMOUNT.get() : ECOLOGY_CLEAN_AMOUNT.getDefault();
        return (float) amount;
    }

    public static float fluxDrainPerChunk() {
        double amount = SPEC.isLoaded() ? FLUX_DRAIN_PER_CHUNK.get() : FLUX_DRAIN_PER_CHUNK.getDefault();
        return (float) amount;
    }

    public static int useCooldown() {
        return SPEC.isLoaded() ? USE_COOLDOWN.get() : USE_COOLDOWN.getDefault();
    }

    public static boolean particles() {
        return !SPEC.isLoaded() || PARTICLES.get();
    }
}
