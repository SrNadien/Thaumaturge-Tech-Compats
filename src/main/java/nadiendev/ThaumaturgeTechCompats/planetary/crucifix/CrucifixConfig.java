package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class CrucifixConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-star-crucifix.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<PlanetaryTier, ModConfigSpec.IntValue> MAX_HEALTH = new EnumMap<>(PlanetaryTier.class);
    private static final Map<PlanetaryTier, ModConfigSpec.DoubleValue> RANGE = new EnumMap<>(PlanetaryTier.class);
    private static final Map<PlanetaryTier, ModConfigSpec.IntValue> BIND_TICKS = new EnumMap<>(PlanetaryTier.class);
    private static final Map<PlanetaryTier, ModConfigSpec.BooleanValue> KILLS_BOSSES = new EnumMap<>(PlanetaryTier.class);
    private static final Map<PlanetaryTier, ModConfigSpec.IntValue> DURABILITY = new EnumMap<>(PlanetaryTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            builder.push(tier.id());
            MAX_HEALTH.put(tier, builder.comment("Highest maximum health of a creature this crucifix can bind. Creatures above it are treated as immune and the binding fails. 0 means no limit.")
                    .defineInRange("max_health", defaultMaxHealth(tier), 0, 1_000_000));
            RANGE.put(tier, builder.comment("Distance in blocks at which this crucifix can be used on a creature you are aiming at.")
                    .defineInRange("range", defaultRange(tier), 2.0D, 128.0D));
            BIND_TICKS.put(tier, builder.comment("Ticks a bound creature hangs in the air before it is dragged underground. Lower is faster. The chains need at least 20 ticks to close.")
                    .defineInRange("bind_ticks", defaultBindTicks(tier), CrucifixTiming.minBind(), 1200));
            KILLS_BOSSES.put(tier, builder.comment("Whether creatures of the crucifix_immune tag (bosses) are bound and killed instead of resisting and breaking the crucifix.")
                    .define("kills_bosses", tier.killsBosses()));
            if (!tier.unbreakable()) {
                DURABILITY.put(tier, builder.comment("Uses of the crucifix. Every activation wears one point and the crucifix breaks with the last one. Needs a game restart.")
                        .defineInRange("durability", defaultDurability(tier), 1, 100_000));
            }
            builder.pop();
        }
        SPEC = builder.build();
    }

    private CrucifixConfig() {
    }

    public static int maxHealth(PlanetaryTier tier) {
        return value(MAX_HEALTH.get(tier));
    }

    public static double range(PlanetaryTier tier) {
        ModConfigSpec.DoubleValue value = RANGE.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int bindTicks(PlanetaryTier tier) {
        return value(BIND_TICKS.get(tier));
    }

    public static boolean killsBosses(PlanetaryTier tier) {
        ModConfigSpec.BooleanValue value = KILLS_BOSSES.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int durability(PlanetaryTier tier) {
        ModConfigSpec.IntValue value = DURABILITY.get(tier);
        return value == null ? 0 : value(value);
    }

    private static int value(ModConfigSpec.IntValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    private static int defaultDurability(PlanetaryTier tier) {
        return switch (tier) {
            case COPPER -> 3;
            case IRON -> 5;
            case GOLD -> 8;
            case DIAMOND -> 12;
            case EMERALD -> 16;
            case THAUMIUM -> 32;
            case ALCHEMICAL_BRASS -> 64;
            case BLAZE_CUBE -> 128;
            case MATRIX -> 256;
            case NEUTRONIUM -> 512;
            case NETHERITE, VOID_METAL, INFINITY, CREATIVE -> 0;
        };
    }

    private static int defaultMaxHealth(PlanetaryTier tier) {
        return switch (tier) {
            case COPPER -> 30;
            case IRON -> 60;
            case GOLD -> 100;
            case DIAMOND -> 200;
            case EMERALD -> 300;
            case THAUMIUM -> 500;
            case ALCHEMICAL_BRASS -> 800;
            case BLAZE_CUBE -> 1500;
            case MATRIX -> 3000;
            case NEUTRONIUM -> 6000;
            case NETHERITE, VOID_METAL, INFINITY, CREATIVE -> 0;
        };
    }

    private static double defaultRange(PlanetaryTier tier) {
        return switch (tier) {
            case COPPER -> 6.0D;
            case IRON -> 7.0D;
            case GOLD -> 8.0D;
            case DIAMOND -> 9.0D;
            case EMERALD -> 10.0D;
            case NETHERITE, THAUMIUM -> 12.0D;
            case ALCHEMICAL_BRASS -> 14.0D;
            case VOID_METAL -> 16.0D;
            case BLAZE_CUBE -> 18.0D;
            case MATRIX -> 20.0D;
            case NEUTRONIUM -> 24.0D;
            case INFINITY -> 32.0D;
            case CREATIVE -> 48.0D;
        };
    }

    private static int defaultBindTicks(PlanetaryTier tier) {
        return switch (tier) {
            case COPPER -> 150;
            case IRON -> 130;
            case GOLD -> 115;
            case DIAMOND -> 100;
            case EMERALD -> 90;
            case NETHERITE -> 80;
            case THAUMIUM -> 75;
            case ALCHEMICAL_BRASS -> 65;
            case VOID_METAL -> 55;
            case BLAZE_CUBE -> 45;
            case MATRIX -> 36;
            case NEUTRONIUM -> 28;
            case INFINITY, CREATIVE -> 20;
        };
    }
}
