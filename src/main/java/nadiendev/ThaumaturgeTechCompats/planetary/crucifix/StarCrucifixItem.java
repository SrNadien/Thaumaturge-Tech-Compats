package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;

public class StarCrucifixItem extends Item {
    public static final String TOOLTIP = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".star_crucifix.";

    private static final float MIN_TEXT_BRIGHTNESS = 0.8F;

    private final PlanetaryTier tier;

    public StarCrucifixItem(Properties properties, PlanetaryTier tier) {
        super(properties);
        this.tier = tier;
    }

    public PlanetaryTier tier() {
        return tier;
    }

    public static int textRgb(PlanetaryTier tier) {
        int rgb = tier.color();
        float[] hsb = Color.RGBtoHSB((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255, null);
        return Color.HSBtoRGB(hsb[0], hsb[1], Math.max(hsb[2], MIN_TEXT_BRIGHTNESS)) & 0xFFFFFF;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (StarCrucifix.hasBinding(player)) {
            return InteractionResult.FAIL;
        }
        LivingEntity target = StarCrucifix.aimedTarget(player, CrucifixConfig.range(tier));
        if (target == null) {
            return InteractionResult.FAIL;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        return StarCrucifix.activate(player, hand, target) == StarCrucifix.Outcome.NONE
                ? InteractionResult.FAIL
                : InteractionResult.CONSUME;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(super.getName(stack).getString()).withColor(textRgb(tier));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(TOOLTIP + "lore").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        int maxHealth = CrucifixConfig.maxHealth(tier);
        if (maxHealth > 0) {
            tooltip.accept(Component.translatable(TOOLTIP + "health", maxHealth).withStyle(ChatFormatting.BLUE));
        } else {
            tooltip.accept(Component.translatable(TOOLTIP + "health_any").withStyle(ChatFormatting.BLUE));
        }
        tooltip.accept(Component.translatable(TOOLTIP + "range", Math.round(CrucifixConfig.range(tier))).withStyle(ChatFormatting.BLUE));
        if (CrucifixConfig.killsBosses(tier)) {
            tooltip.accept(Component.translatable(TOOLTIP + "bosses").withStyle(ChatFormatting.DARK_RED));
        } else {
            tooltip.accept(Component.translatable(TOOLTIP + "no_bosses").withStyle(ChatFormatting.DARK_GRAY));
        }
        if (tier.unbreakable()) {
            tooltip.accept(Component.translatable(TOOLTIP + "unbreakable").withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.accept(Component.translatable(TOOLTIP + "durability", stack.getMaxDamage() - stack.getDamageValue(), stack.getMaxDamage())
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
