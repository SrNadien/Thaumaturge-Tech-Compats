package nadiendev.ThaumaturgeTechCompats.flux.gear;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class GearAssets implements DataProvider {
    public static final String SIPHON_TEXTURE = "thaumaturge:item/resonator";
    public static final String CANNON_TEXTURE = "thaumaturge:item/mechanism_complex";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public GearAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.allOf(
                ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + GearRegistry.SIPHON_NAME + ".json", itemModel(SIPHON_TEXTURE)),
                ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + GearRegistry.CANNON_NAME + ".json", itemModel(CANNON_TEXTURE)));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats flux gear assets";
    }

    private static JsonObject itemModel(String texture) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", texture);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/handheld");
        json.add("textures", textures);
        return json;
    }
}
