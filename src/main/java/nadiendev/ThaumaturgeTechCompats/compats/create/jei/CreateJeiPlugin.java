package nadiendev.ThaumaturgeTechCompats.compats.create.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CreateVariants;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.VariantKind;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Map;

@JeiPlugin
public class CreateJeiPlugin implements IModPlugin {
    private static final ResourceLocation ID = ThaumaturgeTechCompats.id("create");

    private static final Map<String, List<VariantKind>> CATEGORIES = Map.ofEntries(
            Map.entry("milling", List.of(VariantKind.MILLSTONE)),
            Map.entry("pressing", List.of(VariantKind.PRESS)),
            Map.entry("mixing", List.of(VariantKind.MIXER, VariantKind.BASIN)),
            Map.entry("automatic_shapeless", List.of(VariantKind.MIXER, VariantKind.BASIN)),
            Map.entry("automatic_brewing", List.of(VariantKind.MIXER, VariantKind.BASIN)),
            Map.entry("packing", List.of(VariantKind.PRESS, VariantKind.BASIN)),
            Map.entry("automatic_packing", List.of(VariantKind.PRESS, VariantKind.BASIN)),
            Map.entry("sawing", List.of(VariantKind.SAW)),
            Map.entry("block_cutting", List.of(VariantKind.SAW)),
            Map.entry("deploying", List.of(VariantKind.DEPLOYER, VariantKind.DEPOT)),
            Map.entry("spout_filling", List.of(VariantKind.SPOUT)),
            Map.entry("draining", List.of(VariantKind.ITEM_DRAIN)));

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (!CreateIntegration.isLoaded() || !CreateToggles.enabled(CreateToggles.JEI)) {
            return;
        }
        for (Map.Entry<String, List<VariantKind>> category : CATEGORIES.entrySet()) {
            RecipeType<?> type = RecipeType.<Recipe<?>>createRecipeHolderType(ResourceLocation.fromNamespaceAndPath(CreateIntegration.MOD_ID, category.getKey()));
            for (VariantKind kind : category.getValue()) {
                for (CasingTier tier : CasingTier.values()) {
                    if (CreateToggles.variant(kind, tier)) {
                        registration.addRecipeCatalyst(CreateVariants.item(kind, tier).get(), type);
                    }
                }
            }
        }
    }
}
