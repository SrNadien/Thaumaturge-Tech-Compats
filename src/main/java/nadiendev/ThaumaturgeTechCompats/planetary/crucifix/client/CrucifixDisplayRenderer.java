package nadiendev.ThaumaturgeTechCompats.planetary.crucifix.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixDisplayEntity;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixRegistry;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixTiming;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.EnumMap;
import java.util.Map;

public class CrucifixDisplayRenderer extends EntityRenderer<CrucifixDisplayEntity, CrucifixDisplayRenderer.State> {
    private final ItemModelResolver itemModelResolver;
    private final Map<PlanetaryTier, ItemStack> stacks = new EnumMap<>(PlanetaryTier.class);

    public CrucifixDisplayRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
        this.shadowRadius = 0.0F;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(CrucifixDisplayEntity entity, State state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        PlanetaryTier tier = entity.tier();
        state.visible = CrucifixRegistry.has(tier);
        state.time = entity.tickCount + partialTick;
        state.fx = CrucifixClient.displayFx(entity.getId(), partialTick);
        state.item.clear();
        if (state.visible) {
            ItemStack stack = stacks.computeIfAbsent(tier, key -> new ItemStack(CrucifixRegistry.item(key)));
            itemModelResolver.updateForNonLiving(state.item, stack, ItemDisplayContext.FIXED, entity);
        }
    }

    @Override
    public void submit(State state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        if (!state.visible) {
            return;
        }
        float t = state.time;
        float rate = CrucifixTiming.TREMBLE_RATE;
        CrucifixClient.DisplayFx fx = state.fx;
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
        state.item.submit(pose, collector, LightCoordsUtil.pack(light, light), OverlayTexture.NO_OVERLAY, state.outlineColor);
        pose.popPose();
        super.submit(state, pose, collector, camera);
    }

    public static final class State extends EntityRenderState {
        private final ItemStackRenderState item = new ItemStackRenderState();
        private boolean visible;
        private float time;
        private CrucifixClient.DisplayFx fx = CrucifixClient.DisplayFx.CALM;
    }
}
