package nadiendev.ThaumaturgeTechCompats.planetary.salis;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SalisAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public SalisAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (PlanetaryTier tier : SalisIntegration.tiers()) {
            String name = SalisIntegration.itemName(tier);
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", itemModel(tier, name)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats salis mundus variant assets";
    }

    private static JsonObject itemModel(PlanetaryTier tier, String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD + ":item/" + name);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");
        json.add("textures", textures);
        if (tier == PlanetaryTier.INFINITY) {
            textures.addProperty("halo", "avaritia:misc/halo");
            JsonObject halo = new JsonObject();
            halo.addProperty("texture", "#halo");
            halo.addProperty("color", -16777216);
            halo.addProperty("size", 10);
            halo.addProperty("pulse", true);
            json.addProperty("loader", "avaritia:halo");
            json.add("halo", halo);
        }
        return json;
    }
}
