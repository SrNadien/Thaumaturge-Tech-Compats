package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import dev.ftb.mods.ftbunearthed.menu.UneartherMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.items.SlotItemHandler;

public final class MagicalUneartherGui {
    private static final int BACKGROUND = 0xFFD4DBDB;
    private static final int SLOT_LEFT = 15;
    private static final int SLOT_TOP = 17;
    private static final int SLOT_RIGHT = 43;
    private static final int SLOT_BOTTOM = 35;
    private static final int BAR_LEFT = 7;
    private static final int BAR_TOP = 17;
    private static final int BAR_RIGHT = 15;
    private static final int BAR_BOTTOM = 70;
    private static final int BAR_TIP_LEFT = 8;
    private static final int BAR_TIP_TOP = 18;
    private static final int BAR_TIP_RIGHT = 14;
    private static final int BAR_TIP_BOTTOM = 68;

    private MagicalUneartherGui() {
    }

    public static boolean isMagical(AbstractContainerMenu menu) {
        return menu instanceof UneartherMenu unearther && unearther.getUnearther() instanceof MagicalUneartherBlockEntity;
    }

    public static void disableFoodSlot(UneartherMenu menu) {
        if (!(menu.getUnearther() instanceof MagicalUneartherBlockEntity unearther)) {
            return;
        }
        for (int i = 0; i < menu.slots.size(); i++) {
            Slot slot = menu.slots.get(i);
            if (slot instanceof SlotItemHandler handlerSlot && handlerSlot.getItemHandler() == unearther.getFoodHandler()) {
                Slot replacement = new MagicalUneartherFoodSlot(handlerSlot.getItemHandler(), handlerSlot.getSlotIndex(), slot.x, slot.y);
                replacement.index = slot.index;
                menu.slots.set(i, replacement);
            }
        }
    }

    public static void coverFood(GuiGraphics graphics, int left, int top) {
        graphics.fill(left + SLOT_LEFT, top + SLOT_TOP, left + SLOT_RIGHT, top + SLOT_BOTTOM, BACKGROUND);
        graphics.fill(left + BAR_LEFT, top + BAR_TOP, left + BAR_RIGHT, top + BAR_BOTTOM, BACKGROUND);
    }

    public static boolean overFoodBar(int left, int top, int mouseX, int mouseY) {
        return mouseX >= left + BAR_TIP_LEFT && mouseX <= left + BAR_TIP_RIGHT
                && mouseY >= top + BAR_TIP_TOP && mouseY <= top + BAR_TIP_BOTTOM;
    }
}
