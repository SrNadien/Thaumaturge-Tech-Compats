package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ForceCraftIntegration {
    public static final String MOD_ID = "forcecraft";

    private ForceCraftIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static boolean active() {
        return isLoaded() && CompatToggles.enabled(CompatToggles.FORCECRAFT);
    }

    public static void register(IEventBus modBus, ModContainer container) {
        if (!active()) {
            return;
        }
        container.registerConfig(ModConfig.Type.COMMON, ForceCraftConfig.SPEC, ForceCraftConfig.FILE_NAME);
        if (CompatToggles.enabled(CompatToggles.FORCECRAFT_REPAIR)) {
            ForceCraftRegistry.SERIALIZERS.register(modBus);
            NeoForge.EVENT_BUS.addListener(ForceCraftRepair::onPlayerTick);
            NeoForge.EVENT_BUS.addListener(ForceCraftRepair::onTooltip);
        }
        if (CompatToggles.enabled(CompatToggles.FORCECRAFT_FLASK)) {
            NeoForge.EVENT_BUS.addListener(ForceCraftFlask::onImpact);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeServer(), new ForceCraftContent(output));
    }
}
