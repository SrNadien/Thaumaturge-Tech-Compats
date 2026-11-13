package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.nodepower.FluxJarContent;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class VoidMinersMagicalContent implements DataProvider {
    public static final int MINER_COLUMN = 39;
    public static final int SOLAR_COLUMN = 42;
    public static final int MINER_UPGRADES_COLUMN = 40;
    public static final int SOLAR_UPGRADES_COLUMN = 43;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String VOID_MINERS = VoidMinersMagicalIntegration.MOD_ID;
    private static final String RECIPE_DIR = "compat/voidminers/";
    private static final String FLUX_JAR = MOD + ":flux_jar";
    private static final String JAR_NODE = "thaumaturge:jar_node";
    private static final String VOID_SEED = "thaumaturge:void_seed";
    private static final String OBSIDIAN = "minecraft:obsidian";

    private final PackOutput output;

    public VoidMinersMagicalContent(PackOutput output) {
        this.output = output;
    }

    public static int row(VoidMinersMagicalTier tier) {
        return 2 + tier.ordinal() * 2;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            for (boolean solar : new boolean[]{false, true}) {
                String block = block(tier, solar);
                String frame = frame(tier, solar);
                tasks.add(save(cache, "loot_table/blocks/" + block, lootTable(block)));
                tasks.add(save(cache, "loot_table/blocks/" + frame, lootTable(frame)));
                tasks.add(save(cache, "recipe/" + RECIPE_DIR + block, controllerRecipe(tier, solar)));
                tasks.add(save(cache, "recipe/" + RECIPE_DIR + frame, frameRecipe(tier, solar)));
                tasks.add(save(cache, "thaumaturge/research_entry/" + entryName(tier, solar), entry(tier, solar)));
                tasks.add(save(cache, "thaumaturge/research_entry/" + tier.upgradesEntry(solar), upgradesEntry(tier, solar)));
                for (MagicalUpgrade upgrade : MagicalUpgrade.of(solar)) {
                    tasks.add(save(cache, "loot_table/blocks/" + upgrade.blockName(tier), lootTable(upgrade.blockName(tier))));
                    tasks.add(save(cache, "recipe/" + RECIPE_DIR + upgrade.blockName(tier), upgradeRecipe(tier, solar, upgrade)));
                }
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats void miners magical";
    }

    private static String block(VoidMinersMagicalTier tier, boolean solar) {
        return solar ? tier.solarBlock() : tier.minerBlock();
    }

    private static String frame(VoidMinersMagicalTier tier, boolean solar) {
        return solar ? tier.solarFrameBlock() : tier.minerFrameBlock();
    }

    public static String entryName(VoidMinersMagicalTier tier, boolean solar) {
        return solar ? tier.solarEntry() : tier.minerEntry();
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions(VoidMinersMagicalTier tier, boolean solar) {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.modLoaded(VOID_MINERS));
        conditions.add(CompatJson.feature(CompatToggles.NODE_POWER));
        conditions.add(CompatJson.feature(solar ? VoidMinersMagicalIntegration.TOGGLE_SOLAR : VoidMinersMagicalIntegration.TOGGLE_MINER));
        for (VoidMinersMagicalTier current = tier; current != null; current = current.previous()) {
            conditions.add(CompatJson.feature(solar ? current.solarToggle() : current.minerToggle()));
        }
        return conditions;
    }

    private static JsonArray upgradeConditions(VoidMinersMagicalTier tier, boolean solar) {
        JsonArray conditions = conditions(tier, solar);
        for (VoidMinersMagicalTier current = tier; current != null; current = current.previous()) {
            conditions.add(CompatJson.feature(current.upgradesEntry(solar)));
        }
        return conditions;
    }

    private static JsonObject lootTable(String block) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", MOD + ":" + block);

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
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", MOD + ":blocks/" + block);
        return json;
    }

    private static void add(JsonArray components, String item, int count) {
        for (int i = 0; i < count; i++) {
            components.add(ContentJson.item(item));
        }
    }

    private static JsonObject controllerRecipe(VoidMinersMagicalTier tier, boolean solar) {
        VoidMinersMagicalTier previous = tier.previous();
        String catalyst;
        if (previous != null) {
            catalyst = MOD + ":" + block(previous, solar);
        } else {
            catalyst = VOID_MINERS + (solar ? ":solar_rubetine_panel" : ":rubetine_miner");
        }
        JsonArray components = new JsonArray();
        add(components, tier.plate(), 3);
        add(components, tier.mechanism(), 1);
        add(components, solar ? JAR_NODE : FLUX_JAR, 2);
        if (tier.last()) {
            add(components, VOID_SEED, 1);
        }
        JsonObject json = ContentJson.infusion(aspects(tier, solar), ContentJson.item(catalyst), components,
                2 + tier.ordinal() * 2, MOD + ":" + entryName(tier, solar), MOD + ":" + block(tier, solar));
        json.add("neoforge:conditions", conditions(tier, solar));
        return json;
    }

    private static JsonArray aspects(VoidMinersMagicalTier tier, boolean solar) {
        if (solar) {
            return switch (tier) {
                case THAUMIUM -> ContentJson.aspects("thaumaturge:lux", 12, "thaumaturge:potentia", 10, "thaumaturge:metallum", 8, "thaumaturge:alkimia", 6);
                case ALCHEMICAL_BRASS -> ContentJson.aspects("thaumaturge:lux", 18, "thaumaturge:potentia", 16, "thaumaturge:praecantatio", 12, "thaumaturge:machina", 10);
                case VOID_METAL -> ContentJson.aspects("thaumaturge:lux", 24, "thaumaturge:potentia", 24, "thaumaturge:vacuos", 16, "thaumaturge:praecantatio", 16);
            };
        }
        return switch (tier) {
            case THAUMIUM -> ContentJson.aspects("thaumaturge:machina", 12, "thaumaturge:terra", 8, "thaumaturge:metallum", 8, "thaumaturge:potentia", 6);
            case ALCHEMICAL_BRASS -> ContentJson.aspects("thaumaturge:machina", 18, "thaumaturge:terra", 12, "thaumaturge:praecantatio", 10, "thaumaturge:potentia", 12);
            case VOID_METAL -> ContentJson.aspects("thaumaturge:machina", 24, "thaumaturge:terra", 16, "thaumaturge:vacuos", 16, "thaumaturge:potentia", 12);
        };
    }

    private static JsonObject frameRecipe(VoidMinersMagicalTier tier, boolean solar) {
        JsonObject key = new JsonObject();
        key.add("P", ContentJson.item(tier.plate()));
        key.add("O", ContentJson.item(OBSIDIAN));
        VoidMinersMagicalTier previous = tier.previous();
        key.add("F", ContentJson.item(previous == null ? VOID_MINERS + ":frame_base" : MOD + ":" + frame(previous, solar)));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("POP", "OFO", "POP"),
                MOD + ":" + entryName(tier, solar), 30 + tier.ordinal() * 20, MOD + ":" + frame(tier, solar));
        json.add("neoforge:conditions", conditions(tier, solar));
        return json;
    }

    private static JsonObject upgradeRecipe(VoidMinersMagicalTier tier, boolean solar, MagicalUpgrade upgrade) {
        VoidMinersMagicalTier previous = tier.previous();
        JsonObject key = new JsonObject();
        key.add("P", ContentJson.item(tier.plate()));
        key.add("M", ContentJson.item(tier.mechanism()));
        key.add("U", ContentJson.item(previous == null ? upgrade.base() : MOD + ":" + upgrade.blockName(previous)));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings(" M ", "PUP", " M "),
                MOD + ":" + tier.upgradesEntry(solar), 20 + tier.ordinal() * 15, MOD + ":" + upgrade.blockName(tier));
        json.add("neoforge:conditions", upgradeConditions(tier, solar));
        return json;
    }

    private static JsonObject upgradesEntry(VoidMinersMagicalTier tier, boolean solar) {
        String name = tier.upgradesEntry(solar);
        VoidMinersMagicalTier previous = tier.previous();
        List<String> parents = new ArrayList<>();
        parents.add(MOD + ":" + entryName(tier, solar));
        if (previous != null) {
            parents.add(MOD + ":" + previous.upgradesEntry(solar));
        }
        MagicalUpgrade icon = MagicalUpgrade.of(solar).get(0);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", upgradeConditions(tier, solar));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", solar ? SOLAR_UPGRADES_COLUMN : MINER_UPGRADES_COLUMN);
        json.addProperty("row", row(tier));
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(MOD + ":" + icon.blockName(tier)));

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

        List<String> recipes = new ArrayList<>();
        for (MagicalUpgrade upgrade : MagicalUpgrade.of(solar)) {
            recipes.add(MOD + ":" + RECIPE_DIR + upgrade.blockName(tier));
        }
        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(recipes));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects(tier, solar));
        json.addProperty("complexity", 1 + tier.ordinal());
        return json;
    }

    private static List<String> parents(VoidMinersMagicalTier tier, boolean solar) {
        return switch (tier) {
            case THAUMIUM -> List.of(TechCategory.HEADER_ID, MOD + ":" + FluxJarContent.ENTRY, "thaumaturge:base_artifice", "thaumaturge:metallurgy@1");
            case ALCHEMICAL_BRASS -> List.of(MOD + ":" + entryName(VoidMinersMagicalTier.THAUMIUM, solar), "thaumaturge:base_artifice", "thaumaturge:metallurgy@2");
            case VOID_METAL -> List.of(MOD + ":" + entryName(VoidMinersMagicalTier.ALCHEMICAL_BRASS, solar), "thaumaturge:base_artifice", "thaumaturge:base_eldritch");
        };
    }

    private static String knowledgeCategory(VoidMinersMagicalTier tier) {
        return switch (tier) {
            case THAUMIUM -> "thaumaturge:alchemy";
            case ALCHEMICAL_BRASS -> "thaumaturge:artifice";
            case VOID_METAL -> ContentJson.CATEGORY_ELDRITCH;
        };
    }

    private static JsonArray noteAspects(VoidMinersMagicalTier tier, boolean solar) {
        String main = solar ? "thaumaturge:lux" : "thaumaturge:machina";
        return switch (tier) {
            case THAUMIUM -> ContentJson.aspects(main, 5, "thaumaturge:metallum", 4, "thaumaturge:potentia", 3);
            case ALCHEMICAL_BRASS -> ContentJson.aspects(main, 7, "thaumaturge:praecantatio", 5, "thaumaturge:potentia", 5);
            case VOID_METAL -> ContentJson.aspects(main, 9, "thaumaturge:vacuos", 7, "thaumaturge:potentia", 6);
        };
    }

    private static JsonObject entry(VoidMinersMagicalTier tier, boolean solar) {
        String name = entryName(tier, solar);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(tier, solar));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents(tier, solar)));
        json.addProperty("column", solar ? SOLAR_COLUMN : MINER_COLUMN);
        json.addProperty("row", row(tier));
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(MOD + ":" + block(tier, solar)));

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
        second.add("recipes", ContentJson.strings(
                MOD + ":" + RECIPE_DIR + block(tier, solar),
                MOD + ":" + RECIPE_DIR + frame(tier, solar)));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects(tier, solar));
        json.addProperty("complexity", 1 + tier.ordinal());
        return json;
    }
}
