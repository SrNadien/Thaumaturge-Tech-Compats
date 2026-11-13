package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MagicalUneartherFoodSlot extends SlotItemHandler {
    public MagicalUneartherFoodSlot(IItemHandler handler, int slot, int x, int y) {
        super(handler, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public boolean mayPickup(Player player) {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isHighlightable() {
        return false;
    }
}
