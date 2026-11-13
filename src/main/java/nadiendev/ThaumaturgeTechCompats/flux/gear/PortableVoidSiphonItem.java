package nadiendev.ThaumaturgeTechCompats.flux.gear;

import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;

public class PortableVoidSiphonItem extends FluxGearItem {
    public static final String TOOLTIP = "tooltip.thaumaturgetechcompats.portable_void_siphon.";

    private static final int USE_DURATION = 72_000;
    private static final double TOUCH_DISTANCE = 1.5D;

    public PortableVoidSiphonItem(Properties properties) {
        super(properties);
    }

    @Override
    public long fluxCapacity(ItemStack stack) {
        return GearConfig.siphonCapacity();
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!GearConfig.siphonEnabled()) {
            return InteractionResult.PASS;
        }
        if (fluxStored(stack) < costPerPulse()) {
            if (!level.isClientSide()) {
                player.sendOverlayMessage(Component.translatable(MESSAGE_EMPTY).withStyle(ChatFormatting.RED));
            }
            return InteractionResult.FAIL;
        }
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BLOCK;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remaining) {
        if (!(entity instanceof Player player)) {
            return;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            suckParticles(level, player);
            return;
        }
        int used = USE_DURATION - remaining;
        if (used % GearConfig.siphonInterval() == 0 && !pulse(serverLevel, player, stack)) {
            player.stopUsingItem();
            player.sendOverlayMessage(Component.translatable(MESSAGE_EMPTY).withStyle(ChatFormatting.RED));
            return;
        }
        absorb(serverLevel, player);
    }

    private static long costPerPulse() {
        return Math.max(1L, GearConfig.siphonCostPerSecond() * GearConfig.siphonInterval() / 20L);
    }

    private boolean pulse(ServerLevel level, Player player, ItemStack stack) {
        long cost = costPerPulse();
        if (extractFlux(stack, cost, true) < cost) {
            return false;
        }
        extractFlux(stack, cost, false);
        if (GearConfig.siphonResidual()) {
            drawResidual(level, player, stack);
        }
        return true;
    }

    private void drawResidual(ServerLevel level, Player player, ItemStack stack) {
        long ratio = GearConfig.siphonFluxRatio();
        long room = fluxCapacity(stack) - fluxStored(stack);
        float wanted = (float) (GearConfig.siphonFluxPerSecond() * GearConfig.siphonInterval() / 20.0D);
        float fitting = (float) room / ratio;
        float amount = Math.min(wanted, fitting);
        BlockPos pos = player.blockPosition();
        if (amount <= 0.0F || AuraHelper.getFlux(level, pos) <= 0.0F) {
            return;
        }
        float drained = AuraHelper.drainFlux(level, pos, amount, false);
        if (drained > 0.0F) {
            insertFlux(stack, (long) Math.floor(drained * ratio), false);
        }
    }

    private void absorb(ServerLevel level, Player player) {
        double radius = GearConfig.siphonRadius();
        double speed = GearConfig.siphonSpeed();
        Vec3 target = player.position().add(0.0D, player.getBbHeight() * 0.5D, 0.0D);
        AABB area = player.getBoundingBox().inflate(radius);
        if (GearConfig.siphonItems()) {
            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area,
                    item -> item.isAlive() && !item.getItem().isEmpty() && item.position().distanceToSqr(target) <= radius * radius);
            for (ItemEntity item : items) {
                if (pull(item, target, speed)) {
                    item.playerTouch(player);
                }
            }
        }
        if (GearConfig.siphonExperience()) {
            List<ExperienceOrb> orbs = level.getEntitiesOfClass(ExperienceOrb.class, area,
                    orb -> orb.isAlive() && orb.position().distanceToSqr(target) <= radius * radius);
            for (ExperienceOrb orb : orbs) {
                if (pull(orb, target, speed)) {
                    orb.playerTouch(player);
                }
            }
        }
    }

    private static boolean pull(Entity entity, Vec3 target, double speed) {
        Vec3 delta = target.subtract(entity.position());
        double distance = delta.length();
        if (distance <= TOUCH_DISTANCE) {
            return true;
        }
        entity.setDeltaMovement(delta.scale(Math.min(speed, distance) / distance));
        entity.hurtMarked = true;
        return false;
    }

    private static void suckParticles(Level level, Player player) {
        RandomSource random = level.getRandom();
        double radius = GearConfig.siphonRadius() * 0.6D;
        for (int i = 0; i < 2; i++) {
            level.addParticle(ParticleTypes.PORTAL, player.getX(), player.getY() + player.getBbHeight() * 0.5D, player.getZ(),
                    (random.nextDouble() - 0.5D) * 2.0D * radius,
                    (random.nextDouble() - 0.5D) * radius,
                    (random.nextDouble() - 0.5D) * 2.0D * radius);
        }
    }

    @Override
    protected void appendDetails(Consumer<Component> tooltip) {
        tooltip.accept(Component.translatable(TOOLTIP + "description").withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable(TOOLTIP + "usage", format(GearConfig.siphonCostPerSecond()), GearConfig.siphonRadius()).withStyle(ChatFormatting.GRAY));
        if (GearConfig.siphonResidual()) {
            tooltip.accept(Component.translatable(TOOLTIP + "residual", GearConfig.siphonFluxPerSecond(), format(GearConfig.siphonFluxRatio())).withStyle(ChatFormatting.GRAY));
        }
    }
}
