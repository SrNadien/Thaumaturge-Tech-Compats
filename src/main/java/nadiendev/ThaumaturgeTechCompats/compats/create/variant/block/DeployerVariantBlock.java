package nadiendev.ThaumaturgeTechCompats.compats.create.variant.block;

import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CasingVariant;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.VariantKind;

public class DeployerVariantBlock extends DeployerBlock implements CasingVariant {
    private final CasingTier tier;

    public DeployerVariantBlock(Properties properties, CasingTier tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public CasingTier tier() {
        return tier;
    }

    @Override
    public VariantKind kind() {
        return VariantKind.DEPLOYER;
    }
}
