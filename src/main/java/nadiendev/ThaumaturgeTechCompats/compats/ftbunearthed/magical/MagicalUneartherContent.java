package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.FtbUnearthedIntegration;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MagicalUneartherContent implements DataProvider {
    public static final int COLUMN = 30;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String RECIPE_DIR = "compat/ftbunearthed/magical/";
    private static final String BASE_CORE = FtbUnearthedIntegration.MOD_ID + ":core";

    private final PackOutput output;

    public MagicalUneartherContent(PackOutput output) {
        this.output = output;
    }

    public static int row(MagicalUneartherTier tier) {
        return 2 + tier.ordinal() * 2;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (MagicalUneartherTier tier : MagicalUneartherTier.values()) {
            tasks.add(save(cache, "loot_table/blocks/" + tier.blockName(), lootTable(tier)));
            tasks.add(save(cache, "recipe/" + RECIPE_DIR + tier.blockName(), recipe(tier)));
            tasks.add(save(cache, "thaumaturge/research_entry/" + tier.entryName(), entry(tier)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats magical unearthers";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions(MagicalUneartherTier tier) {
        JsonArray conditions = CompatJson.conditions(FtbUnearthedIntegration.MOD_ID);
        conditions.add(CompatJson.feature(CompatToggles.NODE_POWER));
        for (MagicalUneartherTier current = tier; current != null; current = current.previous()) {
            conditions.add(CompatJson.feature(current.toggle()));
        }
        return conditions;
    }

    private static JsonObject lootTable(MagicalUneartherTier tier) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", MOD + ":" + tier.blockName());

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
        json.addProperty("random_sequence", MOD + ":blocks/" + tier.blockName());
        return json;
    }

    private static JsonObject recipe(MagicalUneartherTier tier) {
        MagicalUneartherTier previous = tier.previous();
        JsonObject key = new JsonObject();
        key.add("P", ContentJson.item(tier.plate()));
        key.add("M", ContentJson.item(tier.mechanism()));
        key.add("U", ContentJson.item(previous == null ? BASE_CORE : MOD + ":" + previous.blockName()));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("PMP", " U ", "PMP"),
                MOD + ":" + tier.entryName(), tier.vis(), MOD + ":" + tier.blockName());
        json.add("neoforge:conditions", conditions(tier));
        return json;
    }

    private static JsonArray noteAspects(MagicalUneartherTier tier) {
        return switch (tier) {
            case THAUMIUM -> ContentJson.aspects("thaumaturge:machina", 5, "thaumaturge:terra", 4, "thaumaturge:praecantatio", 3);
            case BRASS -> ContentJson.aspects("thaumaturge:machina", 7, "thaumaturge:terra", 5, "thaumaturge:potentia", 5);
            case VOID -> ContentJson.aspects("thaumaturge:machina", 9, "thaumaturge:vacuos", 7, "thaumaturge:potentia", 6);
        };
    }

    private static List<String> parents(MagicalUneartherTier tier) {
        return switch (tier) {
            case THAUMIUM -> List.of(TechCategory.HEADER_ID, "thaumaturge:base_artifice", "thaumaturge:metallurgy@1");
            case BRASS -> List.of(MOD + ":" + MagicalUneartherTier.THAUMIUM.entryName(), "thaumaturge:base_artifice", "thaumaturge:metallurgy@2");
            case VOID -> List.of(MOD + ":" + MagicalUneartherTier.BRASS.entryName(), "thaumaturge:base_artifice", "thaumaturge:base_eldritch");
        };
    }

    private static String knowledgeCategory(MagicalUneartherTier tier) {
        return switch (tier) {
            case THAUMIUM -> "thaumaturge:alchemy";
            case BRASS -> "thaumaturge:artifice";
            case VOID -> "thaumaturge:eldritch";
        };
    }

    private static JsonObject entry(MagicalUneartherTier tier) {
        String name = tier.entryName();
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(tier));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents(tier)));
        json.addProperty("column", COLUMN);
        json.addProperty("row", row(tier));
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(MOD + ":" + tier.blockName()));

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
        second.add("recipes", ContentJson.strings(MOD + ":" + RECIPE_DIR + tier.blockName()));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects(tier));
        json.addProperty("complexity", 1 + tier.ordinal());
        return json;
    }
}
