package nadiendev.ThaumaturgeTechCompats.purity;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class SplashPotionItem extends Item implements ProjectileItem {
    protected SplashPotionItem(Properties properties) {
        super(properties);
    }

    protected abstract boolean isEnabled();

    protected abstract int cooldownTicks();

    protected abstract ThrowableItemProjectile createProjectile(Level level, LivingEntity owner);

    protected abstract ThrowableItemProjectile createProjectile(Level level, double x, double y, double z);

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!isEnabled()) {
            if (!level.isClientSide) {
                String name = BuiltInRegistries.ITEM.getKey(this).getPath();
                player.displayClientMessage(Component.translatable("message." + ThaumaturgeTechCompats.MOD_ID + "." + name + ".disabled"), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS,
                0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            ThrowableItemProjectile potion = createProjectile(level, player);
            potion.setItem(stack);
            potion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
            level.addFreshEntity(potion);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        int cooldown = cooldownTicks();
        if (cooldown > 0) {
            player.getCooldowns().addCooldown(this, cooldown);
        }
        stack.consume(1, player);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
        ThrowableItemProjectile potion = createProjectile(level, position.x(), position.y(), position.z());
        potion.setItem(stack);
        return potion;
    }

    @Override
    public DispenseConfig createDispenseConfig() {
        return DispenseConfig.builder()
                .uncertainty(DispenseConfig.DEFAULT.uncertainty() * 0.5F)
                .power(DispenseConfig.DEFAULT.power() * 1.25F)
                .build();
    }
}
