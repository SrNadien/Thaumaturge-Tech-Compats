package nadiendev.ThaumaturgeTechCompats.compats.jade;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;

public final class JadeIntegration {
    private JadeIntegration() {
    }

    public static void register() {
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, JadeConfig.SPEC, "thaumaturgetechcompats-jade.toml");
    }
}
