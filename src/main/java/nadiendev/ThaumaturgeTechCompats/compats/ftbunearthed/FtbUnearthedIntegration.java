package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class FtbUnearthedIntegration {
    public static final String MOD_ID = "ftbunearthed";

    private FtbUnearthedIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static boolean tierEnabled(FtbUnearthedBrushTier tier) {
        for (FtbUnearthedBrushTier current = tier; current != null; current = current.previous()) {
            if (!CompatToggles.enabled(current.toggle())) {
                return false;
            }
        }
        return CompatToggles.enabled(CompatToggles.FTB_UNEARTHED);
    }

    public static void register(IEventBus modBus, ModContainer container) {
        if (!isLoaded() || !CompatToggles.enabled(CompatToggles.FTB_UNEARTHED)) {
            return;
        }
        container.registerConfig(ModConfig.Type.STARTUP, FtbUnearthedConfig.SPEC, FtbUnearthedConfig.FILE_NAME);
        FtbUnearthedItems.register(modBus);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (Item item : FtbUnearthedItems.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new FtbUnearthedItemModels(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new FtbUnearthedContent(output));
    }
}
