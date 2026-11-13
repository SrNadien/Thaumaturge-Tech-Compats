package nadiendev.ThaumaturgeTechCompats.tiercontent;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.datagen.RecipeSaver;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ContentJson {
    public static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    public static final String ASPECT_TERMINUS = MOD + ":terminus";
    public static final String CATEGORY_ELDRITCH = "thaumaturge:eldritch";
    public static final String CATEGORY_AVARITIA = MOD + ":avaritia";

    private static final Pattern ITEM_MODEL = Pattern.compile("assets/([^/]+)/models/item/(.+)\\.json");

    private ContentJson() {
    }

    public static CompletableFuture<?> save(PackOutput output, CachedOutput cache, String relativePath, JsonElement json) {
        Path path = output.getOutputFolder().resolve(relativePath);
        CompletableFuture<?> saved = RecipeSaver.save(cache, json, path);
        Matcher matcher = ITEM_MODEL.matcher(relativePath.replace('\\', '/'));
        if (!matcher.matches()) {
            return saved;
        }
        JsonObject model = new JsonObject();
        model.addProperty("type", "minecraft:model");
        model.addProperty("model", matcher.group(1) + ":item/" + matcher.group(2));
        JsonObject definition = new JsonObject();
        definition.add("model", model);
        Path definitionPath = output.getOutputFolder().resolve("assets/" + matcher.group(1) + "/items/" + matcher.group(2) + ".json");
        return CompletableFuture.allOf(saved, DataProvider.saveStable(cache, definition, definitionPath));
    }

    public static JsonObject item(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("item", id);
        return json;
    }

    public static JsonArray strings(String... values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            array.add(value);
        }
        return array;
    }

    public static JsonArray strings(List<String> values) {
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

    public static JsonObject result(String id, int count) {
        JsonObject stack = new JsonObject();
        stack.addProperty("count", count);
        stack.addProperty("id", id);
        return stack;
    }

    public static JsonObject shaped(String category, JsonObject key, JsonArray pattern, String result, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");
        json.addProperty("category", category);
        json.add("key", key);
        json.add("pattern", pattern);
        json.add("result", result(result, count));
        return json;
    }

    public static JsonObject arcaneShaped(JsonObject key, JsonArray pattern, String researchEntry, int vis, String result) {
        return arcaneShaped(key, pattern, researchEntry, vis, result(result, 1));
    }

    public static JsonObject arcaneShaped(JsonObject key, JsonArray pattern, String researchEntry, int vis, JsonObject resultStack) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "thaumaturge:arcane_workbench_shaped");
        json.add("crystals", new JsonArray());
        json.add("key", key);
        json.add("pattern", pattern);
        JsonObject research = new JsonObject();
        research.addProperty("entry", researchEntry);
        json.add("research", research);
        json.add("result", resultStack);
        json.addProperty("vis", vis);
        return json;
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
        json.add("result", result(result, 1));
        return json;
    }

    public static JsonObject research(String name, List<String> parents, int column, int row, String icon, List<String> recipes, int warp, JsonArray noteAspects, int complexity) {
        JsonObject json = new JsonObject();
        json.addProperty("category", CATEGORY_AVARITIA);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", strings(parents));
        json.addProperty("column", column);
        json.addProperty("row", row);
        json.add("meta", strings("round", "hidden"));
        json.add("icons", strings(icon));

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
        json.addProperty("category", CATEGORY_ELDRITCH);
        json.addProperty("amount", 1);
        return json;
    }
}
