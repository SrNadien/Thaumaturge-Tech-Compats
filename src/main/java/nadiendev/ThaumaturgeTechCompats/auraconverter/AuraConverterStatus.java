package nadiendev.ThaumaturgeTechCompats.auraconverter;

import nadiendev.ThaumaturgeTechCompats.auraconverter.gui.AuraConverterMenu;

public enum AuraConverterStatus {
    RUNNING("running", 0x55FF55),
    PAUSED("paused", 0xFFAA00),
    FULL("full", 0x55FFFF),
    NO_VIS("no_vis", 0xFF5555),
    CHUNK_LIMIT("chunk_limit", 0xFFFF55),
    DISABLED("disabled", 0xAAAAAA);

    private static final AuraConverterStatus[] VALUES = values();

    private final String id;
    private final int color;

    AuraConverterStatus(String id, int color) {
        this.id = id;
        this.color = color;
    }

    public String descriptionKey() {
        return translationKey() + ".desc";
    }

    public String translationKey() {
        return AuraConverterMenu.KEY + "status." + id;
    }

    public int color() {
        return color;
    }

    public static AuraConverterStatus byOrdinal(int ordinal) {
        return ordinal >= 0 && ordinal < VALUES.length ? VALUES[ordinal] : DISABLED;
    }
}
