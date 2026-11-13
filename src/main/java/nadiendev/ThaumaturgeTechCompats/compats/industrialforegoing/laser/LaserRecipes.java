package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.recipe.LaserDrillFluidRecipe;
import com.buuz135.industrial.recipe.LaserDrillOreRecipe;
import com.buuz135.industrial.recipe.data.EntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;

final class LaserRecipes {
    private static final float ENTITY_MIN_HEALTH = 5.0F;
    private static final float ENTITY_DAMAGE = 5.0F;

    private LaserRecipes() {
    }

    @SuppressWarnings("unchecked")
    static List<LaserDrillOreRecipe> ore(Level level) {
        RecipeType<LaserDrillOreRecipe> type = (RecipeType<LaserDrillOreRecipe>) ModuleCore.LASER_DRILL_TYPE.get();
        return level.getRecipeManager().getAllRecipesFor(type).stream().map(RecipeHolder::value).toList();
    }

    @SuppressWarnings("unchecked")
    static List<LaserDrillFluidRecipe> fluid(Level level) {
        RecipeType<LaserDrillFluidRecipe> type = (RecipeType<LaserDrillFluidRecipe>) ModuleCore.LASER_DRILL_FLUID_TYPE.get();
        return level.getRecipeManager().getAllRecipesFor(type).stream().map(RecipeHolder::value).toList();
    }

    static boolean entityAllows(Level level, AABB area, Optional<EntityData> required) {
        if (required.isEmpty()) {
            return true;
        }
        EntityData data = required.get();
        CompoundTag expected = data.getData();
        List<LivingEntity> found = level.getEntitiesOfClass(LivingEntity.class, area,
                entity -> entity.getHealth() > ENTITY_MIN_HEALTH && data.getEntity().test(entity));
        for (LivingEntity entity : found) {
            if (!expected.isEmpty() && !NbtUtils.compareNbt(entity.saveWithoutId(new CompoundTag()), expected, true)) {
                continue;
            }
            entity.hurt(entity.damageSources().generic(), ENTITY_DAMAGE);
            return true;
        }
        return false;
    }
}
