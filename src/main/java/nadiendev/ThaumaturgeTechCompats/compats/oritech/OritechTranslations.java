package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import net.neoforged.neoforge.common.data.LanguageProvider;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;

public final class OritechTranslations {
    private OritechTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (OritechNode node : OritechNode.values()) {
            lang.add("block." + MOD + "." + node.blockName(), node.englishName() + " Resource Node");
        }
        for (OritechTier tier : OritechTier.values()) {
            for (OritechAddon addon : OritechAddon.values()) {
                lang.add("block." + MOD + "." + addon.blockName(tier), tier.englishName() + " " + addon.englishName());
            }
        }

        entry(lang, OritechContent.NODES_RESEARCH, "Resource Nodes of Thaumaturge",
                "The engineers of Oritech found veins that never run dry: nodes of bedrock that a deep drill can bleed forever. If the same trick works with cinnabar, amber and quartz, I will never have to dig for them again.",
                "On the infusion altar, a resource node of Oritech surrounded by the ore and the product of the material becomes a node of that material:<BR>- Cinnabar (from the Redstone node), Amber (Gold node) and Quartz (Lapis node), with the Thaumaturge ore and its drop.<BR>Under Oritech's Deep Drill each node gives its material forever: raw cinnabar, amber or quartz. They also generate in the overworld like the nodes of Oritech, with a boulder of deepslate on the surface.<BR>The amount per cycle and how rare each node is can be changed in the configuration, and every node can be turned off there.");

        entry(lang, OritechContent.addonsResearch(OritechTier.BRASS), "Brass Machine Addons",
                "The addons of Oritech make a machine faster, thriftier or hungrier for storage, but their plating is only plating. Brass plates and a simple mechanism should carry the current with fewer losses.",
                "Each addon is built at the arcane workbench around the Oritech addon of the same kind, with brass plates, simple mechanisms and a component of Oritech:<BR>- Speed: processes in 40% of the time (Oritech's addon: 50%) and spends 110% of the energy.<BR>- Efficiency: spends 60% of the energy (Oritech's addon: 80%).<BR>- Capacitor: adds 10,000,000 of capacity and 10,000 of insert rate to the machine.<BR>They are placed against the machine like any other addon. Every value can be changed in the configuration.");
        entry(lang, OritechContent.addonsResearch(OritechTier.THAUMIUM), "Thaumium Machine Addons",
                "Thaumium holds a charge of magic that brass cannot. An addon made of it, with a complex mechanism, should push a machine well past what brass allows.",
                "Each addon is built around the brass addon of the same kind, with thaumium plates, complex mechanisms and an overcharged crystal:<BR>- Speed: processes in 20% of the time and spends 100% of the energy.<BR>- Efficiency: spends 40% of the energy.<BR>- Capacitor: adds 100,000,000 of capacity and 100,000 of insert rate.");
        entry(lang, OritechContent.addonsResearch(OritechTier.VOID), "Void Metal Machine Addons",
                "Void metal gives nothing back to the world, and that includes losses. An addon made of it should be as close as a machine can get to working for free.",
                "Each addon is built around the thaumium addon of the same kind, with void metal plates, complex mechanisms and a Heisenberg compensator:<BR>- Speed: processes in 10% of the time and spends 90% of the energy.<BR>- Efficiency: spends 20% of the energy.<BR>- Capacitor: adds 1,000,000,000 of capacity and 1,000,000 of insert rate.");
    }

    public static void spanish(LanguageProvider lang) {
        for (OritechNode node : OritechNode.values()) {
            lang.add("block." + MOD + "." + node.blockName(), "Nodo de recurso de " + node.spanishName());
        }
        for (OritechTier tier : OritechTier.values()) {
            for (OritechAddon addon : OritechAddon.values()) {
                lang.add("block." + MOD + "." + addon.blockName(tier), addon.spanishName() + " " + tier.spanishSuffix());
            }
        }

        entry(lang, OritechContent.NODES_RESEARCH, "Nodos de recurso de Thaumaturge",
                "Los ingenieros de Oritech encontraron vetas que nunca se agotan: nodos de roca madre que un taladro profundo puede sangrar para siempre. Si el mismo truco funciona con el cinabrio, el ámbar y el cuarzo, no tendré que volver a picarlos.",
                "En el altar de infusión, un nodo de recurso de Oritech rodeado de la mena y del producto del material se convierte en un nodo de ese material:<BR>- Cinabrio (desde el nodo de redstone), ámbar (nodo de oro) y cuarzo (nodo de lapislázuli), con la mena de Thaumaturge y su drop.<BR>Bajo el Taladro Profundo de Oritech cada nodo da su material para siempre: cinabrio en bruto, ámbar o cuarzo. También se generan en el overworld como los nodos de Oritech, con un peñasco de pizarra en la superficie.<BR>La cantidad por ciclo y lo raro que es cada nodo se pueden cambiar en la configuración, y cada nodo se puede desactivar allí.");

        entry(lang, OritechContent.addonsResearch(OritechTier.BRASS), "Mejoras de máquina de latón",
                "Las mejoras de Oritech hacen una máquina más rápida, más frugal o más hambrienta de almacenamiento, pero su revestimiento es solo revestimiento. Placas de latón y un mecanismo simple deberían llevar la corriente con menos pérdidas.",
                "Cada mejora se construye en la mesa de trabajo arcana alrededor de la mejora de Oritech del mismo tipo, con placas de latón, mecanismos simples y un componente de Oritech:<BR>- Velocidad: procesa en el 40% del tiempo (la mejora de Oritech: 50%) y gasta el 110% de la energía.<BR>- Eficiencia: gasta el 60% de la energía (la mejora de Oritech: 80%).<BR>- Capacitor: añade 10.000.000 de capacidad y 10.000 de velocidad de carga a la máquina.<BR>Se colocan junto a la máquina como cualquier otra mejora. Todos los valores se pueden cambiar en la configuración.");
        entry(lang, OritechContent.addonsResearch(OritechTier.THAUMIUM), "Mejoras de máquina de taumio",
                "El taumio guarda una carga de magia que el latón no puede. Una mejora hecha con él, con un mecanismo complejo, debería llevar una máquina mucho más allá de lo que permite el latón.",
                "Cada mejora se construye alrededor de la mejora de latón del mismo tipo, con placas de taumio, mecanismos complejos y un cristal sobrecargado:<BR>- Velocidad: procesa en el 20% del tiempo y gasta el 100% de la energía.<BR>- Eficiencia: gasta el 40% de la energía.<BR>- Capacitor: añade 100.000.000 de capacidad y 100.000 de velocidad de carga.");
        entry(lang, OritechContent.addonsResearch(OritechTier.VOID), "Mejoras de máquina de metal del vacío",
                "El metal del vacío no devuelve nada al mundo, y eso incluye las pérdidas. Una mejora hecha con él debería acercar una máquina todo lo posible a trabajar gratis.",
                "Cada mejora se construye alrededor de la mejora de taumio del mismo tipo, con placas de metal del vacío, mecanismos complejos y un compensador de Heisenberg:<BR>- Velocidad: procesa en el 10% del tiempo y gasta el 90% de la energía.<BR>- Eficiencia: gasta el 20% de la energía.<BR>- Capacitor: añade 1.000.000.000 de capacidad y 1.000.000 de velocidad de carga.");
    }

    private static void entry(LanguageProvider lang, String name, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + name;
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
