package nadiendev.ThaumaturgeTechCompats.emc;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class EmcTranslations {
    private static final String ENTRY = "research." + ThaumaturgeTechCompats.MOD_ID + "." + EmcIntegration.ENTRY;

    private EmcTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(EmcIntegration.RECIPE_MAPPER_KEY, "Thaumaturge Tech Compats recipes");
        lang.add(EmcIntegration.RECIPE_MAPPER_KEY + ".tooltip", "Gives EMC to the results of the arcane workbench, crucible and infusion altar recipes of Thaumaturge from their item ingredients. Aspects, vis and instability add no EMC.");
        lang.add(EmcIntegration.RECIPE_MAPPER_KEY + ".button", "Edit Thaumaturge Tech Compats recipes");
        lang.add(EmcIntegration.EXCLUSION_MAPPER_KEY, "Thaumaturge Tech Compats exclusions");
        lang.add(EmcIntegration.EXCLUSION_MAPPER_KEY + ".tooltip", "Removes the EMC of the items in the thaumaturgetechcompats:no_emc tag: creative and unbreakable items and wands and jars that hold a charge or contents.");
        lang.add(EmcIntegration.EXCLUSION_MAPPER_KEY + ".button", "Edit Thaumaturge Tech Compats exclusions");
        lang.add(ENTRY + ".title", "Equivalent Exchange");
        lang.add(ENTRY + ".stage_0",
                "The philosopher's stone of another craft weighs every thing by what it is worth, and I could teach it to weigh mine. If it knows what iron, gold and diamond are worth, it should learn the worth of thaumium, alchemical brass and void metal, and of everything I forge from them.");
        lang.add(ENTRY + ".stage_1",
                "With ProjectE installed, everything made on the arcane workbench, in the crucible and on the infusion altar gets EMC from the items it is made of, exactly as the dusts and ingots of the vanilla table do. The aspects, the vis and the instability of the recipe add nothing.<BR>The materials that no recipe produces have a value of their own: thaumium ingots are worth more than iron and less than gold, alchemical brass ingots more than gold and less than diamond, and void metal ingots more than diamond and less than netherite. Amber, quicksilver, salis mundus, void seeds and the rest of the raw materials are worth what they would be in the vanilla table.<BR>Creative items, unbreakable pearls and crucifixes, wands and jars that hold a charge or contents carry no EMC.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(EmcIntegration.RECIPE_MAPPER_KEY, "Recetas de Thaumaturge Tech Compats");
        lang.add(EmcIntegration.RECIPE_MAPPER_KEY + ".tooltip", "Da EMC a los resultados de las recetas de la mesa arcana, el crisol y el altar de infusión de Thaumaturge a partir de sus ingredientes. Los aspectos, el vis y la inestabilidad no suman EMC.");
        lang.add(EmcIntegration.RECIPE_MAPPER_KEY + ".button", "Editar recetas de Thaumaturge Tech Compats");
        lang.add(EmcIntegration.EXCLUSION_MAPPER_KEY, "Exclusiones de Thaumaturge Tech Compats");
        lang.add(EmcIntegration.EXCLUSION_MAPPER_KEY + ".tooltip", "Quita el EMC de los ítems de la etiqueta thaumaturgetechcompats:no_emc: ítems creativos e irrompibles, y varitas y jarras que guardan carga o contenido.");
        lang.add(EmcIntegration.EXCLUSION_MAPPER_KEY + ".button", "Editar exclusiones de Thaumaturge Tech Compats");
        lang.add(ENTRY + ".title", "Intercambio equivalente");
        lang.add(ENTRY + ".stage_0",
                "La piedra filosofal de otro oficio pesa cada cosa por lo que vale, y yo podría enseñarle a pesar las mías. Si sabe cuánto valen el hierro, el oro y el diamante, debería aprender lo que valen el taumio, el latón alquímico y el metal del vacío, y todo lo que forjo con ellos.");
        lang.add(ENTRY + ".stage_1",
                "Con ProjectE instalado, todo lo que se fabrica en la mesa arcana, en el crisol y en el altar de infusión recibe EMC a partir de los ítems con que está hecho, igual que los polvos y lingotes de la tabla vanilla. Los aspectos, el vis y la inestabilidad de la receta no suman nada.<BR>Los materiales que ninguna receta produce tienen un valor propio: los lingotes de taumio valen más que el hierro y menos que el oro, los de latón alquímico más que el oro y menos que el diamante, y los de metal del vacío más que el diamante y menos que la netherita. El ámbar, el azogue, el salis mundus, las semillas del vacío y el resto de materias primas valen lo que valdrían en la tabla vanilla.<BR>Los ítems creativos, las perlas y crucifijos irrompibles, y las varitas y jarras que guardan carga o contenido no tienen EMC.");
    }
}
