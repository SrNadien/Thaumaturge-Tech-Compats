package nadiendev.ThaumaturgeTechCompats.infusion;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class InfusionCraftingContext {
    private static final ThreadLocal<Block> MATRIX = new ThreadLocal<>();

    private InfusionCraftingContext() {
    }

    public static void begin(BlockState matrix) {
        MATRIX.set(matrix.getBlock());
    }

    public static void end() {
        MATRIX.remove();
    }

    public static boolean allows(Item result) {
        Block matrix = MATRIX.get();
        if (matrix == null) {
            return true;
        }
        MatrixVariant variant = InfusionRegistry.resultVariant(result);
        Block required = variant == null ? InfusionTiers.requiredMatrix(result) : InfusionRegistry.requiredMatrix(variant);
        return required == null || required == matrix;
    }
}
