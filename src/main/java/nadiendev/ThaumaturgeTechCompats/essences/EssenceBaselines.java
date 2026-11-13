package nadiendev.ThaumaturgeTechCompats.essences;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nadiendev.ThaumaturgeTechCompats.compats.botania.aspects.BotaniaAspects;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOAspects;
import nadiendev.ThaumaturgeTechCompats.compats.forcecraft.ForceCraftAspects;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.processing.OritechAspectTable;
import nadiendev.ThaumaturgeTechCompats.compats.tags.Asp;
import nadiendev.ThaumaturgeTechCompats.compats.tags.AspectPart;
import nadiendev.ThaumaturgeTechCompats.compats.tags.TagAspectEntry;
import nadiendev.ThaumaturgeTechCompats.compats.tags.TagAspectTable;
import nadiendev.ThaumaturgeTechCompats.compats.tags.ThaumaturgeAspects;
import net.neoforged.fml.ModList;
import net.neoforged.fml.jarcontents.JarContents;
import net.neoforged.neoforgespi.language.IModFileInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class EssenceBaselines {
    private static final String THAUMATURGE = "thaumaturge";

    private static final Map<String, List<AspectPart>> OWN_AVARITIA = Map.of(
            "avaritia:crystal_matrix_ingot", List.of(Asp.TERMINUS.of(1), Asp.POTENTIA.of(8), Asp.VITREUS.of(32)),
            "avaritia:neutron_ingot", List.of(Asp.TERMINUS.of(2), Asp.METALLUM.of(12), Asp.POTENTIA.of(12)),
            "avaritia:infinity_catalyst", List.of(Asp.TERMINUS.of(5), Asp.PERMUTATIO.of(12)),
            "avaritia:infinity_ingot", List.of(Asp.TERMINUS.of(16), Asp.ALIENIS.of(64)),
            "avaritia:neutron_collector", List.of(Asp.TERMINUS.of(5), Asp.METALLUM.of(64), Asp.POTENTIA.of(64), Asp.MACHINA.of(64)),
            "avaritia:singularity", List.of(Asp.TERMINUS.of(3), Asp.METALLUM.of(100)),
            "avaritia:infinity_sword", List.of(Asp.TERMINUS.of(60), Asp.AVERSIO.of(999), Asp.MORTUUS.of(999), Asp.ALIENIS.of(100)));

    private EssenceBaselines() {
    }

    public record Baseline(JsonArray conditions, Map<String, Integer> amounts) {
    }

    public static Map<String, Baseline> load() {
        Map<String, Baseline> baselines = new HashMap<>();
        fromThaumaturge(baselines);
        fromTagTable(baselines);
        fromContentProvider(baselines);
        return baselines;
    }

    private static void fromThaumaturge(Map<String, Baseline> baselines) {
        ModList modList = ModList.get();
        IModFileInfo file = modList.getModFileById(THAUMATURGE);
        if (file == null) {
            return;
        }
        JarContents contents = file.getFile().getContents();
        String path = "data/" + THAUMATURGE + "/data_maps/item/base_aspects.json";
        if (!contents.containsFile(path)) {
            return;
        }
        try (Reader reader = new InputStreamReader(contents.openFile(path), StandardCharsets.UTF_8)) {
            JsonObject values = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonObject("values");
            readValues(values, baselines);
        } catch (IOException | RuntimeException exception) {
            throw new IllegalStateException("Cannot read the aspects of Thaumaturge", exception);
        }
    }

    private static void fromTagTable(Map<String, Baseline> baselines) {
        List<TagAspectEntry> entries = new ArrayList<>(TagAspectTable.entries(ThaumaturgeAspects.definedKeys()));
        entries.addAll(OritechAspectTable.entries());
        for (TagAspectEntry entry : entries) {
            JsonArray conditions = new JsonArray();
            for (String mod : entry.mods()) {
                conditions.add(CompatJson.modLoaded(mod));
            }
            for (String feature : entry.features()) {
                conditions.add(CompatJson.feature(feature));
            }
            baselines.put(entry.key(), new Baseline(conditions, amounts(entry.parts())));
        }
    }

    private static void fromContentProvider(Map<String, Baseline> baselines) {
        JsonObject values = new JsonObject();
        ForceCraftAspects.addItems(values);
        EnderIOAspects.addTo(values);
        BotaniaAspects.addTo(values);
        readValues(values, baselines);
        OWN_AVARITIA.forEach((key, parts) -> baselines.put(key, new Baseline(new JsonArray(), amounts(parts))));
    }

    private static void readValues(JsonObject values, Map<String, Baseline> baselines) {
        for (Map.Entry<String, JsonElement> entry : values.entrySet()) {
            JsonArray conditions = new JsonArray();
            JsonElement value = entry.getValue();
            if (value.isJsonObject()) {
                JsonObject object = value.getAsJsonObject();
                if (object.has("neoforge:conditions")) {
                    conditions = object.getAsJsonArray("neoforge:conditions");
                }
                value = object.has("neoforge:value") ? object.get("neoforge:value") : object.get("value");
            }
            Map<String, Integer> amounts = new LinkedHashMap<>();
            for (JsonElement part : value.getAsJsonArray()) {
                JsonObject aspect = part.getAsJsonObject();
                amounts.merge(aspect.get("aspect").getAsString(), aspect.get("amount").getAsInt(), Integer::sum);
            }
            baselines.put(entry.getKey(), new Baseline(conditions, amounts));
        }
    }

    public static Map<String, Integer> amounts(List<AspectPart> parts) {
        Map<String, Integer> amounts = new LinkedHashMap<>();
        for (AspectPart part : parts) {
            amounts.merge(part.aspect().id(), part.amount(), Integer::sum);
        }
        return amounts;
    }
}
