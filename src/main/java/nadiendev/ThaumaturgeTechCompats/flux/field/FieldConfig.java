package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class FieldConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-flux-field.toml";
    public static final ModConfigSpec SPEC;

    private static final List<String> DEFAULT_BAITS = List.of(
            "minecraft:sand=minecraft:desert",
            "minecraft:cactus=minecraft:desert",
            "minecraft:red_sand=minecraft:badlands",
            "minecraft:terracotta=minecraft:badlands",
            "minecraft:snowball=minecraft:snowy_plains",
            "minecraft:snow_block=minecraft:snowy_plains",
            "minecraft:packed_ice=minecraft:ice_spikes",
            "minecraft:grass_block=minecraft:plains",
            "minecraft:wheat_seeds=minecraft:plains",
            "minecraft:sunflower=minecraft:sunflower_plains",
            "minecraft:cornflower=minecraft:flower_forest",
            "minecraft:oak_sapling=minecraft:forest",
            "minecraft:birch_sapling=minecraft:birch_forest",
            "minecraft:dark_oak_sapling=minecraft:dark_forest",
            "minecraft:spruce_sapling=minecraft:taiga",
            "minecraft:jungle_sapling=minecraft:jungle",
            "minecraft:bamboo=minecraft:bamboo_jungle",
            "minecraft:acacia_sapling=minecraft:savanna",
            "minecraft:cherry_sapling=minecraft:cherry_grove",
            "minecraft:mangrove_propagule=minecraft:mangrove_swamp",
            "minecraft:lily_pad=minecraft:swamp",
            "minecraft:red_mushroom=minecraft:mushroom_fields",
            "minecraft:brown_mushroom=minecraft:mushroom_fields",
            "minecraft:mycelium=minecraft:mushroom_fields",
            "minecraft:stone=minecraft:windswept_hills",
            "minecraft:kelp=minecraft:ocean",
            "minecraft:sea_pickle=minecraft:warm_ocean",
            "minecraft:dripstone_block=minecraft:dripstone_caves",
            "minecraft:glow_berries=minecraft:lush_caves",
            "minecraft:sculk=minecraft:deep_dark",
            "minecraft:netherrack=minecraft:nether_wastes",
            "minecraft:crimson_nylium=minecraft:crimson_forest",
            "minecraft:warped_nylium=minecraft:warped_forest",
            "minecraft:soul_sand=minecraft:soul_sand_valley",
            "minecraft:basalt=minecraft:basalt_deltas",
            "minecraft:end_stone=minecraft:the_end");
    private static final List<String> DEFAULT_BLOCKED_BIOMES = List.of(
            "thaumaturge:tainted_lands",
            "thaumaturge:eldritch",
            "thaumaturge:eerie");
    private static final List<String> DEFAULT_BLOCKED_DIMENSIONS = List.of(
            "minecraft:the_end",
            "thaumaturge:outer_lands");

    private static final Map<FluxTier, ModConfigSpec.IntValue> SHIELD_RADIUS = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.LongValue> SHIELD_CAPACITY = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.LongValue> SHIELD_COST = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.LongValue> SHIELD_PROJECTILE_COST = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.LongValue> SHIELD_EXPLOSION_COST = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.IntValue> TERRAFORMER_RADIUS = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.LongValue> TERRAFORMER_CAPACITY = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.LongValue> TERRAFORMER_COST = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.IntValue> TERRAFORMER_COLUMNS = new EnumMap<>(FluxTier.class);

    private static final ModConfigSpec.BooleanValue SHIELD_ENABLED;
    private static final ModConfigSpec.IntValue SHIELD_SCAN_INTERVAL;
    private static final ModConfigSpec.BooleanValue SHIELD_BLOCK_PROJECTILES;
    private static final ModConfigSpec.BooleanValue SHIELD_BLOCK_OWNERLESS;
    private static final ModConfigSpec.BooleanValue SHIELD_PUSH_MONSTERS;
    private static final ModConfigSpec.DoubleValue SHIELD_PUSH_STRENGTH;
    private static final ModConfigSpec.BooleanValue SHIELD_EXPLOSION_BLOCKS;
    private static final ModConfigSpec.DoubleValue SHIELD_EXPLOSION_REDUCTION;
    private static final ModConfigSpec.IntValue SHIELD_RESTART_TICKS;
    private static final ModConfigSpec.IntValue SHIELD_MESSAGE_RADIUS;
    private static final ModConfigSpec.BooleanValue SHIELD_PARTICLES;
    private static final ModConfigSpec.IntValue SHIELD_PARTICLE_COUNT;
    private static final ModConfigSpec.IntValue SHIELD_PARTICLE_INTERVAL;
    private static final ModConfigSpec.DoubleValue SHIELD_PARTICLE_SCALE;

    private static final ModConfigSpec.BooleanValue TERRAFORMER_ENABLED;
    private static final ModConfigSpec.IntValue TERRAFORMER_INTERVAL;
    private static final ModConfigSpec.IntValue TERRAFORMER_MAX_SCANNED;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> TERRAFORMER_BAITS;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> TERRAFORMER_BLOCKED_BIOMES;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> TERRAFORMER_BLOCKED_DIMENSIONS;
    private static final ModConfigSpec.BooleanValue TERRAFORMER_PARTICLES;

    private static final ModConfigSpec.BooleanValue THRUSTER_ENABLED;
    private static final ModConfigSpec.LongValue THRUSTER_CAPACITY;
    private static final ModConfigSpec.DoubleValue THRUSTER_THRUST;
    private static final ModConfigSpec.DoubleValue THRUSTER_MAX_VERTICAL;
    private static final ModConfigSpec.DoubleValue THRUSTER_FORWARD;
    private static final ModConfigSpec.DoubleValue THRUSTER_MAX_HORIZONTAL;
    private static final ModConfigSpec.LongValue THRUSTER_COST_PER_SECOND;
    private static final ModConfigSpec.IntValue THRUSTER_BURST_TICKS;
    private static final ModConfigSpec.IntValue THRUSTER_COOLDOWN_TICKS;
    private static final ModConfigSpec.BooleanValue THRUSTER_FALL_DAMAGE;
    private static final ModConfigSpec.BooleanValue THRUSTER_PARTICLES;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("shield_generator");
        SHIELD_ENABLED = builder.comment("Shield Generators project their barrier. Off turns every Shield Generator into an inert block.")
                .define("enabled", true);
        SHIELD_SCAN_INTERVAL = builder.comment("Ticks between two sweeps of the shield bubble for projectiles and monsters. Lower is safer against fast projectiles and costs more performance.")
                .defineInRange("scan_interval_ticks", 1, 1, 20);
        SHIELD_BLOCK_PROJECTILES = builder.comment("The bubble destroys projectiles fired by hostile mobs, such as arrows, fireballs and shulker bullets.")
                .define("block_projectiles", true);
        SHIELD_BLOCK_OWNERLESS = builder.comment("The bubble also destroys projectiles that have no owner, such as those fired by dispensers.")
                .define("block_ownerless_projectiles", false);
        SHIELD_PUSH_MONSTERS = builder.comment("The bubble pushes hostile mobs out of its radius. Bosses are never pushed.")
                .define("push_monsters", true);
        SHIELD_PUSH_STRENGTH = builder.comment("Horizontal push in blocks per tick that a hostile mob inside the bubble receives on every sweep.")
                .defineInRange("push_strength", 0.35D, 0.0D, 5.0D);
        SHIELD_EXPLOSION_BLOCKS = builder.comment("Explosions that go off inside the bubble do not destroy the blocks inside it.")
                .define("explosion_protect_blocks", true);
        SHIELD_EXPLOSION_REDUCTION = builder.comment("Fraction of the explosion damage that entities inside the bubble do not receive. 1.0 cancels it completely, 0.0 gives no protection to entities.")
                .defineInRange("explosion_damage_reduction", 1.0D, 0.0D, 1.0D);
        SHIELD_RESTART_TICKS = builder.comment("A Shield Generator that ran out of Dimensional Flux starts again once it holds the flux of this many ticks of operation, or its whole capacity if that is less.")
                .defineInRange("restart_ticks", 100, 1, 72_000);
        SHIELD_MESSAGE_RADIUS = builder.comment("Radius in blocks around a Shield Generator in which players get the message that it ran out of Dimensional Flux.")
                .defineInRange("shutdown_message_radius", 32, 0, 256);
        SHIELD_PARTICLES = builder.comment("A shell of particles marks the edge of the bubble. Only players nearby see it.")
                .define("particles", true);
        SHIELD_PARTICLE_COUNT = builder.comment("Particles of the shell spawned every time the shell is refreshed.")
                .defineInRange("particle_count", 8, 1, 128);
        SHIELD_PARTICLE_INTERVAL = builder.comment("Ticks between two refreshes of the particle shell.")
                .defineInRange("particle_interval_ticks", 4, 1, 200);
        SHIELD_PARTICLE_SCALE = builder.comment("Size of the particles of the shell.")
                .defineInRange("particle_scale", 1.0D, 0.1D, 4.0D);
        for (FluxTier tier : FluxTier.values()) {
            builder.push(tier.id());
            SHIELD_RADIUS.put(tier, builder.comment("Radius in blocks of the bubble of a Shield Generator of this tier.")
                    .defineInRange("radius", defaultShieldRadius(tier), 1, 64));
            SHIELD_CAPACITY.put(tier, builder.comment("Dimensional Flux a Shield Generator of this tier can hold.")
                    .defineInRange("capacity", defaultShieldCapacity(tier), 1L, Long.MAX_VALUE / 4));
            SHIELD_COST.put(tier, builder.comment("Dimensional Flux a Shield Generator of this tier uses every tick while its bubble is up.")
                    .defineInRange("cost_per_tick", defaultShieldCost(tier), 0L, Long.MAX_VALUE / 4));
            SHIELD_PROJECTILE_COST.put(tier, builder.comment("Extra Dimensional Flux for every projectile the bubble destroys.")
                    .defineInRange("projectile_cost", 100L, 0L, Long.MAX_VALUE / 4));
            SHIELD_EXPLOSION_COST.put(tier, builder.comment("Extra Dimensional Flux for every explosion the bubble absorbs. Without that much flux the explosion goes through and the shield shuts down.")
                    .defineInRange("explosion_cost", 5_000L, 0L, Long.MAX_VALUE / 4));
            builder.pop();
        }
        builder.pop();

        builder.push("arcane_terraformer");
        TERRAFORMER_ENABLED = builder.comment("Arcane Terraformers change biomes. Off turns every Arcane Terraformer into an inert block.")
                .define("enabled", true);
        TERRAFORMER_INTERVAL = builder.comment("Ticks between two work cycles of an Arcane Terraformer.")
                .defineInRange("cycle_interval_ticks", 20, 1, 72_000);
        TERRAFORMER_MAX_SCANNED = builder.comment("Most columns a Terraformer looks at in a single cycle, including those it skips for being already the target biome, blacklisted or in an unloaded chunk.")
                .defineInRange("max_scanned_per_cycle", 2048, 1, 100_000);
        TERRAFORMER_BAITS = builder.comment("Biome baits: an item and the biome it makes a Terraformer paint, as item_id=biome_id. Right-click the Terraformer with the item to load it.")
                .defineListAllowEmpty("baits", DEFAULT_BAITS, () -> "minecraft:sand=minecraft:desert", FieldConfig::validPair);
        TERRAFORMER_BLOCKED_BIOMES = builder.comment("Biomes that a Terraformer never overwrites and never paints.")
                .defineListAllowEmpty("biome_blacklist", DEFAULT_BLOCKED_BIOMES, () -> "thaumaturge:tainted_lands", FieldConfig::validId);
        TERRAFORMER_BLOCKED_DIMENSIONS = builder.comment("Dimensions in which a Terraformer does nothing.")
                .defineListAllowEmpty("dimension_blacklist", DEFAULT_BLOCKED_DIMENSIONS, () -> "minecraft:the_end", FieldConfig::validId);
        TERRAFORMER_PARTICLES = builder.comment("A Terraformer at work gives out particles.")
                .define("particles", true);
        for (FluxTier tier : FluxTier.values()) {
            builder.push(tier.id());
            TERRAFORMER_RADIUS.put(tier, builder.comment("Radius in blocks of the area an Arcane Terraformer of this tier repaints.")
                    .defineInRange("radius", defaultTerraformerRadius(tier), 1, 256));
            TERRAFORMER_CAPACITY.put(tier, builder.comment("Dimensional Flux an Arcane Terraformer of this tier can hold.")
                    .defineInRange("capacity", defaultTerraformerCapacity(tier), 1L, Long.MAX_VALUE / 4));
            TERRAFORMER_COST.put(tier, builder.comment("Dimensional Flux an Arcane Terraformer of this tier uses for every 4x4 column it repaints.")
                    .defineInRange("cost_per_column", defaultTerraformerCost(tier), 0L, Long.MAX_VALUE / 4));
            TERRAFORMER_COLUMNS.put(tier, builder.comment("Columns an Arcane Terraformer of this tier repaints in every work cycle.")
                    .defineInRange("columns_per_cycle", defaultTerraformerColumns(tier), 1, 4096));
            builder.pop();
        }
        builder.pop();

        builder.push("flight_thruster");
        THRUSTER_ENABLED = builder.comment("The Flight Thruster lifts its wearer. Off turns it into a plain chest piece.")
                .define("enabled", true);
        THRUSTER_CAPACITY = builder.comment("Dimensional Flux a Flight Thruster can hold.")
                .defineInRange("capacity", 200_000L, 1L, Long.MAX_VALUE / 4);
        THRUSTER_THRUST = builder.comment("Upward speed in blocks per tick added every tick of thrust.")
                .defineInRange("thrust_per_tick", 0.12D, 0.0D, 2.0D);
        THRUSTER_MAX_VERTICAL = builder.comment("Highest upward speed in blocks per tick that the thrust alone can reach.")
                .defineInRange("max_vertical_speed", 0.45D, 0.0D, 3.0D);
        THRUSTER_FORWARD = builder.comment("Horizontal speed in blocks per tick added every tick of thrust while the forward key is held.")
                .defineInRange("forward_thrust_per_tick", 0.04D, 0.0D, 2.0D);
        THRUSTER_MAX_HORIZONTAL = builder.comment("Highest horizontal speed in blocks per tick that the forward thrust can reach.")
                .defineInRange("max_horizontal_speed", 0.7D, 0.0D, 3.0D);
        THRUSTER_COST_PER_SECOND = builder.comment("Dimensional Flux used for every second of thrust.")
                .defineInRange("cost_per_second", 800L, 0L, Long.MAX_VALUE / 4);
        THRUSTER_BURST_TICKS = builder.comment("Ticks of continuous thrust before the thruster overheats. Keep it under 60: servers kick players that rise for more than 80 ticks in a row.")
                .defineInRange("burst_ticks", 50, 1, 600);
        THRUSTER_COOLDOWN_TICKS = builder.comment("Ticks the thruster stays off after overheating.")
                .defineInRange("cooldown_ticks", 10, 0, 600);
        THRUSTER_FALL_DAMAGE = builder.comment("The wearer takes no fall damage while the thruster holds any Dimensional Flux.")
                .define("cancel_fall_damage", true);
        THRUSTER_PARTICLES = builder.comment("The thruster gives out particles from the back of its wearer.")
                .define("particles", true);
        builder.pop();

        SPEC = builder.build();
    }

    private FieldConfig() {
    }

    private static <T> T read(ModConfigSpec.ConfigValue<T> value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    private static boolean validId(Object value) {
        return value instanceof String text && Identifier.tryParse(text) != null;
    }

    private static boolean validPair(Object value) {
        if (!(value instanceof String text)) {
            return false;
        }
        int split = text.indexOf('=');
        return split > 0 && validId(text.substring(0, split)) && validId(text.substring(split + 1));
    }

    private static int defaultShieldRadius(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> 8;
            case ALCHEMICAL_BRASS -> 16;
            case VOID_METAL -> 32;
        };
    }

    private static long defaultShieldCapacity(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> 100_000L;
            case ALCHEMICAL_BRASS -> 1_000_000L;
            case VOID_METAL -> 10_000_000L;
        };
    }

    private static long defaultShieldCost(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> 10L;
            case ALCHEMICAL_BRASS -> 40L;
            case VOID_METAL -> 160L;
        };
    }

    private static int defaultTerraformerRadius(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> 16;
            case ALCHEMICAL_BRASS -> 32;
            case VOID_METAL -> 64;
        };
    }

    private static long defaultTerraformerCapacity(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> 200_000L;
            case ALCHEMICAL_BRASS -> 2_000_000L;
            case VOID_METAL -> 20_000_000L;
        };
    }

    private static long defaultTerraformerCost(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> 2_000L;
            case ALCHEMICAL_BRASS -> 1_500L;
            case VOID_METAL -> 1_000L;
        };
    }

    private static int defaultTerraformerColumns(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> 2;
            case ALCHEMICAL_BRASS -> 6;
            case VOID_METAL -> 16;
        };
    }

    public static boolean shieldEnabled() {
        return read(SHIELD_ENABLED);
    }

    public static int shieldScanInterval() {
        return read(SHIELD_SCAN_INTERVAL);
    }

    public static boolean shieldBlockProjectiles() {
        return read(SHIELD_BLOCK_PROJECTILES);
    }

    public static boolean shieldBlockOwnerless() {
        return read(SHIELD_BLOCK_OWNERLESS);
    }

    public static boolean shieldPushMonsters() {
        return read(SHIELD_PUSH_MONSTERS);
    }

    public static double shieldPushStrength() {
        return read(SHIELD_PUSH_STRENGTH);
    }

    public static boolean shieldExplosionBlocks() {
        return read(SHIELD_EXPLOSION_BLOCKS);
    }

    public static double shieldExplosionReduction() {
        return read(SHIELD_EXPLOSION_REDUCTION);
    }

    public static int shieldRestartTicks() {
        return read(SHIELD_RESTART_TICKS);
    }

    public static int shieldMessageRadius() {
        return read(SHIELD_MESSAGE_RADIUS);
    }

    public static boolean shieldParticles() {
        return read(SHIELD_PARTICLES);
    }

    public static int shieldParticleCount() {
        return read(SHIELD_PARTICLE_COUNT);
    }

    public static int shieldParticleInterval() {
        return read(SHIELD_PARTICLE_INTERVAL);
    }

    public static float shieldParticleScale() {
        return read(SHIELD_PARTICLE_SCALE).floatValue();
    }

    public static int shieldRadius(FluxTier tier) {
        return read(SHIELD_RADIUS.get(tier));
    }

    public static long shieldCapacity(FluxTier tier) {
        return read(SHIELD_CAPACITY.get(tier));
    }

    public static long shieldCost(FluxTier tier) {
        return read(SHIELD_COST.get(tier));
    }

    public static long shieldProjectileCost(FluxTier tier) {
        return read(SHIELD_PROJECTILE_COST.get(tier));
    }

    public static long shieldExplosionCost(FluxTier tier) {
        return read(SHIELD_EXPLOSION_COST.get(tier));
    }

    public static boolean terraformerEnabled() {
        return read(TERRAFORMER_ENABLED);
    }

    public static int terraformerInterval() {
        return read(TERRAFORMER_INTERVAL);
    }

    public static int terraformerMaxScanned() {
        return read(TERRAFORMER_MAX_SCANNED);
    }

    public static List<? extends String> terraformerBaits() {
        return read(TERRAFORMER_BAITS);
    }

    public static List<? extends String> terraformerBlockedBiomes() {
        return read(TERRAFORMER_BLOCKED_BIOMES);
    }

    public static List<? extends String> terraformerBlockedDimensions() {
        return read(TERRAFORMER_BLOCKED_DIMENSIONS);
    }

    public static boolean terraformerParticles() {
        return read(TERRAFORMER_PARTICLES);
    }

    public static int terraformerRadius(FluxTier tier) {
        return read(TERRAFORMER_RADIUS.get(tier));
    }

    public static long terraformerCapacity(FluxTier tier) {
        return read(TERRAFORMER_CAPACITY.get(tier));
    }

    public static long terraformerCost(FluxTier tier) {
        return read(TERRAFORMER_COST.get(tier));
    }

    public static int terraformerColumns(FluxTier tier) {
        return read(TERRAFORMER_COLUMNS.get(tier));
    }

    public static boolean thrusterEnabled() {
        return read(THRUSTER_ENABLED);
    }

    public static long thrusterCapacity() {
        return read(THRUSTER_CAPACITY);
    }

    public static double thrusterThrust() {
        return read(THRUSTER_THRUST);
    }

    public static double thrusterMaxVertical() {
        return read(THRUSTER_MAX_VERTICAL);
    }

    public static double thrusterForward() {
        return read(THRUSTER_FORWARD);
    }

    public static double thrusterMaxHorizontal() {
        return read(THRUSTER_MAX_HORIZONTAL);
    }

    public static long thrusterCostPerSecond() {
        return read(THRUSTER_COST_PER_SECOND);
    }

    public static long thrusterCostPerTick() {
        long perSecond = thrusterCostPerSecond();
        return perSecond <= 0L ? 0L : Math.max(1L, (perSecond + 19L) / 20L);
    }

    public static int thrusterBurstTicks() {
        return read(THRUSTER_BURST_TICKS);
    }

    public static int thrusterCooldownTicks() {
        return read(THRUSTER_COOLDOWN_TICKS);
    }

    public static boolean thrusterCancelFallDamage() {
        return read(THRUSTER_FALL_DAMAGE);
    }

    public static boolean thrusterParticles() {
        return read(THRUSTER_PARTICLES);
    }
}
