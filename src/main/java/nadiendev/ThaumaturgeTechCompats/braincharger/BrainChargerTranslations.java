package nadiendev.ThaumaturgeTechCompats.braincharger;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class BrainChargerTranslations {
    private static final String BOX_ENTRY = "research.thaumaturgetechcompats." + BrainChargerContent.BOX_RESEARCH;
    private static final String CHARGER_ENTRY = "research.thaumaturgetechcompats." + BrainChargerContent.CHARGER_RESEARCH;

    private BrainChargerTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_brain_box", "Crystal Matrix Mnemonic Matrix");
        lang.add("block.thaumaturgetechcompats.neutronium_brain_box", "Neutronium Mnemonic Matrix");
        lang.add("block.thaumaturgetechcompats.infinity_brain_box", "Infinity Mnemonic Matrix");
        lang.add("block.thaumaturgetechcompats.matrix_workbench_charger", "Crystal Matrix Workbench Charger");
        lang.add("block.thaumaturgetechcompats.neutronium_workbench_charger", "Neutronium Workbench Charger");
        lang.add("block.thaumaturgetechcompats.infinity_workbench_charger", "Infinity Workbench Charger");

        lang.add(BOX_ENTRY + ".title", "Mnemonic Matrices of Avaritia");
        lang.add(BOX_ENTRY + ".stage_0",
                "A mnemonic matrix lets a thaumatorium remember two more formulas at the same time, but the clockwork inside is small and fragile.<BR>If its frame is rebuilt from plates of crystal matrix, neutronium and infinity, the same matrix should hold many more formulas without losing a single one.");
        lang.add(BOX_ENTRY + ".stage_1",
                "Each matrix is made on an infusion altar, starting from the one before it:<BR>- The Crystal Matrix one starts from an ordinary mnemonic matrix.<BR>- The Neutronium one starts from the Crystal Matrix one.<BR>- The Infinity one starts from the Neutronium one.<BR>They are placed against a thaumatorium exactly like the ordinary ones, and they stack with them. By default they allow 4, 6 and 10 formulas at the same time.");

        lang.add(CHARGER_ENTRY + ".title", "Workbench Chargers of Avaritia");
        lang.add(CHARGER_ENTRY + ".stage_0",
                "The workbench charger pulls vis from the aura of the chunks around my arcane workbench, but it reaches only a short distance and wastes a lot on the way.<BR>Plates of crystal matrix, neutronium and infinity should let it reach much farther and spend far less of the aura on every craft.");
        lang.add(CHARGER_ENTRY + ".stage_1",
                "Each charger is made on an infusion altar, starting from the one before it:<BR>- The Crystal Matrix one starts from an ordinary workbench charger.<BR>- The Neutronium one starts from the Crystal Matrix one.<BR>- The Infinity one starts from the Neutronium one.<BR>They sit on top of the workbench like the ordinary one. By default they draw from 2, 3 and 4 chunks around it and charge only 70%, 40% and 10% of the vis of each craft.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("block.thaumaturgetechcompats.matrix_brain_box", "Matriz Mnemotécnica de Matriz de Cristal");
        lang.add("block.thaumaturgetechcompats.neutronium_brain_box", "Matriz Mnemotécnica de Neutronio");
        lang.add("block.thaumaturgetechcompats.infinity_brain_box", "Matriz Mnemotécnica del Infinito");
        lang.add("block.thaumaturgetechcompats.matrix_workbench_charger", "Cargador de Mesa de Matriz de Cristal");
        lang.add("block.thaumaturgetechcompats.neutronium_workbench_charger", "Cargador de Mesa de Neutronio");
        lang.add("block.thaumaturgetechcompats.infinity_workbench_charger", "Cargador de Mesa del Infinito");

        lang.add(BOX_ENTRY + ".title", "Matrices Mnemotécnicas de Avaritia");
        lang.add(BOX_ENTRY + ".stage_0",
                "Una matriz mnemotécnica permite que un taumatorio recuerde dos fórmulas más a la vez, pero el mecanismo de su interior es pequeño y frágil.<BR>Si reconstruyo su armazón con placas de matriz de cristal, neutronio e infinito, la misma matriz debería guardar muchas más fórmulas sin perder ninguna.");
        lang.add(BOX_ENTRY + ".stage_1",
                "Cada matriz se fabrica en un altar de infusión, partiendo de la anterior:<BR>- La de Matriz de Cristal parte de una matriz mnemotécnica normal.<BR>- La de Neutronio parte de la de Matriz de Cristal.<BR>- La del Infinito parte de la de Neutronio.<BR>Se colocan junto al taumatorio igual que las normales, y se suman a ellas. Por defecto permiten 4, 6 y 10 fórmulas a la vez.");

        lang.add(CHARGER_ENTRY + ".title", "Cargadores de Mesa de Avaritia");
        lang.add(CHARGER_ENTRY + ".stage_0",
                "El cargador de mesa extrae vis del aura de los chunks que rodean mi mesa de trabajo arcana, pero llega poco lejos y desperdicia mucho por el camino.<BR>Las placas de matriz de cristal, neutronio e infinito deberían permitirle llegar mucho más lejos y gastar mucha menos aura en cada fabricación.");
        lang.add(CHARGER_ENTRY + ".stage_1",
                "Cada cargador se fabrica en un altar de infusión, partiendo del anterior:<BR>- El de Matriz de Cristal parte de un cargador de mesa normal.<BR>- El de Neutronio parte del de Matriz de Cristal.<BR>- El del Infinito parte del de Neutronio.<BR>Se colocan sobre la mesa igual que el normal. Por defecto extraen de 2, 3 y 4 chunks a su alrededor y cobran solo el 70%, el 40% y el 10% del vis de cada fabricación.");
    }
}
