package nadiendev.ThaumaturgeTechCompats.registry;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class CreativeTabsTranslations {
    private CreativeTabsTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (CreativeParent parent : CreativeParent.values()) {
            lang.add(parent.key(), parent.english());
        }
        for (CreativeSection section : CreativeSection.values()) {
            lang.add(section.key(), section.english());
        }
    }

    public static void spanish(LanguageProvider lang) {
        for (CreativeParent parent : CreativeParent.values()) {
            lang.add(parent.key(), parent.spanish());
        }
        for (CreativeSection section : CreativeSection.values()) {
            lang.add(section.key(), section.spanish());
        }
    }
}
