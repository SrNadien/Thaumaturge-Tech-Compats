package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public final class CrucifixDamageTypes {
    public static final String NAME = "banished";
    public static final ResourceKey<DamageType> BANISHED = ResourceKey.create(Registries.DAMAGE_TYPE, ThaumaturgeTechCompats.id(NAME));

    private CrucifixDamageTypes() {
    }

    public static DamageSource banished(Level level, @Nullable Entity banisher) {
        return level.damageSources().source(BANISHED, banisher);
    }
}
