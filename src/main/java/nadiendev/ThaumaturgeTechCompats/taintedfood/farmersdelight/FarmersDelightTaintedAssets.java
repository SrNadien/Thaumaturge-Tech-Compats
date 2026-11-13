package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FarmersDelightTaintedAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public FarmersDelightTaintedAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FarmersDelightFood food : FarmersDelightFood.values()) {
            tasks.add(save(cache, food.id(), itemModel("minecraft:item/generated", food.id())));
        }
        for (FarmersDelightKnife knife : FarmersDelightKnife.values()) {
            tasks.add(save(cache, knife.id(), itemModel("minecraft:item/handheld", knife.id())));
        }
        tasks.add(save(cache, FluxKnife.ID, itemModel("minecraft:item/handheld", FluxKnife.ID)));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats tainted Farmer's Delight models";
    }

    private CompletableFuture<?> save(CachedOutput cache, String name, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", json);
    }

    private static JsonObject itemModel(String parent, String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD + ":item/" + name);
        JsonObject json = new JsonObject();
        json.addProperty("parent", parent);
        json.add("textures", textures);
        return json;
    }
}
