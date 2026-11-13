package nadiendev.ThaumaturgeTechCompats.ponder;

import nadiendev.ThaumaturgeTechCompats.config.ThaumaturgeTechCompatsConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PonderConfig {
    private static final Map<String, ModConfigSpec.BooleanValue> SCENES = new LinkedHashMap<>();
    private static ModConfigSpec.BooleanValue enabled;

    private PonderConfig() {
    }

    public static void define(ModConfigSpec.Builder builder) {
        builder.push("ponder");
        enabled = builder.comment("Registers the Ponder scenes of this mod. When false none of them is registered.")
                .define("enabled", true);
        for (PonderScript script : PonderScripts.ALL) {
            SCENES.put(script.id(), builder.comment("Registers the Ponder scene \"" + script.title() + "\".")
                    .define(script.id(), true));
        }
        builder.pop();
    }

    public static boolean enabled() {
        return !ThaumaturgeTechCompatsConfig.SPEC.isLoaded() || enabled.get();
    }

    public static boolean enabled(PonderScript script) {
        return enabled() && (!ThaumaturgeTechCompatsConfig.SPEC.isLoaded() || SCENES.get(script.id()).get());
    }
}
