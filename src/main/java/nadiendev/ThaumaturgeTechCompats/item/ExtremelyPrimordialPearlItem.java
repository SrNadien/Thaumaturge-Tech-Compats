package nadiendev.ThaumaturgeTechCompats.item;

import nadiendev.ThaumaturgeTechCompats.config.ThaumaturgeTechCompatsConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Rarity;

public class ExtremelyPrimordialPearlItem extends Item {
    public ExtremelyPrimordialPearlItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public ItemStackTemplate getCraftingRemainder(ItemInstance instance) {
        return ThaumaturgeTechCompatsConfig.PEARL_KEPT_IN_CRAFTING.get() ? new ItemStackTemplate(this) : null;
    }
}
