package nadiendev.ThaumaturgeTechCompats.purity.warp;

import com.leclowndu93150.thaumaturge.api.warp.IPlayerWarp;
import com.leclowndu93150.thaumaturge.api.warp.WarpHelper;
import com.leclowndu93150.thaumaturge.api.warp.WarpType;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class WarpPotionItem extends Item {
    private static final int DRINK_TICKS = 32;

    private final WarpType type;
    private final WarpPotionConfig.Settings settings;

    public WarpPotionItem(Properties properties, WarpPotionConfig.Settings settings, WarpType type) {
        super(properties);
        this.settings = settings;
        this.type = type;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return DRINK_TICKS;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!settings.enabled()) {
            message(level, player, "disabled");
            return InteractionResultHolder.fail(stack);
        }
        if (removable(player) <= 0) {
            message(level, player, "nothing");
            return InteractionResultHolder.fail(stack);
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Player player = entity instanceof Player found ? found : null;
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            if (settings.enabled() && cleanse(serverPlayer) > 0) {
                applySideEffect(serverPlayer);
                level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                        SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 1.0F, 0.8F);
                serverPlayer.serverLevel().sendParticles(ParticleTypes.ENCHANT, serverPlayer.getX(), serverPlayer.getY(0.7D), serverPlayer.getZ(),
                        30, 0.4D, 0.6D, 0.4D, 0.4D);
            }
        }
        if (player == null) {
            return stack;
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        int cooldown = settings.cooldown();
        if (cooldown > 0) {
            player.getCooldowns().addCooldown(this, cooldown);
        }
        entity.gameEvent(GameEvent.DRINK);
        if (player.getAbilities().instabuild) {
            return stack;
        }
        stack.consume(1, player);
        if (settings.returnBottle()) {
            ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
            if (stack.isEmpty()) {
                return bottle;
            }
            if (!player.getInventory().add(bottle)) {
                player.drop(bottle, false);
            }
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }

    private int removable(Player player) {
        IPlayerWarp warp = WarpHelper.getWarp(player);
        int total = warp.get(type);
        if (settings.alsoNormal()) {
            total += warp.get(WarpType.NORMAL);
        }
        return total;
    }

    private int cleanse(ServerPlayer player) {
        boolean all = settings.removeAll();
        int budget = settings.amount();
        int removed = take(player, type, all, budget);
        if (settings.alsoNormal()) {
            removed += take(player, WarpType.NORMAL, all, budget - removed);
        }
        return removed;
    }

    private static int take(ServerPlayer player, WarpType warpType, boolean all, int budget) {
        int have = WarpHelper.getWarp(player).get(warpType);
        int amount = all ? have : Math.min(have, budget);
        if (amount <= 0) {
            return 0;
        }
        WarpHelper.addWarp(player, -amount, warpType);
        return amount;
    }

    private void applySideEffect(ServerPlayer player) {
        if (!settings.sideEffect()) {
            return;
        }
        ResourceLocation id = ResourceLocation.tryParse(settings.effect());
        if (id == null) {
            return;
        }
        BuiltInRegistries.MOB_EFFECT.getHolder(id).ifPresent(effect ->
                player.addEffect(new MobEffectInstance(effect, settings.effectDuration(), settings.effectAmplifier())));
    }

    private void message(Level level, Player player, String kind) {
        if (!level.isClientSide) {
            String name = BuiltInRegistries.ITEM.getKey(this).getPath();
            player.displayClientMessage(Component.translatable("message." + ThaumaturgeTechCompats.MOD_ID + "." + name + "." + kind), true);
        }
    }
}
