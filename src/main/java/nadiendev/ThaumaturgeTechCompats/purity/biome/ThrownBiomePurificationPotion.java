package nadiendev.ThaumaturgeTechCompats.purity.biome;

import nadiendev.ThaumaturgeTechCompats.purity.PurityRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownBiomePurificationPotion extends ThrowableItemProjectile {
    public static final int SPLASH_COLOR = 0x7CF5B0;

    public ThrownBiomePurificationPotion(EntityType<? extends ThrownBiomePurificationPotion> type, Level level) {
        super(type, level);
    }

    public ThrownBiomePurificationPotion(Level level, LivingEntity owner) {
        super(PurityRegistry.THROWN_BIOME_POTION.get(), owner, level);
    }

    public ThrownBiomePurificationPotion(Level level, double x, double y, double z) {
        super(PurityRegistry.THROWN_BIOME_POTION.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return PurityRegistry.BIOME_POTION.get();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.05D;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (level() instanceof ServerLevel serverLevel) {
            BiomePurifier.purify(serverLevel, blockPosition());
            serverLevel.levelEvent(2002, blockPosition(), SPLASH_COLOR);
            discard();
        }
    }
}
