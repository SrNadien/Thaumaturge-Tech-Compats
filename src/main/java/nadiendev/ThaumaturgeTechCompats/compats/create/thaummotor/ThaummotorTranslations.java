package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ThaummotorTranslations {
    public static final String TOOLTIP_TITLE = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".thaummotor.title";
    public static final String TOOLTIP_SOURCE = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".thaummotor.source";
    public static final String TOOLTIP_NO_SOURCE = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".thaummotor.no_source";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String ENTRY = "research." + MOD + "." + ThaummotorContent.ENTRY;

    private ThaummotorTranslations() {
    }

    public static String sourceKey(NodeSourceKind kind) {
        return TOOLTIP_SOURCE + "." + kind.id();
    }

    public static void english(LanguageProvider lang) {
        lang.add("block." + MOD + "." + ThaummotorRegistry.NAME, "Thaummotor");
        lang.add(TOOLTIP_TITLE, "Thaummotor:");
        lang.add(TOOLTIP_SOURCE, "Powered by %s");
        lang.add(TOOLTIP_NO_SOURCE, "No power source nearby");
        lang.add(sourceKey(NodeSourceKind.JAR_NODE), "a jarred node");
        lang.add(sourceKey(NodeSourceKind.FLUX_JAR), "a Flux Jar");
        lang.add(sourceKey(NodeSourceKind.RIFT_JAR), "a Rift Jar");
        lang.add(sourceKey(NodeSourceKind.RIFT), "a flux rift");
        lang.add(sourceKey(NodeSourceKind.WORLD_NODE), "a node");

        lang.add(ENTRY + ".title", "Thaummotor");
        lang.add(ENTRY + ".stage_0",
                "A node pours out more power than any hand crank ever could, and it does so without asking for water or wind. A motor of brass and thaumium that drinks that power should turn a shaft by itself.<BR>It would have to take what it needs from a source standing close by, and a hungry node is never a safe one.");
        lang.add(ENTRY + ".stage_1",
                "The Thaummotor is made on an arcane workbench from a brass casing, two shafts, two simple mechanisms and four thaumium plates.<BR>While a jarred node, a node, a Flux Jar or a flux rift stands within reach, it takes a little power from it every so often and turns at a fixed speed, providing stress to the whole kinetic network. The richer the source, the more stress it provides, and with no source, or no power left in it, it stops. A hungry node is never a valid source. The goggles of Create tell which source it is using.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block." + MOD + "." + ThaummotorRegistry.NAME, "Taummotor");
        lang.add(TOOLTIP_TITLE, "Taummotor:");
        lang.add(TOOLTIP_SOURCE, "Alimentado por %s");
        lang.add(TOOLTIP_NO_SOURCE, "Sin fuente de poder cerca");
        lang.add(sourceKey(NodeSourceKind.JAR_NODE), "un nodo en frasco");
        lang.add(sourceKey(NodeSourceKind.FLUX_JAR), "una Jarra de flux");
        lang.add(sourceKey(NodeSourceKind.RIFT_JAR), "una Jarra de grieta");
        lang.add(sourceKey(NodeSourceKind.RIFT), "una grieta de flux");
        lang.add(sourceKey(NodeSourceKind.WORLD_NODE), "un nodo");

        lang.add(ENTRY + ".title", "Taummotor");
        lang.add(ENTRY + ".stage_0",
                "Un nodo derrama más poder del que cualquier manivela podría dar, y lo hace sin pedir agua ni viento. Un motor de latón y taumio que beba ese poder debería poder hacer girar un eje por sí solo.<BR>Tendría que tomar lo que necesita de una fuente cercana, y un nodo hambriento nunca es una fuente segura.");
        lang.add(ENTRY + ".stage_1",
                "El Taummotor se fabrica en una mesa de trabajo arcana con una carcasa de latón, dos ejes, dos mecanismos simples y cuatro placas de taumio.<BR>Mientras haya un nodo en frasco, un nodo, una Jarra de flux o una grieta de flux a su alcance, toma de ella un poco de poder cada cierto tiempo y gira a una velocidad fija, dando estrés a toda la red cinética. Cuanto más rica es la fuente, más estrés da, y sin fuente, o sin poder en ella, se detiene. Un nodo hambriento nunca es una fuente válida. Las gafas de Create indican qué fuente está usando.");
    }
}
