package nadiendev.ThaumaturgeTechCompats.transducer;

import com.leclowndu93150.thaumaturge.content.aura.node.BlockNodeTransducer;
import com.leclowndu93150.thaumaturge.registry.TTBlockEntities;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class TransducerIntegration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<DeviceTier, DeferredBlock<BlockNodeTransducer>> TRANSDUCERS = new EnumMap<>(DeviceTier.class);
    private static final Map<DeviceTier, DeferredItem<BlockItem>> TRANSDUCER_ITEMS = new EnumMap<>(DeviceTier.class);

    static {
        for (DeviceTier tier : DeviceTier.values()) {
            DeferredBlock<BlockNodeTransducer> block = BLOCKS.registerBlock(blockName(tier), BlockNodeTransducer::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(3.0F + tier.ordinal(), 30.0F * (tier.ordinal() + 1))
                    .noOcclusion());
            TRANSDUCERS.put(tier, block);
            TRANSDUCER_ITEMS.put(tier, ITEMS.registerSimpleBlockItem(block));
        }
    }

    private TransducerIntegration() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(TransducerIntegration::addBlocksToBlockEntity);
    }

    public static String blockName(DeviceTier tier) {
        return tier.id() + "_node_transducer";
    }

    public static DeferredBlock<BlockNodeTransducer> block(DeviceTier tier) {
        return TRANSDUCERS.get(tier);
    }

    public static DeferredItem<BlockItem> item(DeviceTier tier) {
        return TRANSDUCER_ITEMS.get(tier);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (DeviceTier tier : DeviceTier.values()) {
            blocks.add(TRANSDUCERS.get(tier).get());
        }
        return blocks;
    }

    public static DeviceTier tierOf(Block block) {
        for (DeviceTier tier : DeviceTier.values()) {
            if (TRANSDUCERS.get(tier).get() == block) {
                return tier;
            }
        }
        return null;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (DeviceTier tier : DeviceTier.values()) {
            output.accept(TRANSDUCER_ITEMS.get(tier).get());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new TransducerBlockStates(output, helper));
        generator.addProvider(event.includeClient(), new TransducerItemModels(output, helper));
        generator.addProvider(event.includeServer(), new TransducerContent(output));
    }

    private static void addBlocksToBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        event.modify(TTBlockEntities.NODE_TRANSDUCER.get(), blocks().toArray(Block[]::new));
    }
}
