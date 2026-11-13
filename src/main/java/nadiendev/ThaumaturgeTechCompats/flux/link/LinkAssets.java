package nadiendev.ThaumaturgeTechCompats.flux.link;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LinkAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String[] FACES = {"down", "up", "north", "south", "west", "east"};
    private static final String EXTRACTOR_TEXTURE = "thaumaturge:block/metal_void";
    private static final String LINKER_TEXTURE = "thaumaturge:item/hand_mirror";

    private final PackOutput output;

    public LinkAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            String name = LinkRegistry.linkName(tier);
            tasks.add(save(cache, "blockstates/" + name, blockState(name)));
            tasks.add(save(cache, "models/block/" + name, linkModel(tier)));
            tasks.add(save(cache, "models/item/" + name, blockItemModel(name)));
        }
        String extractor = LinkRegistry.EXTRACTOR_NAME;
        tasks.add(save(cache, "blockstates/" + extractor, blockState(extractor)));
        tasks.add(save(cache, "models/block/" + extractor, extractorModel()));
        tasks.add(save(cache, "models/item/" + extractor, blockItemModel(extractor)));
        tasks.add(save(cache, "models/item/" + LinkRegistry.LINKER_NAME, linkerModel()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats transmission link assets";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/" + path + ".json", json);
    }

    private static String texture(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> "thaumaturge:block/metal_thaumium";
            case ALCHEMICAL_BRASS -> "thaumaturge:block/metal_brass";
            case VOID_METAL -> "thaumaturge:block/metal_void";
        };
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

    private static JsonObject blockItemModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD + ":block/" + name);
        return json;
    }

    private static JsonObject linkerModel() {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", LINKER_TEXTURE);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/handheld");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject linkModel(FluxTier tier) {
        JsonArray elements = new JsonArray();
        elements.add(cuboid(4, 0, 4, 12, 2, 12));
        elements.add(cuboid(6, 2, 6, 10, 9, 10));
        elements.add(cuboid(4, 9, 4, 12, 12, 12));
        return model(texture(tier), elements);
    }

    private static JsonObject extractorModel() {
        JsonArray elements = new JsonArray();
        elements.add(cuboid(2, 0, 2, 14, 3, 14));
        elements.add(cuboid(5, 3, 5, 11, 10, 11));
        elements.add(cuboid(3, 10, 3, 13, 14, 13));
        return model(EXTRACTOR_TEXTURE, elements);
    }

    private static JsonObject model(String texture, JsonArray elements) {
        JsonObject textures = new JsonObject();
        textures.addProperty("metal", texture);
        textures.addProperty("particle", texture);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/block");
        json.addProperty("ambientocclusion", false);
        json.add("textures", textures);
        json.add("elements", elements);
        return json;
    }

    private static JsonObject cuboid(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
        JsonObject faces = new JsonObject();
        for (String face : FACES) {
            JsonObject entry = new JsonObject();
            entry.addProperty("texture", "#metal");
            faces.add(face, entry);
        }
        JsonObject element = new JsonObject();
        element.add("from", numbers(fromX, fromY, fromZ));
        element.add("to", numbers(toX, toY, toZ));
        element.add("faces", faces);
        return element;
    }

    private static JsonArray numbers(int x, int y, int z) {
        JsonArray array = new JsonArray();
        array.add(x);
        array.add(y);
        array.add(z);
        return array;
    }
}
