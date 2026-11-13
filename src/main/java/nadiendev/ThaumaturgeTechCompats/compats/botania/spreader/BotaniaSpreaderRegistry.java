package nadiendev.ThaumaturgeTechCompats.compats.botania.spreader;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaIntegration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class BotaniaSpreaderRegistry {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<SpreaderTier, DeferredBlock<TieredSpreaderBlock>> SPREADERS = new EnumMap<>(SpreaderTier.class);
    private static final Map<SpreaderTier, DeferredItem<BlockItem>> SPREADER_ITEMS = new EnumMap<>(SpreaderTier.class);
    private static final ResourceKey<BlockEntityType<?>> SPREADER_ENTITY = ResourceKey.create(
            Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(BotaniaIntegration.MOD_ID, "mana_spreader"));

    private BotaniaSpreaderRegistry() {
    }

    public static void register(IEventBus modBus) {
        for (SpreaderTier tier : SpreaderTier.values()) {
            if (!BotaniaIntegration.spreaderTierEnabled(tier)) {
                continue;
            }
            DeferredBlock<TieredSpreaderBlock> block = BLOCKS.registerBlock(
                    tier.blockName(),
                    properties -> new TieredSpreaderBlock(tier, properties),
                    () -> BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0F, 10.0F)
                            .sound(SoundType.METAL)
                            .isValidSpawn((state, level, pos, entity) -> false));
            SPREADERS.put(tier, block);
            SPREADER_ITEMS.put(tier, ITEMS.registerItem(tier.blockName(), properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()), () -> itemProperties(tier)));
        }
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(BotaniaSpreaderRegistry::addValidBlocks);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (DeferredBlock<TieredSpreaderBlock> holder : SPREADERS.values()) {
            blocks.add(holder.get());
        }
        return blocks;
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<BlockItem> holder : SPREADER_ITEMS.values()) {
            items.add(holder.get());
        }
        return items;
    }

    private static Item.Properties itemProperties(SpreaderTier tier) {
        Item.Properties properties = new Item.Properties().rarity(tier.rarity());
        if (tier.fireResistant()) {
            properties.fireResistant();
        }
        return properties;
    }

    private static void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        List<Block> blocks = blocks();
        if (!blocks.isEmpty()) {
            event.modify(SPREADER_ENTITY, blocks.toArray(Block[]::new));
        }
    }
}
