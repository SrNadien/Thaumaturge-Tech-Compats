package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MagicalUneartherAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String FTB = "ftbunearthed";
    private static final String[] FACINGS = {"north", "east", "south", "west"};
    private static final int[] ROTATIONS = {0, 90, 180, 270};

    private final PackOutput output;

    public MagicalUneartherAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (MagicalUneartherTier tier : MagicalUneartherTier.values()) {
            String name = tier.blockName();
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/blockstates/" + name + ".json", blockState(name)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/block/" + name + ".json", blockModel(name)));
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", itemModel(name)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats magical unearther assets";
    }

    private static JsonObject blockState(String name) {
        JsonObject variants = new JsonObject();
        for (int i = 0; i < FACINGS.length; i++) {
            JsonObject variant = new JsonObject();
            variant.addProperty("model", MOD + ":block/" + name);
            if (ROTATIONS[i] != 0) {
                variant.addProperty("y", ROTATIONS[i]);
            }
            variants.add("facing=" + FACINGS[i], variant);
        }
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject blockModel(String name) {
        String side = MOD + ":block/" + name + "_side";
        JsonObject textures = new JsonObject();
        textures.addProperty("0", MOD + ":block/" + name + "_top");
        textures.addProperty("1", side);
        textures.addProperty("particle", side);
        JsonObject json = new JsonObject();
        json.addProperty("parent", FTB + ":block/core");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject itemModel(String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("1", MOD + ":block/" + name + "_side");
        JsonObject json = new JsonObject();
        json.addProperty("parent", FTB + ":item/unearther_item");
        json.add("textures", textures);
        return json;
    }
}
