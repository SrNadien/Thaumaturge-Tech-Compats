package nadiendev.ThaumaturgeTechCompats.emc;

import java.util.List;

public final class EmcValues {
    public static final String MOD = "thaumaturge";
    public static final String OWN = "thaumaturgetechcompats";

    public static final List<Value> METALS = List.of(
            new Value(MOD + ":ingot_thaumium", 1024),
            new Value(MOD + ":ingot_brass", 4096),
            new Value(MOD + ":ingot_void", 24576));

    public static final List<Value> MATERIALS = List.of(
            new Value(MOD + ":amber", 256),
            new Value(MOD + ":quicksilver", 512),
            new Value(MOD + ":shimmerleaf", 512),
            new Value(MOD + ":salis_mundus", 64),
            new Value(MOD + ":cinderpearl", 768),
            new Value(MOD + ":rare_earth", 512),
            new Value(MOD + ":void_seed", 4096));

    public static final List<Value> PLANETARY = List.of(
            new Value(OWN + ":copper_planetary_endest_pearl", 4096),
            new Value(OWN + ":copper_star_crucifix", 4096));

    public static final List<String> EXCLUDED = List.of(
            OWN + ":netherite_planetary_endest_pearl",
            OWN + ":void_metal_planetary_endest_pearl",
            OWN + ":infinity_planetary_endest_pearl",
            OWN + ":creative_planetary_endest_pearl",
            OWN + ":netherite_star_crucifix",
            OWN + ":void_metal_star_crucifix",
            OWN + ":infinity_star_crucifix",
            OWN + ":creative_star_crucifix",
            MOD + ":creative_flux_sponge",
            MOD + ":creative_node_placer",
            MOD + ":thaumonomicon_cheat",
            MOD + ":jar_creative",
            MOD + ":jar_node",
            MOD + ":wand",
            MOD + ":wand_staff");

    private EmcValues() {
    }

    public record Value(String item, long emc) {
    }
}
