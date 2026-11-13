package nadiendev.ThaumaturgeTechCompats.wandtier.datagen;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class WandTierItemModelProvider extends ItemModelProvider {
    public WandTierItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        part("matrix_wand_rod", "minecraft:item/handheld");
        part("matrix_wand_cap", "minecraft:item/generated");
    }

    private void part(String name, String parent) {
        withExistingParent(name, parent).texture("layer0", ThaumaturgeTechCompats.id("item/" + name));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats wand tier item models";
    }
}
