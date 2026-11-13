package nadiendev.ThaumaturgeTechCompats.device;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;

public final class DeviceJson {
    public static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    public static final String ASPECT_TERMINUS = MOD + ":terminus";
    public static final String CATEGORY_ELDRITCH = "thaumaturge:eldritch";
    public static final String CATEGORY_AVARITIA = MOD + ":avaritia";

    private DeviceJson() {
    }

    public static JsonObject research(String name, String parent, int column, int row, String icon, JsonArray recipes, int warp, JsonArray noteAspects, int complexity) {
        JsonObject json = new JsonObject();
        json.addProperty("category", CATEGORY_AVARITIA);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", strings(parent));
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
        second.add("recipes", recipes);
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }

    public static JsonObject knowledge(String type) {
        JsonObject json = new JsonObject();
        json.addProperty("type", type);
        json.addProperty("category", CATEGORY_ELDRITCH);
        json.addProperty("amount", 1);
        return json;
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

    public static JsonObject stack(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("count", 1);
        json.addProperty("id", id);
        return json;
    }

    public static JsonObject researchRef(String entry) {
        JsonObject json = new JsonObject();
        json.addProperty("entry", entry);
        return json;
    }
}
