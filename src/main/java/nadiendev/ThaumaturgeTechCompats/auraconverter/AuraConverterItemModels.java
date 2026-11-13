package nadiendev.ThaumaturgeTechCompats.auraconverter;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AuraConverterItemModels extends ItemModelProvider {
    public AuraConverterItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Aura Converter Item Models: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerModels() {
        for (AuraConverterTier tier : AuraConverterTier.values()) {
            withExistingParent(tier.blockName(), modLoc("block/" + AuraConverterBlockStates.modelName(tier, "off")));
        }
    }
}
