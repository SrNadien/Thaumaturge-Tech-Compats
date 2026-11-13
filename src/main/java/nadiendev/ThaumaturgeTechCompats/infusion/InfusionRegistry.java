package nadiendev.ThaumaturgeTechCompats.infusion;

import com.leclowndu93150.thaumaturge.content.infusion.BlockPedestal;
import com.leclowndu93150.thaumaturge.content.infusion.BlockPillar;
import com.leclowndu93150.thaumaturge.registry.TTBlocks;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InfusionRegistry {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<MatrixVariant, DeferredBlock<VariantMatrixBlock>> MATRICES = new EnumMap<>(MatrixVariant.class);
    private static final Map<MatrixVariant, DeferredItem<VariantMatrixItem>> MATRIX_ITEMS = new EnumMap<>(MatrixVariant.class);
    private static final Map<MatrixVariant, Map<StoneKind, DeferredBlock<VariantStoneBlock>>> STONES = new EnumMap<>(MatrixVariant.class);
    private static final Map<MatrixVariant, Map<StoneKind, DeferredItem<VariantStoneItem>>> STONE_ITEMS = new EnumMap<>(MatrixVariant.class);
    private static final Map<MatrixVariant, Map<AltarPart, DeferredBlock<? extends Block>>> ALTAR = new EnumMap<>(MatrixVariant.class);
    private static final Map<MatrixVariant, Map<AltarPart, DeferredItem<BlockItem>>> ALTAR_ITEMS = new EnumMap<>(MatrixVariant.class);
    private static Map<Block, AltarPiece> altarLookup;
    private static Map<Item, MatrixVariant> resultLookup;

    private InfusionRegistry() {
    }

    public static void register(IEventBus modBus, boolean matrices, boolean stones, boolean altar) {
        for (MatrixVariant variant : MatrixVariant.values()) {
            if (matrices) {
                DeferredBlock<VariantMatrixBlock> block = BLOCKS.registerBlock(
                        variant.matrixName(),
                        properties -> new VariantMatrixBlock(properties, variant),
                        BlockBehaviour.Properties.of()
                                .mapColor(MapColor.STONE)
                                .strength(5.0F, 10.0F)
                                .sound(SoundType.STONE)
                                .noOcclusion());
                MATRICES.put(variant, block);
                MATRIX_ITEMS.put(variant, ITEMS.registerItem(variant.matrixName(),
                        properties -> new VariantMatrixItem(block.get(), properties, variant), itemProperties(variant)));
            }
            if (stones) {
                Map<StoneKind, DeferredBlock<VariantStoneBlock>> blocks = new EnumMap<>(StoneKind.class);
                Map<StoneKind, DeferredItem<VariantStoneItem>> items = new EnumMap<>(StoneKind.class);
                for (StoneKind kind : StoneKind.values()) {
                    DeferredBlock<VariantStoneBlock> block = BLOCKS.registerBlock(
                            variant.stoneName(kind),
                            properties -> new VariantStoneBlock(properties, variant, kind),
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.STONE)
                                    .strength(2.0F, 10.0F)
                                    .sound(SoundType.STONE)
                                    .requiresCorrectToolForDrops());
                    blocks.put(kind, block);
                    items.put(kind, ITEMS.registerItem(variant.stoneName(kind),
                            properties -> new VariantStoneItem(block.get(), properties, variant, kind), itemProperties(variant)));
                }
                STONES.put(variant, blocks);
                STONE_ITEMS.put(variant, items);
            }
            if (altar) {
                Map<AltarPart, DeferredBlock<? extends Block>> blocks = new EnumMap<>(AltarPart.class);
                Map<AltarPart, DeferredItem<BlockItem>> items = new EnumMap<>(AltarPart.class);
                for (AltarPart part : AltarPart.values()) {
                    String name = variant.altarName(part);
                    DeferredBlock<? extends Block> block = switch (part) {
                        case STONE -> BLOCKS.registerBlock(name, Block::new, altarProperties(false));
                        case PILLAR -> BLOCKS.registerBlock(name, BlockPillar::new, altarProperties(true));
                        case PEDESTAL -> BLOCKS.registerBlock(name, BlockPedestal::new, altarProperties(true));
                    };
                    blocks.put(part, block);
                    items.put(part, ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties), itemProperties(variant)));
                }
                ALTAR.put(variant, blocks);
                ALTAR_ITEMS.put(variant, items);
            }
        }
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
    }

    public static List<Block> matrixBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (DeferredBlock<VariantMatrixBlock> holder : MATRICES.values()) {
            blocks.add(holder.get());
        }
        return blocks;
    }

    public static List<Item> matrixItems() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<VariantMatrixItem> holder : MATRIX_ITEMS.values()) {
            items.add(holder.get());
        }
        return items;
    }

    public static List<Block> stoneBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (Map<StoneKind, DeferredBlock<VariantStoneBlock>> holders : STONES.values()) {
            for (DeferredBlock<VariantStoneBlock> holder : holders.values()) {
                blocks.add(holder.get());
            }
        }
        return blocks;
    }

    public static List<Block> altarBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (Map<AltarPart, DeferredBlock<? extends Block>> holders : ALTAR.values()) {
            for (DeferredBlock<? extends Block> holder : holders.values()) {
                blocks.add(holder.get());
            }
        }
        return blocks;
    }

    public static List<Block> altarBlocks(AltarPart part) {
        List<Block> blocks = new ArrayList<>();
        for (Map<AltarPart, DeferredBlock<? extends Block>> holders : ALTAR.values()) {
            DeferredBlock<? extends Block> holder = holders.get(part);
            if (holder != null) {
                blocks.add(holder.get());
            }
        }
        return blocks;
    }

    public static AltarPiece altarPiece(Block block) {
        if (ALTAR.isEmpty()) {
            return null;
        }
        Map<Block, AltarPiece> lookup = altarLookup;
        if (lookup == null) {
            lookup = new HashMap<>();
            for (Map.Entry<MatrixVariant, Map<AltarPart, DeferredBlock<? extends Block>>> variant : ALTAR.entrySet()) {
                for (Map.Entry<AltarPart, DeferredBlock<? extends Block>> part : variant.getValue().entrySet()) {
                    lookup.put(part.getValue().get(), new AltarPiece(variant.getKey(), part.getKey()));
                }
            }
            altarLookup = lookup;
        }
        return lookup.get(block);
    }

    public static Block requiredMatrix(MatrixVariant variant) {
        MatrixVariant previous = variant.previous();
        if (previous == null) {
            return TTBlocks.INFUSION_MATRIX.get();
        }
        DeferredBlock<VariantMatrixBlock> holder = MATRICES.get(previous);
        return holder == null ? null : holder.get();
    }

    public static Block ownMatrix(MatrixVariant variant) {
        DeferredBlock<VariantMatrixBlock> holder = MATRICES.get(variant);
        return holder == null ? null : holder.get();
    }

    public static MatrixVariant resultVariant(Item item) {
        Map<Item, MatrixVariant> lookup = resultLookup;
        if (lookup == null) {
            lookup = new HashMap<>();
            for (Map.Entry<MatrixVariant, DeferredItem<VariantMatrixItem>> entry : MATRIX_ITEMS.entrySet()) {
                lookup.put(entry.getValue().get(), entry.getKey());
            }
            for (Map.Entry<MatrixVariant, Map<StoneKind, DeferredItem<VariantStoneItem>>> entry : STONE_ITEMS.entrySet()) {
                for (DeferredItem<VariantStoneItem> holder : entry.getValue().values()) {
                    lookup.put(holder.get(), entry.getKey());
                }
            }
            resultLookup = lookup;
        }
        return lookup.get(item);
    }

    public static List<Block> blocks() {
        List<Block> blocks = matrixBlocks();
        blocks.addAll(stoneBlocks());
        blocks.addAll(altarBlocks());
        return blocks;
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>(matrixItems());
        for (StoneKind kind : StoneKind.values()) {
            for (MatrixVariant variant : MatrixVariant.values()) {
                Map<StoneKind, DeferredItem<VariantStoneItem>> stones = STONE_ITEMS.get(variant);
                if (stones != null) {
                    items.add(stones.get(kind).get());
                }
            }
        }
        for (AltarPart part : AltarPart.values()) {
            for (MatrixVariant variant : MatrixVariant.values()) {
                Map<AltarPart, DeferredItem<BlockItem>> altar = ALTAR_ITEMS.get(variant);
                if (altar != null) {
                    items.add(altar.get(part).get());
                }
            }
        }
        return items;
    }

    private static BlockBehaviour.Properties altarProperties(boolean noOcclusion) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .strength(2.0F, 17.5F)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops();
        return noOcclusion ? properties.noOcclusion() : properties;
    }

    private static Item.Properties itemProperties(MatrixVariant variant) {
        Item.Properties properties = new Item.Properties().rarity(variant.rarity());
        if (variant.fireResistant()) {
            properties.fireResistant();
        }
        return properties;
    }
}
