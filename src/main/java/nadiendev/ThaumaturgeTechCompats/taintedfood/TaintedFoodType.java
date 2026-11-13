package nadiendev.ThaumaturgeTechCompats.taintedfood;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public enum TaintedFoodType {
    BREAD("minecraft:bread", 6, TaintedFoodGender.MASCULINE),
    APPLE("minecraft:apple", 6, TaintedFoodGender.FEMININE),
    CARROT("minecraft:carrot", 6, TaintedFoodGender.FEMININE),
    BAKED_POTATO("minecraft:baked_potato", 8, TaintedFoodGender.FEMININE),
    COOKED_BEEF("minecraft:cooked_beef", 12, TaintedFoodGender.MASCULINE),
    COOKED_PORKCHOP("minecraft:cooked_porkchop", 12, TaintedFoodGender.FEMININE),
    PUMPKIN_PIE("minecraft:pumpkin_pie", 16, TaintedFoodGender.MASCULINE),
    MUSHROOM_STEW("minecraft:mushroom_stew", 12, TaintedFoodGender.MASCULINE),
    ULTIMATE_STEW("avaritia:ultimate_stew", 64, TaintedFoodGender.MASCULINE),
    COSMIC_MEATBALLS("avaritia:cosmic_meatballs", 64, TaintedFoodGender.FEMININE_PLURAL);

    private final ResourceLocation original;
    private final int vitium;
    private final TaintedFoodGender gender;

    TaintedFoodType(String original, int vitium, TaintedFoodGender gender) {
        this.original = ResourceLocation.parse(original);
        this.vitium = vitium;
        this.gender = gender;
    }

    public static List<String> ids() {
        List<String> ids = new ArrayList<>();
        for (TaintedFoodType type : values()) {
            ids.add(type.id());
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

    public String id() {
        return "tainted_" + original.getPath();
    }

    public ResourceLocation itemId() {
        return ResourceLocation.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, id());
    }
}
