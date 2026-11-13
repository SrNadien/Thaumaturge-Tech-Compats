package nadiendev.ThaumaturgeTechCompats.compats.sophisticated;

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

public class SophisticatedContent implements DataProvider {
    public static final String STACK_ENTRY = "sophisticated_stack_upgrades";
    public static final String ARCANE_ENTRY = "sophisticated_arcane_upgrade";
    public static final String BACKPACK_ENTRY = "sophisticated_backpack_tiers";
    public static final String STORAGE_ENTRY = "sophisticated_storage_tiers";
    public static final int STACK_COLUMN = 75;
    public static final int ARCANE_COLUMN = 76;
    public static final int BACKPACK_COLUMN = 77;
    public static final int STORAGE_COLUMN = 78;
    public static final String ARCANE_RECIPE = "arcane_workbench/arcane_workbench_upgrade";

    private static final String MOD = ContentJson.MOD;
    private static final String CORE = SophisticatedIntegration.CORE_ID;
    private static final String BACKPACKS = SophisticatedIntegration.BACKPACKS_ID;
    private static final String STORAGE = SophisticatedIntegration.STORAGE_ID;
    private static final String MECHANISM_SIMPLE = "thaumaturge:mechanism_simple";
    private static final String VOID_SEED = "thaumaturge:void_seed";
    private static final String ARCANE_WORKBENCH = "thaumaturge:arcane_workbench";
    private static final String STACK_BASE_TAG = MOD + ":sophisticated_stack_upgrade_base";
    private static final String CRAFTING_UPGRADE_TAG = MOD + ":sophisticated_crafting_upgrade";
    private static final String CATEGORY_ARTIFICE = "thaumaturge:artifice";
    private static final String INFUSION_ENTRY = "thaumaturge:infusion";
    private static final String[] PRIMALS = {"thaumaturge:aer", "thaumaturge:terra", "thaumaturge:ignis", "thaumaturge:aqua", "thaumaturge:ordo", "thaumaturge:perditio"};

    private final PackOutput output;

    public SophisticatedContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            tasks.add(save(cache, "recipe/infusion/" + tier.stackUpgradeName(), stackInfusion(tier)));
            tasks.add(save(cache, "recipe/sophisticated/" + tier.backpackName(), backpackRecipe(tier)));
            tasks.add(save(cache, "recipe/sophisticated/" + tier.barrelName(), storageRecipe(tier, "barrel", 0)));
            for (int size = 1; size <= SophisticatedConfig.LIMITED_BARRELS; size++) {
                tasks.add(save(cache, "recipe/sophisticated/" + tier.limitedBarrelName(size), storageRecipe(tier, "limited", size)));
            }
            tasks.add(save(cache, "recipe/sophisticated/" + tier.chestName(), storageRecipe(tier, "chest", 0)));
            tasks.add(save(cache, "recipe/sophisticated/" + tier.shulkerBoxName(), storageRecipe(tier, "shulker", 0)));
        }
        tasks.add(save(cache, "recipe/" + ARCANE_RECIPE, arcaneRecipe()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + STACK_ENTRY, stackEntry()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + ARCANE_ENTRY, arcaneEntry()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + BACKPACK_ENTRY, backpackEntry()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + STORAGE_ENTRY, storageEntry()));
        tasks.add(save(cache, "tags/item/sophisticated_stack_upgrade_base", tag(List.of(BACKPACKS + ":stack_upgrade_tier_4", STORAGE + ":stack_upgrade_tier_5"))));
        tasks.add(save(cache, "tags/item/sophisticated_crafting_upgrade", tag(List.of(BACKPACKS + ":crafting_upgrade", STORAGE + ":crafting_upgrade"))));
        tasks.add(saveIn(cache, "data/" + BACKPACKS + "/tags/item/upgrade.json", tag(upgradeItems())));
        tasks.add(saveIn(cache, "data/" + STORAGE + "/tags/item/upgrade.json", tag(upgradeItems())));
        tasks.add(saveIn(cache, "data/minecraft/tags/block/mineable/axe.json", tag(axeBlocks())));
        tasks.add(saveIn(cache, "data/minecraft/tags/block/mineable/pickaxe.json", tag(pickaxeBlocks())));
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            tasks.add(save(cache, "loot_table/blocks/" + tier.backpackName(), backpackLoot(tier.backpackName())));
            tasks.add(save(cache, "loot_table/blocks/" + tier.barrelName(), storageLoot(tier.barrelName())));
            tasks.add(save(cache, "loot_table/blocks/" + tier.chestName(), storageLoot(tier.chestName())));
            tasks.add(save(cache, "loot_table/blocks/" + tier.shulkerBoxName(), storageLoot(tier.shulkerBoxName())));
            for (int size = 1; size <= SophisticatedConfig.LIMITED_BARRELS; size++) {
                tasks.add(save(cache, "loot_table/blocks/" + tier.limitedBarrelName(size), storageLoot(tier.limitedBarrelName(size))));
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats sophisticated";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private CompletableFuture<?> saveIn(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, path, json);
    }

    private static JsonArray conditions(String modId, String feature) {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.modLoaded(modId));
        conditions.add(CompatJson.feature(feature));
        return conditions;
    }

    private static JsonObject tag(List<String> values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            JsonObject entry = new JsonObject();
            entry.addProperty("id", value);
            entry.addProperty("required", false);
            array.add(entry);
        }
        JsonObject json = new JsonObject();
        json.add("values", array);
        return json;
    }

    private static List<String> upgradeItems() {
        List<String> items = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            items.add(MOD + ":" + tier.stackUpgradeName());
        }
        items.add(MOD + ":" + SophisticatedIntegration.ARCANE_UPGRADE_ITEM);
        return items;
    }

    private static List<String> axeBlocks() {
        List<String> blocks = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            blocks.add(MOD + ":" + tier.barrelName());
            blocks.add(MOD + ":" + tier.chestName());
            for (int size = 1; size <= SophisticatedConfig.LIMITED_BARRELS; size++) {
                blocks.add(MOD + ":" + tier.limitedBarrelName(size));
            }
        }
        return blocks;
    }

    private static List<String> pickaxeBlocks() {
        List<String> blocks = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            blocks.add(MOD + ":" + tier.shulkerBoxName());
        }
        return blocks;
    }

    private static JsonObject backpackLoot(String name) {
        return loot(name, "sophisticatedbackpacks:copy_backpack_data", false, BACKPACKS);
    }

    private static JsonObject storageLoot(String name) {
        return loot(name, "sophisticatedstorage:copy_storage_data", true, STORAGE);
    }

    private static JsonObject loot(String name, String copyFunction, boolean copyName, String requiredMod) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", MOD + ":" + name);
        JsonArray entries = new JsonArray();
        entries.add(entry);
        JsonArray functions = new JsonArray();
        if (copyName) {
            JsonObject copy = new JsonObject();
            copy.addProperty("function", "minecraft:copy_name");
            copy.addProperty("source", "block_entity");
            functions.add(copy);
        }
        JsonObject data = new JsonObject();
        data.addProperty("function", copyFunction);
        functions.add(data);
        JsonObject pool = new JsonObject();
        pool.addProperty("bonus_rolls", 0.0);
        pool.add("entries", entries);
        pool.add("functions", functions);
        pool.addProperty("name", "main");
        pool.addProperty("rolls", 1.0);
        JsonArray pools = new JsonArray();
        pools.add(pool);
        JsonObject json = new JsonObject();
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.modLoaded(requiredMod));
        json.add("neoforge:conditions", conditions);
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", MOD + ":blocks/" + name);
        return json;
    }

    private static JsonObject stackInfusion(SophisticatedTier tier) {
        SophisticatedTier previous = tier.previous();
        JsonObject catalyst = new JsonObject();
        if (previous == null) {
            catalyst.addProperty("tag", STACK_BASE_TAG);
        } else {
            catalyst.addProperty("item", MOD + ":" + previous.stackUpgradeName());
        }
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item(tier.plate()));
        }
        components.add(ContentJson.item(tier == SophisticatedTier.VOID_METAL ? VOID_SEED : MECHANISM_SIMPLE));
        int scale = tier.ordinal() + 1;
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:vacuos", 24 * scale, "thaumaturge:metallum", 12 * scale, "thaumaturge:machina", 12 * scale, "thaumaturge:ordo", 8 * scale),
                catalyst,
                components,
                3 * scale,
                MOD + ":" + STACK_ENTRY,
                MOD + ":" + tier.stackUpgradeName());
        json.add("neoforge:conditions", conditions(CORE, SophisticatedIntegration.STACK_UPGRADES));
        return json;
    }

    private static JsonObject arcaneRecipe() {
        JsonObject key = new JsonObject();
        key.add("P", ContentJson.item(SophisticatedTier.THAUMIUM.plate()));
        key.add("W", ContentJson.item(ARCANE_WORKBENCH));
        JsonObject upgrade = new JsonObject();
        upgrade.addProperty("tag", CRAFTING_UPGRADE_TAG);
        key.add("U", upgrade);
        JsonArray pattern = ContentJson.strings("PWP", "PUP", "PPP");
        JsonObject json = ContentJson.arcaneShaped(key, pattern, MOD + ":" + ARCANE_ENTRY, 100, MOD + ":" + SophisticatedIntegration.ARCANE_UPGRADE_ITEM);
        JsonArray crystals = new JsonArray();
        for (String primal : PRIMALS) {
            JsonObject crystal = new JsonObject();
            crystal.addProperty("amount", 5);
            crystal.addProperty("aspect", primal);
            crystals.add(crystal);
        }
        json.add("crystals", crystals);
        json.add("neoforge:conditions", conditions(CORE, SophisticatedIntegration.ARCANE_UPGRADE));
        return json;
    }

    private static JsonObject tierShaped(String type, SophisticatedTier tier, String previousItem, String result, String modId, String feature) {
        JsonObject key = new JsonObject();
        key.add("P", ContentJson.item(tier.plate()));
        key.add("B", ContentJson.item(previousItem));
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(modId, feature));
        json.addProperty("type", type);
        json.addProperty("category", "misc");
        json.add("key", key);
        json.add("pattern", ContentJson.strings("PPP", "PBP", "PPP"));
        json.add("result", ContentJson.result(MOD + ":" + result, 1));
        return json;
    }

    private static JsonObject backpackRecipe(SophisticatedTier tier) {
        SophisticatedTier previous = tier.previous();
        String base = previous == null ? BACKPACKS + ":netherite_backpack" : MOD + ":" + previous.backpackName();
        return tierShaped(BACKPACKS + ":backpack_upgrade", tier, base, tier.backpackName(), BACKPACKS, SophisticatedIntegration.BACKPACK_TIERS);
    }

    private static JsonObject storageRecipe(SophisticatedTier tier, String kind, int size) {
        SophisticatedTier previous = tier.previous();
        String base = previous == null ? STORAGE + ":" + netheriteName(kind, size) : MOD + ":" + tierName(previous, kind, size);
        return tierShaped(STORAGE + ":storage_tier_upgrade", tier, base, tierName(tier, kind, size), STORAGE, SophisticatedIntegration.STORAGE_TIERS);
    }

    private static String tierName(SophisticatedTier tier, String kind, int size) {
        return switch (kind) {
            case "barrel" -> tier.barrelName();
            case "limited" -> tier.limitedBarrelName(size);
            case "chest" -> tier.chestName();
            default -> tier.shulkerBoxName();
        };
    }

    private static String netheriteName(String kind, int size) {
        return switch (kind) {
            case "barrel" -> "netherite_barrel";
            case "limited" -> "limited_netherite_barrel_" + size;
            case "chest" -> "netherite_chest";
            default -> "netherite_shulker_box";
        };
    }

    private static JsonObject entry(String name, JsonArray conditions, List<String> parents, int column, String icon, List<String> recipes,
                                    int theory, int warp, JsonArray noteAspects, int complexity) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", column);
        json.addProperty("row", 2);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(icon));

        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", CATEGORY_ARTIFICE);
        knowledge.addProperty("amount", theory);
        JsonArray required = new JsonArray();
        required.add(knowledge);

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        first.add("required_knowledge", required);
        first.addProperty("warp", warp);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(recipes));

        JsonArray stages = new JsonArray();
        stages.add(first);
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }

    private static JsonObject stackEntry() {
        List<String> recipes = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            recipes.add(MOD + ":infusion/" + tier.stackUpgradeName());
        }
        return entry(STACK_ENTRY, conditions(CORE, SophisticatedIntegration.STACK_UPGRADES), List.of(TechCategory.HEADER_ID, INFUSION_ENTRY), STACK_COLUMN,
                MOD + ":" + SophisticatedTier.VOID_METAL.stackUpgradeName(), recipes, 2, 1,
                ContentJson.aspects("thaumaturge:vacuos", 6, "thaumaturge:machina", 5, "thaumaturge:metallum", 4), 2);
    }

    private static JsonObject arcaneEntry() {
        return entry(ARCANE_ENTRY, conditions(CORE, SophisticatedIntegration.ARCANE_UPGRADE), List.of(TechCategory.HEADER_ID), ARCANE_COLUMN,
                MOD + ":" + SophisticatedIntegration.ARCANE_UPGRADE_ITEM, List.of(MOD + ":" + ARCANE_RECIPE), 2, 1,
                ContentJson.aspects("thaumaturge:machina", 5, "thaumaturge:potentia", 4, "thaumaturge:fabrico", 4), 2);
    }

    private static JsonObject backpackEntry() {
        return entry(BACKPACK_ENTRY, conditions(BACKPACKS, SophisticatedIntegration.BACKPACK_TIERS), List.of(TechCategory.HEADER_ID), BACKPACK_COLUMN,
                MOD + ":" + SophisticatedTier.VOID_METAL.backpackName(), List.of(), 2, 1,
                ContentJson.aspects("thaumaturge:vacuos", 5, "thaumaturge:metallum", 4, "thaumaturge:machina", 3), 2);
    }

    private static JsonObject storageEntry() {
        return entry(STORAGE_ENTRY, conditions(STORAGE, SophisticatedIntegration.STORAGE_TIERS), List.of(TechCategory.HEADER_ID), STORAGE_COLUMN,
                MOD + ":" + SophisticatedTier.VOID_METAL.chestName(), List.of(), 2, 1,
                ContentJson.aspects("thaumaturge:vacuos", 5, "thaumaturge:metallum", 4, "thaumaturge:machina", 3), 2);
    }
}
