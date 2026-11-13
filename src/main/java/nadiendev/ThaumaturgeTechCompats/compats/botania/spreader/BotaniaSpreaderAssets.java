package nadiendev.ThaumaturgeTechCompats.compats.botania.spreader;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BotaniaSpreaderAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String CORE = "botania:block/gaia_spreader_core";

    private final PackOutput output;

    public BotaniaSpreaderAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (SpreaderTier tier : SpreaderTier.values()) {
            String name = tier.blockName();
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/blockstates/" + name + ".json", blockState(name)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/block/" + name + ".json", blockModel(name)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", itemModel(name)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats botania spreader assets";
    }

    private static JsonObject blockState(String name) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", MOD + ":block/" + name);
        JsonObject variants = new JsonObject();
        variants.add("", variant);
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject textures(String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("back", MOD + ":block/" + name + "_back");
        textures.addProperty("inside", MOD + ":block/" + name + "_inside");
        textures.addProperty("outside", MOD + ":block/" + name + "_outside");
        textures.addProperty("side", MOD + ":block/" + name + "_side");
        return textures;
    }

    private static JsonObject blockModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "botania:block/shapes/spreader");
        json.add("textures", textures(name));
        return json;
    }

    private static JsonObject itemModel(String name) {
        JsonObject textures = textures(name);
        textures.addProperty("core", CORE);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "botania:block/shapes/spreader_item");
        json.add("textures", textures);
        return json;
    }
}
