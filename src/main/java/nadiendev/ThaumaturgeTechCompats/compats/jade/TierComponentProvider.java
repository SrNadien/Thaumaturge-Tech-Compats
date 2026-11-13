package nadiendev.ThaumaturgeTechCompats.compats.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.Locale;

public enum TierComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final String PREFIX = "jade.thaumaturgetechcompats.";
    private static final int TICKS_PER_SECOND = 20;
    private static final long RAINBOW_STEP_MILLIS = 25L;
    private static final int RAINBOW_SPREAD = 30;

    @Override
    public Identifier getUid() {
        return TierDataProvider.UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (!data.contains(JadeKeys.ROOT)) {
            return;
        }
        CompoundTag tag = data.getCompoundOrEmpty(JadeKeys.ROOT);
        JadeTier tier = JadeTier.byOrdinal(tag.getIntOr(JadeKeys.TIER, 0));
        JadeFamily family = JadeFamily.byOption(tag.getStringOr(JadeKeys.FAMILY, ""));
        if (tier == null || family == null) {
            return;
        }
        if (tag.getBooleanOr(JadeKeys.SHOW_TIER, false) && config.get(JadeFamily.TIER.optionId())) {
            tooltip.add(Component.translatable(PREFIX + "tier", tierName(tier)).withStyle(ChatFormatting.GRAY));
        }
        if (tag.contains(JadeKeys.STATS) && (!family.configurable() || config.get(family.optionId()))) {
            stats(tooltip, family, tier, tag.getCompoundOrEmpty(JadeKeys.STATS));
        }
    }

    private static void stats(ITooltip tooltip, JadeFamily family, JadeTier tier, CompoundTag stats) {
        switch (family) {
            case STABILIZER -> {
                line(tooltip, "stabilizer.lock", tier, stats.getInt(JadeKeys.LOCK));
                line(tooltip, "stabilizer.feeding", tier, stats.getInt(JadeKeys.FEEDING));
                line(tooltip, "stabilizer.settling", tier, stats.getInt(JadeKeys.SPEED));
            }
            case SMELTER -> {
                int interval = stats.getIntOr(JadeKeys.INTERVAL, 0);
                line(tooltip, "smelter.interval", tier, interval, number(interval / (double) TICKS_PER_SECOND));
                line(tooltip, "smelter.efficiency", tier, number(stats.getFloatOr(JadeKeys.EFFICIENCY, 0.0F) * 100.0));
            }
            case TRANSDUCER -> {
                line(tooltip, "transducer.charge", tier, stats.getInt(JadeKeys.CHARGE));
                int decay = stats.getIntOr(JadeKeys.DECAY, 0);
                line(tooltip, decay <= 1 ? "transducer.decay_every_tick" : "transducer.decay", tier, decay);
            }
            case FURNACE -> {
                line(tooltip, "furnace.speed", tier, stats.getInt(JadeKeys.SPEED));
                line(tooltip, "furnace.savings", tier, stats.getInt(JadeKeys.SAVINGS));
            }
            case BRAIN_BOX -> line(tooltip, "mnemonic_matrix.formulas", tier, stats.getInt(JadeKeys.FORMULAS));
            case CHARGER -> {
                line(tooltip, "workbench_charger.discount", tier, stats.getInt(JadeKeys.DISCOUNT));
                line(tooltip, "workbench_charger.radius", tier, stats.getInt(JadeKeys.RADIUS));
            }
            default -> {
            }
        }
    }

    private static void line(ITooltip tooltip, String key, JadeTier tier, Object... values) {
        Object[] components = new Object[values.length];
        for (int i = 0; i < values.length; i++) {
            components[i] = Component.literal(String.valueOf(values[i])).withStyle(Style.EMPTY.withColor(tier.color()));
        }
        tooltip.add(Component.translatable(PREFIX + key, components).withStyle(ChatFormatting.GRAY));
    }

    private static Component tierName(JadeTier tier) {
        String name = Component.translatable(tier.translationKey()).getString();
        if (tier != JadeTier.INFINITY) {
            return Component.literal(name).withStyle(Style.EMPTY.withColor(tier.color()));
        }
        MutableComponent rainbow = Component.empty();
        long phase = Util.getMillis() / RAINBOW_STEP_MILLIS;
        for (int i = 0; i < name.length(); i++) {
            float hue = ((phase + (long) i * RAINBOW_SPREAD) % 360L) / 360.0F;
            rainbow.append(Component.literal(String.valueOf(name.charAt(i))).withStyle(Style.EMPTY.withColor(Mth.hsvToRgb(hue, 0.6F, 1.0F))));
        }
        return rainbow;
    }

    private static String number(double value) {
        String text = String.format(Locale.ROOT, "%.2f", value);
        if (text.contains(".")) {
            text = text.replaceAll("0+$", "").replaceAll("\\.$", "");
        }
        return text;
    }
}
