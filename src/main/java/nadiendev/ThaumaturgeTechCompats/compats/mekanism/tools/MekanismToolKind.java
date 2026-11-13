package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;

public enum MekanismToolKind {
    PICKAXE("pickaxe", "Pickaxe", "Pico", false, 1.0F, -2.8F, BlockTags.MINEABLE_WITH_PICKAXE, "pickaxes", "c:tools/mining_tool"),
    AXE("axe", "Axe", "Hacha", true, 5.0F, -3.0F, BlockTags.MINEABLE_WITH_AXE, "axes", "c:tools/mining_tool"),
    SHOVEL("shovel", "Shovel", "Pala", true, 1.5F, -3.0F, BlockTags.MINEABLE_WITH_SHOVEL, "shovels", "c:tools/mining_tool"),
    HOE("hoe", "Hoe", "Azada", true, 0.0F, 0.0F, BlockTags.MINEABLE_WITH_HOE, "hoes", "c:tools/mining_tool"),
    SWORD("sword", "Sword", "Espada", true, 3.0F, -2.4F, null, "swords", "c:tools/melee_weapon");

    private final String id;
    private final String english;
    private final String spanish;
    private final boolean feminine;
    private final float attackDamage;
    private final float attackSpeed;
    private final TagKey<Block> mineable;
    private final String vanillaTag;
    private final String conventionalTag;

    MekanismToolKind(String id, String english, String spanish, boolean feminine, float attackDamage, float attackSpeed,
                     TagKey<Block> mineable, String vanillaTag, String conventionalTag) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.feminine = feminine;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.mineable = mineable;
        this.vanillaTag = vanillaTag;
        this.conventionalTag = conventionalTag;
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

    public boolean feminine() {
        return feminine;
    }

    public String vanillaTag() {
        return vanillaTag;
    }

    public String conventionalTag() {
        return conventionalTag;
    }

    public boolean digger() {
        return mineable != null;
    }

    public ItemAttributeModifiers attributes(Tier tier) {
        if (this == SWORD) {
            return SwordItem.createAttributes(tier, attackDamage, attackSpeed);
        }
        float damage = this == HOE ? -tier.getAttackDamageBonus() : attackDamage;
        return DiggerItem.createAttributes(tier, damage, attackSpeed);
    }

    public Tool tool(Tier tier) {
        if (this == SWORD) {
            return SwordItem.createToolProperties();
        }
        return tier.createToolProperties(mineable);
    }
}
