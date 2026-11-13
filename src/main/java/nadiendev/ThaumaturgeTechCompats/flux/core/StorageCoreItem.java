package nadiendev.ThaumaturgeTechCompats.flux.core;

import nadiendev.ThaumaturgeTechCompats.flux.FluxComponents;
import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Consumer;

public class StorageCoreItem extends BlockItem {
    private final FluxTier tier;

    public StorageCoreItem(Block block, Properties properties, FluxTier tier) {
        super(block, properties);
        this.tier = tier;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(super.getName(stack).getString()).withColor(tier.color());
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(CoreTranslations.TOOLTIP_CORE_CAPACITY, CoreTooltip.number(FluxConfig.coreCapacity(tier))).withStyle(ChatFormatting.GRAY));
        long stored = FluxComponents.stored(stack);
        if (stored > 0L) {
            tooltip.accept(Component.translatable(CoreTranslations.TOOLTIP_CORE_STORED, CoreTooltip.number(stored)).withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        tooltip.accept(Component.translatable(CoreTranslations.TOOLTIP_CORE_STRUCTURE).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
