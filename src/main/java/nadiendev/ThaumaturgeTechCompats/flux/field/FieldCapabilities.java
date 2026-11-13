package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxCapabilities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID)
public final class FieldCapabilities {
    private FieldCapabilities() {
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(FluxCapabilities.STORAGE, FieldRegistry.SHIELD_ENTITY.get(), (shield, side) -> shield.storage());
        event.registerBlockEntity(FluxCapabilities.STORAGE, FieldRegistry.TERRAFORMER_ENTITY.get(), (terraformer, side) -> terraformer.storage());
    }
}
