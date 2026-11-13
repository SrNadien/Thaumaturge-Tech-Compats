package nadiendev.ThaumaturgeTechCompats.scribing;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;

public class InfinityScribingToolsItem extends Item {
    public InfinityScribingToolsItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
        if (stack.getDamageValue() > 0 && ScribingConfig.infinitySelfRepair()) {
            stack.setDamageValue(0);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }
}
