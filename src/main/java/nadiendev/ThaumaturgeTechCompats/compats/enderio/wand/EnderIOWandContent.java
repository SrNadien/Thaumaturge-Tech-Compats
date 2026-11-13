package nadiendev.ThaumaturgeTechCompats.compats.enderio.wand;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOJson;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnderIOWandContent implements DataProvider {
    public static final int COLUMN = 73;

    private static final String MOD = EnderIOJson.MOD;
    private static final String ENTRY = EnderIOWandRegistry.ENTRY;
    private static final int ROD_VIS = 40;
    private static final int CAP_VIS = 60;

    private final PackOutput output;

    public EnderIOWandContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            String folder = material.arcane() ? "wand/part/" : "infusion/";
            tasks.add(save(cache, "recipe/" + folder + material.rodName(), material.arcane() ? arcaneRod(material) : infusionRod(material)));
            tasks.add(save(cache, "recipe/" + folder + material.capName(), material.arcane() ? arcaneCap(material) : infusionCap(material)));
        }
        tasks.add(save(cache, "thaumaturge/research_entry/" + ENTRY, entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO wand parts";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions() {
        return EnderIOJson.conditions(EnderIOIntegration.WAND_PARTS);
    }

    private static JsonObject arcaneRod(EnderIOWandMaterial material) {
        JsonObject key = new JsonObject();
        key.add("I", EnderIOJson.ingredient(material.ingredient(), material.tag()));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("  I", " I ", "I  "),
                MOD + ":" + ENTRY, ROD_VIS, MOD + ":" + material.rodName());
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject arcaneCap(EnderIOWandMaterial material) {
        JsonObject key = new JsonObject();
        key.add("I", EnderIOJson.ingredient(material.ingredient(), material.tag()));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("III", "I I"),
                MOD + ":" + ENTRY, CAP_VIS, MOD + ":" + material.capName());
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject infusionRod(EnderIOWandMaterial material) {
        int step = material.ordinal();
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:praecantatio", 4 * step, "thaumaturge:potentia", 3 * step, "thaumaturge:motus", 2 * step),
                ContentJson.item(MOD + ":" + material.previous().rodName()),
                EnderIOJson.repeat(EnderIOJson.ingredient(material.ingredient(), material.tag()), 4),
                1 + step / 2,
                MOD + ":" + ENTRY,
                MOD + ":" + material.rodName());
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject infusionCap(EnderIOWandMaterial material) {
        int step = material.ordinal();
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:praecantatio", 4 * step, "thaumaturge:metallum", 3 * step, "thaumaturge:potentia", 2 * step),
                ContentJson.item(MOD + ":" + material.previous().capName()),
                EnderIOJson.repeat(EnderIOJson.ingredient(material.ingredient(), material.tag()), 4),
                1 + step / 2,
                MOD + ":" + ENTRY,
                MOD + ":" + material.capName());
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static String assembly(EnderIOWandMaterial material) {
        return "thaumaturge:wand/assembly/" + MOD + "_" + material.id() + "_" + MOD + "_" + material.id();
    }

    private static JsonObject entry() {
        List<String> recipes = new ArrayList<>();
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            String folder = material.arcane() ? "wand/part/" : "infusion/";
            recipes.add(MOD + ":" + folder + material.rodName());
            recipes.add(MOD + ":" + folder + material.capName());
        }
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            recipes.add(assembly(material));
        }
        return EnderIOJson.entry(
                ENTRY,
                conditions(),
                List.of(TechCategory.HEADER_ID, "thaumaturge:rod_greatwood", "thaumaturge:cap_thaumium"),
                COLUMN,
                MOD + ":" + EnderIOWandMaterial.END_STEEL.capName(),
                recipes,
                EnderIOJson.CATEGORY_AUROMANCY, 2, 1,
                ContentJson.aspects("thaumaturge:praecantatio", 6, "thaumaturge:metallum", 5, "thaumaturge:potentia", 4),
                3);
    }
}
