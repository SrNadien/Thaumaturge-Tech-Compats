package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.text.NumberFormat;
import java.util.List;

public class FlightThrusterItem extends ArmorItem implements IFluxItem {
    public FlightThrusterItem(Properties properties) {
        super(FieldRegistry.THRUSTER_MATERIAL, ArmorItem.Type.CHESTPLATE, properties);
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        tooltip.add(Component.translatable(FieldTranslations.FLUX, format.format(fluxStored(stack)), format.format(fluxCapacity(stack))).withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable(FieldTranslations.TOOLTIP_THRUSTER_COST, format.format(FieldConfig.thrusterCostPerSecond())).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(FieldTranslations.TOOLTIP_THRUSTER_USE).withStyle(ChatFormatting.DARK_GRAY));
    }
}
