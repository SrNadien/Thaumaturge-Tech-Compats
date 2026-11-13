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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;

public abstract class SplashPotionItem extends Item implements ProjectileItem {
    protected SplashPotionItem(Properties properties) {
        super(properties);
    }

    protected abstract boolean isEnabled();

    protected abstract int cooldownTicks();

    protected abstract ThrowableItemProjectile createProjectile(Level level, LivingEntity owner);

    protected abstract ThrowableItemProjectile createProjectile(Level level, double x, double y, double z);

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!isEnabled()) {
            if (!level.isClientSide()) {
                String name = BuiltInRegistries.ITEM.getKey(this).getPath();
                player.sendOverlayMessage(Component.translatable("message." + ThaumaturgeTechCompats.MOD_ID + "." + name + ".disabled"));
            }
            return InteractionResult.FAIL;
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS,
                0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide()) {
            ThrowableItemProjectile potion = createProjectile(level, player);
            potion.setItem(stack);
            potion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
            level.addFreshEntity(potion);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        int cooldown = cooldownTicks();
        if (cooldown > 0) {
            player.getCooldowns().addCooldown(BuiltInRegistries.ITEM.getKey(this), cooldown);
        }
        stack.consume(1, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable(getDescriptionId() + ".tooltip"));
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
