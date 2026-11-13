package nadiendev.ThaumaturgeTechCompats.compats.jade;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.resources.ResourceLocation;

public enum JadeFamily {
    TIER("tier", "tier_line", true),
    STABILIZER("stabilizers", "node_stabilizers", true),
    SMELTER("smelters", "essentia_smelters", true),
    TRANSDUCER("transducers", "node_transducers", true),
    FURNACE("furnaces", "alchemical_furnaces", true),
    BRAIN_BOX("mnemonic_matrices", "mnemonic_matrices", true),
    CHARGER("workbench_chargers", "workbench_chargers", true),
    JAR("jars", "essentia_jars", false);

    private final String option;
    private final String configKey;
    private final boolean configurable;

    JadeFamily(String option, String configKey, boolean configurable) {
        this.option = option;
        this.configKey = configKey;
        this.configurable = configurable;
    }

    public String option() {
        return option;
    }

    public String configKey() {
        return configKey;
    }

    public boolean configurable() {
        return configurable;
    }

    public ResourceLocation optionId() {
        return ThaumaturgeTechCompats.id(option);
    }

    public static JadeFamily byOption(String option) {
        for (JadeFamily family : values()) {
            if (family.option.equals(option)) {
                return family;
            }
        }
        return null;
    }
}
