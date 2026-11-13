package nadiendev.ThaumaturgeTechCompats.focus.client;

import com.leclowndu93150.thaumaturge.client.color.FocusColorTint;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.focus.FocusItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, value = Dist.CLIENT)
public final class FocusClient {
    private FocusClient() {
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(new FocusColorTint(), FocusItems.MATRIX_FOCUS.get(), FocusItems.INFINITY_FOCUS.get(), FocusItems.BLAZE_CUBE_FOCUS.get());
    }
}
