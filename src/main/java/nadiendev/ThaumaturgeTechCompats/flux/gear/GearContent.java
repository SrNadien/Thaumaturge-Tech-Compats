package nadiendev.ThaumaturgeTechCompats.flux.gear;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.charger.ChargerContent;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GearContent implements DataProvider {
    public static final String SIPHON_ENTRY = "flux_" + GearRegistry.SIPHON_NAME;
    public static final String CANNON_ENTRY = "flux_" + GearRegistry.CANNON_NAME;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final int COLUMN = 58;
    private static final String SIPHON_RECIPE = "arcane_workbench/" + GearRegistry.SIPHON_NAME;
    private static final String CANNON_RECIPE = "infusion/" + GearRegistry.CANNON_NAME;

    private final PackOutput output;

    public GearContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.allOf(
                save(cache, "recipe/" + SIPHON_RECIPE, siphonRecipe()),
                save(cache, "recipe/" + CANNON_RECIPE, cannonRecipe()),
                save(cache, "thaumaturge/research_entry/" + SIPHON_ENTRY, siphonEntry()),
                save(cache, "thaumaturge/research_entry/" + CANNON_ENTRY, cannonEntry()));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats flux gear";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions(FluxTier tier, String toggle) {
        JsonArray conditions = new JsonArray();
        for (FluxTier step : FluxTier.values()) {
            if (step.ordinal() <= tier.ordinal()) {
                conditions.add(CompatJson.feature(step.toggle()));
            }
        }
        conditions.add(CompatJson.feature(toggle));
        return conditions;
    }

    private static String itemId(String name) {
        return MOD + ":" + name;
    }

    private static JsonObject siphonRecipe() {
        JsonObject key = new JsonObject();
        key.add("P", ContentJson.item("thaumaturge:plate_brass"));
        key.add("V", ContentJson.item("thaumaturge:void_seed"));
        key.add("M", ContentJson.item("thaumaturge:mechanism_simple"));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("PVP", " M ", " P "),
                MOD + ":" + SIPHON_ENTRY, 80, itemId(GearRegistry.SIPHON_NAME));
        json.add("crystals", ContentJson.aspects("thaumaturge:aer", 2, "thaumaturge:perditio", 2, "thaumaturge:ordo", 1));
        json.add("neoforge:conditions", conditions(GearIntegration.SIPHON_TIER, GearIntegration.SIPHON_TOGGLE));
        return json;
    }

    private static JsonObject cannonRecipe() {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item("thaumaturge:plate_void"));
        }
        for (int i = 0; i < 2; i++) {
            components.add(ContentJson.item("thaumaturge:void_seed"));
            components.add(ContentJson.item("thaumaturge:vis_resonator"));
        }
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:potentia", 80, "thaumaturge:ignis", 60, "thaumaturge:motus", 40, "thaumaturge:machina", 40),
                ContentJson.item("thaumaturge:mechanism_complex"),
                components,
                5,
                MOD + ":" + CANNON_ENTRY,
                itemId(GearRegistry.CANNON_NAME));
        json.add("neoforge:conditions", conditions(GearIntegration.CANNON_TIER, GearIntegration.CANNON_TOGGLE));
        return json;
    }

    private static JsonObject entry(String name, List<String> parents, int row, String icon, String recipe, String knowledge, int theory, int warp, int complexity, JsonArray notes, JsonArray conditions) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", COLUMN);
        json.addProperty("row", row);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(icon));

        JsonArray stages = new JsonArray();
        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        JsonObject required = new JsonObject();
        required.addProperty("type", "theory");
        required.addProperty("category", knowledge);
        required.addProperty("amount", theory);
        JsonArray requirements = new JsonArray();
        requirements.add(required);
        first.add("required_knowledge", requirements);
        first.addProperty("warp", warp);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(MOD + ":" + recipe));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", notes);
        json.addProperty("complexity", complexity);
        return json;
    }

    private static JsonObject siphonEntry() {
        return entry(SIPHON_ENTRY,
                List.of(MOD + ":" + ChargerContent.research(FluxTier.ALCHEMICAL_BRASS), "thaumaturge:flux_cleanup"),
                4, itemId(GearRegistry.SIPHON_NAME), SIPHON_RECIPE, "thaumaturge:eldritch", 2, 2, 2,
                ContentJson.aspects("thaumaturge:vacuos", 8, "thaumaturge:potentia", 6, "thaumaturge:motus", 5),
                conditions(GearIntegration.SIPHON_TIER, GearIntegration.SIPHON_TOGGLE));
    }

    private static JsonObject cannonEntry() {
        return entry(CANNON_ENTRY,
                List.of(MOD + ":" + ChargerContent.research(FluxTier.VOID_METAL), "thaumaturge:base_eldritch"),
                6, itemId(GearRegistry.CANNON_NAME), CANNON_RECIPE, "thaumaturge:eldritch", 3, 3, 3,
                ContentJson.aspects("thaumaturge:potentia", 10, "thaumaturge:ignis", 8, "thaumaturge:vacuos", 8, "thaumaturge:machina", 6),
                conditions(GearIntegration.CANNON_TIER, GearIntegration.CANNON_TOGGLE));
    }
}
