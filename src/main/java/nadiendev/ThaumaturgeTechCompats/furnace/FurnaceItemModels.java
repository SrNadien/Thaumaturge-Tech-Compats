package nadiendev.ThaumaturgeTechCompats.furnace;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FurnaceItemModels extends ItemModelProvider {
    public FurnaceItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Furnace Item Models: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerModels() {
        for (DeviceTier tier : DeviceTier.values()) {
            ItemModelBuilder model = getBuilder(FurnaceIntegration.blockName(tier))
                    .parent(new ModelFile.UncheckedModelFile(mcLoc("builtin/entity")));
            model.transforms()
                    .transform(ItemDisplayContext.FIXED).scale(0.625F, 0.625F, 0.625F).translation(0, -3.5F, 0).end()
                    .transform(ItemDisplayContext.GUI).rotation(30, 225, 0).scale(0.625F, 0.625F, 0.625F).translation(0, -2.75F, 0).end()
                    .end();
        }
    }
}
