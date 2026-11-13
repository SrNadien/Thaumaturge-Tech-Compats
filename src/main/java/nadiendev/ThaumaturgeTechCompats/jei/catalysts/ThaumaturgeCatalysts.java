package nadiendev.ThaumaturgeTechCompats.jei.catalysts;

import com.leclowndu93150.thaumaturge.compat.jei.category.AspectFromStacksCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterTier;

final class ThaumaturgeCatalysts {
    static final String MOD_ID = "thaumaturge";

    private ThaumaturgeCatalysts() {
    }

    static void register(IRecipeCatalystRegistration registration) {
        for (SmelterTier tier : SmelterTier.values()) {
            CatalystSupport.add(registration, tier.blockName(), AspectFromStacksCategory.RECIPE_TYPE);
        }
        for (DeviceTier tier : DeviceTier.values()) {
            CatalystSupport.add(registration, FurnaceIntegration.blockName(tier), AspectFromStacksCategory.RECIPE_TYPE);
        }
    }
}
