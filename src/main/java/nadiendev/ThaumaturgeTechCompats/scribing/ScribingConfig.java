package nadiendev.ThaumaturgeTechCompats.scribing;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ScribingConfig {
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue INFINITY_SELF_REPAIR;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("scribing_tools");
        INFINITY_SELF_REPAIR = builder.comment("The Infinity scribing tools restore all their ink while they are in a player's inventory.")
                .define("infinity_self_repair", true);
        builder.pop();
        SPEC = builder.build();
    }

    private ScribingConfig() {
    }

    public static boolean infinitySelfRepair() {
        return !SPEC.isLoaded() || INFINITY_SELF_REPAIR.get();
    }
}
