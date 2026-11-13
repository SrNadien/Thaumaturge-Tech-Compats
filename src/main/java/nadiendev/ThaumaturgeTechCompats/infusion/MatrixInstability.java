package nadiendev.ThaumaturgeTechCompats.infusion;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import com.leclowndu93150.thaumaturge.api.warp.WarpHelper;
import com.leclowndu93150.thaumaturge.api.warp.WarpType;
import com.leclowndu93150.thaumaturge.content.infusion.instability.InstabilityContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public final class MatrixInstability {
    private static final double MAX_SCALED_INTENSITY = 3.0D;

    private MatrixInstability() {
    }

    public static boolean solarDay(Level level, BlockPos pos) {
        if (!level.isDay()) {
            return false;
        }
        return !InfusionConfig.flag(MatrixVariant.MATRIX, "day_requires_sky") || level.canSeeSky(pos.above());
    }

    public static double lossMultiplier(MatrixVariant variant, Level level, BlockPos pos) {
        double multiplier = InfusionConfig.number(variant, "instability_multiplier");
        if (variant == MatrixVariant.MATRIX && solarDay(level, pos)) {
            multiplier *= InfusionConfig.number(variant, "day_multiplier");
        }
        return multiplier;
    }

    public static void apply(InstabilityKind kind, InstabilityContext context) {
        MatrixVariant variant = kind.variant();
        String name = kind.configName();
        if (!InfusionConfig.flag(variant, "effects_enabled") || !InfusionConfig.flag(variant, name + "_enabled")) {
            return;
        }
        ServerLevel level = context.level();
        BlockPos pos = context.matrix();
        RandomSource random = context.random();
        if (random.nextDouble() >= InfusionConfig.number(variant, name + "_chance")) {
            return;
        }
        double intensity = 1.0D + Math.max(0.0F, -context.stability()) / InfusionConfig.number(variant, "intensity_stability_scale");
        int radius = InfusionConfig.integer(variant, "effect_radius");
        switch (kind) {
            case SOLAR_FIRE -> {
                burst(level, pos, ParticleTypes.FLAME, 24);
                igniteGround(level, pos, random, InfusionConfig.integer(variant, "fire_attempts") * scale(intensity), radius);
            }
            case SOLAR_LIGHTNING -> {
                burst(level, pos, ParticleTypes.FLAME, 24);
                strikeLightning(level, pos, random, intensity > 2.0D ? 2 : 1, radius);
            }
            case SOLAR_BURN -> {
                burst(level, pos, ParticleTypes.FLAME, 24);
                int ticks = (int) (InfusionConfig.integer(variant, "burn_seconds") * intensity * 20.0D);
                float damage = (float) (InfusionConfig.number(variant, "burn_damage") * intensity);
                for (LivingEntity entity : living(level, pos, radius)) {
                    entity.setRemainingFireTicks(Math.max(entity.getRemainingFireTicks(), ticks));
                    entity.hurt(level.damageSources().inFire(), damage);
                }
            }
            case NUCLEAR_RADIATION -> {
                burst(level, pos, ParticleTypes.ELECTRIC_SPARK, 32);
                int ticks = (int) (InfusionConfig.integer(variant, "radiation_effect_seconds") * intensity * 20.0D);
                int amplifier = Math.min(4, (int) intensity - 1);
                float damage = (float) (InfusionConfig.number(variant, "radiation_damage") * intensity);
                for (LivingEntity entity : living(level, pos, radius)) {
                    entity.hurt(level.damageSources().wither(), damage);
                    entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, ticks, amplifier));
                    entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, ticks, amplifier));
                    if (intensity >= 2.0D) {
                        entity.addEffect(new MobEffectInstance(MobEffects.WITHER, ticks / 2, Math.max(0, amplifier - 1)));
                    }
                }
            }
            case NUCLEAR_CONTAMINATION -> {
                burst(level, pos, ParticleTypes.ELECTRIC_SPARK, 32);
                AuraHelper.addFlux(level, pos, (float) (InfusionConfig.number(variant, "contamination_flux") * intensity));
            }
            case NUCLEAR_MELTDOWN -> {
                burst(level, pos, ParticleTypes.ELECTRIC_SPARK, 32);
                float power = (float) (InfusionConfig.number(variant, "meltdown_power") * Math.min(intensity, MAX_SCALED_INTENSITY));
                Level.ExplosionInteraction interaction = InfusionConfig.flag(variant, "meltdown_breaks_blocks")
                        ? Level.ExplosionInteraction.BLOCK
                        : Level.ExplosionInteraction.NONE;
                level.explode(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, power, interaction);
            }
            case COSMIC_FLUX -> {
                burst(level, pos, ParticleTypes.REVERSE_PORTAL, 48);
                AuraHelper.addFlux(level, pos, (float) (InfusionConfig.number(variant, "flux_amount") * intensity));
            }
            case COSMIC_WARP -> {
                burst(level, pos, ParticleTypes.REVERSE_PORTAL, 48);
                int amount = (int) Math.max(1.0D, InfusionConfig.integer(variant, "warp_amount") * intensity);
                for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area(pos, radius))) {
                    WarpType type = random.nextDouble() < InfusionConfig.number(variant, "warp_normal_chance") ? WarpType.NORMAL : WarpType.TEMPORARY;
                    WarpHelper.addWarp(player, amount, type);
                }
            }
            case COSMIC_SUMMON -> {
                burst(level, pos, ParticleTypes.REVERSE_PORTAL, 48);
                summon(level, pos, random, (int) Math.min(InfusionConfig.integer(variant, "summon_count") * Math.min(intensity, MAX_SCALED_INTENSITY), 64.0D));
            }
            case COSMIC_TELEPORT -> {
                burst(level, pos, ParticleTypes.REVERSE_PORTAL, 48);
                int distance = InfusionConfig.integer(variant, "teleport_distance");
                for (LivingEntity entity : living(level, pos, radius)) {
                    throwAway(level, entity, random, distance);
                }
            }
        }
    }

    private static int scale(double intensity) {
        return Math.max(1, (int) Math.round(intensity));
    }

    private static AABB area(BlockPos pos, int radius) {
        return new AABB(pos).inflate(radius);
    }

    private static List<LivingEntity> living(ServerLevel level, BlockPos pos, int radius) {
        return level.getEntitiesOfClass(LivingEntity.class, area(pos, radius));
    }

    private static void burst(ServerLevel level, BlockPos pos, ParticleOptions particle, int count) {
        level.sendParticles(particle, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, count, 0.8D, 0.8D, 0.8D, 0.1D);
    }

    private static void igniteGround(ServerLevel level, BlockPos pos, RandomSource random, int attempts, int radius) {
        if (!level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            return;
        }
        for (int i = 0; i < attempts; i++) {
            int x = pos.getX() + random.nextInt(radius * 2 + 1) - radius;
            int z = pos.getZ() + random.nextInt(radius * 2 + 1) - radius;
            for (int y = pos.getY() + 3; y >= pos.getY() - 4; y--) {
                BlockPos target = new BlockPos(x, y, z);
                if (!target.equals(pos) && BaseFireBlock.canBePlacedAt(level, target, Direction.UP)) {
                    level.setBlockAndUpdate(target, BaseFireBlock.getState(level, target));
                    break;
                }
            }
        }
    }

    private static void strikeLightning(ServerLevel level, BlockPos pos, RandomSource random, int count, int radius) {
        for (int i = 0; i < count; i++) {
            int x = pos.getX() + random.nextInt(radius * 2 + 1) - radius;
            int z = pos.getZ() + random.nextInt(radius * 2 + 1) - radius;
            BlockPos target = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(x, pos.getY(), z));
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
            if (bolt != null) {
                bolt.moveTo(Vec3.atBottomCenterOf(target));
                level.addFreshEntity(bolt);
            }
        }
    }

    private static void summon(ServerLevel level, BlockPos pos, RandomSource random, int count) {
        List<? extends String> ids = InfusionConfig.summonEntities();
        if (ids.isEmpty()) {
            return;
        }
        for (int i = 0; i < count; i++) {
            ResourceLocation id = ResourceLocation.tryParse(ids.get(random.nextInt(ids.size())));
            Optional<EntityType<?>> type = id == null ? Optional.empty() : BuiltInRegistries.ENTITY_TYPE.getOptional(id);
            if (type.isEmpty()) {
                continue;
            }
            Entity entity = type.get().create(level);
            if (entity == null) {
                continue;
            }
            entity.moveTo(pos.getX() + 0.5D + random.nextInt(7) - 3, pos.getY() + 1.0D, pos.getZ() + 0.5D + random.nextInt(7) - 3,
                    random.nextFloat() * 360.0F, 0.0F);
            if (entity instanceof Mob mob) {
                mob.finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.EVENT, null);
            }
            level.addFreshEntity(entity);
        }
    }

    private static void throwAway(ServerLevel level, LivingEntity entity, RandomSource random, int distance) {
        for (int attempt = 0; attempt < 8; attempt++) {
            double x = entity.getX() + (random.nextDouble() * 2.0D - 1.0D) * distance;
            double z = entity.getZ() + (random.nextDouble() * 2.0D - 1.0D) * distance;
            double y = entity.getY() + random.nextInt(9) - 4;
            if (entity.randomTeleport(x, y, z, true)) {
                level.sendParticles(ParticleTypes.PORTAL, entity.getX(), entity.getY() + 1.0D, entity.getZ(), 16, 0.4D, 0.8D, 0.4D, 0.2D);
                return;
            }
        }
    }
}
