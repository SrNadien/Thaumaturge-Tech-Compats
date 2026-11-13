package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class AvaritiaMachinesRegistry {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<AvaritiaMachineTier, DeferredBlock<TieredCollectorBlock>> COLLECTORS = new EnumMap<>(AvaritiaMachineTier.class);
    private static final Map<AvaritiaMachineTier, DeferredBlock<TieredCompressorBlock>> COMPRESSORS = new EnumMap<>(AvaritiaMachineTier.class);
    private static final Map<AvaritiaMachineTier, DeferredItem<BlockItem>> COLLECTOR_ITEMS = new EnumMap<>(AvaritiaMachineTier.class);
    private static final Map<AvaritiaMachineTier, DeferredItem<BlockItem>> COMPRESSOR_ITEMS = new EnumMap<>(AvaritiaMachineTier.class);

    private AvaritiaMachinesRegistry() {
    }

    public static void register(IEventBus modBus) {
        for (AvaritiaMachineTier tier : AvaritiaMachineTier.values()) {
            DeferredBlock<TieredCollectorBlock> collector = BLOCKS.registerBlock(
                    tier.collectorName(), properties -> new TieredCollectorBlock(tier));
            DeferredBlock<TieredCompressorBlock> compressor = BLOCKS.registerBlock(
                    tier.compressorName(), properties -> new TieredCompressorBlock(tier));
            COLLECTORS.put(tier, collector);
            COMPRESSORS.put(tier, compressor);
            COLLECTOR_ITEMS.put(tier, ITEMS.registerItem(tier.collectorName(), properties -> new BlockItem(collector.get(), properties.useBlockDescriptionPrefix()), () -> itemProperties(tier)));
            COMPRESSOR_ITEMS.put(tier, ITEMS.registerItem(tier.compressorName(), properties -> new BlockItem(compressor.get(), properties.useBlockDescriptionPrefix()), () -> itemProperties(tier)));
        }
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
    }

    public static List<Block> collectors() {
        List<Block> blocks = new ArrayList<>();
        for (DeferredBlock<TieredCollectorBlock> holder : COLLECTORS.values()) {
            blocks.add(holder.get());
        }
        return blocks;
    }

    public static List<Block> compressors() {
        List<Block> blocks = new ArrayList<>();
        for (DeferredBlock<TieredCompressorBlock> holder : COMPRESSORS.values()) {
            blocks.add(holder.get());
        }
        return blocks;
    }

    public static List<Block> blocks() {
        List<Block> blocks = collectors();
        blocks.addAll(compressors());
        return blocks;
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (AvaritiaMachineTier tier : AvaritiaMachineTier.values()) {
            DeferredItem<BlockItem> collector = COLLECTOR_ITEMS.get(tier);
            DeferredItem<BlockItem> compressor = COMPRESSOR_ITEMS.get(tier);
            if (collector != null) {
                items.add(collector.get());
            }
            if (compressor != null) {
                items.add(compressor.get());
            }
        }
        return items;
    }

    private static Item.Properties itemProperties(AvaritiaMachineTier tier) {
        Item.Properties properties = new Item.Properties().rarity(tier.rarity());
        if (tier.fireResistant()) {
            properties.fireResistant();
        }
        return properties;
    }
}
