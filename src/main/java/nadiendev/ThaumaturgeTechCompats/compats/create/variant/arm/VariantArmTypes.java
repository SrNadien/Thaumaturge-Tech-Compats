package nadiendev.ThaumaturgeTechCompats.compats.create.variant.arm;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CasingVariant;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.VariantKind;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class VariantArmTypes {
    public static final DeferredRegister<ArmInteractionPointType> TYPES = DeferredRegister.create(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE, ThaumaturgeTechCompats.MOD_ID);

    static {
        TYPES.register("variant_depot", DepotType::new);
        TYPES.register("variant_saw", SawType::new);
        TYPES.register("variant_deployer", DeployerType::new);
        TYPES.register("variant_millstone", MillstoneType::new);
    }

    private VariantArmTypes() {
    }

    private static boolean is(BlockState state, VariantKind kind) {
        return state.getBlock() instanceof CasingVariant variant && variant.kind() == kind;
    }

    public static class DepotType extends AllArmInteractionPointTypes.DepotType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return is(state, VariantKind.DEPOT);
        }
    }

    public static class SawType extends AllArmInteractionPointTypes.SawType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return is(state, VariantKind.SAW);
        }
    }

    public static class DeployerType extends AllArmInteractionPointTypes.DeployerType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return is(state, VariantKind.DEPLOYER);
        }
    }

    public static class MillstoneType extends AllArmInteractionPointTypes.MillstoneType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return is(state, VariantKind.MILLSTONE);
        }
    }
}
