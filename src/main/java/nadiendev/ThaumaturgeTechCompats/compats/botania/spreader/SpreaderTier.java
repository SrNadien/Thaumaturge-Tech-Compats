package nadiendev.ThaumaturgeTechCompats.compats.botania.spreader;

import net.minecraft.world.item.Rarity;

public enum SpreaderTier {
    THAUMIUM("thaumium", "thaumaturge:plate_thaumium", "Thaumium", "taumio", Rarity.RARE, false, 1280, 12800, 140, 24.0F, 2.5F, 0x6A66E0),
    ALCHEMICAL_BRASS("alchemical_brass", "thaumaturge:plate_brass", "Alchemical Brass", "latón alquímico", Rarity.EPIC, false, 2560, 25600, 160, 28.0F, 3.0F, 0xE2A83A),
    VOID_METAL("void_metal", "thaumaturge:plate_void", "Void Metal", "metal del vacío", Rarity.EPIC, true, 10240, 102400, 200, 32.0F, 4.0F, 0x9B4DE0);

    private final String id;
    private final String plate;
    private final String english;
    private final String spanish;
    private final Rarity rarity;
    private final boolean fireResistant;
    private final int burstMana;
    private final int manaCapacity;
    private final int preLossTicks;
    private final float lossPerTick;
    private final float motionModifier;
    private final int color;

    SpreaderTier(String id, String plate, String english, String spanish, Rarity rarity, boolean fireResistant,
                 int burstMana, int manaCapacity, int preLossTicks, float lossPerTick, float motionModifier, int color) {
        this.id = id;
        this.plate = plate;
        this.english = english;
        this.spanish = spanish;
        this.rarity = rarity;
        this.fireResistant = fireResistant;
        this.burstMana = burstMana;
        this.manaCapacity = manaCapacity;
        this.preLossTicks = preLossTicks;
        this.lossPerTick = lossPerTick;
        this.motionModifier = motionModifier;
        this.color = color;
    }

    public String id() {
        return id;
    }

    public String plate() {
        return plate;
    }

    public String blockName() {
        return id + "_spreader";
    }

    public String toggle() {
        return "botania_spreader_" + id;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public Rarity rarity() {
        return rarity;
    }

    public boolean fireResistant() {
        return fireResistant;
    }

    public int defaultBurstMana() {
        return burstMana;
    }

    public int defaultManaCapacity() {
        return manaCapacity;
    }

    public int defaultPreLossTicks() {
        return preLossTicks;
    }

    public float defaultLossPerTick() {
        return lossPerTick;
    }

    public float defaultMotionModifier() {
        return motionModifier;
    }

    public int defaultColor() {
        return color;
    }

    public SpreaderTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }
}
