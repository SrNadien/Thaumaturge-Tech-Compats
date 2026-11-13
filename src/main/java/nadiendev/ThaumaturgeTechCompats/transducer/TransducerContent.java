package nadiendev.ThaumaturgeTechCompats.transducer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.device.DeviceJson;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TransducerContent implements DataProvider {
    public static final String RESEARCH = "avaritia_transducers";

    private static final int[] VIS = {400, 800, 1600};
    private static final int[] CRYSTALS = {2, 3, 4};

    private final PackOutput output;

    public TransducerContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "data/" + DeviceJson.MOD + "/thaumaturge/research_entry/" + RESEARCH + ".json", research()));
        for (DeviceTier tier : DeviceTier.values()) {
            tasks.add(save(cache, "data/" + DeviceJson.MOD + "/recipe/arcane_workbench/" + TransducerIntegration.blockName(tier) + ".json", recipe(tier)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats node transducers";
    }

    private CompletableFuture<?> save(CachedOutput cache, String relativePath, JsonElement json) {
        Path path = output.getOutputFolder().resolve(relativePath);
        return DataProvider.saveStable(cache, json, path);
    }

    private static JsonObject research() {
        String[] recipes = new String[DeviceTier.values().length];
        for (DeviceTier tier : DeviceTier.values()) {
            recipes[tier.ordinal()] = DeviceJson.MOD + ":arcane_workbench/" + TransducerIntegration.blockName(tier);
        }
        return DeviceJson.research(
                RESEARCH,
                "thaumaturge:node_transducer",
                4, 4,
                DeviceJson.MOD + ":infinity_node_transducer",
                DeviceJson.strings(recipes),
                2,
                DeviceJson.aspects("thaumaturge:auram", 8, "thaumaturge:machina", 6, DeviceJson.ASPECT_TERMINUS, 4),
                3);
    }

    private static String previous(DeviceTier tier) {
        return tier.ordinal() == 0
                ? "thaumaturge:node_transducer"
                : DeviceJson.MOD + ":" + TransducerIntegration.blockName(DeviceTier.values()[tier.ordinal() - 1]);
    }

    private static JsonObject recipe(DeviceTier tier) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "thaumaturge:arcane_workbench_shaped");
        json.add("crystals", DeviceJson.aspects("thaumaturge:ordo", CRYSTALS[tier.ordinal()]));

        JsonObject key = new JsonObject();
        key.add("P", DeviceJson.item(tier.plate()));
        key.add("C", DeviceJson.item("minecraft:comparator"));
        key.add("I", DeviceJson.item(tier.ingot()));
        key.add("S", DeviceJson.item(previous(tier)));
        key.add("N", DeviceJson.tag("thaumaturge:nitors"));
        json.add("key", key);

        json.add("pattern", DeviceJson.strings("PCP", "ISI", "PNP"));
        json.add("research", DeviceJson.researchRef(DeviceJson.MOD + ":" + RESEARCH));
        json.add("result", DeviceJson.stack(DeviceJson.MOD + ":" + TransducerIntegration.blockName(tier)));
        json.addProperty("vis", VIS[tier.ordinal()]);
        return json;
    }
}
