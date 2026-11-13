package nadiendev.ThaumaturgeTechCompats.purity.biome;

import nadiendev.ThaumaturgeTechCompats.purity.SplashPotionItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;

public class BiomePurificationPotionItem extends SplashPotionItem {
    public BiomePurificationPotionItem(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isEnabled() {
        return BiomePurificationConfig.enabled();
    }

    @Override
    protected int cooldownTicks() {
        return BiomePurificationConfig.useCooldown();
    }

    @Override
    protected ThrowableItemProjectile createProjectile(Level level, LivingEntity owner) {
        return new ThrownBiomePurificationPotion(level, owner);
    }

    @Override
    protected ThrowableItemProjectile createProjectile(Level level, double x, double y, double z) {
        return new ThrownBiomePurificationPotion(level, x, y, z);
    }
}
