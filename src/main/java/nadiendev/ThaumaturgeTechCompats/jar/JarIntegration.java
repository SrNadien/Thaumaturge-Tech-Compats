package nadiendev.ThaumaturgeTechCompats.jar;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class JarIntegration {
    private JarIntegration() {
    }

    public static void register(IEventBus modBus) {
        JarRegistry.BLOCKS.register(modBus);
        JarRegistry.ITEMS.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, JarConfig.SPEC, "thaumaturgetechcompats-jars.toml");
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (JarTier tier : JarTier.values()) {
            output.accept(JarRegistry.item(tier).get());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event instanceof GatherDataEvent.Client, new JarBlockStates(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new JarDataProvider(output));
    }
}
