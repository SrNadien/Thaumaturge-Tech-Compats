package nadiendev.ThaumaturgeTechCompats.jei.info;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.taintedfood.TaintedFoodType;
import net.minecraft.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class JeiInfoTexts {
    public static final String TOGGLE = "jei_info";
    public static final String KEY_PREFIX = "jei." + ThaumaturgeTechCompats.MOD_ID + ".info.";

    public record Text(String english, String spanish) {
    }

    private record Name(String en, String es) {
    }

    private static final Name THAUMIUM = new Name("Thaumium", "taumio");
    private static final Name ALCHEMICAL_BRASS = new Name("Alchemical Brass", "latón alquímico");
    private static final Name VOID_METAL = new Name("Void Metal", "metal del vacío");
    private static final Name BRASS = new Name("Brass", "latón");

    private static final Name MATRIX = new Name("Crystal Matrix", "Matriz de Cristal");
    private static final Name NEUTRONIUM = new Name("Neutronium", "Neutronio");
    private static final Name INFINITY = new Name("Infinity", "Infinito");

    private static final Map<String, Text> EXACT = new HashMap<>();
    private static final Map<String, Identifier> TAINTED = new LinkedHashMap<>();

    private static final Text GENERIC_BLOCK = new Text(
            "A block added by Thaumaturge Tech Compats. Its recipe and what it does are written in the research of the Thaumonomicon.",
            "Un bloque añadido por Thaumaturge Tech Compats. Su receta y lo que hace están escritos en la investigación del Thaumonomicon.");
    private static final Text GENERIC_ITEM = new Text(
            "An item added by Thaumaturge Tech Compats. Its recipe and what it does are written in the research of the Thaumonomicon.",
            "Un objeto añadido por Thaumaturge Tech Compats. Su receta y lo que hace están escritos en la investigación del Thaumonomicon.");

    static {
        for (TaintedFoodType type : TaintedFoodType.values()) {
            TAINTED.put(type.id(), type.original());
        }
        exact();
    }

    private JeiInfoTexts() {
    }

    public static String key(String path) {
        return KEY_PREFIX + path;
    }

    public static Map<String, Identifier> taintedFoods() {
        return TAINTED;
    }

    public static Component[] components(String path, boolean block) {
        Identifier original = TAINTED.get(path);
        if (original != null) {
            return new Component[]{Component.translatable(key(path), Component.translatable(Util.makeDescriptionId("item", original)))};
        }
        return new Component[]{Component.translatable(key(path))};
    }

    public static Text text(String path, boolean block) {
        Text exact = EXACT.get(path);
        if (exact != null) {
            return exact;
        }
        Text family = family(path);
        if (family != null) {
            return family;
        }
        return block ? GENERIC_BLOCK : GENERIC_ITEM;
    }

    private static Text t(String en, String es) {
        return new Text(en, contract(es));
    }

    private static String contract(String spanish) {
        return spanish.replace(" de el ", " del ").replace(" a el ", " al ");
    }

    private static Text family(String path) {
        if (path.startsWith("tainted_")) {
            return tainted();
        }
        if (path.startsWith("oritech_")) {
            return oritech(path.substring("oritech_".length()));
        }
        if (path.startsWith("resource_node_")) {
            return resourceNode(path.substring("resource_node_".length()));
        }
        if (path.startsWith("energized_")) {
            return energized(path.substring("energized_".length()));
        }
        if (path.endsWith("_cluster")) {
            return cluster(path.substring(0, path.length() - "_cluster".length()));
        }
        Name avaritia = avaritiaName(path);
        if (avaritia != null) {
            String rest = path.substring(avaritiaPrefix(path).length());
            Text text = avaritia(rest, avaritia);
            if (text != null) {
                return text;
            }
        }
        String prefix = metalPrefix(path);
        if (prefix != null) {
            Name metal = metalName(prefix);
            String rest = path.substring(prefix.length());
            Text text = metal(rest, metal);
            if (text != null) {
                return text;
            }
        }
        return null;
    }

    private static String avaritiaPrefix(String path) {
        if (path.startsWith("matrix_")) {
            return "matrix_";
        }
        if (path.startsWith("neutronium_")) {
            return "neutronium_";
        }
        if (path.startsWith("infinity_")) {
            return "infinity_";
        }
        return null;
    }

    private static Name avaritiaName(String path) {
        String prefix = avaritiaPrefix(path);
        if (prefix == null) {
            return null;
        }
        return switch (prefix) {
            case "matrix_" -> MATRIX;
            case "neutronium_" -> NEUTRONIUM;
            default -> INFINITY;
        };
    }

    private static String metalPrefix(String path) {
        for (String prefix : new String[]{"alchemical_brass_", "thaumium_", "void_metal_", "void_", "brass_"}) {
            if (path.startsWith(prefix)) {
                return prefix;
            }
        }
        return null;
    }

    private static Name metalName(String prefix) {
        return switch (prefix) {
            case "alchemical_brass_" -> ALCHEMICAL_BRASS;
            case "thaumium_" -> THAUMIUM;
            case "brass_" -> BRASS;
            default -> VOID_METAL;
        };
    }

    private static int rank(Name name) {
        if (name == MATRIX) {
            return 0;
        }
        if (name == NEUTRONIUM) {
            return 1;
        }
        return 2;
    }

    private static boolean last(Name name) {
        return name == INFINITY;
    }

    private static Name previous(Name name) {
        if (name == NEUTRONIUM) {
            return MATRIX;
        }
        if (name == INFINITY) {
            return NEUTRONIUM;
        }
        return null;
    }

    private static String ingotEn(Name a) {
        return switch (rank(a)) {
            case 0 -> "crystal matrix";
            case 1 -> "neutron";
            default -> "infinity";
        };
    }

    private static String ingotEs(Name a) {
        return switch (rank(a)) {
            case 0 -> "matriz de cristal";
            case 1 -> "neutrones";
            default -> "infinito";
        };
    }

    private static String pearlEn(Name a) {
        return last(a) ? " and an Extremely Primordial Pearl" : "";
    }

    private static String pearlEs(Name a) {
        return last(a) ? " y una Perla Extremadamente Primordial" : "";
    }

    private static Text avaritia(String rest, Name a) {
        Name before = previous(a);
        String ingotEn = ingotEn(a);
        String ingotEs = ingotEs(a);
        String pearlEn = pearlEn(a);
        String pearlEs = pearlEs(a);
        String beforeEn = before == null ? "" : before.en();
        String beforeEs = before == null ? "" : before.es();
        return switch (rest) {
            case "jar" -> t(
                    "A Warded Jar of " + a.en() + " that stores far more essentia than the jar before it. It is infused on an infusion altar from "
                            + (before == null ? "an ordinary Warded Jar" : "the " + beforeEn + " Jar")
                            + ", with four " + ingotEn + " ingots" + pearlEn + ".\n\nIt keeps its essentia and its aspect filter when it is broken and picked up. Its capacity is set in the configuration.",
                    "Una jarra de " + a.es() + " que guarda mucha más esencia que la anterior. Se infunde en un altar de infusión a partir de "
                            + (before == null ? "una jarra normal" : "la Jarra de " + beforeEs)
                            + ", con cuatro lingotes de " + ingotEs + pearlEs + ".\n\nConserva su esencia y su filtro de aspecto al romperla y recogerla. La capacidad se ajusta en la configuración.");
            case "flux_jar" -> t(
                    "A Flux Jar of " + a.en() + ": it holds more flux and draws it from the aura faster than the Flux Jar before it. Machines that work on magical power can use that flux as their power source.\n\nIt is infused on an infusion altar from "
                            + (before == null ? "a Flux Jar" : "the " + beforeEn + " Flux Jar") + ", with four " + ingotEn + " ingots" + pearlEn
                            + ". Breaking it pours everything it holds back into the aura as flux.",
                    "Una Jarra de flux de " + a.es() + ": guarda más flujo y lo aspira del aura más rápido que la Jarra de flux anterior. Las máquinas que funcionan con poder mágico pueden usar ese flujo como fuente.\n\nSe infunde en un altar de infusión a partir de "
                            + (before == null ? "una Jarra de flux" : "la Jarra de flux de " + beforeEs) + ", con cuatro lingotes de " + ingotEs + pearlEs
                            + ". Al romperla, todo lo que guarda vuelve al aura como flujo.");
            case "infusion_matrix" -> t(
                    "A Runic Matrix of " + a.en() + ", the heart of an infusion altar. It never asks for a symmetric altar and its stability does not recover; what goes wrong is decided by the instability of the craft being infused. "
                            + matrixEn(a) + "\n\nIt is infused on an altar from " + (before == null ? "a Runic Matrix" : "the " + beforeEn + " one") + ", with " + ingotEn + " ingots" + pearlEn
                            + ", and it can only be infused on an altar whose center is " + (before == null ? "a plain Runic Matrix" : "the tier before it") + ". How strong its effects are is set in the configuration.",
                    "Una Matriz rúnica de " + a.es() + ", el corazón de un altar de infusión. Nunca pide un altar simétrico y su estabilidad no se recupera; lo que sale mal lo decide la inestabilidad del crafteo que se infunde. "
                            + matrixEs(a) + "\n\nSe infunde en un altar a partir de " + (before == null ? "una Matriz rúnica" : "la de " + beforeEs) + ", con lingotes de " + ingotEs + pearlEs
                            + ", y solo se puede infundir en un altar cuyo centro sea " + (before == null ? "una Matriz rúnica normal" : "la del nivel anterior") + ". La fuerza de sus efectos se ajusta en la configuración.");
            case "infusion_cost_stone" -> stone(a, true);
            case "infusion_speed_stone" -> stone(a, false);
            case "pillar_stone" -> t(
                    "An Infusion Pillar Stone of " + a.en() + ". Eight of them make the pillars of an infusion altar, and each tier lowers the cost and the cycle time of the altar more than the one before.\n\nIt is crafted at the arcane workbench, four at a time, from "
                            + ingotEn + " ingots and " + (before == null ? "Eldritch Stone Tiles" : "the " + beforeEn + " stones") + (last(a) ? ", with an Extremely Primordial Pearl" : "") + ". The altar never costs less than half; the values are set in the configuration.",
                    "Una Piedra de pilar de infusión de " + a.es() + ". Ocho forman los pilares de un altar de infusión, y cada nivel baja más el coste y el tiempo de ciclo del altar que el anterior.\n\nSe fabrica en la mesa de trabajo arcana, de cuatro en cuatro, con lingotes de "
                            + ingotEs + " y " + (before == null ? "Baldosas de piedra de Eldritch" : "las piedras de " + beforeEs) + (last(a) ? ", y una Perla Extremadamente Primordial" : "") + ". El altar nunca cuesta menos de la mitad; los valores se ajustan en la configuración.");
            case "infusion_pillar" -> t(
                    "An Infusion Pillar of " + a.en() + ", raised by sprinkling Salis Mundus over its stones. The altar is built exactly like the plain one: eight stones, a pedestal and a Runic Matrix of any kind. The look of the pillar follows the stone used.\n\nEach tier lowers the cost and the cycle time of the altar more than the one before, and the pillars also add stability for the plain Runic Matrix.",
                    "Un Pilar de infusión de " + a.es() + ", que se levanta esparciendo Salis Mundus sobre sus piedras. El altar se construye igual que el normal: ocho piedras, un pedestal y una Matriz rúnica de cualquier tipo. El aspecto del pilar sigue a la piedra usada.\n\nCada nivel baja más el coste y el tiempo de ciclo del altar que el anterior, y los pilares también dan estabilidad a la Matriz rúnica normal.");
            case "infusion_pedestal" -> t(
                    "A Pedestal of " + a.en() + " for the infusion altar, where the ingredients of a craft are placed. Better pedestals lower the cost and the cycle time of the altar.\n\nIt is infused on an infusion altar from "
                            + (before == null ? "the Eldritch Pedestal" : "the " + beforeEn + " pedestal") + ", with " + ingotEn + " ingots" + pearlEn + ".",
                    "Un Pedestal de " + a.es() + " para el altar de infusión, donde se colocan los ingredientes de un crafteo. Los mejores pedestales bajan el coste y el tiempo de ciclo del altar.\n\nSe infunde en un altar de infusión a partir de "
                            + (before == null ? "el Pedestal de Eldritch" : "el pedestal de " + beforeEs) + ", con lingotes de " + ingotEs + pearlEs + ".");
            case "brain_box" -> t(
                    "A Mnemonic Matrix of " + a.en() + ". Placed against a thaumatorium, it lets it remember more formulas at the same time without losing any, and it stacks with the ordinary ones.\n\nIt is infused on an altar from "
                            + (before == null ? "an ordinary Mnemonic Matrix" : "the " + beforeEn + " one") + ". How many formulas it allows is set in the configuration.",
                    "Una Matriz Mnemotécnica de " + a.es() + ". Colocada junto a un taumatorio, le deja recordar más fórmulas a la vez sin perder ninguna, y se acumula con las normales.\n\nSe infunde en un altar a partir de "
                            + (before == null ? "una Matriz Mnemotécnica normal" : "la de " + beforeEs) + ". Cuántas fórmulas permite se ajusta en la configuración.");
            case "workbench_charger" -> t(
                    "A Workbench Charger of " + a.en() + ". It sits on top of an arcane workbench and pulls vis from the aura of the chunks around it, reaching farther and charging less of the vis of every craft than the charger before it.\n\nIt is infused on an altar from "
                            + (before == null ? "an ordinary workbench charger" : "the " + beforeEn + " one") + ". Range and charge are set in the configuration.",
                    "Un Cargador de Mesa de " + a.es() + ". Se coloca sobre una mesa de trabajo arcana y saca vis del aura de los chunks de alrededor, llegando más lejos y cobrando menos vis por cada crafteo que el cargador anterior.\n\nSe infunde en un altar a partir de "
                            + (before == null ? "un Cargador de Mesa normal" : "el de " + beforeEs) + ". El alcance y la carga se ajustan en la configuración.");
            case "smelter" -> t(
                    "An Essentia Smeltery of " + a.en() + ", more efficient and quicker to hand over slurry than the smeltery before it; at Infinity no essentia is lost at all. It works like the other smelteries, with the same alembics, slurry pumps, venting ports and bellows.\n\nIt is built on "
                            + (before == null ? "the Void Metal Essentia Smeltery" : "the " + beforeEn + " smeltery") + ", with five plates and two ingots of its own material and an advanced alchemical construct.",
                    "Una Fundición de esencia de " + a.es() + ", más eficiente y más rápida entregando lodo que la fundición anterior; en la de Infinito no se pierde nada de esencia. Funciona como las demás fundiciones, con los mismos alambiques, bombas de lodo, salidas de ventilación y fuelles.\n\nSe construye sobre "
                            + (before == null ? "la Fundición de esencia de metal del vacío" : "la fundición de " + beforeEs) + ", con cinco placas y dos lingotes de su propio material y un constructo alquímico avanzado.");
            case "alchemical_furnace" -> t(
                    "An Alchemical Furnace of " + a.en() + ". It has the structure of the Advanced Alchemical Furnace, but its heart is the matching Avaritia smeltery, so it waits less between items and gives back part of the heat, perditio and aqua it burns.\n\nIt is brought to life with Salis Mundus over the "
                            + a.en() + " smeltery, which needs the one before it. If a part is broken it turns back into its smeltery.",
                    "Un Horno alquímico de " + a.es() + ". Tiene la estructura del Horno alquímico avanzado, pero su corazón es la fundición de Avaritia correspondiente, así que espera menos entre objetos y devuelve parte del calor, perditio y aqua que quema.\n\nCobra vida con Salis Mundus sobre la fundición de "
                            + a.es() + ", que necesita la anterior. Si se rompe una pieza, vuelve a ser su fundición.");
            case "node_stabilizer" -> t(
                    "A Node Stabilizer of " + a.en() + ". Like the Advanced Node Stabilizer, nothing can pull from the node it holds, it can still pull from unprotected nodes nearby and a redstone signal switches it off. Its grip is stronger than the tier before: the node feeds on the aura far more slowly and unstable or fading nodes settle faster.\n\nIt is infused around "
                            + (before == null ? "an Advanced Node Stabilizer" : "the " + beforeEn + " one") + ", with plates and ingots of its own material alternating with nitor and alumentum.",
                    "Un Estabilizador de nodos de " + a.es() + ". Igual que el Estabilizador de nodos avanzado, nada puede sacar del nodo que sujeta, él sí puede sacar de nodos cercanos sin proteger y una señal de redstone lo apaga. Su agarre es más fuerte que el del nivel anterior: el nodo se alimenta del aura mucho más despacio y los nodos inestables o que se apagan se asientan antes.\n\nSe infunde alrededor de "
                            + (before == null ? "un Estabilizador de nodos avanzado" : "el de " + beforeEs) + ", con placas y lingotes de su propio material alternados con nitor y alumentum.");
            case "node_transducer" -> t(
                    "A Node Transducer of " + a.en() + ". Placed above a stabilized node and powered with redstone, it energizes the node like the Node Transducer, but it charges it many times faster and its charge fades more slowly, for the same single point of aspect per tick.\n\nIt is built around "
                            + (before == null ? "the previous Node Transducer" : "the " + beforeEn + " one") + ", with four plates and two ingots of its own material, a comparator and a nitor.",
                    "Un Transductor de nodos de " + a.es() + ". Colocado sobre un nodo estabilizado y alimentado con redstone, activa el nodo como el Transductor de nodos, pero lo carga muchas veces más rápido y su carga se desvanece más despacio, por el mismo único punto de aspecto por tick.\n\nSe construye alrededor de "
                            + (before == null ? "el Transductor de nodos anterior" : "el de " + beforeEs) + ", con cuatro placas y dos lingotes de su propio material, un comparador y un nitor.");
            case "plate" -> t(
                    "A plate of " + a.en() + ", pressed from its ingots: three ingots in a crafting grid make three plates"
                            + (before == null ? "" : ", and it also needs a " + beforeEn + " Plate")
                            + ". It is the base of a golem of that material in the Golem Press, together with a Complex Arcane Mechanism, and an ingredient of wand caps, wand rods and improved foci.",
                    "Una placa de " + a.es() + ", prensada con sus lingotes: tres lingotes en una cuadrícula de crafteo dan tres placas"
                            + (before == null ? "" : ", y además necesita una Placa de " + beforeEs)
                            + ". Es la base de un gólem de ese material en la Prensa de gólems, junto a un Mecanismo arcano complejo, y un ingrediente de pomos y varas de varita y de focos mejorados.");
            case "scribing_tools" -> t(
                    "Scribing Tools of " + a.en() + ": a quill and an inkwell that hold far more ink than ordinary ones and work on a research table in the same way."
                            + (last(a) ? " They restore their ink on their own while they are carried." : " They can be refilled with black dye.")
                            + "\n\nThey are infused on an altar from " + (before == null ? "ordinary scribing tools" : "the " + beforeEn + " tools") + ", with " + ingotEn + " ingots" + pearlEn + ".",
                    "Herramientas de escritura de " + a.es() + ": una pluma y un tintero que guardan mucha más tinta que los normales y funcionan en la mesa de investigación igual."
                            + (last(a) ? " Recuperan su tinta solas mientras se llevan encima." : " Se pueden rellenar con tinte negro.")
                            + "\n\nSe infunden en un altar a partir de " + (before == null ? "herramientas de escritura normales" : "las de " + beforeEs) + ", con lingotes de " + ingotEs + pearlEs + ".");
            case "wand_rod" -> wandRod(a);
            case "wand_cap" -> wandCap(a);
            case "focus" -> focus(a);
            default -> null;
        };
    }

    private static String matrixEn(Name a) {
        return switch (rank(a)) {
            case 0 -> "It suffers solar instability: it burns, sets fires and calls lightning, and every craft is more unstable by day.";
            case 1 -> "It suffers nuclear instability: it gives off radiation, pours flux and can melt down in an explosion.";
            default -> "It counts every unit of essentia it receives as many more, and it suffers cosmic instability, the most dangerous of all: it tears open flux, gives warp, summons creatures from beyond and throws everything near it somewhere else.";
        };
    }

    private static String matrixEs(Name a) {
        return switch (rank(a)) {
            case 0 -> "Sufre inestabilidad solar: quema, prende fuegos y llama a los rayos, y cada crafteo es más inestable de día.";
            case 1 -> "Sufre inestabilidad nuclear: emite radiación, derrama flujo y puede fundirse en una explosión.";
            default -> "Cuenta cada unidad de esencia que recibe como muchas más, y sufre inestabilidad cósmica, la más peligrosa de todas: abre desgarros de flujo, da disformidad, invoca criaturas del más allá y lanza todo lo que tiene cerca a otro lugar.";
        };
    }

    private static Text stone(Name a, boolean cost) {
        Name before = previous(a);
        String ingotEn = ingotEn(a);
        String ingotEs = ingotEs(a);
        String plainEn = cost ? "Infusion Cost Stone" : "Infusion Speed Stone";
        String plainEs = cost ? "Runa de Descuento de Infusión" : "Runa de Velocidad de Infusión";
        String effectEn = cost ? "lowers the cost of the altar" : "shortens the cycle time of the altar";
        String effectEs = cost ? "baja el coste del altar" : "acorta el tiempo de ciclo del altar";
        return t(
                "An Infusion " + (cost ? "Cost" : "Speed") + " Stone of " + a.en() + ". It goes under the four pillars of an infusion altar like the plain stone, and each tier " + effectEn + " more than the one before. The altar never costs less than half.\n\nIt is infused on an altar from "
                        + (before == null ? "the " + plainEn : "the " + before.en() + " stone of the same kind") + ", with four " + ingotEn + " ingots" + pearlEn(a)
                        + ", on an altar whose Runic Matrix is " + (before == null ? "a plain one" : "the tier before it") + ". The values are set in the configuration.",
                "Una " + plainEs + " de " + a.es() + ". Va bajo los cuatro pilares de un altar de infusión como la runa normal, y cada nivel " + effectEs + " más que el anterior. El altar nunca cuesta menos de la mitad.\n\nSe infunde en un altar a partir de "
                        + (before == null ? "la " + plainEs : "la runa de " + before.es() + " del mismo tipo") + ", con cuatro lingotes de " + ingotEs + pearlEs(a)
                        + ", en un altar cuya Matriz rúnica sea " + (before == null ? "una normal" : "la del nivel anterior") + ". Los valores se ajustan en la configuración.");
    }

    private static Text wandRod(Name a) {
        return switch (rank(a)) {
            case 0 -> t(
                    "The Crystal Matrix Rod, a wand rod that channels vis without losing any at all: a wand built with it and a cap costs nothing to cast. Any rod fits any cap.\n\nIt is crafted from three crystal matrix ingots at an arcane workbench.",
                    "La Vara de Matriz de Cristal, una vara de varita que canaliza el vis sin perder nada: una varita hecha con ella y un pomo no cuesta nada al lanzar hechizos. Cualquier vara encaja con cualquier pomo.\n\nSe fabrica con tres lingotes de matriz de cristal en una mesa de trabajo arcana.");
            case 1 -> t(
                    "The Neutronium Rod, a wand rod that holds far more vis than the Crystal Matrix one and wastes far less of it. Any rod fits any cap.\n\nIt is infused on an altar with two neutron ingots and two neutronium plates, using a Crystal Matrix Rod as the catalyst.",
                    "La Vara de Neutronio, una vara de varita que guarda mucho más vis que la de Matriz de Cristal y desperdicia mucho menos. Cualquier vara encaja con cualquier pomo.\n\nSe infunde en un altar con dos lingotes de neutrones y dos placas de neutronio, usando una Vara de Matriz de Cristal como catalizador.");
            default -> null;
        };
    }

    private static Text wandCap(Name a) {
        return switch (rank(a)) {
            case 0 -> t(
                    "The Crystal Matrix Cap, a wand cap that makes the spells of a wand cost nothing. Any rod fits any cap.\n\nIt is crafted at an arcane workbench from three crystal matrix ingots and two crystal matrix plates.",
                    "El Pomo de Matriz de Cristal, un pomo de varita que hace que los hechizos no cuesten nada. Cualquier vara encaja con cualquier pomo.\n\nSe fabrica en una mesa de trabajo arcana con tres lingotes de matriz de cristal y dos placas de matriz de cristal.");
            case 1 -> t(
                    "The Neutronium Cap, a wand cap that cuts the cost of every spell far more than ordinary caps. Any rod fits any cap.\n\nIt is infused on an altar with two neutron ingots and two neutronium plates, using a Crystal Matrix Cap as the catalyst.",
                    "El Pomo de Neutronio, un pomo de varita que recorta el coste de cada hechizo mucho más que los pomos normales. Cualquier vara encaja con cualquier pomo.\n\nSe infunde en un altar con dos lingotes de neutrones y dos placas de neutronio, usando un Pomo de Matriz de Cristal como catalizador.");
            default -> t(
                    "The Infinity Cap, the grandest wand cap: it makes the spells of a wand free. With an Infinity rod, the wand also refills every primal aspect on its own.\n\nIt is infused on an altar on top of a Neutronium Cap, with four infinity ingots and two infinity plates.",
                    "El Pomo de Infinito, el pomo de varita más grandioso: hace que los hechizos sean gratis. Con una vara de Infinito, la varita además rellena sola todos los aspectos primarios.\n\nSe infunde en un altar sobre un Pomo de Neutronio, con cuatro lingotes de infinito y dos placas de infinito.");
        };
    }

    private static Text focus(Name a) {
        if (rank(a) == 0) {
            return t(
                    "A Blank Crystal Matrix Focus, an improved focus that holds a bigger spell than any focus before it.\n\nIt is infused on an altar on top of a Greater Focus, with two crystal matrix ingots and two crystal matrix plates.",
                    "Un Foco de matriz de cristal vacío, un foco mejorado que guarda un hechizo más grande que cualquier foco anterior.\n\nSe infunde en un altar sobre un Foco mayor, con dos lingotes de matriz de cristal y dos placas de matriz de cristal.");
        }
        return t(
                "A Blank Infinity Focus, the most capable focus there is.\n\nIt is infused on an altar on top of a Crystal Matrix Focus, with two infinity ingots, two infinity plates and two neutronium plates.",
                "Un Foco del infinito vacío, el foco más capaz que existe.\n\nSe infunde en un altar sobre un Foco de matriz de cristal, con dos lingotes de infinito, dos placas de infinito y dos placas de neutronio.");
    }

    private static int metalRank(Name metal) {
        if (metal == THAUMIUM || metal == BRASS) {
            return 0;
        }
        if (metal == ALCHEMICAL_BRASS) {
            return 1;
        }
        return 2;
    }

    private static Text metal(String rest, Name m) {
        return switch (rest) {
            case "aura_converter" -> auraConverter(m);
            case "casing" -> casing(m);
            case "mechanical_press" -> variant(m, "Mechanical Press", "Prensa mecánica");
            case "mechanical_mixer" -> variant(m, "Mechanical Mixer", "Mezcladora mecánica");
            case "mechanical_saw" -> variant(m, "Mechanical Saw", "Sierra mecánica");
            case "mechanical_drill" -> variant(m, "Mechanical Drill", "Taladro mecánico");
            case "deployer" -> variant(m, "Deployer", "Desplegador");
            case "millstone" -> variant(m, "Millstone", "Muela");
            case "basin" -> variant(m, "Basin", "Cuenca");
            case "spout" -> variant(m, "Spout", "Surtidor");
            case "item_drain" -> variant(m, "Item Drain", "Escurridor");
            case "depot" -> variant(m, "Depot", "Depósito");
            case "neutron_collector" -> neutron(m, true);
            case "neutron_compressor" -> neutron(m, false);
            case "miner" -> miner(m);
            case "miner_frame" -> frame(m, false);
            case "solar_panel" -> solar(m);
            case "solar_frame" -> frame(m, true);
            case "speed_upgrade" -> upgrade(m, "the mining speed", "la velocidad de minado", false, false);
            case "production_upgrade" -> upgrade(m, "the output", "la producción", false, false);
            case "efficiency_upgrade" -> upgrade(m, "the aura cost", "el coste de aura", false, true);
            case "solar_generation_upgrade" -> upgrade(m, "the energy generation", "la generación de energía", true, false);
            case "solar_saving_upgrade" -> upgrade(m, "the aura cost", "el coste de aura", true, true);
            case "spreader" -> spreader(m);
            case "unearther" -> unearther(m);
            case "knife" -> knife(m);
            case "brush" -> brush(m);
            case "grinding_ball" -> grindingBall(m);
            case "speed_addon" -> foregoing(m, "speed", "velocidad", "makes the machine work faster", "hace que la máquina trabaje más rápido");
            case "efficiency_addon" -> foregoing(m, "efficiency", "eficiencia", "cuts the processing time of the machine", "recorta el tiempo de procesado de la máquina");
            case "processing_addon" -> foregoing(m, "processing", "procesamiento", "multiplies what the machine processes at once", "multiplica lo que la máquina procesa a la vez");
            case "range_addon" -> foregoing(m, "range", "rango", "widens the working range of the machine", "amplía el rango de trabajo de la máquina");
            default -> null;
        };
    }

    private static Text auraConverter(Name m) {
        return switch (metalRank(m)) {
            case 0 -> t(
                    "The Thaumium Aura Converter drains vis from the aura of its chunk and turns it into FE, which it gives to every neighbouring block that accepts it. It never takes energy in.\n\nIt is crafted at the arcane workbench from thaumium plates, copper, redstone, a vis resonator and a simple mechanism. Right-click it to see its stored energy, the aura of the chunk and its state. Every vis it converts leaves a little flux behind, all the converters of a chunk share a drain limit and a redstone signal stops it. Rates and buffer are set in the configuration.",
                    "El Convertidor de aura de taumio saca vis del aura de su chunk y lo convierte en FE, que entrega a todos los bloques vecinos que lo acepten. Nunca recibe energía.\n\nSe fabrica en la mesa de trabajo arcana con placas de taumio, cobre, redstone, un resonador de vis y un mecanismo simple. Pulsa clic derecho para ver su energía guardada, el aura del chunk y su estado. Cada vis que convierte deja un poco de flujo, todos los convertidores de un chunk comparten un límite de drenaje y una señal de redstone lo detiene. Los ritmos y el búfer se ajustan en la configuración.");
            case 1 -> t(
                    "The Alchemical Brass Aura Converter turns the vis of the aura of its chunk into FE like the thaumium one, but it keeps far more in store and hands it out in a steadier, bigger stream.\n\nIt is infused on an altar over a Thaumium Aura Converter, with alchemical brass plates, a simple mechanism and a vis resonator. Rates and buffer are set in the configuration.",
                    "El Convertidor de aura de latón alquímico convierte el vis del aura de su chunk en FE como el de taumio, pero guarda mucho más y lo entrega en un chorro más constante y mayor.\n\nSe infunde en un altar sobre un Convertidor de aura de taumio, con placas de latón alquímico, un mecanismo simple y un resonador de vis. Los ritmos y el búfer se ajustan en la configuración.");
            default -> t(
                    "The Void Metal Aura Converter is the fastest of the three: it drinks the aura faster than any other and keeps almost all of it as current, as long as there is vis to drink. The drain limit of the chunk keeps it from emptying the aura on its own.\n\nIt is infused on an altar over an Alchemical Brass Aura Converter, with void metal plates, a complex mechanism and two vis resonators. Rates and buffer are set in the configuration.",
                    "El Convertidor de aura de metal del vacío es el más rápido de los tres: bebe el aura más rápido que ninguno y se queda casi todo como corriente, mientras haya vis que beber. El límite de drenaje del chunk evita que vacíe el aura él solo.\n\nSe infunde en un altar sobre un Convertidor de aura de latón alquímico, con placas de metal del vacío, un mecanismo complejo y dos resonadores de vis. Los ritmos y el búfer se ajustan en la configuración.");
        };
    }

    private static Text casing(Name m) {
        return switch (metalRank(m) == 1 ? 0 : metalRank(m) == 0 ? 1 : 2) {
            case 0 -> t(
                    "The Alchemical Brass Casing hums beside an aura node. Built into the machines of Create it makes variants that work many times faster while a node is near.\n\nIt is crafted at the arcane workbench from a Brass Casing of Create and four brass plates.",
                    "La Carcasa de latón alquímico zumba junto a un nodo de aura. Integrada en las máquinas de Create da variantes que trabajan muchas veces más rápido mientras hay un nodo cerca.\n\nSe fabrica en la mesa de trabajo arcana con una Carcasa de latón de Create y cuatro placas de latón.");
            case 1 -> t(
                    "The Thaumium Casing is the next step of the Create machinery: its variants reach nodes from farther away than the alchemical brass ones.\n\nIt is infused on an altar over an Alchemical Brass Casing, with four thaumium plates and a simple mechanism.",
                    "La Carcasa de taumio es el siguiente paso de la maquinaria de Create: sus variantes alcanzan nodos desde más lejos que las de latón alquímico.\n\nSe infunde en un altar sobre una Carcasa de latón alquímico, con cuatro placas de taumio y un mecanismo simple.");
            default -> t(
                    "The Void Metal Casing is the finest of the Create machinery: its variants answer a node from the greatest distance.\n\nIt is infused on an altar over a Thaumium Casing, with four void metal plates and a complex mechanism.",
                    "La Carcasa de metal del vacío es la mejor de la maquinaria de Create: sus variantes responden a un nodo desde la mayor distancia.\n\nSe infunde en un altar sobre una Carcasa de taumio, con cuatro placas de metal del vacío y un mecanismo complejo.");
        };
    }

    private static Text variant(Name m, String kindEn, String kindEs) {
        int order = metalRank(m) == 1 ? 0 : metalRank(m) == 0 ? 1 : 2;
        String fromEn = switch (order) {
            case 0 -> "the matching machine of Create, with an Alchemical Brass Casing and two brass plates";
            case 1 -> "the Alchemical Brass variant of the same machine, with a Thaumium Casing, two thaumium plates and a simple mechanism";
            default -> "the Thaumium variant of the same machine, with a Void Metal Casing, two void metal plates and a complex mechanism";
        };
        String fromEs = switch (order) {
            case 0 -> "la máquina de Create correspondiente, con una Carcasa de latón alquímico y dos placas de latón";
            case 1 -> "la variante de latón alquímico de la misma máquina, con una Carcasa de taumio, dos placas de taumio y un mecanismo simple";
            default -> "la variante de taumio de la misma máquina, con una Carcasa de metal del vacío, dos placas de metal del vacío y un mecanismo complejo";
        };
        String reachEn = switch (order) {
            case 0 -> "";
            case 1 -> " It reaches nodes from farther away than the alchemical brass one.";
            default -> " It is the finest tier and answers a node from the greatest distance.";
        };
        String reachEs = switch (order) {
            case 0 -> "";
            case 1 -> " Alcanza nodos desde más lejos que la de latón alquímico.";
            default -> " Es el mejor nivel y responde a un nodo desde la mayor distancia.";
        };
        return t(
                "The " + m.en() + " " + kindEn + " is a variant of the Create machine that works exactly like the original, but many times faster while an aura node is near; away from every node it behaves as the plain machine." + reachEn
                        + "\n\nIt is infused on an altar over " + fromEn + ".",
                "Variante de " + kindEs.toLowerCase() + " de " + m.es() + ": una variante de la máquina de Create que funciona exactamente igual que la original, pero muchas veces más rápido mientras hay un nodo de aura cerca; lejos de todo nodo se comporta como la máquina normal." + reachEs
                        + "\n\nSe infunde en un altar sobre " + fromEs + ".");
    }

    private static Text neutron(Name m, boolean collector) {
        int order = metalRank(m);
        String machineEn = collector ? "Neutron Collector" : "Neutron Compressor";
        String machineEs = collector ? "Colector neutrónico" : "Compresor neutrónico";
        String tierEn = m == ALCHEMICAL_BRASS ? "Brass" : m == VOID_METAL ? "Void" : "Thaumium";
        String tierEs = m == ALCHEMICAL_BRASS ? "de latón" : m == VOID_METAL ? "del vacío" : "de taumio";
        String fromEn = switch (order) {
            case 0 -> "the Densest Neutronium " + (collector ? "Collector" : "Compressor") + ", four thaumium plates and a Simple Arcane Mechanism";
            case 1 -> "the Thaumium " + machineEn + ", four alchemical brass plates and a Simple Arcane Mechanism";
            default -> "the Brass " + machineEn + ", four void metal plates and a Void Seed";
        };
        String fromEs = switch (order) {
            case 0 -> "el " + machineEs + " de neutronio más denso, cuatro placas de taumio y un Mecanismo arcano simple";
            case 1 -> "el " + machineEs + " de taumio, cuatro placas de latón alquímico y un Mecanismo arcano simple";
            default -> "el " + machineEs + " de latón, cuatro placas de metal del vacío y una Semilla del vacío";
        };
        return t(
                "The " + tierEn + " " + machineEn + " works exactly like the Avaritia machine and uses the same recipes, only faster, and it gives more every time it finishes. Each tier is quicker and more generous than the one before.\n\nIt is infused on an altar from " + fromEn + ". How much faster and how much more is set in the configuration.",
                "El " + machineEs + " " + tierEs + " funciona exactamente igual que la máquina de Avaritia y usa las mismas recetas, solo que más rápido, y da más cada vez que termina. Cada nivel es más rápido y más generoso que el anterior.\n\nSe infunde en un altar a partir de " + fromEs + ". Cuánto más rápido y cuánto más se ajusta en la configuración.");
    }

    private static String previousEn(Name m) {
        return m == VOID_METAL ? "Alchemical Brass" : m == ALCHEMICAL_BRASS ? "Thaumium" : null;
    }

    private static String previousEs(Name m) {
        return m == VOID_METAL ? "latón alquímico" : m == ALCHEMICAL_BRASS ? "taumio" : null;
    }

    private static Text miner(Name m) {
        String before = previousEn(m);
        String beforeEs = previousEs(m);
        return t(
                "The " + m.en() + " Miner Controller is a Void Miners miner that takes no energy at all: it works only while it can pay units to a magical power source close by (a jarred node, a Flux Jar, a Rift Jar, a node or a flux rift) and stops the moment it cannot. A hungry node is never a valid source.\n\nIt is infused on an altar from "
                        + (before == null ? "a Rubetine Miner" : "the " + before + " Miner Controller") + ", " + m.en().toLowerCase() + " plates, mechanisms and Flux Jars" + (m == VOID_METAL ? " and a void seed" : "")
                        + ". The structure is built from its frames, glass panels and any miner modifier. Each tier mines faster and yields more than the one before.",
                "El Controlador de minero de " + m.es() + " es un minero de Void Miners que no gasta nada de energía: solo trabaja mientras puede pagar unidades a una fuente de poder mágico cercana (un nodo en jarra, una Jarra de flux, una Jarra de grieta, un nodo o una grieta de flujo) y se detiene en cuanto no puede. Un nodo hambriento nunca es una fuente válida.\n\nSe infunde en un altar a partir de "
                        + (before == null ? "un Minero Rubetine" : "el Controlador de minero de " + beforeEs) + ", placas de " + m.es() + ", mecanismos y Jarras de flux" + (m == VOID_METAL ? " y una semilla del vacío" : "")
                        + ". La estructura se construye con sus marcos, paneles de cristal y cualquier modificador de minero. Cada nivel mina más rápido y rinde más que el anterior.");
    }

    private static Text solar(Name m) {
        String before = previousEn(m);
        String beforeEs = previousEs(m);
        return t(
                "The " + m.en() + " Solar Panel Controller generates energy only while it can pay units to a magical power source close by, with no need for sun or open sky. A hungry node is never a valid source.\n\nIt is infused on an altar from "
                        + (before == null ? "a Rubetine Solar Panel" : "the " + before + " Solar Panel Controller") + ", " + m.en().toLowerCase() + " plates, mechanisms and jarred nodes" + (m == VOID_METAL ? " and a void seed" : "")
                        + ". The structure is built from its frames, glass panels and any solar modifier. Each tier generates more than the one before.",
                "El Controlador de panel solar de " + m.es() + " genera energía solo mientras puede pagar unidades a una fuente de poder mágico cercana, sin necesidad de sol ni de cielo abierto. Un nodo hambriento nunca es una fuente válida.\n\nSe infunde en un altar a partir de "
                        + (before == null ? "un Panel solar Rubetine" : "el Controlador de panel solar de " + beforeEs) + ", placas de " + m.es() + ", mecanismos y nodos en jarra" + (m == VOID_METAL ? " y una semilla del vacío" : "")
                        + ". La estructura se construye con sus marcos, paneles de cristal y cualquier modificador solar. Cada nivel genera más que el anterior.");
    }

    private static Text frame(Name m, boolean solar) {
        String before = previousEn(m);
        String beforeEs = previousEs(m);
        return t(
                "A " + m.en() + " " + (solar ? "Solar Panel" : "Miner") + " Frame, the block the structure of a " + (solar ? "solar panel" : "miner") + " of this tier is built from, together with glass panels and modifiers.\n\nIt is made at the arcane workbench from " + m.en().toLowerCase() + " plates, obsidian and "
                        + (before == null ? "a base frame" : "the matching " + before + " frame") + ".",
                "Un Marco de " + (solar ? "panel solar" : "minero") + " de " + m.es() + ", el bloque con el que se construye la estructura de un " + (solar ? "panel solar" : "minero") + " de este nivel, junto a paneles de cristal y modificadores.\n\nSe fabrica en la mesa de trabajo arcana con placas de " + m.es() + ", obsidiana y "
                        + (before == null ? "un marco base" : "el marco de " + beforeEs + " correspondiente") + ".");
    }

    private static Text upgrade(Name m, String targetEn, String targetEs, boolean solar, boolean lowers) {
        String before = previousEn(m);
        String beforeEs = previousEs(m);
        String verbEn = lowers ? "lowers" : "raises";
        String verbEs = lowers ? "baja" : "sube";
        String kindEn = solar ? "solar panel" : "miner";
        String kindEs = solar ? "panel solar" : "minero";
        return t(
                "A " + m.en() + " " + (solar ? "Solar Panel" : "Miner") + " Upgrade that " + verbEn + " " + targetEn + " of the " + kindEn + " it sits beside. It goes in a free slot of the structure, and its effect multiplies with the other modifiers, including those of Void Miners. Each tier does it more than the one before.\n\nIt is made at the arcane workbench from "
                        + (before == null ? "the matching real Void Miners upgrade" : "the same upgrade of the " + before + " tier") + ", two " + m.en().toLowerCase() + " mechanisms and two plates.",
                "Una mejora de " + kindEs + " de " + m.es() + " que " + verbEs + " " + targetEs + " del " + kindEs + " junto al que está. Va en un hueco libre de la estructura y su efecto se multiplica con los demás modificadores, incluidos los de Void Miners. Cada nivel lo hace más que el anterior.\n\nSe fabrica en la mesa de trabajo arcana a partir de "
                        + (before == null ? "la mejora real de Void Miners correspondiente" : "la misma mejora del nivel de " + beforeEs) + ", dos mecanismos de " + m.es() + " y dos placas.");
    }

    private static Text spreader(Name m) {
        int order = metalRank(m);
        String fromEn = switch (order) {
            case 0 -> "a Gaia Spreader, four thaumium plates and a Simple Arcane Mechanism";
            case 1 -> "the Thaumium Mana Spreader, four alchemical brass plates and a Simple Arcane Mechanism";
            default -> "the Alchemical Brass Mana Spreader, four void metal plates and a Void Seed";
        };
        String fromEs = switch (order) {
            case 0 -> "un Esparcidor de Gaia, cuatro placas de taumio y un Mecanismo arcano simple";
            case 1 -> "el Esparcidor de maná de taumio, cuatro placas de latón alquímico y un Mecanismo arcano simple";
            default -> "el Esparcidor de maná de latón alquímico, cuatro placas de metal del vacío y una Semilla del vacío";
        };
        return t(
                "The " + m.en() + " Mana Spreader holds far more mana and throws its bursts harder than the tier before it. It works like any other spreader, takes lenses and can be bound with the Wand of the Forest.\n\nIt is infused on an altar from " + fromEn
                        + ". Burst size, capacity, range, speed and colour are set in the configuration for each tier.",
                "El Esparcidor de maná de " + m.es() + " guarda mucho más maná y lanza sus ráfagas con más fuerza que el nivel anterior. Funciona como cualquier otro esparcidor, admite lentes y se puede enlazar con la Varita del Bosque.\n\nSe infunde en un altar a partir de " + fromEs
                        + ". El tamaño de ráfaga, la capacidad, el alcance, la velocidad y el color se ajustan en la configuración de cada nivel.");
    }

    private static Text unearther(Name m) {
        int order = metalRank(m);
        String fromEn = switch (order) {
            case 0 -> "an Unearther core, four thaumium plates and two simple mechanisms";
            case 1 -> "a Thaumium Unearther core, four alchemical brass plates and two simple mechanisms";
            default -> "an Alchemical Brass Unearther core, four void metal plates and two complex mechanisms";
        };
        String fromEs = switch (order) {
            case 0 -> "un núcleo de Unearther, cuatro placas de taumio y dos mecanismos simples";
            case 1 -> "un núcleo de Unearther de taumio, cuatro placas de latón alquímico y dos mecanismos simples";
            default -> "un núcleo de Unearther de latón alquímico, cuatro placas de metal del vacío y dos mecanismos complejos";
        };
        String degreeEn = switch (order) {
            case 0 -> "";
            case 1 -> " It looks for power over a wider area and works much faster than the thaumium one, at the price of taking more power each time.";
            default -> " It is the fastest of the three and reaches farthest for power, and also takes the most.";
        };
        String degreeEs = switch (order) {
            case 0 -> "";
            case 1 -> " Busca poder en un área más amplia y trabaja mucho más rápido que el de taumio, a costa de tomar más poder cada vez.";
            default -> " Es el más rápido de los tres y el que llega más lejos en busca de poder, y también el que más toma.";
        };
        return t(
                "The " + m.en() + " Unearther is an Unearther of FTB Unearthed that has no use for food: while a node, a node jar, a flux jar or a flux rift stands near it, it draws power from it every few moments and works faster. Without power it works at the normal speed, unless the settings say otherwise. A hungry node is never a safe source."
                        + degreeEn + "\n\nIt is made at the arcane workbench from " + fromEn + ".",
                "El Unearther de " + m.es() + " es un Unearther de FTB Unearthed que no necesita comida: mientras un nodo, un nodo en jarra, una Jarra de flux o una grieta de flujo está cerca, toma poder de ella cada poco y trabaja más rápido. Sin poder trabaja a velocidad normal, salvo que la configuración diga otra cosa. Un nodo hambriento nunca es una fuente segura."
                        + degreeEs + "\n\nSe fabrica en la mesa de trabajo arcana con " + fromEs + ".");
    }

    private static Text knife(Name m) {
        int order = metalRank(m);
        String tailEn = switch (order) {
            case 0 -> "Craft it at the arcane workbench with a thaumium plate over a stick. It lasts longer, hits harder and enchants better than the iron knife.";
            case 1 -> "Craft it at the arcane workbench with an alchemical brass plate over a Thaumium Knife. It is sturdier, sharper and easier to enchant than the thaumium one.";
            default -> "Craft it at the arcane workbench with a void metal plate over an Alchemical Brass Knife. It is the best Farmer's Delight knife of the three, and sometimes a hit with it leaves Flux Taint on whatever it struck.";
        };
        String tailEs = switch (order) {
            case 0 -> "Se fabrica en la mesa de trabajo arcana con una placa de taumio sobre un palo. Dura más, pega más fuerte y se encanta mejor que el cuchillo de hierro.";
            case 1 -> "Se fabrica en la mesa de trabajo arcana con una placa de latón alquímico sobre un Cuchillo de taumio. Es más resistente, más afilado y se encanta más fácil que el de taumio.";
            default -> "Se fabrica en la mesa de trabajo arcana con una placa de metal del vacío sobre un Cuchillo de latón alquímico. Es el mejor cuchillo de Farmer's Delight de los tres, y a veces un golpe con él deja Corrupción por flujo en lo que haya herido.";
        };
        return t(
                "A " + m.en() + " Knife, a Farmer's Delight knife in every way: it cuts on the Cutting Board and counts as a knife for any recipe that asks for one.\n\n" + tailEn,
                "Un Cuchillo de " + m.es() + ", un cuchillo de Farmer's Delight en todos los sentidos: corta en la Tabla de cortar y cuenta como cuchillo en cualquier receta que pida uno.\n\n" + tailEs);
    }

    private static Text brush(Name m) {
        int order = m == BRASS ? 0 : m == THAUMIUM ? 1 : 2;
        String tailEn = switch (order) {
            case 0 -> "It is made at the arcane workbench from a feather, a brass plate and a stick. It is the lowest of the three brushes in odds and durability.";
            case 1 -> "It is made at the arcane workbench from a brass brush, three thaumium plates and a feather. It is sturdier and works faster than the brass brush, and finds treasure far more often.";
            default -> "It is made at the arcane workbench from a thaumium brush, three void metal plates and a feather. It is the most durable and the fastest of the three, and gives the best odds.";
        };
        String tailEs = switch (order) {
            case 0 -> "Se fabrica en la mesa de trabajo arcana con una pluma, una placa de latón y un palo. Es el de menos probabilidades y durabilidad de los tres pinceles.";
            case 1 -> "Se fabrica en la mesa de trabajo arcana con un pincel de latón, tres placas de taumio y una pluma. Es más resistente y trabaja más rápido que el de latón, y encuentra tesoros con mucha más frecuencia.";
            default -> "Se fabrica en la mesa de trabajo arcana con un pincel de taumio, tres placas de metal del vacío y una pluma. Es el más duradero y el más rápido de los tres, y da las mejores probabilidades.";
        };
        return t(
                "A " + m.en() + " Brush for sand and gravel, used by hand or inside the Unearther of FTB Unearthed. It can unearth primal vis crystals, amber and raw cinnabar.\n\n" + tailEn,
                "Un Pincel de " + m.es() + " para arena y grava, que se usa a mano o dentro del Unearther de FTB Unearthed. Puede desenterrar cristales de vis primarios, ámbar y cinabrio en bruto.\n\n" + tailEs);
    }

    private static Text grindingBall(Name m) {
        return t(
                "A " + m.en() + " Grinding Ball for the SAG Mill of Ender IO. It gives more output and more bonus output than the ball before it, uses less power and lasts longer. The tiers go Brass, Thaumium, Void Metal.\n\nIt is crafted from ingots of " + m.en().toLowerCase() + ". Put it in the grinding ball slot of the mill.",
                "Una Bola de molienda de " + m.es() + " para el Molino SAG de Ender IO. Da más producto y más producto extra que la bola anterior, gasta menos energía y dura más. Los niveles van de latón a taumio y a metal del vacío.\n\nSe fabrica con lingotes de " + m.es() + ". Colócala en la ranura de bolas de molienda del molino.");
    }

    private static Text foregoing(Name m, String kindEn, String kindEs, String effectEn, String effectEs) {
        int order = m == BRASS ? 0 : m == THAUMIUM ? 1 : 2;
        String fromEn = switch (order) {
            case 0 -> "three brass plates, redstone, a glass pane, the material of each addon and Liquid Death";
            case 1 -> "the brass addon of the same kind, three thaumium plates, a simple mechanism, the material of the addon, a diamond and a larger measure of Liquid Death";
            default -> "the thaumium addon of the same kind, three void metal plates, a complex mechanism, the material of the addon, an eye of ender and a great deal of Liquid Death";
        };
        String fromEs = switch (order) {
            case 0 -> "tres placas de latón, redstone, un panel de cristal, el material de cada mejora y Muerte líquida";
            case 1 -> "la mejora de latón del mismo tipo, tres placas de taumio, un mecanismo simple, el material de la mejora, un diamante y una medida mayor de Muerte líquida";
            default -> "la mejora de taumio del mismo tipo, tres placas de metal del vacío, un mecanismo complejo, el material de la mejora, un ojo de ender y mucha Muerte líquida";
        };
        return t(
                "A " + m.en() + " " + capitalize(kindEn) + " Addon for the machines of Industrial Foregoing: it " + effectEn + ", and is stronger than the diamond original and than the tier before it.\n\nIt is made in an Industrial Foregoing dissolution chamber from " + fromEn + ". Place it in the addon slots of a machine, or use it on the machine directly. The values are set in the configuration.",
                "Una mejora de " + kindEs + " de " + m.es() + " para las máquinas de Industrial Foregoing: " + effectEs + ", y es más fuerte que la original de diamante y que la del nivel anterior.\n\nSe fabrica en una cámara de disolución de Industrial Foregoing con " + fromEs + ". Colócala en las ranuras de mejoras de una máquina, o úsala sobre la máquina directamente. Los valores se ajustan en la configuración.");
    }

    private static String capitalize(String value) {
        return value.isEmpty() ? value : Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    private static Text energized(String rest) {
        String tierPrefix = null;
        for (String prefix : new String[]{"alchemical_brass_", "thaumium_", "void_"}) {
            if (rest.startsWith(prefix)) {
                tierPrefix = prefix;
                break;
            }
        }
        if (tierPrefix == null) {
            return null;
        }
        String kind = rest.substring(tierPrefix.length());
        String kindEn;
        String kindEs;
        String article;
        String definite;
        String adjective;
        switch (kind) {
            case "pickaxe" -> {
                kindEn = "pickaxe";
                kindEs = "pico";
                article = "Un";
                definite = "el";
                adjective = "energizado";
            }
            case "axe" -> {
                kindEn = "axe";
                kindEs = "hacha";
                article = "Un";
                definite = "el";
                adjective = "energizada";
            }
            case "shovel" -> {
                kindEn = "shovel";
                kindEs = "pala";
                article = "Una";
                definite = "la";
                adjective = "energizada";
            }
            case "hoe" -> {
                kindEn = "hoe";
                kindEs = "azada";
                article = "Una";
                definite = "la";
                adjective = "energizada";
            }
            case "sword" -> {
                kindEn = "sword";
                kindEs = "espada";
                article = "Una";
                definite = "la";
                adjective = "energizada";
            }
            default -> {
                return null;
            }
        }
        String artLower = article.toLowerCase();
        int order = tierPrefix.equals("thaumium_") ? 0 : tierPrefix.equals("alchemical_brass_") ? 1 : 2;
        Name m = order == 0 ? THAUMIUM : order == 1 ? ALCHEMICAL_BRASS : VOID_METAL;
        String fromEn = switch (order) {
            case 0 -> "the normal thaumium " + kindEn + ", two energy tablets, a reinforced alloy, an advanced control circuit and two thaumium plates";
            case 1 -> "a normal iron " + kindEn + ", the energized thaumium " + kindEn + " (consumed), two energy tablets, an infused alloy, a basic control circuit and two alchemical brass plates";
            default -> "the normal void metal " + kindEn + ", the energized alchemical brass " + kindEn + " (consumed), two energy tablets, an atomic alloy, an ultimate control circuit and two void metal plates";
        };
        String fromEs = switch (order) {
            case 0 -> definite + " " + kindEs + " de taumio normal, dos tablillas de energía, una aleación reforzada, un circuito de control avanzado y dos placas de taumio";
            case 1 -> artLower + " " + kindEs + " de hierro normal, " + definite + " " + kindEs + " de taumio " + adjective + " (se consume), dos tablillas de energía, una aleación infundida, un circuito de control básico y dos placas de latón alquímico";
            default -> definite + " " + kindEs + " de metal del vacío normal, " + definite + " " + kindEs + " de latón alquímico " + adjective + " (se consume), dos tablillas de energía, una aleación atómica, un circuito de control definitivo y dos placas de metal del vacío";
        };
        String degreeEn = switch (order) {
            case 0 -> "It is the first of the energized tools: it holds the least energy, spends the most of it and works the slowest.";
            case 1 -> "It holds more energy, spends less of it and works faster than the thaumium one.";
            default -> "It holds by far the most energy, spends the least per action, mines the fastest and hits the hardest.";
        };
        String degreeEs = switch (order) {
            case 0 -> "Es la primera de las herramientas con energía: guarda la menos energía, gasta la más y trabaja la más lenta.";
            case 1 -> "Guarda más energía, gasta menos y trabaja más rápido que la de taumio.";
            default -> "Guarda con diferencia la mayor energía, gasta lo mínimo por acción, mina lo más rápido y golpea lo más fuerte.";
        };
        return t(
                "An energized " + m.en() + " " + kindEn + " that runs on Mekanism energy: it loses no durability, and every block mined, every hit and every stripped, flattened or tilled block spends some of its charge. It is charged in an energy cube, a charge pad, any Mekanism machine charging slot or any other energy source. With no energy left it does nothing a tool should do until it is charged again.\n\n"
                        + degreeEn + " It is infused on an altar from " + fromEn + ". Capacity, costs, mining speed and damage are set in the configuration.",
                article + " " + kindEs + " de " + m.es() + " " + adjective + " que funciona con energía de Mekanism: no pierde durabilidad, y cada bloque minado, cada golpe y cada bloque pelado, aplanado o arado gasta parte de su carga. Se carga en un cubo de energía, una almohadilla de carga, la ranura de carga de cualquier máquina de Mekanism o cualquier otra fuente de energía. Sin energía no hace nada de lo que debe hacer una herramienta hasta que se vuelva a cargar.\n\n"
                        + degreeEs + " Se infunde en un altar a partir de " + fromEs + ". La capacidad, los costes, la velocidad de minado y el daño se ajustan en la configuración.");
    }

    private static Text oritech(String rest) {
        String tierId;
        Name m;
        if (rest.startsWith("brass_")) {
            tierId = "brass_";
            m = BRASS;
        } else if (rest.startsWith("thaumium_")) {
            tierId = "thaumium_";
            m = THAUMIUM;
        } else if (rest.startsWith("void_")) {
            tierId = "void_";
            m = VOID_METAL;
        } else {
            return null;
        }
        String kind = rest.substring(tierId.length());
        int order = m == BRASS ? 0 : m == THAUMIUM ? 1 : 2;
        String kindEn;
        String kindEs;
        String effectEn;
        String effectEs;
        String partEn;
        String partEs;
        switch (kind) {
            case "speed_addon" -> {
                kindEn = "Speed";
                kindEs = "velocidad";
                effectEn = "makes the machine process in a smaller fraction of the time than Oritech's own addon, at a slightly different energy cost";
                effectEs = "hace que la máquina procese en una fracción menor del tiempo que la mejora propia de Oritech, con un gasto de energía algo distinto";
                partEn = "magnetic coils";
                partEs = "bobinas magnéticas";
            }
            case "efficiency_addon" -> {
                kindEn = "Efficiency";
                kindEs = "eficiencia";
                effectEn = "makes the machine spend a smaller share of its energy than Oritech's own addon";
                effectEs = "hace que la máquina gaste una parte menor de su energía que la mejora propia de Oritech";
                partEn = "electrum ingots";
                partEs = "lingotes de electrum";
            }
            case "capacitor_addon" -> {
                kindEn = "Capacitor";
                kindEs = "capacitor";
                effectEn = "adds a large amount of energy capacity and insert rate to the machine";
                effectEs = "añade una gran cantidad de capacidad de energía y de ritmo de entrada a la máquina";
                partEn = "energite ingots";
                partEs = "lingotes de energite";
            }
            default -> {
                return null;
            }
        }
        String fromEn = switch (order) {
            case 0 -> "the Oritech addon of the same kind, with brass plates, simple mechanisms, " + partEn + " and a crystal";
            case 1 -> "the brass addon of the same kind, with thaumium plates, complex mechanisms, " + partEn + " and an overcharged crystal";
            default -> "the thaumium addon of the same kind, with void metal plates, complex mechanisms, " + partEn + " and a Heisenberg compensator";
        };
        String fromEs = switch (order) {
            case 0 -> "la mejora de Oritech del mismo tipo, con placas de latón, mecanismos simples, " + partEs + " y un cristal";
            case 1 -> "la mejora de latón del mismo tipo, con placas de taumio, mecanismos complejos, " + partEs + " y un cristal sobrecargado";
            default -> "la mejora de taumio del mismo tipo, con placas de metal del vacío, mecanismos complejos, " + partEs + " y un compensador de Heisenberg";
        };
        return t(
                "A " + m.en() + " " + kindEn + " Addon for the machines of Oritech: it " + effectEn + ". Each tier does it better than the one before.\n\nIt is built at the arcane workbench around " + fromEn + ". Place it against the machine like any other addon. Every value is set in the configuration.",
                "Una mejora de " + kindEs + " de " + m.es() + " para las máquinas de Oritech: " + effectEs + ". Cada nivel lo hace mejor que el anterior.\n\nSe construye en la mesa de trabajo arcana alrededor de " + fromEs + ". Colócala junto a la máquina como cualquier otra mejora. Todos los valores se ajustan en la configuración.");
    }

    private static Text resourceNode(String rest) {
        String material;
        String materialEs;
        String from;
        String fromEs;
        switch (rest) {
            case "cinnabar" -> {
                material = "cinnabar";
                materialEs = "cinabrio";
                from = "Redstone";
                fromEs = "Redstone";
            }
            case "amber" -> {
                material = "amber";
                materialEs = "ámbar";
                from = "Gold";
                fromEs = "Oro";
            }
            case "quartz" -> {
                material = "quartz";
                materialEs = "cuarzo";
                from = "Lapis";
                fromEs = "Lapislázuli";
            }
            default -> {
                return null;
            }
        }
        return t(
                "A " + capitalize(material) + " Resource Node: a bedrock node that Oritech's Deep Drill can bleed forever, giving " + (rest.equals("cinnabar") ? "raw cinnabar" : rest) + " without ever running dry. It also generates in the overworld like the nodes of Oritech, with a boulder of deepslate on the surface.\n\nIt is infused on an altar from the " + from + " resource node of Oritech, surrounded by the ore and the product of the material. The amount per cycle and how rare it is are set in the configuration.",
                "Un Nodo de recurso de " + materialEs + ": un nodo de lecho de roca que el Taladro profundo de Oritech puede sangrar para siempre, dando " + (rest.equals("cinnabar") ? "cinabrio en bruto" : materialEs) + " sin agotarse. También se genera en la superficie como los nodos de Oritech, con un pedrusco de pizarra profunda encima.\n\nSe infunde en un altar a partir del nodo de recurso de " + fromEs + " de Oritech, rodeado del mineral y del producto del material. La cantidad por ciclo y su rareza se ajustan en la configuración.");
    }

    private static Text cluster(String rest) {
        String metal;
        String metalEs;
        switch (rest) {
            case "osmium" -> {
                metal = "osmium";
                metalEs = "osmio";
            }
            case "uranium" -> {
                metal = "uranium";
                metalEs = "uranio";
            }
            default -> {
                return null;
            }
        }
        return t(
                "A Native " + capitalize(metal) + " Cluster, a chunk of raw " + metal + " in the style of the Thaumaturge ore clusters. It is smelted for " + metal + " or dissolved in the crucible, and it is the result of the ore-processing routes of Mekanism and of the ores of the Industrial Foregoing Laser Drill that this mod adds.",
                "Un Cúmulo Nativo de " + metalEs + ", un trozo de " + metalEs + " en bruto al estilo de los cúmulos de mineral de Thaumaturge. Se funde para sacar " + metalEs + " o se disuelve en el crisol, y es el resultado de las rutas de procesado de minerales de Mekanism y de los minerales del Taladro láser de Industrial Foregoing que añade este mod.");
    }

    private static Text tainted() {
        return t(
                "The tainted version of %s. It is brewed in a crucible filled with enough vitium, dropping the original food into it as the catalyst.\n\nIt fills more hunger and more saturation than the original and keeps its effects, but whoever eats it is struck by Flux Taint for a while.",
                "La versión contaminada de «%s». Se prepara en un crisol lleno de suficiente vitium, echando el alimento original como catalizador.\n\nSacia más hambre y da más saturación que el original y conserva sus efectos, pero quien lo come recibe Corrupción por flujo durante un tiempo.");
    }

    private static void put(String path, String en, String es) {
        EXACT.put(path, new Text(en, contract(es)));
    }

    private static void exact() {
        put("akashic_record",
                "The Akashic Records, a record that holds every thought that has ever been thought. Opening it floods the mind with every aspect there is, hundreds of points of each, and then a little more.\n\nIt is infused on an altar from the purest ingots of infinity, once its research is complete.",
                "Los Registros Akáshicos, un registro que guarda todo pensamiento que se haya pensado jamás. Al abrirlo, la mente se inunda de todos los aspectos que existen, cientos de puntos de cada uno, y un poco más.\n\nSe infunde en un altar con los lingotes de infinito más puros, una vez completada su investigación.");
        put("extremely_primordial_pearl",
                "The Extremely Primordial Pearl, several primordial pearls folded into one on an infusion altar with the aspect of the end of things. It is not spent when it is used as a catalyst, so it serves as an everlasting one.\n\nIt is a required ingredient of the Infinity tier of nearly every Avaritia upgrade of this mod.",
                "La Perla Extremadamente Primordial, varias perlas primordiales plegadas en una sobre un altar de infusión con el aspecto del fin de las cosas. No se gasta cuando se usa como catalizador, así que sirve como uno eterno.\n\nEs un ingrediente imprescindible del nivel de Infinito de casi todas las mejoras de Avaritia de este mod.");
        put("flux_jar",
                "The Flux Jar slowly pulls flux from the aura of its chunk until it is full. Machines that work on magical power can use that flux as their power source. A hungry node is never a safe source.\n\nIt is made at the arcane workbench from a Warded Jar surrounded by four void metal plates. Breaking it pours everything it holds back into the aura as flux.",
                "La Jarra de flux va sacando flujo del aura de su chunk hasta llenarse. Las máquinas que funcionan con poder mágico pueden usar ese flujo como fuente. Un nodo hambriento nunca es una fuente segura.\n\nSe fabrica en la mesa de trabajo arcana con un frasco arcano rodeado de cuatro placas de metal del vacío. Al romperla, todo lo que guarda vuelve al aura como flujo.");
        put("rift_jar",
                "The Rift Jar, set beside a flux rift, takes the rift apart one point at a time and keeps that as power for any machine that uses magical power. It stops once the rift is down to its smallest size.\n\nIt is made at the arcane workbench from a Warded Jar, two void seeds and two void metal plates. A broken Rift Jar spills what it still holds back into the aura as flux.",
                "La Jarra de grieta, colocada junto a una grieta de flujo, la desmonta punto a punto y guarda eso como poder para cualquier máquina que use poder mágico. Se detiene cuando la grieta llega a su tamaño mínimo.\n\nSe fabrica en la mesa de trabajo arcana con un frasco arcano, dos semillas del vacío y dos placas de metal del vacío. Una Jarra de grieta rota devuelve al aura, como flujo, lo que aún guarda.");
        put("thaummotor",
                "The Thaummotor is a Create motor with no wires: while a jarred node, a node, a Flux Jar or a flux rift stands within reach, it takes a little power from it every so often and turns at a fixed speed, providing stress to the whole kinetic network. The richer the source, the more stress it gives; with no source, or none left, it stops. A hungry node is never a valid source.\n\nIt is made at the arcane workbench from a brass casing, two shafts, two simple mechanisms and four thaumium plates. The goggles of Create tell which source it is using.",
                "El Taummotor es un motor de Create sin cables: mientras un nodo en jarra, un nodo, una Jarra de flux o una grieta de flujo está a su alcance, toma un poco de poder cada poco y gira a velocidad fija, dando estrés a toda la red cinética. Cuanto más rica es la fuente, más estrés da; sin fuente, o sin poder en ella, se detiene. Un nodo hambriento nunca es una fuente válida.\n\nSe fabrica en la mesa de trabajo arcana con una carcasa de latón, dos ejes, dos mecanismos simples y cuatro placas de taumio. Las gafas de Create dicen qué fuente está usando.");
        put("seal_create",
                "The Create Seal sits on a block of Create and lets a golem work it: it inserts the items the golem carries, takes items out, and can also click the block, which turns hand cranks and valve handles. The filter picks the items of every task, and each task can be switched off in the options of the seal.\n\nIt is infused over a Use Seal with a cogwheel, a brass sheet, andesite alloy and a simple mechanism.",
                "El Sello de Create se coloca sobre un bloque de Create y deja que un gólem lo maneje: inserta los objetos que lleva el gólem, saca objetos y también puede hacer clic en el bloque, lo que gira manivelas y asas de válvula. El filtro elige los objetos de cada tarea, y cada tarea se puede desactivar en las opciones del sello.\n\nSe infunde sobre un Sello de uso con un engranaje, una lámina de latón, aleación de andesita y un mecanismo simple.");
        put("purification_potion",
                "The Purification Splash Potion cures infected creatures in the area where it breaks: pets, livestock and monsters alike return to their natural selves, and it also drives the flux sickness out of the blood.\n\nIt is brewed in the crucible from Ordo, Victus, Aqua and Lux, with an Ethereal Bloom as the catalyst.",
                "La Poción Arrojadiza de Purificación cura a las criaturas infectadas en la zona donde se rompe: mascotas, ganado y monstruos vuelven a ser ellos mismos, y además saca del cuerpo la enfermedad del flujo.\n\nSe prepara en el crisol con Ordo, Victus, Aqua y Lux, con una Flor etérea como catalizador.");
        put("magical_laser_drill",
                "The Magical Laser Drill works like the Laser Drill of Industrial Foregoing, with the same screen painted purple, but its bar holds vis instead of power. Place it with its emitting face looking at any Laser Base, mundane or magical, inside its working area, and every operation spends 1 vis and gives a step of work to the base. The vis comes from the aura of its chunk and, if the aura cannot pay, from a node, a jarred node, a Flux Jar, a Rift Jar or a flux rift within reach; a hungry node never counts.\n\nIt is infused on an altar from an ordinary Laser Drill. Vis cost, speed, buffer and sources are set in the configuration.",
                "El Taladro Láser Mágico funciona como el Taladro Láser de Industrial Foregoing, con la misma pantalla pintada de morado, pero su barra guarda vis en vez de energía. Colócalo con su cara emisora mirando a cualquier Base Láser, mundana o mágica, dentro de su área de trabajo, y cada operación consume 1 vis y da un paso de trabajo a la base. El vis sale del aura de su chunk y, si el aura no puede pagar, de un nodo, un nodo en jarra, una Jarra de flux, una Jarra de grieta o una grieta de flujo a su alcance; un nodo hambriento nunca cuenta.\n\nSe infunde en un altar a partir de un Taladro Láser normal. El coste de vis, la velocidad, el depósito y las fuentes se ajustan en la configuración.");
        put("magical_ore_laser_base",
                "The Magical Ore Laser Base is the Ore Laser Base of Industrial Foregoing with its screen painted purple: six lens slots, fifteen output slots and a depth that decides which ores appear. Lenses raise the chance of the ores of their color, and the work advances every time a Laser Drill, mundane or magical, sends its beam into it.\n\nIt is infused on an altar from an ordinary Ore Laser Base. Steps of work and lens weight are set in the configuration.",
                "La Base Láser Mágica de Minerales es la Base Láser de Minerales de Industrial Foregoing con su pantalla pintada de morado: seis ranuras de lente, quince de salida y una profundidad que decide qué minerales aparecen. Las lentes suben la probabilidad de los minerales de su color, y el trabajo avanza cada vez que un Taladro Láser, mundano o mágico, le envía su rayo.\n\nSe infunde en un altar a partir de una Base Láser de Minerales normal. Los pasos de trabajo y el peso de las lentes se ajustan en la configuración.");
        put("magical_fluid_laser_base",
                "The Magical Fluid Laser Base is the Fluid Laser Base of Industrial Foregoing with its screen painted purple: a lens slot that chooses the fluid, a depth that decides where it can be found and a tank of 32000 mB. The work advances every time a Laser Drill, mundane or magical, sends its beam into it.\n\nIt is infused on an altar from an ordinary Fluid Laser Base. Steps of work are set in the configuration.",
                "La Base Láser Mágica de Fluidos es la Base Láser de Fluidos de Industrial Foregoing con su pantalla pintada de morado: una ranura de lente que elige el fluido, una profundidad que decide dónde puede encontrarse y un tanque de 32000 mB. El trabajo avanza cada vez que un Taladro Láser, mundano o mágico, le envía su rayo.\n\nSe infunde en un altar a partir de una Base Láser de Fluidos normal. Los pasos de trabajo se ajustan en la configuración.");
        put("magical_infinity_drill",
                "The Magical Infinity Drill mines a whole area at once and drops everything at your feet, like the Infinity Drill of Industrial Foregoing, but it holds vis instead of power. A Recharge Pedestal fills it, and while its charging is on it also draws vis slowly from the aura around you. The more vis it holds, the wider the area, and it falls back to a smaller level as it empties. Its tank takes Liquid Death, which is spent instead of vis.\n\nShift and right click steps the area down one level; right click on air turns the charging on or off. It is infused on an altar from an Infinity Drill.",
                "El Taladro Mágico del Infinito mina un área entera de golpe y suelta todo a tus pies, como el Taladro del Infinito de Industrial Foregoing, pero guarda vis en vez de energía. Un Pedestal de recarga lo llena, y mientras su carga está activa también saca vis despacio del aura que te rodea. Cuanto más vis guarda, más ancha es el área, y baja a un nivel menor al vaciarse. Su depósito admite Muerte líquida, que se gasta en lugar de vis.\n\nMayús y clic derecho baja el área un nivel; clic derecho al aire activa o desactiva la carga. Se infunde en un altar a partir de un Taladro del Infinito.");
        put("flux_knife",
                "The Flux Knife is a Farmer's Delight knife that bites into the world itself: sneak and use it on a block to open a flux rift beside it, sized to the flux it takes from the aura of that chunk. If the chunk does not have enough, it opens nothing. Every rift wears the blade, and the knife needs a pause before the next one.\n\nOn the Cutting Board, food it cuts also adds flux to the air. It is infused from a void metal knife with two Flux Crystals, Tainted Goo and a Bottle of Taint.",
                "El Cuchillo de flujo es un cuchillo de Farmer's Delight que muerde el propio mundo: agáchate y úsalo sobre un bloque para abrir una grieta de flujo a su lado, del tamaño del flujo que saca del aura de ese chunk. Si el chunk no tiene bastante, no abre nada. Cada grieta desgasta la hoja, y el cuchillo necesita una pausa antes de la siguiente.\n\nEn la Tabla de cortar, la comida que corta también añade flujo al aire. Se infunde a partir de un cuchillo de metal del vacío con dos cristales brutos de Vitium, Tainted Goo y una Botella de la Corrupción.");
        put("blaze_cube_focus",
                "A Blank Blaze Cube Focus, an improved focus that is not as deep as the crystal matrix one but fans out into many more branches and repeats, which suits a barrage of fire.\n\nIt is infused on an altar on top of a Greater Focus, with two blaze cubes and two crystal matrix plates.",
                "Un Foco de cubo de blaze vacío, un foco mejorado que no es tan profundo como el de matriz de cristal pero se abre en muchas más ramas y repeticiones, lo que le va bien a una lluvia de fuego.\n\nSe infunde en un altar sobre un Foco mayor, con dos cubos de blaze y dos placas de matriz de cristal.");
        put("livingwood_wand_rod",
                "The Livingwood Rod, a wand rod made of living wood from Botania. Any rod fits any cap.\n\nIt is crafted from three Livingwood Twigs at an arcane workbench, and the Dreamwood Rod is infused from it.",
                "La Vara de Madera Viviente, una vara de varita hecha con la madera viva de Botania. Cualquier vara encaja con cualquier pomo.\n\nSe fabrica con tres ramitas de madera viva en una mesa de trabajo arcana, y de ella se infunde la Vara de Madera Onírica.");
        put("dreamwood_wand_rod",
                "The Dreamwood Rod, a wand rod of dreaming wood from Botania that holds more vis than the livingwood one. Any rod fits any cap.\n\nIt is infused on an altar from a Livingwood Rod with four Dreamwood Twigs.",
                "La Vara de Madera Onírica, una vara de varita de la madera onírica de Botania que guarda más vis que la de madera viva. Cualquier vara encaja con cualquier pomo.\n\nSe infunde en un altar a partir de una Vara de Madera Viviente con cuatro ramitas de madera onírica.");
        put("manasteel_wand_cap",
                "The Manasteel Cap, a wand cap that makes every spell cheaper. Any rod fits any cap.\n\nIt is crafted from five Manasteel Ingots at an arcane workbench, and the Elementium and Terrasteel caps are infused from it.",
                "El Pomo de Manacero, un pomo de varita que abarata cada hechizo. Cualquier vara encaja con cualquier pomo.\n\nSe fabrica con cinco lingotes de manacero en una mesa de trabajo arcana, y de él se infunden los pomos de elementio y de Terracero.");
        put("elementium_wand_cap",
                "The Elementium Cap, a wand cap that makes the primal aspects of air, water, earth and fire cheaper still. Any rod fits any cap.\n\nIt is infused on an altar from a Manasteel Cap with Elementium Ingots and Pixie Dust.",
                "El Pomo de Elementio, un pomo de varita que abarata aún más los aspectos primarios de aire, agua, tierra y fuego. Cualquier vara encaja con cualquier pomo.\n\nSe infunde en un altar a partir de un Pomo de Manacero con lingotes de elementium y polvo de hada.");
        put("terrasteel_wand_cap",
                "The Terrasteel Cap, a wand cap that makes every spell cheaper than the manasteel one. Any rod fits any cap.\n\nIt is infused on an altar from a Manasteel Cap with Terrasteel Ingots and a Mana Diamond.",
                "El Pomo de Terracero, un pomo de varita que abarata cada hechizo más que el de acero de maná. Cualquier vara encaja con cualquier pomo.\n\nSe infunde en un altar a partir de un Pomo de Manacero con lingotes de terracero y un diamante de maná.");
    }
}
