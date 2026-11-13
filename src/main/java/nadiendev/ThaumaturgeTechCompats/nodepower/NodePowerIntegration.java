package nadiendev.ThaumaturgeTechCompats.nodepower;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class NodePowerIntegration {
    private NodePowerIntegration() {
    }

    public static void register(IEventBus modBus) {
        NodePowerRegistry.BLOCKS.register(modBus);
        NodePowerRegistry.ITEMS.register(modBus);
        NodePowerRegistry.BLOCK_ENTITIES.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, NodePowerConfig.SPEC, NodePowerConfig.FILE_NAME);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (FluxJarTier tier : FluxJarTier.values()) {
            output.accept(NodePowerRegistry.item(tier).get());
        }
        output.accept(NodePowerRegistry.RIFT_JAR_ITEM.get());
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new FluxJarAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new FluxJarContent(output));
    }
}
