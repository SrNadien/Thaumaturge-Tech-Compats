package nadiendev.ThaumaturgeTechCompats.transducer.client;

import com.leclowndu93150.thaumaturge.client.model.NodeStabilizerItemSpecialRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class TieredTransducerItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final NodeStabilizerItemSpecialRenderer delegate = new NodeStabilizerItemSpecialRenderer(false, true);

    public TieredTransducerItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
        DeviceTier tier = stack.getItem() instanceof BlockItem blockItem ? TransducerIntegration.tierOf(blockItem.getBlock()) : null;
        MultiBufferSource source = tier == null ? buffer : TransducerSwaps.wrap(buffer, tier);
        delegate.renderByItem(stack, context, pose, source, light, overlay);
    }
}
