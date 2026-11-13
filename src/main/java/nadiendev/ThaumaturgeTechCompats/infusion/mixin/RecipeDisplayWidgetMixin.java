package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.api.recipe.IInfusionRecipe;
import com.leclowndu93150.thaumaturge.client.render.research.RecipeDisplayWidget;
import nadiendev.ThaumaturgeTechCompats.infusion.client.InfusionInstabilityDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeDisplayWidget.class)
public abstract class RecipeDisplayWidgetMixin {
    @Inject(method = "drawInfusionPage", at = @At("HEAD"), require = 0)
    private static void thaumaturgetechcompats$beginPage(GuiGraphics graphics, int x, int y, IInfusionRecipe recipe, CallbackInfo ci) {
        InfusionInstabilityDisplay.begin(recipe);
    }

    @Inject(method = "drawInfusionPage", at = @At("RETURN"), require = 0)
    private static void thaumaturgetechcompats$endPage(GuiGraphics graphics, int x, int y, IInfusionRecipe recipe, CallbackInfo ci) {
        InfusionInstabilityDisplay.end();
    }

    @ModifyArg(method = "drawInfusionPage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;append(Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;"),
            index = 0, require = 0)
    private static Component thaumaturgetechcompats$tierLabel(Component level) {
        Component name = InfusionInstabilityDisplay.name();
        return name == null ? level : name;
    }
}
