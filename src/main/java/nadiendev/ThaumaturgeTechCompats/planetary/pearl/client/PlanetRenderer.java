package nadiendev.ThaumaturgeTechCompats.planetary.pearl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PearlColors;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PearlConfig;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PlanetBody;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PlanetEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PlanetRenderer extends EntityRenderer<PlanetEntity> {
    private static final int SEGMENTS = 16;
    private static final int RINGS = 10;
    private static final float RADIUS = 0.5F;

    public PlanetRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(PlanetEntity entity) {
        return entity.body().texture();
    }

    @Override
    public void render(PlanetEntity entity, float yaw, float partial, PoseStack pose, MultiBufferSource buffers, int light) {
        float fade = entity.fade(partial);
        if (fade <= 0.0F) {
            return;
        }
        PlanetBody body = entity.body();
        PlanetaryTier tier = entity.tier();
        float size = body.size() * PearlConfig.planetScale() * fade;
        int tint = PearlColors.mix(0xFFFFFF, PearlColors.planetColor(tier, entity.index()), PearlConfig.tintStrength());
        pose.pushPose();
        pose.translate(0.0F, entity.getBbHeight() * 0.5F, 0.0F);
        pose.mulPose(Axis.YP.rotationDegrees((entity.tickCount + partial) * body.spin()));
        pose.scale(size, size, size);
        VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
        sphere(pose.last(), consumer, (tint >> 16) & 255, (tint >> 8) & 255, tint & 255);
        pose.popPose();
        super.render(entity, yaw, partial, pose, buffers, light);
    }

    private static void sphere(PoseStack.Pose pose, VertexConsumer consumer, int r, int g, int b) {
        for (int ring = 0; ring < RINGS; ring++) {
            float v0 = (float) ring / RINGS;
            float v1 = (float) (ring + 1) / RINGS;
            for (int segment = 0; segment < SEGMENTS; segment++) {
                float u0 = (float) segment / SEGMENTS;
                float u1 = (float) (segment + 1) / SEGMENTS;
                vertex(pose, consumer, r, g, b, u0, v0);
                vertex(pose, consumer, r, g, b, u0, v1);
                vertex(pose, consumer, r, g, b, u1, v1);
                vertex(pose, consumer, r, g, b, u1, v0);
            }
        }
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, int r, int g, int b, float u, float v) {
        double polar = Math.PI * v;
        double azimuth = 2.0D * Math.PI * u;
        float nx = (float) (Math.sin(polar) * Math.cos(azimuth));
        float ny = (float) Math.cos(polar);
        float nz = (float) (Math.sin(polar) * Math.sin(azimuth));
        consumer.addVertex(pose, nx * RADIUS, ny * RADIUS, nz * RADIUS)
                .setColor(r, g, b, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(pose, nx, ny, nz);
    }
}
