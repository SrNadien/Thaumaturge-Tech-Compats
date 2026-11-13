package nadiendev.ThaumaturgeTechCompats.furnace;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import net.minecraft.data.PackOutput;

public class FurnaceItemModels extends ModelAssets {
    public FurnaceItemModels(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "Furnace Item Models: " + MOD;
    }

    @Override
    protected void register() {
        for (DeviceTier tier : DeviceTier.values()) {
            String name = FurnaceIntegration.blockName(tier);
            JsonObject model = model("minecraft:block/block", textures("particle", "minecraft:block/furnace_top"));
            model.add("display", display(
                    "fixed", null, v(0, -3.5F, 0), v(0.625F, 0.625F, 0.625F),
                    "gui", v(30, 225, 0), v(0, -2.75F, 0), v(0.625F, 0.625F, 0.625F)));
            itemModel(name, model);

            JsonObject renderer = new JsonObject();
            renderer.addProperty("type", "thaumaturge:advanced_alchemical_furnace");
            JsonObject special = new JsonObject();
            special.addProperty("type", "minecraft:special");
            special.addProperty("base", mod("item/" + name));
            special.add("model", renderer);
            itemDefinition(name, definition(special));
        }
    }
}
