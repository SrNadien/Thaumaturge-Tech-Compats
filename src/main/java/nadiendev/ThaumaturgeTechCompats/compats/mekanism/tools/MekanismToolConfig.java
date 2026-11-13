package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class MekanismToolConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-mekanism-tools.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<MekanismToolTier, ModConfigSpec.LongValue> CAPACITY = new EnumMap<>(MekanismToolTier.class);
    private static final Map<MekanismToolTier, ModConfigSpec.LongValue> BLOCK_COST = new EnumMap<>(MekanismToolTier.class);
    private static final Map<MekanismToolTier, ModConfigSpec.LongValue> ATTACK_COST = new EnumMap<>(MekanismToolTier.class);
    private static final Map<MekanismToolTier, ModConfigSpec.LongValue> USE_COST = new EnumMap<>(MekanismToolTier.class);
    private static final Map<MekanismToolTier, ModConfigSpec.DoubleValue> MINING_SPEED = new EnumMap<>(MekanismToolTier.class);
    private static final Map<MekanismToolTier, ModConfigSpec.DoubleValue> ATTACK_DAMAGE = new EnumMap<>(MekanismToolTier.class);
    private static final Map<MekanismToolTier, ModConfigSpec.IntValue> ENCHANTABILITY = new EnumMap<>(MekanismToolTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        for (MekanismToolTier tier : MekanismToolTier.values()) {
            builder.push(tier.id());
            CAPACITY.put(tier, builder.comment("Energy the tools of this tier can hold, in Mekanism joules. The Atomic Disassembler holds 1000000. Needs a game restart for the charge bar and the creative tab.")
                    .defineInRange("capacity", tier.defaultCapacity(), 1L, 1_000_000_000_000L));
            BLOCK_COST.put(tier, builder.comment("Energy spent on every block mined, in Mekanism joules. 0 makes mining free.")
                    .defineInRange("block_cost", tier.defaultBlockCost(), 0L, 1_000_000_000L));
            ATTACK_COST.put(tier, builder.comment("Energy spent on every hit on a creature, in Mekanism joules. 0 makes fighting free.")
                    .defineInRange("attack_cost", tier.defaultAttackCost(), 0L, 1_000_000_000L));
            USE_COST.put(tier, builder.comment("Energy spent on every right click that strips, flattens or tills a block, in Mekanism joules. 0 makes it free.")
                    .defineInRange("use_cost", tier.defaultUseCost(), 0L, 1_000_000_000L));
            MINING_SPEED.put(tier, builder.comment("Mining speed against every block the tool is meant for while it holds energy. Iron tools use 6, and Thaumaturge's own tools use 7 for thaumium and 8 for void metal. Needs a game restart.")
                    .defineInRange("mining_speed", tier.defaultMiningSpeed(), 1.0D, 1000.0D));
            ATTACK_DAMAGE.put(tier, builder.comment("Attack damage bonus of the tier, added to the base damage of each tool. Needs a game restart.")
                    .defineInRange("attack_damage", tier.defaultAttackDamage(), 0.0D, 1000.0D));
            ENCHANTABILITY.put(tier, builder.comment("Enchantability of the tools of this tier. Needs a game restart.")
                    .defineInRange("enchantability", tier.defaultEnchantability(), 0, 100));
            builder.pop();
        }
        SPEC = builder.build();
    }

    private MekanismToolConfig() {
    }

    public static long capacity(MekanismToolTier tier) {
        return read(CAPACITY.get(tier));
    }

    public static long blockCost(MekanismToolTier tier) {
        return read(BLOCK_COST.get(tier));
    }

    public static long attackCost(MekanismToolTier tier) {
        return read(ATTACK_COST.get(tier));
    }

    public static long useCost(MekanismToolTier tier) {
        return read(USE_COST.get(tier));
    }

    public static double miningSpeed(MekanismToolTier tier) {
        return read(MINING_SPEED.get(tier));
    }

    public static double attackDamage(MekanismToolTier tier) {
        return read(ATTACK_DAMAGE.get(tier));
    }

    public static int enchantability(MekanismToolTier tier) {
        return read(ENCHANTABILITY.get(tier));
    }

    private static <T> T read(ModConfigSpec.ConfigValue<T> value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
