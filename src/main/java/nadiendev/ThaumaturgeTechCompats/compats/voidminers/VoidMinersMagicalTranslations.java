package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.nodepower.NodeSourceKind;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class VoidMinersMagicalTranslations {
    public static final String TOOLTIP = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".voidminers.";
    public static final String WORKING = TOOLTIP + "working";
    public static final String NO_SOURCE = TOOLTIP + "no_source";
    public static final String HALTED = TOOLTIP + "halted";
    public static final String STRUCTURE_MISSING = TOOLTIP + "structure_missing";
    public static final String NO_VIEW = TOOLTIP + "no_view";
    public static final String INVENTORY_FULL = TOOLTIP + "inventory_full";
    public static final String ENERGY_FULL = TOOLTIP + "energy_full";
    public static final String SOURCE = TOOLTIP + "source";
    public static final String PROGRESS = TOOLTIP + "progress";
    public static final String ENERGY = TOOLTIP + "energy";
    public static final String GENERATION = TOOLTIP + "generation";
    public static final String HOVER_MINER_COST = TOOLTIP + "hover.miner_cost";
    public static final String HOVER_MINER_RATES = TOOLTIP + "hover.miner_rates";
    public static final String HOVER_SOLAR_COST = TOOLTIP + "hover.solar_cost";
    public static final String HOVER_SOLAR_GENERATION = TOOLTIP + "hover.solar_generation";

    public static final String JADE_AURA = "jade." + ThaumaturgeTechCompats.MOD_ID + ".voidminers.aura";
    public static final String JADE_NO_AURA = "jade." + ThaumaturgeTechCompats.MOD_ID + ".voidminers.no_aura";
    public static final String JADE_GENERATION = "jade." + ThaumaturgeTechCompats.MOD_ID + ".voidminers.generation";

    public static final String CHAT = "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".voidminers.chat.";
    public static final String CHAT_HEADER_MINER = CHAT + "header.miner";
    public static final String CHAT_HEADER_SOLAR = CHAT + "header.solar";
    public static final String CHAT_AURA = CHAT + "aura";
    public static final String CHAT_AURA_NONE = CHAT + "aura.none";
    public static final String CHAT_COST = CHAT + "cost";
    public static final String CHAT_COST_VALUE = CHAT + "cost.value";
    public static final String CHAT_DURATION = CHAT + "duration";
    public static final String CHAT_DURATION_VALUE = CHAT + "duration.value";
    public static final String CHAT_BOOST_VALUE = CHAT + "boost.value";
    public static final String CHAT_ENERGY = CHAT + "energy";
    public static final String CHAT_ENERGY_VALUE = CHAT + "energy.value";
    public static final String CHAT_UPGRADES = CHAT + "upgrades";
    public static final String CHAT_UPGRADES_MINER = CHAT + "upgrades.miner";
    public static final String CHAT_UPGRADES_SOLAR = CHAT + "upgrades.solar";
    public static final String CHAT_STOPPED = CHAT + "stopped";
    public static final String JADE_COST = "jade." + ThaumaturgeTechCompats.MOD_ID + ".voidminers.cost";
    public static final String JADE_UPGRADES_MINER = "jade." + ThaumaturgeTechCompats.MOD_ID + ".voidminers.upgrades.miner";
    public static final String JADE_UPGRADES_SOLAR = "jade." + ThaumaturgeTechCompats.MOD_ID + ".voidminers.upgrades.solar";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private VoidMinersMagicalTranslations() {
    }

    public static String sourceKey(NodeSourceKind kind) {
        return TOOLTIP + "source_kind." + kind.id();
    }

    public static String chatTierKey(VoidMinersMagicalTier tier) {
        return CHAT + "tier." + tier.id();
    }

    public static String reasonKey(MagicalPower.Failure failure) {
        return CHAT + "reason." + failure.id();
    }

    public static String jadeReasonKey(MagicalPower.Failure failure) {
        return "jade." + MOD + ".voidminers.reason." + failure.id();
    }

    private static String research(String entry) {
        return "research." + MOD + "." + entry;
    }

    public static void english(LanguageProvider lang) {
        lang.add(VoidMinersMagicalPack.PACK_KEY, "Thaumaturge Tech Compats - Void Miners tints");
        lang.add(WORKING, "Working");
        lang.add(NO_SOURCE, "No magical power source in range");
        lang.add(HALTED, "Halted");
        lang.add(STRUCTURE_MISSING, "The structure is incomplete");
        lang.add(NO_VIEW, "It cannot see bedrock or the void");
        lang.add(INVENTORY_FULL, "Not enough empty slots");
        lang.add(ENERGY_FULL, "The energy storage is full");
        lang.add(SOURCE, "Power source: %s");
        lang.add(PROGRESS, "Progress: %s%%");
        lang.add(ENERGY, "Energy: %s / %s FE");
        lang.add(GENERATION, "Generation: %s FE/t");
        lang.add(HOVER_MINER_COST, "Runs only on magical power: %s units every %s ticks of work.");
        lang.add(HOVER_MINER_RATES, "Mining time %s ticks, speed x%s, output x%s.");
        lang.add(HOVER_SOLAR_COST, "Runs only on magical power: %s units every %s ticks of work.");
        lang.add(HOVER_SOLAR_GENERATION, "Generates %s FE/t while it has a source, day or night.");
        lang.add(sourceKey(NodeSourceKind.JAR_NODE), "Jarred node");
        lang.add(sourceKey(NodeSourceKind.FLUX_JAR), "Flux Jar");
        lang.add(sourceKey(NodeSourceKind.RIFT_JAR), "Rift Jar");
        lang.add(sourceKey(NodeSourceKind.RIFT), "Flux rift");
        lang.add(sourceKey(NodeSourceKind.WORLD_NODE), "Node");
        lang.add(JADE_AURA, "Aura: %s");
        lang.add(JADE_NO_AURA, "Aura: no source");
        lang.add(CHAT_HEADER_MINER, "%s MINER");
        lang.add(CHAT_HEADER_SOLAR, "%s SOLAR PANEL");
        lang.add(chatTierKey(VoidMinersMagicalTier.THAUMIUM), "THAUMIUM");
        lang.add(chatTierKey(VoidMinersMagicalTier.ALCHEMICAL_BRASS), "ALCHEMICAL BRASS");
        lang.add(chatTierKey(VoidMinersMagicalTier.VOID_METAL), "VOID METAL");
        lang.add(CHAT_AURA, "\u00a7e\u2726 AURA: ");
        lang.add(CHAT_AURA_NONE, "None");
        lang.add(CHAT_COST, "\u00a7c\u26a1 AURA COST: ");
        lang.add(CHAT_COST_VALUE, "\u00a7f%s units / %s ticks \u00a7b(%s\u00d7)");
        lang.add(CHAT_DURATION, "\u00a79\u23f1 DURATION: ");
        lang.add(CHAT_DURATION_VALUE, "\u00a7f%s ticks \u00a7b(%s\u00d7)");
        lang.add(CHAT_BOOST_VALUE, "\u00a7f%s\u00d7");
        lang.add(CHAT_ENERGY, "\u00a7e\u26a1 ENERGY: ");
        lang.add(CHAT_ENERGY_VALUE, "%s \u00a7f%s \u00a77/ \u00a7f%s RF");
        lang.add(CHAT_UPGRADES, "\u00a7b\u2699 MAGICAL UPGRADES: ");
        lang.add(CHAT_UPGRADES_MINER, "\u00a7fspeed \u00d7%s \u00a77| \u00a7foutput \u00d7%s \u00a77| \u00a7faura cost \u00d7%s");
        lang.add(CHAT_UPGRADES_SOLAR, "\u00a7fgeneration \u00d7%s \u00a77| \u00a7faura cost \u00d7%s");
        lang.add(CHAT_STOPPED, "STOPPED");
        lang.add(reasonKey(MagicalPower.Failure.NONE), "No aura source in range.");
        lang.add(reasonKey(MagicalPower.Failure.NO_SOURCE), "No aura source in range.");
        lang.add(reasonKey(MagicalPower.Failure.HUNGRY), "A hungry node is nearby, and it is not a valid source.");
        lang.add(reasonKey(MagicalPower.Failure.EXHAUSTED), "The aura source is exhausted.");
        lang.add(jadeReasonKey(MagicalPower.Failure.NONE), "Reason: No aura source in range");
        lang.add(jadeReasonKey(MagicalPower.Failure.NO_SOURCE), "Reason: No aura source in range");
        lang.add(jadeReasonKey(MagicalPower.Failure.HUNGRY), "Reason: Hungry node nearby (not a valid source)");
        lang.add(jadeReasonKey(MagicalPower.Failure.EXHAUSTED), "Reason: The aura source is exhausted");
        lang.add(JADE_COST, "Aura cost: %s units / %s ticks (\u00d7%s)");
        lang.add(JADE_UPGRADES_MINER, "Upgrades: speed \u00d7%s, output \u00d7%s, aura cost \u00d7%s");
        lang.add(JADE_UPGRADES_SOLAR, "Upgrades: generation \u00d7%s, aura cost \u00d7%s");

        lang.add(JADE_GENERATION, "Generation: %s FE/t");
        lang.add(MagicalUpgrade.SPEED.tooltipKey(), "Mining speed x%s");
        lang.add(MagicalUpgrade.PRODUCTION.tooltipKey(), "Output x%s");
        lang.add(MagicalUpgrade.EFFICIENCY.tooltipKey(), "Aura cost x%s");
        lang.add(MagicalUpgrade.GENERATION.tooltipKey(), "Generation x%s");
        lang.add(MagicalUpgrade.SAVING.tooltipKey(), "Aura cost x%s");
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            String name = tier.english();
            lang.add("block." + MOD + "." + tier.minerBlock(), name + " Miner Controller");
            lang.add("block." + MOD + "." + tier.minerFrameBlock(), name + " Miner Frame");
            lang.add("block." + MOD + "." + tier.solarBlock(), name + " Solar Panel Controller");
            lang.add("block." + MOD + "." + tier.solarFrameBlock(), name + " Solar Panel Frame");
            VoidMinersMagicalTier previous = tier.previous();
            String from = previous == null
                    ? "a Rubetine Miner"
                    : "the " + previous.english() + " Miner Controller";
            String solarFrom = previous == null
                    ? "a Rubetine Solar Panel"
                    : "the " + previous.english() + " Solar Panel Controller";
            String extra = tier.last() ? ", a void seed" : "";
            String frameBase = previous == null ? "a base frame" : "the matching " + previous.english() + " frame";
            lang.add(research(tier.minerEntry()) + ".title", name + " Miner");
            lang.add(research(tier.minerEntry()) + ".stage_0",
                    "The miners of the void eat power until their crystals run dry. A node, a jar of flux or a rift gives far more than any crystal, if something could drink from it without choking.<BR>A miner built on " + name.toLowerCase() + " should work from one of those instead of a wire.");
            lang.add(research(tier.minerEntry()) + ".stage_1",
                    "The " + name + " Miner Controller is made on an infusion altar from " + from + ", " + name.toLowerCase() + " plates, mechanisms and Flux Jars" + extra + ".<BR>Its frames are made on an arcane workbench from " + name.toLowerCase() + " plates, obsidian and " + frameBase + ". The structure is built out of these frames, glass panels and any miner modifier, and it takes no energy at all: it works only while it can pay units to a magical power source close by, and stops the moment it cannot. A hungry node is never a valid source.");
            lang.add(research(tier.solarEntry()) + ".title", name + " Solar Panel");
            lang.add(research(tier.solarEntry()) + ".stage_0",
                    "A solar panel is only as good as the sky above it. If the aura could light it from within, neither night nor storm would matter.<BR>A panel built on " + name.toLowerCase() + " should burn from a jarred node, a Flux Jar or a rift.");
            lang.add(research(tier.solarEntry()) + ".stage_1",
                    "The " + name + " Solar Panel Controller is made on an infusion altar from " + solarFrom + ", " + name.toLowerCase() + " plates, mechanisms and jarred nodes" + extra + ".<BR>Its frames are made on an arcane workbench from " + name.toLowerCase() + " plates, obsidian and " + frameBase + ". It generates energy only while it can pay units to a magical power source close by, with no need for sun or open sky. A hungry node is never a valid source.");
            for (boolean solar : new boolean[]{false, true}) {
                String kind = solar ? " Solar Panel " : " Miner ";
                for (MagicalUpgrade upgrade : MagicalUpgrade.of(solar)) {
                    lang.add("block." + MOD + "." + upgrade.blockName(tier), name + kind + upgrade.english() + " Upgrade");
                }
                String upgrades = research(tier.upgradesEntry(solar));
                String base = solar ? "a Rubetine solar upgrade" : "a Rubetine miner upgrade";
                String chain = previous == null ? "the matching real Void Miners upgrade, " + base : "the same upgrade of the " + previous.english() + " tier";
                String target = solar ? "solar panel" : "miner";
                lang.add(upgrades + ".title", name + (solar ? " Solar Panel" : " Miner") + " Upgrades");
                lang.add(upgrades + ".stage_0",
                        "A structure has room for more than its controller. The slots left between the frames can hold blocks that bend the aura of the " + target + " a little further.<BR>Upgrades made of " + name.toLowerCase() + " should do it better than anything the crystals offer.");
                lang.add(upgrades + ".stage_1",
                        "Every " + name + " upgrade is made on an arcane workbench from " + chain + ", two " + name.toLowerCase() + " mechanisms and two plates.<BR>Place them in the free slots of the structure. Their effects multiply with each other and with the real Void Miners modifiers, and the cost ones lower the aura units the " + target + " pays to its power source.");
            }
        }
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(VoidMinersMagicalPack.PACK_KEY, "Thaumaturge Tech Compats - Tintes de Void Miners");
        lang.add(WORKING, "Trabajando");
        lang.add(NO_SOURCE, "Sin fuente de poder mágico cerca");
        lang.add(HALTED, "Detenido");
        lang.add(STRUCTURE_MISSING, "La estructura está incompleta");
        lang.add(NO_VIEW, "No ve la roca madre ni el vacío");
        lang.add(INVENTORY_FULL, "No hay suficientes huecos libres");
        lang.add(ENERGY_FULL, "El almacén de energía está lleno");
        lang.add(SOURCE, "Fuente de poder: %s");
        lang.add(PROGRESS, "Progreso: %s%%");
        lang.add(ENERGY, "Energía: %s / %s FE");
        lang.add(GENERATION, "Generación: %s FE/t");
        lang.add(HOVER_MINER_COST, "Funciona solo con poder mágico: %s unidades cada %s ticks de trabajo.");
        lang.add(HOVER_MINER_RATES, "Tiempo de minado %s ticks, velocidad x%s, producción x%s.");
        lang.add(HOVER_SOLAR_COST, "Funciona solo con poder mágico: %s unidades cada %s ticks de trabajo.");
        lang.add(HOVER_SOLAR_GENERATION, "Genera %s FE/t mientras tiene fuente, de día y de noche.");
        lang.add(sourceKey(NodeSourceKind.JAR_NODE), "Nodo en tarro");
        lang.add(sourceKey(NodeSourceKind.FLUX_JAR), "Jarra de flux");
        lang.add(sourceKey(NodeSourceKind.RIFT_JAR), "Jarra de grieta");
        lang.add(sourceKey(NodeSourceKind.RIFT), "Grieta de flux");
        lang.add(sourceKey(NodeSourceKind.WORLD_NODE), "Nodo");
        lang.add(JADE_AURA, "Aura: %s");
        lang.add(JADE_NO_AURA, "Aura: sin fuente");
        lang.add(CHAT_HEADER_MINER, "MINERO DE %s");
        lang.add(CHAT_HEADER_SOLAR, "PANEL SOLAR DE %s");
        lang.add(chatTierKey(VoidMinersMagicalTier.THAUMIUM), "TAUMIO");
        lang.add(chatTierKey(VoidMinersMagicalTier.ALCHEMICAL_BRASS), "LAT\u00d3N ALQU\u00cdMICO");
        lang.add(chatTierKey(VoidMinersMagicalTier.VOID_METAL), "METAL DEL VAC\u00cdO");
        lang.add(CHAT_AURA, "\u00a7e\u2726 AURA: ");
        lang.add(CHAT_AURA_NONE, "Ninguna");
        lang.add(CHAT_COST, "\u00a7c\u26a1 COSTE DE AURA: ");
        lang.add(CHAT_COST_VALUE, "\u00a7f%s unidades / %s ticks \u00a7b(%s\u00d7)");
        lang.add(CHAT_DURATION, "\u00a79\u23f1 DURACI\u00d3N: ");
        lang.add(CHAT_DURATION_VALUE, "\u00a7f%s ticks \u00a7b(%s\u00d7)");
        lang.add(CHAT_BOOST_VALUE, "\u00a7f%s\u00d7");
        lang.add(CHAT_ENERGY, "\u00a7e\u26a1 ENERG\u00cdA: ");
        lang.add(CHAT_ENERGY_VALUE, "%s \u00a7f%s \u00a77/ \u00a7f%s RF");
        lang.add(CHAT_UPGRADES, "\u00a7b\u2699 MEJORAS M\u00c1GICAS: ");
        lang.add(CHAT_UPGRADES_MINER, "\u00a7fvelocidad \u00d7%s \u00a77| \u00a7fproducci\u00f3n \u00d7%s \u00a77| \u00a7fcoste de aura \u00d7%s");
        lang.add(CHAT_UPGRADES_SOLAR, "\u00a7fgeneraci\u00f3n \u00d7%s \u00a77| \u00a7fcoste de aura \u00d7%s");
        lang.add(CHAT_STOPPED, "DETENIDO");
        lang.add(reasonKey(MagicalPower.Failure.NONE), "No hay fuente de aura cerca.");
        lang.add(reasonKey(MagicalPower.Failure.NO_SOURCE), "No hay fuente de aura cerca.");
        lang.add(reasonKey(MagicalPower.Failure.HUNGRY), "Hay un nodo hambriento cerca, y no sirve como fuente.");
        lang.add(reasonKey(MagicalPower.Failure.EXHAUSTED), "La fuente de aura est\u00e1 agotada.");
        lang.add(jadeReasonKey(MagicalPower.Failure.NONE), "Motivo: No hay fuente de aura cerca");
        lang.add(jadeReasonKey(MagicalPower.Failure.NO_SOURCE), "Motivo: No hay fuente de aura cerca");
        lang.add(jadeReasonKey(MagicalPower.Failure.HUNGRY), "Motivo: Nodo hambriento cerca (no sirve como fuente)");
        lang.add(jadeReasonKey(MagicalPower.Failure.EXHAUSTED), "Motivo: La fuente de aura est\u00e1 agotada");
        lang.add(JADE_COST, "Coste de aura: %s unidades / %s ticks (\u00d7%s)");
        lang.add(JADE_UPGRADES_MINER, "Mejoras: velocidad \u00d7%s, producci\u00f3n \u00d7%s, coste de aura \u00d7%s");
        lang.add(JADE_UPGRADES_SOLAR, "Mejoras: generaci\u00f3n \u00d7%s, coste de aura \u00d7%s");

        lang.add(JADE_GENERATION, "Generación: %s FE/t");
        lang.add(MagicalUpgrade.SPEED.tooltipKey(), "Velocidad de minado x%s");
        lang.add(MagicalUpgrade.PRODUCTION.tooltipKey(), "Producción x%s");
        lang.add(MagicalUpgrade.EFFICIENCY.tooltipKey(), "Coste de aura x%s");
        lang.add(MagicalUpgrade.GENERATION.tooltipKey(), "Generación x%s");
        lang.add(MagicalUpgrade.SAVING.tooltipKey(), "Coste de aura x%s");
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            String name = tier.spanish();
            lang.add("block." + MOD + "." + tier.minerBlock(), "Controlador de minero de " + name);
            lang.add("block." + MOD + "." + tier.minerFrameBlock(), "Marco de minero de " + name);
            lang.add("block." + MOD + "." + tier.solarBlock(), "Controlador de panel solar de " + name);
            lang.add("block." + MOD + "." + tier.solarFrameBlock(), "Marco de panel solar de " + name);
            VoidMinersMagicalTier previous = tier.previous();
            String from = previous == null
                    ? "un Minero de Rubetina"
                    : "el Controlador de minero de " + previous.spanish();
            String solarFrom = previous == null
                    ? "un Panel solar de Rubetina"
                    : "el Controlador de panel solar de " + previous.spanish();
            String extra = tier.last() ? ", una semilla del vacío" : "";
            String frameBase = previous == null ? "un marco base" : "el marco equivalente de " + previous.spanish();
            lang.add(research(tier.minerEntry()) + ".title", "Minero de " + name);
            lang.add(research(tier.minerEntry()) + ".stage_0",
                    "Los mineros del vacío devoran energía hasta que sus cristales se secan. Un nodo, una jarra de flux o una grieta dan mucho más que cualquier cristal, si algo pudiera beber de ellos sin atragantarse.<BR>Un minero levantado sobre " + name + " debería trabajar con uno de ellos en vez de con un cable.");
            lang.add(research(tier.minerEntry()) + ".stage_1",
                    "El Controlador de minero de " + name + " se fabrica en un altar de infusión con " + from + ", placas de " + name + ", mecanismos y Jarras de flux" + extra + ".<BR>Sus marcos se fabrican en una mesa de trabajo arcana con placas de " + name + ", obsidiana y " + frameBase + ". La estructura se levanta con esos marcos, paneles de cristal y cualquier modificador de minero, y no usa nada de energía: trabaja únicamente mientras puede pagar unidades a una fuente de poder mágico cercana, y se detiene en cuanto no puede. Un nodo hambriento nunca es una fuente válida.");
            lang.add(research(tier.solarEntry()) + ".title", "Panel solar de " + name);
            lang.add(research(tier.solarEntry()) + ".stage_0",
                    "Un panel solar vale lo que vale el cielo sobre él. Si el aura pudiera encenderlo desde dentro, ni la noche ni la tormenta importarían.<BR>Un panel levantado sobre " + name + " debería arder con un nodo en tarro, una Jarra de flux o una grieta.");
            lang.add(research(tier.solarEntry()) + ".stage_1",
                    "El Controlador de panel solar de " + name + " se fabrica en un altar de infusión con " + solarFrom + ", placas de " + name + ", mecanismos y nodos en tarros" + extra + ".<BR>Sus marcos se fabrican en una mesa de trabajo arcana con placas de " + name + ", obsidiana y " + frameBase + ". Genera energía únicamente mientras puede pagar unidades a una fuente de poder mágico cercana, sin necesitar sol ni cielo abierto. Un nodo hambriento nunca es una fuente válida.");
            for (boolean solar : new boolean[]{false, true}) {
                String kind = solar ? "panel solar" : "minero";
                for (MagicalUpgrade upgrade : MagicalUpgrade.of(solar)) {
                    lang.add("block." + MOD + "." + upgrade.blockName(tier), "Mejora de " + upgrade.spanish() + " de " + kind + " de " + name);
                }
                String upgrades = research(tier.upgradesEntry(solar));
                String base = solar ? "una mejora solar de Rubetina" : "una mejora de minero de Rubetina";
                String chain = previous == null ? "la mejora real equivalente de Void Miners, " + base : "la misma mejora del tier de " + previous.spanish();
                lang.add(upgrades + ".title", "Mejoras de " + kind + " de " + name);
                lang.add(upgrades + ".stage_0",
                        "Una estructura tiene sitio para más que su controlador. Los huecos que quedan entre los marcos pueden alojar bloques que doblen un poco más el aura del " + kind + ".<BR>Las mejoras hechas de " + name + " deberían lograrlo mejor que cualquier cosa que ofrezcan los cristales.");
                lang.add(upgrades + ".stage_1",
                        "Cada mejora de " + name + " se fabrica en una mesa de trabajo arcana con " + chain + ", dos mecanismos y dos placas de " + name + ".<BR>Colócalas en los huecos libres de la estructura. Sus efectos se multiplican entre sí y con los modificadores reales de Void Miners, y las de coste reducen las unidades de aura que el " + kind + " paga a su fuente de poder.");
            }
        }
    }
}
