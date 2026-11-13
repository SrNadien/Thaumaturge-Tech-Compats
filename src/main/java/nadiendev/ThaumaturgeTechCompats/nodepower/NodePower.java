package nadiendev.ThaumaturgeTechCompats.nodepower;

import com.leclowndu93150.thaumaturge.api.nodes.NodeType;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityJarNode;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import com.leclowndu93150.thaumaturge.content.entity.EntityFluxRift;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public final class NodePower {
    private NodePower() {
    }

    public static Optional<NodeSource> find(Level level, BlockPos origin, double radius) {
        return find(level, origin, radius, EnumSet.allOf(NodeSourceKind.class));
    }

    public static Optional<NodeSource> find(Level level, BlockPos origin, double radius, Set<NodeSourceKind> allowed) {
        if (!(level instanceof ServerLevel server) || !CompatToggles.enabled(CompatToggles.NODE_POWER)) {
            return Optional.empty();
        }
        for (NodeSourceKind kind : NodePowerConfig.order()) {
            if (!allowed.contains(kind)) {
                continue;
            }
            Optional<NodeSource> found = switch (kind) {
                case JAR_NODE -> nearestNode(server, origin, radius, NodeSourceKind.JAR_NODE);
                case WORLD_NODE -> nearestNode(server, origin, radius, NodeSourceKind.WORLD_NODE);
                case FLUX_JAR -> nearestFluxJar(server, origin, radius);
                case RIFT_JAR -> nearestRiftJar(server, origin, radius);
                case RIFT -> nearestRift(server, origin, radius);
            };
            if (found.isPresent()) {
                return found;
            }
        }
        return Optional.empty();
    }

    public static boolean isHungry(BlockEntityNode node) {
        return node.getNodeType() == NodeType.HUNGRY;
    }

    private static Optional<NodeSource> nearestNode(ServerLevel level, BlockPos origin, double radius, NodeSourceKind kind) {
        boolean jarred = kind == NodeSourceKind.JAR_NODE;
        return nearest(level, origin, radius, BlockEntityNode.class,
                node -> (node instanceof BlockEntityJarNode) == jarred && !isHungry(node) && !node.isRemoved())
                .map(node -> new AspectNodeSource(node, kind));
    }

    private static Optional<NodeSource> nearestFluxJar(ServerLevel level, BlockPos origin, double radius) {
        return nearest(level, origin, radius, FluxJarBlockEntity.class, jar -> !jar.isRemoved() && jar.flux() >= 1.0F)
                .map(FluxJarSource::new);
    }

    private static Optional<NodeSource> nearestRiftJar(ServerLevel level, BlockPos origin, double radius) {
        return nearest(level, origin, radius, RiftJarBlockEntity.class, jar -> !jar.isRemoved() && jar.power() >= 1.0F)
                .map(RiftJarSource::new);
    }

    private static Optional<NodeSource> nearestRift(ServerLevel level, BlockPos origin, double radius) {
        AABB area = new AABB(origin).inflate(radius);
        double limit = radius * radius;
        EntityFluxRift best = null;
        double bestDistance = limit;
        for (EntityFluxRift rift : level.getEntitiesOfClass(EntityFluxRift.class, area)) {
            if (rift.isRemoved() || rift.isCollapsing() || rift.currentSize() <= NodePowerConfig.riftMinSize()) {
                continue;
            }
            double distance = distanceSquared(rift, origin);
            if (distance <= bestDistance) {
                best = rift;
                bestDistance = distance;
            }
        }
        return Optional.ofNullable(best).map(RiftSource::new);
    }

    private static double distanceSquared(Entity entity, BlockPos origin) {
        return entity.distanceToSqr(origin.getX() + 0.5D, origin.getY() + 0.5D, origin.getZ() + 0.5D);
    }

    private static <T extends BlockEntity> Optional<T> nearest(ServerLevel level, BlockPos origin, double radius, Class<T> type, Predicate<T> filter) {
        int reach = (int) Math.ceil(radius);
        int minX = (origin.getX() - reach) >> 4;
        int maxX = (origin.getX() + reach) >> 4;
        int minZ = (origin.getZ() - reach) >> 4;
        int maxZ = (origin.getZ() + reach) >> 4;
        double bestDistance = radius * radius;
        T best = null;
        for (int chunkX = minX; chunkX <= maxX; chunkX++) {
            for (int chunkZ = minZ; chunkZ <= maxZ; chunkZ++) {
                LevelChunk chunk = level.getChunkSource().getChunkNow(chunkX, chunkZ);
                if (chunk == null) {
                    continue;
                }
                for (BlockEntity entity : chunk.getBlockEntities().values()) {
                    if (!type.isInstance(entity)) {
                        continue;
                    }
                    double distance = entity.getBlockPos().distSqr(origin);
                    T candidate = type.cast(entity);
                    if (distance <= bestDistance && filter.test(candidate)) {
                        best = candidate;
                        bestDistance = distance;
                    }
                }
            }
        }
        return Optional.ofNullable(best);
    }
}
