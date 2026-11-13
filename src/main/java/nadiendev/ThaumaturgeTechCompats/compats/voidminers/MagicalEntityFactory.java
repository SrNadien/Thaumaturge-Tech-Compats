package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface MagicalEntityFactory {
    BlockEntity createMagicalEntity(BlockPos pos, BlockState state);
}
