package nadiendev.ThaumaturgeTechCompats.creative;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class CreativeIntegration {
    private CreativeIntegration() {
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new CreativeContent(generator.getPackOutput()));
    }
}
