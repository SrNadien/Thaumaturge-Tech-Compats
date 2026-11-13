package nadiendev.ThaumaturgeTechCompats.compats.tags;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.processing.OritechAspectTable;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TagAspectsProvider implements DataProvider {
    private static final int DATA_PACK_FORMAT = 48;

    private final PackOutput output;

    public TagAspectsProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Set<String> defined = ThaumaturgeAspects.definedKeys();
        List<TagAspectEntry> entries = new ArrayList<>(TagAspectTable.entries(defined));
        entries.addAll(OritechAspectTable.entries());

        JsonObject values = new JsonObject();
        for (TagAspectEntry entry : entries) {
            values.add(entry.key(), entryJson(entry));
        }
        JsonObject map = new JsonObject();
        map.add("values", values);

        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(CompatJson.save(output, cache, TagAspectsIntegration.PACK_PATH + "/pack.mcmeta", packMeta()));
        tasks.add(CompatJson.save(output, cache, TagAspectsIntegration.PACK_PATH + "/data/thaumaturge/data_maps/item/base_aspects.json", map));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats tag aspects";
    }

    private static JsonObject packMeta() {
        JsonObject pack = new JsonObject();
        pack.addProperty("description", "Aspects for common tags and for the items of other mods.");
        pack.addProperty("pack_format", DATA_PACK_FORMAT);
        JsonObject json = new JsonObject();
        json.add("pack", pack);
        return json;
    }

    private static JsonObject entryJson(TagAspectEntry entry) {
        JsonArray conditions = new JsonArray();
        for (String mod : entry.mods()) {
            conditions.add(CompatJson.modLoaded(mod));
        }
        for (String feature : entry.features()) {
            conditions.add(CompatJson.feature(feature));
        }
        JsonArray aspects = new JsonArray();
        for (AspectPart part : entry.parts()) {
            JsonObject aspect = new JsonObject();
            aspect.addProperty("aspect", part.aspect().id());
            aspect.addProperty("amount", part.amount());
            aspects.add(aspect);
        }
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions);
        json.add("neoforge:value", aspects);
        return json;
    }
}
