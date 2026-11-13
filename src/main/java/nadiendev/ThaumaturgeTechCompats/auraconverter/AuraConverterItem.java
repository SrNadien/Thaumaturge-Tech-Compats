package nadiendev.ThaumaturgeTechCompats.auraconverter;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AuraConverterItem extends BlockItem {
    public static final String TOOLTIP_RATE = "tooltip.thaumaturgetechcompats.aura_converter.rate";
    public static final String TOOLTIP_BUFFER = "tooltip.thaumaturgetechcompats.aura_converter.buffer";
    public static final String TOOLTIP_FLUX = "tooltip.thaumaturgetechcompats.aura_converter.flux";

    private final AuraConverterTier tier;

    public AuraConverterItem(AuraConverterBlock block, Properties properties) {
        super(block, properties);
        this.tier = block.tier();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(TOOLTIP_RATE, format(AuraConverterConfig.fePerVis(tier)), format(AuraConverterConfig.maxOutput(tier))).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(TOOLTIP_BUFFER, format(AuraConverterConfig.buffer(tier))).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(TOOLTIP_FLUX, new DecimalFormat("0.##").format(AuraConverterConfig.fluxPerVis())).withStyle(ChatFormatting.DARK_PURPLE));
    }

    private static String format(long value) {
        return NumberFormat.getIntegerInstance().format(value);
    }
}
