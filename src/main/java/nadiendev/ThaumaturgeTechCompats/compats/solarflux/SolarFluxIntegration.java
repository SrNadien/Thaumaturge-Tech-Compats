package nadiendev.ThaumaturgeTechCompats.compats.solarflux;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class SolarFluxIntegration {
    public static final String MOD_ID = "solarflux";
    public static final String CATEGORY = "solarflux";

    private SolarFluxIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get().isLoaded(MOD_ID);
    }

    public static void register() {
        if (isLoaded() && CompatToggles.enabled(CompatToggles.SOLAR_FLUX)) {
            SolarFluxPanels.register();
        }
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!isLoaded()) {
            return;
        }
        for (Item item : SolarFluxPanels.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new SolarFluxContent(generator.getPackOutput()));
    }
}
