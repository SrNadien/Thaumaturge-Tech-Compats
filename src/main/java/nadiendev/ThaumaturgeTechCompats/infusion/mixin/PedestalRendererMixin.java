package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.client.render.blockentity.PedestalRenderer;
import com.leclowndu93150.thaumaturge.content.infusion.BlockEntityPedestal;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionVariants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PedestalRenderer.class)
public abstract class PedestalRendererMixin {
    @Inject(method = "pedestalHeight", at = @At("HEAD"), cancellable = true, require = 0)
    private static void thaumaturgetechcompats$tallPedestal(BlockEntityPedestal pedestal, CallbackInfoReturnable<Float> cir) {
        if (InfusionVariants.altarOf(pedestal.getBlockState()) != null) {
            cir.setReturnValue(0.75F);
        }
    }
}
