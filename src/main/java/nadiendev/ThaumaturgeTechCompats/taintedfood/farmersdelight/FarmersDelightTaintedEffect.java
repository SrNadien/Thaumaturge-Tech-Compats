package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import com.leclowndu93150.thaumaturge.registry.TTMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Optional;

public final class FarmersDelightTaintedEffect {
    private FarmersDelightTaintedEffect() {
    }

    public static MobEffectInstance create(ResourceLocation id, int duration, int amplifier) {
        Optional<Holder.Reference<MobEffect>> found = BuiltInRegistries.MOB_EFFECT.getHolder(id);
        Holder<MobEffect> holder = found.isPresent() ? found.get() : TTMobEffects.FLUX_TAINT;
        return new MobEffectInstance(holder, duration, amplifier);
    }
}
