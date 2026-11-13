package nadiendev.ThaumaturgeTechCompats.compats.create;

import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class CreateConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-create.toml";
    public static final ModConfigSpec SPEC;

    private static final Map<String, ModConfigSpec.BooleanValue> TOGGLES = new LinkedHashMap<>();
    private static final Map<CasingTier, ModConfigSpec.IntValue> MULTIPLIERS = new EnumMap<>(CasingTier.class);
    private static final Map<CasingTier, ModConfigSpec.DoubleValue> RADII = new EnumMap<>(CasingTier.class);
    private static final ModConfigSpec.IntValue SCAN_INTERVAL;
    private static final ModConfigSpec.IntValue OPERATE_INTERVAL;
    private static final ModConfigSpec.BooleanValue SEAL_ANY_BLOCK;
    private static final ModConfigSpec.IntValue PROBE_INTERVAL;
    private static final ModConfigSpec.BooleanValue PARTICLES;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("toggles");
        for (String id : CreateToggles.ALL) {
            TOGGLES.put(id, builder.comment(CreateToggles.description(id)).define(id, true));
        }
        builder.pop();

        builder.push("seal");
        SCAN_INTERVAL = builder.comment("Ticks between the moments the Create Seal looks for items to insert or extract.")
                .defineInRange("scan_interval_ticks", 20, 5, 200);
        OPERATE_INTERVAL = builder.comment("Ticks between two clicks of a golem operating a Create block, for example a hand crank.")
                .defineInRange("operate_interval_ticks", 8, 2, 200);
        SEAL_ANY_BLOCK = builder.comment("When true the Create Seal can be placed on any block, not only on blocks of Create.")
                .define("place_on_any_block", false);
        builder.pop();

        builder.push("node_boost");
        PROBE_INTERVAL = builder.comment("Ticks between two searches for a node near a machine variant.")
                .defineInRange("probe_interval_ticks", 20, 5, 200);
        PARTICLES = builder.comment("Show particles over a machine variant while it is quickened.")
                .define("particles", true);
        for (CasingTier tier : CasingTier.values()) {
            builder.push(tier.blockName());
            MULTIPLIERS.put(tier, builder.comment("How many times faster the machine variant runs while a node is near.")
                    .defineInRange("speed_multiplier", 15, 1, 64));
            RADII.put(tier, builder.comment("Distance in blocks to the nearest aura node that quickens the machine variant.")
                    .defineInRange("node_radius", tier.defaultRadius(), 1.0D, 128.0D));
            builder.pop();
        }
        builder.pop();

        SPEC = builder.build();
    }

    private CreateConfig() {
    }

    static boolean toggle(String id) {
        ModConfigSpec.BooleanValue value = TOGGLES.get(id);
        if (value == null) {
            return true;
        }
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int multiplier(CasingTier tier) {
        ModConfigSpec.IntValue value = MULTIPLIERS.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static double radius(CasingTier tier) {
        ModConfigSpec.DoubleValue value = RADII.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static int scanInterval() {
        return SPEC.isLoaded() ? SCAN_INTERVAL.get() : SCAN_INTERVAL.getDefault();
    }

    public static int operateInterval() {
        return SPEC.isLoaded() ? OPERATE_INTERVAL.get() : OPERATE_INTERVAL.getDefault();
    }

    public static boolean sealAnyBlock() {
        return SPEC.isLoaded() ? SEAL_ANY_BLOCK.get() : SEAL_ANY_BLOCK.getDefault();
    }

    public static int probeInterval() {
        return SPEC.isLoaded() ? PROBE_INTERVAL.get() : PROBE_INTERVAL.getDefault();
    }

    public static boolean particles() {
        return SPEC.isLoaded() ? PARTICLES.get() : PARTICLES.getDefault();
    }
}
