package nadiendev.ThaumaturgeTechCompats.compats.create;

import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.VariantKind;

import java.util.ArrayList;
import java.util.List;

public final class CreateToggles {
    public static final String TECH = "create_tech";
    public static final String SEAL = "create_seal";
    public static final String CASING_BRASS = "create_casing_brass";
    public static final String CASING_THAUMIUM = "create_casing_thaumium";
    public static final String CASING_VOID = "create_casing_void";
    public static final String NODE_BOOST = "create_node_boost";
    public static final String VARIANTS = "create_variants";
    public static final String JEI = "create_jei";
    public static final String VARIANT_PRESS = "create_variant_press";
    public static final String VARIANT_MIXER = "create_variant_mixer";
    public static final String VARIANT_SAW = "create_variant_saw";
    public static final String VARIANT_DRILL = "create_variant_drill";
    public static final String VARIANT_DEPLOYER = "create_variant_deployer";
    public static final String VARIANT_MILLSTONE = "create_variant_millstone";
    public static final String VARIANT_BASIN = "create_variant_basin";
    public static final String VARIANT_SPOUT = "create_variant_spout";
    public static final String VARIANT_ITEM_DRAIN = "create_variant_item_drain";
    public static final String VARIANT_DEPOT = "create_variant_depot";
    public static final String THAUMMOTOR = "create_thaummotor";

    public static final List<String> ALL = all();

    private CreateToggles() {
    }

    private static List<String> all() {
        List<String> ids = new ArrayList<>(List.of(TECH, SEAL, CASING_BRASS, CASING_THAUMIUM, CASING_VOID, NODE_BOOST, VARIANTS, JEI, THAUMMOTOR));
        for (VariantKind kind : VariantKind.values()) {
            ids.add(kind.toggle());
        }
        return List.copyOf(ids);
    }

    public static boolean owns(String id) {
        return ALL.contains(id);
    }

    public static boolean enabled(String id) {
        if (TECH.equals(id)) {
            return CreateConfig.toggle(TECH);
        }
        return CreateConfig.toggle(TECH) && CreateConfig.toggle(id);
    }

    public static boolean variant(VariantKind kind, CasingTier tier) {
        return enabled(VARIANTS) && enabled(kind.toggle()) && enabled(tier.toggle());
    }

    public static String description(String id) {
        return switch (id) {
            case TECH -> "The whole Create integration of the Tech category: Create Seal, node casings and the node-quickened machine variants. Needs a game restart for the creative tab.";
            case SEAL -> "The Create Seal that lets golems feed, empty and operate Create machines.";
            case CASING_BRASS -> "The alchemical brass node casing and the alchemical brass machine variants.";
            case CASING_THAUMIUM -> "The thaumium node casing and the thaumium machine variants.";
            case CASING_VOID -> "The void metal node casing and the void metal machine variants.";
            case NODE_BOOST -> "Machine variants run faster near an aura node. When off they behave like the plain Create machines.";
            case VARIANTS -> "All the casing variants of Create machines and blocks: recipes, research and creative tab. Needs a game restart for the creative tab.";
            case JEI -> "The machine variants appear as catalysts in the JEI categories of their plain machine.";
            case VARIANT_PRESS -> "The Mechanical Press variants.";
            case VARIANT_MIXER -> "The Mechanical Mixer variants.";
            case VARIANT_SAW -> "The Mechanical Saw variants.";
            case VARIANT_DRILL -> "The Mechanical Drill variants.";
            case VARIANT_DEPLOYER -> "The Deployer variants.";
            case VARIANT_MILLSTONE -> "The Millstone variants.";
            case VARIANT_BASIN -> "The Basin variants.";
            case VARIANT_SPOUT -> "The Spout variants.";
            case VARIANT_ITEM_DRAIN -> "The Item Drain variants.";
            case VARIANT_DEPOT -> "The Depot variants.";
            case THAUMMOTOR -> "The Thaummotor, a Create generator that gives stress while it has a node, a jarred node, a flux jar, a rift jar or a flux rift near it. Needs a game restart.";
            default -> "";
        };
    }
}
