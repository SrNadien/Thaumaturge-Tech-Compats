package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class FluxKnifeCutting {
    private FluxKnifeCutting() {
    }

    public static boolean active(ItemStack tool) {
        return tool != null && tool.getItem() instanceof FluxKnifeItem && FarmersDelightTaintedConfig.cutEnabled();
    }

    public static List<ItemStack> apply(ServerLevel level, BlockPos pos, ItemStack stored, List<ItemStack> results) {
        List<ItemStack> kept = new ArrayList<>(results);
        FoodProperties storedFood = stored.get(DataComponents.FOOD);
        boolean food = storedFood != null;
        int resultNutrition = 0;
        for (ItemStack result : kept) {
            FoodProperties resultFood = result.get(DataComponents.FOOD);
            if (resultFood != null) {
                food = true;
                resultNutrition += resultFood.nutrition() * result.getCount();
            }
        }
        if (FarmersDelightTaintedConfig.cutFoodOnly() && !food) {
            return kept;
        }
        int nutrition = storedFood == null ? resultNutrition : storedFood.nutrition();
        float flux = FarmersDelightTaintedConfig.cutBaseFlux() + FarmersDelightTaintedConfig.cutFluxPerNutrition() * nutrition;
        flux = Math.min(flux, FarmersDelightTaintedConfig.cutMaxFlux());
        if (flux > 0.0F) {
            AuraHelper.addFlux(level, pos, flux);
        }
        if (FarmersDelightTaintedConfig.CUT_FLUX_ONLY.equals(FarmersDelightTaintedConfig.cutMode())) {
            kept.removeIf(result -> result.has(DataComponents.FOOD));
        }
        return kept;
    }
}
