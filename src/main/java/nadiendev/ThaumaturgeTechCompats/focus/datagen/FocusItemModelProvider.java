package nadiendev.ThaumaturgeTechCompats.focus.datagen;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FocusItemModelProvider extends ItemModelProvider {
    public FocusItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        basicItem(ThaumaturgeTechCompats.id("matrix_focus"));
        basicItem(ThaumaturgeTechCompats.id("blaze_cube_focus"));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats focus item models";
    }
}
