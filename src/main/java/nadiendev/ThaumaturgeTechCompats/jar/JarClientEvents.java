package nadiendev.ThaumaturgeTechCompats.jar;

import com.leclowndu93150.thaumaturge.client.model.JarItemSpecialRenderer;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class JarClientEvents {
    private JarClientEvents() {
    }

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        IClientItemExtensions extensions = new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new JarItemSpecialRenderer();
                }
                return renderer;
            }
        };
        for (JarTier tier : JarTier.values()) {
            event.registerItem(extensions, JarRegistry.item(tier));
        }
    }
}
