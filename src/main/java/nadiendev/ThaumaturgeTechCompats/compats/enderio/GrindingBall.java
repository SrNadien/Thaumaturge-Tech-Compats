package nadiendev.ThaumaturgeTechCompats.compats.enderio;

public enum GrindingBall {
    BRASS("brass", "c:ingots/brass", 1.15F, 1.3F, 0.85F, 60000),
    THAUMIUM("thaumium", "c:ingots/thaumium", 1.45F, 1.8F, 0.8F, 110000),
    VOID_METAL("void_metal", "c:ingots/void_metal", 1.5F, 2.3F, 0.65F, 140000);

    public static final int RECIPE_YIELD = 24;

    private final String id;
    private final String ingotTag;
    private final float outputMultiplier;
    private final float bonusMultiplier;
    private final float powerUse;
    private final int durability;

    GrindingBall(String id, String ingotTag, float outputMultiplier, float bonusMultiplier, float powerUse, int durability) {
        this.id = id;
        this.ingotTag = ingotTag;
        this.outputMultiplier = outputMultiplier;
        this.bonusMultiplier = bonusMultiplier;
        this.powerUse = powerUse;
        this.durability = durability;
    }

    public String id() {
        return id;
    }

    public String itemName() {
        return id + "_grinding_ball";
    }

    public String ingotTag() {
        return ingotTag;
    }

    public float outputMultiplier() {
        return outputMultiplier;
    }

    public float bonusMultiplier() {
        return bonusMultiplier;
    }

    public float powerUse() {
        return powerUse;
    }

    public int durability() {
        return durability;
    }
}
