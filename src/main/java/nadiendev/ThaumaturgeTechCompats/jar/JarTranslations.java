package nadiendev.ThaumaturgeTechCompats.jar;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class JarTranslations {
    private static final String ENTRY = "research.thaumaturgetechcompats." + JarDataProvider.RESEARCH_ENTRY;

    private JarTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_jar", "Crystal Matrix Jar");
        lang.add("block.thaumaturgetechcompats.neutronium_jar", "Neutronium Jar");
        lang.add("block.thaumaturgetechcompats.infinity_jar", "Infinity Jar");

        lang.add(ENTRY + ".title", "Jars of Avaritia");
        lang.add(ENTRY + ".stage_0",
                "A warded jar is only as good as the glass it is made from, and ordinary glass can only be warded so far.<BR>Crystal matrix, neutronium and infinity are all materials that bend the rules of what a vessel can contain. If I wrap a warded jar in them, it should hold far more essentia than any jar I have seen before.");
        lang.add(ENTRY + ".stage_1",
                "Each jar is made on an infusion altar, starting from a plain warded jar:<BR>- The Crystal Matrix Jar is surrounded by crystal matrix ingots.<BR>- The Neutronium Jar needs a Crystal Matrix Jar and neutron ingots.<BR>- The Infinity Jar needs a Neutronium Jar, infinity ingots and an Extremely Primordial Pearl.<BR>They work like any other warded jar, only larger. Labels and braces fit them as usual, and essentia is kept when the jar is picked up.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_jar", "Jarra de Matriz de Cristal");
        lang.add("block.thaumaturgetechcompats.neutronium_jar", "Jarra de Neutronio");
        lang.add("block.thaumaturgetechcompats.infinity_jar", "Jarra del Infinito");

        lang.add(ENTRY + ".title", "Jarras de Avaritia");
        lang.add(ENTRY + ".stage_0",
                "Un frasco arcano solo es tan bueno como el vidrio con el que está hecho, y el vidrio corriente solo admite cierta protección.<BR>La matriz de cristal, el neutronio y el infinito son materiales que doblan las reglas de lo que un recipiente puede contener. Si envuelvo un frasco arcano con ellos, debería guardar mucha más esencia que cualquier jarra que haya visto.");
        lang.add(ENTRY + ".stage_1",
                "Cada jarra se fabrica en un altar de infusión, partiendo de una jarra normal:<BR>- La Jarra de Matriz de Cristal se rodea de lingotes de matriz de cristal.<BR>- La Jarra de Neutronio necesita una Jarra de Matriz de Cristal y lingotes de neutrones.<BR>- La Jarra del Infinito necesita una Jarra de Neutronio, lingotes de infinito y una Perla Extremadamente Primordial.<BR>Funcionan como cualquier otra jarra, solo que más grandes. Las etiquetas y los seguros encajan como siempre, y la esencia se conserva al recoger la jarra.");
    }
}
