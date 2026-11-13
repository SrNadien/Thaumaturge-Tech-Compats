package nadiendev.ThaumaturgeTechCompats.golem.datagen;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.golem.GolemResearch;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import nadiendev.ThaumaturgeTechCompats.tiercontent.Tier;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GolemContentProvider implements DataProvider {
    private static final String MOD = ContentJson.MOD;

    private final PackOutput output;

    public GolemContentProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (Tier tier : Tier.values()) {
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/" + tier.plateName() + ".json", plateRecipe(tier)));
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + GolemResearch.NAME + ".json", golemResearch()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats golem content";
    }

    private static JsonObject plateRecipe(Tier tier) {
        JsonObject key = new JsonObject();
        key.add("N", ContentJson.item(tier.ingot()));
        if (tier == Tier.MATRIX) {
            return ContentJson.shaped("misc", key, ContentJson.strings("NNN"), MOD + ":" + tier.plateName(), 3);
        }
        Tier previous = tier == Tier.NEUTRONIUM ? Tier.MATRIX : Tier.NEUTRONIUM;
        key.add("P", ContentJson.item(MOD + ":" + previous.plateName()));
        return ContentJson.shaped("misc", key, ContentJson.strings("NNN", " P "), MOD + ":" + tier.plateName(), 3);
    }

    private static JsonObject golemResearch() {
        return ContentJson.research(
                GolemResearch.NAME,
                List.of("thaumaturge:mat_stud_thaumium", MOD + ":extremely_primordial_pearl"),
                0, 6,
                MOD + ":neutronium_plate",
                List.of(MOD + ":" + Tier.MATRIX.plateName(), MOD + ":" + Tier.NEUTRONIUM.plateName(), MOD + ":" + Tier.INFINITY.plateName()),
                2,
                ContentJson.aspects("thaumaturge:metallum", 8, "thaumaturge:machina", 6, "thaumaturge:humanus", 4, ContentJson.ASPECT_TERMINUS, 4),
                3);
    }
}
