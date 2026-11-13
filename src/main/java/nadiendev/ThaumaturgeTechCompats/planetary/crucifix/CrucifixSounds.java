package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public final class CrucifixSounds {
    public static final boolean PREFER_CUSTOM_ROAR = false;

    @Nullable
    private static final Method AMBIENT = method(Mob.class, "getAmbientSound");
    @Nullable
    private static final Method HURT = method(LivingEntity.class, "getHurtSound", DamageSource.class);

    private CrucifixSounds() {
    }

    public static void strain(ServerLevel level, LivingEntity boss, double strain) {
        play(level, boss.position(), CrucifixRegistry.STRAIN.get(), 0.8F + 0.6F * (float) strain, 0.7F + 0.5F * (float) strain);
    }

    public static void roar(ServerLevel level, LivingEntity boss) {
        SoundEvent voice = PREFER_CUSTOM_ROAR ? null : voice(boss);
        if (voice != null) {
            level.playSound(null, boss.getX(), boss.getY(), boss.getZ(), voice, SoundSource.HOSTILE, 3.0F, CrucifixTiming.ROAR_PITCH);
        } else {
            level.playSound(null, boss.getX(), boss.getY(), boss.getZ(), CrucifixRegistry.ROAR.get(), SoundSource.HOSTILE, 3.0F, 1.0F);
        }
    }

    public static void snap(ServerLevel level, LivingEntity boss, int chain) {
        play(level, boss.position(), CrucifixRegistry.SHATTER.get(), 1.2F, 0.9F + 0.15F * chain);
    }

    public static void crack(ServerLevel level, Vec3 at) {
        play(level, at, CrucifixRegistry.CRACK.get(), 1.5F, 1.0F);
    }

    public static void explode(ServerLevel level, Vec3 at) {
        play(level, at, CrucifixRegistry.EXPLODE.get(), 2.5F, 1.0F);
    }

    private static void play(ServerLevel level, Vec3 at, SoundEvent sound, float volume, float pitch) {
        level.playSound(null, at.x, at.y, at.z, sound, SoundSource.PLAYERS, volume, pitch);
    }

    @Nullable
    private static SoundEvent voice(LivingEntity boss) {
        try {
            if (boss instanceof Mob && AMBIENT != null && AMBIENT.invoke(boss) instanceof SoundEvent ambient) {
                return ambient;
            }
            if (HURT != null && HURT.invoke(boss, boss.damageSources().generic()) instanceof SoundEvent hurt) {
                return hurt;
            }
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return null;
        }
        return null;
    }

    @Nullable
    private static Method method(Class<?> owner, String name, Class<?>... parameters) {
        try {
            Method method = owner.getDeclaredMethod(name, parameters);
            method.setAccessible(true);
            return method;
        } catch (ReflectiveOperationException | RuntimeException e) {
            return null;
        }
    }
}
