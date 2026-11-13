package nadiendev.ThaumaturgeTechCompats.compats.mekanism;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.ClusterMaterial;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MekanismClusterProvider implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String MEKANISM = "mekanism";
    private static final String SALIS = "thaumaturge:salis_mundus";
    private static final String BASE = "data/" + MOD + "/recipe/compat/mekanism/";

    private static final int ORE_INPUT = 1;
    private static final int ENRICHING_YIELD = 2;
    private static final int PURIFYING_YIELD = 3;
    private static final int INJECTING_YIELD = 4;
    private static final int SALIS_YIELD = 2;
    private static final int CRYSTAL_YIELD = 1;

    private static final int RAW_ENRICHING_INPUT = 3;
    private static final int RAW_ENRICHING_YIELD = 4;
    private static final int RAW_PURIFYING_INPUT = 1;
    private static final int RAW_PURIFYING_YIELD = 2;
    private static final int RAW_INJECTING_INPUT = 3;
    private static final int RAW_INJECTING_YIELD = 8;
    private static final int RAW_SALIS_INPUT = 3;
    private static final int RAW_SALIS_YIELD = 4;

    private static final int OXYGEN_AMOUNT = 1;
    private static final int HYDROGEN_CHLORIDE_AMOUNT = 1;
    private static final int BIO_PER_OPERATION = 20;
    private static final int BIO_PER_SALIS = 20;

    private final PackOutput output;

    public MekanismClusterProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "chemical_conversion/salis_mundus_to_bio", salisConversion()));
        for (ClusterMaterial material : ClusterMaterial.values()) {
            String id = material.id();
            tasks.add(save(cache, "enriching/x2_ore_" + id,
                    enriching(material, material.oreTag(), ORE_INPUT, ENRICHING_YIELD)));
            tasks.add(save(cache, "purifying/x3_ore_" + id,
                    purifying(material, material.oreTag(), ORE_INPUT, PURIFYING_YIELD)));
            tasks.add(save(cache, "injecting/x4_ore_" + id,
                    injecting(material, material.oreTag(), ORE_INPUT, INJECTING_YIELD)));
            tasks.add(save(cache, "metallurgic_infusing/salis_x2_ore_" + id,
                    salisInfusing(material, material.oreTag(), ORE_INPUT, SALIS_YIELD)));
            if (material.hasRaw()) {
                tasks.add(save(cache, "enriching/x2_raw_" + id,
                        enriching(material, material.rawTag(), RAW_ENRICHING_INPUT, RAW_ENRICHING_YIELD)));
                tasks.add(save(cache, "purifying/x3_raw_" + id,
                        purifying(material, material.rawTag(), RAW_PURIFYING_INPUT, RAW_PURIFYING_YIELD)));
                tasks.add(save(cache, "injecting/x4_raw_" + id,
                        injecting(material, material.rawTag(), RAW_INJECTING_INPUT, RAW_INJECTING_YIELD)));
                tasks.add(save(cache, "metallurgic_infusing/salis_x2_raw_" + id,
                        salisInfusing(material, material.rawTag(), RAW_SALIS_INPUT, RAW_SALIS_YIELD)));
            }
            if (material.hasChain()) {
                tasks.add(save(cache, "enriching/x5_crystal_" + id, crystalEnriching(material)));
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Mekanism clusters";
    }

    private CompletableFuture<?> save(CachedOutput cache, String name, JsonObject json) {
        return CompatJson.save(output, cache, BASE + name + ".json", json);
    }

    private static JsonArray conditions(ClusterMaterial material, String toggle, String... tags) {
        JsonArray conditions = CompatJson.conditionsFor(MEKANISM, material, tags);
        conditions.add(CompatJson.feature(toggle));
        return conditions;
    }

    private static JsonObject chemical(String id, int amount) {
        JsonObject json = new JsonObject();
        json.addProperty("amount", amount);
        json.addProperty("chemical", id);
        return json;
    }

    private static JsonObject chemicalTag(String tag, int amount) {
        JsonObject json = new JsonObject();
        json.addProperty("amount", amount);
        json.addProperty("tag", tag);
        return json;
    }

    private static JsonObject salisConversion() {
        JsonArray conditions = CompatJson.conditions(MEKANISM);
        conditions.add(CompatJson.feature(CompatToggles.MEKANISM_SALIS));
        JsonObject json = CompatJson.recipe("mekanism:chemical_conversion", conditions);
        json.add("input", CompatJson.itemCount(SALIS, 1));
        JsonObject bio = new JsonObject();
        bio.addProperty("amount", BIO_PER_SALIS);
        bio.addProperty("id", "mekanism:bio");
        json.add("output", bio);
        return json;
    }

    private static JsonObject enriching(ClusterMaterial material, String tag, int count, int yield) {
        JsonObject json = CompatJson.recipe("mekanism:enriching", conditions(material, CompatToggles.MEKANISM_X2, tag));
        json.add("input", CompatJson.tagCount(tag, count));
        json.add("output", CompatJson.result(material.cluster(), yield));
        return json;
    }

    private static JsonObject purifying(ClusterMaterial material, String tag, int count, int yield) {
        JsonObject json = CompatJson.recipe("mekanism:purifying", conditions(material, CompatToggles.MEKANISM_X3, tag));
        json.add("chemical_input", chemical("mekanism:oxygen", OXYGEN_AMOUNT));
        json.add("item_input", CompatJson.tagCount(tag, count));
        json.add("output", CompatJson.result(material.cluster(), yield));
        json.addProperty("per_tick_usage", true);
        return json;
    }

    private static JsonObject injecting(ClusterMaterial material, String tag, int count, int yield) {
        JsonObject json = CompatJson.recipe("mekanism:injecting", conditions(material, CompatToggles.MEKANISM_X4, tag));
        json.add("chemical_input", chemical("mekanism:hydrogen_chloride", HYDROGEN_CHLORIDE_AMOUNT));
        json.add("item_input", CompatJson.tagCount(tag, count));
        json.add("output", CompatJson.result(material.cluster(), yield));
        json.addProperty("per_tick_usage", true);
        return json;
    }

    private static JsonObject salisInfusing(ClusterMaterial material, String tag, int count, int yield) {
        JsonObject json = CompatJson.recipe("mekanism:metallurgic_infusing", conditions(material, CompatToggles.MEKANISM_SALIS, tag));
        json.add("chemical_input", chemicalTag("mekanism:bio", BIO_PER_OPERATION));
        json.add("item_input", CompatJson.tagCount(tag, count));
        json.add("output", CompatJson.result(material.cluster(), yield));
        json.addProperty("per_tick_usage", false);
        return json;
    }

    private static JsonObject crystalEnriching(ClusterMaterial material) {
        String tag = "c:crystals/" + material.id();
        JsonObject json = CompatJson.recipe("mekanism:enriching", conditions(material, CompatToggles.MEKANISM_X5, tag));
        json.add("input", CompatJson.tagCount(tag, 1));
        json.add("output", CompatJson.result(material.cluster(), CRYSTAL_YIELD));
        return json;
    }
}
