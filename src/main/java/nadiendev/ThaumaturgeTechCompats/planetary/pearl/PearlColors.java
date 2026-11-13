package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;

import java.awt.Color;

public final class PearlColors {
    private static final float MIN_TEXT_BRIGHTNESS = 0.8F;
    private static final long RAINBOW_PERIOD = 6_000L;

    private PearlColors() {
    }

    public static int textRgb(int rgb) {
        float[] hsb = Color.RGBtoHSB((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255, null);
        return Color.HSBtoRGB(hsb[0], hsb[1], Math.max(hsb[2], MIN_TEXT_BRIGHTNESS)) & 0xFFFFFF;
    }

    public static int rainbow(float offset) {
        float hue = ((System.currentTimeMillis() % RAINBOW_PERIOD) / (float) RAINBOW_PERIOD + offset) % 1.0F;
        return Color.HSBtoRGB(hue, 0.65F, 1.0F) & 0xFFFFFF;
    }

    public static int nameColor(PlanetaryTier tier) {
        return tier.creative() ? rainbow(0.0F) : textRgb(tier.color());
    }

    public static int planetColor(PlanetaryTier tier, int index) {
        return tier.creative() ? rainbow(index / 12.0F) : tier.color();
    }

    public static int mix(int from, int to, float amount) {
        int r = Math.round(((from >> 16) & 255) * (1.0F - amount) + ((to >> 16) & 255) * amount);
        int g = Math.round(((from >> 8) & 255) * (1.0F - amount) + ((to >> 8) & 255) * amount);
        int b = Math.round((from & 255) * (1.0F - amount) + (to & 255) * amount);
        return (r << 16) | (g << 8) | b;
    }
}
