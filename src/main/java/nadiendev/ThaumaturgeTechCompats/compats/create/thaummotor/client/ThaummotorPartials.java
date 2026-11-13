package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor.ThaummotorAssets;
import net.minecraft.resources.Identifier;

public final class ThaummotorPartials {
    public static final PartialModel SHAFT_HALF = PartialModel.of(
            Identifier.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, ThaummotorAssets.SHAFT_HALF_MODEL));

    private ThaummotorPartials() {
    }
}
