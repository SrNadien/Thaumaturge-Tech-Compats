package nadiendev.ThaumaturgeTechCompats.furnace;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.device.DeviceJson;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import nadiendev.ThaumaturgeTechCompats.datagen.RecipeSaver;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FurnaceContent implements DataProvider {
    public static final String RESEARCH = "avaritia_furnaces";

    private final PackOutput output;

    public FurnaceContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "data/" + DeviceJson.MOD + "/thaumaturge/research_entry/" + RESEARCH + ".json", research()));
        for (DeviceTier tier : DeviceTier.values()) {
            String name = FurnaceIntegration.blockName(tier);
            tasks.add(save(cache, "data/" + DeviceJson.MOD + "/thaumaturge/blueprint/" + name + ".json", blueprint(tier)));
            tasks.add(save(cache, "data/" + DeviceJson.MOD + "/recipe/dust_trigger/" + name + ".json", dustTrigger(tier)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats alchemical furnaces";
    }

    private CompletableFuture<?> save(CachedOutput cache, String relativePath, JsonElement json) {
        Path path = output.getOutputFolder().resolve(relativePath);
        return RecipeSaver.save(cache, json, path);
    }

    private static JsonObject research() {
        String[] recipes = new String[DeviceTier.values().length];
        for (DeviceTier tier : DeviceTier.values()) {
            recipes[tier.ordinal()] = DeviceJson.MOD + ":dust_trigger/" + FurnaceIntegration.blockName(tier);
        }
        return DeviceJson.research(
                RESEARCH,
                "thaumaturge:advanced_alchemical_furnace",
                6, 4,
                DeviceJson.MOD + ":infinity_alchemical_furnace",
                DeviceJson.strings(recipes),
                3,
                DeviceJson.aspects("thaumaturge:ignis", 8, "thaumaturge:alkimia", 6, DeviceJson.ASPECT_TERMINUS, 4),
                3);
    }

    private static JsonObject part(String sourceBlock, String targetBlock) {
        JsonObject source = new JsonObject();
        source.addProperty("type", "block");
        source.addProperty("block", sourceBlock);
        JsonObject target = new JsonObject();
        target.addProperty("type", "block");
        target.addProperty("block", targetBlock);
        JsonObject part = new JsonObject();
        part.add("source", source);
        part.add("target", target);
        return part;
    }

    private static JsonObject blueprint(DeviceTier tier) {
        JsonObject keys = new JsonObject();
        keys.add("A", part("thaumaturge:alembic", "thaumaturge:advanced_alchemical_furnace_alembic_placeholder"));
        keys.add("C", part("thaumaturge:alchemical_construct", "thaumaturge:advanced_alchemical_furnace_construct_placeholder"));
        keys.add("F", part(DeviceJson.MOD + ":" + tier.id() + "_smelter", DeviceJson.MOD + ":" + FurnaceIntegration.blockName(tier)));
        keys.add("N", part("thaumaturge:advanced_alchemical_construct", "thaumaturge:advanced_alchemical_furnace_nozzle"));
        keys.add("V", part("thaumaturge:advanced_alchemical_construct", "thaumaturge:advanced_alchemical_furnace_advanced_construct_placeholder"));

        JsonArray pattern = new JsonArray();
        pattern.add(DeviceJson.strings("ACA", "C C", "ACA"));
        pattern.add(DeviceJson.strings("VNV", "NFN", "VNV"));

        JsonObject json = new JsonObject();
        json.add("keys", keys);
        json.add("pattern", pattern);
        json.addProperty("x_size", 3);
        json.addProperty("y_size", 2);
        json.addProperty("z_size", 3);
        return json;
    }

    private static JsonObject dustTrigger(DeviceTier tier) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "thaumaturge:dust_trigger_multiblock");
        json.addProperty("blueprint", DeviceJson.MOD + ":" + FurnaceIntegration.blockName(tier));
        json.add("research", DeviceJson.researchRef(DeviceJson.MOD + ":" + RESEARCH));
        json.add("result", DeviceJson.stack(DeviceJson.MOD + ":" + FurnaceIntegration.blockName(tier)));
        return json;
    }
}
