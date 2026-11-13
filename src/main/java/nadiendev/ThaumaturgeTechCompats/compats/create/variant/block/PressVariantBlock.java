package nadiendev.ThaumaturgeTechCompats.compats.create.variant.block;

import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CasingVariant;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.VariantKind;

public class PressVariantBlock extends MechanicalPressBlock implements CasingVariant {
    private final CasingTier tier;

    public PressVariantBlock(Properties properties, CasingTier tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public CasingTier tier() {
        return tier;
    }

    @Override
    public VariantKind kind() {
        return VariantKind.PRESS;
    }
}
