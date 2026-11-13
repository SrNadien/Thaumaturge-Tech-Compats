package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.api.recipe.IInfusionRecipe;
import com.leclowndu93150.thaumaturge.compat.jei.category.InfusionCategory;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import nadiendev.ThaumaturgeTechCompats.infusion.client.InfusionInstabilityDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InfusionCategory.class)
public abstract class InfusionCategoryMixin {
    private static final String DRAW = "draw(Lnet/minecraft/world/item/crafting/RecipeHolder;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lnet/minecraft/client/gui/GuiGraphicsExtractor;DD)V";
    private static final String TOOLTIP = "getTooltip(Lmezz/jei/api/gui/builder/ITooltipBuilder;Lnet/minecraft/world/item/crafting/RecipeHolder;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;DD)V";
    private static final String LABEL = "gui.thaumaturge.infusion.instability";
    private static final int LABEL_CENTER_X = 73;
    private static final int LABEL_Y = 158;
    private static final int LABEL_HEIGHT = 9;

    @Inject(method = DRAW, at = @At("HEAD"), require = 0)
    private void thaumaturgetechcompats$beginDraw(RecipeHolder<?> holder, IRecipeSlotsView slots, GuiGraphicsExtractor graphics, double mouseX, double mouseY, CallbackInfo ci) {
        InfusionInstabilityDisplay.begin((IInfusionRecipe) holder.value());
    }

    @Inject(method = DRAW, at = @At("RETURN"), require = 0)
    private void thaumaturgetechcompats$endDraw(RecipeHolder<?> holder, IRecipeSlotsView slots, GuiGraphicsExtractor graphics, double mouseX, double mouseY, CallbackInfo ci) {
        InfusionInstabilityDisplay.end();
    }

    @ModifyArg(method = DRAW,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"),
            index = 1, require = 0)
    private Object[] thaumaturgetechcompats$tierLabel(Object[] arguments) {
        Component name = InfusionInstabilityDisplay.name();
        return name == null ? arguments : new Object[]{name};
    }

    @Inject(method = TOOLTIP, at = @At("RETURN"), require = 0)
    private void thaumaturgetechcompats$instabilityTooltip(ITooltipBuilder builder, RecipeHolder<?> holder, IRecipeSlotsView slots, double mouseX, double mouseY, CallbackInfo ci) {
        IInfusionRecipe recipe = (IInfusionRecipe) holder.value();
        List<Component> lines = InfusionInstabilityDisplay.tooltip(recipe);
        if (lines.isEmpty()) {
            return;
        }
        Component name = InfusionInstabilityDisplay.tierName(recipe);
        Component label = Component.translatable(LABEL, name);
        int half = Minecraft.getInstance().font.width(label) / 2;
        if (mouseX >= LABEL_CENTER_X - half && mouseX <= LABEL_CENTER_X + half && mouseY >= LABEL_Y && mouseY < LABEL_Y + LABEL_HEIGHT) {
            builder.addAll(lines);
        }
    }
}
