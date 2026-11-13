package nadiendev.ThaumaturgeTechCompats.config;

import nadiendev.ThaumaturgeTechCompats.ponder.PonderConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class ThaumaturgeTechCompatsConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue AKASHIC_ENABLED;
    public static final ModConfigSpec.IntValue AKASHIC_AMOUNT;
    public static final ModConfigSpec.BooleanValue AKASHIC_CONSUMED_IN_CREATIVE;

    public static final ModConfigSpec.BooleanValue PEARL_KEPT_IN_CRAFTING;

    public static final ModConfigSpec.BooleanValue INFINITY_ROD_REFILLS_VIS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("akashic_record");
        AKASHIC_ENABLED = builder.comment("Lets the Akashic Record grant aspects when used.")
                .define("enabled", true);
        AKASHIC_AMOUNT = builder.comment("Points of every aspect the Akashic Record adds to the player's aspect pool.")
                .defineInRange("amount", 999, 1, 32767);
        AKASHIC_CONSUMED_IN_CREATIVE = builder.comment("Consume the Akashic Record when a creative player uses it.")
                .define("consumed_in_creative", false);
        builder.pop();

        builder.push("extremely_primordial_pearl");
        PEARL_KEPT_IN_CRAFTING = builder.comment("The pearl stays in the crafting grid instead of being consumed.")
                .define("kept_in_crafting", true);
        builder.pop();

        builder.push("infinity_rod");
        INFINITY_ROD_REFILLS_VIS = builder.comment("A wand with the Infinity rod refills every primal aspect to the maximum.")
                .define("refills_vis", true);
        builder.pop();

        PonderConfig.define(builder);

        SPEC = builder.build();
    }

    private ThaumaturgeTechCompatsConfig() {
    }
}
