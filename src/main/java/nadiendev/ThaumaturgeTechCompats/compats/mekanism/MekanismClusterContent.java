package nadiendev.ThaumaturgeTechCompats.compats.mekanism;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.ClusterMaterial;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MekanismClusterContent implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String RECIPES = "data/" + MOD + "/recipe/compat/mekanism/";
    private static final String RESEARCH = "thaumaturge:metal_purification";
    private static final int SMELTING_TIME = 200;
    private static final int BLASTING_TIME = 100;
    private static final int INGOTS_PER_CLUSTER = 2;
    private static final float EXPERIENCE = 1.0F;
    private static final int METALLUM = 5;
    private static final int ORDO = 5;

    private final PackOutput output;

    public MekanismClusterContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (ClusterMaterial material : ClusterMaterial.ownMaterials()) {
            String id = material.id();
            tasks.add(CompatJson.save(output, cache, RECIPES + "smelting/" + id + "_ingot_from_cluster.json",
                    cooking("minecraft:smelting", material, SMELTING_TIME)));
            tasks.add(CompatJson.save(output, cache, RECIPES + "blasting/" + id + "_ingot_from_cluster.json",
                    cooking("minecraft:blasting", material, BLASTING_TIME)));
            tasks.add(CompatJson.save(output, cache, RECIPES + "crucible/cluster_" + id + ".json", crucible(material)));
        }
        tasks.add(CompatJson.save(output, cache, "data/thaumaturge/tags/item/clusters.json", clustersTag()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Mekanism cluster content";
    }

    private static JsonArray conditions(ClusterMaterial material, String... tags) {
        return CompatJson.conditionsFor(ClusterMaterial.OWN_MOD, material, tags);
    }

    private static JsonObject cooking(String type, ClusterMaterial material, int time) {
        JsonObject json = CompatJson.recipe(type, conditions(material));
        json.addProperty("category", "misc");
        json.addProperty("cookingtime", time);
        json.addProperty("experience", EXPERIENCE);
        json.addProperty("group", material.id() + "_ingot");
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", material.cluster());
        json.add("ingredient", ingredient);
        json.add("result", CompatJson.result(ClusterMaterial.OWN_MOD + ":ingot_" + material.id(), INGOTS_PER_CLUSTER));
        return json;
    }

    private static JsonObject crucible(ClusterMaterial material) {
        JsonObject json = CompatJson.recipe("thaumaturge:crucible", conditions(material, material.rawTag()));
        JsonArray aspects = new JsonArray();
        aspects.add(aspect("thaumaturge:metallum", METALLUM));
        aspects.add(aspect("thaumaturge:ordo", ORDO));
        json.add("aspects", aspects);
        JsonObject catalyst = new JsonObject();
        catalyst.addProperty("tag", material.rawTag());
        json.add("catalyst", catalyst);
        JsonObject research = new JsonObject();
        research.addProperty("entry", RESEARCH);
        json.add("research", research);
        json.add("result", CompatJson.result(material.cluster(), 1));
        return json;
    }

    private static JsonObject aspect(String id, int amount) {
        JsonObject json = new JsonObject();
        json.addProperty("amount", amount);
        json.addProperty("aspect", id);
        return json;
    }

    private static JsonObject clustersTag() {
        JsonArray values = new JsonArray();
        for (ClusterMaterial material : ClusterMaterial.ownMaterials()) {
            JsonObject entry = new JsonObject();
            entry.addProperty("id", material.cluster());
            entry.addProperty("required", false);
            values.add(entry);
        }
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        json.add("values", values);
        return json;
    }
}
