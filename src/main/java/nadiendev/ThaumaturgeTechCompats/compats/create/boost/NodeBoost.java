package nadiendev.ThaumaturgeTechCompats.compats.create.boost;

import com.leclowndu93150.thaumaturge.content.aura.node.NodeLocationIndex;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateConfig;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CasingVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Map;
import java.util.WeakHashMap;

public final class NodeBoost {
    private static final Map<BlockEntity, Probe> PROBES = new WeakHashMap<>();
    private static boolean running;

    private NodeBoost() {
    }

    public static int extraTicks(BlockEntity entity) {
        if (running || !(entity.getLevel() instanceof ServerLevel level) || entity.isRemoved()) {
            return 0;
        }
        if (!(entity.getBlockState().getBlock() instanceof CasingVariant variant)) {
            return 0;
        }
        CasingTier tier = variant.tier();
        if (!tier.enabled() || !CreateToggles.enabled(variant.kind().toggle()) || level.tickRateManager().isFrozen()) {
            return 0;
        }
        Probe probe = PROBES.computeIfAbsent(entity, key -> new Probe());
        long now = level.getGameTime();
        if (now >= probe.next) {
            probe.next = now + CreateConfig.probeInterval();
            probe.near = nodeNear(level, entity.getBlockPos(), tier);
            if (probe.near && CreateConfig.particles()) {
                showParticles(level, entity.getBlockPos());
            }
        }
        return probe.near ? Math.max(0, tier.multiplier() - 1) : 0;
    }

    public static boolean enter() {
        if (running) {
            return false;
        }
        running = true;
        return true;
    }

    public static void exit() {
        running = false;
    }

    public static boolean nodeNear(Level level, BlockPos pos, CasingTier tier) {
        return level instanceof ServerLevel server
                && NodeLocationIndex.get(server).findNearestAny(pos, tier.radius()).isPresent();
    }

    private static void showParticles(ServerLevel level, BlockPos pos) {
        level.sendParticles(ParticleTypes.ENCHANT, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 6, 0.35D, 0.35D, 0.35D, 0.4D);
    }

    private static final class Probe {
        private long next;
        private boolean near;
    }
}
