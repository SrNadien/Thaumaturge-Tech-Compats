package nadiendev.ThaumaturgeTechCompats.compats.enderio.mixin;

import com.enderio.enderio.content.machines.capacitor_bank.CapacitorTier;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.CapacitorConfig;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank.CapacitorBankSupport;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(value = CapacitorTier.class, remap = false)
public abstract class CapacitorTierMixin {
    @Shadow
    @Final
    @Mutable
    private static CapacitorTier[] $VALUES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void thaumaturgetechcompats$addTiers(CallbackInfo ci) {
        if (!CapacitorBankSupport.enabled()) {
            return;
        }
        List<CapacitorTier> tiers = new ArrayList<>(Arrays.asList($VALUES));
        for (EnderIOTier tier : EnderIOTier.values()) {
            tiers.add(CapacitorTierInvoker.thaumaturgetechcompats$create(tier.name(), tiers.size(), CapacitorConfig.bankCapacity(tier)));
        }
        $VALUES = tiers.toArray(new CapacitorTier[0]);
    }
}
