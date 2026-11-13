package nadiendev.ThaumaturgeTechCompats.taintedfood;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TaintedFoodAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public TaintedFoodAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (TaintedFoodType type : TaintedFoodType.values()) {
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + type.id() + ".json", itemModel(type)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats tainted food models";
    }

    private static JsonObject itemModel(TaintedFoodType type) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD + ":item/" + type.id());
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");
        json.add("textures", textures);
        return json;
    }
}
