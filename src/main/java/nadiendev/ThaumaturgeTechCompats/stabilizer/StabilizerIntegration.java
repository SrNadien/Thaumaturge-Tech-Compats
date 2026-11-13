package nadiendev.ThaumaturgeTechCompats.stabilizer;

import com.leclowndu93150.thaumaturge.content.aura.node.BlockNodeStabilizer;
import com.leclowndu93150.thaumaturge.registry.TTBlockEntities;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceBlockTags;
import nadiendev.ThaumaturgeTechCompats.device.DeviceConfig;
import nadiendev.ThaumaturgeTechCompats.device.DeviceLoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.minecraft.world.item.BlockItem;
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
import java.util.concurrent.CompletableFuture;

public final class StabilizerIntegration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<StabilizerTier, DeferredBlock<BlockNodeStabilizer>> STABILIZERS = new EnumMap<>(StabilizerTier.class);
    private static final Map<StabilizerTier, DeferredItem<BlockItem>> STABILIZER_ITEMS = new EnumMap<>(StabilizerTier.class);

    static {
        for (StabilizerTier tier : StabilizerTier.values()) {
            DeferredBlock<BlockNodeStabilizer> block = BLOCKS.registerBlock(tier.blockName(), properties -> new BlockNodeStabilizer(properties, true), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(tier.hardness(), tier.resistance())
                    .noOcclusion());
            STABILIZERS.put(tier, block);
            STABILIZER_ITEMS.put(tier, ITEMS.registerSimpleBlockItem(block));
        }
    }

    private StabilizerIntegration() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(StabilizerIntegration::addBlocksToBlockEntity);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, DeviceConfig.SPEC, "thaumaturgetechcompats-devices.toml");
    }

    public static DeferredBlock<BlockNodeStabilizer> block(StabilizerTier tier) {
        return STABILIZERS.get(tier);
    }

    public static DeferredItem<BlockItem> item(StabilizerTier tier) {
        return STABILIZER_ITEMS.get(tier);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (StabilizerTier tier : StabilizerTier.values()) {
            blocks.add(STABILIZERS.get(tier).get());
        }
        return blocks;
    }

    public static StabilizerTier tierOf(Block block) {
        for (StabilizerTier tier : StabilizerTier.values()) {
            if (STABILIZERS.get(tier).get() == block) {
                return tier;
            }
        }
        return null;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (StabilizerTier tier : StabilizerTier.values()) {
            output.accept(STABILIZER_ITEMS.get(tier).get());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new StabilizerBlockStates(output, helper));
        generator.addProvider(event.includeClient(), new StabilizerItemModels(output, helper));
        generator.addProvider(event.includeServer(), DeviceLoot.provider(output, lookup));
        generator.addProvider(event.includeServer(), new DeviceBlockTags(output, lookup, helper));
        generator.addProvider(event.includeServer(), new StabilizerContent(output));
    }

    private static void addBlocksToBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        event.modify(TTBlockEntities.NODE_STABILIZER.get(), blocks().toArray(Block[]::new));
    }
}
