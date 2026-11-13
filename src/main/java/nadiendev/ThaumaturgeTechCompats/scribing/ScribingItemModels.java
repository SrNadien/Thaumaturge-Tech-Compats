package nadiendev.ThaumaturgeTechCompats.scribing;


import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class ScribingItemModels extends ModelAssets {
    public ScribingItemModels(PackOutput output) {
        super(output);
    }

    @Override
    protected void register() {
        for (ScribingTier tier : ScribingTier.values()) {
            if (tier == ScribingTier.NEUTRONIUM || tier == ScribingTier.INFINITY) {
                continue;
            }
            basicItem(tier.toolsName());
        }
    }

    @Override
    public String getName() {
        return "Item Models: thaumaturgetechcompats scribing tools";
    }
}
