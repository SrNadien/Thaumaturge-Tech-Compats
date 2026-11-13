package nadiendev.ThaumaturgeTechCompats.furnace.client;

import com.leclowndu93150.thaumaturge.client.render.blockentity.AdvancedAlchemicalFurnaceRenderer;
import com.leclowndu93150.thaumaturge.content.essentia.advancedfurnace.BlockEntityAdvancedAlchemicalFurnace;
import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public final class TieredFurnaceRenderer implements BlockEntityRenderer<BlockEntityAdvancedAlchemicalFurnace> {
    private final AdvancedAlchemicalFurnaceRenderer delegate;

    public TieredFurnaceRenderer(BlockEntityRendererProvider.Context context) {
        this.delegate = new AdvancedAlchemicalFurnaceRenderer(context);
    }

    @Override
    public void render(BlockEntityAdvancedAlchemicalFurnace furnace, float partialTick, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
        DeviceTier tier = FurnaceIntegration.tierOf(furnace.getBlockState().getBlock());
        MultiBufferSource source = tier == null ? buffer : FurnaceSwaps.wrap(buffer, tier);
        delegate.render(furnace, partialTick, pose, source, light, overlay);
    }
}
