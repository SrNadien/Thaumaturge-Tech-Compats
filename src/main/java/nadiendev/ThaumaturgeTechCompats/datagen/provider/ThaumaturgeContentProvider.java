package nadiendev.ThaumaturgeTechCompats.datagen.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOAspects;
import nadiendev.ThaumaturgeTechCompats.compats.forcecraft.ForceCraftAspects;
import nadiendev.ThaumaturgeTechCompats.compats.botania.aspects.BotaniaAspects;
import nadiendev.ThaumaturgeTechCompats.datagen.RecipeSaver;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThaumaturgeContentProvider implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String ASPECT_TERMINUS = MOD + ":terminus";
    private static final String CATEGORY_ELDRITCH = "thaumaturge:eldritch";
    private static final String CATEGORY_AVARITIA = MOD + ":avaritia";

    private static final String PEARL = MOD + ":extremely_primordial_pearl";
    private static final String AKASHIC = MOD + ":akashic_record";

    private final PackOutput output;

    public ThaumaturgeContentProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "data/" + MOD + "/thaumaturge/aspect/terminus.json", terminusAspect()));
        tasks.add(save(cache, "data/" + MOD + "/thaumaturge/research_category/avaritia.json", avaritiaCategory()));
        tasks.add(save(cache, "data/" + MOD + "/thaumaturge/research_entry/extremely_primordial_pearl.json", pearlResearch()));
        tasks.add(save(cache, "data/" + MOD + "/thaumaturge/research_entry/akashic_record.json", akashicResearch()));
        tasks.add(save(cache, "data/" + MOD + "/thaumaturge/research_entry/infinity_wand.json", wandResearch()));
        tasks.add(save(cache, "data/" + MOD + "/recipe/infusion/extremely_primordial_pearl.json", pearlInfusion()));
        tasks.add(save(cache, "data/" + MOD + "/recipe/infusion/akashic_record.json", akashicInfusion()));
        tasks.add(save(cache, "data/" + MOD + "/recipe/infinity_wand.json", infinityWandRecipe()));
        tasks.add(save(cache, "data/" + MOD + "/recipe/infusion/infinity_wand_cap.json", infinityCapInfusion()));
        tasks.add(save(cache, "data/thaumaturge/data_maps/item/base_aspects.json", baseAspects()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats content";
    }

    private CompletableFuture<?> save(CachedOutput cache, String relativePath, JsonElement json) {
        Path path = output.getOutputFolder().resolve(relativePath);
        return RecipeSaver.save(cache, json, path);
    }

    private static JsonObject terminusAspect() {
        JsonObject json = new JsonObject();
        json.addProperty("color", 0xB90000);
        json.add("components", strings("thaumaturge:desiderium", "thaumaturge:alienis"));
        json.addProperty("tag", "terminus");
        json.addProperty("texture", MOD + ":textures/aspects/terminus.png");
        return json;
    }

    private static JsonObject avaritiaCategory() {
        JsonObject json = new JsonObject();
        json.addProperty("background", "thaumaturge:textures/gui/gui_research_back_6_smooth.png");
        json.add("formula", aspects(
                ASPECT_TERMINUS, 20,
                "thaumaturge:alienis", 10,
                "thaumaturge:desiderium", 10,
                "thaumaturge:praecantatio", 5,
                "thaumaturge:potentia", 5,
                "thaumaturge:metallum", 5,
                "thaumaturge:vitreus", 5,
                "thaumaturge:permutatio", 5));
        json.addProperty("icon", MOD + ":textures/research/cat_avaritia.png");
        json.addProperty("index", 7);
        json.addProperty("overlay_background", "thaumaturge:textures/gui/gui_research_back_over.png");
        return json;
    }

    private static JsonObject pearlResearch() {
        return research(
                "extremely_primordial_pearl",
                "thaumaturge:primordial_pearl",
                0, 0,
                PEARL,
                MOD + ":infusion/extremely_primordial_pearl",
                2,
                aspects("thaumaturge:alienis", 8, "thaumaturge:praecantatio", 6, "thaumaturge:vacuos", 5),
                3);
    }

    private static JsonObject akashicResearch() {
        return research(
                "akashic_record",
                MOD + ":extremely_primordial_pearl",
                2, 0,
                AKASHIC,
                MOD + ":infusion/akashic_record",
                3,
                aspects("thaumaturge:cognitio", 8, "thaumaturge:praecantatio", 6, ASPECT_TERMINUS, 4),
                3);
    }

    private static JsonObject wandResearch() {
        return research(
                "infinity_wand",
                AKASHIC,
                4, 0,
                "thaumaturge:wand",
                MOD + ":infusion/infinity_wand_cap",
                3,
                aspects("thaumaturge:praecantatio", 8, "thaumaturge:potentia", 6, ASPECT_TERMINUS, 4),
                3);
    }

    private static JsonObject research(String name, String parent, int column, int row, String icon, String recipe, int warp, JsonArray noteAspects, int complexity) {
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
        if (recipe != null) {
            second.add("recipes", strings(recipe));
        }
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

    private static JsonObject pearlInfusion() {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 7; i++) {
            components.add(item("thaumaturge:primordial_pearl"));
        }
        return infusion(
                aspects(
                        "thaumaturge:alienis", 64,
                        "thaumaturge:praecantatio", 64,
                        "thaumaturge:aqua", 64,
                        "thaumaturge:ignis", 64,
                        "thaumaturge:terra", 64,
                        "thaumaturge:aer", 64,
                        "thaumaturge:ordo", 64,
                        "thaumaturge:perditio", 64,
                        ASPECT_TERMINUS, 32),
                item("thaumaturge:primordial_pearl"),
                components,
                12,
                PEARL,
                PEARL);
    }

    private static JsonObject infinityCapInfusion() {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(item("avaritia:infinity_ingot"));
        }
        components.add(item(MOD + ":infinity_plate"));
        components.add(item(MOD + ":infinity_plate"));
        return infusion(
                aspects(
                        "thaumaturge:praecantatio", 48,
                        "thaumaturge:alienis", 32,
                        "thaumaturge:potentia", 32,
                        ASPECT_TERMINUS, 24),
                item(MOD + ":neutronium_wand_cap"),
                components,
                10,
                MOD + ":infinity_wand",
                MOD + ":infinity_wand_cap");
    }

    private static JsonObject akashicInfusion() {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(item("avaritia:infinity_ingot"));
        }
        return infusion(
                aspects(
                        "thaumaturge:cognitio", 64,
                        "thaumaturge:praecantatio", 64,
                        ASPECT_TERMINUS, 32),
                item("thaumaturge:curio_knowledge"),
                components,
                12,
                AKASHIC,
                AKASHIC);
    }

    private static JsonObject infusion(JsonArray aspects, JsonObject catalyst, JsonArray components, int instability, String researchEntry, String result) {
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

    private static JsonObject infinityWandRecipe() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "avaritia:shaped_table");

        JsonObject key = new JsonObject();
        key.add("C", item("avaritia:crystal_matrix_ingot"));
        key.add("I", item("avaritia:infinity_ingot"));
        key.add("N", item("avaritia:neutron_ingot"));
        key.add("X", item("avaritia:infinity"));
        json.add("key", key);

        json.add("pattern", strings(
                "      C  ",
                "     CIC ",
                "     NIIC",
                "    NINC ",
                "   NXN   ",
                " CNIN    ",
                "CIIN     ",
                " CIC     ",
                "  C      "));

        JsonObject parts = new JsonObject();
        parts.addProperty("cap", MOD + ":infinity");
        parts.addProperty("rod", MOD + ":infinity");
        JsonObject components = new JsonObject();
        components.add("thaumaturge:wand_parts", parts);

        JsonObject result = new JsonObject();
        result.addProperty("count", 1);
        result.addProperty("id", "thaumaturge:wand");
        result.add("components", components);
        json.add("result", result);

        json.addProperty("tier", 4);
        return json;
    }

    private static JsonObject baseAspects() {
        JsonObject values = new JsonObject();
        values.add("avaritia:crystal_matrix_ingot", aspects(ASPECT_TERMINUS, 1, "thaumaturge:potentia", 8, "thaumaturge:vitreus", 32));
        values.add("avaritia:neutron_ingot", aspects(ASPECT_TERMINUS, 2, "thaumaturge:metallum", 12, "thaumaturge:potentia", 12));
        values.add("avaritia:infinity_catalyst", aspects(ASPECT_TERMINUS, 5, "thaumaturge:permutatio", 12));
        values.add("avaritia:infinity_ingot", aspects(ASPECT_TERMINUS, 16, "thaumaturge:alienis", 64));
        values.add("avaritia:neutron_collector", aspects(ASPECT_TERMINUS, 5, "thaumaturge:metallum", 64, "thaumaturge:potentia", 64, "thaumaturge:machina", 64));
        values.add("avaritia:singularity", aspects(ASPECT_TERMINUS, 3, "thaumaturge:metallum", 100));
        values.add("avaritia:infinity_sword", aspects(ASPECT_TERMINUS, 60, "thaumaturge:aversio", 999, "thaumaturge:mortuus", 999, "thaumaturge:alienis", 100));
        ForceCraftAspects.addItems(values);
        EnderIOAspects.addTo(values);
        BotaniaAspects.addTo(values);

        JsonObject json = new JsonObject();
        json.add("values", values);
        return json;
    }

    private static JsonObject item(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("item", id);
        return json;
    }

    private static JsonArray strings(String... values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            array.add(value);
        }
        return array;
    }

    private static JsonArray aspects(Object... pairs) {
        JsonArray array = new JsonArray();
        for (int i = 0; i < pairs.length; i += 2) {
            JsonObject entry = new JsonObject();
            entry.addProperty("aspect", (String) pairs[i]);
            entry.addProperty("amount", (Integer) pairs[i + 1]);
            array.add(entry);
        }
        return array;
    }
}
