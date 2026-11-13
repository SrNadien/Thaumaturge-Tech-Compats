package nadiendev.ThaumaturgeTechCompats.flux.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.flux.FluxComponents;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CoreContent implements DataProvider {
    public static final int COLUMN = 50;
    public static final int ROW = 8;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String RECIPE_FOLDER = "flux/core/";

    private final PackOutput output;

    public CoreContent(PackOutput output) {
        this.output = output;
    }

    public static String entry(FluxTier tier) {
        return "flux_storage_core_" + tier.id();
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            String core = CoreRegistry.coreName(tier);
            String cell = CoreRegistry.cellName(tier);
            tasks.add(save(cache, "loot_table/blocks/" + core, lootTable(tier)));
            tasks.add(save(cache, "recipe/" + RECIPE_FOLDER + core, coreRecipe(tier)));
            tasks.add(save(cache, "recipe/" + RECIPE_FOLDER + cell, cellRecipe(tier)));
            tasks.add(save(cache, "thaumaturge/research_entry/" + entry(tier), research(tier)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats flux storage core";
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

    private static String metalBlock(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> "thaumaturge:metal_thaumium";
            case ALCHEMICAL_BRASS -> "thaumaturge:metal_brass";
            case VOID_METAL -> "thaumaturge:metal_void";
        };
    }

    private static String ingot(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> "thaumaturge:ingot_thaumium";
            case ALCHEMICAL_BRASS -> "thaumaturge:ingot_brass";
            case VOID_METAL -> "thaumaturge:ingot_void";
        };
    }

    private static String plate(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> "thaumaturge:plate_thaumium";
            case ALCHEMICAL_BRASS -> "thaumaturge:plate_brass";
            case VOID_METAL -> "thaumaturge:plate_void";
        };
    }

    private static JsonObject tag(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("tag", id);
        return json;
    }

    private static JsonArray crystals(Object... pairs) {
        JsonArray array = new JsonArray();
        for (int i = 0; i < pairs.length; i += 2) {
            JsonObject crystal = new JsonObject();
            crystal.addProperty("amount", (Integer) pairs[i + 1]);
            crystal.addProperty("aspect", (String) pairs[i]);
            array.add(crystal);
        }
        return array;
    }

    private static JsonObject lootTable(FluxTier tier) {
        JsonArray include = new JsonArray();
        include.add(FluxComponents.STORED.getId().toString());

        JsonObject copy = new JsonObject();
        copy.addProperty("function", "minecraft:copy_components");
        copy.addProperty("source", "block_entity");
        copy.add("include", include);

        JsonArray functions = new JsonArray();
        functions.add(copy);

        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", MOD + ":" + CoreRegistry.coreName(tier));
        entry.add("functions", functions);

        JsonArray entries = new JsonArray();
        entries.add(entry);

        JsonObject condition = new JsonObject();
        condition.addProperty("condition", "minecraft:survives_explosion");
        JsonArray poolConditions = new JsonArray();
        poolConditions.add(condition);

        JsonObject pool = new JsonObject();
        pool.addProperty("bonus_rolls", 0.0);
        pool.add("conditions", poolConditions);
        pool.add("entries", entries);
        pool.addProperty("rolls", 1.0);

        JsonArray pools = new JsonArray();
        pools.add(pool);

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(tier, CoreIntegration.CORE_TOGGLE));
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", MOD + ":blocks/" + CoreRegistry.coreName(tier));
        return json;
    }

    private static JsonObject coreRecipe(FluxTier tier) {
        String result = MOD + ":" + CoreRegistry.coreName(tier);
        JsonObject json;
        if (tier == FluxTier.THAUMIUM) {
            JsonObject key = new JsonObject();
            key.add("p", ContentJson.item(plate(tier)));
            key.add("a", ContentJson.item("thaumaturge:alumentum"));
            key.add("m", ContentJson.item("thaumaturge:mechanism_simple"));
            key.add("b", ContentJson.item(metalBlock(tier)));
            json = ContentJson.arcaneShaped(key, ContentJson.strings("pap", "mbm", "pap"), MOD + ":" + entry(tier), 150, result);
            json.add("crystals", crystals("thaumaturge:ordo", 20, "thaumaturge:terra", 10, "thaumaturge:aer", 10));
        } else {
            JsonArray components = new JsonArray();
            for (int i = 0; i < 2; i++) {
                components.add(ContentJson.item(metalBlock(tier)));
                components.add(tag("thaumaturge:nitors"));
                components.add(ContentJson.item(plate(tier)));
                components.add(ContentJson.item("thaumaturge:alumentum"));
            }
            int step = tier.ordinal();
            JsonArray aspects = ContentJson.aspects(
                    "thaumaturge:auram", 16 + 16 * step,
                    "thaumaturge:potentia", 16 + 16 * step,
                    "thaumaturge:praemunio", 16 + 8 * step,
                    "thaumaturge:ordo", 16 + 8 * step);
            json = ContentJson.infusion(aspects, ContentJson.item(MOD + ":" + CoreRegistry.coreName(tier.previous())), components, 6 + 3 * step, MOD + ":" + entry(tier), result);
        }
        json.add("neoforge:conditions", conditions(tier, CoreIntegration.CORE_TOGGLE));
        return json;
    }

    private static JsonObject cellRecipe(FluxTier tier) {
        String result = MOD + ":" + CoreRegistry.cellName(tier);
        JsonObject json;
        if (tier == FluxTier.THAUMIUM) {
            JsonObject key = new JsonObject();
            key.add("p", ContentJson.item(plate(tier)));
            key.add("a", ContentJson.item("thaumaturge:alumentum"));
            key.add("j", ContentJson.item("thaumaturge:jar_normal"));
            json = ContentJson.arcaneShaped(key, ContentJson.strings("pap", "aja", "pap"), MOD + ":" + entry(tier), 80, result);
            json.add("crystals", crystals("thaumaturge:ordo", 10, "thaumaturge:aqua", 5));
        } else {
            JsonArray components = new JsonArray();
            for (int i = 0; i < 2; i++) {
                components.add(ContentJson.item(ingot(tier)));
                components.add(ContentJson.item("thaumaturge:alumentum"));
                components.add(ContentJson.item(plate(tier)));
            }
            int step = tier.ordinal();
            JsonArray aspects = ContentJson.aspects(
                    "thaumaturge:auram", 12 + 12 * step,
                    "thaumaturge:potentia", 12 + 12 * step,
                    "thaumaturge:vacuos", 8 + 8 * step,
                    "thaumaturge:ordo", 12 + 6 * step);
            json = ContentJson.infusion(aspects, ContentJson.item(MOD + ":" + CoreRegistry.cellName(tier.previous())), components, 4 + 3 * step, MOD + ":" + entry(tier), result);
        }
        json.add("neoforge:conditions", conditions(tier, CoreIntegration.CELL_TOGGLE));
        return json;
    }

    private static JsonArray parents(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> ContentJson.strings(TechCategory.HEADER_ID, "thaumaturge:base_artifice", "thaumaturge:metallurgy@2");
            case ALCHEMICAL_BRASS -> ContentJson.strings(MOD + ":" + entry(FluxTier.THAUMIUM), "thaumaturge:base_artifice", "thaumaturge:metallurgy@1");
            case VOID_METAL -> ContentJson.strings(MOD + ":" + entry(FluxTier.ALCHEMICAL_BRASS), "thaumaturge:base_artifice", "thaumaturge:base_eldritch");
        };
    }

    private static JsonObject research(FluxTier tier) {
        String name = entry(tier);
        int step = tier.ordinal();

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(tier, CoreIntegration.CORE_TOGGLE));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", parents(tier));
        json.addProperty("column", COLUMN + step);
        json.addProperty("row", ROW);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(MOD + ":" + CoreRegistry.coreName(tier)));

        JsonArray stages = new JsonArray();

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", tier == FluxTier.VOID_METAL ? "thaumaturge:eldritch" : "thaumaturge:artifice");
        knowledge.addProperty("amount", tier == FluxTier.THAUMIUM ? 1 : 2);
        JsonArray required = new JsonArray();
        required.add(knowledge);
        first.add("required_knowledge", required);
        first.addProperty("warp", tier == FluxTier.VOID_METAL ? 2 : 1);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(
                MOD + ":" + RECIPE_FOLDER + CoreRegistry.coreName(tier),
                MOD + ":" + RECIPE_FOLDER + CoreRegistry.cellName(tier)));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", ContentJson.aspects(
                "thaumaturge:potentia", 6 + 2 * step,
                "thaumaturge:praemunio", 5 + step,
                "thaumaturge:auram", 4 + 2 * step));
        json.addProperty("complexity", step + 1);
        return json;
    }
}
