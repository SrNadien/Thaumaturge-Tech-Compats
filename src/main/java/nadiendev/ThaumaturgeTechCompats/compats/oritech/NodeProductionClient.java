package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;

public final class NodeProductionClient {
    private NodeProductionClient() {
    }

    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        NodeProduction.apply(event.getRecipeManager());
    }
}
