package nadiendev.ThaumaturgeTechCompats.nodepower;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class NodePowerTranslations {
    private static final String ENTRY = "research.thaumaturgetechcompats." + FluxJarContent.ENTRY;
    private static final String VARIANTS = "research.thaumaturgetechcompats." + FluxJarContent.VARIANTS_ENTRY;
    private static final String RIFT = "research.thaumaturgetechcompats." + FluxJarContent.RIFT_ENTRY;

    private NodePowerTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.flux_jar", "Flux Jar");
        lang.add("block.thaumaturgetechcompats.matrix_flux_jar", "Crystal Matrix Flux Jar");
        lang.add("block.thaumaturgetechcompats.neutronium_flux_jar", "Neutronium Flux Jar");
        lang.add("block.thaumaturgetechcompats.infinity_flux_jar", "Infinity Flux Jar");
        lang.add("message.thaumaturgetechcompats.flux_jar", "Flux: %s / %s");
        lang.add("block.thaumaturgetechcompats.rift_jar", "Rift Jar");
        lang.add("message.thaumaturgetechcompats.rift_jar", "Rift power: %s / %s");
        lang.add(RIFT + ".title", "Rift Jar");
        lang.add(RIFT + ".stage_0",
                "A flux rift cannot be sealed, only worn down. The Void Siphon already shrinks one a little at a time and gives me seeds for my trouble.<BR>If a jar could do the same with the tear itself, it would keep what the rift gives up instead of letting it spill.");
        lang.add(RIFT + ".stage_1",
                "The Rift Jar is made on an arcane workbench from a warded jar, two void seeds and two void metal plates.<BR>Set beside a flux rift, it takes the rift apart one point at a time and keeps that as power for any machine that uses magical power. It stops once the rift is down to its smallest size. A broken Rift Jar spills what it still holds back into the aura as flux.");

        lang.add(ENTRY + ".title", "Flux Jar");
        lang.add(ENTRY + ".stage_0",
                "Flux is only a danger while it is loose. A vessel lined with void metal should be able to draw it out of the aura of its chunk and keep it.<BR>Whatever machine stands beside it could then burn that flux, instead of letting it poison the land.");
        lang.add(ENTRY + ".stage_1",
                "The Flux Jar is made on an arcane workbench from a warded jar surrounded by four void metal plates.<BR>It slowly pulls flux from the aura around it until it is full, and machines that work on magical power can use that flux as their source. A hungry node is never a safe source, and neither is a jar that has been broken: breaking the Flux Jar pours everything it holds back into the aura.");

        lang.add(VARIANTS + ".title", "Flux Jars of Avaritia");
        lang.add(VARIANTS + ".stage_0",
                "A jar of void metal holds only so much, and it fills only so fast. The same materials that stretched my warded jars should stretch these ones too.<BR>Crystal matrix, neutronium and infinity should let a Flux Jar hold far more and draw the aura far quicker.");
        lang.add(VARIANTS + ".stage_1",
                "Each Flux Jar is made on an infusion altar, starting from the one before it:<BR>- The Crystal Matrix Flux Jar needs a Flux Jar and crystal matrix ingots.<BR>- The Neutronium Flux Jar needs a Crystal Matrix Flux Jar and neutron ingots.<BR>- The Infinity Flux Jar needs a Neutronium Flux Jar, infinity ingots and an Extremely Primordial Pearl.<BR>They work like the plain Flux Jar, only bigger and faster, and breaking one still pours its flux back into the aura.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.flux_jar", "Jarra de flux");
        lang.add("block.thaumaturgetechcompats.matrix_flux_jar", "Jarra de flux de Matriz de Cristal");
        lang.add("block.thaumaturgetechcompats.neutronium_flux_jar", "Jarra de flux de Neutronio");
        lang.add("block.thaumaturgetechcompats.infinity_flux_jar", "Jarra de flux del Infinito");
        lang.add("message.thaumaturgetechcompats.flux_jar", "Flux: %s / %s");
        lang.add("block.thaumaturgetechcompats.rift_jar", "Jarra de grieta");
        lang.add("message.thaumaturgetechcompats.rift_jar", "Poder de la grieta: %s / %s");
        lang.add(RIFT + ".title", "Jarra de grieta");
        lang.add(RIFT + ".stage_0",
                "Una grieta de flux no se puede sellar, solo desgastar. El Sifón del Vacío ya encoge una poco a poco y me da semillas por las molestias.<BR>Si una jarra pudiera hacer lo mismo con el propio desgarro, guardaría lo que la grieta suelta en vez de dejar que se derrame.");
        lang.add(RIFT + ".stage_1",
                "La Jarra de grieta se fabrica en una mesa de trabajo arcana con un frasco arcano, dos semillas del vacío y dos placas de metal del vacío.<BR>Colocada junto a una grieta de flux, la desmonta punto a punto y guarda eso como poder para cualquier máquina que use poder mágico. Se detiene cuando la grieta llega a su tamaño mínimo. Una Jarra de grieta rota devuelve al aura, como flux, lo que aún guarda.");

        lang.add(ENTRY + ".title", "Jarra de flux");
        lang.add(ENTRY + ".stage_0",
                "El flux solo es un peligro mientras anda suelto. Un recipiente forrado de metal del vacío debería poder sacarlo del aura de su chunk y guardarlo.<BR>Cualquier máquina que esté a su lado podría quemar ese flux, en vez de dejar que envenene la tierra.");
        lang.add(ENTRY + ".stage_1",
                "La Jarra de flux se fabrica en una mesa de trabajo arcana con un frasco arcano rodeado de cuatro placas de metal del vacío.<BR>Va sacando flux del aura que la rodea hasta llenarse, y las máquinas que funcionan con poder mágico pueden usar ese flux como fuente. Un nodo hambriento nunca es una fuente segura, y una jarra rota tampoco: al romper la Jarra de flux, todo lo que guarda vuelve al aura.");

        lang.add(VARIANTS + ".title", "Jarras de flux de Avaritia");
        lang.add(VARIANTS + ".stage_0",
                "Una jarra de metal del vacío guarda lo que guarda y se llena a su ritmo. Los mismos materiales que ensancharon mis frascos arcanos deberían ensanchar estas.<BR>La matriz de cristal, el neutronio y el infinito deberían dejar que una Jarra de flux guarde mucho más y aspire el aura mucho más rápido.");
        lang.add(VARIANTS + ".stage_1",
                "Cada Jarra de flux se fabrica en un altar de infusión, partiendo de la anterior:<BR>- La Jarra de flux de Matriz de Cristal necesita una Jarra de flux y lingotes de matriz de cristal.<BR>- La Jarra de flux de Neutronio necesita una Jarra de flux de Matriz de Cristal y lingotes de neutrones.<BR>- La Jarra de flux del Infinito necesita una Jarra de flux de Neutronio, lingotes de infinito y una Perla Extremadamente Primordial.<BR>Funcionan como la Jarra de flux normal, solo que más grandes y rápidas, y al romper una el flux que guarda sigue volviendo al aura.");
    }
}
