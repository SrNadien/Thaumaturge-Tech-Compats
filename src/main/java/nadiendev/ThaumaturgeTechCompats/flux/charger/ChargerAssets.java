package nadiendev.ThaumaturgeTechCompats.flux.charger;

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

public class ChargerAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public ChargerAssets(PackOutput output) {
        this.output = output;
    }

    public static String texture(FluxTier tier, String part) {
        String metal = switch (tier) {
            case THAUMIUM -> "metal_thaumium";
            case ALCHEMICAL_BRASS -> "metal_brass";
            case VOID_METAL -> "metal_void";
        };
        return "thaumaturge:block/" + metal;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            String name = ChargerRegistry.blockName(tier);
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/blockstates/" + name + ".json", blockState(name)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/block/" + name + ".json", blockModel(tier)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/block/" + name + "_on.json", blockModel(tier)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", itemModel(name)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats environmental charger assets";
    }

    private static JsonObject blockState(String name) {
        JsonObject off = new JsonObject();
        off.addProperty("model", MOD + ":block/" + name);
        JsonObject on = new JsonObject();
        on.addProperty("model", MOD + ":block/" + name + "_on");
        JsonObject variants = new JsonObject();
        variants.add("active=false", off);
        variants.add("active=true", on);
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject blockModel(FluxTier tier) {
        JsonObject textures = new JsonObject();
        textures.addProperty("top", texture(tier, "top"));
        textures.addProperty("bottom", texture(tier, "bottom"));
        textures.addProperty("side", texture(tier, "side"));
        textures.addProperty("particle", texture(tier, "side"));
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/cube_bottom_top");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject itemModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD + ":block/" + name);
        return json;
    }
}
