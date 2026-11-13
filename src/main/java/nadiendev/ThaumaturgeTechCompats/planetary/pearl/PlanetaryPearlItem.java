package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;

public class PlanetaryPearlItem extends Item {
    public static final String TOOLTIP = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".planetary_pearl.";

    private static final int LISTED_BODIES = 4;

    private final PlanetaryTier tier;

    public PlanetaryPearlItem(Properties properties, PlanetaryTier tier) {
        super(properties);
        this.tier = tier;
    }

    public PlanetaryTier tier() {
        return tier;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId()).withColor(PearlColors.nameColor(tier));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return tier.unbreakable() ? 0 : PearlConfig.durability(tier);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return tier.unbreakable();
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!PearlConfig.tierEnabled(tier)) {
            return InteractionResult.PASS;
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL,
                0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        int cooldown = PearlConfig.cooldown(tier);
        if (cooldown > 0) {
            player.getCooldowns().addCooldown(BuiltInRegistries.ITEM.getKey(this), cooldown);
        }
        if (!level.isClientSide()) {
            PlanetaryPearlEntity pearl = new PlanetaryPearlEntity(PearlRegistry.PEARL_ENTITY.get(), level);
            pearl.setItem(stack.copyWithCount(1));
            pearl.setOwner(player);
            pearl.setPos(player.getX(), player.getEyeY() - 0.1D, player.getZ());
            pearl.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(pearl);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!level.isClientSide() && !tier.unbreakable() && !player.getAbilities().instabuild) {
            stack.hurtAndBreak(1, player, hand.asEquipmentSlot());
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        int planets = PearlConfig.planets(tier);
        tooltip.accept(Component.translatable(TOOLTIP + "description").withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(TOOLTIP + "planets", planets).withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(TOOLTIP + "bodies", bodies(planets)).withStyle(ChatFormatting.DARK_GRAY));
        tooltip.accept(Component.translatable(TOOLTIP + "damage", format(PearlConfig.damage(tier)), format(PearlConfig.interval(tier) / 20.0D)).withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(TOOLTIP + "duration", format(PearlConfig.duration(tier) / 20.0D), format(PearlConfig.radius(tier))).withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(TOOLTIP + "cooldown", format(PearlConfig.cooldown(tier) / 20.0D)).withStyle(ChatFormatting.GRAY));
        if (tier.killsBosses()) {
            tooltip.accept(Component.translatable(TOOLTIP + "kills_bosses").withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.accept(Component.translatable(TOOLTIP + "boss_cap", format(PearlConfig.bossCap(tier))).withStyle(ChatFormatting.GRAY));
        }
        tooltip.accept((tier.unbreakable() ? Component.translatable(TOOLTIP + "unbreakable") : Component.translatable(TOOLTIP + "durability", stack.getMaxDamage() - stack.getDamageValue(), stack.getMaxDamage()))
                .withStyle(tier.unbreakable() ? ChatFormatting.AQUA : ChatFormatting.DARK_GRAY));
        if (!PearlConfig.tierEnabled(tier)) {
            tooltip.accept(Component.translatable(TOOLTIP + "disabled").withStyle(ChatFormatting.RED));
        }
    }

    private static Component bodies(int planets) {
        int shown = Math.min(planets, LISTED_BODIES);
        MutableComponent names = Component.empty();
        for (int i = 0; i < shown; i++) {
            if (i > 0) {
                names.append(", ");
            }
            names.append(Component.translatable(PlanetBody.of(i).nameKey()));
        }
        if (planets > shown) {
            names.append(Component.translatable(TOOLTIP + "more", planets - shown));
        }
        return names;
    }

    private static String format(double value) {
        return value == Math.rint(value) ? Long.toString((long) value) : String.format("%.1f", value);
    }
}
