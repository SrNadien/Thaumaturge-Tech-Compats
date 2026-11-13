package nadiendev.ThaumaturgeTechCompats.transducer;

import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.device.EntityItemModels;
import net.minecraft.data.PackOutput;

public class TransducerItemModels extends ModelAssets {
    public TransducerItemModels(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Transducer Item Models: " + MOD;
    }

    @Override
    protected void register() {
        for (DeviceTier tier : DeviceTier.values()) {
            String name = TransducerIntegration.blockName(tier);
            itemModel(name, EntityItemModels.nodeDeviceModel(mod("block/" + name)));
            itemDefinition(name, EntityItemModels.nodeDeviceDefinition(mod("item/" + name), true));
        }
    }
}
