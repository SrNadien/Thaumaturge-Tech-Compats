package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.voidminersremastered.init.ModifierType;
import nadiendev.voidminersremastered.util.ColorUtil;
import nadiendev.voidminersremastered.world.block.ModifierBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class MagicalModifierBlock extends ModifierBlock {
    private final VoidMinersMagicalTier tier;
    private final MagicalUpgrade upgrade;

    public MagicalModifierBlock(Properties properties, VoidMinersMagicalTier tier, MagicalUpgrade upgrade) {
        super(properties, tier.id(), VoidMinersMagicalStructures.color(tier), type(upgrade));
        this.tier = tier;
        this.upgrade = upgrade;
    }

    private static ModifierType type(MagicalUpgrade upgrade) {
        return switch (upgrade) {
            case SPEED -> ModifierType.SPEED;
            case PRODUCTION -> ModifierType.ITEM;
            case EFFICIENCY -> ModifierType.ENERGY;
            case GENERATION -> ModifierType.EFFICIENCY;
            case SAVING -> ModifierType.WEATHER;
        };
    }

    public VoidMinersMagicalTier tier() {
        return tier;
    }

    public MagicalUpgrade upgrade() {
        return upgrade;
    }

    @Override
    public MutableComponent getName() {
        return Component.literal(Component.translatable(getDescriptionId()).getString()).withColor(VoidMinersMagicalConfig.textRgb(tier));
    }

    @Override
    public ColorUtil getColor() {
        return VoidMinersMagicalStructures.color(tier);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(upgrade.tooltipKey(), VoidMinersMagicalConfig.upgrade(tier, upgrade)).withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
