package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class IndustrialForegoingAddonsTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private IndustrialForegoingAddonsTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (AddonTier tier : AddonTier.values()) {
            for (AddonKind kind : AddonKind.values()) {
                lang.add("item." + MOD + "." + IndustrialForegoingAddonsIntegration.itemId(kind, tier),
                        tier.english() + " " + kind.english() + " Addon");
            }
        }
        lang.add(IndustrialForegoingAddonItem.tooltipKey(AddonKind.SPEED), "Speed Increase: +%s%%");
        lang.add(IndustrialForegoingAddonItem.tooltipKey(AddonKind.EFFICIENCY), "Processing Time Reduction: -%s%%");
        lang.add(IndustrialForegoingAddonItem.tooltipKey(AddonKind.PROCESSING), "Processing: x%s");
        lang.add(IndustrialForegoingAddonItem.tooltipKey(AddonKind.RANGE), "Extra Range: +%s");

        entry(lang, AddonTier.BRASS, "Brass Industrial Addons",
                "The machines of Industrial Foregoing are hungry for something better than gold and diamond. Their dissolution chambers drink latex from rubber trees, but I know a far deadlier solvent. If Liquid Death can eat through flesh and bone, it should have no trouble melting brass plates into a chip that a machine will accept.",
                "The chip has set. Speed, efficiency, processing and range: a brass addon for each, and every one of them stronger than the diamond originals.<PAGE>Brass Addons<DIV>They are made in an Industrial Foregoing dissolution chamber from three brass plates, redstone, a glass pane, the material of each addon and Liquid Death. Place them in the addon slots of a machine, or use them on the machine directly. The values can be changed in the configuration.");
        entry(lang, AddonTier.THAUMIUM, "Thaumium Industrial Addons",
                "Brass carries the current, but thaumium carries the intent. A chip wrapped in thaumium and bound with a simple mechanism should tell a machine not only to work harder, but how.",
                "The thaumium has taken to the chip like a second circuit.<PAGE>Thaumium Addons<DIV>Each one is made in an Industrial Foregoing dissolution chamber from the brass addon of the same kind, three thaumium plates, a simple mechanism, the material of the addon, a diamond and a larger measure of Liquid Death.");
        entry(lang, AddonTier.VOID, "Void Metal Industrial Addons",
                "A machine with a void metal chip does not so much work faster as work from somewhere the clock does not reach. The void swallows the waiting between one operation and the next.",
                "The chips are dark, cold and impossibly quick.<PAGE>Void Metal Addons<DIV>Each one is made in an Industrial Foregoing dissolution chamber from the thaumium addon of the same kind, three void metal plates, a complex mechanism, the material of the addon, an eye of ender and a great deal of Liquid Death.");
    }

    public static void spanish(LanguageProvider lang) {
        for (AddonTier tier : AddonTier.values()) {
            for (AddonKind kind : AddonKind.values()) {
                lang.add("item." + MOD + "." + IndustrialForegoingAddonsIntegration.itemId(kind, tier),
                        "Addon de " + kind.spanish() + " de " + tier.spanish());
            }
        }
        lang.add(IndustrialForegoingAddonItem.tooltipKey(AddonKind.SPEED), "Aumento de velocidad: +%s%%");
        lang.add(IndustrialForegoingAddonItem.tooltipKey(AddonKind.EFFICIENCY), "Reducción de tiempo de proceso: -%s%%");
        lang.add(IndustrialForegoingAddonItem.tooltipKey(AddonKind.PROCESSING), "Procesamiento: x%s");
        lang.add(IndustrialForegoingAddonItem.tooltipKey(AddonKind.RANGE), "Rango extra: +%s");

        entry(lang, AddonTier.BRASS, "Addons industriales de latón",
                "Las máquinas de Industrial Foregoing ansían algo mejor que el oro y el diamante. Sus cámaras de disolución beben látex de árboles de caucho, pero conozco un disolvente mucho más letal. Si la Muerte Líquida puede devorar carne y hueso, no le costará fundir placas de latón hasta formar un chip que una máquina acepte.",
                "El chip ha cuajado. Velocidad, eficiencia, procesamiento y rango: un addon de latón para cada uno, todos más fuertes que los originales de diamante.<PAGE>Addons de latón<DIV>Se fabrican en una cámara de disolución de Industrial Foregoing con tres placas de latón, redstone, un panel de cristal, el material propio de cada addon y Muerte Líquida. Se colocan en las ranuras de addons de una máquina, o se usan directamente sobre ella. Los valores se pueden cambiar en la configuración.");
        entry(lang, AddonTier.THAUMIUM, "Addons industriales de taumio",
                "El latón lleva la corriente, pero el taumio lleva la intención. Un chip envuelto en taumio y atado con un mecanismo simple debería indicarle a la máquina no solo que trabaje más, sino cómo.",
                "El taumio se ha pegado al chip como un segundo circuito.<PAGE>Addons de taumio<DIV>Cada uno se fabrica en una cámara de disolución de Industrial Foregoing con el addon de latón del mismo tipo, tres placas de taumio, un mecanismo simple, el material del addon, un diamante y una cantidad mayor de Muerte Líquida.");
        entry(lang, AddonTier.VOID, "Addons industriales de metal del vacío",
                "Una máquina con un chip de metal del vacío no trabaja más rápido, sino desde un lugar al que el reloj no llega. El vacío se traga la espera entre una operación y la siguiente.",
                "Los chips son oscuros, fríos e increíblemente rápidos.<PAGE>Addons de metal del vacío<DIV>Cada uno se fabrica en una cámara de disolución de Industrial Foregoing con el addon de taumio del mismo tipo, tres placas de metal del vacío, un mecanismo complejo, el material del addon, un ojo de ender y mucha Muerte Líquida.");
    }

    private static void entry(LanguageProvider lang, AddonTier tier, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + IndustrialForegoingAddonsContent.entryName(tier);
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
