package nadiendev.ThaumaturgeTechCompats.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons.IndustrialForegoingAddonsIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight.FarmersDelightTaintedIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaIntegration;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools.MekanismToolIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools.MekanismToolTier;
import nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.AvaritiaMachinesIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical.MagicalUneartherIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.OritechFeatures;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedIntegration;
import nadiendev.ThaumaturgeTechCompats.emc.EmcIntegration;
import nadiendev.ThaumaturgeTechCompats.essences.EssencesIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.core.CoreIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.gear.GearIntegration;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import nadiendev.ThaumaturgeTechCompats.planetary.salis.SalisIntegration;
import nadiendev.ThaumaturgeTechCompats.purity.PurityToggles;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public final class CompatToggles {
    public static final String FILE_NAME = "thaumaturgetechcompats-compats.toml";
    public static final ModConfigSpec SPEC;

    public static final String CREATE = "create";
    public static final String NODE_POWER = "node_power";
    public static final String JEI_CATALYSTS = "jei_catalysts";
    public static final String JEI_INFO = "jei_info";
    public static final String TAINTED_FOOD = "tainted_food";
    public static final String MEKANISM = "mekanism";
    public static final String INDUSTRIAL_FOREGOING = "industrialforegoing";
    public static final String SOLAR_FLUX = "solarflux";
    public static final String PURIFICATION_POTION = "purification_potion";
    public static final String ORITECH = OritechFeatures.MOD_ID;
    public static final String MEKANISM_X2 = "mekanism_x2";
    public static final String MEKANISM_X3 = "mekanism_x3";
    public static final String MEKANISM_X4 = "mekanism_x4";
    public static final String MEKANISM_X5 = "mekanism_x5";
    public static final String MEKANISM_SALIS = "mekanism_salis";
    public static final String MEKANISM_CLUSTERS = "mekanism_clusters";
    public static final String FTB_UNEARTHED = "ftbunearthed";
    public static final String FTB_UNEARTHED_CORE_RECIPE = "ftbunearthed_core_recipe";
    public static final String FTB_UNEARTHED_BRUSH_BRASS = "ftbunearthed_brush_brass";
    public static final String FTB_UNEARTHED_BRUSH_THAUMIUM = "ftbunearthed_brush_thaumium";
    public static final String FTB_UNEARTHED_BRUSH_VOID = "ftbunearthed_brush_void";
    public static final String FTB_UNEARTHED_LOOT_VIS_CRYSTALS = "ftbunearthed_loot_vis_crystals";
    public static final String FTB_UNEARTHED_LOOT_AMBER = "ftbunearthed_loot_amber";
    public static final String FTB_UNEARTHED_LOOT_RAW_CINNABAR = "ftbunearthed_loot_raw_cinnabar";
    public static final String FORCECRAFT = "forcecraft";
    public static final String FORCECRAFT_REPAIR = "forcecraft_repair";
    public static final String FORCECRAFT_ASPECTS = "forcecraft_aspects";
    public static final String FORCECRAFT_FLASK = "forcecraft_flask";
    public static final String TAG_ASPECTS = "tag_aspects";
    public static final String TAG_ASPECTS_INGOTS = "tag_aspects_ingots";
    public static final String TAG_ASPECTS_NUGGETS = "tag_aspects_nuggets";
    public static final String TAG_ASPECTS_ORES = "tag_aspects_ores";
    public static final String TAG_ASPECTS_RAW_MATERIALS = "tag_aspects_raw_materials";
    public static final String TAG_ASPECTS_GEMS = "tag_aspects_gems";
    public static final String TAG_ASPECTS_DUSTS = "tag_aspects_dusts";
    public static final String TAG_ASPECTS_STORAGE_BLOCKS = "tag_aspects_storage_blocks";
    public static final String TAG_ASPECTS_PARTS = "tag_aspects_parts";
    public static final String TAG_ASPECTS_INTERMEDIATES = "tag_aspects_intermediates";
    public static final String TAG_ASPECTS_FOODS = "tag_aspects_foods";
    public static final String ORITECH_ASPECTS = "oritech_aspects";
    public static final String ORITECH_PROCESSING = "oritech_processing";
    public static final String ENDERIO = "enderio";
    public static final String ENDERIO_ASPECTS = "enderio_aspects";
    public static final String ENDERIO_GRINDING_BALLS = "enderio_grinding_balls";
    public static final String ORITECH_PULVERIZER = "oritech_processing_pulverizer";
    public static final String ORITECH_GRINDER = "oritech_processing_grinder";
    public static final String ORITECH_BONUS = "oritech_processing_bonus";
    public static final String AURA_CONVERTER = "aura_converter";
    public static final String AURA_CONVERTER_BRASS = "aura_converter_brass";
    public static final String AURA_CONVERTER_THAUMIUM = "aura_converter_thaumium";
    public static final String AURA_CONVERTER_VOID = "aura_converter_void";
    public static final String MAGIC_LASER_DRILL = "magic_laser_drill";
    public static final String MAGIC_INFINITY_DRILL = "magic_infinity_drill";
    public static final String SOLAR_ALCHEMICAL = "solar_alchemical";
    public static final String SOLAR_ALCHEMICAL_BRASS = "solar_alchemical_brass";
    public static final String SOLAR_THAUMIUM = "solar_thaumium";
    public static final String SOLAR_VOID_METAL = "solar_void_metal";

    private static final Map<String, ModConfigSpec.BooleanValue> VALUES = new LinkedHashMap<>();

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("compats");
        define(builder, CREATE, "Recipes for the Thaumaturge clusters in Create.");
        define(builder, NODE_POWER, "The node power base: machines of other mods that use nodes, node jars, flux jars and flux rifts as a power source, the Flux Jar and its research. Needs a game restart.");
        for (FluxTier tier : FluxTier.values()) {
            define(builder, tier.toggle(), "The devices of the Dimensional Flux network of this tier. Needs a game restart.");
        }
        for (String id : CoreIntegration.toggleIds()) {
            define(builder, id, "A piece of the Dimensional Flux Storage Core. Needs a game restart.");
        }
        define(builder, GearIntegration.SIPHON_TOGGLE, "The Portable Void Siphon. Needs a game restart.");
        define(builder, GearIntegration.CANNON_TOGGLE, "The Discharge Cannon. Needs a game restart.");
        for (String id : SophisticatedIntegration.toggleIds()) {
            define(builder, id, "A feature of the Sophisticated Backpacks and Sophisticated Storage compat. Needs a game restart.");
        }
        define(builder, EmcIntegration.TOGGLE, "ProjectE integration: EMC for Thaumaturge recipes and base values. Recipes apply when data packs reload; base values need a game restart.");
        define(builder, EssencesIntegration.TOGGLE, "The Tech Compats essences (sol, nuclear, cosmic and terminus) on base items, inherited through recipes. Needs a game restart.");
        define(builder, SalisIntegration.TOGGLE, "The Salis Mundus variants made of Avaritia materials. Needs a game restart.");
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            define(builder, tier.pearlToggle(), "The Planetary Endest Pearl of this tier. Needs a game restart.");
            define(builder, tier.crucifixToggle(), "The Star Crucifix of this tier. Needs a game restart.");
        }
        define(builder, JEI_CATALYSTS, "The machines, stations and tools of this addon appear as catalysts in the JEI categories of the machines they match. Needs a game restart.");
        define(builder, JEI_INFO, "Every block and item of this addon has an information page in JEI. Needs a game restart.");
        for (String id : BotaniaIntegration.toggleIds()) {
            define(builder, id, "Botania Revived integration: higher tiers of mana spreaders, the Botania wand rods and caps and the aspects of Botania items. Needs a game restart.");
        }
        define(builder, InfusionIntegration.MATRIX_TOGGLE, "The Crystal Matrix, Neutronium and Infinity variants of the Runic Matrix, with their own instability, recipes and research. Needs a game restart.");
        define(builder, InfusionIntegration.STONE_TOGGLE, "The Crystal Matrix, Neutronium and Infinity variants of the Infusion Cost and Speed Stones, with their recipes and research. Needs a game restart.");
        define(builder, InfusionIntegration.ALTAR_TOGGLE, "The Crystal Matrix, Neutronium and Infinity pillars and pedestals of the infusion altar, with their recipes and research. Needs a game restart.");
        define(builder, VoidMinersMagicalIntegration.TOGGLE_MINER, "The Thaumic Miner for Void Miners Remastered, which only works while it has a magical power source. Needs a game restart.");
        define(builder, VoidMinersMagicalIntegration.TOGGLE_SOLAR, "The Thaumic Solar Panel for Void Miners Remastered, which only generates energy while it has a magical power source. Needs a game restart.");
        for (String id : VoidMinersMagicalIntegration.toggleIds()) {
            define(builder, id, "A tier of the Thaumic Miner or Thaumic Solar Panel for Void Miners Remastered, with its recipe, frame and research. Needs a game restart.");
        }
        define(builder, MekanismToolIntegration.TOGGLE, "Energy tools for Mekanism: Thaumium, Alchemical Brass and Void Metal tools that run on Mekanism energy, with their infusion recipes and research. Needs a game restart.");
        for (MekanismToolTier tier : MekanismToolTier.values()) {
            define(builder, tier.toggle(), "The energized tools of this tier for Mekanism. Needs a game restart.");
        }
        define(builder, AvaritiaMachinesIntegration.TOGGLE, "Higher tiers of the Avaritia Neutron Collector and Neutronium Compressor, with their infusion recipes and research. Needs a game restart.");
        define(builder, TAINTED_FOOD, "Tainted versions of vanilla and Avaritia foods, brewed in the crucible, with their research. Needs a game restart.");
        for (String id : FarmersDelightTaintedIntegration.toggleIds()) {
            define(builder, id, "Tainted Farmers Delight foods or knives, with their crucible and infusion recipes and research. Needs a game restart.");
        }
        define(builder, MEKANISM, "Recipes for the Thaumaturge clusters in Mekanism.");
        define(builder, INDUSTRIAL_FOREGOING, "Laser Drill ores for the Thaumaturge clusters and ores in Industrial Foregoing.");
        defineOff(builder, MEKANISM_X2, "Mekanism x2 route: Enrichment Chamber, ore to 2 clusters. Off by default: it shares machine and input with Mekanism own ore recipes.");
        defineOff(builder, MEKANISM_X3, "Mekanism x3 route: Purification Chamber with oxygen, ore to 3 clusters. Off by default: it shares machine and input with Mekanism own ore recipes.");
        defineOff(builder, MEKANISM_X4, "Mekanism x4 route: Chemical Injection Chamber with hydrogen chloride, ore to 4 clusters. Off by default: it shares machine and input with Mekanism own ore recipes.");
        define(builder, MEKANISM_X5, "Mekanism x5 route: dissolution, washing and crystallization, then every crystal becomes a cluster in the Enrichment Chamber.");
        define(builder, MEKANISM_SALIS, "Mekanism Salis Mundus route: Salis Mundus as bio infusion in the Metallurgic Infuser, ore to 2 clusters.");
        define(builder, MEKANISM_CLUSTERS, "The Osmium and Uranium clusters, their smelting and crucible recipes. Needs a game restart.");
        define(builder, FTB_UNEARTHED, "The brass, thaumium and void metal brushes for FTB Unearthed, their research and the Thaumaturge loot they unearth. Needs a game restart.");
        define(builder, FTB_UNEARTHED_CORE_RECIPE, "A crafting table recipe for the Unearther of FTB Unearthed, which has none of its own: iron, glass, hoppers, a brush and a piston. Applies when data packs reload.");
        define(builder, FTB_UNEARTHED_BRUSH_BRASS, "The brass brush for FTB Unearthed, its recipe, research and loot. Needs a game restart.");
        define(builder, FTB_UNEARTHED_BRUSH_THAUMIUM, "The thaumium brush for FTB Unearthed, its recipe, research and loot. Needs the brass brush. Needs a game restart.");
        define(builder, FTB_UNEARTHED_BRUSH_VOID, "The void metal brush for FTB Unearthed, its recipe, research and loot. Needs the thaumium brush. Needs a game restart.");
        define(builder, FTB_UNEARTHED_LOOT_VIS_CRYSTALS, "The primal vis crystals that the Thaumaturge brushes unearth from sand and gravel in FTB Unearthed.");
        define(builder, FTB_UNEARTHED_LOOT_AMBER, "The amber that the Thaumaturge brushes unearth from sand and gravel in FTB Unearthed.");
        define(builder, FTB_UNEARTHED_LOOT_RAW_CINNABAR, "The raw cinnabar that the Thaumaturge brushes unearth from sand and gravel in FTB Unearthed.");
        for (String id : MagicalUneartherIntegration.toggleIds()) {
            define(builder, id, "A magical Unearther tier for FTB Unearthed that runs on node power instead of food, with its recipe and research. Needs a game restart.");
        }
        define(builder, FORCECRAFT, "Everything the addon adds for ForceCraft: the Repair infusion, the aspects of its items and mobs and the Force Flask rules. Needs a game restart.");
        define(builder, TAG_ASPECTS, "Aspects for every item of a common c: tag (ingots, ores, gems, dusts and so on), so any mod gets them by material. Applies when data packs reload.");
        define(builder, TAG_ASPECTS_INGOTS, "Tag aspects for c:ingots/*.");
        define(builder, TAG_ASPECTS_NUGGETS, "Tag aspects for c:nuggets/*.");
        define(builder, TAG_ASPECTS_ORES, "Tag aspects for c:ores/*.");
        define(builder, TAG_ASPECTS_RAW_MATERIALS, "Tag aspects for c:raw_materials/*.");
        define(builder, TAG_ASPECTS_GEMS, "Tag aspects for c:gems/*.");
        define(builder, TAG_ASPECTS_DUSTS, "Tag aspects for c:dusts/*.");
        define(builder, TAG_ASPECTS_STORAGE_BLOCKS, "Tag aspects for c:storage_blocks/*, raw blocks included.");
        define(builder, TAG_ASPECTS_PARTS, "Tag aspects for c:plates/*, c:rods/*, c:gears/* and c:wires/*.");
        define(builder, TAG_ASPECTS_INTERMEDIATES, "Tag aspects for c:clumps/*, c:dirty_dusts/*, c:shards/* and c:crystals/*.");
        define(builder, TAG_ASPECTS_FOODS, "Tag aspects for crops, seeds, mushrooms, fruits, vegetables, meats and fish.");
        define(builder, ORITECH_ASPECTS, "Aspects for the items and blocks of Oritech. Applies when data packs reload.");
        define(builder, ORITECH_PROCESSING, "Oritech Pulverizer and Grinder recipes for the Thaumaturge clusters and ores. Applies when data packs reload.");
        define(builder, ENDERIO, "Everything the addon adds for Ender IO. Turning it off turns every Ender IO feature off. Needs a game restart.");
        define(builder, ENDERIO_ASPECTS, "Thaumcraft aspects for the items and machines of Ender IO.");
        define(builder, ENDERIO_GRINDING_BALLS, "The brass, thaumium and void metal grinding balls for the SAG Mill of Ender IO. Needs a game restart.");
        define(builder, EnderIOIntegration.CONDUITS, "The thaumium, alchemical brass and void metal energy, item and fluid conduits for Ender IO. Needs a game restart.");
        define(builder, EnderIOIntegration.CAPACITORS, "The thaumium, alchemical brass and void metal capacitors for Ender IO machines. Needs a game restart.");
        define(builder, EnderIOIntegration.CAPACITOR_BANKS, "The thaumium, alchemical brass and void metal capacitor banks for Ender IO. Needs a game restart.");
        define(builder, EnderIOIntegration.WAND_PARTS, "The wand caps and wand rods made of Ender IO materials. Needs a game restart.");
        define(builder, ORITECH_PULVERIZER, "The Oritech Pulverizer recipes for the Thaumaturge clusters and ores.");
        define(builder, ORITECH_GRINDER, "The Oritech Grinder recipes for the Thaumaturge clusters and ores.");
        define(builder, ORITECH_BONUS, "One extra raw material from every Thaumaturge cluster or ore in the Oritech Pulverizer and Grinder. Off gives the same yield as Oritech ores.");
        define(builder, MAGIC_LASER_DRILL, "The Magical Laser Drill, Ore Laser Base and Fluid Laser Base of Industrial Foregoing, their research and their recipes.");
        define(builder, MAGIC_INFINITY_DRILL, "The Magical Infinity Drill of Industrial Foregoing, its research and its recipe. Needs a game restart.");
        define(builder, ORITECH, "Everything the addon adds for Oritech: tiered ores, machine addons, their recipes and research. Needs a game restart.");
        define(builder, SOLAR_FLUX, "The Thaumcraft solar panels for Solar Flux Reborn. Needs a game restart.");
        builder.pop();

        builder.push("industrial_foregoing_addons");
        define(builder, IndustrialForegoingAddonsIntegration.TOGGLE, "The brass, thaumium and void metal addons for Industrial Foregoing machines. Needs a game restart.");
        for (String id : IndustrialForegoingAddonsIntegration.toggleIds()) {
            define(builder, id, "This Industrial Foregoing addon and its recipe. Needs a game restart.");
        }
        builder.pop();

        builder.push("oritech");
        for (String id : OritechFeatures.all()) {
            define(builder, id, "Oritech integration feature. Needs a game restart.");
        }
        builder.pop();

        builder.push("purity");
        define(builder, PURIFICATION_POTION, "The Purification Splash Potion that cures infected creatures, with its recipe and research.");
        define(builder, PurityToggles.BIOME_POTION, "The Biome Purification Splash Potion, which cleans flux-corrupted biomes and blocks, with its recipe and research. Needs the Purification Splash Potion.");
        define(builder, PurityToggles.CLARITY_POTION, "The Potion of Clarity, which removes temporary warp, with its recipe and research.");
        define(builder, PurityToggles.ABSOLUTION_POTION, "The Potion of Absolution, which removes permanent warp, with its recipe and research. Needs the Potion of Clarity.");
        builder.pop();

        builder.push("forcecraft");
        define(builder, FORCECRAFT_REPAIR, "The Repair infusion of the Force Infuser: Order Shard as reagent, tools, armor and rods repair themselves with vis from the aura. Needs a game restart.");
        define(builder, FORCECRAFT_ASPECTS, "Thaumaturge aspects for the items, blocks and mobs of ForceCraft.");
        define(builder, FORCECRAFT_FLASK, "Keeps the bosses and tainted anchors of Thaumaturge out of the Force Flask.");
        builder.pop();

        builder.push("aura_converter");
        define(builder, AURA_CONVERTER, "The Aura Converters: machines that turn vis from the aura of the chunk into FE, with their recipes and research.");
        define(builder, AURA_CONVERTER_BRASS, "The alchemical brass Aura Converter, its recipe and research. Needs the thaumium Aura Converter.");
        define(builder, AURA_CONVERTER_THAUMIUM, "The thaumium Aura Converter, its recipe and research.");
        define(builder, AURA_CONVERTER_VOID, "The void metal Aura Converter, its recipe and research. Needs the alchemical brass Aura Converter.");
        builder.pop();

        builder.push("solar_panels");
        define(builder, SOLAR_ALCHEMICAL, "The alchemical solar panel. Needs a game restart.");
        define(builder, SOLAR_ALCHEMICAL_BRASS, "The alchemical brass solar panel. Needs a game restart.");
        define(builder, SOLAR_THAUMIUM, "The thaumium solar panel. Needs a game restart.");
        define(builder, SOLAR_VOID_METAL, "The void metal solar panel. Needs a game restart.");
        builder.pop();

        SPEC = builder.build();
    }

    private CompatToggles() {
    }

    private static void defineOff(ModConfigSpec.Builder builder, String id, String comment) {
        VALUES.put(id, builder.comment(comment).define(id, false));
    }

    private static void define(ModConfigSpec.Builder builder, String id, String comment) {
        VALUES.put(id, builder.comment(comment).define(id, true));
    }

    public static boolean enabled(String id) {
        if (CreateToggles.owns(id)) {
            return CreateToggles.enabled(id);
        }
        ModConfigSpec.BooleanValue value = VALUES.get(id);
        if (value == null) {
            return true;
        }
        if (SPEC.isLoaded()) {
            return value.get();
        }
        return readEarly(id, value.getDefault());
    }

    private static boolean readEarly(String id, boolean fallback) {
        Path path = FMLPaths.CONFIGDIR.get().resolve(FILE_NAME);
        if (!Files.isRegularFile(path)) {
            return fallback;
        }
        try (FileConfig config = FileConfig.of(path)) {
            config.load();
            for (String section : new String[]{"compats", "solar_panels", "industrial_foregoing_addons", "oritech", "purity", "forcecraft", "aura_converter"}) {
                Object found = config.get(section + "." + id);
                if (found instanceof Boolean bool) {
                    return bool;
                }
            }
        } catch (RuntimeException exception) {
            return fallback;
        }
        return fallback;
    }
}
