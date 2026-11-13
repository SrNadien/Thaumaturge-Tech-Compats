package nadiendev.ThaumaturgeTechCompats.datagen.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class ModelAssets implements DataProvider {
    public static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;
    private final Map<String, JsonElement> files = new LinkedHashMap<>();

    protected ModelAssets(PackOutput output) {
        this.output = output;
    }

    protected abstract void register();

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        files.clear();
        register();
        Path assets = output.getOutputFolder().resolve("assets/" + MOD);
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : files.entrySet()) {
            tasks.add(DataProvider.saveStable(cache, entry.getValue(), assets.resolve(entry.getKey() + ".json")));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    protected void file(String path, JsonElement json) {
        files.put(path, json);
    }

    public static String mod(String path) {
        return MOD + ":" + path;
    }

    public static JsonObject textures(String... pairs) {
        JsonObject textures = new JsonObject();
        for (int i = 0; i < pairs.length; i += 2) {
            textures.addProperty(pairs[i], pairs[i + 1]);
        }
        return textures;
    }

    public static JsonObject model(String parent, JsonObject textures) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", parent);
        if (textures != null) {
            json.add("textures", textures);
        }
        return json;
    }

    public static JsonObject modelDefinition(String model) {
        JsonObject inner = new JsonObject();
        inner.addProperty("type", "minecraft:model");
        inner.addProperty("model", model);
        return definition(inner);
    }

    public static JsonObject definition(JsonObject model) {
        JsonObject json = new JsonObject();
        json.add("model", model);
        return json;
    }

    public static JsonObject display(Object... entries) {
        JsonObject display = new JsonObject();
        for (int i = 0; i < entries.length; i += 4) {
            JsonObject transform = new JsonObject();
            float[] rotation = (float[]) entries[i + 1];
            float[] translation = (float[]) entries[i + 2];
            float[] scale = (float[]) entries[i + 3];
            if (rotation != null) {
                transform.add("rotation", vec(rotation));
            }
            if (translation != null) {
                transform.add("translation", vec(translation));
            }
            if (scale != null) {
                transform.add("scale", vec(scale));
            }
            display.add((String) entries[i], transform);
        }
        return display;
    }

    public static float[] v(float x, float y, float z) {
        return new float[]{x, y, z};
    }

    private static JsonArray vec(float[] values) {
        JsonArray array = new JsonArray();
        for (float value : values) {
            array.add(value);
        }
        return array;
    }

    protected void item(String name, String parent, JsonObject textures) {
        file("models/item/" + name, model(parent, textures));
        file("items/" + name, modelDefinition(mod("item/" + name)));
    }

    protected void basicItem(String name) {
        item(name, "minecraft:item/generated", textures("layer0", mod("item/" + name)));
    }

    protected void blockItem(String name, String blockModel) {
        file("models/item/" + name, model(blockModel, null));
        file("items/" + name, modelDefinition(mod("item/" + name)));
    }

    protected void blockModel(String name, JsonObject json) {
        file("models/block/" + name, json);
    }

    protected void itemDefinition(String name, JsonObject json) {
        file("items/" + name, json);
    }

    protected void itemModel(String name, JsonObject json) {
        file("models/item/" + name, json);
    }

    protected void simpleState(Block block, String model) {
        blockState(block, state -> variant(model, 0));
    }

    protected void blockState(Block block, Function<BlockState, JsonObject> variants) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        Map<String, JsonObject> sorted = new TreeMap<>();
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            sorted.put(stateKey(state), variants.apply(state));
        }
        JsonObject all = new JsonObject();
        sorted.forEach(all::add);
        JsonObject json = new JsonObject();
        json.add("variants", all);
        file("blockstates/" + name, json);
    }

    public static JsonObject variant(String model, int rotationY) {
        JsonObject json = new JsonObject();
        json.addProperty("model", model);
        if (rotationY != 0) {
            json.addProperty("y", rotationY);
        }
        return json;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static String stateKey(BlockState state) {
        Map<String, String> values = new TreeMap<>();
        for (Property property : state.getProperties()) {
            values.put(property.getName(), property.getName((Comparable) state.getValue(property)));
        }
        List<String> parts = new ArrayList<>();
        values.forEach((key, value) -> parts.add(key + "=" + value));
        return String.join(",", parts);
    }
}
