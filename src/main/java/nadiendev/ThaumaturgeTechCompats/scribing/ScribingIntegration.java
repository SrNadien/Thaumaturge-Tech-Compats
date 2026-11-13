package nadiendev.ThaumaturgeTechCompats.scribing;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ScribingIntegration {
    private ScribingIntegration() {
    }

    public static void register(IEventBus modBus) {
        ScribingRegistry.ITEMS.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ScribingConfig.SPEC, "thaumaturgetechcompats-scribing.toml");
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (ScribingTier tier : ScribingTier.values()) {
            output.accept(ScribingRegistry.tools(tier).get());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event instanceof GatherDataEvent.Client, new ScribingItemModels(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new ScribingDataProvider(output));
    }
}
