package nadiendev.ThaumaturgeTechCompats.braincharger.mixin;

import com.leclowndu93150.thaumaturge.content.essentia.thaumatorium.work.BrainBoxScanner;
import nadiendev.ThaumaturgeTechCompats.braincharger.BrainBoxBonus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BrainBoxScanner.class, remap = false)
public abstract class BrainBoxScannerMixin {
    @Inject(method = "count", at = @At("RETURN"), cancellable = true)
    private void thaumaturgetechcompats$countAvaritiaBoxes(ServerLevel level, BlockPos pos, Direction front, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValueI() + BrainBoxBonus.extraBoxes(level, pos, front));
    }
}
