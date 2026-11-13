package nadiendev.ThaumaturgeTechCompats.stabilizer;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.EntityItemModels;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class StabilizerItemModels extends ItemModelProvider {
    public StabilizerItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Stabilizer Item Models: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerModels() {
        for (StabilizerTier tier : StabilizerTier.values()) {
            EntityItemModels.nodeDevice(this, tier.blockName(), modLoc("block/" + tier.blockName()));
        }
    }
}
