package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.mixin;

import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.gui.WideStorageLayout;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageBackgroundProperties;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = StorageScreenBase.class, remap = false)
public abstract class StorageScreenWideMixin {
    @Shadow
    protected StorageBackgroundProperties storageBackgroundProperties;

    @Shadow
    public abstract int getSlotsOnLine();

    private boolean thaumaturgetechcompats$wide() {
        StorageScreenBase<?> screen = (StorageScreenBase<?>) (Object) this;
        return WideStorageLayout.applies(screen.getMenu());
    }

    @Redirect(method = "updateDimensionsAndSlotPositions", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC,
            target = "Lnet/p3pp3rf1y/sophisticatedcore/common/gui/StorageBackgroundProperties;REGULAR_12_SLOT:Lnet/p3pp3rf1y/sophisticatedcore/common/gui/StorageBackgroundProperties;"), require = 0)
    private StorageBackgroundProperties thaumaturgetechcompats$regular() {
        return thaumaturgetechcompats$wide() ? WideStorageLayout.REGULAR : StorageBackgroundProperties.REGULAR_12_SLOT;
    }

    @Redirect(method = "updateDimensionsAndSlotPositions", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC,
            target = "Lnet/p3pp3rf1y/sophisticatedcore/common/gui/StorageBackgroundProperties;WIDER_12_SLOT:Lnet/p3pp3rf1y/sophisticatedcore/common/gui/StorageBackgroundProperties;"), require = 0)
    private StorageBackgroundProperties thaumaturgetechcompats$wider() {
        return thaumaturgetechcompats$wide() ? WideStorageLayout.WIDER : StorageBackgroundProperties.WIDER_12_SLOT;
    }

    @Redirect(method = {"updateDimensionsAndSlotPositions", "updateInventoryScrollPanel", "getNumberOfVisibleRows", "initUpgradeInventoryControls"},
            at = @At(value = "INVOKE", target = "Lnet/p3pp3rf1y/sophisticatedcore/common/gui/StorageContainerMenuBase;getNumberOfRows()I"), require = 0)
    private int thaumaturgetechcompats$rows(StorageContainerMenuBase<?> menu) {
        return WideStorageLayout.applies(menu) ? WideStorageLayout.rows(menu) : menu.getNumberOfRows();
    }

    @Inject(method = "drawInventoryBg", at = @At("HEAD"), cancellable = true, require = 0)
    private void thaumaturgetechcompats$drawBackground(GuiGraphics graphics, int x, int y, Identifier texture, CallbackInfo ci) {
        StorageScreenBase<?> screen = (StorageScreenBase<?>) (Object) this;
        if (WideStorageLayout.drawBackground(graphics, x, y, screen.getXSize(), screen.getYSize() - WideStorageLayout.HEIGHT_WITHOUT_STORAGE_SLOTS, texture)) {
            ci.cancel();
        }
    }

    @Inject(method = "drawSlotBg(Lnet/minecraft/client/gui/GuiGraphics;III)V", at = @At("HEAD"), cancellable = true, require = 0)
    private void thaumaturgetechcompats$drawSlots(GuiGraphics graphics, int x, int y, int visibleSlots, CallbackInfo ci) {
        if (WideStorageLayout.isWide(storageBackgroundProperties)) {
            WideStorageLayout.drawSlots(graphics, x, y, getSlotsOnLine(), visibleSlots);
            ci.cancel();
        }
    }
}
