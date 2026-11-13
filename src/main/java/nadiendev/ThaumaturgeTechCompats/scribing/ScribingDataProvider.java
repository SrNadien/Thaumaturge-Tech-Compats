package nadiendev.ThaumaturgeTechCompats.scribing;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.jar.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static nadiendev.ThaumaturgeTechCompats.jar.ContentJson.ASPECT_TERMINUS;
import static nadiendev.ThaumaturgeTechCompats.jar.ContentJson.MOD;
import static nadiendev.ThaumaturgeTechCompats.jar.ContentJson.aspects;
import static nadiendev.ThaumaturgeTechCompats.jar.ContentJson.item;

public class ScribingDataProvider implements DataProvider {
    public static final String RESEARCH_ENTRY = "avaritia_scribing";
    private static final String PEARL = MOD + ":extremely_primordial_pearl";
    private static final String BASE_TOOLS = "thaumaturge:scribing_tools";

    private final PackOutput output;

    public ScribingDataProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (ScribingTier tier : ScribingTier.values()) {
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + tier.toolsName() + ".json", infusion(tier)));
            if (hasRefill(tier)) {
                tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/" + tier.toolsName() + "_refill.json",
                        ContentJson.refillRecipe(id(tier), id(tier))));
            }
        }
        tasks.add(ContentJson.save(output, cache, "data/thaumaturge/tags/item/scribing_tools.json", scribingToolsTag()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + RESEARCH_ENTRY + ".json", research()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats scribing tools";
    }

    private static String id(ScribingTier tier) {
        return MOD + ":" + tier.toolsName();
    }

    private static boolean hasRefill(ScribingTier tier) {
        return tier != ScribingTier.INFINITY;
    }

    private static JsonObject scribingToolsTag() {
        JsonArray values = new JsonArray();
        for (ScribingTier tier : ScribingTier.values()) {
            values.add(id(tier));
        }
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        json.add("values", values);
        return json;
    }

    private static JsonObject infusion(ScribingTier tier) {
        JsonArray components = new JsonArray();
        JsonArray aspects;
        int instability;
        String catalyst = BASE_TOOLS;
        switch (tier) {
            case MATRIX -> {
                addAll(components, "avaritia:crystal_matrix_ingot", 4);
                aspects = aspects(
                        "thaumaturge:cognitio", 16,
                        "thaumaturge:instrumentum", 16,
                        "thaumaturge:potentia", 8,
                        ASPECT_TERMINUS, 4);
                instability = 2;
            }
            case NEUTRONIUM -> {
                catalyst = id(ScribingTier.MATRIX);
                addAll(components, "avaritia:neutron_ingot", 4);
                aspects = aspects(
                        "thaumaturge:cognitio", 32,
                        "thaumaturge:metallum", 24,
                        "thaumaturge:instrumentum", 24,
                        ASPECT_TERMINUS, 8);
                instability = 5;
            }
            default -> {
                catalyst = id(ScribingTier.NEUTRONIUM);
                addAll(components, "avaritia:infinity_ingot", 4);
                components.add(item(PEARL));
                aspects = aspects(
                        "thaumaturge:cognitio", 48,
                        "thaumaturge:alienis", 32,
                        "thaumaturge:instrumentum", 32,
                        ASPECT_TERMINUS, 24);
                instability = 8;
            }
        }
        return ContentJson.infusion(aspects, item(catalyst), components, instability, MOD + ":" + RESEARCH_ENTRY, id(tier));
    }

    private static void addAll(JsonArray components, String item, int count) {
        for (int i = 0; i < count; i++) {
            components.add(item(item));
        }
    }

    private static JsonObject research() {
        return ContentJson.research(
                RESEARCH_ENTRY,
                new String[]{"thaumaturge:research", PEARL},
                2, 2,
                new String[]{id(ScribingTier.MATRIX), id(ScribingTier.NEUTRONIUM), id(ScribingTier.INFINITY)},
                new String[]{
                        MOD + ":infusion/" + ScribingTier.MATRIX.toolsName(),
                        MOD + ":infusion/" + ScribingTier.NEUTRONIUM.toolsName(),
                        MOD + ":infusion/" + ScribingTier.INFINITY.toolsName(),
                        MOD + ":" + ScribingTier.MATRIX.toolsName() + "_refill",
                        MOD + ":" + ScribingTier.NEUTRONIUM.toolsName() + "_refill"},
                2,
                aspects("thaumaturge:cognitio", 6, "thaumaturge:instrumentum", 4, ASPECT_TERMINUS, 3),
                2);
    }
}
