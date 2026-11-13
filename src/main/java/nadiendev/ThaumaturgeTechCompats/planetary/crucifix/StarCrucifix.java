package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixEffectPayload.Phase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID)
public final class StarCrucifix {
    public enum Outcome { NONE, BOUND, FAILED }

    public record FailState(boolean roared, int snapped, boolean cracked) {
    }

    static final String BOUND_TAG = ThaumaturgeTechCompats.MOD_ID + "_crucifix_bound";

    private static final int CHAINS_SOUND_TICK = 3;

    private static final Map<UUID, Binding> BINDINGS = new LinkedHashMap<>();

    @Nullable
    private static Vec3 dropRedirect;

    private static final class Binding {
        final Player player;
        final LivingEntity target;
        final ServerLevel level;
        final PlanetaryTier tier;
        final boolean success;
        final boolean broken;
        final boolean killsBosses;
        final int bind;
        final long startTick;
        final Vec3 anchor;
        final CrucifixDisplayEntity display;
        int age;
        double sinkFrom = Double.NaN;
        boolean roared;
        int snapped;
        boolean cracked;

        Binding(Player player, LivingEntity target, ServerLevel level, PlanetaryTier tier, boolean success, boolean broken, CrucifixDisplayEntity display) {
            this.player = player;
            this.target = target;
            this.level = level;
            this.tier = tier;
            this.success = success;
            this.broken = broken;
            this.killsBosses = CrucifixConfig.killsBosses(tier);
            this.bind = CrucifixConfig.bindTicks(tier);
            this.startTick = level.getGameTime();
            this.anchor = target.position();
            this.display = display;
        }
    }

    private StarCrucifix() {
    }

    public static boolean isBoss(LivingEntity entity) {
        return entity.getType().is(CrucifixTags.IMMUNE);
    }

    public static boolean isImmune(LivingEntity entity, PlanetaryTier tier) {
        if (isBoss(entity) && !CrucifixConfig.killsBosses(tier)) {
            return true;
        }
        int cap = CrucifixConfig.maxHealth(tier);
        return cap > 0 && entity.getMaxHealth() > cap;
    }

    public static boolean hasBinding(Player player) {
        return BINDINGS.containsKey(player.getUUID());
    }

    public static boolean isBound(Entity entity) {
        for (Binding binding : BINDINGS.values()) {
            if (binding.success && binding.target == entity) {
                return true;
            }
        }
        return false;
    }

    public static boolean ownsDisplay(CrucifixDisplayEntity display) {
        for (Binding binding : BINDINGS.values()) {
            if (binding.display == display) {
                return true;
            }
        }
        return false;
    }

    public static Optional<CrucifixDisplayEntity> display(Player player) {
        return Optional.ofNullable(BINDINGS.get(player.getUUID())).map(binding -> binding.display);
    }

    public static Optional<FailState> failState(Player player) {
        Binding binding = BINDINGS.get(player.getUUID());
        return binding == null || binding.success ? Optional.empty()
                : Optional.of(new FailState(binding.roared, binding.snapped, binding.cracked));
    }

    public static Optional<InteractionHand> readyHand(Player player) {
        for (InteractionHand hand : InteractionHand.values()) {
            if (player.getItemInHand(hand).getItem() instanceof StarCrucifixItem) {
                return Optional.of(hand);
            }
        }
        return Optional.empty();
    }

    @Nullable
    public static LivingEntity aimedTarget(Player player, double range) {
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getViewVector(1.0F);
        Vec3 end = eye.add(look.scale(range));
        HitResult block = player.level().clip(new ClipContext(eye, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (block.getType() != HitResult.Type.MISS) {
            end = block.getLocation();
        }
        AABB search = player.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0);
        EntityHitResult hit = ProjectileUtil.getEntityHitResult(player, eye, end, search,
                e -> !e.isSpectator() && e.isPickable(), eye.distanceToSqr(end));
        if (hit == null) {
            return null;
        }
        Entity entity = hit.getEntity();
        if (entity instanceof PartEntity<?> part) {
            entity = part.getParent();
        }
        return entity instanceof LivingEntity living && living instanceof Enemy && !(living instanceof Player) && living.isAlive()
                ? living : null;
    }

    public static Vec3 displayCenter(Level level, Player player, LivingEntity target) {
        Vec3 toPlayer = new Vec3(player.getX() - target.getX(), 0.0, player.getZ() - target.getZ());
        double distance = toPlayer.length();
        Vec3 dir = distance > 1.0E-4 ? toPlayer.scale(1.0 / distance) : fallbackDirection(player);
        double halfWidth = target.getBbWidth() * 0.5;
        double reach = CrucifixTiming.displayDistance(distance, halfWidth, player.getBbWidth() * 0.5);
        double y = target.getEyeY() + CrucifixTiming.DISPLAY_ABOVE_EYES;
        Vec3 center = new Vec3(target.getX(), y, target.getZ());
        double closest = halfWidth + CrucifixTiming.DISPLAY_SIZE * 0.5 + 0.1;
        for (double d = reach; d >= closest; d -= 0.25) {
            Vec3 at = center.add(dir.scale(d));
            if (free(level, at)) {
                return at;
            }
        }
        Vec3 at = center.add(dir.scale(reach));
        for (double up = 0.25; up <= 2.0; up += 0.25) {
            if (free(level, at.add(0.0, up, 0.0))) {
                return at.add(0.0, up, 0.0);
            }
        }
        return at;
    }

    private static Vec3 fallbackDirection(Player player) {
        Vec3 look = player.getViewVector(1.0F);
        Vec3 back = new Vec3(-look.x, 0.0, -look.z);
        return back.lengthSqr() > 1.0E-6 ? back.normalize() : new Vec3(0.0, 0.0, 1.0);
    }

    private static boolean free(Level level, Vec3 center) {
        return level.noCollision(AABB.ofSize(center, CrucifixTiming.DISPLAY_SIZE, CrucifixTiming.DISPLAY_SIZE, CrucifixTiming.DISPLAY_SIZE));
    }

    private static ItemStack particleStack(PlanetaryTier tier) {
        return new ItemStack(CrucifixRegistry.item(tier));
    }

    private static BlockState splinters() {
        return Blocks.OAK_PLANKS.defaultBlockState();
    }

    public static Outcome activate(Player player, InteractionHand hand, LivingEntity target) {
        if (!(player.level() instanceof ServerLevel level)) {
            return Outcome.NONE;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (!(stack.getItem() instanceof StarCrucifixItem crucifix) || !CrucifixIntegration.enabled(crucifix.tier()) || hasBinding(player)) {
            return Outcome.NONE;
        }
        PlanetaryTier tier = crucifix.tier();
        if (target instanceof Player || !target.isAlive() || target.level() != level || isBound(target)) {
            return Outcome.NONE;
        }

        boolean success = !isImmune(target, tier);
        boolean broken = false;
        if (stack.isDamageableItem() && !player.hasInfiniteMaterials()) {
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            broken = stack.isEmpty();
        }

        Vec3 center = displayCenter(level, player, target);
        CrucifixDisplayEntity display = new CrucifixDisplayEntity(CrucifixRegistry.DISPLAY.get(), level);
        display.setTier(tier);
        display.moveTo(center.x, center.y - CrucifixTiming.DISPLAY_SIZE * 0.5, center.z, 0.0F, 0.0F);
        Binding binding = new Binding(player, target, level, tier, success, broken, display);
        BINDINGS.put(player.getUUID(), binding);
        level.addFreshEntity(display);

        if (success) {
            hold(target, binding.anchor);
        }
        play(binding, CrucifixRegistry.BIND.get(), success ? 1.0F : 0.8F);
        send(binding, Phase.START);
        return success ? Outcome.BOUND : Outcome.FAILED;
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide()) {
            return;
        }
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker) || attacker instanceof Player) {
            return;
        }
        Optional<InteractionHand> hand = readyHand(player);
        if (hand.isPresent() && activate(player, hand.get(), attacker) == Outcome.BOUND) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (BINDINGS.isEmpty()) {
            return;
        }
        for (Binding binding : new ArrayList<>(BINDINGS.values())) {
            binding.age++;
            cues(binding);
            LivingEntity target = binding.target;
            if (!target.isAlive() || target.level() != binding.level) {
                finish(binding, Phase.CANCEL);
            } else if (CrucifixTiming.finished(binding.success, binding.age, binding.bind)) {
                if (binding.success) {
                    banish(binding);
                } else {
                    explode(binding);
                }
            } else if (binding.success) {
                if (CrucifixTiming.sinking(binding.age, binding.bind)) {
                    sink(binding);
                } else {
                    lift(binding);
                }
            } else {
                resist(binding);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        Entity entity = event.getEntity();
        if (dropRedirect != null && (entity instanceof ItemEntity || entity instanceof ExperienceOrb)) {
            entity.setPos(dropRedirect.x, dropRedirect.y, dropRedirect.z);
            return;
        }
        if (entity instanceof LivingEntity living && living.getPersistentData().contains(BOUND_TAG) && !isBound(living)) {
            CompoundTag saved = living.getPersistentData().getCompound(BOUND_TAG);
            release(living);
            if (saved.contains("AnchorY")) {
                living.setPos(saved.getDouble("AnchorX"), saved.getDouble("AnchorY"), saved.getDouble("AnchorZ"));
            }
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        BINDINGS.clear();
        dropRedirect = null;
    }

    private static void hold(LivingEntity target, Vec3 anchor) {
        CompoundTag saved = new CompoundTag();
        saved.putBoolean("NoAI", target instanceof Mob mob && mob.isNoAi());
        saved.putBoolean("NoGravity", target.isNoGravity());
        saved.putBoolean("Invulnerable", target.isInvulnerable());
        saved.putDouble("AnchorX", anchor.x);
        saved.putDouble("AnchorY", anchor.y);
        saved.putDouble("AnchorZ", anchor.z);
        target.getPersistentData().put(BOUND_TAG, saved);
        if (target instanceof Mob mob) {
            mob.setNoAi(true);
            mob.setTarget(null);
            mob.getNavigation().stop();
        }
        target.setNoGravity(true);
        target.setInvulnerable(true);
        target.setDeltaMovement(Vec3.ZERO);
    }

    private static void release(LivingEntity target) {
        CompoundTag saved = target.getPersistentData().getCompound(BOUND_TAG);
        target.getPersistentData().remove(BOUND_TAG);
        if (target instanceof Mob mob) {
            mob.setNoAi(saved.getBoolean("NoAI"));
        }
        target.setNoGravity(saved.getBoolean("NoGravity"));
        target.setInvulnerable(saved.getBoolean("Invulnerable"));
        target.noPhysics = false;
    }

    private static void lift(Binding binding) {
        LivingEntity target = binding.target;
        double wanted = binding.anchor.y + CrucifixTiming.lift(binding.age, binding.bind);
        target.setDeltaMovement(Vec3.ZERO);
        target.move(MoverType.SELF, new Vec3(0.0, wanted - target.getY(), 0.0));
        target.setPos(binding.anchor.x, target.getY(), binding.anchor.z);
        target.resetFallDistance();
    }

    private static void sink(Binding binding) {
        LivingEntity target = binding.target;
        Vec3 anchor = binding.anchor;
        if (Double.isNaN(binding.sinkFrom)) {
            binding.sinkFrom = target.getY();
            binding.level.playSound(null, anchor.x, anchor.y, anchor.z, CrucifixRegistry.DRAG.get(), SoundSource.PLAYERS, 1.2F, 0.8F);
        }
        double bottom = anchor.y - CrucifixTiming.sinkDepth(target.getBbHeight());
        double y = Mth.lerp(CrucifixTiming.sink(binding.age, binding.bind), binding.sinkFrom, bottom);
        target.noPhysics = true;
        target.setDeltaMovement(Vec3.ZERO);
        target.setPos(anchor.x, y, anchor.z);
        target.resetFallDistance();

        double w = target.getBbWidth() * 0.5 + 0.2;
        if (y < anchor.y + 0.5) {
            BlockState ground = binding.level.getBlockState(BlockPos.containing(anchor.x, anchor.y - 0.01, anchor.z));
            if (!ground.isAir()) {
                binding.level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, ground),
                        anchor.x, anchor.y + 0.05, anchor.z, 8, w, 0.02, w, 0.15);
            }
            if (binding.age % 2 == 0) {
                binding.level.sendParticles(ParticleTypes.SOUL, anchor.x, anchor.y + 0.2, anchor.z, 1, w, 0.05, w, 0.02);
            }
        }
        binding.level.sendParticles(CrucifixRegistry.STAR_SPARKLE.get(), anchor.x, anchor.y + 0.3, anchor.z, 2, w, 0.2, w, 0.02);
    }

    private static void banish(Binding binding) {
        LivingEntity target = binding.target;
        Vec3 anchor = binding.anchor;
        play(binding, CrucifixRegistry.BANISH.get(), 0.8F);
        binding.level.sendParticles(CrucifixRegistry.STAR_SPARKLE.get(), anchor.x, anchor.y + 0.3, anchor.z, 16,
                target.getBbWidth() * 0.5, 0.2, target.getBbWidth() * 0.5, 0.05);

        target.setInvulnerable(false);
        float overkill = target.getMaxHealth() * 100.0F + target.getAbsorptionAmount() + 1000.0F;
        dropRedirect = anchor.add(0.0, 0.1, 0.0);
        try {
            DamageSource source = CrucifixDamageTypes.banished(binding.level, binding.player);
            strike(target, source, overkill);
            if (target.isAlive() && binding.killsBosses && isBoss(target)) {
                target.setHealth(0.0F);
                target.die(source);
            }
        } finally {
            dropRedirect = null;
        }
        if (target.isAlive()) {
            release(target);
            target.teleportTo(anchor.x, anchor.y, anchor.z);
        } else {
            target.getPersistentData().remove(BOUND_TAG);
        }
        finish(binding, Phase.BANISH);
    }

    private static void strike(LivingEntity target, DamageSource source, float amount) {
        if (target instanceof EnderDragon dragon) {
            dragon.hurt(dragon.head, source, amount);
        } else {
            target.hurt(source, amount);
        }
    }

    private static void resist(Binding binding) {
        int age = binding.age;
        ServerLevel level = binding.level;
        LivingEntity boss = binding.target;
        if (age >= CrucifixTiming.FORM_TICKS && age < CrucifixTiming.FAIL_ROAR_TICKS
                && (age - CrucifixTiming.FORM_TICKS) % CrucifixTiming.STRAIN_SOUND_INTERVAL == 0) {
            CrucifixSounds.strain(level, boss, CrucifixTiming.strain(age));
        }
        if (!binding.roared && age >= CrucifixTiming.FAIL_ROAR_TICKS) {
            binding.roared = true;
            CrucifixSounds.roar(level, boss);
        }
        while (binding.snapped < CrucifixTiming.snapped(age)) {
            CrucifixSounds.snap(level, boss, binding.snapped);
            binding.snapped++;
        }
        if (!binding.cracked && CrucifixTiming.cracked(age)) {
            binding.cracked = true;
            Vec3 c = binding.display.getBoundingBox().getCenter();
            CrucifixSounds.crack(level, c);
            level.sendParticles(ParticleTypes.FLASH, c.x, c.y, c.z, 1, 0.0, 0.0, 0.0, 0.0);
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, c.x, c.y, c.z, 14, 0.1, 0.2, 0.1, 0.25);
            level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, particleStack(binding.tier)),
                    c.x, c.y, c.z, 6, 0.05, 0.15, 0.05, 0.05);
        }
    }

    private static void explode(Binding binding) {
        ServerLevel level = binding.level;
        CrucifixDisplayEntity display = binding.display;
        Vec3 c = display.getBoundingBox().getCenter();
        CrucifixSounds.explode(level, c);
        level.sendParticles(ParticleTypes.FLASH, c.x, c.y, c.z, 1, 0.0, 0.0, 0.0, 0.0);
        level.sendParticles(ParticleTypes.EXPLOSION, c.x, c.y, c.z, 1, 0.0, 0.0, 0.0, 0.0);
        level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, particleStack(binding.tier)),
                c.x, c.y, c.z, 36, 0.1, 0.15, 0.1, 0.3);
        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, splinters()),
                c.x, c.y, c.z, 24, 0.15, 0.2, 0.15, 0.3);
        level.sendParticles(CrucifixRegistry.STAR_FRAGMENT.get(), c.x, c.y, c.z, 30, 0.15, 0.15, 0.15, 0.25);
        level.sendParticles(CrucifixRegistry.STAR_SPARKLE.get(), c.x, c.y, c.z, 12, 0.4, 0.4, 0.4, 0.05);
        display.discard();
        aftermath(binding);
        finish(binding, Phase.SHATTER);
    }

    private static void aftermath(Binding binding) {
        LivingEntity boss = binding.target;
        Player holder = binding.player;
        if (holder.isAlive() && !holder.isSpectator() && holder.level() == binding.level
                && holder.distanceTo(boss) <= CrucifixTiming.FAIL_AFTERMATH_RANGE) {
            Vec3 away = new Vec3(holder.getX() - boss.getX(), 0.0, holder.getZ() - boss.getZ());
            away = away.lengthSqr() > 1.0E-6 ? away.normalize() : fallbackDirection(holder);
            double push = Mth.clamp(1.0 - holder.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 0.0, 1.0);
            if (push > 0.0) {
                holder.setDeltaMovement(away.x * CrucifixTiming.KNOCKBACK_HORIZONTAL * push,
                        Math.max(holder.getDeltaMovement().y, CrucifixTiming.KNOCKBACK_VERTICAL * push),
                        away.z * CrucifixTiming.KNOCKBACK_HORIZONTAL * push);
                holder.hasImpulse = true;
                holder.hurtMarked = true;
            }
            holder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, CrucifixTiming.SHAKEN_TICKS,
                    CrucifixTiming.SHAKEN_SLOWNESS_AMPLIFIER), boss);
            holder.addEffect(new MobEffectInstance(MobEffects.DARKNESS, CrucifixTiming.SHAKEN_TICKS, 0), boss);
        }
        if (boss.isAlive()) {
            boss.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, CrucifixTiming.BOSS_FURY_TICKS,
                    CrucifixTiming.BOSS_FURY_AMPLIFIER), holder);
        }
    }

    private static void finish(Binding binding, Phase phase) {
        BINDINGS.remove(binding.player.getUUID(), binding);
        breakDisplay(binding);
        if (phase == Phase.CANCEL && binding.success && binding.target.getPersistentData().contains(BOUND_TAG)) {
            release(binding.target);
        }
        send(binding, phase);
    }

    private static void breakDisplay(Binding binding) {
        CrucifixDisplayEntity display = binding.display;
        if (display.isRemoved()) {
            return;
        }
        ServerLevel level = binding.level;
        Vec3 c = display.getBoundingBox().getCenter();
        if (!binding.broken) {
            level.sendParticles(CrucifixRegistry.STAR_SPARKLE.get(), c.x, c.y, c.z, 10, 0.2, 0.25, 0.2, 0.03);
            display.discard();
            return;
        }
        level.playSound(null, c.x, c.y, c.z, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.9F, 0.8F + level.random.nextFloat() * 0.4F);
        level.playSound(null, c.x, c.y, c.z, CrucifixRegistry.BREAK.get(), SoundSource.PLAYERS, 0.8F, 0.9F + level.random.nextFloat() * 0.2F);
        level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, particleStack(binding.tier)),
                c.x, c.y, c.z, 18, 0.12, 0.2, 0.12, 0.08);
        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, splinters()),
                c.x, c.y, c.z, 8, 0.12, 0.2, 0.12, 0.1);
        level.sendParticles(CrucifixRegistry.STAR_SPARKLE.get(), c.x, c.y, c.z, 4, 0.2, 0.25, 0.2, 0.03);
        display.discard();
    }

    private static void play(Binding binding, SoundEvent sound, float pitch) {
        LivingEntity target = binding.target;
        binding.level.playSound(null, target.getX(), target.getY(), target.getZ(), sound, SoundSource.PLAYERS, 1.0F, pitch);
    }

    private static void cues(Binding binding) {
        if (binding.age == CHAINS_SOUND_TICK) {
            play(binding, CrucifixRegistry.CHAINS.get(), binding.success ? 1.0F : 0.9F);
        }
        if (binding.age == CrucifixTiming.FORM_TICKS) {
            play(binding, CrucifixRegistry.TREMBLE.get(), binding.success ? 1.0F : 1.25F);
        }
    }

    private static void send(Binding binding, Phase phase) {
        CrucifixEffectPayload payload = new CrucifixEffectPayload(binding.player.getId(), binding.target.getId(),
                binding.display.getId(), phase, binding.success, binding.startTick, binding.bind, binding.tier);
        PacketDistributor.sendToPlayersTrackingEntity(binding.target, payload);
        if (binding.player.level() instanceof ServerLevel) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(binding.player, payload);
        }
    }
}
