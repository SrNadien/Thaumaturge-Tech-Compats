package nadiendev.ThaumaturgeTechCompats.nodepower.client;

import com.leclowndu93150.thaumaturge.client.render.TTShaders;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;

final class RiftJarRenderTypes {
    private static final int BUFFER_SIZE = 1536;
    private static final String PREFIX = ThaumaturgeTechCompats.MOD_ID + "_rift_jar_";

    static final RenderType ENDER_GLOW = ender("ender_glow", RenderStateShard.ADDITIVE_TRANSPARENCY, RenderStateShard.COLOR_WRITE);
    static final RenderType ENDER_CORE = ender("ender_core", RenderStateShard.NO_TRANSPARENCY, RenderStateShard.COLOR_DEPTH_WRITE);
    static final RenderType COLOR_GLOW = colour("color_glow", RenderStateShard.ADDITIVE_TRANSPARENCY, RenderStateShard.COLOR_WRITE);
    static final RenderType COLOR_CORE = colour("color_core", RenderStateShard.NO_TRANSPARENCY, RenderStateShard.COLOR_DEPTH_WRITE);

    private RiftJarRenderTypes() {
    }

    private static RenderType ender(String name, RenderStateShard.TransparencyStateShard transparency, RenderStateShard.WriteMaskStateShard mask) {
        return RenderType.create(
                PREFIX + name,
                DefaultVertexFormat.POSITION,
                VertexFormat.Mode.QUADS,
                BUFFER_SIZE,
                false,
                false,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(TTShaders::ender))
                        .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                                .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                                .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                                .build())
                        .setTransparencyState(transparency)
                        .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                        .setWriteMaskState(mask)
                        .setCullState(RenderStateShard.NO_CULL)
                        .createCompositeState(false));
    }

    private static RenderType colour(String name, RenderStateShard.TransparencyStateShard transparency, RenderStateShard.WriteMaskStateShard mask) {
        return RenderType.create(
                PREFIX + name,
                DefaultVertexFormat.POSITION_COLOR,
                VertexFormat.Mode.QUADS,
                BUFFER_SIZE,
                false,
                false,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorShader))
                        .setTransparencyState(transparency)
                        .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                        .setWriteMaskState(mask)
                        .setCullState(RenderStateShard.NO_CULL)
                        .createCompositeState(false));
    }
}
