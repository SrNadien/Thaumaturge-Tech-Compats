package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.core.Direction;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThaummotorAssets implements DataProvider {
    public static final String SHAFT_HALF_MODEL = "block/" + ThaummotorRegistry.NAME + "_shaft_half";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String NAME = ThaummotorRegistry.NAME;
    private static final String HORIZONTAL_MODEL = MOD + ":block/" + NAME;
    private static final String VERTICAL_MODEL = MOD + ":block/" + NAME + "_vertical";
    private static final String ITEM_BLOCK_MODEL = MOD + ":block/" + NAME + "_item";

    private final PackOutput output;

    public ThaummotorAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "blockstates/" + NAME, blockState()));
        tasks.add(save(cache, "models/block/" + NAME, model(ThaummotorModels.BLOCK)));
        tasks.add(save(cache, "models/block/" + NAME + "_vertical", model(ThaummotorModels.BLOCK_VERTICAL)));
        tasks.add(save(cache, "models/block/" + NAME + "_item", model(ThaummotorModels.ITEM)));
        tasks.add(save(cache, "models/block/" + NAME + "_shaft_half", model(ThaummotorModels.SHAFT_HALF)));
        tasks.add(save(cache, "models/item/" + NAME, itemModel()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Thaummotor assets";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/" + path + ".json", json);
    }

    private static JsonObject model(String template) {
        return JsonParser.parseString(template.replace("%MOD%", MOD)).getAsJsonObject();
    }

    private static JsonObject variant(String model, int x, int y) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", model);
        if (x != 0) {
            variant.addProperty("x", x);
        }
        if (y != 0) {
            variant.addProperty("y", y);
        }
        return variant;
    }

    private static JsonObject blockState() {
        JsonObject variants = new JsonObject();
        variants.add("facing=" + Direction.DOWN.getName(), variant(VERTICAL_MODEL, 180, 0));
        variants.add("facing=" + Direction.EAST.getName(), variant(HORIZONTAL_MODEL, 0, 270));
        variants.add("facing=" + Direction.NORTH.getName(), variant(HORIZONTAL_MODEL, 0, 180));
        variants.add("facing=" + Direction.SOUTH.getName(), variant(HORIZONTAL_MODEL, 0, 0));
        variants.add("facing=" + Direction.UP.getName(), variant(VERTICAL_MODEL, 0, 0));
        variants.add("facing=" + Direction.WEST.getName(), variant(HORIZONTAL_MODEL, 0, 90));
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject itemModel() {
        JsonObject json = new JsonObject();
        json.addProperty("parent", ITEM_BLOCK_MODEL);
        return json;
    }
}
