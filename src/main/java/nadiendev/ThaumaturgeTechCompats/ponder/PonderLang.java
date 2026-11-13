package nadiendev.ThaumaturgeTechCompats.ponder;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.ponder.client.TuuPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;

public final class PonderLang {
    private static boolean registered;

    private PonderLang() {
    }

    public static void english(LanguageProvider lang) {
        ensurePlugin();
        PonderIndex.getLangAccess().provideLang(ThaumaturgeTechCompats.MOD_ID, lang::add);
    }

    public static void spanish(LanguageProvider lang) {
        ensurePlugin();
        Map<String, String> dictionary = dictionary();
        PonderIndex.getLangAccess().provideLang(ThaumaturgeTechCompats.MOD_ID, (key, value) -> {
            String translated = dictionary.get(value);
            if (translated == null) {
                throw new IllegalStateException("Missing Spanish translation for " + key + ": " + value);
            }
            lang.add(key, translated);
        });
    }

    private static void ensurePlugin() {
        if (!registered) {
            PonderIndex.addPlugin(new TuuPonderPlugin());
            registered = true;
        }
    }

    private static Map<String, String> dictionary() {
        Map<String, String> dictionary = new HashMap<>();
        put(dictionary, PonderScripts.MULTIBLOCKS_TITLE, PonderScripts.MULTIBLOCKS_TITLE_ES);
        put(dictionary, PonderScripts.MULTIBLOCKS_DESCRIPTION, PonderScripts.MULTIBLOCKS_DESCRIPTION_ES);
        for (PonderScript script : PonderScripts.ALL) {
            put(dictionary, script.title(), script.spanishTitle());
            for (int line = 0; line < script.lines().size(); line++) {
                put(dictionary, script.lines().get(line), script.spanishLines().get(line));
            }
        }
        return dictionary;
    }

    private static void put(Map<String, String> dictionary, String english, String spanish) {
        String previous = dictionary.put(english, spanish);
        if (previous != null && !previous.equals(spanish)) {
            throw new IllegalStateException("Conflicting translations for " + english);
        }
    }
}
