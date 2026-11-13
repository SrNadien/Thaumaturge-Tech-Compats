package nadiendev.ThaumaturgeTechCompats.stabilizer;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class StabilizerTranslations {
    private static final String RESEARCH = "research." + ThaumaturgeTechCompats.MOD_ID + "." + StabilizerContent.RESEARCH;

    private StabilizerTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_node_stabilizer", "Crystal Matrix Node Stabilizer");
        lang.add("block.thaumaturgetechcompats.neutronium_node_stabilizer", "Neutronium Node Stabilizer");
        lang.add("block.thaumaturgetechcompats.infinity_node_stabilizer", "Infinity Node Stabilizer");

        lang.add(RESEARCH + ".title", "Avaritia Node Stabilizers");
        lang.add(RESEARCH + ".stage_0",
                "The advanced stabilizer holds a node steady, yet the node still feeds a little on the aura and it still takes its time to settle when it is unstable or fading. A lock this important deserves a better frame.<BR>Crystal matrix, neutronium and infinity should each give a frame that grips a node harder than the last. I will need a strong infusion to bind such metals around a stabilizer that already works.");
        lang.add(RESEARCH + ".stage_1",
                "Each stabilizer is infused around the previous one, using plates and ingots of its own material alternating with nitor and alumentum:<BR>- Crystal Matrix: around an Advanced Node Stabilizer.<BR>- Neutronium: around the Crystal Matrix one.<BR>- Infinity: around the Neutronium one.<BR>All three hold a node the way an Advanced Node Stabilizer does: nothing can pull from it, it can still pull from unprotected nodes nearby, and a redstone signal switches them off. What changes is the grip:<BR>- Crystal Matrix: the node feeds on the aura 50 times slower than normal, and unstable or fading nodes settle 3 times as fast as with the basic stabilizer.<BR>- Neutronium: 200 times slower, and 4 times as fast.<BR>- Infinity: 1000 times slower, and 5 times as fast. The node practically stops feeding.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_node_stabilizer", "Estabilizador de nodos de matriz de cristal");
        lang.add("block.thaumaturgetechcompats.neutronium_node_stabilizer", "Estabilizador de nodos de neutronio");
        lang.add("block.thaumaturgetechcompats.infinity_node_stabilizer", "Estabilizador de nodos de infinito");

        lang.add(RESEARCH + ".title", "Estabilizadores de Nodos de Avaritia");
        lang.add(RESEARCH + ".stage_0",
                "El estabilizador avanzado mantiene un nodo quieto, pero el nodo aún se alimenta un poco del aura y sigue tardando en asentarse cuando es inestable o se apaga. Un bloqueo tan importante merece una estructura mejor.<BR>La matriz de cristal, el neutronio y el infinito deberían dar cada uno una estructura que sujete un nodo con más fuerza que la anterior. Necesitaré una infusión potente para unir esos metales alrededor de un estabilizador que ya funciona.");
        lang.add(RESEARCH + ".stage_1",
                "Cada estabilizador se infunde alrededor del anterior, usando placas y lingotes de su propio material alternados con nitor y alumentum:<BR>- Matriz de Cristal: alrededor de un Estabilizador de nodos avanzado.<BR>- Neutronio: alrededor del de Matriz de Cristal.<BR>- Infinito: alrededor del de Neutronio.<BR>Los tres retienen un nodo como un Estabilizador de nodos avanzado: nada puede absorber de él, él aún puede absorber de nodos cercanos sin proteger, y una señal de redstone los apaga. Lo que cambia es la fuerza del agarre:<BR>- Matriz de Cristal: el nodo se alimenta del aura 50 veces más despacio de lo normal, y los nodos inestables o que se apagan se asientan 3 veces más rápido que con el estabilizador básico.<BR>- Neutronio: 200 veces más despacio, y 4 veces más rápido.<BR>- Infinito: 1000 veces más despacio, y 5 veces más rápido. El nodo prácticamente deja de alimentarse.");
    }
}
