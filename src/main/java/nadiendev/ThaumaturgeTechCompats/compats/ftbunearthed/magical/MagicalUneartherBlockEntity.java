package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import dev.ftb.mods.ftbunearthed.block.UneartherCoreBlockEntity;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePower;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.Optional;

public class MagicalUneartherBlockEntity extends UneartherCoreBlockEntity {
    private static final int FOOD_SLOT = 1;

    private final IItemHandler foodStub = new InertFoodHandler();
    private final IItemHandler itemHandler;
    private boolean powered;
    private int countdown;

    public MagicalUneartherBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.itemHandler = new FoodlessHandler(super.getItemHandler());
    }

    public boolean powered() {
        return powered;
    }

    @Override
    public void tickServer(ServerLevel level) {
        MagicalUneartherTier tier = tier();
        boolean working = tier != null && getCurrentRecipe().isPresent() && !getInputStack().isEmpty();
        if (!working) {
            powered = false;
            countdown = 0;
        } else if (countdown <= 0) {
            countdown = MagicalUneartherConfig.interval();
            powered = acquire(level, tier);
        } else {
            countdown--;
        }
        if (working && !powered && MagicalUneartherConfig.stopWithoutSource()) {
            return;
        }
        super.tickServer(level);
    }

    @Override
    public boolean hasSuperBrush() {
        return powered || super.hasSuperBrush();
    }

    @Override
    public int getEffectiveSpeedBoost() {
        int real = super.hasSuperBrush() ? super.getEffectiveSpeedBoost() : 0;
        MagicalUneartherTier tier = tier();
        if (!powered || tier == null) {
            return real;
        }
        return Math.max(real, MagicalUneartherConfig.speedBoost(tier));
    }

    @Override
    public IItemHandler getFoodHandler() {
        return foodStub;
    }

    @Override
    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    private MagicalUneartherTier tier() {
        return getBlockState().getBlock() instanceof MagicalUneartherBlock block ? block.tier() : null;
    }

    private boolean acquire(ServerLevel level, MagicalUneartherTier tier) {
        Optional<NodeSource> found = NodePower.find(level, getBlockPos(), MagicalUneartherConfig.radius(tier), MagicalUneartherConfig.sources());
        if (found.isEmpty()) {
            return false;
        }
        NodeSource source = found.get();
        int cost = MagicalUneartherConfig.cost(tier);
        return source.drain(cost, true) >= cost && source.drain(cost, false) >= cost;
    }

    private static final class InertFoodHandler extends ItemStackHandler {
        private InertFoodHandler() {
            super(1);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return stack;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 0;
        }
    }

    private static final class FoodlessHandler implements IItemHandler {
        private final IItemHandler delegate;

        private FoodlessHandler(IItemHandler delegate) {
            this.delegate = delegate;
        }

        @Override
        public int getSlots() {
            return delegate.getSlots();
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return delegate.getStackInSlot(slot);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return slot == FOOD_SLOT ? stack : delegate.insertItem(slot, stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return delegate.extractItem(slot, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return delegate.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot != FOOD_SLOT && delegate.isItemValid(slot, stack);
        }
    }
}
