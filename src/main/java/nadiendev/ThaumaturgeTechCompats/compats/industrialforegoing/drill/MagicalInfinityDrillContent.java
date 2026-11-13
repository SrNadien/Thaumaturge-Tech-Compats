package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.aspects;
import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.item;

public class MagicalInfinityDrillContent implements DataProvider {
    public static final String RESEARCH = MagicalInfinityDrillRegistry.NAME;

    private static final String NAME = MagicalInfinityDrillRegistry.NAME;

    private final PackOutput output;

    public MagicalInfinityDrillContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "thaumaturge/research_entry/" + RESEARCH, research()));
        tasks.add(save(cache, "recipe/infusion/" + NAME, recipe()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Magical Infinity Drill content";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions() {
        JsonArray conditions = CompatJson.conditions(MagicalInfinityDrillIntegration.MOD_ID);
        conditions.add(CompatJson.feature(CompatToggles.MAGIC_INFINITY_DRILL));
        return conditions;
    }

    private static JsonObject research() {
        JsonObject json = ContentJson.research(
                RESEARCH,
                List.of(TechCategory.HEADER_ID, "thaumaturge:recharge_pedestal", "thaumaturge:liquid_death"),
                0, 4,
                MOD + ":" + NAME,
                List.of(MOD + ":infusion/" + NAME),
                1,
                aspects("thaumaturge:machina", 6, "thaumaturge:potentia", 5, "thaumaturge:auram", 4),
                3);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static void add(JsonArray components, String id, int count) {
        for (int i = 0; i < count; i++) {
            components.add(item(id));
        }
    }

    private static JsonObject recipe() {
        JsonArray components = new JsonArray();
        add(components, "thaumaturge:vis_resonator", 2);
        add(components, "thaumaturge:mechanism_complex", 1);
        add(components, "thaumaturge:plate_thaumium", 2);
        add(components, "thaumaturge:plate_void", 2);
        add(components, "thaumaturge:morphic_resonator", 1);
        JsonObject json = ContentJson.infusion(
                aspects("thaumaturge:machina", 80, "thaumaturge:potentia", 64, "thaumaturge:auram", 48, "thaumaturge:terra", 32, "thaumaturge:motus", 32),
                item(MagicalInfinityDrillIntegration.INFINITY_DRILL),
                components,
                6,
                MOD + ":" + RESEARCH,
                MOD + ":" + NAME);
        json.add("neoforge:conditions", conditions());
        return json;
    }
}
