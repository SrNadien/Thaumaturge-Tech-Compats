package nadiendev.ThaumaturgeTechCompats.flux.gear;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DischargeCannonItem extends FluxGearItem {
    public static final String TOOLTIP = "tooltip.thaumaturgetechcompats.discharge_cannon.";

    private static final int USE_DURATION = 72_000;
    private static final double TRACER_STEP = 0.75D;
    private static final DustParticleOptions TRACER = new DustParticleOptions(Vec3.fromRGB24(FluxTier.THAUMIUM.color()).toVector3f(), 1.2F);

    public DischargeCannonItem(Properties properties) {
        super(properties);
    }

    @Override
    public long fluxCapacity(ItemStack stack) {
        return GearConfig.cannonCapacity();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!GearConfig.cannonEnabled()) {
            return InteractionResultHolder.pass(stack);
        }
        if (fluxStored(stack) < GearConfig.cannonCost()) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable(MESSAGE_EMPTY).withStyle(ChatFormatting.RED), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remaining) {
        if (!(entity instanceof Player player) || !(level instanceof ServerLevel serverLevel) || !GearConfig.cannonEnabled()) {
            return;
        }
        if (USE_DURATION - remaining < GearConfig.cannonChargeTicks()) {
            return;
        }
        long cost = GearConfig.cannonCost();
        if (extractFlux(stack, cost, true) < cost) {
            player.displayClientMessage(Component.translatable(MESSAGE_EMPTY).withStyle(ChatFormatting.RED), true);
            return;
        }
        extractFlux(stack, cost, false);
        fire(serverLevel, player);
        player.getCooldowns().addCooldown(this, GearConfig.cannonCooldown());
    }

    private static void fire(ServerLevel level, Player player) {
        Vec3 from = player.getEyePosition();
        Vec3 look = player.getViewVector(1.0F);
        Vec3 to = from.add(look.scale(GearConfig.cannonRange()));
        BlockHitResult blockHit = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        Vec3 end = blockHit.getType() == HitResult.Type.MISS ? to : blockHit.getLocation();
        AABB sweep = player.getBoundingBox().expandTowards(end.subtract(from)).inflate(1.0D);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(player, from, end, sweep,
                entity -> entity.isAlive() && !entity.isSpectator() && entity.isPickable() && entity != player, from.distanceToSqr(end));
        Vec3 impact = entityHit != null ? entityHit.getLocation() : end;

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.PLAYERS, 0.8F, 1.6F);
        double length = from.distanceTo(impact);
        Vec3 direction = impact.subtract(from).normalize();
        for (double travelled = 1.0D; travelled < length; travelled += TRACER_STEP) {
            Vec3 point = from.add(direction.scale(travelled));
            level.sendParticles(TRACER, point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        level.sendParticles(ParticleTypes.EXPLOSION, impact.x, impact.y, impact.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        level.sendParticles(ParticleTypes.END_ROD, impact.x, impact.y, impact.z, 30, 0.4D, 0.4D, 0.4D, 0.2D);
        level.playSound(null, impact.x, impact.y, impact.z, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 1.0F, 1.4F);

        float damage = (float) GearConfig.cannonDamage();
        DamageSource source = level.damageSources().indirectMagic(player, player);
        Entity target = entityHit != null ? entityHit.getEntity() : null;
        if (target != null) {
            target.hurt(source, damage);
            if (target instanceof LivingEntity living && GearConfig.cannonKnockback() > 0.0D) {
                living.knockback(GearConfig.cannonKnockback(), -direction.x, -direction.z);
            }
        }
        double splashRadius = GearConfig.cannonSplashRadius();
        float splashDamage = (float) (damage * GearConfig.cannonSplashFraction());
        if (splashRadius > 0.0D && splashDamage > 0.0F) {
            List<LivingEntity> nearby = level.getEntitiesOfClass(LivingEntity.class, new AABB(impact, impact).inflate(splashRadius),
                    living -> living.isAlive() && living != player && living != target && living.getBoundingBox().getCenter().distanceToSqr(impact) <= splashRadius * splashRadius);
            for (LivingEntity living : nearby) {
                living.hurt(source, splashDamage);
            }
        }
    }

    @Override
    protected void appendDetails(List<Component> tooltip) {
        tooltip.add(Component.translatable(TOOLTIP + "description").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(TOOLTIP + "shot", format(GearConfig.cannonCost()), GearConfig.cannonDamage()).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(TOOLTIP + "range", GearConfig.cannonRange(), String.format("%.1f", GearConfig.cannonCooldown() / 20.0D)).withStyle(ChatFormatting.GRAY));
    }
}
