package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class CrucifixClientConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-star-crucifix-client.toml";
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue SCREEN_EFFECTS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        SCREEN_EFFECTS = builder.comment("Screen shake and flash when a Star Crucifix fails against a boss. Also scaled by the vanilla Screen Effect Scale option.")
                .define("screen_effects", true);
        SPEC = builder.build();
    }

    private CrucifixClientConfig() {
    }

    public static boolean screenEffects() {
        return SPEC.isLoaded() ? SCREEN_EFFECTS.get() : SCREEN_EFFECTS.getDefault();
    }
}
