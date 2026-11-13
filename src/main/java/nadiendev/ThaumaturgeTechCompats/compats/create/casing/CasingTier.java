package nadiendev.ThaumaturgeTechCompats.compats.create.casing;

import nadiendev.ThaumaturgeTechCompats.compats.create.CreateConfig;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;

public enum CasingTier {
    BRASS("alchemical_brass", "alchemical_brass_casing", CreateToggles.CASING_BRASS),
    THAUMIUM("thaumium", "thaumium_casing", CreateToggles.CASING_THAUMIUM),
    VOID("void_metal", "void_metal_casing", CreateToggles.CASING_VOID);

    private final String material;
    private final String blockName;
    private final String toggle;

    CasingTier(String material, String blockName, String toggle) {
        this.material = material;
        this.blockName = blockName;
        this.toggle = toggle;
    }

    public String material() {
        return material;
    }

    public String blockName() {
        return blockName;
    }

    public String toggle() {
        return toggle;
    }

    public int level() {
        return ordinal() + 1;
    }

    public CasingTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }

    public boolean enabled() {
        return CreateToggles.enabled(toggle) && CreateToggles.enabled(CreateToggles.NODE_BOOST);
    }

    public int multiplier() {
        return CreateConfig.multiplier(this);
    }

    public double defaultRadius() {
        return switch (this) {
            case BRASS -> 6.0D;
            case THAUMIUM -> 10.0D;
            case VOID -> 16.0D;
        };
    }

    public double radius() {
        return CreateConfig.radius(this);
    }

    public static CasingTier byLevel(int level) {
        for (CasingTier tier : values()) {
            if (tier.level() == level) {
                return tier;
            }
        }
        return null;
    }
}
