package nadiendev.ThaumaturgeTechCompats.nodepower;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FluxJarAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String RIFT_JAR = "rift_jar";
    private static final int EMPTY = 0;
    private static final int FULL = RiftJarBlock.MAX_FILL;

    private final PackOutput output;

    public FluxJarAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (FluxJarTier tier : FluxJarTier.values()) {
            String name = tier.blockName();
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/blockstates/" + name + ".json", fluxBlockState(name)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/block/" + name + "_" + EMPTY + ".json", blockModel(name, EMPTY)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/block/" + name + "_" + FULL + ".json", blockModel(name, FULL)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", itemModel(name)));
        }
        tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/blockstates/" + RIFT_JAR + ".json", riftBlockState(RIFT_JAR)));
        for (int fill = 0; fill <= RiftJarBlock.MAX_FILL; fill++) {
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/block/" + RIFT_JAR + "_" + fill + ".json", blockModel(RIFT_JAR, fill)));
        }
        tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + RIFT_JAR + ".json", itemModel(RIFT_JAR)));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats flux jar assets";
    }

    private static JsonObject fluxBlockState(String name) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", MOD + ":block/" + name + "_" + EMPTY);
        JsonObject variants = new JsonObject();
        variants.add("", variant);
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject riftBlockState(String name) {
        JsonObject variants = new JsonObject();
        for (int fill = 0; fill <= RiftJarBlock.MAX_FILL; fill++) {
            JsonObject variant = new JsonObject();
            variant.addProperty("model", MOD + ":block/" + name + "_" + fill);
            variants.add("fill=" + fill, variant);
        }
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject blockModel(String name, int fill) {
        String side = MOD + ":block/" + name + "_side_" + fill;
        JsonObject textures = new JsonObject();
        textures.addProperty("side", side);
        textures.addProperty("top", MOD + ":block/" + name + "_top");
        textures.addProperty("bottom", MOD + ":block/" + name + "_bottom");
        textures.addProperty("particle", side);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "thaumaturge:block/jar_normal");
        json.addProperty("render_type", "minecraft:translucent");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject itemModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD + ":block/" + name + "_" + FULL);
        return json;
    }
}
