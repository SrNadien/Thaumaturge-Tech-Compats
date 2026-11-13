package nadiendev.ThaumaturgeTechCompats.nodepower;

import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.Aspects;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TTAspects;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class AspectNodeSource implements NodeSource {
    private final BlockEntityNode node;
    private final NodeSourceKind kind;

    public AspectNodeSource(BlockEntityNode node, NodeSourceKind kind) {
        this.node = node;
        this.kind = kind;
    }

    @Override
    public NodeSourceKind kind() {
        return kind;
    }

    @Override
    public BlockPos pos() {
        return node.getBlockPos();
    }

    @Override
    public int available() {
        Level level = node.getLevel();
        if (level == null || node.isRemoved()) {
            return 0;
        }
        AspectList aspects = node.getAspects();
        int total = 0;
        for (ResourceKey<IAspect> key : TTAspects.PRIMALS) {
            total += aspects.amountOf(Aspects.resolve(level, key));
        }
        return total;
    }

    @Override
    public int drain(int amount, boolean simulate) {
        Level level = node.getLevel();
        if (level == null || node.isRemoved() || amount <= 0) {
            return 0;
        }
        int granted = Math.min(amount, available());
        float ratio = NodePowerConfig.nodeConsumeRatio();
        if (granted <= 0 || simulate || ratio <= 0.0F) {
            return granted;
        }
        int cost = Math.max(1, Math.round(granted * ratio));
        int remaining = cost;
        for (ResourceKey<IAspect> key : TTAspects.PRIMALS) {
            if (remaining <= 0) {
                break;
            }
            Holder<IAspect> aspect = Aspects.resolve(level, key);
            int take = Math.min(remaining, node.getAspects().amountOf(aspect));
            if (take <= 0) {
                continue;
            }
            if (node.drain(aspect, take)) {
                remaining -= take;
            }
        }
        return granted;
    }
}
