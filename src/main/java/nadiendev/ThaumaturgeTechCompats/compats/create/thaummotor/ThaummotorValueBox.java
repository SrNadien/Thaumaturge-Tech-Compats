package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ThaummotorValueBox extends ValueBoxTransform.Sided {
    @Override
    protected Vec3 getSouthLocation() {
        return VecHelper.voxelSpace(8.0D, 8.0D, 12.5D);
    }

    @Override
    public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(ThaummotorBlock.FACING);
        return super.getLocalOffset(level, pos, state).add(Vec3.atLowerCornerOf(facing.getNormal()).scale(-0.0625D));
    }

    @Override
    public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack stack) {
        super.rotate(level, pos, state, stack);
        Direction facing = state.getValue(ThaummotorBlock.FACING);
        if (facing.getAxis() == Direction.Axis.Y || getSide() != Direction.UP) {
            return;
        }
        TransformStack.of(stack).rotateZDegrees(-AngleHelper.horizontalAngle(facing) + 180.0F);
    }

    @Override
    protected boolean isSideActive(BlockState state, Direction direction) {
        Direction facing = state.getValue(ThaummotorBlock.FACING);
        if (facing.getAxis() != Direction.Axis.Y && direction == Direction.DOWN) {
            return false;
        }
        return direction.getAxis() != facing.getAxis();
    }
}
