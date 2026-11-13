package nadiendev.ThaumaturgeTechCompats.nodepower.client;

import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodePowerRegistry;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class RiftJarItemRenderer extends BlockEntityWithoutLevelRenderer {
    private static final long TIME_WRAP = 1_000_000L;
    private static final float MILLIS_PER_TICK = 50.0F;

    public RiftJarItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(NodePowerRegistry.RIFT_JAR.get().defaultBlockState(), pose, buffers, light, overlay);
        if (!RiftJarClientConfig.enabled()) {
            return;
        }
        float time = (Util.getMillis() % TIME_WRAP) / MILLIS_PER_TICK;
        RiftJarVisual.drawItem(pose, buffers, context, RiftJarVisual.sizeFor(RiftJarClientConfig.itemFill()), time);
    }
}
