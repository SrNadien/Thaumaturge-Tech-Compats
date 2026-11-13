package nadiendev.ThaumaturgeTechCompats.mixin;

import com.leclowndu93150.thaumaturge.content.essentia.advancedfurnace.BlockEntityAdvancedAlchemicalFurnace;
import nadiendev.ThaumaturgeTechCompats.device.DeviceConfig;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityAdvancedAlchemicalFurnace.class)
public abstract class BlockEntityAdvancedAlchemicalFurnaceMixin {
    private static final int HEAT_PER_POINT = 2;

    @Shadow
    private int cooldown;

    @Shadow
    private int cycleDuration;

    @Shadow
    private int heat;

    @Shadow
    private int perditio;

    @Shadow
    private int aqua;

    @Unique
    private int avaritia$cost;

    @Shadow
    public abstract int inputCost();

    @Inject(method = "tryProcessInput", at = @At("HEAD"), require = 0)
    private void avaritia$rememberCost(ServerLevel level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        this.avaritia$cost = this.inputCost();
    }

    @Inject(method = "tryProcessInput", at = @At("RETURN"), require = 0)
    private void avaritia$tieredCycle(ServerLevel level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() || !DeviceConfig.furnacesEnabled()) {
            return;
        }
        DeviceTier tier = FurnaceIntegration.tierOf(((BlockEntity) (Object) this).getBlockState().getBlock());
        if (tier == null) {
            return;
        }
        int refund = this.avaritia$cost * DeviceConfig.furnaceSavings(tier) / 100;
        int max = BlockEntityAdvancedAlchemicalFurnace.MAX_POWER;
        this.heat = Math.min(max, this.heat + refund * HEAT_PER_POINT);
        this.perditio = Math.min(max, this.perditio + refund);
        this.aqua = Math.min(max, this.aqua + refund);
        this.cooldown = Math.max(1, this.cooldown / DeviceConfig.furnaceSpeed(tier));
        this.cycleDuration = this.cooldown;
    }
}
