package nadiendev.ThaumaturgeTechCompats.compats.create.mixin;

import nadiendev.ThaumaturgeTechCompats.compats.create.boost.NodeBoost;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.BoostPauses;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.simibubi.create.foundation.blockEntity.SmartBlockEntity", remap = false)
public abstract class SmartBlockEntityMixin {
    @Shadow(remap = false)
    public abstract void tick();

    @Inject(method = "tick", at = @At("TAIL"), remap = false, require = 0)
    private void thaumaturgetechcompats$nodeBoost(CallbackInfo ci) {
        BlockEntity entity = (BlockEntity) (Object) this;
        int extra = NodeBoost.extraTicks(entity);
        if (extra <= 0 || !NodeBoost.enter()) {
            return;
        }
        try {
            for (int index = 0; index < extra && !entity.isRemoved(); index++) {
                if (BoostPauses.atSamplePoint(entity)) {
                    break;
                }
                this.tick();
            }
        } finally {
            NodeBoost.exit();
        }
    }
}
