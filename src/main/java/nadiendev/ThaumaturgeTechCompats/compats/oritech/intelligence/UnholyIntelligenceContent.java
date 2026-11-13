package nadiendev.ThaumaturgeTechCompats.compats.oritech.intelligence;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.OritechFeatures;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.aspects;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.item;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.strings;

public class UnholyIntelligenceContent implements DataProvider {
    public static final String RESEARCH = UnholyIntelligenceIntegration.TOGGLE;
    public static final String RECIPE = "arcane_workbench/oritech/unholy_intelligence";
    public static final int COLUMN = 18;
    public static final int ROW = 2;

    private static final String ORITECH = OritechFeatures.MOD_ID;
    private static final int VIS = 100;
    private static final int CRYSTALS = 2;

    private final PackOutput output;

    public UnholyIntelligenceContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.allOf(
                save(cache, "thaumaturge/research_entry/" + RESEARCH, entry()),
                save(cache, "recipe/" + RECIPE, recipe()));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Oritech Unholy Intelligence";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions() {
        JsonArray conditions = CompatJson.conditions(ORITECH);
        conditions.add(CompatJson.feature(UnholyIntelligenceIntegration.TOGGLE));
        return conditions;
    }

    private static JsonObject recipe() {
        JsonObject plate = new JsonObject();
        plate.addProperty("tag", "c:plates/thaumium");
        JsonObject key = new JsonObject();
        key.add("t", plate);
        key.add("m", item("thaumaturge:mind_clockwork"));
        key.add("c", item("thaumaturge:mechanism_complex"));
        key.add("j", item("thaumaturge:jar_brain"));
        key.add("d", item(ORITECH + ":dubios_container"));
        JsonObject json = ContentJson.arcaneShaped(key, strings("tmt", "cjc", "tdt"), MOD + ":" + RESEARCH, VIS, ORITECH + ":unholy_intelligence");
        JsonArray crystals = new JsonArray();
        crystals.add(crystal("thaumaturge:ordo"));
        crystals.add(crystal("thaumaturge:perditio"));
        json.add("crystals", crystals);
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject crystal(String aspect) {
        JsonObject crystal = new JsonObject();
        crystal.addProperty("amount", CRYSTALS);
        crystal.addProperty("aspect", aspect);
        return crystal;
    }

    private static JsonObject entry() {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions());
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + RESEARCH + ".title");
        json.add("parents", strings(List.of(TechCategory.HEADER_ID, "thaumaturge:jar_brain", "thaumaturge:base_artifice")));
        json.addProperty("column", COLUMN);
        json.addProperty("row", ROW);
        json.add("meta", strings("round", "hidden"));
        json.add("icons", strings(ORITECH + ":unholy_intelligence"));

        JsonArray stages = new JsonArray();

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + RESEARCH + ".stage_0");
        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", "thaumaturge:golemancy");
        knowledge.addProperty("amount", 2);
        JsonArray required = new JsonArray();
        required.add(knowledge);
        first.add("required_knowledge", required);
        first.addProperty("warp", 2);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + RESEARCH + ".stage_1");
        second.add("recipes", strings(MOD + ":" + RECIPE));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", aspects("thaumaturge:cognitio", 5, "thaumaturge:exanimis", 4, "thaumaturge:machina", 4));
        json.addProperty("complexity", 2);
        return json;
    }
}
