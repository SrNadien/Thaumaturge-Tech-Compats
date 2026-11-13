package nadiendev.ThaumaturgeTechCompats.scribing;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ScribingItemModels extends ItemModelProvider {
    public ScribingItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        for (ScribingTier tier : ScribingTier.values()) {
            if (tier == ScribingTier.NEUTRONIUM || tier == ScribingTier.INFINITY) {
                continue;
            }
            basicItem(ThaumaturgeTechCompats.id(tier.toolsName()));
        }
    }

    @Override
    public String getName() {
        return "Item Models: " + ThaumaturgeTechCompats.MOD_ID + " scribing tools";
    }
}
