package nadiendev.ThaumaturgeTechCompats.jar;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Rarity;

public enum JarTier implements StringRepresentable {
    MATRIX("matrix", 500, Rarity.RARE, false),
    NEUTRONIUM("neutronium", 1000, Rarity.EPIC, true),
    INFINITY("infinity", 2500, Rarity.EPIC, true);

    public static final StringRepresentable.EnumCodec<JarTier> CODEC = StringRepresentable.fromEnum(JarTier::values);

    private final String id;
    private final int defaultCapacity;
    private final Rarity rarity;
    private final boolean fireResistant;

    JarTier(String id, int defaultCapacity, Rarity rarity, boolean fireResistant) {
        this.id = id;
        this.defaultCapacity = defaultCapacity;
        this.rarity = rarity;
        this.fireResistant = fireResistant;
    }

    public String id() {
        return id;
    }

    public String jarName() {
        return id + "_jar";
    }

    public int defaultCapacity() {
        return defaultCapacity;
    }

    public Rarity rarity() {
        return rarity;
    }

    public boolean fireResistant() {
        return fireResistant;
    }

    @Override
    public String getSerializedName() {
        return id;
    }
}
