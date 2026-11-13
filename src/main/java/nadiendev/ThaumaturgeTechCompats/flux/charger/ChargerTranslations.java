package nadiendev.ThaumaturgeTechCompats.flux.charger;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ChargerTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String MESSAGE = EnvironmentalChargerBlock.MESSAGE;
    private static final String TOOLTIP = ChargerItem.TOOLTIP;

    private ChargerTranslations() {
    }

    private static String block(FluxTier tier) {
        return "block." + MOD + "." + ChargerRegistry.blockName(tier);
    }

    private static String entry(FluxTier tier) {
        return "research." + MOD + "." + ChargerContent.research(tier);
    }

    public static void english(LanguageProvider lang) {
        lang.add(block(FluxTier.THAUMIUM), "Thaumium Environmental Charger");
        lang.add(block(FluxTier.ALCHEMICAL_BRASS), "Alchemical Brass Environmental Charger");
        lang.add(block(FluxTier.VOID_METAL), "Void Metal Environmental Charger");
        lang.add(MESSAGE + "status", "%s: %s");
        lang.add(MESSAGE + "status.empty", "no Dimensional Flux");
        lang.add(MESSAGE + "status.idle", "nothing to recharge nearby");
        lang.add(MESSAGE + "status.charging", "recharging");
        lang.add(MESSAGE + "flux", "Dimensional Flux: %s / %s DF");
        lang.add(MESSAGE + "area", "Radius: %s blocks, %s DF per item every tick");
        lang.add(TOOLTIP + "description", "Releases energy in a nearby area to automatically recharge the objects and tools you carry in your inventory.");
        lang.add(TOOLTIP + "area", "Radius: %s blocks, %s DF per item every tick");
        lang.add(TOOLTIP + "capacity", "Stores up to %s DF");

        lang.add(entry(FluxTier.THAUMIUM) + ".title", "Environmental Charger");
        lang.add(entry(FluxTier.THAUMIUM) + ".stage_0",
                "A recharge pedestal gives one thing its fill and then asks me to carry it back. Dimensional Flux, once it flows through my network, should not need such a ritual.<BR>A block that gives it away into the air around it could recharge everything I carry without me ever taking anything out of my pack.");
        lang.add(entry(FluxTier.THAUMIUM) + ".stage_1",
                "The Thaumium Environmental Charger is made on an arcane workbench from seven thaumium plates, a vis resonator and a simple mechanism.<BR>It releases energy in a nearby area to automatically recharge the objects and tools you carry in your inventory. It keeps its own store of Dimensional Flux, which transmission links, storage cores and extractors fill by pushing into it, and every few ticks it gives a share to each charged item, wand and vis item of every player in reach. Right click it to read its state, its radius and its rate.");

        lang.add(entry(FluxTier.ALCHEMICAL_BRASS) + ".title", "Alchemical Brass Environmental Charger");
        lang.add(entry(FluxTier.ALCHEMICAL_BRASS) + ".stage_0",
                "Thaumium reaches little and gives little. A frame of alchemical brass should cast the same field much farther and much faster.");
        lang.add(entry(FluxTier.ALCHEMICAL_BRASS) + ".stage_1",
                "The Alchemical Brass Environmental Charger is made on an infusion altar from a Thaumium Environmental Charger, four brass plates, a simple mechanism and a vis resonator.<BR>It works like the thaumium one, only with a wider radius, a higher rate and a bigger store.");

        lang.add(entry(FluxTier.VOID_METAL) + ".title", "Void Metal Environmental Charger");
        lang.add(entry(FluxTier.VOID_METAL) + ".stage_0",
                "The void swallows whatever it is given. If I teach it to give back instead, a charger of void metal should fill whatever stands in its field before it has time to notice.");
        lang.add(entry(FluxTier.VOID_METAL) + ".stage_1",
                "The Void Metal Environmental Charger is made on an infusion altar from an Alchemical Brass Environmental Charger, four void metal plates, a complex mechanism and two vis resonators.<BR>It works like the others, with the widest radius, the highest rate and the biggest store.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(block(FluxTier.THAUMIUM), "Cargador ambiental de taumio");
        lang.add(block(FluxTier.ALCHEMICAL_BRASS), "Cargador ambiental de latón alquímico");
        lang.add(block(FluxTier.VOID_METAL), "Cargador ambiental de metal del vacío");
        lang.add(MESSAGE + "status", "%s: %s");
        lang.add(MESSAGE + "status.empty", "sin Flujo Dimensional");
        lang.add(MESSAGE + "status.idle", "nada que recargar cerca");
        lang.add(MESSAGE + "status.charging", "recargando");
        lang.add(MESSAGE + "flux", "Flujo Dimensional: %s / %s DF");
        lang.add(MESSAGE + "area", "Radio: %s bloques, %s DF por objeto en cada tick");
        lang.add(TOOLTIP + "description", "Libera energía en un área cercana para recargar automáticamente los objetos y herramientas que llevas en tu inventario.");
        lang.add(TOOLTIP + "area", "Radio: %s bloques, %s DF por objeto en cada tick");
        lang.add(TOOLTIP + "capacity", "Almacena hasta %s DF");

        lang.add(entry(FluxTier.THAUMIUM) + ".title", "Cargador ambiental");
        lang.add(entry(FluxTier.THAUMIUM) + ".stage_0",
                "Un pedestal de recarga llena una sola cosa y luego me pide que la lleve de vuelta. El Flujo Dimensional, una vez que corra por mi red, no debería necesitar semejante ritual.<BR>Un bloque que lo regale al aire que lo rodea podría recargar todo lo que llevo encima sin que yo saque nada de mi mochila.");
        lang.add(entry(FluxTier.THAUMIUM) + ".stage_1",
                "El Cargador ambiental de taumio se fabrica en una mesa de trabajo arcana con siete placas de taumio, un resonador de vis y un mecanismo simple.<BR>Libera energía en un área cercana para recargar automáticamente los objetos y herramientas que llevas en tu inventario. Guarda su propio almacén de Flujo Dimensional, que los enlaces de transmisión, los núcleos de almacenamiento y los extractores llenan empujando hacia él, y cada pocos ticks da una parte a cada objeto con carga, varita u objeto de vis de todos los jugadores a su alcance. Haz clic derecho para leer su estado, su radio y su ritmo.");

        lang.add(entry(FluxTier.ALCHEMICAL_BRASS) + ".title", "Cargador ambiental de latón alquímico");
        lang.add(entry(FluxTier.ALCHEMICAL_BRASS) + ".stage_0",
                "El taumio llega poco y da poco. Un armazón de latón alquímico debería lanzar el mismo campo mucho más lejos y mucho más rápido.");
        lang.add(entry(FluxTier.ALCHEMICAL_BRASS) + ".stage_1",
                "El Cargador ambiental de latón alquímico se fabrica en un altar de infusión con un Cargador ambiental de taumio, cuatro placas de latón, un mecanismo simple y un resonador de vis.<BR>Funciona como el de taumio, solo que con más radio, más ritmo y un almacén mayor.");

        lang.add(entry(FluxTier.VOID_METAL) + ".title", "Cargador ambiental de metal del vacío");
        lang.add(entry(FluxTier.VOID_METAL) + ".stage_0",
                "El vacío se traga todo lo que se le da. Si le enseño a devolver en vez de tragar, un cargador de metal del vacío debería llenar todo lo que pise su campo antes de que le dé tiempo a darse cuenta.");
        lang.add(entry(FluxTier.VOID_METAL) + ".stage_1",
                "El Cargador ambiental de metal del vacío se fabrica en un altar de infusión con un Cargador ambiental de latón alquímico, cuatro placas de metal del vacío, un mecanismo complejo y dos resonadores de vis.<BR>Funciona como los demás, con el mayor radio, el mayor ritmo y el mayor almacén.");
    }
}
