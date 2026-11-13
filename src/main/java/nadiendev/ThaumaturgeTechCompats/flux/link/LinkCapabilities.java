package nadiendev.ThaumaturgeTechCompats.flux.link;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxCapabilities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class LinkCapabilities {
    private LinkCapabilities() {
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(FluxCapabilities.STORAGE, LinkRegistry.LINK_ENTITY.get(), (link, side) -> link.storage());
        event.registerBlockEntity(FluxCapabilities.STORAGE, LinkRegistry.EXTRACTOR_ENTITY.get(), (extractor, side) -> extractor.output());
    }
}
