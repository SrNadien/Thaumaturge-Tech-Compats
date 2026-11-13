package nadiendev.ThaumaturgeTechCompats.device.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

import java.util.List;

public final class TextureSwapBuffer implements MultiBufferSource {
    private final MultiBufferSource source;
    private final List<Swap> swaps;

    public TextureSwapBuffer(MultiBufferSource source, List<Swap> swaps) {
        this.source = source;
        this.swaps = swaps;
    }

    @Override
    public VertexConsumer getBuffer(RenderType type) {
        for (Swap swap : swaps) {
            if (type.equals(swap.from())) {
                VertexConsumer consumer = source.getBuffer(swap.to());
                return swap.flatTint() ? new FlatTintConsumer(consumer) : consumer;
            }
        }
        return source.getBuffer(type);
    }

    public record Swap(RenderType from, RenderType to, boolean flatTint) {
    }
}
