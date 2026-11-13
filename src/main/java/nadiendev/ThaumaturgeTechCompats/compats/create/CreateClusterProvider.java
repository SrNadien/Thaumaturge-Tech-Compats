package nadiendev.ThaumaturgeTechCompats.compats.create;

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

public class CreateClusterProvider implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final int CRUSHING_TIME = 250;
    private static final int MILLING_TIME = 350;
    private static final int CLUSTER_FACTOR = 2;

    private final PackOutput output;

    public CreateClusterProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (ClusterMaterial material : ClusterMaterial.values()) {
            tasks.add(CompatJson.save(output, cache,
                    "data/" + MOD + "/recipe/compat/create/crushing/cluster_" + material.id() + ".json", crushing(material)));
            tasks.add(CompatJson.save(output, cache,
                    "data/" + MOD + "/recipe/compat/create/milling/cluster_" + material.id() + ".json", milling(material)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Create clusters";
    }

    private static JsonObject crushing(ClusterMaterial material) {
        JsonObject json = CompatJson.recipe("create:crushing", CompatJson.conditionsFor("create", material));
        json.add("ingredients", ingredients(material));
        json.addProperty("processing_time", CRUSHING_TIME);
        JsonArray results = new JsonArray();
        results.add(CompatJson.result(product(material), guaranteed(material) * CLUSTER_FACTOR));
        JsonObject bonus = CompatJson.result(product(material), CLUSTER_FACTOR);
        bonus.addProperty("chance", bonusChance(material));
        results.add(bonus);
        JsonObject experience = CompatJson.result("create:experience_nugget", CLUSTER_FACTOR);
        experience.addProperty("chance", 0.75);
        results.add(experience);
        json.add("results", results);
        return json;
    }

    private static JsonObject milling(ClusterMaterial material) {
        JsonObject json = CompatJson.recipe("create:milling", CompatJson.conditionsFor("create", material));
        json.add("ingredients", ingredients(material));
        json.addProperty("processing_time", MILLING_TIME);
        JsonArray results = new JsonArray();
        results.add(CompatJson.result(product(material), guaranteed(material) * CLUSTER_FACTOR));
        json.add("results", results);
        return json;
    }

    private static JsonArray ingredients(ClusterMaterial material) {
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", material.cluster());
        JsonArray array = new JsonArray();
        array.add(ingredient);
        return array;
    }

    private static String product(ClusterMaterial material) {
        return switch (material) {
            case IRON -> "create:crushed_raw_iron";
            case COPPER -> "create:crushed_raw_copper";
            case GOLD -> "create:crushed_raw_gold";
            case LEAD -> "create:crushed_raw_lead";
            case TIN -> "create:crushed_raw_tin";
            case SILVER -> "create:crushed_raw_silver";
            case CINNABAR -> "thaumaturge:raw_cinnabar";
            case QUARTZ -> "minecraft:quartz";
            case OSMIUM -> "mekanism:raw_osmium";
            case URANIUM -> "mekanism:raw_uranium";
        };
    }

    private static int guaranteed(ClusterMaterial material) {
        return material == ClusterMaterial.QUARTZ ? 2 : 1;
    }

    private static double bonusChance(ClusterMaterial material) {
        return material == ClusterMaterial.QUARTZ ? 0.25 : 0.75;
    }
}
