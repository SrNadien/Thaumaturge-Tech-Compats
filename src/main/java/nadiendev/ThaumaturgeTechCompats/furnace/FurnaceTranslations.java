package nadiendev.ThaumaturgeTechCompats.furnace;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class FurnaceTranslations {
    private static final String RESEARCH = "research." + ThaumaturgeTechCompats.MOD_ID + "." + FurnaceContent.RESEARCH;

    private FurnaceTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_alchemical_furnace", "Crystal Matrix Alchemical Furnace");
        lang.add("block.thaumaturgetechcompats.neutronium_alchemical_furnace", "Neutronium Alchemical Furnace");
        lang.add("block.thaumaturgetechcompats.infinity_alchemical_furnace", "Infinity Alchemical Furnace");

        lang.add(RESEARCH + ".title", "Avaritia Alchemical Furnaces");
        lang.add(RESEARCH + ".stage_0",
                "The advanced alchemical furnace is hungry, but it still waits between every item it breaks down and it still squanders part of the heat, perditio and aqua it burns.<BR>If its heart were one of the Avaritia smelteries, the same walls should hold a furnace that works much faster and wastes far less.");
        lang.add(RESEARCH + ".stage_1",
                "The structure is exactly that of the Advanced Alchemical Furnace, a three by three base with two layers, but the essentia smeltery at its centre is the matching Avaritia smeltery: Crystal Matrix, Neutronium or Infinity. Salis mundus sprinkled over it brings it to life, and since every smeltery needs the one before it, every furnace needs the one before it too.<BR>- Crystal Matrix: waits half as long between items and gives back 25% of the heat, perditio and aqua it burns.<BR>- Neutronium: waits a quarter as long and gives back 50%.<BR>- Infinity: waits a tenth as long and gives back 90%.<BR>If a part is broken the furnace turns back into its smeltery. They draw and release essentia exactly like the Advanced Alchemical Furnace.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_alchemical_furnace", "Horno alquímico de matriz de cristal");
        lang.add("block.thaumaturgetechcompats.neutronium_alchemical_furnace", "Horno alquímico de neutronio");
        lang.add("block.thaumaturgetechcompats.infinity_alchemical_furnace", "Horno alquímico de infinito");

        lang.add(RESEARCH + ".title", "Hornos Alquímicos de Avaritia");
        lang.add(RESEARCH + ".stage_0",
                "El horno alquímico avanzado es voraz, pero aún espera entre cada objeto que descompone y sigue malgastando parte del calor, perditio y aqua que quema.<BR>Si su corazón fuera una de las fundiciones de Avaritia, las mismas paredes deberían contener un horno que trabaja mucho más rápido y desperdicia mucho menos.");
        lang.add(RESEARCH + ".stage_1",
                "La estructura es exactamente la del Horno alquímico avanzado, una base de tres por tres con dos capas, pero la fundición de esencia del centro es la fundición de Avaritia correspondiente: Matriz de Cristal, Neutronio o Infinito. Espolvorear salis mundus sobre ella la hace cobrar vida, y como cada fundición necesita la anterior, cada horno necesita también la anterior.<BR>- Matriz de Cristal: espera la mitad entre objetos y devuelve el 25% del calor, perditio y aqua que quema.<BR>- Neutronio: espera una cuarta parte y devuelve el 50%.<BR>- Infinito: espera una décima parte y devuelve el 90%.<BR>Si se rompe una parte, el horno vuelve a ser su fundición. Absorben y liberan esencia exactamente igual que el Horno alquímico avanzado.");
    }
}
