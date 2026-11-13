package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.mixin;

import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage.StorageTierMaterials;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage.TierShulkerBoxBlock;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.p3pp3rf1y.sophisticatedstorage.client.render.ShulkerBoxRenderer", remap = false)
public abstract class ShulkerBoxRendererMixin {
    @Inject(method = "getTierMaterial", at = @At("HEAD"), cancellable = true)
    private void thaumaturgetechcompats$tierMaterial(Block block, CallbackInfoReturnable<Material> cir) {
        if (block instanceof TierShulkerBoxBlock shulkerBox) {
            cir.setReturnValue(StorageTierMaterials.shulkerBox(shulkerBox.tier()));
        }
    }
}
