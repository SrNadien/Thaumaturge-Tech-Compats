package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing;

import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.MagicalLaserRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public final class IndustrialForegoingIntegration {
    public static final String MOD_ID = "industrialforegoing";
    public static final String TITANIUM_ID = "titanium";
    public static final String CATEGORY = "industrial_foregoing";

    private IndustrialForegoingIntegration() {
    }

    public static boolean loaded() {
        ModList mods = ModList.get();
        return mods != null && mods.isLoaded(MOD_ID) && mods.isLoaded(TITANIUM_ID);
    }

    public static void register(IEventBus modBus) {
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, IndustrialForegoingConfig.SPEC, IndustrialForegoingConfig.FILE_NAME);
        if (loaded()) {
            MagicalLaserRegistry.register(modBus);
        }
    }

    public static List<Block> blocks() {
        return loaded() ? MagicalLaserRegistry.blocks() : List.of();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!loaded()) {
            return;
        }
        for (LaserMachine machine : LaserMachine.values()) {
            if (IndustrialForegoingConfig.enabled(machine)) {
                output.accept(MagicalLaserRegistry.item(machine));
            }
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event instanceof GatherDataEvent.Client, new IndustrialForegoingAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new IndustrialForegoingContent(output));
    }
}
