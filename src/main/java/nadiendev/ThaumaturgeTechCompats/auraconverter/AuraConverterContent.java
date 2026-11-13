package nadiendev.ThaumaturgeTechCompats.auraconverter;

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

public class AuraConverterContent implements DataProvider {
    public static final int COLUMN = 27;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public AuraConverterContent(PackOutput output) {
        this.output = output;
    }

    public static String research(AuraConverterTier tier) {
        return "aura_converter_" + tier.name().toLowerCase();
    }

    public static String recipeFolder(AuraConverterTier tier) {
        return tier == AuraConverterTier.THAUMIUM ? "arcane_workbench" : "infusion";
    }

    public static String recipeId(AuraConverterTier tier) {
        return recipeFolder(tier) + "/" + tier.blockName();
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (AuraConverterTier tier : AuraConverterTier.values()) {
            tasks.add(save(cache, "thaumaturge/research_entry/" + research(tier), entry(tier)));
            tasks.add(save(cache, "recipe/" + recipeId(tier), recipe(tier)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats aura converters";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions(AuraConverterTier tier) {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.feature(CompatToggles.AURA_CONVERTER));
        for (AuraConverterTier step : AuraConverterTier.values()) {
            if (step.ordinal() <= tier.ordinal()) {
                conditions.add(CompatJson.feature(step.toggle()));
            }
        }
        return conditions;
    }

    private static String itemId(AuraConverterTier tier) {
        return MOD + ":" + tier.blockName();
    }

    private static JsonObject entry(AuraConverterTier tier) {
        List<String> parents = switch (tier) {
            case THAUMIUM -> List.of(TechCategory.HEADER_ID, "thaumaturge:vis_generator", "thaumaturge:metallurgy@2");
            case BRASS -> List.of(MOD + ":" + research(AuraConverterTier.THAUMIUM), "thaumaturge:base_artifice", "thaumaturge:metallurgy@1");
            case VOID -> List.of(MOD + ":" + research(AuraConverterTier.BRASS), "thaumaturge:base_artifice", "thaumaturge:base_eldritch");
        };
        String knowledge = tier == AuraConverterTier.VOID ? "thaumaturge:eldritch" : "thaumaturge:artifice";
        int theory = tier == AuraConverterTier.THAUMIUM ? 1 : 2;
        int complexity = tier.ordinal() + 1;
        JsonArray notes = switch (tier) {
            case THAUMIUM -> ContentJson.aspects("thaumaturge:auram", 6, "thaumaturge:potentia", 5, "thaumaturge:machina", 4);
            case BRASS -> ContentJson.aspects("thaumaturge:auram", 8, "thaumaturge:potentia", 8, "thaumaturge:machina", 6);
            case VOID -> ContentJson.aspects("thaumaturge:auram", 10, "thaumaturge:potentia", 10, "thaumaturge:vacuos", 8, "thaumaturge:machina", 8);
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
        required.addProperty("amount", theory);
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
        json.addProperty("complexity", complexity);
        return json;
    }

    private static JsonObject recipe(AuraConverterTier tier) {
        JsonObject json = switch (tier) {
            case THAUMIUM -> thaumium();
            case BRASS -> infusion(tier, ContentJson.aspects("thaumaturge:potentia", 40, "thaumaturge:machina", 30, "thaumaturge:auram", 20),
                    3, "thaumaturge:plate_brass", 4, "thaumaturge:mechanism_simple", 1, 1);
            case VOID -> infusion(tier, ContentJson.aspects("thaumaturge:potentia", 80, "thaumaturge:machina", 40, "thaumaturge:auram", 40, "thaumaturge:vacuos", 40),
                    4, "thaumaturge:plate_void", 4, "thaumaturge:mechanism_complex", 1, 2);
        };
        json.add("neoforge:conditions", conditions(tier));
        return json;
    }

    private static JsonObject thaumium() {
        JsonObject key = new JsonObject();
        key.add("B", ContentJson.item("thaumaturge:plate_thaumium"));
        key.add("S", tag("c:dusts/redstone"));
        key.add("C", tag("c:ingots/copper"));
        key.add("V", ContentJson.item("thaumaturge:vis_resonator"));
        key.add("M", ContentJson.item("thaumaturge:mechanism_simple"));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("BSB", "CVC", "BMB"),
                MOD + ":" + research(AuraConverterTier.THAUMIUM), 50, itemId(AuraConverterTier.THAUMIUM));
        json.add("crystals", ContentJson.aspects("thaumaturge:ignis", 2, "thaumaturge:ordo", 1));
        return json;
    }

    private static JsonObject infusion(AuraConverterTier tier, JsonArray aspects, int instability, String plate, int plates, String mechanism, int mechanisms, int resonators) {
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

    private static JsonObject tag(String id) {
        JsonObject json = new JsonObject();
        json.addProperty("tag", id);
        return json;
    }
}
