package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;
import java.util.UUID;

public class PlanetEntity extends Entity {
    private static final EntityDataAccessor<Integer> TARGET = SynchedEntityData.defineId(PlanetEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(PlanetEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COUNT = SynchedEntityData.defineId(PlanetEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TIER = SynchedEntityData.defineId(PlanetEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(PlanetEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Long> BORN = SynchedEntityData.defineId(PlanetEntity.class, EntityDataSerializers.LONG);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(PlanetEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(PlanetEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Vector3f> ANCHOR = SynchedEntityData.defineId(PlanetEntity.class, EntityDataSerializers.VECTOR3);

    private static final double BASE_ANGULAR_SPEED = 0.11D;
    private static final double GOLDEN_ANGLE = 137.5D;
    private static final double PHASE_STEP = 47.0D;
    private static final int WARMUP = 6;
    private static final int FADE_TICKS = 8;
    private static final int SOUND_GAP = 8;

    private UUID ownerId;
    private Vec3 lastCenter;
    private float lastHalfWidth;
    private long lastSound = Long.MIN_VALUE;

    public PlanetEntity(EntityType<? extends PlanetEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public void configure(PlanetaryTier tier, int index, int count, int targetId, Vec3 anchor, Vec3 center, float halfWidth, long born, int duration, float radius, float speed, UUID owner) {
        entityData.set(TIER, tier.ordinal());
        entityData.set(INDEX, index);
        entityData.set(COUNT, count);
        entityData.set(TARGET, targetId);
        entityData.set(ANCHOR, anchor.toVector3f());
        entityData.set(BORN, born);
        entityData.set(DURATION, duration);
        entityData.set(RADIUS, radius);
        entityData.set(SPEED, speed);
        this.ownerId = owner;
        this.lastCenter = center;
        this.lastHalfWidth = halfWidth;
        placeAt(center, halfWidth, 0L);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TARGET, -1);
        builder.define(INDEX, 0);
        builder.define(COUNT, 1);
        builder.define(TIER, 0);
        builder.define(DURATION, 100);
        builder.define(BORN, 0L);
        builder.define(RADIUS, 2.0F);
        builder.define(SPEED, 1.0F);
        builder.define(ANCHOR, new Vector3f());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public Component getName() {
        return Component.translatable(body().nameKey());
    }

    public PlanetaryTier tier() {
        PlanetaryTier[] tiers = PlanetaryTier.values();
        return tiers[Math.max(0, Math.min(tiers.length - 1, entityData.get(TIER)))];
    }

    public int index() {
        return entityData.get(INDEX);
    }

    public PlanetBody body() {
        return PlanetBody.of(index());
    }

    public float fade(float partial) {
        float age = level().getGameTime() - entityData.get(BORN) + partial;
        float in = Math.min(1.0F, Math.max(0.0F, age / FADE_TICKS));
        float out = Math.min(1.0F, Math.max(0.0F, (entityData.get(DURATION) - age) / FADE_TICKS));
        return Math.min(in, out);
    }

    public static Vec3 orbitOffset(int index, int count, double baseRadius, double halfWidth, double speed, double age) {
        PlanetBody body = PlanetBody.of(index);
        double fraction = count <= 1 ? 0.5D : (double) index / (count - 1);
        double scale = 0.7D + 0.6D * fraction;
        double radius = baseRadius * scale + halfWidth;
        double omega = BASE_ANGULAR_SPEED * body.speed() * speed / Math.pow(scale, 0.75D);
        double theta = Math.toRadians(index * PHASE_STEP) + (body.retrograde() ? -omega : omega) * age;
        double inclination = Math.toRadians(body.inclination());
        double node = Math.toRadians(index * GOLDEN_ANGLE);
        double x = radius * Math.cos(theta);
        double z = radius * Math.sin(theta);
        double y = -z * Math.sin(inclination);
        double zi = z * Math.cos(inclination);
        return new Vec3(x * Math.cos(node) - zi * Math.sin(node), y, x * Math.sin(node) + zi * Math.cos(node));
    }

    private void placeAt(Vec3 center, float halfWidth, long age) {
        Vec3 point = center.add(orbitOffset(index(), entityData.get(COUNT), entityData.get(RADIUS), halfWidth, entityData.get(SPEED), age));
        setPos(point.x, point.y - getBbHeight() * 0.5D, point.z);
    }

    private Vec3 anchor() {
        Vector3f anchor = entityData.get(ANCHOR);
        return new Vec3(anchor.x(), anchor.y(), anchor.z());
    }

    private Vec3 center() {
        int id = entityData.get(TARGET);
        if (id < 0) {
            lastHalfWidth = 0.0F;
            return anchor();
        }
        Entity target = level().getEntity(id);
        if (target != null && target.isAlive()) {
            lastHalfWidth = target.getBbWidth() * 0.5F;
            lastCenter = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
            return lastCenter;
        }
        if (level().isClientSide) {
            return lastCenter != null ? lastCenter : anchor();
        }
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        long age = level().getGameTime() - entityData.get(BORN);
        if (level() instanceof ServerLevel server) {
            serverTick(server, age);
        } else {
            Vec3 center = center();
            if (center != null) {
                placeAt(center, lastHalfWidth, age);
            }
            trail();
        }
    }

    private void serverTick(ServerLevel server, long age) {
        PlanetaryTier tier = tier();
        if (age >= entityData.get(DURATION)) {
            dissipate(server, tier);
            return;
        }
        Vec3 center = center();
        if (center == null) {
            dissipate(server, tier);
            return;
        }
        placeAt(center, lastHalfWidth, age);
        int interval = PearlConfig.interval(tier);
        if (age >= WARMUP && (age + index()) % interval == 0) {
            strike(server, tier, age);
        }
    }

    private void strike(ServerLevel server, PlanetaryTier tier, long age) {
        Player owner = ownerId == null ? null : server.getPlayerByUUID(ownerId);
        boolean struck = false;
        int targetId = entityData.get(TARGET);
        if (targetId >= 0) {
            if (server.getEntity(targetId) instanceof LivingEntity living && PlanetSwarm.valid(server, ownerId, living)) {
                PlanetDamage.hit(server, this, owner, living, tier);
                struck = true;
            }
        } else {
            double radius = PearlConfig.pointHitRadius() + body().size() * 0.5D;
            List<LivingEntity> victims = server.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(radius),
                    living -> PlanetSwarm.valid(server, ownerId, living) && living.getBoundingBox().getCenter().distanceToSqr(getBoundingBox().getCenter()) <= radius * radius);
            for (LivingEntity living : victims) {
                PlanetDamage.hit(server, this, owner, living, tier);
                struck = true;
            }
        }
        if (struck && PearlConfig.sounds() && age - lastSound >= SOUND_GAP) {
            lastSound = age;
            server.playSound(null, getX(), getY(), getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS,
                    PearlConfig.soundVolume() * 0.6F, 0.6F + 0.12F * index());
        }
    }

    private void dissipate(ServerLevel server, PlanetaryTier tier) {
        Vec3 center = getBoundingBox().getCenter();
        Vector3f color = Vec3.fromRGB24(PearlColors.planetColor(tier, index())).toVector3f();
        server.sendParticles(new DustParticleOptions(color, 1.2F), center.x, center.y, center.z, 12, 0.2D, 0.2D, 0.2D, 0.05D);
        server.sendParticles(ParticleTypes.END_ROD, center.x, center.y, center.z, 6, 0.15D, 0.15D, 0.15D, 0.05D);
        if (index() == 0 && PearlConfig.sounds()) {
            server.playSound(null, center.x, center.y, center.z, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, PearlConfig.soundVolume() * 0.7F, 1.8F);
        }
        discard();
    }

    private void trail() {
        int density = PearlConfig.trailParticles();
        if (density <= 0) {
            return;
        }
        Vec3 center = getBoundingBox().getCenter();
        Vector3f color = Vec3.fromRGB24(PearlColors.planetColor(tier(), index())).toVector3f();
        DustParticleOptions dust = new DustParticleOptions(color, 0.8F);
        double spread = body().size() * 0.3D;
        for (int i = 0; i < density; i++) {
            level().addParticle(dust, center.x + random.nextGaussian() * spread, center.y + random.nextGaussian() * spread, center.z + random.nextGaussian() * spread, 0.0D, 0.0D, 0.0D);
        }
        if (tickCount % 4 == 0) {
            level().addParticle(ParticleTypes.END_ROD, center.x, center.y, center.z, 0.0D, 0.0D, 0.0D);
        }
    }
}
