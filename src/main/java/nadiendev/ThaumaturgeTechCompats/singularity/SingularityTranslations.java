package nadiendev.ThaumaturgeTechCompats.singularity;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class SingularityTranslations {
    private SingularityTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (SingularityKind kind : SingularityKind.values()) {
            lang.add(kind.displayKey(), kind.english());
        }
    }

    public static void spanish(LanguageProvider lang) {
        for (SingularityKind kind : SingularityKind.values()) {
            lang.add(kind.displayKey(), kind.spanish());
        }
    }
}
