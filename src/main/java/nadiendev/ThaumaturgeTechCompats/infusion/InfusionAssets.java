package nadiendev.ThaumaturgeTechCompats.infusion;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InfusionAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String MATRIX_MODEL = "thaumaturge:block/infusion_matrix";
    private static final String PEDESTAL_MODEL = "thaumaturge:block/pedestal_eldritch";
    private static final String PILLAR_MESH = "thaumaturge:models/mesh/pillar.ttmesh";
    private static final String[] FACINGS = {"east", "north", "south", "west"};
    private static final int[] FACING_ROTATIONS = {90, 0, 180, 270};
    private static final String PILLAR_DISPLAY = "{"
            + "\"gui\":{\"rotation\":[30,225,0],\"translation\":[0,0,0],\"scale\":[0.625,0.625,0.625]},"
            + "\"ground\":{\"rotation\":[0,0,0],\"translation\":[0,3,0],\"scale\":[0.25,0.25,0.25]},"
            + "\"fixed\":{\"rotation\":[0,0,0],\"translation\":[0,0,0],\"scale\":[0.5,0.5,0.5]},"
            + "\"thirdperson_righthand\":{\"rotation\":[75,45,0],\"translation\":[0,2.5,0],\"scale\":[0.375,0.375,0.375]},"
            + "\"firstperson_righthand\":{\"rotation\":[0,45,0],\"translation\":[0,0,0],\"scale\":[0.4,0.4,0.4]},"
            + "\"firstperson_lefthand\":{\"rotation\":[0,225,0],\"translation\":[0,0,0],\"scale\":[0.4,0.4,0.4]}}";

    private final PackOutput output;

    public InfusionAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (MatrixVariant variant : MatrixVariant.values()) {
            String matrix = variant.matrixName();
            tasks.add(save(cache, "blockstates/" + matrix, blockState(matrix)));
            tasks.add(save(cache, "models/block/" + matrix, matrixModel(variant)));
            tasks.add(save(cache, "models/item/" + matrix, itemModel(matrix)));
            for (StoneKind kind : StoneKind.values()) {
                String stone = variant.stoneName(kind);
                tasks.add(save(cache, "blockstates/" + stone, blockState(stone)));
                tasks.add(save(cache, "models/block/" + stone, stoneModel(stone)));
                tasks.add(save(cache, "models/item/" + stone, itemModel(stone)));
            }
            for (AltarPart part : AltarPart.values()) {
                String name = variant.altarName(part);
                switch (part) {
                    case STONE -> {
                        tasks.add(save(cache, "blockstates/" + name, blockState(name)));
                        tasks.add(save(cache, "models/block/" + name, cubeModel(InfusionTextures.stone(variant))));
                    }
                    case PILLAR -> {
                        tasks.add(save(cache, "blockstates/" + name, pillarState(name)));
                        tasks.add(save(cache, "models/block/" + name, pillarModel(variant)));
                    }
                    case PEDESTAL -> {
                        tasks.add(save(cache, "blockstates/" + name, blockState(name)));
                        tasks.add(save(cache, "models/block/" + name, pedestalModel(variant)));
                    }
                }
                tasks.add(save(cache, "models/item/" + name, itemModel(name)));
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats infusion variant assets";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/" + path + ".json", json);
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

    private static JsonObject pillarState(String name) {
        JsonObject variants = new JsonObject();
        for (int i = 0; i < FACINGS.length; i++) {
            JsonObject variant = new JsonObject();
            variant.addProperty("model", MOD + ":block/" + name);
            if (FACING_ROTATIONS[i] != 0) {
                variant.addProperty("y", FACING_ROTATIONS[i]);
            }
            variants.add("facing=" + FACINGS[i], variant);
        }
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject matrixModel(MatrixVariant variant) {
        JsonObject textures = new JsonObject();
        textures.addProperty("side", MOD + ":block/" + variant.id() + "_infuser");
        JsonObject json = new JsonObject();
        json.addProperty("parent", MATRIX_MODEL);
        json.add("textures", textures);
        return json;
    }

    private static JsonObject stoneModel(String name) {
        return cubeModel(MOD + ":block/" + name);
    }

    private static JsonObject cubeModel(String texture) {
        JsonObject textures = new JsonObject();
        textures.addProperty("all", texture);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/cube_all");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject pedestalModel(MatrixVariant variant) {
        JsonObject textures = new JsonObject();
        textures.addProperty("side", InfusionTextures.pedestalSide(variant));
        textures.addProperty("top", InfusionTextures.pedestalTop(variant));
        textures.addProperty("particle", InfusionTextures.pedestalSide(variant));
        JsonObject json = new JsonObject();
        json.addProperty("parent", PEDESTAL_MODEL);
        json.add("textures", textures);
        return json;
    }

    private static JsonObject pillarModel(MatrixVariant variant) {
        JsonObject textures = new JsonObject();
        textures.addProperty("texture", InfusionTextures.pillar(variant));
        textures.addProperty("particle", InfusionTextures.pillar(variant));
        JsonObject json = new JsonObject();
        json.addProperty("loader", "thaumaturge:mesh");
        json.addProperty("parent", "minecraft:block/block");
        json.addProperty("ambientocclusion", false);
        json.addProperty("model", PILLAR_MESH);
        json.addProperty("corner_space", true);
        json.add("textures", textures);
        json.add("display", JsonParser.parseString(PILLAR_DISPLAY));
        return json;
    }

    private static JsonObject itemModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD + ":block/" + name);
        return json;
    }
}
