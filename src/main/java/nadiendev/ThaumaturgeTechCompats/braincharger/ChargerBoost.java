package nadiendev.ThaumaturgeTechCompats.braincharger;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import com.leclowndu93150.thaumaturge.content.workbench.BlockEntityArcaneWorkbench;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ChargerBoost {
    private static final double EPSILON = 1.0E-4;
    private static final int UNLIMITED_VIS = 1_000_000;

    private ChargerBoost() {
    }

    public static BoostTier tierAbove(BlockEntityArcaneWorkbench workbench) {
        Level level = workbench.getLevel();
        if (level == null || !BrainChargerConfig.chargersEnabled()) {
            return null;
        }
        if (level.getBlockState(workbench.getBlockPos().above()).getBlock() instanceof TieredWorkbenchCharger charger) {
            return charger.tier();
        }
        return null;
    }

    public static int availableVis(BlockEntityArcaneWorkbench workbench, BoostTier tier) {
        int discount = BrainChargerConfig.discountPercent(tier);
        if (discount >= 100) {
            return UNLIMITED_VIS;
        }
        Level level = workbench.getLevel();
        double total = 0.0;
        for (BlockPos anchor : anchors(level, workbench.getBlockPos(), BrainChargerConfig.radius(tier))) {
            total += AuraHelper.getVis(level, anchor);
        }
        return (int) Math.min(UNLIMITED_VIS, Math.floor(total * 100.0 / (100 - discount)));
    }

    public static boolean spend(BlockEntityArcaneWorkbench workbench, BoostTier tier, int amount, TransactionContext transaction) {
        if (amount <= 0) {
            return true;
        }
        Level level = workbench.getLevel();
        if (level == null || level.isClientSide()) {
            return false;
        }
        double remaining = Math.ceil(amount * (100 - BrainChargerConfig.discountPercent(tier)) / 100.0);
        if (remaining <= EPSILON) {
            return true;
        }
        List<BlockPos> pending = anchors(level, workbench.getBlockPos(), BrainChargerConfig.radius(tier));
        while (remaining > EPSILON && !pending.isEmpty()) {
            double drainedThisPass = 0.0;
            double share = remaining / pending.size();
            Iterator<BlockPos> iterator = pending.iterator();
            while (iterator.hasNext()) {
                BlockPos anchor = iterator.next();
                float wanted = (float) Math.min(remaining, share);
                float drained = Math.max(0.0F, Math.min(wanted, AuraHelper.drainVis(level, anchor, wanted, transaction)));
                remaining -= drained;
                drainedThisPass += drained;
                if (drained + EPSILON < wanted) {
                    iterator.remove();
                }
                if (remaining <= EPSILON) {
                    return true;
                }
            }
            if (drainedThisPass <= EPSILON) {
                return false;
            }
        }
        return remaining <= EPSILON;
    }

    private static List<BlockPos> anchors(Level level, BlockPos origin, int radius) {
        ChunkPos center = ChunkPos.containing(origin);
        List<BlockPos> anchors = new ArrayList<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos middle = new ChunkPos(center.x() + dx, center.z() + dz).getMiddleBlockPosition(origin.getY());
                if (level.hasChunkAt(middle)) {
                    anchors.add(middle);
                }
            }
        }
        return anchors;
    }
}
