package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.world.item.Rarity;

public enum FtbUnearthedBrushTier {
    BRASS("brass", "brass_brush", "thaumaturge:plate_brass", "Brass", "latón", CompatToggles.FTB_UNEARTHED_BRUSH_BRASS,
            192, 60, 0.6F, 1.0D, 10, Rarity.COMMON),
    THAUMIUM("thaumium", "thaumium_brush", "thaumaturge:plate_thaumium", "Thaumium", "taumio", CompatToggles.FTB_UNEARTHED_BRUSH_THAUMIUM,
            512, 40, 0.4F, 1.75D, 20, Rarity.UNCOMMON),
    VOID("void", "void_brush", "thaumaturge:plate_void", "Void Metal", "metal del vacío", CompatToggles.FTB_UNEARTHED_BRUSH_VOID,
            1024, 25, 0.25F, 3.0D, 30, Rarity.RARE);

    private final String id;
    private final String itemId;
    private final String plate;
    private final String english;
    private final String spanish;
    private final String toggle;
    private final int durability;
    private final int processingTime;
    private final float damageChance;
    private final double lootMultiplier;
    private final int vis;
    private final Rarity rarity;

    FtbUnearthedBrushTier(String id, String itemId, String plate, String english, String spanish, String toggle,
                          int durability, int processingTime, float damageChance, double lootMultiplier, int vis, Rarity rarity) {
        this.id = id;
        this.itemId = itemId;
        this.plate = plate;
        this.english = english;
        this.spanish = spanish;
        this.toggle = toggle;
        this.durability = durability;
        this.processingTime = processingTime;
        this.damageChance = damageChance;
        this.lootMultiplier = lootMultiplier;
        this.vis = vis;
        this.rarity = rarity;
    }

    public String id() {
        return id;
    }

    public String itemId() {
        return itemId;
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

    public String toggle() {
        return toggle;
    }

    public int defaultDurability() {
        return durability;
    }

    public int processingTime() {
        return processingTime;
    }

    public float damageChance() {
        return damageChance;
    }

    public double lootMultiplier() {
        return lootMultiplier;
    }

    public int vis() {
        return vis;
    }

    public Rarity rarity() {
        return rarity;
    }

    public FtbUnearthedBrushTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }
}
