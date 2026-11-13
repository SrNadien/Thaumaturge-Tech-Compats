package nadiendev.ThaumaturgeTechCompats.purity;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class PurityItemModels extends ItemModelProvider {
    private static final String PLACEHOLDER_TEXTURE = "item/" + PurityRegistry.POTION_NAME;

    public PurityItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        basicItem(PurityRegistry.PURIFICATION_POTION.get());
        placeholder(PurityRegistry.BIOME_POTION_NAME);
        placeholder(PurityRegistry.CLARITY_POTION_NAME);
        placeholder(PurityRegistry.ABSOLUTION_POTION_NAME);
    }

    private void placeholder(String name) {
        withExistingParent(name, mcLoc("item/generated")).texture("layer0", modLoc(PLACEHOLDER_TEXTURE));
    }

    @Override
    public String getName() {
        return "Item Models: " + ThaumaturgeTechCompats.MOD_ID + " purity";
    }
}
