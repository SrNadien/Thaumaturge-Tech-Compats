package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines;

import committee.nova.mods.avaritia.init.registry.ModTileEntities;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

import java.util.List;

public final class AvaritiaMachinesIntegration {
    public static final String TOGGLE = "avaritia_machine_tiers";

    private AvaritiaMachinesIntegration() {
    }

    public static boolean enabled() {
        return CompatToggles.enabled(TOGGLE);
    }

    public static void register(IEventBus modBus) {
        if (!enabled()) {
            return;
        }
        AvaritiaMachinesRegistry.register(modBus);
        modBus.addListener(AvaritiaMachinesIntegration::addValidBlocks);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, AvaritiaMachinesConfig.SPEC, AvaritiaMachinesConfig.FILE_NAME);
    }

    public static List<Block> blocks() {
        return enabled() ? AvaritiaMachinesRegistry.blocks() : List.of();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!enabled()) {
            return;
        }
        for (Item item : AvaritiaMachinesRegistry.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new AvaritiaMachinesAssets(output));
        generator.addProvider(event.includeServer(), new AvaritiaMachinesContent(output));
    }

    private static void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(ModTileEntities.neutron_collector_tile.getKey(), AvaritiaMachinesRegistry.collectors().toArray(Block[]::new));
        event.modify(ModTileEntities.neutron_compressor_tile.getKey(), AvaritiaMachinesRegistry.compressors().toArray(Block[]::new));
    }
}
