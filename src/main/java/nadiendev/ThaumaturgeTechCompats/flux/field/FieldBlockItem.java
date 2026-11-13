package nadiendev.ThaumaturgeTechCompats.flux.field;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.text.NumberFormat;
import java.util.List;

public class FieldBlockItem extends BlockItem {
    public FieldBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        if (getBlock() instanceof ShieldGeneratorBlock shield) {
            tooltip.add(Component.translatable(FieldTranslations.TOOLTIP_SHIELD_RADIUS, FieldConfig.shieldRadius(shield.tier())).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable(FieldTranslations.TOOLTIP_SHIELD_COST, format.format(FieldConfig.shieldCost(shield.tier()))).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable(FieldTranslations.TOOLTIP_SHIELD_CAPACITY, format.format(FieldConfig.shieldCapacity(shield.tier()))).withStyle(ChatFormatting.GRAY));
        } else if (getBlock() instanceof TerraformerBlock terraformer) {
            tooltip.add(Component.translatable(FieldTranslations.TOOLTIP_TERRAFORMER_RADIUS, FieldConfig.terraformerRadius(terraformer.tier())).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable(FieldTranslations.TOOLTIP_TERRAFORMER_COST, format.format(FieldConfig.terraformerCost(terraformer.tier()))).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable(FieldTranslations.TOOLTIP_TERRAFORMER_CAPACITY, format.format(FieldConfig.terraformerCapacity(terraformer.tier()))).withStyle(ChatFormatting.GRAY));
        }
    }
}
