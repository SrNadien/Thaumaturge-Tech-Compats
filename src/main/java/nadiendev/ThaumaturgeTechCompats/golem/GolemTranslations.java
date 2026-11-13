package nadiendev.ThaumaturgeTechCompats.golem;

import com.leclowndu93150.thaumaturge.api.golems.parts.GolemMaterial;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class GolemTranslations {
    private GolemTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("item.thaumaturgetechcompats.matrix_plate", "Crystal Matrix Plate");
        lang.add("item.thaumaturgetechcompats.neutronium_plate", "Neutronium Plate");
        lang.add("item.thaumaturgetechcompats.infinity_plate", "Infinity Plate");

        lang.add(GolemMaterial.nameKey(ThaumaturgeTechCompats.id("matrix")), "Crystal Matrix");
        lang.add(GolemMaterial.nameKey(ThaumaturgeTechCompats.id("neutronium")), "Neutronium");
        lang.add(GolemMaterial.nameKey(ThaumaturgeTechCompats.id("infinity")), "Infinity");
        lang.add(GolemMaterial.descriptionKey(ThaumaturgeTechCompats.id("matrix")),
                "The golem is crafted from crystal matrix. Its crystalline frame is light and agile, and shrugs off fire and explosions.");
        lang.add(GolemMaterial.descriptionKey(ThaumaturgeTechCompats.id("neutronium")),
                "The golem is crafted from neutronium. Incredibly dense and sturdy, it is heavy, armored and immune to fire and explosions.");
        lang.add(GolemMaterial.descriptionKey(ThaumaturgeTechCompats.id("infinity")),
                "The golem is crafted from infinity. Nothing can stop it: it is brutal, heavily armored, immune to fire and explosions, and slowly repairs itself.");

        lang.add("research.thaumaturgetechcompats.avaritia_golems.title", "Material Studies: Avaritia");
        lang.add("research.thaumaturgetechcompats.avaritia_golems.stage_0",
                "Thaumium and void metal are strong, but the ingots of Avaritia are on another level entirely.<BR>Crystal matrix is light and clear, neutronium is impossibly dense and infinity is simply beyond measure. Pressed into plates, they should make golems unlike any I have built before.");
        lang.add("research.thaumaturgetechcompats.avaritia_golems.stage_1",
                "Three ingots pressed in a crafting grid make three plates. Neutronium plates also need a Crystal Matrix Plate, and infinity plates a Neutronium Plate.<BR>- Crystal Matrix: light, fireproof and blastproof.<BR>- Neutronium: heavy, armored, fireproof and blastproof.<BR>- Infinity: the sturdiest of all, brutal, armored, fireproof, blastproof and self-repairing.<BR>Each plate is the base of a golem in the Golem Press, together with a Complex Arcane Mechanism.<BR>The plates are also ingredients for the wand caps and rods and for the improved foci of this chapter.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("item.thaumaturgetechcompats.matrix_plate", "Placa de Matriz de Cristal");
        lang.add("item.thaumaturgetechcompats.neutronium_plate", "Placa de Neutronio");
        lang.add("item.thaumaturgetechcompats.infinity_plate", "Placa de Infinito");

        lang.add(GolemMaterial.nameKey(ThaumaturgeTechCompats.id("matrix")), "Matriz de Cristal");
        lang.add(GolemMaterial.nameKey(ThaumaturgeTechCompats.id("neutronium")), "Neutronio");
        lang.add(GolemMaterial.nameKey(ThaumaturgeTechCompats.id("infinity")), "Infinito");
        lang.add(GolemMaterial.descriptionKey(ThaumaturgeTechCompats.id("matrix")),
                "El gólem está hecho de matriz de cristal. Su armazón cristalino es ligero y ágil, e ignora el fuego y las explosiones.");
        lang.add(GolemMaterial.descriptionKey(ThaumaturgeTechCompats.id("neutronium")),
                "El gólem está hecho de neutronio. Increíblemente denso y resistente, es pesado, acorazado e inmune al fuego y a las explosiones.");
        lang.add(GolemMaterial.descriptionKey(ThaumaturgeTechCompats.id("infinity")),
                "El gólem está hecho de infinito. Nada puede detenerlo: es brutal, muy acorazado, inmune al fuego y a las explosiones, y se repara solo poco a poco.");

        lang.add("research.thaumaturgetechcompats.avaritia_golems.title", "Estudio de materiales: Avaritia");
        lang.add("research.thaumaturgetechcompats.avaritia_golems.stage_0",
                "El taumio y el metal del vacío son fuertes, pero los lingotes de Avaritia están en otra liga.<BR>La matriz de cristal es ligera y translúcida, el neutronio es imposiblemente denso y el infinito simplemente no tiene medida. Prensados en placas, deberían dar gólems distintos a todo lo que he construido.");
        lang.add("research.thaumaturgetechcompats.avaritia_golems.stage_1",
                "Tres lingotes prensados en una mesa de crafteo dan tres placas. Las de neutronio necesitan además una Placa de Matriz de Cristal, y las de infinito una Placa de Neutronio.<BR>- Matriz de Cristal: ligero, a prueba de fuego y de explosiones.<BR>- Neutronio: pesado, acorazado, a prueba de fuego y de explosiones.<BR>- Infinito: el más resistente, brutal, acorazado, a prueba de fuego y explosiones y autorreparable.<BR>Cada placa es la base de un gólem en la Prensa de Gólems, junto con un Mecanismo Arcano Complejo.<BR>Las placas también son ingredientes de los pomos y varas de varita y de los focos mejorados de este capítulo.");
    }
}
