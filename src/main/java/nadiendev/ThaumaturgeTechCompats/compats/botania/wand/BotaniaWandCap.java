package nadiendev.ThaumaturgeTechCompats.compats.botania.wand;

import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TTAspects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Rarity;

import java.util.List;

public enum BotaniaWandCap {
    MANASTEEL("manasteel", "Manasteel", "de Manacero", Rarity.UNCOMMON, 0.9F, 0.9F, 6, List.of()),
    ELEMENTIUM("elementium", "Elementium", "de Elementio", Rarity.RARE, 1.0F, 0.8F, 8, List.of(TTAspects.AER, TTAspects.AQUA, TTAspects.TERRA, TTAspects.IGNIS)),
    TERRASTEEL("terrasteel", "Terrasteel", "de Terracero", Rarity.RARE, 0.8F, 0.8F, 10, List.of());

    private final String id;
    private final String english;
    private final String spanish;
    private final Rarity rarity;
    private final float baseCostModifier;
    private final float specialCostModifier;
    private final int craftCost;
    private final List<ResourceKey<IAspect>> specialAspects;

    BotaniaWandCap(String id, String english, String spanish, Rarity rarity, float baseCostModifier, float specialCostModifier, int craftCost,
                   List<ResourceKey<IAspect>> specialAspects) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.rarity = rarity;
        this.baseCostModifier = baseCostModifier;
        this.specialCostModifier = specialCostModifier;
        this.craftCost = craftCost;
        this.specialAspects = specialAspects;
    }

    public String id() {
        return id;
    }

    public String itemName() {
        return id + "_wand_cap";
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

    public float defaultBaseCostModifier() {
        return baseCostModifier;
    }

    public float defaultSpecialCostModifier() {
        return specialCostModifier;
    }

    public int defaultCraftCost() {
        return craftCost;
    }

    public List<ResourceKey<IAspect>> specialAspects() {
        return specialAspects;
    }
}
