package nadiendev.ThaumaturgeTechCompats.infusion.mixin;

import com.leclowndu93150.thaumaturge.content.infusion.InfusionDataMaps;
import com.leclowndu93150.thaumaturge.content.infusion.InfusionModifier;
import com.leclowndu93150.thaumaturge.content.infusion.MatrixEnvironment;
import nadiendev.ThaumaturgeTechCompats.infusion.AltarPart;
import nadiendev.ThaumaturgeTechCompats.infusion.AltarPiece;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionConfig;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionVariants;
import nadiendev.ThaumaturgeTechCompats.infusion.MatrixVariant;
import nadiendev.ThaumaturgeTechCompats.infusion.VariantStoneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MatrixEnvironment.class)
public abstract class MatrixEnvironmentMixin {
    @Inject(method = "survey", at = @At("RETURN"), cancellable = true, require = 0)
    private static void thaumaturgetechcompats$noSymmetry(Level level, BlockPos pos, CallbackInfoReturnable<MatrixEnvironment> cir) {
        if (InfusionVariants.matrixOf(level.getBlockState(pos)) == null) {
            return;
        }
        MatrixEnvironment environment = cir.getReturnValue();
        cir.setReturnValue(new MatrixEnvironment(environment.pedestals(), environment.cycleTime(), environment.costMult(), 0.0F));
    }

    @Inject(method = "modifier", at = @At("HEAD"), cancellable = true, require = 0)
    private static void thaumaturgetechcompats$variantModifier(BlockState state, DataMapType<Block, InfusionModifier> type, CallbackInfoReturnable<InfusionModifier> cir) {
        VariantStoneBlock stone = InfusionVariants.stoneOf(state);
        if (stone != null) {
            MatrixVariant variant = stone.variant();
            cir.setReturnValue(new InfusionModifier(
                    InfusionConfig.stoneInteger(variant, stone.kind(), "cycle_time_modifier"),
                    (float) InfusionConfig.stoneNumber(variant, stone.kind(), "cost_modifier"),
                    (float) InfusionConfig.stoneNumber(variant, stone.kind(), "stability_modifier")));
            return;
        }
        AltarPiece piece = InfusionVariants.altarOf(state);
        if (piece == null || !piece.part().modifies()) {
            return;
        }
        boolean matches = piece.part() == AltarPart.PILLAR
                ? type == InfusionDataMaps.PILLAR_SET
                : type == InfusionDataMaps.PEDESTAL;
        if (!matches) {
            cir.setReturnValue(null);
            return;
        }
        MatrixVariant variant = piece.variant();
        cir.setReturnValue(new InfusionModifier(
                InfusionConfig.altarInteger(variant, piece.part(), "cycle_time_modifier"),
                (float) InfusionConfig.altarNumber(variant, piece.part(), "cost_modifier"),
                (float) InfusionConfig.altarNumber(variant, piece.part(), "stability_modifier")));
    }
}
