package nadiendev.ThaumaturgeTechCompats.smelter;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class SmelterTranslations {
    private static final String RESEARCH = "research." + ThaumaturgeTechCompats.MOD_ID + "." + SmelterContent.RESEARCH;

    private SmelterTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_smelter", "Crystal Matrix Essentia Smeltery");
        lang.add("block.thaumaturgetechcompats.neutronium_smelter", "Neutronium Essentia Smeltery");
        lang.add("block.thaumaturgetechcompats.infinity_smelter", "Infinity Essentia Smeltery");

        lang.add(RESEARCH + ".title", "Avaritia Smelteries");
        lang.add(RESEARCH + ".stage_0",
                "The void metal smeltery is the best that ordinary metals can give me, and it still wastes essentia and takes its time about it.<BR>The ingots of Avaritia are another matter. Crystal matrix is almost lattice-perfect, neutronium holds more mass than any vessel should, and infinity holds everything. If I rebuild the smeltery out of them, nothing should be lost to flux any more.");
        lang.add(RESEARCH + ".stage_1",
                "Each smeltery is built on the one before it, with five plates and two ingots of its own material and an advanced alchemical construct:<BR>- Crystal Matrix: 97% efficiency, hands slurry over every 8 ticks.<BR>- Neutronium: 98.5% efficiency, every 5 ticks.<BR>- Infinity: perfect efficiency, so no essentia is ever lost, every 2 ticks.<BR>They work exactly like the other smelteries, with the same alembics, slurry pumps, venting ports and bellows.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_smelter", "Fundición de esencia de matriz de cristal");
        lang.add("block.thaumaturgetechcompats.neutronium_smelter", "Fundición de esencia de neutronio");
        lang.add("block.thaumaturgetechcompats.infinity_smelter", "Fundición de esencia de infinito");

        lang.add(RESEARCH + ".title", "Fundiciones de Avaritia");
        lang.add(RESEARCH + ".stage_0",
                "La fundición de metal del vacío es lo mejor que pueden darme los metales corrientes, y aun así desperdicia esencia y se toma su tiempo.<BR>Los lingotes de Avaritia son otra cosa. La matriz de cristal es casi una red perfecta, el neutronio guarda más masa de la que ningún recipiente debería soportar, y el infinito lo contiene todo. Si reconstruyo la fundición con ellos, ya no debería perderse nada en forma de flujo.");
        lang.add(RESEARCH + ".stage_1",
                "Cada fundición se construye sobre la anterior, con cinco placas y dos lingotes de su propio material y una construcción alquímica avanzada:<BR>- Matriz de Cristal: 97% de eficiencia, entrega la pulpa cada 8 ticks.<BR>- Neutronio: 98,5% de eficiencia, cada 5 ticks.<BR>- Infinito: eficiencia perfecta, así que nunca se pierde esencia, cada 2 ticks.<BR>Funcionan exactamente igual que las demás fundiciones, con los mismos alambiques, bombas de pulpa, respiraderos y fuelles.");
    }
}
