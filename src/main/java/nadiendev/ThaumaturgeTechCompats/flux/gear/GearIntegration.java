package nadiendev.ThaumaturgeTechCompats.flux.gear;

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

public final class GearIntegration {
    public static final String SIPHON_TOGGLE = "flux_gear_portable_void_siphon";
    public static final String CANNON_TOGGLE = "flux_gear_discharge_cannon";
    public static final FluxTier SIPHON_TIER = FluxTier.ALCHEMICAL_BRASS;
    public static final FluxTier CANNON_TIER = FluxTier.VOID_METAL;

    private GearIntegration() {
    }

    public static void register(IEventBus modBus) {
        GearRegistry.ITEMS.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, GearConfig.SPEC, GearConfig.FILE_NAME);
    }

    public static List<Block> blocks() {
        return List.of();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (GearConfig.siphonEnabled()) {
            output.accept(GearRegistry.SIPHON.get());
            output.accept(GearRegistry.SIPHON.get().fullStack());
        }
        if (GearConfig.cannonEnabled()) {
            output.accept(GearRegistry.CANNON.get());
            output.accept(GearRegistry.CANNON.get().fullStack());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new GearAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new GearContent(output));
    }
}
