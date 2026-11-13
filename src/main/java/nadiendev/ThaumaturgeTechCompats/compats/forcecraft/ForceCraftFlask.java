package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

public final class ForceCraftFlask {
    public static final ResourceLocation FLASK_ENTITY = ResourceLocation.fromNamespaceAndPath(ForceCraftIntegration.MOD_ID, "force_flask");

    private ForceCraftFlask() {
    }

    public static void onImpact(ProjectileImpactEvent event) {
        if (!(event.getRayTraceResult() instanceof EntityHitResult hit) || !ForceCraftConfig.flaskEnabled()) {
            return;
        }
        Entity projectile = event.getProjectile();
        if (!FLASK_ENTITY.equals(BuiltInRegistries.ENTITY_TYPE.getKey(projectile.getType()))) {
            return;
        }
        ResourceLocation target = BuiltInRegistries.ENTITY_TYPE.getKey(hit.getEntity().getType());
        if (ForceCraftConfig.flaskBlocks(target)) {
            event.setCanceled(true);
        }
    }
}
