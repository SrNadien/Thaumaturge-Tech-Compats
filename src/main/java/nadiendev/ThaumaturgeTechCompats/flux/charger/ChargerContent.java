package nadiendev.ThaumaturgeTechCompats.flux.charger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChargerContent implements DataProvider {
    public static final int COLUMN = 57;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public ChargerContent(PackOutput output) {
        this.output = output;
    }

    public static String research(FluxTier tier) {
        return "flux_charger_" + tier.id();
    }

    public static String recipeId(FluxTier tier) {
        return (tier == FluxTier.THAUMIUM ? "arcane_workbench/" : "infusion/") + ChargerRegistry.blockName(tier);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            tasks.add(save(cache, "loot_table/blocks/" + ChargerRegistry.blockName(tier), lootTable(tier)));
            tasks.add(save(cache, "recipe/" + recipeId(tier), recipe(tier)));
            tasks.add(save(cache, "thaumaturge/research_entry/" + research(tier), entry(tier)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats environmental chargers";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static String itemId(FluxTier tier) {
        return MOD + ":" + ChargerRegistry.blockName(tier);
    }

    private static JsonArray conditions(FluxTier tier) {
        JsonArray conditions = new JsonArray();
        for (FluxTier step : FluxTier.values()) {
            if (step.ordinal() <= tier.ordinal()) {
                conditions.add(CompatJson.feature(step.toggle()));
            }
        }
        return conditions;
    }

    private static JsonObject lootTable(FluxTier tier) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", itemId(tier));

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
        json.addProperty("random_sequence", MOD + ":blocks/" + ChargerRegistry.blockName(tier));
        return json;
    }

    private static JsonObject recipe(FluxTier tier) {
        JsonObject json = switch (tier) {
            case THAUMIUM -> thaumium();
            case ALCHEMICAL_BRASS -> infusion(tier, ContentJson.aspects("thaumaturge:potentia", 40, "thaumaturge:auram", 30, "thaumaturge:machina", 20),
                    3, "thaumaturge:plate_brass", 4, "thaumaturge:mechanism_simple", 1, 1);
            case VOID_METAL -> infusion(tier, ContentJson.aspects("thaumaturge:potentia", 80, "thaumaturge:auram", 60, "thaumaturge:machina", 40, "thaumaturge:vacuos", 40),
                    5, "thaumaturge:plate_void", 4, "thaumaturge:mechanism_complex", 1, 2);
        };
        json.add("neoforge:conditions", conditions(tier));
        return json;
    }

    private static JsonObject thaumium() {
        JsonObject key = new JsonObject();
        key.add("P", ContentJson.item("thaumaturge:plate_thaumium"));
        key.add("R", ContentJson.item("thaumaturge:vis_resonator"));
        key.add("M", ContentJson.item("thaumaturge:mechanism_simple"));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("PRP", "PMP", "PPP"),
                MOD + ":" + research(FluxTier.THAUMIUM), 60, itemId(FluxTier.THAUMIUM));
        json.add("crystals", ContentJson.aspects("thaumaturge:aer", 2, "thaumaturge:ordo", 2));
        return json;
    }

    private static JsonObject infusion(FluxTier tier, JsonArray aspects, int instability, String plate, int plates, String mechanism, int mechanisms, int resonators) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < plates; i++) {
            components.add(ContentJson.item(plate));
        }
        for (int i = 0; i < mechanisms; i++) {
            components.add(ContentJson.item(mechanism));
        }
        for (int i = 0; i < resonators; i++) {
            components.add(ContentJson.item("thaumaturge:vis_resonator"));
        }
        return ContentJson.infusion(
                aspects,
                ContentJson.item(itemId(tier.previous())),
                components,
                instability,
                MOD + ":" + research(tier),
                itemId(tier));
    }

    private static JsonObject entry(FluxTier tier) {
        List<String> parents = switch (tier) {
            case THAUMIUM -> List.of(TechCategory.HEADER_ID, "thaumaturge:recharge_pedestal", "thaumaturge:metallurgy@2");
            case ALCHEMICAL_BRASS -> List.of(MOD + ":" + research(FluxTier.THAUMIUM), "thaumaturge:base_infusion", "thaumaturge:metallurgy@1");
            case VOID_METAL -> List.of(MOD + ":" + research(FluxTier.ALCHEMICAL_BRASS), "thaumaturge:base_eldritch");
        };
        String knowledge = tier == FluxTier.VOID_METAL ? "thaumaturge:eldritch" : "thaumaturge:artifice";
        JsonArray notes = switch (tier) {
            case THAUMIUM -> ContentJson.aspects("thaumaturge:auram", 6, "thaumaturge:potentia", 5, "thaumaturge:machina", 4);
            case ALCHEMICAL_BRASS -> ContentJson.aspects("thaumaturge:auram", 8, "thaumaturge:potentia", 8, "thaumaturge:machina", 6);
            case VOID_METAL -> ContentJson.aspects("thaumaturge:auram", 10, "thaumaturge:potentia", 10, "thaumaturge:vacuos", 8, "thaumaturge:machina", 8);
        };

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(tier));
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.addProperty("name", "research." + MOD + "." + research(tier) + ".title");
        json.add("parents", ContentJson.strings(parents));
        json.addProperty("column", COLUMN);
        json.addProperty("row", 2 + tier.ordinal() * 2);
        json.add("meta", ContentJson.strings("round", "hidden"));
        json.add("icons", ContentJson.strings(itemId(tier)));

        JsonArray stages = new JsonArray();
        JsonObject first = new JsonObject();
        first.addProperty("text", "research." + MOD + "." + research(tier) + ".stage_0");
        JsonObject required = new JsonObject();
        required.addProperty("type", "theory");
        required.addProperty("category", knowledge);
        required.addProperty("amount", tier == FluxTier.THAUMIUM ? 1 : 2);
        JsonArray requirements = new JsonArray();
        requirements.add(required);
        first.add("required_knowledge", requirements);
        first.addProperty("warp", tier.ordinal());
        stages.add(first);

        JsonObject second = new JsonObject();
        second.addProperty("text", "research." + MOD + "." + research(tier) + ".stage_1");
        second.add("recipes", ContentJson.strings(MOD + ":" + recipeId(tier)));
        stages.add(second);

        json.add("stages", stages);
        json.add("note_aspects", notes);
        json.addProperty("complexity", tier.ordinal() + 1);
        return json;
    }
}
