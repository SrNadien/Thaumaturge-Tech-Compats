package nadiendev.ThaumaturgeTechCompats.infusion;

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

public class VariantStoneItem extends BlockItem {
    private final MatrixVariant variant;

    public VariantStoneItem(Block block, Properties properties, MatrixVariant variant, StoneKind kind) {
        super(block, properties);
        this.variant = variant;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        Component requirement = InfusionTranslations.requirement(variant);
        if (requirement != null) {
            tooltip.accept(requirement.copy().withStyle(ChatFormatting.GRAY));
        }
    }
}
