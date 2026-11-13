package nadiendev.ThaumaturgeTechCompats.compats.create.data;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CasingTier;
import nadiendev.ThaumaturgeTechCompats.compats.create.casing.CreateCasings;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;

public class CreateBlockStates extends BlockStateProvider {
    public CreateBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Create Block States: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerStatesAndModels() {
        for (CasingTier tier : CasingTier.values()) {
            simpleBlockWithItem(CreateCasings.block(tier).get(), cubeAll(CreateCasings.block(tier).get()));
        }
    }
}
