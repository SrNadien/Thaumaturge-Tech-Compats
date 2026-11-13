package nadiendev.ThaumaturgeTechCompats.emc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class EmcExclusionTags implements DataProvider {
    private final PackOutput output;

    public EmcExclusionTags(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        JsonArray values = new JsonArray();
        for (String item : EmcValues.EXCLUDED) {
            JsonObject entry = new JsonObject();
            entry.addProperty("id", item);
            entry.addProperty("required", false);
            values.add(entry);
        }
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        json.add("values", values);
        return ContentJson.save(output, cache, "data/" + ThaumaturgeTechCompats.MOD_ID + "/tags/item/no_emc.json", json);
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats no EMC tag";
    }
}
