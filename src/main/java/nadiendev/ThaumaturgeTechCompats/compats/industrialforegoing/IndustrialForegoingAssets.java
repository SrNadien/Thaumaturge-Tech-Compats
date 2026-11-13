package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.core.Direction;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;

public class IndustrialForegoingAssets implements DataProvider {
    private static final String DRILL_FRONT = "magical_laser_drill_front";
    private static final String DRILL_BACK = "magical_laser_drill_back";
    private static final String DRILL_TOP = "magical_laser_drill_top";
    private static final String DRILL_BOTTOM = "magical_laser_drill_bottom";
    private static final String DRILL_SIDE = "magical_laser_drill_side";
    private static final String BASE_TOP = "magical_laser_base_top";
    private static final String BASE_BOTTOM = "magical_laser_base_bottom";

    private final PackOutput output;

    public IndustrialForegoingAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (LaserMachine machine : LaserMachine.values()) {
            String name = machine.blockName();
            tasks.add(save(cache, "blockstates/" + name, machine.base() ? plainBlockState(name) : drillBlockState(name)));
            tasks.add(save(cache, "models/block/" + name, machine.base() ? baseModel(machine) : drillModel()));
            tasks.add(save(cache, "models/item/" + name, itemModel(name)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Industrial Foregoing assets";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/" + path + ".json", json);
    }

    private static String texture(String name) {
        return MOD + ":block/" + name;
    }

    private static JsonObject variant(String model, int x, int y) {
        JsonObject json = new JsonObject();
        json.addProperty("model", MOD + ":block/" + model);
        if (x != 0) {
            json.addProperty("x", x);
        }
        if (y != 0) {
            json.addProperty("y", y);
        }
        return json;
    }

    private static JsonObject drillBlockState(String name) {
        JsonObject variants = new JsonObject();
        variants.add("facing=" + Direction.DOWN.getName(), variant(name, 90, 270));
        variants.add("facing=" + Direction.UP.getName(), variant(name, 270, 270));
        variants.add("facing=" + Direction.NORTH.getName(), variant(name, 0, 0));
        variants.add("facing=" + Direction.SOUTH.getName(), variant(name, 0, 180));
        variants.add("facing=" + Direction.WEST.getName(), variant(name, 0, 270));
        variants.add("facing=" + Direction.EAST.getName(), variant(name, 0, 90));
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject plainBlockState(String name) {
        JsonObject variants = new JsonObject();
        variants.add("", variant(name, 0, 0));
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject drillModel() {
        JsonObject textures = new JsonObject();
        textures.addProperty("particle", texture(DRILL_SIDE));
        textures.addProperty("down", texture(DRILL_BOTTOM));
        textures.addProperty("up", texture(DRILL_TOP));
        textures.addProperty("north", texture(DRILL_FRONT));
        textures.addProperty("south", texture(DRILL_BACK));
        textures.addProperty("east", texture(DRILL_SIDE));
        textures.addProperty("west", texture(DRILL_SIDE));
        return cube(textures);
    }

    private static JsonObject baseModel(LaserMachine machine) {
        String side = texture(machine.blockName() + "_side");
        JsonObject textures = new JsonObject();
        textures.addProperty("particle", side);
        textures.addProperty("down", texture(BASE_BOTTOM));
        textures.addProperty("up", texture(BASE_TOP));
        textures.addProperty("north", side);
        textures.addProperty("south", side);
        textures.addProperty("east", side);
        textures.addProperty("west", side);
        return cube(textures);
    }

    private static JsonObject cube(JsonObject textures) {
        JsonObject json = new JsonObject();
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
