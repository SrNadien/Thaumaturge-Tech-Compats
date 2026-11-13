package nadiendev.ThaumaturgeTechCompats.compats.tags;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.neoforged.fml.ModList;
import net.neoforged.fml.jarcontents.JarContents;
import net.neoforged.neoforgespi.language.IModFileInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
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
        JarContents contents = file.getFile().getContents();
        String path = "data/" + MOD_ID + "/data_maps/item/base_aspects.json";
        if (!contents.containsFile(path)) {
            return keys;
        }
        try (Reader reader = new InputStreamReader(contents.openFile(path), StandardCharsets.UTF_8)) {
            JsonElement root = JsonParser.parseReader(reader);
            JsonObject values = root.getAsJsonObject().getAsJsonObject("values");
            keys.addAll(values.keySet());
        } catch (IOException | RuntimeException exception) {
            return keys;
        }
        return keys;
    }
}
