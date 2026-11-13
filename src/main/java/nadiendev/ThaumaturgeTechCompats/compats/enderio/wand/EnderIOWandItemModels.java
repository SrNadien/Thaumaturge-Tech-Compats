package nadiendev.ThaumaturgeTechCompats.compats.enderio.wand;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOJson;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnderIOWandItemModels implements DataProvider {
    private static final String MOD = EnderIOJson.MOD;

    private final PackOutput output;

    public EnderIOWandItemModels(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            tasks.add(save(cache, material.rodName(), "minecraft:item/handheld"));
            tasks.add(save(cache, material.capName(), "minecraft:item/generated"));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO wand part item models";
    }

    private CompletableFuture<?> save(CachedOutput cache, String name, String parent) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD + ":item/" + name);
        JsonObject json = new JsonObject();
        json.addProperty("parent", parent);
        json.add("textures", textures);
        return ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", json);
    }
}
