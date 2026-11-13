package nadiendev.ThaumaturgeTechCompats.purity;

import com.leclowndu93150.thaumaturge.api.entity.trait.MobTrait;
import com.leclowndu93150.thaumaturge.api.entity.trait.MobTraits;
import com.leclowndu93150.thaumaturge.registry.TTMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public final class PurityCure {
    private PurityCure() {
    }

    public static int cureArea(ServerLevel level, Entity center) {
        if (!PurityConfig.enabled()) {
            return 0;
        }
        double radius = PurityConfig.radius();
        AABB box = center.getBoundingBox().inflate(radius, radius * 0.5D, radius);
        int cured = 0;
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, box)) {
            if (entity.distanceToSqr(center) > radius * radius || !isTarget(entity)) {
                continue;
            }
            if (!isInfected(entity) || level.getRandom().nextDouble() >= PurityConfig.cureChance()) {
                continue;
            }
            if (cure(level, entity)) {
                cured++;
            }
        }
        return cured;
    }

    public static boolean isInfected(LivingEntity entity) {
        if (!taintTraits(entity).isEmpty()) {
            return true;
        }
        return PurityConfig.removeFluxEffects() && !fluxEffects(entity).isEmpty();
    }

    public static boolean cure(ServerLevel level, LivingEntity entity) {
        boolean changed = false;
        for (Holder<MobTrait> trait : taintTraits(entity)) {
            changed |= MobTraits.remove(entity, trait);
        }
        if (PurityConfig.removeFluxEffects()) {
            for (Holder<MobEffect> effect : fluxEffects(entity)) {
                changed |= entity.removeEffect(effect);
            }
        }
        if (!changed) {
            return false;
        }
        float heal = PurityConfig.healAmount();
        if (heal > 0.0F && !(entity instanceof Player)) {
            entity.heal(heal);
        }
        if (entity.getHealth() > entity.getMaxHealth()) {
            entity.setHealth(entity.getMaxHealth());
        }
        if (PurityConfig.particles()) {
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, entity.getX(), entity.getY(0.5D), entity.getZ(),
                    12, entity.getBbWidth() * 0.5D, entity.getBbHeight() * 0.4D, entity.getBbWidth() * 0.5D, 0.0D);
        }
        return true;
    }

    private static boolean isTarget(LivingEntity entity) {
        String id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
        if (PurityConfig.blacklist().contains(id)) {
            return false;
        }
        if (entity instanceof Player) {
            return PurityConfig.curePlayers();
        }
        if (entity instanceof OwnableEntity ownable && ownable.getOwnerReference() != null) {
            return PurityConfig.curePets();
        }
        if (entity instanceof Enemy) {
            return PurityConfig.cureMonsters();
        }
        return PurityConfig.curePeaceful();
    }

    private static List<Holder<MobTrait>> taintTraits(LivingEntity entity) {
        List<Holder<MobTrait>> found = new ArrayList<>();
        for (Holder<MobTrait> trait : MobTraits.traits(entity)) {
            if (trait.value().isTaint()) {
                found.add(trait);
            }
        }
        return found;
    }

    private static List<Holder<MobEffect>> fluxEffects(LivingEntity entity) {
        List<Holder<MobEffect>> found = new ArrayList<>();
        if (entity.hasEffect(TTMobEffects.FLUX_TAINT)) {
            found.add(TTMobEffects.FLUX_TAINT);
        }
        if (entity.hasEffect(TTMobEffects.INFECTIOUS_VIS_EXHAUST)) {
            found.add(TTMobEffects.INFECTIOUS_VIS_EXHAUST);
        }
        return found;
    }
}
