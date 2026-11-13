package nadiendev.ThaumaturgeTechCompats.flux.gear;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.text.NumberFormat;
import java.util.List;

public abstract class FluxGearItem extends Item implements IFluxItem {
    public static final String TOOLTIP_STORED = "tooltip.thaumaturgetechcompats.flux_gear.stored";
    public static final String MESSAGE_EMPTY = "message.thaumaturgetechcompats.flux_gear.empty";

    private static final int BAR_WIDTH = 13;
    private static final int BAR_COLOR = FluxTier.THAUMIUM.color();

    protected FluxGearItem(Properties properties) {
        super(properties);
    }

    public static String format(long value) {
        return NumberFormat.getIntegerInstance().format(value);
    }

    public ItemStack fullStack() {
        ItemStack stack = new ItemStack(this);
        insertFlux(stack, fluxCapacity(stack), false);
        return stack;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return fluxCapacity(stack) > 0L;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        long capacity = fluxCapacity(stack);
        if (capacity <= 0L) {
            return 0;
        }
        return Math.max(0, Math.min(BAR_WIDTH, Math.round(BAR_WIDTH * (float) ((double) fluxStored(stack) / capacity))));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        appendDetails(tooltip);
        tooltip.add(Component.translatable(TOOLTIP_STORED, format(fluxStored(stack)), format(fluxCapacity(stack))).withStyle(ChatFormatting.DARK_PURPLE));
    }

    protected abstract void appendDetails(List<Component> tooltip);
}
