package nadiendev.ThaumaturgeTechCompats.flux.link;

import nadiendev.ThaumaturgeTechCompats.flux.FluxCapabilities;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;

public final class FluxTransfer {
    private static final Direction[] DIRECTIONS = Direction.values();

    private FluxTransfer() {
    }

    public static long scale(long value, int factor) {
        if (factor <= 1) {
            return value;
        }
        return value > Long.MAX_VALUE / 4 / factor ? Long.MAX_VALUE / 4 : value * factor;
    }

    public static long move(IFluxStorage from, IFluxStorage to, long max) {
        long amount = Math.min(max, from.stored());
        if (amount <= 0L) {
            return 0L;
        }
        long fits = to.insert(amount, true);
        if (fits <= 0L) {
            return 0L;
        }
        long taken = from.extract(fits, false);
        if (taken <= 0L) {
            return 0L;
        }
        long accepted = to.insert(taken, false);
        if (accepted < taken) {
            from.insert(taken - accepted, false);
        }
        return accepted;
    }

    public static long balance(IFluxStorage a, IFluxStorage b, long max) {
        double capacityA = a.capacity();
        double capacityB = b.capacity();
        if (capacityA <= 0.0D || capacityB <= 0.0D) {
            return 0L;
        }
        double difference = (a.stored() * capacityB - b.stored() * capacityA) / (capacityA + capacityB);
        if (difference >= 1.0D) {
            return move(a, b, Math.min(max, (long) difference));
        }
        if (difference <= -1.0D) {
            return move(b, a, Math.min(max, (long) -difference));
        }
        return 0L;
    }

    public static long exchange(ServerLevel level, BlockPos pos, IFluxStorage own, long budget, boolean allowPull, boolean skipLowerLinks) {
        long left = budget;
        int offset = (int) (level.getGameTime() % DIRECTIONS.length);
        for (int i = 0; i < DIRECTIONS.length && left > 0L; i++) {
            Direction direction = DIRECTIONS[(i + offset) % DIRECTIONS.length];
            BlockPos neighborPos = pos.relative(direction);
            if (!level.hasChunkAt(neighborPos)) {
                continue;
            }
            if (skipLowerLinks && neighborPos.asLong() < pos.asLong() && level.getBlockEntity(neighborPos) instanceof TransmissionLinkBlockEntity) {
                continue;
            }
            IFluxStorage other = level.getCapability(FluxCapabilities.STORAGE, neighborPos, direction.getOpposite());
            if (other == null || other == own) {
                continue;
            }
            left -= exchangeWith(own, other, left, allowPull);
        }
        return budget - left;
    }

    private static long exchangeWith(IFluxStorage own, IFluxStorage other, long limit, boolean allowPull) {
        boolean canInsert = other.canInsert();
        boolean canExtract = other.canExtract();
        if (!allowPull) {
            return canInsert ? move(own, other, limit) : 0L;
        }
        if (canInsert && !canExtract) {
            return move(own, other, limit);
        }
        if (canExtract && !canInsert) {
            return move(other, own, limit);
        }
        if (canInsert) {
            return balance(own, other, limit);
        }
        return 0L;
    }
}
