package nadiendev.ThaumaturgeTechCompats.essences;

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

public class EssencesContent implements DataProvider {
    public static final String ENTRY = "tech_essences";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String PEARL = MOD + ":extremely_primordial_pearl";
    private static final int COLUMN = 69;
    private static final int ROW = 0;

    private final PackOutput output;

    public EssencesContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY + ".json", entry());
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats essences research";
    }

    private static JsonObject entry() {
        JsonObject json = ContentJson.research(
                ENTRY,
                List.of(PEARL),
                COLUMN, ROW,
                "minecraft:nether_star",
                List.of(),
                0,
                ContentJson.aspects(
                        "thaumaturge:praecantatio", 4,
                        MOD + ":sol", 3,
                        MOD + ":nuclear", 3,
                        MOD + ":cosmic", 3,
                        ContentJson.ASPECT_TERMINUS, 3),
                1);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.feature(EssencesIntegration.TOGGLE));
        json.add("neoforge:conditions", conditions);
        return json;
    }
}
