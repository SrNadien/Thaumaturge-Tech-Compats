package nadiendev.ThaumaturgeTechCompats.flux.charger;

import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TTAspects;
import com.leclowndu93150.thaumaturge.api.items.RechargeAccess;
import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.content.wands.WandVisHelper;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxItem;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxStorage;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ChargerRecharge {
    private static final int MAX_CENTIVIS_PER_PASS = 1_000_000;
    private static final int MAX_VIS_PER_PASS = 100_000;

    private ChargerRecharge() {
    }

    public static long charge(ItemStack stack, IFluxStorage source, long perItem) {
        if (stack.isEmpty() || source.stored() <= 0L || perItem <= 0L) {
            return 0L;
        }
        long budget = Math.min(perItem, source.stored());
        if (stack.getItem() instanceof IFluxItem item) {
            return chargeFlux(stack, item, source, budget);
        }
        if (stack.getItem() instanceof ItemWand) {
            return ChargerConfig.wands() ? chargeWand(stack, source, budget) : 0L;
        }
        return ChargerConfig.visItems() ? chargeVis(stack, source, budget) : 0L;
    }

    private static long chargeFlux(ItemStack stack, IFluxItem item, IFluxStorage source, long budget) {
        long accepted = item.insertFlux(stack, budget, true);
        if (accepted <= 0L) {
            return 0L;
        }
        long taken = source.extract(accepted, false);
        if (taken > 0L) {
            item.insertFlux(stack, taken, false);
        }
        return taken;
    }

    private static long chargeWand(ItemStack stack, IFluxStorage source, long budget) {
        int remaining = (int) Math.min(MAX_CENTIVIS_PER_PASS, budget / ChargerConfig.fluxPerCentivis());
        if (remaining <= 0) {
            return 0L;
        }
        int max = WandVisHelper.getMaxVis(stack);
        List<ResourceKey<IAspect>> open = new ArrayList<>();
        for (ResourceKey<IAspect> primal : TTAspects.PRIMALS) {
            if (WandVisHelper.getVis(stack, primal) < max) {
                open.add(primal);
            }
        }
        if (open.isEmpty()) {
            return 0L;
        }
        int share = Math.max(1, remaining / open.size());
        int added = 0;
        for (ResourceKey<IAspect> primal : open) {
            if (remaining <= 0) {
                break;
            }
            int room = max - WandVisHelper.getVis(stack, primal);
            int step = Math.min(share, Math.min(remaining, room));
            if (step > 0) {
                WandVisHelper.addRealVis(stack, primal, step, true);
                added += step;
                remaining -= step;
            }
        }
        if (added <= 0) {
            return 0L;
        }
        return source.extract(added * ChargerConfig.fluxPerCentivis(), false);
    }

    private static long chargeVis(ItemStack stack, IFluxStorage source, long budget) {
        int room = RechargeAccess.roomFor(stack);
        if (room <= 0) {
            return 0L;
        }
        int vis = (int) Math.min(Math.min(room, MAX_VIS_PER_PASS), budget / ChargerConfig.fluxPerVis());
        if (vis <= 0) {
            return 0L;
        }
        int added = RechargeAccess.addVis(stack, vis);
        if (added <= 0) {
            return 0L;
        }
        return source.extract(added * ChargerConfig.fluxPerVis(), false);
    }
}
