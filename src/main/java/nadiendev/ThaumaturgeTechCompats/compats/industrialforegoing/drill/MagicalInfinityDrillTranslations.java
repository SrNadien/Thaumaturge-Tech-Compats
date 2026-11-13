package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class MagicalInfinityDrillTranslations {
    private static final String ENTRY = "research." + ThaumaturgeTechCompats.MOD_ID + "." + MagicalInfinityDrillContent.RESEARCH;
    private static final String ITEM = "item." + ThaumaturgeTechCompats.MOD_ID + "." + MagicalInfinityDrillRegistry.NAME;

    private MagicalInfinityDrillTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(ITEM, "Magical Infinity Drill");
        lang.add(ENTRY + ".title", "Magical Infinity Drill");
        lang.add(ENTRY + ".stage_0",
                "The Infinity Drill of the industrial engineers gets bigger the more power it swallows, and it swallows a lot of it. It is a hungry, mundane thing that needs a charger, a cable and a tank of fuel.<BR>I know how to store vis inside an object and how to make a tool recharge itself from the aura around me. I also know a liquid that devours everything it touches. Put together, they should feed a drill that grows with the magic it holds.");
        lang.add(ENTRY + ".stage_1",
                "The Magical Infinity Drill is born on an infusion altar from an Infinity Drill. It mines a whole area at once and drops everything at my feet, just like the original, but it holds vis instead of power. A Recharge Pedestal fills it, and while its charging is on it also draws vis slowly from the aura around me.<BR>The more vis it holds, the wider the area: nothing at first, then 3x3, 5x5, 7x7, 9x9, 11x11 and, when it is almost full, the Artifact level, a 13x13x13 cube. Every mined block costs 1 vis and the drill falls back to a smaller level as it empties. Its tank takes Liquid Death, which is spent instead of vis without lowering the level. Right click on a source of Liquid Death fills the tank.<BR>Shift and right click steps the area down one level, wrapping back to the largest one. Right click on air turns the charging on or off. The vis cost, the levels, the speed, the tank and the rest can be changed in its configuration.");

        lang.add(MagicalInfinityDrillItem.TOOLTIP + "area", "Current area: %s");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "level", "Level: %s");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "vis", "Vis: %s/%s vis for the next level");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "vis_full", "Vis: %s/%s vis");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "liquid", "Liquid: %s/%s mB of Liquid Death");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "max_area", "Max area: %s");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "charging", "Charging: %s");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "enabled", "Enabled");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "disabled", "Disabled");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "glowing", "Glowing");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "hint_area", "Shift + right click changes the area");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "hint_charge", "Right click toggles charging, or fills the tank from Liquid Death");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.poor", "Poor");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.common", "Common");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.uncommon", "Uncommon");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.rare", "Rare");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.epic", "Epic");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.legendary", "Legendary");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.artifact", "Artifact");
        lang.add(MagicalInfinityDrillItem.MESSAGE + "area", "Mining area: %s");
        lang.add(MagicalInfinityDrillItem.MESSAGE + "charging_on", "Charging enabled");
        lang.add(MagicalInfinityDrillItem.MESSAGE + "charging_off", "Charging disabled");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(ITEM, "Taladro del Infinito Mágico");
        lang.add(ENTRY + ".title", "Taladro del Infinito Mágico");
        lang.add(ENTRY + ".stage_0",
                "El Taladro del Infinito de los ingenieros industriales crece cuanta más energía se traga, y se traga muchísima. Es una cosa hambrienta y mundana que necesita un cargador, un cable y un depósito de combustible.<BR>Sé cómo guardar vis dentro de un objeto y cómo hacer que una herramienta se recargue sola con el aura que me rodea. También conozco un líquido que devora todo lo que toca. Juntos deberían alimentar un taladro que crece con la magia que contiene.");
        lang.add(ENTRY + ".stage_1",
                "El Taladro del Infinito Mágico nace en un altar de infusión a partir de un Taladro del Infinito. Mina un área entera de una vez y suelta todo a mis pies, igual que el original, pero guarda vis en lugar de energía. Un Pedestal de Recarga lo llena, y mientras su carga está activada también extrae vis despacio del aura que me rodea.<BR>Cuanto más vis contiene, más ancha es el área: nada al principio, luego 3x3, 5x5, 7x7, 9x9, 11x11 y, cuando está casi lleno, el nivel Artefacto, un cubo de 13x13x13. Cada bloque minado cuesta 1 vis y el taladro baja a un nivel menor a medida que se vacía. Su depósito admite Muerte Líquida, que se gasta en lugar del vis sin bajar el nivel. Clic derecho sobre una fuente de Muerte Líquida llena el depósito.<BR>Mayús y clic derecho baja el área un nivel y vuelve al mayor. Clic derecho en el aire activa o desactiva la carga. El coste de vis, los niveles, la velocidad, el depósito y el resto se pueden cambiar en su configuración.");

        lang.add(MagicalInfinityDrillItem.TOOLTIP + "area", "Área actual: %s");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "level", "Nivel: %s");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "vis", "Vis: %s/%s vis para el próximo nivel");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "vis_full", "Vis: %s/%s vis");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "liquid", "Líquido: %s/%s mB de Muerte Líquida");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "max_area", "Área máxima: %s");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "charging", "Cargando: %s");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "enabled", "Activado");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "disabled", "Desactivado");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "glowing", "Brillante");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "hint_area", "Mayús + clic derecho cambia el área");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "hint_charge", "Clic derecho activa la carga, o llena el depósito con Muerte Líquida");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.poor", "Pobre");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.common", "Común");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.uncommon", "Poco común");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.rare", "Raro");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.epic", "Épico");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.legendary", "Legendario");
        lang.add(MagicalInfinityDrillItem.TOOLTIP + "tier.artifact", "Artefacto");
        lang.add(MagicalInfinityDrillItem.MESSAGE + "area", "Área de minado: %s");
        lang.add(MagicalInfinityDrillItem.MESSAGE + "charging_on", "Carga activada");
        lang.add(MagicalInfinityDrillItem.MESSAGE + "charging_off", "Carga desactivada");
    }
}
