package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines;

import nadiendev.ThaumaturgeTechCompats.infusion.MatrixVariant;
import net.minecraft.world.item.Rarity;

public enum AvaritiaMachineTier {
    THAUMIUM("thaumium", "thaumaturge:plate_thaumium", "Thaumium", "taumio", "Thaumium", "de taumio", Rarity.RARE, false, 2, 1, 2, 1),
    ALCHEMICAL_BRASS("alchemical_brass", "thaumaturge:plate_brass", "Alchemical Brass", "latón alquímico", "Brass", "de latón", Rarity.EPIC, false, 4, 2, 4, 2),
    VOID_METAL("void_metal", "thaumaturge:plate_void", "Void Metal", "metal del vacío", "Void", "del vacío", Rarity.EPIC, true, 8, 4, 8, 4);

    private final String id;
    private final String plate;
    private final String english;
    private final String spanish;
    private final String shortEnglish;
    private final String shortSpanish;
    private final Rarity rarity;
    private final boolean fireResistant;
    private final int collectorSpeed;
    private final int collectorYield;
    private final int compressorSpeed;
    private final int compressorYield;

    AvaritiaMachineTier(String id, String plate, String english, String spanish, String shortEnglish, String shortSpanish,
                        Rarity rarity, boolean fireResistant,
                        int collectorSpeed, int collectorYield, int compressorSpeed, int compressorYield) {
        this.id = id;
        this.plate = plate;
        this.english = english;
        this.spanish = spanish;
        this.shortEnglish = shortEnglish;
        this.shortSpanish = shortSpanish;
        this.rarity = rarity;
        this.fireResistant = fireResistant;
        this.collectorSpeed = collectorSpeed;
        this.collectorYield = collectorYield;
        this.compressorSpeed = compressorSpeed;
        this.compressorYield = compressorYield;
    }

    public String id() {
        return id;
    }

    public String plate() {
        return plate;
    }

    public String collectorName() {
        return id + "_neutron_collector";
    }

    public String compressorName() {
        return id + "_neutron_compressor";
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public String shortEnglish() {
        return shortEnglish;
    }

    public String shortSpanish() {
        return shortSpanish;
    }

    public Rarity rarity() {
        return rarity;
    }

    public boolean fireResistant() {
        return fireResistant;
    }

    public int defaultCollectorSpeed() {
        return collectorSpeed;
    }

    public int defaultCollectorYield() {
        return collectorYield;
    }

    public int defaultCompressorSpeed() {
        return compressorSpeed;
    }

    public int defaultCompressorYield() {
        return compressorYield;
    }

    public MatrixVariant variant() {
        return MatrixVariant.values()[ordinal()];
    }

    public AvaritiaMachineTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }
}
