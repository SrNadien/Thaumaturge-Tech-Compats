package nadiendev.ThaumaturgeTechCompats.jei.info;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.LinkedHashMap;
import java.util.Map;

public final class JeiInfoTranslations {
    private JeiInfoTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (Map.Entry<String, Boolean> entry : paths().entrySet()) {
            lang.add(JeiInfoTexts.key(entry.getKey()), JeiInfoTexts.text(entry.getKey(), entry.getValue()).english());
        }
    }

    public static void spanish(LanguageProvider lang) {
        for (Map.Entry<String, Boolean> entry : paths().entrySet()) {
            lang.add(JeiInfoTexts.key(entry.getKey()), JeiInfoTexts.text(entry.getKey(), entry.getValue()).spanish());
        }
    }

    private static Map<String, Boolean> paths() {
        Map<String, Boolean> paths = new LinkedHashMap<>();
        for (Map.Entry<ResourceKey<Item>, Item> entry : BuiltInRegistries.ITEM.entrySet()) {
            Identifier id = entry.getKey().identifier();
            if (ThaumaturgeTechCompats.MOD_ID.equals(id.getNamespace())) {
                paths.put(id.getPath(), entry.getValue() instanceof BlockItem);
            }
        }
        for (String path : JeiInfoTexts.taintedFoods().keySet()) {
            paths.putIfAbsent(path, false);
        }
        return paths;
    }
}
