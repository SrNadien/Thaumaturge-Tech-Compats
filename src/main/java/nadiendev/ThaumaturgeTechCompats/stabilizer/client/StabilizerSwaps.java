package nadiendev.ThaumaturgeTechCompats.stabilizer.client;

import com.leclowndu93150.thaumaturge.client.render.TTRenderTypes;
import nadiendev.ThaumaturgeTechCompats.device.client.TextureSwapBuffer;
import nadiendev.ThaumaturgeTechCompats.device.client.TextureSwapBuffer.Swap;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerTier;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

final class StabilizerSwaps {
    private static final ResourceLocation ORIGINAL_BASE = ResourceLocation.fromNamespaceAndPath("thaumaturge", "textures/block/node_stabilizer.png");
    private static final ResourceLocation ORIGINAL_OVERLAY = ResourceLocation.fromNamespaceAndPath("thaumaturge", "textures/block/node_stabilizer_over.png");
    private static final Map<StabilizerTier, List<Swap>> CACHE = new EnumMap<>(StabilizerTier.class);

    private StabilizerSwaps() {
    }

    static MultiBufferSource wrap(MultiBufferSource source, StabilizerTier tier) {
        return new TextureSwapBuffer(source, CACHE.computeIfAbsent(tier, StabilizerSwaps::build));
    }

    private static List<Swap> build(StabilizerTier tier) {
        return List.of(
                new Swap(RenderType.entityCutout(ORIGINAL_BASE), RenderType.entityCutout(tier.baseTexture()), false),
                new Swap(TTRenderTypes.entityAdditiveEmissive(ORIGINAL_OVERLAY), TTRenderTypes.entityAdditiveEmissive(tier.overlayTexture()), true));
    }
}
