package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

public class CrucifixDisplayEntity extends Entity {
    private static final EntityDataAccessor<Integer> TIER = SynchedEntityData.defineId(CrucifixDisplayEntity.class, EntityDataSerializers.INT);

    public CrucifixDisplayEntity(EntityType<? extends CrucifixDisplayEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public PlanetaryTier tier() {
        PlanetaryTier[] tiers = PlanetaryTier.values();
        return tiers[Mth.clamp(this.entityData.get(TIER), 0, tiers.length - 1)];
    }

    public void setTier(PlanetaryTier tier) {
        this.entityData.set(TIER, tier.ordinal());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TIER, PlanetaryTier.CREATIVE.ordinal());
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(Vec3.ZERO);
        yRotO = getYRot();
        setYRot(CrucifixTiming.spin(tickCount));
        if (level().isClientSide()) {
            if (random.nextInt(3) == 0) {
                double angle = random.nextDouble() * Mth.TWO_PI;
                level().addParticle(CrucifixRegistry.STAR_SPARKLE.get(), getX() + Math.cos(angle) * 0.35,
                        getY() + random.nextDouble() * CrucifixTiming.DISPLAY_SIZE * 1.4, getZ() + Math.sin(angle) * 0.35,
                        0.0, 0.01, 0.0);
            }
        } else if (tickCount > 1 && !StarCrucifix.ownsDisplay(this)) {
            discard();
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean skipAttackInteraction(Entity attacker) {
        return true;
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
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canChangeDimensions(Level from, Level to) {
        return false;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 64.0 * 64.0;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }
}
