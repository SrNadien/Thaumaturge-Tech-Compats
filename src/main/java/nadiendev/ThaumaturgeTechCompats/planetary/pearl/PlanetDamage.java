package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class PlanetDamage {
    public static final ResourceKey<DamageType> PLANETARY = key("planetary");
    public static final ResourceKey<DamageType> PLANETARY_TRUE = key("planetary_true");
    public static final String MESSAGE_ID = "planetary";

    private static final TagKey<EntityType<?>> BOSSES = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c", "bosses"));

    private PlanetDamage() {
    }

    private static ResourceKey<DamageType> key(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, name));
    }

    public static boolean isBoss(LivingEntity entity) {
        EntityType<?> type = entity.getType();
        if (entity instanceof WitherBoss || entity instanceof EnderDragon || type.is(BOSSES)) {
            return true;
        }
        String id = BuiltInRegistries.ENTITY_TYPE.getKey(type).toString();
        return PearlConfig.extraBosses().contains(id);
    }

    public static void hit(ServerLevel level, PlanetEntity planet, Player owner, LivingEntity victim, PlanetaryTier tier) {
        boolean killer = tier.killsBosses();
        ResourceKey<DamageType> typeKey = killer ? PLANETARY_TRUE : PLANETARY;
        DamageSource source = new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(typeKey), planet, owner);
        float amount = (float) PearlConfig.damage(tier);
        if (!killer && isBoss(victim)) {
            amount = Math.min(amount, (float) PearlConfig.bossCap(tier));
        }
        Vec3 motion = victim.getDeltaMovement();
        if (PearlConfig.resetInvulnerability()) {
            victim.invulnerableTime = 0;
        }
        victim.hurt(source, amount);
        Vec3 pushed = victim.getDeltaMovement();
        victim.setDeltaMovement(motion.add(pushed.subtract(motion).scale(PearlConfig.knockbackScale())));
        if (killer && PearlConfig.executeBosses() && victim.isAlive() && victim.getHealth() <= amount && !protectedPlayer(victim)) {
            victim.invulnerableTime = 0;
            victim.kill();
        }
        if (PearlConfig.hitParticles()) {
            Vec3 center = victim.getBoundingBox().getCenter();
            level.sendParticles(ParticleTypes.ENCHANTED_HIT, center.x, center.y, center.z, 6, 0.3D, 0.3D, 0.3D, 0.1D);
        }
    }

    private static boolean protectedPlayer(LivingEntity victim) {
        return victim instanceof Player player && (player.isCreative() || player.isSpectator());
    }
}
