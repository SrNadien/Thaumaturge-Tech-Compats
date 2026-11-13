package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeGuiManager;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;

public final class ArcaneUpgradeClient {
    public static final String DISCOUNT_KEY = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".arcane_upgrade_charger.discount";
    public static final String RADIUS_KEY = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".arcane_upgrade_charger.radius";

    private ArcaneUpgradeClient() {
    }

    public static void registerTab(UpgradeContainerType<ArcaneUpgradeWrapper, ArcaneUpgradeContainer> type) {
        NeoForge.EVENT_BUS.addListener(ArcaneUpgradeClient::addChargerTooltip);
        UpgradeGuiManager.registerTab(type, (ArcaneUpgradeContainer container, Position position, StorageScreenBase<?> screen) ->
                new ArcaneUpgradeTab(container, position, screen, ArcaneUpgradeButtons.SHIFT_CLICK_TARGET, ArcaneUpgradeButtons.REFILL_CRAFTING_GRID));
    }

    private static void addChargerTooltip(ItemTooltipEvent event) {
        ChargerEffect effect = ChargerEffects.of(event.getItemStack());
        if (effect == null) {
            return;
        }
        event.getToolTip().add(Component.translatable(DISCOUNT_KEY, effect.discountPercent()).withStyle(ChatFormatting.DARK_GRAY));
        event.getToolTip().add(Component.translatable(RADIUS_KEY, effect.radius()).withStyle(ChatFormatting.DARK_GRAY));
    }
}
