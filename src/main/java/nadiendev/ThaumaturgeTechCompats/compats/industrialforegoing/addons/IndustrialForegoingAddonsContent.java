package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IndustrialForegoingAddonsContent implements DataProvider {
    public static final String ENTRY_PREFIX = "industrialforegoing_addons_";
    public static final int COLUMN = 3;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public IndustrialForegoingAddonsContent(PackOutput output) {
        this.output = output;
    }

    public static String entryName(AddonTier tier) {
        return ENTRY_PREFIX + tier.id();
    }

    public static int row(AddonTier tier) {
        return 2 + tier.ordinal() * 2;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (AddonTier tier : AddonTier.values()) {
            tasks.add(save(cache, "thaumaturge/research_entry/" + entryName(tier), entry(tier)));
            for (AddonKind kind : AddonKind.values()) {
                tasks.add(save(cache, "recipe/compat/industrialforegoing/addons/" + IndustrialForegoingAddonsIntegration.itemId(kind, tier), recipe(kind, tier)));
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Industrial Foregoing addons";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray baseConditions(String... features) {
        JsonArray conditions = new JsonArray();
        JsonObject loaded = new JsonObject();
        loaded.addProperty("type", "neoforge:mod_loaded");
        loaded.addProperty("modid", IndustrialForegoingAddonsIntegration.MOD_ID);
        conditions.add(loaded);
        conditions.add(CompatJson.feature(IndustrialForegoingAddonsIntegration.TOGGLE));
        for (String feature : features) {
            conditions.add(CompatJson.feature(feature));
        }
        return conditions;
    }

    private static JsonArray entryConditions(AddonTier tier) {
        JsonArray conditions = baseConditions();
        JsonArray values = new JsonArray();
        for (AddonKind kind : AddonKind.values()) {
            values.add(CompatJson.feature(IndustrialForegoingAddonsIntegration.toggleId(kind, tier)));
        }
        JsonObject any = new JsonObject();
        any.addProperty("type", "neoforge:or");
        any.add("values", values);
        conditions.add(any);
        return conditions;
    }

    private static JsonObject ingredient(String item) {
        return ContentJson.item(item);
    }

    private static List<String> materials(AddonKind kind) {
        return switch (kind) {
            case SPEED -> List.of("minecraft:sugar", "minecraft:sugar");
            case EFFICIENCY -> List.of("minecraft:blaze_rod", "minecraft:blaze_rod");
            case PROCESSING -> List.of("minecraft:furnace", "minecraft:crafting_table");
            case RANGE -> List.of("minecraft:ender_pearl", "minecraft:ender_pearl");
        };
    }

    private static List<String> inputs(AddonKind kind, AddonTier tier) {
        List<String> inputs = new ArrayList<>();
        AddonTier previous = tier.previous();
        if (previous != null) {
            inputs.add(MOD + ":" + IndustrialForegoingAddonsIntegration.itemId(kind, previous));
        }
        inputs.add(tier.plate());
        inputs.add(tier.plate());
        inputs.add(tier.plate());
        switch (tier) {
            case BRASS -> {
                inputs.add("minecraft:redstone");
                inputs.add("minecraft:redstone");
                inputs.add("minecraft:glass_pane");
            }
            case THAUMIUM -> inputs.add("thaumaturge:mechanism_simple");
            case VOID -> inputs.add("thaumaturge:mechanism_complex");
        }
        inputs.addAll(materials(kind));
        if (tier == AddonTier.THAUMIUM) {
            inputs.add("minecraft:diamond");
        }
        if (tier == AddonTier.VOID) {
            inputs.add("minecraft:ender_eye");
        }
        return inputs;
    }

    private static JsonObject recipe(AddonKind kind, AddonTier tier) {
        List<String> features = new ArrayList<>();
        features.add(IndustrialForegoingAddonsIntegration.toggleId(kind, tier));
        AddonTier previous = tier.previous();
        if (previous != null) {
            features.add(IndustrialForegoingAddonsIntegration.toggleId(kind, previous));
        }
        JsonObject json = CompatJson.recipe("industrialforegoing:dissolution_chamber", baseConditions(features.toArray(String[]::new)));
        JsonArray input = new JsonArray();
        for (String item : inputs(kind, tier)) {
            input.add(ingredient(item));
        }
        json.add("input", input);
        JsonObject fluid = new JsonObject();
        fluid.addProperty("amount", tier.deathAmount());
        fluid.addProperty("fluid", IndustrialForegoingAddonsIntegration.LIQUID_DEATH);
        json.add("inputFluid", fluid);
        json.add("output", CompatJson.result(MOD + ":" + IndustrialForegoingAddonsIntegration.itemId(kind, tier), 1));
        json.addProperty("processingTime", tier.processingTime());
        return json;
    }

    private static JsonArray noteAspects(AddonTier tier) {
        return switch (tier) {
            case BRASS -> ContentJson.aspects("thaumaturge:machina", 4, "thaumaturge:metallum", 4, "thaumaturge:mortuus", 4);
            case THAUMIUM -> ContentJson.aspects("thaumaturge:machina", 6, "thaumaturge:potentia", 6, "thaumaturge:mortuus", 5);
            case VOID -> ContentJson.aspects("thaumaturge:machina", 8, "thaumaturge:vacuos", 8, "thaumaturge:mortuus", 6);
        };
    }

    private static List<String> parents(AddonTier tier) {
        return switch (tier) {
            case BRASS -> List.of(TechCategory.HEADER_ID, "thaumaturge:liquid_death", "thaumaturge:metallurgy@1");
            case THAUMIUM -> List.of(MOD + ":" + entryName(AddonTier.BRASS), "thaumaturge:base_artifice", "thaumaturge:metallurgy@2");
            case VOID -> List.of(MOD + ":" + entryName(AddonTier.THAUMIUM), "thaumaturge:base_artifice", "thaumaturge:base_eldritch");
        };
    }

    private static String knowledgeCategory(AddonTier tier) {
        return switch (tier) {
            case BRASS -> "thaumaturge:alchemy";
            case THAUMIUM -> "thaumaturge:artifice";
            case VOID -> "thaumaturge:eldritch";
        };
    }

    private static JsonObject entry(AddonTier tier) {
        String name = entryName(tier);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", entryConditions(tier));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents(tier)));
        json.addProperty("column", COLUMN);
        json.addProperty("row", row(tier));
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(MOD + ":" + IndustrialForegoingAddonsIntegration.itemId(AddonKind.SPEED, tier)));

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
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects(tier));
        json.addProperty("complexity", 1 + tier.ordinal());
        return json;
    }
}
