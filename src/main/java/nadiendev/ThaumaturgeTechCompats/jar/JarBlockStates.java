package nadiendev.ThaumaturgeTechCompats.jar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class JarBlockStates extends ModelAssets {
    private static final String JAR_PARENT = "thaumaturge:block/jar_normal";

    public JarBlockStates(PackOutput output) {
        super(output);
    }

    @Override
    protected void register() {
        for (JarTier tier : JarTier.values()) {
            String name = tier.jarName();
            String side = mod("block/" + name + "_side");
            JsonObject model = model(JAR_PARENT, textures(
                    "side", side,
                    "top", mod("block/" + name + "_top"),
                    "bottom", mod("block/" + name + "_bottom"),
                    "particle", side));
            model.addProperty("render_type", "minecraft:translucent");
            blockModel(name, model);
            simpleState(JarRegistry.block(tier).get(), mod("block/" + name));

            JsonObject special = new JsonObject();
            special.addProperty("type", "thaumaturge:jar");
            JsonObject specialModel = new JsonObject();
            specialModel.addProperty("type", "minecraft:special");
            specialModel.addProperty("base", mod("block/" + name));
            specialModel.add("model", special);
            JsonObject plain = new JsonObject();
            plain.addProperty("type", "minecraft:model");
            plain.addProperty("model", mod("block/" + name));
            JsonArray models = new JsonArray();
            models.add(plain);
            models.add(specialModel);
            JsonObject composite = new JsonObject();
            composite.addProperty("type", "minecraft:composite");
            composite.add("models", models);
            itemDefinition(name, definition(composite));
        }
    }

    @Override
    public String getName() {
        return "Block States: " + MOD + " jars";
    }
}
