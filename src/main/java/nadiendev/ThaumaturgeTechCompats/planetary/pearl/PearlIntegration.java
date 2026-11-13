package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class PearlIntegration {
    private PearlIntegration() {
    }

    public static void register(IEventBus modBus) {
        PearlRegistry.ITEMS.register(modBus);
        PearlRegistry.ENTITIES.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, PearlConfig.SPEC, PearlConfig.FILE_NAME);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            if (PearlConfig.tierEnabled(tier)) {
                output.accept(PearlRegistry.pearl(tier).get());
            }
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new PearlAssets(output));
        generator.addProvider(event.includeServer(), new PearlContent(output));
    }
}
