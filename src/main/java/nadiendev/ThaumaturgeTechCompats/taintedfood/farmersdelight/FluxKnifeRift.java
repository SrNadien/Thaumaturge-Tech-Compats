package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import com.leclowndu93150.thaumaturge.content.entity.EntityFluxRift;
import com.leclowndu93150.thaumaturge.registry.TTEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;

import java.util.Optional;

public final class FluxKnifeRift {
    private static final float AURA_SIZE_DIVISOR = 3.0F;
    private static final float FULL_TURN = 360.0F;
    private static final double CELL_CENTER = 0.5D;
    private static final double PARTICLE_SPREAD = 0.6D;
    private static final double PARTICLE_SPEED = 0.2D;
    private static final float EPSILON = 0.001F;

    private FluxKnifeRift() {
    }

    public static InteractionResult open(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null || !FarmersDelightTaintedConfig.riftEnabled()) {
            return InteractionResult.PASS;
        }
        if (FarmersDelightTaintedConfig.riftRequireSneak() && !player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }
        if (level.getBlockState(context.getClickedPos()).getBlock() instanceof CuttingBoardBlock) {
            return InteractionResult.PASS;
        }
        if (!(level instanceof ServerLevel server)) {
            return InteractionResult.SUCCESS;
        }
        ItemStack stack = context.getItemInHand();
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            return InteractionResult.FAIL;
        }
        BlockPos target = context.getClickedPos().relative(context.getClickedFace());
        int size = Math.max(1, Math.min(EntityFluxRift.MAX_RIFT_SIZE, FarmersDelightTaintedConfig.riftSize()));
        float cost = size * size / AURA_SIZE_DIVISOR * FarmersDelightTaintedConfig.riftCostFactor();
        String mode = FarmersDelightTaintedConfig.riftCostMode();
        if (cost > 0.0F && !canPay(server, target, mode, cost)) {
            player.sendOverlayMessage(Component.translatable(FluxKnife.NO_AURA_KEY));
            return InteractionResult.FAIL;
        }
        if (!spawn(server, target, size)) {
            return InteractionResult.FAIL;
        }
        if (cost > 0.0F) {
            pay(server, target, mode, cost);
        }
        int wear = FarmersDelightTaintedConfig.riftDurabilityCost();
        if (wear > 0) {
            stack.hurtAndBreak(wear, player, LivingEntity.getSlotForHand(context.getHand()));
        }
        int cooldown = FarmersDelightTaintedConfig.riftCooldown();
        if (cooldown > 0) {
            player.getCooldowns().addCooldown(stack.getItem(), cooldown);
        }
        effects(server, target);
        return InteractionResult.SUCCESS;
    }

    private static boolean canPay(ServerLevel level, BlockPos pos, String mode, float cost) {
        if (FarmersDelightTaintedConfig.COST_FLUX.equals(mode)) {
            return AuraHelper.drainFlux(level, pos, cost, true) >= cost - EPSILON;
        }
        if (FarmersDelightTaintedConfig.COST_VIS.equals(mode)) {
            return AuraHelper.drainVis(level, pos, cost, true) >= cost - EPSILON;
        }
        return true;
    }

    private static void pay(ServerLevel level, BlockPos pos, String mode, float cost) {
        if (FarmersDelightTaintedConfig.COST_FLUX.equals(mode)) {
            AuraHelper.drainFlux(level, pos, cost, false);
        } else if (FarmersDelightTaintedConfig.COST_VIS.equals(mode)) {
            AuraHelper.drainVis(level, pos, cost, false);
        }
    }

    private static boolean spawn(ServerLevel level, BlockPos pos, int size) {
        EntityFluxRift rift = TTEntities.FLUX_RIFT.get().create(level);
        if (rift == null) {
            return false;
        }
        RandomSource random = level.getRandom();
        rift.reseed(random.nextInt());
        rift.moveTo(pos.getX() + CELL_CENTER, pos.getY() + CELL_CENTER, pos.getZ() + CELL_CENTER, random.nextFloat() * FULL_TURN, 0.0F);
        rift.resize(size);
        return level.addFreshEntity(rift);
    }

    private static void effects(ServerLevel level, BlockPos pos) {
        double x = pos.getX() + CELL_CENTER;
        double y = pos.getY() + CELL_CENTER;
        double z = pos.getZ() + CELL_CENTER;
        Identifier soundId = Identifier.tryParse(FarmersDelightTaintedConfig.riftSound());
        float volume = FarmersDelightTaintedConfig.riftSoundVolume();
        if (soundId != null && volume > 0.0F) {
            Optional<SoundEvent> sound = BuiltInRegistries.SOUND_EVENT.getOptional(soundId);
            sound.ifPresent(event -> level.playSound(null, pos, event, SoundSource.PLAYERS, volume, 1.0F));
        }
        Identifier particleId = Identifier.tryParse(FarmersDelightTaintedConfig.riftParticle());
        int count = FarmersDelightTaintedConfig.riftParticleCount();
        if (particleId != null && count > 0) {
            Optional<ParticleType<?>> particle = BuiltInRegistries.PARTICLE_TYPE.getOptional(particleId);
            if (particle.isPresent() && particle.get() instanceof SimpleParticleType simple) {
                level.sendParticles(simple, x, y, z, count, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPREAD, PARTICLE_SPEED);
            }
        }
    }
}
