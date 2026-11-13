package nadiendev.ThaumaturgeTechCompats.compats.create.variant;

import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.api.contraption.BlockMovementChecks;
import com.simibubi.create.api.contraption.ContraptionMovementSetting;
import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorageType;
import com.simibubi.create.api.contraption.transformable.MovedBlockTransformerRegistries;
import com.simibubi.create.api.packager.InventoryIdentifier;
import com.simibubi.create.api.packager.unpacking.UnpackingHandler;
import com.simibubi.create.api.registry.SimpleRegistry;
import com.simibubi.create.api.stress.BlockStressValues;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public final class VariantCompat {
    private VariantCompat() {
    }

    public static void apply() {
        for (VariantKind kind : VariantKind.values()) {
            Block base = CreateVariants.baseBlock(kind);
            for (CasingTier tier : CasingTier.values()) {
                Block variant = CreateVariants.block(kind, tier).get();
                BlockStressValues.IMPACTS.register(variant, () -> BlockStressValues.getImpact(base));
                copy(MovementBehaviour.REGISTRY, base, variant);
                copy(MovingInteractionBehaviour.REGISTRY, base, variant);
                copy(ContraptionMovementSetting.REGISTRY, base, variant);
                copy(MountedItemStorageType.REGISTRY, base, variant);
                copy(MountedFluidStorageType.REGISTRY, base, variant);
                copy(MovedBlockTransformerRegistries.BLOCK_TRANSFORMERS, base, variant);
                copy(UnpackingHandler.REGISTRY, base, variant);
                copy(InventoryIdentifier.REGISTRY, base, variant);
            }
        }
        BlockMovementChecks.registerNotSupportiveCheck((state, direction) -> {
            if (state.getBlock() instanceof CasingVariant variant
                    && (variant.kind() == VariantKind.DRILL || variant.kind() == VariantKind.SAW)
                    && state.hasProperty(BlockStateProperties.FACING)
                    && state.getValue(BlockStateProperties.FACING) == direction) {
                return BlockMovementChecks.CheckResult.SUCCESS;
            }
            return BlockMovementChecks.CheckResult.PASS;
        });
    }

    private static <V> void copy(SimpleRegistry<Block, V> registry, Block base, Block variant) {
        V value = registry.get(base);
        if (value != null) {
            registry.register(variant, value);
        }
    }
}
