package nadiendev.ThaumaturgeTechCompats.furnace;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FurnaceBlockStates extends BlockStateProvider {
    private static final ModelFile EMPTY = new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath("thaumaturge", "block/empty"));

    public FurnaceBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Furnace Block States: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerStatesAndModels() {
        for (DeviceTier tier : DeviceTier.values()) {
            simpleBlock(FurnaceIntegration.block(tier).get(), EMPTY);
        }
    }
}
