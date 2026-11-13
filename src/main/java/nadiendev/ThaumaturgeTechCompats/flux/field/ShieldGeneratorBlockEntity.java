package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.flux.IFluxStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.joml.Vector3f;

import java.text.NumberFormat;

public class ShieldGeneratorBlockEntity extends BlockEntity {
    private static final String ACTIVE_KEY = "active";
    private static final Vector3f SHELL_BLUE = new Vector3f(0.30F, 0.55F, 1.0F);
    private static final float SHELL_LIGHTEN = 0.35F;
    private static final double PARTICLE_VIEW_MARGIN = 24.0D;
    private static final double PUSH_LIFT = 0.04D;

    private final FluxTier tier;
    private final FieldFluxStorage storage;
    private boolean active;
    private long age;

    public ShieldGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(FieldRegistry.SHIELD_ENTITY.get(), pos, state);
        this.tier = ((ShieldGeneratorBlock) state.getBlock()).tier();
        this.storage = new FieldFluxStorage(() -> FieldConfig.shieldCapacity(tier));
    }

    public FluxTier tier() {
        return tier;
    }

    public IFluxStorage storage() {
        return storage;
    }

    public boolean isActive() {
        return active;
    }

    public int radius() {
        return FieldConfig.shieldRadius(tier);
    }

    public Vec3 center() {
        return Vec3.atCenterOf(worldPosition);
    }

    public boolean covers(Vec3 position) {
        double radius = radius();
        return position.distanceToSqr(center()) <= radius * radius;
    }

    public boolean pay(ServerLevel level, long amount) {
        if (storage.stored() < amount) {
            shutDown(level);
            return false;
        }
        storage.consume(amount);
        return true;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        storage.save(tag);
        tag.putBoolean(ACTIVE_KEY, active);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        storage.load(tag);
        active = tag.getBoolean(ACTIVE_KEY);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (active && level instanceof ServerLevel server) {
            ShieldNetwork.add(server.dimension(), this);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (level != null && !level.isClientSide) {
            ShieldNetwork.remove(level.dimension(), this);
        }
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        if (level != null && !level.isClientSide) {
            ShieldNetwork.remove(level.dimension(), this);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ShieldGeneratorBlockEntity shield) {
        if (level instanceof ServerLevel server) {
            shield.tick(server);
        }
    }

    private void setActive(ServerLevel level, boolean value) {
        if (active == value) {
            return;
        }
        active = value;
        if (value) {
            ShieldNetwork.add(level.dimension(), this);
        } else {
            ShieldNetwork.remove(level.dimension(), this);
        }
        setChanged();
    }

    private void shutDown(ServerLevel level) {
        if (!active) {
            return;
        }
        setActive(level, false);
        int range = FieldConfig.shieldMessageRadius();
        if (range <= 0) {
            return;
        }
        Component message = Component.translatable(FieldTranslations.SHIELD_SHUTDOWN, worldPosition.toShortString()).withStyle(ChatFormatting.RED);
        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, new AABB(worldPosition).inflate(range))) {
            player.displayClientMessage(message, true);
        }
    }

    private void tick(ServerLevel level) {
        age++;
        if (age % 20L == 0L) {
            setChanged();
        }
        if (!FieldIntegration.enabled(tier) || !FieldConfig.shieldEnabled()) {
            setActive(level, false);
            return;
        }
        long cost = FieldConfig.shieldCost(tier);
        if (!active) {
            long restart = (long) Math.min((double) storage.capacity(), (double) cost * FieldConfig.shieldRestartTicks());
            if (storage.stored() < Math.max(1L, restart)) {
                return;
            }
            setActive(level, true);
        }
        if (!pay(level, cost)) {
            return;
        }
        if (age % FieldConfig.shieldScanInterval() == 0L) {
            sweep(level);
        }
        if (active && FieldConfig.shieldParticles() && age % FieldConfig.shieldParticleInterval() == 0L) {
            shell(level);
        }
    }

    private void sweep(ServerLevel level) {
        boolean projectiles = FieldConfig.shieldBlockProjectiles();
        boolean push = FieldConfig.shieldPushMonsters();
        if (!projectiles && !push) {
            return;
        }
        double radius = radius();
        double radiusSq = radius * radius;
        Vec3 center = center();
        long projectileCost = FieldConfig.shieldProjectileCost(tier);
        for (Entity entity : level.getEntities((Entity) null, new AABB(worldPosition).inflate(radius), Entity::isAlive)) {
            if (entity.distanceToSqr(center) > radiusSq) {
                continue;
            }
            if (projectiles && entity instanceof Projectile projectile && isHostile(projectile)) {
                if (!pay(level, projectileCost)) {
                    return;
                }
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK, projectile.getX(), projectile.getY(), projectile.getZ(), 6, 0.1D, 0.1D, 0.1D, 0.05D);
                projectile.discard();
            } else if (push && entity instanceof Mob mob && mob instanceof Enemy && !mob.getType().is(Tags.EntityTypes.BOSSES) && !mob.isPassenger()) {
                pushOut(level, mob, center);
            }
        }
    }

    private static boolean isHostile(Projectile projectile) {
        Entity owner = projectile.getOwner();
        return owner instanceof Enemy || owner == null && FieldConfig.shieldBlockOwnerless();
    }

    private static void pushOut(ServerLevel level, Mob mob, Vec3 center) {
        Vec3 away = mob.position().add(0.0D, mob.getBbHeight() * 0.5D, 0.0D).subtract(center);
        if (away.lengthSqr() < 1.0E-4D) {
            away = new Vec3(level.random.nextDouble() - 0.5D, 0.0D, level.random.nextDouble() - 0.5D);
        }
        double strength = FieldConfig.shieldPushStrength();
        away = away.normalize();
        mob.push(away.x * strength, away.y * strength * 0.5D + PUSH_LIFT, away.z * strength);
    }

    private void shell(ServerLevel level) {
        double radius = radius();
        Vec3 center = center();
        Player nearest = level.getNearestPlayer(center.x, center.y, center.z, radius + PARTICLE_VIEW_MARGIN, false);
        if (nearest == null) {
            return;
        }
        RandomSource random = level.random;
        Vec3 toward = nearest.position().subtract(center);
        if (toward.lengthSqr() < 1.0E-4D) {
            toward = new Vec3(0.0D, 1.0D, 0.0D);
        }
        toward = toward.normalize();
        ParticleOptions particle = new DustColorTransitionOptions(SHELL_BLUE, shellViolet(), FieldConfig.shieldParticleScale());
        for (int i = 0; i < FieldConfig.shieldParticleCount(); i++) {
            Vec3 direction = toward.add(random.nextGaussian() * 0.7D, random.nextGaussian() * 0.7D, random.nextGaussian() * 0.7D);
            if (direction.lengthSqr() < 1.0E-4D) {
                direction = toward;
            }
            direction = direction.normalize().scale(radius);
            level.sendParticles(particle, center.x + direction.x, center.y + direction.y, center.z + direction.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private Vector3f shellViolet() {
        int rgb = tier.color();
        float red = (rgb >> 16 & 255) / 255.0F;
        float green = (rgb >> 8 & 255) / 255.0F;
        float blue = (rgb & 255) / 255.0F;
        return new Vector3f(red + (1.0F - red) * SHELL_LIGHTEN, green + (1.0F - green) * SHELL_LIGHTEN, blue + (1.0F - blue) * SHELL_LIGHTEN);
    }

    public void report(Player player) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        long cost = FieldConfig.shieldCost(tier);
        player.displayClientMessage(getBlockState().getBlock().getName().withStyle(ChatFormatting.LIGHT_PURPLE), false);
        if (!FieldIntegration.enabled(tier) || !FieldConfig.shieldEnabled()) {
            player.displayClientMessage(Component.translatable(FieldTranslations.SHIELD_DISABLED).withStyle(ChatFormatting.RED), false);
            return;
        }
        player.displayClientMessage(Component.translatable(active ? FieldTranslations.SHIELD_STATE_ON : FieldTranslations.SHIELD_STATE_OFF)
                .withStyle(active ? ChatFormatting.GREEN : ChatFormatting.RED), false);
        player.displayClientMessage(Component.translatable(FieldTranslations.SHIELD_RADIUS, radius()).withStyle(ChatFormatting.GRAY), false);
        player.displayClientMessage(Component.translatable(FieldTranslations.FLUX, format.format(storage.stored()), format.format(storage.capacity())).withStyle(ChatFormatting.GRAY), false);
        if (cost > 0L) {
            player.displayClientMessage(Component.translatable(FieldTranslations.SHIELD_COST, format.format(cost), format.format(storage.stored() / cost / 20L)).withStyle(ChatFormatting.GRAY), false);
        }
    }
}
