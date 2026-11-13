package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import com.leclowndu93150.thaumaturge.content.workbench.MenuArcaneWorkbench;
import com.leclowndu93150.thaumaturge.content.workbench.SlotCrystalEssentia;
import com.leclowndu93150.thaumaturge.content.workbench.SlotWorkbenchWand;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.inventory.StatefulComponentItemHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

import java.util.function.Consumer;

public class ArcaneUpgradeWrapper extends UpgradeWrapperBase<ArcaneUpgradeWrapper, ArcaneUpgradeItem> {
    public static final int GRID_SLOTS = 9;
    public static final int CRYSTAL_SLOTS = 6;
    public static final int WAND_INDEX = GRID_SLOTS + CRYSTAL_SLOTS;
    public static final int CHARGER_INDEX = WAND_INDEX + 1;
    public static final int TOTAL_SLOTS = CHARGER_INDEX + 1;

    private final StatefulComponentItemHandler inventory;

    public ArcaneUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
        super(storageWrapper, upgrade, upgradeSaveHandler);

        if (upgrade.has(DataComponents.CONTAINER)) {
            upgrade.set(ModCoreDataComponents.LENIENT_CONTAINER, upgrade.get(DataComponents.CONTAINER));
        }
        upgrade.remove(DataComponents.CONTAINER);
        inventory = new StatefulComponentItemHandler(upgrade, ModCoreDataComponents.LENIENT_CONTAINER.get(), TOTAL_SLOTS) {
            @Override
            protected void onContentsChanged(int slot, ItemStack oldStack, ItemStack newStack) {
                super.onContentsChanged(slot, oldStack, newStack);
                save();
            }

            @Override
            public int getSlotLimit(int slot) {
                return slot >= WAND_INDEX ? 1 : super.getSlotLimit(slot);
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                if (slot < GRID_SLOTS) {
                    return true;
                }
                if (slot == WAND_INDEX) {
                    return stack.isEmpty() || SlotWorkbenchWand.isUsableWand(stack);
                }
                if (slot == CHARGER_INDEX) {
                    return stack.isEmpty() || ChargerEffects.isCharger(stack);
                }
                return SlotCrystalEssentia.isValidCrystal(stack, MenuArcaneWorkbench.PRIMAL_ORDER.get(slot - GRID_SLOTS));
            }
        };
    }

    public StatefulComponentItemHandler getInventory() {
        return inventory;
    }

    @Override
    public boolean canBeDisabled() {
        return false;
    }

    public boolean shouldShiftClickIntoStorage() {
        return upgrade.getOrDefault(ModCoreDataComponents.SHIFT_CLICK_INTO_STORAGE, true);
    }

    public void setShiftClickIntoStorage(boolean shiftClickIntoStorage) {
        upgrade.set(ModCoreDataComponents.SHIFT_CLICK_INTO_STORAGE, shiftClickIntoStorage);
        save();
    }

    public boolean shouldRefillCraftingGridNBT() {
        return upgrade.getOrDefault(ModCoreDataComponents.REFILL_CRAFTING_GRID, false);
    }

    public void setRefillCraftingGridNBT(boolean replenish) {
        upgrade.set(ModCoreDataComponents.REFILL_CRAFTING_GRID, replenish);
        save();
    }

    public boolean extractFromStorageOrPlayer(Player player, ItemStack stack) {
        return extractFromStorage(stack) || extractFromPlayer(player, stack);
    }

    private boolean extractFromPlayer(Player player, ItemStack stack) {
        int index = player.getInventory().findSlotMatchingItem(stack);
        if (index >= 0) {
            player.getInventory().removeItem(index, 1);
            return true;
        }
        return false;
    }

    private boolean extractFromStorage(ItemStack stack) {
        return !InventoryHelper.extractFromInventory(s -> ItemStack.isSameItemSameComponents(s, stack), 1, storageWrapper.getInventoryHandler(), false)
                .isEmpty();
    }

    public boolean insertIntoStorageOrPlayer(Player player, ItemStack stack) {
        if (shouldShiftClickIntoStorage() && InventoryHelper.insertIntoInventory(stack, storageWrapper.getInventoryHandler(), false).isEmpty()) {
            return true;
        }
        return player.getInventory().add(stack);
    }
}
