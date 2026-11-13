package nadiendev.ThaumaturgeTechCompats.compats.create.casing;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class CreateCasings {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<CasingTier, DeferredBlock<Block>> CASINGS = new EnumMap<>(CasingTier.class);
    private static final Map<CasingTier, DeferredItem<BlockItem>> CASING_ITEMS = new EnumMap<>(CasingTier.class);

    static {
        for (CasingTier tier : CasingTier.values()) {
            DeferredBlock<Block> block = BLOCKS.registerBlock(tier.blockName(), Block::new, BlockBehaviour.Properties.of()
                    .mapColor(color(tier))
                    .strength(3.5F, 6.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops());
            CASINGS.put(tier, block);
            CASING_ITEMS.put(tier, ITEMS.registerSimpleBlockItem(block));
        }
    }

    private CreateCasings() {
    }

    private static MapColor color(CasingTier tier) {
        return switch (tier) {
            case BRASS -> MapColor.GOLD;
            case THAUMIUM -> MapColor.COLOR_PURPLE;
            case VOID -> MapColor.COLOR_BLACK;
        };
    }

    public static DeferredBlock<Block> block(CasingTier tier) {
        return CASINGS.get(tier);
    }

    public static DeferredItem<BlockItem> item(CasingTier tier) {
        return CASING_ITEMS.get(tier);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (CasingTier tier : CasingTier.values()) {
            blocks.add(CASINGS.get(tier).get());
        }
        return blocks;
    }

    public static CasingTier tierOf(Item item) {
        for (CasingTier tier : CasingTier.values()) {
            if (CASING_ITEMS.get(tier).get() == item) {
                return tier;
            }
        }
        return null;
    }
}
