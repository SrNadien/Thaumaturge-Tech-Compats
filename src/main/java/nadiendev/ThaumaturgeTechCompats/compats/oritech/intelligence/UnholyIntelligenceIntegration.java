package nadiendev.ThaumaturgeTechCompats.compats.oritech.intelligence;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class UnholyIntelligenceIntegration {
    public static final String TOGGLE = "oritech_unholy_intelligence";

    private UnholyIntelligenceIntegration() {
    }

    public static void register(IEventBus modBus) {
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new UnholyIntelligenceContent(generator.getPackOutput()));
    }
}
