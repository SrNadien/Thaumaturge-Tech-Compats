package nadiendev.ThaumaturgeTechCompats.compats.create.variant;

import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;

public enum VariantKind {
    PRESS("mechanical_press", CreateToggles.VARIANT_PRESS),
    MIXER("mechanical_mixer", CreateToggles.VARIANT_MIXER),
    SAW("mechanical_saw", CreateToggles.VARIANT_SAW),
    DRILL("mechanical_drill", CreateToggles.VARIANT_DRILL),
    DEPLOYER("deployer", CreateToggles.VARIANT_DEPLOYER),
    MILLSTONE("millstone", CreateToggles.VARIANT_MILLSTONE),
    BASIN("basin", CreateToggles.VARIANT_BASIN),
    SPOUT("spout", CreateToggles.VARIANT_SPOUT),
    ITEM_DRAIN("item_drain", CreateToggles.VARIANT_ITEM_DRAIN),
    DEPOT("depot", CreateToggles.VARIANT_DEPOT);

    private final String id;
    private final String toggle;

    VariantKind(String id, String toggle) {
        this.id = id;
        this.toggle = toggle;
    }

    public String id() {
        return id;
    }

    public String toggle() {
        return toggle;
    }

    public String blockName(CasingTier tier) {
        return tier.material() + "_" + id;
    }
}
