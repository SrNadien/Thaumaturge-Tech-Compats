package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.mixin;

import dev.ftb.mods.ftbunearthed.client.UneartherScreen;
import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.MagicalUneartherGui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = UneartherScreen.class, remap = false)
public abstract class UneartherScreenMixin {
    @Inject(method = "renderBg", at = @At("RETURN"))
    private void thaumaturgetechcompats$coverFood(GuiGraphics graphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        if (MagicalUneartherGui.isMagical(screen.getMenu())) {
            MagicalUneartherGui.coverFood(graphics, screen.getGuiLeft(), screen.getGuiTop());
        }
    }

    @Inject(method = "renderTooltip", at = @At("HEAD"), cancellable = true)
    private void thaumaturgetechcompats$noFoodTooltip(GuiGraphics graphics, int mouseX, int mouseY, CallbackInfo ci) {
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        if (MagicalUneartherGui.isMagical(screen.getMenu())
                && MagicalUneartherGui.overFoodBar(screen.getGuiLeft(), screen.getGuiTop(), mouseX, mouseY)) {
            ci.cancel();
        }
    }
}
