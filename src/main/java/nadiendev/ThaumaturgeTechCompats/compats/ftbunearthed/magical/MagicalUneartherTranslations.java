package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class MagicalUneartherTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private MagicalUneartherTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (MagicalUneartherTier tier : MagicalUneartherTier.values()) {
            lang.add("block." + MOD + "." + tier.blockName(), tier.english() + " Unearther");
        }
        entry(lang, MagicalUneartherTier.THAUMIUM, "Thaumium Unearther",
                "The villagers of the Unearther work faster when they are fed, but a thaumaturge has better things to offer than bread. A core cut from thaumium, which keeps whatever power is poured into it, set where the aura is strong, should be able to feed on that instead.",
                "The Thaumium Unearther is made at the arcane workbench from an Unearther core, four thaumium plates and two simple mechanisms. It is built with the same frame as the ordinary Unearther.<BR>It has no use for food: while a node, a node jar, a flux jar or a flux rift stands near it, it draws power from it every few moments and works faster. Without power it works at the normal speed, unless the settings say otherwise. A hungry node is never a safe source.");
        entry(lang, MagicalUneartherTier.BRASS, "Alchemical Brass Unearther",
                "Alchemical brass answers the aura better than thaumium. A core of brass should be able to drink deeper from the aura than one of thaumium, and spend it better.",
                "The Alchemical Brass Unearther is made at the arcane workbench from a Thaumium Unearther core, four alchemical brass plates and two simple mechanisms.<BR>It looks for power over a wider area and works much faster than the thaumium one, at the price of taking more power each time. A hungry node is never a safe source.");
        entry(lang, MagicalUneartherTier.VOID, "Void Metal Unearther",
                "There is nothing for sand to hold on to in the void. A core of void metal should let the work finish almost before it has begun.",
                "The Void Metal Unearther is made at the arcane workbench from an Alchemical Brass Unearther core, four void metal plates and two complex mechanisms.<BR>It is the fastest of the three and the one that reaches the farthest for power, and it is also the one that takes the most. A hungry node is never a safe source.");
    }

    public static void spanish(LanguageProvider lang) {
        for (MagicalUneartherTier tier : MagicalUneartherTier.values()) {
            lang.add("block." + MOD + "." + tier.blockName(), "Unearther de " + tier.spanish());
        }
        entry(lang, MagicalUneartherTier.THAUMIUM, "Unearther de taumio",
                "Los aldeanos del Unearther trabajan más rápido cuando comen, pero un taumaturgo tiene cosas mejores que ofrecerles que el pan. Un núcleo tallado en taumio, que conserva todo el poder que se vierte en él, puesto donde el aura es fuerte, debería poder alimentarse de ella.",
                "El Unearther de taumio se fabrica en la mesa de trabajo arcana con un núcleo de Unearther, cuatro placas de taumio y dos mecanismos simples. Se monta con el mismo marco que el Unearther normal.<BR>No necesita comida: mientras un nodo, un frasco con nodo, una jarra de flux o una grieta de flux esté cerca, saca poder de ella cada pocos instantes y trabaja más rápido. Sin poder trabaja a la velocidad normal, salvo que los ajustes digan otra cosa. Un nodo hambriento nunca es una fuente segura.");
        entry(lang, MagicalUneartherTier.BRASS, "Unearther de latón alquímico",
                "El latón alquímico responde al aura mejor que el taumio. Un núcleo de latón debería poder beber más hondo del aura que uno de taumio, y gastarlo mejor.",
                "El Unearther de latón alquímico se fabrica en la mesa de trabajo arcana con un núcleo de Unearther de taumio, cuatro placas de latón alquímico y dos mecanismos simples.<BR>Busca poder en una zona más amplia y trabaja mucho más rápido que el de taumio, a cambio de tomar más poder cada vez. Un nodo hambriento nunca es una fuente segura.");
        entry(lang, MagicalUneartherTier.VOID, "Unearther de metal del vacío",
                "En el vacío la arena no tiene dónde agarrarse. Un núcleo de metal del vacío debería dejar el trabajo hecho casi antes de empezarlo.",
                "El Unearther de metal del vacío se fabrica en la mesa de trabajo arcana con un núcleo de Unearther de latón alquímico, cuatro placas de metal del vacío y dos mecanismos complejos.<BR>Es el más rápido de los tres y el que llega más lejos en busca de poder, y también el que más toma. Un nodo hambriento nunca es una fuente segura.");
    }

    private static void entry(LanguageProvider lang, MagicalUneartherTier tier, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + tier.entryName();
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
