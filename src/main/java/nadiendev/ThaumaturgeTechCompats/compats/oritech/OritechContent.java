package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.aspects;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.item;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.strings;

public class OritechContent implements DataProvider {
    public static final String NODES_RESEARCH = "oritech_nodes";

    private static final String ORITECH = OritechFeatures.MOD_ID;
    private static final int COLUMN = 9;
    private static final int NODE_ROW = 2;
    private static final int ADDON_ROW = 4;
    private static final int BOULDER_FILLER = 17;
    private static final int BOULDER_ORES = 3;

    private final PackOutput output;

    public OritechContent(PackOutput output) {
        this.output = output;
    }

    public static String addonsResearch(OritechTier tier) {
        return "oritech_" + tier.id() + "_addons";
    }

    public static String nodeFeature(OritechNode node) {
        return "oritech_node_" + node.id();
    }

    private static String infusionPath(OritechNode node) {
        return "infusion/oritech/" + node.blockName();
    }

    private static String arcanePath(OritechAddon addon, OritechTier tier) {
        return "arcane_workbench/oritech/" + addon.blockName(tier);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "thaumaturge/research_entry/" + NODES_RESEARCH, nodesEntry()));
        tasks.add(saveAt(cache, "data/" + ORITECH + "/tags/block/resource_nodes.json", nodeTag()));
        for (OritechNode node : OritechNode.values()) {
            tasks.add(save(cache, "recipe/" + infusionPath(node), nodeInfusion(node)));
            tasks.add(save(cache, "recipe/" + NodeProduction.RECIPE_FOLDER + node.id(), deepDrill(node)));
            tasks.add(save(cache, "worldgen/configured_feature/" + nodeFeature(node), configuredFeature(node)));
            tasks.add(save(cache, "worldgen/placed_feature/" + nodeFeature(node), placedFeature(node)));
            tasks.add(save(cache, "neoforge/biome_modifier/" + nodeFeature(node), biomeModifier(node)));
        }
        for (OritechTier tier : OritechTier.values()) {
            tasks.add(save(cache, "thaumaturge/research_entry/" + addonsResearch(tier), addonsEntry(tier)));
            for (OritechAddon addon : OritechAddon.values()) {
                tasks.add(save(cache, "recipe/" + arcanePath(addon, tier), addonRecipe(tier, addon)));
                tasks.add(save(cache, "loot_table/blocks/" + addon.blockName(tier), addonLoot(tier, addon)));
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Oritech content";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return saveAt(cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private CompletableFuture<?> saveAt(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, path, json);
    }

    private static JsonArray conditions(List<String> features) {
        JsonArray conditions = CompatJson.conditions(ORITECH);
        for (String feature : features) {
            conditions.add(CompatJson.feature(feature));
        }
        return conditions;
    }

    private static JsonArray nodeConditions(OritechNode node) {
        return conditions(OritechFeatures.nodeChain(node));
    }

    private static JsonArray addonConditions(OritechTier tier, OritechAddon addon) {
        return conditions(OritechFeatures.addonChain(tier, addon));
    }

    private static JsonObject entry(String name, List<String> parents, int row, String icon, List<String> recipes, String knowledgeCategory, int theory, int warp, JsonArray noteAspects, int complexity, String group) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(List.of(group)));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", strings(parents));
        json.addProperty("column", COLUMN);
        json.addProperty("row", row);
        json.add("meta", strings("round", "hidden"));
        json.add("icons", strings(icon));

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
        first.addProperty("warp", warp);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", strings(recipes));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }

    private static JsonObject nodesEntry() {
        List<String> recipes = new ArrayList<>();
        for (OritechNode node : OritechNode.values()) {
            recipes.add(MOD + ":" + infusionPath(node));
        }
        return entry(NODES_RESEARCH,
                List.of(TechCategory.HEADER_ID, "thaumaturge:base_infusion"),
                NODE_ROW,
                ORITECH + ":resource_node_uranium",
                recipes,
                "thaumaturge:artifice", 1, 1,
                aspects("thaumaturge:permutatio", 4, "thaumaturge:metallum", 4, "thaumaturge:terra", 3),
                2,
                OritechFeatures.NODES);
    }

    private static JsonObject addonsEntry(OritechTier tier) {
        OritechTier previous = tier.previous();
        List<String> parents = new ArrayList<>();
        if (previous == null) {
            parents.add(TechCategory.HEADER_ID);
        } else {
            parents.add(MOD + ":" + addonsResearch(previous));
        }
        parents.add("thaumaturge:base_artifice");
        switch (tier) {
            case BRASS -> parents.add("thaumaturge:metallurgy@1");
            case THAUMIUM -> parents.add("thaumaturge:metallurgy@2");
            case VOID -> parents.add("thaumaturge:base_eldritch");
        }
        String knowledge = switch (tier) {
            case BRASS -> "thaumaturge:alchemy";
            case THAUMIUM -> "thaumaturge:artifice";
            case VOID -> "thaumaturge:eldritch";
        };
        List<String> recipes = new ArrayList<>();
        for (OritechAddon addon : OritechAddon.values()) {
            recipes.add(MOD + ":" + arcanePath(addon, tier));
        }
        return entry(addonsResearch(tier),
                parents,
                ADDON_ROW + tier.ordinal() * 2,
                MOD + ":" + OritechAddon.SPEED.blockName(tier),
                recipes,
                knowledge, 1 + tier.ordinal(), tier.ordinal(),
                aspects("thaumaturge:machina", 4 + tier.ordinal() * 2, "thaumaturge:potentia", 4 + tier.ordinal() * 2, "thaumaturge:motus", 3 + tier.ordinal()),
                1 + tier.ordinal(),
                OritechFeatures.ADDONS);
    }

    private static JsonObject nodeTag() {
        JsonArray values = new JsonArray();
        for (OritechNode node : OritechNode.values()) {
            JsonObject value = new JsonObject();
            value.addProperty("id", MOD + ":" + node.blockName());
            value.addProperty("required", false);
            values.add(value);
        }
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        json.add("values", values);
        return json;
    }

    private static JsonObject nodeInfusion(OritechNode node) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            if (node.ore() != null) {
                components.add(item(node.ore()));
                components.add(item(node.product()));
            } else {
                components.add(item(node.product()));
                components.add(item(node.product()));
            }
        }
        JsonObject json = ContentJson.infusion(
                aspects("thaumaturge:permutatio", 16, "thaumaturge:metallum", 12, "thaumaturge:terra", 12),
                item(node.catalyst()),
                components,
                4,
                MOD + ":" + NODES_RESEARCH,
                MOD + ":" + node.blockName());
        json.add("neoforge:conditions", nodeConditions(node));
        return json;
    }

    private static JsonObject deepDrill(OritechNode node) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", nodeConditions(node));
        json.addProperty("type", ORITECH + ":deep_drill");
        JsonArray ingredients = new JsonArray();
        ingredients.add(item(MOD + ":" + node.blockName()));
        json.add("ingredients", ingredients);
        JsonArray results = new JsonArray();
        results.add(CompatJson.result(node.product(), node.production()));
        json.add("results", results);
        json.addProperty("time", 1);
        return json;
    }

    private static JsonObject configuredFeature(OritechNode node) {
        JsonArray nodeOres = new JsonArray();
        nodeOres.add(MOD + ":" + node.blockName());
        JsonArray boulderOres = new JsonArray();
        for (int i = 0; i < BOULDER_ORES; i++) {
            boulderOres.add(node.boulderOre());
        }
        for (int i = 0; i < BOULDER_FILLER; i++) {
            boulderOres.add("minecraft:deepslate");
        }
        JsonObject config = new JsonObject();
        config.addProperty("nodeSize", 6);
        config.addProperty("nodeOreChance", 0.3);
        config.addProperty("boulderRadius", 3);
        config.add("nodeOres", nodeOres);
        config.add("boulderOres", boulderOres);
        config.addProperty("overlayBlock", node.overlay());
        config.addProperty("overlayHeight", 5);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", nodeConditions(node));
        json.addProperty("type", ORITECH + ":resource_node");
        json.add("config", config);
        return json;
    }

    private static JsonObject placedFeature(OritechNode node) {
        JsonObject rarity = new JsonObject();
        rarity.addProperty("type", MOD + ":node_rarity");
        rarity.addProperty("node", node.id());
        JsonObject square = new JsonObject();
        square.addProperty("type", "minecraft:in_square");
        JsonObject heightmap = new JsonObject();
        heightmap.addProperty("type", "minecraft:heightmap");
        heightmap.addProperty("heightmap", "WORLD_SURFACE_WG");
        JsonArray placement = new JsonArray();
        placement.add(rarity);
        placement.add(square);
        placement.add(heightmap);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", nodeConditions(node));
        json.addProperty("feature", MOD + ":" + nodeFeature(node));
        json.add("placement", placement);
        return json;
    }

    private static JsonObject biomeModifier(OritechNode node) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", nodeConditions(node));
        json.addProperty("type", "neoforge:add_features");
        json.addProperty("biomes", "#minecraft:is_overworld");
        json.addProperty("features", MOD + ":" + nodeFeature(node));
        json.addProperty("step", "top_layer_modification");
        return json;
    }

    private static JsonObject addonRecipe(OritechTier tier, OritechAddon addon) {
        OritechTier previous = tier.previous();
        String catalyst = previous == null ? addon.baseBlock() : MOD + ":" + addon.blockName(previous);
        String special = switch (tier) {
            case BRASS -> addon.component();
            case THAUMIUM -> "oritech:overcharged_crystal";
            case VOID -> "oritech:heisenberg_compensator";
        };
        JsonObject key = new JsonObject();
        key.add("p", item(tier.plate()));
        key.add("m", item(tier.mechanism()));
        key.add("s", item(special));
        key.add("c", item(catalyst));
        JsonObject json = ContentJson.arcaneShaped(key, strings("pmp", "scs", "pmp"), MOD + ":" + addonsResearch(tier), tier.vis(), MOD + ":" + addon.blockName(tier));
        JsonArray crystals = new JsonArray();
        JsonObject crystal = new JsonObject();
        crystal.addProperty("amount", tier.crystals());
        crystal.addProperty("aspect", addon.crystal());
        crystals.add(crystal);
        json.add("crystals", crystals);
        json.add("neoforge:conditions", addonConditions(tier, addon));
        return json;
    }

    private static JsonObject addonLoot(OritechTier tier, OritechAddon addon) {
        String name = addon.blockName(tier);
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", MOD + ":" + name);
        JsonObject survives = new JsonObject();
        survives.addProperty("condition", "minecraft:survives_explosion");
        JsonArray poolConditions = new JsonArray();
        poolConditions.add(survives);
        JsonArray entries = new JsonArray();
        entries.add(entry);
        JsonObject pool = new JsonObject();
        pool.addProperty("bonus_rolls", 0.0);
        pool.add("conditions", poolConditions);
        pool.add("entries", entries);
        pool.addProperty("rolls", 1.0);
        JsonArray pools = new JsonArray();
        pools.add(pool);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", addonConditions(tier, addon));
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", MOD + ":blocks/" + name);
        return json;
    }
}
