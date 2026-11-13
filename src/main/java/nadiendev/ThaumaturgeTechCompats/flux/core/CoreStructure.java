package nadiendev.ThaumaturgeTechCompats.flux.core;

import com.leclowndu93150.thaumaturge.content.aura.node.BlockNodeStabilizer;
import com.leclowndu93150.thaumaturge.content.infusion.BlockEntityPedestal;
import com.leclowndu93150.thaumaturge.registry.TTBlocks;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public final class CoreStructure {
    private CoreStructure() {
    }

    public static Block metal(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> TTBlocks.METAL_THAUMIUM_BLOCK.get();
            case ALCHEMICAL_BRASS -> TTBlocks.METAL_BRASS_BLOCK.get();
            case VOID_METAL -> TTBlocks.METAL_VOID_BLOCK.get();
        };
    }

    public static boolean accepts(FluxTier core, BlockState state) {
        Block block = state.getBlock();
        for (FluxTier tier : FluxTier.values()) {
            boolean allowed = tier == core || (CoreConfig.acceptHigherTier() && tier.ordinal() > core.ordinal());
            if (allowed && metal(tier) == block) {
                return true;
            }
        }
        return false;
    }

    public static List<BlockPos> required(BlockPos core) {
        List<BlockPos> positions = new ArrayList<>();
        if (CoreConfig.requireRing()) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (x != 0 || z != 0) {
                        positions.add(core.offset(x, 0, z));
                    }
                }
            }
        }
        if (CoreConfig.requireCaps()) {
            positions.add(core.above());
            positions.add(core.below());
        }
        return positions;
    }

    public static int stabilizerPoints(Block block) {
        if (!(block instanceof BlockNodeStabilizer stabilizer)) {
            return 0;
        }
        StabilizerTier tier = StabilizerIntegration.tierOf(block);
        if (tier != null) {
            return CoreConfig.weight(tier);
        }
        return stabilizer.isAdvanced() ? CoreConfig.weightAdvanced() : CoreConfig.weightBasic();
    }

    public static CoreScan scan(Level level, BlockPos core, FluxTier tier) {
        int missing = 0;
        for (BlockPos position : required(core)) {
            if (!level.isLoaded(position) || !accepts(tier, level.getBlockState(position))) {
                missing++;
            }
        }

        int cellRadius = CoreConfig.cellRadius();
        int stabilizerRadius = CoreConfig.stabilizerRadius();
        int maxCells = CoreConfig.maxCells();
        int radius = Math.max(cellRadius, stabilizerRadius);
        int cells = 0;
        long cellCapacity = 0L;
        int stabilizers = 0;
        int points = 0;
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }
                    cursor.set(core.getX() + dx, core.getY() + dy, core.getZ() + dz);
                    if (!level.isLoaded(cursor)) {
                        continue;
                    }
                    int distance = Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)));
                    BlockState state = level.getBlockState(cursor);
                    if (distance <= stabilizerRadius && state.getBlock() instanceof BlockNodeStabilizer) {
                        stabilizers++;
                        points += stabilizerPoints(state.getBlock());
                    }
                    if (distance <= cellRadius && cells < maxCells && state.hasBlockEntity()) {
                        BlockEntity entity = level.getBlockEntity(cursor);
                        if (entity instanceof BlockEntityPedestal pedestal) {
                            ItemStack stack = pedestal.getItem();
                            if (stack.getItem() instanceof CapacityCellItem cell) {
                                cells++;
                                cellCapacity = Math.min(Long.MAX_VALUE / 8L, cellCapacity + CoreConfig.cellCapacity(cell.tier()));
                            }
                        }
                    }
                }
            }
        }
        return new CoreScan(missing == 0, missing, cells, cellCapacity, stabilizers, points);
    }
}
