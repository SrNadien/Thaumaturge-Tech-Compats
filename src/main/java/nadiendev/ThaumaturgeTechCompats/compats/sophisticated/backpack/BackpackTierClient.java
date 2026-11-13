package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.backpack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.util.Lazy;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackItemStackRenderer;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;

public final class BackpackTierClient {
    private BackpackTierClient() {
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(BackpackTierClient::registerExtensions);
        modBus.addListener(BackpackTierClient::registerItemColors);
        modBus.addListener(BackpackTierClient::registerBlockColors);
    }

    private static void registerExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            private final Lazy<BlockEntityWithoutLevelRenderer> renderer = Lazy.of(
                    () -> new BackpackItemStackRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer.get();
            }
        }, BackpackTierRegistry.items().toArray(new Item[0]));
    }

    private static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex < 0 || tintIndex > 1 || !(stack.getItem() instanceof BackpackItem)) {
                return -1;
            }
            return tintIndex == 0 ? BackpackItem.getMainColor(stack) : BackpackItem.getAccentColor(stack);
        }, BackpackTierRegistry.items().toArray(new Item[0]));
    }

    private static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (tintIndex < 0 || tintIndex > 1 || pos == null) {
                return -1;
            }
            int fallback = tintIndex == 0 ? BackpackWrapper.DEFAULT_MAIN_COLOR : BackpackWrapper.DEFAULT_ACCENT_COLOR;
            return WorldHelper.getBlockEntity(level, pos, BackpackBlockEntity.class)
                    .map(entity -> tintIndex == 0 ? entity.getMainColor() : entity.getAccentColor()).orElse(fallback);
        }, BackpackTierRegistry.blocks().toArray(new Block[0]));
    }
}
