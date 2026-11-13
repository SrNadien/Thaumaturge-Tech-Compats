package nadiendev.ThaumaturgeTechCompats.taintedfood;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;

public enum TaintedFoodGender {
    MASCULINE("masculine"),
    FEMININE("feminine"),
    MASCULINE_PLURAL("masculine_plural"),
    FEMININE_PLURAL("feminine_plural");

    private final String key;

    TaintedFoodGender(String key) {
        this.key = key;
    }

    public String nameKey() {
        return "item." + ThaumaturgeTechCompats.MOD_ID + ".tainted_food." + key;
    }
}
