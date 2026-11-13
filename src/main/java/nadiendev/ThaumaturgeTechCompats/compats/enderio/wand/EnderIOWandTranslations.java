package nadiendev.ThaumaturgeTechCompats.compats.enderio.wand;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class EnderIOWandTranslations {
    private static final String ITEM = "item." + ThaumaturgeTechCompats.MOD_ID + ".";
    private static final String ENTRY = "research." + ThaumaturgeTechCompats.MOD_ID + "." + EnderIOWandRegistry.ENTRY;

    private EnderIOWandTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            lang.add(ITEM + material.rodName(), material.english() + " Rod");
            lang.add(ITEM + material.capName(), material.english() + " Cap");
            lang.add("wand.thaumaturge.rod." + material.id(), material.english());
            lang.add("wand.thaumaturge.cap." + material.id(), material.english() + " Capped");
        }
        lang.add(ENTRY + ".title", "Ender IO Wands");
        lang.add(ENTRY + ".stage_0",
                "The alloys of Ender IO were made to carry power, and power is only a step away from vis. A rod and a cap drawn from them, from the humble redstone alloy to the end steel forged beyond the world, should make wands that grow stronger with every metal.<BR>Ten materials, ten rods and ten caps, each one better than the last.");
        lang.add(ENTRY + ".stage_1",
                "The rod and the cap are separate parts, and any rod fits any cap. The materials, from weakest to strongest, are redstone alloy, conductive alloy, pulsating alloy, energetic alloy, dark steel, soularium, pulsating crystal, vibrant alloy, vibrant crystal and end steel.<BR>- The first three are crafted at an arcane workbench: a rod from three ingots and a cap from five.<BR>- Every other rod and cap is infused from the part of the material before it, with four ingots, or four crystals for the crystal ones.<BR>Stronger materials hold more vis in the rod and make every spell cheaper with the cap, and each cap makes its own aspects cheaper still. The crystals, the vibrant alloy and the end steel glow. The exact values are set in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        for (EnderIOWandMaterial material : EnderIOWandMaterial.values()) {
            lang.add(ITEM + material.rodName(), "Vara " + material.spanish());
            lang.add(ITEM + material.capName(), "Pomo " + material.spanish());
            lang.add("wand.thaumaturge.rod." + material.id(), material.spanish());
            lang.add("wand.thaumaturge.cap." + material.id(), capitalize(material.spanish().replaceFirst("^de ", "")));
        }
        lang.add(ENTRY + ".title", "Varitas de Ender IO");
        lang.add(ENTRY + ".stage_0",
                "Las aleaciones de Ender IO se hicieron para llevar energía, y la energía está a un paso del vis. Una vara y un pomo sacados de ellas, desde la humilde aleación de redstone hasta el acero del End forjado más allá del mundo, deberían dar varitas que se hacen más fuertes con cada metal.<BR>Diez materiales, diez varas y diez pomos, cada uno mejor que el anterior.");
        lang.add(ENTRY + ".stage_1",
                "La vara y el pomo son piezas separadas, y cualquier vara encaja con cualquier pomo. Los materiales, del más débil al más fuerte, son aleación de redstone, aleación conductora, aleación pulsante, aleación energética, acero oscuro, soularium, cristal pulsante, aleación vibrante, cristal vibrante y acero del End.<BR>- Los tres primeros se fabrican en una mesa de trabajo arcana: una vara con tres lingotes y un pomo con cinco.<BR>- Todas las demás varas y pomos se infunden a partir de la pieza del material anterior, con cuatro lingotes, o cuatro cristales en los de cristal.<BR>Los materiales más fuertes guardan más vis en la vara y abaratan cada hechizo con el pomo, y cada pomo abarata todavía más sus propios aspectos. Los cristales, la aleación vibrante y el acero del End brillan. Los valores exactos se ajustan en la configuración.");
    }

    private static String capitalize(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
