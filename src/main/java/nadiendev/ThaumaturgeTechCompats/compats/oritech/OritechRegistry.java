package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public final class OritechRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENTS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<NodeRarityFilter>> NODE_RARITY = PLACEMENTS.register("node_rarity", () -> () -> NodeRarityFilter.CODEC);

    private static final List<DeferredBlock<? extends Block>> ADDON_BLOCKS = new ArrayList<>();
    private static final List<DeferredItem<BlockItem>> ITEM_HOLDERS = new ArrayList<>();

    private OritechRegistry() {
    }

    public static void registerAll() {
        for (OritechNode node : OritechNode.values()) {
            if (OritechFeatures.nodeEnabled(node)) {
                registerNode(node);
            }
        }
        if (AddonBridge.available()) {
            for (OritechTier tier : OritechTier.values()) {
                for (OritechAddon addon : OritechAddon.values()) {
                    if (OritechFeatures.addonEnabled(tier, addon)) {
                        registerAddon(tier, addon);
                    }
                }
            }
        }
    }

    public static List<Block> addonBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (DeferredBlock<? extends Block> holder : ADDON_BLOCKS) {
            blocks.add(holder.get());
        }
        return blocks;
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<BlockItem> holder : ITEM_HOLDERS) {
            items.add(holder.get());
        }
        return items;
    }

    private static void registerNode(OritechNode node) {
        DeferredBlock<Block> block = BLOCKS.registerBlock(node.blockName(), Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK));
        ITEM_HOLDERS.add(ITEMS.registerSimpleBlockItem(block));
    }

    private static void registerAddon(OritechTier tier, OritechAddon addon) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion();
        DeferredBlock<Block> block = BLOCKS.registerBlock(addon.blockName(tier), blockProperties -> AddonBridge.create(blockProperties, tier, addon), properties);
        ADDON_BLOCKS.add(block);
        ITEM_HOLDERS.add(ITEMS.registerSimpleBlockItem(block));
    }
}
