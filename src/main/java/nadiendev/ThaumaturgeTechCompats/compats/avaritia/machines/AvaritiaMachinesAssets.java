package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AvaritiaMachinesAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String[] FACINGS = {"north", "east", "south", "west"};

    private final PackOutput output;

    public AvaritiaMachinesAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (AvaritiaMachineTier tier : AvaritiaMachineTier.values()) {
            add(tasks, cache, tier.collectorName());
            add(tasks, cache, tier.compressorName());
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats avaritia machine assets";
    }

    private void add(List<CompletableFuture<?>> tasks, CachedOutput cache, String name) {
        tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/blockstates/" + name + ".json", blockState(name)));
        tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/block/" + name + ".json", blockModel(name)));
        tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", itemModel(name)));
    }

    private static JsonObject blockState(String name) {
        JsonObject variants = new JsonObject();
        for (int i = 0; i < FACINGS.length; i++) {
            JsonObject variant = new JsonObject();
            variant.addProperty("model", MOD + ":block/" + name);
            if (i > 0) {
                variant.addProperty("y", i * 90);
            }
            variants.add("facing=" + FACINGS[i], variant);
        }
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject blockModel(String name) {
        String front = MOD + ":block/" + name + "_front";
        String frame = MOD + ":block/" + name + "_frame";
        JsonObject textures = new JsonObject();
        textures.addProperty("particle", front);
        textures.addProperty("up", MOD + ":block/" + name + "_top");
        textures.addProperty("down", frame);
        textures.addProperty("north", front);
        textures.addProperty("south", frame);
        textures.addProperty("east", MOD + ":block/" + name + "_side_right");
        textures.addProperty("west", MOD + ":block/" + name + "_side_left");
        JsonObject json = new JsonObject();
        json.addProperty("render_type", "minecraft:cutout");
        json.addProperty("parent", "minecraft:block/cube");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject itemModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD + ":block/" + name);
        return json;
    }
}
