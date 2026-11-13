package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public final class PlanetSwarm {
    private static final double POINT_LIFT = 1.0D;

    private PlanetSwarm() {
    }

    public static boolean valid(ServerLevel level, UUID ownerId, LivingEntity victim) {
        if (!victim.isAlive() || victim.isSpectator() || victim instanceof ArmorStand) {
            return false;
        }
        if (ownerId != null && victim.getUUID().equals(ownerId)) {
            return PearlConfig.includeThrower();
        }
        if (victim instanceof Player player && (!PearlConfig.hitPlayers() || player.isCreative())) {
            return false;
        }
        Player owner = ownerId == null ? null : level.getPlayerByUUID(ownerId);
        if (owner != null && owner.isAlliedTo(victim)) {
            return false;
        }
        return !(PearlConfig.sparePets() && ownerId != null && victim instanceof TamableAnimal pet && pet.getOwnerReference() != null && ownerId.equals(pet.getOwnerReference().getUUID()));
    }

    public static LivingEntity findTarget(ServerLevel level, UUID ownerId, Vec3 at, double radius) {
        List<LivingEntity> candidates = level.getEntitiesOfClass(LivingEntity.class, new AABB(at, at).inflate(radius),
                living -> valid(level, ownerId, living) && living.getBoundingBox().distanceToSqr(at) <= radius * radius);
        return candidates.stream().min(Comparator.comparingDouble(living -> living.getBoundingBox().distanceToSqr(at))).orElse(null);
    }

    public static int summon(ServerLevel level, UUID ownerId, PlanetaryTier tier, LivingEntity target, Vec3 anchor) {
        int wanted = PearlConfig.planets(tier);
        int active = level.getEntities(EntityTypeTest.forClass(PlanetEntity.class), planet -> true).size();
        int count = Math.max(0, Math.min(wanted, PearlConfig.maxActive() - active));
        if (count == 0) {
            return 0;
        }
        Vec3 center = target != null ? target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D) : anchor.add(0.0D, POINT_LIFT, 0.0D);
        float halfWidth = target != null ? target.getBbWidth() * 0.5F : 0.0F;
        long born = level.getGameTime();
        int duration = PearlConfig.duration(tier);
        float radius = (float) PearlConfig.radius(tier);
        float speed = (float) PearlConfig.orbitSpeed();
        for (int index = 0; index < count; index++) {
            PlanetEntity planet = new PlanetEntity(PearlRegistry.PLANET.get(), level);
            planet.configure(tier, index, count, target != null ? target.getId() : -1, center, center, halfWidth, born, duration, radius, speed, ownerId);
            level.addFreshEntity(planet);
        }
        level.sendParticles(ParticleTypes.END_ROD, center.x, center.y, center.z, 24, 0.5D, 0.5D, 0.5D, 0.15D);
        if (PearlConfig.sounds()) {
            level.playSound(null, center.x, center.y, center.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, PearlConfig.soundVolume(), 1.6F);
        }
        return count;
    }
}
