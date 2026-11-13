package nadiendev.ThaumaturgeTechCompats.smelter;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SmelterItemModels extends ItemModelProvider {
    public SmelterItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Smelter Item Models: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerModels() {
        for (SmelterTier tier : SmelterTier.values()) {
            getBuilder(tier.blockName()).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + tier.blockName() + "_off")));
        }
    }
}
