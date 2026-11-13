package nadiendev.ThaumaturgeTechCompats.golem.datagen;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.Tier;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class GolemItemModelProvider extends ItemModelProvider {
    public GolemItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        for (Tier tier : Tier.values()) {
            if (tier == Tier.NEUTRONIUM || tier == Tier.INFINITY) {
                continue;
            }
            basicItem(ThaumaturgeTechCompats.id(tier.plateName()));
        }
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats golem item models";
    }
}
