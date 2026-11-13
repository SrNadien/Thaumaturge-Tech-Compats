package nadiendev.ThaumaturgeTechCompats.compats.create.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.seal.CreateSeals;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.VariantKind;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateContent implements DataProvider {
    public static final String SEAL = "create_seal";
    public static final String BRASS = "create_casing_brass";
    public static final String THAUMIUM = "create_casing_thaumium";
    public static final String VOID = "create_casing_void";

    public static final int COLUMN = 6;
    public static final int ROW_SEAL = 2;
    public static final int ROW_BRASS = 4;
    public static final int ROW_THAUMIUM = 6;
    public static final int ROW_VOID = 8;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String CREATE = CreateIntegration.MOD_ID;
    private static final String TECH = MOD + ":tech";

    private final PackOutput output;

    public CreateContent(PackOutput output) {
        this.output = output;
    }

    public static String entryOf(CasingTier tier) {
        return switch (tier) {
            case BRASS -> BRASS;
            case THAUMIUM -> THAUMIUM;
            case VOID -> VOID;
        };
    }

    public static String variantRecipePath(VariantKind kind, CasingTier tier) {
        return "infusion/create_variant_" + kind.blockName(tier);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "thaumaturge/research_entry/" + SEAL, entry(SEAL,
                List.of(TECH, "thaumaturge:seal_use"), ROW_SEAL, MOD + ":" + CreateSeals.ITEM_NAME, List.of("infusion/create_seal"),
                "thaumaturge:golemancy", 1, 1,
                ContentJson.aspects("thaumaturge:machina", 4, "thaumaturge:instrumentum", 3, "thaumaturge:motus", 3), 2)));
        tasks.add(save(cache, "thaumaturge/research_entry/" + BRASS, entry(BRASS,
                List.of(MOD + ":" + SEAL, "thaumaturge:metallurgy@1"), ROW_BRASS, MOD + ":" + CasingTier.BRASS.blockName(), recipes(CasingTier.BRASS, "create_casing_brass"),
                "thaumaturge:artifice", 1, 2,
                ContentJson.aspects("thaumaturge:machina", 6, "thaumaturge:metallum", 5, "thaumaturge:auram", 4), 2)));
        tasks.add(save(cache, "thaumaturge/research_entry/" + THAUMIUM, entry(THAUMIUM,
                List.of(MOD + ":" + BRASS, "thaumaturge:base_artifice", "thaumaturge:metallurgy@2"), ROW_THAUMIUM, MOD + ":" + CasingTier.THAUMIUM.blockName(), recipes(CasingTier.THAUMIUM, "infusion/create_casing_thaumium"),
                "thaumaturge:artifice", 2, 2,
                ContentJson.aspects("thaumaturge:potentia", 8, "thaumaturge:auram", 6, "thaumaturge:motus", 6), 2)));
        tasks.add(save(cache, "thaumaturge/research_entry/" + VOID, entry(VOID,
                List.of(MOD + ":" + THAUMIUM, "thaumaturge:base_artifice", "thaumaturge:base_eldritch"), ROW_VOID, MOD + ":" + CasingTier.VOID.blockName(), recipes(CasingTier.VOID, "infusion/create_casing_void"),
                "thaumaturge:eldritch", 2, 3,
                ContentJson.aspects("thaumaturge:vacuos", 8, "thaumaturge:potentia", 8, "thaumaturge:auram", 8), 3)));
        tasks.add(save(cache, "recipe/infusion/create_seal", sealRecipe()));
        tasks.add(save(cache, "recipe/create_casing_brass", brassRecipe()));
        tasks.add(save(cache, "recipe/infusion/create_casing_thaumium", thaumiumRecipe()));
        tasks.add(save(cache, "recipe/infusion/create_casing_void", voidRecipe()));
        for (VariantKind kind : VariantKind.values()) {
            for (CasingTier tier : CasingTier.values()) {
                tasks.add(save(cache, "recipe/" + variantRecipePath(kind, tier), variantRecipe(kind, tier)));
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Create tech";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static List<String> recipes(CasingTier tier, String casingRecipe) {
        List<String> recipes = new ArrayList<>();
        recipes.add(casingRecipe);
        for (VariantKind kind : VariantKind.values()) {
            recipes.add(variantRecipePath(kind, tier));
        }
        return recipes;
    }

    private static JsonArray conditions(String... features) {
        JsonArray array = new JsonArray();
        JsonObject loaded = new JsonObject();
        loaded.addProperty("type", "neoforge:mod_loaded");
        loaded.addProperty("modid", CREATE);
        array.add(loaded);
        array.add(CompatJson.feature(CreateToggles.TECH));
        for (String feature : features) {
            array.add(CompatJson.feature(feature));
        }
        return array;
    }

    private static String[] tierFeatures(CasingTier tier) {
        return switch (tier) {
            case BRASS -> new String[]{CreateToggles.CASING_BRASS};
            case THAUMIUM -> new String[]{CreateToggles.CASING_BRASS, CreateToggles.CASING_THAUMIUM};
            case VOID -> new String[]{CreateToggles.CASING_BRASS, CreateToggles.CASING_THAUMIUM, CreateToggles.CASING_VOID};
        };
    }

    private static String[] featureOf(String entry) {
        return switch (entry) {
            case SEAL -> new String[]{CreateToggles.SEAL};
            case BRASS -> tierFeatures(CasingTier.BRASS);
            case THAUMIUM -> tierFeatures(CasingTier.THAUMIUM);
            case VOID -> tierFeatures(CasingTier.VOID);
            default -> new String[0];
        };
    }

    private static JsonObject entry(String name, List<String> parents, int row, String icon, List<String> recipes, String knowledgeCategory, int theory, int warp, JsonArray noteAspects, int complexity) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(featureOf(name)));
        json.addProperty("category", TECH);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", COLUMN);
        json.addProperty("row", row);
        json.add("meta", ContentJson.strings("round", "hidden"));
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
        List<String> ids = new ArrayList<>();
        for (String recipe : recipes) {
            ids.add(MOD + ":" + recipe);
        }
        second.add("recipes", ContentJson.strings(ids));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }

    private static String item(String path) {
        return MOD + ":" + path;
    }

    private static JsonObject sealRecipe() {
        JsonArray components = new JsonArray();
        components.add(ContentJson.item(CREATE + ":cogwheel"));
        components.add(ContentJson.item(CREATE + ":brass_sheet"));
        components.add(ContentJson.item(CREATE + ":andesite_alloy"));
        components.add(ContentJson.item("thaumaturge:mechanism_simple"));
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:machina", 20, "thaumaturge:fabrico", 10, "thaumaturge:motus", 10),
                ContentJson.item("thaumaturge:seal_use"),
                components,
                2,
                item(SEAL),
                item(CreateSeals.ITEM_NAME));
        json.add("neoforge:conditions", conditions(featureOf(SEAL)));
        return json;
    }

    private static JsonObject brassRecipe() {
        JsonObject key = new JsonObject();
        key.add("p", ContentJson.item("thaumaturge:plate_brass"));
        key.add("c", ContentJson.item(CREATE + ":brass_casing"));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings(" p ", "pcp", " p "), item(BRASS), 20, item(CasingTier.BRASS.blockName()));
        json.add("neoforge:conditions", conditions(featureOf(BRASS)));
        return json;
    }

    private static JsonObject thaumiumRecipe() {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item("thaumaturge:plate_thaumium"));
        }
        components.add(ContentJson.item("thaumaturge:mechanism_simple"));
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:potentia", 20, "thaumaturge:auram", 10, "thaumaturge:motus", 10),
                ContentJson.item(item(CasingTier.BRASS.blockName())),
                components,
                3,
                item(THAUMIUM),
                item(CasingTier.THAUMIUM.blockName()));
        json.add("neoforge:conditions", conditions(featureOf(THAUMIUM)));
        return json;
    }

    private static JsonObject voidRecipe() {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item("thaumaturge:plate_void"));
        }
        components.add(ContentJson.item("thaumaturge:mechanism_complex"));
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:vacuos", 20, "thaumaturge:potentia", 20, "thaumaturge:auram", 20),
                ContentJson.item(item(CasingTier.THAUMIUM.blockName())),
                components,
                4,
                item(VOID),
                item(CasingTier.VOID.blockName()));
        json.add("neoforge:conditions", conditions(featureOf(VOID)));
        return json;
    }

    private static JsonObject variantRecipe(VariantKind kind, CasingTier tier) {
        JsonArray components = new JsonArray();
        components.add(ContentJson.item(item(tier.blockName())));
        String plate = switch (tier) {
            case BRASS -> "thaumaturge:plate_brass";
            case THAUMIUM -> "thaumaturge:plate_thaumium";
            case VOID -> "thaumaturge:plate_void";
        };
        components.add(ContentJson.item(plate));
        components.add(ContentJson.item(plate));
        switch (tier) {
            case THAUMIUM -> components.add(ContentJson.item("thaumaturge:mechanism_simple"));
            case VOID -> components.add(ContentJson.item("thaumaturge:mechanism_complex"));
            default -> {
            }
        }
        JsonArray aspects = switch (tier) {
            case BRASS -> ContentJson.aspects("thaumaturge:machina", 20, "thaumaturge:metallum", 10, "thaumaturge:motus", 10);
            case THAUMIUM -> ContentJson.aspects("thaumaturge:potentia", 20, "thaumaturge:auram", 10, "thaumaturge:motus", 10);
            case VOID -> ContentJson.aspects("thaumaturge:vacuos", 20, "thaumaturge:potentia", 20, "thaumaturge:auram", 20);
        };
        CasingTier previous = tier.previous();
        String catalyst = previous == null ? CREATE + ":" + kind.id() : item(kind.blockName(previous));
        JsonObject json = ContentJson.infusion(
                aspects,
                ContentJson.item(catalyst),
                components,
                tier.level() + 1,
                item(entryOf(tier)),
                item(kind.blockName(tier)));
        List<String> features = new ArrayList<>(List.of(tierFeatures(tier)));
        features.add(CreateToggles.VARIANTS);
        features.add(kind.toggle());
        json.add("neoforge:conditions", conditions(features.toArray(String[]::new)));
        return json;
    }
}
