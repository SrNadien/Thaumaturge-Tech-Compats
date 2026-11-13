package nadiendev.ThaumaturgeTechCompats.compats.create.variant;

import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.BasinVariantBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.DeployerVariantBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.DepotVariantBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.DrillVariantBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.ItemDrainVariantBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.MillstoneVariantBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.MixerVariantBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.PressVariantBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.SawVariantBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.block.SpoutVariantBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class CreateVariants {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<VariantKind, Map<CasingTier, DeferredBlock<Block>>> VARIANT_BLOCKS = new EnumMap<>(VariantKind.class);
    private static final Map<VariantKind, Map<CasingTier, DeferredItem<BlockItem>>> VARIANT_ITEMS = new EnumMap<>(VariantKind.class);

    static {
        for (VariantKind kind : VariantKind.values()) {
            Map<CasingTier, DeferredBlock<Block>> blocks = new EnumMap<>(CasingTier.class);
            Map<CasingTier, DeferredItem<BlockItem>> items = new EnumMap<>(CasingTier.class);
            for (CasingTier tier : CasingTier.values()) {
                String name = name(kind, tier);
                DeferredBlock<Block> block = BLOCKS.register(name, () -> create(kind, tier));
                blocks.put(tier, block);
                items.put(tier, ITEMS.register(name, () -> createItem(kind, block.get())));
            }
            VARIANT_BLOCKS.put(kind, blocks);
            VARIANT_ITEMS.put(kind, items);
        }
    }

    private CreateVariants() {
    }

    public static String name(VariantKind kind, CasingTier tier) {
        return kind.blockName(tier);
    }

    public static ResourceLocation baseId(VariantKind kind) {
        return ResourceLocation.fromNamespaceAndPath(CreateIntegration.MOD_ID, kind.id());
    }

    public static Block baseBlock(VariantKind kind) {
        return BuiltInRegistries.BLOCK.get(baseId(kind));
    }

    public static DeferredBlock<Block> block(VariantKind kind, CasingTier tier) {
        return VARIANT_BLOCKS.get(kind).get(tier);
    }

    public static DeferredItem<BlockItem> item(VariantKind kind, CasingTier tier) {
        return VARIANT_ITEMS.get(kind).get(tier);
    }

    public static Block[] blocks(VariantKind kind) {
        List<Block> blocks = new ArrayList<>();
        for (CasingTier tier : CasingTier.values()) {
            blocks.add(block(kind, tier).get());
        }
        return blocks.toArray(Block[]::new);
    }

    public static List<Block> allBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (VariantKind kind : VariantKind.values()) {
            for (CasingTier tier : CasingTier.values()) {
                blocks.add(block(kind, tier).get());
            }
        }
        return blocks;
    }

    private static BlockBehaviour.Properties properties(VariantKind kind, CasingTier tier) {
        return BlockBehaviour.Properties.ofFullCopy(baseBlock(kind)).mapColor(color(tier));
    }

    private static MapColor color(CasingTier tier) {
        return switch (tier) {
            case BRASS -> MapColor.GOLD;
            case THAUMIUM -> MapColor.COLOR_PURPLE;
            case VOID -> MapColor.COLOR_BLACK;
        };
    }

    private static Block create(VariantKind kind, CasingTier tier) {
        BlockBehaviour.Properties properties = properties(kind, tier);
        return switch (kind) {
            case PRESS -> new PressVariantBlock(properties, tier);
            case MIXER -> new MixerVariantBlock(properties, tier);
            case SAW -> new SawVariantBlock(properties, tier);
            case DRILL -> new DrillVariantBlock(properties, tier);
            case DEPLOYER -> new DeployerVariantBlock(properties, tier);
            case MILLSTONE -> new MillstoneVariantBlock(properties, tier);
            case BASIN -> new BasinVariantBlock(properties, tier);
            case SPOUT -> new SpoutVariantBlock(properties, tier);
            case ITEM_DRAIN -> new ItemDrainVariantBlock(properties, tier);
            case DEPOT -> new DepotVariantBlock(properties, tier);
        };
    }

    private static BlockItem createItem(VariantKind kind, Block block) {
        Item.Properties properties = new Item.Properties();
        return switch (kind) {
            case PRESS, DEPLOYER, SPOUT -> new AssemblyOperatorBlockItem(block, properties);
            default -> new BlockItem(block, properties);
        };
    }
}
