package nadiendev.ThaumaturgeTechCompats.flux;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class FluxConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-flux-network.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<FluxTier, ModConfigSpec.IntValue> LINK_RANGE = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.LongValue> LINK_RATE = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.LongValue> CORE_CAPACITY = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.IntValue> CHARGER_RADIUS = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, ModConfigSpec.LongValue> CHARGER_RATE = new EnumMap<>(FluxTier.class);
    private static final ModConfigSpec.IntValue RIFT_EXTRACT_INTERVAL;
    private static final ModConfigSpec.LongValue RIFT_FLUX_PER_SIZE;
    private static final ModConfigSpec.IntValue RIFT_EXTRACT_RADIUS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        for (FluxTier tier : FluxTier.values()) {
            builder.push(tier.id());
            LINK_RANGE.put(tier, builder.comment("Blocks between two Transmission Links of this tier.")
                    .defineInRange("link_range", tier.defaultLinkRange(), 1, 1024));
            LINK_RATE.put(tier, builder.comment("Dimensional Flux a Transmission Link of this tier moves every tick.")
                    .defineInRange("link_rate_per_tick", tier.defaultLinkRate(), 1L, Long.MAX_VALUE / 4));
            CORE_CAPACITY.put(tier, builder.comment("Base Dimensional Flux a Storage Core of this tier can hold, before Capacity Cells.")
                    .defineInRange("core_base_capacity", tier.defaultCoreCapacity(), 1L, Long.MAX_VALUE / 4));
            CHARGER_RADIUS.put(tier, builder.comment("Radius in blocks in which an Environmental Charger of this tier recharges items.")
                    .defineInRange("charger_radius", tier.defaultChargerRadius(), 1, 64));
            CHARGER_RATE.put(tier, builder.comment("Dimensional Flux an Environmental Charger of this tier gives to each item every tick.")
                    .defineInRange("charger_rate_per_tick", tier.defaultChargerRate(), 1L, Long.MAX_VALUE / 4));
            builder.pop();
        }
        builder.push("rift_extractor");
        RIFT_EXTRACT_INTERVAL = builder.comment("Ticks between two extractions of a Rift Extractor. Every extraction shrinks the rift by one point.")
                .defineInRange("interval", 40, 1, 72_000);
        RIFT_FLUX_PER_SIZE = builder.comment("Dimensional Flux a Rift Extractor gets for every point of size it takes from a rift.")
                .defineInRange("flux_per_size", 5_000L, 1L, Long.MAX_VALUE / 4);
        RIFT_EXTRACT_RADIUS = builder.comment("Blocks around the Rift Extractor in which it reaches a flux rift.")
                .defineInRange("radius", 6, 1, 32);
        builder.pop();
        SPEC = builder.build();
    }

    private FluxConfig() {
    }

    public static int linkRange(FluxTier tier) {
        return SPEC.isLoaded() ? LINK_RANGE.get(tier).get() : tier.defaultLinkRange();
    }

    public static long linkRate(FluxTier tier) {
        return SPEC.isLoaded() ? LINK_RATE.get(tier).get() : tier.defaultLinkRate();
    }

    public static long coreCapacity(FluxTier tier) {
        return SPEC.isLoaded() ? CORE_CAPACITY.get(tier).get() : tier.defaultCoreCapacity();
    }

    public static int chargerRadius(FluxTier tier) {
        return SPEC.isLoaded() ? CHARGER_RADIUS.get(tier).get() : tier.defaultChargerRadius();
    }

    public static long chargerRate(FluxTier tier) {
        return SPEC.isLoaded() ? CHARGER_RATE.get(tier).get() : tier.defaultChargerRate();
    }

    public static int riftExtractInterval() {
        return SPEC.isLoaded() ? RIFT_EXTRACT_INTERVAL.get() : 40;
    }

    public static long riftFluxPerSize() {
        return SPEC.isLoaded() ? RIFT_FLUX_PER_SIZE.get() : 5_000L;
    }

    public static int riftExtractRadius() {
        return SPEC.isLoaded() ? RIFT_EXTRACT_RADIUS.get() : 6;
    }
}
