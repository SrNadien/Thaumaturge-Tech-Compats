package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage;

import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedTier;
import net.p3pp3rf1y.sophisticatedstorage.block.ShulkerBoxBlock;

import java.util.function.Supplier;

public class TierShulkerBoxBlock extends ShulkerBoxBlock {
    private final SophisticatedTier tier;

    public TierShulkerBoxBlock(SophisticatedTier tier, Supplier<Integer> slots, Supplier<Integer> upgradeSlots, float explosionResistance) {
        super(slots, upgradeSlots, explosionResistance);
        this.tier = tier;
    }

    public SophisticatedTier tier() {
        return tier;
    }
}
