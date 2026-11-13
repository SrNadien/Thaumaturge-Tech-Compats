package nadiendev.ThaumaturgeTechCompats.purity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class PurityVariantContent {
    public static final String BIOME_ENTRY = PurityToggles.BIOME_POTION;
    public static final String CLARITY_ENTRY = PurityToggles.CLARITY_POTION;
    public static final String ABSOLUTION_ENTRY = PurityToggles.ABSOLUTION_POTION;
    public static final String BIOME_ENTRY_ID = PurityContent.MOD + ":" + BIOME_ENTRY;
    public static final String CLARITY_ENTRY_ID = PurityContent.MOD + ":" + CLARITY_ENTRY;
    public static final String ABSOLUTION_ENTRY_ID = PurityContent.MOD + ":" + ABSOLUTION_ENTRY;

    private static final int BIOME_COLUMN = -1;
    private static final int BIOME_ROW = 4;
    private static final int CLARITY_COLUMN = 1;
    private static final int CLARITY_ROW = 4;
    private static final int ABSOLUTION_COLUMN = 1;
    private static final int ABSOLUTION_ROW = 6;

    private PurityVariantContent() {
    }

    public static Map<String, JsonObject> files() {
        Map<String, JsonObject> files = new LinkedHashMap<>();
        files.put("thaumaturge/research_entry/" + BIOME_ENTRY, biomeEntry());
        files.put("recipe/crucible/" + BIOME_ENTRY, biomeRecipe());
        files.put("thaumaturge/research_entry/" + CLARITY_ENTRY, clarityEntry());
        files.put("recipe/crucible/" + CLARITY_ENTRY, clarityRecipe());
        files.put("thaumaturge/research_entry/" + ABSOLUTION_ENTRY, absolutionEntry());
        files.put("recipe/crucible/" + ABSOLUTION_ENTRY, absolutionRecipe());
        return files;
    }

    private static JsonArray conditions(String... toggles) {
        JsonArray array = new JsonArray();
        array.add(CompatJson.feature(CompatToggles.PURIFICATION_POTION));
        for (String toggle : toggles) {
            array.add(CompatJson.feature(toggle));
        }
        return array;
    }

    private static JsonObject entry(String name, List<String> parents, int column, int row, JsonArray noteAspects, int complexity,
                                    JsonArray knowledge, JsonArray conditions) {
        JsonObject json = PurityContent.base(name, parents, column, row, PurityContent.MOD + ":" + name, noteAspects, complexity);
        json.add("neoforge:conditions", conditions);

        JsonArray stages = new JsonArray();
        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + PurityContent.MOD + "." + name + ".stage_0");
        first.add("required_knowledge", knowledge);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + PurityContent.MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(PurityContent.MOD + ":crucible/" + name));
        stages.add(second);

        json.add("stages", stages);
        return json;
    }

    private static JsonObject recipe(String name, JsonArray aspects, String catalyst, JsonArray conditions) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions);
        json.addProperty("type", "thaumaturge:crucible");
        json.add("aspects", aspects);
        json.add("catalyst", ContentJson.item(catalyst));
        JsonObject research = new JsonObject();
        research.addProperty("entry", PurityContent.MOD + ":" + name);
        json.add("research", research);
        json.add("result", ContentJson.result(PurityContent.MOD + ":" + name, 1));
        return json;
    }

    private static JsonArray knowledge(JsonObject... entries) {
        JsonArray array = new JsonArray();
        for (JsonObject entry : entries) {
            array.add(entry);
        }
        return array;
    }

    private static JsonObject biomeEntry() {
        return entry(BIOME_ENTRY, List.of(PurityContent.ENTRY_ID), BIOME_COLUMN, BIOME_ROW,
                ContentJson.aspects("thaumaturge:ordo", 6, "thaumaturge:victus", 4, "thaumaturge:vitium", 4, "thaumaturge:auram", 3), 3,
                knowledge(PurityContent.knowledge("observation", "thaumaturge:auromancy", 2),
                        PurityContent.knowledge("theory", "thaumaturge:auromancy", 2),
                        PurityContent.knowledge("theory", "thaumaturge:alchemy", 3)),
                conditions(PurityToggles.BIOME_POTION));
    }

    private static JsonObject biomeRecipe() {
        return recipe(BIOME_ENTRY, ContentJson.aspects(
                "thaumaturge:ordo", 80,
                "thaumaturge:victus", 60,
                "thaumaturge:aqua", 60,
                "thaumaturge:lux", 60,
                "thaumaturge:auram", 50,
                "thaumaturge:praecantatio", 40,
                "thaumaturge:vitreus", 40,
                "thaumaturge:herba", 30),
                PurityContent.MOD + ":" + PurityRegistry.POTION_NAME,
                conditions(PurityToggles.BIOME_POTION));
    }

    private static JsonObject clarityEntry() {
        return entry(CLARITY_ENTRY, List.of(PurityContent.ENTRY_ID), CLARITY_COLUMN, CLARITY_ROW,
                ContentJson.aspects("thaumaturge:cognitio", 5, "thaumaturge:ordo", 4, "thaumaturge:spiritus", 3, "thaumaturge:alienis", 3), 2,
                knowledge(PurityContent.knowledge("observation", "thaumaturge:eldritch", 1),
                        PurityContent.knowledge("theory", "thaumaturge:eldritch", 1),
                        PurityContent.knowledge("theory", "thaumaturge:alchemy", 2)),
                conditions(PurityToggles.CLARITY_POTION));
    }

    private static JsonObject clarityRecipe() {
        return recipe(CLARITY_ENTRY, ContentJson.aspects(
                "thaumaturge:cognitio", 60,
                "thaumaturge:ordo", 50,
                "thaumaturge:spiritus", 40,
                "thaumaturge:aqua", 40,
                "thaumaturge:victus", 30,
                "thaumaturge:alienis", 20),
                "thaumaturge:sanity_soap",
                conditions(PurityToggles.CLARITY_POTION));
    }

    private static JsonObject absolutionEntry() {
        return entry(ABSOLUTION_ENTRY, List.of(CLARITY_ENTRY_ID, "thaumaturge:voidseer_pearl"), ABSOLUTION_COLUMN, ABSOLUTION_ROW,
                ContentJson.aspects("thaumaturge:alienis", 6, "thaumaturge:cognitio", 6, "thaumaturge:vacuos", 5, "thaumaturge:spiritus", 4), 3,
                knowledge(PurityContent.knowledge("observation", "thaumaturge:eldritch", 3),
                        PurityContent.knowledge("theory", "thaumaturge:eldritch", 3),
                        PurityContent.knowledge("theory", "thaumaturge:alchemy", 3)),
                conditions(PurityToggles.CLARITY_POTION, PurityToggles.ABSOLUTION_POTION));
    }

    private static JsonObject absolutionRecipe() {
        return recipe(ABSOLUTION_ENTRY, ContentJson.aspects(
                "thaumaturge:cognitio", 100,
                "thaumaturge:ordo", 100,
                "thaumaturge:spiritus", 80,
                "thaumaturge:alienis", 80,
                "thaumaturge:praecantatio", 80,
                "thaumaturge:vacuos", 60,
                "thaumaturge:tenebrae", 60,
                "thaumaturge:victus", 60,
                "thaumaturge:auram", 50,
                "thaumaturge:perditio", 40),
                "thaumaturge:eldritch_eye",
                conditions(PurityToggles.CLARITY_POTION, PurityToggles.ABSOLUTION_POTION));
    }
}
