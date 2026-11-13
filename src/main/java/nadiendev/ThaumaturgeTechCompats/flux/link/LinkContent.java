package nadiendev.ThaumaturgeTechCompats.flux.link;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.nodepower.FluxJarContent;
import nadiendev.ThaumaturgeTechCompats.nodepower.FluxJarTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LinkContent implements DataProvider {
    public static final String EXTRACTOR_ENTRY = "flux_rift_extractor";
    public static final String LINK_ENTRY = "flux_transmission_link";
    public static final String BRASS_ENTRY = "flux_transmission_link_alchemical_brass";
    public static final String VOID_ENTRY = "flux_transmission_link_void_metal";
    public static final int COLUMN = 55;
    public static final int BRANCH_COLUMN = 56;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String RECIPE_FOLDER = "flux/";
    private static final String EXTRACTOR_RECIPE = RECIPE_FOLDER + LinkRegistry.EXTRACTOR_NAME;
    private static final String LINKER_RECIPE = RECIPE_FOLDER + LinkRegistry.LINKER_NAME;
    private static final String PLATE_THAUMIUM = "thaumaturge:plate_thaumium";
    private static final String PLATE_BRASS = "thaumaturge:plate_brass";
    private static final String PLATE_VOID = "thaumaturge:plate_void";
    private static final String MECHANISM_SIMPLE = "thaumaturge:mechanism_simple";
    private static final String MECHANISM_COMPLEX = "thaumaturge:mechanism_complex";
    private static final String QUICKSILVER = "thaumaturge:quicksilver";
    private static final String VOID_SEED = "thaumaturge:void_seed";

    private final PackOutput output;

    public LinkContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            String name = LinkRegistry.linkName(tier);
            tasks.add(save(cache, "loot_table/blocks/" + name, lootTable(name)));
            tasks.add(save(cache, "recipe/" + linkRecipePath(tier), linkRecipe(tier)));
        }
        tasks.add(save(cache, "loot_table/blocks/" + LinkRegistry.EXTRACTOR_NAME, lootTable(LinkRegistry.EXTRACTOR_NAME)));
        tasks.add(save(cache, "recipe/" + EXTRACTOR_RECIPE, extractorRecipe()));
        tasks.add(save(cache, "recipe/" + LINKER_RECIPE, linkerRecipe()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + EXTRACTOR_ENTRY, extractorEntry()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + LINK_ENTRY, linkEntry()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + BRASS_ENTRY, brassEntry()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + VOID_ENTRY, voidEntry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats transmission links";
    }

    public static String linkRecipePath(FluxTier tier) {
        return RECIPE_FOLDER + LinkRegistry.linkName(tier);
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static String id(String name) {
        return MOD + ":" + name;
    }

    private static JsonArray conditions(String... features) {
        JsonArray conditions = new JsonArray();
        for (String feature : features) {
            conditions.add(CompatJson.feature(feature));
        }
        return conditions;
    }

    private static JsonArray tierConditions(FluxTier tier) {
        List<String> features = new ArrayList<>();
        features.add(CompatToggles.NODE_POWER);
        for (FluxTier each : FluxTier.values()) {
            features.add(each.toggle());
            if (each == tier) {
                break;
            }
        }
        return conditions(features.toArray(String[]::new));
    }

    private static JsonObject lootTable(String name) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", id(name));

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
        json.addProperty("random_sequence", MOD + ":blocks/" + name);
        return json;
    }

    private static JsonObject extractorRecipe() {
        JsonArray components = new JsonArray();
        components.add(ContentJson.item(VOID_SEED));
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item(PLATE_VOID));
        }
        components.add(ContentJson.item(MECHANISM_COMPLEX));
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects(
                        "thaumaturge:vacuos", 24,
                        "thaumaturge:potentia", 24,
                        "thaumaturge:alienis", 16,
                        "thaumaturge:praemunio", 8),
                ContentJson.item(id(FluxJarTier.BASE.blockName())),
                components,
                4,
                id(EXTRACTOR_ENTRY),
                id(LinkRegistry.EXTRACTOR_NAME));
        json.add("neoforge:conditions", conditions(FluxTier.THAUMIUM.toggle(), CompatToggles.NODE_POWER));
        return json;
    }

    private static JsonObject linkerRecipe() {
        JsonObject key = new JsonObject();
        key.add("q", ContentJson.item(QUICKSILVER));
        key.add("p", ContentJson.item(PLATE_THAUMIUM));
        key.add("m", ContentJson.item(MECHANISM_SIMPLE));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("q", "p", "m"), id(LINK_ENTRY), 20, id(LinkRegistry.LINKER_NAME));
        json.add("crystals", ContentJson.aspects("thaumaturge:aer", 1));
        json.add("neoforge:conditions", tierConditions(FluxTier.THAUMIUM));
        return json;
    }

    private static JsonObject linkRecipe(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> thaumiumLinkRecipe();
            case ALCHEMICAL_BRASS -> upgradeRecipe(tier, ContentJson.aspects(
                            "thaumaturge:permutatio", 24,
                            "thaumaturge:motus", 24,
                            "thaumaturge:potentia", 24),
                    PLATE_BRASS, MECHANISM_SIMPLE, 3, BRASS_ENTRY);
            case VOID_METAL -> upgradeRecipe(tier, ContentJson.aspects(
                            "thaumaturge:permutatio", 48,
                            "thaumaturge:motus", 48,
                            "thaumaturge:vacuos", 32,
                            "thaumaturge:alienis", 24),
                    PLATE_VOID, MECHANISM_COMPLEX, 4, VOID_ENTRY);
        };
    }

    private static JsonObject thaumiumLinkRecipe() {
        JsonObject key = new JsonObject();
        key.add("q", ContentJson.item(QUICKSILVER));
        key.add("p", ContentJson.item(PLATE_THAUMIUM));
        key.add("m", ContentJson.item(MECHANISM_SIMPLE));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("pqp", "qmq", "ppp"), id(LINK_ENTRY), 40,
                ContentJson.result(id(LinkRegistry.linkName(FluxTier.THAUMIUM)), 2));
        json.add("crystals", ContentJson.aspects("thaumaturge:aer", 2, "thaumaturge:ordo", 1));
        json.add("neoforge:conditions", tierConditions(FluxTier.THAUMIUM));
        return json;
    }

    private static JsonObject upgradeRecipe(FluxTier tier, JsonArray aspects, String plate, String mechanism, int instability, String entry) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item(plate));
        }
        components.add(ContentJson.item(mechanism));
        components.add(ContentJson.item(tier == FluxTier.VOID_METAL ? VOID_SEED : QUICKSILVER));
        JsonObject json = ContentJson.infusion(
                aspects,
                ContentJson.item(id(LinkRegistry.linkName(tier.previous()))),
                components,
                instability,
                id(entry),
                id(LinkRegistry.linkName(tier)));
        json.add("neoforge:conditions", tierConditions(tier));
        return json;
    }

    private static JsonObject extractorEntry() {
        return entry(EXTRACTOR_ENTRY, List.of(id(FluxJarContent.RIFT_ENTRY)), COLUMN, 0,
                id(LinkRegistry.EXTRACTOR_NAME), "thaumaturge:eldritch", 3, 2,
                List.of(id(EXTRACTOR_RECIPE)),
                ContentJson.aspects("thaumaturge:vacuos", 8, "thaumaturge:potentia", 6, "thaumaturge:alienis", 4),
                3, conditions(FluxTier.THAUMIUM.toggle(), CompatToggles.NODE_POWER));
    }

    private static JsonObject linkEntry() {
        return entry(LINK_ENTRY, List.of(id(EXTRACTOR_ENTRY)), COLUMN, 2,
                id(LinkRegistry.linkName(FluxTier.THAUMIUM)), "thaumaturge:artifice", 2, 1,
                List.of(id(linkRecipePath(FluxTier.THAUMIUM)), id(LINKER_RECIPE)),
                ContentJson.aspects("thaumaturge:permutatio", 6, "thaumaturge:motus", 5, "thaumaturge:vinculum", 4),
                2, tierConditions(FluxTier.THAUMIUM));
    }

    private static JsonObject brassEntry() {
        return entry(BRASS_ENTRY, List.of(id(LINK_ENTRY)), BRANCH_COLUMN, 3,
                id(LinkRegistry.linkName(FluxTier.ALCHEMICAL_BRASS)), "thaumaturge:artifice", 2, 1,
                List.of(id(linkRecipePath(FluxTier.ALCHEMICAL_BRASS))),
                ContentJson.aspects("thaumaturge:permutatio", 6, "thaumaturge:potentia", 5, "thaumaturge:metallum", 4),
                2, tierConditions(FluxTier.ALCHEMICAL_BRASS));
    }

    private static JsonObject voidEntry() {
        return entry(VOID_ENTRY, List.of(id(BRASS_ENTRY)), BRANCH_COLUMN, 5,
                id(LinkRegistry.linkName(FluxTier.VOID_METAL)), "thaumaturge:eldritch", 3, 2,
                List.of(id(linkRecipePath(FluxTier.VOID_METAL))),
                ContentJson.aspects("thaumaturge:permutatio", 8, "thaumaturge:vacuos", 6, "thaumaturge:alienis", 4),
                3, tierConditions(FluxTier.VOID_METAL));
    }

    private static JsonObject entry(String name, List<String> parents, int column, int row, String icon, String knowledgeCategory, int knowledgeAmount, int warp, List<String> recipes, JsonArray noteAspects, int complexity, JsonArray conditions) {
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", column);
        json.addProperty("row", row);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(icon));

        JsonArray stages = new JsonArray();

        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + name + ".stage_0");
        JsonObject knowledge = new JsonObject();
        knowledge.addProperty("type", "theory");
        knowledge.addProperty("category", knowledgeCategory);
        knowledge.addProperty("amount", knowledgeAmount);
        JsonArray required = new JsonArray();
        required.add(knowledge);
        first.add("required_knowledge", required);
        first.addProperty("warp", warp);
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        second.add("recipes", ContentJson.strings(recipes));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects);
        json.addProperty("complexity", complexity);
        return json;
    }
}
