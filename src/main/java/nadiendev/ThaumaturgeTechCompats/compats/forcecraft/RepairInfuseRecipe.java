package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import com.mojang.serialization.MapCodec;
import com.mrbysco.forcecraft.blockentity.infuser.InfuserUpgrade;
import com.mrbysco.forcecraft.upgrade.UpgradeBookData;
import com.mrbysco.forcecraft.upgrade.UpgradeBookTier;
import com.mrbysco.forcecraft.recipe.InfuseRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Optional;

public class RepairInfuseRecipe extends InfuseRecipe {
    private static final InfuserUpgrade MARK = new InfuserUpgrade() {
        @Override
        public MapCodec<? extends InfuserUpgrade> codec() {
            return MapCodec.unit(this);
        }

        @Override
        public boolean apply(ItemStack stack, UpgradeBookData data) {
            return false;
        }

        @Override
        public int levelCap(ItemStack stack) {
            return 0;
        }
    };

    public RepairInfuseRecipe(Ingredient center, Ingredient input, UpgradeBookTier tier, int time) {
        super(center, input, MARK, tier, Optional.empty(), 0, time, "");
    }

    @Override
    public boolean matchesTool(ItemStack toolStack, boolean ignoreInfused) {
        return getCenter().test(toolStack) && !ForceCraftRepair.isMarked(toolStack);
    }

    @Override
    public boolean matchesModifier(ItemStack tool, ItemStack modifier, UpgradeBookData data, boolean ignoreInfused) {
        boolean matched = super.matchesModifier(tool, modifier, data, ignoreInfused);
        if (matched && ignoreInfused) {
            ForceCraftRepair.mark(tool);
        }
        return matched;
    }

    @Override
    public boolean hasOutput() {
        return false;
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return ForceCraftRegistry.REPAIR_SERIALIZER.get();
    }
}
