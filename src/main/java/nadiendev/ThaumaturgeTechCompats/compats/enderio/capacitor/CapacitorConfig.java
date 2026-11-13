package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor;

import com.enderio.enderio.api.capacitor.CapacitorData;
import com.enderio.enderio.api.capacitor.CapacitorModifier;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class CapacitorConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-enderio-capacitors.toml";
    public static final ModConfigSpec SPEC;

    private static final CapacitorModifier[] SPECIALIZED = {
            CapacitorModifier.ENERGY_CAPACITY,
            CapacitorModifier.ENERGY_USE,
            CapacitorModifier.FUEL_EFFICIENCY,
            CapacitorModifier.BURNING_ENERGY_GENERATION
    };

    private static final Map<EnderIOTier, ModConfigSpec.DoubleValue> BASE = new EnumMap<>(EnderIOTier.class);
    private static final Map<EnderIOTier, Map<CapacitorModifier, ModConfigSpec.DoubleValue>> SPECIALIZATIONS = new EnumMap<>(EnderIOTier.class);
    private static final Map<EnderIOTier, ModConfigSpec.IntValue> BANK_CAPACITY = new EnumMap<>(EnderIOTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        for (EnderIOTier tier : EnderIOTier.values()) {
            builder.push(tier.id());
            BASE.put(tier, builder.comment("Capacitor level of the capacitor. The basic capacitor of Ender IO is 1, the double-layer one is 2 and the octadic one is 3. Machines scale their energy capacity, energy use and generation with it.")
                    .defineInRange("level", defaultBase(tier), 0.0D, 1000.0D));
            Map<CapacitorModifier, ModConfigSpec.DoubleValue> map = new EnumMap<>(CapacitorModifier.class);
            for (CapacitorModifier modifier : SPECIALIZED) {
                map.put(modifier, builder.comment("Capacitor level used only for " + modifier.getSerializedName() + ", instead of the level. 0 uses the level.")
                        .defineInRange(modifier.getSerializedName(), 0.0D, 0.0D, 1000.0D));
            }
            SPECIALIZATIONS.put(tier, map);
            BANK_CAPACITY.put(tier, builder.comment("Energy the capacitor bank of this level stores per block. Connected banks of the same level add their capacity up, and the bank moves as much energy per tick as it stores. The vibrant capacitor bank of Ender IO stores 4000000. Needs a game restart.")
                    .defineInRange("bank_capacity", defaultBankCapacity(tier), 1, Integer.MAX_VALUE));
            builder.pop();
        }
        SPEC = builder.build();
    }

    private CapacitorConfig() {
    }

    public static double defaultBase(EnderIOTier tier) {
        return switch (tier) {
            case THAUMIUM -> 4.0D;
            case ALCHEMICAL_BRASS -> 5.5D;
            case VOID_METAL -> 7.5D;
        };
    }

    public static int defaultBankCapacity(EnderIOTier tier) {
        return switch (tier) {
            case THAUMIUM -> 8_000_000;
            case ALCHEMICAL_BRASS -> 16_000_000;
            case VOID_METAL -> 32_000_000;
        };
    }

    public static ModConfigSpec.IntValue bankCapacity(EnderIOTier tier) {
        return BANK_CAPACITY.get(tier);
    }

    public static CapacitorData data(EnderIOTier tier) {
        float base = (float) value(BASE.get(tier));
        Map<CapacitorModifier, Float> specializations = new EnumMap<>(CapacitorModifier.class);
        for (CapacitorModifier modifier : SPECIALIZED) {
            double level = value(SPECIALIZATIONS.get(tier).get(modifier));
            if (level > 0.0D) {
                specializations.put(modifier, (float) level);
            }
        }
        return new CapacitorData(base, specializations);
    }

    private static double value(ModConfigSpec.DoubleValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
