package nadiendev.ThaumaturgeTechCompats.transducer.client;

import com.leclowndu93150.thaumaturge.registry.TTBlockEntities;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, value = Dist.CLIENT)
public final class TransducerClient {
    private static BlockEntityWithoutLevelRenderer itemRenderer;

    private TransducerClient() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TTBlockEntities.NODE_TRANSDUCER.get(), TieredTransducerRenderer::new);
    }

    @SubscribeEvent
    public static void registerItemExtensions(RegisterClientExtensionsEvent event) {
        IClientItemExtensions extensions = new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (itemRenderer == null) {
                    itemRenderer = new TieredTransducerItemRenderer();
                }
                return itemRenderer;
            }
        };
        Item[] items = new Item[DeviceTier.values().length];
        for (DeviceTier tier : DeviceTier.values()) {
            items[tier.ordinal()] = TransducerIntegration.item(tier).get();
        }
        event.registerItem(extensions, items);
    }
}
