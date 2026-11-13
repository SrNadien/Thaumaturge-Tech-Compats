package nadiendev.ThaumaturgeTechCompats.purity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownPurificationPotion extends ThrowableItemProjectile {
    public static final int SPLASH_COLOR = 0x8FE3FF;

    public ThrownPurificationPotion(EntityType<? extends ThrownPurificationPotion> type, Level level) {
        super(type, level);
    }

    public ThrownPurificationPotion(Level level, LivingEntity owner) {
        super(PurityRegistry.THROWN_POTION.get(), owner, level);
    }

    public ThrownPurificationPotion(Level level, double x, double y, double z) {
        super(PurityRegistry.THROWN_POTION.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return PurityRegistry.PURIFICATION_POTION.get();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.05D;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (level() instanceof ServerLevel serverLevel) {
            PurityCure.cureArea(serverLevel, this);
            serverLevel.levelEvent(2002, blockPosition(), SPLASH_COLOR);
            discard();
        }
    }
}
