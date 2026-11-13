package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.TranslatableContents;

public final class MagicalTooltip {
    public static final String ENERGY = "tooltip.voidminers.controller.energy";
    public static final String CONSUMPTION = "tooltip.voidminers.controller.consumption";
    public static final String DURATION = "tooltip.voidminers.controller.duration";
    public static final String ITEM_BOOST = "tooltip.voidminers.controller.item_boost";
    public static final String EFFICIENCY = "tooltip.voidminers.controller.efficiency";
    public static final String STATUS = "tooltip.voidminers.controller.status.status";
    public static final String NOT_ENOUGH_POWER = "tooltip.voidminers.controller.status.not_enough_power";
    public static final String NOT_ENOUGH_POWER_BUFFER = "tooltip.voidminers.controller.status.not_enough_power_buffer";
    public static final String NOT_ENOUGH_POWER_NEXT = "tooltip.voidminers.controller.status.not_enough_power_for_next_operation";
    public static final String LIMITED_BY_RAIN = "tooltip.voidminers.controller.efficiency_limited_by_rain";
    public static final String LIMITED_BY_THUNDER = "tooltip.voidminers.controller.efficiency_limited_by_thunder";
    public static final String LIMITED_BY_TIME = "tooltip.voidminers.controller.efficiency_limited_by_time_of_day";

    private static final String UPGRADE_PREFIX = "⚙ UPGRADE";
    private static final String FILLED = "▌";
    private static final int BAR_LENGTH = 16;
    private static final float HALF = 0.5F;

    private MagicalTooltip() {
    }

    public static String key(Component line) {
        return line.getContents() instanceof TranslatableContents contents ? contents.getKey() : "";
    }

    public static boolean isUpgradeLine(Component line) {
        return line.getContents() instanceof PlainTextContents plain && plain.text().startsWith(UPGRADE_PREFIX);
    }

    public static String number(double value) {
        return String.format("%.2f", value);
    }

    public static String bar(String color, float fraction) {
        int filled = (int) (fraction * BAR_LENGTH);
        StringBuilder builder = new StringBuilder(color);
        for (int i = 0; i < BAR_LENGTH; i++) {
            if (i < filled || (i == filled && fraction * BAR_LENGTH - filled > HALF)) {
                builder.append(FILLED);
            } else {
                builder.append("§8").append(FILLED);
            }
        }
        return builder + "§r";
    }

    public static Component header(VoidMinersMagicalTier tier, boolean solar) {
        MutableComponent name = Component.translatable(solar ? VoidMinersMagicalTranslations.CHAT_HEADER_SOLAR : VoidMinersMagicalTranslations.CHAT_HEADER_MINER,
                Component.translatable(VoidMinersMagicalTranslations.chatTierKey(tier))).withColor(VoidMinersMagicalConfig.rgb(tier));
        return Component.literal("═══").withStyle(ChatFormatting.GRAY)
                .append(name)
                .append(Component.literal(" ═══").withStyle(ChatFormatting.GRAY));
    }

    public static Component aura(NodeSourceKind source) {
        Component value = source == null
                ? Component.translatable(VoidMinersMagicalTranslations.CHAT_AURA_NONE).withStyle(ChatFormatting.RED)
                : Component.translatable(VoidMinersMagicalTranslations.sourceKey(source)).withStyle(ChatFormatting.WHITE);
        return Component.translatable(VoidMinersMagicalTranslations.CHAT_AURA).append(value);
    }

    public static Component cost(int units, int interval, float multiplier) {
        return Component.translatable(VoidMinersMagicalTranslations.CHAT_COST)
                .append(Component.translatable(VoidMinersMagicalTranslations.CHAT_COST_VALUE, units, interval, number(multiplier)));
    }

    public static Component duration(int ticks, float factor) {
        return Component.translatable(VoidMinersMagicalTranslations.CHAT_DURATION)
                .append(Component.translatable(VoidMinersMagicalTranslations.CHAT_DURATION_VALUE, ticks, number(factor)));
    }

    public static Component boost(float multiplier) {
        return Component.translatable(ITEM_BOOST)
                .append(Component.translatable(VoidMinersMagicalTranslations.CHAT_BOOST_VALUE, number(multiplier)));
    }

    public static Component energy(long stored, long capacity) {
        float fraction = capacity <= 0L ? 0.0F : (float) ((double) stored / (double) capacity);
        return Component.translatable(VoidMinersMagicalTranslations.CHAT_ENERGY)
                .append(Component.translatable(VoidMinersMagicalTranslations.CHAT_ENERGY_VALUE, bar("§a", fraction),
                        String.format("%,d", stored), String.format("%,d", capacity)));
    }

    public static Component efficiency(float factor) {
        return Component.translatable(EFFICIENCY).withStyle(ChatFormatting.YELLOW)
                .append(Component.literal(number(factor * 100.0D) + "%").withStyle(ChatFormatting.WHITE));
    }

    public static Component upgrades(MagicalUpgradeTotals totals, boolean solar) {
        Component value = solar
                ? Component.translatable(VoidMinersMagicalTranslations.CHAT_UPGRADES_SOLAR, number(totals.generation()), number(totals.cost()))
                : Component.translatable(VoidMinersMagicalTranslations.CHAT_UPGRADES_MINER, number(totals.speed()), number(totals.production()), number(totals.cost()));
        return Component.translatable(VoidMinersMagicalTranslations.CHAT_UPGRADES).append(value);
    }

    public static Component stopped() {
        return Component.translatable(STATUS).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(VoidMinersMagicalTranslations.CHAT_STOPPED).withStyle(ChatFormatting.RED));
    }

    public static Component reason(MagicalPower.Failure failure) {
        return Component.translatable(VoidMinersMagicalTranslations.reasonKey(failure)).withStyle(ChatFormatting.YELLOW);
    }
}
