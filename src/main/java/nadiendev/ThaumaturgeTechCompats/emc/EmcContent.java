package nadiendev.ThaumaturgeTechCompats.emc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EmcContent implements DataProvider {
    private static final int COLUMN = 70;
    private static final int ROW = 0;

    private final PackOutput output;

    public EmcContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return ContentJson.save(output, cache, "data/" + ThaumaturgeTechCompats.MOD_ID + "/thaumaturge/research_entry/" + EmcIntegration.ENTRY + ".json", entry());
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats ProjectE research";
    }

    private static JsonObject entry() {
        JsonObject json = ContentJson.research(
                EmcIntegration.ENTRY,
                List.of(TechCategory.HEADER_ID),
                COLUMN, ROW,
                "thaumaturge:ingot_thaumium",
                List.of(),
                1,
                ContentJson.aspects("thaumaturge:metallum", 4, "thaumaturge:permutatio", 3),
                1);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.modLoaded(EmcIntegration.MOD_ID));
        conditions.add(CompatJson.feature(EmcIntegration.TOGGLE));
        json.add("neoforge:conditions", conditions);
        return json;
    }
}
