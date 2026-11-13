package nadiendev.ThaumaturgeTechCompats.infusion.jei;

import com.leclowndu93150.thaumaturge.compat.jei.category.InfusionCategory;
import com.leclowndu93150.thaumaturge.content.infusion.InfusionRecipe;
import com.leclowndu93150.thaumaturge.registry.TTBlocks;
import com.leclowndu93150.thaumaturge.registry.TTRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.infusion.client.InfusionTierTooltips;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionIntegration;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionRegistry;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionTiers;
import nadiendev.ThaumaturgeTechCompats.infusion.MatrixVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JeiPlugin
public class InfusionJeiPlugin implements IModPlugin {
    private static final Identifier ID = ThaumaturgeTechCompats.id("infusion");
    private static final Map<Block, IRecipeType<RecipeHolder<InfusionRecipe>>> TYPES = new LinkedHashMap<>();
    private static final List<RecipeHolder<InfusionRecipe>> MOVED = new ArrayList<>();

    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        TYPES.clear();
        if (!InfusionIntegration.matricesEnabled() && !InfusionIntegration.stonesEnabled() && !InfusionIntegration.altarEnabled()) {
            return;
        }
        addCategory(registration, "variant_infusion_basic", TTBlocks.INFUSION_MATRIX.get());
        for (MatrixVariant variant : MatrixVariant.values()) {
            Block matrix = InfusionRegistry.ownMatrix(variant);
            if (matrix != null) {
                addCategory(registration, "variant_infusion_" + variant.id(), matrix);
            }
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        for (Map.Entry<Block, IRecipeType<RecipeHolder<InfusionRecipe>>> entry : TYPES.entrySet()) {
            registration.addRecipeCatalysts(entry.getValue(), entry.getKey().asItem());
        }
        if (!InfusionIntegration.matricesEnabled()) {
            return;
        }
        ItemLike[] matrices = InfusionRegistry.matrixItems().toArray(ItemLike[]::new);
        registration.addRecipeCatalysts(InfusionCategory.ENCHANTMENT_RECIPE_TYPE, matrices);
        registration.addRecipeCatalysts(InfusionCategory.RUNIC_RECIPE_TYPE, matrices);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        MOVED.clear();
        Level level = Minecraft.getInstance().level;
        if (level == null || TYPES.isEmpty()) {
            return;
        }
        Map<IRecipeType<RecipeHolder<InfusionRecipe>>, List<RecipeHolder<InfusionRecipe>>> grouped = new LinkedHashMap<>();
        for (RecipeHolder<InfusionRecipe> holder : InfusionTierTooltips.recipes().byType(TTRecipeTypes.INFUSION.get())) {
            Block required = requiredMatrix(holder.value().resultItem().getItem());
            IRecipeType<RecipeHolder<InfusionRecipe>> type = required == null ? null : TYPES.get(required);
            if (type != null) {
                grouped.computeIfAbsent(type, key -> new ArrayList<>()).add(holder);
                MOVED.add(holder);
            }
        }
        grouped.forEach(registration::addRecipes);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        if (!MOVED.isEmpty()) {
            runtime.getRecipeManager().hideRecipes(InfusionCategory.RECIPE_TYPE, new ArrayList<>(MOVED));
        }
    }

    private static Block requiredMatrix(Item result) {
        MatrixVariant variant = InfusionRegistry.resultVariant(result);
        return variant == null ? InfusionTiers.requiredMatrix(result) : InfusionRegistry.requiredMatrix(variant);
    }

    private static void addCategory(IRecipeCategoryRegistration registration, String name, Block matrix) {
        IRecipeType<RecipeHolder<InfusionRecipe>> type = IRecipeType.create(ThaumaturgeTechCompats.MOD_ID, name, holderClass());
        TYPES.put(matrix, type);
        registration.addRecipeCategories(new VariantInfusionCategory(registration.getJeiHelpers().getGuiHelper(), type, matrix));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Class<? extends RecipeHolder<InfusionRecipe>> holderClass() {
        return (Class) RecipeHolder.class;
    }
}
