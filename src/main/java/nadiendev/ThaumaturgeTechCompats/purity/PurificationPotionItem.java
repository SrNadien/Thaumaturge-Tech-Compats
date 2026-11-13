package nadiendev.ThaumaturgeTechCompats.purity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;

public class PurificationPotionItem extends SplashPotionItem {
    public PurificationPotionItem(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isEnabled() {
        return PurityConfig.enabled();
    }

    @Override
    protected int cooldownTicks() {
        return PurityConfig.useCooldown();
    }

    @Override
    protected ThrowableItemProjectile createProjectile(Level level, LivingEntity owner) {
        return new ThrownPurificationPotion(level, owner);
    }

    @Override
    protected ThrowableItemProjectile createProjectile(Level level, double x, double y, double z) {
        return new ThrownPurificationPotion(level, x, y, z);
    }
}
