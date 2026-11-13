package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.buuz135.industrial.block.resourceproduction.tile.ILaserBase;
import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.buuz135.industrial.recipe.LaserDrillOreRecipe;
import com.buuz135.industrial.recipe.LaserDrillRarity;
import com.buuz135.industrial.utils.ItemStackUtils;
import com.buuz135.industrial.utils.ItemStackWeightedItem;
import com.hrznstudio.titanium._impl.TagConfig;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.augment.AugmentTypes;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.hrznstudio.titanium.component.sideness.IFacingComponent;
import com.hrznstudio.titanium.item.AugmentWrapper;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.IndustrialForegoingConfig;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.LaserMachine;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.client.LaserGui;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.random.WeightedRandom;
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
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class MagicalOreLaserBaseTile extends IndustrialMachineTile<MagicalOreLaserBaseTile> implements ILaserBase<MagicalOreLaserBaseTile> {
    private static final int LENS_SLOTS = 6;
    private static final int OUTPUT_SLOTS = 15;
    private static final int RESET_DEPTH = -100;

    @Save
    private ProgressBarComponent<MagicalOreLaserBaseTile> work;
    @Save
    private SidedInventoryComponent<MagicalOreLaserBaseTile> lens;
    @Save
    private SidedInventoryComponent<MagicalOreLaserBaseTile> output;
    @Save
    private int miningDepth;

    public MagicalOreLaserBaseTile(BlockPos pos, BlockState state) {
        super(MagicalLaserRegistry.ORE_BASE, pos, state);
        setShowEnergy(false);
        miningDepth = pos.getY();

        work = new LaserBaseBar<MagicalOreLaserBaseTile>(12, 22, IndustrialForegoingConfig.oreMaxProgress())
                .setColor(DyeColor.YELLOW)
                .setCanIncrease(tile -> true)
                .setProgressIncrease(0)
                .setCanReset(tile -> true)
                .setOnStart(this::applyEfficiency)
                .setOnFinishWork(this::produce);
        addProgressBar(work);

        lens = (SidedInventoryComponent<MagicalOreLaserBaseTile>) new SidedInventoryComponent<MagicalOreLaserBaseTile>("lens", 30, 24, LENS_SLOTS, 0)
                .setColor(DyeColor.BLUE)
                .setRange(2, 3)
                .setSlotLimit(1);
        addInventory(lens);
        lens.getFacingModes().replaceAll((side, mode) -> IFacingComponent.FaceMode.NONE);

        output = (SidedInventoryComponent<MagicalOreLaserBaseTile>) new SidedInventoryComponent<MagicalOreLaserBaseTile>("output", 74, 24, OUTPUT_SLOTS, 1)
                .setColor(DyeColor.ORANGE)
                .setRange(5, 3)
                .setInputFilter((stack, slot) -> false);
        addInventory(output);
    }

    private void applyEfficiency() {
        float efficiency = hasAugmentInstalled(AugmentTypes.EFFICIENCY)
                ? AugmentWrapper.getType(getInstalledAugments(AugmentTypes.EFFICIENCY).get(0), AugmentTypes.EFFICIENCY)
                : 1.0F;
        work.setMaxProgress((int) Math.floor(IndustrialForegoingConfig.oreMaxProgress() * efficiency));
    }

    private void produce() {
        if (level == null || level.isClientSide() || !IndustrialForegoingConfig.enabled(LaserMachine.ORE_BASE) || ItemStackUtils.isInventoryFull(output)) {
            return;
        }
        AABB area = Shapes.box(-1.0D, 0.0D, -1.0D, 2.0D, 3.0D, 2.0D)
                .move(worldPosition.getX(), worldPosition.getY() - 1, worldPosition.getZ()).bounds();
        DimensionType dimension = level.dimensionType();
        Holder<Biome> biome = level.getBiome(worldPosition);
        List<ItemStackWeightedItem> candidates = new ArrayList<>();
        for (LaserDrillOreRecipe recipe : LaserRecipes.ore(level)) {
            LaserDrillRarity rarity = LaserDrillRarity.getValidRarity(level, recipe.rarity, dimension, biome, miningDepth);
            if (rarity == null || !LaserRecipes.entityAllows(level, area, recipe.entityData)) {
                continue;
            }
            ItemStack result = pick(recipe);
            if (!result.isEmpty()) {
                candidates.add(new ItemStackWeightedItem(result, weight(recipe, rarity)));
            }
        }
        if (candidates.isEmpty()) {
            return;
        }
        WeightedRandom.getRandomItem(level.getRandom(), candidates)
                .ifPresent(chosen -> ItemHandlerHelper.insertItem(output, chosen.getStack(), false));
    }

    private int weight(LaserDrillOreRecipe recipe, LaserDrillRarity rarity) {
        int weight = rarity.weight();
        for (int slot = 0; slot < lens.getSlots(); slot++) {
            if (recipe.catalyst.test(lens.getStackInSlot(slot))) {
                weight += IndustrialForegoingConfig.oreLensBonus();
            }
        }
        return weight;
    }

    private static ItemStack pick(LaserDrillOreRecipe recipe) {
        ItemStack[] options = recipe.output.getItems();
        for (String namespace : TagConfig.ITEM_PREFERENCE) {
            for (ItemStack option : options) {
                if (BuiltInRegistries.ITEM.getKey(option.getItem()).getNamespace().equals(namespace)) {
                    return option.copy();
                }
            }
        }
        return options.length == 0 ? ItemStack.EMPTY : options[0].copy();
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
    protected EnergyStorageComponent<MagicalOreLaserBaseTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(0, 4, 10);
    }

    @Override
    public ProgressBarComponent<MagicalOreLaserBaseTile> getBar() {
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
    public MagicalOreLaserBaseTile getSelf() {
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
