package nadiendev.ThaumaturgeTechCompats.nodepower;

import com.leclowndu93150.thaumaturge.content.entity.EntityFluxRift;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;

public final class RiftSource implements NodeSource {
    private final EntityFluxRift rift;

    public RiftSource(EntityFluxRift rift) {
        this.rift = rift;
    }

    @Override
    public NodeSourceKind kind() {
        return NodeSourceKind.RIFT;
    }

    @Override
    public BlockPos pos() {
        return rift.blockPosition();
    }

    @Override
    public int available() {
        if (rift.isRemoved() || rift.isCollapsing()) {
            return 0;
        }
        return Math.max(0, rift.currentSize() - NodePowerConfig.riftMinSize());
    }

    @Override
    public int drain(int amount, boolean simulate) {
        int granted = Math.min(Math.max(amount, 0), available());
        if (granted <= 0 || simulate) {
            return granted;
        }
        RandomSource random = rift.level().getRandom();
        float chance = NodePowerConfig.riftShrinkChance();
        for (int i = 0; i < granted; i++) {
            if (rift.currentSize() > NodePowerConfig.riftMinSize() && random.nextFloat() < chance) {
                rift.resize(rift.currentSize() - 1);
            }
        }
        return granted;
    }
}
