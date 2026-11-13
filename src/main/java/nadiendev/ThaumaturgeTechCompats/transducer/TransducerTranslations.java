package nadiendev.ThaumaturgeTechCompats.transducer;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class TransducerTranslations {
    private static final String RESEARCH = "research." + ThaumaturgeTechCompats.MOD_ID + "." + TransducerContent.RESEARCH;

    private TransducerTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_node_transducer", "Crystal Matrix Node Transducer");
        lang.add("block.thaumaturgetechcompats.neutronium_node_transducer", "Neutronium Node Transducer");
        lang.add("block.thaumaturgetechcompats.infinity_node_transducer", "Infinity Node Transducer");

        lang.add(RESEARCH + ".title", "Avaritia Node Transducers");
        lang.add(RESEARCH + ".stage_0",
                "Waking a node with the plain transducer is slow work. Every point of power must be fed in one tick at a time, and while the signal flickers the charge leaks away just as slowly.<BR>The ingots of Avaritia carry far more current. A transducer shaped from them should pour power into a node many times faster, and hold on to it longer.");
        lang.add(RESEARCH + ".stage_1",
                "Each transducer is built around the previous one, with four plates and two ingots of its own material, a comparator and a nitor:<BR>- Crystal Matrix: charges a node 2 times faster and loses charge half as fast.<BR>- Neutronium: 4 times faster, with a charge that fades 4 times slower.<BR>- Infinity: 10 times faster, with a charge that fades 10 times slower.<BR>They are placed above a stabilized node and powered with redstone exactly like the Node Transducer, and they drain the same single point of aspect each tick, so the node is energized for a fraction of the aspects.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_node_transducer", "Transductor de nodos de matriz de cristal");
        lang.add("block.thaumaturgetechcompats.neutronium_node_transducer", "Transductor de nodos de neutronio");
        lang.add("block.thaumaturgetechcompats.infinity_node_transducer", "Transductor de nodos de infinito");

        lang.add(RESEARCH + ".title", "Transductores de Nodos de Avaritia");
        lang.add(RESEARCH + ".stage_0",
                "Despertar un nodo con el transductor corriente es un trabajo lento. Cada punto de energía debe introducirse de uno en uno por tick, y mientras la señal parpadea la carga se escapa igual de despacio.<BR>Los lingotes de Avaritia transportan mucha más corriente. Un transductor hecho con ellos debería volcar energía en un nodo muchas veces más rápido, y retenerla durante más tiempo.");
        lang.add(RESEARCH + ".stage_1",
                "Cada transductor se construye alrededor del anterior, con cuatro placas y dos lingotes de su propio material, un comparador y un nitor:<BR>- Matriz de Cristal: carga un nodo 2 veces más rápido y pierde la carga a la mitad de velocidad.<BR>- Neutronio: 4 veces más rápido, con una carga que se desvanece 4 veces más despacio.<BR>- Infinito: 10 veces más rápido, con una carga que se desvanece 10 veces más despacio.<BR>Se colocan sobre un nodo estabilizado y se activan con redstone igual que el Transductor de nodos, y drenan el mismo punto de aspecto por tick, así que el nodo se energiza con una fracción de los aspectos.");
    }
}
