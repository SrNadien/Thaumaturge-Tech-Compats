package nadiendev.ThaumaturgeTechCompats.taintedfood;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TaintedFoodContent implements DataProvider {
    public static final String ENTRY = "tainted_food";
    public static final String ENTRY_ID = ThaumaturgeTechCompats.MOD_ID + ":" + ENTRY;
    public static final int COLUMN = 36;
    public static final int ROW = 2;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String RECIPE_DIR = "crucible/tainted_food/";
    private static final String ICON = "thaumaturge:tainted_goo";
    private static final String ASPECT_FLUX = "thaumaturge:vitium";

    private final PackOutput output;

    public TaintedFoodContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "thaumaturge/research_entry/" + ENTRY, entry()));
        for (TaintedFoodType type : TaintedFoodType.values()) {
            tasks.add(save(cache, "recipe/" + RECIPE_DIR + type.id(), recipe(type)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats tainted food";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray featureConditions() {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.feature(TaintedFoodIntegration.TOGGLE));
        return conditions;
    }

    private static JsonObject itemExists(String item) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:item_exists");
        condition.addProperty("item", item);
        return condition;
    }

    private static JsonObject knowledge(String type, String category, int amount) {
        JsonObject json = new JsonObject();
        json.addProperty("type", type);
        json.addProperty("category", category);
        json.addProperty("amount", amount);
        return json;
    }

    private static JsonObject recipe(TaintedFoodType type) {
        JsonArray conditions = featureConditions();
        conditions.add(itemExists(type.itemId().toString()));
        JsonObject json = CompatJson.recipe("thaumaturge:crucible", conditions);
        json.add("aspects", ContentJson.aspects(ASPECT_FLUX, type.vitium()));
        json.add("catalyst", ContentJson.item(type.original().toString()));
        JsonObject research = new JsonObject();
        research.addProperty("entry", ENTRY_ID);
        json.add("research", research);
        json.add("result", ContentJson.result(type.itemId().toString(), 1));
        return json;
    }

    private static JsonObject entry() {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", featureConditions());
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + ENTRY + ".title");
        json.add("parents", ContentJson.strings(TechCategory.HEADER_ID, "thaumaturge:bottle_taint"));
        json.addProperty("column", COLUMN);
        json.addProperty("row", ROW);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(ICON));

        JsonArray stages = new JsonArray();

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + ENTRY + ".stage_0");
        JsonArray required = new JsonArray();
        required.add(knowledge("observation", "thaumaturge:alchemy", 1));
        required.add(knowledge("theory", "thaumaturge:alchemy", 2));
        first.add("required_knowledge", required);
        first.addProperty("warp", 1);
        stages.add(first);

        List<String> recipes = new ArrayList<>();
        for (TaintedFoodType type : TaintedFoodType.values()) {
            recipes.add(MOD + ":" + RECIPE_DIR + type.id());
        }
        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + ENTRY + ".stage_1");
        second.add("recipes", ContentJson.strings(recipes));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", ContentJson.aspects("thaumaturge:vitium", 6, "thaumaturge:victus", 5, "thaumaturge:alkimia", 3));
        json.addProperty("complexity", 2);
        return json;
    }
}
