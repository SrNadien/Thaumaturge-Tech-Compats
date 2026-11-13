package nadiendev.ThaumaturgeTechCompats.nodepower.client;

import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TTAspects;
import com.leclowndu93150.thaumaturge.client.render.blockentity.JarRenderer;
import com.leclowndu93150.thaumaturge.content.essentia.EssentiaTransportHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import nadiendev.ThaumaturgeTechCompats.nodepower.FluxJarBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;

public final class FluxJarRenderer implements BlockEntityRenderer<FluxJarBlockEntity> {
    private static final int FALLBACK_COLOR = 0x800080;
    private static final int OPAQUE = 0xFF000000;
    private static final int RGB_MASK = 0x00FFFFFF;
    private static final int FULL_BRIGHT = 15728880;
    private static final int UNIT_CAPACITY = 1;
    private static final float MIN_VISIBLE = 0.2F;

    public FluxJarRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(FluxJarBlockEntity jar, float partialTick, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        float ratio = jar.fillRatio();
        if (ratio <= 0.0F) {
            return;
        }
        float visible = MIN_VISIBLE + (1.0F - MIN_VISIBLE) * ratio;
        JarRenderer.submitFluid(visible, UNIT_CAPACITY, fluxColor(jar.getLevel()), FULL_BRIGHT,
                JarRenderer.ANIMATED_GLOW_MATERIAL.sprite(), pose, buffers);
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
}
