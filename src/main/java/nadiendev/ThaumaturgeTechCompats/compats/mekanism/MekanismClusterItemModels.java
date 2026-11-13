package nadiendev.ThaumaturgeTechCompats.compats.mekanism;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.ClusterMaterial;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;

public class MekanismClusterItemModels extends ItemModelProvider {
    public MekanismClusterItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        for (ClusterMaterial material : ClusterMaterial.ownMaterials()) {
            basicItem(ThaumaturgeTechCompats.id(material.itemName()));
        }
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Mekanism cluster item models";
    }
}
