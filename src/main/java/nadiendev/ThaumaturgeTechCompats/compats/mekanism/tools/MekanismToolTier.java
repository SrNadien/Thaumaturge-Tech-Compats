package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public enum MekanismToolTier implements Tier {
    THAUMIUM("thaumium", "Thaumium", "taumio", "mekanism_energy_tools_thaumium", "thaumaturge:thaumium_", "thaumaturge:plate_thaumium",
            "mekanism:alloy_reinforced", "mekanism:advanced_control_circuit", Rarity.UNCOMMON, BlockTags.INCORRECT_FOR_IRON_TOOL,
            250_000L, 600L, 1000L, 500L, 6.0D, 2.0D, 14),
    BRASS("alchemical_brass", "Alchemical Brass", "latón alquímico", "mekanism_energy_tools_alchemical_brass", "minecraft:iron_", "thaumaturge:plate_brass",
            "mekanism:alloy_infused", "mekanism:basic_control_circuit", Rarity.RARE, BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            1_000_000L, 500L, 800L, 400L, 8.0D, 3.0D, 18),
    VOID("void", "Void Metal", "metal del vacío", "mekanism_energy_tools_void", "thaumaturge:void_", "thaumaturge:plate_void",
            "mekanism:alloy_atomic", "mekanism:ultimate_control_circuit", Rarity.EPIC, BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            4_000_000L, 400L, 600L, 300L, 10.0D, 3.5D, 22);

    private final String id;
    private final String english;
    private final String spanish;
    private final String toggle;
    private final String baseToolPrefix;
    private final String plate;
    private final String alloy;
    private final String circuit;
    private final Rarity rarity;
    private final TagKey<Block> incorrectBlocks;
    private final long capacity;
    private final long blockCost;
    private final long attackCost;
    private final long useCost;
    private final double miningSpeed;
    private final double attackDamage;
    private final int enchantability;

    MekanismToolTier(String id, String english, String spanish, String toggle, String baseToolPrefix, String plate, String alloy, String circuit,
                     Rarity rarity, TagKey<Block> incorrectBlocks, long capacity, long blockCost, long attackCost, long useCost,
                     double miningSpeed, double attackDamage, int enchantability) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.toggle = toggle;
        this.baseToolPrefix = baseToolPrefix;
        this.plate = plate;
        this.alloy = alloy;
        this.circuit = circuit;
        this.rarity = rarity;
        this.incorrectBlocks = incorrectBlocks;
        this.capacity = capacity;
        this.blockCost = blockCost;
        this.attackCost = attackCost;
        this.useCost = useCost;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
    }

    public String id() {
        return id;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public String toggle() {
        return toggle;
    }

    public String baseTool(MekanismToolKind kind) {
        return baseToolPrefix + kind.id();
    }

    public String plate() {
        return plate;
    }

    public String alloy() {
        return alloy;
    }

    public String circuit() {
        return circuit;
    }

    public Rarity rarity() {
        return rarity;
    }

    public long defaultCapacity() {
        return capacity;
    }

    public long defaultBlockCost() {
        return blockCost;
    }

    public long defaultAttackCost() {
        return attackCost;
    }

    public long defaultUseCost() {
        return useCost;
    }

    public double defaultMiningSpeed() {
        return miningSpeed;
    }

    public double defaultAttackDamage() {
        return attackDamage;
    }

    public int defaultEnchantability() {
        return enchantability;
    }

    public MekanismToolTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }

    @Override
    public int getUses() {
        return 1;
    }

    @Override
    public float getSpeed() {
        return (float) MekanismToolConfig.miningSpeed(this);
    }

    @Override
    public float getAttackDamageBonus() {
        return (float) MekanismToolConfig.attackDamage(this);
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return incorrectBlocks;
    }

    @Override
    public int getEnchantmentValue() {
        return MekanismToolConfig.enchantability(this);
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }
}
