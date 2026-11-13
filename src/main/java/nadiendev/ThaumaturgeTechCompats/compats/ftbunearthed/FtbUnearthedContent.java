package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed;

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

public class FtbUnearthedContent implements DataProvider {
    public static final String ENTRY_PREFIX = "ftbunearthed_";
    public static final int COLUMN = 24;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String RECIPE_DIR = "compat/ftbunearthed/";

    private final PackOutput output;

    public FtbUnearthedContent(PackOutput output) {
        this.output = output;
    }

    public static String entryName(FtbUnearthedBrushTier tier) {
        return ENTRY_PREFIX + tier.itemId();
    }

    public static int row(FtbUnearthedBrushTier tier) {
        return 2 + tier.ordinal() * 2;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FtbUnearthedBrushTier tier : FtbUnearthedBrushTier.values()) {
            tasks.add(save(cache, "thaumaturge/research_entry/" + entryName(tier), entry(tier)));
            tasks.add(save(cache, "recipe/" + RECIPE_DIR + tier.itemId(), brushRecipe(tier)));
            for (FtbUnearthedSource source : FtbUnearthedSource.values()) {
                for (int mask = 1; mask < (1 << FtbUnearthedLoot.values().length); mask++) {
                    List<FtbUnearthedLoot> loot = subset(mask);
                    tasks.add(save(cache, "recipe/" + RECIPE_DIR + "unearther/" + tier.id() + "_" + source.id() + "_" + suffix(loot),
                            uneartherRecipe(tier, source, loot)));
                }
            }
        }
        tasks.add(save(cache, "recipe/" + RECIPE_DIR + "unearther_core", coreRecipe()));
        tasks.add(ContentJson.save(output, cache, "data/c/tags/item/tools/brush.json", brushTag()));
        tasks.add(ContentJson.save(output, cache, "data/minecraft/tags/item/enchantable/durability.json", brushTag()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats FTB Unearthed";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static List<FtbUnearthedLoot> subset(int mask) {
        List<FtbUnearthedLoot> loot = new ArrayList<>();
        for (FtbUnearthedLoot type : FtbUnearthedLoot.values()) {
            if ((mask & (1 << type.ordinal())) != 0) {
                loot.add(type);
            }
        }
        return loot;
    }

    private static String suffix(List<FtbUnearthedLoot> loot) {
        StringBuilder builder = new StringBuilder();
        for (FtbUnearthedLoot type : loot) {
            builder.append(type.letter());
        }
        return builder.toString();
    }

    private static JsonObject coreRecipe() {
        JsonObject key = new JsonObject();
        key.add("i", ContentJson.item("minecraft:iron_ingot"));
        key.add("g", ContentJson.item("minecraft:glass"));
        key.add("h", ContentJson.item("minecraft:hopper"));
        key.add("b", ContentJson.item("minecraft:brush"));
        key.add("p", ContentJson.item("minecraft:piston"));
        JsonObject json = ContentJson.shaped("misc", key, ContentJson.strings("igi", "hbh", "ipi"), "ftbunearthed:core", 1);
        JsonArray conditions = CompatJson.conditions(FtbUnearthedIntegration.MOD_ID);
        conditions.add(CompatJson.feature(CompatToggles.FTB_UNEARTHED_CORE_RECIPE));
        json.add("neoforge:conditions", conditions);
        return json;
    }

    private static JsonArray tierConditions(FtbUnearthedBrushTier tier) {
        JsonArray conditions = CompatJson.conditions(FtbUnearthedIntegration.MOD_ID);
        for (FtbUnearthedBrushTier current = tier; current != null; current = current.previous()) {
            conditions.add(CompatJson.feature(current.toggle()));
        }
        return conditions;
    }

    private static JsonObject not(JsonObject value) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:not");
        condition.add("value", value);
        return condition;
    }

    private static JsonObject brushTag() {
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        JsonArray values = new JsonArray();
        for (FtbUnearthedBrushTier tier : FtbUnearthedBrushTier.values()) {
            JsonObject value = new JsonObject();
            value.addProperty("id", MOD + ":" + tier.itemId());
            value.addProperty("required", false);
            values.add(value);
        }
        json.add("values", values);
        return json;
    }

    private static JsonObject uneartherRecipe(FtbUnearthedBrushTier tier, FtbUnearthedSource source, List<FtbUnearthedLoot> loot) {
        JsonArray conditions = tierConditions(tier);
        for (FtbUnearthedLoot type : FtbUnearthedLoot.values()) {
            JsonObject feature = CompatJson.feature(type.toggle());
            conditions.add(loot.contains(type) ? feature : not(feature));
        }
        JsonObject json = CompatJson.recipe("ftbunearthed:unearther", conditions);
        json.addProperty("input_block", source.block());
        JsonObject worker = new JsonObject();
        worker.addProperty("profession", "minecraft:mason");
        json.add("worker", worker);
        json.add("tool_item", ContentJson.item(MOD + ":" + tier.itemId()));
        json.addProperty("processing_time", tier.processingTime());
        json.addProperty("damage_chance", tier.damageChance());
        JsonArray outputs = new JsonArray();
        for (FtbUnearthedLoot type : loot) {
            for (JsonObject output : type.outputs(source, tier)) {
                outputs.add(output);
            }
        }
        json.add("output_items", outputs);
        return json;
    }

    private static JsonObject brushRecipe(FtbUnearthedBrushTier tier) {
        JsonObject key = new JsonObject();
        key.add("f", ContentJson.item("minecraft:feather"));
        key.add("p", ContentJson.item(tier.plate()));
        JsonArray pattern;
        FtbUnearthedBrushTier previous = tier.previous();
        if (previous == null) {
            key.add("s", ContentJson.item("minecraft:stick"));
            pattern = ContentJson.strings(" f ", " p ", " s ");
        } else {
            key.add("b", ContentJson.item(MOD + ":" + previous.itemId()));
            pattern = ContentJson.strings(" f ", "pbp", " p ");
        }
        JsonObject json = ContentJson.arcaneShaped(key, pattern, MOD + ":" + entryName(tier), tier.vis(), MOD + ":" + tier.itemId());
        json.add("neoforge:conditions", tierConditions(tier));
        return json;
    }

    private static JsonArray noteAspects(FtbUnearthedBrushTier tier) {
        return switch (tier) {
            case BRASS -> ContentJson.aspects("thaumaturge:instrumentum", 4, "thaumaturge:metallum", 4, "thaumaturge:sensus", 3);
            case THAUMIUM -> ContentJson.aspects("thaumaturge:instrumentum", 6, "thaumaturge:praecantatio", 5, "thaumaturge:sensus", 5);
            case VOID -> ContentJson.aspects("thaumaturge:instrumentum", 8, "thaumaturge:vacuos", 6, "thaumaturge:sensus", 6);
        };
    }

    private static List<String> parents(FtbUnearthedBrushTier tier) {
        return switch (tier) {
            case BRASS -> List.of(TechCategory.HEADER_ID, "thaumaturge:metallurgy@1");
            case THAUMIUM -> List.of(MOD + ":" + entryName(FtbUnearthedBrushTier.BRASS), "thaumaturge:base_artifice", "thaumaturge:metallurgy@2");
            case VOID -> List.of(MOD + ":" + entryName(FtbUnearthedBrushTier.THAUMIUM), "thaumaturge:base_artifice", "thaumaturge:base_eldritch");
        };
    }

    private static String knowledgeCategory(FtbUnearthedBrushTier tier) {
        return switch (tier) {
            case BRASS -> "thaumaturge:alchemy";
            case THAUMIUM -> "thaumaturge:artifice";
            case VOID -> "thaumaturge:eldritch";
        };
    }

    private static JsonObject entry(FtbUnearthedBrushTier tier) {
        String name = entryName(tier);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", tierConditions(tier));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents(tier)));
        json.addProperty("column", COLUMN);
        json.addProperty("row", row(tier));
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(MOD + ":" + tier.itemId()));

        JsonArray stages = new JsonArray();
        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", knowledgeCategory(tier));
        knowledge.addProperty("amount", 1 + tier.ordinal());
        JsonArray required = new JsonArray();
        required.add(knowledge);
        first.add("required_knowledge", required);
        first.addProperty("warp", tier.ordinal());
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(MOD + ":" + RECIPE_DIR + tier.itemId()));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects(tier));
        json.addProperty("complexity", 1 + tier.ordinal());
        return json;
    }
}
