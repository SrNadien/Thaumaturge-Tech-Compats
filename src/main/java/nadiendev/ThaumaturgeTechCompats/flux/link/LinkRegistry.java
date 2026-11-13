package nadiendev.ThaumaturgeTechCompats.flux.link;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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

public final class LinkRegistry {
    public static final String EXTRACTOR_NAME = "rift_extractor";
    public static final String LINKER_NAME = "transmission_linker";
    public static final String LINK_SUFFIX = "_transmission_link";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);

    private static final Map<FluxTier, DeferredBlock<TransmissionLinkBlock>> LINK_BLOCKS = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, DeferredItem<BlockItem>> LINK_ITEMS = new EnumMap<>(FluxTier.class);

    static {
        for (FluxTier tier : FluxTier.values()) {
            DeferredBlock<TransmissionLinkBlock> block = BLOCKS.registerBlock(
                    linkName(tier),
                    properties -> new TransmissionLinkBlock(properties, tier),
                    () -> BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0F, 6.0F)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()
                            .noOcclusion());
            LINK_BLOCKS.put(tier, block);
            LINK_ITEMS.put(tier, ITEMS.registerSimpleBlockItem(block, () -> new Item.Properties().rarity(tier.rarity())));
        }
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TransmissionLinkBlockEntity>> LINK_ENTITY = BLOCK_ENTITIES.register(
            "transmission_link",
            (Supplier<BlockEntityType<TransmissionLinkBlockEntity>>) () -> new BlockEntityType<>(
                    TransmissionLinkBlockEntity::new,
                    LINK_BLOCKS.values().stream().map(DeferredBlock::get).toArray(Block[]::new)));

    public static final DeferredBlock<RiftExtractorBlock> EXTRACTOR = BLOCKS.registerBlock(
            EXTRACTOR_NAME,
            RiftExtractorBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(3.5F, 6.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .noOcclusion());

    public static final DeferredItem<BlockItem> EXTRACTOR_ITEM = ITEMS.registerSimpleBlockItem(EXTRACTOR, () -> new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RiftExtractorBlockEntity>> EXTRACTOR_ENTITY = BLOCK_ENTITIES.register(
            EXTRACTOR_NAME,
            (Supplier<BlockEntityType<RiftExtractorBlockEntity>>) () -> new BlockEntityType<>(RiftExtractorBlockEntity::new, EXTRACTOR.get()));

    public static final DeferredItem<TransmissionLinkerItem> LINKER = ITEMS.registerItem(
            LINKER_NAME,
            TransmissionLinkerItem::new,
            () -> new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));

    private LinkRegistry() {
    }

    public static String linkName(FluxTier tier) {
        return tier.id() + LINK_SUFFIX;
    }

    public static DeferredBlock<TransmissionLinkBlock> link(FluxTier tier) {
        return LINK_BLOCKS.get(tier);
    }

    public static DeferredItem<BlockItem> linkItem(FluxTier tier) {
        return LINK_ITEMS.get(tier);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            blocks.add(link(tier).get());
        }
        blocks.add(EXTRACTOR.get());
        return blocks;
    }
}
