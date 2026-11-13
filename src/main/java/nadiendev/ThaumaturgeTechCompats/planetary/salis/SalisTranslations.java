package nadiendev.ThaumaturgeTechCompats.planetary.salis;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class SalisTranslations {
    private static final String ITEM = "item." + ThaumaturgeTechCompats.MOD_ID + ".";
    private static final String ENTRY = "research." + ThaumaturgeTechCompats.MOD_ID + "." + SalisContent.ENTRY;

    private SalisTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (PlanetaryTier tier : SalisIntegration.tiers()) {
            lang.add(ITEM + SalisIntegration.itemName(tier), tier.english() + " Salis Mundus");
            lang.add(SalisVariantItem.TOOLTIP_VARIANT + "." + tier.id(), tier.english());
        }
        lang.add(SalisVariantItem.TOOLTIP_VARIANT, "Salis Mundus variant made of %s");
        lang.add(SalisVariantItem.TOOLTIP_SAME, "Works exactly like Salis Mundus");
        lang.add(ENTRY + ".title", "Salis Mundus Variants");
        lang.add(ENTRY + ".stage_0",
                "The Salis Mundus opens the way for every structure I wake with it, and yet it is only a pinch of salt. If I fold the heaviest materials of Avaritia into it inside an infusion altar, I should obtain dusts that still wake the same structures but carry the weight of those materials.<BR>Blaze cube, crystal matrix, neutronium and infinity should each give a variant, every one built on the one before it.");
        lang.add(ENTRY + ".stage_1",
                "Each variant is infused on an altar, starting from the previous dust, with four units of its material, and gives four dusts:<BR>- The Blaze Cube Salis Mundus needs the Salis Mundus and four blaze cubes.<BR>- The Crystal Matrix Salis Mundus needs the Blaze Cube Salis Mundus and four crystal matrix ingots.<BR>- The Neutronium Salis Mundus needs the Crystal Matrix Salis Mundus and four neutronium ingots.<BR>- The Infinity Salis Mundus needs the Neutronium Salis Mundus and four infinity ingots.<BR>All of them work exactly like the Salis Mundus and wake the same structures.<BR>The Crystal Matrix, Neutronium and Infinity ones can only be infused on an altar whose center is the Runic Matrix of their own tier, with the instability that matrix brings.<BR>These dusts are the ones asked for by the recipes of the planetary pearls and star crucifixes of the blaze cube, crystal matrix, neutronium and infinity levels.");
    }

    public static void spanish(LanguageProvider lang) {
        for (PlanetaryTier tier : SalisIntegration.tiers()) {
            lang.add(ITEM + SalisIntegration.itemName(tier), "Salis Mundus " + tier.spanish());
            lang.add(SalisVariantItem.TOOLTIP_VARIANT + "." + tier.id(), tier.spanish());
        }
        lang.add(SalisVariantItem.TOOLTIP_VARIANT, "Variante de Salis Mundus %s");
        lang.add(SalisVariantItem.TOOLTIP_SAME, "Funciona exactamente igual que el Salis Mundus");
        lang.add(ENTRY + ".title", "Variantes del Salis Mundus");
        lang.add(ENTRY + ".stage_0",
                "El Salis Mundus abre el camino de cada estructura que despierto con él, y sin embargo no es más que un puñado de sal. Si pliego en él los materiales más pesados de Avaritia dentro de un altar de infusión, debería obtener polvos que sigan despertando las mismas estructuras pero con el peso de esos materiales.<BR>El cubo de blaze, la matriz de cristal, el neutronio y el infinito deberían dar cada uno una variante, y cada una se construye sobre la anterior.");
        lang.add(ENTRY + ".stage_1",
                "Cada variante se infunde en un altar, partiendo del polvo anterior, con cuatro unidades de su material, y da cuatro polvos:<BR>- El Salis Mundus de cubo de blaze necesita el Salis Mundus y cuatro cubos de blaze.<BR>- El Salis Mundus de matriz de cristal necesita el Salis Mundus de cubo de blaze y cuatro lingotes de matriz de cristal.<BR>- El Salis Mundus de neutronio necesita el Salis Mundus de matriz de cristal y cuatro lingotes de neutronio.<BR>- El Salis Mundus infinito necesita el Salis Mundus de neutronio y cuatro lingotes de infinito.<BR>Todos funcionan exactamente igual que el Salis Mundus y despiertan las mismas estructuras.<BR>Los de matriz de cristal, neutronio e infinito solo se pueden infundir en un altar cuyo centro sea la Matriz rúnica de su propio nivel, con la inestabilidad que esa matriz trae.<BR>Estos polvos son los que piden las recetas de las perlas planetarias y los crucifijos estelares de los niveles de cubo de blaze, matriz de cristal, neutronio e infinito.");
    }
}
