package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import com.leclowndu93150.thaumaturge.registry.TTMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Optional;

public final class FarmersDelightTaintedEffect {
    private FarmersDelightTaintedEffect() {
    }

    public static MobEffectInstance create(Identifier id, int duration, int amplifier) {
        Optional<Holder.Reference<MobEffect>> found = BuiltInRegistries.MOB_EFFECT.get(id);
        Holder<MobEffect> holder = found.isPresent() ? found.get() : TTMobEffects.FLUX_TAINT;
        return new MobEffectInstance(holder, duration, amplifier);
    }
}
