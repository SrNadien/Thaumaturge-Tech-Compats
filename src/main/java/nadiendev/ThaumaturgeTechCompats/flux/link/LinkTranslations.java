package nadiendev.ThaumaturgeTechCompats.flux.link;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Locale;

public final class LinkTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String MESSAGE = "message." + MOD + ".";

    public static final String LINK_STATUS = MESSAGE + "link.status";
    public static final String LINK_PARTNER = MESSAGE + "link.partner";
    public static final String LINK_FLUX = MESSAGE + "link.flux";
    public static final String LINK_RATE = MESSAGE + "link.rate";
    public static final String EXTRACTOR_FLUX = MESSAGE + "extractor.flux";
    public static final String EXTRACTOR_RIFT = MESSAGE + "extractor.rift";
    public static final String EXTRACTOR_RIFT_FOUND = MESSAGE + "extractor.rift.found";
    public static final String EXTRACTOR_RIFT_MISSING = MESSAGE + "extractor.rift.missing";
    public static final String EXTRACTOR_RATE = MESSAGE + "extractor.rate";
    public static final String LINKER_SELECTED = MESSAGE + "linker.selected";
    public static final String LINKER_DESELECTED = MESSAGE + "linker.deselected";
    public static final String LINKER_CLEARED = MESSAGE + "linker.cleared";
    public static final String LINKER_OTHER_DIMENSION = MESSAGE + "linker.other_dimension";
    public static final String LINKER_LOST = MESSAGE + "linker.lost";
    public static final String LINKER_OUT_OF_RANGE = MESSAGE + "linker.out_of_range";
    public static final String LINKER_LINKED = MESSAGE + "linker.linked";
    public static final String LINKER_NOT_LINKED = MESSAGE + "linker.not_linked";
    public static final String LINKER_UNLINKED = MESSAGE + "linker.unlinked";
    public static final String LINKER_DISABLED = MESSAGE + "linker.disabled";
    public static final String LINKER_TOOLTIP_SELECTED = "tooltip." + MOD + ".linker.selected";
    public static final String LINKER_TOOLTIP_USE = "tooltip." + MOD + ".linker.use";
    public static final String LINKER_TOOLTIP_SNEAK = "tooltip." + MOD + ".linker.sneak";

    private static final String EXTRACTOR_ENTRY = "research." + MOD + "." + LinkContent.EXTRACTOR_ENTRY;
    private static final String LINK_ENTRY = "research." + MOD + "." + LinkContent.LINK_ENTRY;
    private static final String BRASS_ENTRY = "research." + MOD + "." + LinkContent.BRASS_ENTRY;
    private static final String VOID_ENTRY = "research." + MOD + "." + LinkContent.VOID_ENTRY;

    private LinkTranslations() {
    }

    public static String status(TransmissionLinkBlockEntity.Status status) {
        return MESSAGE + "link.status." + status.name().toLowerCase(Locale.ROOT);
    }

    public static void english(LanguageProvider lang) {
        lang.add("block." + MOD + "." + LinkRegistry.linkName(FluxTier.THAUMIUM), "Thaumium Transmission Link");
        lang.add("block." + MOD + "." + LinkRegistry.linkName(FluxTier.ALCHEMICAL_BRASS), "Alchemical Brass Transmission Link");
        lang.add("block." + MOD + "." + LinkRegistry.linkName(FluxTier.VOID_METAL), "Void Metal Transmission Link");
        lang.add("block." + MOD + "." + LinkRegistry.EXTRACTOR_NAME, "Rift Extractor");
        lang.add("item." + MOD + "." + LinkRegistry.LINKER_NAME, "Transmission Linker");

        lang.add(LINK_STATUS, "Status: %s");
        lang.add(status(TransmissionLinkBlockEntity.Status.UNLINKED), "not linked");
        lang.add(status(TransmissionLinkBlockEntity.Status.ACTIVE), "linked");
        lang.add(status(TransmissionLinkBlockEntity.Status.PARTNER_UNLOADED), "partner not loaded");
        lang.add(status(TransmissionLinkBlockEntity.Status.OUT_OF_RANGE), "out of range");
        lang.add(status(TransmissionLinkBlockEntity.Status.DISABLED), "disabled");
        lang.add(LINK_PARTNER, "Partner: %s (%s blocks, range %s)");
        lang.add(LINK_FLUX, "Dimensional Flux: %s / %s DF");
        lang.add(LINK_RATE, "Rate: %s DF per tick");
        lang.add(EXTRACTOR_FLUX, "Dimensional Flux: %s / %s DF");
        lang.add(EXTRACTOR_RIFT, "Rift in reach: %s");
        lang.add(EXTRACTOR_RIFT_FOUND, "yes");
        lang.add(EXTRACTOR_RIFT_MISSING, "no");
        lang.add(EXTRACTOR_RATE, "Takes one point from a rift for %s DF every %s ticks, up to %s blocks away");
        lang.add(LINKER_SELECTED, "Transmission Link selected at %s. Use the linker on another one to connect them.");
        lang.add(LINKER_DESELECTED, "Selection cleared.");
        lang.add(LINKER_CLEARED, "Selection cleared.");
        lang.add(LINKER_OTHER_DIMENSION, "The selected link is in another dimension. Selected the one at %s instead.");
        lang.add(LINKER_LOST, "The selected link is gone or not loaded. Selected the one at %s instead.");
        lang.add(LINKER_OUT_OF_RANGE, "Too far apart: %s blocks, the limit is %s.");
        lang.add(LINKER_LINKED, "Linked with the Transmission Link at %s.");
        lang.add(LINKER_NOT_LINKED, "This Transmission Link has no partner.");
        lang.add(LINKER_UNLINKED, "Transmission Link disconnected.");
        lang.add(LINKER_DISABLED, "Transmission Links of this tier are disabled.");
        lang.add(LINKER_TOOLTIP_SELECTED, "Selected link: %s");
        lang.add(LINKER_TOOLTIP_USE, "Use it on a Transmission Link, then on a second one to connect them.");
        lang.add(LINKER_TOOLTIP_SNEAK, "Sneak and use it on a link to disconnect it, or in the air to clear the selection.");

        lang.add(EXTRACTOR_ENTRY + ".title", "Rift Extractor");
        lang.add(EXTRACTOR_ENTRY + ".stage_0",
                "A flux rift is a wound in space, and what leaks out of it is pure energy. A jar can catch a little of it as power, but a machine could take it straight from the tear: Dimensional Flux, energy drawn from open rifts to feed advanced machinery.<BR>It would have to eat the rift as it drinks, shrinking it a little with every sip.");
        lang.add(EXTRACTOR_ENTRY + ".stage_1",
                "The Rift Extractor is made on an infusion altar from a Flux Jar, a void seed, four void metal plates and a complex arcane mechanism.<BR>It absorbs energy directly from an open flux rift within reach, taking one point of its size every so often, and turns each point into Dimensional Flux. It stops once the rift is down to its smallest size or once it has no room left to store more. The Flux it holds is pushed into the storages and Transmission Links that touch it.");

        lang.add(LINK_ENTRY + ".title", "Transmission Link");
        lang.add(LINK_ENTRY + ".stage_0",
                "Dimensional Flux is of no use where it is born. Something has to redirect and connect the beams of energy over a distance, between devices that never touch.<BR>Two pieces of the same make, each tuned to the other, should do it.");
        lang.add(LINK_ENTRY + ".stage_1",
                "The Thaumium Transmission Link is made on an arcane workbench from thaumium plates, quicksilver and a simple arcane mechanism, and the recipe gives two of them. The Transmission Linker is made from quicksilver, a thaumium plate and a simple arcane mechanism.<BR>Use the linker on one link and then on another to pair them; sneak and use it on a link to break the pair. Each link has a single partner, and the beam between them is visible. Flux flows both ways until both buffers are equally full, and a link also takes flux from the storages next to it and gives it to the ones that can receive it. Links placed side by side pass flux along, so chains are possible. The reach of a pair is the smaller of the two, and it only works while both ends are loaded.");

        lang.add(BRASS_ENTRY + ".title", "Alchemical Brass Transmission Link");
        lang.add(BRASS_ENTRY + ".stage_0",
                "Thaumium carries a trickle of Flux a short way. Brass, which holds the work of the crucible, should carry a stream much further.");
        lang.add(BRASS_ENTRY + ".stage_1",
                "The Alchemical Brass Transmission Link is made on an infusion altar from a Thaumium Transmission Link, alchemical brass plates, a simple arcane mechanism and quicksilver.<BR>It reaches farther and moves far more Flux every tick. It can be paired with a link of another tier, but the pair only reaches as far and moves only as much as the weaker of the two.");

        lang.add(VOID_ENTRY + ".title", "Void Metal Transmission Link");
        lang.add(VOID_ENTRY + ".stage_0",
                "The void does not care about distance. A link made of it should barely notice it.");
        lang.add(VOID_ENTRY + ".stage_1",
                "The Void Metal Transmission Link is made on an infusion altar from an Alchemical Brass Transmission Link, void metal plates, a complex arcane mechanism and a void seed.<BR>It has the greatest reach and the highest rate of all the links. It can be paired with a link of another tier, but the pair only reaches as far and moves only as much as the weaker of the two.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block." + MOD + "." + LinkRegistry.linkName(FluxTier.THAUMIUM), "Enlace de transmisión de taumio");
        lang.add("block." + MOD + "." + LinkRegistry.linkName(FluxTier.ALCHEMICAL_BRASS), "Enlace de transmisión de latón alquímico");
        lang.add("block." + MOD + "." + LinkRegistry.linkName(FluxTier.VOID_METAL), "Enlace de transmisión de metal del vacío");
        lang.add("block." + MOD + "." + LinkRegistry.EXTRACTOR_NAME, "Extractor de grietas");
        lang.add("item." + MOD + "." + LinkRegistry.LINKER_NAME, "Enlazador de transmisión");

        lang.add(LINK_STATUS, "Estado: %s");
        lang.add(status(TransmissionLinkBlockEntity.Status.UNLINKED), "sin enlazar");
        lang.add(status(TransmissionLinkBlockEntity.Status.ACTIVE), "enlazado");
        lang.add(status(TransmissionLinkBlockEntity.Status.PARTNER_UNLOADED), "pareja no cargada");
        lang.add(status(TransmissionLinkBlockEntity.Status.OUT_OF_RANGE), "fuera de alcance");
        lang.add(status(TransmissionLinkBlockEntity.Status.DISABLED), "desactivado");
        lang.add(LINK_PARTNER, "Pareja: %s (%s bloques, alcance %s)");
        lang.add(LINK_FLUX, "Flujo Dimensional: %s / %s DF");
        lang.add(LINK_RATE, "Ritmo: %s DF por tick");
        lang.add(EXTRACTOR_FLUX, "Flujo Dimensional: %s / %s DF");
        lang.add(EXTRACTOR_RIFT, "Grieta al alcance: %s");
        lang.add(EXTRACTOR_RIFT_FOUND, "sí");
        lang.add(EXTRACTOR_RIFT_MISSING, "no");
        lang.add(EXTRACTOR_RATE, "Toma un punto de una grieta por %s DF cada %s ticks, hasta %s bloques de distancia");
        lang.add(LINKER_SELECTED, "Enlace de transmisión seleccionado en %s. Usa el enlazador en otro para conectarlos.");
        lang.add(LINKER_DESELECTED, "Selección borrada.");
        lang.add(LINKER_CLEARED, "Selección borrada.");
        lang.add(LINKER_OTHER_DIMENSION, "El enlace seleccionado está en otra dimensión. Se ha seleccionado el de %s en su lugar.");
        lang.add(LINKER_LOST, "El enlace seleccionado ya no existe o no está cargado. Se ha seleccionado el de %s en su lugar.");
        lang.add(LINKER_OUT_OF_RANGE, "Demasiado lejos: %s bloques, el límite es %s.");
        lang.add(LINKER_LINKED, "Enlazado con el enlace de transmisión de %s.");
        lang.add(LINKER_NOT_LINKED, "Este enlace de transmisión no tiene pareja.");
        lang.add(LINKER_UNLINKED, "Enlace de transmisión desconectado.");
        lang.add(LINKER_DISABLED, "Los enlaces de transmisión de este nivel están desactivados.");
        lang.add(LINKER_TOOLTIP_SELECTED, "Enlace seleccionado: %s");
        lang.add(LINKER_TOOLTIP_USE, "Úsalo en un enlace de transmisión y luego en otro para conectarlos.");
        lang.add(LINKER_TOOLTIP_SNEAK, "Agáchate y úsalo en un enlace para desconectarlo, o al aire para borrar la selección.");

        lang.add(EXTRACTOR_ENTRY + ".title", "Extractor de grietas");
        lang.add(EXTRACTOR_ENTRY + ".stage_0",
                "Una grieta de flux es una herida en el espacio, y lo que se escapa de ella es energía pura. Una jarra puede atrapar un poco como poder, pero una máquina podría tomarla directamente del desgarro: Flujo Dimensional, energía extraída de grietas abiertas para alimentar maquinaria avanzada.<BR>Tendría que devorar la grieta mientras bebe, encogiéndola un poco con cada sorbo.");
        lang.add(EXTRACTOR_ENTRY + ".stage_1",
                "El Extractor de grietas se fabrica en un altar de infusión con una Jarra de flux, una semilla del vacío, cuatro placas de metal del vacío y un mecanismo arcano complejo.<BR>Absorbe energía directamente de una grieta de flux abierta a su alcance, quitándole un punto de tamaño cada cierto tiempo, y convierte cada punto en Flujo Dimensional. Se detiene cuando la grieta llega a su tamaño mínimo o cuando no le queda sitio para guardar más. El Flujo que guarda se empuja a los almacenes y enlaces de transmisión que lo tocan.");

        lang.add(LINK_ENTRY + ".title", "Enlace de transmisión");
        lang.add(LINK_ENTRY + ".stage_0",
                "El Flujo Dimensional no sirve de nada donde nace. Algo tiene que redirigir y conectar los haces de energía a distancia, entre dispositivos que nunca se tocan.<BR>Dos piezas de la misma hechura, cada una afinada a la otra, deberían lograrlo.");
        lang.add(LINK_ENTRY + ".stage_1",
                "El Enlace de transmisión de taumio se fabrica en una mesa de trabajo arcana con placas de taumio, mercurio y un mecanismo arcano simple, y la receta da dos. El Enlazador de transmisión se fabrica con mercurio, una placa de taumio y un mecanismo arcano simple.<BR>Usa el enlazador en un enlace y luego en otro para emparejarlos; agáchate y úsalo en un enlace para romper la pareja. Cada enlace tiene una única pareja y el haz entre ambos es visible. El flujo va en los dos sentidos hasta que ambos búferes están igual de llenos, y un enlace también toma flujo de los almacenes que tiene al lado y se lo da a los que pueden recibirlo. Los enlaces colocados uno junto a otro se pasan el flujo, así que se pueden hacer cadenas. El alcance de una pareja es el menor de los dos y solo funciona mientras ambos extremos estén cargados.");

        lang.add(BRASS_ENTRY + ".title", "Enlace de transmisión de latón alquímico");
        lang.add(BRASS_ENTRY + ".stage_0",
                "El taumio lleva un hilo de Flujo a poca distancia. El latón, que guarda el trabajo del crisol, debería llevar un chorro mucho más lejos.");
        lang.add(BRASS_ENTRY + ".stage_1",
                "El Enlace de transmisión de latón alquímico se fabrica en un altar de infusión a partir de un Enlace de transmisión de taumio, placas de latón alquímico, un mecanismo arcano simple y mercurio.<BR>Llega más lejos y mueve mucho más Flujo cada tick. Se puede emparejar con un enlace de otro nivel, pero la pareja solo llega y mueve lo que permita el más débil de los dos.");

        lang.add(VOID_ENTRY + ".title", "Enlace de transmisión de metal del vacío");
        lang.add(VOID_ENTRY + ".stage_0",
                "Al vacío no le importa la distancia. Un enlace hecho de él apenas debería notarla.");
        lang.add(VOID_ENTRY + ".stage_1",
                "El Enlace de transmisión de metal del vacío se fabrica en un altar de infusión a partir de un Enlace de transmisión de latón alquímico, placas de metal del vacío, un mecanismo arcano complejo y una semilla del vacío.<BR>Tiene el mayor alcance y el mayor ritmo de todos los enlaces. Se puede emparejar con un enlace de otro nivel, pero la pareja solo llega y mueve lo que permita el más débil de los dos.");
    }
}
