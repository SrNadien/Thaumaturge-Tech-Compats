package nadiendev.ThaumaturgeTechCompats.golem.datagen;

import nadiendev.ThaumaturgeTechCompats.tiercontent.Tier;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class GolemItemModelProvider extends ModelAssets {
    public GolemItemModelProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void register() {
        for (Tier tier : Tier.values()) {
            if (tier == Tier.NEUTRONIUM || tier == Tier.INFINITY) {
                continue;
            }
            basicItem(tier.plateName());
        }
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats golem item models";
    }
}
