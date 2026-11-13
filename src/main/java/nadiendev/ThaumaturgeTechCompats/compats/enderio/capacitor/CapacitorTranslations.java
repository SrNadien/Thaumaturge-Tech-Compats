package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank.CapacitorBankContent;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank.CapacitorBankSupport;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class CapacitorTranslations {
    private static final String ITEM = "item." + ThaumaturgeTechCompats.MOD_ID + ".";
    private static final String BLOCK = "block." + EnderIOIntegration.MOD_ID + ".";
    private static final String ENTRY = "research." + ThaumaturgeTechCompats.MOD_ID + "." + CapacitorContent.ENTRY;
    private static final String BANK_ENTRY = "research." + ThaumaturgeTechCompats.MOD_ID + "." + CapacitorBankContent.ENTRY;

    private CapacitorTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (EnderIOTier tier : EnderIOTier.values()) {
            lang.add(ITEM + tier.capacitorName(), tier.english() + " Capacitor");
        }
        lang.add(ENTRY + ".title", "Magical Capacitors");
        lang.add(ENTRY + ".stage_0",
                "The octadic capacitor is the best the engineers of Ender IO can build, and every machine of theirs answers to the capacitor it holds. A capacitor that stores vis-forged metal in its layers should push them well past that.<BR>Each metal I have mastered should give a capacitor that beats the one before it.");
        lang.add(ENTRY + ".stage_1",
                "Every capacitor is infused from the one below it, with four plates of the new metal, two Vibrant Alloy Ingots and a Vibrant Crystal. The Thaumium Capacitor is infused from an Octadic Capacitor, the Alchemical Brass Capacitor from the Thaumium one, and the Void Metal Capacitor from the Alchemical Brass one. Alchemical Brass adds a simple mechanism and Void Metal a void seed.<BR>They go in the capacitor slot of any Ender IO machine and work like an octadic capacitor with a higher level, so the machine stores more energy and works faster. The exact levels are set in the configuration.");
        for (EnderIOTier tier : EnderIOTier.values()) {
            lang.add(BLOCK + CapacitorBankSupport.blockName(tier), tier.english() + " Capacitor Bank");
        }
        lang.add(BANK_ENTRY + ".title", "Magical Capacitor Banks");
        lang.add(BANK_ENTRY + ".stage_0",
                "A single capacitor inside a machine is not enough for a long night. The engineers of Ender IO bind capacitor banks into one great reservoir, and each bank links to every neighbour of its own kind. If my vis-forged capacitors beat the octadic one, banks lined with them should hold far more than the vibrant bank.<BR>Each metal should give a bank that beats the one before it.");
        lang.add(BANK_ENTRY + ".stage_1",
                "Every bank is infused from the one below it, with four plates of the new metal and four capacitors of that same metal. The Thaumium Capacitor Bank is infused from a Vibrant Capacitor Bank, the Alchemical Brass one from the Thaumium bank, and the Void Metal one from the Alchemical Brass bank.<BR>They work like the banks of Ender IO: place them touching and they share a single pool of energy, but only banks of the same kind join together. The capacity of each one is set in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        for (EnderIOTier tier : EnderIOTier.values()) {
            lang.add(ITEM + tier.capacitorName(), "Capacitor " + tier.spanish());
        }
        lang.add(ENTRY + ".title", "Capacitores mágicos");
        lang.add(ENTRY + ".stage_0",
                "El capacitor octádico es lo mejor que pueden construir los ingenieros de Ender IO, y todas sus máquinas responden al capacitor que llevan dentro. Un capacitor que guarde metal forjado con vis entre sus capas debería superarlo con creces.<BR>Cada metal que domine debería dar un capacitor que supere al anterior.");
        lang.add(ENTRY + ".stage_1",
                "Cada capacitor se infunde a partir del inferior, con cuatro placas del nuevo metal, dos Lingotes de aleación vibrante y un Cristal vibrante. El Capacitor de taumio se infunde a partir de un Capacitor octádico, el de latón alquímico a partir del de taumio y el de metal del vacío a partir del de latón alquímico. El latón alquímico añade un mecanismo simple y el metal del vacío una semilla del vacío.<BR>Van en la ranura de capacitor de cualquier máquina de Ender IO y funcionan como un capacitor octádico de nivel más alto, así que la máquina almacena más energía y trabaja más rápido. Los niveles exactos se ajustan en la configuración.");
        for (EnderIOTier tier : EnderIOTier.values()) {
            lang.add(BLOCK + CapacitorBankSupport.blockName(tier), "Banco de capacitores " + tier.spanish());
        }
        lang.add(BANK_ENTRY + ".title", "Bancos de capacitores mágicos");
        lang.add(BANK_ENTRY + ".stage_0",
                "Un solo capacitor dentro de una máquina no basta para una noche larga. Los ingenieros de Ender IO unen bancos de capacitores en un único gran depósito, y cada banco se enlaza con todos sus vecinos de su misma clase. Si mis capacitores forjados con vis superan al octádico, unos bancos forrados con ellos deberían guardar mucho más que el banco vibrante.<BR>Cada metal debería dar un banco que supere al anterior.");
        lang.add(BANK_ENTRY + ".stage_1",
                "Cada banco se infunde a partir del inferior, con cuatro placas del nuevo metal y cuatro capacitores de ese mismo metal. El Banco de capacitores de taumio se infunde a partir de un Banco de capacitores vibrante, el de latón alquímico a partir del de taumio y el de metal del vacío a partir del de latón alquímico.<BR>Funcionan como los bancos de Ender IO: colócalos tocándose y comparten una única reserva de energía, pero solo se unen los bancos de la misma clase. La capacidad de cada uno se ajusta en la configuración.");
    }
}
