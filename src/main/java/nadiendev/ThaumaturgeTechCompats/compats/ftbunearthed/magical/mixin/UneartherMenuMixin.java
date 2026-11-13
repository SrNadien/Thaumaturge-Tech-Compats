package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.mixin;

import dev.ftb.mods.ftbunearthed.menu.UneartherMenu;
import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.MagicalUneartherGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = UneartherMenu.class, remap = false)
public abstract class UneartherMenuMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void thaumaturgetechcompats$noFoodSlot(CallbackInfo ci) {
        MagicalUneartherGui.disableFoodSlot((UneartherMenu) (Object) this);
    }
}
