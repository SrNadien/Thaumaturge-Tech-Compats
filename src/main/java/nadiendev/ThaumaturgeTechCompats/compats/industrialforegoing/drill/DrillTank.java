package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

public class DrillTank extends FluidHandlerItemStack {
    public static final ResourceLocation LIQUID_DEATH = ResourceLocation.parse("thaumaturge:liquid_death");

    public DrillTank(ItemStack container) {
        super(MagicalInfinityDrillRegistry.LIQUID, container, MagicalInfinityDrillConfig.liquidCapacity());
    }

    public static boolean isLiquidDeath(Fluid fluid) {
        return fluid != Fluids.EMPTY && LIQUID_DEATH.equals(BuiltInRegistries.FLUID.getKey(fluid));
    }

    public static Fluid liquidDeath() {
        return BuiltInRegistries.FLUID.get(LIQUID_DEATH);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return MagicalInfinityDrillConfig.liquidEnabled() && isLiquidDeath(fluid.getFluid());
    }
}
