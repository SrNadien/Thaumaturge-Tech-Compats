package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.buuz135.industrial.block.resourceproduction.tile.ILaserBase;
import com.buuz135.industrial.block.tile.IndustrialAreaWorkingTile;
import com.buuz135.industrial.block.tile.IndustrialWorkingTile;
import com.buuz135.industrial.block.tile.RangeManager;
import com.buuz135.industrial.utils.BlockUtils;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.IndustrialForegoingConfig;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.LaserMachine;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.client.LaserGui;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePower;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

public class MagicalLaserDrillTile extends IndustrialAreaWorkingTile<MagicalLaserDrillTile> {
    private static final float EPSILON = 0.0001F;
    private static final int TOP_UP_INTERVAL = 10;

    @Save
    private BlockPos target = BlockPos.ZERO;

    private int topUpTimer;

    public MagicalLaserDrillTile(BlockPos pos, BlockState state) {
        super(MagicalLaserRegistry.DRILL, RangeManager.RangeType.BEHIND, false, IndustrialForegoingConfig.operationUnits(), pos, state);
    }

    @Override
    public IndustrialWorkingTile<MagicalLaserDrillTile>.WorkAction work() {
        int cost = IndustrialForegoingConfig.operationUnits();
        if (level == null || !IndustrialForegoingConfig.enabled(LaserMachine.DRILL)) {
            return new WorkAction(1.0F, 0);
        }
        if (!target.equals(BlockPos.ZERO) && !isValidTarget(target)) {
            target = BlockPos.ZERO;
            markForUpdate();
        }
        if (target.equals(BlockPos.ZERO)) {
            findTarget();
        }
        if (!target.equals(BlockPos.ZERO) && payable(cost) && level.getBlockEntity(target) instanceof ILaserBase<?> base) {
            ProgressBarComponent<?> bar = base.getBar();
            bar.setProgress(bar.getProgress() + 1);
            bar.tickBar();
            return new WorkAction(1.0F, cost);
        }
        return new WorkAction(1.0F, 0);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, MagicalLaserDrillTile tile) {
        super.serverTick(level, pos, state, tile);
        topUpTimer++;
        if (topUpTimer < TOP_UP_INTERVAL) {
            return;
        }
        topUpTimer = 0;
        if (!IndustrialForegoingConfig.enabled(LaserMachine.DRILL)) {
            return;
        }
        boolean wanted = !target.equals(BlockPos.ZERO) || IndustrialForegoingConfig.refillWhenIdle();
        int cost = IndustrialForegoingConfig.operationUnits();
        if (wanted && cost > 0 && vis().room() >= cost) {
            refill(cost);
        }
    }

    private boolean payable(int cost) {
        if (cost <= 0 || hasEnergy(cost)) {
            return true;
        }
        refill(cost);
        return hasEnergy(cost);
    }

    private void refill(int units) {
        if (level == null || level.isClientSide() || units <= 0) {
            return;
        }
        VisStorageComponent<MagicalLaserDrillTile> store = vis();
        if (IndustrialForegoingConfig.useAura()) {
            float vis = units / (float) IndustrialForegoingConfig.UNITS_PER_VIS;
            if (AuraHelper.drainVis(level, worldPosition, vis, true) >= vis - EPSILON) {
                AuraHelper.drainVis(level, worldPosition, vis, false);
                store.fill(units);
                return;
            }
        }
        if (IndustrialForegoingConfig.useNodes()) {
            Optional<NodeSource> found = NodePower.find(level, worldPosition, IndustrialForegoingConfig.nodeRadius());
            int paid = IndustrialForegoingConfig.nodeUnits();
            if (found.isPresent() && found.get().drain(paid, true) >= paid && found.get().drain(paid, false) >= paid) {
                store.fill(units);
            }
        }
    }

    private VisStorageComponent<MagicalLaserDrillTile> vis() {
        return (VisStorageComponent<MagicalLaserDrillTile>) getEnergyStorage();
    }

    @Override
    protected EnergyStorageComponent<MagicalLaserDrillTile> createEnergyStorage() {
        return new VisStorageComponent<>(IndustrialForegoingConfig.bufferUnits(), 10, 20);
    }

    @Override
    public VoxelShape getWorkingArea() {
        Vec3i normal = getFacingDirection().getOpposite().getNormal();
        return Shapes.box(-1.0D, 0.0D, -1.0D, 2.0D, 3.0D, 2.0D).move(
                worldPosition.getX() + normal.getX() * 2,
                worldPosition.getY() + normal.getY() * 2 - 1,
                worldPosition.getZ() + normal.getZ() * 2);
    }

    public void findTarget() {
        for (BlockPos candidate : BlockUtils.getBlockPosInAABB(getWorkingArea().bounds())) {
            if (isValidTarget(candidate)) {
                target = candidate;
                markForUpdate();
                return;
            }
        }
    }

    public boolean isValidTarget(BlockPos pos) {
        return !pos.equals(BlockPos.ZERO) && level != null && level.getBlockEntity(pos) instanceof ILaserBase<?>;
    }

    public BlockPos getTarget() {
        return target;
    }

    @Override
    public int getMaxProgress() {
        return IndustrialForegoingConfig.interval();
    }

    @Override
    public IAssetProvider getAssetProvider() {
        return PurpleAssetProvider.INSTANCE;
    }

    @Override
    public MagicalLaserDrillTile getSelf() {
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initClient() {
        super.initClient();
        addGuiAddonFactory(LaserGui::targetLabel);
        addGuiAddonFactory(() -> LaserGui.targetValue(this::getTarget));
    }
}
