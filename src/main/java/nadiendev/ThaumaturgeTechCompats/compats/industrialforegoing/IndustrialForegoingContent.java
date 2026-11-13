package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing;

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

public class IndustrialForegoingContent implements DataProvider {
    private static final String CATEGORY_ID = TechCategory.CATEGORY_ID;
    private static final int DRILL_COLUMN = 61;
    private static final int DRILL_ROW = 2;
    private static final int ORE_BASE_COLUMN = 61;
    private static final int FLUID_BASE_COLUMN = 62;
    private static final int BASE_ROW = 4;

    private final PackOutput output;

    public IndustrialForegoingContent(PackOutput output) {
        this.output = output;
    }

    public static String research(LaserMachine machine) {
        return machine.blockName();
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (LaserMachine machine : LaserMachine.values()) {
            String name = machine.blockName();
            tasks.add(save(cache, "thaumaturge/research_entry/" + research(machine), entry(machine)));
            tasks.add(save(cache, "recipe/infusion/" + name, recipe(machine)));
            tasks.add(save(cache, "loot_table/blocks/" + name, loot(name)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Industrial Foregoing content";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray modLoaded() {
        JsonArray conditions = CompatJson.conditions(IndustrialForegoingIntegration.MOD_ID);
        conditions.add(CompatJson.modLoaded(IndustrialForegoingIntegration.TITANIUM_ID));
        conditions.add(CompatJson.feature(CompatToggles.MAGIC_LASER_DRILL));
        return conditions;
    }

    private static List<String> parents(LaserMachine machine) {
        return switch (machine) {
            case DRILL -> List.of(TechCategory.HEADER_ID, "thaumaturge:arcane_bore", "thaumaturge:vis_generator");
            case ORE_BASE -> List.of(MOD + ":" + research(LaserMachine.DRILL));
            case FLUID_BASE -> List.of(MOD + ":" + research(LaserMachine.ORE_BASE));
        };
    }

    private static JsonObject entry(LaserMachine machine) {
        String name = machine.blockName();
        int column = switch (machine) {
            case DRILL -> DRILL_COLUMN;
            case ORE_BASE -> ORE_BASE_COLUMN;
            case FLUID_BASE -> FLUID_BASE_COLUMN;
        };
        JsonObject json = ContentJson.research(
                research(machine),
                parents(machine),
                column, machine == LaserMachine.DRILL ? DRILL_ROW : BASE_ROW,
                MOD + ":" + name,
                List.of(MOD + ":infusion/" + name),
                1,
                aspects("thaumaturge:machina", 6, "thaumaturge:potentia", 4, machine.aspect(), 3),
                machine == LaserMachine.DRILL ? 3 : 2);
        json.addProperty("category", CATEGORY_ID);
        json.add("neoforge:conditions", modLoaded());
        return json;
    }

    private static void add(JsonArray components, String id, int count) {
        for (int i = 0; i < count; i++) {
            components.add(item(id));
        }
    }

    private static JsonObject recipe(LaserMachine machine) {
        JsonArray components = new JsonArray();
        add(components, "thaumaturge:vis_resonator", 2);
        add(components, "thaumaturge:mechanism_complex", 1);
        add(components, "thaumaturge:plate_thaumium", 2);
        add(components, "thaumaturge:morphic_resonator", 1);
        add(components, machine.extraComponent(), 1);
        JsonObject json = ContentJson.infusion(
                aspects("thaumaturge:machina", 64, "thaumaturge:potentia", 48, "thaumaturge:auram", 32, machine.aspect(), 24),
                item(machine.original()),
                components,
                5,
                MOD + ":" + research(machine),
                MOD + ":" + machine.blockName());
        json.add("neoforge:conditions", modLoaded());
        return json;
    }

    private static JsonObject loot(String name) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", MOD + ":" + name);
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
}
