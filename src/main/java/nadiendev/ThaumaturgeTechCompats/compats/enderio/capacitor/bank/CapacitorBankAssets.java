package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOJson;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CapacitorBankAssets implements DataProvider {
    private static final String MOD = EnderIOJson.MOD;
    private static final String EIO = EnderIOIntegration.MOD_ID;
    private static final String ASSETS = "assets/";

    private final PackOutput output;

    public CapacitorBankAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (EnderIOTier tier : EnderIOTier.values()) {
            String name = CapacitorBankSupport.blockName(tier);
            tasks.add(ContentJson.save(output, cache, ASSETS + EIO + "/blockstates/" + name + ".json", blockstate(name)));
            tasks.add(ContentJson.save(output, cache, ASSETS + EIO + "/models/item/" + name + ".json", itemModel(name)));
            tasks.add(ContentJson.save(output, cache, ASSETS + MOD + "/models/block/" + name + ".json", blockModel(name)));
            tasks.add(ContentJson.save(output, cache, ASSETS + MOD + "/models/block/" + name + "_part.json", partModel(name)));
            tasks.add(ContentJson.save(output, cache, ASSETS + MOD + "/models/block/athena/" + name + "_part.json", athenaModel(name)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO capacitor bank models";
    }

    private static JsonObject blockstate(String name) {
        JsonObject model = new JsonObject();
        model.addProperty("model", MOD + ":block/" + name);
        JsonObject variants = new JsonObject();
        variants.add("", model);
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject blockModel(String name) {
        JsonObject overlay = new JsonObject();
        overlay.addProperty("parent", EIO + ":block/io_overlay");

        JsonObject whenAthena = new JsonObject();
        whenAthena.addProperty("parent", MOD + ":block/athena/" + name + "_part");
        JsonObject otherwise = new JsonObject();
        otherwise.addProperty("parent", MOD + ":block/" + name + "_part");
        JsonObject block = new JsonObject();
        block.addProperty("loader", "endercore:modloaded");
        block.addProperty("mod", "athena");
        block.add("if", whenAthena);
        block.add("else", otherwise);

        JsonObject children = new JsonObject();
        children.add("overlay", overlay);
        children.add("block", block);

        JsonObject textures = new JsonObject();
        textures.addProperty("particle", MOD + ":block/" + name);

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/block");
        json.add("children", children);
        json.add("item_render_order", ContentJson.strings("overlay", "block"));
        json.addProperty("loader", "neoforge:composite");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject partModel(String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("all", MOD + ":block/" + name);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/cube_all");
        json.addProperty("render_type", "solid");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject athenaModel(String name) {
        JsonObject ctm = new JsonObject();
        ctm.addProperty("center", MOD + ":block/ctm/" + name + "_center");
        ctm.addProperty("empty", MOD + ":block/ctm/" + name + "_empty");
        ctm.addProperty("horizontal", MOD + ":block/ctm/" + name + "_horizontal");
        ctm.addProperty("particle", MOD + ":block/" + name);
        ctm.addProperty("vertical", MOD + ":block/ctm/" + name + "_vertical");
        JsonObject json = new JsonObject();
        json.addProperty("loader", "athena:athena");
        json.addProperty("athena:loader", "athena:ctm");
        json.add("ctm_textures", ctm);
        return json;
    }

    private static JsonObject itemModel(String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("0", MOD + ":block/" + name);
        textures.addProperty("1", EIO + ":block/capacitor_additionals/capacitor_bank_bar_end");
        textures.addProperty("particle", MOD + ":block/" + name);

        JsonArray elements = new JsonArray();
        elements.add(cube());
        elements.add(bar(0.0F, 8.0F, 0, 8));
        elements.add(bar(8.0F, 16.0F, 8, 16));

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/block");
        json.add("textures", textures);
        json.add("elements", elements);
        return json;
    }

    private static JsonObject cube() {
        JsonObject faces = new JsonObject();
        for (String side : new String[]{"north", "east", "south", "west"}) {
            faces.add(side, face(0, 0, 16, 16, "#0", null));
        }
        faces.add("up", face(0, 0, 16, 16, "#0", 90));
        faces.add("down", face(0, 0, 16, 16, "#0", 270));
        return element(point(0.0F, 0.0F, 0.0F), point(16.0F, 16.0F, 16.0F), faces);
    }

    private static JsonObject bar(float fromY, float toY, int uvFrom, int uvTo) {
        JsonObject faces = new JsonObject();
        faces.add("east", face(0, uvFrom, 16, uvTo, "#1", null));
        return element(point(16.001F, fromY, 0.0F), point(16.001F, toY, 16.0F), faces);
    }

    private static JsonObject element(JsonArray from, JsonArray to, JsonObject faces) {
        JsonObject rotation = new JsonObject();
        rotation.addProperty("angle", 0);
        rotation.addProperty("axis", "y");
        rotation.add("origin", point(8.0F, 8.0F, 8.0F));
        JsonObject json = new JsonObject();
        json.add("from", from);
        json.add("to", to);
        json.add("rotation", rotation);
        json.add("faces", faces);
        return json;
    }

    private static JsonObject face(int u1, int v1, int u2, int v2, String texture, Integer rotation) {
        JsonArray uv = new JsonArray();
        uv.add(u1);
        uv.add(v1);
        uv.add(u2);
        uv.add(v2);
        JsonObject json = new JsonObject();
        json.add("uv", uv);
        if (rotation != null) {
            json.addProperty("rotation", rotation);
        }
        json.addProperty("texture", texture);
        return json;
    }

    private static JsonArray point(float x, float y, float z) {
        JsonArray array = new JsonArray();
        array.add(x);
        array.add(y);
        array.add(z);
        return array;
    }
}
