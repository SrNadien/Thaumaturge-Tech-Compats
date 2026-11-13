package nadiendev.ThaumaturgeTechCompats.planetary.crucifix.client;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class CrucifixClientSetup {
    private CrucifixClientSetup() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CrucifixRegistry.DISPLAY.get(), CrucifixDisplayRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(CrucifixRegistry.STAR_SPARKLE.get(), StarSparkleParticle.Provider::new);
        event.registerSpriteSet(CrucifixRegistry.STAR_FRAGMENT.get(), StarSparkleParticle.FragmentProvider::new);
    }
}
