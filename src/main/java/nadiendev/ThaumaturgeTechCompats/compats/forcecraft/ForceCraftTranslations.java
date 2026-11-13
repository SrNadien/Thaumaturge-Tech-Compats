package nadiendev.ThaumaturgeTechCompats.compats.forcecraft;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ForceCraftTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private ForceCraftTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(ForceCraftRepair.TOOLTIP_KEY, "Repair infusion: mends itself with vis from the aura");
        entry(lang, "Force Repair",
                "The Force Infuser can bind a charge into a tool, but it knows nothing of mending. An order shard, though, is order itself, and order is what a broken edge longs for. If the infuser feeds one to a tool, it should learn to drink the aura and put itself back together.",
                "The infuser has taught the tool to mend itself.<PAGE>Repair<DIV>Place an Order Shard in one of the eight slots around a Force tool, a Force armor piece or a Force Rod, with an Upgrade Tome of the seventh tier in the book slot. The infusion can be added to a tool that is already infused.<BR>From then on the item slowly repairs itself while it is in the inventory, drawing vis from the aura of the chunk where its owner stands. If the aura is empty, nothing is repaired.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(ForceCraftRepair.TOOLTIP_KEY, "Infusión de reparación: se repara con vis del aura");
        entry(lang, "Reparación de la Fuerza",
                "El Infusor de la Fuerza puede atar una carga a una herramienta, pero no sabe nada de reparar. Un fragmento de orden, en cambio, es el orden en sí, y el orden es lo que anhela un filo roto. Si el infusor se lo da a una herramienta, debería aprender a beber del aura y recomponerse sola.",
                "El infusor ha enseñado a la herramienta a repararse a sí misma.<PAGE>Reparación<DIV>Coloca un Fragmento de Orden en una de las ocho ranuras alrededor de una herramienta de Fuerza, una pieza de armadura de Fuerza o una Vara de Fuerza, con un Tomo de Mejora de nivel siete en la ranura del libro. La infusión se puede añadir a una herramienta que ya esté infundida.<BR>A partir de entonces el objeto se repara despacio mientras esté en el inventario, tomando vis del aura del chunk donde está su dueño. Si el aura está vacía, no se repara nada.");
    }

    private static void entry(LanguageProvider lang, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + ForceCraftContent.REPAIR_ENTRY;
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
