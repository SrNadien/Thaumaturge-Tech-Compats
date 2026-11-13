package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class MekanismToolTooltip {
    private MekanismToolTooltip() {
    }

    public static void append(ItemStack stack, List<Component> tooltip) {
        if (!(stack.getItem() instanceof MekanismEnergyTool tool)) {
            return;
        }
        MekanismToolTier tier = tool.tier();
        MekanismToolKind kind = tool.kind();
        tooltip.add(Component.translatable(MekanismToolTranslations.TOOLTIP + "energy",
                EnergyDisplay.of(MekanismToolBehavior.stored(stack), MekanismToolBehavior.capacity(stack)).getTextComponent()).withStyle(ChatFormatting.GRAY));
        if (!MekanismToolBehavior.hasEnergy(stack)) {
            tooltip.add(Component.translatable(MekanismToolTranslations.TOOLTIP + "empty").withStyle(ChatFormatting.RED));
        }
        tooltip.add(cost("block_cost", MekanismToolConfig.blockCost(tier)));
        tooltip.add(cost("attack_cost", MekanismToolConfig.attackCost(tier)));
        if (kind == MekanismToolKind.AXE || kind == MekanismToolKind.SHOVEL || kind == MekanismToolKind.HOE) {
            tooltip.add(cost("use_cost", MekanismToolConfig.useCost(tier)));
        }
    }

    private static Component cost(String key, long joules) {
        return Component.translatable(MekanismToolTranslations.TOOLTIP + key, EnergyDisplay.of(joules).getTextComponent()).withStyle(ChatFormatting.DARK_GRAY);
    }
}
