package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ForceCraftContent implements DataProvider {
    public static final String REPAIR_ENTRY = "forcecraft_repair";
    public static final String REPAIR_RECIPE = "forcecraft/repair_infusion";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String FORCECRAFT = ForceCraftIntegration.MOD_ID;
    private static final String REAGENT = "thaumaturge:crystal_ordo";
    private static final String CENTER_TAG = "forcecraft:valid_infuser_charge";
    private static final String TIER = "7";
    private static final int TIME = 60;
    private static final int COLUMN = 15;
    private static final int ROW = 2;

    private final PackOutput output;

    public ForceCraftContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "data/" + MOD + "/recipe/" + REPAIR_RECIPE + ".json", repairRecipe()));
        tasks.add(save(cache, "data/" + MOD + "/thaumaturge/research_entry/" + REPAIR_ENTRY + ".json", repairResearch()));
        tasks.add(save(cache, "data/thaumaturge/data_maps/entity_type/entity_aspects.json", ForceCraftAspects.entities()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats ForceCraft";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return CompatJson.save(output, cache, path, json);
    }

    private static JsonArray repairConditions() {
        JsonArray conditions = CompatJson.conditions(FORCECRAFT);
        conditions.add(CompatJson.feature(CompatToggles.FORCECRAFT_REPAIR));
        return conditions;
    }

    private static JsonObject repairRecipe() {
        JsonObject json = CompatJson.recipe(MOD + ":" + ForceCraftRegistry.REPAIR_SERIALIZER_ID, repairConditions());
        JsonObject center = new JsonObject();
        center.addProperty("tag", CENTER_TAG);
        json.add("center", center);
        json.add("ingredient", ContentJson.item(REAGENT));
        json.addProperty("tier", TIER);
        json.addProperty("time", TIME);
        return json;
    }

    private static JsonObject repairResearch() {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", repairConditions());
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + REPAIR_ENTRY + ".title");
        json.add("parents", ContentJson.strings(TechCategory.HEADER_ID, "thaumaturge:infusion_enchantment"));
        json.addProperty("column", COLUMN);
        json.addProperty("row", ROW);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(FORCECRAFT + ":infuser"));

        JsonArray stages = new JsonArray();

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + REPAIR_ENTRY + ".stage_0");
        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", "thaumaturge:artifice");
        knowledge.addProperty("amount", 2);
        JsonArray required = new JsonArray();
        required.add(knowledge);
        first.add("required_knowledge", required);
        first.addProperty("warp", 0);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + REPAIR_ENTRY + ".stage_1");
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", ContentJson.aspects("thaumaturge:ordo", 8, "thaumaturge:instrumentum", 6, "thaumaturge:potentia", 4));
        json.addProperty("complexity", 2);
        return json;
    }
}
