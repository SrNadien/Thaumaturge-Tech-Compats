package nadiendev.ThaumaturgeTechCompats.jei.catalysts;

import com.mojang.logging.LogUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.FtbUnearthedIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.IndustrialForegoingIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight.FarmersDelightTaintedIntegration;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

@JeiPlugin
public class CatalystsJeiPlugin implements IModPlugin {
    public static final String TOGGLE = "jei_catalysts";

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourceLocation ID = ThaumaturgeTechCompats.id("catalysts");

    @Override
    public ResourceLocation getPluginUid() {
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
        if (FtbUnearthedIntegration.isLoaded()) {
            guard("FTB Unearthed", () -> UneartherCatalysts.register(registration));
        }
        if (VoidMinersMagicalIntegration.isLoaded()) {
            guard("Void Miners", () -> VoidMinersCatalysts.register(registration));
        }
        if (IndustrialForegoingIntegration.loaded()) {
            guard("Industrial Foregoing", () -> IndustrialForegoingCatalysts.register(registration));
        }
        if (FarmersDelightTaintedIntegration.isLoaded()) {
            guard("Farmers Delight", () -> FarmersDelightCatalysts.register(registration));
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
