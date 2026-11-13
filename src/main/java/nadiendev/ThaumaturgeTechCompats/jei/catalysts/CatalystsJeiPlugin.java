package nadiendev.ThaumaturgeTechCompats.jei.catalysts;

import com.mojang.logging.LogUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

@JeiPlugin
public class CatalystsJeiPlugin implements IModPlugin {
    public static final String TOGGLE = "jei_catalysts";

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Identifier ID = ThaumaturgeTechCompats.id("catalysts");

    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (!CompatToggles.enabled(TOGGLE)) {
            return;
        }
        if (CatalystSupport.loaded(ThaumaturgeCatalysts.MOD_ID)) {
            guard("Thaumaturge", () -> ThaumaturgeCatalysts.register(registration));
        }
        if (VoidMinersMagicalIntegration.isLoaded()) {
            guard("Void Miners", () -> VoidMinersCatalysts.register(registration));
        }
    }

    private static void guard(String target, Runnable action) {
        try {
            action.run();
        } catch (RuntimeException | LinkageError exception) {
            LOGGER.warn("The JEI catalysts for {} could not be registered", target, exception);
        }
    }
}
