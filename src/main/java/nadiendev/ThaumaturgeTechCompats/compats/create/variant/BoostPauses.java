package nadiendev.ThaumaturgeTechCompats.compats.create.variant;

import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.lang.reflect.Field;

public final class BoostPauses {
    private static final int SPOUT_FILL_TICK = 5;
    private static final Field DEPLOYER_STATE = deployerField("state");
    private static final Field DEPLOYER_TIMER = deployerField("timer");

    private BoostPauses() {
    }

    public static boolean atSamplePoint(BlockEntity entity) {
        if (entity instanceof MechanicalPressBlockEntity press) {
            PressingBehaviour behaviour = press.getPressingBehaviour();
            return behaviour != null && behaviour.running && behaviour.runningTicks == PressingBehaviour.CYCLE / 2;
        }
        if (entity instanceof SpoutBlockEntity spout) {
            return spout.processingTicks == SPOUT_FILL_TICK;
        }
        if (entity instanceof DeployerBlockEntity deployer) {
            return deployerTransitionPending(deployer);
        }
        if (entity instanceof DepotBlockEntity depot) {
            return machineAbove(depot);
        }
        return false;
    }

    private static boolean machineAbove(DepotBlockEntity depot) {
        Level level = depot.getLevel();
        return level != null && BlockEntityBehaviour.get(level, depot.getBlockPos().above(2), BeltProcessingBehaviour.TYPE) != null;
    }

    private static boolean deployerTransitionPending(DeployerBlockEntity deployer) {
        if (DEPLOYER_STATE == null || DEPLOYER_TIMER == null) {
            return false;
        }
        try {
            Object state = DEPLOYER_STATE.get(deployer);
            return state instanceof Enum<?> value && "EXPANDING".equals(value.name()) && DEPLOYER_TIMER.getInt(deployer) <= 0;
        } catch (IllegalAccessException exception) {
            return false;
        }
    }

    private static Field deployerField(String name) {
        try {
            Field field = DeployerBlockEntity.class.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (ReflectiveOperationException | RuntimeException exception) {
            return null;
        }
    }
}
