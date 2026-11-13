package nadiendev.ThaumaturgeTechCompats.stabilizer;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.MeshLoaderBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class StabilizerBlockStates extends BlockStateProvider {
    public StabilizerBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Stabilizer Block States: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerStatesAndModels() {
        for (StabilizerTier tier : StabilizerTier.values()) {
            BlockModelBuilder model = models().getBuilder(tier.blockName())
                    .parent(models().getExistingFile(mcLoc("block/block")))
                    .texture("texture", modLoc("block/" + tier.blockName()))
                    .texture("particle", "#texture");
            model.customLoader(MeshLoaderBuilder::new).end();
            simpleBlock(StabilizerIntegration.block(tier).get(), model);
        }
    }
}
