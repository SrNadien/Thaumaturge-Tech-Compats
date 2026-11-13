package nadiendev.ThaumaturgeTechCompats.mixin;

import com.leclowndu93150.thaumaturge.content.essentia.advancedfurnace.AdvancedAlchemicalFurnaceStructure;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancedAlchemicalFurnaceStructure.class)
public abstract class AdvancedAlchemicalFurnaceStructureMixin {
    @Inject(method = "disassembleAround", at = @At("HEAD"), require = 0)
    private static void avaritia$restoreSmelter(LevelAccessor level, BlockPos pos, CallbackInfo ci) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 0; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    cursor.setWithOffset(pos, dx, dy, dz);
                    DeviceTier tier = FurnaceIntegration.tierOf(level.getBlockState(cursor).getBlock());
                    if (tier != null) {
                        level.setBlock(cursor.immutable(), FurnaceIntegration.smelter(tier).defaultBlockState(), 3);
                        return;
                    }
                }
            }
        }
    }
}
