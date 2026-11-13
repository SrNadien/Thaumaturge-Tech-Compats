package nadiendev.ThaumaturgeTechCompats.compats.enderio.wand;

import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TTAspects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Rarity;

import java.util.List;

public enum EnderIOWandMaterial {
    REDSTONE_ALLOY("redstone_alloy", "c:ingots/redstone_alloy", true, "Redstone Alloy", "de aleación de redstone", Rarity.UNCOMMON,
            40, 4, false, 1.0F, 0.9F, 3, List.of(TTAspects.POTENTIA)),
    CONDUCTIVE_ALLOY("conductive_alloy", "c:ingots/conductive_alloy", true, "Conductive Alloy", "de aleación conductora", Rarity.UNCOMMON,
            55, 5, false, 0.98F, 0.88F, 4, List.of(TTAspects.POTENTIA, TTAspects.MOTUS)),
    PULSATING_ALLOY("pulsating_alloy", "c:ingots/pulsating_alloy", true, "Pulsating Alloy", "de aleación pulsante", Rarity.UNCOMMON,
            70, 6, false, 0.96F, 0.86F, 5, List.of(TTAspects.MOTUS, TTAspects.ALIENIS)),
    ENERGETIC_ALLOY("energetic_alloy", "c:ingots/energetic_alloy", true, "Energetic Alloy", "de aleación energética", Rarity.UNCOMMON,
            85, 7, false, 0.94F, 0.84F, 6, List.of(TTAspects.POTENTIA, TTAspects.LUX)),
    DARK_STEEL("dark_steel", "c:ingots/dark_steel", true, "Dark Steel", "de acero oscuro", Rarity.RARE,
            100, 8, false, 0.92F, 0.82F, 7, List.of(TTAspects.TENEBRAE, TTAspects.PRAEMUNIO)),
    SOULARIUM("soularium", "c:ingots/soularium", true, "Soularium", "de soularium", Rarity.RARE,
            115, 9, false, 0.9F, 0.8F, 8, List.of(TTAspects.SPIRITUS, TTAspects.MORTUUS)),
    PULSATING_CRYSTAL("pulsating_crystal", "enderio:pulsating_crystal", false, "Pulsating Crystal", "de cristal pulsante", Rarity.RARE,
            130, 10, true, 0.88F, 0.78F, 9, List.of(TTAspects.MOTUS, TTAspects.VITREUS)),
    VIBRANT_ALLOY("vibrant_alloy", "c:ingots/vibrant_alloy", true, "Vibrant Alloy", "de aleación vibrante", Rarity.RARE,
            145, 11, true, 0.86F, 0.76F, 10, List.of(TTAspects.LUX, TTAspects.POTENTIA, TTAspects.MOTUS)),
    VIBRANT_CRYSTAL("vibrant_crystal", "enderio:vibrant_crystal", false, "Vibrant Crystal", "de cristal vibrante", Rarity.EPIC,
            160, 12, true, 0.84F, 0.74F, 11, List.of(TTAspects.LUX, TTAspects.VITREUS)),
    END_STEEL("end_steel", "c:ingots/end_steel", true, "End Steel", "de acero del End", Rarity.EPIC,
            175, 13, true, 0.8F, 0.7F, 12, List.of(TTAspects.ALIENIS, TTAspects.TENEBRAE, TTAspects.PRAEMUNIO));

    public static final int ARCANE_COUNT = 3;

    private final String id;
    private final String ingredient;
    private final boolean tag;
    private final String english;
    private final String spanish;
    private final Rarity rarity;
    private final int capacity;
    private final int rodCraftCost;
    private final boolean glow;
    private final float baseCostModifier;
    private final float specialCostModifier;
    private final int capCraftCost;
    private final List<ResourceKey<IAspect>> specialAspects;

    EnderIOWandMaterial(String id, String ingredient, boolean tag, String english, String spanish, Rarity rarity,
                        int capacity, int rodCraftCost, boolean glow,
                        float baseCostModifier, float specialCostModifier, int capCraftCost,
                        List<ResourceKey<IAspect>> specialAspects) {
        this.id = id;
        this.ingredient = ingredient;
        this.tag = tag;
        this.english = english;
        this.spanish = spanish;
        this.rarity = rarity;
        this.capacity = capacity;
        this.rodCraftCost = rodCraftCost;
        this.glow = glow;
        this.baseCostModifier = baseCostModifier;
        this.specialCostModifier = specialCostModifier;
        this.capCraftCost = capCraftCost;
        this.specialAspects = specialAspects;
    }

    public String id() {
        return id;
    }

    public String rodName() {
        return id + "_wand_rod";
    }

    public String capName() {
        return id + "_wand_cap";
    }

    public String ingredient() {
        return ingredient;
    }

    public boolean tag() {
        return tag;
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

    public int defaultRodCraftCost() {
        return rodCraftCost;
    }

    public boolean glow() {
        return glow;
    }

    public float defaultBaseCostModifier() {
        return baseCostModifier;
    }

    public float defaultSpecialCostModifier() {
        return specialCostModifier;
    }

    public int defaultCapCraftCost() {
        return capCraftCost;
    }

    public List<ResourceKey<IAspect>> specialAspects() {
        return specialAspects;
    }

    public boolean arcane() {
        return ordinal() < ARCANE_COUNT;
    }

    public EnderIOWandMaterial previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }
}
