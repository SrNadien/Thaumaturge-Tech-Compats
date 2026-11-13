package nadiendev.ThaumaturgeTechCompats.ponder.client;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.createmod.ponder.api.client.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, value = Dist.CLIENT)
public final class PonderClient {
    private PonderClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new TuuPonderPlugin());
    }
}
