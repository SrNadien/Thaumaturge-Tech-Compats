package nadiendev.ThaumaturgeTechCompats.flux;

import net.minecraft.world.item.ItemStack;

public interface IFluxItem {
    long fluxCapacity(ItemStack stack);

    default long fluxStored(ItemStack stack) {
        return FluxComponents.stored(stack);
    }

    default long insertFlux(ItemStack stack, long amount, boolean simulate) {
        return FluxComponents.insert(stack, fluxCapacity(stack), amount, simulate);
    }

    default long extractFlux(ItemStack stack, long amount, boolean simulate) {
        return FluxComponents.extract(stack, amount, simulate);
    }
}
