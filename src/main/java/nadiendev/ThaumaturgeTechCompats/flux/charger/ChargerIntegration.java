package nadiendev.ThaumaturgeTechCompats.flux.charger;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public final class ChargerIntegration {
    private ChargerIntegration() {
    }

    public static void register(IEventBus modBus) {
        ChargerRegistry.BLOCKS.register(modBus);
        ChargerRegistry.ITEMS.register(modBus);
        ChargerRegistry.BLOCK_ENTITIES.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ChargerConfig.SPEC, ChargerConfig.FILE_NAME);
    }

    public static List<Block> blocks() {
        return ChargerRegistry.blocks();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (FluxTier tier : FluxTier.values()) {
            if (ChargerConfig.enabled(tier)) {
                output.accept(ChargerRegistry.item(tier).get());
            }
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new ChargerAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new ChargerContent(output));
    }
}
