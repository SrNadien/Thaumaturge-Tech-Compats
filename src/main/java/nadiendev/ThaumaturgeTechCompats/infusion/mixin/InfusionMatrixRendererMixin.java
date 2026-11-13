package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.client.render.TTFlatRenderTypes;
import com.leclowndu93150.thaumaturge.client.render.blockentity.InfusionMatrixRenderer;
import com.leclowndu93150.thaumaturge.content.infusion.BlockEntityInfusionMatrix;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionVariants;
import nadiendev.ThaumaturgeTechCompats.infusion.MatrixVariant;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(InfusionMatrixRenderer.class)
public abstract class InfusionMatrixRendererMixin {
    private static final Map<Identifier, RenderType> thaumaturgetechcompats$glow = new HashMap<>();

    @Inject(method = "pickTexture", at = @At("HEAD"), cancellable = true, require = 0)
    private static void thaumaturgetechcompats$variantTexture(BlockEntityInfusionMatrix matrix, CallbackInfoReturnable<Identifier> cir) {
        MatrixVariant variant = InfusionVariants.matrixOf(matrix.getBlockState());
        if (variant != null) {
            cir.setReturnValue(variant.texture());
        }
    }

    @Inject(method = "glowTypeFor", at = @At("HEAD"), cancellable = true, require = 0)
    private static void thaumaturgetechcompats$variantGlow(Identifier texture, CallbackInfoReturnable<RenderType> cir) {
        for (MatrixVariant variant : MatrixVariant.values()) {
            if (variant.texture().equals(texture)) {
                cir.setReturnValue(thaumaturgetechcompats$glow.computeIfAbsent(texture, TTFlatRenderTypes::entityAdditiveFlat));
                return;
            }
        }
    }
}
