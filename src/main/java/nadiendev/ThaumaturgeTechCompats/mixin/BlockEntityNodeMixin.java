package nadiendev.ThaumaturgeTechCompats.mixin;

import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import nadiendev.ThaumaturgeTechCompats.device.DeviceConfig;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerTier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityNode.class)
public abstract class BlockEntityNodeMixin {
    private static final int ADVANCED_LOCK = 2;
    private static final int ADVANCED_FEEDING_FACTOR = 20;

    @Shadow
    private int lock;

    @Inject(method = "checkLock", at = @At("TAIL"), require = 0)
    private void avaritia$applyStabilizerTier(ServerLevel level, BlockPos pos, CallbackInfo ci) {
        if (this.lock != ADVANCED_LOCK || !DeviceConfig.stabilizersEnabled()) {
            return;
        }
        StabilizerTier tier = StabilizerIntegration.tierOf(level.getBlockState(pos.below()).getBlock());
        if (tier != null) {
            this.lock = tier.lockLevel();
        }
    }

    @Inject(method = "feedingIntervalFactor", at = @At("HEAD"), cancellable = true, require = 0)
    private void avaritia$tieredFeeding(CallbackInfoReturnable<Integer> cir) {
        DeviceTier tier = DeviceTier.byLock(this.lock);
        if (tier != null) {
            cir.setReturnValue(DeviceConfig.stabilizersEnabled() ? DeviceConfig.feedingFactor(tier) : ADVANCED_FEEDING_FACTOR);
        }
    }
}
