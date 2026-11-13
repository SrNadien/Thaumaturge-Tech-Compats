package nadiendev.ThaumaturgeTechCompats.auraconverter;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class AuraConverterBlockStates extends ModelAssets {
    public AuraConverterBlockStates(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Aura Converter Block States: " + MOD;
    }

    @Override
    protected void register() {
        for (AuraConverterTier tier : AuraConverterTier.values()) {
            blockModel(modelName(tier, "off"), cubeModel(tier, "front_off"));
            blockModel(modelName(tier, "on"), cubeModel(tier, "front_on"));
            blockState(AuraConverterRegistry.block(tier).get(), state -> variant(
                    mod("block/" + modelName(tier, state.getValue(AuraConverterBlock.ACTIVE) ? "on" : "off")),
                    (int) ((state.getValue(AuraConverterBlock.FACING).toYRot() + 180.0F) % 360.0F)));
        }
    }

    public static String modelName(AuraConverterTier tier, String state) {
        return tier.blockName() + "_" + state;
    }

    private static JsonObject cubeModel(AuraConverterTier tier, String front) {
        String side = texture(tier, "side");
        return model("minecraft:block/cube", textures(
                "down", texture(tier, "bottom"),
                "up", texture(tier, "top"),
                "north", texture(tier, front),
                "south", texture(tier, "back"),
                "east", side,
                "west", side,
                "particle", side));
    }

    private static String texture(AuraConverterTier tier, String part) {
        return mod("block/" + tier.blockName() + "_" + part);
    }
}
