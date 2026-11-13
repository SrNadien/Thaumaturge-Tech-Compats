package nadiendev.ThaumaturgeTechCompats.stabilizer.client;

import com.leclowndu93150.thaumaturge.client.render.blockentity.NodeStabilizerRenderer;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNodeStabilizer;
import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerTier;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;

public final class TieredStabilizerRenderer implements BlockEntityRenderer<BlockEntityNodeStabilizer> {
    private final NodeStabilizerRenderer delegate;

    public TieredStabilizerRenderer(BlockEntityRendererProvider.Context context) {
        this.delegate = new NodeStabilizerRenderer(context);
    }

    @Override
    public void render(BlockEntityNodeStabilizer stabilizer, float partialTick, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
        StabilizerTier tier = StabilizerIntegration.tierOf(stabilizer.getBlockState().getBlock());
        MultiBufferSource source = tier == null ? buffer : StabilizerSwaps.wrap(buffer, tier);
        delegate.render(stabilizer, partialTick, pose, source, light, overlay);
    }

    @Override
    public boolean shouldRenderOffScreen(BlockEntityNodeStabilizer stabilizer) {
        return delegate.shouldRenderOffScreen(stabilizer);
    }

    @Override
    public AABB getRenderBoundingBox(BlockEntityNodeStabilizer stabilizer) {
        return delegate.getRenderBoundingBox(stabilizer);
    }
}
