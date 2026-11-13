package nadiendev.ThaumaturgeTechCompats.flux.link;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Locale;

public class TransmissionLinkerItem extends Item {
    public TransmissionLinkerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        if (player == null || !(level.getBlockEntity(pos) instanceof TransmissionLinkBlockEntity link)) {
            return InteractionResult.PASS;
        }
        if (!(level instanceof ServerLevel server)) {
            return InteractionResult.sidedSuccess(true);
        }
        ItemStack stack = context.getItemInHand();
        if (!CompatToggles.enabled(link.tier().toggle())) {
            message(player, LinkTranslations.LINKER_DISABLED);
        } else if (player.isShiftKeyDown()) {
            disconnect(player, link);
        } else {
            connect(server, player, stack, link, pos);
        }
        return InteractionResult.sidedSuccess(false);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && stack.has(LinkComponents.SELECTED.get())) {
            if (!level.isClientSide) {
                stack.remove(LinkComponents.SELECTED.get());
                message(player, LinkTranslations.LINKER_CLEARED);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.has(LinkComponents.SELECTED.get());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        GlobalPos selected = stack.get(LinkComponents.SELECTED.get());
        if (selected != null) {
            tooltip.add(Component.translatable(LinkTranslations.LINKER_TOOLTIP_SELECTED, selected.pos().toShortString()).withStyle(ChatFormatting.AQUA));
        }
        tooltip.add(Component.translatable(LinkTranslations.LINKER_TOOLTIP_USE).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(LinkTranslations.LINKER_TOOLTIP_SNEAK).withStyle(ChatFormatting.DARK_GRAY));
    }

    private static void connect(ServerLevel level, Player player, ItemStack stack, TransmissionLinkBlockEntity link, BlockPos pos) {
        GlobalPos here = GlobalPos.of(level.dimension(), pos);
        GlobalPos selected = stack.get(LinkComponents.SELECTED.get());
        if (selected == null) {
            select(stack, here);
            message(player, LinkTranslations.LINKER_SELECTED, pos.toShortString());
            return;
        }
        if (selected.equals(here)) {
            stack.remove(LinkComponents.SELECTED.get());
            message(player, LinkTranslations.LINKER_DESELECTED);
            return;
        }
        if (!selected.dimension().equals(level.dimension())) {
            select(stack, here);
            message(player, LinkTranslations.LINKER_OTHER_DIMENSION, pos.toShortString());
            return;
        }
        BlockPos otherPos = selected.pos();
        if (!level.hasChunkAt(otherPos) || !(level.getBlockEntity(otherPos) instanceof TransmissionLinkBlockEntity other)) {
            select(stack, here);
            message(player, LinkTranslations.LINKER_LOST, pos.toShortString());
            return;
        }
        if (!link.inRange(other)) {
            message(player, LinkTranslations.LINKER_OUT_OF_RANGE,
                    String.format(Locale.ROOT, "%.1f", Math.sqrt(pos.distSqr(otherPos))), link.range(other));
            return;
        }
        TransmissionLinkBlockEntity.pair(other, link);
        stack.remove(LinkComponents.SELECTED.get());
        message(player, LinkTranslations.LINKER_LINKED, otherPos.toShortString());
    }

    private static void disconnect(Player player, TransmissionLinkBlockEntity link) {
        if (link.partner() == null) {
            message(player, LinkTranslations.LINKER_NOT_LINKED);
            return;
        }
        link.breakLink();
        message(player, LinkTranslations.LINKER_UNLINKED);
    }

    private static void select(ItemStack stack, GlobalPos pos) {
        stack.set(LinkComponents.SELECTED.get(), pos);
    }

    private static void message(Player player, String key, Object... arguments) {
        player.displayClientMessage(Component.translatable(key, arguments), true);
    }
}
