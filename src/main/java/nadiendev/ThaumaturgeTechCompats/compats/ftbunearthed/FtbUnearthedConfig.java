package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class FtbUnearthedConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-ftbunearthed.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<FtbUnearthedBrushTier, ModConfigSpec.IntValue> DURABILITY = new EnumMap<>(FtbUnearthedBrushTier.class);

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("Durability of the brushes. FTB Unearthed Crude Brush has 64, Reinforced Brush 256. Needs a game restart.").push("durability");
        for (FtbUnearthedBrushTier tier : FtbUnearthedBrushTier.values()) {
            DURABILITY.put(tier, builder.defineInRange(tier.id(), tier.defaultDurability(), 1, 100_000));
        }
        builder.pop();
        SPEC = builder.build();
    }

    private FtbUnearthedConfig() {
    }

    public static int durability(FtbUnearthedBrushTier tier) {
        ModConfigSpec.IntValue value = DURABILITY.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
