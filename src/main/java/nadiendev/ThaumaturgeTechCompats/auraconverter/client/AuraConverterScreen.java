package nadiendev.ThaumaturgeTechCompats.auraconverter.client;

import com.leclowndu93150.thaumaturge.client.screen.AbstractTTContainerScreen;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterStatus;
import nadiendev.ThaumaturgeTechCompats.auraconverter.gui.AuraConverterMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AuraConverterScreen extends AbstractTTContainerScreen<AuraConverterMenu> {
    private static final String KEY = AuraConverterMenu.KEY;
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("thaumaturge", "textures/gui/gui_void_siphon.png");
    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;
    private static final int TEXTURE_SIZE = 256;
    private static final int INVENTORY_V = 77;
    private static final int INVENTORY_HEIGHT = 89;

    private static final int PANEL_LEFT = 4;
    private static final int PANEL_TOP = 4;
    private static final int PANEL_RIGHT = 172;
    private static final int PANEL_BOTTOM = 75;
    private static final int TITLE_X = 10;
    private static final int TITLE_Y = 9;
    private static final float TITLE_WIDTH = 156.0F;

    private static final int BAR_TOP = 22;
    private static final int BAR_HEIGHT = 48;
    private static final int BAR_WIDTH = 14;
    private static final int ENERGY_BAR_X = 11;
    private static final int AURA_BAR_X = 151;

    private static final int TEXT_X = 32;
    private static final int TEXT_Y = 22;
    private static final int LINE_HEIGHT = 10;
    private static final int LINES = 5;

    private static final int OUTLINE = 0xFF000000;
    private static final int BODY_TOP = 0xFF2C2338;
    private static final int BODY_BOTTOM = 0xFF18121F;
    private static final int BAR_BACKGROUND = 0xFF0E0A12;
    private static final int BAR_GRID = 0x55000000;
    private static final int ENERGY_TOP = 0xFFFF6B4A;
    private static final int ENERGY_BOTTOM = 0xFF9E1B12;
    private static final int AURA_TOP = 0xFFC79BFF;
    private static final int AURA_BOTTOM = 0xFF5B2DB0;
    private static final int FLOOR_MARK = 0xFFFFFFFF;
    private static final int LABEL = 0xFFC8C8D8;
    private static final int TITLE = 0xFFFFFFFF;
    private static final int OPAQUE = 0xFF000000;
    private static final int COMPACT_LIMIT = 10_000;
    private static final String[] SUFFIXES = {"k", "M", "G", "T", "P"};

    public AuraConverterScreen(AuraConverterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, TEXTURE, WIDTH, HEIGHT);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBackgroundTexture(GuiGraphics graphics) {
        graphics.blit(TEXTURE, leftPos, topPos + INVENTORY_V, 0.0F, (float) INVENTORY_V, WIDTH, INVENTORY_HEIGHT, TEXTURE_SIZE, TEXTURE_SIZE);
        drawPanel(graphics);
    }

    @Override
    protected void renderBackgroundOverlay(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        drawTitle(graphics);
        drawBar(graphics, ENERGY_BAR_X, energyRatio(), ENERGY_TOP, ENERGY_BOTTOM, -1.0D);
        drawBar(graphics, AURA_BAR_X, auraRatio(), AURA_TOP, AURA_BOTTOM, floorRatio());
        AuraConverterStatus status = menu.status();
        Component state = Component.translatable(KEY + "state", Component.translatable(status.translationKey()).withStyle(Style.EMPTY.withColor(status.color())));
        drawLine(graphics, state, 0);
        drawLine(graphics, Component.translatable(KEY + "stored", compact(menu.value(AuraConverterMenu.ENERGY))), 1);
        drawLine(graphics, Component.translatable(KEY + "aura", compact((long) menu.floatValue(AuraConverterMenu.VIS)), compact((long) auraScale())), 2);
        drawLine(graphics, Component.translatable(KEY + "converting", compact(menu.value(AuraConverterMenu.PRODUCED))), 3);
        drawLine(graphics, Component.translatable(KEY + "output", compact(menu.value(AuraConverterMenu.OUTPUT))), 4);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        List<Component> tooltip = hoverTooltip(mouseX, mouseY);
        if (!tooltip.isEmpty()) {
            graphics.renderComponentTooltip(font, tooltip, mouseX, mouseY);
        }
    }

    private List<Component> hoverTooltip(int mouseX, int mouseY) {
        List<Component> lines = new ArrayList<>();
        if (over(mouseX, mouseY, ENERGY_BAR_X, BAR_TOP, BAR_WIDTH, BAR_HEIGHT)) {
            lines.add(Component.translatable(KEY + "tooltip.energy.title"));
            lines.add(Component.translatable(KEY + "tooltip.energy.amount", exact(menu.value(AuraConverterMenu.ENERGY)), exact(menu.value(AuraConverterMenu.CAPACITY))));
            lines.add(Component.translatable(KEY + "tooltip.energy.output", exact(menu.value(AuraConverterMenu.MAX_OUTPUT))));
        } else if (over(mouseX, mouseY, AURA_BAR_X, BAR_TOP, BAR_WIDTH, BAR_HEIGHT)) {
            lines.add(Component.translatable(KEY + "tooltip.aura.title"));
            lines.add(Component.translatable(KEY + "tooltip.aura.amount", decimal(menu.floatValue(AuraConverterMenu.VIS)), decimal(menu.floatValue(AuraConverterMenu.BASE))));
            lines.add(Component.translatable(KEY + "tooltip.aura.floor", decimal(menu.floatValue(AuraConverterMenu.FLOOR))));
            lines.add(Component.translatable(KEY + "tooltip.aura.rate", exact(menu.value(AuraConverterMenu.FE_PER_VIS))));
        } else if (over(mouseX, mouseY, TEXT_X, TEXT_Y, AURA_BAR_X - TEXT_X - 2, LINE_HEIGHT)) {
            AuraConverterStatus status = menu.status();
            lines.add(Component.translatable(status.translationKey()).withStyle(Style.EMPTY.withColor(status.color())));
            lines.add(Component.translatable(status.descriptionKey()));
        }
        return lines;
    }

    private boolean over(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= leftPos + x && mouseX < leftPos + x + width && mouseY >= topPos + y && mouseY < topPos + y + height;
    }

    private void drawPanel(GuiGraphics graphics) {
        int left = leftPos + PANEL_LEFT;
        int top = topPos + PANEL_TOP;
        int right = leftPos + PANEL_RIGHT;
        int bottom = topPos + PANEL_BOTTOM;
        graphics.fill(left, top, right, bottom, OUTLINE);
        graphics.fill(left + 1, top + 1, right - 1, bottom - 1, OPAQUE | menu.tier().accent());
        graphics.fill(left + 2, top + 2, right - 2, bottom - 2, OUTLINE);
        graphics.fillGradient(left + 3, top + 3, right - 3, bottom - 3, BODY_TOP, BODY_BOTTOM);
    }

    private void drawTitle(GuiGraphics graphics) {
        Component title = getTitle();
        float scale = Math.min(1.0F, TITLE_WIDTH / Math.max(1, font.width(title)));
        graphics.pose().pushPose();
        graphics.pose().translate(leftPos + TITLE_X, topPos + TITLE_Y, 0.0F);
        graphics.pose().scale(scale, scale, 1.0F);
        graphics.drawString(font, title, 0, 0, TITLE, true);
        graphics.pose().popPose();
    }

    private void drawLine(GuiGraphics graphics, Component text, int line) {
        if (line < LINES) {
            graphics.drawString(font, text, leftPos + TEXT_X, topPos + TEXT_Y + line * LINE_HEIGHT, LABEL, false);
        }
    }

    private void drawBar(GuiGraphics graphics, int relativeX, double ratio, int top, int bottom, double floor) {
        int x = leftPos + relativeX;
        int y = topPos + BAR_TOP;
        graphics.fill(x - 1, y - 1, x + BAR_WIDTH + 1, y + BAR_HEIGHT + 1, OUTLINE);
        graphics.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, BAR_BACKGROUND);
        int filled = (int) Math.round(BAR_HEIGHT * clamp(ratio));
        if (filled > 0) {
            graphics.fillGradient(x, y + BAR_HEIGHT - filled, x + BAR_WIDTH, y + BAR_HEIGHT, top, bottom);
        }
        for (int step = 1; step < 4; step++) {
            int line = y + BAR_HEIGHT * step / 4;
            graphics.fill(x, line, x + BAR_WIDTH, line + 1, BAR_GRID);
        }
        if (floor >= 0.0D) {
            int line = y + BAR_HEIGHT - (int) Math.round(BAR_HEIGHT * clamp(floor));
            graphics.fill(x, line, x + BAR_WIDTH, line + 1, FLOOR_MARK);
        }
    }

    private double energyRatio() {
        long capacity = menu.value(AuraConverterMenu.CAPACITY);
        return capacity <= 0L ? 0.0D : (double) menu.value(AuraConverterMenu.ENERGY) / capacity;
    }

    private float auraScale() {
        return Math.max(1.0F, Math.max(menu.floatValue(AuraConverterMenu.BASE), menu.floatValue(AuraConverterMenu.VIS)));
    }

    private double auraRatio() {
        return menu.floatValue(AuraConverterMenu.VIS) / auraScale();
    }

    private double floorRatio() {
        return menu.floatValue(AuraConverterMenu.FLOOR) / auraScale();
    }

    private static double clamp(double value) {
        return Math.max(0.0D, Math.min(1.0D, value));
    }

    private static String exact(long value) {
        return NumberFormat.getIntegerInstance().format(value);
    }

    private static String decimal(float value) {
        return String.format(Locale.ROOT, "%.1f", value);
    }

    private static String compact(long value) {
        if (value < COMPACT_LIMIT) {
            return exact(value);
        }
        double scaled = value;
        int suffix = -1;
        while (scaled >= 1000.0D && suffix < SUFFIXES.length - 1) {
            scaled /= 1000.0D;
            suffix++;
        }
        String format = scaled >= 100.0D ? "%.0f%s" : scaled >= 10.0D ? "%.1f%s" : "%.2f%s";
        return String.format(Locale.ROOT, format, scaled, SUFFIXES[suffix]);
    }
}
