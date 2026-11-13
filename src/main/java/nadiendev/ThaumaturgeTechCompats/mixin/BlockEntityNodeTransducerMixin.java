package nadiendev.ThaumaturgeTechCompats.mixin;

import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNodeTransducer;
import nadiendev.ThaumaturgeTechCompats.device.DeviceConfig;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityNodeTransducer.class)
public abstract class BlockEntityNodeTransducerMixin {
    @Shadow
    private int count;

    @Unique
    private int avaritia$countBefore;

    @Inject(method = "charge", at = @At("HEAD"), require = 0)
    private void avaritia$rememberCount(BlockEntityNode node, boolean powered, boolean stabilized, RandomSource random, CallbackInfo ci) {
        this.avaritia$countBefore = this.count;
    }

    @Inject(method = "charge", at = @At("TAIL"), require = 0)
    private void avaritia$tieredCharge(BlockEntityNode node, boolean powered, boolean stabilized, RandomSource random, CallbackInfo ci) {
        if (!DeviceConfig.transducersEnabled()) {
            return;
        }
        DeviceTier tier = TransducerIntegration.tierOf(((BlockEntity) (Object) this).getBlockState().getBlock());
        if (tier == null) {
            return;
        }
        if (this.count > this.avaritia$countBefore) {
            this.count = Math.min(BlockEntityNodeTransducer.CHARGE_TARGET, this.count + DeviceConfig.chargePerTick(tier) - 1);
        } else if (this.count < this.avaritia$countBefore && random.nextInt(DeviceConfig.decayInterval(tier)) != 0) {
            this.count = this.avaritia$countBefore;
        }
    }
}
