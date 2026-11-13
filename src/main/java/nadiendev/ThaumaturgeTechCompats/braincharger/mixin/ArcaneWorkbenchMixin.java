package nadiendev.ThaumaturgeTechCompats.braincharger.mixin;

import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import com.leclowndu93150.thaumaturge.content.workbench.BlockEntityArcaneWorkbench;
import nadiendev.ThaumaturgeTechCompats.braincharger.BoostTier;
import nadiendev.ThaumaturgeTechCompats.braincharger.ChargerBoost;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockEntityArcaneWorkbench.class, remap = false)
public abstract class ArcaneWorkbenchMixin {
    @Inject(method = "refreshAura", at = @At("TAIL"))
    private void thaumaturgetechcompats$refreshAura(CallbackInfo ci) {
        BlockEntityArcaneWorkbench workbench = (BlockEntityArcaneWorkbench) (Object) this;
        BoostTier tier = ChargerBoost.tierAbove(workbench);
        if (tier != null && workbench.getLevel() != null && !workbench.getLevel().isClientSide()) {
            workbench.auraVis = ChargerBoost.availableVis(workbench, tier);
        }
    }

    @Inject(method = "spendAura(ILnet/neoforged/neoforge/transfer/transaction/TransactionContext;)Z", at = @At("HEAD"), cancellable = true)
    private void thaumaturgetechcompats$spendAura(int amount, TransactionContext transaction, CallbackInfoReturnable<Boolean> cir) {
        BlockEntityArcaneWorkbench workbench = (BlockEntityArcaneWorkbench) (Object) this;
        BoostTier tier = ChargerBoost.tierAbove(workbench);
        if (tier != null) {
            cir.setReturnValue(ChargerBoost.spend(workbench, tier, amount, transaction));
        }
    }
}
