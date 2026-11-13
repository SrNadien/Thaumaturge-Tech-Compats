package nadiendev.ThaumaturgeTechCompats.transducer.client;

import com.leclowndu93150.thaumaturge.client.render.blockentity.NodeTransducerRenderer;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNodeTransducer;
import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public final class TieredTransducerRenderer implements BlockEntityRenderer<BlockEntityNodeTransducer> {
    private final NodeTransducerRenderer delegate;

    public TieredTransducerRenderer(BlockEntityRendererProvider.Context context) {
        this.delegate = new NodeTransducerRenderer(context);
    }

    @Override
    public void render(BlockEntityNodeTransducer transducer, float partialTick, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
        DeviceTier tier = TransducerIntegration.tierOf(transducer.getBlockState().getBlock());
        MultiBufferSource source = tier == null ? buffer : TransducerSwaps.wrap(buffer, tier);
        delegate.render(transducer, partialTick, pose, source, light, overlay);
    }
}
