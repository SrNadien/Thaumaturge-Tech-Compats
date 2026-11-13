package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinition;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonDefinitions;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.UV;

import java.util.Map;

import static net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper.getButtonStateData;

public final class ArcaneUpgradeButtons {
    private static final String PREFIX = "gui." + ThaumaturgeTechCompats.MOD_ID + ".upgrades.buttons.";

    public static final ButtonDefinition.Toggle<Boolean> SHIFT_CLICK_TARGET = ButtonDefinitions.createToggleButtonDefinition(Map.of(true,
            getButtonStateData(new UV(32, 48), Dimension.SQUARE_16, new Position(1, 1),
                    TranslationHelper.INSTANCE.getTranslatedLines(PREFIX + "shift_click_into_storage", null)),
            false, getButtonStateData(new UV(48, 48), Dimension.SQUARE_16, new Position(1, 1),
                    TranslationHelper.INSTANCE.getTranslatedLines(PREFIX + "shift_click_into_inventory", null))));

    public static final ButtonDefinition.Toggle<Boolean> REFILL_CRAFTING_GRID = ButtonDefinitions.createToggleButtonDefinition(Map.of(true,
            getButtonStateData(new UV(32, 112), Dimension.SQUARE_16, new Position(1, 1),
                    TranslationHelper.INSTANCE.getTranslatedLines(PREFIX + "refill_crafting_grid", null)),
            false, getButtonStateData(new UV(48, 112), Dimension.SQUARE_16, new Position(1, 1),
                    TranslationHelper.INSTANCE.getTranslatedLines(PREFIX + "do_not_refill_crafting_grid", null))));

    private ArcaneUpgradeButtons() {
    }
}
