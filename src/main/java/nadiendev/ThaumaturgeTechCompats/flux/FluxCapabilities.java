package nadiendev.ThaumaturgeTechCompats.flux;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;

public final class FluxCapabilities {
    public static final BlockCapability<IFluxStorage, Direction> STORAGE = BlockCapability.createSided(
            ThaumaturgeTechCompats.id("dimensional_flux"), IFluxStorage.class);

    private FluxCapabilities() {
    }
}
