package nadiendev.ThaumaturgeTechCompats.compats.botania.wand;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class BotaniaWandConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-botania-wand.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<BotaniaWandRod, ModConfigSpec.IntValue> ROD_CAPACITY = new EnumMap<>(BotaniaWandRod.class);
    private static final Map<BotaniaWandRod, ModConfigSpec.IntValue> ROD_CRAFT_COST = new EnumMap<>(BotaniaWandRod.class);
    private static final Map<BotaniaWandCap, ModConfigSpec.DoubleValue> CAP_BASE_COST = new EnumMap<>(BotaniaWandCap.class);
    private static final Map<BotaniaWandCap, ModConfigSpec.DoubleValue> CAP_SPECIAL_COST = new EnumMap<>(BotaniaWandCap.class);
    private static final Map<BotaniaWandCap, ModConfigSpec.IntValue> CAP_CRAFT_COST = new EnumMap<>(BotaniaWandCap.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("rods");
        for (BotaniaWandRod rod : BotaniaWandRod.values()) {
            builder.push(rod.id());
            ROD_CAPACITY.put(rod, builder.comment("Vis capacity of the rod, in the same units as the rods of Thaumaturge. A wooden rod has 25, a greatwood rod 50 and a silverwood rod 100.")
                    .defineInRange("capacity", rod.defaultCapacity(), 1, 10_000_000));
            ROD_CRAFT_COST.put(rod, builder.comment("Craft cost factor of the rod. The vis an assembled wand costs to make is this times the craft cost of its cap.")
                    .defineInRange("craft_cost", rod.defaultCraftCost(), 1, 100_000));
            builder.pop();
        }
        builder.pop();
        builder.push("caps");
        for (BotaniaWandCap cap : BotaniaWandCap.values()) {
            builder.push(cap.id());
            CAP_BASE_COST.put(cap, builder.comment("Multiplier of the vis every spell costs. 1.0 is the full price, 0.5 is half and 0.0 is free.")
                    .defineInRange("cost_modifier", (double) cap.defaultBaseCostModifier(), 0.0D, 10.0D));
            if (!cap.specialAspects().isEmpty()) {
                CAP_SPECIAL_COST.put(cap, builder.comment("Multiplier of the vis cost for the primal aspects aer, aqua, terra and ignis, instead of the cost modifier.")
                        .defineInRange("special_cost_modifier", (double) cap.defaultSpecialCostModifier(), 0.0D, 10.0D));
            }
            CAP_CRAFT_COST.put(cap, builder.comment("Craft cost factor of the cap. The vis an assembled wand costs to make is this times the craft cost of its rod.")
                    .defineInRange("craft_cost", cap.defaultCraftCost(), 1, 100_000));
            builder.pop();
        }
        builder.pop();
        SPEC = builder.build();
    }

    private BotaniaWandConfig() {
    }

    public static int rodCapacity(BotaniaWandRod rod) {
        return value(ROD_CAPACITY.get(rod));
    }

    public static int rodCraftCost(BotaniaWandRod rod) {
        return value(ROD_CRAFT_COST.get(rod));
    }

    public static float capBaseCost(BotaniaWandCap cap) {
        return (float) value(CAP_BASE_COST.get(cap));
    }

    public static float capSpecialCost(BotaniaWandCap cap) {
        ModConfigSpec.DoubleValue value = CAP_SPECIAL_COST.get(cap);
        return value == null ? cap.defaultSpecialCostModifier() : (float) value(value);
    }

    public static int capCraftCost(BotaniaWandCap cap) {
        return value(CAP_CRAFT_COST.get(cap));
    }

    private static int value(ModConfigSpec.IntValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    private static double value(ModConfigSpec.DoubleValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
