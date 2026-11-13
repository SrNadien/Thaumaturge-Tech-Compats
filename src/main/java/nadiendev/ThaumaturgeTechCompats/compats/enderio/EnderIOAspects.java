package nadiendev.ThaumaturgeTechCompats.compats.enderio;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;

import java.util.LinkedHashMap;
import java.util.Map;

public final class EnderIOAspects {
    private static final Map<String, Object[]> ITEMS = new LinkedHashMap<>();

    static {
        add("conductive_alloy_ingot", "metallum", 15, "potentia", 5, "permutatio", 5);
        add("energetic_alloy_ingot", "metallum", 10, "desiderium", 10, "potentia", 10, "lux", 5);
        add("vibrant_alloy_ingot", "metallum", 10, "potentia", 10, "motus", 10, "alienis", 5, "lux", 5);
        add("redstone_alloy_ingot", "potentia", 10, "ordo", 5, "metallum", 3);
        add("pulsating_alloy_ingot", "metallum", 15, "motus", 10, "alienis", 5);
        add("dark_steel_ingot", "metallum", 15, "tenebrae", 5, "terra", 5, "praemunio", 5);
        add("soularium_ingot", "metallum", 10, "spiritus", 10, "desiderium", 5);
        add("end_steel_ingot", "metallum", 15, "tenebrae", 5, "alienis", 5, "praemunio", 10, "terra", 3);

        add("conductive_alloy_nugget", "metallum", 1, "potentia", 1);
        add("energetic_alloy_nugget", "metallum", 1, "desiderium", 1);
        add("vibrant_alloy_nugget", "metallum", 1, "motus", 1);
        add("redstone_alloy_nugget", "potentia", 1, "ordo", 1);
        add("pulsating_alloy_nugget", "metallum", 1, "motus", 1);
        add("dark_steel_nugget", "metallum", 1, "tenebrae", 1);
        add("soularium_nugget", "metallum", 1, "spiritus", 1);
        add("end_steel_nugget", "metallum", 1, "alienis", 1);

        add("silicon", "vitreus", 5, "ordo", 3);
        add("grains_of_infinity", "potentia", 10, "praecantatio", 5);
        add("iron_gear", "metallum", 15, "motus", 5);
        add("energized_gear", "metallum", 15, "potentia", 10, "motus", 5);
        add("vibrant_gear", "metallum", 15, "potentia", 10, "motus", 10, "lux", 5);
        add("dark_bimetal_gear", "metallum", 20, "motus", 5, "tenebrae", 5);

        add("basic_capacitor", "potentia", 10, "metallum", 5, "machina", 5);
        add("double_layer_capacitor", "potentia", 20, "metallum", 10, "machina", 10);
        add("octadic_capacitor", "potentia", 40, "metallum", 15, "machina", 15);
        add("loot_capacitor", "potentia", 40, "machina", 10, "praecantatio", 10);

        add("powdered_coal", "ignis", 5, "terra", 2);
        add("powdered_iron", "metallum", 15);
        add("powdered_gold", "metallum", 10, "desiderium", 10);
        add("powdered_copper", "metallum", 10, "permutatio", 5);
        add("powdered_tin", "metallum", 10, "vitreus", 5);
        add("powdered_ender_pearl", "alienis", 10, "motus", 15);
        add("powdered_obsidian", "terra", 5, "ignis", 5, "tenebrae", 5);
        add("powdered_lapis_lazuli", "sensus", 5, "vitreus", 5);
        add("powdered_quartz", "vitreus", 5);
        add("prescient_powder", "sensus", 5, "cognitio", 5);
        add("vibrant_powder", "lux", 5, "potentia", 5, "motus", 5);
        add("pulsating_powder", "motus", 5, "potentia", 5);
        add("ender_crystal_powder", "alienis", 10, "vitreus", 5, "motus", 5);
        add("soul_powder", "spiritus", 5, "mortuus", 3);
        add("confusing_powder", "perditio", 5, "sensus", 3);
        add("withering_powder", "mortuus", 5, "perditio", 5);

        add("vibrant_crystal", "vitreus", 15, "potentia", 10, "motus", 10, "lux", 5);
        add("pulsating_crystal", "vitreus", 15, "motus", 10, "alienis", 10);
        add("prescient_crystal", "vitreus", 15, "cognitio", 10, "sensus", 10);
        add("weather_crystal", "vitreus", 15, "aer", 10, "aqua", 5);
        add("enticing_crystal", "vitreus", 10, "desiderium", 10);
        add("ender_crystal", "vitreus", 15, "alienis", 10, "motus", 10);

        add("zombie_electrode", "potentia", 8, "exanimis", 5, "metallum", 5);
        add("z_logic_controller", "machina", 10, "cognitio", 5, "exanimis", 5);
        add("ender_resonator", "alienis", 10, "machina", 10, "motus", 5);
        add("skeletal_contractor", "machina", 10, "mortuus", 5, "vinculum", 5);
        add("guardian_diode", "machina", 10, "potentia", 10, "aqua", 5);

        add("photovoltaic_composite", "vitreus", 5, "lux", 5, "ordo", 5);
        add("photovoltaic_plate", "lux", 10, "potentia", 5, "vitreus", 5);
        add("photovoltaic_cell", "lux", 10, "potentia", 10, "vitreus", 5);
        add("energetic_photovoltaic_module", "machina", 10, "lux", 15, "potentia", 15);
        add("pulsating_photovoltaic_module", "machina", 10, "lux", 20, "potentia", 20, "motus", 5);
        add("vibrant_photovoltaic_module", "machina", 15, "lux", 30, "potentia", 30, "motus", 10);

        add("conduit_binder", "vinculum", 5, "terra", 3);
        add("conduit_binder_composite", "vinculum", 3, "terra", 2);
        add("conduit", "machina", 5, "vinculum", 5, "motus", 3);
        add("conduit_probe", "instrumentum", 10, "sensus", 10, "machina", 5);

        add("basic_item_filter", "ordo", 5, "sensus", 5);
        add("big_item_filter", "ordo", 10, "sensus", 10);
        add("advanced_item_filter", "ordo", 10, "sensus", 10, "cognitio", 5);
        add("big_advanced_item_filter", "ordo", 15, "sensus", 15, "cognitio", 10);
        add("basic_fluid_filter", "aqua", 5, "ordo", 5, "sensus", 5);
        add("basic_soul_filter", "spiritus", 5, "ordo", 5, "sensus", 5);

        add("yeta_wrench", "instrumentum", 15, "metallum", 10, "motus", 5);
        add("electromagnet", "potentia", 15, "metallum", 10, "motus", 10);
        add("experience_rod", "cognitio", 10, "victus", 10, "instrumentum", 5);
        add("staff_of_levity", "volatus", 20, "aer", 10, "instrumentum", 5, "praecantatio", 5);
        add("staff_of_travelling", "alienis", 20, "motus", 20, "instrumentum", 5, "praecantatio", 5);
        add("coordinate_selector", "sensus", 10, "cognitio", 10, "instrumentum", 5);
        add("location_printout", "cognitio", 5, "sensus", 5);
        add("cold_fire_igniter", "ignis", 10, "gelum", 10, "instrumentum", 5);
        add("dark_steel_sword", "metallum", 30, "aversio", 10, "tenebrae", 10);
        add("glider_wing", "volatus", 15, "aer", 10);
        add("glider", "volatus", 25, "aer", 15, "motus", 5);
        add("soul_vial", "vitreus", 5, "vacuos", 3, "spiritus", 5);
        add("void_vial", "vitreus", 5, "vacuos", 8);
        add("enderios", "victus", 5, "alienis", 3);

        add("void_chassis", "machina", 15, "metallum", 15, "vacuos", 5);
        add("ensouled_chassis", "machina", 15, "metallum", 15, "spiritus", 15);

        add("alloy_smelter", "machina", 20, "ignis", 15, "metallum", 15, "permutatio", 10);
        add("sag_mill", "machina", 20, "metallum", 15, "perditio", 10, "terra", 5);
        add("stirling_generator", "machina", 15, "ignis", 10, "potentia", 15, "metallum", 10);
        add("crafter", "machina", 15, "fabrico", 15, "instrumentum", 10, "metallum", 10);
        add("vat", "machina", 15, "aqua", 15, "alkimia", 15, "metallum", 5);
        add("soul_binder", "machina", 15, "spiritus", 20, "vinculum", 15, "metallum", 10);
        add("slice_and_splice", "machina", 20, "fabrico", 15, "permutatio", 10, "aversio", 5);
        add("enchanter", "praecantatio", 25, "machina", 10, "cognitio", 10, "metallum", 5);
        add("farming_station", "machina", 15, "herba", 20, "victus", 10, "fabrico", 5);
        add("painting_machine", "machina", 15, "sensus", 10, "fabrico", 10);
        add("powered_spawner", "machina", 20, "bestia", 20, "praecantatio", 10, "spiritus", 10);
        add("broken_spawner", "bestia", 15, "vacuos", 10, "praecantatio", 5);
        add("wired_charger", "machina", 15, "potentia", 15, "metallum", 10);
        add("wireless_charger", "machina", 15, "potentia", 20, "motus", 10, "alienis", 5);
        add("xp_obelisk", "cognitio", 20, "machina", 10, "victus", 10, "praecantatio", 5);
        add("xp_vacuum", "cognitio", 10, "motus", 15, "machina", 10, "vinculum", 5);
        add("vacuum_chest", "motus", 15, "vacuos", 10, "machina", 5, "vinculum", 5);
        add("impulse_hopper", "machina", 10, "motus", 10, "metallum", 5, "vinculum", 5);
        add("travel_anchor", "alienis", 20, "motus", 20, "machina", 5);
        add("drain", "aqua", 15, "machina", 5, "vacuos", 5, "metallum", 5);
        add("fluid_tank", "aqua", 10, "metallum", 15, "vitreus", 10, "vacuos", 5);
        add("pressurized_fluid_tank", "aqua", 10, "metallum", 20, "vitreus", 10, "praemunio", 5);
        add("basic_capacitor_bank", "potentia", 30, "metallum", 20, "machina", 10, "ordo", 5);
        add("advanced_capacitor_bank", "potentia", 50, "metallum", 25, "machina", 15, "ordo", 5);
        add("vibrant_capacitor_bank", "potentia", 80, "metallum", 30, "machina", 20, "ordo", 5);
        add("soul_engine", "machina", 15, "spiritus", 20, "potentia", 15);
        add("soul_chain", "vinculum", 10, "spiritus", 10, "metallum", 5);
        add("attractor_obelisk", "machina", 10, "bestia", 15, "desiderium", 10, "praecantatio", 5);
        add("aversion_obelisk", "machina", 10, "aversio", 15, "praemunio", 10);
        add("inhibitor_obelisk", "machina", 10, "vacuos", 10, "praemunio", 10);
        add("relocator_obelisk", "machina", 10, "alienis", 10, "motus", 15);
        add("weather_obelisk", "machina", 10, "aer", 10, "aqua", 10, "praecantatio", 5);
        add("enderface", "sensus", 10, "alienis", 10, "cognitio", 5);
    }

    private EnderIOAspects() {
    }

    private static void add(String item, Object... aspects) {
        ITEMS.put(item, aspects);
    }

    public static Iterable<String> items() {
        return ITEMS.keySet();
    }

    public static void addTo(JsonObject values) {
        for (Map.Entry<String, Object[]> entry : ITEMS.entrySet()) {
            Object[] pairs = entry.getValue().clone();
            for (int i = 0; i < pairs.length; i += 2) {
                pairs[i] = "thaumaturge:" + pairs[i];
            }
            JsonObject json = new JsonObject();
            json.add("neoforge:conditions", conditions());
            json.add("value", ContentJson.aspects(pairs));
            values.add(EnderIOIntegration.MOD_ID + ":" + entry.getKey(), json);
        }
    }

    private static JsonArray conditions() {
        JsonArray conditions = CompatJson.conditions(EnderIOIntegration.MOD_ID);
        conditions.add(CompatJson.feature(CompatToggles.ENDERIO_ASPECTS));
        return conditions;
    }
}
