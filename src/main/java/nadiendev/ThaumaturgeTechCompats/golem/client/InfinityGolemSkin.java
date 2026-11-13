package nadiendev.ThaumaturgeTechCompats.golem.client;

import com.leclowndu93150.thaumaturge.client.golem.GolemSkins;
import com.mojang.blaze3d.platform.NativeImage;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.golem.GolemMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.io.InputStream;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, value = Dist.CLIENT)
public final class InfinityGolemSkin {
    private static final int PERIOD_TICKS = 2;

    private static int width;
    private static int height;
    private static int[] alpha;
    private static float[] shade;
    private static float minShade;
    private static float maxShade;
    private static boolean failed;
    private static long tick;

    private InfinityGolemSkin() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (failed || minecraft.level == null || minecraft.isPaused()) {
            return;
        }
        tick++;
        if (tick % PERIOD_TICKS != 0) {
            return;
        }
        try {
            ResourceLocation material = GolemMaterials.INFINITY.get().texture();
            ResourceLocation skin = GolemSkins.forMaterial(material);
            if (skin.equals(GolemSkins.COPPER)) {
                return;
            }
            AbstractTexture texture = minecraft.getTextureManager().getTexture(skin);
            if (!(texture instanceof DynamicTexture dynamic) || dynamic.getPixels() == null) {
                return;
            }
            if (alpha == null && !loadBase(minecraft)) {
                return;
            }
            NativeImage pixels = dynamic.getPixels();
            if (pixels.getWidth() != width || pixels.getHeight() != height) {
                return;
            }
            float time = tick * 3.0F;
            float span = Math.max(0.001F, maxShade - minShade);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int index = y * width + x;
                    if (alpha[index] == 0) {
                        continue;
                    }
                    float t = Mth.clamp((shade[index] - minShade) / span, 0.0F, 1.0F);
                    float hue = (x * 5.0F + y * 3.0F + time) % 360.0F;
                    int rgb = Mth.hsvToRgb(hue / 360.0F, 0.5F, 0.3F + 0.7F * t);
                    int argb = FastColor.ARGB32.color(alpha[index], FastColor.ARGB32.red(rgb), FastColor.ARGB32.green(rgb), FastColor.ARGB32.blue(rgb));
                    pixels.setPixelRGBA(x, y, FastColor.ABGR32.fromArgb32(argb));
                }
            }
            dynamic.upload();
        } catch (Exception exception) {
            failed = true;
        }
    }

    private static boolean loadBase(Minecraft minecraft) {
        try (InputStream stream = minecraft.getResourceManager().open(GolemSkins.COPPER);
             NativeImage image = NativeImage.read(stream)) {
            width = image.getWidth();
            height = image.getHeight();
            alpha = new int[width * height];
            shade = new float[width * height];
            minShade = Float.MAX_VALUE;
            maxShade = -Float.MAX_VALUE;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getPixelRGBA(x, y);
                    int index = y * width + x;
                    alpha[index] = FastColor.ABGR32.alpha(pixel);
                    if (alpha[index] == 0) {
                        continue;
                    }
                    float value = 0.299F * FastColor.ABGR32.red(pixel) + 0.587F * FastColor.ABGR32.green(pixel) + 0.114F * FastColor.ABGR32.blue(pixel);
                    shade[index] = value;
                    minShade = Math.min(minShade, value);
                    maxShade = Math.max(maxShade, value);
                }
            }
            return true;
        } catch (Exception exception) {
            failed = true;
            return false;
        }
    }
}
