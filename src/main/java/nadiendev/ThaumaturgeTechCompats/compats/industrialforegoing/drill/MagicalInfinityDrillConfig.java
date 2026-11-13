package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class MagicalInfinityDrillConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-industrialforegoing-drill.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<DrillTier, ModConfigSpec.IntValue> RADIUS = new EnumMap<>(DrillTier.class);
    private static final Map<DrillTier, ModConfigSpec.IntValue> MIN_VIS = new EnumMap<>(DrillTier.class);

    private static final ModConfigSpec.BooleanValue ENABLED;
    private static final ModConfigSpec.BooleanValue WORKS_WITHOUT_VIS;
    private static final ModConfigSpec.IntValue MAX_VIS;
    private static final ModConfigSpec.IntValue VIS_PER_BLOCK;
    private static final ModConfigSpec.DoubleValue MINING_SPEED;
    private static final ModConfigSpec.BooleanValue ARTIFACT_DEPTH;
    private static final ModConfigSpec.BooleanValue SKIP_BLOCK_ENTITIES;
    private static final ModConfigSpec.BooleanValue UNBREAKING_SAVES_VIS;
    private static final ModConfigSpec.IntValue ENCHANTABILITY;
    private static final ModConfigSpec.DoubleValue ATTACK_DAMAGE;
    private static final ModConfigSpec.DoubleValue ATTACK_SPEED;
    private static final ModConfigSpec.BooleanValue CHARGING_BY_DEFAULT;
    private static final ModConfigSpec.IntValue RECHARGE_INTERVAL;
    private static final ModConfigSpec.IntValue RECHARGE_AMOUNT;
    private static final ModConfigSpec.BooleanValue LIQUID_ENABLED;
    private static final ModConfigSpec.IntValue LIQUID_CAPACITY;
    private static final ModConfigSpec.IntValue LIQUID_PER_BLOCK;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("general");
        ENABLED = builder.comment("Turns the Magical Infinity Drill off completely: it mines like a plain tool, without area, speed or level.")
                .define("enabled", true);
        MAX_VIS = builder.comment("Vis the drill can hold. The levels are unlocked by the vis it holds, like the Infinity Drill unlocks them with the power it holds.")
                .defineInRange("max_vis", 5000, 1, 1_000_000);
        VIS_PER_BLOCK = builder.comment("Vis spent on every mined block, the one that was hit included. 0 makes mining free. The Infinity Drill spends 10000 FE per block and one vis is worth 1000 FE, but a drill that holds vis is far smaller than one that holds FE.")
                .defineInRange("vis_per_block", 1, 0, 100_000);
        WORKS_WITHOUT_VIS = builder.comment("The drill keeps its speed and its area even when it holds no vis, without spending anything.")
                .define("works_without_vis", false);
        MINING_SPEED = builder.comment("Mining speed against every block a pickaxe or a shovel can mine while the drill has vis or liquid. The Infinity Drill uses 10.")
                .defineInRange("mining_speed", 10.0D, 0.0D, 1000.0D);
        ARTIFACT_DEPTH = builder.comment("The Artifact level also digs as deep as it is wide, a cube, instead of a single layer. The Infinity Drill does the same.")
                .define("artifact_depth", true);
        SKIP_BLOCK_ENTITIES = builder.comment("The area skips blocks that hold data, like chests and machines, so their contents are never lost. The Infinity Drill does not skip them.")
                .define("skip_block_entities", true);
        UNBREAKING_SAVES_VIS = builder.comment("Unbreaking makes the drill spend vis or liquid on fewer blocks.")
                .define("unbreaking_saves_vis", true);
        ENCHANTABILITY = builder.comment("Enchantability of the drill. 0 makes it impossible to enchant.")
                .defineInRange("enchantability", 15, 0, 100);
        ATTACK_DAMAGE = builder.comment("Attack damage added by the drill. The Infinity Drill adds 3.")
                .defineInRange("attack_damage", 3.0D, -100.0D, 1000.0D);
        ATTACK_SPEED = builder.comment("Attack speed added by the drill. The Infinity Drill adds -2.5.")
                .defineInRange("attack_speed", -2.5D, -100.0D, 100.0D);
        builder.pop();

        builder.push("charging");
        CHARGING_BY_DEFAULT = builder.comment("New drills start with charging turned on. Right click on air toggles it.")
                .define("enabled_by_default", true);
        RECHARGE_INTERVAL = builder.comment("Ticks between two charges from the aura around the player while charging is on.")
                .defineInRange("interval_ticks", 20, 1, 12000);
        RECHARGE_AMOUNT = builder.comment("Vis drawn from the aura into the drill on every charge. A Recharge Pedestal or any other way to recharge an item also works.")
                .defineInRange("amount", 5, 0, 10000);
        builder.pop();

        builder.push("liquid_death");
        LIQUID_ENABLED = builder.comment("The drill has a tank for Liquid Death. While it holds enough, it is spent instead of vis, like the Infinity Drill spends Bio Fuel instead of power. The level is still given by the vis.")
                .define("enabled", true);
        LIQUID_CAPACITY = builder.comment("Capacity of the tank, in mB. The Infinity Drill holds 1000000.")
                .defineInRange("capacity_mb", 10000, 1, 1_000_000_000);
        LIQUID_PER_BLOCK = builder.comment("Liquid Death spent on every mined block, in mB. The Infinity Drill spends 30 of Bio Fuel.")
                .defineInRange("mb_per_block", 5, 0, 100_000);
        builder.pop();

        builder.push("levels");
        for (DrillTier tier : DrillTier.values()) {
            builder.push(tier.id());
            RADIUS.put(tier, builder.comment("Blocks the area reaches around the mined block. 0 is one block, 1 is 3x3, 6 is 13x13.")
                    .defineInRange("radius", tier.defaultRadius(), 0, 12));
            if (tier != DrillTier.POOR) {
                MIN_VIS.put(tier, builder.comment("Vis the drill must hold for this level to be available.")
                        .defineInRange("min_vis", tier.defaultMinVis(), 0, 1_000_000));
            }
            builder.pop();
        }
        builder.pop();

        SPEC = builder.build();
    }

    private MagicalInfinityDrillConfig() {
    }

    private static <T> T read(ModConfigSpec.ConfigValue<T> value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static boolean enabled() {
        return CompatToggles.enabled(CompatToggles.INDUSTRIAL_FOREGOING)
                && CompatToggles.enabled(CompatToggles.MAGIC_INFINITY_DRILL)
                && read(ENABLED);
    }

    public static int maxVis() {
        return read(MAX_VIS);
    }

    public static int visPerBlock() {
        return read(VIS_PER_BLOCK);
    }

    public static boolean worksWithoutVis() {
        return read(WORKS_WITHOUT_VIS);
    }

    public static float miningSpeed() {
        return read(MINING_SPEED).floatValue();
    }

    public static boolean artifactDepth() {
        return read(ARTIFACT_DEPTH);
    }

    public static boolean skipBlockEntities() {
        return read(SKIP_BLOCK_ENTITIES);
    }

    public static boolean unbreakingSavesVis() {
        return read(UNBREAKING_SAVES_VIS);
    }

    public static int enchantability() {
        return read(ENCHANTABILITY);
    }

    public static double attackDamage() {
        return read(ATTACK_DAMAGE);
    }

    public static double attackSpeed() {
        return read(ATTACK_SPEED);
    }

    public static boolean chargingByDefault() {
        return read(CHARGING_BY_DEFAULT);
    }

    public static int rechargeInterval() {
        return read(RECHARGE_INTERVAL);
    }

    public static int rechargeAmount() {
        return read(RECHARGE_AMOUNT);
    }

    public static boolean liquidEnabled() {
        return read(LIQUID_ENABLED);
    }

    public static int liquidCapacity() {
        return read(LIQUID_CAPACITY);
    }

    public static int liquidPerBlock() {
        return read(LIQUID_PER_BLOCK);
    }

    public static int radius(DrillTier tier) {
        return read(RADIUS.get(tier));
    }

    public static int minVis(DrillTier tier) {
        ModConfigSpec.IntValue value = MIN_VIS.get(tier);
        return value == null ? 0 : read(value);
    }
}
