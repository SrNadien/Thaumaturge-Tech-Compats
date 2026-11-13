package nadiendev.ThaumaturgeTechCompats.nodepower;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Rarity;

public enum FluxJarTier implements StringRepresentable {
    BASE("flux_jar", 100.0D, 0.25D, Rarity.UNCOMMON, false),
    MATRIX("matrix_flux_jar", 250.0D, 0.75D, Rarity.RARE, false),
    NEUTRONIUM("neutronium_flux_jar", 600.0D, 2.0D, Rarity.EPIC, true),
    INFINITY("infinity_flux_jar", 1500.0D, 6.0D, Rarity.EPIC, true);

    public static final StringRepresentable.EnumCodec<FluxJarTier> CODEC = StringRepresentable.fromEnum(FluxJarTier::values);

    private final String blockName;
    private final double defaultCapacity;
    private final double defaultFillAmount;
    private final Rarity rarity;
    private final boolean fireResistant;

    FluxJarTier(String blockName, double defaultCapacity, double defaultFillAmount, Rarity rarity, boolean fireResistant) {
        this.blockName = blockName;
        this.defaultCapacity = defaultCapacity;
        this.defaultFillAmount = defaultFillAmount;
        this.rarity = rarity;
        this.fireResistant = fireResistant;
    }

    public String blockName() {
        return blockName;
    }

    public double defaultCapacity() {
        return defaultCapacity;
    }

    public double defaultFillAmount() {
        return defaultFillAmount;
    }

    public Rarity rarity() {
        return rarity;
    }

    public boolean fireResistant() {
        return fireResistant;
    }

    public FluxJarTier previous() {
        return this == BASE ? null : values()[ordinal() - 1];
    }

    @Override
    public String getSerializedName() {
        return blockName;
    }
}
