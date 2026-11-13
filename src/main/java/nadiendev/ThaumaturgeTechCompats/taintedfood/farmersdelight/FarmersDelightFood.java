package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.taintedfood.TaintedFoodGender;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public enum FarmersDelightFood {
    BEEF_STEW("beef_stew", 14, TaintedFoodGender.MASCULINE, true),
    CHICKEN_SOUP("chicken_soup", 14, TaintedFoodGender.FEMININE, true),
    VEGETABLE_SOUP("vegetable_soup", 12, TaintedFoodGender.FEMININE, true),
    FISH_STEW("fish_stew", 14, TaintedFoodGender.MASCULINE, true),
    BAKED_COD_STEW("baked_cod_stew", 16, TaintedFoodGender.MASCULINE, true),
    NOODLE_SOUP("noodle_soup", 14, TaintedFoodGender.FEMININE, true),
    FRIED_RICE("fried_rice", 14, TaintedFoodGender.MASCULINE, true),
    RATATOUILLE("ratatouille", 14, TaintedFoodGender.MASCULINE, true),
    STEAK_AND_POTATOES("steak_and_potatoes", 18, TaintedFoodGender.MASCULINE, true),
    HAMBURGER("hamburger", 14, TaintedFoodGender.FEMININE, false),
    BACON_SANDWICH("bacon_sandwich", 14, TaintedFoodGender.MASCULINE, false),
    COOKED_BACON("cooked_bacon", 10, TaintedFoodGender.MASCULINE, false),
    BEEF_PATTY("beef_patty", 10, TaintedFoodGender.FEMININE, false),
    DUMPLINGS("dumplings", 12, TaintedFoodGender.FEMININE_PLURAL, false);

    private final ResourceLocation original;
    private final int vitium;
    private final TaintedFoodGender gender;
    private final boolean bowl;

    FarmersDelightFood(String path, int vitium, TaintedFoodGender gender, boolean bowl) {
        this.original = ResourceLocation.fromNamespaceAndPath(FarmersDelightTaintedIntegration.MOD_ID, path);
        this.vitium = vitium;
        this.gender = gender;
        this.bowl = bowl;
    }

    public static List<String> ids() {
        List<String> ids = new ArrayList<>();
        for (FarmersDelightFood food : values()) {
            ids.add(food.id());
        }
        return ids;
    }

    public ResourceLocation original() {
        return original;
    }

    public int vitium() {
        return vitium;
    }

    public TaintedFoodGender gender() {
        return gender;
    }

    public boolean bowl() {
        return bowl;
    }

    public String id() {
        return "tainted_" + original.getPath();
    }

    public ResourceLocation itemId() {
        return ResourceLocation.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, id());
    }
}
