package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor.client;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor.ThaummotorBlockEntity;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class ThaummotorRenderer extends KineticBlockEntityRenderer<ThaummotorBlockEntity> {
    public ThaummotorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(ThaummotorBlockEntity blockEntity, BlockState state) {
        return CachedBuffers.partialFacing(ThaummotorPartials.SHAFT_HALF, state);
    }
}
