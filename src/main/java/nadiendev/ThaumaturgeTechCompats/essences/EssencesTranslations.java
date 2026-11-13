package nadiendev.ThaumaturgeTechCompats.essences;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class EssencesTranslations {
    private static final String ENTRY = "research." + ThaumaturgeTechCompats.MOD_ID + "." + EssencesContent.ENTRY;

    private EssencesTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(ENTRY + ".title", "Tech Compats Essences");
        lang.add(ENTRY + ".stage_0",
                "Four essences keep showing up in my notes that are not among the primal ones: Sol, Terminus, Nuclear and Cosmic. They do not cling to just any material, so I should learn where they hide and what they leave behind in everything made from them.");
        lang.add(ENTRY + ".stage_1",
                "Each of these essences sits in the base resources, and the items built from them carry it on.<BR>- Sol, the power of the sun: glowstone dust and blocks, blaze rods and powder, magma cream, daylight detectors, sea lanterns, sunflowers, torchflowers, honey and amber, a little gold, the cells and panels of Solar Flux, the blaze and sunny quartz of Botania and the photovoltaic parts of Ender IO.<BR>- Terminus, the end of everything: the late game of Avaritia, such as infinity ingots, tools and armor, the Endest Pearl, the singularities and the higher Neutronium Collectors and Compressors.<BR>- Nuclear, radiation and fission: ancient debris, netherite, TNT and end crystals in small amounts, the uranium, plutonium, polonium and thorium of Mekanism and Oritech, their pellets and reactor rods, and the neutronium of Avaritia.<BR>- Cosmic, the stars and the infinite: nether stars, eyes of ender, ender pearls, dragon eggs and breath, shulker shells, elytra, chorus fruit, echo shards, amethyst, the Crystal Matrix and Infinity ingots, the Endest Pearl and the Void Seed.<BR>Whatever is crafted from these resources inherits their essences according to what it is made of, whether the material is vanilla, from Thaumaturge or from another mod: the Void Metal Ingot, brewed around a Void Seed, carries Cosmic, and a Neutronium Gear carries the Nuclear of its ingots. The same goes for the machines and tools of every other mod installed.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(ENTRY + ".title", "Esencias de Tech Compats");
        lang.add(ENTRY + ".stage_0",
                "En mis notas siguen apareciendo cuatro esencias que no son primarias: Sol, Terminus, Nuclear y Cósmico. No se pegan a cualquier material, así que debería aprender dónde se esconden y qué dejan en todo lo que se fabrica con ellas.");
        lang.add(ENTRY + ".stage_1",
                "Cada una de estas esencias está en los recursos base, y los objetos construidos con ellos la heredan.<BR>- Sol, el poder del sol: polvo y bloques de piedra luminosa, varas y polvo de blaze, crema de magma, sensores de luz solar, linternas marinas, girasoles, flores antorcha, miel y ámbar, un poco de oro, las células y paneles de Solar Flux, el cuarzo de blaze y el cuarzo soleado de Botania y las piezas fotovoltaicas de Ender IO.<BR>- Terminus, el fin de todo: el final de partida de Avaritia, como los lingotes, herramientas y armaduras del infinito, la Perla Endest, las singularidades y los Colectores y Compresores de neutronio superiores.<BR>- Nuclear, radiación y fisión: restos antiguos, netherita, TNT y cristales del End en pequeña cantidad, el uranio, plutonio, polonio y torio de Mekanism y Oritech, sus pellets y varillas de reactor, y el neutronio de Avaritia.<BR>- Cósmico, las estrellas y lo infinito: estrellas del Nether, ojos de ender, perlas de ender, huevos y aliento de dragón, caparazones de shulker, élitros, fruta de chorus, fragmentos de eco, amatista, los lingotes de Matriz de Cristal e Infinito, la Perla Endest y la Semilla del vacío.<BR>Todo lo que se fabrica con estos recursos hereda sus esencias según de qué esté hecho, ya sea un material de vanilla, de Thaumaturge o de otro mod: el Lingote de metal del vacío, elaborado en torno a una Semilla del vacío, lleva Cósmico, y un Engranaje de neutronio lleva el Nuclear de sus lingotes. Lo mismo vale para las máquinas y herramientas de cualquier otro mod instalado.");
    }
}
