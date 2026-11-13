package nadiendev.ThaumaturgeTechCompats.scribing;

import net.minecraft.world.item.Rarity;

public enum ScribingTier {
    MATRIX("matrix", 300, Rarity.RARE, false),
    NEUTRONIUM("neutronium", 1000, Rarity.EPIC, true),
    INFINITY("infinity", 1_000_000, Rarity.EPIC, true);

    private final String id;
    private final int durability;
    private final Rarity rarity;
    private final boolean fireResistant;

    ScribingTier(String id, int durability, Rarity rarity, boolean fireResistant) {
        this.id = id;
        this.durability = durability;
        this.rarity = rarity;
        this.fireResistant = fireResistant;
    }

    public String id() {
        return id;
    }

    public String toolsName() {
        return id + "_scribing_tools";
    }

    public int durability() {
        return durability;
    }

    public Rarity rarity() {
        return rarity;
    }

    public boolean fireResistant() {
        return fireResistant;
    }
}
