package nadiendev.ThaumaturgeTechCompats.creative;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.ASPECT_TERMINUS;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.aspects;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.item;

public class CreativeContent implements DataProvider {
    public static final String RESEARCH = "avaritia_creative";
    public static final String NODE_PLACER_RECIPE = "creative_node_placer";
    public static final String CHEAT_BOOK_RECIPE = "thaumonomicon_cheat";
    private static final String PEARL = MOD + ":extremely_primordial_pearl";

    private final PackOutput output;

    public CreativeContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "recipe/infusion/" + NODE_PLACER_RECIPE, nodePlacer()));
        tasks.add(save(cache, "recipe/infusion/" + CHEAT_BOOK_RECIPE, cheatBook()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + RESEARCH, research()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats creative items";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static void add(JsonArray components, String id, int count) {
        for (int i = 0; i < count; i++) {
            components.add(item(id));
        }
    }

    private static JsonObject nodePlacer() {
        JsonArray components = new JsonArray();
        add(components, MOD + ":infinity_plate", 2);
        add(components, MOD + ":neutronium_plate", 2);
        add(components, "avaritia:neutron_ingot", 2);
        add(components, "thaumaturge:primordial_pearl", 2);
        return ContentJson.infusion(
                aspects(
                        "thaumaturge:auram", 128,
                        "thaumaturge:alienis", 96,
                        "thaumaturge:praecantatio", 96,
                        "thaumaturge:vinculum", 64,
                        ASPECT_TERMINUS, 48),
                item("thaumaturge:jar_node"),
                components,
                12,
                MOD + ":" + RESEARCH,
                "thaumaturge:creative_node_placer");
    }

    private static JsonObject cheatBook() {
        JsonArray components = new JsonArray();
        add(components, MOD + ":akashic_record", 1);
        add(components, PEARL, 1);
        add(components, MOD + ":infinity_plate", 2);
        add(components, "avaritia:infinity_ingot", 2);
        return ContentJson.infusion(
                aspects(
                        "thaumaturge:cognitio", 128,
                        "thaumaturge:praecantatio", 96,
                        "thaumaturge:alienis", 96,
                        "thaumaturge:desiderium", 64,
                        ASPECT_TERMINUS, 64),
                item("thaumaturge:thaumonomicon"),
                components,
                12,
                MOD + ":" + RESEARCH,
                "thaumaturge:thaumonomicon_cheat");
    }

    private static JsonObject research() {
        return ContentJson.research(
                RESEARCH,
                List.of(MOD + ":akashic_record", "thaumaturge:nodes"),
                8, 2,
                "thaumaturge:thaumonomicon_cheat",
                List.of(
                        MOD + ":infusion/" + NODE_PLACER_RECIPE,
                        MOD + ":infusion/" + CHEAT_BOOK_RECIPE),
                3,
                aspects("thaumaturge:cognitio", 8, "thaumaturge:auram", 6, ASPECT_TERMINUS, 6),
                3);
    }
}
