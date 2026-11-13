package nadiendev.ThaumaturgeTechCompats.jar;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class JarConfig {
    public static final ModConfigSpec SPEC;

    private static final Map<JarTier, ModConfigSpec.IntValue> CAPACITIES = new EnumMap<>(JarTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("jars");
        for (JarTier tier : JarTier.values()) {
            CAPACITIES.put(tier, builder.comment("Units of essentia the " + tier.jarName() + " can hold. The warded jar of Thaumaturge holds 250.")
                    .defineInRange(tier.id() + "_capacity", tier.defaultCapacity(), 1, 1_000_000));
        }
        builder.pop();
        SPEC = builder.build();
    }

    private JarConfig() {
    }

    public static int capacity(JarTier tier) {
        if (!SPEC.isLoaded()) {
            return tier.defaultCapacity();
        }
        return CAPACITIES.get(tier).get();
    }
}
