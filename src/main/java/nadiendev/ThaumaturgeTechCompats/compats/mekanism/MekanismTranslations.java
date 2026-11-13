package nadiendev.ThaumaturgeTechCompats.compats.mekanism;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class MekanismTranslations {
    private static final String ITEM = "item." + ThaumaturgeTechCompats.MOD_ID + ".";

    private MekanismTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(ITEM + "osmium_cluster", "Native Osmium Cluster");
        lang.add(ITEM + "uranium_cluster", "Native Uranium Cluster");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(ITEM + "osmium_cluster", "Racimo de osmio");
        lang.add(ITEM + "uranium_cluster", "Racimo de uranio");
    }
}
