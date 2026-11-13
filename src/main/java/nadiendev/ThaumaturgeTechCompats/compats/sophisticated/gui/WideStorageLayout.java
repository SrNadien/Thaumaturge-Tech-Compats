package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.gui;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageBackgroundProperties;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;

import java.util.Optional;

public final class WideStorageLayout {
    public static final int COLUMNS = 18;
    public static final int HEIGHT_WITHOUT_STORAGE_SLOTS = 114;

    private static final int MIN_SLOTS = 82;
    private static final int TEXTURE_WIDTH = 512;
    private static final int TEXTURE_HEIGHT = 256;
    private static final int SLOT = 18;
    private static final int SLOTS_X = 7;
    private static final int SLOTS_Y = 17;
    private static final int SLOT_TEXTURE_ROWS = 12;
    private static final int HEADER = 17;
    private static final int FOOTER = 97;
    private static final int STRETCH_MAX = 150;
    private static final Identifier BACKGROUND = texture("storage_wide");
    private static final Identifier BACKGROUND_SCROLL = texture("storage_wide_scroll");
    private static final Identifier SLOTS = texture("storage_wide_slots");

    public static final StorageBackgroundProperties REGULAR = new StorageBackgroundProperties(COLUMNS, 80, BACKGROUND);
    public static final StorageBackgroundProperties WIDER = new StorageBackgroundProperties(COLUMNS, 83, BACKGROUND_SCROLL);

    private WideStorageLayout() {
    }

    private static Identifier texture(String name) {
        return Identifier.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, "textures/gui/" + name + ".png");
    }

    public static boolean applies(StorageContainerMenuBase<?> menu) {
        return SophisticatedIntegration.wideGuiActive() && menu.getNumberOfStorageInventorySlots() >= MIN_SLOTS && isTierStorage(menu);
    }

    public static int rows(StorageContainerMenuBase<?> menu) {
        int columns = Math.max(1, COLUMNS - menu.getColumnsTaken());
        return (menu.getNumberOfStorageInventorySlots() + columns - 1) / columns;
    }

    public static boolean isWide(StorageBackgroundProperties properties) {
        return properties == REGULAR || properties == WIDER;
    }

    private static boolean isTierStorage(StorageContainerMenuBase<?> menu) {
        Optional<BlockPos> position = menu.getBlockPosition();
        if (position.isPresent() && Minecraft.getInstance().level != null) {
            Block block = Minecraft.getInstance().level.getBlockState(position.get()).getBlock();
            if (BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(ThaumaturgeTechCompats.MOD_ID)) {
                return true;
            }
        }
        ItemStack wrapped = menu.getStorageWrapper().getWrappedStorageStack();
        return !wrapped.isEmpty() && BuiltInRegistries.ITEM.getKey(wrapped.getItem()).getNamespace().equals(ThaumaturgeTechCompats.MOD_ID);
    }

    public static boolean drawBackground(GuiGraphics graphics, int x, int y, int width, int storageHeight, Identifier texture) {
        if (!texture.equals(BACKGROUND) && !texture.equals(BACKGROUND_SCROLL)) {
            return false;
        }
        int top = Math.min(storageHeight / 2, STRETCH_MAX);
        int stretch = storageHeight - 2 * top;
        graphics.blit(texture, x, y, 0, 0, width, HEADER + top, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        int drawn = 0;
        while (drawn < stretch) {
            int chunk = Math.min(STRETCH_MAX, stretch - drawn);
            graphics.blit(texture, x, y + HEADER + top + drawn, 0, HEADER, width, chunk, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            drawn += chunk;
        }
        graphics.blit(texture, x, y + HEADER + top + stretch, 0, TEXTURE_HEIGHT - (FOOTER + top), width, FOOTER + top, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        return true;
    }

    public static void drawSlots(GuiGraphics graphics, int x, int y, int slotsOnLine, int visibleSlots) {
        if (slotsOnLine <= 0 || visibleSlots <= 0) {
            return;
        }
        int left = x + SLOTS_X;
        int top = y + SLOTS_Y;
        int fullRows = visibleSlots / slotsOnLine;
        int remaining = visibleSlots % slotsOnLine;
        int row = 0;
        while (row < fullRows) {
            int chunk = Math.min(SLOT_TEXTURE_ROWS, fullRows - row);
            graphics.blit(SLOTS, left, top + row * SLOT, 0, 0, slotsOnLine * SLOT, chunk * SLOT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            row += chunk;
        }
        if (remaining > 0) {
            graphics.blit(SLOTS, left, top + fullRows * SLOT, 0, 0, remaining * SLOT, SLOT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }
}
