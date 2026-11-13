package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

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

public class PearlAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final int HALO_COLOR = -16777216;
    private static final int HALO_SIZE = 4;
    private static final int INFINITY_HALO_SIZE = 10;

    private final PackOutput output;

    public PearlAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            String name = PearlRegistry.name(tier);
            tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", itemModel(tier, name)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats planetary pearl assets";
    }

    private static JsonObject itemModel(PlanetaryTier tier, String name) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD + ":item/" + name);
        textures.addProperty("halo", "avaritia:misc/halo");

        JsonObject halo = new JsonObject();
        halo.addProperty("texture", "#halo");
        halo.addProperty("color", HALO_COLOR);
        halo.addProperty("size", tier == PlanetaryTier.INFINITY ? INFINITY_HALO_SIZE : HALO_SIZE);
        halo.addProperty("pulse", true);

        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");
        json.add("textures", textures);
        json.addProperty("loader", "avaritia:halo");
        json.add("halo", halo);
        return json;
    }
}
