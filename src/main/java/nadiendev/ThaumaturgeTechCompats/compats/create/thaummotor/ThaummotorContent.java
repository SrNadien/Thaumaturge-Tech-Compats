package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.compats.create.data.CreateContent;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThaummotorContent implements DataProvider {
    public static final String ENTRY = "create_thaummotor";
    public static final int COLUMN = 21;
    public static final int ROW = 2;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String NAME = ThaummotorRegistry.NAME;
    private static final String RECIPE = "create/" + NAME;

    private final PackOutput output;

    public ThaummotorContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/loot_table/blocks/" + NAME + ".json", lootTable()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/" + RECIPE + ".json", recipe()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY + ".json", entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Thaummotor";
    }

    private static JsonArray conditions() {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.modLoaded(CreateIntegration.MOD_ID));
        conditions.add(CompatJson.feature(CreateToggles.TECH));
        conditions.add(CompatJson.feature(CreateToggles.CASING_BRASS));
        conditions.add(CompatJson.feature(ThaummotorIntegration.TOGGLE));
        conditions.add(CompatJson.feature(CompatToggles.NODE_POWER));
        return conditions;
    }

    private static JsonObject lootTable() {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", MOD + ":" + NAME);

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
        json.add("neoforge:conditions", conditions());
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", MOD + ":blocks/" + NAME);
        return json;
    }

    private static JsonObject recipe() {
        JsonObject key = new JsonObject();
        key.add("p", ContentJson.item("thaumaturge:plate_thaumium"));
        key.add("m", ContentJson.item("thaumaturge:mechanism_simple"));
        key.add("s", ContentJson.item(CreateIntegration.MOD_ID + ":shaft"));
        key.add("c", ContentJson.item(CreateIntegration.MOD_ID + ":brass_casing"));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("pmp", "scs", "pmp"), MOD + ":" + ENTRY, 60, MOD + ":" + NAME);
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject entry() {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions());
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + ENTRY + ".title");
        json.add("parents", ContentJson.strings(MOD + ":" + CreateContent.BRASS, "thaumaturge:base_artifice"));
        json.addProperty("column", COLUMN);
        json.addProperty("row", ROW);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(MOD + ":" + NAME));

        JsonArray stages = new JsonArray();

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + ENTRY + ".stage_0");
        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", "thaumaturge:artifice");
        knowledge.addProperty("amount", 2);
        JsonArray required = new JsonArray();
        required.add(knowledge);
        first.add("required_knowledge", required);
        first.addProperty("warp", 1);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + ENTRY + ".stage_1");
        second.add("recipes", ContentJson.strings(MOD + ":" + RECIPE));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", ContentJson.aspects("thaumaturge:potentia", 6, "thaumaturge:motus", 5, "thaumaturge:machina", 4));
        json.addProperty("complexity", 2);
        return json;
    }
}
