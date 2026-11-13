package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum FarmersDelightKnife implements Tier {
    THAUMIUM("thaumium_knife", "thaumaturge:plate_thaumium", "thaumaturge:ingot_thaumium", Rarity.UNCOMMON,
            BlockTags.INCORRECT_FOR_IRON_TOOL, 450, 2.5D, -1.9D, 6.5D, 16, 0.0D, 0, 20),
    ALCHEMICAL_BRASS("alchemical_brass_knife", "thaumaturge:plate_brass", "thaumaturge:ingot_brass", Rarity.RARE,
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 800, 3.0D, -1.8D, 7.5D, 20, 0.0D, 0, 35),
    VOID_METAL("void_metal_knife", "thaumaturge:plate_void", "thaumaturge:ingot_void", Rarity.EPIC,
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1300, 4.0D, -1.7D, 9.0D, 24, 0.35D, 100, 50);

    private static final String TAINT_TOOLTIP_KEY = "item." + ThaumaturgeTechCompats.MOD_ID + ".knife_taint.tooltip";

    private final String id;
    private final ResourceLocation plate;
    private final ResourceLocation ingot;
    private final Rarity rarity;
    private final TagKey<Block> incorrectBlocks;
    private final int durability;
    private final double damageBonus;
    private final double attackSpeed;
    private final double miningSpeed;
    private final int enchantability;
    private final double taintChance;
    private final int taintDuration;
    private final int vis;

    FarmersDelightKnife(String id, String plate, String ingot, Rarity rarity, TagKey<Block> incorrectBlocks, int durability,
                        double damageBonus, double attackSpeed, double miningSpeed, int enchantability,
                        double taintChance, int taintDuration, int vis) {
        this.id = id;
        this.plate = ResourceLocation.parse(plate);
        this.ingot = ResourceLocation.parse(ingot);
        this.rarity = rarity;
        this.incorrectBlocks = incorrectBlocks;
        this.durability = durability;
        this.damageBonus = damageBonus;
        this.attackSpeed = attackSpeed;
        this.miningSpeed = miningSpeed;
        this.enchantability = enchantability;
        this.taintChance = taintChance;
        this.taintDuration = taintDuration;
        this.vis = vis;
    }

    public static List<String> ids() {
        List<String> ids = new ArrayList<>();
        for (FarmersDelightKnife knife : values()) {
            ids.add(knife.id());
        }
        return ids;
    }

    public static String taintTooltipKey() {
        return TAINT_TOOLTIP_KEY;
    }

    public String id() {
        return id;
    }

    public ResourceLocation itemId() {
        return ResourceLocation.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, id);
    }

    public String entryName() {
        return "farmersdelight_" + id;
    }

    public String nameKey() {
        return "item." + ThaumaturgeTechCompats.MOD_ID + "." + id;
    }

    public ResourceLocation plate() {
        return plate;
    }

    public Rarity rarity() {
        return rarity;
    }

    public int defaultDurability() {
        return durability;
    }

    public double defaultDamageBonus() {
        return damageBonus;
    }

    public double defaultAttackSpeed() {
        return attackSpeed;
    }

    public double defaultMiningSpeed() {
        return miningSpeed;
    }

    public int defaultEnchantability() {
        return enchantability;
    }

    public double defaultTaintChance() {
        return taintChance;
    }

    public int defaultTaintDuration() {
        return taintDuration;
    }

    public int vis() {
        return vis;
    }

    public FarmersDelightKnife previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }

    @Override
    public int getUses() {
        return FarmersDelightTaintedConfig.durability(this);
    }

    @Override
    public float getSpeed() {
        return FarmersDelightTaintedConfig.miningSpeed(this);
    }

    @Override
    public float getAttackDamageBonus() {
        return FarmersDelightTaintedConfig.damageBonus(this);
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return incorrectBlocks;
    }

    @Override
    public int getEnchantmentValue() {
        return FarmersDelightTaintedConfig.enchantability(this);
    }

    @Override
    public Ingredient getRepairIngredient() {
        Optional<Item> item = BuiltInRegistries.ITEM.getOptional(ingot);
        return item.map(value -> Ingredient.of(value)).orElse(Ingredient.EMPTY);
    }
}
