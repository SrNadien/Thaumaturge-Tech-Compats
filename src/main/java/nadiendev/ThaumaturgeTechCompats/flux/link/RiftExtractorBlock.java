package nadiendev.ThaumaturgeTechCompats.flux.link;

import com.mojang.serialization.MapCodec;
import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Locale;

public class RiftExtractorBlock extends BaseEntityBlock {
    public static final MapCodec<RiftExtractorBlock> CODEC = simpleCodec(RiftExtractorBlock::new);

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);

    public RiftExtractorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RiftExtractorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(type, LinkRegistry.EXTRACTOR_ENTITY.get(), RiftExtractorBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof RiftExtractorBlockEntity extractor) {
            if (level instanceof ServerLevel server) {
                report(server, player, extractor);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private void report(ServerLevel level, Player player, RiftExtractorBlockEntity extractor) {
        boolean found = extractor.nearestRift(level) != null;
        player.displayClientMessage(getName().withStyle(ChatFormatting.LIGHT_PURPLE), false);
        player.displayClientMessage(Component.translatable(LinkTranslations.EXTRACTOR_FLUX,
                String.format(Locale.ROOT, "%,d", extractor.storage().stored()),
                String.format(Locale.ROOT, "%,d", extractor.storage().capacity())).withStyle(ChatFormatting.GRAY), false);
        player.displayClientMessage(Component.translatable(LinkTranslations.EXTRACTOR_RIFT,
                Component.translatable(found ? LinkTranslations.EXTRACTOR_RIFT_FOUND : LinkTranslations.EXTRACTOR_RIFT_MISSING)
                        .withStyle(found ? ChatFormatting.GREEN : ChatFormatting.RED)).withStyle(ChatFormatting.GRAY), false);
        player.displayClientMessage(Component.translatable(LinkTranslations.EXTRACTOR_RATE,
                String.format(Locale.ROOT, "%,d", FluxConfig.riftFluxPerSize()),
                FluxConfig.riftExtractInterval(),
                FluxConfig.riftExtractRadius()).withStyle(ChatFormatting.GRAY), false);
    }
}
