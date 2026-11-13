package nadiendev.ThaumaturgeTechCompats.compats.oritech.processing;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.ClusterMaterial;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.OritechFeatures;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class OritechProcessingRecipes implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String ORITECH = OritechFeatures.MOD_ID;
    private static final String MEKANISM = "mekanism";
    private static final String BASE = "data/" + MOD + "/recipe/compat/oritech/";
    private static final int PULVERIZER_TIME = 100;
    private static final int GRINDER_TIME = 40;
    private static final int BONUS = 1;

    private final PackOutput output;

    public OritechProcessingRecipes(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (Source source : sources()) {
            for (Machine machine : Machine.values()) {
                for (boolean bonus : new boolean[]{true, false}) {
                    String path = BASE + machine.folder + "/" + (bonus ? "bonus" : "base") + "/" + source.name + ".json";
                    tasks.add(CompatJson.save(output, cache, path, recipe(source, machine, bonus)));
                }
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Oritech processing";
    }

    private static List<Source> sources() {
        List<Source> sources = new ArrayList<>();
        for (ClusterMaterial material : ClusterMaterial.values()) {
            switch (material) {
                case IRON -> sources.add(cluster(material, "minecraft:raw_iron", 2, 2, 100, result("oritech:raw_nickel", 1), null));
                case COPPER -> sources.add(cluster(material, "minecraft:raw_copper", 2, 2, 100, result("minecraft:raw_gold", 1), null));
                case GOLD -> sources.add(cluster(material, "minecraft:raw_gold", 2, 2, 100, result("minecraft:raw_copper", 1), null));
                case LEAD -> sources.add(cluster(material, "mekanism:raw_lead", 2, 2, 100, result("mekanism:raw_tin", 1), MEKANISM));
                case TIN -> sources.add(cluster(material, "mekanism:raw_tin", 2, 2, 100, result("mekanism:raw_lead", 1), MEKANISM));
                case CINNABAR -> sources.add(cluster(material, "thaumaturge:raw_cinnabar", 2, 2, 100, result("thaumaturge:nugget_quicksilver", 2), null));
                case QUARTZ -> sources.add(cluster(material, "minecraft:quartz", 2, 3, 100, null, null));
                case OSMIUM -> sources.add(cluster(material, "mekanism:raw_osmium", 2, 2, 150, result("oritech:raw_platinum", 1), MEKANISM));
                case URANIUM -> sources.add(cluster(material, "oritech:raw_uranium", 2, 3, 100, result("oritech:small_plutonium_dust", 1), null));
                default -> {
                }
            }
        }
        sources.add(oreTag("ore_cinnabar", "c:ores/cinnabar", "thaumaturge:raw_cinnabar", 2, 2, result("thaumaturge:nugget_quicksilver", 2)));
        sources.add(oreTag("ore_amber", "c:ores/amber", "thaumaturge:amber", 2, 3, null));
        sources.add(oreItem("ore_quartz", "thaumaturge:ore_quartz", "minecraft:quartz", 2, 3));
        sources.add(oreItem("deepslate_ore_quartz", "thaumaturge:deepslate_ore_quartz", "minecraft:quartz", 2, 3));
        return sources;
    }

    private static Source cluster(ClusterMaterial material, String main, int pulverizer, int grinder, int pulverizerTime, Result byproduct, String outputMod) {
        return new Source("cluster_" + material.id(), "item", material.cluster(), main, pulverizer, grinder, pulverizerTime, byproduct, outputMod, material, null);
    }

    private static Source oreTag(String name, String tag, String main, int pulverizer, int grinder, Result byproduct) {
        return new Source(name, "tag", tag, main, pulverizer, grinder, PULVERIZER_TIME, byproduct, null, null, tag);
    }

    private static Source oreItem(String name, String item, String main, int pulverizer, int grinder) {
        return new Source(name, "item", item, main, pulverizer, grinder, PULVERIZER_TIME, null, null, null, null);
    }

    private static Result result(String id, int count) {
        return new Result(id, count);
    }

    private static JsonObject recipe(Source source, Machine machine, boolean bonus) {
        JsonObject json = CompatJson.recipe("oritech:" + machine.type, conditions(source, machine, bonus));
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty(source.ingredientType, source.ingredient);
        JsonArray ingredients = new JsonArray();
        ingredients.add(ingredient);
        json.add("ingredients", ingredients);
        int base = machine == Machine.PULVERIZER ? source.pulverizer : source.grinder;
        JsonArray results = new JsonArray();
        results.add(CompatJson.result(source.main, base + (bonus ? BONUS : 0)));
        if (machine == Machine.GRINDER && source.byproduct != null) {
            results.add(CompatJson.result(source.byproduct.id, source.byproduct.count));
        }
        json.add("results", results);
        json.addProperty("time", machine == Machine.PULVERIZER ? source.pulverizerTime : GRINDER_TIME);
        return json;
    }

    private static JsonArray conditions(Source source, Machine machine, boolean bonus) {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.modLoaded(ORITECH));
        conditions.add(CompatJson.feature(CompatToggles.ORITECH_PROCESSING));
        conditions.add(CompatJson.feature(machine.toggle));
        if (bonus) {
            conditions.add(CompatJson.feature(CompatToggles.ORITECH_BONUS));
        } else {
            conditions.add(not(CompatJson.feature(CompatToggles.ORITECH_BONUS)));
        }
        if (source.material != null && source.material.requiredMod() != null) {
            conditions.add(CompatJson.modLoaded(source.material.requiredMod()));
            conditions.add(CompatJson.feature(CompatToggles.MEKANISM_CLUSTERS));
        }
        if (source.outputMod != null) {
            conditions.add(CompatJson.modLoaded(source.outputMod));
        }
        if (source.nonEmptyTag != null) {
            JsonObject empty = new JsonObject();
            empty.addProperty("type", "neoforge:tag_empty");
            empty.addProperty("tag", source.nonEmptyTag);
            conditions.add(not(empty));
        }
        return conditions;
    }

    private static JsonObject not(JsonObject value) {
        JsonObject not = new JsonObject();
        not.addProperty("type", "neoforge:not");
        not.add("value", value);
        return not;
    }

    private enum Machine {
        PULVERIZER("pulverizer", "pulverizer", CompatToggles.ORITECH_PULVERIZER),
        GRINDER("grinder", "grinder", CompatToggles.ORITECH_GRINDER);

        private final String folder;
        private final String type;
        private final String toggle;

        Machine(String folder, String type, String toggle) {
            this.folder = folder;
            this.type = type;
            this.toggle = toggle;
        }
    }

    private record Result(String id, int count) {
    }

    private record Source(String name, String ingredientType, String ingredient, String main, int pulverizer, int grinder, int pulverizerTime, Result byproduct, String outputMod, ClusterMaterial material, String nonEmptyTag) {
    }
}
