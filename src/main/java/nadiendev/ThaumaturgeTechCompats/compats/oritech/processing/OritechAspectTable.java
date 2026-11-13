package nadiendev.ThaumaturgeTechCompats.compats.oritech.processing;

import nadiendev.ThaumaturgeTechCompats.compats.oritech.OritechFeatures;
import nadiendev.ThaumaturgeTechCompats.compats.tags.Asp;
import nadiendev.ThaumaturgeTechCompats.compats.tags.AspectMath;
import nadiendev.ThaumaturgeTechCompats.compats.tags.AspectPart;
import nadiendev.ThaumaturgeTechCompats.compats.tags.MaterialKind;
import nadiendev.ThaumaturgeTechCompats.compats.tags.TagAspectEntry;
import nadiendev.ThaumaturgeTechCompats.compats.tags.TagForm;
import nadiendev.ThaumaturgeTechCompats.compats.tags.TagMaterial;
import nadiendev.ThaumaturgeTechCompats.compats.tags.TagMaterials;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class OritechAspectTable {
    private static final String MOD = OritechFeatures.MOD_ID;
    private static final List<AspectPart> NO_EXTRAS = List.of();

    private final Map<String, List<AspectPart>> items = new LinkedHashMap<>();

    private OritechAspectTable() {
    }

    public static List<TagAspectEntry> entries() {
        OritechAspectTable table = new OritechAspectTable();
        table.materials();
        table.components();
        table.machines();
        table.addons();
        table.logistics();
        table.reactorAndAccelerator();
        table.equipment();
        List<TagAspectEntry> entries = new ArrayList<>();
        table.items.forEach((id, parts) -> entries.add(new TagAspectEntry(MOD + ":" + id, parts, List.of(CompatToggles.ORITECH_ASPECTS), List.of(MOD))));
        return entries;
    }

    private void put(String id, AspectPart... parts) {
        items.put(id, List.of(parts));
    }

    private void put(String id, List<AspectPart> parts) {
        items.put(id, parts);
    }

    private void form(String id, String material, TagForm form) {
        put(id, form.scale(TagMaterials.require(material).aspects()));
    }

    private void form(String id, TagMaterial material, TagForm form) {
        put(id, form.scale(material.aspects()));
    }

    private void small(String id, String material, TagForm form) {
        put(id, AspectMath.scale(form.scale(TagMaterials.require(material).aspects()), 1, 3, NO_EXTRAS));
    }

    private void gem(String id, String material) {
        put(id, AspectMath.scale(TagMaterials.require(material).aspects(), 1, 1, List.of(Asp.VITREUS.of(3))));
    }

    private void ownMetal(String name, AspectPart... aspects) {
        TagMaterial material = new TagMaterial(name, MaterialKind.METAL, List.of(aspects));
        form(name + "_ingot", material, TagForm.INGOT);
        form(name + "_dust", material, TagForm.DUST);
        form(name + "_block", material, TagForm.BLOCK);
    }

    private void materials() {
        for (String metal : List.of("iron", "copper", "gold")) {
            form(metal + "_clump", metal, TagForm.CLUMP);
            small("small_" + metal + "_clump", metal, TagForm.CLUMP);
            form(metal + "_dust", metal, TagForm.DUST);
            small("small_" + metal + "_dust", metal, TagForm.DUST);
            gem(metal + "_gem", metal);
        }
        form("copper_nugget", "copper", TagForm.NUGGET);

        for (String metal : List.of("nickel", "platinum")) {
            form(metal + "_ingot", metal, TagForm.INGOT);
            form(metal + "_nugget", metal, TagForm.NUGGET);
            form("raw_" + metal, metal, TagForm.RAW);
            form(metal + "_clump", metal, TagForm.CLUMP);
            small("small_" + metal + "_clump", metal, TagForm.CLUMP);
            form(metal + "_dust", metal, TagForm.DUST);
            small("small_" + metal + "_dust", metal, TagForm.DUST);
            gem(metal + "_gem", metal);
            form(metal + "_block", metal, TagForm.BLOCK);
            form("raw_" + metal + "_block", metal, TagForm.RAW_BLOCK);
        }
        form("nickel_ore", "nickel", TagForm.ORE);
        form("deepslate_nickel_ore", "nickel", TagForm.ORE);
        form("deepslate_platinum_ore", "platinum", TagForm.ORE);
        form("endstone_platinum_ore", "platinum", TagForm.ORE);

        form("raw_uranium", "uranium", TagForm.RAW);
        gem("uranium_gem", "uranium");
        form("uranium_dust", "uranium", TagForm.DUST);
        small("small_uranium_dust", "uranium", TagForm.DUST);
        put("uranium_pellet", Asp.POTENTIA.of(10), Asp.PERDITIO.of(3), Asp.METALLUM.of(4));
        put("small_uranium_pellet", Asp.POTENTIA.of(3), Asp.PERDITIO.of(1), Asp.METALLUM.of(1));
        form("uranium_dust_block", "uranium", TagForm.BLOCK);
        form("raw_uranium_block", "uranium", TagForm.RAW_BLOCK);
        form("deepslate_uranium_ore", "uranium", TagForm.ORE);
        put("uranium_crystal", Asp.POTENTIA.of(20), Asp.VITREUS.of(10), Asp.PERDITIO.of(5), Asp.TERRA.of(5));
        form("plutonium_dust", "plutonium", TagForm.DUST);
        small("small_plutonium_dust", "plutonium", TagForm.DUST);
        put("plutonium_pellet", Asp.POTENTIA.of(15), Asp.PERDITIO.of(8), Asp.METALLUM.of(4));
        put("small_plutonium_pellet", Asp.POTENTIA.of(5), Asp.PERDITIO.of(3), Asp.METALLUM.of(1));

        form("steel_ingot", "steel", TagForm.INGOT);
        form("steel_dust", "steel", TagForm.DUST);
        form("steel_block", "steel", TagForm.BLOCK);
        form("electrum_ingot", "electrum", TagForm.INGOT);
        form("electrum_dust", "electrum", TagForm.DUST);
        form("electrum_block", "electrum", TagForm.BLOCK);

        ownMetal("adamant", Asp.METALLUM.of(15), Asp.PRAEMUNIO.of(10), Asp.DESIDERIUM.of(5));
        ownMetal("biosteel", Asp.METALLUM.of(12), Asp.VICTUS.of(8));
        ownMetal("duratium", Asp.METALLUM.of(12), Asp.ORDO.of(10), Asp.PRAEMUNIO.of(5));
        ownMetal("energite", Asp.METALLUM.of(10), Asp.POTENTIA.of(15));
        put("prometheum_ingot", Asp.METALLUM.of(10), Asp.IGNIS.of(15), Asp.POTENTIA.of(10), Asp.ALIENIS.of(5));

        put("coal_dust", Asp.IGNIS.of(8), Asp.POTENTIA.of(6));
        put("quartz_dust", Asp.VITREUS.of(5));
        put("fluxite", Asp.POTENTIA.of(15), Asp.VITREUS.of(5));
        put("fluxite_block", Asp.POTENTIA.of(45), Asp.VITREUS.of(15), Asp.ORDO.of(5));
        put("strange_matter", Asp.ALIENIS.of(15), Asp.VACUOS.of(10), Asp.PERDITIO.of(5));
        put("enderic_compound", Asp.ALIENIS.of(10), Asp.MOTUS.of(8), Asp.PRAECANTATIO.of(4));

        put("silicon", Asp.VITREUS.of(8), Asp.TERRA.of(4));
        put("raw_silicon", Asp.TERRA.of(6), Asp.VITREUS.of(4));
        put("silicon_wafer", Asp.VITREUS.of(8), Asp.TERRA.of(4), Asp.MACHINA.of(3));
        put("silicon_block", Asp.VITREUS.of(24), Asp.TERRA.of(12), Asp.ORDO.of(5));

        put("raw_biopolymer", Asp.VICTUS.of(5), Asp.HERBA.of(5), Asp.ALKIMIA.of(3));
        put("polymer_resin", Asp.ALKIMIA.of(6), Asp.HERBA.of(3));
        put("plastic_sheet", Asp.ALKIMIA.of(6), Asp.FABRICO.of(3));
        put("plastic_block", Asp.ALKIMIA.of(18), Asp.FABRICO.of(9), Asp.ORDO.of(5));
        put("biomass", Asp.VICTUS.of(5), Asp.HERBA.of(5));
        put("biomass_block", Asp.VICTUS.of(15), Asp.HERBA.of(15), Asp.ORDO.of(5));
        put("solid_biofuel", Asp.IGNIS.of(8), Asp.HERBA.of(5), Asp.POTENTIA.of(3));
        put("banana", Asp.VICTUS.of(5), Asp.HERBA.of(3));
        put("banana_block", Asp.VICTUS.of(15), Asp.HERBA.of(9));
        put("packed_wheat", Asp.HERBA.of(5), Asp.VICTUS.of(3), Asp.FABRICO.of(2));

        put("carbon_fibre_strands", Asp.FABRICO.of(3), Asp.TERRA.of(3), Asp.IGNIS.of(1));
        put("reinforced_carbon_sheet", Asp.FABRICO.of(6), Asp.PRAEMUNIO.of(6), Asp.TERRA.of(4));
        put("clay_catalyst_beads", Asp.TERRA.of(5), Asp.ALKIMIA.of(4), Asp.PRAECANTATIO.of(2));

        for (String paint : List.of("diamond_paint", "redstone_paint", "orange_paint", "camo_paint", "fluxite_paint", "white_paint", "industrial_paint", "netherite_paint", "sculk_paint")) {
            put(paint, Asp.SENSUS.of(4), Asp.FABRICO.of(1));
        }
    }

    private void components() {
        put("fine_wire", Asp.METALLUM.of(3), Asp.PERMUTATIO.of(2));
        put("magnetic_coil", Asp.METALLUM.of(12), Asp.POTENTIA.of(8), Asp.VINCULUM.of(4));
        put("motor", Asp.METALLUM.of(20), Asp.MOTUS.of(10), Asp.MACHINA.of(10), Asp.POTENTIA.of(5));
        put("machine_plating", Asp.METALLUM.of(12), Asp.PRAEMUNIO.of(6));
        put("basic_battery", Asp.POTENTIA.of(20), Asp.METALLUM.of(8), Asp.MACHINA.of(4));
        put("advanced_battery", Asp.POTENTIA.of(40), Asp.METALLUM.of(12), Asp.MACHINA.of(8), Asp.VITREUS.of(6));
        put("overcharged_crystal", Asp.POTENTIA.of(30), Asp.VITREUS.of(12), Asp.PRAECANTATIO.of(6));
        put("superconductor", Asp.POTENTIA.of(10), Asp.METALLUM.of(8), Asp.ORDO.of(6));
        put("processing_unit", Asp.MACHINA.of(10), Asp.COGNITIO.of(8), Asp.POTENTIA.of(5), Asp.VITREUS.of(4));
        put("advanced_computing_engine", Asp.MACHINA.of(20), Asp.COGNITIO.of(20), Asp.POTENTIA.of(10), Asp.VITREUS.of(8));
        put("super_ai_chip", Asp.COGNITIO.of(40), Asp.MACHINA.of(20), Asp.SPIRITUS.of(10), Asp.POTENTIA.of(10));
        put("unholy_intelligence", Asp.COGNITIO.of(30), Asp.EXANIMIS.of(10), Asp.ALIENIS.of(10));
        put("heisenberg_compensator", Asp.ORDO.of(15), Asp.VACUOS.of(10), Asp.POTENTIA.of(10), Asp.PRAECANTATIO.of(5));
        put("flux_gate", Asp.POTENTIA.of(12), Asp.VINCULUM.of(8), Asp.ALIENIS.of(4));
        put("dubios_container", Asp.VACUOS.of(12), Asp.PERDITIO.of(6), Asp.ALIENIS.of(4));
        put("unstable_container", Asp.VACUOS.of(15), Asp.POTENTIA.of(10), Asp.PERDITIO.of(10));
        put("enderic_lens", Asp.VITREUS.of(12), Asp.ALIENIS.of(8), Asp.SENSUS.of(6));
        put("ion_thruster", Asp.MOTUS.of(15), Asp.VOLATUS.of(10), Asp.POTENTIA.of(8), Asp.METALLUM.of(10));
    }

    private void machines() {
        machine("pulverizer_block", Asp.PERDITIO.of(6), Asp.TERRA.of(4));
        machine("fragment_forge_block", Asp.FABRICO.of(8), Asp.PERDITIO.of(4));
        machine("assembler_block", Asp.FABRICO.of(12), Asp.INSTRUMENTUM.of(8));
        machine("foundry_block", Asp.IGNIS.of(12), Asp.PERMUTATIO.of(6));
        machine("centrifuge_block", Asp.MOTUS.of(10), Asp.ORDO.of(6));
        machine("atomic_forge_block", Asp.IGNIS.of(10), Asp.PRAECANTATIO.of(6), Asp.ALIENIS.of(4));
        machine("refinery_block", Asp.ALKIMIA.of(12), Asp.AQUA.of(6));
        machine("refinery_module_block", Asp.ALKIMIA.of(8), Asp.AQUA.of(4));
        machine("tainted_refinery_block", Asp.VITIUM.of(10), Asp.ALKIMIA.of(8));
        machine("cooler_block", Asp.GELUM.of(12));
        machine("particle_collector_block", Asp.POTENTIA.of(10), Asp.ALIENIS.of(6));
        machine("powered_furnace_block", Asp.IGNIS.of(12));
        machine("deep_drill_block", Asp.TERRA.of(12), Asp.PERDITIO.of(4));
        machine("pump_block", Asp.AQUA.of(10));
        machine("placer_block", Asp.FABRICO.of(8));
        machine("destroyer_block", Asp.PERDITIO.of(10));
        machine("fertilizer_block", Asp.HERBA.of(8), Asp.VICTUS.of(8));
        machine("charger_block", Asp.POTENTIA.of(10));
        machine("drone_port_block", Asp.VOLATUS.of(10), Asp.SENSUS.of(4));
        machine("laser_arm_block", Asp.IGNIS.of(10), Asp.SENSUS.of(6));
        machine("treefeller_block", Asp.HERBA.of(6), Asp.PERDITIO.of(8));
        machine("enchanter_block", Asp.PRAECANTATIO.of(15));
        machine("enchantment_catalyst_block", Asp.PRAECANTATIO.of(12), Asp.VITREUS.of(6));
        machine("spawner_controller_block", Asp.BESTIA.of(10), Asp.VINCULUM.of(8));
        machine("spawner_cage_block", Asp.BESTIA.of(6), Asp.VINCULUM.of(10));
        machine("simple_augment_station", Asp.PRAECANTATIO.of(6), Asp.FABRICO.of(6));
        machine("advanced_augment_station", Asp.PRAECANTATIO.of(12), Asp.FABRICO.of(10), Asp.COGNITIO.of(4));
        machine("arcane_augment_station", Asp.PRAECANTATIO.of(20), Asp.FABRICO.of(10), Asp.COGNITIO.of(8));
        machine("augment_application_block", Asp.PRAECANTATIO.of(8), Asp.FABRICO.of(6));
        machine("basic_generator_block", Asp.POTENTIA.of(15), Asp.IGNIS.of(6));
        machine("fuel_generator_block", Asp.POTENTIA.of(20), Asp.IGNIS.of(12));
        machine("bio_generator_block", Asp.POTENTIA.of(15), Asp.VICTUS.of(8), Asp.HERBA.of(6));
        machine("lava_generator_block", Asp.POTENTIA.of(18), Asp.IGNIS.of(14), Asp.TERRA.of(6));
        machine("steam_engine_block", Asp.POTENTIA.of(18), Asp.AQUA.of(8), Asp.IGNIS.of(8));
        machine("big_solar_panel_block", Asp.POTENTIA.of(20), Asp.LUX.of(15), Asp.VITREUS.of(10));
        put("black_hole_block", Asp.VACUOS.of(40), Asp.ALIENIS.of(20), Asp.POTENTIA.of(20), Asp.TENEBRAE.of(20));
        put("small_storage_block", Asp.VACUOS.of(6), Asp.METALLUM.of(12));
        put("large_storage_block", Asp.VACUOS.of(12), Asp.METALLUM.of(24));
        put("small_tank_block", Asp.VACUOS.of(6), Asp.VITREUS.of(8), Asp.METALLUM.of(6));
        put("machine_frame_block", Asp.METALLUM.of(18), Asp.MACHINA.of(6));
        put("machine_extender", Asp.METALLUM.of(8), Asp.MACHINA.of(4), Asp.VINCULUM.of(3));
        put("nuke", Asp.POTENTIA.of(60), Asp.PERDITIO.of(40), Asp.IGNIS.of(30), Asp.AVERSIO.of(20));
        put("low_yield_nuke", Asp.POTENTIA.of(30), Asp.PERDITIO.of(20), Asp.IGNIS.of(15), Asp.AVERSIO.of(10));
        for (int tier = 1; tier <= 7; tier++) {
            put("machine_core_" + tier,
                    Asp.MACHINA.of(8 + 4 * tier),
                    Asp.POTENTIA.of(4 + 3 * tier),
                    Asp.METALLUM.of(8 + 2 * tier),
                    Asp.COGNITIO.of(tier >= 3 ? tier * 2 : 1));
        }
    }

    private void machine(String id, AspectPart... flavour) {
        List<AspectPart> parts = new ArrayList<>(List.of(Asp.MACHINA.of(12), Asp.METALLUM.of(16), Asp.POTENTIA.of(6)));
        parts.addAll(List.of(flavour));
        put(id, AspectMath.scale(parts, 1, 1, NO_EXTRAS));
    }

    private void addons() {
        put("machine_speed_addon", Asp.MOTUS.of(12), Asp.MACHINA.of(6), Asp.POTENTIA.of(4));
        put("machine_processing_addon", Asp.MACHINA.of(10), Asp.COGNITIO.of(4));
        put("machine_ultimate_addon", Asp.MOTUS.of(20), Asp.MACHINA.of(15), Asp.POTENTIA.of(15), Asp.COGNITIO.of(8));
        put("machine_fluid_addon", Asp.AQUA.of(10), Asp.MACHINA.of(6));
        put("machine_yield_addon", Asp.DESIDERIUM.of(10), Asp.MACHINA.of(6), Asp.PERMUTATIO.of(4));
        put("machine_silk_touch_addon", Asp.SENSUS.of(8), Asp.MACHINA.of(6), Asp.PRAECANTATIO.of(3));
        put("steam_boiler_addon", Asp.AQUA.of(8), Asp.IGNIS.of(8), Asp.MACHINA.of(6));
        put("crop_filter_addon", Asp.HERBA.of(8), Asp.MACHINA.of(6), Asp.ORDO.of(4));
        put("quarry_addon", Asp.TERRA.of(12), Asp.MACHINA.of(8), Asp.PERDITIO.of(4));
        put("machine_hunter_addon", Asp.BESTIA.of(8), Asp.MACHINA.of(6), Asp.AVERSIO.of(4));
        put("machine_efficiency_addon", Asp.ORDO.of(10), Asp.MACHINA.of(6), Asp.POTENTIA.of(3));
        put("machine_capacitor_addon", Asp.POTENTIA.of(15), Asp.MACHINA.of(6));
        put("capacitor_addon_extender", Asp.POTENTIA.of(8), Asp.MACHINA.of(4), Asp.VINCULUM.of(3));
        put("machine_acceptor_addon", Asp.VINCULUM.of(8), Asp.MACHINA.of(6), Asp.MOTUS.of(3));
        put("machine_inventory_proxy_addon", Asp.VINCULUM.of(8), Asp.MACHINA.of(6), Asp.VACUOS.of(3));
        put("machine_redstone_addon", Asp.POTENTIA.of(8), Asp.MACHINA.of(6), Asp.COGNITIO.of(2));
        put("machine_burst_addon", Asp.POTENTIA.of(12), Asp.MOTUS.of(8), Asp.MACHINA.of(6));
        put("machine_combi_addon", Asp.MACHINA.of(12), Asp.PERMUTATIO.of(8), Asp.VINCULUM.of(6));
    }

    private void logistics() {
        put("item_pipe", Asp.MOTUS.of(4), Asp.METALLUM.of(3));
        put("transparent_item_pipe", Asp.MOTUS.of(4), Asp.VITREUS.of(3));
        put("fluid_pipe", Asp.AQUA.of(4), Asp.MOTUS.of(3), Asp.METALLUM.of(3));
        put("energy_pipe", Asp.POTENTIA.of(4), Asp.METALLUM.of(3), Asp.PERMUTATIO.of(1));
        put("framed_item_pipe", Asp.MOTUS.of(4), Asp.METALLUM.of(3), Asp.FABRICO.of(1));
        put("framed_fluid_pipe", Asp.AQUA.of(4), Asp.MOTUS.of(3), Asp.METALLUM.of(3), Asp.FABRICO.of(1));
        put("framed_energy_pipe", Asp.POTENTIA.of(4), Asp.METALLUM.of(3), Asp.FABRICO.of(1));
        put("framed_superconductor", Asp.POTENTIA.of(10), Asp.METALLUM.of(8), Asp.ORDO.of(6), Asp.FABRICO.of(1));
        put("item_pipe_duct_block", Asp.MOTUS.of(4), Asp.METALLUM.of(3), Asp.VACUOS.of(1));
        put("fluid_pipe_duct_block", Asp.AQUA.of(4), Asp.METALLUM.of(3), Asp.VACUOS.of(1));
        put("energy_pipe_duct_block", Asp.POTENTIA.of(4), Asp.METALLUM.of(3), Asp.VACUOS.of(1));
        put("superconductor_duct_block", Asp.POTENTIA.of(10), Asp.METALLUM.of(8), Asp.VACUOS.of(1));
        put("pipe_booster_block", Asp.MOTUS.of(8), Asp.POTENTIA.of(6), Asp.MACHINA.of(4));
        put("item_filter_block", Asp.ORDO.of(6), Asp.SENSUS.of(4), Asp.MACHINA.of(3));
        put("power_pole_block", Asp.POTENTIA.of(6), Asp.METALLUM.of(6), Asp.VINCULUM.of(2));
        put("machine_plating_block", Asp.METALLUM.of(12), Asp.PRAEMUNIO.of(6));
        put("iron_plating_block", Asp.METALLUM.of(15), Asp.PRAEMUNIO.of(4));
        put("nickel_plating_block", Asp.METALLUM.of(10), Asp.ORDO.of(3), Asp.PRAEMUNIO.of(4));
        put("carbon_plating_block", Asp.FABRICO.of(6), Asp.PRAEMUNIO.of(8), Asp.TERRA.of(4));
        put("industrial_glass_block", Asp.VITREUS.of(5), Asp.PRAEMUNIO.of(2));
        put("metal_beam_block", Asp.METALLUM.of(8));
        put("metal_girder_block", Asp.METALLUM.of(6));
        put("ceiling_light", Asp.LUX.of(6), Asp.VITREUS.of(3), Asp.METALLUM.of(3));
        put("ceiling_light_hanging", Asp.LUX.of(6), Asp.VITREUS.of(3), Asp.METALLUM.of(3));
        put("tech_door", Asp.METALLUM.of(10), Asp.MOTUS.of(4), Asp.MACHINA.of(4));
        put("hangar_door", Asp.METALLUM.of(20), Asp.MOTUS.of(8), Asp.MACHINA.of(8));
        put("tech_button", Asp.MACHINA.of(3), Asp.POTENTIA.of(2));
        put("tech_lever", Asp.MACHINA.of(3), Asp.POTENTIA.of(2));
    }

    private void reactorAndAccelerator() {
        put("reactor_controller", Asp.POTENTIA.of(30), Asp.MACHINA.of(15), Asp.COGNITIO.of(8), Asp.METALLUM.of(20));
        put("reactor_wall", Asp.METALLUM.of(10), Asp.PRAEMUNIO.of(8));
        put("reactor_rod", Asp.POTENTIA.of(15), Asp.METALLUM.of(10), Asp.PERDITIO.of(4));
        put("reactor_double_rod", Asp.POTENTIA.of(30), Asp.METALLUM.of(20), Asp.PERDITIO.of(8));
        put("reactor_quad_rod", Asp.POTENTIA.of(60), Asp.METALLUM.of(40), Asp.PERDITIO.of(16));
        put("reactor_vent", Asp.AER.of(8), Asp.METALLUM.of(8));
        put("reactor_reflector", Asp.METALLUM.of(8), Asp.MOTUS.of(4), Asp.LUX.of(4));
        put("reactor_heat_pipe", Asp.IGNIS.of(6), Asp.METALLUM.of(6));
        put("reactor_condenser", Asp.GELUM.of(8), Asp.AQUA.of(6), Asp.METALLUM.of(8));
        for (String port : List.of("reactor_fuel_port", "reactor_absorber_port", "reactor_energy_port", "reactor_redstone_port")) {
            put(port, Asp.VINCULUM.of(6), Asp.MACHINA.of(6), Asp.METALLUM.of(8));
        }
        put("accelerator_ring", Asp.POTENTIA.of(15), Asp.MOTUS.of(12), Asp.METALLUM.of(12));
        put("accelerator_motor", Asp.MOTUS.of(15), Asp.POTENTIA.of(12), Asp.MACHINA.of(6));
        put("accelerator_controller", Asp.COGNITIO.of(10), Asp.POTENTIA.of(15), Asp.MACHINA.of(10));
        put("accelerator_sensor", Asp.SENSUS.of(12), Asp.MACHINA.of(6));
    }

    private void equipment() {
        put("wrench", Asp.METALLUM.of(10), Asp.INSTRUMENTUM.of(8), Asp.MOTUS.of(3));
        put("hand_drill", Asp.INSTRUMENTUM.of(15), Asp.METALLUM.of(15), Asp.POTENTIA.of(10), Asp.TERRA.of(5));
        put("chainsaw", Asp.INSTRUMENTUM.of(15), Asp.METALLUM.of(15), Asp.POTENTIA.of(10), Asp.PERDITIO.of(5));
        put("target_designator", Asp.SENSUS.of(10), Asp.MACHINA.of(8), Asp.METALLUM.of(6));
        put("portable_laser", Asp.POTENTIA.of(20), Asp.IGNIS.of(12), Asp.MACHINA.of(12), Asp.METALLUM.of(10), Asp.VITREUS.of(6));
        put("electric_mace", Asp.POTENTIA.of(15), Asp.AVERSIO.of(10), Asp.METALLUM.of(20));
        put("weed_killer", Asp.ALKIMIA.of(6), Asp.MORTUUS.of(5), Asp.HERBA.of(3));
        put("promethium_axe", Asp.INSTRUMENTUM.of(15), Asp.IGNIS.of(15), Asp.METALLUM.of(20));
        put("promethium_pickaxe", Asp.INSTRUMENTUM.of(15), Asp.IGNIS.of(15), Asp.METALLUM.of(20));
        put("exo_helmet", Asp.PRAEMUNIO.of(20), Asp.METALLUM.of(25), Asp.MACHINA.of(6), Asp.POTENTIA.of(6));
        put("exo_chestplate", Asp.PRAEMUNIO.of(32), Asp.METALLUM.of(40), Asp.MACHINA.of(8), Asp.POTENTIA.of(8));
        put("exo_leggings", Asp.PRAEMUNIO.of(28), Asp.METALLUM.of(35), Asp.MACHINA.of(7), Asp.POTENTIA.of(7));
        put("exo_boots", Asp.PRAEMUNIO.of(16), Asp.METALLUM.of(20), Asp.MACHINA.of(5), Asp.POTENTIA.of(5));
        put("jetpack", Asp.MOTUS.of(20), Asp.VOLATUS.of(25), Asp.METALLUM.of(20), Asp.POTENTIA.of(10), Asp.MACHINA.of(10));
        put("exo_jetpack", Asp.MOTUS.of(25), Asp.VOLATUS.of(30), Asp.METALLUM.of(30), Asp.POTENTIA.of(14), Asp.MACHINA.of(12), Asp.PRAEMUNIO.of(12));
        put("jetpack_elytra", Asp.MOTUS.of(25), Asp.VOLATUS.of(40), Asp.METALLUM.of(20), Asp.POTENTIA.of(10), Asp.MACHINA.of(10));
        put("jetpack_exo_elytra", Asp.MOTUS.of(30), Asp.VOLATUS.of(45), Asp.METALLUM.of(30), Asp.POTENTIA.of(14), Asp.MACHINA.of(12), Asp.PRAEMUNIO.of(12));
    }
}
