package nadiendev.ThaumaturgeTechCompats.ponder;

import nadiendev.ThaumaturgeTechCompats.braincharger.BoostTier;
import nadiendev.ThaumaturgeTechCompats.braincharger.BrainChargerConfig;
import nadiendev.ThaumaturgeTechCompats.device.DeviceConfig;
import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;

import java.util.List;

public final class PonderScripts {
    public static final String MULTIBLOCKS_TAG = "multiblocks";
    public static final String MULTIBLOCKS_TITLE = "Multiblock Structures";
    public static final String MULTIBLOCKS_TITLE_ES = "Estructuras multibloque";
    public static final String MULTIBLOCKS_DESCRIPTION = "Machines of Thaumaturge and its Avaritia additions that are built out of several blocks";
    public static final String MULTIBLOCKS_DESCRIPTION_ES = "Máquinas de Thaumaturge y de sus añadidos de Avaritia que se construyen con varios bloques";

    public static final PonderScript INFUSION_ARCANE = new PonderScript("infusion_altar_arcane",
            "Infusion Altar", "Altar de infusión",
            List.of(
                    "The Infusion Altar is built from Arcane Stone: four pillars, a pedestal in the middle and a Runic Matrix floating two blocks above it.",
                    "Sprinkle Salis Mundus over the structure to bind it. The pillars take shape and the altar is ready.",
                    "Place more pedestals around the altar and put the ingredients on them. The item to infuse goes on the central pedestal.",
                    "Click the Runic Matrix with a casting tool to begin. Essentia is drained from nearby containers and instability builds up while it works."),
            List.of(
                    "El altar de infusión se construye con piedra arcana: cuatro pilares, un pedestal en el centro y una matriz rúnica flotando dos bloques por encima.",
                    "Espolvorea Salis Mundus sobre la estructura para unirla. Los pilares toman forma y el altar queda listo.",
                    "Coloca más pedestales alrededor del altar y pon los ingredientes en ellos. El objeto a infundir va en el pedestal central.",
                    "Haz clic en la matriz rúnica con una herramienta de lanzamiento para empezar. La esencia se extrae de los recipientes cercanos y la inestabilidad aumenta mientras trabaja."));

    public static final PonderScript INFUSION_ANCIENT = new PonderScript("infusion_altar_ancient",
            "Infusion Altar: Ancient Stone", "Altar de infusión: piedra antigua",
            List.of(
                    "An Infusion Altar can also be built from Ancient Stone Tiles. The shape is the same: four pillars, a central pedestal and a Runic Matrix two blocks above it.",
                    "Sprinkle Salis Mundus to bind it. Ancient pillars lower the essentia cost, speed the infusion up a little and add a small amount of instability.",
                    "Ancient Pedestals placed around the altar lower the essentia cost as well. Put your ingredients on them as usual.",
                    "Start the infusion by clicking the Runic Matrix with a casting tool."),
            List.of(
                    "Un altar de infusión también se puede construir con baldosas de piedra antigua. La forma es la misma: cuatro pilares, un pedestal central y una matriz rúnica dos bloques por encima.",
                    "Espolvorea Salis Mundus para unirlo. Los pilares antiguos reducen el coste de esencia, aceleran un poco la infusión y añaden algo de inestabilidad.",
                    "Los pedestales antiguos colocados alrededor del altar también reducen el coste de esencia. Pon en ellos tus ingredientes como siempre.",
                    "Empieza la infusión haciendo clic en la matriz rúnica con una herramienta de lanzamiento."));

    public static final PonderScript INFUSION_ELDRITCH = new PonderScript("infusion_altar_eldritch",
            "Infusion Altar: Eldritch Stone", "Altar de infusión: piedra eldritch",
            List.of(
                    "An Infusion Altar can also be built from Eldritch Stone Tiles. The shape is the same: four pillars, a central pedestal and a Runic Matrix two blocks above it.",
                    "Sprinkle Salis Mundus to bind it. Eldritch pillars reduce instability and speed the infusion up, at the price of a slightly higher essentia cost.",
                    "Eldritch Pedestals have the same effect, but keep them balanced: unbalanced Eldritch Pedestals massively increase instability.",
                    "Start the infusion by clicking the Runic Matrix with a casting tool."),
            List.of(
                    "Un altar de infusión también se puede construir con baldosas de piedra eldritch. La forma es la misma: cuatro pilares, un pedestal central y una matriz rúnica dos bloques por encima.",
                    "Espolvorea Salis Mundus para unirlo. Los pilares eldritch reducen la inestabilidad y aceleran la infusión, a costa de un coste de esencia algo mayor.",
                    "Los pedestales eldritch tienen el mismo efecto, pero mantenlos equilibrados: si no lo están, la inestabilidad se dispara.",
                    "Empieza la infusión haciendo clic en la matriz rúnica con una herramienta de lanzamiento."));

    public static final PonderScript ADVANCED_FURNACE = new PonderScript("advanced_alchemical_furnace",
            "Advanced Alchemical Furnace", "Horno alquímico avanzado",
            List.of(
                    "The Advanced Alchemical Furnace has a 3x3 base: an Essentia Smeltery in the centre, surrounded by Advanced Alchemical Constructs.",
                    "On top go Arcane Alembics on the corners and Alchemical Constructs between them, leaving the middle open.",
                    "Sprinkle Salis Mundus over it and the whole structure fuses into a single machine.",
                    "It burns no fuel. It draws ignis, perditio and aqua from nearby vis relays or nodes to stay hot, and items dropped onto its core break down quickly while it does.",
                    "The essentia collects inside and tubes can draw it from the nozzles on each side. If the structure is broken, whatever it holds escapes as flux."),
            List.of(
                    "El horno alquímico avanzado tiene una base de 3x3: una fundición de esencia en el centro, rodeada de constructos alquímicos avanzados.",
                    "Encima van alambiques arcanos en las esquinas y constructos alquímicos entre ellos, dejando el medio libre.",
                    "Espolvorea Salis Mundus sobre él y toda la estructura se funde en una sola máquina.",
                    "No quema combustible. Toma ignis, perditio y aqua de relés de vis o nodos cercanos para mantenerse caliente, y los objetos que caen sobre su núcleo se descomponen rápido mientras lo está.",
                    "La esencia se acumula en su interior y se puede extraer con tubos desde las boquillas de cada lado. Si la estructura se rompe, todo lo que contiene escapa en forma de flux."));

    public static final PonderScript INFERNAL_FURNACE = new PonderScript("infernal_furnace",
            "Infernal Furnace", "Horno infernal",
            List.of(
                    "The Infernal Furnace is a 3x3x3 cube of Nether Bricks and Obsidian with a single block of lava in the middle.",
                    "A block of iron bars in front of the lava is the outlet. Sprinkle Salis Mundus over the structure to form the furnace.",
                    "Drop items in from the top. They are smelted without fuel and spat out the front, and items that cannot be smelted are destroyed. Now and then it yields bonus nuggets.",
                    "Place a chest or another container in front of it to collect the output. A steady supply of vis from the aura speeds it up, but it may occasionally create a little flux."),
            List.of(
                    "El horno infernal es un cubo de 3x3x3 de ladrillos del Nether y obsidiana con un único bloque de lava en el medio.",
                    "Un bloque de barrotes de hierro delante de la lava es la salida. Espolvorea Salis Mundus sobre la estructura para formar el horno.",
                    "Echa objetos desde arriba. Se funden sin combustible y salen por delante, y lo que no se puede fundir se destruye. De vez en cuando da pepitas extra.",
                    "Pon un cofre u otro contenedor delante para recoger lo que produce. Un suministro constante de vis del aura lo acelera, pero a veces crea un poco de flux."));

    public static final PonderScript GOLEM_PRESS = new PonderScript("golem_press",
            "Golem Press", "Prensa de gólems",
            List.of(
                    "The Golem Press is a small structure: a Cauldron, an Anvil, a Stone Table and a Piston facing up, with a block of iron bars on top of the piston.",
                    "Sprinkle Salis Mundus over it and it becomes a single press.",
                    "Supply it with Machina essentia through the ports below the press and use it to pick the components of your golem."),
            List.of(
                    "La prensa de gólems es una estructura pequeña: un caldero, un yunque, una mesa de piedra y un pistón mirando hacia arriba, con un bloque de barrotes de hierro sobre el pistón.",
                    "Espolvorea Salis Mundus sobre ella y se convierte en una sola prensa.",
                    "Suminístrale esencia Machina por los puertos de debajo de la prensa y úsala para elegir los componentes de tu gólem."));

    public static final PonderScript THAUMATORIUM = new PonderScript("thaumatorium",
            "Thaumatorium", "Taumatorio",
            List.of(
                    "The Thaumatorium stacks two Alchemical Constructs on top of a Crucible.",
                    "Sprinkle Salis Mundus over it and the constructs become the Thaumatorium. The crucible base needs no water, but it still needs a heat source.",
                    "Essentia is drawn in through tubes and the finished object is ejected out of the front.",
                    "A Mnemonic Matrix attached to its side lets it keep track of two more formulas at the same time."),
            List.of(
                    "El taumatorio apila dos constructos alquímicos sobre un crisol.",
                    "Espolvorea Salis Mundus sobre él y los constructos se convierten en el taumatorio. El crisol de la base no necesita agua, pero sí una fuente de calor.",
                    "La esencia entra por tubos y el objeto terminado sale por el frente.",
                    "Una matriz mnemotécnica pegada a su lado le permite recordar dos fórmulas más a la vez."));

    public static final PonderScript NODE_TOWER = new PonderScript("node_energizing",
            "Stabilizing and Energizing Nodes", "Estabilizar y energizar nodos",
            List.of(
                    "A Node Stabilizer placed directly beneath a node holds it still. The node feeds on the aura more slowly and no longer pulls from other nodes.",
                    "The Advanced Node Stabilizer is a stronger lock: the node barely feeds at all and nothing can pull from it, though it can still pull from unprotected nodes. A redstone signal switches either stabilizer off.",
                    "To energize a node, place a Node Transducer directly above it and power only the transducer with redstone.",
                    "Never let redstone reach the stabilizer. If it is removed or switched off beneath an energized node, the node tears itself apart in a violent explosion."),
            List.of(
                    "Un estabilizador de nodos colocado justo debajo de un nodo lo mantiene quieto. El nodo se alimenta del aura más despacio y deja de absorber de otros nodos.",
                    "El estabilizador de nodos avanzado es un cierre más fuerte: el nodo casi no se alimenta y nadie puede absorber de él, aunque sí puede absorber de nodos sin proteger. Una señal de redstone apaga cualquiera de los dos.",
                    "Para energizar un nodo, coloca un transductor de nodo justo encima y da redstone solo al transductor.",
                    "Nunca dejes que la redstone llegue al estabilizador. Si se retira o se apaga bajo un nodo energizado, el nodo se desgarra en una violenta explosión."));

    public static final PonderScript RESEARCH_TABLE = new PonderScript("research_table",
            "Research Table", "Mesa de investigación",
            List.of(
                    "The Research Table is made of two blocks placed side by side.",
                    "Bookshelves, a Brain in a Jar and other blocks around it can grant bonus aspects when you work at the table.",
                    "Use it with scribing tools and paper to combine aspects and complete research."),
            List.of(
                    "La mesa de investigación se compone de dos bloques colocados uno junto al otro.",
                    "Las estanterías, un cerebro en un frasco y otros bloques a su alrededor pueden darte aspectos extra cuando trabajas en la mesa.",
                    "Úsala con herramientas de escritura y papel para combinar aspectos y completar investigaciones."));

    public static final PonderScript WORKBENCH_CHARGER = new PonderScript("workbench_charger",
            "Workbench Charger", "Cargador de mesa de trabajo",
            List.of(
                    "A Workbench Charger placed on top of an Arcane Workbench lets it draw vis from the surrounding chunks too. The same rig works on the Focal Manipulator.",
                    "Vis is drawn from the chunk of the workbench and the eight around it, split between all nine so that no single chunk is depleted."),
            List.of(
                    "Un cargador de mesa de trabajo colocado sobre una mesa de trabajo arcana le permite tomar vis también de los chunks cercanos. El mismo aparato sirve para el manipulador focal.",
                    "El vis se toma del chunk de la mesa y de los ocho que lo rodean, repartido entre los nueve para que ningún chunk se agote."));

    public static final PonderScript TIERED_STABILIZERS = new PonderScript("tiered_node_stabilizers",
            "Avaritia Node Stabilizers", "Estabilizadores de nodos de Avaritia",
            List.of(
                    "The Crystal Matrix, Neutronium and Infinity Node Stabilizers work like the Advanced Node Stabilizer: place them directly beneath a node. The basic one slows feeding 2 times and the advanced one 20 times.",
                    "Crystal Matrix Node Stabilizer: the node feeds on the aura " + DeviceConfig.feedingFactor(DeviceTier.MATRIX) + " times slower (by default).",
                    "Neutronium Node Stabilizer: the node feeds on the aura " + DeviceConfig.feedingFactor(DeviceTier.NEUTRONIUM) + " times slower (by default).",
                    "Infinity Node Stabilizer: the node feeds on the aura " + DeviceConfig.feedingFactor(DeviceTier.INFINITY) + " times slower (by default)."),
            List.of(
                    "Los estabilizadores de nodos de matriz de cristal, neutronio e infinito funcionan como el estabilizador de nodos avanzado: colócalos justo debajo de un nodo. El básico frena la alimentación 2 veces y el avanzado 20 veces.",
                    "Estabilizador de nodos de matriz de cristal: el nodo se alimenta del aura " + DeviceConfig.feedingFactor(DeviceTier.MATRIX) + " veces más despacio (por defecto).",
                    "Estabilizador de nodos de neutronio: el nodo se alimenta del aura " + DeviceConfig.feedingFactor(DeviceTier.NEUTRONIUM) + " veces más despacio (por defecto).",
                    "Estabilizador de nodos de infinito: el nodo se alimenta del aura " + DeviceConfig.feedingFactor(DeviceTier.INFINITY) + " veces más despacio (por defecto)."));

    public static final PonderScript TIERED_TRANSDUCERS = new PonderScript("tiered_node_transducers",
            "Avaritia Node Transducers", "Transductores de nodos de Avaritia",
            List.of(
                    "The Crystal Matrix, Neutronium and Infinity Node Transducers go directly above a node held by a stabilizer, just like the Node Transducer, and are powered with redstone in the same way.",
                    "Crystal Matrix Node Transducer: charges the node " + DeviceConfig.chargePerTick(DeviceTier.MATRIX) + " times faster and loses its charge " + DeviceConfig.decayInterval(DeviceTier.MATRIX) + " times slower without redstone (by default).",
                    "Neutronium Node Transducer: charges the node " + DeviceConfig.chargePerTick(DeviceTier.NEUTRONIUM) + " times faster and loses its charge " + DeviceConfig.decayInterval(DeviceTier.NEUTRONIUM) + " times slower without redstone (by default).",
                    "Infinity Node Transducer: charges the node " + DeviceConfig.chargePerTick(DeviceTier.INFINITY) + " times faster and loses its charge " + DeviceConfig.decayInterval(DeviceTier.INFINITY) + " times slower without redstone (by default)."),
            List.of(
                    "Los transductores de nodos de matriz de cristal, neutronio e infinito van justo encima de un nodo sujeto por un estabilizador, igual que el transductor de nodo, y se alimentan con redstone de la misma forma.",
                    "Transductor de nodos de matriz de cristal: carga el nodo " + DeviceConfig.chargePerTick(DeviceTier.MATRIX) + " veces más rápido y pierde su carga " + DeviceConfig.decayInterval(DeviceTier.MATRIX) + " veces más despacio sin redstone (por defecto).",
                    "Transductor de nodos de neutronio: carga el nodo " + DeviceConfig.chargePerTick(DeviceTier.NEUTRONIUM) + " veces más rápido y pierde su carga " + DeviceConfig.decayInterval(DeviceTier.NEUTRONIUM) + " veces más despacio sin redstone (por defecto).",
                    "Transductor de nodos de infinito: carga el nodo " + DeviceConfig.chargePerTick(DeviceTier.INFINITY) + " veces más rápido y pierde su carga " + DeviceConfig.decayInterval(DeviceTier.INFINITY) + " veces más despacio sin redstone (por defecto)."));

    public static final PonderScript TIERED_FURNACES = new PonderScript("tiered_alchemical_furnaces",
            "Avaritia Alchemical Furnaces", "Hornos alquímicos de Avaritia",
            List.of(
                    "Every Avaritia alchemical furnace is built exactly like the Advanced Alchemical Furnace, but with the matching Avaritia Essentia Smeltery in the centre instead of the Essentia Smeltery.",
                    "Sprinkle Salis Mundus to form it. The Crystal Matrix Alchemical Furnace works " + DeviceConfig.furnaceSpeed(DeviceTier.MATRIX) + " times faster and gives back " + DeviceConfig.furnaceSavings(DeviceTier.MATRIX) + "%% of the heat, perditio and aqua spent on each item (by default).",
                    "Neutronium Alchemical Furnace: " + DeviceConfig.furnaceSpeed(DeviceTier.NEUTRONIUM) + " times faster and " + DeviceConfig.furnaceSavings(DeviceTier.NEUTRONIUM) + "%% of the heat, perditio and aqua given back (by default).",
                    "Infinity Alchemical Furnace: " + DeviceConfig.furnaceSpeed(DeviceTier.INFINITY) + " times faster and " + DeviceConfig.furnaceSavings(DeviceTier.INFINITY) + "%% of the heat, perditio and aqua given back (by default)."),
            List.of(
                    "Cada horno alquímico de Avaritia se construye igual que el horno alquímico avanzado, pero con la fundición de esencia de Avaritia correspondiente en el centro en lugar de la fundición de esencia.",
                    "Espolvorea Salis Mundus para formarlo. El horno alquímico de matriz de cristal trabaja " + DeviceConfig.furnaceSpeed(DeviceTier.MATRIX) + " veces más rápido y devuelve el " + DeviceConfig.furnaceSavings(DeviceTier.MATRIX) + "%% del calor, perditio y aqua gastados en cada objeto (por defecto).",
                    "Horno alquímico de neutronio: " + DeviceConfig.furnaceSpeed(DeviceTier.NEUTRONIUM) + " veces más rápido y devuelve el " + DeviceConfig.furnaceSavings(DeviceTier.NEUTRONIUM) + "%% del calor, perditio y aqua (por defecto).",
                    "Horno alquímico de infinito: " + DeviceConfig.furnaceSpeed(DeviceTier.INFINITY) + " veces más rápido y devuelve el " + DeviceConfig.furnaceSavings(DeviceTier.INFINITY) + "%% del calor, perditio y aqua (por defecto)."));

    public static final PonderScript TIERED_MATRICES = new PonderScript("tiered_mnemonic_matrices",
            "Avaritia Mnemonic Matrices", "Matrices mnemotécnicas de Avaritia",
            List.of(
                    "Avaritia Mnemonic Matrices attach to the sides of a Thaumatorium, facing it, just like the Mnemonic Matrix, but each one lets it keep track of more formulas at once. The Mnemonic Matrix gives 2.",
                    "Crystal Matrix Mnemonic Matrix: " + BrainChargerConfig.formulas(BoostTier.MATRIX) + " formulas each (by default).",
                    "Neutronium Mnemonic Matrix: " + BrainChargerConfig.formulas(BoostTier.NEUTRONIUM) + " formulas each (by default).",
                    "Infinity Mnemonic Matrix: " + BrainChargerConfig.formulas(BoostTier.INFINITY) + " formulas each (by default)."),
            List.of(
                    "Las matrices mnemotécnicas de Avaritia se pegan a los lados de un taumatorio, mirando hacia él, igual que la matriz mnemotécnica, pero cada una le permite recordar más fórmulas a la vez. La matriz mnemotécnica da 2.",
                    "Matriz mnemotécnica de matriz de cristal: " + BrainChargerConfig.formulas(BoostTier.MATRIX) + " fórmulas cada una (por defecto).",
                    "Matriz mnemotécnica de neutronio: " + BrainChargerConfig.formulas(BoostTier.NEUTRONIUM) + " fórmulas cada una (por defecto).",
                    "Matriz mnemotécnica del infinito: " + BrainChargerConfig.formulas(BoostTier.INFINITY) + " fórmulas cada una (por defecto)."));

    public static final PonderScript TIERED_CHARGERS = new PonderScript("tiered_workbench_chargers",
            "Avaritia Workbench Chargers", "Cargadores de mesa de Avaritia",
            List.of(
                    "Avaritia Workbench Chargers go on top of an Arcane Workbench like the Workbench Charger, but they reach farther and make arcane crafting cheaper. The Workbench Charger reaches 1 chunk around the workbench.",
                    "Crystal Matrix Workbench Charger: draws vis from " + BrainChargerConfig.radius(BoostTier.MATRIX) + " chunks around the workbench and charges " + BrainChargerConfig.discountPercent(BoostTier.MATRIX) + "%% less vis (by default).",
                    "Neutronium Workbench Charger: draws vis from " + BrainChargerConfig.radius(BoostTier.NEUTRONIUM) + " chunks around the workbench and charges " + BrainChargerConfig.discountPercent(BoostTier.NEUTRONIUM) + "%% less vis (by default).",
                    "Infinity Workbench Charger: draws vis from " + BrainChargerConfig.radius(BoostTier.INFINITY) + " chunks around the workbench and charges " + BrainChargerConfig.discountPercent(BoostTier.INFINITY) + "%% less vis (by default)."),
            List.of(
                    "Los cargadores de mesa de Avaritia van sobre una mesa de trabajo arcana como el cargador de mesa de trabajo, pero llegan más lejos y abaratan la artesanía arcana. El cargador de mesa de trabajo llega a 1 chunk alrededor de la mesa.",
                    "Cargador de mesa de matriz de cristal: toma vis de " + BrainChargerConfig.radius(BoostTier.MATRIX) + " chunks alrededor de la mesa y cobra un " + BrainChargerConfig.discountPercent(BoostTier.MATRIX) + "%% menos de vis (por defecto).",
                    "Cargador de mesa de neutronio: toma vis de " + BrainChargerConfig.radius(BoostTier.NEUTRONIUM) + " chunks alrededor de la mesa y cobra un " + BrainChargerConfig.discountPercent(BoostTier.NEUTRONIUM) + "%% menos de vis (por defecto).",
                    "Cargador de mesa del infinito: toma vis de " + BrainChargerConfig.radius(BoostTier.INFINITY) + " chunks alrededor de la mesa y cobra un " + BrainChargerConfig.discountPercent(BoostTier.INFINITY) + "%% menos de vis (por defecto)."));

    public static final List<PonderScript> ALL = List.of(
            INFUSION_ARCANE, INFUSION_ANCIENT, INFUSION_ELDRITCH, ADVANCED_FURNACE, INFERNAL_FURNACE, GOLEM_PRESS,
            THAUMATORIUM, NODE_TOWER, RESEARCH_TABLE, WORKBENCH_CHARGER, TIERED_STABILIZERS, TIERED_TRANSDUCERS,
            TIERED_FURNACES, TIERED_MATRICES, TIERED_CHARGERS);

    private PonderScripts() {
    }
}
