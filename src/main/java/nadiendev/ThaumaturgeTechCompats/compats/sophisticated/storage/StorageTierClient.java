package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.p3pp3rf1y.sophisticatedstorage.block.BarrelBlock;
import net.p3pp3rf1y.sophisticatedstorage.block.BarrelBlockClientExtensions;
import net.p3pp3rf1y.sophisticatedstorage.client.render.ChestItemRenderer;
import net.p3pp3rf1y.sophisticatedstorage.client.render.ShulkerBoxItemRenderer;

import java.util.ArrayList;
import java.util.List;

public final class StorageTierClient {
    private static final ResourceLocation REFERENCE_BARREL = ResourceLocation.fromNamespaceAndPath(StorageTierRegistry.STORAGE_MOD, "netherite_barrel");
    private static final ResourceLocation REFERENCE_CHEST = ResourceLocation.fromNamespaceAndPath(StorageTierRegistry.STORAGE_MOD, "netherite_chest");

    private StorageTierClient() {
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(StorageTierClient::registerExtensions);
        modBus.addListener(StorageTierClient::registerItemColors);
        modBus.addListener(StorageTierClient::registerBlockColors);
    }

    private static void registerExtensions(RegisterClientExtensionsEvent event) {
        for (Block block : StorageTierRegistry.barrelBlocks()) {
            event.registerBlock(new BarrelBlockClientExtensions((BarrelBlock) block), block);
        }
        for (Block block : StorageTierRegistry.limitedBarrelBlocks()) {
            event.registerBlock(new BarrelBlockClientExtensions((BarrelBlock) block), block);
        }
        event.registerItem(ChestItemRenderer.getItemRenderProperties(), StorageTierRegistry.chestItems().toArray(new Item[0]));
        event.registerItem(ShulkerBoxItemRenderer.getItemRenderProperties(), StorageTierRegistry.shulkerBoxItems().toArray(new Item[0]));
    }

    private static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            Item reference = BuiltInRegistries.ITEM.get(REFERENCE_BARREL);
            ItemStack proxy = new ItemStack(reference);
            proxy.applyComponents(stack.getComponents());
            return event.getItemColors().getColor(proxy, tintIndex);
        }, StorageTierRegistry.barrelItems().toArray(new Item[0]));
    }

    private static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            BlockState reference = BuiltInRegistries.BLOCK.get(REFERENCE_BARREL).defaultBlockState();
            return event.getBlockColors().getColor(reference, level, pos, tintIndex);
        }, concatenated(StorageTierRegistry.barrelBlocks(), StorageTierRegistry.limitedBarrelBlocks()));
        event.register((state, level, pos, tintIndex) -> {
            BlockState reference = BuiltInRegistries.BLOCK.get(REFERENCE_CHEST).defaultBlockState();
            return event.getBlockColors().getColor(reference, level, pos, tintIndex);
        }, concatenated(StorageTierRegistry.chestBlocks(), StorageTierRegistry.shulkerBoxBlocks()));
    }

    private static Block[] concatenated(List<Block> first, List<Block> second) {
        List<Block> blocks = new ArrayList<>(first);
        blocks.addAll(second);
        return blocks.toArray(new Block[0]);
    }
}
