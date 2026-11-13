package nadiendev.ThaumaturgeTechCompats.planetary.crucifix.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixClientConfig;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixEffectPayload;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixRegistry;
import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.CrucifixTiming;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, value = Dist.CLIENT)
public final class CrucifixClient {
    private static final class Effect {
        final int playerId;
        final int targetId;
        final int displayId;
        final boolean success;
        final long startTick;
        final int bind;
        boolean ended;
        int snapsShown;
        Vec3 bossPos;

        Effect(int playerId, int targetId, int displayId, boolean success, long startTick, int bind) {
            this.playerId = playerId;
            this.targetId = targetId;
            this.displayId = displayId;
            this.success = success;
            this.startTick = startTick;
            this.bind = bind;
        }
    }

    public record DisplayFx(float tremble, float glow, float swell) {
        public static final DisplayFx CALM = new DisplayFx(1.0F, 1.0F, 1.0F);
    }

    private static final double LINK = 0.24;
    private static final double REACH_PART = 0.62;
    private static final double WRAP_TURNS = 0.85;
    private static final double SNAP_POINT = 0.45;

    private static final Vector3f ANGRY_RED = new Vector3f(1.0F, 0.12F, 0.06F);
    private static final Vector3f EMBER = new Vector3f(1.0F, 0.45F, 0.1F);

    private static final Map<Integer, Effect> EFFECTS = new HashMap<>();
    private static final Set<LivingEntity> SHAKEN = Collections.newSetFromMap(new IdentityHashMap<>());

    private CrucifixClient() {
    }

    public static void accept(CrucifixEffectPayload payload) {
        switch (payload.phase()) {
            case START -> EFFECTS.put(payload.playerId(),
                    new Effect(payload.playerId(), payload.targetId(), payload.displayId(), payload.success(), payload.startTick(), payload.bindTicks()));
            case SHATTER -> {
                Effect effect = EFFECTS.get(payload.playerId());
                if (effect != null && !effect.success) {
                    effect.ended = true;
                } else {
                    EFFECTS.remove(payload.playerId());
                }
            }
            case BANISH, CANCEL -> EFFECTS.remove(payload.playerId());
        }
    }

    private static double age(Effect effect, ClientLevel level, float partialTick) {
        return Math.max(0.0, level.getGameTime() - effect.startTick + partialTick);
    }

    private static int lifetime(Effect effect) {
        return effect.success ? CrucifixTiming.duration(true, effect.bind) + 40
                : CrucifixTiming.FAIL_TICKS + Math.max(CrucifixTiming.FAIL_AFTERMATH_TICKS, CrucifixTiming.FLASH_TICKS) + 5;
    }

    private static boolean chainsVisible(Effect effect, double age) {
        return effect.success || !effect.ended && age < CrucifixTiming.FAIL_TICKS;
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        EFFECTS.clear();
        SHAKEN.clear();
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null || mc.isPaused() || EFFECTS.isEmpty()) {
            return;
        }
        long now = level.getGameTime();
        EFFECTS.values().removeIf(e -> now - e.startTick > lifetime(e));
        RandomSource random = level.random;
        for (Effect effect : EFFECTS.values()) {
            if (!(level.getEntity(effect.targetId) instanceof LivingEntity target)) {
                continue;
            }
            effect.bossPos = target.position();
            double age = age(effect, level, 0.0F);
            if (chainsVisible(effect, age)) {
                AABB box = target.getBoundingBox();
                Vec3 center = box.getCenter();
                for (int i = 0; i < 2; i++) {
                    double angle = random.nextDouble() * Mth.TWO_PI;
                    double radius = box.getXsize() * 0.5 + 0.3 + random.nextDouble() * 0.4;
                    level.addParticle(CrucifixRegistry.STAR_SPARKLE.get(), center.x + Math.cos(angle) * radius,
                            box.minY + random.nextDouble() * box.getYsize(), center.z + Math.sin(angle) * radius,
                            0.0, 0.015 + random.nextDouble() * 0.02, 0.0);
                }
            }
            if (!effect.success) {
                failTick(level, effect, target, age, random);
            }
        }
    }

    private static void failTick(ClientLevel level, Effect effect, LivingEntity boss, double age, RandomSource random) {
        double wave = CrucifixTiming.shockwave(age);
        if (wave >= 0.0) {
            shockwave(level, boss.position(), Math.max(0.6, CrucifixTiming.SHOCKWAVE_RADIUS * wave), random);
        }
        if (!chainsVisible(effect, age) || !(level.getEntity(effect.displayId) instanceof Entity display)) {
            return;
        }

        Vec3 origin = display.getBoundingBox().getCenter();
        double strain = CrucifixTiming.strain(age);
        int snapped = CrucifixTiming.snapped(age);
        for (int c = snapped; c < CrucifixTiming.CHAINS; c++) {
            if (age < CrucifixTiming.FORM_TICKS || random.nextDouble() > 0.2 + 0.7 * strain) {
                continue;
            }
            Vec3 at = path(origin, boss, c, age, 1.0F).at(random.nextDouble() * 0.95);
            double vx = (random.nextDouble() - 0.5) * 0.12, vy = random.nextDouble() * 0.1, vz = (random.nextDouble() - 0.5) * 0.12;
            if (strain < 0.45) {
                level.addParticle(ParticleTypes.WAX_ON, at.x, at.y, at.z, vx * 20, vy * 20, vz * 20);
            } else if (random.nextInt(4) == 0) {
                level.addParticle(ParticleTypes.LAVA, at.x, at.y, at.z, 0.0, 0.0, 0.0);
            } else {
                level.addParticle(ParticleTypes.SMALL_FLAME, at.x, at.y, at.z, vx, vy, vz);
            }
        }
        while (effect.snapsShown < snapped) {
            int c = effect.snapsShown++;
            Vec3 at = path(origin, boss, c, CrucifixTiming.snapTick(c), 1.0F).at(SNAP_POINT);
            snapBurst(level, at, random);
        }
    }

    private static void shockwave(ClientLevel level, Vec3 center, double radius, RandomSource random) {
        int points = Mth.clamp((int) (radius * Mth.TWO_PI / 0.4), 10, 150);
        DustParticleOptions red = new DustParticleOptions(ANGRY_RED, 2.2F);
        DustColorTransitionOptions ember = new DustColorTransitionOptions(EMBER, ANGRY_RED, 1.6F);
        for (int i = 0; i < points; i++) {
            double angle = Mth.TWO_PI * (i + random.nextDouble() * 0.5) / points;
            double x = center.x + Math.cos(angle) * radius, z = center.z + Math.sin(angle) * radius;
            double y = center.y + 0.15;
            level.addParticle(i % 3 == 0 ? ember : red, x, y + random.nextDouble() * 0.2, z, 0.0, 0.0, 0.0);
            if (random.nextInt(5) == 0) {
                BlockState ground = level.getBlockState(BlockPos.containing(x, center.y - 0.5, z));
                if (!ground.isAir() && ground.getRenderShape() != RenderShape.INVISIBLE) {
                    level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, ground), x, y, z,
                            Math.cos(angle) * 0.2, 0.25, Math.sin(angle) * 0.2);
                }
            }
        }
    }

    private static void snapBurst(ClientLevel level, Vec3 at, RandomSource random) {
        ItemParticleOption shard = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.GOLD_NUGGET));
        DustParticleOptions red = new DustParticleOptions(ANGRY_RED, 1.4F);
        for (int i = 0; i < 26; i++) {
            Vec3 dir = new Vec3(random.nextDouble() - 0.5, random.nextDouble() - 0.35, random.nextDouble() - 0.5).normalize();
            double speed = 0.15 + random.nextDouble() * 0.25;
            if (i < 6) {
                level.addParticle(ParticleTypes.END_ROD, at.x, at.y, at.z, dir.x * speed * 0.5, dir.y * speed * 0.5, dir.z * speed * 0.5);
            } else if (i < 14) {
                level.addParticle(ParticleTypes.ELECTRIC_SPARK, at.x, at.y, at.z, dir.x * speed * 1.5, dir.y * speed * 1.5, dir.z * speed * 1.5);
            } else if (i < 20) {
                level.addParticle(shard, at.x, at.y, at.z, dir.x * speed, dir.y * speed + 0.1, dir.z * speed);
            } else {
                level.addParticle(red, at.x + dir.x * 0.2, at.y + dir.y * 0.2, at.z + dir.z * 0.2, 0.0, 0.0, 0.0);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderLivingPre(RenderLivingEvent.Pre<?, ?> event) {
        if (EFFECTS.isEmpty()) {
            return;
        }
        LivingEntity entity = event.getEntity();
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        for (Effect effect : EFFECTS.values()) {
            if (effect.targetId != entity.getId()) {
                continue;
            }
            double age = age(effect, level, event.getPartialTick());
            if (!chainsVisible(effect, age)) {
                return;
            }
            double amplitude = CrucifixTiming.shake(effect.success, age, effect.bind);
            float t = (float) age * CrucifixTiming.TREMBLE_RATE;
            double lift = 0.0;
            if (!effect.success) {
                amplitude *= 1.0 + 0.5 * CrucifixTiming.failShake(age);
                if (CrucifixTiming.snapped(age) == 0) {
                    lift = CrucifixTiming.FAIL_BOSS_LIFT * CrucifixTiming.strain(age) * (0.6 + 0.4 * Mth.sin((float) age * 2.3F));
                }
            }
            PoseStack pose = event.getPoseStack();
            pose.pushPose();
            pose.translate(Mth.sin(t * 5.3F + entity.getId()) * amplitude, lift, Mth.cos(t * 6.7F + entity.getId()) * amplitude);
            SHAKEN.add(entity);
            return;
        }
    }

    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event) {
        if (SHAKEN.remove(event.getEntity())) {
            event.getPoseStack().popPose();
        }
    }

    public static DisplayFx displayFx(int displayId, float partialTick) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null || EFFECTS.isEmpty()) {
            return DisplayFx.CALM;
        }
        for (Effect effect : EFFECTS.values()) {
            if (effect.displayId != displayId || effect.success) {
                continue;
            }
            double age = age(effect, level, partialTick);
            double strain = CrucifixTiming.strain(age);
            boolean cracked = CrucifixTiming.cracked(age);
            long tick = (long) age;
            RandomSource flicker = RandomSource.create(displayId * 31L + tick);
            float glow = 1.0F;
            if (cracked) {
                glow = tick % 2 == 0 ? 1.0F : 0.45F;
            } else if (flicker.nextDouble() < 0.35 * strain) {
                glow = 0.3F + 0.4F * flicker.nextFloat();
            }
            float swell = cracked ? 1.0F + 0.15F * (float) Mth.clamp((age - CrucifixTiming.FAIL_CRACK_TICKS)
                    / (CrucifixTiming.FAIL_TICKS - CrucifixTiming.FAIL_CRACK_TICKS), 0.0, 1.0) : 1.0F;
            return new DisplayFx(1.0F + 5.0F * (float) strain + (cracked ? 6.0F : 0.0F), glow, swell);
        }
        return DisplayFx.CALM;
    }

    private static boolean screenEffects(Minecraft mc) {
        return CrucifixClientConfig.screenEffects() && mc.options.screenEffectScale().get() > 0.0;
    }

    @SubscribeEvent
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        if (EFFECTS.isEmpty()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null || !screenEffects(mc)) {
            return;
        }
        float partialTick = (float) event.getPartialTick();
        Vec3 eye = event.getCamera().getPosition();
        double strongest = 0.0;
        for (Effect effect : EFFECTS.values()) {
            if (effect.success || effect.bossPos == null) {
                continue;
            }
            double near = 1.0 - eye.distanceTo(effect.bossPos) / CrucifixTiming.SCREEN_SHAKE_RANGE;
            if (near <= 0.0) {
                continue;
            }
            strongest = Math.max(strongest, CrucifixTiming.failShake(age(effect, level, partialTick)) * near);
        }
        if (strongest <= 0.0) {
            return;
        }
        float degrees = (float) (strongest * CrucifixTiming.SCREEN_SHAKE_DEGREES * mc.options.screenEffectScale().get());
        float t = (level.getGameTime() % 24000L + partialTick) * 1.3F;
        event.setYaw(event.getYaw() + degrees * (Mth.sin(t * 1.7F) * 0.6F + Mth.sin(t * 4.3F + 1.3F) * 0.4F));
        event.setPitch(event.getPitch() + degrees * (Mth.sin(t * 2.1F + 0.7F) * 0.6F + Mth.sin(t * 5.1F + 2.1F) * 0.4F));
        event.setRoll(event.getRoll() + degrees * 0.5F * Mth.sin(t * 3.3F + 0.4F));
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (EFFECTS.isEmpty()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null || mc.player == null || !screenEffects(mc) || mc.options.hideLightningFlash().get()) {
            return;
        }
        Effect effect = EFFECTS.get(mc.player.getId());
        if (effect == null || effect.success) {
            return;
        }
        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
        double f = (age(effect, level, partialTick) - CrucifixTiming.FAIL_TICKS) / CrucifixTiming.FLASH_TICKS;
        if (f < 0.0 || f >= 1.0) {
            return;
        }
        double alpha = 0.7 * Math.pow(1.0 - f, 1.5) * mc.options.screenEffectScale().get();
        int a = (int) (Mth.clamp(alpha, 0.0, 1.0) * 255.0);
        int g = (int) (Mth.lerp(f, 0.95, 0.15) * 255.0), b = (int) (Mth.lerp(f, 0.9, 0.1) * 255.0);
        event.getGuiGraphics().fill(0, 0, event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight(),
                a << 24 | 0xFF << 16 | g << 8 | b);
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES || EFFECTS.isEmpty()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) {
            return;
        }
        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
        Vec3 cam = event.getCamera().getPosition();
        MultiBufferSource.BufferSource buffers = mc.renderBuffers().bufferSource();
        VertexConsumer out = buffers.getBuffer(RenderType.lightning());
        Matrix4f matrix = event.getPoseStack().last().pose();
        for (Effect effect : EFFECTS.values()) {
            double age = age(effect, level, partialTick);
            if (!chainsVisible(effect, age)) {
                continue;
            }
            if (level.getEntity(effect.targetId) instanceof LivingEntity target && level.getEntity(effect.displayId) instanceof Entity display) {
                Vec3 origin = display.getPosition(partialTick).add(0.0, CrucifixTiming.DISPLAY_SIZE * 0.5, 0.0);
                chains(out, matrix, cam, origin, target, effect, age, partialTick);
            }
        }
        buffers.endBatch(RenderType.lightning());
    }

    private static ChainPath path(Vec3 origin, LivingEntity target, int chain, double age, float partialTick) {
        Vec3 base = target.getPosition(partialTick);
        double radius = target.getBbWidth() * 0.5 + 0.12;
        double facing = Math.atan2(origin.z - base.z, origin.x - base.x);
        double direction = chain % 2 == 0 ? 1.0 : -1.0;
        double height = target.getBbHeight() * (0.3 + 0.25 * chain);
        double start = facing + (chain - 1) * 0.7 + direction * age * 0.025 * CrucifixTiming.TREMBLE_RATE;
        return new ChainPath(origin, base, height, radius, start, direction);
    }

    private static void chains(VertexConsumer out, Matrix4f matrix, Vec3 cam, Vec3 origin, LivingEntity target, Effect effect,
                               double age, float partialTick) {
        double form = CrucifixTiming.formation(age);
        double strain = effect.success ? 0.0 : CrucifixTiming.strain(age);
        RandomSource jitter = RandomSource.create(target.getId() * 341873128712L + (long) age);
        for (int c = 0; c < CrucifixTiming.CHAINS; c++) {
            double recoil = 0.0;
            if (!effect.success) {
                double since = age - CrucifixTiming.snapTick(c);
                if (since >= CrucifixTiming.SNAP_RECOIL_TICKS) {
                    continue;
                }
                if (since >= 0.0) {
                    recoil = since / CrucifixTiming.SNAP_RECOIL_TICKS;
                }
            }
            ChainPath path = path(origin, target, c, age, partialTick).taut(strain);
            int links = Math.max(6, (int) Math.ceil(path.length() / LINK));
            int shown = (int) Math.ceil(links * form);
            Vec3 straight = path.wrap(0.0).subtract(origin);
            Vec3 perp = straight.cross(new Vec3(0.0, 1.0, 0.0));
            perp = perp.lengthSqr() > 1.0E-6 ? perp.normalize() : new Vec3(1.0, 0.0, 0.0);
            double gap = 0.12 + 0.13 * strain;
            for (int i = 0; i < shown; i++) {
                double s0 = (i + gap) / links, s1 = (i + 1.0 - gap) / links;
                double mid = (i + 0.5) / links;
                double droop = 0.0;
                if (recoil > 0.0) {
                    if (mid < SNAP_POINT) {
                        if (mid > SNAP_POINT * (1.0 - recoil)) {
                            continue;
                        }
                    } else {
                        if (mid < SNAP_POINT + (1.0 - SNAP_POINT) * recoil * 0.5) {
                            continue;
                        }
                        droop = 0.5 * recoil * (mid - SNAP_POINT);
                    }
                }
                Vec3 from = vibrate(path.at(s0), s0, perp, strain, age, c).add(0.0, -droop, 0.0);
                Vec3 to = vibrate(path.at(s1), s1, perp, strain, age, c).add(0.0, -droop, 0.0);
                if (strain > 0.0) {
                    double j = 0.16 * strain;
                    from = from.add((jitter.nextDouble() - 0.5) * j, (jitter.nextDouble() - 0.5) * j, (jitter.nextDouble() - 0.5) * j);
                    to = to.add((jitter.nextDouble() - 0.5) * j, (jitter.nextDouble() - 0.5) * j, (jitter.nextDouble() - 0.5) * j);
                }
                Vec3 along = to.subtract(from);
                if (along.lengthSqr() < 1.0E-8) {
                    continue;
                }
                along = along.normalize();
                Vec3 side = Math.abs(along.y) > 0.95 ? new Vec3(1.0, 0.0, 0.0) : along.cross(new Vec3(0.0, 1.0, 0.0)).normalize();
                Vec3 across = (i % 2 == 0 ? side : along.cross(side).normalize()).scale(0.06);
                float flicker = strain > 0.0 ? (float) (0.65 + 0.35 * Math.sin(age * (2.1 + 3.0 * strain) + i * 1.7)) : 1.0F;
                flicker *= (float) (1.0 - recoil);
                float warm = (float) strain;
                bar(out, matrix, cam, from, to, across.scale(1.3 + 0.4 * warm), Mth.lerp(warm, 0.45F, 1.0F), Mth.lerp(warm, 0.62F, 0.18F),
                        Mth.lerp(warm, 1.0F, 0.05F), (0.18F + 0.14F * warm) * flicker);
                float r = 1.0F, g = Mth.lerp(warm, 0.93F, 0.32F), b = Mth.lerp(warm, 0.68F, 0.12F), a = 0.9F * flicker;
                Vec3 thin = across.normalize().scale(0.012);
                Vec3 cap = along.scale(0.012);
                bar(out, matrix, cam, from.add(across), to.add(across), thin, r, g, b, a);
                bar(out, matrix, cam, from.subtract(across), to.subtract(across), thin, r, g, b, a);
                bar(out, matrix, cam, from.subtract(across), from.add(across), cap, r, g, b, a);
                bar(out, matrix, cam, to.subtract(across), to.add(across), cap, r, g, b, a);
            }
        }
    }

    private static Vec3 vibrate(Vec3 at, double s, Vec3 perp, double strain, double age, int chain) {
        if (strain <= 0.0 || s >= REACH_PART) {
            return at;
        }
        double t = s / REACH_PART;
        double amp = 0.1 * strain * Math.sin(Math.PI * t);
        double phase = age * (1.4 + 1.2 * strain) + chain * 2.1;
        return at.add(perp.scale(amp * Math.sin(phase + t * 9.0))).add(0.0, amp * 0.6 * Math.cos(phase * 1.3 + t * 7.0), 0.0);
    }

    private record ChainPath(Vec3 origin, Vec3 base, double height, double radius, double start, double direction, double slack) {
        ChainPath(Vec3 origin, Vec3 base, double height, double radius, double start, double direction) {
            this(origin, base, height, radius, start, direction, 1.0);
        }

        ChainPath taut(double strain) {
            return new ChainPath(origin, base, height, radius, start, direction, 1.0 - strain);
        }

        Vec3 wrap(double w) {
            double angle = start + direction * w * WRAP_TURNS * Mth.TWO_PI;
            return new Vec3(base.x + Math.cos(angle) * radius, base.y + height + (w - 0.5) * 0.25, base.z + Math.sin(angle) * radius);
        }

        Vec3 at(double s) {
            if (s < REACH_PART) {
                double t = s / REACH_PART;
                Vec3 end = wrap(0.0);
                double sag = slack * 0.18 * Math.sin(Math.PI * t) * Math.min(1.0, origin.distanceTo(end) / 3.0);
                return origin.lerp(end, t).add(0.0, -sag, 0.0);
            }
            return wrap((s - REACH_PART) / (1.0 - REACH_PART));
        }

        double length() {
            return origin.distanceTo(wrap(0.0)) / REACH_PART;
        }
    }

    private static void bar(VertexConsumer out, Matrix4f matrix, Vec3 cam, Vec3 from, Vec3 to, Vec3 half, float r, float g, float b, float a) {
        Vec3 p0 = from.subtract(half).subtract(cam), p1 = from.add(half).subtract(cam);
        Vec3 p2 = to.add(half).subtract(cam), p3 = to.subtract(half).subtract(cam);
        vertex(out, matrix, p0, r, g, b, a);
        vertex(out, matrix, p1, r, g, b, a);
        vertex(out, matrix, p2, r, g, b, a);
        vertex(out, matrix, p3, r, g, b, a);
        vertex(out, matrix, p3, r, g, b, a);
        vertex(out, matrix, p2, r, g, b, a);
        vertex(out, matrix, p1, r, g, b, a);
        vertex(out, matrix, p0, r, g, b, a);
    }

    private static void vertex(VertexConsumer out, Matrix4f matrix, Vec3 p, float r, float g, float b, float a) {
        out.addVertex(matrix, (float) p.x, (float) p.y, (float) p.z).setColor(r, g, b, a);
    }
}
