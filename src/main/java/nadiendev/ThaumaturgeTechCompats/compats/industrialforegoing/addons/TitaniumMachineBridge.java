package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import java.lang.reflect.Method;

final class TitaniumMachineBridge {
    private static final String MACHINE_CLASS = "com.hrznstudio.titanium.block.tile.MachineTile";

    private static boolean resolved;
    private static Class<?> machineClass;
    private static Method canAcceptAugment;
    private static Method getAugmentInventory;

    private TitaniumMachineBridge() {
    }

    static boolean install(BlockEntity blockEntity, ItemStack stack) {
        if (blockEntity == null || !resolve() || !machineClass.isInstance(blockEntity)) {
            return false;
        }
        try {
            if (!(Boolean) canAcceptAugment.invoke(blockEntity, stack)) {
                return false;
            }
            if (!(getAugmentInventory.invoke(blockEntity) instanceof IItemHandlerModifiable inventory)) {
                return false;
            }
            for (int slot = 0; slot < inventory.getSlots(); slot++) {
                if (inventory.getStackInSlot(slot).isEmpty()) {
                    inventory.setStackInSlot(slot, stack);
                    return true;
                }
            }
        } catch (ReflectiveOperationException | RuntimeException exception) {
            return false;
        }
        return false;
    }

    private static boolean resolve() {
        if (!resolved) {
            resolved = true;
            try {
                machineClass = Class.forName(MACHINE_CLASS, false, TitaniumMachineBridge.class.getClassLoader());
                canAcceptAugment = machineClass.getMethod("canAcceptAugment", ItemStack.class);
                getAugmentInventory = machineClass.getMethod("getAugmentInventory");
            } catch (ReflectiveOperationException | LinkageError exception) {
                machineClass = null;
            }
        }
        return machineClass != null;
    }
}
