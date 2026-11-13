package nadiendev.ThaumaturgeTechCompats.wandtier.client;

import com.leclowndu93150.thaumaturge.api.wands.WandCap;
import com.leclowndu93150.thaumaturge.api.wands.render.IWandRenderer;
import com.leclowndu93150.thaumaturge.api.wands.render.WandRenderContext;
import com.leclowndu93150.thaumaturge.api.wands.render.WandRenderers;
import com.leclowndu93150.thaumaturge.client.render.BoxGeometry;
import com.leclowndu93150.thaumaturge.client.render.TTFlatRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.registry.ModWandParts;
import nadiendev.ThaumaturgeTechCompats.wandtier.WandTierParts;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class WandCapGlowRenderer implements IWandRenderer {
    private static final int PRIORITY = 100;
    private static final float PX = 0.0625F;
    private static final float STAFF_SHIFT = 0.2F;
    private static final float STAFF_OUTER_XZ = 1.2F;
    private static final float STAFF_OUTER_Y = 1.1F;
    private static final int TEXTURE_SIZE = 32;
    private static final int WHITE = -1;

    private record Placement(float offsetY, float scaleXz, float scaleY) {
    }

    private WandCapGlowRenderer() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> WandRenderers.register(PRIORITY, WandCapGlowRenderer::glows, new WandCapGlowRenderer()));
    }

    private static boolean glows(WandRenderContext context) {
        WandCap cap = context.cap();
        return cap == ModWandParts.INFINITY_CAP.get()
                || cap == ModWandParts.MATRIX_CAP.get()
                || cap == WandTierParts.NEUTRONIUM_CAP.get();
    }

    private static List<Placement> placements(boolean staff, boolean sceptre) {
        List<Placement> result = new ArrayList<>();
        if (sceptre) {
            result.add(new Placement(-0.08125F, 1.75F, 1.8F));
            result.add(new Placement(0.175F, 1.55F, 0.45F));
        } else {
            result.add(new Placement(-0.0625F, 1.3F, 1.5F));
        }
        if (staff) {
            result.add(new Placement(0.25F, 1.1F, 0.5F));
            result.add(new Placement(1.8522726F, 1.3F, 1.5F));
        } else {
            result.add(new Placement(1.0F, 1.3F, 1.5F));
        }
        return result;
    }

    @Override
    public void render(WandRenderContext context) {
        context.renderDefault();
        boolean staff = context.rod().staff();
        ResourceLocation texture = context.cap().texture();
        RenderType type = context.firstPersonHand()
                ? RenderType.entityCutoutNoCull(texture)
                : TTFlatRenderTypes.entityCutoutFlat(texture);
        VertexConsumer consumer = context.buffers().getBuffer(type);
        PoseStack pose = context.poseStack();
        pose.pushPose();
        if (staff) {
            pose.translate(0.0F, STAFF_SHIFT, 0.0F);
        }
        float outerXz = staff ? STAFF_OUTER_XZ : 1.0F;
        float outerY = staff ? STAFF_OUTER_Y : 1.0F;
        pose.scale(outerXz, outerY, outerXz);
        for (Placement placement : placements(staff, context.sceptre())) {
            pose.pushPose();
            pose.translate(0.0F, placement.offsetY(), 0.0F);
            pose.scale(placement.scaleXz(), placement.scaleY(), placement.scaleXz());
            pose.translate(0.0F, PX, 0.0F);
            BoxGeometry.box(pose.last(), consumer,
                    -PX, -PX, -PX, PX, PX, PX,
                    0, 0, 2, 2, 2,
                    TEXTURE_SIZE, TEXTURE_SIZE,
                    WHITE, LightTexture.FULL_BRIGHT,
                    true);
            pose.popPose();
        }
        pose.popPose();
    }
}
