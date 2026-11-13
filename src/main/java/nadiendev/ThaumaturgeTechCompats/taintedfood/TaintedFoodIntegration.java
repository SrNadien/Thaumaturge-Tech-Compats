package nadiendev.ThaumaturgeTechCompats.taintedfood;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class TaintedFoodIntegration {
    public static final String TOGGLE = "tainted_food";

    private static boolean registered;

    private TaintedFoodIntegration() {
    }

    public static boolean active() {
        return registered && TaintedFoodConfig.enabled();
    }

    public static void register(IEventBus modBus) {
        if (!CompatToggles.enabled(TOGGLE)) {
            return;
        }
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.STARTUP, TaintedFoodConfig.SPEC, TaintedFoodConfig.FILE_NAME);
        if (!TaintedFoodConfig.enabled()) {
            return;
        }
        TaintedFoodItems.register(modBus);
        registered = true;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!registered) {
            return;
        }
        for (Item item : TaintedFoodItems.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new TaintedFoodAssets(output));
        generator.addProvider(event.includeServer(), new TaintedFoodContent(output));
    }
}
