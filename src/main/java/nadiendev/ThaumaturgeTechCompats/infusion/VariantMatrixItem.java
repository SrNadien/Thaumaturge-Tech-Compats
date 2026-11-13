package nadiendev.ThaumaturgeTechCompats.infusion;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class VariantMatrixItem extends BlockItem {
    private final MatrixVariant variant;

    public VariantMatrixItem(Block block, Properties properties, MatrixVariant variant) {
        super(block, properties);
        this.variant = variant;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        for (Component line : InfusionTranslations.tooltip(variant)) {
            tooltip.add(line.copy().withStyle(ChatFormatting.GRAY));
        }
    }
}
