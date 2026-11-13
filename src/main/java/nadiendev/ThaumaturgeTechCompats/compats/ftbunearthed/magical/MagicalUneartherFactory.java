package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface MagicalUneartherFactory {
    BlockEntity createMagicalEntity(BlockPos pos, BlockState state);
}
