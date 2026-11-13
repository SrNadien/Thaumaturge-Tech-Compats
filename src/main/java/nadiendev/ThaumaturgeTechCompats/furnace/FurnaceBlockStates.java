package nadiendev.ThaumaturgeTechCompats.furnace;

import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import net.minecraft.data.PackOutput;

public class FurnaceBlockStates extends ModelAssets {
    public FurnaceBlockStates(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Furnace Block States: " + MOD;
    }

    @Override
    protected void register() {
        for (DeviceTier tier : DeviceTier.values()) {
            simpleState(FurnaceIntegration.block(tier).get(), "thaumaturge:block/empty");
        }
    }
}
