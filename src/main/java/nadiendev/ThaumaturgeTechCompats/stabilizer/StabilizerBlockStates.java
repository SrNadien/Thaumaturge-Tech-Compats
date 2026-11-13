package nadiendev.ThaumaturgeTechCompats.stabilizer;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class StabilizerBlockStates extends ModelAssets {
    public StabilizerBlockStates(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Stabilizer Block States: " + MOD;
    }

    @Override
    protected void register() {
        for (StabilizerTier tier : StabilizerTier.values()) {
            JsonObject model = model("minecraft:block/block", textures("texture", mod("block/" + tier.blockName()), "particle", "#texture"));
            model.addProperty("loader", "thaumaturge:mesh");
            model.addProperty("model", "thaumaturge:models/mesh/node_stabilizer.ttmesh");
            blockModel(tier.blockName(), model);
            simpleState(StabilizerIntegration.block(tier).get(), mod("block/" + tier.blockName()));
        }
    }
}
