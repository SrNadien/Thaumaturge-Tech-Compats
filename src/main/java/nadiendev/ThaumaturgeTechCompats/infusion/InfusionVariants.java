package nadiendev.ThaumaturgeTechCompats.infusion;

import net.minecraft.world.level.block.state.BlockState;

public final class InfusionVariants {
    private InfusionVariants() {
    }

    public static MatrixVariant matrixOf(BlockState state) {
        return state.getBlock() instanceof VariantMatrixBlock block ? block.variant() : null;
    }

    public static VariantStoneBlock stoneOf(BlockState state) {
        return state.getBlock() instanceof VariantStoneBlock block ? block : null;
    }

    public static AltarPiece altarOf(BlockState state) {
        return InfusionRegistry.altarPiece(state.getBlock());
    }
}
