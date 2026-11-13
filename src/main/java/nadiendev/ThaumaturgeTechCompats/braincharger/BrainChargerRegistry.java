package nadiendev.ThaumaturgeTechCompats.braincharger;

import com.leclowndu93150.thaumaturge.content.essentia.thaumatorium.BlockBrainBox;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.BlockItem;
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

public final class BrainChargerRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<BoostTier, DeferredBlock<BlockBrainBox>> BOXES = new EnumMap<>(BoostTier.class);
    private static final Map<BoostTier, DeferredItem<BlockItem>> BOX_ITEMS = new EnumMap<>(BoostTier.class);
    private static final Map<BoostTier, DeferredBlock<TieredWorkbenchCharger>> CHARGERS = new EnumMap<>(BoostTier.class);
    private static final Map<BoostTier, DeferredItem<BlockItem>> CHARGER_ITEMS = new EnumMap<>(BoostTier.class);

    static {
        for (BoostTier tier : BoostTier.values()) {
            DeferredBlock<BlockBrainBox> box = BLOCKS.registerBlock(tier.boxName(), BlockBrainBox::new, () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(2.0F + tier.ordinal(), 6.0F + 4.0F * tier.ordinal())
                    .sound(SoundType.METAL)
                    .noOcclusion());
            BOXES.put(tier, box);
            BOX_ITEMS.put(tier, ITEMS.registerSimpleBlockItem(box));

            DeferredBlock<TieredWorkbenchCharger> charger = BLOCKS.registerBlock(tier.chargerName(),
                    properties -> new TieredWorkbenchCharger(properties, tier), () -> BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(2.0F + tier.ordinal(), 10.0F + 5.0F * tier.ordinal())
                            .sound(SoundType.METAL)
                            .noOcclusion());
            CHARGERS.put(tier, charger);
            CHARGER_ITEMS.put(tier, ITEMS.registerSimpleBlockItem(charger));
        }
    }

    private BrainChargerRegistry() {
    }

    public static DeferredBlock<BlockBrainBox> box(BoostTier tier) {
        return BOXES.get(tier);
    }

    public static DeferredItem<BlockItem> boxItem(BoostTier tier) {
        return BOX_ITEMS.get(tier);
    }

    public static DeferredBlock<TieredWorkbenchCharger> charger(BoostTier tier) {
        return CHARGERS.get(tier);
    }

    public static DeferredItem<BlockItem> chargerItem(BoostTier tier) {
        return CHARGER_ITEMS.get(tier);
    }

    public static BoostTier boxTier(Block block) {
        for (BoostTier tier : BoostTier.values()) {
            if (BOXES.get(tier).get() == block) {
                return tier;
            }
        }
        return null;
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (BoostTier tier : BoostTier.values()) {
            blocks.add(BOXES.get(tier).get());
            blocks.add(CHARGERS.get(tier).get());
        }
        return blocks;
    }
}
