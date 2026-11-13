package nadiendev.ThaumaturgeTechCompats.auraconverter;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class AuraConverterConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-aura-converter.toml";
    public static final ModConfigSpec SPEC;

    private static final long MAX_ENERGY = 1_000_000_000_000_000L;

    private static final Map<AuraConverterTier, ModConfigSpec.LongValue> FE_PER_VIS = new EnumMap<>(AuraConverterTier.class);
    private static final Map<AuraConverterTier, ModConfigSpec.LongValue> BUFFER = new EnumMap<>(AuraConverterTier.class);
    private static final Map<AuraConverterTier, ModConfigSpec.LongValue> OUTPUT = new EnumMap<>(AuraConverterTier.class);
    private static final ModConfigSpec.DoubleValue FLUX_PER_VIS;
    private static final ModConfigSpec.DoubleValue MIN_AURA_FRACTION;
    private static final ModConfigSpec.DoubleValue MAX_VIS_PER_CHUNK_PER_TICK;
    private static final ModConfigSpec.BooleanValue REDSTONE_PAUSES;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("general");
        FLUX_PER_VIS = builder.comment("Flux added to the aura of the chunk for every vis converted. 0 makes the conversion free of pollution.")
                .defineInRange("flux_per_vis", 0.1D, 0.0D, 100.0D);
        MIN_AURA_FRACTION = builder.comment("Fraction of the base aura of the chunk that is never drained. A converter stops while the vis of the chunk is at or under base * this value.")
                .defineInRange("min_aura_fraction", 0.25D, 0.0D, 1.0D);
        MAX_VIS_PER_CHUNK_PER_TICK = builder.comment("Most vis per tick that all the Aura Converters of one chunk can drain together. At 1.0 a converter that makes N FE per vis converts up to N FE per tick.")
                .defineInRange("max_vis_per_chunk_per_tick", 1.0D, 0.0D, 1000.0D);
        REDSTONE_PAUSES = builder.comment("A redstone signal stops the conversion. The stored energy can still be taken out.")
                .define("redstone_pauses", true);
        builder.pop();

        for (AuraConverterTier tier : AuraConverterTier.values()) {
            builder.push(tier.id());
            FE_PER_VIS.put(tier, builder.comment("FE that every vis drained from the aura is converted into.")
                    .defineInRange("convert_fe_per_vis", tier.defaultFePerVis(), 1L, MAX_ENERGY));
            OUTPUT.put(tier, builder.comment("Most FE per tick that the converter gives out, whether pushed to its neighbours or pulled from it. The FE capability of NeoForge is an int, so a single call never moves more than 2147483647 FE.")
                    .defineInRange("extract_fe_per_tick", tier.defaultOutput(), 1L, MAX_ENERGY));
            BUFFER.put(tier, builder.comment("Capacity of the internal FE buffer.")
                    .defineInRange("buffer_capacity", tier.defaultBuffer(), 1L, MAX_ENERGY));
            builder.pop();
        }

        SPEC = builder.build();
    }

    private AuraConverterConfig() {
    }

    public static boolean enabled(AuraConverterTier tier) {
        if (!CompatToggles.enabled(CompatToggles.AURA_CONVERTER)) {
            return false;
        }
        for (AuraConverterTier step = tier; step != null; step = step.previous()) {
            if (!CompatToggles.enabled(step.toggle())) {
                return false;
            }
        }
        return true;
    }

    public static long fePerVis(AuraConverterTier tier) {
        return SPEC.isLoaded() ? FE_PER_VIS.get(tier).get() : tier.defaultFePerVis();
    }

    public static long buffer(AuraConverterTier tier) {
        return SPEC.isLoaded() ? BUFFER.get(tier).get() : tier.defaultBuffer();
    }

    public static long maxOutput(AuraConverterTier tier) {
        return SPEC.isLoaded() ? OUTPUT.get(tier).get() : tier.defaultOutput();
    }

    public static float fluxPerVis() {
        return SPEC.isLoaded() ? FLUX_PER_VIS.get().floatValue() : 0.1F;
    }

    public static float minAuraFraction() {
        return SPEC.isLoaded() ? MIN_AURA_FRACTION.get().floatValue() : 0.25F;
    }

    public static float maxVisPerChunkPerTick() {
        return SPEC.isLoaded() ? MAX_VIS_PER_CHUNK_PER_TICK.get().floatValue() : 1.0F;
    }

    public static boolean redstonePauses() {
        return !SPEC.isLoaded() || REDSTONE_PAUSES.get();
    }
}
