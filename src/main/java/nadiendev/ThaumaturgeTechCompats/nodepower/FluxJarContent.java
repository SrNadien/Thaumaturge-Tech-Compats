package nadiendev.ThaumaturgeTechCompats.nodepower;

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

public class FluxJarContent implements DataProvider {
    public static final String ENTRY = "node_power_flux_jar";
    public static final String VARIANTS_ENTRY = "node_power_flux_jars_avaritia";
    public static final String RIFT_ENTRY = "node_power_rift_jar";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String PEARL = MOD + ":extremely_primordial_pearl";
    private static final String ARCANE_RECIPE = "nodepower/" + FluxJarTier.BASE.blockName();
    private static final int COLUMN = 12;
    private static final int ROW = 2;
    private static final int VARIANTS_COLUMN = 2;
    private static final int VARIANTS_ROW = 4;
    private static final String RIFT_JAR = "rift_jar";
    private static final String RIFT_ARCANE_RECIPE = "nodepower/" + RIFT_JAR;
    private static final int RIFT_ROW = 4;

    private final PackOutput output;

    public FluxJarContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxJarTier tier : FluxJarTier.values()) {
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/loot_table/blocks/" + tier.blockName() + ".json", lootTable(id(tier), tier.blockName())));
            if (tier != FluxJarTier.BASE) {
                tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + tier.blockName() + ".json", infusion(tier)));
            }
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/loot_table/blocks/" + RIFT_JAR + ".json", lootTable(MOD + ":" + RIFT_JAR, RIFT_JAR)));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/" + RIFT_ARCANE_RECIPE + ".json", riftArcaneRecipe()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + RIFT_ENTRY + ".json", riftEntry()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/" + ARCANE_RECIPE + ".json", arcaneRecipe()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY + ".json", entry()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + VARIANTS_ENTRY + ".json", variantsEntry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats flux jars";
    }

    private static String id(FluxJarTier tier) {
        return MOD + ":" + tier.blockName();
    }

    private static JsonArray conditions() {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.feature(CompatToggles.NODE_POWER));
        return conditions;
    }

    private static JsonObject lootTable(String itemId, String blockName) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", itemId);

        JsonArray entries = new JsonArray();
        entries.add(entry);

        JsonObject condition = new JsonObject();
        condition.addProperty("condition", "minecraft:survives_explosion");
        JsonArray conditions = new JsonArray();
        conditions.add(condition);

        JsonObject pool = new JsonObject();
        pool.addProperty("bonus_rolls", 0.0);
        pool.add("conditions", conditions);
        pool.add("entries", entries);
        pool.addProperty("rolls", 1.0);

        JsonArray pools = new JsonArray();
        pools.add(pool);

        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", MOD + ":blocks/" + blockName);
        return json;
    }

    private static JsonObject riftArcaneRecipe() {
        JsonObject key = new JsonObject();
        key.add("v", ContentJson.item("thaumaturge:void_seed"));
        key.add("p", ContentJson.item("thaumaturge:plate_void"));
        key.add("j", ContentJson.item("thaumaturge:jar_normal"));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings(" v ", "pjp", " v "), MOD + ":" + RIFT_ENTRY, 60, MOD + ":" + RIFT_JAR);
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject riftEntry() {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions());
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + RIFT_ENTRY + ".title");
        json.add("parents", ContentJson.strings(MOD + ":" + ENTRY));
        json.addProperty("column", COLUMN);
        json.addProperty("row", RIFT_ROW);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(MOD + ":" + RIFT_JAR));

        JsonArray stages = new JsonArray();

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + RIFT_ENTRY + ".stage_0");
        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", "thaumaturge:eldritch");
        knowledge.addProperty("amount", 3);
        JsonArray required = new JsonArray();
        required.add(knowledge);
        first.add("required_knowledge", required);
        first.addProperty("warp", 2);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + RIFT_ENTRY + ".stage_1");
        second.add("recipes", ContentJson.strings(MOD + ":" + RIFT_ARCANE_RECIPE));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", ContentJson.aspects("thaumaturge:vacuos", 8, "thaumaturge:alienis", 4, "thaumaturge:praemunio", 4));
        json.addProperty("complexity", 3);
        return json;
    }

    private static JsonObject arcaneRecipe() {
        JsonObject key = new JsonObject();
        key.add("p", ContentJson.item("thaumaturge:plate_void"));
        key.add("j", ContentJson.item("thaumaturge:jar_normal"));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings(" p ", "pjp", " p "), MOD + ":" + ENTRY, 40, id(FluxJarTier.BASE));
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject infusion(FluxJarTier tier) {
        JsonArray components = new JsonArray();
        JsonArray aspects;
        int instability;
        switch (tier) {
            case MATRIX -> {
                addAll(components, "avaritia:crystal_matrix_ingot", 4);
                aspects = ContentJson.aspects(
                        "thaumaturge:vacuos", 24,
                        "thaumaturge:praemunio", 16,
                        "thaumaturge:vitreus", 16,
                        ContentJson.ASPECT_TERMINUS, 4);
                instability = 3;
            }
            case NEUTRONIUM -> {
                addAll(components, "avaritia:neutron_ingot", 4);
                aspects = ContentJson.aspects(
                        "thaumaturge:vacuos", 32,
                        "thaumaturge:praemunio", 32,
                        "thaumaturge:metallum", 24,
                        ContentJson.ASPECT_TERMINUS, 8);
                instability = 6;
            }
            default -> {
                addAll(components, "avaritia:infinity_ingot", 4);
                components.add(ContentJson.item(PEARL));
                aspects = ContentJson.aspects(
                        "thaumaturge:vacuos", 48,
                        "thaumaturge:praemunio", 48,
                        "thaumaturge:alienis", 32,
                        ContentJson.ASPECT_TERMINUS, 24);
                instability = 9;
            }
        }
        JsonObject json = ContentJson.infusion(aspects, ContentJson.item(id(tier.previous())), components, instability, MOD + ":" + VARIANTS_ENTRY, id(tier));
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static void addAll(JsonArray components, String item, int count) {
        for (int i = 0; i < count; i++) {
            components.add(ContentJson.item(item));
        }
    }

    private static JsonObject entry() {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions());
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + ENTRY + ".title");
        json.add("parents", ContentJson.strings(TechCategory.HEADER_ID, "thaumaturge:warded_jars", "thaumaturge:base_eldritch"));
        json.addProperty("column", COLUMN);
        json.addProperty("row", ROW);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(id(FluxJarTier.BASE)));

        JsonArray stages = new JsonArray();

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + ENTRY + ".stage_0");
        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", "thaumaturge:eldritch");
        knowledge.addProperty("amount", 2);
        JsonArray required = new JsonArray();
        required.add(knowledge);
        first.add("required_knowledge", required);
        first.addProperty("warp", 1);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + ENTRY + ".stage_1");
        second.add("recipes", ContentJson.strings(MOD + ":" + ARCANE_RECIPE));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", ContentJson.aspects("thaumaturge:vacuos", 6, "thaumaturge:praemunio", 4, "thaumaturge:vitreus", 3));
        json.addProperty("complexity", 2);
        return json;
    }

    private static JsonObject variantsEntry() {
        JsonObject json = ContentJson.research(
                VARIANTS_ENTRY,
                List.of(MOD + ":" + ENTRY, PEARL),
                VARIANTS_COLUMN, VARIANTS_ROW,
                id(FluxJarTier.INFINITY),
                List.of(
                        MOD + ":infusion/" + FluxJarTier.MATRIX.blockName(),
                        MOD + ":infusion/" + FluxJarTier.NEUTRONIUM.blockName(),
                        MOD + ":infusion/" + FluxJarTier.INFINITY.blockName()),
                2,
                ContentJson.aspects("thaumaturge:vacuos", 6, "thaumaturge:vitreus", 4, ContentJson.ASPECT_TERMINUS, 3),
                2);
        json.add("neoforge:conditions", conditions());
        return json;
    }
}
