package nadiendev.ThaumaturgeTechCompats.focus.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import nadiendev.ThaumaturgeTechCompats.tiercontent.Tier;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FocusContentProvider implements DataProvider {
    public static final String RESEARCH = "avaritia_foci";

    private static final String MOD = ContentJson.MOD;
    private static final String MATRIX_FOCUS = MOD + ":matrix_focus";
    private static final String INFINITY_FOCUS = MOD + ":infinity_focus";
    private static final String BLAZE_CUBE_FOCUS = MOD + ":blaze_cube_focus";
    private static final String MATRIX_PLATE = MOD + ":" + Tier.MATRIX.plateName();
    private static final String NEUTRONIUM_PLATE = MOD + ":" + Tier.NEUTRONIUM.plateName();
    private static final String INFINITY_PLATE = MOD + ":" + Tier.INFINITY.plateName();
    private static final String RESEARCH_ENTRY = MOD + ":" + RESEARCH;

    private final PackOutput output;

    public FocusContentProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "data/" + MOD + "/recipe/infusion/matrix_focus.json", matrixFocus()));
        tasks.add(save(cache, "data/" + MOD + "/recipe/infusion/infinity_focus.json", infinityFocus()));
        tasks.add(save(cache, "data/" + MOD + "/recipe/infusion/blaze_cube_focus.json", blazeCubeFocus()));
        tasks.add(save(cache, "data/" + MOD + "/thaumaturge/research_entry/" + RESEARCH + ".json", research()));
        tasks.add(save(cache, "data/thaumaturge/data_maps/item/spell_focus.json", focusTiers()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats focus content";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, path, json);
    }

    private static JsonArray items(String... ids) {
        JsonArray array = new JsonArray();
        for (String id : ids) {
            array.add(ContentJson.item(id));
        }
        return array;
    }

    private static JsonObject matrixFocus() {
        return ContentJson.infusion(
                ContentJson.aspects(
                        "thaumaturge:praecantatio", 50,
                        "thaumaturge:ordo", 100,
                        "thaumaturge:vacuos", 100,
                        ContentJson.ASPECT_TERMINUS, 16),
                ContentJson.item("thaumaturge:focus_3"),
                items(Tier.MATRIX.ingot(), MATRIX_PLATE, Tier.MATRIX.ingot(), MATRIX_PLATE),
                8,
                RESEARCH_ENTRY,
                MATRIX_FOCUS);
    }

    private static JsonObject infinityFocus() {
        return ContentJson.infusion(
                ContentJson.aspects(
                        "thaumaturge:praecantatio", 100,
                        "thaumaturge:ordo", 150,
                        "thaumaturge:alienis", 150,
                        ContentJson.ASPECT_TERMINUS, 48),
                ContentJson.item(MATRIX_FOCUS),
                items(Tier.INFINITY.ingot(), INFINITY_PLATE, Tier.INFINITY.ingot(), INFINITY_PLATE, NEUTRONIUM_PLATE, NEUTRONIUM_PLATE),
                14,
                RESEARCH_ENTRY,
                INFINITY_FOCUS);
    }

    private static JsonObject blazeCubeFocus() {
        return ContentJson.infusion(
                ContentJson.aspects(
                        "thaumaturge:ignis", 100,
                        "thaumaturge:praecantatio", 50,
                        "thaumaturge:potentia", 75,
                        ContentJson.ASPECT_TERMINUS, 16),
                ContentJson.item("thaumaturge:focus_3"),
                items("avaritia:blaze_cube", MATRIX_PLATE, "avaritia:blaze_cube", MATRIX_PLATE),
                10,
                RESEARCH_ENTRY,
                BLAZE_CUBE_FOCUS);
    }

    private static JsonObject tier(int complexity, int depth, int branches, int repeats) {
        JsonObject json = new JsonObject();
        json.addProperty("complexity", complexity);
        json.addProperty("depth", depth);
        json.addProperty("branches", branches);
        json.addProperty("repeats", repeats);
        return json;
    }

    private static JsonObject focusTiers() {
        JsonObject values = new JsonObject();
        values.add(MATRIX_FOCUS, tier(110, 18, 6, 6));
        values.add(BLAZE_CUBE_FOCUS, tier(95, 12, 10, 12));
        values.add(INFINITY_FOCUS, tier(250, 32, 16, 16));
        JsonObject json = new JsonObject();
        json.add("values", values);
        return json;
    }

    private static JsonObject research() {
        return ContentJson.research(
                RESEARCH,
                List.of("thaumaturge:focus_greater", MOD + ":extremely_primordial_pearl"),
                6, 6,
                INFINITY_FOCUS,
                List.of(MOD + ":infusion/matrix_focus", MOD + ":infusion/infinity_focus", MOD + ":infusion/blaze_cube_focus"),
                3,
                ContentJson.aspects("thaumaturge:praecantatio", 8, "thaumaturge:vitreus", 6, "thaumaturge:cognitio", 6, ContentJson.ASPECT_TERMINUS, 4),
                3);
    }
}
