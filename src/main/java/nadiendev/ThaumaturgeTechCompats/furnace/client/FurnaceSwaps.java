package nadiendev.ThaumaturgeTechCompats.furnace.client;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.device.client.TextureSwapBuffer;
import nadiendev.ThaumaturgeTechCompats.device.client.TextureSwapBuffer.Swap;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

final class FurnaceSwaps {
    private static final Map<DeviceTier, List<Swap>> CACHE = new EnumMap<>(DeviceTier.class);

    private FurnaceSwaps() {
    }

    static MultiBufferSource wrap(MultiBufferSource source, DeviceTier tier) {
        return new TextureSwapBuffer(source, CACHE.computeIfAbsent(tier, FurnaceSwaps::build));
    }

    private static List<Swap> build(DeviceTier tier) {
        String name = FurnaceIntegration.blockName(tier);
        List<Swap> swaps = new ArrayList<>();
        swaps.add(swap("advanced_alchemical_furnace.png", name + ".png"));
        swaps.add(swap("advanced_alchemical_furnace_on.png", name + "_on.png"));
        swaps.add(swap("advanced_alchemical_furnace_tank.png", name + "_tank.png"));
        swaps.add(swap("advanced_alchemical_furnace_tank_on.png", name + "_tank_on.png"));
        swaps.add(swap("metal_thaumium.png", name + "_trim.png"));
        return swaps;
    }

    private static Swap swap(String original, String replacement) {
        ResourceLocation from = ResourceLocation.fromNamespaceAndPath("thaumaturge", "textures/block/" + original);
        ResourceLocation to = ThaumaturgeTechCompats.id("textures/block/" + replacement);
        return new Swap(RenderType.entityCutout(from), RenderType.entityCutout(to), false);
    }
}
