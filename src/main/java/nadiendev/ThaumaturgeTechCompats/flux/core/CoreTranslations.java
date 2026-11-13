package nadiendev.ThaumaturgeTechCompats.flux.core;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class CoreTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String TOOLTIP = "tooltip." + MOD + ".flux_core.";
    private static final String CHAT = "message." + MOD + ".flux_core.";
    private static final String JADE = "jade." + MOD + ".flux_core.";

    public static final String TOOLTIP_CELL_CAPACITY = TOOLTIP + "cell.capacity";
    public static final String TOOLTIP_CELL_PLACE = TOOLTIP + "cell.place";
    public static final String TOOLTIP_CORE_CAPACITY = TOOLTIP + "core.capacity";
    public static final String TOOLTIP_CORE_STORED = TOOLTIP + "core.stored";
    public static final String TOOLTIP_CORE_STRUCTURE = TOOLTIP + "core.structure";
    public static final String WARNING = CHAT + "warning";
    public static final String STATE_IDLE = CHAT + "state.idle";
    public static final String STATE_STABLE = CHAT + "state.stable";
    public static final String STATE_OVERLOADED = CHAT + "state.overloaded";
    public static final String STRUCTURE_FORMED = CHAT + "structure.formed";
    public static final String STRUCTURE_INCOMPLETE = CHAT + "structure.incomplete";
    public static final String HINT_RING_AND_CAPS = CHAT + "hint.ring_and_caps";
    public static final String HINT_RING = CHAT + "hint.ring";
    public static final String HINT_CAPS = CHAT + "hint.caps";
    public static final String CHAT_HEADER = CHAT + "header";
    public static final String CHAT_ENERGY = CHAT + "energy";
    public static final String CHAT_ENERGY_VALUE = CHAT + "energy.value";
    public static final String CHAT_STRUCTURE = CHAT + "structure";
    public static final String CHAT_STABILIZERS = CHAT + "stabilizers";
    public static final String CHAT_STABILIZERS_VALUE = CHAT + "stabilizers.value";
    public static final String CHAT_CELLS = CHAT + "cells";
    public static final String CHAT_CELLS_VALUE = CHAT + "cells.value";
    public static final String CHAT_STATUS = CHAT + "status";
    public static final String JADE_ENERGY = JADE + "energy";
    public static final String JADE_STRUCTURE = JADE + "structure";
    public static final String JADE_STABILIZERS = JADE + "stabilizers";
    public static final String JADE_CELLS = JADE + "cells";

    private CoreTranslations() {
    }

    public static String tierKey(FluxTier tier) {
        return TOOLTIP + "tier." + tier.id();
    }

    private static String prefix(FluxTier tier) {
        return "research." + MOD + "." + CoreContent.entry(tier);
    }

    public static void english(LanguageProvider lang) {
        lang.add(tierKey(FluxTier.THAUMIUM), "Thaumium");
        lang.add(tierKey(FluxTier.ALCHEMICAL_BRASS), "Alchemical Brass");
        lang.add(tierKey(FluxTier.VOID_METAL), "Void Metal");
        for (FluxTier tier : FluxTier.values()) {
            String name = switch (tier) {
                case THAUMIUM -> "Thaumium";
                case ALCHEMICAL_BRASS -> "Alchemical Brass";
                case VOID_METAL -> "Void Metal";
            };
            lang.add("block." + MOD + "." + CoreRegistry.coreName(tier), name + " Flux Storage Core");
            lang.add("item." + MOD + "." + CoreRegistry.cellName(tier), name + " Flux Capacity Cell");
        }

        lang.add(TOOLTIP_CELL_CAPACITY, "Adds %s DF to the capacity of a Storage Core");
        lang.add(TOOLTIP_CELL_PLACE, "Works on a pedestal within %s blocks of the core");
        lang.add(TOOLTIP_CORE_CAPACITY, "Base capacity: %s DF");
        lang.add(TOOLTIP_CORE_STORED, "Holds %s DF");
        lang.add(TOOLTIP_CORE_STRUCTURE, "Needs a ring of metal blocks around it and node stabilizers nearby");
        lang.add(WARNING, "%s is overloading! It needs more node stabilizers nearby.");
        lang.add(STATE_IDLE, "Idle");
        lang.add(STATE_STABLE, "Stable");
        lang.add(STATE_OVERLOADED, "OVERLOADED");
        lang.add(STRUCTURE_FORMED, "Formed");
        lang.add(STRUCTURE_INCOMPLETE, "Incomplete, %s blocks missing");
        lang.add(HINT_RING_AND_CAPS, "Surround it with a ring of eight %s blocks on its level and put one more above it and another below it.");
        lang.add(HINT_RING, "Surround it with a ring of eight %s blocks on its level.");
        lang.add(HINT_CAPS, "Put a %s block above it and another below it.");
        lang.add(CHAT_HEADER, "%s STORAGE CORE");
        lang.add(CHAT_ENERGY, "⚡ ENERGY: ");
        lang.add(CHAT_ENERGY_VALUE, " %s / %s DF");
        lang.add(CHAT_STRUCTURE, "▣ STRUCTURE: ");
        lang.add(CHAT_STABILIZERS, "✦ STABILIZERS: ");
        lang.add(CHAT_STABILIZERS_VALUE, "%s of %s points needed (%s stabilizers found)");
        lang.add(CHAT_CELLS, "◈ CAPACITY CELLS: ");
        lang.add(CHAT_CELLS_VALUE, "%s (+%s DF)");
        lang.add(CHAT_STATUS, "⚠ STATUS: ");
        lang.add(JADE_ENERGY, "Energy: %s / %s DF");
        lang.add(JADE_STRUCTURE, "Structure: ");
        lang.add(JADE_STABILIZERS, "Stabilizer points: %s / %s");
        lang.add(JADE_CELLS, "Capacity cells: %s (+%s DF)");

        lang.add(prefix(FluxTier.THAUMIUM) + ".title", "Flux Storage Core");
        lang.add(prefix(FluxTier.THAUMIUM) + ".stage_0",
                "Every machine of my network hungers for the same thing, and so far I have nowhere to keep it. A block of thaumium, ringed by more of its own kind, should hold a great reserve of dimensional flux without letting it slip away.<BR>But a reserve that grows too full will want to spill, and something will have to hold it still.");
        lang.add(prefix(FluxTier.THAUMIUM) + ".stage_1", stage("english", "The Thaumium Flux Storage Core and the Thaumium Flux Capacity Cell are made at the arcane workbench.", "thaumium"));
        lang.add(prefix(FluxTier.ALCHEMICAL_BRASS) + ".title", "Alchemical Brass Storage Core");
        lang.add(prefix(FluxTier.ALCHEMICAL_BRASS) + ".stage_0",
                "A bigger reserve asks for a sturdier vessel. Alchemical brass takes a stronger charge than thaumium does, and a core made of it should keep far more flux, so long as its ring is made of brass too.");
        lang.add(prefix(FluxTier.ALCHEMICAL_BRASS) + ".stage_1", stage("english", "The Alchemical Brass Flux Storage Core and the Alchemical Brass Flux Capacity Cell are made by infusion, the first from the Thaumium Flux Storage Core and the second from the Thaumium Flux Capacity Cell.", "alchemical brass"));
        lang.add(prefix(FluxTier.VOID_METAL) + ".title", "Void Metal Storage Core");
        lang.add(prefix(FluxTier.VOID_METAL) + ".stage_0",
                "There is a metal that drinks everything it is given and never fills. A core of void metal should keep a reserve beyond measure, and it will be the most dangerous of all when its stabilizers fail.");
        lang.add(prefix(FluxTier.VOID_METAL) + ".stage_1", stage("english", "The Void Metal Flux Storage Core and the Void Metal Flux Capacity Cell are made by infusion, the first from the Alchemical Brass Flux Storage Core and the second from the Alchemical Brass Flux Capacity Cell.", "void metal"));
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(tierKey(FluxTier.THAUMIUM), "Taumio");
        lang.add(tierKey(FluxTier.ALCHEMICAL_BRASS), "Latón alquímico");
        lang.add(tierKey(FluxTier.VOID_METAL), "Metal del vacío");
        for (FluxTier tier : FluxTier.values()) {
            String name = switch (tier) {
                case THAUMIUM -> "de Taumio";
                case ALCHEMICAL_BRASS -> "de Latón alquímico";
                case VOID_METAL -> "de Metal del vacío";
            };
            lang.add("block." + MOD + "." + CoreRegistry.coreName(tier), "Núcleo de almacenamiento de flujo " + name);
            lang.add("item." + MOD + "." + CoreRegistry.cellName(tier), "Celda de capacidad de flujo " + name);
        }

        lang.add(TOOLTIP_CELL_CAPACITY, "Añade %s DF a la capacidad de un Núcleo de almacenamiento");
        lang.add(TOOLTIP_CELL_PLACE, "Funciona sobre un pedestal a menos de %s bloques del núcleo");
        lang.add(TOOLTIP_CORE_CAPACITY, "Capacidad base: %s DF");
        lang.add(TOOLTIP_CORE_STORED, "Contiene %s DF");
        lang.add(TOOLTIP_CORE_STRUCTURE, "Necesita un anillo de bloques de metal alrededor y estabilizadores de nodo cerca");
        lang.add(WARNING, "¡%s se está sobrecargando! Necesita más estabilizadores de nodo cerca.");
        lang.add(STATE_IDLE, "Inactivo");
        lang.add(STATE_STABLE, "Estable");
        lang.add(STATE_OVERLOADED, "SOBRECARGADO");
        lang.add(STRUCTURE_FORMED, "Formada");
        lang.add(STRUCTURE_INCOMPLETE, "Incompleta, faltan %s bloques");
        lang.add(HINT_RING_AND_CAPS, "Ródealo con un anillo de ocho bloques de %s a su altura y pon otro encima y otro debajo.");
        lang.add(HINT_RING, "Ródealo con un anillo de ocho bloques de %s a su altura.");
        lang.add(HINT_CAPS, "Pon un bloque de %s encima y otro debajo.");
        lang.add(CHAT_HEADER, "NÚCLEO DE ALMACENAMIENTO DE %s");
        lang.add(CHAT_ENERGY, "⚡ ENERGÍA: ");
        lang.add(CHAT_ENERGY_VALUE, " %s / %s DF");
        lang.add(CHAT_STRUCTURE, "▣ ESTRUCTURA: ");
        lang.add(CHAT_STABILIZERS, "✦ ESTABILIZADORES: ");
        lang.add(CHAT_STABILIZERS_VALUE, "%s de %s puntos necesarios (%s estabilizadores encontrados)");
        lang.add(CHAT_CELLS, "◈ CELDAS DE CAPACIDAD: ");
        lang.add(CHAT_CELLS_VALUE, "%s (+%s DF)");
        lang.add(CHAT_STATUS, "⚠ ESTADO: ");
        lang.add(JADE_ENERGY, "Energía: %s / %s DF");
        lang.add(JADE_STRUCTURE, "Estructura: ");
        lang.add(JADE_STABILIZERS, "Puntos de estabilizador: %s / %s");
        lang.add(JADE_CELLS, "Celdas de capacidad: %s (+%s DF)");

        lang.add(prefix(FluxTier.THAUMIUM) + ".title", "Núcleo de almacenamiento de flujo");
        lang.add(prefix(FluxTier.THAUMIUM) + ".stage_0",
                "Todas las máquinas de mi red tienen hambre de lo mismo y hasta ahora no tengo dónde guardarlo. Un bloque de taumio, rodeado de más de los suyos, debería contener una gran reserva de flujo dimensional sin dejarla escapar.<BR>Pero una reserva que se llena demasiado querrá derramarse, y algo tendrá que mantenerla quieta.");
        lang.add(prefix(FluxTier.THAUMIUM) + ".stage_1", stage("spanish", "El Núcleo de almacenamiento de flujo de Taumio y la Celda de capacidad de flujo de Taumio se fabrican en la mesa de trabajo arcana.", "taumio"));
        lang.add(prefix(FluxTier.ALCHEMICAL_BRASS) + ".title", "Núcleo de almacenamiento de latón alquímico");
        lang.add(prefix(FluxTier.ALCHEMICAL_BRASS) + ".stage_0",
                "Una reserva mayor pide un recipiente más robusto. El latón alquímico soporta una carga más fuerte que el taumio, y un núcleo hecho con él debería guardar mucho más flujo, siempre que su anillo también sea de latón.");
        lang.add(prefix(FluxTier.ALCHEMICAL_BRASS) + ".stage_1", stage("spanish", "El Núcleo de almacenamiento de flujo de Latón alquímico y la Celda de capacidad de flujo de Latón alquímico se fabrican por infusión, el primero a partir del Núcleo de Taumio y la segunda a partir de la Celda de Taumio.", "latón alquímico"));
        lang.add(prefix(FluxTier.VOID_METAL) + ".title", "Núcleo de almacenamiento de metal del vacío");
        lang.add(prefix(FluxTier.VOID_METAL) + ".stage_0",
                "Hay un metal que bebe todo lo que se le da y nunca se llena. Un núcleo de metal del vacío debería guardar una reserva sin medida, y será el más peligroso de todos cuando fallen sus estabilizadores.");
        lang.add(prefix(FluxTier.VOID_METAL) + ".stage_1", stage("spanish", "El Núcleo de almacenamiento de flujo de Metal del vacío y la Celda de capacidad de flujo de Metal del vacío se fabrican por infusión, el primero a partir del Núcleo de Latón alquímico y la segunda a partir de la Celda de Latón alquímico.", "metal del vacío"));
    }

    private static String stage(String language, String crafting, String metal) {
        if (language.equals("spanish")) {
            return crafting
                    + "<PAGE>Estructura<DIV>El núcleo solo funciona mientras sea el centro de un anillo de ocho bloques de " + metal
                    + " a su altura, con otro bloque de " + metal + " justo encima y otro justo debajo. Los bloques de un tier superior también valen. Mientras no esté formado ni guarda ni transmite flujo. Haz clic derecho sobre él para ver su estado."
                    + "<PAGE>Celdas y estabilizadores<DIV>Las Celdas de capacidad puestas en pedestales, del tipo que sean, a menos de seis bloques del núcleo aumentan su capacidad. Los estabilizadores de nodo a menos de ocho bloques lo mantienen en calma: cuanto más lleno está, más puntos de estabilizador necesita. Por encima de la mitad y sin puntos suficientes se sobrecarga: suelta flujo al aura, lanza chispas y daña a quien esté cerca. Las distancias, las cifras y los efectos se pueden cambiar en la configuración.";
        }
        return crafting
                + "<PAGE>Structure<DIV>The core only works while it is the centre of a ring of eight " + metal
                + " blocks on its own level, with one more " + metal + " block right above it and another right below it. Blocks of a higher tier count too. Until it is formed it neither stores nor passes on any flux. Right-click it to see its state."
                + "<PAGE>Cells and stabilizers<DIV>Capacity Cells placed on pedestals, of any kind, within six blocks of the core raise its capacity. Node stabilizers within eight blocks keep it calm: the fuller it is, the more stabilizer points it needs. Above half full and short of points, it overloads: it releases flux into the aura, throws sparks and hurts whoever stands near. The distances, figures and effects can be changed in the configuration.";
    }
}
