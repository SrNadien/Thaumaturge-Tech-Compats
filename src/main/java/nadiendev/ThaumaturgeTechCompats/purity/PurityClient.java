package nadiendev.ThaumaturgeTechCompats.purity;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, value = Dist.CLIENT)
public final class PurityClient {
    private PurityClient() {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PurityRegistry.THROWN_POTION.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(PurityRegistry.THROWN_BIOME_POTION.get(), ThrownItemRenderer::new);
    }
}
