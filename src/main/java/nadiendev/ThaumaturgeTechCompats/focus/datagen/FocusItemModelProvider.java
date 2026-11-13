package nadiendev.ThaumaturgeTechCompats.focus.datagen;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.datagen.model.ModelAssets;
import net.minecraft.data.PackOutput;

public class FocusItemModelProvider extends ModelAssets {
    public FocusItemModelProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void register() {
        tinted("matrix_focus");
        tinted("blaze_cube_focus");
    }

    private void tinted(String name) {
        basicItem(name);
        JsonObject tint = new JsonObject();
        tint.addProperty("type", "thaumaturge:focus_color");
        JsonArray tints = new JsonArray();
        tints.add(tint);
        JsonObject model = new JsonObject();
        model.addProperty("type", "minecraft:model");
        model.addProperty("model", mod("item/" + name));
        model.add("tints", tints);
        itemDefinition(name, definition(model));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats focus item models";
    }
}
