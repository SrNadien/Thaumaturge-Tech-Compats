package nadiendev.ThaumaturgeTechCompats.wandtier;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class WandTierTranslations {
    private WandTierTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("item.thaumaturgetechcompats.matrix_wand_rod", "Crystal Matrix Rod");
        lang.add("item.thaumaturgetechcompats.matrix_wand_cap", "Crystal Matrix Cap");
        lang.add("item.thaumaturgetechcompats.neutronium_wand_rod", "Neutronium Rod");
        lang.add("item.thaumaturgetechcompats.neutronium_wand_cap", "Neutronium Cap");
        lang.add("item.thaumaturgetechcompats.infinity_wand_cap", "Infinity Cap");

        lang.add("wand.thaumaturge.cap.neutronium", "Neutronium Capped");
        lang.add("wand.thaumaturge.rod.neutronium", "Neutronium");
        lang.add("wand.thaumaturge.rod.matrix", "Matrix");

        lang.add("research.thaumaturgetechcompats.neutronium_wand.title", "Neutronium Wand");
        lang.add("research.thaumaturgetechcompats.neutronium_wand.stage_0",
                "Crystal matrix is only the first step. Neutronium is a star's heart pressed into metal, and a rod and a cap forged from it should hold far more power and waste far less of it.<BR>The new parts must be built on top of the crystal matrix ones, and I will need the aspect of the end of things to bind the neutron ingots to them.");
        lang.add("research.thaumaturgetechcompats.neutronium_wand.stage_1",
                "Both parts are infused with two neutron ingots and two neutronium plates. The Neutronium Cap uses a Crystal Matrix Cap as its catalyst, and the Neutronium Rod uses a Crystal Matrix Rod.<BR>Assembled into a wand, they hold 5000 vis and cut the cost of every spell by half.");

        lang.add("research.thaumaturgetechcompats.matrix_wand.title", "Crystal Matrix Wand");
        lang.add("research.thaumaturgetechcompats.matrix_wand.stage_0",
                "Quartz is clear and orderly, and crystal matrix is that same order taken to its limit. A rod and a cap made from it should channel vis without losing any at all.");
        lang.add("research.thaumaturgetechcompats.matrix_wand.stage_1",
                "The Crystal Matrix Rod is crafted from three crystal matrix ingots at an arcane workbench. The Crystal Matrix Cap needs three ingots and two crystal matrix plates, the same plates used to build golems.<BR>Assembled into a wand, they hold 1000 vis and its spells cost nothing.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("item.thaumaturgetechcompats.matrix_wand_rod", "Vara de Matriz de Cristal");
        lang.add("item.thaumaturgetechcompats.matrix_wand_cap", "Pomo de Matriz de Cristal");
        lang.add("item.thaumaturgetechcompats.neutronium_wand_rod", "Vara de Neutronio");
        lang.add("item.thaumaturgetechcompats.neutronium_wand_cap", "Pomo de Neutronio");
        lang.add("item.thaumaturgetechcompats.infinity_wand_cap", "Pomo de Infinito");

        lang.add("wand.thaumaturge.cap.neutronium", "Neutronio");
        lang.add("wand.thaumaturge.rod.neutronium", "de Neutronio");
        lang.add("wand.thaumaturge.rod.matrix", "de Matriz");

        lang.add("research.thaumaturgetechcompats.neutronium_wand.title", "Varita de Neutronio");
        lang.add("research.thaumaturgetechcompats.neutronium_wand.stage_0",
                "La matriz de cristal es solo el primer paso. El neutronio es el corazón de una estrella prensado en metal, y una vara y un pomo forjados con él deberían guardar mucho más poder y desperdiciar mucho menos.<BR>Las nuevas piezas deben construirse sobre las de matriz de cristal, y necesitaré el aspecto del fin de las cosas para unirles los lingotes de neutrones.");
        lang.add("research.thaumaturgetechcompats.neutronium_wand.stage_1",
                "Ambas piezas se infunden con dos lingotes de neutrones y dos placas de neutronio. El Pomo de Neutronio usa como catalizador un Pomo de Matriz de Cristal, y la Vara de Neutronio una Vara de Matriz de Cristal.<BR>Montadas en una varita, almacenan 5000 de vis y reducen a la mitad el coste de cada hechizo.");

        lang.add("research.thaumaturgetechcompats.matrix_wand.title", "Varita de Matriz de Cristal");
        lang.add("research.thaumaturgetechcompats.matrix_wand.stage_0",
                "El cuarzo es claro y ordenado, y la matriz de cristal es ese mismo orden llevado al límite. Una vara y un pomo hechos con ella deberían canalizar el vis sin perder nada en absoluto.");
        lang.add("research.thaumaturgetechcompats.matrix_wand.stage_1",
                "La Vara de Matriz de Cristal se fabrica con tres lingotes de matriz de cristal en una mesa de trabajo arcana. El Pomo de Matriz de Cristal necesita tres lingotes y dos placas de matriz de cristal, las mismas que se usan para construir gólems.<BR>Montados en una varita, almacenan 1000 de vis y sus hechizos no cuestan nada.");
    }
}
