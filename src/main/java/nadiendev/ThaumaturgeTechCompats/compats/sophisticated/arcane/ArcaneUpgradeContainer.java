package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.recipe.ArcaneCraftingTransaction;
import com.leclowndu93150.thaumaturge.api.recipe.ArcaneWorkbenchContext;
import com.leclowndu93150.thaumaturge.api.recipe.IArcaneCraftingStore;
import com.leclowndu93150.thaumaturge.api.transaction.Transaction;
import com.leclowndu93150.thaumaturge.api.transaction.TransactionContext;
import com.leclowndu93150.thaumaturge.content.recipe.workbench.ArcaneCraftingInput;
import com.leclowndu93150.thaumaturge.content.taint.item.ItemEssentiaCrystal;
import com.leclowndu93150.thaumaturge.registry.TTRecipeTypes;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedConfig;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.p3pp3rf1y.sophisticatedcore.common.gui.ICraftingContainer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SlotSuppliedHandler;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.crafting.CraftingItemHandler;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class ArcaneUpgradeContainer extends UpgradeContainerBase<ArcaneUpgradeWrapper, ArcaneUpgradeContainer> implements ICraftingContainer {
    public static final UUID HOST = UUID.nameUUIDFromBytes("thaumaturgetechcompats:sophisticated_arcane_workbench_upgrade".getBytes(StandardCharsets.UTF_8));
    public static final int GRID = ArcaneUpgradeWrapper.GRID_SLOTS;
    public static final int RESULT = GRID;
    public static final int CRYSTAL_START = GRID + 1;
    public static final int WAND_SLOT = CRYSTAL_START + ArcaneUpgradeWrapper.CRYSTAL_SLOTS;
    public static final int CHARGER_SLOT = WAND_SLOT + 1;

    private static final int GRID_SIZE = 3;
    private static final String DATA_SHIFT_CLICK_INTO_STORAGE = "shiftClickIntoStorage";
    private static final String DATA_REFILL_CRAFTING_GRID = "refill_crafting_grid";

    private final ResultContainer craftResult = new ResultContainer();
    private final CraftingItemHandler craftMatrix;
    private final ResultSlot craftingResultSlot;

    public ArcaneUpgradeContainer(Player player, int upgradeContainerId, ArcaneUpgradeWrapper upgradeWrapper,
                                  UpgradeContainerType<ArcaneUpgradeWrapper, ArcaneUpgradeContainer> type) {
        super(player, upgradeContainerId, upgradeWrapper, type);

        for (int slot = 0; slot < GRID; slot++) {
            slots.add(new SlotSuppliedHandler(supplyFromWrapper(ArcaneUpgradeWrapper::getInventory), slot, -100, -100) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    updateCraftingResult();
                    craftMatrix.setChanged();
                }

                @Override
                public boolean mayPickup(Player player) {
                    return getItem().isEmpty() || super.mayPickup(player);
                }
            });
        }
        craftMatrix = new CraftingItemHandler(supplyFromWrapper(ArcaneUpgradeWrapper::getInventory), this::onCraftMatrixChanged);
        craftingResultSlot = new ResultSlot(player, craftMatrix, craftResult, RESULT, -100, -100) {
            @Override
            public boolean mayPickup(Player thePlayer) {
                if (!super.mayPickup(thePlayer)) {
                    return false;
                }
                return !(thePlayer instanceof ServerPlayer serverPlayer) || preview(serverPlayer).successful();
            }

            @Override
            public void onTake(Player thePlayer, ItemStack stack) {
                if (!(thePlayer instanceof ServerPlayer serverPlayer)) {
                    return;
                }
                ItemStack remainingStack = getItem();
                checkTakeAchievements(stack);
                craft(serverPlayer);
                if (!remainingStack.isEmpty()) {
                    player.drop(remainingStack, false);
                }
                updateCraftingResult();
            }
        };
        slots.add(craftingResultSlot);
        for (int slot = GRID; slot < ArcaneUpgradeWrapper.TOTAL_SLOTS; slot++) {
            slots.add(new SlotSuppliedHandler(supplyFromWrapper(ArcaneUpgradeWrapper::getInventory), slot, -100, -100) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    updateCraftingResult();
                }
            });
        }
    }

    @Override
    public void onInit() {
        super.onInit();
        updateCraftingResult();
    }

    private void onCraftMatrixChanged(Container container) {
        updateCraftingResult();
    }

    private void updateCraftingResult() {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        ItemStack output = ItemStack.EMPTY;
        if (!gridIsEmpty()) {
            ArcaneCraftingTransaction.Result result = preview(serverPlayer);
            if (result.successful()) {
                output = result.output().copy();
            }
        }
        craftingResultSlot.set(output);
    }

    private boolean gridIsEmpty() {
        IItemHandlerModifiable inventory = upgradeWrapper.getInventory();
        for (int slot = 0; slot < GRID; slot++) {
            if (!inventory.getStackInSlot(slot).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private ArcaneCraftingInput.Positioned positioned() {
        IItemHandlerModifiable inventory = upgradeWrapper.getInventory();
        List<ItemStack> items = new ArrayList<>(GRID + ArcaneUpgradeWrapper.CRYSTAL_SLOTS + 1);
        for (int slot = 0; slot < ArcaneUpgradeWrapper.WAND_INDEX; slot++) {
            items.add(inventory.getStackInSlot(slot).copy());
        }
        items.add(SophisticatedConfig.wandSlotEnabled() ? inventory.getStackInSlot(ArcaneUpgradeWrapper.WAND_INDEX).copy() : ItemStack.EMPTY);
        return ArcaneCraftingInput.ofPositioned(GRID_SIZE, GRID_SIZE, items);
    }

    private ArcaneWorkbenchContext context(ServerPlayer serverPlayer) {
        return ArcaneWorkbenchContext.virtual(serverPlayer, HOST, serverPlayer.getUUID());
    }

    private ArcaneCraftingTransaction.Result preview(ServerPlayer serverPlayer) {
        ArcaneCraftingInput input = positioned().input().withPlayer(serverPlayer);
        return withCharger(() -> ArcaneCraftingTransaction.preview(context(serverPlayer), serverPlayer, input));
    }

    private boolean craft(ServerPlayer serverPlayer) {
        ArcaneCraftingInput.Positioned positioned = positioned();
        ArcaneCraftingInput input = positioned.input().withPlayer(serverPlayer);
        try (Transaction transaction = Transaction.openRoot()) {
            ArcaneCraftingTransaction.Result result = withCharger(() -> ArcaneCraftingTransaction.craft(context(serverPlayer), serverPlayer, input,
                    new GridStore(serverPlayer, positioned), transaction));
            if (result.successful()) {
                transaction.commit();
                return true;
            }
            return false;
        }
    }

    private <T> T withCharger(Supplier<T> action) {
        ArcaneUpgradeAuraSource.CHARGER.set(ChargerEffects.of(upgradeWrapper.getInventory().getStackInSlot(ArcaneUpgradeWrapper.CHARGER_INDEX)));
        try {
            return action.get();
        } finally {
            ArcaneUpgradeAuraSource.CHARGER.remove();
        }
    }

    @Override
    public void handlePacket(CompoundTag data) {
        if (data.contains(DATA_SHIFT_CLICK_INTO_STORAGE)) {
            setShiftClickIntoStorage(data.getBoolean(DATA_SHIFT_CLICK_INTO_STORAGE));
        }
        if (data.contains(DATA_REFILL_CRAFTING_GRID)) {
            setRefillCraftingGrid(data.getBoolean(DATA_REFILL_CRAFTING_GRID));
        }
    }

    @Override
    public ItemStack getSlotStackToTransfer(Slot slot) {
        if (slot == craftingResultSlot) {
            ItemStack slotStack = slot.getItem();
            slotStack.getItem().onCraftedBy(slotStack, player.level(), player);
            return slotStack;
        }
        return super.getSlotStackToTransfer(slot);
    }

    @Override
    public List<Slot> getRecipeSlots() {
        return slots.subList(0, GRID);
    }

    @Override
    public Container getCraftMatrix() {
        return craftMatrix;
    }

    @Override
    public void setRecipeUsed(ResourceLocation recipeId) {
    }

    @Override
    public RecipeType<?> getRecipeType() {
        return TTRecipeTypes.ARCANE.get();
    }

    public boolean shouldShiftClickIntoStorage() {
        return upgradeWrapper.shouldShiftClickIntoStorage();
    }

    public void setShiftClickIntoStorage(boolean shiftClickIntoStorage) {
        upgradeWrapper.setShiftClickIntoStorage(shiftClickIntoStorage);
        sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), DATA_SHIFT_CLICK_INTO_STORAGE, shiftClickIntoStorage));
    }

    @Override
    public boolean shouldRefillCraftingGrid() {
        return upgradeWrapper.shouldRefillCraftingGridNBT();
    }

    public void setRefillCraftingGrid(boolean replenish) {
        upgradeWrapper.setRefillCraftingGridNBT(replenish);
        sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), DATA_REFILL_CRAFTING_GRID, replenish));
    }

    @Override
    public boolean mergeIntoStorageFirst(Slot slot) {
        return !(slot instanceof ResultSlot) || shouldShiftClickIntoStorage();
    }

    @Override
    public boolean allowsPickupAll(Slot slot) {
        return slot != craftingResultSlot;
    }

    @Override
    public int getRepeatedQuickMoveLimit(Slot slot, ItemStack transferredStack) {
        return slot == craftingResultSlot && shouldRefillCraftingGrid() ? transferredStack.getMaxStackSize() : 0;
    }

    private final class GridStore implements IArcaneCraftingStore {
        private final ServerPlayer serverPlayer;
        private final ArcaneCraftingInput.Positioned positioned;

        private GridStore(ServerPlayer serverPlayer, ArcaneCraftingInput.Positioned positioned) {
            this.serverPlayer = serverPlayer;
            this.positioned = positioned;
        }

        @Override
        public boolean consume(Consumption consumption, TransactionContext transaction) {
            int width = positioned.input().width();
            int height = positioned.input().height();
            List<ItemStack> grid = consumption.grid();
            if (grid.size() != width * height) {
                return false;
            }
            IItemHandlerModifiable inventory = upgradeWrapper.getInventory();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (!ItemStack.matches(inventory.getStackInSlot(slotOf(x, y)), grid.get(y * width + x))) {
                        return false;
                    }
                }
            }
            int[] crystalTaken = new int[ArcaneUpgradeWrapper.CRYSTAL_SLOTS];
            if (!planCrystals(consumption, inventory, crystalTaken)) {
                return false;
            }
            for (int index = 0; index < crystalTaken.length; index++) {
                if (crystalTaken[index] > 0) {
                    ItemStack crystal = inventory.getStackInSlot(GRID + index);
                    inventory.setStackInSlot(GRID + index, shrunk(crystal, crystalTaken[index]));
                }
            }
            if (SophisticatedConfig.wandSlotEnabled() && !ItemStack.matches(inventory.getStackInSlot(ArcaneUpgradeWrapper.WAND_INDEX), consumption.wand())) {
                inventory.setStackInSlot(ArcaneUpgradeWrapper.WAND_INDEX, consumption.wand().copy());
            }
            List<ItemStack> remainders = consumption.remainders();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int slot = slotOf(x, y);
                    ItemStack current = inventory.getStackInSlot(slot);
                    ItemStack remainder = y * width + x < remainders.size() ? remainders.get(y * width + x).copy() : ItemStack.EMPTY;
                    inventory.setStackInSlot(slot, afterCraft(current, remainder));
                }
            }
            return true;
        }

        private int slotOf(int x, int y) {
            return (positioned.top() + y) * GRID_SIZE + positioned.left() + x;
        }

        private boolean planCrystals(Consumption consumption, IItemHandlerModifiable inventory, int[] taken) {
            for (AspectInstance needed : consumption.crystals().entries()) {
                int remaining = needed.amount();
                String tag = needed.aspect().value().tag();
                for (int index = 0; index < ArcaneUpgradeWrapper.CRYSTAL_SLOTS && remaining > 0; index++) {
                    ItemStack crystal = inventory.getStackInSlot(GRID + index);
                    if (crystal.isEmpty() || !(crystal.getItem() instanceof ItemEssentiaCrystal)) {
                        continue;
                    }
                    Holder<IAspect> aspect = ItemEssentiaCrystal.aspectOf(crystal);
                    if (aspect == null || !aspect.value().tag().equals(tag)) {
                        continue;
                    }
                    int amount = Math.min(remaining, crystal.getCount() - taken[index]);
                    if (amount > 0) {
                        taken[index] += amount;
                        remaining -= amount;
                    }
                }
                if (remaining > 0) {
                    return false;
                }
            }
            return true;
        }

        private ItemStack afterCraft(ItemStack current, ItemStack remainder) {
            ItemStack next;
            if (!remainder.isEmpty() || current.isEmpty()) {
                next = shrunk(current, 1);
            } else if (shouldRefillCraftingGrid() && upgradeWrapper.extractFromStorageOrPlayer(serverPlayer, current)) {
                next = current;
            } else {
                next = shrunk(current, 1);
            }
            if (remainder.isEmpty()) {
                return next;
            }
            if (next.isEmpty()) {
                return remainder;
            }
            if (ItemStack.isSameItemSameComponents(next, remainder)) {
                ItemStack merged = remainder.copy();
                merged.grow(next.getCount());
                return merged;
            }
            if (!serverPlayer.getInventory().add(remainder)) {
                serverPlayer.drop(remainder, false);
            }
            return next;
        }

        private ItemStack shrunk(ItemStack stack, int amount) {
            if (stack.getCount() <= amount) {
                return ItemStack.EMPTY;
            }
            return stack.copyWithCount(stack.getCount() - amount);
        }
    }
}
