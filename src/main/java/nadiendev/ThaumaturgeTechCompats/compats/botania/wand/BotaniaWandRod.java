package nadiendev.ThaumaturgeTechCompats.compats.botania.wand;

import net.minecraft.world.item.Rarity;

public enum BotaniaWandRod {
    LIVINGWOOD("livingwood", "Livingwood", "de Madera Viviente", Rarity.UNCOMMON, 60, 6, false),
    DREAMWOOD("dreamwood", "Dreamwood", "de Madera Onírica", Rarity.RARE, 120, 9, true);

    private final String id;
    private final String english;
    private final String spanish;
    private final Rarity rarity;
    private final int capacity;
    private final int craftCost;
    private final boolean glow;

    BotaniaWandRod(String id, String english, String spanish, Rarity rarity, int capacity, int craftCost, boolean glow) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.rarity = rarity;
        this.capacity = capacity;
        this.craftCost = craftCost;
        this.glow = glow;
    }

    public String id() {
        return id;
    }

    public String itemName() {
        return id + "_wand_rod";
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

    public int defaultCapacity() {
        return capacity;
    }

    public int defaultCraftCost() {
        return craftCost;
    }

    public boolean glow() {
        return glow;
    }
}
