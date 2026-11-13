package nadiendev.ThaumaturgeTechCompats.stabilizer.client;

import com.leclowndu93150.thaumaturge.client.model.NodeStabilizerItemSpecialRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerTier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class TieredStabilizerItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final NodeStabilizerItemSpecialRenderer delegate = new NodeStabilizerItemSpecialRenderer(true);

    public TieredStabilizerItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
        StabilizerTier tier = stack.getItem() instanceof BlockItem blockItem ? StabilizerIntegration.tierOf(blockItem.getBlock()) : null;
        MultiBufferSource source = tier == null ? buffer : StabilizerSwaps.wrap(buffer, tier);
        delegate.renderByItem(stack, context, pose, source, light, overlay);
    }
}
