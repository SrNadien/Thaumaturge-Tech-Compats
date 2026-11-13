package nadiendev.ThaumaturgeTechCompats.emc;

import com.leclowndu93150.thaumaturge.content.infusion.InfusionRecipe;
import com.leclowndu93150.thaumaturge.content.recipe.crucible.CrucibleRecipe;
import com.leclowndu93150.thaumaturge.content.recipe.workbench.ArcaneCraftingRecipe;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import moze_intel.projecte.api.mapper.collector.IMappingCollector;
import moze_intel.projecte.api.mapper.recipe.INSSFakeGroupManager;
import moze_intel.projecte.api.mapper.recipe.IRecipeTypeMapper;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import moze_intel.projecte.api.nss.NSSItem;
import moze_intel.projecte.api.nss.NormalizedSimpleStack;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RecipeTypeMapper(requiredMods = {"thaumaturge"})
public class EmcRecipeMapper implements IRecipeTypeMapper {
    private static final Set<ResourceLocation> TYPES = Set.of(
            ResourceLocation.fromNamespaceAndPath("thaumaturge", "arcane_workbench"),
            ResourceLocation.fromNamespaceAndPath("thaumaturge", "crucible"),
            ResourceLocation.fromNamespaceAndPath("thaumaturge", "infusion"));

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Thaumaturge recipes";
    }

    @Override
    public String getTranslationKey() {
        return EmcIntegration.RECIPE_MAPPER_KEY;
    }

    @Override
    public String getDescription() {
        return "Gives EMC to the results of the arcane workbench, crucible and infusion altar recipes of Thaumaturge from their item ingredients. Aspects, vis and instability add no EMC.";
    }

    @Override
    public boolean isAvailable() {
        return EmcIntegration.enabled();
    }

    @Override
    public boolean canHandle(RecipeType<?> recipeType) {
        if (!EmcIntegration.enabled()) {
            return false;
        }
        ResourceLocation key = BuiltInRegistries.RECIPE_TYPE.getKey(recipeType);
        return key != null && TYPES.contains(key);
    }

    @Override
    public boolean handleRecipe(IMappingCollector<NormalizedSimpleStack, Long> mapper, RecipeHolder<?> holder, RegistryAccess access, INSSFakeGroupManager groups) {
        Recipe<?> recipe = holder.value();
        List<Ingredient> ingredients = new ArrayList<>();
        ItemStack output;
        if (recipe instanceof ArcaneCraftingRecipe arcane) {
            ingredients.addAll(arcane.getIngredients());
            output = arcane.getResultItem(access);
        } else if (recipe instanceof CrucibleRecipe crucible) {
            ingredients.add(crucible.catalyst());
            output = crucible.getResultItem(access);
        } else if (recipe instanceof InfusionRecipe infusion) {
            ingredients.add(infusion.catalyst());
            ingredients.addAll(infusion.components());
            output = infusion.resultItem();
            if (rewritesCatalyst(infusion.catalyst(), output)) {
                return true;
            }
        } else {
            return false;
        }
        if (output.isEmpty()) {
            return false;
        }

        Object2IntMap<NormalizedSimpleStack> inputs = new Object2IntOpenHashMap<>();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.isEmpty()) {
                continue;
            }
            List<ItemStack> candidates = candidates(ingredient);
            if (candidates.isEmpty()) {
                return true;
            }
            if (candidates.size() == 1) {
                if (!addInput(inputs, candidates.getFirst())) {
                    return true;
                }
                continue;
            }
            Set<NormalizedSimpleStack> options = new LinkedHashSet<>();
            for (ItemStack candidate : candidates) {
                options.add(NSSItem.createItem(candidate));
            }
            inputs.mergeInt(groups.getOrCreateFakeGroup(options).dummy(), 1, Integer::sum);
        }
        if (inputs.isEmpty()) {
            return true;
        }
        mapper.addConversion(output.getCount(), NSSItem.createItem(output), inputs);
        return true;
    }

    private static boolean rewritesCatalyst(Ingredient catalyst, ItemStack output) {
        for (ItemStack stack : candidates(catalyst)) {
            if (stack.getItem() == output.getItem()) {
                return true;
            }
        }
        return false;
    }

    private static List<ItemStack> candidates(Ingredient ingredient) {
        List<ItemStack> result = new ArrayList<>();
        try {
            for (ItemStack stack : ingredient.getItems()) {
                if (!stack.isEmpty() && !stack.is(Items.BARRIER)) {
                    result.add(stack);
                }
            }
        } catch (RuntimeException exception) {
            return List.of();
        }
        return result;
    }

    private static boolean addInput(Object2IntMap<NormalizedSimpleStack> inputs, ItemStack stack) {
        try {
            ItemStack remainder = stack.getCraftingRemainingItem();
            if (!remainder.isEmpty()) {
                inputs.mergeInt(NSSItem.createItem(remainder), -1, Integer::sum);
            }
        } catch (RuntimeException exception) {
            return false;
        }
        inputs.mergeInt(NSSItem.createItem(stack), 1, Integer::sum);
        return true;
    }
}
