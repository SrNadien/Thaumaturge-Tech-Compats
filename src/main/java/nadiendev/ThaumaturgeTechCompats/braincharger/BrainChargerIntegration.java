package nadiendev.ThaumaturgeTechCompats.braincharger;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class BrainChargerIntegration {
    private BrainChargerIntegration() {
    }

    public static void register(IEventBus modBus) {
        BrainChargerRegistry.BLOCKS.register(modBus);
        BrainChargerRegistry.ITEMS.register(modBus);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, BrainChargerConfig.SPEC, "thaumaturgetechcompats-brain-charger.toml");
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (BoostTier tier : BoostTier.values()) {
            output.accept(BrainChargerRegistry.boxItem(tier).get());
        }
        for (BoostTier tier : BoostTier.values()) {
            output.accept(BrainChargerRegistry.chargerItem(tier).get());
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event instanceof GatherDataEvent.Client, new BrainChargerAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new BrainChargerContent(output));
    }
}
