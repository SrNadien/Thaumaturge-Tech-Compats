package nadiendev.ThaumaturgeTechCompats.flux;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Rarity;

public enum FluxTier implements StringRepresentable {
    THAUMIUM("thaumium", 0xC685FF, 16, 2_000L, 1_000_000L, 8, 200L, Rarity.UNCOMMON),
    ALCHEMICAL_BRASS("alchemical_brass", 0xEDB11A, 48, 20_000L, 25_000_000L, 12, 2_000L, Rarity.RARE),
    VOID_METAL("void_metal", 0x2B085C, 128, 200_000L, 1_000_000_000L, 16, 20_000L, Rarity.EPIC);

    public static final Codec<FluxTier> CODEC = StringRepresentable.fromEnum(FluxTier::values);

    private final String id;
    private final int color;
    private final int defaultLinkRange;
    private final long defaultLinkRate;
    private final long defaultCoreCapacity;
    private final int defaultChargerRadius;
    private final long defaultChargerRate;
    private final Rarity rarity;

    FluxTier(String id, int color, int defaultLinkRange, long defaultLinkRate, long defaultCoreCapacity,
             int defaultChargerRadius, long defaultChargerRate, Rarity rarity) {
        this.id = id;
        this.color = color;
        this.defaultLinkRange = defaultLinkRange;
        this.defaultLinkRate = defaultLinkRate;
        this.defaultCoreCapacity = defaultCoreCapacity;
        this.defaultChargerRadius = defaultChargerRadius;
        this.defaultChargerRate = defaultChargerRate;
        this.rarity = rarity;
    }

    public String id() {
        return id;
    }

    public int color() {
        return color;
    }

    public int defaultLinkRange() {
        return defaultLinkRange;
    }

    public long defaultLinkRate() {
        return defaultLinkRate;
    }

    public long defaultCoreCapacity() {
        return defaultCoreCapacity;
    }

    public int defaultChargerRadius() {
        return defaultChargerRadius;
    }

    public long defaultChargerRate() {
        return defaultChargerRate;
    }

    public Rarity rarity() {
        return rarity;
    }

    public FluxTier previous() {
        return this == THAUMIUM ? null : values()[ordinal() - 1];
    }

    public String toggle() {
        return "flux_network_" + id;
    }

    @Override
    public String getSerializedName() {
        return id;
    }
}
