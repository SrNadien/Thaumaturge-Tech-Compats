package nadiendev.ThaumaturgeTechCompats.flux.core;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public final class CoreTooltip {
    private static final String RULE = "═══";
    private static final String FILLED = "▌";
    private static final int BAR_LENGTH = 16;

    private CoreTooltip() {
    }

    public static String number(long value) {
        return String.format("%,d", value);
    }

    public static Component bar(ChatFormatting color, float fraction) {
        int filled = Math.max(0, Math.min(BAR_LENGTH, Math.round(fraction * BAR_LENGTH)));
        MutableComponent bar = Component.literal(FILLED.repeat(filled)).withStyle(color);
        return bar.append(Component.literal(FILLED.repeat(BAR_LENGTH - filled)).withStyle(ChatFormatting.DARK_GRAY));
    }

    private static Component name(CoreStatus status) {
        return Component.translatable(CoreTranslations.tierKey(status.tier())).withColor(status.tier().color());
    }

    private static Component stateOf(CoreStatus status) {
        if (!status.formed()) {
            return Component.translatable(CoreTranslations.STATE_IDLE).withStyle(ChatFormatting.GRAY);
        }
        if (status.overloaded()) {
            return Component.translatable(CoreTranslations.STATE_OVERLOADED).withStyle(ChatFormatting.RED);
        }
        return Component.translatable(CoreTranslations.STATE_STABLE).withStyle(ChatFormatting.GREEN);
    }

    private static Component structureOf(CoreStatus status) {
        if (status.formed()) {
            return Component.translatable(CoreTranslations.STRUCTURE_FORMED).withStyle(ChatFormatting.GREEN);
        }
        return Component.translatable(CoreTranslations.STRUCTURE_INCOMPLETE, status.missing()).withStyle(ChatFormatting.RED);
    }

    private static Component hint(CoreStatus status) {
        Component metal = Component.translatable(CoreTranslations.tierKey(status.tier()));
        String key;
        if (CoreConfig.requireRing() && CoreConfig.requireCaps()) {
            key = CoreTranslations.HINT_RING_AND_CAPS;
        } else if (CoreConfig.requireRing()) {
            key = CoreTranslations.HINT_RING;
        } else {
            key = CoreTranslations.HINT_CAPS;
        }
        return Component.translatable(key, metal).withStyle(ChatFormatting.GRAY);
    }

    public static List<Component> chatLines(CoreStatus status) {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal(RULE).withStyle(ChatFormatting.GRAY)
                .append(Component.translatable(CoreTranslations.CHAT_HEADER, name(status)))
                .append(Component.literal(" " + RULE).withStyle(ChatFormatting.GRAY)));
        ChatFormatting barColor = status.overloaded() ? ChatFormatting.RED : ChatFormatting.GREEN;
        lines.add(Component.translatable(CoreTranslations.CHAT_ENERGY).withStyle(ChatFormatting.YELLOW)
                .append(bar(barColor, status.fraction()))
                .append(Component.translatable(CoreTranslations.CHAT_ENERGY_VALUE, number(status.stored()), number(status.capacity())).withStyle(ChatFormatting.WHITE)));
        lines.add(Component.translatable(CoreTranslations.CHAT_STRUCTURE).withStyle(ChatFormatting.AQUA).append(structureOf(status)));
        if (!status.formed()) {
            lines.add(hint(status));
        }
        lines.add(Component.translatable(CoreTranslations.CHAT_STABILIZERS).withStyle(ChatFormatting.LIGHT_PURPLE)
                .append(Component.translatable(CoreTranslations.CHAT_STABILIZERS_VALUE, status.points(), status.required(), status.stabilizers()).withStyle(ChatFormatting.WHITE)));
        lines.add(Component.translatable(CoreTranslations.CHAT_CELLS).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(CoreTranslations.CHAT_CELLS_VALUE, status.cells(), number(status.cellCapacity())).withStyle(ChatFormatting.WHITE)));
        lines.add(Component.translatable(CoreTranslations.CHAT_STATUS).withStyle(ChatFormatting.GOLD).append(stateOf(status)));
        return lines;
    }

    public static List<Component> jadeLines(CoreStatus status) {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.translatable(CoreTranslations.JADE_ENERGY, number(status.stored()), number(status.capacity())).withStyle(ChatFormatting.YELLOW));
        lines.add(Component.translatable(CoreTranslations.JADE_STRUCTURE).withStyle(ChatFormatting.AQUA).append(structureOf(status)));
        lines.add(Component.translatable(CoreTranslations.JADE_STABILIZERS, status.points(), status.required()).withStyle(ChatFormatting.LIGHT_PURPLE));
        lines.add(Component.translatable(CoreTranslations.JADE_CELLS, status.cells(), number(status.cellCapacity())).withStyle(ChatFormatting.GOLD));
        if (status.overloaded()) {
            lines.add(Component.translatable(CoreTranslations.STATE_OVERLOADED).withStyle(ChatFormatting.RED));
        }
        return lines;
    }
}
