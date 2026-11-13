package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public final class FarmersDelightTaintedIntegration {
    public static final String MOD_ID = "farmersdelight";
    public static final String TOGGLE_FOOD = "tainted_food_farmersdelight";
    public static final String TOGGLE_KNIVES = "tainted_knives";
    public static final String TOGGLE_FLUX_KNIFE = "tainted_flux_knife";

    private static boolean foodRegistered;
    private static boolean knivesRegistered;
    private static boolean fluxKnifeRegistered;

    private FarmersDelightTaintedIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static List<String> toggleIds() {
        return List.of(TOGGLE_FOOD, TOGGLE_KNIVES, TOGGLE_FLUX_KNIFE);
    }

    public static boolean foodActive() {
        return foodRegistered;
    }

    public static boolean knivesActive() {
        return knivesRegistered;
    }

    public static boolean fluxKnifeActive() {
        return fluxKnifeRegistered;
    }

    public static void register(IEventBus modBus) {
        if (!isLoaded() || (!CompatToggles.enabled(TOGGLE_FOOD) && !CompatToggles.enabled(TOGGLE_KNIVES) && !CompatToggles.enabled(TOGGLE_FLUX_KNIFE))) {
            return;
        }
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.STARTUP, FarmersDelightTaintedConfig.SPEC, FarmersDelightTaintedConfig.FILE_NAME);
        foodRegistered = FarmersDelightTaintedConfig.foodEnabled();
        knivesRegistered = FarmersDelightTaintedConfig.knivesEnabled();
        fluxKnifeRegistered = FarmersDelightTaintedConfig.fluxKnifeEnabled();
        if (!foodRegistered && !knivesRegistered && !fluxKnifeRegistered) {
            return;
        }
        FarmersDelightTaintedItems.register(modBus, foodRegistered, knivesRegistered, fluxKnifeRegistered);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (foodRegistered) {
            for (Item item : FarmersDelightTaintedItems.foods()) {
                output.accept(item);
            }
        }
        if (knivesRegistered) {
            for (Item item : FarmersDelightTaintedItems.knives()) {
                output.accept(item);
            }
        }
        if (fluxKnifeRegistered) {
            output.accept(FarmersDelightTaintedItems.fluxKnife());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new FarmersDelightTaintedAssets(output));
        generator.addProvider(event.includeServer(), new FarmersDelightTaintedContent(output));
    }
}
