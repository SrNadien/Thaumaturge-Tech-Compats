package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.core.Direction;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;

public class OritechAssets implements DataProvider {
    private static final String ORITECH = OritechFeatures.MOD_ID;

    private final PackOutput output;

    public OritechAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (OritechNode node : OritechNode.values()) {
            String name = node.blockName();
            tasks.add(save(cache, "blockstates/" + name, simpleState(name)));
            tasks.add(save(cache, "models/block/" + name, nodeModel(name)));
            tasks.add(save(cache, "models/item/" + name, parent(MOD + ":block/" + name)));
        }
        for (OritechTier tier : OritechTier.values()) {
            for (OritechAddon addon : OritechAddon.values()) {
                String name = addon.blockName(tier);
                tasks.add(save(cache, "blockstates/" + name, addonState(name)));
                tasks.add(save(cache, "models/block/" + name, addonModel(addon, ORITECH + ":block/" + addon.baseName(), MOD + ":block/" + name + "_off")));
                tasks.add(save(cache, "models/block/" + name + "_on", addonModel(addon, ORITECH + ":block/" + addon.baseName() + "_on", MOD + ":block/" + name + "_on")));
                tasks.add(save(cache, "models/item/" + name, addonModel(addon, ORITECH + ":item/" + addon.baseName(), MOD + ":block/" + name + "_off")));
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Oritech assets";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/" + path + ".json", json);
    }

    private static JsonObject parent(String model) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", model);
        return json;
    }

    private static JsonObject simpleState(String name) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", MOD + ":block/" + name);
        JsonObject variants = new JsonObject();
        variants.add("", variant);
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject nodeModel(String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("all", MOD + ":block/" + name);
        JsonObject json = parent(ORITECH + ":block/resource_node_iron");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject addonModel(OritechAddon addon, String parentModel, String texture) {
        JsonObject textures = new JsonObject();
        textures.addProperty(addon.textureKey(), texture);
        JsonObject json = parent(parentModel);
        json.add("textures", textures);
        return json;
    }

    private static JsonObject rotation(String model, int x, int y) {
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

    private static JsonObject addonState(String name) {
        JsonObject variants = new JsonObject();
        for (boolean used : new boolean[]{true, false}) {
            String model = used ? name + "_on" : name;
            String suffix = ",addon_used=" + used;
            variants.add("face=wall,facing=" + Direction.NORTH.getName() + suffix, rotation(model, 270, 180));
            variants.add("face=wall,facing=" + Direction.SOUTH.getName() + suffix, rotation(model, 270, 0));
            variants.add("face=wall,facing=" + Direction.WEST.getName() + suffix, rotation(model, 270, 90));
            variants.add("face=wall,facing=" + Direction.EAST.getName() + suffix, rotation(model, 270, 270));
            variants.add("face=floor,facing=" + Direction.NORTH.getName() + suffix, rotation(model, 0, 0));
            variants.add("face=floor,facing=" + Direction.SOUTH.getName() + suffix, rotation(model, 0, 180));
            variants.add("face=floor,facing=" + Direction.WEST.getName() + suffix, rotation(model, 0, 270));
            variants.add("face=floor,facing=" + Direction.EAST.getName() + suffix, rotation(model, 0, 90));
            variants.add("face=ceiling,facing=" + Direction.NORTH.getName() + suffix, rotation(model, 180, 0));
            variants.add("face=ceiling,facing=" + Direction.SOUTH.getName() + suffix, rotation(model, 180, 180));
            variants.add("face=ceiling,facing=" + Direction.WEST.getName() + suffix, rotation(model, 180, 270));
            variants.add("face=ceiling,facing=" + Direction.EAST.getName() + suffix, rotation(model, 180, 90));
        }
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }
}
