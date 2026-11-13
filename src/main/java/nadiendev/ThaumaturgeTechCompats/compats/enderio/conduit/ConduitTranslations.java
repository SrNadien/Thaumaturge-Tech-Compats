package nadiendev.ThaumaturgeTechCompats.compats.enderio.conduit;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ConduitTranslations {
    private static final String CONDUIT = "conduit." + ThaumaturgeTechCompats.MOD_ID + ".";
    private static final String ENTRY = "research." + ThaumaturgeTechCompats.MOD_ID + "." + ConduitContent.ENTRY;

    private ConduitTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (ConduitKind kind : ConduitKind.values()) {
            for (EnderIOTier tier : EnderIOTier.values()) {
                lang.add(CONDUIT + kind.name(tier), tier.english() + " " + kind.english() + " Conduit");
            }
        }
        lang.add(ENTRY + ".title", "Magical Conduits");
        lang.add(ENTRY + ".stage_0",
                "The conduits of Ender IO carry power, items and fluids through walls and across rooms, but even the vibrant ones have a limit. If I bind thaumaturgic metal into them, the limit should be mine to set.<BR>Each metal I have mastered should give a conduit that outruns the one before it.");
        lang.add(ENTRY + ".stage_1",
                "Every conduit is made at the arcane workbench from the one below it, with four plates of the new metal, two Conduit Binders and a material of its kind: a Vibrant Alloy Ingot for energy, a Pulsating Alloy Ingot for items and a Vibrant Crystal for fluids. One conduit makes eight.<BR>The Thaumium conduits are made from the vibrant conduits of Ender IO, the Alchemical Brass ones from the Thaumium ones, and the Void Metal ones from the Alchemical Brass ones. Thaumium adds a fifth plate, Alchemical Brass a simple mechanism and Void Metal a void seed.<BR>The workbench also needs vis and crystals: ignis for energy, aer for items and aqua for fluids, with order crystals, and more of everything for each metal.<BR>Energy conduits move more RF every tick, item conduits move more items and cycle faster, and fluid conduits move more mB every tick. The exact values are set in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        for (ConduitKind kind : ConduitKind.values()) {
            for (EnderIOTier tier : EnderIOTier.values()) {
                lang.add(CONDUIT + kind.name(tier), "Conducto " + kind.spanish() + " " + tier.spanish());
            }
        }
        lang.add(ENTRY + ".title", "Conductos mágicos");
        lang.add(ENTRY + ".stage_0",
                "Los conductos de Ender IO llevan energía, objetos y fluidos a través de paredes y habitaciones, pero hasta los vibrantes tienen un límite. Si les ato metal taumatúrgico, el límite debería ponerlo yo.<BR>Cada metal que domine debería dar un conducto que deje atrás al anterior.");
        lang.add(ENTRY + ".stage_1",
                "Cada conducto se fabrica en la mesa arcana a partir del inferior, con cuatro placas del nuevo metal, dos Aglutinantes de conducto y un material de su clase: un Lingote de aleación vibrante para la energía, un Lingote de aleación pulsante para los objetos y un Cristal vibrante para los fluidos. De uno salen ocho.<BR>Los conductos de taumio se fabrican a partir de los conductos vibrantes de Ender IO, los de latón alquímico a partir de los de taumio y los de metal del vacío a partir de los de latón alquímico. El taumio añade una quinta placa, el latón alquímico un mecanismo simple y el metal del vacío una semilla del vacío.<BR>La mesa también pide vis y cristales: ignis para la energía, aer para los objetos y aqua para los fluidos, con cristales de orden, y más de todo con cada metal.<BR>Los conductos de energía mueven más RF por tick, los de objetos mueven más objetos y ciclan más rápido, y los de fluidos mueven más mB por tick. Los valores exactos se ajustan en la configuración.");
    }
}
