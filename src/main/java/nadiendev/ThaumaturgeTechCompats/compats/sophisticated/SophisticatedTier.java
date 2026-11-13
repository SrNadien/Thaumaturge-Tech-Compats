package nadiendev.ThaumaturgeTechCompats.compats.sophisticated;

import net.minecraft.world.item.Rarity;

public enum SophisticatedTier {
    THAUMIUM("thaumium", "thaumaturge:plate_thaumium", "Thaumium", "de taumio", Rarity.RARE, 0xC685FF),
    ALCHEMICAL_BRASS("alchemical_brass", "thaumaturge:plate_brass", "Alchemical Brass", "de latón alquímico", Rarity.EPIC, 0xEDB11A),
    VOID_METAL("void_metal", "thaumaturge:plate_void", "Void Metal", "de metal del vacío", Rarity.EPIC, 0x2B085C);

    private final String id;
    private final String plate;
    private final String english;
    private final String spanish;
    private final Rarity rarity;
    private final int color;

    SophisticatedTier(String id, String plate, String english, String spanish, Rarity rarity, int color) {
        this.id = id;
        this.plate = plate;
        this.english = english;
        this.spanish = spanish;
        this.rarity = rarity;
        this.color = color;
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

    public int color() {
        return color;
    }

    public int nameColor() {
        return this == VOID_METAL ? 0x8C4DE0 : color;
    }

    public SophisticatedTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }

    public String stackUpgradeName() {
        return id + "_stack_upgrade";
    }

    public String backpackName() {
        return id + "_backpack";
    }

    public String barrelName() {
        return id + "_barrel";
    }

    public String limitedBarrelName(int size) {
        return "limited_" + id + "_barrel_" + size;
    }

    public String chestName() {
        return id + "_chest";
    }

    public String shulkerBoxName() {
        return id + "_shulker_box";
    }

    public String clipsTexture() {
        return id + "_clips";
    }

    public String chestTexture() {
        return id + "_tier";
    }
}
