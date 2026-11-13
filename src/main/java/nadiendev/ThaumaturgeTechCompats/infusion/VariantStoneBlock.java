package nadiendev.ThaumaturgeTechCompats.infusion;

import net.minecraft.world.level.block.Block;

public class VariantStoneBlock extends Block {
    private final MatrixVariant variant;
    private final StoneKind kind;

    public VariantStoneBlock(Properties properties, MatrixVariant variant, StoneKind kind) {
        super(properties);
        this.variant = variant;
        this.kind = kind;
    }

    public MatrixVariant variant() {
        return variant;
    }

    public StoneKind kind() {
        return kind;
    }
}
