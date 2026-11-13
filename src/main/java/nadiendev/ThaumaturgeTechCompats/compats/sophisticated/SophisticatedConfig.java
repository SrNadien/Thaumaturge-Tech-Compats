package nadiendev.ThaumaturgeTechCompats.compats.sophisticated;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public final class SophisticatedConfig {
    public static final String FILE_NAME = ThaumaturgeTechCompats.MOD_ID + "-sophisticated.toml";
    public static final ModConfigSpec SPEC;
    public static final int LIMITED_BARRELS = 4;

    private static final Map<SophisticatedTier, ModConfigSpec.DoubleValue> STACK_MULTIPLIER = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue> BACKPACK_SLOTS = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue> BACKPACK_UPGRADES = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue> BARREL_SLOTS = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue> BARREL_UPGRADES = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue> CHEST_SLOTS = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue> CHEST_UPGRADES = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue> SHULKER_SLOTS = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue> SHULKER_UPGRADES = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue[]> LIMITED_MULTIPLIER = new EnumMap<>(SophisticatedTier.class);
    private static final Map<SophisticatedTier, ModConfigSpec.IntValue[]> LIMITED_UPGRADES = new EnumMap<>(SophisticatedTier.class);
    private static final ModConfigSpec.IntValue STACK_UPGRADES_PER_STORAGE;
    private static final ModConfigSpec.IntValue AURA_CHUNK_RADIUS;
    private static final ModConfigSpec.BooleanValue CREATIVE_ALL_WOODS;
    private static final ModConfigSpec.BooleanValue WAND_ENABLED;
    private static final ModConfigSpec.BooleanValue CHARGERS_ENABLED;
    private static final ModConfigSpec.IntValue THAUMATURGE_CHARGER_RADIUS;
    private static final ModConfigSpec.IntValue THAUMATURGE_CHARGER_DISCOUNT;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("stack_upgrades");
        STACK_UPGRADES_PER_STORAGE = builder.comment("How many stack upgrades of any kind, the ones of Sophisticated included, a storage accepts. Sophisticated Backpacks and Sophisticated Storage use 3 by default. Needs a game restart.")
                .defineInRange("max_per_storage", 3, 1, 64);
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            builder.push(tier.id());
            STACK_MULTIPLIER.put(tier, builder.comment("Times the number of items that fit in one slot. The Stack Upgrade Tier 4 of Sophisticated Backpacks multiplies by 16 and the Stack Upgrade Tier 5 of Sophisticated Storage by 32. Needs a game restart.")
                    .defineInRange("multiplier", defaultStackMultiplier(tier), 1.0D, 1.0E15D));
            builder.pop();
        }
        builder.pop();

        builder.push("arcane_workbench_upgrade");
        AURA_CHUNK_RADIUS = builder.comment("Chunks around the chunk of the player that the upgrade may also draw vis from after the chunk of the player has run dry. 0 draws only from the chunk of the player.")
                .defineInRange("aura_chunk_radius", 0, 0, 2);
        WAND_ENABLED = builder.comment("Whether a wand in the wand slot of the upgrade pays the vis of the crafts and applies its discount, like the wand in the Arcane Workbench.")
                .define("wand_slot_enabled", true);
        CHARGERS_ENABLED = builder.comment("Whether a workbench charger item in the charger slot of the upgrade gives its vis discount and vis radius to the crafts of the upgrade.")
                .define("charger_slot_enabled", true);
        THAUMATURGE_CHARGER_RADIUS = builder.comment("Radius in chunks around the player from which the Arcane Workbench Charger of Thaumaturge draws vis when it is in the charger slot. The charger beside a workbench uses 1.")
                .defineInRange("thaumaturge_charger_radius_chunks", 1, 1, 8);
        THAUMATURGE_CHARGER_DISCOUNT = builder.comment("Percentage of the vis cost that the Arcane Workbench Charger of Thaumaturge does not charge when it is in the charger slot.")
                .defineInRange("thaumaturge_charger_discount_percent", 0, 0, 100);
        builder.pop();

        builder.push("backpacks");
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            builder.push(tier.id());
            BACKPACK_SLOTS.put(tier, builder.comment("Number of inventory slots in the backpack. The Netherite Backpack has 120.")
                    .defineInRange("inventory_slots", defaultBackpackSlots(tier), 1, 5000));
            BACKPACK_UPGRADES.put(tier, builder.comment("Number of upgrade slots in the backpack. The Netherite Backpack has 7.")
                    .defineInRange("upgrade_slots", defaultBackpackUpgrades(tier), 0, 10));
            builder.pop();
        }
        builder.pop();

        builder.push("storage");
        CREATIVE_ALL_WOODS = builder.comment("Whether the creative tab lists the barrels, limited barrels and chests of every tier in every wood type, the ones added by other mods included, as Sophisticated Storage does for its own. If false it lists only the acacia variant.")
                .define("creative_all_wood_variants", true);
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            builder.push(tier.id());
            BARREL_SLOTS.put(tier, builder.comment("Number of inventory slots in the barrel. The Netherite Barrel has 132.")
                    .defineInRange("barrel_inventory_slots", defaultStorageSlots(tier), 1, 360));
            BARREL_UPGRADES.put(tier, builder.comment("Number of upgrade slots in the barrel. The Netherite Barrel has 5.")
                    .defineInRange("barrel_upgrade_slots", defaultStorageUpgrades(tier), 0, 10));
            CHEST_SLOTS.put(tier, builder.comment("Number of inventory slots in the chest. The Netherite Chest has 132.")
                    .defineInRange("chest_inventory_slots", defaultStorageSlots(tier), 1, 360));
            CHEST_UPGRADES.put(tier, builder.comment("Number of upgrade slots in the chest. The Netherite Chest has 5.")
                    .defineInRange("chest_upgrade_slots", defaultStorageUpgrades(tier), 0, 10));
            SHULKER_SLOTS.put(tier, builder.comment("Number of inventory slots in the shulker box. The Netherite Shulker Box has 132.")
                    .defineInRange("shulker_box_inventory_slots", defaultStorageSlots(tier), 1, 360));
            SHULKER_UPGRADES.put(tier, builder.comment("Number of upgrade slots in the shulker box. The Netherite Shulker Box has 5.")
                    .defineInRange("shulker_box_upgrade_slots", defaultStorageUpgrades(tier), 0, 10));
            ModConfigSpec.IntValue[] multipliers = new ModConfigSpec.IntValue[LIMITED_BARRELS];
            ModConfigSpec.IntValue[] upgrades = new ModConfigSpec.IntValue[LIMITED_BARRELS];
            for (int size = 1; size <= LIMITED_BARRELS; size++) {
                multipliers[size - 1] = builder.comment("Multiplier used to calculate the base slot limit of limited barrel " + size + ". The Limited Netherite Barrel " + size + " gives " + defaultNetheriteLimited(size) + ".")
                        .defineInRange("limited_barrel_" + size + "_slot_limit_multiplier", defaultLimitedMultiplier(tier, size), 1, 8192);
                upgrades[size - 1] = builder.comment("Number of upgrade slots in limited barrel " + size + ". The Limited Netherite Barrel " + size + " has 5.")
                        .defineInRange("limited_barrel_" + size + "_upgrade_slots", defaultStorageUpgrades(tier), 0, 10);
            }
            LIMITED_MULTIPLIER.put(tier, multipliers);
            LIMITED_UPGRADES.put(tier, upgrades);
            builder.pop();
        }
        builder.pop();

        SPEC = builder.build();
    }

    private SophisticatedConfig() {
    }

    public static double defaultStackMultiplier(SophisticatedTier tier) {
        return switch (tier) {
            case THAUMIUM -> 8.0E12D;
            case ALCHEMICAL_BRASS -> 1.6E13D;
            case VOID_METAL -> 3.2E13D;
        };
    }

    public static int defaultBackpackSlots(SophisticatedTier tier) {
        return switch (tier) {
            case THAUMIUM -> 700;
            case ALCHEMICAL_BRASS -> 850;
            case VOID_METAL -> 1000;
        };
    }

    public static int defaultBackpackUpgrades(SophisticatedTier tier) {
        return switch (tier) {
            case THAUMIUM -> 8;
            case ALCHEMICAL_BRASS -> 9;
            case VOID_METAL -> 10;
        };
    }

    public static int defaultStorageSlots(SophisticatedTier tier) {
        return switch (tier) {
            case THAUMIUM -> 144;
            case ALCHEMICAL_BRASS -> 162;
            case VOID_METAL -> 180;
        };
    }

    public static int defaultStorageUpgrades(SophisticatedTier tier) {
        return switch (tier) {
            case THAUMIUM -> 6;
            case ALCHEMICAL_BRASS -> 7;
            case VOID_METAL -> 8;
        };
    }

    public static int defaultNetheriteLimited(int size) {
        return switch (size) {
            case 1 -> 160;
            case 2 -> 80;
            case 3 -> 50;
            default -> 40;
        };
    }

    public static int defaultLimitedMultiplier(SophisticatedTier tier, int size) {
        int base = defaultNetheriteLimited(size);
        return switch (tier) {
            case THAUMIUM -> base * 6 / 5;
            case ALCHEMICAL_BRASS -> base * 7 / 5;
            case VOID_METAL -> base * 8 / 5;
        };
    }

    public static double stackMultiplier(SophisticatedTier tier) {
        ModConfigSpec.DoubleValue value = STACK_MULTIPLIER.get(tier);
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }

    public static boolean creativeAllWoodVariants() {
        return SPEC.isLoaded() ? CREATIVE_ALL_WOODS.get() : CREATIVE_ALL_WOODS.getDefault();
    }

    public static boolean wandSlotEnabled() {
        return SPEC.isLoaded() ? WAND_ENABLED.get() : WAND_ENABLED.getDefault();
    }

    public static boolean chargerSlotEnabled() {
        return SPEC.isLoaded() ? CHARGERS_ENABLED.get() : CHARGERS_ENABLED.getDefault();
    }

    public static int thaumaturgeChargerRadius() {
        return value(THAUMATURGE_CHARGER_RADIUS);
    }

    public static int thaumaturgeChargerDiscount() {
        return value(THAUMATURGE_CHARGER_DISCOUNT);
    }

    public static int stackUpgradesPerStorage() {
        return value(STACK_UPGRADES_PER_STORAGE);
    }

    public static int auraChunkRadius() {
        return value(AURA_CHUNK_RADIUS);
    }

    public static Supplier<Integer> backpackSlots(SophisticatedTier tier) {
        return () -> value(BACKPACK_SLOTS.get(tier));
    }

    public static Supplier<Integer> backpackUpgrades(SophisticatedTier tier) {
        return () -> value(BACKPACK_UPGRADES.get(tier));
    }

    public static Supplier<Integer> barrelSlots(SophisticatedTier tier) {
        return () -> value(BARREL_SLOTS.get(tier));
    }

    public static Supplier<Integer> barrelUpgrades(SophisticatedTier tier) {
        return () -> value(BARREL_UPGRADES.get(tier));
    }

    public static Supplier<Integer> chestSlots(SophisticatedTier tier) {
        return () -> value(CHEST_SLOTS.get(tier));
    }

    public static Supplier<Integer> chestUpgrades(SophisticatedTier tier) {
        return () -> value(CHEST_UPGRADES.get(tier));
    }

    public static Supplier<Integer> shulkerBoxSlots(SophisticatedTier tier) {
        return () -> value(SHULKER_SLOTS.get(tier));
    }

    public static Supplier<Integer> shulkerBoxUpgrades(SophisticatedTier tier) {
        return () -> value(SHULKER_UPGRADES.get(tier));
    }

    public static Supplier<Integer> limitedBarrelMultiplier(SophisticatedTier tier, int size) {
        return () -> value(LIMITED_MULTIPLIER.get(tier)[size - 1]);
    }

    public static Supplier<Integer> limitedBarrelUpgrades(SophisticatedTier tier, int size) {
        return () -> value(LIMITED_UPGRADES.get(tier)[size - 1]);
    }

    private static int value(ModConfigSpec.IntValue value) {
        return SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}
