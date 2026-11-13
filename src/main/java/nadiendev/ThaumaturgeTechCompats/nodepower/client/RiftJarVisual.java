package nadiendev.ThaumaturgeTechCompats.nodepower.client;

import com.leclowndu93150.thaumaturge.client.effect.LateWorldRenderQueue;
import com.leclowndu93150.thaumaturge.compat.iris.IrisCompat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

final class RiftJarVisual {
    static final long ITEM_SEED = 4207L;

    private static final float[] GLOW_SCALES = {2.2F, 1.75F, 1.4F};
    private static final float[] GLOW_ALPHAS = {0.10F, 0.16F, 0.26F};
    private static final int GLOW_RED = 150;
    private static final int GLOW_GREEN = 60;
    private static final int GLOW_BLUE = 235;
    private static final int CORE_COLOR = FastColor.ARGB32.color(255, 14, 4, 28);
    private static final float VISIBLE_SIZE = 0.001F;
    private static final float TICKS_PER_SECOND = 20.0F;

    private RiftJarVisual() {
    }

    static float sizeFor(float fraction) {
        float spark = RiftJarClientConfig.emptySparkSize();
        float grown = (float) Math.pow(Mth.clamp(fraction, 0.0F, 1.0F), RiftJarClientConfig.growthCurve());
        return RiftJarClientConfig.maxSize() * (spark + (1.0F - spark) * grown);
    }

    static void drawWorld(PoseStack pose, MultiBufferSource buffers, float size, float time, long seed) {
        if (size < VISIBLE_SIZE) {
            return;
        }
        RiftJarTube tube = tube(seed, time);
        pose.pushPose();
        place(pose, size, time);
        if (RiftJarClientConfig.glowIntensity() > 0.0F) {
            for (float scale : GLOW_SCALES) {
                LateWorldRenderQueue.drawOwnShader(pose, buffers, RiftJarRenderTypes.ENDER_GLOW,
                        (last, consumer) -> tube.write(consumer, last.pose(), scale, false, 0));
            }
        }
        LateWorldRenderQueue.drawOwnShader(pose, buffers, RiftJarRenderTypes.ENDER_CORE,
                (last, consumer) -> tube.write(consumer, last.pose(), 1.0F, false, 0));
        pose.popPose();
    }

    static void drawItem(PoseStack pose, MultiBufferSource buffers, ItemDisplayContext context, float size, float time) {
        if (size < VISIBLE_SIZE) {
            return;
        }
        RiftJarTube tube = tube(ITEM_SEED, time);
        boolean ender = RiftJarClientConfig.itemUseEnderShader() && context != ItemDisplayContext.GUI && !IrisCompat.shadersActive();
        pose.pushPose();
        place(pose, size, time);
        float intensity = RiftJarClientConfig.glowIntensity();
        if (intensity > 0.0F) {
            for (int pass = 0; pass < GLOW_SCALES.length; pass++) {
                if (ender) {
                    tube.write(buffers.getBuffer(RiftJarRenderTypes.ENDER_GLOW), pose.last().pose(), GLOW_SCALES[pass], false, 0);
                } else {
                    int alpha = Mth.clamp((int) (GLOW_ALPHAS[pass] * intensity * 255.0F), 0, 255);
                    int color = FastColor.ARGB32.color(alpha, GLOW_RED, GLOW_GREEN, GLOW_BLUE);
                    tube.write(buffers.getBuffer(RiftJarRenderTypes.COLOR_GLOW), pose.last().pose(), GLOW_SCALES[pass], true, color);
                }
            }
        }
        if (ender) {
            tube.write(buffers.getBuffer(RiftJarRenderTypes.ENDER_CORE), pose.last().pose(), 1.0F, false, 0);
        } else {
            tube.write(buffers.getBuffer(RiftJarRenderTypes.COLOR_CORE), pose.last().pose(), 1.0F, true, CORE_COLOR);
        }
        pose.popPose();
    }

    private static RiftJarTube tube(long seed, float time) {
        return RiftJarTube.build(RiftJarOutline.of(seed), time, RiftJarClientConfig.sway(), RiftJarClientConfig.throb(), RiftJarClientConfig.thickness());
    }

    private static void place(PoseStack pose, float size, float time) {
        float bob = Mth.sin(time * RiftJarClientConfig.bobSpeed()) * RiftJarClientConfig.bobAmplitude();
        pose.translate(0.5F, RiftJarClientConfig.height() + bob, 0.5F);
        pose.mulPose(Axis.YP.rotationDegrees(time * RiftJarClientConfig.spinDegreesPerSecond() / TICKS_PER_SECOND));
        pose.mulPose(Axis.ZP.rotationDegrees(RiftJarClientConfig.tiltDegrees()));
        pose.scale(size, size, size);
    }
}
