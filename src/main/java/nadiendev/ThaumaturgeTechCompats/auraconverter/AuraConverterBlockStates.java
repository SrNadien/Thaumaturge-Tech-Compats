package nadiendev.ThaumaturgeTechCompats.auraconverter;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AuraConverterBlockStates extends BlockStateProvider {
    public AuraConverterBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, ThaumaturgeTechCompats.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "Aura Converter Block States: " + ThaumaturgeTechCompats.MOD_ID;
    }

    @Override
    protected void registerStatesAndModels() {
        for (AuraConverterTier tier : AuraConverterTier.values()) {
            BlockModelBuilder off = cubeModel(tier, "off", "front_off");
            BlockModelBuilder on = cubeModel(tier, "on", "front_on");
            getVariantBuilder(AuraConverterRegistry.block(tier).get()).forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(state.getValue(AuraConverterBlock.ACTIVE) ? on : off)
                    .rotationY((int) ((state.getValue(AuraConverterBlock.FACING).toYRot() + 180.0F) % 360.0F))
                    .build());
        }
    }

    public static String modelName(AuraConverterTier tier, String state) {
        return tier.blockName() + "_" + state;
    }

    private BlockModelBuilder cubeModel(AuraConverterTier tier, String state, String front) {
        ResourceLocation side = texture(tier, "side");
        return models().cube(modelName(tier, state), texture(tier, "bottom"), texture(tier, "top"), texture(tier, front), texture(tier, "back"), side, side)
                .texture("particle", side);
    }

    private ResourceLocation texture(AuraConverterTier tier, String part) {
        return modLoc("block/" + tier.blockName() + "_" + part);
    }
}
