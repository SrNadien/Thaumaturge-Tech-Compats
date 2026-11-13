package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.content.infusion.InfusionInput;
import com.leclowndu93150.thaumaturge.content.infusion.InfusionRecipe;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionCraftingContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InfusionRecipe.class)
public abstract class InfusionRecipeMixin {
    @Inject(method = "matches(Lcom/leclowndu93150/thaumaturge/content/infusion/InfusionInput;Lnet/minecraft/world/level/Level;)Z",
            at = @At("RETURN"), cancellable = true, require = 0)
    private void thaumaturgetechcompats$requiredMatrix(InfusionInput input, Level level, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && !InfusionCraftingContext.allows(((InfusionRecipe) (Object) this).resultItem().getItem())) {
            cir.setReturnValue(false);
        }
    }
}
