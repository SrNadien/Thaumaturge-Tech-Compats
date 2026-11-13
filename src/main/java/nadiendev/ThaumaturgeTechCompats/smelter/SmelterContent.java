package nadiendev.ThaumaturgeTechCompats.smelter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.device.DeviceJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SmelterContent implements DataProvider {
    public static final String RESEARCH = "avaritia_smelters";

    private final PackOutput output;

    public SmelterContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "data/" + DeviceJson.MOD + "/thaumaturge/research_entry/" + RESEARCH + ".json", research()));
        for (SmelterTier tier : SmelterTier.values()) {
            tasks.add(save(cache, "data/" + DeviceJson.MOD + "/recipe/arcane_workbench/" + tier.blockName() + ".json", recipe(tier)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats smelters";
    }

    private CompletableFuture<?> save(CachedOutput cache, String relativePath, JsonElement json) {
        Path path = output.getOutputFolder().resolve(relativePath);
        return DataProvider.saveStable(cache, json, path);
    }

    private static JsonObject research() {
        String[] recipes = new String[SmelterTier.values().length];
        for (SmelterTier tier : SmelterTier.values()) {
            recipes[tier.ordinal()] = DeviceJson.MOD + ":arcane_workbench/" + tier.blockName();
        }
        return DeviceJson.research(
                RESEARCH,
                "thaumaturge:essentia_smelter_void",
                0, 4,
                DeviceJson.MOD + ":matrix_smelter",
                DeviceJson.strings(recipes),
                2,
                DeviceJson.aspects("thaumaturge:ignis", 8, "thaumaturge:alkimia", 6, DeviceJson.ASPECT_TERMINUS, 4),
                3);
    }

    private static String plateOf(SmelterTier tier) {
        return DeviceJson.MOD + ":" + tier.id() + "_plate";
    }

    private static JsonObject recipe(SmelterTier tier) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "thaumaturge:arcane_workbench_shaped");
        json.add("crystals", DeviceJson.aspects("thaumaturge:ignis", tier.ignisCrystals()));

        JsonObject key = new JsonObject();
        key.add("C", DeviceJson.item(plateOf(tier)));
        key.add("F", DeviceJson.item("thaumaturge:advanced_alchemical_construct"));
        key.add("P", DeviceJson.item(tier.ingot()));
        key.add("R", DeviceJson.item(tier.previous()));
        json.add("key", key);

        json.add("pattern", DeviceJson.strings("PRP", "CFC", "CCC"));
        json.add("research", DeviceJson.researchRef(DeviceJson.MOD + ":" + RESEARCH));
        json.add("result", DeviceJson.stack(DeviceJson.MOD + ":" + tier.blockName()));
        json.addProperty("vis", tier.vis());
        return json;
    }
}
