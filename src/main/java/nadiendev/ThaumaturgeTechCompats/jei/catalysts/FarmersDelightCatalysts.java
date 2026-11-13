package nadiendev.ThaumaturgeTechCompats.jei.catalysts;

import mezz.jei.api.registration.IRecipeCatalystRegistration;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight.FarmersDelightKnife;
import nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight.FarmersDelightTaintedIntegration;
import nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight.FluxKnife;
import vectorwing.farmersdelight.integration.jei.FDRecipeTypes;

final class FarmersDelightCatalysts {
    private FarmersDelightCatalysts() {
    }

    static void register(IRecipeCatalystRegistration registration) {
        if (FarmersDelightTaintedIntegration.knivesActive() && CompatToggles.enabled(FarmersDelightTaintedIntegration.TOGGLE_KNIVES)) {
            for (FarmersDelightKnife knife : FarmersDelightKnife.values()) {
                CatalystSupport.add(registration, knife.id(), FDRecipeTypes.CUTTING);
            }
        }
        if (FarmersDelightTaintedIntegration.fluxKnifeActive() && CompatToggles.enabled(FarmersDelightTaintedIntegration.TOGGLE_FLUX_KNIFE)) {
            CatalystSupport.add(registration, FluxKnife.ID, FDRecipeTypes.CUTTING);
        }
    }
}
