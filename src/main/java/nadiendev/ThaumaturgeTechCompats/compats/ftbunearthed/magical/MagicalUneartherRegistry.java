package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class MagicalUneartherRegistry {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<MagicalUneartherTier, DeferredBlock<MagicalUneartherBlock>> CORES = new EnumMap<>(MagicalUneartherTier.class);
    private static final Map<MagicalUneartherTier, DeferredItem<Item>> CORE_ITEMS = new EnumMap<>(MagicalUneartherTier.class);

    private MagicalUneartherRegistry() {
    }

    public static void register(IEventBus modBus) {
        for (MagicalUneartherTier tier : MagicalUneartherTier.values()) {
            if (!MagicalUneartherIntegration.tierEnabled(tier)) {
                continue;
            }
            DeferredBlock<MagicalUneartherBlock> block = BLOCKS.registerBlock(
                    tier.blockName(),
                    properties -> new MagicalUneartherBlock(properties, tier),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0F, 10.0F)
                            .sound(SoundType.METAL));
            CORES.put(tier, block);
            CORE_ITEMS.put(tier, ITEMS.register(tier.blockName(), () -> new BlockItem(block.get(), new Item.Properties().rarity(tier.rarity()))));
        }
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (DeferredBlock<MagicalUneartherBlock> holder : CORES.values()) {
            blocks.add(holder.get());
        }
        return blocks;
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<Item> holder : CORE_ITEMS.values()) {
            items.add(holder.get());
        }
        return items;
    }
}
