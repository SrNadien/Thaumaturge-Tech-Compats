package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AvaritiaMachinesContent implements DataProvider {
    public static final String ENTRY = "avaritia_machine_tiers";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String PEARL = MOD + ":extremely_primordial_pearl";
    private static final String MECHANISM_SIMPLE = "thaumaturge:mechanism_simple";
    private static final String VOID_SEED = "thaumaturge:void_seed";
    private static final String BASE_COLLECTOR = "avaritia:densest_neutron_collector";
    private static final String BASE_COMPRESSOR = "avaritia:densest_neutron_compressor";
    private static final String ASPECT_NUCLEAR = MOD + ":nuclear";
    private static final String ASPECT_COSMIC = MOD + ":cosmic";
    private static final int COLUMN = 4;
    private static final int ROW = 4;

    private final PackOutput output;

    public AvaritiaMachinesContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (AvaritiaMachineTier tier : AvaritiaMachineTier.values()) {
            for (String name : new String[]{tier.collectorName(), tier.compressorName()}) {
                tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/loot_table/blocks/" + name + ".json", lootTable(name)));
            }
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + tier.collectorName() + ".json", infusion(tier, true)));
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + tier.compressorName() + ".json", infusion(tier, false)));
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/aspect/nuclear.json", aspect(0x8BE02A, "nuclear", "thaumaturge:potentia", "thaumaturge:perditio")));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/aspect/cosmic.json", aspect(0x5B3FD9, "cosmic", "thaumaturge:alienis", "thaumaturge:auram")));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY + ".json", entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats avaritia machines";
    }

    private static JsonArray conditions() {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.feature(AvaritiaMachinesIntegration.TOGGLE));
        return conditions;
    }

    private static JsonObject aspect(int color, String tag, String first, String second) {
        JsonObject json = new JsonObject();
        json.addProperty("color", color);
        json.add("components", ContentJson.strings(first, second));
        json.addProperty("tag", tag);
        json.addProperty("texture", MOD + ":textures/aspects/" + tag + ".png");
        return json;
    }

    private static JsonObject lootTable(String name) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", MOD + ":" + name);

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
        json.addProperty("random_sequence", MOD + ":blocks/" + name);
        return json;
    }

    private static String catalyst(AvaritiaMachineTier tier, boolean collector) {
        AvaritiaMachineTier previous = tier.previous();
        if (previous == null) {
            return collector ? BASE_COLLECTOR : BASE_COMPRESSOR;
        }
        return MOD + ":" + (collector ? previous.collectorName() : previous.compressorName());
    }

    private static JsonObject infusion(AvaritiaMachineTier tier, boolean collector) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item(tier.plate()));
        }
        String focus = collector ? "thaumaturge:vacuos" : "thaumaturge:praemunio";
        String support = collector ? "thaumaturge:potentia" : "thaumaturge:ordo";
        JsonArray aspects;
        int instability;
        switch (tier) {
            case THAUMIUM -> {
                components.add(ContentJson.item(MECHANISM_SIMPLE));
                aspects = ContentJson.aspects("thaumaturge:machina", 24, focus, 16, support, 16, ContentJson.ASPECT_TERMINUS, 4);
                instability = 3;
            }
            case ALCHEMICAL_BRASS -> {
                components.add(ContentJson.item(MECHANISM_SIMPLE));
                aspects = ContentJson.aspects("thaumaturge:machina", 32, focus, 32, support, 24, ASPECT_NUCLEAR, 16, ContentJson.ASPECT_TERMINUS, 8);
                instability = 6;
            }
            default -> {
                components.add(ContentJson.item(VOID_SEED));
                aspects = ContentJson.aspects("thaumaturge:machina", 48, focus, 48, "thaumaturge:alienis", 32, ASPECT_NUCLEAR, 24, ASPECT_COSMIC, 32, ContentJson.ASPECT_TERMINUS, 24);
                instability = 9;
            }
        }
        String result = MOD + ":" + (collector ? tier.collectorName() : tier.compressorName());
        JsonObject json = ContentJson.infusion(aspects, ContentJson.item(catalyst(tier, collector)), components, instability, MOD + ":" + ENTRY, result);
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject entry() {
        List<String> recipes = new ArrayList<>();
        for (AvaritiaMachineTier tier : AvaritiaMachineTier.values()) {
            recipes.add(MOD + ":infusion/" + tier.collectorName());
            recipes.add(MOD + ":infusion/" + tier.compressorName());
        }
        JsonObject json = ContentJson.research(
                ENTRY,
                List.of(PEARL),
                COLUMN, ROW,
                MOD + ":" + AvaritiaMachineTier.VOID_METAL.collectorName(),
                recipes,
                2,
                ContentJson.aspects("thaumaturge:machina", 6, "thaumaturge:potentia", 4, ContentJson.ASPECT_TERMINUS, 3),
                2);
        json.add("neoforge:conditions", conditions());
        return json;
    }
}
