package nadiendev.ThaumaturgeTechCompats.compats.botania.wand;

import net.neoforged.neoforge.common.data.LanguageProvider;

public final class BotaniaWandTranslations {
    private static final String ITEM = "item.thaumaturgetechcompats.";
    private static final String ENTRY = "research.thaumaturgetechcompats." + BotaniaWandRegistry.ENTRY;

    private BotaniaWandTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (BotaniaWandRod rod : BotaniaWandRod.values()) {
            lang.add(ITEM + rod.itemName(), rod.english() + " Rod");
            lang.add("wand.thaumaturge.rod." + rod.id(), rod.english());
        }
        for (BotaniaWandCap cap : BotaniaWandCap.values()) {
            lang.add(ITEM + cap.itemName(), cap.english() + " Cap");
            lang.add("wand.thaumaturge.cap." + cap.id(), cap.english() + " Capped");
        }
        lang.add(ENTRY + ".title", "Botanical Wands");
        lang.add(ENTRY + ".stage_0",
                "The botanists grow wood that is alive and wood that dreams, and forge steel from mana, from the elves and from the earth itself. All of it hums with a power that is not vis, yet it should still be bent into a wand.<BR>A rod of living or dreaming wood and a cap of one of their metals should make a wand of a kind nobody has built before.");
        lang.add(ENTRY + ".stage_1",
                "The rod and the cap are separate parts, and any rod fits any cap:<BR>- The Livingwood Rod is crafted from three Livingwood Twigs at an arcane workbench. The Dreamwood Rod is infused from it with four Dreamwood Twigs.<BR>- The Manasteel Cap is crafted from five Manasteel Ingots at an arcane workbench. The Elementium Cap and the Terrasteel Cap are infused from it, with Elementium Ingots and Pixie Dust or with Terrasteel Ingots and a Mana Diamond.<BR>Dreamwood holds more vis than livingwood. The Manasteel and Terrasteel caps make every spell cheaper, and the Elementium Cap makes the primal aspects of air, water, earth and fire cheaper still. The exact values are set in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        for (BotaniaWandRod rod : BotaniaWandRod.values()) {
            lang.add(ITEM + rod.itemName(), "Vara " + rod.spanish());
            lang.add("wand.thaumaturge.rod." + rod.id(), rod.spanish());
        }
        for (BotaniaWandCap cap : BotaniaWandCap.values()) {
            lang.add(ITEM + cap.itemName(), "Pomo " + cap.spanish());
            lang.add("wand.thaumaturge.cap." + cap.id(), cap.spanish().replaceFirst("^de ", ""));
        }
        lang.add(ENTRY + ".title", "Varitas botánicas");
        lang.add(ENTRY + ".stage_0",
                "Los botánicos cultivan madera que vive y madera que sueña, y forjan acero con maná, con los elfos y con la propia tierra. Todo ello vibra con un poder que no es vis, pero aun así debería poder doblegarse en una varita.<BR>Una vara de madera viva o soñadora y un pomo de uno de sus metales deberían dar una varita como nadie ha construido antes.");
        lang.add(ENTRY + ".stage_1",
                "La vara y el pomo son piezas separadas, y cualquier vara encaja con cualquier pomo:<BR>- La Vara de Madera Viviente se fabrica con tres Ramitas de madera viviente en una mesa de trabajo arcana. La Vara de Madera Onírica se infunde a partir de ella con cuatro Ramitas de madera onírica.<BR>- El Pomo de Manacero se fabrica con cinco Lingotes de manacero en una mesa de trabajo arcana. El Pomo de Elementio y el Pomo de Terracero se infunden a partir de él, con Lingotes de elementio y Polvo de hada o con Lingotes de terracero y un Diamante de maná.<BR>La madera onírica guarda más vis que la viviente. Los pomos de manacero y de terracero abaratan cada hechizo, y el de elementio abarata todavía más los aspectos primarios de aire, agua, tierra y fuego. Los valores exactos se ajustan en la configuración.");
    }
}
