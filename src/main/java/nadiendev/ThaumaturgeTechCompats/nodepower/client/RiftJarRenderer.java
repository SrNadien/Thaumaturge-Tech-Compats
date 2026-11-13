package nadiendev.ThaumaturgeTechCompats.nodepower.client;

import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.nodepower.RiftJarBlock;
import nadiendev.ThaumaturgeTechCompats.nodepower.RiftJarBlockEntity;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.WeakHashMap;

public final class RiftJarRenderer implements BlockEntityRenderer<RiftJarBlockEntity> {
    private static final long TIME_WRAP = 100_000L;
    private static final float MAX_FRAME_SECONDS = 0.25F;
    private static final float MILLIS_PER_SECOND = 1000.0F;

    private final Map<RiftJarBlockEntity, Animation> animations = new WeakHashMap<>();

    public RiftJarRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(RiftJarBlockEntity jar, float partialTick, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        if (!RiftJarClientConfig.enabled()) {
            return;
        }
        BlockState state = jar.getBlockState();
        int fill = state.hasProperty(RiftJarBlock.FILL) ? state.getValue(RiftJarBlock.FILL) : 0;
        float shown = animate(jar, fill / (float) RiftJarBlock.MAX_FILL);
        float size = RiftJarVisual.sizeFor(shown);
        Level level = jar.getLevel();
        float time = (level == null ? 0L : level.getGameTime() % TIME_WRAP) + partialTick;
        RiftJarVisual.drawWorld(pose, buffers, size, time, Mth.getSeed(jar.getBlockPos()));
    }

    private float animate(RiftJarBlockEntity jar, float target) {
        long now = Util.getMillis();
        Animation animation = animations.get(jar);
        if (animation == null) {
            animation = new Animation(target, now);
            animations.put(jar, animation);
            return target;
        }
        float seconds = Mth.clamp((now - animation.millis) / MILLIS_PER_SECOND, 0.0F, MAX_FRAME_SECONDS);
        float blend = 1.0F - (float) Math.exp(-seconds * RiftJarClientConfig.growSpeed());
        animation.value += (target - animation.value) * blend;
        animation.millis = now;
        return animation.value;
    }

    private static final class Animation {
        private float value;
        private long millis;

        private Animation(float value, long millis) {
            this.value = value;
            this.millis = millis;
        }
    }
}
