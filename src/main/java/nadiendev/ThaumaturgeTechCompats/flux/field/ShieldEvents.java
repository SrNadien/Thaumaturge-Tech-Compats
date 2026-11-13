package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import java.util.List;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID)
public final class ShieldEvents {
    private ShieldEvents() {
    }

    @SubscribeEvent
    public static void onDetonate(ExplosionEvent.Detonate event) {
        if (!(event.getLevel() instanceof ServerLevel level) || !FieldConfig.shieldEnabled()) {
            return;
        }
        List<ShieldGeneratorBlockEntity> shields = ShieldNetwork.all(level);
        if (shields.isEmpty()) {
            return;
        }
        boolean blocks = FieldConfig.shieldExplosionBlocks();
        boolean entities = FieldConfig.shieldExplosionReduction() >= 1.0D;
        for (ShieldGeneratorBlockEntity shield : shields) {
            if (!shield.isActive()) {
                continue;
            }
            boolean touches = blocks && event.getAffectedBlocks().stream().anyMatch(pos -> shield.covers(Vec3.atCenterOf(pos)))
                    || entities && event.getAffectedEntities().stream().anyMatch(entity -> shield.covers(entity.position()));
            if (!touches || !shield.pay(level, FieldConfig.shieldExplosionCost(shield.tier()))) {
                continue;
            }
            if (blocks) {
                List<BlockPos> affected = event.getAffectedBlocks();
                affected.removeIf(pos -> shield.covers(Vec3.atCenterOf(pos)));
            }
            if (entities) {
                List<Entity> affected = event.getAffectedEntities();
                affected.removeIf(entity -> shield.covers(entity.position()));
            }
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        double reduction = FieldConfig.shieldExplosionReduction();
        if (level.isClientSide || reduction <= 0.0D || !event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
            return;
        }
        if (ShieldNetwork.covering(level, entity.position()).isEmpty()) {
            return;
        }
        if (reduction >= 1.0D) {
            event.setCanceled(true);
        } else {
            event.setAmount((float) (event.getAmount() * (1.0D - reduction)));
        }
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        ShieldNetwork.clear();
    }
}
