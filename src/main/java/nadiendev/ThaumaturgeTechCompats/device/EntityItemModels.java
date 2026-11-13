package nadiendev.ThaumaturgeTechCompats.device;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;

public final class EntityItemModels {
    private EntityItemModels() {
    }

    public static JsonObject nodeDeviceModel(String particle) {
        JsonObject model = ModelAssets.model("minecraft:block/block", ModelAssets.textures("particle", particle));
        model.add("display", ModelAssets.display(
                "gui", ModelAssets.v(30, 45, 0), ModelAssets.v(0, 0, 0), ModelAssets.v(0.7F, 0.7F, 0.7F),
                "ground", null, ModelAssets.v(0, 3, 0), ModelAssets.v(0.5F, 0.5F, 0.5F),
                "fixed", ModelAssets.v(0, 180, 0), null, ModelAssets.v(0.75F, 0.75F, 0.75F),
                "thirdperson_righthand", ModelAssets.v(75, 45, 0), ModelAssets.v(0, 2.5F, 0), ModelAssets.v(0.375F, 0.375F, 0.375F),
                "firstperson_righthand", ModelAssets.v(0, 45, 0), null, ModelAssets.v(0.4F, 0.4F, 0.4F),
                "firstperson_lefthand", ModelAssets.v(0, 225, 0), null, ModelAssets.v(0.4F, 0.4F, 0.4F)));
        return model;
    }

    public static JsonObject nodeDeviceDefinition(String base, boolean transducer) {
        JsonObject renderer = new JsonObject();
        renderer.addProperty("type", "thaumaturge:node_stabilizer");
        renderer.addProperty("advanced", false);
        if (transducer) {
            renderer.addProperty("transducer", true);
        }
        JsonObject special = new JsonObject();
        special.addProperty("type", "minecraft:special");
        special.addProperty("base", base);
        special.add("model", renderer);
        return ModelAssets.definition(special);
    }
}
