package nadiendev.ThaumaturgeTechCompats.braincharger;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;

public class BrainChargerAssets implements DataProvider {
    private static final String BOX_MODEL = "thaumaturge:block/brain_box";
    private static final String CHARGER_MESH = "thaumaturge:models/mesh/arcane_workbench_charger.ttmesh";

    private final PackOutput output;

    public BrainChargerAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (BoostTier tier : BoostTier.values()) {
            String box = tier.boxName();
            tasks.add(save(cache, "blockstates/" + box, boxState(box)));
            tasks.add(save(cache, "models/block/" + box, boxModel(box)));
            tasks.add(save(cache, "models/item/" + box, itemModel(box)));

            String charger = tier.chargerName();
            tasks.add(save(cache, "blockstates/" + charger, simpleState(charger)));
            tasks.add(save(cache, "models/block/" + charger, chargerModel(charger)));
            tasks.add(save(cache, "models/item/" + charger, itemModel(charger)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats mnemonic matrix and charger assets";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/" + path + ".json", json);
    }

    private static JsonObject variant(String model, int x, int y) {
        JsonObject json = new JsonObject();
        json.addProperty("model", MOD + ":block/" + model);
        if (x != 0) {
            json.addProperty("x", x);
        }
        if (y != 0) {
            json.addProperty("y", y);
        }
        return json;
    }

    private static JsonObject boxState(String name) {
        JsonObject variants = new JsonObject();
        variants.add("facing=down", variant(name, 0, 0));
        variants.add("facing=east", variant(name, 90, 270));
        variants.add("facing=north", variant(name, 90, 180));
        variants.add("facing=south", variant(name, 90, 0));
        variants.add("facing=up", variant(name, 180, 0));
        variants.add("facing=west", variant(name, 90, 90));
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject simpleState(String name) {
        JsonObject variants = new JsonObject();
        variants.add("", variant(name, 0, 0));
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject boxModel(String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("box", MOD + ":block/" + name);
        textures.addProperty("particle", "#box");
        JsonObject json = new JsonObject();
        json.addProperty("parent", BOX_MODEL);
        json.add("textures", textures);
        return json;
    }

    private static JsonObject chargerModel(String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("texture", MOD + ":block/" + name);
        textures.addProperty("particle", "#texture");
        JsonObject json = new JsonObject();
        json.addProperty("loader", "thaumaturge:mesh");
        json.addProperty("parent", "block/block");
        json.addProperty("model", CHARGER_MESH);
        json.addProperty("corner_space", true);
        json.add("textures", textures);
        return json;
    }

    private static JsonObject itemModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD + ":block/" + name);
        return json;
    }
}
