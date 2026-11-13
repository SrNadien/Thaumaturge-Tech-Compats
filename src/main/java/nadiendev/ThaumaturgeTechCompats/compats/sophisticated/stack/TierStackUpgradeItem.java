package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.stack;

import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedTier;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedTranslations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeItem;

import java.util.List;
import java.util.function.Consumer;

public class TierStackUpgradeItem extends StackUpgradeItem {
    private static final double[] UNIT_VALUES = {1.0E12D, 1.0E9D, 1.0E6D, 1.0E3D};

    private final SophisticatedTier tier;

    public TierStackUpgradeItem(SophisticatedTier tier, double multiplier, IUpgradeCountLimitConfig limits) {
        super(multiplier, limits);
        this.tier = tier;
    }

    public SophisticatedTier tier() {
        return tier;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(SophisticatedTranslations.STACK_TOOLTIP, compact(getStackSizeMultiplier())).withStyle(ChatFormatting.DARK_GRAY));
    }

    private static Component compact(double value) {
        for (int i = 0; i < UNIT_VALUES.length; i++) {
            if (value >= UNIT_VALUES[i]) {
                return Component.literal(number(value / UNIT_VALUES[i]) + " ").append(Component.translatable(SophisticatedTranslations.UNIT_KEYS[i]));
            }
        }
        return Component.literal(number(value));
    }

    private static String number(double value) {
        double rounded = Math.round(value * 100.0D) / 100.0D;
        return rounded == Math.rint(rounded) ? Long.toString((long) rounded) : Double.toString(rounded);
    }
}
