package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IndustrialForegoingAddonsItemModels extends ItemModelProvider {
    public IndustrialForegoingAddonsItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        for (AddonTier tier : AddonTier.values()) {
            for (AddonKind kind : AddonKind.values()) {
                basicItem(ThaumaturgeTechCompats.id(IndustrialForegoingAddonsIntegration.itemId(kind, tier)));
            }
        }
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Industrial Foregoing addon item models";
    }
}
