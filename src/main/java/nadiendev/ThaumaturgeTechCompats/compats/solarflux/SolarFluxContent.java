package nadiendev.ThaumaturgeTechCompats.compats.solarflux;

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

public class SolarFluxContent implements DataProvider {
    public static final String ASPECT_SOL = ThaumaturgeTechCompats.MOD_ID + ":sol";
    public static final String CATEGORY_ID = TechCategory.CATEGORY_ID;
    public static final String ROOT = "solarflux_alchemical";
    public static final String BRASS = "solarflux_brass";
    public static final String THAUMIUM = "solarflux_thaumium";
    public static final String VOID = "solarflux_void";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String SF = SolarFluxIntegration.MOD_ID;

    private final PackOutput output;

    public SolarFluxContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "thaumaturge/aspect/sol", aspect()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + ROOT, entry(ROOT,
                List.of(TechCategory.HEADER_ID), 12, 2, SF + ":sp_alchemical", "crucible/alchemical_solar_panel,crucible/alchemical_solar_panel_twilight,crucible/alchemical_solar_panel_draconic",
                "thaumaturge:alchemy", 1, 1,
                ContentJson.aspects("thaumaturge:lux", 6, "thaumaturge:alkimia", 5, ASPECT_SOL, 4), 1, List.of("round", "hidden"))));
        tasks.add(save(cache, "thaumaturge/research_entry/" + BRASS, entry(BRASS,
                List.of(MOD + ":" + ROOT, "thaumaturge:metallurgy@1"), 12, 4, SF + ":sp_alchemical_brass", "brass_solar_panel",
                "thaumaturge:alchemy", 1, 2,
                ContentJson.aspects("thaumaturge:lux", 8, "thaumaturge:metallum", 6, ASPECT_SOL, 4), 2, List.of("round", "hidden"))));
        tasks.add(save(cache, "thaumaturge/research_entry/" + THAUMIUM, entry(THAUMIUM,
                List.of(MOD + ":" + BRASS, "thaumaturge:base_artifice", "thaumaturge:metallurgy@2"), 12, 6, SF + ":sp_thaumium", "infusion/thaumium_solar_panel",
                "thaumaturge:artifice", 2, 2,
                ContentJson.aspects("thaumaturge:lux", 10, "thaumaturge:potentia", 8, ASPECT_SOL, 6), 2, List.of("round", "hidden"))));
        tasks.add(save(cache, "thaumaturge/research_entry/" + VOID, entry(VOID,
                List.of(MOD + ":" + THAUMIUM, "thaumaturge:base_artifice", "thaumaturge:base_eldritch"), 12, 8, SF + ":sp_void_metal", "infusion/void_metal_solar_panel",
                "thaumaturge:eldritch", 2, 3,
                ContentJson.aspects("thaumaturge:lux", 12, "thaumaturge:vacuos", 8, "thaumaturge:potentia", 8, ASPECT_SOL, 8), 3, List.of("round", "hidden"))));
        tasks.add(save(cache, "recipe/crucible/alchemical_solar_panel", alchemicalRecipe(SF + ":sp_8", false, false)));
        tasks.add(save(cache, "recipe/crucible/alchemical_solar_panel_twilight", alchemicalRecipe(SF + ":sp_tf.fiery", true, false)));
        tasks.add(save(cache, "recipe/crucible/alchemical_solar_panel_draconic", alchemicalRecipe(SF + ":sp_de.chaotic", false, true)));
        tasks.add(save(cache, "recipe/brass_solar_panel", brassRecipe()));
        tasks.add(save(cache, "recipe/infusion/thaumium_solar_panel", thaumiumRecipe()));
        tasks.add(save(cache, "recipe/infusion/void_metal_solar_panel", voidRecipe()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats solar flux";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions(String... features) {
        JsonArray array = CompatJson.conditions(SF);
        for (String feature : features) {
            if (feature != null) {
                array.add(CompatJson.feature(feature));
            }
        }
        return array;
    }

    private static String[] featureOf(String entry) {
        return switch (entry) {
            case ROOT -> new String[]{CompatToggles.SOLAR_ALCHEMICAL};
            case BRASS -> new String[]{CompatToggles.SOLAR_ALCHEMICAL, CompatToggles.SOLAR_ALCHEMICAL_BRASS};
            case THAUMIUM -> new String[]{CompatToggles.SOLAR_ALCHEMICAL, CompatToggles.SOLAR_ALCHEMICAL_BRASS, CompatToggles.SOLAR_THAUMIUM};
            case VOID -> new String[]{CompatToggles.SOLAR_ALCHEMICAL, CompatToggles.SOLAR_ALCHEMICAL_BRASS, CompatToggles.SOLAR_THAUMIUM, CompatToggles.SOLAR_VOID_METAL};
            default -> new String[0];
        };
    }

    private static JsonObject aspect() {
        JsonObject json = new JsonObject();
        json.addProperty("color", 0xFFB600);
        json.add("components", ContentJson.strings("thaumaturge:lux", "thaumaturge:victus"));
        json.addProperty("tag", "sol");
        json.addProperty("texture", MOD + ":textures/aspects/sol.png");
        return json;
    }

    private static JsonObject entry(String name, List<String> parents, int column, int row, String icon, String recipe, String knowledgeCategory, int theory, int warp, JsonArray noteAspects, int complexity, List<String> meta) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(featureOf(name)));
        json.addProperty("category", CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", column);
        json.addProperty("row", row);
        json.add("meta", ContentJson.strings(meta));
        json.add("icons", ContentJson.strings(icon));

        JsonArray stages = new JsonArray();
        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", knowledgeCategory);
        knowledge.addProperty("amount", theory);
        JsonArray required = new JsonArray();
        required.add(knowledge);
        first.add("required_knowledge", required);
        first.addProperty("warp", warp - 1);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        if (recipe != null) {
            List<String> recipeIds = new ArrayList<>();
            for (String part : recipe.split(",")) {
                recipeIds.add(MOD + ":" + part);
            }
            second.add("recipes", ContentJson.strings(recipeIds));
        }
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }

    private static JsonObject researchRef(String entry) {
        JsonObject research = new JsonObject();
        research.addProperty("entry", MOD + ":" + entry);
        return research;
    }

    private static JsonObject alchemicalRecipe(String catalyst, boolean twilight, boolean draconic) {
        JsonObject json = new JsonObject();
        JsonArray conditions = conditions(featureOf(ROOT));
        if (draconic) {
            conditions.add(modLoaded("draconicevolution"));
        } else if (twilight) {
            conditions.add(modLoaded("twilightforest"));
            conditions.add(not(modLoaded("draconicevolution")));
        } else {
            conditions.add(not(modLoaded("twilightforest")));
            conditions.add(not(modLoaded("draconicevolution")));
        }
        json.add("neoforge:conditions", conditions);
        json.addProperty("type", "thaumaturge:crucible");
        json.add("aspects", ContentJson.aspects(ASPECT_SOL, 20, "thaumaturge:machina", 10));
        json.add("catalyst", ContentJson.item(catalyst));
        json.add("research", researchRef(ROOT));
        json.add("result", ContentJson.result(SF + ":sp_alchemical", 1));
        return json;
    }

    private static JsonObject modLoaded(String modId) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:mod_loaded");
        condition.addProperty("modid", modId);
        return condition;
    }

    private static JsonObject not(JsonObject value) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:not");
        condition.add("value", value);
        return condition;
    }

    private static JsonObject brassRecipe() {
        JsonObject key = new JsonObject();
        key.add("p", ContentJson.item(SF + ":photovoltaic_cell_1"));
        key.add("b", ContentJson.item("thaumaturge:plate_brass"));
        key.add("s", ContentJson.item(SF + ":sp_alchemical"));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("ppp", "bsb", "bbb"), MOD + ":" + BRASS, 20, SF + ":sp_alchemical_brass");
        json.add("neoforge:conditions", conditions(featureOf(BRASS)));
        return json;
    }

    private static JsonObject thaumiumRecipe() {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 3; i++) {
            components.add(ContentJson.item("thaumaturge:plate_thaumium"));
            components.add(ContentJson.item(SF + ":photovoltaic_cell_2"));
        }
        components.add(ContentJson.item("thaumaturge:plate_thaumium"));
        components.add(ContentJson.item("thaumaturge:mechanism_simple"));
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects(ASPECT_SOL, 40, "thaumaturge:potentia", 20),
                ContentJson.item(SF + ":sp_alchemical_brass"),
                components,
                3,
                MOD + ":" + THAUMIUM,
                SF + ":sp_thaumium");
        json.add("neoforge:conditions", conditions(featureOf(THAUMIUM)));
        return json;
    }

    private static JsonObject voidRecipe() {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 3; i++) {
            components.add(ContentJson.item("thaumaturge:plate_void"));
            components.add(ContentJson.item(SF + ":photovoltaic_cell_3"));
        }
        components.add(ContentJson.item("thaumaturge:plate_void"));
        components.add(ContentJson.item("thaumaturge:mechanism_complex"));
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects(ASPECT_SOL, 60, "thaumaturge:potentia", 40, "thaumaturge:vacuos", 20),
                ContentJson.item(SF + ":sp_thaumium"),
                components,
                4,
                MOD + ":" + VOID,
                SF + ":sp_void_metal");
        json.add("neoforge:conditions", conditions(featureOf(VOID)));
        return json;
    }
}
