package nadiendev.ThaumaturgeTechCompats.item;

import nadiendev.ThaumaturgeTechCompats.config.ThaumaturgeTechCompatsConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class ExtremelyPrimordialPearlItem extends Item {
    public ExtremelyPrimordialPearlItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return ThaumaturgeTechCompatsConfig.PEARL_KEPT_IN_CRAFTING.get();
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return ThaumaturgeTechCompatsConfig.PEARL_KEPT_IN_CRAFTING.get() ? stack.copy() : ItemStack.EMPTY;
    }
}
