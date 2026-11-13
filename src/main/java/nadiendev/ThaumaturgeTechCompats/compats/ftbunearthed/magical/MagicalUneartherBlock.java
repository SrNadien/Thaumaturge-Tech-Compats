package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import dev.ftb.mods.ftbunearthed.block.UneartherCoreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MagicalUneartherBlock extends UneartherCoreBlock implements MagicalUneartherFactory {
    private final MagicalUneartherTier tier;

    public MagicalUneartherBlock(Properties properties, MagicalUneartherTier tier) {
        super(properties);
        this.tier = tier;
    }

    public MagicalUneartherTier tier() {
        return tier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return createMagicalEntity(pos, state);
    }

    @Override
    public BlockEntity createMagicalEntity(BlockPos pos, BlockState state) {
        return new MagicalUneartherBlockEntity(pos, state);
    }
}
