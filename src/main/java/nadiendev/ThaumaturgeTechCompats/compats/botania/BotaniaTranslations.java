package nadiendev.ThaumaturgeTechCompats.compats.botania;

import nadiendev.ThaumaturgeTechCompats.compats.botania.spreader.BotaniaSpreaderTranslations;
import nadiendev.ThaumaturgeTechCompats.compats.botania.wand.BotaniaWandTranslations;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class BotaniaTranslations {
    private BotaniaTranslations() {
    }

    public static void english(LanguageProvider lang) {
        BotaniaSpreaderTranslations.english(lang);
        BotaniaWandTranslations.english(lang);
    }

    public static void spanish(LanguageProvider lang) {
        BotaniaSpreaderTranslations.spanish(lang);
        BotaniaWandTranslations.spanish(lang);
    }
}
