package nadiendev.ThaumaturgeTechCompats.jar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.leclowndu93150.thaumaturge.registry.TTDataComponents;
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
import static nadiendev.ThaumaturgeTechCompats.jar.ContentJson.strings;

public class JarDataProvider implements DataProvider {
    public static final String RESEARCH_ENTRY = "avaritia_jars";
    private static final String PEARL = MOD + ":extremely_primordial_pearl";

    private final PackOutput output;

    public JarDataProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (JarTier tier : JarTier.values()) {
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/loot_table/blocks/" + tier.jarName() + ".json", lootTable(tier)));
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + tier.jarName() + ".json", infusion(tier)));
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + RESEARCH_ENTRY + ".json", research()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats jars";
    }

    private static String id(JarTier tier) {
        return MOD + ":" + tier.jarName();
    }

    private static JsonObject lootTable(JarTier tier) {
        JsonObject copy = new JsonObject();
        copy.addProperty("function", "minecraft:copy_components");
        copy.add("include", strings(
                TTDataComponents.ESSENTIA_CONTENTS.getId().toString(),
                TTDataComponents.ASPECT_FILTER.getId().toString()));
        copy.addProperty("source", "block_entity");

        JsonArray functions = new JsonArray();
        functions.add(copy);

        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.add("functions", functions);
        entry.addProperty("name", id(tier));

        JsonArray entries = new JsonArray();
        entries.add(entry);

        JsonObject condition = new JsonObject();
        condition.addProperty("condition", "minecraft:survives_explosion");
        JsonArray conditions = new JsonArray();
        conditions.add(condition);

        JsonObject pool = new JsonObject();
        pool.addProperty("bonus_rolls", 0.0);
        pool.add("conditions", conditions);
        pool.add("entries", entries);
        pool.addProperty("rolls", 1.0);

        JsonArray pools = new JsonArray();
        pools.add(pool);

        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", MOD + ":blocks/" + tier.jarName());
        return json;
    }

    private static JsonObject infusion(JarTier tier) {
        String entry = MOD + ":" + RESEARCH_ENTRY;
        JsonArray components = new JsonArray();
        JsonArray aspects;
        int instability;
        String catalyst = "thaumaturge:jar_normal";
        switch (tier) {
            case MATRIX -> {
                addAll(components, "avaritia:crystal_matrix_ingot", 4);
                aspects = aspects(
                        "thaumaturge:vitreus", 24,
                        "thaumaturge:praemunio", 16,
                        "thaumaturge:potentia", 16,
                        ASPECT_TERMINUS, 4);
                instability = 3;
            }
            case NEUTRONIUM -> {
                catalyst = id(JarTier.MATRIX);
                addAll(components, "avaritia:neutron_ingot", 4);
                aspects = aspects(
                        "thaumaturge:metallum", 32,
                        "thaumaturge:praemunio", 32,
                        "thaumaturge:vitreus", 16,
                        ASPECT_TERMINUS, 8);
                instability = 6;
            }
            default -> {
                catalyst = id(JarTier.NEUTRONIUM);
                addAll(components, "avaritia:infinity_ingot", 4);
                components.add(item(PEARL));
                aspects = aspects(
                        "thaumaturge:alienis", 32,
                        "thaumaturge:praemunio", 48,
                        "thaumaturge:vacuos", 32,
                        ASPECT_TERMINUS, 24);
                instability = 9;
            }
        }
        return ContentJson.infusion(aspects, item(catalyst), components, instability, entry, id(tier));
    }

    private static void addAll(JsonArray components, String item, int count) {
        for (int i = 0; i < count; i++) {
            components.add(item(item));
        }
    }

    private static JsonObject research() {
        return ContentJson.research(
                RESEARCH_ENTRY,
                new String[]{"thaumaturge:warded_jars", PEARL},
                0, 2,
                new String[]{id(JarTier.MATRIX), id(JarTier.NEUTRONIUM), id(JarTier.INFINITY)},
                new String[]{
                        MOD + ":infusion/" + JarTier.MATRIX.jarName(),
                        MOD + ":infusion/" + JarTier.NEUTRONIUM.jarName(),
                        MOD + ":infusion/" + JarTier.INFINITY.jarName()},
                2,
                aspects("thaumaturge:alkimia", 6, "thaumaturge:vitreus", 4, ASPECT_TERMINUS, 3),
                2);
    }
}
