package nadiendev.ThaumaturgeTechCompats.furnace.client;

import com.leclowndu93150.thaumaturge.client.model.AdvancedAlchemicalFurnaceItemSpecialRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class TieredFurnaceItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final AdvancedAlchemicalFurnaceItemSpecialRenderer delegate = new AdvancedAlchemicalFurnaceItemSpecialRenderer();

    public TieredFurnaceItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
        DeviceTier tier = stack.getItem() instanceof BlockItem blockItem ? FurnaceIntegration.tierOf(blockItem.getBlock()) : null;
        MultiBufferSource source = tier == null ? buffer : FurnaceSwaps.wrap(buffer, tier);
        delegate.renderByItem(stack, context, pose, source, light, overlay);
    }
}
