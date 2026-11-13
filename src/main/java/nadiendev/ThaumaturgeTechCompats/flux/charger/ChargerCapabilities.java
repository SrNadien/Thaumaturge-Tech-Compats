package nadiendev.ThaumaturgeTechCompats.flux.charger;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxCapabilities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ChargerCapabilities {
    private ChargerCapabilities() {
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(FluxCapabilities.STORAGE, ChargerRegistry.ENTITY.get(), (charger, side) -> charger.storage());
    }
}
