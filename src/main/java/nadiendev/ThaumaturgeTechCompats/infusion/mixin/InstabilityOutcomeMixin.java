package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.content.infusion.instability.InstabilityOutcome;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionOutcomes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(InstabilityOutcome.class)
public abstract class InstabilityOutcomeMixin {
    @Inject(method = "roll", at = @At("HEAD"), cancellable = true, require = 0)
    private static void thaumaturgetechcompats$filteredRoll(RegistryAccess access, RandomSource random, CallbackInfoReturnable<Optional<InstabilityOutcome>> cir) {
        cir.setReturnValue(InfusionOutcomes.roll(access, random));
    }
}
