package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.content.infusion.BlockPedestal;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionVariants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockPedestal.class)
public abstract class BlockPedestalMixin {
    @Shadow
    @Final
    private static VoxelShape ANCIENT_AND_ELDRITCH_SHAPE;

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true, require = 0)
    private void thaumaturgetechcompats$tallShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (InfusionVariants.altarOf(state) != null) {
            cir.setReturnValue(ANCIENT_AND_ELDRITCH_SHAPE);
        }
    }
}
