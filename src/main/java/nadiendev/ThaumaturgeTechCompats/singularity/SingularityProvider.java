package nadiendev.ThaumaturgeTechCompats.singularity;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SingularityProvider implements DataProvider {
    private final PackOutput output;

    public SingularityProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (SingularityKind kind : SingularityKind.values()) {
            Path path = output.getOutputFolder().resolve("data/" + ThaumaturgeTechCompats.MOD_ID + "/singularities/" + kind.id() + ".json");
            tasks.add(DataProvider.saveStable(cache, singularity(kind), path));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats singularities";
    }

    private static JsonObject singularity(SingularityKind kind) {
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", kind.ingredient());

        JsonObject json = new JsonObject();
        json.addProperty("name", ThaumaturgeTechCompats.MOD_ID + ":" + kind.id());
        json.addProperty("displayName", kind.displayKey());
        json.addProperty("overlayColor", kind.overlayColor());
        json.addProperty("underlayColor", kind.underlayColor());
        json.add("ingredient", ingredient);
        return json;
    }
}
