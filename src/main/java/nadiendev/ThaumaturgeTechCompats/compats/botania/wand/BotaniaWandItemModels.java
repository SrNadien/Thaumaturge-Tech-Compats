package nadiendev.ThaumaturgeTechCompats.compats.botania.wand;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BotaniaWandItemModels implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public BotaniaWandItemModels(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (BotaniaWandRod rod : BotaniaWandRod.values()) {
            tasks.add(save(cache, rod.itemName(), "minecraft:item/handheld"));
        }
        for (BotaniaWandCap cap : BotaniaWandCap.values()) {
            tasks.add(save(cache, cap.itemName(), "minecraft:item/generated"));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats botania wand item models";
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
