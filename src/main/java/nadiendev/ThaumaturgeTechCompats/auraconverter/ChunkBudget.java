package nadiendev.ThaumaturgeTechCompats.auraconverter;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.WeakHashMap;

public final class ChunkBudget {
    private static final int PURGE_SIZE = 512;
    private static final long STALE_TICKS = 200L;
    private static final Map<Level, Long2ObjectOpenHashMap<Entry>> BUDGETS = new WeakHashMap<>();

    private ChunkBudget() {
    }

    public static synchronized float reserve(Level level, BlockPos pos, float requested) {
        long now = level.getGameTime();
        Long2ObjectOpenHashMap<Entry> chunks = BUDGETS.computeIfAbsent(level, key -> new Long2ObjectOpenHashMap<>());
        if (chunks.size() > PURGE_SIZE) {
            chunks.values().removeIf(entry -> entry.tick < now - STALE_TICKS);
        }
        Entry entry = chunks.computeIfAbsent(ChunkPos.pack(pos), key -> new Entry());
        if (entry.tick != now) {
            entry.tick = now;
            entry.used = 0.0F;
        }
        float granted = Math.max(0.0F, Math.min(requested, AuraConverterConfig.maxVisPerChunkPerTick() - entry.used));
        entry.used += granted;
        return granted;
    }

    public static synchronized void release(Level level, BlockPos pos, float unused) {
        if (unused <= 0.0F) {
            return;
        }
        Long2ObjectOpenHashMap<Entry> chunks = BUDGETS.get(level);
        if (chunks == null) {
            return;
        }
        Entry entry = chunks.get(ChunkPos.pack(pos));
        if (entry != null && entry.tick == level.getGameTime()) {
            entry.used = Math.max(0.0F, entry.used - unused);
        }
    }

    private static final class Entry {
        private long tick = Long.MIN_VALUE;
        private float used;
    }
}
