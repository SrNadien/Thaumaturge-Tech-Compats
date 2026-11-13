package nadiendev.ThaumaturgeTechCompats.braincharger;

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
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.strings;

public class BrainChargerContent implements DataProvider {
    public static final String BOX_RESEARCH = "avaritia_brain_box";
    public static final String CHARGER_RESEARCH = "avaritia_workbench_charger";
    private static final String PEARL = MOD + ":extremely_primordial_pearl";

    private final PackOutput output;

    public BrainChargerContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (BoostTier tier : BoostTier.values()) {
            tasks.add(save(cache, "loot_table/blocks/" + tier.boxName(), loot(tier.boxName())));
            tasks.add(save(cache, "loot_table/blocks/" + tier.chargerName(), loot(tier.chargerName())));
            tasks.add(save(cache, "recipe/infusion/" + tier.boxName(), boxRecipe(tier)));
            tasks.add(save(cache, "recipe/infusion/" + tier.chargerName(), chargerRecipe(tier)));
        }
        tasks.add(save(cache, "thaumaturge/research_entry/" + BOX_RESEARCH, boxResearch()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + CHARGER_RESEARCH, chargerResearch()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats mnemonic matrices and chargers";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static String id(String name) {
        return MOD + ":" + name;
    }

    private static JsonObject loot(String name) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", id(name));
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
        json.addProperty("random_sequence", MOD + ":blocks/" + name);
        return json;
    }

    private static void add(JsonArray components, String id, int count) {
        for (int i = 0; i < count; i++) {
            components.add(item(id));
        }
    }

    private static JsonObject boxRecipe(BoostTier tier) {
        BoostTier previous = tier.previous();
        String catalyst = previous == null ? "thaumaturge:brain_box" : id(previous.boxName());
        JsonArray components = new JsonArray();
        add(components, id(tier.tier().plateName()), 4);
        JsonArray aspects;
        int instability;
        switch (tier) {
            case MATRIX -> {
                add(components, "thaumaturge:mind_clockwork", 1);
                aspects = aspects("thaumaturge:cognitio", 24, "thaumaturge:machina", 16, "thaumaturge:ordo", 16, ASPECT_TERMINUS, 4);
                instability = 4;
            }
            case NEUTRONIUM -> {
                add(components, "thaumaturge:mind_biothaumic", 1);
                aspects = aspects("thaumaturge:cognitio", 40, "thaumaturge:machina", 32, "thaumaturge:metallum", 24, ASPECT_TERMINUS, 8);
                instability = 7;
            }
            default -> {
                add(components, PEARL, 1);
                aspects = aspects("thaumaturge:cognitio", 64, "thaumaturge:machina", 48, "thaumaturge:alienis", 32, ASPECT_TERMINUS, 24);
                instability = 10;
            }
        }
        return ContentJson.infusion(aspects, item(catalyst), components, instability, id(BOX_RESEARCH), id(tier.boxName()));
    }

    private static JsonObject chargerRecipe(BoostTier tier) {
        BoostTier previous = tier.previous();
        String catalyst = previous == null ? "thaumaturge:arcane_workbench_charger" : id(previous.chargerName());
        JsonArray components = new JsonArray();
        add(components, id(tier.tier().plateName()), 4);
        add(components, "thaumaturge:vis_resonator", 1);
        add(components, tier.tier().ingot(), 2);
        JsonArray aspects;
        int instability;
        switch (tier) {
            case MATRIX -> {
                aspects = aspects("thaumaturge:auram", 24, "thaumaturge:potentia", 24, "thaumaturge:fabrico", 16, ASPECT_TERMINUS, 4);
                instability = 4;
            }
            case NEUTRONIUM -> {
                aspects = aspects("thaumaturge:auram", 40, "thaumaturge:potentia", 40, "thaumaturge:metallum", 24, ASPECT_TERMINUS, 8);
                instability = 7;
            }
            default -> {
                add(components, PEARL, 1);
                aspects = aspects("thaumaturge:auram", 64, "thaumaturge:potentia", 64, "thaumaturge:alienis", 32, ASPECT_TERMINUS, 24);
                instability = 10;
            }
        }
        return ContentJson.infusion(aspects, item(catalyst), components, instability, id(CHARGER_RESEARCH), id(tier.chargerName()));
    }

    private static JsonObject boxResearch() {
        List<String> recipes = new ArrayList<>();
        for (BoostTier tier : BoostTier.values()) {
            recipes.add(MOD + ":infusion/" + tier.boxName());
        }
        return ContentJson.research(
                BOX_RESEARCH,
                List.of("thaumaturge:thaumatorium", PEARL),
                4, 2,
                id(BoostTier.INFINITY.boxName()),
                recipes,
                2,
                aspects("thaumaturge:cognitio", 6, "thaumaturge:machina", 4, ASPECT_TERMINUS, 3),
                2);
    }

    private static JsonObject chargerResearch() {
        List<String> recipes = new ArrayList<>();
        for (BoostTier tier : BoostTier.values()) {
            recipes.add(MOD + ":infusion/" + tier.chargerName());
        }
        return ContentJson.research(
                CHARGER_RESEARCH,
                List.of("thaumaturge:workbench_charger", PEARL),
                6, 2,
                id(BoostTier.INFINITY.chargerName()),
                recipes,
                2,
                aspects("thaumaturge:auram", 6, "thaumaturge:potentia", 4, ASPECT_TERMINUS, 3),
                2);
    }
}
