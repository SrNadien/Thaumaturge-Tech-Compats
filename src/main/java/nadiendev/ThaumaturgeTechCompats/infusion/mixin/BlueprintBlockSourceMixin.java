package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.api.recipe.BlueprintSource;
import com.leclowndu93150.thaumaturge.registry.TTBlocks;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionVariants;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlueprintSource.BlockSource.class)
public abstract class BlueprintBlockSourceMixin {
    @Inject(method = "matches", at = @At("RETURN"), cancellable = true, require = 0)
    private void thaumaturgetechcompats$acceptMatrixVariants(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() || InfusionVariants.matrixOf(state) == null) {
            return;
        }
        BlueprintSource.BlockSource self = (BlueprintSource.BlockSource) (Object) this;
        if (self.block() == TTBlocks.INFUSION_MATRIX.get()) {
            cir.setReturnValue(true);
        }
    }
}
