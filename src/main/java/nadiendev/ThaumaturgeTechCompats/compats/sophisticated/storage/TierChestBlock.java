package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage;

import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedTier;
import net.p3pp3rf1y.sophisticatedstorage.block.ChestBlock;

import java.util.function.Supplier;

public class TierChestBlock extends ChestBlock {
    private final SophisticatedTier tier;

    public TierChestBlock(SophisticatedTier tier, Supplier<Integer> slots, Supplier<Integer> upgradeSlots, float explosionResistance) {
        super(slots, upgradeSlots, explosionResistance);
        this.tier = tier;
    }

    public SophisticatedTier tier() {
        return tier;
    }
}
