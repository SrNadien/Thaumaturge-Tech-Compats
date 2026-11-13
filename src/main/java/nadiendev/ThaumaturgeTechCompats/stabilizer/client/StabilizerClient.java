package nadiendev.ThaumaturgeTechCompats.stabilizer.client;

import com.leclowndu93150.thaumaturge.registry.TTBlockEntities;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerTier;
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
public final class StabilizerClient {
    private static BlockEntityWithoutLevelRenderer itemRenderer;

    private StabilizerClient() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TTBlockEntities.NODE_STABILIZER.get(), TieredStabilizerRenderer::new);
    }

    @SubscribeEvent
    public static void registerItemExtensions(RegisterClientExtensionsEvent event) {
        IClientItemExtensions extensions = new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (itemRenderer == null) {
                    itemRenderer = new TieredStabilizerItemRenderer();
                }
                return itemRenderer;
            }
        };
        Item[] items = new Item[StabilizerTier.values().length];
        for (StabilizerTier tier : StabilizerTier.values()) {
            items[tier.ordinal()] = StabilizerIntegration.item(tier).get();
        }
        event.registerItem(extensions, items);
    }
}
