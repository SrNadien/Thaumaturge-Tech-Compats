package nadiendev.ThaumaturgeTechCompats.emc;

import com.leclowndu93150.thaumaturge.content.infusion.InfusionRecipe;
import com.leclowndu93150.thaumaturge.content.recipe.crucible.CrucibleRecipe;
import com.leclowndu93150.thaumaturge.content.recipe.workbench.ArcaneShapedCraftingRecipe;
import com.leclowndu93150.thaumaturge.content.recipe.workbench.ArcaneShapelessCraftingRecipe;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import com.yaskulsky.equivox.api.mapper.collector.IMappingCollector;
import com.yaskulsky.equivox.api.mapper.recipe.INSSFakeGroupManager;
import com.yaskulsky.equivox.api.mapper.recipe.IRecipeTypeMapper;
import com.yaskulsky.equivox.api.mapper.recipe.RecipeTypeMapper;
import com.yaskulsky.equivox.api.nss.NSSItem;
import com.yaskulsky.equivox.api.nss.NormalizedSimpleStack;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
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
    private static final Set<Identifier> TYPES = Set.of(
            Identifier.fromNamespaceAndPath("thaumaturge", "arcane_workbench"),
            Identifier.fromNamespaceAndPath("thaumaturge", "crucible"),
            Identifier.fromNamespaceAndPath("thaumaturge", "infusion"));

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
        Identifier key = BuiltInRegistries.RECIPE_TYPE.getKey(recipeType);
        return key != null && TYPES.contains(key);
    }

    @Override
    public boolean handleRecipe(IMappingCollector<NormalizedSimpleStack, Long> mapper, RecipeHolder<?> holder, RegistryAccess access, INSSFakeGroupManager groups) {
        Recipe<?> recipe = holder.value();
        List<Ingredient> ingredients = new ArrayList<>();
        ItemStack output;
        if (recipe instanceof ArcaneShapedCraftingRecipe shaped) {
            shaped.getIngredients().forEach(ingredient -> ingredient.ifPresent(ingredients::add));
            output = shaped.result().create();
        } else if (recipe instanceof ArcaneShapelessCraftingRecipe shapeless) {
            ingredients.addAll(shapeless.ingredients());
            output = shapeless.result().create();
        } else if (recipe instanceof CrucibleRecipe crucible) {
            ingredients.add(crucible.catalyst());
            output = crucible.rawResult().create();
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
            List<Holder<Item>> candidates = candidates(ingredient);
            if (candidates.isEmpty()) {
                return true;
            }
            if (candidates.size() == 1) {
                addInput(inputs, candidates.getFirst());
                continue;
            }
            Set<NormalizedSimpleStack> options = new LinkedHashSet<>();
            for (Holder<Item> candidate : candidates) {
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
        for (Holder<Item> holder : candidates(catalyst)) {
            if (holder.value() == output.getItem()) {
                return true;
            }
        }
        return false;
    }

    private static List<Holder<Item>> candidates(Ingredient ingredient) {
        List<Holder<Item>> result = new ArrayList<>();
        try {
            ingredient.items().forEach(holder -> {
                if (holder.value() != Items.BARRIER && holder.value() != Items.AIR) {
                    result.add(holder);
                }
            });
        } catch (RuntimeException exception) {
            return List.of();
        }
        return result;
    }

    private static void addInput(Object2IntMap<NormalizedSimpleStack> inputs, Holder<Item> holder) {
        ItemStackTemplate remainder = holder.value().getCraftingRemainder();
        if (remainder != null) {
            inputs.mergeInt(NSSItem.createItem(remainder.typeHolder()), -remainder.count(), Integer::sum);
        }
        inputs.mergeInt(NSSItem.createItem(holder), 1, Integer::sum);
    }
}
