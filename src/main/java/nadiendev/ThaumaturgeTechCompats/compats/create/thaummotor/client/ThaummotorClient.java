package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor.client;

import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor.ThaummotorBlockEntity;
import nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor.ThaummotorRegistry;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ThaummotorClient {
    private ThaummotorClient() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        if (!CreateIntegration.isLoaded()) {
            return;
        }
        event.registerBlockEntityRenderer(ThaummotorRegistry.THAUMMOTOR_ENTITY.get(), ThaummotorRenderer::new);
    }

    @SubscribeEvent
    public static void registerPartialModels(ModelEvent.RegisterAdditional event) {
        if (!CreateIntegration.isLoaded()) {
            return;
        }
        event.register(ModelResourceLocation.standalone(ThaummotorPartials.SHAFT_HALF.modelLocation()));
    }

    @SubscribeEvent
    public static void registerVisual(FMLClientSetupEvent event) {
        if (!CreateIntegration.isLoaded()) {
            return;
        }
        event.enqueueWork(() -> {
            SimpleBlockEntityVisualizer.<ThaummotorBlockEntity>builder(ThaummotorRegistry.THAUMMOTOR_ENTITY.get())
                    .factory(OrientedRotatingVisual.of(ThaummotorPartials.SHAFT_HALF))
                    .skipVanillaRender(blockEntity -> true)
                    .apply();
        });
    }
}
