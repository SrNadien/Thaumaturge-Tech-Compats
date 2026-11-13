package nadiendev.ThaumaturgeTechCompats.jei.catalysts;

import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalConfig;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalTier;
import nadiendev.voidminersremastered.server.recipe.MinerRecipe;

final class VoidMinersCatalysts {
    private VoidMinersCatalysts() {
    }

    static void register(IRecipeCatalystRegistration registration) {
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            if (VoidMinersMagicalIntegration.minerTierEnabled(tier)) {
                IRecipeType<MinerRecipe> type = IRecipeType.create(VoidMinersMagicalIntegration.MOD_ID,
                        "miner/tier" + VoidMinersMagicalConfig.minerTier(tier) + "_miner", MinerRecipe.class);
                CatalystSupport.add(registration, tier.minerBlock(), type);
            }
        }
    }
}
