package nadiendev.ThaumaturgeTechCompats.flux.field;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FieldContent implements DataProvider {
    public static final int SHIELD_COLUMN = 59;
    public static final int TERRAFORMER_COLUMN = 60;
    public static final int THRUSTER_ROW = 8;
    public static final String THRUSTER_ENTRY = "field_flight_thruster";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String PLATE_THAUMIUM = "thaumaturge:plate_thaumium";
    private static final String MECHANISM_SIMPLE = "thaumaturge:mechanism_simple";
    private static final String MECHANISM_COMPLEX = "thaumaturge:mechanism_complex";
    private static final String RESONATOR = "thaumaturge:vis_resonator";
    private static final String MIRRORED_GLASS = "thaumaturge:mirrored_glass";

    private final PackOutput output;

    public FieldContent(PackOutput output) {
        this.output = output;
    }

    public static String shieldEntry(FluxTier tier) {
        return "field_shield_" + tier.id();
    }

    public static String terraformerEntry(FluxTier tier) {
        return "field_terraformer_" + tier.id();
    }

    private static String recipeFolder(FluxTier tier) {
        return tier == FluxTier.THAUMIUM ? "arcane_workbench" : "infusion";
    }

    private static String shieldRecipe(FluxTier tier) {
        return recipeFolder(tier) + "/" + FieldRegistry.shieldName(tier);
    }

    private static String terraformerRecipe(FluxTier tier) {
        return recipeFolder(tier) + "/" + FieldRegistry.terraformerName(tier);
    }

    private static String thrusterRecipe() {
        return "infusion/" + FieldRegistry.THRUSTER_NAME;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            tasks.add(save(cache, "loot_table/blocks/" + FieldRegistry.shieldName(tier), loot(FieldRegistry.shieldName(tier), conditions(tier))));
            tasks.add(save(cache, "loot_table/blocks/" + FieldRegistry.terraformerName(tier), loot(FieldRegistry.terraformerName(tier), conditions(tier))));
            tasks.add(save(cache, "recipe/" + shieldRecipe(tier), shieldRecipeJson(tier)));
            tasks.add(save(cache, "recipe/" + terraformerRecipe(tier), terraformerRecipeJson(tier)));
            tasks.add(save(cache, "thaumaturge/research_entry/" + shieldEntry(tier), shieldResearch(tier)));
            tasks.add(save(cache, "thaumaturge/research_entry/" + terraformerEntry(tier), terraformerResearch(tier)));
        }
        tasks.add(save(cache, "recipe/" + thrusterRecipe(), thrusterRecipeJson()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + THRUSTER_ENTRY, thrusterResearch()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats dimensional flux field gear";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions(FluxTier tier) {
        JsonArray conditions = new JsonArray();
        for (FluxTier step : FluxTier.values()) {
            if (step.ordinal() <= tier.ordinal()) {
                conditions.add(CompatJson.feature(step.toggle()));
            }
        }
        return conditions;
    }

    private static String itemId(String name) {
        return MOD + ":" + name;
    }

    private static JsonObject loot(String name, JsonArray conditions) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", itemId(name));

        JsonArray entries = new JsonArray();
        entries.add(entry);

        JsonObject survives = new JsonObject();
        survives.addProperty("condition", "minecraft:survives_explosion");
        JsonArray poolConditions = new JsonArray();
        poolConditions.add(survives);

        JsonObject pool = new JsonObject();
        pool.addProperty("bonus_rolls", 0.0);
        pool.add("conditions", poolConditions);
        pool.add("entries", entries);
        pool.addProperty("rolls", 1.0);

        JsonArray pools = new JsonArray();
        pools.add(pool);

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions);
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", MOD + ":blocks/" + name);
        return json;
    }

    private static JsonObject shieldRecipeJson(FluxTier tier) {
        JsonObject json = switch (tier) {
            case THAUMIUM -> {
                JsonObject key = new JsonObject();
                key.add("p", ContentJson.item(PLATE_THAUMIUM));
                key.add("g", ContentJson.item(MIRRORED_GLASS));
                key.add("m", ContentJson.item(MECHANISM_SIMPLE));
                key.add("r", ContentJson.item(RESONATOR));
                JsonObject arcane = ContentJson.arcaneShaped(key, ContentJson.strings("pgp", "mrm", "pgp"),
                        MOD + ":" + shieldEntry(tier), 60, itemId(FieldRegistry.shieldName(tier)));
                arcane.add("crystals", ContentJson.aspects("thaumaturge:ordo", 2, "thaumaturge:terra", 1));
                yield arcane;
            }
            case ALCHEMICAL_BRASS -> infusion(tier, FieldRegistry.shieldName(tier), shieldEntry(tier),
                    ContentJson.aspects("thaumaturge:praemunio", 40, "thaumaturge:potentia", 30, "thaumaturge:machina", 20),
                    3, "thaumaturge:plate_brass", 4, MECHANISM_SIMPLE, 1, 1, MIRRORED_GLASS, 2);
            case VOID_METAL -> infusion(tier, FieldRegistry.shieldName(tier), shieldEntry(tier),
                    ContentJson.aspects("thaumaturge:praemunio", 80, "thaumaturge:potentia", 60, "thaumaturge:vacuos", 40, "thaumaturge:machina", 40),
                    4, "thaumaturge:plate_void", 4, MECHANISM_COMPLEX, 1, 2, MIRRORED_GLASS, 1);
        };
        json.add("neoforge:conditions", conditions(tier));
        return json;
    }

    private static JsonObject terraformerRecipeJson(FluxTier tier) {
        JsonObject json = switch (tier) {
            case THAUMIUM -> {
                JsonObject key = new JsonObject();
                key.add("p", ContentJson.item(PLATE_THAUMIUM));
                key.add("g", ContentJson.item("minecraft:grass_block"));
                key.add("m", ContentJson.item(MECHANISM_SIMPLE));
                key.add("v", ContentJson.item(RESONATOR));
                key.add("s", ContentJson.item("minecraft:sand"));
                JsonObject arcane = ContentJson.arcaneShaped(key, ContentJson.strings("pgp", "mvm", "psp"),
                        MOD + ":" + terraformerEntry(tier), 80, itemId(FieldRegistry.terraformerName(tier)));
                arcane.add("crystals", ContentJson.aspects("thaumaturge:terra", 2, "thaumaturge:aqua", 2));
                yield arcane;
            }
            case ALCHEMICAL_BRASS -> {
                JsonObject brass = infusion(tier, FieldRegistry.terraformerName(tier), terraformerEntry(tier),
                        ContentJson.aspects("thaumaturge:permutatio", 40, "thaumaturge:terra", 30, "thaumaturge:potentia", 20),
                        3, "thaumaturge:plate_brass", 4, MECHANISM_SIMPLE, 1, 1, "minecraft:cactus", 1);
                brass.getAsJsonArray("components").add(ContentJson.item("minecraft:snow_block"));
                yield brass;
            }
            case VOID_METAL -> infusion(tier, FieldRegistry.terraformerName(tier), terraformerEntry(tier),
                    ContentJson.aspects("thaumaturge:permutatio", 80, "thaumaturge:terra", 60, "thaumaturge:potentia", 40, "thaumaturge:vacuos", 30),
                    4, "thaumaturge:plate_void", 4, MECHANISM_COMPLEX, 1, 2, "minecraft:red_mushroom", 1);
        };
        json.add("neoforge:conditions", conditions(tier));
        return json;
    }

    private static JsonObject thrusterRecipeJson() {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 3; i++) {
            components.add(ContentJson.item("thaumaturge:plate_brass"));
        }
        for (int i = 0; i < 2; i++) {
            components.add(ContentJson.item(MECHANISM_COMPLEX));
        }
        components.add(ContentJson.item("thaumaturge:void_seed"));
        for (int i = 0; i < 2; i++) {
            components.add(ContentJson.item(RESONATOR));
        }
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:volatus", 60, "thaumaturge:motus", 40, "thaumaturge:potentia", 40, "thaumaturge:machina", 20, "thaumaturge:vacuos", 20),
                ContentJson.item("thaumaturge:thaumium_chest"),
                components,
                4,
                MOD + ":" + THRUSTER_ENTRY,
                itemId(FieldRegistry.THRUSTER_NAME));
        json.add("neoforge:conditions", conditions(FluxTier.ALCHEMICAL_BRASS));
        return json;
    }

    private static JsonObject infusion(FluxTier tier, String result, String entry, JsonArray aspects, int instability,
                                       String plate, int plates, String mechanism, int mechanisms, int resonators, String extra, int extras) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < plates; i++) {
            components.add(ContentJson.item(plate));
        }
        for (int i = 0; i < mechanisms; i++) {
            components.add(ContentJson.item(mechanism));
        }
        for (int i = 0; i < resonators; i++) {
            components.add(ContentJson.item(RESONATOR));
        }
        for (int i = 0; i < extras; i++) {
            components.add(ContentJson.item(extra));
        }
        String catalyst = result.endsWith("shield_generator") ? FieldRegistry.shieldName(tier.previous()) : FieldRegistry.terraformerName(tier.previous());
        return ContentJson.infusion(aspects, ContentJson.item(itemId(catalyst)), components, instability, MOD + ":" + entry, itemId(result));
    }

    private static JsonObject shieldResearch(FluxTier tier) {
        List<String> parents = switch (tier) {
            case THAUMIUM -> List.of(TechCategory.HEADER_ID, "thaumaturge:base_artifice", "thaumaturge:metallurgy@2");
            case ALCHEMICAL_BRASS -> List.of(MOD + ":" + shieldEntry(FluxTier.THAUMIUM), "thaumaturge:infusion", "thaumaturge:metallurgy@1");
            case VOID_METAL -> List.of(MOD + ":" + shieldEntry(FluxTier.ALCHEMICAL_BRASS), "thaumaturge:infusion", "thaumaturge:base_eldritch");
        };
        JsonArray notes = switch (tier) {
            case THAUMIUM -> ContentJson.aspects("thaumaturge:praemunio", 6, "thaumaturge:potentia", 5, "thaumaturge:machina", 4);
            case ALCHEMICAL_BRASS -> ContentJson.aspects("thaumaturge:praemunio", 8, "thaumaturge:potentia", 8, "thaumaturge:machina", 6);
            case VOID_METAL -> ContentJson.aspects("thaumaturge:praemunio", 10, "thaumaturge:potentia", 10, "thaumaturge:vacuos", 8, "thaumaturge:machina", 8);
        };
        return entry(tier, shieldEntry(tier), parents, SHIELD_COLUMN, 2 + tier.ordinal() * 2, FieldRegistry.shieldName(tier), shieldRecipe(tier), notes);
    }

    private static JsonObject terraformerResearch(FluxTier tier) {
        List<String> parents = switch (tier) {
            case THAUMIUM -> List.of(TechCategory.HEADER_ID, "thaumaturge:base_artifice", "thaumaturge:metallurgy@2");
            case ALCHEMICAL_BRASS -> List.of(MOD + ":" + terraformerEntry(FluxTier.THAUMIUM), "thaumaturge:infusion", "thaumaturge:metallurgy@1");
            case VOID_METAL -> List.of(MOD + ":" + terraformerEntry(FluxTier.ALCHEMICAL_BRASS), "thaumaturge:infusion", "thaumaturge:base_eldritch");
        };
        JsonArray notes = switch (tier) {
            case THAUMIUM -> ContentJson.aspects("thaumaturge:permutatio", 6, "thaumaturge:terra", 5, "thaumaturge:potentia", 4);
            case ALCHEMICAL_BRASS -> ContentJson.aspects("thaumaturge:permutatio", 8, "thaumaturge:terra", 8, "thaumaturge:potentia", 6);
            case VOID_METAL -> ContentJson.aspects("thaumaturge:permutatio", 10, "thaumaturge:terra", 10, "thaumaturge:vacuos", 8, "thaumaturge:potentia", 8);
        };
        return entry(tier, terraformerEntry(tier), parents, TERRAFORMER_COLUMN, 2 + tier.ordinal() * 2, FieldRegistry.terraformerName(tier), terraformerRecipe(tier), notes);
    }

    private static JsonObject thrusterResearch() {
        List<String> parents = List.of(TechCategory.HEADER_ID, "thaumaturge:infusion", "thaumaturge:base_eldritch");
        JsonArray notes = ContentJson.aspects("thaumaturge:volatus", 8, "thaumaturge:motus", 8, "thaumaturge:potentia", 6, "thaumaturge:vacuos", 4);
        JsonObject json = entry(FluxTier.ALCHEMICAL_BRASS, THRUSTER_ENTRY, parents, SHIELD_COLUMN, THRUSTER_ROW, FieldRegistry.THRUSTER_NAME, thrusterRecipe(), notes);
        json.addProperty("complexity", 3);
        json.add("neoforge:conditions", conditions(FluxTier.ALCHEMICAL_BRASS));
        JsonArray stages = json.getAsJsonArray("stages");
        JsonObject first = stages.get(0).getAsJsonObject();
        first.addProperty("warp", 2);
        JsonObject knowledge = first.getAsJsonArray("required_knowledge").get(0).getAsJsonObject();
        knowledge.addProperty("category", "thaumaturge:eldritch");
        knowledge.addProperty("amount", 2);
        return json;
    }

    private static JsonObject entry(FluxTier tier, String name, List<String> parents, int column, int row, String icon, String recipe, JsonArray notes) {
        boolean eldritch = tier == FluxTier.VOID_METAL;
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(tier));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", column);
        json.addProperty("row", row);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(itemId(icon)));

        JsonArray stages = new JsonArray();
        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        JsonObject required = new JsonObject();
        required.addProperty("type", "theory");
        required.addProperty("category", eldritch ? "thaumaturge:eldritch" : "thaumaturge:artifice");
        required.addProperty("amount", tier == FluxTier.THAUMIUM ? 1 : 2);
        JsonArray requirements = new JsonArray();
        requirements.add(required);
        first.add("required_knowledge", requirements);
        first.addProperty("warp", tier.ordinal());
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(MOD + ":" + recipe));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", notes);
        json.addProperty("complexity", tier.ordinal() + 1);
        return json;
    }
}
