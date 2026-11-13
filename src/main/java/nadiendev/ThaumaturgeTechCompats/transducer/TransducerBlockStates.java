package nadiendev.ThaumaturgeTechCompats.transducer;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.device.MeshLoaderBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TransducerBlockStates extends BlockStateProvider {
    public TransducerBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Transducer Block States: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerStatesAndModels() {
        for (DeviceTier tier : DeviceTier.values()) {
            String name = TransducerIntegration.blockName(tier);
            BlockModelBuilder model = models().getBuilder(name)
                    .parent(models().getExistingFile(mcLoc("block/block")))
                    .texture("texture", modLoc("block/" + name))
                    .texture("particle", "#texture");
            model.customLoader(MeshLoaderBuilder::new).end();
            simpleBlock(TransducerIntegration.block(tier).get(), model);
        }
    }
}
