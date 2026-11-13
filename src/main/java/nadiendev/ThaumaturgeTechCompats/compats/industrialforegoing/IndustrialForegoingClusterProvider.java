package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing;

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

public class IndustrialForegoingClusterProvider implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String THE_END = "minecraft:the_end";
    private static final String THE_NETHER = "minecraft:the_nether";
    private static final int FALLBACK_WEIGHT = 1;

    private final PackOutput output;

    public IndustrialForegoingClusterProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (Drill drill : drills()) {
            tasks.add(CompatJson.save(output, cache,
                    "data/" + MOD + "/recipe/compat/industrialforegoing/laser_drill_ore/" + drill.name() + ".json", drill.toJson()));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Industrial Foregoing ores";
    }

    private static List<Drill> drills() {
        return List.of(
                Drill.item("cluster_iron", "thaumaturge:cluster_iron", "brown", 5, 68, 6, false),
                Drill.item("cluster_copper", "thaumaturge:cluster_copper", "orange", 35, 65, 3, false),
                Drill.item("cluster_gold", "thaumaturge:cluster_gold", "yellow", 32, 80, 5, false),
                Drill.item("cluster_lead", "thaumaturge:cluster_lead", "purple", 10, 40, 2, false),
                Drill.item("cluster_tin", "thaumaturge:cluster_tin", "light_gray", 64, 96, 2, false),
                Drill.item("cluster_silver", "thaumaturge:cluster_silver", "gray", 10, 40, 2, false),
                Drill.item("cluster_cinnabar", "thaumaturge:cluster_cinnabar", "red", 30, 70, 2, false),
                Drill.item("cluster_quartz", "thaumaturge:cluster_quartz", "white", 7, 117, 4, true),
                Drill.own("cluster_osmium", ClusterMaterial.OSMIUM, "light_blue", 5, 60, 3),
                Drill.own("cluster_uranium", ClusterMaterial.URANIUM, "green", 0, 50, 2),
                Drill.tag("amber", "c:ores/amber", "orange", 40, 70, 2),
                Drill.item("quartz_ore", "thaumaturge:ore_quartz", "white", 0, 64, 4, false));
    }

    private record Drill(String name, String output, boolean tag, ClusterMaterial material, String lens, int min, int max, int weight, boolean nether) {
        static Drill item(String name, String output, String lens, int min, int max, int weight, boolean nether) {
            return new Drill(name, output, false, null, lens, min, max, weight, nether);
        }

        static Drill tag(String name, String output, String lens, int min, int max, int weight) {
            return new Drill(name, output, true, null, lens, min, max, weight, false);
        }

        static Drill own(String name, ClusterMaterial material, String lens, int min, int max, int weight) {
            return new Drill(name, material.cluster(), false, material, lens, min, max, weight, false);
        }

        JsonObject toJson() {
            JsonArray conditions = material != null
                    ? CompatJson.conditionsFor("industrialforegoing", material)
                    : tag
                    ? CompatJson.conditions("industrialforegoing", output)
                    : CompatJson.conditions("industrialforegoing");
            JsonObject json = CompatJson.recipe("industrialforegoing:laser_drill_ore", conditions);
            JsonObject catalyst = new JsonObject();
            catalyst.addProperty("item", "industrialforegoing:" + lens + "_laser_lens");
            json.add("catalyst", catalyst);
            json.add("output", tag ? CompatJson.tagCount(output, 1) : CompatJson.itemCount(output, 1));
            JsonArray rarity = new JsonArray();
            rarity.add(entry(min, max, weight, nether ? List.of() : List.of(THE_END), nether ? List.of(THE_NETHER) : List.of()));
            rarity.add(entry(0, 255, FALLBACK_WEIGHT, List.of(THE_END), List.of()));
            json.add("rarity", rarity);
            return json;
        }

        private static JsonObject entry(int min, int max, int weight, List<String> blacklist, List<String> whitelist) {
            JsonObject entry = new JsonObject();
            entry.add("biome_filter", filter(List.of(), List.of()));
            entry.addProperty("depth_max", max);
            entry.addProperty("depth_min", min);
            entry.add("dimension_filter", filter(blacklist, whitelist));
            entry.addProperty("weight", weight);
            return entry;
        }

        private static JsonObject filter(List<String> blacklist, List<String> whitelist) {
            JsonObject filter = new JsonObject();
            JsonArray black = new JsonArray();
            blacklist.forEach(black::add);
            JsonArray white = new JsonArray();
            whitelist.forEach(white::add);
            filter.add("blacklist", black);
            filter.add("whitelist", white);
            return filter;
        }
    }
}
