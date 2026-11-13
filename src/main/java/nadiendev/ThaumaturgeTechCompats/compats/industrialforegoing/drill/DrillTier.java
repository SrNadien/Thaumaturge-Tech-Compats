package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill;

import net.minecraft.ChatFormatting;

public enum DrillTier {
    POOR("poor", ChatFormatting.GRAY, 0, 0),
    COMMON("common", ChatFormatting.WHITE, 1, 100),
    UNCOMMON("uncommon", ChatFormatting.GREEN, 2, 400),
    RARE("rare", ChatFormatting.BLUE, 3, 1000),
    EPIC("epic", ChatFormatting.DARK_PURPLE, 4, 2000),
    LEGENDARY("legendary", ChatFormatting.GOLD, 5, 3500),
    ARTIFACT("artifact", ChatFormatting.YELLOW, 6, 4500);

    private final String id;
    private final ChatFormatting color;
    private final int defaultRadius;
    private final int defaultMinVis;

    DrillTier(String id, ChatFormatting color, int defaultRadius, int defaultMinVis) {
        this.id = id;
        this.color = color;
        this.defaultRadius = defaultRadius;
        this.defaultMinVis = defaultMinVis;
    }

    public String id() {
        return id;
    }

    public ChatFormatting color() {
        return color;
    }

    public int defaultRadius() {
        return defaultRadius;
    }

    public int defaultMinVis() {
        return defaultMinVis;
    }

    public int radius() {
        return MagicalInfinityDrillConfig.radius(this);
    }

    public int minVis() {
        return MagicalInfinityDrillConfig.minVis(this);
    }

    public boolean isLast() {
        return this == values()[values().length - 1];
    }

    public DrillTier next() {
        return isLast() ? this : values()[ordinal() + 1];
    }

    public static DrillTier byOrdinal(int ordinal) {
        DrillTier[] all = values();
        return all[Math.max(0, Math.min(all.length - 1, ordinal))];
    }

    public static DrillTier unlocked(int storedVis) {
        DrillTier[] all = values();
        for (int i = all.length - 1; i > 0; i--) {
            if (storedVis >= all[i].minVis()) {
                return all[i];
            }
        }
        return POOR;
    }
}
