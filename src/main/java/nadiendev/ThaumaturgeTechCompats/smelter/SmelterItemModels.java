package nadiendev.ThaumaturgeTechCompats.smelter;

import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class SmelterItemModels extends ModelAssets {
    public SmelterItemModels(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Smelter Item Models: " + MOD;
    }

    @Override
    protected void register() {
        for (SmelterTier tier : SmelterTier.values()) {
            blockItem(tier.blockName(), mod("block/" + tier.blockName() + "_off"));
        }
    }
}
