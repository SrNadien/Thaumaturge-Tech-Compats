package nadiendev.ThaumaturgeTechCompats.jei.catalysts;

import dev.ftb.mods.ftbunearthed.integration.jei.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.MagicalUneartherIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.MagicalUneartherTier;

final class UneartherCatalysts {
    private UneartherCatalysts() {
    }

    static void register(IRecipeCatalystRegistration registration) {
        if (!MagicalUneartherIntegration.active()) {
            return;
        }
        for (MagicalUneartherTier tier : MagicalUneartherTier.values()) {
            if (MagicalUneartherIntegration.tierEnabled(tier)) {
                CatalystSupport.add(registration, tier.blockName(), RecipeTypes.UNEARTHER);
            }
        }
    }
}
