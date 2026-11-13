package nadiendev.ThaumaturgeTechCompats.focus;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class FocusTranslations {
    private FocusTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("item.thaumaturgetechcompats.matrix_focus", "Blank Crystal Matrix Focus");
        lang.add("item.thaumaturgetechcompats.infinity_focus", "Blank Infinity Focus");
        lang.add("item.thaumaturgetechcompats.blaze_cube_focus", "Blank Blaze Cube Focus");

        lang.add("research.thaumaturgetechcompats.avaritia_foci.title", "Improved Foci");
        lang.add("research.thaumaturgetechcompats.avaritia_foci.stage_0",
                "Even a Greater Focus runs out of room when I try to weave a truly grand spell into it. Crystal matrix holds order without losing any of it, and infinity holds anything at all.<BR>The foci must be built on top of one another, and the aspect of the end of things will keep the crystal from shattering.");
        lang.add("research.thaumaturgetechcompats.avaritia_foci.stage_1",
                "All three are infused on top of a Greater Focus or another improved focus.<BR>- Crystal Matrix Focus: a Greater Focus, two crystal matrix ingots and two crystal matrix plates. Holds a bigger spell than any focus before it.<BR>- Infinity Focus: a Crystal Matrix Focus, two infinity ingots, two infinity plates and two neutronium plates. The most capable focus there is.<BR>- Blaze Cube Focus: a Greater Focus, two blaze cubes and two crystal matrix plates. Not as deep as the matrix focus, but it fans out into many more branches and repeats, which suits a barrage of fire.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("item.thaumaturgetechcompats.matrix_focus", "Foco de matriz de cristal vacío");
        lang.add("item.thaumaturgetechcompats.infinity_focus", "Foco del infinito vacío");
        lang.add("item.thaumaturgetechcompats.blaze_cube_focus", "Foco de cubo de blaze vacío");

        lang.add("research.thaumaturgetechcompats.avaritia_foci.title", "Focos mejorados");
        lang.add("research.thaumaturgetechcompats.avaritia_foci.stage_0",
                "Hasta un foco mejorado vacío se queda sin espacio cuando intento tejer en él un hechizo realmente grandioso. La matriz de cristal conserva el orden sin perder nada de él, y el infinito sostiene cualquier cosa.<BR>Los focos deben construirse unos sobre otros, y el aspecto del fin de las cosas evitará que el cristal se rompa.");
        lang.add("research.thaumaturgetechcompats.avaritia_foci.stage_1",
                "Los tres se infunden sobre un foco mejorado vacío u otro foco de este capítulo.<BR>- Foco de matriz de cristal: un foco mejorado vacío, dos lingotes y dos placas de matriz de cristal. Admite un hechizo mayor que cualquier foco anterior.<BR>- Foco del infinito: un foco de matriz de cristal, dos lingotes de infinito, dos placas de infinito y dos placas de neutronio. El foco más capaz que existe.<BR>- Foco de cubo de blaze: un foco mejorado vacío, dos cubos de blaze y dos placas de matriz de cristal. Menos profundo que el de matriz, pero se abre en muchas más ramas y repeticiones, ideal para una lluvia de fuego.");
    }
}
