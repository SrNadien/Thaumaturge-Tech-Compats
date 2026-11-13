package nadiendev.ThaumaturgeTechCompats.furnace;

import com.leclowndu93150.thaumaturge.content.essentia.advancedfurnace.BlockAdvancedAlchemicalFurnace;
import com.leclowndu93150.thaumaturge.registry.TTBlockEntities;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterIntegration;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterTier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
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

public final class FurnaceIntegration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<DeviceTier, DeferredBlock<BlockAdvancedAlchemicalFurnace>> FURNACES = new EnumMap<>(DeviceTier.class);
    private static final Map<DeviceTier, DeferredItem<FurnacePlacerItem>> FURNACE_ITEMS = new EnumMap<>(DeviceTier.class);

    static {
        for (DeviceTier tier : DeviceTier.values()) {
            DeferredBlock<BlockAdvancedAlchemicalFurnace> block = BLOCKS.registerBlock(blockName(tier), BlockAdvancedAlchemicalFurnace::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F + tier.ordinal(), 12.0F * (tier.ordinal() + 1))
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(BlockAdvancedAlchemicalFurnace.LIT) ? 13 : 0));
            FURNACES.put(tier, block);
            FURNACE_ITEMS.put(tier, ITEMS.register(blockName(tier), () -> new FurnacePlacerItem(block.get(), blockName(tier), new Item.Properties())));
        }
    }

    private FurnaceIntegration() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(FurnaceIntegration::addBlocksToBlockEntity);
    }

    public static String blockName(DeviceTier tier) {
        return tier.id() + "_alchemical_furnace";
    }

    public static DeferredBlock<BlockAdvancedAlchemicalFurnace> block(DeviceTier tier) {
        return FURNACES.get(tier);
    }

    public static DeferredItem<FurnacePlacerItem> item(DeviceTier tier) {
        return FURNACE_ITEMS.get(tier);
    }

    public static Block smelter(DeviceTier tier) {
        return SmelterIntegration.block(SmelterTier.values()[tier.ordinal()]).get();
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (DeviceTier tier : DeviceTier.values()) {
            blocks.add(FURNACES.get(tier).get());
        }
        return blocks;
    }

    public static DeviceTier tierOf(Block block) {
        for (DeviceTier tier : DeviceTier.values()) {
            if (FURNACES.get(tier).get() == block) {
                return tier;
            }
        }
        return null;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (DeviceTier tier : DeviceTier.values()) {
            output.accept(FURNACE_ITEMS.get(tier).get());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new FurnaceBlockStates(output, helper));
        generator.addProvider(event.includeClient(), new FurnaceItemModels(output, helper));
        generator.addProvider(event.includeServer(), new FurnaceContent(output));
    }

    private static void addBlocksToBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        event.modify(TTBlockEntities.ADVANCED_ALCHEMICAL_FURNACE.get(), blocks().toArray(Block[]::new));
    }
}
