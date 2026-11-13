package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.mixin;

import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.MagicalUneartherFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockEntityType.class, remap = false)
public abstract class BlockEntityTypeMixin<T extends BlockEntity> {
    @SuppressWarnings("unchecked")
    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private void thaumaturgetechcompats$magicalUnearther(BlockPos pos, BlockState state, CallbackInfoReturnable<T> cir) {
        if (state.getBlock() instanceof MagicalUneartherFactory factory) {
            cir.setReturnValue((T) factory.createMagicalEntity(pos, state));
        }
    }
}
