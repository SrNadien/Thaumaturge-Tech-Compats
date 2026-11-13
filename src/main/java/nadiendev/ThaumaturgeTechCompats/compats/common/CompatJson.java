package nadiendev.ThaumaturgeTechCompats.compats.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.datagen.RecipeSaver;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public final class CompatJson {
    private CompatJson() {
    }

    public static JsonArray conditions(String modId, String... nonEmptyTags) {
        JsonArray conditions = new JsonArray();
        JsonObject loaded = new JsonObject();
        loaded.addProperty("type", "neoforge:mod_loaded");
        loaded.addProperty("modid", modId);
        conditions.add(loaded);
        conditions.add(feature(modId));
        for (String tag : nonEmptyTags) {
            if (tag == null) {
                continue;
            }
            JsonObject empty = new JsonObject();
            empty.addProperty("type", "neoforge:tag_empty");
            empty.addProperty("tag", tag);
            JsonObject not = new JsonObject();
            not.addProperty("type", "neoforge:not");
            not.add("value", empty);
            conditions.add(not);
        }
        return conditions;
    }

    public static JsonArray conditionsFor(String modId, ClusterMaterial material, String... nonEmptyTags) {
        JsonArray conditions = conditions(modId, nonEmptyTags);
        if (material.requiredMod() != null) {
            conditions.add(modLoaded(material.requiredMod()));
            conditions.add(feature(CompatToggles.MEKANISM_CLUSTERS));
        }
        return conditions;
    }

    public static JsonObject modLoaded(String modId) {
        JsonObject loaded = new JsonObject();
        loaded.addProperty("type", "neoforge:mod_loaded");
        loaded.addProperty("modid", modId);
        return loaded;
    }

    public static JsonObject feature(String id) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "thaumaturgetechcompats:feature_enabled");
        condition.addProperty("feature", id);
        return condition;
    }

    public static JsonObject recipe(String type, JsonArray conditions) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions);
        json.addProperty("type", type);
        return json;
    }

    public static JsonObject tagCount(String tag, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("count", count);
        json.addProperty("tag", tag);
        return json;
    }

    public static JsonObject itemCount(String item, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("count", count);
        json.addProperty("item", item);
        return json;
    }

    public static JsonObject result(String id, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("count", count);
        json.addProperty("id", id);
        return json;
    }

    public static CompletableFuture<?> save(PackOutput output, CachedOutput cache, String relativePath, JsonElement json) {
        Path path = output.getOutputFolder().resolve(relativePath);
        return RecipeSaver.save(cache, json, path);
    }
}
