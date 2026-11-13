package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class IndustrialForegoingTranslations {
    public static final String AURA_TITLE = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".magical_laser.aura";
    public static final String AURA_AMOUNT = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".magical_laser.aura_amount";

    private IndustrialForegoingTranslations() {
    }

    private static String block(LaserMachine machine) {
        return "block." + ThaumaturgeTechCompats.MOD_ID + "." + machine.blockName();
    }

    private static String entry(LaserMachine machine) {
        return "research." + ThaumaturgeTechCompats.MOD_ID + "." + IndustrialForegoingContent.research(machine);
    }

    public static void english(LanguageProvider lang) {
        lang.add(AURA_TITLE, "Aura");
        lang.add(AURA_AMOUNT, "%s / %s vis");

        lang.add(block(LaserMachine.DRILL), "Magical Laser Drill");
        lang.add(entry(LaserMachine.DRILL) + ".title", "Magical Laser Drill");
        lang.add(entry(LaserMachine.DRILL) + ".stage_0",
                "The laser drill of the industrial engineers does nothing by itself: it only sends its beam into a laser base, which does the real digging, and it needs a steady stream of mundane power to do even that.<BR>I already know how to draw vis straight out of the aura, and nodes hold more power than any wire ever carried. Joining the two should give me a drill that needs no cables at all.");
        lang.add(entry(LaserMachine.DRILL) + ".stage_1",
                "The Magical Laser Drill is born on an infusion altar from an ordinary Laser Drill. It looks and works like the original, with the same screen painted purple, but its bar holds vis instead of power.<BR>Place it so that its emitting face looks at any Laser Base, mundane or magical. The base must be inside the area of three blocks per side centred two blocks in front of it, and the screen shows the target it found. Every operation spends 1 vis, which is worth the 1000 power of the original, and gives one step of work to the base.<BR>The vis is taken from the aura of its own chunk and, when the aura cannot pay, from a node, a jarred node, a Flux Jar, a Rift Jar or a flux rift within reach. A hungry node is never a source. Redstone, addons and the settings of the machine work as in the original. The cost, the speed, the buffer and the sources can all be changed in its configuration.");

        lang.add(block(LaserMachine.ORE_BASE), "Magical Ore Laser Base");
        lang.add(entry(LaserMachine.ORE_BASE) + ".title", "Magical Ore Laser Base");
        lang.add(entry(LaserMachine.ORE_BASE) + ".stage_0",
                "The magical drill sends its beam, but something has to receive it and turn it into stone and metal. The base of the industrial engineers does it with lenses of colored glass that tilt the odds toward the ore of their color.<BR>The beam is the same and so is the digging. All I need is a base wrapped in the same violet metal as the drill.");
        lang.add(entry(LaserMachine.ORE_BASE) + ".stage_1",
                "The Magical Ore Laser Base is born on an infusion altar from an ordinary Ore Laser Base and a diamond pickaxe. It is the same machine with its screen painted purple: six lens slots, fifteen output slots and the depth that decides which ores can appear.<BR>Lenses raise the chance of the ores of their color, and the work needs no power of its own: it advances every time a Laser Drill, mundane or magical, sends its beam into it. An efficiency addon shortens the work. Hoppers and pipes can take the output out as in the original.<BR>The number of steps, the weight given by each lens and the base itself can be changed in its configuration.");

        lang.add(block(LaserMachine.FLUID_BASE), "Magical Fluid Laser Base");
        lang.add(entry(LaserMachine.FLUID_BASE) + ".title", "Magical Fluid Laser Base");
        lang.add(entry(LaserMachine.FLUID_BASE) + ".stage_0",
                "The ore base listens to the beam of the drill, but the engineers built a second base that does not dig stone: it draws fluids out of the deep earth when a lens tunes it to them.<BR>It is the same beam and the same violet metal, with a tank where the ore base keeps its chest.");
        lang.add(entry(LaserMachine.FLUID_BASE) + ".stage_1",
                "The Magical Fluid Laser Base is born on an infusion altar from an ordinary Fluid Laser Base and a bucket. It is the same machine with its screen painted purple: one lens slot that chooses the fluid, the depth that decides where it can be found and a tank of 32000 mB with its bucket slots.<BR>It needs no power of its own: it advances every time a Laser Drill, mundane or magical, sends its beam into it. An efficiency addon shortens the work, and the fluid can be pumped out of the tank as in the original.<BR>The number of steps and the base itself can be changed in its configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(AURA_TITLE, "Aura");
        lang.add(AURA_AMOUNT, "%s / %s vis");

        lang.add(block(LaserMachine.DRILL), "Taladro Láser Mágico");
        lang.add(entry(LaserMachine.DRILL) + ".title", "Taladro Láser Mágico");
        lang.add(entry(LaserMachine.DRILL) + ".stage_0",
                "El taladro láser de los ingenieros industriales no hace nada por sí solo: solo envía su rayo a una base láser, que es quien cava de verdad, y necesita un flujo constante de energía mundana hasta para eso.<BR>Ya sé cómo extraer vis directamente del aura, y los nodos guardan más poder que cualquier cable. Unir ambas cosas debería darme un taladro que no necesite ningún cable.");
        lang.add(entry(LaserMachine.DRILL) + ".stage_1",
                "El Taladro Láser Mágico nace en un altar de infusión a partir de un Taladro Láser normal. Se ve y funciona como el original, con la misma pantalla pintada de morado, pero su barra guarda vis en lugar de energía.<BR>Colócalo de forma que su cara emisora mire a cualquier Base Láser, mundana o mágica. La base debe estar dentro del área de tres bloques por lado centrada a dos bloques por delante, y la pantalla muestra el objetivo que encontró. Cada operación consume 1 vis, que equivale a los 1000 de energía del original, y da un paso de trabajo a la base.<BR>El vis sale del aura de su propio chunk y, cuando el aura no puede pagar, de un nodo, un nodo en jarra, una Jarra de flux, una Jarra de grieta o una grieta de flujo a su alcance. Un nodo hambriento nunca es una fuente. La redstone, las mejoras y los ajustes de la máquina funcionan como en el original. El coste, la velocidad, el depósito y las fuentes se pueden cambiar en su configuración.");

        lang.add(block(LaserMachine.ORE_BASE), "Base Láser Mágica de Minerales");
        lang.add(entry(LaserMachine.ORE_BASE) + ".title", "Base Láser Mágica de Minerales");
        lang.add(entry(LaserMachine.ORE_BASE) + ".stage_0",
                "El taladro mágico envía su rayo, pero algo tiene que recibirlo y convertirlo en piedra y metal. La base de los ingenieros industriales lo hace con lentes de cristal de colores que inclinan las probabilidades hacia el mineral de su color.<BR>El rayo es el mismo y el cavado también. Solo necesito una base envuelta en el mismo metal violeta que el taladro.");
        lang.add(entry(LaserMachine.ORE_BASE) + ".stage_1",
                "La Base Láser Mágica de Minerales nace en un altar de infusión a partir de una Base Láser de Minerales normal y un pico de diamante. Es la misma máquina con su pantalla pintada de morado: seis ranuras de lente, quince de salida y la profundidad que decide qué minerales pueden aparecer.<BR>Las lentes suben la probabilidad de los minerales de su color, y el trabajo no necesita energía propia: avanza cada vez que un Taladro Láser, mundano o mágico, le envía su rayo. Una mejora de eficiencia acorta el trabajo. Las tolvas y las tuberías pueden sacar la salida como en el original.<BR>El número de pasos, el peso que da cada lente y la propia base se pueden cambiar en su configuración.");

        lang.add(block(LaserMachine.FLUID_BASE), "Base Láser Mágica de Fluidos");
        lang.add(entry(LaserMachine.FLUID_BASE) + ".title", "Base Láser Mágica de Fluidos");
        lang.add(entry(LaserMachine.FLUID_BASE) + ".stage_0",
                "La base de minerales escucha el rayo del taladro, pero los ingenieros construyeron una segunda base que no cava piedra: extrae fluidos de lo más profundo de la tierra cuando una lente la sintoniza con ellos.<BR>Es el mismo rayo y el mismo metal violeta, con un tanque donde la base de minerales guarda su cofre.");
        lang.add(entry(LaserMachine.FLUID_BASE) + ".stage_1",
                "La Base Láser Mágica de Fluidos nace en un altar de infusión a partir de una Base Láser de Fluidos normal y un cubo. Es la misma máquina con su pantalla pintada de morado: una ranura de lente que elige el fluido, la profundidad que decide dónde puede encontrarse y un tanque de 32000 mB con sus ranuras de cubo.<BR>No necesita energía propia: avanza cada vez que un Taladro Láser, mundano o mágico, le envía su rayo. Una mejora de eficiencia acorta el trabajo, y el fluido se puede bombear del tanque como en el original.<BR>El número de pasos y la propia base se pueden cambiar en su configuración.");
    }
}
