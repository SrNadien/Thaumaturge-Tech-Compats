package nadiendev.ThaumaturgeTechCompats.infusion;

import com.leclowndu93150.thaumaturge.content.infusion.BlockEntityInfusionMatrix;
import com.leclowndu93150.thaumaturge.registry.TTBlockEntities;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VariantMatrixBlock extends BaseEntityBlock {
    public static final MapCodec<VariantMatrixBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(),
            MatrixVariant.CODEC.fieldOf("variant").forGetter(VariantMatrixBlock::variant)
    ).apply(instance, VariantMatrixBlock::new));

    private static final VoxelShape SHAPE = box(0.8D, 0.8D, 0.8D, 15.2D, 15.2D, 15.2D);

    private final MatrixVariant variant;

    public VariantMatrixBlock(Properties properties, MatrixVariant variant) {
        super(properties);
        this.variant = variant;
    }

    public MatrixVariant variant() {
        return variant;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityInfusionMatrix(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        BlockEntityTicker<BlockEntityInfusionMatrix> ticker = level.isClientSide()
                ? BlockEntityInfusionMatrix::clientTick
                : BlockEntityInfusionMatrix::serverTick;
        return createTickerHelper(type, TTBlockEntities.INFUSION_MATRIX.get(), ticker);
    }
}
