package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class MekanismToolTranslations {
    public static final String TOOLTIP = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".mekanism_tools.";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private MekanismToolTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (MekanismToolTier tier : MekanismToolTier.values()) {
            for (MekanismToolKind kind : MekanismToolKind.values()) {
                lang.add("item." + MOD + "." + MekanismToolRegistry.itemId(tier, kind), "Energized " + tier.english() + " " + kind.english());
            }
        }
        lang.add(TOOLTIP + "energy", "Energy: %s");
        lang.add(TOOLTIP + "empty", "Out of energy: it does nothing until it is charged");
        lang.add(TOOLTIP + "block_cost", "Mining: %s per block");
        lang.add(TOOLTIP + "attack_cost", "Fighting: %s per hit");
        lang.add(TOOLTIP + "use_cost", "Stripping, flattening and tilling: %s per use");

        entry(lang, MekanismToolTier.THAUMIUM, "Energized Thaumium Tools",
                "The thaumium tools never tire, but they wear out all the same, and a tool that wears out has to be mended or thrown away. The machines of Mekanism do not wear out: they feed on a stored current and ask for nothing else. I wonder whether a tool of thaumium can be taught to drink from the same well.",
                "The plan is simple enough. A normal thaumium tool, two energy tablets, a reinforced alloy, an advanced control circuit and two thaumium plates, all together on an infusion altar.<PAGE>Energized Thaumium Tools<DIV>The infusion altar gives me an energized thaumium pickaxe, axe, shovel, hoe or sword from the matching normal tool. They no longer lose durability. They hold energy, and every block mined, every hit on a creature and every stripped, flattened or tilled block spends some of it. They are charged in an energy cube, a charge pad, a charging slot of any Mekanism machine or with any other source of energy, and the charge bar under the item shows what is left. With no energy left they mine like bare hands, drop nothing a tool should drop and hit like a fist, until I charge them again.<PAGE>They are the first of the energized tools: they hold the least energy, spend the most of it and work the slowest, and the alchemical brass ones improve on them. The capacity, the cost of every action, the mining speed and the damage can be changed in the configuration.");
        entry(lang, MekanismToolTier.BRASS, "Energized Alchemical Brass Tools",
                "The energized thaumium tools drink from the well, but only a little at a time. Brass is the metal that listens best to the aura, and a plain iron tool bound with alchemical brass, with the thaumium tool I already have as its first draught, might be taught to drink deeper.",
                "The plan is simple enough. A normal iron tool, the energized thaumium tool of the same kind, two energy tablets, an infused alloy, a basic control circuit and two alchemical brass plates, all together on an infusion altar.<PAGE>Energized Alchemical Brass Tools<DIV>The infusion altar gives me an energized alchemical brass pickaxe, axe, shovel, hoe or sword from the matching iron tool, and the energized thaumium tool is consumed in the process. They work like the thaumium ones: they lose no durability, they hold energy and every block mined, every hit and every stripped, flattened or tilled block spends some of it. With no energy left they are as useless as bare hands.<PAGE>They hold more energy, spend less of it and work faster than the thaumium ones. The capacity, the cost of every action, the mining speed and the damage can be changed in the configuration.");
        entry(lang, MekanismToolTier.VOID, "Energized Void Metal Tools",
                "Void metal mends itself, which is the one thing a machine will never do. Yet a void tool that is also fed by a current should never be found wanting, neither by wear nor by hunger.",
                "A normal void metal tool, the energized alchemical brass tool of the same kind, two energy tablets, an atomic alloy, an ultimate control circuit and two void metal plates, on the infusion altar.<PAGE>Energized Void Metal Tools<DIV>These tools come from the normal void metal ones, and the energized alchemical brass tool is consumed in the process. They work like the brass ones, with a far larger store of energy, a lower cost for every action, a faster mining speed and a stronger blow. They also lose no durability and they too are useless once the energy is gone. They are made in the infusion altar one by one: pickaxe, axe, shovel, hoe and sword.<PAGE>The capacity, the cost of every action, the mining speed and the damage can be changed in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        for (MekanismToolTier tier : MekanismToolTier.values()) {
            for (MekanismToolKind kind : MekanismToolKind.values()) {
                lang.add("item." + MOD + "." + MekanismToolRegistry.itemId(tier, kind),
                        kind.spanish() + " de " + tier.spanish() + (kind.feminine() ? " energizada" : " energizado"));
            }
        }
        lang.add(TOOLTIP + "energy", "Energía: %s");
        lang.add(TOOLTIP + "empty", "Sin energía: no sirve de nada hasta que se cargue");
        lang.add(TOOLTIP + "block_cost", "Minar: %s por bloque");
        lang.add(TOOLTIP + "attack_cost", "Combatir: %s por golpe");
        lang.add(TOOLTIP + "use_cost", "Descortezar, aplanar y labrar: %s por uso");

        entry(lang, MekanismToolTier.THAUMIUM, "Herramientas de taumio energizadas",
                "Las herramientas de taumio no se cansan, pero se desgastan igual, y una herramienta que se desgasta hay que repararla o tirarla. Las máquinas de Mekanism no se desgastan: se alimentan de una corriente almacenada y no piden nada más. Me pregunto si una herramienta de taumio puede aprender a beber del mismo pozo.",
                "El plan es bastante sencillo. Una herramienta de taumio normal, dos tabletas de energía, una aleación reforzada, un circuito de control avanzado y dos placas de taumio, todo junto en un altar de infusión.<PAGE>Herramientas de taumio energizadas<DIV>El altar de infusión me da un pico, un hacha, una pala, una azada o una espada de taumio energizados a partir de la herramienta normal correspondiente. Ya no pierden durabilidad. Guardan energía, y cada bloque minado, cada golpe a una criatura y cada bloque descortezado, aplanado o labrado gasta un poco. Se cargan en un cubo de energía, una plataforma de carga, la ranura de carga de cualquier máquina de Mekanism o con cualquier otra fuente de energía, y la barra de carga bajo el objeto muestra lo que queda. Sin energía minan como las manos desnudas, no sueltan nada de lo que debería soltar una herramienta y golpean como un puño, hasta que las cargue de nuevo.<PAGE>Son las primeras de las herramientas energizadas: guardan la menor cantidad de energía, gastan más y trabajan más despacio, y las de latón alquímico las mejoran. La capacidad, el coste de cada acción, la velocidad de minado y el daño se pueden cambiar en la configuración.");
        entry(lang, MekanismToolTier.BRASS, "Herramientas de latón alquímico energizadas",
                "Las herramientas de taumio energizadas beben del pozo, pero solo un poco cada vez. El latón es el metal que mejor escucha al aura, y una herramienta de hierro corriente, ligada con latón alquímico y con la herramienta de taumio que ya tengo como primer trago, quizá aprenda a beber más hondo.",
                "El plan es bastante sencillo. Una herramienta de hierro normal, la herramienta de taumio energizada del mismo tipo, dos tabletas de energía, una aleación infundida, un circuito de control básico y dos placas de latón alquímico, todo junto en un altar de infusión.<PAGE>Herramientas de latón alquímico energizadas<DIV>El altar de infusión me da un pico, un hacha, una pala, una azada o una espada de latón alquímico energizados a partir de la herramienta de hierro correspondiente, y la herramienta de taumio energizada se consume en el proceso. Funcionan como las de taumio: no pierden durabilidad, guardan energía y cada bloque minado, cada golpe y cada bloque descortezado, aplanado o labrado gasta un poco. Sin energía son tan inútiles como las manos desnudas.<PAGE>Guardan más energía, gastan menos y trabajan más rápido que las de taumio. La capacidad, el coste de cada acción, la velocidad de minado y el daño se pueden cambiar en la configuración.");
        entry(lang, MekanismToolTier.VOID, "Herramientas de metal del vacío energizadas",
                "El metal del vacío se repara solo, que es lo único que una máquina nunca hará. Aun así, una herramienta del vacío alimentada también por una corriente no debería quedarse corta jamás, ni por desgaste ni por hambre.",
                "Una herramienta de metal del vacío normal, la herramienta de latón alquímico energizada del mismo tipo, dos tabletas de energía, una aleación atómica, un circuito de control definitivo y dos placas de metal del vacío, en el altar de infusión.<PAGE>Herramientas de metal del vacío energizadas<DIV>Estas herramientas nacen de las normales de metal del vacío, y la herramienta de latón alquímico energizada se consume en el proceso. Funcionan como las de latón, con una reserva de energía mucho mayor, un coste menor por cada acción, una velocidad de minado más alta y un golpe más fuerte. Tampoco pierden durabilidad y también quedan inútiles cuando se acaba la energía. Se hacen una a una en el altar de infusión: pico, hacha, pala, azada y espada.<PAGE>La capacidad, el coste de cada acción, la velocidad de minado y el daño se pueden cambiar en la configuración.");
    }

    private static void entry(LanguageProvider lang, MekanismToolTier tier, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + MekanismToolContent.entryName(tier);
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
