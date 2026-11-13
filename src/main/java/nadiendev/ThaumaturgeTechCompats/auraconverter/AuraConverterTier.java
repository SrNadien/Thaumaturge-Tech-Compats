package nadiendev.ThaumaturgeTechCompats.auraconverter;

import com.mojang.serialization.Codec;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.util.StringRepresentable;

public enum AuraConverterTier implements StringRepresentable {
    THAUMIUM("thaumium", CompatToggles.AURA_CONVERTER_THAUMIUM, 250_000L, 250_000L, 10_000_000L, 0x7B6FD0),
    BRASS("alchemical_brass", CompatToggles.AURA_CONVERTER_BRASS, 1_000_000L, 1_000_000L, 100_000_000L, 0xC9A24A),
    VOID("void_metal", CompatToggles.AURA_CONVERTER_VOID, 560_000_000L, 500_000_000L, 10_000_000_000L, 0x7A4FC2);

    public static final Codec<AuraConverterTier> CODEC = StringRepresentable.fromEnum(AuraConverterTier::values);

    private final String id;
    private final String toggle;
    private final long defaultFePerVis;
    private final long defaultOutput;
    private final long defaultBuffer;
    private final int accent;

    AuraConverterTier(String id, String toggle, long defaultFePerVis, long defaultOutput, long defaultBuffer, int accent) {
        this.id = id;
        this.toggle = toggle;
        this.defaultFePerVis = defaultFePerVis;
        this.defaultOutput = defaultOutput;
        this.defaultBuffer = defaultBuffer;
        this.accent = accent;
    }

    public String id() {
        return id;
    }

    public String blockName() {
        return id + "_aura_converter";
    }

    public String toggle() {
        return toggle;
    }

    public long defaultFePerVis() {
        return defaultFePerVis;
    }

    public long defaultOutput() {
        return defaultOutput;
    }

    public long defaultBuffer() {
        return defaultBuffer;
    }

    public int accent() {
        return accent;
    }

    public AuraConverterTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }

    @Override
    public String getSerializedName() {
        return id;
    }
}
