package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class OritechConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-oritech.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<OritechNode, ModConfigSpec.IntValue> NODE_PRODUCTION = new EnumMap<>(OritechNode.class);
    private static final Map<OritechNode, ModConfigSpec.IntValue> NODE_RARITY = new EnumMap<>(OritechNode.class);
    private static final Map<OritechTier, ModConfigSpec.DoubleValue> SPEED_ADDON_SPEED = new EnumMap<>(OritechTier.class);
    private static final Map<OritechTier, ModConfigSpec.DoubleValue> SPEED_ADDON_ENERGY = new EnumMap<>(OritechTier.class);
    private static final Map<OritechTier, ModConfigSpec.DoubleValue> EFFICIENCY_ADDON_ENERGY = new EnumMap<>(OritechTier.class);
    private static final Map<OritechTier, ModConfigSpec.LongValue> CAPACITOR_CAPACITY = new EnumMap<>(OritechTier.class);
    private static final Map<OritechTier, ModConfigSpec.LongValue> CAPACITOR_INSERT = new EnumMap<>(OritechTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("resource_nodes");
        for (OritechNode node : OritechNode.values()) {
            builder.push(node.id());
            NODE_PRODUCTION.put(node, builder.comment("Items that the Oritech Deep Drill produces from a " + node.englishName() + " Resource Node each cycle. Oritech's own nodes produce 1. Needs a game restart.")
                    .defineInRange("production", node.production(), 1, 64));
            NODE_RARITY.put(node, builder.comment("A " + node.englishName() + " Resource Node generates in 1 of every this many chunks of the overworld. Higher is rarer. Oritech's own nodes use 350 to 500.")
                    .defineInRange("world_gen_rarity", node.rarity(), 1, 1_000_000));
            builder.pop();
        }
        builder.pop();

        for (OritechTier tier : OritechTier.values()) {
            builder.push(tier.id());
            SPEED_ADDON_SPEED.put(tier, builder.comment("Processing time multiplier of the " + tier.englishName() + " speed addon. Lower is faster. Oritech's speed addon uses 0.5. Needs a game restart.")
                    .defineInRange("speed_addon_speed_multiplier", tier.speedAddonSpeed(), 0.0D, 100.0D));
            SPEED_ADDON_ENERGY.put(tier, builder.comment("Energy use multiplier of the " + tier.englishName() + " speed addon. Lower is cheaper. Oritech's speed addon uses 1.2. Needs a game restart.")
                    .defineInRange("speed_addon_energy_multiplier", tier.speedAddonEnergy(), 0.0D, 100.0D));
            EFFICIENCY_ADDON_ENERGY.put(tier, builder.comment("Energy use multiplier of the " + tier.englishName() + " efficiency addon. Lower is cheaper. Oritech's efficiency addon uses 0.8. Needs a game restart.")
                    .defineInRange("efficiency_addon_energy_multiplier", tier.efficiencyAddonEnergy(), 0.0D, 100.0D));
            CAPACITOR_CAPACITY.put(tier, builder.comment("Energy capacity added by the " + tier.englishName() + " capacitor addon. Oritech's capacitor addon adds 2000000. Needs a game restart.")
                    .defineInRange("capacitor_addon_capacity", tier.capacitorCapacity(), 0L, 1_000_000_000_000L));
            CAPACITOR_INSERT.put(tier, builder.comment("Energy insert rate added by the " + tier.englishName() + " capacitor addon. Oritech's capacitor addon adds 2000. Needs a game restart.")
                    .defineInRange("capacitor_addon_insert_rate", tier.capacitorInsert(), 0L, 1_000_000_000_000L));
            builder.pop();
        }

        SPEC = builder.build();
    }

    private OritechConfig() {
    }

    public static int nodeProduction(OritechNode node) {
        return SPEC.isLoaded() ? NODE_PRODUCTION.get(node).get() : node.production();
    }

    public static int nodeRarity(OritechNode node) {
        return SPEC.isLoaded() ? NODE_RARITY.get(node).get() : node.rarity();
    }

    public static float speedAddonSpeed(OritechTier tier) {
        return SPEC.isLoaded() ? SPEED_ADDON_SPEED.get(tier).get().floatValue() : (float) tier.speedAddonSpeed();
    }

    public static float speedAddonEnergy(OritechTier tier) {
        return SPEC.isLoaded() ? SPEED_ADDON_ENERGY.get(tier).get().floatValue() : (float) tier.speedAddonEnergy();
    }

    public static float efficiencyAddonEnergy(OritechTier tier) {
        return SPEC.isLoaded() ? EFFICIENCY_ADDON_ENERGY.get(tier).get().floatValue() : (float) tier.efficiencyAddonEnergy();
    }

    public static long capacitorCapacity(OritechTier tier) {
        return SPEC.isLoaded() ? CAPACITOR_CAPACITY.get(tier).get() : tier.capacitorCapacity();
    }

    public static long capacitorInsert(OritechTier tier) {
        return SPEC.isLoaded() ? CAPACITOR_INSERT.get(tier).get() : tier.capacitorInsert();
    }
}
