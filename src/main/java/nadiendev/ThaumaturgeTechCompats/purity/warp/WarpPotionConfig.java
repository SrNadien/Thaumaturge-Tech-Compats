package nadiendev.ThaumaturgeTechCompats.purity.warp;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.purity.PurityToggles;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.function.Supplier;

public final class WarpPotionConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-purity-warp.toml";
    public static final ModConfigSpec SPEC;
    public static final Settings CLARITY;
    public static final Settings ABSOLUTION;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("clarity_potion");
        CLARITY = Settings.define(builder, PurityToggles.CLARITY_POTION, "temporary warp", true, 10, 1000, "minecraft:nausea", 100, 0, 20, true);
        builder.pop();
        builder.push("absolution_potion");
        ABSOLUTION = Settings.define(builder, PurityToggles.ABSOLUTION_POTION, "permanent warp", false, 1, 100, "minecraft:weakness", 1200, 1, 100, false);
        builder.pop();
        SPEC = builder.build();
    }

    private WarpPotionConfig() {
    }

    private static <T> T value(ModConfigSpec.ConfigValue<T> value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static final class Settings {
        private final String toggle;
        private final Supplier<Boolean> enabled;
        private final Supplier<Boolean> removeAll;
        private final Supplier<Integer> amount;
        private final Supplier<Boolean> alsoNormal;
        private final Supplier<Boolean> sideEffect;
        private final Supplier<String> effect;
        private final Supplier<Integer> effectDuration;
        private final Supplier<Integer> effectAmplifier;
        private final Supplier<Integer> cooldown;
        private final Supplier<Boolean> returnBottle;

        private Settings(String toggle, Supplier<Boolean> enabled, Supplier<Boolean> removeAll, Supplier<Integer> amount,
                         Supplier<Boolean> alsoNormal, Supplier<Boolean> sideEffect, Supplier<String> effect,
                         Supplier<Integer> effectDuration, Supplier<Integer> effectAmplifier, Supplier<Integer> cooldown,
                         Supplier<Boolean> returnBottle) {
            this.toggle = toggle;
            this.enabled = enabled;
            this.removeAll = removeAll;
            this.amount = amount;
            this.alsoNormal = alsoNormal;
            this.sideEffect = sideEffect;
            this.effect = effect;
            this.effectDuration = effectDuration;
            this.effectAmplifier = effectAmplifier;
            this.cooldown = cooldown;
            this.returnBottle = returnBottle;
        }

        private static Settings define(ModConfigSpec.Builder builder, String toggle, String warpName, boolean removeAllDefault,
                                       int amountDefault, int amountMax, String effectDefault, int durationDefault,
                                       int amplifierDefault, int cooldownDefault, boolean withNormal) {
            ModConfigSpec.BooleanValue enabled = builder.comment("Lets this potion remove " + warpName + " when it is drunk.")
                    .define("enabled", true);
            ModConfigSpec.BooleanValue removeAll = builder.comment("Removes all of the " + warpName + " in one drink instead of a fixed amount.")
                    .define("remove_all", removeAllDefault);
            ModConfigSpec.IntValue amount = builder.comment("Points of " + warpName + " removed by one drink when remove_all is off.")
                    .defineInRange("amount", amountDefault, 1, amountMax);
            ModConfigSpec.BooleanValue alsoNormal = withNormal
                    ? builder.comment("Also removes ordinary (sticky) warp, using the same amount rule, after the " + warpName + ".")
                    .define("also_remove_normal_warp", false)
                    : null;
            ModConfigSpec.BooleanValue sideEffect = builder.comment("Applies the side effect below to the player after drinking.")
                    .define("side_effect_enabled", true);
            ModConfigSpec.ConfigValue<String> effect = builder.comment("Mob effect id of the side effect.")
                    .define("side_effect_id", effectDefault);
            ModConfigSpec.IntValue duration = builder.comment("Duration of the side effect, in ticks.")
                    .defineInRange("side_effect_duration_ticks", durationDefault, 1, 1000000);
            ModConfigSpec.IntValue amplifier = builder.comment("Amplifier of the side effect, 0 is level one.")
                    .defineInRange("side_effect_amplifier", amplifierDefault, 0, 255);
            ModConfigSpec.IntValue cooldown = builder.comment("Ticks a player has to wait before drinking another one. 0 disables it.")
                    .defineInRange("use_cooldown_ticks", cooldownDefault, 0, 72000);
            ModConfigSpec.BooleanValue returnBottle = builder.comment("Gives back a glass bottle after drinking.")
                    .define("return_glass_bottle", true);
            return new Settings(toggle,
                    () -> value(enabled), () -> value(removeAll), () -> value(amount),
                    alsoNormal == null ? () -> false : () -> value(alsoNormal),
                    () -> value(sideEffect), () -> value(effect), () -> value(duration), () -> value(amplifier),
                    () -> value(cooldown), () -> value(returnBottle));
        }

        public boolean enabled() {
            return enabled.get() && CompatToggles.enabled(CompatToggles.PURIFICATION_POTION) && CompatToggles.enabled(toggle);
        }

        public boolean removeAll() {
            return removeAll.get();
        }

        public int amount() {
            return amount.get();
        }

        public boolean alsoNormal() {
            return alsoNormal.get();
        }

        public boolean sideEffect() {
            return sideEffect.get();
        }

        public String effect() {
            return effect.get();
        }

        public int effectDuration() {
            return effectDuration.get();
        }

        public int effectAmplifier() {
            return effectAmplifier.get();
        }

        public int cooldown() {
            return cooldown.get();
        }

        public boolean returnBottle() {
            return returnBottle.get();
        }
    }
}
