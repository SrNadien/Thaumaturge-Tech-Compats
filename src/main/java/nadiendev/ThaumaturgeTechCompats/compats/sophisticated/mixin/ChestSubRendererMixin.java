package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.mixin;

import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage.StorageTierMaterials;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage.TierChestBlock;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.p3pp3rf1y.sophisticatedstorage.client.render.ChestRenderer$ChestSubRenderer", remap = false)
public abstract class ChestSubRendererMixin {
    @Shadow
    @Final
    private ChestType chestType;

    @Inject(method = "getTierMaterial", at = @At("HEAD"), cancellable = true)
    private void thaumaturgetechcompats$tierMaterial(Block block, CallbackInfoReturnable<Material> cir) {
        if (block instanceof TierChestBlock chest) {
            cir.setReturnValue(StorageTierMaterials.chest(chest.tier(), chestType));
        }
    }
}
