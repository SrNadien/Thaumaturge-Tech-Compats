package nadiendev.ThaumaturgeTechCompats.planetary.pearl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PearlColors;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PearlConfig;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PlanetBody;
import nadiendev.ThaumaturgeTechCompats.planetary.pearl.PlanetEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.LightCoordsUtil;

public class PlanetRenderer extends EntityRenderer<PlanetEntity, PlanetRenderer.State> {
    private static final int SEGMENTS = 16;
    private static final int RINGS = 10;
    private static final float RADIUS = 0.5F;

    public PlanetRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(PlanetEntity entity, State state, float partial) {
        super.extractRenderState(entity, state, partial);
        PlanetBody body = entity.body();
        PlanetaryTier tier = entity.tier();
        state.fade = entity.fade(partial);
        state.texture = body.texture();
        state.size = body.size() * PearlConfig.planetScale() * state.fade;
        state.tint = PearlColors.mix(0xFFFFFF, PearlColors.planetColor(tier, entity.index()), PearlConfig.tintStrength());
        state.rotation = (entity.tickCount + partial) * body.spin();
        state.height = entity.getBbHeight() * 0.5F;
    }

    @Override
    public void submit(State state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        if (state.fade <= 0.0F) {
            return;
        }
        pose.pushPose();
        pose.translate(0.0F, state.height, 0.0F);
        pose.mulPose(Axis.YP.rotationDegrees(state.rotation));
        pose.scale(state.size, state.size, state.size);
        int tint = state.tint;
        collector.submitCustomGeometry(pose, RenderTypes.entityCutout(state.texture),
                (last, consumer) -> sphere(last, consumer, (tint >> 16) & 255, (tint >> 8) & 255, tint & 255));
        pose.popPose();
        super.submit(state, pose, collector, camera);
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
                .setLight(LightCoordsUtil.FULL_BRIGHT)
                .setNormal(pose, nx, ny, nz);
    }

    public static final class State extends EntityRenderState {
        private float fade;
        private Identifier texture;
        private float size;
        private int tint;
        private float rotation;
        private float height;
    }
}
