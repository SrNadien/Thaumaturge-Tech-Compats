package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.content.infusion.InstabilityEvents;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionOutcomes;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionVariants;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InstabilityEvents.class)
public abstract class InstabilityEventsMixin {
    @Inject(method = "trigger(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/List;F)V", at = @At("HEAD"), require = 0)
    private static void thaumaturgetechcompats$beginTrigger(ServerLevel level, BlockPos pos, List<BlockPos> pedestals, float stability, CallbackInfo ci) {
        InfusionOutcomes.begin(InfusionVariants.matrixOf(level.getBlockState(pos)));
    }

    @Inject(method = "trigger(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/List;F)V", at = @At("RETURN"), require = 0)
    private static void thaumaturgetechcompats$endTrigger(ServerLevel level, BlockPos pos, List<BlockPos> pedestals, float stability, CallbackInfo ci) {
        InfusionOutcomes.end();
    }
}
