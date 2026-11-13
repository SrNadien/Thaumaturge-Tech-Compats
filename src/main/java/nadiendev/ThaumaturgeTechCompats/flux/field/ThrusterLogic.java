package nadiendev.ThaumaturgeTechCompats.flux.field;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public final class ThrusterLogic {
    private static final double BACK_OFFSET = 0.3D;
    private static final double BACK_HEIGHT = 0.9D;

    private ThrusterLogic() {
    }

    public static boolean airborne(Player player) {
        return !player.onGround()
                && !player.isInWater()
                && !player.isInLava()
                && !player.isFallFlying()
                && !player.isPassenger()
                && !player.isSpectator()
                && !player.getAbilities().flying;
    }

    public static void tick(Player player, ItemStack stack, FlightThrusterItem item, ThrusterState state, ThrusterInput input) {
        boolean server = !player.level().isClientSide();
        if (state.cooldown() > 0) {
            state.cooldown(state.cooldown() - 1);
        }
        long cost = FieldConfig.thrusterCostPerTick();
        boolean thrusting = input.up()
                && airborne(player)
                && state.cooldown() == 0
                && (cost <= 0L || item.fluxStored(stack) >= cost);
        if (!thrusting) {
            state.heat(state.heat() - 1);
            return;
        }
        accelerate(player, input.forward());
        player.fallDistance = 0.0F;
        if (server) {
            item.extractFlux(stack, cost, false);
            if (FieldConfig.thrusterParticles() && player.level() instanceof ServerLevel level) {
                exhaust(level, player);
            }
        }
        state.heat(state.heat() + 1);
        if (state.heat() >= FieldConfig.thrusterBurstTicks()) {
            state.heat(0);
            state.cooldown(FieldConfig.thrusterCooldownTicks());
        }
    }

    private static void accelerate(Player player, boolean forward) {
        Vec3 motion = player.getDeltaMovement();
        double vertical = Math.max(motion.y, Math.min(motion.y + FieldConfig.thrusterThrust(), FieldConfig.thrusterMaxVertical()));
        double x = motion.x;
        double z = motion.z;
        if (forward) {
            Vec3 look = player.getLookAngle();
            Vec3 heading = new Vec3(look.x, 0.0D, look.z);
            if (heading.lengthSqr() > 1.0E-6D) {
                heading = heading.normalize().scale(FieldConfig.thrusterForward());
                x += heading.x;
                z += heading.z;
                double speed = Math.sqrt(x * x + z * z);
                double limit = Math.max(FieldConfig.thrusterMaxHorizontal(), Math.sqrt(motion.x * motion.x + motion.z * motion.z));
                if (speed > limit) {
                    x *= limit / speed;
                    z *= limit / speed;
                }
            }
        }
        player.setDeltaMovement(x, vertical, z);
    }

    private static void exhaust(ServerLevel level, Player player) {
        double yaw = Math.toRadians(player.yBodyRot);
        double x = player.getX() + Math.sin(yaw) * BACK_OFFSET;
        double z = player.getZ() - Math.cos(yaw) * BACK_OFFSET;
        double y = player.getY() + BACK_HEIGHT;
        level.sendParticles(ParticleTypes.SMALL_FLAME, x, y, z, 0, 0.0D, -1.0D, 0.0D, 0.12D);
        level.sendParticles(ParticleTypes.REVERSE_PORTAL, x, y, z, 2, 0.12D, 0.05D, 0.12D, 0.02D);
    }
}
