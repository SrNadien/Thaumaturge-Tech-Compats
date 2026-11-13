package nadiendev.ThaumaturgeTechCompats.compats.botania.spreader;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class BotaniaSpreaderConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-botania-spreaders.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<SpreaderTier, ModConfigSpec.IntValue> BURST_MANA = new EnumMap<>(SpreaderTier.class);
    private static final Map<SpreaderTier, ModConfigSpec.IntValue> MANA_CAPACITY = new EnumMap<>(SpreaderTier.class);
    private static final Map<SpreaderTier, ModConfigSpec.IntValue> PRE_LOSS_TICKS = new EnumMap<>(SpreaderTier.class);
    private static final Map<SpreaderTier, ModConfigSpec.DoubleValue> LOSS_PER_TICK = new EnumMap<>(SpreaderTier.class);
    private static final Map<SpreaderTier, ModConfigSpec.DoubleValue> MOTION_MODIFIER = new EnumMap<>(SpreaderTier.class);
    private static final Map<SpreaderTier, ModConfigSpec.IntValue> BURST_COLOR = new EnumMap<>(SpreaderTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        for (SpreaderTier tier : SpreaderTier.values()) {
            builder.push(tier.id());
            BURST_MANA.put(tier, builder.comment("Mana carried by every burst this spreader shoots. The spreader fires as soon as it holds this much. A Gaia Spreader shoots 640.")
                    .defineInRange("burst_mana", tier.defaultBurstMana(), 1, 100_000_000));
            MANA_CAPACITY.put(tier, builder.comment("Mana the spreader can hold. A Gaia Spreader holds 6400.")
                    .defineInRange("mana_capacity", tier.defaultManaCapacity(), 1, 2_000_000_000));
            PRE_LOSS_TICKS.put(tier, builder.comment("Ticks a burst flies before it starts losing mana. A Gaia Spreader burst flies 120.")
                    .defineInRange("ticks_before_loss", tier.defaultPreLossTicks(), 0, 100_000));
            LOSS_PER_TICK.put(tier, builder.comment("Mana a burst loses on every tick after that. A Gaia Spreader burst loses 20.")
                    .defineInRange("loss_per_tick", (double) tier.defaultLossPerTick(), 0.0D, 1_000_000.0D));
            MOTION_MODIFIER.put(tier, builder.comment("Multiplier of the speed of the burst. A Gaia Spreader burst moves at 2.0.")
                    .defineInRange("speed_multiplier", (double) tier.defaultMotionModifier(), 0.1D, 64.0D));
            BURST_COLOR.put(tier, builder.comment("Colour of the burst as a 24 bit RGB number, 0 to 16777215.")
                    .defineInRange("burst_color", tier.defaultColor(), 0, 0xFFFFFF));
            builder.pop();
        }
        SPEC = builder.build();
    }

    private BotaniaSpreaderConfig() {
    }

    public static int burstMana(SpreaderTier tier) {
        return value(BURST_MANA.get(tier));
    }

    public static int manaCapacity(SpreaderTier tier) {
        return Math.max(value(MANA_CAPACITY.get(tier)), burstMana(tier));
    }

    public static int preLossTicks(SpreaderTier tier) {
        return value(PRE_LOSS_TICKS.get(tier));
    }

    public static float lossPerTick(SpreaderTier tier) {
        return (float) value(LOSS_PER_TICK.get(tier));
    }

    public static float motionModifier(SpreaderTier tier) {
        return (float) value(MOTION_MODIFIER.get(tier));
    }

    public static int burstColor(SpreaderTier tier) {
        return value(BURST_COLOR.get(tier));
    }

    private static int value(ModConfigSpec.IntValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    private static double value(ModConfigSpec.DoubleValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
