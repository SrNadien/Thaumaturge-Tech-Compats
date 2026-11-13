package nadiendev.ThaumaturgeTechCompats.compats.oritech.intelligence;

import net.neoforged.neoforge.common.data.LanguageProvider;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;

public final class UnholyIntelligenceTranslations {
    private UnholyIntelligenceTranslations() {
    }

    public static void english(LanguageProvider lang) {
        entry(lang, "Unholy Intelligence",
                "The engineers of Oritech steal the spark of thought from an Allay or a Vex with a dubious container, or boil it out of strange matter in a centrifuge. But I already keep a mind in a jar. If a brain in a jar can think, a vessel built to hold what machines cannot should be able to hold that thought too.",
                "At the arcane workbench, a Brain in a Jar, a clockwork mind, two complex mechanisms, four thaumium plates and a Dubious Container of Oritech become an Unholy Intelligence, the same logic core that Oritech obtains by capturing an Allay or from the centrifuge.<BR>The workbench needs the vis and the order and entropy crystals shown in the recipe. The recipe can be turned off in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        entry(lang, "Inteligencia profana",
                "Los ingenieros de Oritech le roban la chispa del pensamiento a un allay o a un vex con un contenedor dudoso, o la hierven fuera de la materia extraña en una centrifugadora. Pero yo ya guardo una mente en un tarro. Si un cerebro en un tarro puede pensar, un recipiente hecho para contener lo que las máquinas no pueden debería poder contener ese pensamiento también.",
                "En la mesa de trabajo arcana, un cerebro en un tarro, una mente de relojería, dos mecanismos complejos, cuatro placas de taumio y un contenedor dudoso de Oritech se convierten en una inteligencia profana, el mismo núcleo lógico que Oritech obtiene capturando un allay o en la centrifugadora.<BR>La mesa necesita el vis y los cristales de orden y entropía que muestra la receta. La receta se puede desactivar en la configuración.");
    }

    private static void entry(LanguageProvider lang, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + UnholyIntelligenceContent.RESEARCH;
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
