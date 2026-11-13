package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ToggleButton;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;
import net.p3pp3rf1y.sophisticatedcore.upgrades.crafting.ICraftingUIPart;

import java.util.List;

import static net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper.GUI_CONTROLS;

public class ArcaneUpgradeTab extends UpgradeSettingsTab<ArcaneUpgradeContainer> {
    public static final String LABEL_KEY = "gui.thaumaturgetechcompats.upgrades.arcane_workbench";
    public static final String TOOLTIP_KEY = LABEL_KEY + ".tooltip";

    private static final TextureBlitData ARROW = new TextureBlitData(GUI_CONTROLS, new UV(97, 216), new Dimension(15, 8));
    private static final int CRYSTAL_COLUMNS = 3;
    private static final int CRYSTAL_ROWS = 2;
    public static final String WAND_HINT_KEY = LABEL_KEY + ".wand_slot";
    public static final String CHARGER_HINT_KEY = LABEL_KEY + ".charger_slot";

    private static final int EXTRA_ROW_OFFSET = 36 + 8;
    private static final int CRYSTAL_AREA_WIDTH = CRYSTAL_COLUMNS * 18 + 4;

    private final ICraftingUIPart craftingUIAddition;

    public ArcaneUpgradeTab(ArcaneUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen,
                            ButtonDefinition.Toggle<Boolean> shiftClickTargetButton, ButtonDefinition.Toggle<Boolean> refillCraftingGridButton) {
        super(upgradeContainer, position, screen, Component.translatable(LABEL_KEY), Component.translatable(TOOLTIP_KEY));
        addHideableChild(new ToggleButton<>(new Position(x + 3, y + 24), shiftClickTargetButton,
                button -> getContainer().setShiftClickIntoStorage(!getContainer().shouldShiftClickIntoStorage()), getContainer()::shouldShiftClickIntoStorage));
        addHideableChild(new ToggleButton<>(new Position(x + 21, y + 24), refillCraftingGridButton,
                button -> getContainer().setRefillCraftingGrid(!getContainer().shouldRefillCraftingGrid()), getContainer()::shouldRefillCraftingGrid));
        craftingUIAddition = screen.getCraftingUIAddition();
        openTabDimension = new Dimension(63 + craftingUIAddition.getWidth() + CRYSTAL_AREA_WIDTH, 142);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, minecraft, mouseX, mouseY);
        if (getContainer().isOpen()) {
            int gridX = x + 3 + craftingUIAddition.getWidth();
            GuiHelper.renderSlotsBackground(guiGraphics, gridX, y + 44, 3, 3);
            GuiHelper.blit(guiGraphics, gridX + 19, y + 101, ARROW);
            GuiHelper.blit(guiGraphics, gridX + 14, y + 111, GuiHelper.CRAFTING_RESULT_SLOT);
            GuiHelper.renderSlotsBackground(guiGraphics, gridX + 54 + 4, y + 44, CRYSTAL_COLUMNS, CRYSTAL_ROWS);
            GuiHelper.renderSlotsBackground(guiGraphics, gridX + 54 + 4, y + 44 + EXTRA_ROW_OFFSET, 2, 1);
        }
    }

    @Override
    protected void onTabClose() {
        super.onTabClose();
        craftingUIAddition.onCraftingSlotsHidden();
    }

    @Override
    protected void moveSlotsToTab() {
        List<Slot> slots = getContainer().getSlots();
        int gridX = x + 3 + craftingUIAddition.getWidth() - screen.getGuiLeft();
        int gridY = y + 44 - screen.getGuiTop();
        for (int slotNumber = 0; slotNumber < ArcaneUpgradeContainer.GRID; slotNumber++) {
            Slot slot = slots.get(slotNumber);
            SlotPositions.move(slot, gridX + 1 + (slotNumber % 3) * 18, gridY + 1 + (slotNumber / 3) * 18);
        }

        Slot resultSlot = slots.get(ArcaneUpgradeContainer.RESULT);
        SlotPositions.move(resultSlot, gridX + 19, gridY + 72);

        for (int crystal = 0; crystal < CRYSTAL_COLUMNS * CRYSTAL_ROWS; crystal++) {
            Slot slot = slots.get(ArcaneUpgradeContainer.CRYSTAL_START + crystal);
            SlotPositions.move(slot, gridX + 54 + 4 + 1 + (crystal % CRYSTAL_COLUMNS) * 18, gridY + 1 + (crystal / CRYSTAL_COLUMNS) * 18);
        }

        SlotPositions.move(slots.get(ArcaneUpgradeContainer.WAND_SLOT), gridX + 54 + 4 + 1, gridY + EXTRA_ROW_OFFSET + 1);
        SlotPositions.move(slots.get(ArcaneUpgradeContainer.CHARGER_SLOT), gridX + 54 + 4 + 1 + 18, gridY + EXTRA_ROW_OFFSET + 1);

        craftingUIAddition.onCraftingSlotsDisplayed(slots.subList(0, ArcaneUpgradeContainer.CRYSTAL_START));
    }

    @Override
    public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
        if (!getContainer().isOpen()) {
            return;
        }
        Slot wand = getContainer().getSlots().get(ArcaneUpgradeContainer.WAND_SLOT);
        Slot charger = getContainer().getSlots().get(ArcaneUpgradeContainer.CHARGER_SLOT);
        if (!wand.hasItem() && isOver(wand, mouseX, mouseY)) {
            guiGraphics.renderTooltip(minecraft.font, Component.translatable(WAND_HINT_KEY), mouseX, mouseY);
        } else if (!charger.hasItem() && isOver(charger, mouseX, mouseY)) {
            guiGraphics.renderTooltip(minecraft.font, Component.translatable(CHARGER_HINT_KEY), mouseX, mouseY);
        }
    }

    private boolean isOver(Slot slot, int mouseX, int mouseY) {
        int left = this.screen.getGuiLeft() + slot.x;
        int top = this.screen.getGuiTop() + slot.y;
        return mouseX >= left && mouseX < left + 16 && mouseY >= top && mouseY < top + 16;
    }
}
