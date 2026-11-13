package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;

import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class FlightThrusterItem extends Item implements IFluxItem {
    public FlightThrusterItem(Properties properties) {
        super(properties);
    }

    @Override
    public long fluxCapacity(ItemStack stack) {
        return FieldConfig.thrusterCapacity();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        long capacity = fluxCapacity(stack);
        return capacity <= 0L ? 0 : (int) Math.min(13L, Math.round(13.0D * fluxStored(stack) / capacity));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return FluxTier.THAUMIUM.color();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        tooltip.accept(Component.translatable(FieldTranslations.FLUX, format.format(fluxStored(stack)), format.format(fluxCapacity(stack))).withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.accept(Component.translatable(FieldTranslations.TOOLTIP_THRUSTER_COST, format.format(FieldConfig.thrusterCostPerSecond())).withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(FieldTranslations.TOOLTIP_THRUSTER_USE).withStyle(ChatFormatting.DARK_GRAY));
    }
}
