package nadiendev.ThaumaturgeTechCompats.nodepower;

import net.minecraft.core.BlockPos;

public interface NodeSource {
    NodeSourceKind kind();

    BlockPos pos();

    int available();

    int drain(int amount, boolean simulate);
}
