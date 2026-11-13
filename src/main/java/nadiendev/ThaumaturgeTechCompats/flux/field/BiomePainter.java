package nadiendev.ThaumaturgeTechCompats.flux.field;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BiomePainter {
    private BiomePainter() {
    }

    public static long pack(int quartX, int quartZ) {
        return ((long) quartX << 32) | (quartZ & 0xFFFFFFFFL);
    }

    public static int quartX(long key) {
        return (int) (key >> 32);
    }

    public static int quartZ(long key) {
        return (int) key;
    }

    public static void paint(ServerLevel level, Holder<Biome> biome, Map<ChunkPos, Set<Long>> columns) {
        List<ChunkAccess> changed = new ArrayList<>();
        for (Map.Entry<ChunkPos, Set<Long>> entry : columns.entrySet()) {
            LevelChunk chunk = level.getChunkSource().getChunkNow(entry.getKey().x, entry.getKey().z);
            if (chunk == null) {
                continue;
            }
            Set<Long> cells = entry.getValue();
            BiomeResolver resolver = (x, y, z, sampler) -> cells.contains(pack(x, z)) ? biome : chunk.getNoiseBiome(x, y, z);
            chunk.fillBiomesFromNoise(resolver, level.getChunkSource().randomState().sampler());
            chunk.setUnsaved(true);
            changed.add(chunk);
        }
        if (!changed.isEmpty()) {
            level.getChunkSource().chunkMap.resendBiomesForChunks(changed);
        }
    }
}
