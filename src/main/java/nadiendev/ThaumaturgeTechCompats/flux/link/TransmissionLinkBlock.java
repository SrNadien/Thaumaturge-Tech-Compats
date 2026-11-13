package nadiendev.ThaumaturgeTechCompats.flux.link;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

public class TransmissionLinkBlock extends BaseEntityBlock {
    public static final MapCodec<TransmissionLinkBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(),
            FluxTier.CODEC.fieldOf("tier").forGetter(TransmissionLinkBlock::tier)
    ).apply(instance, TransmissionLinkBlock::new));

    private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);

    private final FluxTier tier;

    public TransmissionLinkBlock(Properties properties, FluxTier tier) {
        super(properties);
        this.tier = tier;
    }

    public FluxTier tier() {
        return tier;
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
        return new TransmissionLinkBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(type, LinkRegistry.LINK_ENTITY.get(), TransmissionLinkBlockEntity::tick);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (stack.getItem() instanceof TransmissionLinkerItem) {
            return InteractionResult.PASS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof TransmissionLinkBlockEntity link) {
            if (level instanceof ServerLevel server) {
                report(server, player, link);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private void report(ServerLevel level, Player player, TransmissionLinkBlockEntity link) {
        TransmissionLinkBlockEntity.Status status = link.status(level);
        player.sendSystemMessage(getName().withStyle(Style.EMPTY.withColor(tier.color())));
        player.sendSystemMessage(Component.translatable(LinkTranslations.LINK_STATUS,
                Component.translatable(LinkTranslations.status(status)).withStyle(statusColor(status))).withStyle(ChatFormatting.GRAY));
        TransmissionLinkBlockEntity other = link.partnerEntity(level);
        if (link.partner() != null) {
            double distance = other == null ? Math.sqrt(link.getBlockPos().distSqr(link.partner())) : Math.sqrt(link.getBlockPos().distSqr(other.getBlockPos()));
            int range = other == null ? FluxConfig.linkRange(tier) : link.range(other);
            player.sendSystemMessage(Component.translatable(LinkTranslations.LINK_PARTNER,
                    link.partner().toShortString(), String.format(Locale.ROOT, "%.1f", distance), range).withStyle(ChatFormatting.GRAY));
        }
        player.sendSystemMessage(Component.translatable(LinkTranslations.LINK_FLUX,
                String.format(Locale.ROOT, "%,d", link.storage().stored()),
                String.format(Locale.ROOT, "%,d", link.storage().capacity())).withStyle(ChatFormatting.GRAY));
        player.sendSystemMessage(Component.translatable(LinkTranslations.LINK_RATE,
                String.format(Locale.ROOT, "%,d", FluxConfig.linkRate(tier))).withStyle(ChatFormatting.GRAY));
    }

    private static ChatFormatting statusColor(TransmissionLinkBlockEntity.Status status) {
        return switch (status) {
            case ACTIVE -> ChatFormatting.GREEN;
            case UNLINKED -> ChatFormatting.YELLOW;
            case PARTNER_UNLOADED, OUT_OF_RANGE, DISABLED -> ChatFormatting.RED;
        };
    }
}
