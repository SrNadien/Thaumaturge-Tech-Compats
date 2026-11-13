package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.api.recipe.InfusionCraftingTransaction;
import com.leclowndu93150.thaumaturge.content.infusion.BlockEntityInfusionMatrix;
import com.leclowndu93150.thaumaturge.content.infusion.InfusionCraftingTransactions;
import com.leclowndu93150.thaumaturge.content.infusion.InfusionInput;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionCraftingContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InfusionCraftingTransactions.class)
public abstract class InfusionCraftingTransactionsMixin {
    private static final String INSPECT = "inspect(Lcom/leclowndu93150/thaumaturge/content/infusion/BlockEntityInfusionMatrix;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Lcom/leclowndu93150/thaumaturge/content/infusion/InfusionInput;)Lcom/leclowndu93150/thaumaturge/api/recipe/InfusionCraftingTransaction$Inspection;";

    @Inject(method = INSPECT, at = @At("HEAD"), require = 0)
    private static void thaumaturgetechcompats$beginInspect(BlockEntityInfusionMatrix matrix, ServerLevel level, ServerPlayer player, InfusionInput input,
                                                            CallbackInfoReturnable<InfusionCraftingTransaction.Inspection> cir) {
        InfusionCraftingContext.begin(matrix.getBlockState());
    }

    @Inject(method = INSPECT, at = @At("RETURN"), require = 0)
    private static void thaumaturgetechcompats$endInspect(BlockEntityInfusionMatrix matrix, ServerLevel level, ServerPlayer player, InfusionInput input,
                                                          CallbackInfoReturnable<InfusionCraftingTransaction.Inspection> cir) {
        InfusionCraftingContext.end();
    }
}
