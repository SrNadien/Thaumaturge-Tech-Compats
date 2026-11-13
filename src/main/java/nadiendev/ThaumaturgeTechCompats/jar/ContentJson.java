package nadiendev.ThaumaturgeTechCompats.jar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.datagen.RecipeSaver;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public final class ContentJson {
    public static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    public static final String ASPECT_TERMINUS = MOD + ":terminus";
    public static final String CATEGORY_AVARITIA = MOD + ":avaritia";
    public static final String CATEGORY_KNOWLEDGE = "thaumaturge:eldritch";

    private ContentJson() {
    }

    public static CompletableFuture<?> save(PackOutput output, CachedOutput cache, String relativePath, JsonElement json) {
        Path path = output.getOutputFolder().resolve(relativePath);
        return RecipeSaver.save(cache, json, path);
    }

    public static JsonObject item(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("item", id);
        return json;
    }

    public static JsonObject tag(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("tag", id);
        return json;
    }

    public static JsonArray strings(String... values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            array.add(value);
        }
        return array;
    }

    public static JsonArray aspects(Object... pairs) {
        JsonArray array = new JsonArray();
        for (int i = 0; i < pairs.length; i += 2) {
            JsonObject entry = new JsonObject();
            entry.addProperty("aspect", (String) pairs[i]);
            entry.addProperty("amount", (Integer) pairs[i + 1]);
            array.add(entry);
        }
        return array;
    }

    public static JsonObject infusion(JsonArray aspects, JsonObject catalyst, JsonArray components, int instability, String researchEntry, String result) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "thaumaturge:infusion");
        json.add("aspects", aspects);
        json.add("catalyst", catalyst);
        json.add("components", components);
        json.addProperty("instability", instability);

        JsonObject research = new JsonObject();
        research.addProperty("entry", researchEntry);
        json.add("research", research);

        JsonObject stack = new JsonObject();
        stack.addProperty("count", 1);
        stack.addProperty("id", result);
        json.add("result", stack);
        return json;
    }

    public static JsonObject refillRecipe(String tool, String result) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");
        json.addProperty("category", "equipment");

        JsonObject key = new JsonObject();
        key.add("T", item(tool));
        key.add("D", tag("c:dyes/black"));
        json.add("key", key);
        json.add("pattern", strings("T", "D"));

        JsonObject stack = new JsonObject();
        stack.addProperty("count", 1);
        stack.addProperty("id", result);
        json.add("result", stack);
        return json;
    }

    public static JsonObject research(String name, String[] parents, int column, int row, String[] icons, String[] recipes, int warp, JsonArray noteAspects, int complexity) {
        JsonObject json = new JsonObject();
        json.addProperty("category", CATEGORY_AVARITIA);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", strings(parents));
        json.addProperty("column", column);
        json.addProperty("row", row);
        json.add("meta", strings("round", "hidden"));
        json.add("icons", strings(icons));

        JsonArray stages = new JsonArray();

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        JsonArray knowledge = new JsonArray();
        knowledge.add(knowledge("observation"));
        knowledge.add(knowledge("theory"));
        first.add("required_knowledge", knowledge);
        first.addProperty("warp", warp);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", strings(recipes));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }

    private static JsonObject knowledge(String type) {
        JsonObject json = new JsonObject();
        json.addProperty("type", type);
        json.addProperty("category", CATEGORY_KNOWLEDGE);
        json.addProperty("amount", 1);
        return json;
    }
}
