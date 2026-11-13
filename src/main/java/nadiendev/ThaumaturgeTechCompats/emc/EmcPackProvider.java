package nadiendev.ThaumaturgeTechCompats.emc;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class EmcPackProvider implements DataProvider {
    private static final int DATA_PACK_FORMAT = 48;

    private final PackOutput output;

    public EmcPackProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        JsonObject pack = new JsonObject();
        pack.addProperty("description", "Base EMC values of Thaumaturge Tech Compats for Equivox.");
        pack.addProperty("pack_format", DATA_PACK_FORMAT);
        JsonObject json = new JsonObject();
        json.add("pack", pack);
        return ContentJson.save(output, cache, EmcIntegration.PACK_PATH + "/pack.mcmeta", json);
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Equivox pack";
    }
}
