package nadiendev.ThaumaturgeTechCompats.compats.tags;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public final class ThaumaturgeAspects {
    private static final String MOD_ID = "thaumaturge";

    private ThaumaturgeAspects() {
    }

    public static Set<String> definedKeys() {
        Set<String> keys = new HashSet<>();
        IModFileInfo file = ModList.get().getModFileById(MOD_ID);
        if (file == null) {
            return keys;
        }
        Path path = file.getFile().findResource("data", MOD_ID, "data_maps", "item", "base_aspects.json");
        if (!Files.isRegularFile(path)) {
            return keys;
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            JsonElement root = JsonParser.parseReader(reader);
            JsonObject values = root.getAsJsonObject().getAsJsonObject("values");
            keys.addAll(values.keySet());
        } catch (IOException | RuntimeException exception) {
            return keys;
        }
        return keys;
    }
}
