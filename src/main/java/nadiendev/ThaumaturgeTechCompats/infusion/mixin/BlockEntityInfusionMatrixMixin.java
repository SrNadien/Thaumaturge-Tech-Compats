package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.content.infusion.BlockEntityInfusionMatrix;
import com.leclowndu93150.thaumaturge.content.infusion.InfusionCraftJob;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionConfig;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionCraftingContext;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionVariants;
import nadiendev.ThaumaturgeTechCompats.infusion.MatrixInstability;
import nadiendev.ThaumaturgeTechCompats.infusion.MatrixVariant;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityInfusionMatrix.class)
public abstract class BlockEntityInfusionMatrixMixin {
    @Shadow
    private InfusionCraftJob job;

    @Inject(method = "tryStartCraft", at = @At("HEAD"), require = 0)
    private void thaumaturgetechcompats$beginStart(ServerLevel level, Player player, CallbackInfoReturnable<Boolean> cir) {
        InfusionCraftingContext.begin(((BlockEntity) (Object) this).getBlockState());
    }

    @Inject(method = "tryStartCraft", at = @At("RETURN"), require = 0)
    private void thaumaturgetechcompats$endStart(ServerLevel level, Player player, CallbackInfoReturnable<Boolean> cir) {
        InfusionCraftingContext.end();
    }

    @Inject(method = "lossPerCycle", at = @At("RETURN"), cancellable = true, require = 0)
    private void thaumaturgetechcompats$variantLoss(CallbackInfoReturnable<Float> cir) {
        BlockEntity self = (BlockEntity) (Object) this;
        MatrixVariant variant = InfusionVariants.matrixOf(self.getBlockState());
        if (variant != null && self.getLevel() != null) {
            cir.setReturnValue(cir.getReturnValueF() * (float) MatrixInstability.lossMultiplier(variant, self.getLevel(), self.getBlockPos()));
        }
    }

    @ModifyArg(method = "drainEssentiaCycle",
            at = @At(value = "INVOKE", target = "Lcom/leclowndu93150/thaumaturge/content/infusion/InfusionCraftJob;setEssentia(Lcom/leclowndu93150/thaumaturge/api/aspect/AspectList;)V"),
            index = 0, require = 0)
    private AspectList thaumaturgetechcompats$variantEssentia(AspectList reduced) {
        MatrixVariant variant = InfusionVariants.matrixOf(((BlockEntity) (Object) this).getBlockState());
        if (variant == null || this.job == null) {
            return reduced;
        }
        int extra = InfusionConfig.integer(variant, "essentia_multiplier") - 1;
        if (extra <= 0) {
            return reduced;
        }
        AspectList result = reduced;
        for (AspectInstance before : this.job.essentia().entries()) {
            int left = result.amountOf(before.aspect());
            if (left < before.amount()) {
                int more = Math.min(extra, left);
                if (more > 0) {
                    result = result.reduce(before.aspect(), more);
                }
                break;
            }
        }
        return result;
    }
}
