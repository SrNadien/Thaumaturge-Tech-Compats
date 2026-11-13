package nadiendev.ThaumaturgeTechCompats.purity.biome;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import com.leclowndu93150.thaumaturge.api.taint.ITaintBlock;
import com.leclowndu93150.thaumaturge.api.taint.TaintApi;
import com.leclowndu93150.thaumaturge.content.entity.AbstractTaintSeed;
import com.leclowndu93150.thaumaturge.content.entity.EntityTaintSeedPrime;
import com.leclowndu93150.thaumaturge.content.taint.ecology.TaintBiomeManager;
import com.leclowndu93150.thaumaturge.content.taint.flux.PhysicalFlux;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public final class BiomePurifier {
    private static final int BLOCKS_PER_QUART = 4;
    private static final int MAX_PARTICLES = 160;

    private BiomePurifier() {
    }

    public record Summary(int biomeColumns, int blocks, int seeds) {
        public boolean changedAnything() {
            return biomeColumns > 0 || blocks > 0 || seeds > 0;
        }
    }

    public static Summary purify(ServerLevel level, BlockPos center) {
        if (!BiomePurificationConfig.enabled()) {
            return new Summary(0, 0, 0);
        }
        int radius = BiomePurificationConfig.radius();
        int vertical = BiomePurificationConfig.verticalRadius();
        int seeds = BiomePurificationConfig.removeSeeds() ? removeSeeds(level, center, radius, vertical) : 0;
        int blocks = BiomePurificationConfig.cleanBlocks() || BiomePurificationConfig.cleanPhysicalFlux()
                ? cleanBlocks(level, center, radius, vertical) : 0;
        int columns = BiomePurificationConfig.restoreBiome() ? restoreBiome(level, center, radius) : 0;
        cleanChunks(level, center, radius);
        Summary summary = new Summary(columns, blocks, seeds);
        if (BiomePurificationConfig.particles()) {
            int count = Math.min(MAX_PARTICLES, radius * 8);
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, center.getX() + 0.5D, center.getY() + 0.5D, center.getZ() + 0.5D,
                    count, radius * 0.45D, vertical * 0.3D, radius * 0.45D, 0.0D);
        }
        return summary;
    }

    private static int removeSeeds(ServerLevel level, BlockPos center, int radius, int vertical) {
        boolean prime = BiomePurificationConfig.removePrimeSeeds();
        AABB box = new AABB(center).inflate(radius, vertical, radius);
        List<AbstractTaintSeed> seeds = level.getEntitiesOfClass(AbstractTaintSeed.class, box,
                seed -> prime || !(seed instanceof EntityTaintSeedPrime));
        for (AbstractTaintSeed seed : seeds) {
            if (BiomePurificationConfig.particles()) {
                level.sendParticles(ParticleTypes.POOF, seed.getX(), seed.getY(0.5D), seed.getZ(), 12, 0.3D, 0.3D, 0.3D, 0.02D);
            }
            seed.discard();
        }
        return seeds.size();
    }

    private static int cleanBlocks(ServerLevel level, BlockPos center, int radius, int vertical) {
        boolean decay = BiomePurificationConfig.cleanBlocks();
        boolean flux = BiomePurificationConfig.cleanPhysicalFlux();
        double chance = BiomePurificationConfig.blockCleanChance();
        RandomSource random = level.getRandom();
        double horizontalSq = (double) radius * radius;
        double verticalSq = (double) vertical * vertical;
        int minY = Math.max(level.getMinY(), center.getY() - vertical);
        int maxY = Math.min(level.getMaxY(), center.getY() + vertical);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        int cleaned = 0;
        for (int x = center.getX() - radius; x <= center.getX() + radius; x++) {
            for (int z = center.getZ() - radius; z <= center.getZ() + radius; z++) {
                double dx = x - center.getX();
                double dz = z - center.getZ();
                double ring = (dx * dx + dz * dz) / horizontalSq;
                if (ring > 1.0D) {
                    continue;
                }
                cursor.set(x, center.getY(), z);
                if (!level.hasChunkAt(cursor)) {
                    continue;
                }
                for (int y = minY; y <= maxY; y++) {
                    double dy = y - center.getY();
                    if (ring + dy * dy / verticalSq > 1.0D) {
                        continue;
                    }
                    cursor.setY(y);
                    BlockState state = level.getBlockState(cursor);
                    if (state.isAir()) {
                        continue;
                    }
                    if (decay && state.getBlock() instanceof ITaintBlock taintBlock) {
                        if (chance < 1.0D && random.nextDouble() >= chance) {
                            continue;
                        }
                        taintBlock.decay(level, cursor.immutable(), state);
                        cleaned++;
                    } else if (flux && PhysicalFlux.isPhysicalFlux(state)) {
                        int amount = PhysicalFlux.amount(level, cursor);
                        if (amount > 0 && PhysicalFlux.reduce(level, cursor.immutable(), amount) > 0) {
                            cleaned++;
                        }
                    }
                }
            }
        }
        return cleaned;
    }

    private static int restoreBiome(ServerLevel level, BlockPos center, int radius) {
        boolean natural = BiomePurificationConfig.restoreNaturalBiome();
        double radiusSq = (double) radius * radius;
        int startX = Math.floorDiv(center.getX() - radius, BLOCKS_PER_QUART) * BLOCKS_PER_QUART;
        int startZ = Math.floorDiv(center.getZ() - radius, BLOCKS_PER_QUART) * BLOCKS_PER_QUART;
        int restored = 0;
        for (int x = startX; x <= center.getX() + radius; x += BLOCKS_PER_QUART) {
            for (int z = startZ; z <= center.getZ() + radius; z += BLOCKS_PER_QUART) {
                double dx = x + BLOCKS_PER_QUART * 0.5D - center.getX();
                double dz = z + BLOCKS_PER_QUART * 0.5D - center.getZ();
                if (dx * dx + dz * dz > radiusSq) {
                    continue;
                }
                BlockPos column = new BlockPos(x, center.getY(), z);
                if (!level.hasChunkAt(column)) {
                    continue;
                }
                if (!natural && !TaintBiomeManager.isChangedColumn(level, column)) {
                    continue;
                }
                if (TaintBiomeManager.restoreColumn(level, column)) {
                    restored++;
                }
            }
        }
        return restored;
    }

    private static void cleanChunks(ServerLevel level, BlockPos center, int radius) {
        float ecology = BiomePurificationConfig.ecologyCleanAmount();
        float flux = BiomePurificationConfig.fluxDrainPerChunk();
        if (ecology <= 0.0F && flux <= 0.0F) {
            return;
        }
        int minChunkX = SectionPos.blockToSectionCoord(center.getX() - radius);
        int maxChunkX = SectionPos.blockToSectionCoord(center.getX() + radius);
        int minChunkZ = SectionPos.blockToSectionCoord(center.getZ() - radius);
        int maxChunkZ = SectionPos.blockToSectionCoord(center.getZ() + radius);
        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                BlockPos pos = new BlockPos(SectionPos.sectionToBlockCoord(chunkX, 8), center.getY(), SectionPos.sectionToBlockCoord(chunkZ, 8));
                if (!level.hasChunkAt(pos)) {
                    continue;
                }
                if (ecology > 0.0F) {
                    TaintApi.cleanEcologicalPressure(level, pos, ecology);
                }
                if (flux > 0.0F) {
                    AuraHelper.drainFlux(level, pos, flux, false);
                }
            }
        }
    }
}
