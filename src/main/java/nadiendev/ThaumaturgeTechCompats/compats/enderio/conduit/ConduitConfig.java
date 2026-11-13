package nadiendev.ThaumaturgeTechCompats.compats.enderio.conduit;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class ConduitConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-enderio-conduits.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<ConduitKind, Map<EnderIOTier, ModConfigSpec.IntValue>> RATES = new EnumMap<>(ConduitKind.class);
    private static final Map<ConduitKind, Map<EnderIOTier, ModConfigSpec.IntValue>> TICKS = new EnumMap<>(ConduitKind.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        for (ConduitKind kind : ConduitKind.values()) {
            builder.push(kind.id());
            for (EnderIOTier tier : EnderIOTier.values()) {
                builder.push(tier.id());
                RATES.computeIfAbsent(kind, key -> new EnumMap<>(EnderIOTier.class)).put(tier,
                        builder.comment(rateComment(kind)).defineInRange("transfer_rate", kind.defaultRate(tier), 1, Integer.MAX_VALUE));
                if (kind.hasTicks()) {
                    TICKS.computeIfAbsent(kind, key -> new EnumMap<>(EnderIOTier.class)).put(tier,
                            builder.comment("Ticks between two transfer cycles. Lower is faster. Ender IO uses 20 for the basic item conduit and 10 for the vibrant one.")
                                    .defineInRange("ticks_per_cycle", kind.defaultTicks(tier), 1, 20));
                }
                builder.pop();
            }
            builder.pop();
        }
        SPEC = builder.build();
    }

    private ConduitConfig() {
    }

    public static int rate(ConduitKind kind, EnderIOTier tier) {
        return value(RATES.get(kind).get(tier));
    }

    public static int ticks(ConduitKind kind, EnderIOTier tier) {
        return value(TICKS.get(kind).get(tier));
    }

    private static String rateComment(ConduitKind kind) {
        return switch (kind) {
            case ENERGY -> "Energy the conduit moves every tick, in RF. The vibrant energy conduit of Ender IO moves 192000.";
            case ITEM -> "Items the conduit moves every transfer cycle. The vibrant item conduit of Ender IO moves 64.";
            case FLUID -> "Fluid the conduit moves every tick, in mB. The vibrant fluid conduit of Ender IO moves 8000.";
        };
    }

    private static int value(ModConfigSpec.IntValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
