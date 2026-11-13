package nadiendev.ThaumaturgeTechCompats.essences;

import nadiendev.ThaumaturgeTechCompats.compats.tags.Asp;
import nadiendev.ThaumaturgeTechCompats.compats.tags.AspectPart;

import java.util.ArrayList;
import java.util.List;

public final class EssenceAspectTable {
    private static final String SF = "solarflux:";
    private static final String AVARITIA = "avaritia:";
    private static final String MEK_GEN = "mekanismgenerators:";
    private static final List<AspectPart> NO_BASE = List.of();

    private final List<EssenceEntry> entries = new ArrayList<>();

    private EssenceAspectTable() {
    }

    public static List<EssenceEntry> entries() {
        EssenceAspectTable table = new EssenceAspectTable();
        table.sol();
        table.nuclear();
        table.cosmic();
        table.terminus();
        return table.entries;
    }

    private void on(String key, AspectPart... essences) {
        entries.add(new EssenceEntry(key, NO_BASE, List.of(essences)));
    }

    private void make(String key, List<AspectPart> base, AspectPart... essences) {
        entries.add(new EssenceEntry(key, base, List.of(essences)));
    }

    private static List<AspectPart> base(AspectPart... parts) {
        return List.of(parts);
    }

    private void sol() {
        on("#c:dusts/glowstone", Asp.SOL.of(3));
        on("minecraft:glowstone", Asp.SOL.of(8));
        on("#c:rods/blaze", Asp.SOL.of(3));
        on("minecraft:blaze_powder", Asp.SOL.of(2));
        on("minecraft:magma_cream", Asp.SOL.of(2));
        on("minecraft:daylight_detector", Asp.SOL.of(6));
        make("minecraft:sea_lantern", base(Asp.AQUA.of(33), Asp.TERRA.of(15), Asp.VITREUS.of(18), Asp.LUX.of(18)), Asp.SOL.of(3));
        on("minecraft:sunflower", Asp.SOL.of(4));
        on("minecraft:torchflower", Asp.SOL.of(2));
        on("minecraft:lava_bucket", Asp.SOL.of(1));
        on("#c:ingots/gold", Asp.SOL.of(1));
        on("#c:storage_blocks/gold", Asp.SOL.of(3));
        on("thaumaturge:amber", Asp.SOL.of(2));
        on("minecraft:honeycomb", Asp.SOL.of(1));
        on("minecraft:honey_bottle", Asp.SOL.of(1));
        on("minecraft:honey_block", Asp.SOL.of(2));
        make("create:refined_radiance", base(Asp.LUX.of(12), Asp.PRAECANTATIO.of(4)), Asp.SOL.of(5));
        solarFlux();
        on("botania:blaze_block", Asp.SOL.of(6));
        on("botania:blaze_quartz", Asp.SOL.of(2));
        on("botania:quartz_blaze", Asp.SOL.of(2));
        on("botania:blaze_quartz_pillar", Asp.SOL.of(2));
        on("botania:blaze_kindling", Asp.SOL.of(2));
        on("botania:sunny_quartz", Asp.SOL.of(3));
        on("botania:quartz_sunny", Asp.SOL.of(3));
        on("botania:sunny_quartz_pillar", Asp.SOL.of(3));
        on("botania:daybloom_motif", Asp.SOL.of(2));
        on("enderio:photovoltaic_cell", Asp.SOL.of(3));
        on("enderio:photovoltaic_plate", Asp.SOL.of(2));
        on("enderio:photovoltaic_composite", Asp.SOL.of(2));
        on("enderio:energetic_photovoltaic_module", Asp.SOL.of(4));
        on("enderio:pulsating_photovoltaic_module", Asp.SOL.of(6));
        on("enderio:vibrant_photovoltaic_module", Asp.SOL.of(9));
        on("oritech:big_solar_panel_block", Asp.SOL.of(8));
        make(MEK_GEN + "solar_panel", base(Asp.VITREUS.of(8), Asp.LUX.of(6), Asp.POTENTIA.of(4)), Asp.SOL.of(3));
        make(MEK_GEN + "solar_generator", base(Asp.METALLUM.of(12), Asp.POTENTIA.of(12), Asp.LUX.of(8), Asp.MACHINA.of(4)), Asp.SOL.of(6));
        make(MEK_GEN + "advanced_solar_generator", base(Asp.METALLUM.of(20), Asp.POTENTIA.of(24), Asp.LUX.of(15), Asp.MACHINA.of(8)), Asp.SOL.of(14));
        make(AVARITIA + "star_fuel", base(Asp.IGNIS.of(20), Asp.POTENTIA.of(20), Asp.LUX.of(8)), Asp.SOL.of(6), Asp.COSMIC.of(3));
        make(AVARITIA + "star_fuel_block", base(Asp.IGNIS.of(100), Asp.POTENTIA.of(100), Asp.LUX.of(40)), Asp.SOL.of(30), Asp.COSMIC.of(12));
    }

    private void solarFlux() {
        int[] cells = {2, 3, 4, 6, 8, 12};
        for (int tier = 0; tier < cells.length; tier++) {
            make(SF + "photovoltaic_cell_" + (tier + 1), base(Asp.LUX.of(6), Asp.POTENTIA.of(4), Asp.VITREUS.of(4)), Asp.SOL.of(cells[tier]));
        }
        int[] panels = {3, 4, 6, 8, 11, 15, 20, 26};
        for (int tier = 0; tier < panels.length; tier++) {
            make(SF + "sp_" + (tier + 1), base(Asp.METALLUM.of(10), Asp.POTENTIA.of(10), Asp.LUX.of(6), Asp.MACHINA.of(3)), Asp.SOL.of(panels[tier]));
        }
        make(SF + "mirror", base(Asp.LUX.of(5), Asp.VITREUS.of(5), Asp.METALLUM.of(3)), Asp.SOL.of(2));
        make(SF + "blazing_coating", base(Asp.IGNIS.of(8), Asp.POTENTIA.of(3)), Asp.SOL.of(3));
        make(SF + "sp_avaritia.neutronium", base(Asp.METALLUM.of(30), Asp.POTENTIA.of(30), Asp.LUX.of(15), Asp.MACHINA.of(10)), Asp.SOL.of(32), Asp.NUCLEAR.of(6), Asp.TERMINUS.of(2));
        make(SF + "sp_avaritia.infinity", base(Asp.METALLUM.of(40), Asp.POTENTIA.of(40), Asp.LUX.of(20), Asp.ALIENIS.of(20)), Asp.SOL.of(40), Asp.COSMIC.of(10), Asp.TERMINUS.of(8));
    }

    private void nuclear() {
        on("#c:ingots/netherite", Asp.NUCLEAR.of(4));
        on("minecraft:ancient_debris", Asp.NUCLEAR.of(3));
        on("minecraft:netherite_scrap", Asp.NUCLEAR.of(2));
        make("minecraft:tnt", base(Asp.IGNIS.of(37), Asp.PERDITIO.of(52), Asp.ALKIMIA.of(18), Asp.TERRA.of(15)), Asp.NUCLEAR.of(1));
        on("minecraft:end_crystal", Asp.NUCLEAR.of(1), Asp.COSMIC.of(5));
        make("mekanism:yellow_cake_uranium", base(Asp.POTENTIA.of(8), Asp.PERDITIO.of(4), Asp.METALLUM.of(4), Asp.ALKIMIA.of(2)), Asp.NUCLEAR.of(3));
        make("mekanism:pellet_plutonium", base(Asp.POTENTIA.of(12), Asp.PERDITIO.of(6), Asp.METALLUM.of(4)), Asp.NUCLEAR.of(5));
        make("mekanism:pellet_polonium", base(Asp.POTENTIA.of(12), Asp.PERDITIO.of(8), Asp.METALLUM.of(4)), Asp.NUCLEAR.of(5));
        make("mekanism:pellet_antimatter", base(Asp.POTENTIA.of(20), Asp.PERDITIO.of(10), Asp.ALIENIS.of(8)), Asp.NUCLEAR.of(6), Asp.COSMIC.of(4));
        make("mekanism:reprocessed_fissile_fragment", base(Asp.POTENTIA.of(8), Asp.PERDITIO.of(8), Asp.METALLUM.of(4)), Asp.NUCLEAR.of(4));
        make("mekanism:radioactive_waste_barrel", base(Asp.METALLUM.of(10), Asp.PERDITIO.of(10), Asp.VITIUM.of(6)), Asp.NUCLEAR.of(6));
        make("mekanism:alloy_atomic", base(Asp.METALLUM.of(12), Asp.PRAECANTATIO.of(6), Asp.ALIENIS.of(6)), Asp.NUCLEAR.of(3));
        make("mekanism:uranium_oxide_bucket", base(Asp.VACUOS.of(5), Asp.METALLUM.of(3), Asp.POTENTIA.of(6)), Asp.NUCLEAR.of(2));
        make("mekanism:uranium_hexafluoride_bucket", base(Asp.VACUOS.of(5), Asp.METALLUM.of(3), Asp.POTENTIA.of(6)), Asp.NUCLEAR.of(2));
        make(MEK_GEN + "fission_fuel_assembly", base(Asp.METALLUM.of(12), Asp.POTENTIA.of(10), Asp.PRAEMUNIO.of(6)), Asp.NUCLEAR.of(6));
        make(MEK_GEN + "fission_reactor_casing", base(Asp.METALLUM.of(12), Asp.PRAEMUNIO.of(8), Asp.ORDO.of(4)), Asp.NUCLEAR.of(2));
        make(MEK_GEN + "control_rod_assembly", base(Asp.METALLUM.of(12), Asp.ORDO.of(6), Asp.PRAEMUNIO.of(4)), Asp.NUCLEAR.of(3));
        make(MEK_GEN + "hohlraum", base(Asp.POTENTIA.of(15), Asp.VITREUS.of(6), Asp.METALLUM.of(4)), Asp.NUCLEAR.of(6), Asp.SOL.of(3));
        on("oritech:uranium_pellet", Asp.NUCLEAR.of(3));
        on("oritech:small_uranium_pellet", Asp.NUCLEAR.of(1));
        on("oritech:plutonium_pellet", Asp.NUCLEAR.of(5));
        on("oritech:small_plutonium_pellet", Asp.NUCLEAR.of(2));
        on("oritech:uranium_crystal", Asp.NUCLEAR.of(3));
        on("oritech:reactor_rod", Asp.NUCLEAR.of(3));
        on("oritech:reactor_double_rod", Asp.NUCLEAR.of(6));
        on("oritech:reactor_quad_rod", Asp.NUCLEAR.of(12));
        on("oritech:reactor_controller", Asp.NUCLEAR.of(4));
        on("oritech:atomic_forge_block", Asp.NUCLEAR.of(3));
        make("create:crushed_raw_uranium", base(Asp.TERRA.of(2), Asp.METALLUM.of(8), Asp.POTENTIA.of(8), Asp.PERDITIO.of(4)), Asp.NUCLEAR.of(3));
        make("industrialforegoing:infinity_nuke", base(Asp.PERDITIO.of(40), Asp.IGNIS.of(40), Asp.POTENTIA.of(40), Asp.MACHINA.of(20)), Asp.NUCLEAR.of(12), Asp.TERMINUS.of(4));
        on(AVARITIA + "neutron_ingot", Asp.NUCLEAR.of(3));
        make(AVARITIA + "neutron_nugget", base(Asp.METALLUM.of(1), Asp.POTENTIA.of(1)), Asp.NUCLEAR.of(1));
        make(AVARITIA + "neutron_pile", base(Asp.METALLUM.of(2), Asp.POTENTIA.of(2)), Asp.NUCLEAR.of(1));
        make(AVARITIA + "neutron", base(Asp.METALLUM.of(80), Asp.POTENTIA.of(80)), Asp.NUCLEAR.of(20), Asp.TERMINUS.of(12));
    }

    private void cosmic() {
        on("#c:nether_stars", Asp.COSMIC.of(8));
        on("minecraft:ender_eye", Asp.COSMIC.of(3));
        on("#c:ender_pearls", Asp.COSMIC.of(2));
        on("minecraft:dragon_egg", Asp.COSMIC.of(12));
        on("minecraft:dragon_breath", Asp.COSMIC.of(4));
        on("minecraft:shulker_shell", Asp.COSMIC.of(3));
        on("minecraft:elytra", Asp.COSMIC.of(5));
        on("minecraft:chorus_fruit", Asp.COSMIC.of(1));
        on("minecraft:popped_chorus_fruit", Asp.COSMIC.of(1));
        on("minecraft:echo_shard", Asp.COSMIC.of(4));
        on("#c:gems/amethyst", Asp.COSMIC.of(1));
        on("thaumaturge:void_seed", Asp.COSMIC.of(3));
        on("botania:frozen_star", Asp.COSMIC.of(2));
        on("botania:ender_eye_block", Asp.COSMIC.of(4));
        on("enderio:ender_crystal", Asp.COSMIC.of(3));
        on("enderio:ender_crystal_powder", Asp.COSMIC.of(1));
        on("enderio:powdered_ender_pearl", Asp.COSMIC.of(1));
        on("enderio:grains_of_infinity", Asp.COSMIC.of(3));
        on("enderio:end_steel_ingot", Asp.COSMIC.of(2));
        on("oritech:enderic_compound", Asp.COSMIC.of(2));
        on("oritech:enderic_lens", Asp.COSMIC.of(3));
        on("oritech:black_hole_block", Asp.COSMIC.of(8), Asp.TERMINUS.of(2));
        make("create:shadow_steel", base(Asp.METALLUM.of(12), Asp.TENEBRAE.of(12)), Asp.COSMIC.of(3));
        on(AVARITIA + "crystal_matrix_ingot", Asp.COSMIC.of(6));
        make(AVARITIA + "crystal_matrix", base(Asp.POTENTIA.of(54), Asp.VITREUS.of(216), Asp.TERMINUS.of(6)), Asp.COSMIC.of(40));
        make(AVARITIA + "endest_pearl", base(Asp.ALIENIS.of(20), Asp.MOTUS.of(20), Asp.PRAECANTATIO.of(5)), Asp.COSMIC.of(10), Asp.TERMINUS.of(4));
        on(AVARITIA + "singularity", Asp.COSMIC.of(3));
        make(AVARITIA + "eternal_singularity", base(Asp.METALLUM.of(100), Asp.ALIENIS.of(20)), Asp.COSMIC.of(10), Asp.TERMINUS.of(12));
    }

    private void terminus() {
        on(AVARITIA + "infinity_ingot", Asp.COSMIC.of(16));
        make(AVARITIA + "infinity_nugget", base(Asp.ALIENIS.of(7)), Asp.TERMINUS.of(2), Asp.COSMIC.of(2));
        make(AVARITIA + "infinity", base(Asp.ALIENIS.of(400)), Asp.TERMINUS.of(108), Asp.COSMIC.of(108));
        on(AVARITIA + "infinity_catalyst", Asp.COSMIC.of(4));
        on(AVARITIA + "infinity_sword", Asp.COSMIC.of(20));
        for (String tool : List.of("pickaxe", "axe", "shovel", "hoe")) {
            make(AVARITIA + "infinity_" + tool, base(Asp.ALIENIS.of(60), Asp.METALLUM.of(40), Asp.INSTRUMENTUM.of(12)), Asp.TERMINUS.of(24), Asp.COSMIC.of(16));
        }
        for (String weapon : List.of("mace", "trident", "bow", "crossbow")) {
            make(AVARITIA + "infinity_" + weapon, base(Asp.ALIENIS.of(60), Asp.METALLUM.of(40), Asp.AVERSIO.of(20)), Asp.TERMINUS.of(24), Asp.COSMIC.of(16));
        }
        for (String armor : List.of("helmet", "chestplate", "pants", "boots")) {
            make(AVARITIA + "infinity_" + armor, base(Asp.ALIENIS.of(40), Asp.PRAEMUNIO.of(40), Asp.METALLUM.of(30)), Asp.TERMINUS.of(20), Asp.COSMIC.of(12));
        }
        make(AVARITIA + "infinity_shield", base(Asp.ALIENIS.of(40), Asp.PRAEMUNIO.of(50), Asp.METALLUM.of(30)), Asp.TERMINUS.of(20), Asp.COSMIC.of(12));
        make(AVARITIA + "infinity_elytra", base(Asp.VOLATUS.of(40), Asp.MOTUS.of(30), Asp.ALIENIS.of(40)), Asp.TERMINUS.of(20), Asp.COSMIC.of(20));
        make(AVARITIA + "infinity_totem", base(Asp.VICTUS.of(40), Asp.ORDO.of(20), Asp.ALIENIS.of(20)), Asp.TERMINUS.of(12), Asp.COSMIC.of(10));
        make(AVARITIA + "infinity_ring", base(Asp.PRAECANTATIO.of(30), Asp.ALIENIS.of(30)), Asp.TERMINUS.of(10), Asp.COSMIC.of(8));
        on(AVARITIA + "neutron_collector", Asp.NUCLEAR.of(6), Asp.COSMIC.of(2));
        make(AVARITIA + "neutron_compressor", base(Asp.METALLUM.of(64), Asp.MACHINA.of(64), Asp.PRAEMUNIO.of(32)), Asp.TERMINUS.of(5), Asp.NUCLEAR.of(6), Asp.COSMIC.of(2));
        String[] tiers = {"dense", "denser", "densest"};
        int[] terminus = {8, 12, 18};
        int[] nuclear = {10, 14, 20};
        int[] cosmic = {4, 6, 10};
        for (int tier = 0; tier < tiers.length; tier++) {
            make(AVARITIA + tiers[tier] + "_neutron_collector", base(Asp.METALLUM.of(64), Asp.POTENTIA.of(64), Asp.MACHINA.of(64)),
                    Asp.TERMINUS.of(terminus[tier]), Asp.NUCLEAR.of(nuclear[tier]), Asp.COSMIC.of(cosmic[tier]));
            make(AVARITIA + tiers[tier] + "_neutron_compressor", base(Asp.METALLUM.of(64), Asp.MACHINA.of(64), Asp.PRAEMUNIO.of(32)),
                    Asp.TERMINUS.of(terminus[tier]), Asp.NUCLEAR.of(nuclear[tier]), Asp.COSMIC.of(cosmic[tier]));
        }
    }
}
