package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOJson;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CapacitorItemModels implements DataProvider {
    private static final String MOD = EnderIOJson.MOD;

    private final PackOutput output;

    public CapacitorItemModels(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (EnderIOTier tier : EnderIOTier.values()) {
            String name = tier.capacitorName();
            JsonObject textures = new JsonObject();
            textures.addProperty("layer0", MOD + ":item/" + name);
            JsonObject json = new JsonObject();
            json.addProperty("parent", "minecraft:item/generated");
            json.add("textures", textures);
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", json));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO capacitor item models";
    }
}
