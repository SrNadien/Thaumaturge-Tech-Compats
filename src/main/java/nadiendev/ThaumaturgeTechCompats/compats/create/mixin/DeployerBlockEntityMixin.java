package nadiendev.ThaumaturgeTechCompats.compats.create.mixin;

import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CasingVariant;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity", remap = false)
public abstract class DeployerBlockEntityMixin {
    @Inject(method = "getMovementVector", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void thaumaturgetechcompats$variantVector(CallbackInfoReturnable<Vec3> cir) {
        BlockState state = ((BlockEntity) (Object) this).getBlockState();
        if (state.getBlock() instanceof CasingVariant && state.hasProperty(BlockStateProperties.FACING)) {
            cir.setReturnValue(Vec3.atLowerCornerOf(state.getValue(BlockStateProperties.FACING).getNormal()));
        }
    }
}
