package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.Map;

public final class IndustrialForegoingAddonsConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-industrialforegoing-addons.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<String, ModConfigSpec.DoubleValue> VALUES = new HashMap<>();

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        for (AddonKind kind : AddonKind.values()) {
            builder.comment(comment(kind)).push(kind.id());
            for (AddonTier tier : AddonTier.values()) {
                VALUES.put(key(kind, tier), builder.defineInRange(tier.id(), kind.defaultValue(tier), kind.min(), kind.max()));
            }
            builder.pop();
        }
        SPEC = builder.build();
    }

    private IndustrialForegoingAddonsConfig() {
    }

    public static float value(AddonKind kind, AddonTier tier) {
        ModConfigSpec.DoubleValue value = VALUES.get(key(kind, tier));
        double result = SPEC.isLoaded() ? value.get() : value.getDefault();
        return (float) result;
    }

    private static String key(AddonKind kind, AddonTier tier) {
        return kind.id() + "." + tier.id();
    }

    private static String comment(AddonKind kind) {
        return switch (kind) {
            case SPEED -> "Work speed multiplier of the speed addons (Industrial Foregoing uses 2 and 3). Applied as whole ticks of progress per tick.";
            case EFFICIENCY -> "Multiplier of the time between machine operations (Industrial Foregoing uses 0.9 and 0.8). Lower is faster.";
            case PROCESSING -> "Operations performed per machine cycle (Industrial Foregoing uses 2 and 3).";
            case RANGE -> "Extra working range of the range addons, in blocks minus one (Industrial Foregoing goes from 0 to 11).";
        };
    }
}
