package nadiendev.ThaumaturgeTechCompats.auraconverter;

import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class AuraConverterItemModels extends ModelAssets {
    public AuraConverterItemModels(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Aura Converter Item Models: " + MOD;
    }

    @Override
    protected void register() {
        for (AuraConverterTier tier : AuraConverterTier.values()) {
            blockItem(tier.blockName(), mod("block/" + AuraConverterBlockStates.modelName(tier, "off")));
        }
    }
}
