package nadiendev.ThaumaturgeTechCompats.flux.core;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Consumer;

public class CapacityCellItem extends Item {
    private final FluxTier tier;

    public CapacityCellItem(Properties properties, FluxTier tier) {
        super(properties);
        this.tier = tier;
    }

    public FluxTier tier() {
        return tier;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(super.getName(stack).getString()).withColor(tier.color());
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(CoreTranslations.TOOLTIP_CELL_CAPACITY, CoreTooltip.number(CoreConfig.cellCapacity(tier))).withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(CoreTranslations.TOOLTIP_CELL_PLACE, CoreConfig.cellRadius()).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
