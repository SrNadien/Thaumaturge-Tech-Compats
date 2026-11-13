package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MekanismToolItemModels implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public MekanismToolItemModels(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (MekanismToolTier tier : MekanismToolTier.values()) {
            for (MekanismToolKind kind : MekanismToolKind.values()) {
                String name = MekanismToolRegistry.itemId(tier, kind);
                tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", itemModel(name)));
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Mekanism energy tools item models";
    }

    private static JsonObject itemModel(String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD + ":item/" + name);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/handheld");
        json.add("textures", textures);
        return json;
    }
}
