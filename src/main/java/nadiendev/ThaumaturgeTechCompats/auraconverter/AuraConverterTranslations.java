package nadiendev.ThaumaturgeTechCompats.auraconverter;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.auraconverter.gui.AuraConverterMenu;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.text.NumberFormat;
import java.util.Locale;

public final class AuraConverterTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final Locale SPANISH = Locale.GERMANY;

    private AuraConverterTranslations() {
    }

    private static String number(long value, Locale locale) {
        return NumberFormat.getIntegerInstance(locale).format(value);
    }

    private static String gui(String key) {
        return AuraConverterMenu.KEY + key;
    }

    public static void english(LanguageProvider lang) {
        lang.add("block." + MOD + ".alchemical_brass_aura_converter", "Alchemical Brass Aura Converter");
        lang.add("block." + MOD + ".thaumium_aura_converter", "Thaumium Aura Converter");
        lang.add("block." + MOD + ".void_metal_aura_converter", "Void Metal Aura Converter");
        lang.add(AuraConverterItem.TOOLTIP_RATE, "Converts every vis into %s FE and gives out up to %s FE/t");
        lang.add(AuraConverterItem.TOOLTIP_BUFFER, "Buffer: %s FE");
        lang.add(AuraConverterItem.TOOLTIP_FLUX, "Every vis converted adds %s flux to the aura");
        lang.add(gui("state"), "State: %s");
        lang.add(gui("stored"), "Stored: %s FE");
        lang.add(gui("aura"), "Aura: %s / %s vis");
        lang.add(gui("converting"), "Converting: %s FE/t");
        lang.add(gui("output"), "Output: %s FE/t");
        lang.add(gui("status.running"), "Converting");
        lang.add(gui("status.running.desc"), "The converter is draining vis from the aura of the chunk and storing it as energy.");
        lang.add(gui("status.paused"), "Paused");
        lang.add(gui("status.paused.desc"), "A redstone signal has stopped the conversion. The stored energy can still be taken out.");
        lang.add(gui("status.full"), "Buffer full");
        lang.add(gui("status.full.desc"), "The buffer is full. The conversion goes on as the energy is taken out.");
        lang.add(gui("status.no_vis"), "No vis");
        lang.add(gui("status.no_vis.desc"), "The aura of the chunk is down to the part that is never drained, so there is no vis to take.");
        lang.add(gui("status.chunk_limit"), "Chunk limit");
        lang.add(gui("status.chunk_limit.desc"), "The converters of this chunk have already drained the most vis they can take together this tick.");
        lang.add(gui("status.disabled"), "Disabled");
        lang.add(gui("status.disabled.desc"), "This converter is turned off in the configuration.");
        lang.add(gui("tooltip.energy.title"), "Stored energy");
        lang.add(gui("tooltip.energy.amount"), "%s / %s FE");
        lang.add(gui("tooltip.energy.output"), "Gives out up to %s FE per tick");
        lang.add(gui("tooltip.aura.title"), "Aura of the chunk");
        lang.add(gui("tooltip.aura.amount"), "%s / %s vis");
        lang.add(gui("tooltip.aura.floor"), "Never drained under %s vis");
        lang.add(gui("tooltip.aura.rate"), "Every vis becomes %s FE");
        entry(lang, AuraConverterTier.THAUMIUM, "Aura Converter",
                "The aura is a store of power that nobody has learned to tap without a wand or a machine of the arcane. If a vis generator can feed on a flame, a device built around a resonator should be able to feed on the aura itself, and hand back its strength as plain current.",
                "The resonator has been tuned to the aura of the chunk and the thaumium carries the current away.<PAGE>Thaumium Aura Converter<DIV>It is crafted at the arcane workbench from thaumium plates, copper, redstone, a vis resonator and a simple mechanism. It drains vis from the aura of its chunk and turns every vis into "
                        + number(AuraConverterTier.THAUMIUM.defaultFePerVis(), Locale.US) + " FE. It gives out up to " + number(AuraConverterTier.THAUMIUM.defaultOutput(), Locale.US) + " FE per tick and stores " + number(AuraConverterTier.THAUMIUM.defaultBuffer(), Locale.US)
                        + " FE. It gives the energy to every neighbouring block that accepts it and never takes any in.<BR>Right-click it to see the stored energy, the aura of the chunk, its state and its rate. Every vis it converts leaves a little flux behind, it never drains the aura under a quarter of its base, and all the converters of a chunk share a limit on what they can drain. A redstone signal stops it. The figures can be changed in the configuration.");
        entry(lang, AuraConverterTier.BRASS, "Alchemical Brass Aura Converter",
                "Thaumium holds magic, but it lets it slip away slowly. Alchemical brass, bound to the aura since the crucible, should keep far more of it in store and hand it out in a steadier stream.",
                "The brass wraps the resonator and nothing is lost on the way out.<PAGE>Alchemical Brass Aura Converter<DIV>It is infused over a thaumium aura converter with alchemical brass plates, a simple mechanism and a vis resonator. Every vis becomes "
                        + number(AuraConverterTier.BRASS.defaultFePerVis(), Locale.US) + " FE. It gives out up to " + number(AuraConverterTier.BRASS.defaultOutput(), Locale.US) + " FE per tick and stores " + number(AuraConverterTier.BRASS.defaultBuffer(), Locale.US) + " FE.");
        entry(lang, AuraConverterTier.VOID, "Void Metal Aura Converter",
                "The void swallows everything that comes near it, aura included. A converter of void metal should drink the aura faster than any other and keep almost all of it as current, as long as the aura is there to drink.",
                "The void drinks the aura and gives it back as pure power.<PAGE>Void Metal Aura Converter<DIV>It is infused over an alchemical brass aura converter with void metal plates, a complex mechanism and two vis resonators. Every vis becomes "
                        + number(AuraConverterTier.VOID.defaultFePerVis(), Locale.US) + " FE. It gives out up to " + number(AuraConverterTier.VOID.defaultOutput(), Locale.US) + " FE per tick and stores " + number(AuraConverterTier.VOID.defaultBuffer(), Locale.US)
                        + " FE. The limit of the chunk keeps it from emptying the aura on its own.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block." + MOD + ".alchemical_brass_aura_converter", "Convertidor de aura de latón alquímico");
        lang.add("block." + MOD + ".thaumium_aura_converter", "Convertidor de aura de taumio");
        lang.add("block." + MOD + ".void_metal_aura_converter", "Convertidor de aura de metal del vacío");
        lang.add(AuraConverterItem.TOOLTIP_RATE, "Convierte cada vis en %s FE y entrega hasta %s FE/t");
        lang.add(AuraConverterItem.TOOLTIP_BUFFER, "Búfer: %s FE");
        lang.add(AuraConverterItem.TOOLTIP_FLUX, "Cada vis convertido añade %s de flujo al aura");
        lang.add(gui("state"), "Estado: %s");
        lang.add(gui("stored"), "Almacenado: %s FE");
        lang.add(gui("aura"), "Aura: %s / %s vis");
        lang.add(gui("converting"), "Conversión: %s FE/t");
        lang.add(gui("output"), "Salida: %s FE/t");
        lang.add(gui("status.running"), "Convirtiendo");
        lang.add(gui("status.running.desc"), "El convertidor está drenando vis del aura del chunk y guardándolo como energía.");
        lang.add(gui("status.paused"), "Pausado");
        lang.add(gui("status.paused.desc"), "Una señal de redstone ha detenido la conversión. La energía almacenada aún se puede sacar.");
        lang.add(gui("status.full"), "Búfer lleno");
        lang.add(gui("status.full.desc"), "El búfer está lleno. La conversión sigue a medida que se saca la energía.");
        lang.add(gui("status.no_vis"), "Sin vis");
        lang.add(gui("status.no_vis.desc"), "El aura del chunk ha bajado hasta la parte que nunca se drena, así que no hay vis que tomar.");
        lang.add(gui("status.chunk_limit"), "Límite del chunk");
        lang.add(gui("status.chunk_limit.desc"), "Los convertidores de este chunk ya han drenado este tick lo máximo que pueden tomar entre todos.");
        lang.add(gui("status.disabled"), "Desactivado");
        lang.add(gui("status.disabled.desc"), "Este convertidor está desactivado en la configuración.");
        lang.add(gui("tooltip.energy.title"), "Energía almacenada");
        lang.add(gui("tooltip.energy.amount"), "%s / %s FE");
        lang.add(gui("tooltip.energy.output"), "Entrega hasta %s FE por tick");
        lang.add(gui("tooltip.aura.title"), "Aura del chunk");
        lang.add(gui("tooltip.aura.amount"), "%s / %s vis");
        lang.add(gui("tooltip.aura.floor"), "Nunca se drena por debajo de %s vis");
        lang.add(gui("tooltip.aura.rate"), "Cada vis se convierte en %s FE");
        entry(lang, AuraConverterTier.THAUMIUM, "Convertidor de aura",
                "El aura es una reserva de poder que nadie ha aprendido a aprovechar sin una varita o una máquina arcana. Si un generador de vis puede alimentarse de una llama, un aparato construido alrededor de un resonador debería poder alimentarse del propio aura y devolver su fuerza como simple corriente.",
                "El resonador se ha sintonizado con el aura del chunk y el taumio se lleva la corriente.<PAGE>Convertidor de aura de taumio<DIV>Se fabrica en la mesa de trabajo arcana con placas de taumio, cobre, redstone, un resonador de vis y un mecanismo simple. Drena vis del aura de su chunk y convierte cada vis en "
                        + number(AuraConverterTier.THAUMIUM.defaultFePerVis(), SPANISH) + " FE. Entrega hasta " + number(AuraConverterTier.THAUMIUM.defaultOutput(), SPANISH) + " FE por tick y almacena " + number(AuraConverterTier.THAUMIUM.defaultBuffer(), SPANISH)
                        + " FE. Da la energía a todos los bloques vecinos que la acepten y nunca recibe ninguna.<BR>Haz clic derecho para ver la energía almacenada, el aura del chunk, su estado y su ritmo. Cada vis que convierte deja un poco de flujo, nunca drena el aura por debajo de una cuarta parte de su base, y todos los convertidores de un chunk comparten un límite de lo que pueden drenar. Una señal de redstone lo detiene. Las cifras se pueden cambiar en la configuración.");
        entry(lang, AuraConverterTier.BRASS, "Convertidor de aura de latón alquímico",
                "El taumio retiene la magia, pero la deja escapar despacio. El latón alquímico, ligado al aura desde el crisol, debería guardar mucha más y entregarla en un chorro más constante.",
                "El latón envuelve el resonador y nada se pierde por el camino.<PAGE>Convertidor de aura de latón alquímico<DIV>Se infunde sobre un convertidor de aura de taumio con placas de latón alquímico, un mecanismo simple y un resonador de vis. Cada vis se convierte en "
                        + number(AuraConverterTier.BRASS.defaultFePerVis(), SPANISH) + " FE. Entrega hasta " + number(AuraConverterTier.BRASS.defaultOutput(), SPANISH) + " FE por tick y almacena " + number(AuraConverterTier.BRASS.defaultBuffer(), SPANISH) + " FE.");
        entry(lang, AuraConverterTier.VOID, "Convertidor de aura de metal del vacío",
                "El vacío se traga todo lo que se le acerca, el aura incluida. Un convertidor de metal del vacío debería beber el aura más rápido que ninguno y conservar casi toda como corriente, mientras quede aura que beber.",
                "El vacío bebe el aura y la devuelve como poder puro.<PAGE>Convertidor de aura de metal del vacío<DIV>Se infunde sobre un convertidor de aura de latón alquímico con placas de metal del vacío, un mecanismo complejo y dos resonadores de vis. Cada vis se convierte en "
                        + number(AuraConverterTier.VOID.defaultFePerVis(), SPANISH) + " FE. Entrega hasta " + number(AuraConverterTier.VOID.defaultOutput(), SPANISH) + " FE por tick y almacena " + number(AuraConverterTier.VOID.defaultBuffer(), SPANISH)
                        + " FE. El límite del chunk impide que vacíe el aura por sí solo.");
    }

    private static void entry(LanguageProvider lang, AuraConverterTier tier, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + AuraConverterContent.research(tier);
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
