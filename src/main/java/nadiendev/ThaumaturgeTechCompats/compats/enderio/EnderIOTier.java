package nadiendev.ThaumaturgeTechCompats.compats.enderio;

import net.minecraft.world.item.Rarity;

public enum EnderIOTier {
    THAUMIUM("thaumium", "thaumaturge:plate_thaumium", "Thaumium", "de taumio", Rarity.RARE),
    ALCHEMICAL_BRASS("alchemical_brass", "thaumaturge:plate_brass", "Alchemical Brass", "de latón alquímico", Rarity.EPIC),
    VOID_METAL("void_metal", "thaumaturge:plate_void", "Void Metal", "de metal del vacío", Rarity.EPIC);

    private final String id;
    private final String plate;
    private final String english;
    private final String spanish;
    private final Rarity rarity;

    EnderIOTier(String id, String plate, String english, String spanish, Rarity rarity) {
        this.id = id;
        this.plate = plate;
        this.english = english;
        this.spanish = spanish;
        this.rarity = rarity;
    }

    public String id() {
        return id;
    }

    public String plate() {
        return plate;
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

    public String capacitorName() {
        return id + "_capacitor";
    }

    public EnderIOTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }
}
