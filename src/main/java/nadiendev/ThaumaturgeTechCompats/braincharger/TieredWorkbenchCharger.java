package nadiendev.ThaumaturgeTechCompats.braincharger;

import com.leclowndu93150.thaumaturge.content.workbench.BlockArcaneWorkbenchCharger;

public class TieredWorkbenchCharger extends BlockArcaneWorkbenchCharger {
    private final BoostTier tier;

    public TieredWorkbenchCharger(Properties properties, BoostTier tier) {
        super(properties);
        this.tier = tier;
    }

    public BoostTier tier() {
        return tier;
    }
}
