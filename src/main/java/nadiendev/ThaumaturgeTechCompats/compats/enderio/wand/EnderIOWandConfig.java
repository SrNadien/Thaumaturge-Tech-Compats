package nadiendev.ThaumaturgeTechCompats.compats.enderio.wand;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class EnderIOWandConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-enderio-wand-parts.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<EnderIOWandMaterial, ModConfigSpec.IntValue> ROD_CAPACITY = new EnumMap<>(EnderIOWandMaterial.class);
    private static final Map<EnderIOWandMaterial, ModConfigSpec.IntValue> ROD_CRAFT_COST = new EnumMap<>(EnderIOWandMaterial.class);
    private static final Map<EnderIOWandMaterial, ModConfigSpec.DoubleValue> CAP_BASE_COST = new EnumMap<>(EnderIOWandMaterial.class);
    private static final Map<EnderIOWandMaterial, ModConfigSpec.DoubleValue> CAP_SPECIAL_COST = new EnumMap<>(EnderIOWandMaterial.class);
    private static final Map<EnderIOWandMaterial, ModConfigSpec.IntValue> CAP_CRAFT_COST = new EnumMap<>(EnderIOWandMaterial.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("rods");
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            builder.push(material.id());
            ROD_CAPACITY.put(material, builder.comment("Vis capacity of the rod, in the same units as the rods of Thaumaturge. A wooden rod has 25, a greatwood rod 50 and a silverwood rod 100.")
                    .defineInRange("capacity", material.defaultCapacity(), 1, 10_000_000));
            ROD_CRAFT_COST.put(material, builder.comment("Craft cost factor of the rod. The vis an assembled wand costs to make is this times the craft cost of its cap.")
                    .defineInRange("craft_cost", material.defaultRodCraftCost(), 1, 100_000));
            builder.pop();
        }
        builder.pop();
        builder.push("caps");
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            builder.push(material.id());
            CAP_BASE_COST.put(material, builder.comment("Multiplier of the vis every spell costs. 1.0 is the full price, 0.5 is half and 0.0 is free. The thaumium cap is 0.9 and the void cap 0.8.")
                    .defineInRange("cost_modifier", (double) material.defaultBaseCostModifier(), 0.0D, 10.0D));
            CAP_SPECIAL_COST.put(material, builder.comment("Multiplier of the vis cost for the special aspects of this cap, instead of the cost modifier.")
                    .defineInRange("special_cost_modifier", (double) material.defaultSpecialCostModifier(), 0.0D, 10.0D));
            CAP_CRAFT_COST.put(material, builder.comment("Craft cost factor of the cap. The vis an assembled wand costs to make is this times the craft cost of its rod.")
                    .defineInRange("craft_cost", material.defaultCapCraftCost(), 1, 100_000));
            builder.pop();
        }
        builder.pop();
        SPEC = builder.build();
    }

    private EnderIOWandConfig() {
    }

    public static int rodCapacity(EnderIOWandMaterial material) {
        return value(ROD_CAPACITY.get(material));
    }

    public static int rodCraftCost(EnderIOWandMaterial material) {
        return value(ROD_CRAFT_COST.get(material));
    }

    public static float capBaseCost(EnderIOWandMaterial material) {
        return (float) value(CAP_BASE_COST.get(material));
    }

    public static float capSpecialCost(EnderIOWandMaterial material) {
        return (float) value(CAP_SPECIAL_COST.get(material));
    }

    public static int capCraftCost(EnderIOWandMaterial material) {
        return value(CAP_CRAFT_COST.get(material));
    }

    private static int value(ModConfigSpec.IntValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    private static double value(ModConfigSpec.DoubleValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
