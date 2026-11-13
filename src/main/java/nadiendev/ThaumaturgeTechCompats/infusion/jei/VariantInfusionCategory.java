package nadiendev.ThaumaturgeTechCompats.infusion.jei;

import com.leclowndu93150.thaumaturge.compat.jei.category.InfusionCategory;
import com.leclowndu93150.thaumaturge.content.infusion.InfusionRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionTranslations;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;

public class VariantInfusionCategory implements IRecipeCategory<RecipeHolder<InfusionRecipe>> {
    private static final String BASE_TITLE = "gui.thaumaturge.recipe_type.infusion";

    private final InfusionCategory<InfusionRecipe> delegate;
    private final RecipeType<RecipeHolder<InfusionRecipe>> type;
    private final Component title;
    private final IDrawable icon;

    public VariantInfusionCategory(IGuiHelper helper, RecipeType<RecipeHolder<InfusionRecipe>> type, Block matrix) {
        this.delegate = new InfusionCategory<>(helper, InfusionCategory.RECIPE_TYPE, BASE_TITLE);
        this.type = type;
        this.title = InfusionTranslations.jeiTitle(matrix);
        this.icon = helper.createDrawableItemStack(new ItemStack(matrix.asItem()));
    }

    @Override
    public RecipeType<RecipeHolder<InfusionRecipe>> getRecipeType() {
        return type;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return delegate.getWidth();
    }

    @Override
    public int getHeight() {
        return delegate.getHeight();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<InfusionRecipe> recipe, IFocusGroup focuses) {
        delegate.setRecipe(builder, recipe, focuses);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, RecipeHolder<InfusionRecipe> recipe, IRecipeSlotsView slots, double mouseX, double mouseY) {
        delegate.getTooltip(tooltip, recipe, slots, mouseX, mouseY);
    }

    @Override
    public void draw(RecipeHolder<InfusionRecipe> recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        delegate.draw(recipe, slots, graphics, mouseX, mouseY);
    }
}
