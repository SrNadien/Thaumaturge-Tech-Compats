package nadiendev.ThaumaturgeTechCompats.compats.enderio;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;

import java.util.List;

public final class EnderIOJson {
    public static final String MOD = ContentJson.MOD;
    public static final String CATEGORY_ARTIFICE = "thaumaturge:artifice";
    public static final String CATEGORY_AUROMANCY = "thaumaturge:auromancy";
    public static final String MECHANISM_SIMPLE = "thaumaturge:mechanism_simple";
    public static final String VOID_SEED = "thaumaturge:void_seed";
    public static final String INFUSION_ENTRY = "thaumaturge:infusion";
    public static final int ROW = 2;

    private EnderIOJson() {
    }

    public static JsonArray conditions(String feature) {
        JsonArray conditions = CompatJson.conditions(EnderIOIntegration.MOD_ID);
        conditions.add(CompatJson.feature(feature));
        return conditions;
    }

    public static JsonObject tag(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("tag", id);
        return json;
    }

    public static JsonObject ingredient(String id, boolean tag) {
        return tag ? tag(id) : ContentJson.item(id);
    }

    public static JsonArray repeat(JsonObject ingredient, int count) {
        JsonArray array = new JsonArray();
        for (int i = 0; i < count; i++) {
            array.add(ingredient.deepCopy());
        }
        return array;
    }

    public static JsonObject entry(String name, JsonArray conditions, List<String> parents, int column, String icon, List<String> recipes,
                                   String knowledgeCategory, int theory, int warp, JsonArray noteAspects, int complexity) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", column);
        json.addProperty("row", ROW);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(icon));

        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", knowledgeCategory);
        knowledge.addProperty("amount", theory);
        JsonArray required = new JsonArray();
        required.add(knowledge);

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        first.add("required_knowledge", required);
        first.addProperty("warp", warp);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(recipes));

        JsonArray stages = new JsonArray();
        stages.add(first);
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }
}
