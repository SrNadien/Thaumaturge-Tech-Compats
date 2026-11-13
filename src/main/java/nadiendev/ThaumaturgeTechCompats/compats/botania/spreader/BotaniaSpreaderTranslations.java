package nadiendev.ThaumaturgeTechCompats.compats.botania.spreader;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class BotaniaSpreaderTranslations {
    private static final String BLOCK = "block.thaumaturgetechcompats.";
    private static final String ENTRY = "research.thaumaturgetechcompats." + BotaniaSpreaderContent.ENTRY;

    private BotaniaSpreaderTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (SpreaderTier tier : SpreaderTier.values()) {
            lang.add(BLOCK + tier.blockName(), tier.english() + " Mana Spreader");
        }
        lang.add(ENTRY + ".title", "Higher Mana Spreaders");
        lang.add(ENTRY + ".stage_0",
                "The Gaia Spreader is the finest the botanists can build, and still its bursts are small and slow beside what an infusion altar could wake in them. If the metals that widened my jars were set into the spreader, it should hold far more mana and throw it much harder.<BR>Thaumium, alchemical brass and void metal should each carry a spreader a step further than the last.");
        lang.add(ENTRY + ".stage_1",
                "Every spreader is made on an infusion altar, starting from the one before it:<BR>- The Thaumium Mana Spreader needs a Gaia Spreader, four thaumium plates and a Simple Arcane Mechanism.<BR>- The Alchemical Brass Mana Spreader needs the Thaumium one, four alchemical brass plates and a Simple Arcane Mechanism.<BR>- The Void Metal Mana Spreader needs the Alchemical Brass one, four void metal plates and a Void Seed.<BR>They work like any other spreader, take lenses and can be bound with the Wand of the Forest. How much mana a burst carries, how much the spreader holds, how far and how fast the burst flies and what colour it has is set in the configuration for each tier.");
    }

    public static void spanish(LanguageProvider lang) {
        for (SpreaderTier tier : SpreaderTier.values()) {
            lang.add(BLOCK + tier.blockName(), "Esparcidor de maná de " + tier.spanish());
        }
        lang.add(ENTRY + ".title", "Esparcidores de maná superiores");
        lang.add(ENTRY + ".stage_0",
                "El Esparcidor de Gaia es lo mejor que saben construir los botánicos, y aun así sus ráfagas son pequeñas y lentas junto a lo que un altar de infusión podría despertar en ellas. Si los metales que ensancharon mis frascos se engastaran en el esparcidor, debería guardar mucho más maná y lanzarlo con mucha más fuerza.<BR>El taumio, el latón alquímico y el metal del vacío deberían llevar cada uno al esparcidor un paso más allá que el anterior.");
        lang.add(ENTRY + ".stage_1",
                "Cada esparcidor se fabrica en un altar de infusión, partiendo del anterior:<BR>- El Esparcidor de maná de taumio necesita un Esparcidor de Gaia, cuatro placas de taumio y un Mecanismo arcano simple.<BR>- El Esparcidor de maná de latón alquímico necesita el de taumio, cuatro placas de latón alquímico y un Mecanismo arcano simple.<BR>- El Esparcidor de maná de metal del vacío necesita el de latón alquímico, cuatro placas de metal del vacío y una Semilla del vacío.<BR>Funcionan como cualquier otro esparcidor, aceptan lentes y se pueden enlazar con la Varita del bosque. Cuánto maná lleva cada ráfaga, cuánto guarda el esparcidor, hasta dónde y a qué velocidad vuela la ráfaga y de qué color es se ajusta en la configuración de cada nivel.");
    }
}
