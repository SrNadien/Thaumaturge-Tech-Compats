package nadiendev.ThaumaturgeTechCompats.taintedfood;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public final class TaintedFoodConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-tainted-food.toml";
    public static final ModConfigSpec SPEC;

    private static final String DEFAULT_EFFECT = "thaumaturge:flux_taint";

    private static final ModConfigSpec.BooleanValue ENABLED;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> FOODS;
    private static final ModConfigSpec.DoubleValue HUNGER_MULTIPLIER;
    private static final ModConfigSpec.DoubleValue SATURATION_MULTIPLIER;
    private static final ModConfigSpec.ConfigValue<String> EFFECT_ID;
    private static final ModConfigSpec.IntValue EFFECT_DURATION;
    private static final ModConfigSpec.DoubleValue EFFECT_MULTIPLIER;
    private static final ModConfigSpec.IntValue EFFECT_AMPLIFIER;
    private static final ModConfigSpec.DoubleValue EFFECT_CHANCE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("tainted_food");
        ENABLED = builder.comment("Turns the tainted foods off completely: no items, no recipes. Needs a game restart.")
                .define("enabled", true);
        FOODS = builder.comment("Tainted foods that exist, by item id. Remove an entry to remove that food and its recipe. Needs a game restart.")
                .defineListAllowEmpty("foods", TaintedFoodType.ids(), () -> "tainted_bread", TaintedFoodConfig::validName);
        HUNGER_MULTIPLIER = builder.comment("Multiplies the hunger that the original food restores. Needs a game restart.")
                .defineInRange("hunger_multiplier", 1.5D, 0.1D, 20.0D);
        SATURATION_MULTIPLIER = builder.comment("Multiplies the saturation that the original food restores. Needs a game restart.")
                .defineInRange("saturation_multiplier", 1.5D, 0.0D, 20.0D);
        builder.pop();

        builder.push("effect");
        EFFECT_ID = builder.comment("Effect applied by every tainted food. The default is the Flux Taint of Thaumaturge.")
                .define("id", DEFAULT_EFFECT, TaintedFoodConfig::validId);
        EFFECT_DURATION = builder.comment("Base duration of the effect, in ticks.")
                .defineInRange("duration_ticks", 200, 0, 1_000_000);
        EFFECT_MULTIPLIER = builder.comment("Multiplies the duration of the effect. 0 removes the effect from the tainted foods. Needs a game restart.")
                .defineInRange("duration_multiplier", 1.0D, 0.0D, 100.0D);
        EFFECT_AMPLIFIER = builder.comment("Amplifier of the effect. 0 is level one.")
                .defineInRange("amplifier", 0, 0, 255);
        EFFECT_CHANCE = builder.comment("Chance that eating a tainted food applies the effect. Needs a game restart.")
                .defineInRange("chance", 1.0D, 0.0D, 1.0D);
        builder.pop();

        SPEC = builder.build();
    }

    private TaintedFoodConfig() {
    }

    private static boolean validName(Object value) {
        return value instanceof String text && !text.isBlank();
    }

    private static boolean validId(Object value) {
        return value instanceof String text && ResourceLocation.tryParse(text) != null;
    }

    private static <T> T read(ModConfigSpec.ConfigValue<T> value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static boolean enabled() {
        return CompatToggles.enabled(TaintedFoodIntegration.TOGGLE) && read(ENABLED);
    }

    public static boolean foodEnabled(TaintedFoodType type) {
        return read(FOODS).contains(type.id());
    }

    public static float hungerMultiplier() {
        return read(HUNGER_MULTIPLIER).floatValue();
    }

    public static float saturationMultiplier() {
        return read(SATURATION_MULTIPLIER).floatValue();
    }

    public static ResourceLocation effectId() {
        ResourceLocation id = ResourceLocation.tryParse(read(EFFECT_ID));
        return id == null ? ResourceLocation.parse(DEFAULT_EFFECT) : id;
    }

    public static int effectDuration() {
        return Math.round(read(EFFECT_DURATION) * read(EFFECT_MULTIPLIER).floatValue());
    }

    public static int effectAmplifier() {
        return read(EFFECT_AMPLIFIER);
    }

    public static float effectChance() {
        return read(EFFECT_CHANCE).floatValue();
    }
}
