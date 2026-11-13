package nadiendev.ThaumaturgeTechCompats.wandtier.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import nadiendev.ThaumaturgeTechCompats.tiercontent.Tier;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WandTierContentProvider implements DataProvider {
    private static final String MOD = ContentJson.MOD;
    private static final String NEUTRONIUM_WAND = "neutronium_wand";
    private static final String MATRIX_WAND = "matrix_wand";
    private static final String PEARL = MOD + ":extremely_primordial_pearl";
    private static final String MATRIX_CAP = MOD + ":matrix_wand_cap";
    private static final String MATRIX_PLATE = MOD + ":" + Tier.MATRIX.plateName();
    private static final String NEUTRONIUM_PLATE = MOD + ":" + Tier.NEUTRONIUM.plateName();

    private final PackOutput output;

    public WandTierContentProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "recipe/wand/part/matrix_wand_rod.json", matrixRodRecipe()));
        tasks.add(save(cache, "recipe/wand/part/matrix_wand_cap.json", matrixCapRecipe()));
        tasks.add(save(cache, "recipe/infusion/neutronium_wand_cap.json", neutroniumCapInfusion()));
        tasks.add(save(cache, "recipe/infusion/neutronium_wand_rod.json", neutroniumRodInfusion()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + NEUTRONIUM_WAND + ".json", neutroniumResearch()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + MATRIX_WAND + ".json", matrixResearch()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats wand tier content";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonElement json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path, json);
    }

    private static JsonObject matrixRodRecipe() {
        JsonObject key = new JsonObject();
        key.add("C", ContentJson.item(Tier.MATRIX.ingot()));
        return ContentJson.arcaneShaped(
                key,
                ContentJson.strings("  C", " C ", "C  "),
                MOD + ":" + MATRIX_WAND,
                300,
                MOD + ":matrix_wand_rod");
    }

    private static JsonObject matrixCapRecipe() {
        JsonObject key = new JsonObject();
        key.add("I", ContentJson.item(Tier.MATRIX.ingot()));
        key.add("P", ContentJson.item(MATRIX_PLATE));
        return ContentJson.arcaneShaped(
                key,
                ContentJson.strings("III", "P P"),
                MOD + ":" + MATRIX_WAND,
                600,
                MATRIX_CAP);
    }

    private static JsonObject neutroniumCapInfusion() {
        JsonArray components = new JsonArray();
        components.add(ContentJson.item(Tier.NEUTRONIUM.ingot()));
        components.add(ContentJson.item(NEUTRONIUM_PLATE));
        components.add(ContentJson.item(Tier.NEUTRONIUM.ingot()));
        components.add(ContentJson.item(NEUTRONIUM_PLATE));
        return ContentJson.infusion(
                ContentJson.aspects(
                        "thaumaturge:potentia", 24,
                        "thaumaturge:metallum", 24,
                        "thaumaturge:auram", 12,
                        ContentJson.ASPECT_TERMINUS, 8),
                ContentJson.item(MATRIX_CAP),
                components,
                6,
                MOD + ":" + NEUTRONIUM_WAND,
                MOD + ":neutronium_wand_cap");
    }

    private static JsonObject neutroniumRodInfusion() {
        JsonArray components = new JsonArray();
        components.add(ContentJson.item(Tier.NEUTRONIUM.ingot()));
        components.add(ContentJson.item(NEUTRONIUM_PLATE));
        components.add(ContentJson.item(Tier.NEUTRONIUM.ingot()));
        components.add(ContentJson.item(NEUTRONIUM_PLATE));
        return ContentJson.infusion(
                ContentJson.aspects(
                        "thaumaturge:terra", 32,
                        "thaumaturge:praecantatio", 24,
                        "thaumaturge:potentia", 24,
                        ContentJson.ASPECT_TERMINUS, 8),
                ContentJson.item(MOD + ":matrix_wand_rod"),
                components,
                8,
                MOD + ":" + NEUTRONIUM_WAND,
                MOD + ":neutronium_wand_rod");
    }

    private static String assembly(String cap, String rod) {
        return "thaumaturge:wand/assembly/" + MOD + "_" + cap + "_" + MOD + "_" + rod;
    }

    private static JsonObject neutroniumResearch() {
        return ContentJson.research(
                NEUTRONIUM_WAND,
                List.of("thaumaturge:rod_obsidian", MOD + ":" + MATRIX_WAND),
                2, 6,
                MOD + ":neutronium_wand_rod",
                List.of(
                        MOD + ":infusion/neutronium_wand_cap",
                        MOD + ":infusion/neutronium_wand_rod",
                        assembly("neutronium", "neutronium")),
                3,
                ContentJson.aspects("thaumaturge:praecantatio", 8, "thaumaturge:metallum", 6, "thaumaturge:potentia", 6, ContentJson.ASPECT_TERMINUS, 4),
                3);
    }

    private static JsonObject matrixResearch() {
        return ContentJson.research(
                MATRIX_WAND,
                List.of("thaumaturge:rod_quartz", PEARL),
                4, 6,
                MOD + ":matrix_wand_cap",
                List.of(
                        MOD + ":wand/part/matrix_wand_cap",
                        MOD + ":wand/part/matrix_wand_rod",
                        assembly("matrix", "matrix")),
                3,
                ContentJson.aspects("thaumaturge:praecantatio", 8, "thaumaturge:vitreus", 6, "thaumaturge:potentia", 6, ContentJson.ASPECT_TERMINUS, 4),
                3);
    }
}
