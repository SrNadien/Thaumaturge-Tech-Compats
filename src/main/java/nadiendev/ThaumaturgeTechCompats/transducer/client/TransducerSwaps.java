package nadiendev.ThaumaturgeTechCompats.transducer.client;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.device.client.TextureSwapBuffer;
import nadiendev.ThaumaturgeTechCompats.device.client.TextureSwapBuffer.Swap;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

final class TransducerSwaps {
    private static final ResourceLocation ORIGINAL_BASE = ResourceLocation.fromNamespaceAndPath("thaumaturge", "textures/block/node_converter.png");
    private static final Map<DeviceTier, List<Swap>> CACHE = new EnumMap<>(DeviceTier.class);

    private TransducerSwaps() {
    }

    static MultiBufferSource wrap(MultiBufferSource source, DeviceTier tier) {
        return new TextureSwapBuffer(source, CACHE.computeIfAbsent(tier, TransducerSwaps::build));
    }

    private static List<Swap> build(DeviceTier tier) {
        ResourceLocation texture = ThaumaturgeTechCompats.id("textures/block/" + TransducerIntegration.blockName(tier) + ".png");
        return List.of(new Swap(RenderType.entityCutout(ORIGINAL_BASE), RenderType.entityCutout(texture), false));
    }
}
