package nadiendev.ThaumaturgeTechCompats.essences;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.tags.AspectPart;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public class EssencesProvider implements DataProvider {
    private static final int DATA_PACK_FORMAT = 48;
    private static final Set<String> BUILT_IN_NAMESPACES = Set.of("minecraft", "c", "neoforge", "thaumaturge", ThaumaturgeTechCompats.MOD_ID);

    private final PackOutput output;

    public EssencesProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Map<String, EssenceBaselines.Baseline> baselines = EssenceBaselines.load();
        Map<String, JsonObject> entries = new TreeMap<>();
        for (EssenceEntry entry : EssenceAspectTable.entries()) {
            if (entries.put(entry.key(), entryJson(entry, baselines.get(entry.key()))) != null) {
                throw new IllegalStateException("Essence entry defined twice: " + entry.key());
            }
        }

        JsonObject values = new JsonObject();
        entries.forEach(values::add);
        JsonObject map = new JsonObject();
        map.add("values", values);

        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(CompatJson.save(output, cache, EssencesIntegration.PACK_PATH + "/pack.mcmeta", packMeta()));
        tasks.add(CompatJson.save(output, cache, EssencesIntegration.PACK_PATH + "/data/thaumaturge/data_maps/item/base_aspects.json", map));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats own essences";
    }

    private static JsonObject packMeta() {
        JsonObject pack = new JsonObject();
        pack.addProperty("description", "The essences of Thaumaturge Tech Compats in the base aspects of items.");
        pack.addProperty("pack_format", DATA_PACK_FORMAT);
        JsonObject json = new JsonObject();
        json.add("pack", pack);
        return json;
    }

    private static JsonObject entryJson(EssenceEntry entry, EssenceBaselines.Baseline baseline) {
        Map<String, Integer> amounts;
        JsonArray conditions = new JsonArray();
        String mod = modOf(entry.key());
        if (mod != null) {
            conditions.add(CompatJson.modLoaded(mod));
        }
        if (baseline != null) {
            amounts = new LinkedHashMap<>(baseline.amounts());
            baseline.conditions().forEach(condition -> addOnce(conditions, condition.getAsJsonObject()));
        } else if (!entry.base().isEmpty()) {
            amounts = EssenceBaselines.amounts(entry.base());
        } else {
            throw new IllegalStateException("Essence entry without an aspect base: " + entry.key());
        }
        for (AspectPart part : entry.essences()) {
            amounts.merge(part.aspect().id(), part.amount(), Integer::sum);
        }
        addOnce(conditions, CompatJson.feature(EssencesIntegration.TOGGLE));

        JsonArray aspects = new JsonArray();
        amounts.forEach((aspect, amount) -> {
            JsonObject json = new JsonObject();
            json.addProperty("aspect", aspect);
            json.addProperty("amount", amount);
            aspects.add(json);
        });
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions);
        json.add("neoforge:value", aspects);
        return json;
    }

    private static void addOnce(JsonArray conditions, JsonObject condition) {
        if (!conditions.contains(condition)) {
            conditions.add(condition);
        }
    }

    private static String modOf(String key) {
        String id = key.startsWith("#") ? key.substring(1) : key;
        String namespace = id.substring(0, id.indexOf(':'));
        return BUILT_IN_NAMESPACES.contains(namespace) ? null : namespace;
    }
}
