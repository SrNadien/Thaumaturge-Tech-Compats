package nadiendev.ThaumaturgeTechCompats.stabilizer;

import com.google.gson.JsonArray;
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

public class StabilizerContent implements DataProvider {
    public static final String RESEARCH = "avaritia_stabilizers";

    private final PackOutput output;

    public StabilizerContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "data/" + DeviceJson.MOD + "/thaumaturge/research_entry/" + RESEARCH + ".json", research()));
        for (StabilizerTier tier : StabilizerTier.values()) {
            tasks.add(save(cache, "data/" + DeviceJson.MOD + "/recipe/infusion/" + tier.blockName() + ".json", infusion(tier)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats node stabilizers";
    }

    private CompletableFuture<?> save(CachedOutput cache, String relativePath, JsonElement json) {
        Path path = output.getOutputFolder().resolve(relativePath);
        return DataProvider.saveStable(cache, json, path);
    }

    private static JsonObject research() {
        String[] recipes = new String[StabilizerTier.values().length];
        for (StabilizerTier tier : StabilizerTier.values()) {
            recipes[tier.ordinal()] = DeviceJson.MOD + ":infusion/" + tier.blockName();
        }
        return DeviceJson.research(
                RESEARCH,
                "thaumaturge:node_stabilizer_advanced",
                2, 4,
                DeviceJson.MOD + ":infinity_node_stabilizer",
                DeviceJson.strings(recipes),
                2,
                DeviceJson.aspects("thaumaturge:auram", 8, "thaumaturge:ordo", 6, DeviceJson.ASPECT_TERMINUS, 4),
                3);
    }

    private static JsonObject infusion(StabilizerTier tier) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 2; i++) {
            components.add(DeviceJson.item(tier.ingot()));
            components.add(DeviceJson.tag("thaumaturge:nitors"));
            components.add(DeviceJson.item(tier.plate()));
            components.add(DeviceJson.item("thaumaturge:alumentum"));
        }

        JsonObject json = new JsonObject();
        json.addProperty("type", "thaumaturge:infusion");
        json.add("aspects", DeviceJson.aspects(
                "thaumaturge:auram", tier.auram(),
                "thaumaturge:praecantatio", 32,
                "thaumaturge:ordo", 32,
                "thaumaturge:potentia", 32,
                DeviceJson.ASPECT_TERMINUS, 8 * (tier.ordinal() + 1)));
        json.add("catalyst", DeviceJson.item(tier.catalyst()));
        json.add("components", components);
        json.addProperty("instability", tier.instability());
        json.add("research", DeviceJson.researchRef(DeviceJson.MOD + ":" + RESEARCH));
        json.add("result", DeviceJson.stack(DeviceJson.MOD + ":" + tier.blockName()));
        return json;
    }
}
