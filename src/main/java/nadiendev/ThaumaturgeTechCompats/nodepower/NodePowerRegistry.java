package nadiendev.ThaumaturgeTechCompats.nodepower;

import com.leclowndu93150.thaumaturge.registry.TTSoundTypes;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public final class NodePowerRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);

    private static final Map<FluxJarTier, DeferredBlock<FluxJarBlock>> JAR_BLOCKS = new EnumMap<>(FluxJarTier.class);
    private static final Map<FluxJarTier, DeferredItem<BlockItem>> JAR_ITEMS = new EnumMap<>(FluxJarTier.class);

    static {
        for (FluxJarTier tier : FluxJarTier.values()) {
            DeferredBlock<FluxJarBlock> block = BLOCKS.registerBlock(
                    tier.blockName(),
                    properties -> new FluxJarBlock(properties, tier),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.NONE)
                            .strength(0.3F)
                            .sound(TTSoundTypes.JAR)
                            .noOcclusion());
            Item.Properties itemProperties = new Item.Properties().rarity(tier.rarity());
            if (tier.fireResistant()) {
                itemProperties.fireResistant();
            }
            JAR_BLOCKS.put(tier, block);
            JAR_ITEMS.put(tier, ITEMS.registerSimpleBlockItem(block, itemProperties));
        }
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FluxJarBlockEntity>> FLUX_JAR_ENTITY = BLOCK_ENTITIES.register(
            "flux_jar",
            (Supplier<BlockEntityType<FluxJarBlockEntity>>) () -> BlockEntityType.Builder.of(
                    FluxJarBlockEntity::new,
                    JAR_BLOCKS.values().stream().map(DeferredBlock::get).toArray(Block[]::new)).build(null));

    public static final DeferredBlock<RiftJarBlock> RIFT_JAR = BLOCKS.registerBlock(
            "rift_jar",
            RiftJarBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NONE)
                    .strength(0.3F)
                    .sound(TTSoundTypes.JAR)
                    .noOcclusion());

    public static final DeferredItem<BlockItem> RIFT_JAR_ITEM = ITEMS.registerSimpleBlockItem(RIFT_JAR, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RiftJarBlockEntity>> RIFT_JAR_ENTITY = BLOCK_ENTITIES.register(
            "rift_jar",
            (Supplier<BlockEntityType<RiftJarBlockEntity>>) () -> BlockEntityType.Builder.of(RiftJarBlockEntity::new, RIFT_JAR.get()).build(null));

    private NodePowerRegistry() {
    }

    public static DeferredBlock<FluxJarBlock> block(FluxJarTier tier) {
        return JAR_BLOCKS.get(tier);
    }

    public static DeferredItem<BlockItem> item(FluxJarTier tier) {
        return JAR_ITEMS.get(tier);
    }
}
