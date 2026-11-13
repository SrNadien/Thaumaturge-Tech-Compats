package nadiendev.ThaumaturgeTechCompats.nodepower;

import net.minecraft.core.BlockPos;

public final class FluxJarSource implements NodeSource {
    private final FluxJarBlockEntity jar;

    public FluxJarSource(FluxJarBlockEntity jar) {
        this.jar = jar;
    }

    @Override
    public NodeSourceKind kind() {
        return NodeSourceKind.FLUX_JAR;
    }

    @Override
    public BlockPos pos() {
        return jar.getBlockPos();
    }

    @Override
    public int available() {
        return jar.isRemoved() ? 0 : (int) Math.floor(jar.flux());
    }

    @Override
    public int drain(int amount, boolean simulate) {
        int take = Math.min(Math.max(amount, 0), available());
        if (take > 0 && !simulate) {
            jar.consume(take);
        }
        return take;
    }
}
