package nadiendev.ThaumaturgeTechCompats.compats.oritech.processing;

import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class OritechProcessingIntegration {
    private OritechProcessingIntegration() {
    }

    public static void addProviders(GatherDataEvent event) {
        event.getGenerator().addProvider(event instanceof GatherDataEvent.Server, new OritechProcessingRecipes(event.getGenerator().getPackOutput()));
    }
}
