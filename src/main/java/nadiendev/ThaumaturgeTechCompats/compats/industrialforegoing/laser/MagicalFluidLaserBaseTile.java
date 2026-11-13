package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.buuz135.industrial.block.resourceproduction.tile.ILaserBase;
import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.buuz135.industrial.recipe.LaserDrillFluidRecipe;
import com.buuz135.industrial.recipe.LaserDrillRarity;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.augment.AugmentTypes;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.hrznstudio.titanium.component.sideness.IFacingComponent;
import com.hrznstudio.titanium.item.AugmentWrapper;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.IndustrialForegoingConfig;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.LaserMachine;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.client.LaserGui;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class MagicalFluidLaserBaseTile extends IndustrialMachineTile<MagicalFluidLaserBaseTile> implements ILaserBase<MagicalFluidLaserBaseTile> {
    private static final int TANK_CAPACITY = 32000;
    private static final int RESET_DEPTH = -100;

    @Save
    private ProgressBarComponent<MagicalFluidLaserBaseTile> work;
    @Save
    private SidedInventoryComponent<MagicalFluidLaserBaseTile> catalyst;
    @Save
    private SidedFluidTankComponent<MagicalFluidLaserBaseTile> output;
    @Save
    private int miningDepth;

    public MagicalFluidLaserBaseTile(BlockPos pos, BlockState state) {
        super(MagicalLaserRegistry.FLUID_BASE, pos, state);
        setShowEnergy(false);
        miningDepth = pos.getY();

        work = new LaserBaseBar<MagicalFluidLaserBaseTile>(74, 42, IndustrialForegoingConfig.fluidMaxProgress())
                .setBarDirection(ProgressBarComponent.BarDirection.ARROW_RIGHT)
                .setCanIncrease(tile -> true)
                .setProgressIncrease(0)
                .setCanReset(tile -> true)
                .setOnStart(this::applyEfficiency)
                .setOnFinishWork(this::produce);
        addProgressBar(work);

        catalyst = (SidedInventoryComponent<MagicalFluidLaserBaseTile>) new SidedInventoryComponent<MagicalFluidLaserBaseTile>("lens", 50, 42, 1, 0)
                .setColor(DyeColor.BLUE)
                .setRange(2, 3)
                .setSlotLimit(1);
        addInventory(catalyst);
        catalyst.getFacingModes().replaceAll((side, mode) -> IFacingComponent.FaceMode.NONE);

        output = (SidedFluidTankComponent<MagicalFluidLaserBaseTile>) new SidedFluidTankComponent<MagicalFluidLaserBaseTile>("output", TANK_CAPACITY, 102, 20, 1)
                .setColor(DyeColor.ORANGE)
                .setTankAction(FluidTankComponent.Action.DRAIN);
        addTank(output);
    }

    private void applyEfficiency() {
        float efficiency = hasAugmentInstalled(AugmentTypes.EFFICIENCY)
                ? AugmentWrapper.getType(getInstalledAugments(AugmentTypes.EFFICIENCY).get(0), AugmentTypes.EFFICIENCY)
                : 1.0F;
        work.setMaxProgress((int) Math.floor(IndustrialForegoingConfig.fluidMaxProgress() * efficiency));
    }

    private void produce() {
        ItemStack lens = catalyst.getStackInSlot(0);
        if (level == null || level.isClientSide() || lens.isEmpty() || !IndustrialForegoingConfig.enabled(LaserMachine.FLUID_BASE)) {
            return;
        }
        AABB area = Shapes.box(-1.0D, 0.0D, -1.0D, 2.0D, 3.0D, 2.0D)
                .move(worldPosition.getX(), worldPosition.getY() - 1, worldPosition.getZ()).bounds();
        DimensionType dimension = level.dimensionType();
        Holder<Biome> biome = level.getBiome(worldPosition);
        for (LaserDrillFluidRecipe recipe : LaserRecipes.fluid(level)) {
            if (!recipe.catalyst.test(lens)
                    || LaserDrillRarity.getValidRarity(level, recipe.rarity, dimension, biome, miningDepth) == null
                    || !LaserRecipes.entityAllows(level, area, recipe.entityData)) {
                continue;
            }
            FluidStack[] fluids = recipe.output.getFluids();
            if (fluids.length > 0) {
                output.fillForced(fluids[0].copy(), IFluidHandler.FluidAction.EXECUTE);
            }
            return;
        }
    }

    @Override
    public void handleButtonMessage(int id, Player player, CompoundTag data) {
        super.handleButtonMessage(id, player, data);
        if (id == LaserDepthMessage.ID) {
            miningDepth = data.getInt(LaserDepthMessage.KEY);
            syncObject(miningDepth);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (miningDepth == RESET_DEPTH) {
            miningDepth = worldPosition.getY();
        }
    }

    @Override
    protected EnergyStorageComponent<MagicalFluidLaserBaseTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(0, 4, 10);
    }

    @Override
    public ProgressBarComponent<MagicalFluidLaserBaseTile> getBar() {
        return work;
    }

    @Override
    public boolean canAcceptAugment(ItemStack stack) {
        return AugmentWrapper.hasType(stack, AugmentTypes.EFFICIENCY);
    }

    @Override
    public IAssetProvider getAssetProvider() {
        return PurpleAssetProvider.INSTANCE;
    }

    @Override
    public MagicalFluidLaserBaseTile getSelf() {
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initClient() {
        super.initClient();
        addGuiAddonFactory(LaserGui::depthLabel);
        addGuiAddonFactory(() -> LaserGui.depthField(getBlockPos(), miningDepth));
    }
}
