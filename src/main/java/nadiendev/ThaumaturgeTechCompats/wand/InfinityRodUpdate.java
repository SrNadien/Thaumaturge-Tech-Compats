package nadiendev.ThaumaturgeTechCompats.wand;

import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TTAspects;
import com.leclowndu93150.thaumaturge.api.wands.IWandRodOnUpdate;
import com.leclowndu93150.thaumaturge.content.wands.WandVisHelper;
import nadiendev.ThaumaturgeTechCompats.config.ThaumaturgeTechCompatsConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InfinityRodUpdate implements IWandRodOnUpdate {
    @Override
    public void onUpdate(ItemStack stack, Player player) {
        if (!ThaumaturgeTechCompatsConfig.INFINITY_ROD_REFILLS_VIS.get()) {
            return;
        }
        int max = WandVisHelper.capacityOf(stack);
        for (ResourceKey<IAspect> primal : TTAspects.PRIMALS) {
            if (WandVisHelper.storedIn(stack, primal) < max) {
                WandVisHelper.fill(stack);
                return;
            }
        }
    }
}
