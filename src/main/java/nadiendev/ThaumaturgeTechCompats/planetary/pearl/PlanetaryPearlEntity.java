package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import org.joml.Vector3f;

import java.util.UUID;

public class PlanetaryPearlEntity extends ThrowableItemProjectile {
    public PlanetaryPearlEntity(EntityType<? extends PlanetaryPearlEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected Item getDefaultItem() {
        return PearlRegistry.pearl(PlanetaryTier.COPPER).get();
    }

    public PlanetaryTier tier() {
        return getItem().getItem() instanceof PlanetaryPearlItem pearl ? pearl.tier() : PlanetaryTier.COPPER;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide && PearlConfig.trailParticles() > 0) {
            Vector3f color = Vec3.fromRGB24(PearlColors.planetColor(tier(), tickCount)).toVector3f();
            level().addParticle(new DustParticleOptions(color, 1.0F), getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!(level() instanceof ServerLevel server) || isRemoved()) {
            return;
        }
        Entity struck = result.getEntity();
        Entity hit = struck instanceof PartEntity<?> part ? part.getParent() : struck;
        UUID ownerId = getOwner() == null ? null : getOwner().getUUID();
        if (hit instanceof LivingEntity living && PlanetSwarm.valid(server, ownerId, living)) {
            PlanetSwarm.summon(server, ownerId, tier(), living, result.getLocation());
            discard();
            return;
        }
        impactAt(server, result.getLocation());
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (level() instanceof ServerLevel server && !isRemoved()) {
            impactAt(server, result.getLocation());
        }
    }

    private void impactAt(ServerLevel server, Vec3 at) {
        UUID ownerId = getOwner() == null ? null : getOwner().getUUID();
        LivingEntity target = PlanetSwarm.findTarget(server, ownerId, at, PearlConfig.searchRadius());
        PlanetSwarm.summon(server, ownerId, tier(), target, at);
        discard();
    }
}
