package nadiendev.ThaumaturgeTechCompats.compats.enderio;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.CapacitorTranslations;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.conduit.ConduitTranslations;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.wand.EnderIOWandTranslations;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class EnderIOTranslations {
    private static final String ITEM = "item." + ThaumaturgeTechCompats.MOD_ID + ".";

    private EnderIOTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(ITEM + GrindingBall.BRASS.itemName(), "Brass Grinding Ball");
        lang.add(ITEM + GrindingBall.THAUMIUM.itemName(), "Thaumium Grinding Ball");
        lang.add(ITEM + GrindingBall.VOID_METAL.itemName(), "Void Metal Grinding Ball");
        ConduitTranslations.english(lang);
        CapacitorTranslations.english(lang);
        EnderIOWandTranslations.english(lang);
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(ITEM + GrindingBall.BRASS.itemName(), "Bola de molienda de latón");
        lang.add(ITEM + GrindingBall.THAUMIUM.itemName(), "Bola de molienda de taumio");
        lang.add(ITEM + GrindingBall.VOID_METAL.itemName(), "Bola de molienda de metal del vacío");
        ConduitTranslations.spanish(lang);
        CapacitorTranslations.spanish(lang);
        EnderIOWandTranslations.spanish(lang);
    }
}
