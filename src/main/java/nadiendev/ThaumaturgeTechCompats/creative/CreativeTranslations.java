package nadiendev.ThaumaturgeTechCompats.creative;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class CreativeTranslations {
    private static final String ENTRY = "research.thaumaturgetechcompats." + CreativeContent.RESEARCH;

    private CreativeTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(ENTRY + ".title", "Creative Artifacts");
        lang.add(ENTRY + ".stage_0",
                "Some tools of Thaumaturge were never meant to be built by mortal hands: a device that plants whole nodes in the aura, and a Thaumonomicon that already knows every secret there is to know.<BR>With the Akashic Records, the pearls and plates of infinity and a great deal of aspects, an infusion altar might be forced to forge them anyway.");
        lang.add(ENTRY + ".stage_1",
                "Both artifacts are made on an infusion altar:<BR>- The Creative Node Placer is infused from a Node Jar, with infinity and neutronium plates, neutron ingots and primordial pearls.<BR>- The Cheater's Thaumonomicon is infused from a Thaumonomicon, with the Akashic Records, an Extremely Primordial Pearl, infinity plates and infinity ingots.<BR>Their power is the same as the ones from the creative world, so I should use them with care.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(ENTRY + ".title", "Artefactos Creativos");
        lang.add(ENTRY + ".stage_0",
                "Algunas herramientas de Thaumaturge nunca estuvieron pensadas para que manos mortales las construyan: un artefacto que planta nodos enteros en el aura y un Thaumonomicon que ya conoce todos los secretos que existen.<BR>Con los Registros Akáshicos, las perlas y placas de infinito y una enorme cantidad de aspectos, quizá un altar de infusión pueda forzarse a forjarlos igualmente.");
        lang.add(ENTRY + ".stage_1",
                "Ambos artefactos se fabrican en un altar de infusión:<BR>- El Colocador de Nodos Creativo se infunde a partir de un Tarro de Nodo, con placas de infinito y de neutronio, lingotes de neutrones y perlas primordiales.<BR>- El Thaumonomicon del Tramposo se infunde a partir de un Thaumonomicon, con los Registros Akáshicos, una Perla Extremadamente Primordial, placas de infinito y lingotes de infinito.<BR>Su poder es el mismo que el de los del mundo creativo, así que debo usarlos con cuidado.");
    }
}
