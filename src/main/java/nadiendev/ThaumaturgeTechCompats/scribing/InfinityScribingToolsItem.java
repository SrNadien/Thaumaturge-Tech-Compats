package nadiendev.ThaumaturgeTechCompats.scribing;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InfinityScribingToolsItem extends Item {
    public InfinityScribingToolsItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && stack.getDamageValue() > 0 && ScribingConfig.infinitySelfRepair()) {
            stack.setDamageValue(0);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }
}
