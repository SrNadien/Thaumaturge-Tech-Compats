package nadiendev.ThaumaturgeTechCompats.scribing;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ScribingTranslations {
    private static final String ENTRY = "research.thaumaturgetechcompats." + ScribingDataProvider.RESEARCH_ENTRY;

    private ScribingTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("item.thaumaturgetechcompats.matrix_scribing_tools", "Crystal Matrix Scribing Tools");
        lang.add("item.thaumaturgetechcompats.neutronium_scribing_tools", "Neutronium Scribing Tools");
        lang.add("item.thaumaturgetechcompats.infinity_scribing_tools", "Infinity Scribing Tools");

        lang.add(ENTRY + ".title", "Scribing Tools of Avaritia");
        lang.add(ENTRY + ".stage_0",
                "My scribing tools run dry far too quickly. Every aspect I place on a research note drains a little of the ink, and a long night of research can empty a whole set.<BR>A quill and an inkwell made of crystal matrix, neutronium and infinity should hold much more ink, and the last of them may never run out.");
        lang.add(ENTRY + ".stage_1",
                "Each set is made on an infusion altar, starting from ordinary scribing tools:<BR>- The Crystal Matrix tools are surrounded by crystal matrix ingots.<BR>- The Neutronium tools need the Crystal Matrix tools and neutron ingots.<BR>- The Infinity tools need the Neutronium tools, infinity ingots and an Extremely Primordial Pearl.<BR>All of them work on a research table exactly like ordinary scribing tools. The Infinity tools restore their ink on their own while I carry them. The Crystal Matrix and Neutronium tools can be refilled with black dye, like the ordinary ones.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("item.thaumaturgetechcompats.matrix_scribing_tools", "Herramientas de escritura de Matriz de Cristal");
        lang.add("item.thaumaturgetechcompats.neutronium_scribing_tools", "Herramientas de escritura de Neutronio");
        lang.add("item.thaumaturgetechcompats.infinity_scribing_tools", "Herramientas de escritura del Infinito");

        lang.add(ENTRY + ".title", "Herramientas de escritura de Avaritia");
        lang.add(ENTRY + ".stage_0",
                "Mis herramientas de escritura se quedan sin tinta demasiado rápido. Cada aspecto que coloco en una nota de investigación gasta un poco de tinta, y una larga noche de estudio puede vaciar un juego entero.<BR>Una pluma y un tintero de matriz de cristal, neutronio e infinito deberían guardar mucha más tinta, y el último quizá no se agote nunca.");
        lang.add(ENTRY + ".stage_1",
                "Cada juego se fabrica en un altar de infusión, partiendo de unas herramientas de escritura normales:<BR>- Las de Matriz de Cristal se rodean de lingotes de matriz de cristal.<BR>- Las de Neutronio necesitan las de Matriz de Cristal y lingotes de neutrones.<BR>- Las del Infinito necesitan las de Neutronio, lingotes de infinito y una Perla Extremadamente Primordial.<BR>Todas funcionan en una mesa de investigación igual que las normales. Las del Infinito recuperan su tinta solas mientras las llevo encima. Las de Matriz de Cristal y Neutronio se pueden rellenar con tinte negro, como las normales.");
    }
}
