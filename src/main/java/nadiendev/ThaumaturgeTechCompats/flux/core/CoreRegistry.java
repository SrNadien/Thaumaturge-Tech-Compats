package nadiendev.ThaumaturgeTechCompats.flux.core;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class CoreRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);

    private static final Map<FluxTier, DeferredBlock<StorageCoreBlock>> CORES = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, DeferredItem<StorageCoreItem>> CORE_ITEMS = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, DeferredItem<CapacityCellItem>> CELLS = new EnumMap<>(FluxTier.class);

    static {
        for (FluxTier tier : FluxTier.values()) {
            DeferredBlock<StorageCoreBlock> block = BLOCKS.registerBlock(
                    coreName(tier),
                    properties -> new StorageCoreBlock(properties, tier),
                    () -> BlockBehaviour.Properties.of()
                            .mapColor(mapColor(tier))
                            .strength(5.0F + 2.0F * tier.ordinal(), 10.0F + 10.0F * tier.ordinal())
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()
                            .lightLevel(state -> state.getValue(StorageCoreBlock.FORMED) ? 7 : 0));
            CORES.put(tier, block);
            CORE_ITEMS.put(tier, ITEMS.registerItem(coreName(tier), properties -> new StorageCoreItem(block.get(), properties.rarity(tier.rarity()), tier)));
            CELLS.put(tier, ITEMS.registerItem(cellName(tier), properties -> new CapacityCellItem(properties.stacksTo(1).rarity(tier.rarity()), tier)));
        }
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StorageCoreBlockEntity>> CORE_ENTITY = BLOCK_ENTITIES.register(
            "flux_storage_core",
            (Supplier<BlockEntityType<StorageCoreBlockEntity>>) () -> new BlockEntityType<>(StorageCoreBlockEntity::new, blocks().toArray(Block[]::new)));

    private CoreRegistry() {
    }

    private static MapColor mapColor(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> MapColor.COLOR_PURPLE;
            case ALCHEMICAL_BRASS -> MapColor.COLOR_YELLOW;
            case VOID_METAL -> MapColor.COLOR_BLACK;
        };
    }

    public static String coreName(FluxTier tier) {
        return tier.id() + "_flux_storage_core";
    }

    public static String cellName(FluxTier tier) {
        return tier.id() + "_flux_capacity_cell";
    }

    public static DeferredBlock<StorageCoreBlock> core(FluxTier tier) {
        return CORES.get(tier);
    }

    public static DeferredItem<StorageCoreItem> coreItem(FluxTier tier) {
        return CORE_ITEMS.get(tier);
    }

    public static DeferredItem<CapacityCellItem> cell(FluxTier tier) {
        return CELLS.get(tier);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            blocks.add(CORES.get(tier).get());
        }
        return blocks;
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            items.add(CORE_ITEMS.get(tier).get());
            items.add(CELLS.get(tier).get());
        }
        return items;
    }
}
