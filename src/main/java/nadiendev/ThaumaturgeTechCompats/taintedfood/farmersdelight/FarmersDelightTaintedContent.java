package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.taintedfood.TaintedFoodIntegration;
import nadiendev.ThaumaturgeTechCompats.taintedfood.TaintedFoodType;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FarmersDelightTaintedContent implements DataProvider {
    public static final String ENTRY_FOOD = "tainted_farmersdelight_food";
    public static final int COLUMN = 45;
    public static final int ROW_FOOD = 2;
    public static final int ROW_KNIVES = 4;
    public static final int ROW_FLUX_KNIFE = ROW_KNIVES + 3;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String CRUCIBLE_DIR = "crucible/tainted_farmersdelight/";
    private static final String ARCANE_DIR = "arcane/farmersdelight_knives/";
    private static final String COOKING_DIR = "cooking/tainted_farmersdelight/";
    private static final String CUTTING_DIR = "cutting/tainted_farmersdelight/";
    private static final String INFUSION_DIR = "infusion/";
    private static final String FLUX_RECIPE = "farmersdelight_" + FluxKnife.ID;
    private static final String FLUX_CRYSTAL = "thaumaturge:crystal_vitium";
    private static final String TAINTED_GOO = "thaumaturge:tainted_goo";
    private static final String FLUX_BOTTLE = "thaumaturge:bottle_taint";
    private static final String ASPECT_FLUX = "thaumaturge:vitium";
    private static final String STICK = "minecraft:stick";
    private static final String BOWL = "minecraft:bowl";
    private static final String CARROT = TaintedFoodType.CARROT.itemId().toString();
    private static final String POTATO = TaintedFoodType.BAKED_POTATO.itemId().toString();
    private static final String BEEF = TaintedFoodType.COOKED_BEEF.itemId().toString();
    private static final String PORKCHOP = TaintedFoodType.COOKED_PORKCHOP.itemId().toString();

    private final PackOutput output;

    public FarmersDelightTaintedContent(PackOutput output) {
        this.output = output;
    }

    private record Cooking(String name, FarmersDelightFood result, List<JsonObject> ingredients) {
    }

    private record Cutting(String name, String input, FarmersDelightFood result, int count) {
    }

    private static List<Cooking> cookingRecipes() {
        return List.of(
                new Cooking("beef_stew", FarmersDelightFood.BEEF_STEW,
                        List.of(ContentJson.item(BEEF), ContentJson.item(CARROT), ContentJson.item(POTATO))),
                new Cooking("vegetable_soup", FarmersDelightFood.VEGETABLE_SOUP,
                        List.of(ContentJson.item(CARROT), ContentJson.item(POTATO), tag("c:crops/beetroot"), tag("c:foods/leafy_green"))),
                new Cooking("steak_and_potatoes", FarmersDelightFood.STEAK_AND_POTATOES,
                        List.of(ContentJson.item(BEEF), ContentJson.item(POTATO), tag("c:crops/onion"))),
                new Cooking("fried_rice", FarmersDelightFood.FRIED_RICE,
                        List.of(tag("c:crops/rice"), tag("c:eggs"), ContentJson.item(CARROT), tag("c:crops/onion"))));
    }

    private static List<Cutting> cuttingRecipes() {
        return List.of(
                new Cutting("beef_patty", BEEF, FarmersDelightFood.BEEF_PATTY, 2),
                new Cutting("cooked_bacon", PORKCHOP, FarmersDelightFood.COOKED_BACON, 2));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "thaumaturge/research_entry/" + ENTRY_FOOD, foodEntry()));
        for (FarmersDelightFood food : FarmersDelightFood.values()) {
            tasks.add(save(cache, "recipe/" + CRUCIBLE_DIR + food.id(), crucible(food)));
        }
        for (FarmersDelightKnife knife : FarmersDelightKnife.values()) {
            tasks.add(save(cache, "recipe/" + ARCANE_DIR + knife.id(), arcane(knife)));
            tasks.add(save(cache, "thaumaturge/research_entry/" + knife.entryName(), knifeEntry(knife)));
        }
        for (Cooking cooking : cookingRecipes()) {
            tasks.add(save(cache, "recipe/" + COOKING_DIR + cooking.name(), cooking(cooking)));
        }
        for (Cutting cutting : cuttingRecipes()) {
            tasks.add(save(cache, "recipe/" + CUTTING_DIR + cutting.name(), cutting(cutting)));
        }
        tasks.add(save(cache, "recipe/" + INFUSION_DIR + FLUX_RECIPE, fluxInfusion()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + FluxKnife.ENTRY_NAME, fluxEntry()));
        tasks.add(ContentJson.save(output, cache, "data/c/tags/item/tools/knife.json", knifeTag()));
        tasks.add(ContentJson.save(output, cache, "data/" + FarmersDelightTaintedIntegration.MOD_ID + "/tags/item/tools/knives.json", knifeTag()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats tainted Farmer's Delight";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions(String toggle) {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.modLoaded(FarmersDelightTaintedIntegration.MOD_ID));
        conditions.add(CompatJson.feature(toggle));
        return conditions;
    }

    private static JsonObject itemExists(String item) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:item_exists");
        condition.addProperty("item", item);
        return condition;
    }

    private static JsonObject tag(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("tag", id);
        return json;
    }

    private static JsonObject knowledge(String type, String category, int amount) {
        JsonObject json = new JsonObject();
        json.addProperty("type", type);
        json.addProperty("category", category);
        json.addProperty("amount", amount);
        return json;
    }

    private static JsonObject knifeTag() {
        JsonArray values = new JsonArray();
        for (FarmersDelightKnife knife : FarmersDelightKnife.values()) {
            JsonObject value = new JsonObject();
            value.addProperty("id", knife.itemId().toString());
            value.addProperty("required", false);
            values.add(value);
        }
        JsonObject fluxValue = new JsonObject();
        fluxValue.addProperty("id", FluxKnife.itemId().toString());
        fluxValue.addProperty("required", false);
        values.add(fluxValue);
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        json.add("values", values);
        return json;
    }

    private static JsonObject crucible(FarmersDelightFood food) {
        JsonArray conditions = conditions(FarmersDelightTaintedIntegration.TOGGLE_FOOD);
        conditions.add(itemExists(food.itemId().toString()));
        JsonObject json = CompatJson.recipe("thaumaturge:crucible", conditions);
        json.add("aspects", ContentJson.aspects(ASPECT_FLUX, food.vitium()));
        json.add("catalyst", ContentJson.item(food.original().toString()));
        JsonObject research = new JsonObject();
        research.addProperty("entry", MOD + ":" + ENTRY_FOOD);
        json.add("research", research);
        json.add("result", ContentJson.result(food.itemId().toString(), 1));
        return json;
    }

    private static JsonObject arcane(FarmersDelightKnife knife) {
        FarmersDelightKnife previous = knife.previous();
        JsonObject key = new JsonObject();
        key.add("p", ContentJson.item(knife.plate().toString()));
        key.add("s", ContentJson.item(previous == null ? STICK : previous.itemId().toString()));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings(" p ", " s "), MOD + ":" + knife.entryName(),
                knife.vis(), knife.itemId().toString());
        json.add("neoforge:conditions", knifeConditions(knife));
        return json;
    }

    private static JsonArray knifeConditions(FarmersDelightKnife knife) {
        JsonArray conditions = conditions(FarmersDelightTaintedIntegration.TOGGLE_KNIVES);
        for (FarmersDelightKnife current = knife; current != null; current = current.previous()) {
            conditions.add(itemExists(current.itemId().toString()));
        }
        return conditions;
    }

    private static JsonArray fluxConditions() {
        JsonArray conditions = conditions(FarmersDelightTaintedIntegration.TOGGLE_FLUX_KNIFE);
        conditions.add(itemExists(FluxKnife.itemId().toString()));
        conditions.add(itemExists(FarmersDelightKnife.VOID_METAL.itemId().toString()));
        return conditions;
    }

    private static JsonObject fluxInfusion() {
        JsonArray components = new JsonArray();
        components.add(ContentJson.item(FLUX_CRYSTAL));
        components.add(ContentJson.item(TAINTED_GOO));
        components.add(ContentJson.item(FLUX_CRYSTAL));
        components.add(ContentJson.item(FLUX_BOTTLE));
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects(ASPECT_FLUX, 40, "thaumaturge:vacuos", 24, "thaumaturge:alienis", 12, "thaumaturge:instrumentum", 16),
                ContentJson.item(FarmersDelightKnife.VOID_METAL.itemId().toString()), components, 6,
                MOD + ":" + FluxKnife.ENTRY_NAME, FluxKnife.itemId().toString());
        json.add("neoforge:conditions", fluxConditions());
        return json;
    }

    private static JsonObject fluxEntry() {
        JsonArray required = new JsonArray();
        required.add(knowledge("theory", "thaumaturge:eldritch", 4));
        return entry(fluxConditions(), FluxKnife.ENTRY_NAME, ROW_FLUX_KNIFE,
                List.of(MOD + ":" + FarmersDelightKnife.VOID_METAL.entryName(), "thaumaturge:base_infusion", "thaumaturge:flux_rift"),
                FluxKnife.itemId().toString(), required, 3,
                List.of(MOD + ":" + INFUSION_DIR + FLUX_RECIPE),
                ContentJson.aspects(ASPECT_FLUX, 8, "thaumaturge:vacuos", 6, "thaumaturge:alienis", 5, "thaumaturge:instrumentum", 6), 3);
    }

    private static JsonArray taintedConditions(List<String> items, String result) {
        JsonArray conditions = conditions(FarmersDelightTaintedIntegration.TOGGLE_FOOD);
        conditions.add(CompatJson.feature(TaintedFoodIntegration.TOGGLE));
        for (String item : items) {
            conditions.add(itemExists(item));
        }
        conditions.add(itemExists(result));
        return conditions;
    }

    private static JsonObject cooking(Cooking cooking) {
        List<String> existing = new ArrayList<>();
        JsonArray ingredients = new JsonArray();
        for (JsonObject ingredient : cooking.ingredients()) {
            ingredients.add(ingredient);
            if (ingredient.has("item")) {
                existing.add(ingredient.get("item").getAsString());
            }
        }
        JsonObject json = CompatJson.recipe("farmersdelight:cooking", taintedConditions(existing, cooking.result().itemId().toString()));
        json.addProperty("cookingtime", 200);
        json.add("container", ContentJson.result(BOWL, 1));
        json.addProperty("experience", 1.0F);
        json.add("ingredients", ingredients);
        json.addProperty("recipe_book_tab", "meals");
        json.add("result", ContentJson.result(cooking.result().itemId().toString(), 1));
        return json;
    }

    private static JsonObject cutting(Cutting cutting) {
        JsonObject json = CompatJson.recipe("farmersdelight:cutting",
                taintedConditions(List.of(cutting.input()), cutting.result().itemId().toString()));
        JsonArray ingredients = new JsonArray();
        ingredients.add(ContentJson.item(cutting.input()));
        json.add("ingredients", ingredients);
        JsonObject stack = new JsonObject();
        stack.add("item", ContentJson.result(cutting.result().itemId().toString(), cutting.count()));
        JsonArray results = new JsonArray();
        results.add(stack);
        json.add("result", results);
        JsonObject ability = new JsonObject();
        ability.addProperty("type", "farmersdelight:item_ability");
        ability.addProperty("action", "knife_dig");
        JsonArray tool = new JsonArray();
        tool.add(ability);
        tool.add(tag("c:tools/knife"));
        json.add("tool", tool);
        return json;
    }

    private static JsonObject foodEntry() {
        JsonArray conditions = conditions(FarmersDelightTaintedIntegration.TOGGLE_FOOD);
        List<String> recipes = new ArrayList<>();
        for (FarmersDelightFood food : FarmersDelightFood.values()) {
            recipes.add(MOD + ":" + CRUCIBLE_DIR + food.id());
        }
        JsonArray required = new JsonArray();
        required.add(knowledge("observation", "thaumaturge:alchemy", 1));
        required.add(knowledge("theory", "thaumaturge:alchemy", 2));
        return entry(conditions, ENTRY_FOOD, ROW_FOOD,
                List.of(TechCategory.HEADER_ID, "thaumaturge:bottle_taint"),
                FarmersDelightFood.BEEF_STEW.itemId().toString(), required, 1, recipes,
                ContentJson.aspects("thaumaturge:vitium", 6, "thaumaturge:victus", 5, "thaumaturge:alkimia", 3), 2);
    }

    private static List<String> knifeParents(FarmersDelightKnife knife) {
        return switch (knife) {
            case THAUMIUM -> List.of(TechCategory.HEADER_ID, "thaumaturge:metallurgy@1");
            case ALCHEMICAL_BRASS -> List.of(MOD + ":" + FarmersDelightKnife.THAUMIUM.entryName(), "thaumaturge:base_artifice", "thaumaturge:metallurgy@2");
            case VOID_METAL -> List.of(MOD + ":" + FarmersDelightKnife.ALCHEMICAL_BRASS.entryName(), "thaumaturge:base_artifice", "thaumaturge:base_eldritch", "thaumaturge:bottle_taint");
        };
    }

    private static String knifeCategory(FarmersDelightKnife knife) {
        return switch (knife) {
            case THAUMIUM -> "thaumaturge:alchemy";
            case ALCHEMICAL_BRASS -> "thaumaturge:artifice";
            case VOID_METAL -> "thaumaturge:eldritch";
        };
    }

    private static JsonArray knifeAspects(FarmersDelightKnife knife) {
        return switch (knife) {
            case THAUMIUM -> ContentJson.aspects("thaumaturge:instrumentum", 4, "thaumaturge:praecantatio", 4, "thaumaturge:victus", 3);
            case ALCHEMICAL_BRASS -> ContentJson.aspects("thaumaturge:instrumentum", 6, "thaumaturge:metallum", 5, "thaumaturge:victus", 5);
            case VOID_METAL -> ContentJson.aspects("thaumaturge:instrumentum", 8, "thaumaturge:vitium", 6, "thaumaturge:vacuos", 6);
        };
    }

    private static JsonObject knifeEntry(FarmersDelightKnife knife) {
        JsonArray required = new JsonArray();
        required.add(knowledge("theory", knifeCategory(knife), 1 + knife.ordinal()));
        return entry(knifeConditions(knife), knife.entryName(), ROW_KNIVES + knife.ordinal(), knifeParents(knife),
                knife.itemId().toString(), required, knife.ordinal(),
                List.of(MOD + ":" + ARCANE_DIR + knife.id()), knifeAspects(knife), 1 + knife.ordinal());
    }

    private static JsonObject entry(JsonArray conditions, String name, int row, List<String> parents, String icon,
                                    JsonArray required, int warp, List<String> recipes, JsonArray noteAspects, int complexity) {
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
        first.add("required_knowledge", required);
        first.addProperty("warp", warp);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(recipes));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }
}
