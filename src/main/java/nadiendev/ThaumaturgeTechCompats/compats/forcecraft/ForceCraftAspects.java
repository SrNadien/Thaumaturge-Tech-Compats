package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;

public final class ForceCraftAspects {
    private static final String ASPECT = "thaumaturge:";

    private ForceCraftAspects() {
    }

    public static void addItems(JsonObject values) {
        put(values, "forcecraft:force_gem", "potentia", 20, "praecantatio", 10, "vitreus", 10);
        put(values, "forcecraft:power_ore", "terra", 5, "potentia", 15, "praecantatio", 5, "vitreus", 10);
        put(values, "forcecraft:deepslate_power_ore", "terra", 5, "tenebrae", 2, "potentia", 15, "praecantatio", 5, "vitreus", 10);
        put(values, "forcecraft:force_ingot", "metallum", 10, "potentia", 10, "praecantatio", 5);
        put(values, "forcecraft:force_nugget", "metallum", 1, "potentia", 1);
        put(values, "forcecraft:force_stick", "herba", 1, "potentia", 2);
        put(values, "forcecraft:force_rod", "potentia", 10, "praecantatio", 5, "metallum", 5);
        put(values, "forcecraft:force_gear", "machina", 10, "metallum", 10, "potentia", 10);
        put(values, "forcecraft:force_bucket", "vacuos", 5, "metallum", 3, "potentia", 20, "praecantatio", 5, "aqua", 5);
        put(values, "forcecraft:force_flask", "vacuos", 5, "vitreus", 5);
        put(values, "forcecraft:force_filled_force_flask", "vitreus", 5, "potentia", 20, "praecantatio", 5, "aqua", 5);
        put(values, "forcecraft:force_log", "herba", 20, "potentia", 5);
        put(values, "forcecraft:force_wood", "herba", 20, "potentia", 5);
        put(values, "forcecraft:force_planks", "herba", 5, "potentia", 1);
        put(values, "forcecraft:force_leaves", "herba", 5, "potentia", 2);
        put(values, "forcecraft:force_sapling", "herba", 15, "victus", 5, "potentia", 5);
        put(values, "#forcecraft:force_brick", "terra", 5, "ignis", 1, "potentia", 2);
        put(values, "#forcecraft:force_torch", "lux", 5, "potentia", 2);
        put(values, "forcecraft:time_torch", "lux", 5, "motus", 10, "potentia", 5);
        put(values, "#forcecraft:force_furnace", "ignis", 10, "potentia", 10, "machina", 5);
        put(values, "forcecraft:force_engine", "machina", 20, "motus", 10, "potentia", 20, "metallum", 10);
        put(values, "forcecraft:infuser", "praecantatio", 20, "fabrico", 20, "potentia", 15, "machina", 10);
        put(values, "forcecraft:upgrade_core", "potentia", 10, "ordo", 5, "fabrico", 5);
        put(values, "forcecraft:inert_core", "ordo", 5, "potentia", 5);
        put(values, "forcecraft:heat_core", "ignis", 20, "potentia", 5);
        put(values, "forcecraft:freezing_core", "gelum", 20, "potentia", 5);
        put(values, "forcecraft:speed_core", "motus", 20, "potentia", 5);
        put(values, "forcecraft:grinding_core", "perditio", 15, "potentia", 5);
        put(values, "forcecraft:experience_core", "cognitio", 20, "potentia", 5);
        put(values, "forcecraft:treasure_core", "desiderium", 20, "potentia", 5);
        put(values, "forcecraft:claw", "bestia", 10, "aversio", 5, "instrumentum", 5);
        put(values, "forcecraft:recovery_heart", "victus", 20, "praecantatio", 5);
        put(values, "forcecraft:bottled_wither", "mortuus", 20, "tenebrae", 10, "vitreus", 5);
        put(values, "forcecraft:pile_of_gunpowder", "perditio", 3, "ignis", 2);
        put(values, "forcecraft:soul_wafer", "spiritus", 15, "praecantatio", 5);
        put(values, "forcecraft:golden_power_source", "potentia", 20, "metallum", 10, "desiderium", 10);
        put(values, "forcecraft:upgrade_tome", "cognitio", 20, "potentia", 10, "praecantatio", 10);
        put(values, "forcecraft:experience_tome", "cognitio", 25, "potentia", 10, "praecantatio", 10);
        put(values, "#forcecraft:chu_jelly", "aqua", 5, "victus", 5, "alkimia", 1);
    }

    public static JsonObject entities() {
        JsonObject values = new JsonObject();
        put(values, "forcecraft:red_chu_chu", "victus", 10, "aqua", 5, "ignis", 5);
        put(values, "forcecraft:green_chu_chu", "victus", 10, "aqua", 5, "herba", 5);
        put(values, "forcecraft:blue_chu_chu", "victus", 10, "aqua", 10);
        put(values, "forcecraft:gold_chu_chu", "victus", 10, "aqua", 5, "metallum", 5, "desiderium", 5);
        put(values, "forcecraft:creeper_tot", "perditio", 8, "ignis", 5, "bestia", 3);
        put(values, "forcecraft:ender_tot", "alienis", 8, "motus", 5, "bestia", 3);
        put(values, "forcecraft:angry_enderman", "alienis", 10, "aversio", 6, "motus", 10, "bestia", 3);
        put(values, "forcecraft:fairy", "volatus", 8, "spiritus", 5, "lux", 5, "praecantatio", 3);
        put(values, "forcecraft:cold_chicken", "bestia", 5, "volatus", 5, "gelum", 5);
        put(values, "forcecraft:cold_cow", "bestia", 15, "terra", 10, "gelum", 5);
        put(values, "forcecraft:cold_pig", "bestia", 10, "terra", 5, "gelum", 5);
        JsonObject json = new JsonObject();
        json.add("values", values);
        return json;
    }

    private static void put(JsonObject values, String key, Object... pairs) {
        JsonArray conditions = CompatJson.conditions(ForceCraftIntegration.MOD_ID);
        conditions.add(CompatJson.feature(CompatToggles.FORCECRAFT_ASPECTS));
        JsonObject entry = new JsonObject();
        entry.add("neoforge:conditions", conditions);
        entry.add("value", aspects(pairs));
        values.add(key, entry);
    }

    private static JsonArray aspects(Object... pairs) {
        Object[] prefixed = new Object[pairs.length];
        for (int i = 0; i < pairs.length; i += 2) {
            prefixed[i] = ASPECT + pairs[i];
            prefixed[i + 1] = pairs[i + 1];
        }
        return ContentJson.aspects(prefixed);
    }
}
