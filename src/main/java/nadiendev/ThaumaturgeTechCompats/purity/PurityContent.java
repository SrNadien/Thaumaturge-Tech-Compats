package nadiendev.ThaumaturgeTechCompats.purity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PurityContent implements DataProvider {
    public static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    public static final String CATEGORY = "purity";
    public static final String HEADER = "purity";
    public static final String ENTRY = "purification_potion";
    public static final String SCAN = "taint_creature";
    public static final String FLAG = "f_taint_creature";
    public static final String CATEGORY_ID = MOD + ":" + CATEGORY;
    public static final String HEADER_ID = MOD + ":" + HEADER;
    public static final String ENTRY_ID = MOD + ":" + ENTRY;
    public static final String FLAG_ID = MOD + ":" + FLAG;

    private final PackOutput output;

    public PurityContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "thaumaturge/research_category/" + CATEGORY, category()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + HEADER, header()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + ENTRY, entry()));
        tasks.add(save(cache, "thaumaturge/scan/" + SCAN, scan()));
        tasks.add(save(cache, "recipe/crucible/" + ENTRY, recipe()));
        PurityVariantContent.files().forEach((path, json) -> tasks.add(save(cache, path, json)));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats purity";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions() {
        JsonArray array = new JsonArray();
        array.add(CompatJson.feature(CompatToggles.PURIFICATION_POTION));
        return array;
    }

    private static JsonObject category() {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions());
        json.addProperty("background", "thaumaturge:textures/gui/gui_research_back_4_smooth.png");
        json.add("formula", ContentJson.aspects(
                "thaumaturge:ordo", 20,
                "thaumaturge:victus", 10,
                "thaumaturge:lux", 10,
                "thaumaturge:aqua", 5,
                "thaumaturge:alkimia", 5,
                "thaumaturge:vitium", 5));
        json.addProperty("icon", MOD + ":textures/research/cat_purity.png");
        json.addProperty("index", 11);
        json.addProperty("overlay_background", "thaumaturge:textures/gui/gui_research_back_over.png");
        return json;
    }

    static JsonObject knowledge(String type, String category, int amount) {
        JsonObject json = new JsonObject();
        json.addProperty("type", type);
        json.addProperty("category", category);
        json.addProperty("amount", amount);
        return json;
    }

    static JsonObject base(String name, List<String> parents, int column, int row, String icon, JsonArray noteAspects, int complexity) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions());
        json.addProperty("category", CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", column);
        json.addProperty("row", row);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(icon));
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }

    private static JsonObject header() {
        JsonObject json = base(HEADER, List.of("thaumaturge:first_steps"), 0, 0, MOD + ":" + ENTRY,
                ContentJson.aspects("thaumaturge:ordo", 4, "thaumaturge:victus", 3), 1);

        JsonArray stages = new JsonArray();
        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + HEADER + ".stage_0");
        JsonArray required = new JsonArray();
        required.add(knowledge("observation", "thaumaturge:alchemy", 1));
        required.add(knowledge("theory", "thaumaturge:alchemy", 1));
        first.add("required_knowledge", required);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + HEADER + ".stage_1");
        stages.add(second);

        json.add("stages", stages);
        return json;
    }

    private static JsonObject entry() {
        JsonObject json = base(ENTRY, List.of(HEADER_ID, "thaumaturge:flux_cleanup", "thaumaturge:sane_soap"), 0, 2, MOD + ":" + PurityRegistry.POTION_NAME,
                ContentJson.aspects("thaumaturge:ordo", 5, "thaumaturge:victus", 4, "thaumaturge:vitium", 3, "thaumaturge:lux", 3), 2);

        JsonArray stages = new JsonArray();
        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + ENTRY + ".stage_0");
        first.add("required_research", ContentJson.strings(FLAG_ID));
        JsonArray required = new JsonArray();
        required.add(knowledge("observation", "thaumaturge:alchemy", 2));
        required.add(knowledge("theory", "thaumaturge:alchemy", 2));
        required.add(knowledge("observation", "thaumaturge:auromancy", 1));
        first.add("required_knowledge", required);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + ENTRY + ".stage_1");
        second.add("recipes", ContentJson.strings(MOD + ":crucible/" + ENTRY));
        stages.add(second);

        json.add("stages", stages);
        return json;
    }

    private static JsonObject scan() {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions());
        json.addProperty("entities", "#thaumaturge:tainted");
        json.addProperty("key", FLAG_ID);
        return json;
    }

    private static JsonObject recipe() {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions());
        json.addProperty("type", "thaumaturge:crucible");
        json.add("aspects", ContentJson.aspects(
                "thaumaturge:ordo", 40,
                "thaumaturge:victus", 40,
                "thaumaturge:aqua", 30,
                "thaumaturge:lux", 30));
        json.add("catalyst", ContentJson.item("thaumaturge:ethereal_bloom"));
        JsonObject research = new JsonObject();
        research.addProperty("entry", ENTRY_ID);
        json.add("research", research);
        json.add("result", ContentJson.result(MOD + ":" + PurityRegistry.POTION_NAME, 1));
        return json;
    }
}
