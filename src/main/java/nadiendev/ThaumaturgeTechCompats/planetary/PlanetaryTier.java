package nadiendev.ThaumaturgeTechCompats.planetary;

import net.minecraft.world.item.Rarity;

public enum PlanetaryTier {
    COPPER("copper", "Copper", "de cobre", 0xB87333, Rarity.COMMON, "minecraft:copper_ingot"),
    IRON("iron", "Iron", "de hierro", 0xD8D8D8, Rarity.COMMON, "minecraft:iron_ingot"),
    GOLD("gold", "Gold", "de oro", 0xFAD64A, Rarity.UNCOMMON, "minecraft:gold_ingot"),
    DIAMOND("diamond", "Diamond", "de diamante", 0x4AEDD9, Rarity.UNCOMMON, "minecraft:diamond"),
    EMERALD("emerald", "Emerald", "de esmeralda", 0x17DD62, Rarity.UNCOMMON, "minecraft:emerald"),
    NETHERITE("netherite", "Netherite", "de netherita", 0x5A4A52, Rarity.RARE, "minecraft:netherite_ingot"),
    THAUMIUM("thaumium", "Thaumium", "de taumio", 0xC685FF, Rarity.RARE, "thaumaturge:ingot_thaumium"),
    ALCHEMICAL_BRASS("alchemical_brass", "Alchemical Brass", "de latón alquímico", 0xEDB11A, Rarity.RARE, "thaumaturge:ingot_brass"),
    VOID_METAL("void_metal", "Void Metal", "de metal del vacío", 0x2B085C, Rarity.EPIC, "thaumaturge:ingot_void"),
    BLAZE_CUBE("blaze_cube", "Blaze Cube", "de cubo de blaze", 0xFF8A1F, Rarity.EPIC, "avaritia:blaze_cube"),
    MATRIX("matrix", "Crystal Matrix", "de matriz de cristal", 0x69E0F0, Rarity.EPIC, "avaritia:crystal_matrix_ingot"),
    NEUTRONIUM("neutronium", "Neutronium", "de neutronio", 0x3E4048, Rarity.EPIC, "avaritia:neutron_ingot"),
    INFINITY("infinity", "Infinity", "infinito", 0xFFD6FF, Rarity.EPIC, "avaritia:infinity_ingot"),
    CREATIVE("creative", "Creative", "creativo", 0xFF55FF, Rarity.EPIC, null);

    private final String id;
    private final String english;
    private final String spanish;
    private final int color;
    private final Rarity rarity;
    private final String ingot;

    PlanetaryTier(String id, String english, String spanish, int color, Rarity rarity, String ingot) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.color = color;
        this.rarity = rarity;
        this.ingot = ingot;
    }

    public String id() {
        return id;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public int color() {
        return color;
    }

    public Rarity rarity() {
        return rarity;
    }

    public String ingot() {
        return ingot;
    }

    public boolean creative() {
        return this == CREATIVE;
    }

    public boolean killsBosses() {
        return this == NETHERITE || this == VOID_METAL || this == INFINITY || this == CREATIVE;
    }

    public boolean unbreakable() {
        return killsBosses();
    }

    public boolean craftable() {
        return ingot != null;
    }

    public PlanetaryTier previous() {
        return ordinal() == 0 || this == CREATIVE ? null : values()[ordinal() - 1];
    }

    public String pearlToggle() {
        return "planetary_pearl_" + id;
    }

    public String crucifixToggle() {
        return "star_crucifix_" + id;
    }
}
