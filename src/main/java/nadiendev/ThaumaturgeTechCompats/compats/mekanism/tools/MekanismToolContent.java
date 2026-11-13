package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

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

public class MekanismToolContent implements DataProvider {
    public static final String ENTRY_PREFIX = "mekanismtools_";
    public static final int COLUMN = 33;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String RECIPE_DIR = "compat/mekanism/tools/";

    private final PackOutput output;

    public MekanismToolContent(PackOutput output) {
        this.output = output;
    }

    public static String entryName(MekanismToolTier tier) {
        return ENTRY_PREFIX + tier.id();
    }

    public static int row(MekanismToolTier tier) {
        return 2 + tier.ordinal() * 2;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (MekanismToolTier tier : MekanismToolTier.values()) {
            tasks.add(save(cache, "thaumaturge/research_entry/" + entryName(tier), entry(tier)));
            for (MekanismToolKind kind : MekanismToolKind.values()) {
                tasks.add(save(cache, "recipe/" + RECIPE_DIR + MekanismToolRegistry.itemId(tier, kind), recipe(tier, kind)));
            }
        }
        for (MekanismToolKind kind : MekanismToolKind.values()) {
            tasks.add(ContentJson.save(output, cache, "data/minecraft/tags/item/" + kind.vanillaTag() + ".json", tag(kind)));
        }
        tasks.add(ContentJson.save(output, cache, "data/c/tags/item/tools/mining_tool.json",
                tag(MekanismToolKind.PICKAXE, MekanismToolKind.AXE, MekanismToolKind.SHOVEL, MekanismToolKind.HOE)));
        tasks.add(ContentJson.save(output, cache, "data/c/tags/item/tools/melee_weapon.json", tag(MekanismToolKind.SWORD)));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Mekanism energy tools";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions(MekanismToolTier tier) {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.modLoaded(MekanismToolIntegration.MOD_ID));
        conditions.add(CompatJson.feature(MekanismToolIntegration.TOGGLE));
        for (MekanismToolTier current = tier; current != null; current = current.previous()) {
            conditions.add(CompatJson.feature(current.toggle()));
        }
        return conditions;
    }

    private static JsonObject tag(MekanismToolKind... kinds) {
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        JsonArray values = new JsonArray();
        for (MekanismToolTier tier : MekanismToolTier.values()) {
            for (MekanismToolKind kind : kinds) {
                JsonObject value = new JsonObject();
                value.addProperty("id", MOD + ":" + MekanismToolRegistry.itemId(tier, kind));
                value.addProperty("required", false);
                values.add(value);
            }
        }
        json.add("values", values);
        return json;
    }

    private static JsonObject recipe(MekanismToolTier tier, MekanismToolKind kind) {
        JsonArray components = new JsonArray();
        components.add(ContentJson.item("mekanism:energy_tablet"));
        components.add(ContentJson.item("mekanism:energy_tablet"));
        components.add(ContentJson.item(tier.alloy()));
        components.add(ContentJson.item(tier.circuit()));
        components.add(ContentJson.item(tier.plate()));
        components.add(ContentJson.item(tier.plate()));
        MekanismToolTier previous = tier.previous();
        if (previous != null) {
            components.add(ContentJson.item(MOD + ":" + MekanismToolRegistry.itemId(previous, kind)));
        }
        JsonObject json = ContentJson.infusion(
                aspects(tier),
                ContentJson.item(tier.baseTool(kind)),
                components,
                1 + tier.ordinal() * 2,
                MOD + ":" + entryName(tier),
                MOD + ":" + MekanismToolRegistry.itemId(tier, kind));
        json.add("neoforge:conditions", conditions(tier));
        return json;
    }

    private static JsonArray aspects(MekanismToolTier tier) {
        return switch (tier) {
            case THAUMIUM -> ContentJson.aspects("thaumaturge:machina", 15, "thaumaturge:potentia", 15, "thaumaturge:metallum", 10, "thaumaturge:instrumentum", 10);
            case BRASS -> ContentJson.aspects("thaumaturge:machina", 30, "thaumaturge:potentia", 30, "thaumaturge:metallum", 20, "thaumaturge:instrumentum", 20);
            case VOID -> ContentJson.aspects("thaumaturge:machina", 50, "thaumaturge:potentia", 50, "thaumaturge:vacuos", 30, "thaumaturge:instrumentum", 30, "thaumaturge:praecantatio", 20);
        };
    }

    private static JsonArray noteAspects(MekanismToolTier tier) {
        return switch (tier) {
            case THAUMIUM -> ContentJson.aspects("thaumaturge:machina", 4, "thaumaturge:potentia", 4, "thaumaturge:instrumentum", 3);
            case BRASS -> ContentJson.aspects("thaumaturge:machina", 5, "thaumaturge:potentia", 5, "thaumaturge:instrumentum", 4);
            case VOID -> ContentJson.aspects("thaumaturge:machina", 6, "thaumaturge:potentia", 6, "thaumaturge:vacuos", 5, "thaumaturge:instrumentum", 5);
        };
    }

    private static List<String> parents(MekanismToolTier tier) {
        return switch (tier) {
            case THAUMIUM -> List.of(TechCategory.HEADER_ID, "thaumaturge:base_infusion", "thaumaturge:metallurgy@2");
            case BRASS -> List.of(MOD + ":" + entryName(MekanismToolTier.THAUMIUM), "thaumaturge:metallurgy@1");
            case VOID -> List.of(MOD + ":" + entryName(MekanismToolTier.BRASS), "thaumaturge:infusion_eldritch");
        };
    }

    private static String knowledgeCategory(MekanismToolTier tier) {
        return switch (tier) {
            case BRASS, THAUMIUM -> "thaumaturge:artifice";
            case VOID -> "thaumaturge:eldritch";
        };
    }

    private static JsonObject entry(MekanismToolTier tier) {
        String name = entryName(tier);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(tier));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + name + ".title");
        json.add("parents", ContentJson.strings(parents(tier)));
        json.addProperty("column", COLUMN);
        json.addProperty("row", row(tier));
        json.add("meta", ContentJson.strings("round", "hidden"));
        List<String> icons = new ArrayList<>();
        for (MekanismToolKind kind : MekanismToolKind.values()) {
            icons.add(MOD + ":" + MekanismToolRegistry.itemId(tier, kind));
        }
        json.add("icons", ContentJson.strings(icons));

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
        first.addProperty("warp", Math.max(0, tier.ordinal() - 1));
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + name + ".stage_1");
        List<String> recipes = new ArrayList<>();
        for (MekanismToolKind kind : MekanismToolKind.values()) {
            recipes.add(MOD + ":" + RECIPE_DIR + MekanismToolRegistry.itemId(tier, kind));
        }
        second.add("recipes", ContentJson.strings(recipes));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", noteAspects(tier));
        json.addProperty("complexity", 1 + tier.ordinal());
        return json;
    }
}
