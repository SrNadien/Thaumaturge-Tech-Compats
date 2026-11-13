package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

public final class NodeProductionClient {
    private NodeProductionClient() {
    }

    public static void onRecipesUpdated(RecipesReceivedEvent event) {
        NodeProduction.apply(event.getRecipeMap().values());
    }
}
