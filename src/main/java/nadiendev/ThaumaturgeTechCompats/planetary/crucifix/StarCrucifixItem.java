package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.awt.Color;
import java.util.List;

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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (StarCrucifix.hasBinding(player)) {
            return InteractionResultHolder.fail(stack);
        }
        LivingEntity target = StarCrucifix.aimedTarget(player, CrucifixConfig.range(tier));
        if (target == null) {
            return InteractionResultHolder.fail(stack);
        }
        if (level.isClientSide()) {
            return InteractionResultHolder.success(stack);
        }
        return StarCrucifix.activate(player, hand, target) == StarCrucifix.Outcome.NONE
                ? InteractionResultHolder.fail(stack)
                : InteractionResultHolder.consume(player.getItemInHand(hand));
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(TOOLTIP + "lore").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        int maxHealth = CrucifixConfig.maxHealth(tier);
        if (maxHealth > 0) {
            tooltip.add(Component.translatable(TOOLTIP + "health", maxHealth).withStyle(ChatFormatting.BLUE));
        } else {
            tooltip.add(Component.translatable(TOOLTIP + "health_any").withStyle(ChatFormatting.BLUE));
        }
        tooltip.add(Component.translatable(TOOLTIP + "range", Math.round(CrucifixConfig.range(tier))).withStyle(ChatFormatting.BLUE));
        if (CrucifixConfig.killsBosses(tier)) {
            tooltip.add(Component.translatable(TOOLTIP + "bosses").withStyle(ChatFormatting.DARK_RED));
        } else {
            tooltip.add(Component.translatable(TOOLTIP + "no_bosses").withStyle(ChatFormatting.DARK_GRAY));
        }
        if (tier.unbreakable()) {
            tooltip.add(Component.translatable(TOOLTIP + "unbreakable").withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.add(Component.translatable(TOOLTIP + "durability", stack.getMaxDamage() - stack.getDamageValue(), stack.getMaxDamage())
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
