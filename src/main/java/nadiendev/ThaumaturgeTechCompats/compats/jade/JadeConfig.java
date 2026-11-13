package nadiendev.ThaumaturgeTechCompats.compats.jade;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class JadeConfig {
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue ENABLED;
    private static final Map<JadeFamily, ModConfigSpec.BooleanValue> FAMILIES = new EnumMap<>(JadeFamily.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("jade");
        ENABLED = builder.comment("Master switch for the Jade tooltips of the Avaritia blocks. When false none of them is sent.")
                .define("enabled", true);
        for (JadeFamily family : JadeFamily.values()) {
            if (!family.configurable()) {
                continue;
            }
            FAMILIES.put(family, builder.comment("Shows the " + family.configKey().replace('_', ' ') + " information in Jade.")
                    .define(family.configKey(), true));
        }
        builder.pop();

        SPEC = builder.build();
    }

    private JadeConfig() {
    }

    public static boolean enabled() {
        return !SPEC.isLoaded() || ENABLED.get();
    }

    public static boolean enabled(JadeFamily family) {
        if (!enabled()) {
            return false;
        }
        ModConfigSpec.BooleanValue value = FAMILIES.get(family);
        return value == null || !SPEC.isLoaded() || value.get();
    }
}
