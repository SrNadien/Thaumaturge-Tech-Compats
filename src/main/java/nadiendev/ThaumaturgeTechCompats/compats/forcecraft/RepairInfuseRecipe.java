package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import com.mrbysco.forcecraft.blockentities.InfuserBlockEntity;
import com.mrbysco.forcecraft.blockentities.InfuserModifierType;
import com.mrbysco.forcecraft.items.infuser.UpgradeBookTier;
import com.mrbysco.forcecraft.recipe.InfuseRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RepairInfuseRecipe extends InfuseRecipe {
    public RepairInfuseRecipe(Ingredient center, Ingredient input, UpgradeBookTier tier, int time) {
        super(center, input, InfuserModifierType.ITEM, tier, ItemStack.EMPTY, time);
    }

    @Override
    public boolean matchesTool(ItemStack toolStack, boolean ignoreInfused) {
        return this.center.test(toolStack) && !ForceCraftRepair.isMarked(toolStack);
    }

    @Override
    public boolean matchesModifier(RecipeInput inv, ItemStack modifier, boolean ignoreInfused) {
        boolean matched = super.matchesModifier(inv, modifier, ignoreInfused);
        if (matched && ignoreInfused) {
            ForceCraftRepair.mark(inv.getItem(InfuserBlockEntity.SLOT_TOOL));
        }
        return matched;
    }

    @Override
    public boolean hasOutput() {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ForceCraftRegistry.REPAIR_SERIALIZER.get();
    }
}
