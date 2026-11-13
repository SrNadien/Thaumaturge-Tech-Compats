package nadiendev.ThaumaturgeTechCompats.flux.field;

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

public class FieldAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String BASE_METAL = "thaumaturge:block/base_metal";
    private static final String THRUSTER_ITEM_TEXTURE = "thaumaturge:item/thaumostatic_harness";

    private final PackOutput output;

    public FieldAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            String shield = FieldRegistry.shieldName(tier);
            tasks.add(save(cache, "blockstates/" + shield, blockState(shield)));
            tasks.add(save(cache, "models/block/" + shield, cubeAll(markerTexture(tier))));
            tasks.add(save(cache, "models/item/" + shield, blockItem(shield)));
            String terraformer = FieldRegistry.terraformerName(tier);
            tasks.add(save(cache, "blockstates/" + terraformer, blockState(terraformer)));
            tasks.add(save(cache, "models/block/" + terraformer, cubeColumn(markerTexture(tier), BASE_METAL)));
            tasks.add(save(cache, "models/item/" + terraformer, blockItem(terraformer)));
        }
        tasks.add(save(cache, "models/item/" + FieldRegistry.THRUSTER_NAME, generated(THRUSTER_ITEM_TEXTURE)));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats dimensional flux field assets";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/" + path + ".json", json);
    }

    private static String markerTexture(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> "thaumaturge:block/metal_thaumium";
            case ALCHEMICAL_BRASS -> "thaumaturge:block/metal_brass";
            case VOID_METAL -> "thaumaturge:block/metal_void";
        };
    }

    private static JsonObject blockState(String name) {
        JsonObject model = new JsonObject();
        model.addProperty("model", MOD + ":block/" + name);
        JsonObject variants = new JsonObject();
        variants.add("", model);
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject cubeAll(String texture) {
        JsonObject textures = new JsonObject();
        textures.addProperty("all", texture);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/cube_all");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject cubeColumn(String side, String end) {
        JsonObject textures = new JsonObject();
        textures.addProperty("end", end);
        textures.addProperty("side", side);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/cube_column");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject blockItem(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD + ":block/" + name);
        return json;
    }

    private static JsonObject generated(String texture) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", texture);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");
        json.add("textures", textures);
        return json;
    }
}
