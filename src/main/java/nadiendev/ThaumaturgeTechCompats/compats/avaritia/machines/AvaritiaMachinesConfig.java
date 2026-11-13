package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class AvaritiaMachinesConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-avaritia-machines.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<AvaritiaMachineTier, ModConfigSpec.IntValue> COLLECTOR_SPEED = new EnumMap<>(AvaritiaMachineTier.class);
    private static final Map<AvaritiaMachineTier, ModConfigSpec.IntValue> COLLECTOR_YIELD = new EnumMap<>(AvaritiaMachineTier.class);
    private static final Map<AvaritiaMachineTier, ModConfigSpec.IntValue> COMPRESSOR_SPEED = new EnumMap<>(AvaritiaMachineTier.class);
    private static final Map<AvaritiaMachineTier, ModConfigSpec.IntValue> COMPRESSOR_YIELD = new EnumMap<>(AvaritiaMachineTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("collector");
        for (AvaritiaMachineTier tier : AvaritiaMachineTier.values()) {
            builder.push(tier.id());
            COLLECTOR_SPEED.put(tier, builder.comment("Times the Densest Neutronium Collector works on every tick. 1 is the speed of the Densest Neutronium Collector.")
                    .defineInRange("speed_multiplier", tier.defaultCollectorSpeed(), 1, 64));
            COLLECTOR_YIELD.put(tier, builder.comment("Items produced every time the collector finishes a cycle. 1 is what the Densest Neutronium Collector gives.")
                    .defineInRange("production_multiplier", tier.defaultCollectorYield(), 1, 64));
            builder.pop();
        }
        builder.pop();
        builder.push("compressor");
        for (AvaritiaMachineTier tier : AvaritiaMachineTier.values()) {
            builder.push(tier.id());
            COMPRESSOR_SPEED.put(tier, builder.comment("Times the Densest Neutronium Compressor works on every tick. 1 is the speed of the Densest Neutronium Compressor.")
                    .defineInRange("speed_multiplier", tier.defaultCompressorSpeed(), 1, 64));
            COMPRESSOR_YIELD.put(tier, builder.comment("Multiplier of the items the compressor gives every time it finishes a recipe, with the same materials. 1 is what the Densest Neutronium Compressor gives.")
                    .defineInRange("production_multiplier", tier.defaultCompressorYield(), 1, 64));
            builder.pop();
        }
        builder.pop();
        SPEC = builder.build();
    }

    private AvaritiaMachinesConfig() {
    }

    public static int collectorSpeed(AvaritiaMachineTier tier) {
        return value(COLLECTOR_SPEED.get(tier));
    }

    public static int collectorYield(AvaritiaMachineTier tier) {
        return value(COLLECTOR_YIELD.get(tier));
    }

    public static int compressorSpeed(AvaritiaMachineTier tier) {
        return value(COMPRESSOR_SPEED.get(tier));
    }

    public static int compressorYield(AvaritiaMachineTier tier) {
        return value(COMPRESSOR_YIELD.get(tier));
    }

    private static int value(ModConfigSpec.IntValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
