package nadiendev.ThaumaturgeTechCompats.transducer;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.device.EntityItemModels;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TransducerItemModels extends ItemModelProvider {
    public TransducerItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Transducer Item Models: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerModels() {
        for (DeviceTier tier : DeviceTier.values()) {
            String name = TransducerIntegration.blockName(tier);
            EntityItemModels.nodeDevice(this, name, modLoc("block/" + name));
        }
    }
}
