package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import mekanism.api.Action;
import mekanism.api.energy.IStrictEnergyHandler;
import net.minecraft.world.item.ItemStack;

public class MekanismToolHandler implements IStrictEnergyHandler {
    private final ItemStack stack;

    public MekanismToolHandler(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public int getEnergyContainerCount() {
        return 1;
    }

    @Override
    public long getEnergy(int container) {
        return container == 0 ? MekanismToolBehavior.stored(stack) : 0L;
    }

    @Override
    public void setEnergy(int container, long energy) {
        if (container == 0) {
            MekanismToolBehavior.store(stack, energy);
        }
    }

    @Override
    public long getMaxEnergy(int container) {
        return container == 0 ? MekanismToolBehavior.capacity(stack) : 0L;
    }

    @Override
    public long getNeededEnergy(int container) {
        return getMaxEnergy(container) - getEnergy(container);
    }

    @Override
    public long insertEnergy(int container, long amount, Action action) {
        if (container != 0 || amount <= 0L) {
            return amount;
        }
        long accepted = Math.min(amount, getNeededEnergy(0));
        if (accepted > 0L && action.execute()) {
            MekanismToolBehavior.store(stack, getEnergy(0) + accepted);
        }
        return amount - accepted;
    }

    @Override
    public long extractEnergy(int container, long amount, Action action) {
        if (container != 0 || amount <= 0L) {
            return 0L;
        }
        long extracted = Math.min(amount, getEnergy(0));
        if (extracted > 0L && action.execute()) {
            MekanismToolBehavior.store(stack, getEnergy(0) - extracted);
        }
        return extracted;
    }
}
