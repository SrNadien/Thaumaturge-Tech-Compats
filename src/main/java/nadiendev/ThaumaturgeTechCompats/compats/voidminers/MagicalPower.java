package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePower;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSource;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Optional;
import java.util.Set;

public final class MagicalPower {
    public enum Failure {
        NONE("none"),
        NO_SOURCE("no_source"),
        HUNGRY("hungry"),
        EXHAUSTED("exhausted");

        private final String id;

        Failure(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }

        public static Failure byId(String id) {
            for (Failure failure : values()) {
                if (failure.id.equals(id)) {
                    return failure;
                }
            }
            return NONE;
        }
    }

    private long paidUntil = Long.MIN_VALUE;
    private long nextAttempt = Long.MIN_VALUE;
    private NodeSourceKind source;
    private Failure failure = Failure.NO_SOURCE;

    public boolean active(Level level, BlockPos origin, double radius, Set<NodeSourceKind> kinds, int units, int interval) {
        long time = level.getGameTime();
        if (time < paidUntil) {
            return true;
        }
        source = null;
        if (time < nextAttempt) {
            return false;
        }
        nextAttempt = time + interval;
        Optional<NodeSource> found = NodePower.find(level, origin, radius, kinds);
        if (found.isEmpty()) {
            failure = hungryNearby(level, origin, radius) ? Failure.HUNGRY : Failure.NO_SOURCE;
            return false;
        }
        NodeSource candidate = found.get();
        if (candidate.drain(units, true) >= units && candidate.drain(units, false) >= units) {
            source = candidate.kind();
            paidUntil = time + interval;
            failure = Failure.NONE;
            return true;
        }
        failure = Failure.EXHAUSTED;
        return false;
    }

    public boolean paid(Level level) {
        return level != null && level.getGameTime() < paidUntil;
    }

    public NodeSourceKind source() {
        return source;
    }

    public Failure failure() {
        return failure;
    }

    private static boolean hungryNearby(Level level, BlockPos origin, double radius) {
        if (!(level instanceof ServerLevel server)) {
            return false;
        }
        int reach = (int) Math.ceil(radius);
        double limit = radius * radius;
        for (int chunkX = (origin.getX() - reach) >> 4; chunkX <= (origin.getX() + reach) >> 4; chunkX++) {
            for (int chunkZ = (origin.getZ() - reach) >> 4; chunkZ <= (origin.getZ() + reach) >> 4; chunkZ++) {
                LevelChunk chunk = server.getChunkSource().getChunkNow(chunkX, chunkZ);
                if (chunk == null) {
                    continue;
                }
                for (BlockEntity entity : chunk.getBlockEntities().values()) {
                    if (entity instanceof BlockEntityNode node && !node.isRemoved() && NodePower.isHungry(node)
                            && node.getBlockPos().distSqr(origin) <= limit) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
