package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class FtbUnearthedTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private FtbUnearthedTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("item." + MOD + "." + FtbUnearthedBrushTier.BRASS.itemId(), "Brass Brush");
        lang.add("item." + MOD + "." + FtbUnearthedBrushTier.THAUMIUM.itemId(), "Thaumium Brush");
        lang.add("item." + MOD + "." + FtbUnearthedBrushTier.VOID.itemId(), "Void Metal Brush");

        entry(lang, FtbUnearthedBrushTier.BRASS, "Brass Brush",
                "Fine bristles and a handle of brass are all it takes to separate a grain of something precious from a heap of sand. The villagers of the Unearther already know the trade; I only have to give them a better tool.",
                "The brass holds its edge and the bristles do not fray.<PAGE>Brass Brush<DIV>It is made at the arcane workbench from a feather, a brass plate and a stick. Used on sand or gravel, by hand or inside the Unearther of FTB Unearthed, it can unearth vis crystals, amber and raw cinnabar. The odds and the durability are the lowest of the three brushes.");
        entry(lang, FtbUnearthedBrushTier.THAUMIUM, "Thaumium Brush",
                "Thaumium listens to the hand that holds it. A brush of thaumium should feel the glint of amber under the sand before the bristles ever touch it.",
                "The brush trembles faintly whenever there is something worth finding.<PAGE>Thaumium Brush<DIV>It is made at the arcane workbench from a brass brush, three thaumium plates and a feather. It is sturdier and works faster than the brass brush, and it unearths vis crystals, amber and raw cinnabar far more often.");
        entry(lang, FtbUnearthedBrushTier.VOID, "Void Metal Brush",
                "The void does not sweep away the sand, it simply is not there, and whatever was hidden in it is left behind. A void brush should find treasure that no other tool could reach.",
                "The bristles are dark and weightless, and the sand parts for them.<PAGE>Void Metal Brush<DIV>It is made at the arcane workbench from a thaumium brush, three void metal plates and a feather. It is the most durable and the fastest of the three, and it gives the best odds of vis crystals, amber and raw cinnabar.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("item." + MOD + "." + FtbUnearthedBrushTier.BRASS.itemId(), "Brocha de latón");
        lang.add("item." + MOD + "." + FtbUnearthedBrushTier.THAUMIUM.itemId(), "Brocha de taumio");
        lang.add("item." + MOD + "." + FtbUnearthedBrushTier.VOID.itemId(), "Brocha de metal del vacío");

        entry(lang, FtbUnearthedBrushTier.BRASS, "Brocha de latón",
                "Unas cerdas finas y un mango de latón bastan para separar un grano de algo valioso de un montón de arena. Los aldeanos del Unearther ya conocen el oficio; solo tengo que darles una herramienta mejor.",
                "El latón conserva el filo y las cerdas no se deshilachan.<PAGE>Brocha de latón<DIV>Se fabrica en la mesa de trabajo arcana con una pluma, una placa de latón y un palo. Usada sobre arena o grava, a mano o dentro del Unearther de FTB Unearthed, puede desenterrar cristales de vis, ámbar y cinabrio en bruto. La probabilidad y la durabilidad son las más bajas de las tres brochas.");
        entry(lang, FtbUnearthedBrushTier.THAUMIUM, "Brocha de taumio",
                "El taumio escucha a la mano que lo sostiene. Una brocha de taumio debería notar el destello del ámbar bajo la arena antes de que las cerdas lo toquen.",
                "La brocha tiembla levemente cuando hay algo que merece ser encontrado.<PAGE>Brocha de taumio<DIV>Se fabrica en la mesa de trabajo arcana con una brocha de latón, tres placas de taumio y una pluma. Es más resistente y trabaja más rápido que la de latón, y desentierra cristales de vis, ámbar y cinabrio en bruto con mucha más frecuencia.");
        entry(lang, FtbUnearthedBrushTier.VOID, "Brocha de metal del vacío",
                "El vacío no barre la arena, simplemente no está, y lo que había escondido en ella queda atrás. Una brocha del vacío debería hallar tesoros que ninguna otra herramienta alcanza.",
                "Las cerdas son oscuras y no pesan, y la arena se aparta ante ellas.<PAGE>Brocha de metal del vacío<DIV>Se fabrica en la mesa de trabajo arcana con una brocha de taumio, tres placas de metal del vacío y una pluma. Es la más duradera y rápida de las tres, y da la mejor probabilidad de cristales de vis, ámbar y cinabrio en bruto.");
    }

    private static void entry(LanguageProvider lang, FtbUnearthedBrushTier tier, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + FtbUnearthedContent.entryName(tier);
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
