package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import com.leclowndu93150.thaumaturge.content.workbench.BlockArcaneWorkbenchCharger;
import nadiendev.ThaumaturgeTechCompats.braincharger.BrainChargerConfig;
import nadiendev.ThaumaturgeTechCompats.braincharger.TieredWorkbenchCharger;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedConfig;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public final class ChargerEffects {
    private ChargerEffects() {
    }

    public static boolean isCharger(ItemStack stack) {
        return stack.getItem() instanceof BlockItem item && item.getBlock() instanceof BlockArcaneWorkbenchCharger;
    }

    public static ChargerEffect of(ItemStack stack) {
        if (!SophisticatedConfig.chargerSlotEnabled() || !isCharger(stack)) {
            return null;
        }
        Block block = ((BlockItem) stack.getItem()).getBlock();
        if (block instanceof TieredWorkbenchCharger tiered) {
            return new ChargerEffect(BrainChargerConfig.radius(tiered.tier()), BrainChargerConfig.discountPercent(tiered.tier()));
        }
        return new ChargerEffect(SophisticatedConfig.thaumaturgeChargerRadius(), SophisticatedConfig.thaumaturgeChargerDiscount());
    }
}
