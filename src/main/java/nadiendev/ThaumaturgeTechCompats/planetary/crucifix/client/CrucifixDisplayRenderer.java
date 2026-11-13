package nadiendev.ThaumaturgeTechCompats.planetary.crucifix.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixDisplayEntity;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixRegistry;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixTiming;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.EnumMap;
import java.util.Map;

public class CrucifixDisplayRenderer extends EntityRenderer<CrucifixDisplayEntity> {
    private final ItemRenderer itemRenderer;
    private final Map<PlanetaryTier, ItemStack> stacks = new EnumMap<>(PlanetaryTier.class);

    public CrucifixDisplayRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(CrucifixDisplayEntity entity, float entityYaw, float partialTick, PoseStack pose,
                       MultiBufferSource buffers, int packedLight) {
        PlanetaryTier tier = entity.tier();
        if (!CrucifixRegistry.has(tier)) {
            return;
        }
        ItemStack stack = stacks.computeIfAbsent(tier, key -> new ItemStack(CrucifixRegistry.item(key)));
        float t = entity.tickCount + partialTick;
        float rate = CrucifixTiming.TREMBLE_RATE;
        CrucifixClient.DisplayFx fx = CrucifixClient.displayFx(entity.getId(), partialTick);
        float extra = fx.tremble() - 1.0F;
        pose.pushPose();
        pose.translate(0.0F, CrucifixTiming.DISPLAY_SIZE * 0.5F, 0.0F);
        if (extra > 0.0F) {
            pose.translate(Mth.sin(t * 11.0F) * 0.008F * extra, Mth.cos(t * 9.0F) * 0.005F * extra, Mth.cos(t * 13.0F) * 0.008F * extra);
        }
        pose.mulPose(Axis.YP.rotationDegrees(CrucifixTiming.spin(t)));
        pose.mulPose(Axis.ZP.rotationDegrees(Mth.sin(t * 1.9F * rate) * 3.0F + Mth.sin(t * 7.3F) * 1.5F * extra));
        pose.mulPose(Axis.XP.rotationDegrees(Mth.cos(t * 2.3F * rate) * 2.0F + Mth.cos(t * 8.1F) * 1.2F * extra));
        float scale = CrucifixTiming.DISPLAY_SCALE * fx.swell();
        pose.scale(scale, scale, scale);
        int light = Math.round(15.0F * fx.glow());
        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LightTexture.pack(light, light), OverlayTexture.NO_OVERLAY,
                pose, buffers, entity.level(), entity.getId());
        pose.popPose();
        super.render(entity, entityYaw, partialTick, pose, buffers, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(CrucifixDisplayEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
