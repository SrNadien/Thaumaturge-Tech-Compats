package nadiendev.ThaumaturgeTechCompats.braincharger;

import com.leclowndu93150.thaumaturge.content.essentia.thaumatorium.work.NeighbourRing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public final class BrainBoxBonus {
    private BrainBoxBonus() {
    }

    public static int extraBoxes(ServerLevel level, BlockPos pos, Direction front) {
        if (!BrainChargerConfig.boxesEnabled()) {
            return 0;
        }
        NeighbourRing ring = new NeighbourRing();
        int formulas = 0;
        for (int slot = 0; slot < ring.slots(); slot++) {
            if (!ring.aim(pos, front, slot) || !level.hasChunkAt(ring.pos())) {
                continue;
            }
            BlockState state = level.getBlockState(ring.pos());
            BoostTier tier = BrainChargerRegistry.boxTier(state.getBlock());
            if (tier != null && state.getValue(BlockStateProperties.FACING) == ring.side().getOpposite()) {
                formulas += BrainChargerConfig.formulas(tier);
            }
        }
        return (formulas + 1) / 2;
    }
}
