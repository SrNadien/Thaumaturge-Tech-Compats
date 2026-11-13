package nadiendev.ThaumaturgeTechCompats.flux.core;

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

public class CoreAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String FORMED_SUFFIX = "_formed";

    private final PackOutput output;

    public CoreAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            String core = CoreRegistry.coreName(tier);
            String cell = CoreRegistry.cellName(tier);
            tasks.add(save(cache, "blockstates/" + core, blockState(core)));
            tasks.add(save(cache, "models/block/" + core, cube(blockMarker(tier))));
            tasks.add(save(cache, "models/block/" + core + FORMED_SUFFIX, cube(blockMarker(tier))));
            tasks.add(save(cache, "models/item/" + core, parent(MOD + ":block/" + core)));
            tasks.add(save(cache, "models/item/" + cell, generated(itemMarker(tier))));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats flux core assets";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/" + path + ".json", json);
    }

    private static String blockMarker(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> "thaumaturge:block/metal_thaumium";
            case ALCHEMICAL_BRASS -> "thaumaturge:block/metal_brass";
            case VOID_METAL -> "thaumaturge:block/metal_void";
        };
    }

    private static String itemMarker(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> "thaumaturge:item/ingot_thaumium";
            case ALCHEMICAL_BRASS -> "thaumaturge:item/ingot_brass";
            case VOID_METAL -> "thaumaturge:item/ingot_void";
        };
    }

    private static JsonObject variant(String model) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", model);
        return variant;
    }

    private static JsonObject blockState(String core) {
        JsonObject variants = new JsonObject();
        variants.add(StorageCoreBlock.FORMED.getName() + "=false", variant(MOD + ":block/" + core));
        variants.add(StorageCoreBlock.FORMED.getName() + "=true", variant(MOD + ":block/" + core + FORMED_SUFFIX));
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject cube(String texture) {
        JsonObject textures = new JsonObject();
        textures.addProperty("all", texture);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/cube_all");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject parent(String model) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", model);
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
