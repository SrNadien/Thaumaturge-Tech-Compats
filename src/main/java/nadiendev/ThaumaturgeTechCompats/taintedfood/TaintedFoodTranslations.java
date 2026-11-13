package nadiendev.ThaumaturgeTechCompats.taintedfood;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class TaintedFoodTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private TaintedFoodTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (TaintedFoodGender gender : TaintedFoodGender.values()) {
            lang.add(gender.nameKey(), "Tainted %s");
        }
        entry(lang, "Tainted Food",
                "The flux that spoils a field can also be coaxed into a loaf of bread. If the crucible is fed enough vitium, whatever edible I drop into it should come out heavier with hunger, and with something else besides.",
                "A little flux has soaked into the food.<PAGE>Tainted Food<DIV>Fill a crucible with enough vitium and drop the food into it as the catalyst. Bread, apples, carrots, baked potatoes, steak, cooked porkchops, pumpkin pies and mushroom stew each have their own recipe, and so do the Ultimate Stew and the Cosmic Meatballs of Avaritia.<BR>The tainted food fills more hunger and more saturation than the original and keeps its effects, but whoever eats it is struck by Flux Taint for a while.");
    }

    public static void spanish(LanguageProvider lang) {
        for (TaintedFoodGender gender : TaintedFoodGender.values()) {
            lang.add(gender.nameKey(), "%s " + spanishSuffix(gender));
        }
        entry(lang, "Comida contaminada",
                "El flujo que echa a perder un campo también se puede convencer para que entre en una hogaza de pan. Si alimento el crisol con suficiente vitium, cualquier comida que deje caer en él debería salir más cargada de hambre, y con algo más.",
                "Un poco de flujo ha empapado la comida.<PAGE>Comida contaminada<DIV>Llena un crisol con suficiente vitium y echa la comida como catalizador. El pan, las manzanas, las zanahorias, las patatas asadas, el filete, las chuletas de cerdo cocinadas, los pasteles de calabaza y el estofado de champiñones tienen cada uno su receta, igual que el Ultimate Stew y las Cosmic Meatballs de Avaritia.<BR>La comida contaminada sacia más hambre y más saturación que la original y conserva sus efectos, pero quien la come sufre Corrupción por flujo durante un tiempo.");
    }

    private static String spanishSuffix(TaintedFoodGender gender) {
        return switch (gender) {
            case MASCULINE -> "contaminado";
            case FEMININE -> "contaminada";
            case MASCULINE_PLURAL -> "contaminados";
            case FEMININE_PLURAL -> "contaminadas";
        };
    }

    private static void entry(LanguageProvider lang, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + TaintedFoodContent.ENTRY;
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
