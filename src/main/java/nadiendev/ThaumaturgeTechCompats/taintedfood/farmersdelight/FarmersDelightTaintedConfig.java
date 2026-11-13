package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class FarmersDelightTaintedConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-tainted-farmersdelight.toml";
    public static final ModConfigSpec SPEC;

    private static final String DEFAULT_EFFECT = "thaumaturge:flux_taint";

    private static final ModConfigSpec.BooleanValue FOOD_ENABLED;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> FOODS;
    private static final ModConfigSpec.DoubleValue HUNGER_MULTIPLIER;
    private static final ModConfigSpec.DoubleValue SATURATION_MULTIPLIER;
    private static final ModConfigSpec.ConfigValue<String> FOOD_EFFECT_ID;
    private static final ModConfigSpec.IntValue FOOD_EFFECT_DURATION;
    private static final ModConfigSpec.DoubleValue FOOD_EFFECT_MULTIPLIER;
    private static final ModConfigSpec.IntValue FOOD_EFFECT_AMPLIFIER;
    private static final ModConfigSpec.DoubleValue FOOD_EFFECT_CHANCE;
    private static final ModConfigSpec.BooleanValue KNIVES_ENABLED;
    private static final ModConfigSpec.ConfigValue<List<? extends String>> KNIVES;
    private static final ModConfigSpec.ConfigValue<String> KNIFE_EFFECT_ID;
    private static final ModConfigSpec.IntValue KNIFE_EFFECT_AMPLIFIER;
    private static final Map<FarmersDelightKnife, KnifeSettings> KNIFE_SETTINGS = new EnumMap<>(FarmersDelightKnife.class);

    public static final String COST_NONE = "none";
    public static final String COST_FLUX = "drain_flux";
    public static final String COST_VIS = "drain_vis";
    public static final String CUT_FLUX_AND_ITEM = "flux_and_item";
    public static final String CUT_FLUX_ONLY = "flux_only";

    private static final ModConfigSpec.BooleanValue FLUX_KNIFE_ENABLED;
    private static final ModConfigSpec.IntValue FLUX_KNIFE_DURABILITY;
    private static final ModConfigSpec.DoubleValue FLUX_KNIFE_DAMAGE_BONUS;
    private static final ModConfigSpec.DoubleValue FLUX_KNIFE_ATTACK_SPEED;
    private static final ModConfigSpec.DoubleValue FLUX_KNIFE_MINING_SPEED;
    private static final ModConfigSpec.IntValue FLUX_KNIFE_ENCHANTABILITY;
    private static final ModConfigSpec.BooleanValue RIFT_ENABLED;
    private static final ModConfigSpec.BooleanValue RIFT_REQUIRE_SNEAK;
    private static final ModConfigSpec.IntValue RIFT_SIZE;
    private static final ModConfigSpec.ConfigValue<String> RIFT_COST_MODE;
    private static final ModConfigSpec.DoubleValue RIFT_COST_FACTOR;
    private static final ModConfigSpec.IntValue RIFT_DURABILITY_COST;
    private static final ModConfigSpec.IntValue RIFT_COOLDOWN;
    private static final ModConfigSpec.ConfigValue<String> RIFT_SOUND;
    private static final ModConfigSpec.DoubleValue RIFT_SOUND_VOLUME;
    private static final ModConfigSpec.ConfigValue<String> RIFT_PARTICLE;
    private static final ModConfigSpec.IntValue RIFT_PARTICLE_COUNT;
    private static final ModConfigSpec.BooleanValue CUT_ENABLED;
    private static final ModConfigSpec.ConfigValue<String> CUT_MODE;
    private static final ModConfigSpec.BooleanValue CUT_FOOD_ONLY;
    private static final ModConfigSpec.DoubleValue CUT_BASE_FLUX;
    private static final ModConfigSpec.DoubleValue CUT_FLUX_PER_NUTRITION;
    private static final ModConfigSpec.DoubleValue CUT_MAX_FLUX;

    private record KnifeSettings(ModConfigSpec.IntValue durability, ModConfigSpec.DoubleValue damageBonus,
                                 ModConfigSpec.DoubleValue attackSpeed, ModConfigSpec.DoubleValue miningSpeed,
                                 ModConfigSpec.IntValue enchantability, ModConfigSpec.DoubleValue taintChance,
                                 ModConfigSpec.IntValue taintDuration) {
    }

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("tainted_food");
        FOOD_ENABLED = builder.comment("Turns the tainted Farmer's Delight foods off completely: no items, no recipes. Needs a game restart.")
                .define("enabled", true);
        FOODS = builder.comment("Tainted Farmer's Delight foods that exist, by item id. Remove an entry to remove that food and its recipe. Needs a game restart.")
                .defineListAllowEmpty("foods", FarmersDelightFood.ids(), () -> "tainted_beef_stew", FarmersDelightTaintedConfig::validName);
        HUNGER_MULTIPLIER = builder.comment("Multiplies the hunger that the original food restores. Needs a game restart.")
                .defineInRange("hunger_multiplier", 1.5D, 0.1D, 20.0D);
        SATURATION_MULTIPLIER = builder.comment("Multiplies the saturation that the original food restores. Needs a game restart.")
                .defineInRange("saturation_multiplier", 1.5D, 0.0D, 20.0D);
        builder.pop();

        builder.push("food_effect");
        FOOD_EFFECT_ID = builder.comment("Effect applied by every tainted Farmer's Delight food. The default is the Flux Taint of Thaumaturge.")
                .define("id", DEFAULT_EFFECT, FarmersDelightTaintedConfig::validId);
        FOOD_EFFECT_DURATION = builder.comment("Base duration of the effect, in ticks.")
                .defineInRange("duration_ticks", 200, 0, 1_000_000);
        FOOD_EFFECT_MULTIPLIER = builder.comment("Multiplies the duration of the effect. 0 removes the effect from the tainted foods. Needs a game restart.")
                .defineInRange("duration_multiplier", 1.0D, 0.0D, 100.0D);
        FOOD_EFFECT_AMPLIFIER = builder.comment("Amplifier of the effect. 0 is level one.")
                .defineInRange("amplifier", 0, 0, 255);
        FOOD_EFFECT_CHANCE = builder.comment("Chance that eating a tainted food applies the effect. Needs a game restart.")
                .defineInRange("chance", 1.0D, 0.0D, 1.0D);
        builder.pop();

        builder.push("knives");
        KNIVES_ENABLED = builder.comment("Turns the thaumium, alchemical brass and void metal knives off completely: no items, no recipes. Needs a game restart.")
                .define("enabled", true);
        KNIVES = builder.comment("Knives that exist, by item id. Remove an entry to remove that knife and its recipe. Needs a game restart.")
                .defineListAllowEmpty("knives", FarmersDelightKnife.ids(), () -> "thaumium_knife", FarmersDelightTaintedConfig::validName);
        for (FarmersDelightKnife knife : FarmersDelightKnife.values()) {
            builder.comment("Stats of the " + knife.id() + ". Needs a game restart.").push(knife.id());
            ModConfigSpec.IntValue durability = builder.comment("Durability. The iron knife of Farmer's Delight has 375.")
                    .defineInRange("durability", knife.defaultDurability(), 1, 100_000);
            ModConfigSpec.DoubleValue damageBonus = builder.comment("Attack damage added to the 0.5 base of every Farmer's Delight knife. The iron knife adds 2.0.")
                    .defineInRange("damage_bonus", knife.defaultDamageBonus(), 0.0D, 1000.0D);
            ModConfigSpec.DoubleValue attackSpeed = builder.comment("Attack speed modifier. The knives of Farmer's Delight use -2.0, and higher is faster.")
                    .defineInRange("attack_speed", knife.defaultAttackSpeed(), -3.9D, 10.0D);
            ModConfigSpec.DoubleValue miningSpeed = builder.comment("Mining speed on the blocks a knife is good at. The iron knife uses 6.0.")
                    .defineInRange("mining_speed", knife.defaultMiningSpeed(), 0.1D, 1000.0D);
            ModConfigSpec.IntValue enchantability = builder.comment("Enchantability. The iron knife has 14.")
                    .defineInRange("enchantability", knife.defaultEnchantability(), 0, 100);
            ModConfigSpec.DoubleValue taintChance = builder.comment("Chance that a hit applies the knife effect.")
                    .defineInRange("taint_chance", knife.defaultTaintChance(), 0.0D, 1.0D);
            ModConfigSpec.IntValue taintDuration = builder.comment("Duration of the knife effect, in ticks. 0 removes the effect from this knife.")
                    .defineInRange("taint_duration_ticks", knife.defaultTaintDuration(), 0, 1_000_000);
            KNIFE_SETTINGS.put(knife, new KnifeSettings(durability, damageBonus, attackSpeed, miningSpeed, enchantability, taintChance, taintDuration));
            builder.pop();
        }
        builder.pop();

        builder.push("knife_effect");
        KNIFE_EFFECT_ID = builder.comment("Effect that a knife applies to what it hits when its chance and duration allow it. The default is the Flux Taint of Thaumaturge.")
                .define("id", DEFAULT_EFFECT, FarmersDelightTaintedConfig::validId);
        KNIFE_EFFECT_AMPLIFIER = builder.comment("Amplifier of the effect. 0 is level one.")
                .defineInRange("amplifier", 0, 0, 255);
        builder.pop();

        builder.push("flux_knife");
        FLUX_KNIFE_ENABLED = builder.comment("Turns the Flux Knife off completely: no item, no recipe. Needs a game restart.")
                .define("enabled", true);
        FLUX_KNIFE_DURABILITY = builder.comment("Durability of the Flux Knife. Needs a game restart.")
                .defineInRange("durability", FluxKnife.DEFAULT_DURABILITY, 1, 100_000);
        FLUX_KNIFE_DAMAGE_BONUS = builder.comment("Attack damage that the Flux Knife adds to the 0.5 base of every Farmer's Delight knife. Needs a game restart.")
                .defineInRange("damage_bonus", FluxKnife.DEFAULT_DAMAGE_BONUS, 0.0D, 1000.0D);
        FLUX_KNIFE_ATTACK_SPEED = builder.comment("Attack speed modifier of the Flux Knife. Needs a game restart.")
                .defineInRange("attack_speed", FluxKnife.DEFAULT_ATTACK_SPEED, -3.9D, 10.0D);
        FLUX_KNIFE_MINING_SPEED = builder.comment("Mining speed of the Flux Knife on the blocks a knife is good at. Needs a game restart.")
                .defineInRange("mining_speed", FluxKnife.DEFAULT_MINING_SPEED, 0.1D, 1000.0D);
        FLUX_KNIFE_ENCHANTABILITY = builder.comment("Enchantability of the Flux Knife. Needs a game restart.")
                .defineInRange("enchantability", FluxKnife.DEFAULT_ENCHANTABILITY, 0, 100);

        builder.push("rift");
        RIFT_ENABLED = builder.comment("Lets the Flux Knife open a flux rift on the block it is used on.")
                .define("enabled", true);
        RIFT_REQUIRE_SNEAK = builder.comment("The rift only opens while sneaking, so the knife keeps working as a normal knife on every other block.")
                .define("require_sneak", true);
        RIFT_SIZE = builder.comment("Initial size of the rift. Thaumaturge caps rifts at 100.")
                .defineInRange("initial_size", 5, 1, 100);
        RIFT_COST_MODE = builder.comment("What opening a rift costs in the aura of the chunk where it opens. none: nothing. drain_flux: the flux of that chunk, and it fails if there is not enough. drain_vis: the vis of that chunk, and it fails if there is not enough.")
                .defineInList("cost_mode", COST_FLUX, new ArrayList<>(List.of(COST_NONE, COST_FLUX, COST_VIS)));
        RIFT_COST_FACTOR = builder.comment("Multiplies the aura cost. At 1.0 a rift costs size squared divided by 3, which is how Thaumaturge turns flux into rift size.")
                .defineInRange("cost_factor", 1.0D, 0.0D, 1000.0D);
        RIFT_DURABILITY_COST = builder.comment("Durability that opening a rift wears off the knife.")
                .defineInRange("durability_cost", 10, 0, 10_000);
        RIFT_COOLDOWN = builder.comment("Ticks before the knife can open another rift.")
                .defineInRange("cooldown_ticks", 200, 0, 1_000_000);
        RIFT_SOUND = builder.comment("Sound played when a rift opens. An empty or unknown id plays nothing.")
                .define("sound", "minecraft:block.portal.trigger", FarmersDelightTaintedConfig::validOptionalId);
        RIFT_SOUND_VOLUME = builder.comment("Volume of that sound.")
                .defineInRange("sound_volume", 1.0D, 0.0D, 10.0D);
        RIFT_PARTICLE = builder.comment("Particle sent where a rift opens. It has to be a particle without parameters. An empty or unknown id sends nothing.")
                .define("particle", "minecraft:portal", FarmersDelightTaintedConfig::validOptionalId);
        RIFT_PARTICLE_COUNT = builder.comment("How many of those particles.")
                .defineInRange("particle_count", 48, 0, 1000);
        builder.pop();

        builder.push("cutting");
        CUT_ENABLED = builder.comment("Food cut with the Flux Knife on the Cutting Board turns into flux in the aura.")
                .define("enabled", true);
        CUT_MODE = builder.comment("flux_and_item: the cut gives its normal result and adds flux to the aura. flux_only: the food results of the cut are not given, only the flux.")
                .defineInList("mode", CUT_FLUX_AND_ITEM, new ArrayList<>(List.of(CUT_FLUX_AND_ITEM, CUT_FLUX_ONLY)));
        CUT_FOOD_ONLY = builder.comment("Only cuts of food make flux. When off, every recipe cut with the knife adds the base flux.")
                .define("food_only", true);
        CUT_BASE_FLUX = builder.comment("Flux that every cut adds before counting the nutrition of the food.")
                .defineInRange("base_flux", 0.5D, 0.0D, 1000.0D);
        CUT_FLUX_PER_NUTRITION = builder.comment("Flux added per point of nutrition of the item that is cut. If the cut item has no nutrition, the food results of the recipe are counted.")
                .defineInRange("flux_per_nutrition", 0.25D, 0.0D, 1000.0D);
        CUT_MAX_FLUX = builder.comment("Most flux that one cut can add.")
                .defineInRange("max_flux", 6.0D, 0.0D, 10_000.0D);
        builder.pop();
        builder.pop();

        SPEC = builder.build();
    }

    private FarmersDelightTaintedConfig() {
    }

    private static boolean validName(Object value) {
        return value instanceof String text && !text.isBlank();
    }

    private static boolean validId(Object value) {
        return value instanceof String text && ResourceLocation.tryParse(text) != null;
    }

    private static boolean validOptionalId(Object value) {
        return value instanceof String text && (text.isBlank() || ResourceLocation.tryParse(text) != null);
    }

    private static <T> T read(ModConfigSpec.ConfigValue<T> value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    private static ResourceLocation effect(ModConfigSpec.ConfigValue<String> value) {
        ResourceLocation id = ResourceLocation.tryParse(read(value));
        return id == null ? ResourceLocation.parse(DEFAULT_EFFECT) : id;
    }

    public static boolean foodEnabled() {
        return CompatToggles.enabled(FarmersDelightTaintedIntegration.TOGGLE_FOOD) && read(FOOD_ENABLED);
    }

    public static boolean foodEnabled(FarmersDelightFood food) {
        return read(FOODS).contains(food.id());
    }

    public static float hungerMultiplier() {
        return read(HUNGER_MULTIPLIER).floatValue();
    }

    public static float saturationMultiplier() {
        return read(SATURATION_MULTIPLIER).floatValue();
    }

    public static ResourceLocation foodEffectId() {
        return effect(FOOD_EFFECT_ID);
    }

    public static int foodEffectDuration() {
        return Math.round(read(FOOD_EFFECT_DURATION) * read(FOOD_EFFECT_MULTIPLIER).floatValue());
    }

    public static int foodEffectAmplifier() {
        return read(FOOD_EFFECT_AMPLIFIER);
    }

    public static float foodEffectChance() {
        return read(FOOD_EFFECT_CHANCE).floatValue();
    }

    public static boolean knivesEnabled() {
        return CompatToggles.enabled(FarmersDelightTaintedIntegration.TOGGLE_KNIVES) && read(KNIVES_ENABLED);
    }

    public static boolean knifeEnabled(FarmersDelightKnife knife) {
        return read(KNIVES).contains(knife.id());
    }

    public static ResourceLocation knifeEffectId() {
        return effect(KNIFE_EFFECT_ID);
    }

    public static int knifeEffectAmplifier() {
        return read(KNIFE_EFFECT_AMPLIFIER);
    }

    public static boolean fluxKnifeEnabled() {
        return CompatToggles.enabled(FarmersDelightTaintedIntegration.TOGGLE_FLUX_KNIFE) && read(FLUX_KNIFE_ENABLED);
    }

    public static int fluxKnifeDurability() {
        return read(FLUX_KNIFE_DURABILITY);
    }

    public static float fluxKnifeDamageBonus() {
        return read(FLUX_KNIFE_DAMAGE_BONUS).floatValue();
    }

    public static float fluxKnifeAttackSpeed() {
        return read(FLUX_KNIFE_ATTACK_SPEED).floatValue();
    }

    public static float fluxKnifeMiningSpeed() {
        return read(FLUX_KNIFE_MINING_SPEED).floatValue();
    }

    public static int fluxKnifeEnchantability() {
        return read(FLUX_KNIFE_ENCHANTABILITY);
    }

    public static boolean riftEnabled() {
        return read(RIFT_ENABLED);
    }

    public static boolean riftRequireSneak() {
        return read(RIFT_REQUIRE_SNEAK);
    }

    public static int riftSize() {
        return read(RIFT_SIZE);
    }

    public static String riftCostMode() {
        return read(RIFT_COST_MODE);
    }

    public static float riftCostFactor() {
        return read(RIFT_COST_FACTOR).floatValue();
    }

    public static int riftDurabilityCost() {
        return read(RIFT_DURABILITY_COST);
    }

    public static int riftCooldown() {
        return read(RIFT_COOLDOWN);
    }

    public static String riftSound() {
        return read(RIFT_SOUND);
    }

    public static float riftSoundVolume() {
        return read(RIFT_SOUND_VOLUME).floatValue();
    }

    public static String riftParticle() {
        return read(RIFT_PARTICLE);
    }

    public static int riftParticleCount() {
        return read(RIFT_PARTICLE_COUNT);
    }

    public static boolean cutEnabled() {
        return read(CUT_ENABLED);
    }

    public static String cutMode() {
        return read(CUT_MODE);
    }

    public static boolean cutFoodOnly() {
        return read(CUT_FOOD_ONLY);
    }

    public static float cutBaseFlux() {
        return read(CUT_BASE_FLUX).floatValue();
    }

    public static float cutFluxPerNutrition() {
        return read(CUT_FLUX_PER_NUTRITION).floatValue();
    }

    public static float cutMaxFlux() {
        return read(CUT_MAX_FLUX).floatValue();
    }

    public static int durability(FarmersDelightKnife knife) {
        return read(KNIFE_SETTINGS.get(knife).durability());
    }

    public static float damageBonus(FarmersDelightKnife knife) {
        return read(KNIFE_SETTINGS.get(knife).damageBonus()).floatValue();
    }

    public static float attackSpeed(FarmersDelightKnife knife) {
        return read(KNIFE_SETTINGS.get(knife).attackSpeed()).floatValue();
    }

    public static float miningSpeed(FarmersDelightKnife knife) {
        return read(KNIFE_SETTINGS.get(knife).miningSpeed()).floatValue();
    }

    public static int enchantability(FarmersDelightKnife knife) {
        return read(KNIFE_SETTINGS.get(knife).enchantability());
    }

    public static float taintChance(FarmersDelightKnife knife) {
        return read(KNIFE_SETTINGS.get(knife).taintChance()).floatValue();
    }

    public static int taintDuration(FarmersDelightKnife knife) {
        return read(KNIFE_SETTINGS.get(knife).taintDuration());
    }
}
