package nadiendev.ThaumaturgeTechCompats.nodepower.client;

import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TTAspects;
import com.leclowndu93150.thaumaturge.client.render.blockentity.JarRenderer;
import com.leclowndu93150.thaumaturge.content.essentia.EssentiaTransportHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.nodepower.FluxJarBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class FluxJarRenderer implements BlockEntityRenderer<FluxJarBlockEntity, FluxJarRenderer.State> {
    private static final int FALLBACK_COLOR = 0x800080;
    private static final int OPAQUE = 0xFF000000;
    private static final int RGB_MASK = 0x00FFFFFF;
    private static final int FULL_BRIGHT = 15728880;
    private static final int UNIT_CAPACITY = 1;
    private static final float MIN_VISIBLE = 0.2F;

    private final SpriteGetter sprites;

    public FluxJarRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(FluxJarBlockEntity jar, State state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.CrumblingOverlay crumbling) {
        BlockEntityRenderer.super.extractRenderState(jar, state, partialTick, cameraPos, crumbling);
        state.ratio = jar.fillRatio();
        state.color = fluxColor(jar.getLevel());
    }

    @Override
    public void submit(State state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        if (state.ratio <= 0.0F) {
            return;
        }
        float visible = MIN_VISIBLE + (1.0F - MIN_VISIBLE) * state.ratio;
        TextureAtlasSprite sprite = sprites.get(JarRenderer.ANIMATED_GLOW_SPRITE);
        JarRenderer.submitFluid(visible, UNIT_CAPACITY, state.color, FULL_BRIGHT, sprite, pose, collector);
    }

    private static int fluxColor(Level level) {
        int rgb = FALLBACK_COLOR;
        if (level != null) {
            Holder<IAspect> aspect = EssentiaTransportHelper.resolve(level, TTAspects.VITIUM);
            if (aspect != null) {
                rgb = aspect.value().color();
            }
        }
        return OPAQUE | (rgb & RGB_MASK);
    }

    public static final class State extends BlockEntityRenderState {
        private float ratio;
        private int color;
    }
}
