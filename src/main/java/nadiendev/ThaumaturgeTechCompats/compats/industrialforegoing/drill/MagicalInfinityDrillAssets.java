package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;

public class MagicalInfinityDrillAssets implements DataProvider {
    private static final String NAME = MagicalInfinityDrillRegistry.NAME;
    private static final String PARENT = MagicalInfinityDrillIntegration.MOD_ID + ":item/infinity_drill";

    private final PackOutput output;

    public MagicalInfinityDrillAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + NAME + ".json", itemModel());
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Magical Infinity Drill assets";
    }

    private static JsonObject itemModel() {
        JsonObject textures = new JsonObject();
        textures.addProperty("2", MOD + ":item/" + NAME);
        textures.addProperty("main", MOD + ":item/" + NAME + "_head");
        textures.addProperty("particle", MOD + ":item/" + NAME);
        JsonObject json = new JsonObject();
        json.addProperty("parent", PARENT);
        json.add("textures", textures);
        return json;
    }
}
