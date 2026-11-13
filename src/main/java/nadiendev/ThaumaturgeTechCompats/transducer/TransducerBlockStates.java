package nadiendev.ThaumaturgeTechCompats.transducer;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import net.minecraft.data.PackOutput;

public class TransducerBlockStates extends ModelAssets {
    public TransducerBlockStates(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Transducer Block States: " + MOD;
    }

    @Override
    protected void register() {
        for (DeviceTier tier : DeviceTier.values()) {
            String name = TransducerIntegration.blockName(tier);
            JsonObject model = model("minecraft:block/block", textures("texture", mod("block/" + name), "particle", "#texture"));
            model.addProperty("loader", "thaumaturge:mesh");
            model.addProperty("model", "thaumaturge:models/mesh/node_stabilizer.ttmesh");
            blockModel(name, model);
            simpleState(TransducerIntegration.block(tier).get(), mod("block/" + name));
        }
    }
}
