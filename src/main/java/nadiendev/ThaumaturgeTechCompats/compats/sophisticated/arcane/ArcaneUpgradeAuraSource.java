package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import com.leclowndu93150.thaumaturge.api.recipe.ArcaneWorkbenchContext;
import com.leclowndu93150.thaumaturge.api.recipe.IArcaneWorkbench;
import com.leclowndu93150.thaumaturge.api.recipe.IWorkbenchAuraSource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public final class ArcaneUpgradeAuraSource implements IWorkbenchAuraSource {
    static final ThreadLocal<ChargerEffect> CHARGER = new ThreadLocal<>();

    private static final float EPSILON = 0.0001F;

    @Override
    public int supply(ArcaneWorkbenchContext context, ServerPlayer player, IArcaneWorkbench workbench, int amount, TransactionContext transaction) {
        if (amount <= 0 || !ArcaneUpgradeContainer.HOST.equals(context.hostIdentity())) {
            return 0;
        }
        ChargerEffect effect = CHARGER.get();
        int radius = effect == null ? SophisticatedConfig.auraChunkRadius() : effect.radius();
        int discount = effect == null ? 0 : effect.discountPercent();
        int needed = discount >= 100 ? 0 : (int) Math.ceil(amount * (100 - discount) / 100.0D);
        if (needed <= 0) {
            return amount;
        }
        float drained = drain(player.serverLevel(), player.blockPosition(), radius, needed, transaction);
        if (drained + EPSILON >= needed) {
            return amount;
        }
        return (int) Math.floor((drained + EPSILON) * amount / needed);
    }

    private static float drain(ServerLevel level, BlockPos origin, int radius, int needed, TransactionContext transaction) {
        float drained = AuraHelper.drainVis(level, origin, needed, transaction);
        for (int ring = 1; ring <= radius && drained + EPSILON < needed; ring++) {
            for (int dx = -ring; dx <= ring && drained + EPSILON < needed; dx++) {
                for (int dz = -ring; dz <= ring && drained + EPSILON < needed; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) {
                        continue;
                    }
                    BlockPos neighbour = origin.offset(dx * 16, 0, dz * 16);
                    if (level.hasChunkAt(neighbour)) {
                        drained += AuraHelper.drainVis(level, neighbour, needed - drained, transaction);
                    }
                }
            }
        }
        return drained;
    }
}
