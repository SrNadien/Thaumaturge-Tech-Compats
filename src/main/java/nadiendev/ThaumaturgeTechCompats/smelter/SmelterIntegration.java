package nadiendev.ThaumaturgeTechCompats.smelter;

import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockSmelter;
import com.leclowndu93150.thaumaturge.registry.TTBlockEntities;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class SmelterIntegration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<SmelterTier, DeferredBlock<BlockSmelter>> SMELTERS = new EnumMap<>(SmelterTier.class);
    private static final Map<SmelterTier, DeferredItem<BlockItem>> SMELTER_ITEMS = new EnumMap<>(SmelterTier.class);

    static {
        for (SmelterTier tier : SmelterTier.values()) {
            DeferredBlock<BlockSmelter> block = BLOCKS.registerBlock(tier.blockName(), BlockSmelter::new, () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(tier.hardness(), tier.resistance())
                    .sound(SoundType.METAL)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(BlockSmelter.LIT) ? 13 : 0));
            SMELTERS.put(tier, block);
            SMELTER_ITEMS.put(tier, ITEMS.registerSimpleBlockItem(block));
        }
    }

    private SmelterIntegration() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(SmelterIntegration::addBlocksToBlockEntity);
    }

    public static DeferredBlock<BlockSmelter> block(SmelterTier tier) {
        return SMELTERS.get(tier);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (SmelterTier tier : SmelterTier.values()) {
            blocks.add(SMELTERS.get(tier).get());
        }
        return blocks;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (SmelterTier tier : SmelterTier.values()) {
            output.accept(SMELTER_ITEMS.get(tier).get());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        generator.addProvider(event instanceof GatherDataEvent.Client, new SmelterBlockStates(output));
        generator.addProvider(event instanceof GatherDataEvent.Client, new SmelterItemModels(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new SmelterStatsProvider(output, lookup));
        generator.addProvider(event instanceof GatherDataEvent.Server, new SmelterContent(output));
    }

    private static void addBlocksToBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        event.modify(TTBlockEntities.SMELTER.get(), blocks().toArray(Block[]::new));
    }
}
