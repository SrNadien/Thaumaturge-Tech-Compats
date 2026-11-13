package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class AvaritiaMachinesTranslations {
    private static final String BLOCK = "block.thaumaturgetechcompats.";
    private static final String ENTRY = "research.thaumaturgetechcompats." + AvaritiaMachinesContent.ENTRY;

    private AvaritiaMachinesTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (AvaritiaMachineTier tier : AvaritiaMachineTier.values()) {
            lang.add(BLOCK + tier.collectorName(), tier.shortEnglish() + " Neutron Collector");
            lang.add(BLOCK + tier.compressorName(), tier.shortEnglish() + " Neutron Compressor");
        }
        lang.add("aspect.thaumaturgetechcompats.nuclear", "Nuclear");
        lang.add("aspect.thaumaturgetechcompats.nuclear.desc", "Radiation, Atomic Power, Fission");
        lang.add("aspect.thaumaturgetechcompats.nuclear.help", "the power locked in the heart of matter");
        lang.add("aspect.thaumaturgetechcompats.cosmic", "Cosmic");
        lang.add("aspect.thaumaturgetechcompats.cosmic.desc", "Stars, the Universe, Infinity");
        lang.add("aspect.thaumaturgetechcompats.cosmic.help", "the vastness between the stars");
        lang.add(ENTRY + ".title", "Higher Neutronium Machines");
        lang.add(ENTRY + ".stage_0",
                "The Neutronium Collector and Compressor of Avaritia already grow through three denser forms, yet even the Densest one is slow to a mind that has seen the Terminus. If the infusion altar could lend them the metals that stretched my jars, they should work faster still.<BR>Thaumium, alchemical brass and void metal might let these machines run several times over in a single tick, and give more for the same work.");
        lang.add(ENTRY + ".stage_1",
                "Every machine is made on an infusion altar, starting from the one before it:<BR>- The Thaumium Neutron Collector and Compressor need the Densest Neutronium Collector or Compressor, four thaumium plates and a Simple Arcane Mechanism.<BR>- The Brass Neutron Collector and Compressor need the Thaumium machine, four alchemical brass plates and a Simple Arcane Mechanism.<BR>- The Void Neutron Collector and Compressor need the Brass machine, four void metal plates and a Void Seed.<BR>They work exactly like the machines of Avaritia and use the same recipes, only faster, and each tier also gives more every time it finishes. How much faster and how much more is set in the configuration.<BR>Each tier can only be infused on an altar whose center is the Runic Matrix of its own tier, and that matrix brings its own instability to the craft:<BR>- The Thaumium machines need the Crystal Matrix one: solar instability, with fires, lightning and burns.<BR>- The Brass machines need the Neutronium one: nuclear instability, with radiation, flux poured into the aura and a meltdown that explodes.<BR>- The Void machines need the Infinity one: cosmic instability, with flux torn open, warp, creatures from beyond and everything nearby thrown elsewhere.<BR>The Brass and Void machines also ask for two new essences. Nuclear, made of potentia and perditio, is the power locked in the heart of matter. Cosmic, made of alienis and auram, is the vastness between the stars. The Brass machines ask for Nuclear, and the Void machines ask for Nuclear and Cosmic.");
    }

    public static void spanish(LanguageProvider lang) {
        for (AvaritiaMachineTier tier : AvaritiaMachineTier.values()) {
            lang.add(BLOCK + tier.collectorName(), "Colector neutrónico " + tier.shortSpanish());
            lang.add(BLOCK + tier.compressorName(), "Compresor neutrónico " + tier.shortSpanish());
        }
        lang.add("aspect.thaumaturgetechcompats.nuclear", "Nuclear");
        lang.add("aspect.thaumaturgetechcompats.nuclear.desc", "Radiación, energía atómica, fisión");
        lang.add("aspect.thaumaturgetechcompats.nuclear.help", "el poder encerrado en el corazón de la materia");
        lang.add("aspect.thaumaturgetechcompats.cosmic", "Cósmico");
        lang.add("aspect.thaumaturgetechcompats.cosmic.desc", "Estrellas, el universo, el infinito");
        lang.add("aspect.thaumaturgetechcompats.cosmic.help", "la inmensidad entre las estrellas");
        lang.add(ENTRY + ".title", "Máquinas de neutronio superiores");
        lang.add(ENTRY + ".stage_0",
                "El Colector y el Compresor de neutronio de Avaritia ya crecen en tres formas más densas, pero incluso la más densa es lenta para una mente que ha visto el Terminus. Si el altar de infusión pudiera prestarles los metales que ensancharon mis frascos, deberían trabajar todavía más rápido.<BR>El taumio, el latón alquímico y el metal del vacío quizá dejen que estas máquinas funcionen varias veces en un solo tick, y que den más por el mismo trabajo.");
        lang.add(ENTRY + ".stage_1",
                "Cada máquina se fabrica en un altar de infusión, partiendo de la anterior:<BR>- El Colector y el Compresor neutrónicos de taumio necesitan el Colector o el Compresor de neutronio más denso, cuatro placas de taumio y un Mecanismo arcano simple.<BR>- El Colector y el Compresor neutrónicos de latón necesitan la máquina de taumio, cuatro placas de latón alquímico y un Mecanismo arcano simple.<BR>- El Colector y el Compresor neutrónicos del vacío necesitan la máquina de latón, cuatro placas de metal del vacío y una Semilla del vacío.<BR>Funcionan igual que las máquinas de Avaritia y usan las mismas recetas, solo que más rápido, y cada nivel además da más cada vez que termina. Cuánto más rápido y cuánto más se ajusta en la configuración.<BR>Cada nivel solo se puede infundir en un altar cuyo centro sea la Matriz rúnica de su propio nivel, y esa matriz trae su propia inestabilidad a la fabricación:<BR>- Las máquinas de taumio necesitan la de Matriz de Cristal: inestabilidad solar, con fuegos, rayos y quemaduras.<BR>- Las máquinas de latón necesitan la de Neutronio: inestabilidad nuclear, con radiación, flux derramado en el aura y una fusión que explota.<BR>- Las máquinas del vacío necesitan la de Infinito: inestabilidad cósmica, con flux desgarrado, disformidad, criaturas del más allá y todo lo cercano lanzado a otra parte.<BR>Las máquinas de latón y del vacío también piden dos esencias nuevas. Nuclear, hecha de potentia y perditio, es el poder encerrado en el corazón de la materia. Cósmico, hecho de alienis y auram, es la inmensidad entre las estrellas. Las máquinas de latón piden Nuclear, y las del vacío piden Nuclear y Cósmico.");
    }
}
