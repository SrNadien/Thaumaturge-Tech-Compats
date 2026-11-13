package nadiendev.ThaumaturgeTechCompats.stabilizer;

import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import nadiendev.ThaumaturgeTechCompats.device.EntityItemModels;
import net.minecraft.data.PackOutput;

public class StabilizerItemModels extends ModelAssets {
    public StabilizerItemModels(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Stabilizer Item Models: " + MOD;
    }

    @Override
    protected void register() {
        for (StabilizerTier tier : StabilizerTier.values()) {
            String name = tier.blockName();
            itemModel(name, EntityItemModels.nodeDeviceModel(mod("block/" + name)));
            itemDefinition(name, EntityItemModels.nodeDeviceDefinition(mod("item/" + name), false));
        }
    }
}
