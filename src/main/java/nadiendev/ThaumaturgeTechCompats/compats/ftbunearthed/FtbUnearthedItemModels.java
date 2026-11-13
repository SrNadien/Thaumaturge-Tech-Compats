package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FtbUnearthedItemModels extends ItemModelProvider {
    private static final float[] BRUSHING = {0.25F, 0.5F, 0.75F};

    public FtbUnearthedItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        for (FtbUnearthedBrushTier tier : FtbUnearthedBrushTier.values()) {
            String name = tier.itemId();
            ItemModelBuilder model = withExistingParent(name, mcLoc("item/brush")).texture("layer0", modLoc("item/" + name));
            for (int stage = 0; stage < BRUSHING.length; stage++) {
                ItemModelBuilder brushing = withExistingParent(name + "_brushing_" + stage, mcLoc("item/brush_brushing_" + stage))
                        .texture("layer0", modLoc("item/" + name));
                model.override().predicate(mcLoc("brushing"), BRUSHING[stage]).model(brushing).end();
            }
        }
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats FTB Unearthed item models";
    }
}
