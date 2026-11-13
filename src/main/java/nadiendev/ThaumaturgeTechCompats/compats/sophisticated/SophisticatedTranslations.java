package nadiendev.ThaumaturgeTechCompats.compats.sophisticated;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class SophisticatedTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String ITEM = "item." + MOD + ".";
    private static final String BLOCK = "block." + MOD + ".";
    private static final String BUTTON = "gui." + MOD + ".upgrades.buttons.";
    private static final String ENTRY = "research." + MOD + ".";
    public static final String STACK_TOOLTIP = "item." + MOD + ".stack_upgrade.tooltip";
    public static final String[] UNIT_KEYS = {
            "tooltip." + MOD + ".unit.trillion",
            "tooltip." + MOD + ".unit.billion",
            "tooltip." + MOD + ".unit.million",
            "tooltip." + MOD + ".unit.thousand"};
    private static final String[] ROMAN = {"I", "II", "III", "IV"};

    private SophisticatedTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(STACK_TOOLTIP, "Multiplies the number of stacks that can fit in a slot by %s");
        lang.add(UNIT_KEYS[0], "trillion");
        lang.add(UNIT_KEYS[1], "billion");
        lang.add(UNIT_KEYS[2], "million");
        lang.add(UNIT_KEYS[3], "thousand");
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            String name = tier.english();
            lang.add(ITEM + tier.stackUpgradeName(), name + " Stack Upgrade");
            lang.add(ITEM + tier.backpackName(), name + " Backpack");
            lang.add(BLOCK + tier.backpackName(), name + " Backpack");
            lang.add(BLOCK + tier.barrelName(), name + " Barrel");
            for (int size = 1; size <= SophisticatedConfig.LIMITED_BARRELS; size++) {
                lang.add(BLOCK + tier.limitedBarrelName(size), "Limited " + name + " Barrel " + ROMAN[size - 1]);
            }
            lang.add(BLOCK + tier.chestName(), name + " Chest");
            lang.add(BLOCK + tier.shulkerBoxName(), name + " Shulker Box");
        }
        lang.add(ITEM + SophisticatedIntegration.ARCANE_UPGRADE_ITEM, "Arcane Workbench Upgrade");
        lang.add(ITEM + SophisticatedIntegration.ARCANE_UPGRADE_ITEM + ".tooltip",
                "Arcane workbench in an upgrade tab\nCrystals go in its own slots and the vis comes from the aura of the chunk you stand in");
        lang.add("gui." + MOD + ".upgrades.arcane_workbench", "Arcane Craft");
        lang.add("gui." + MOD + ".upgrades.arcane_workbench.tooltip", "Arcane Crafting");
        lang.add("gui." + MOD + ".upgrades.arcane_workbench.wand_slot", "Wand\nPays the vis of the crafts like the wand of an arcane workbench");
        lang.add("gui." + MOD + ".upgrades.arcane_workbench.charger_slot", "Workbench Charger\nGives its vis discount and vis radius to the crafts");
        lang.add("tooltip." + MOD + ".arcane_upgrade_charger.discount", "In an Arcane Workbench Upgrade: %s%% vis discount");
        lang.add("tooltip." + MOD + ".arcane_upgrade_charger.radius", "In an Arcane Workbench Upgrade: vis from %s chunks around you");
        lang.add(BUTTON + "shift_click_into_storage", "Shift Click Result Into\nStorage");
        lang.add(BUTTON + "shift_click_into_inventory", "Shift Click Result Into\nPlayer's Inventory");
        lang.add(BUTTON + "refill_crafting_grid", "Refill Crafting Grid");
        lang.add(BUTTON + "do_not_refill_crafting_grid", "Do Not Refill Crafting Grid");

        lang.add(ENTRY + SophisticatedContent.STACK_ENTRY + ".title", "Magical Stack Upgrades");
        lang.add(ENTRY + SophisticatedContent.STACK_ENTRY + ".stage_0",
                "The stack upgrades of the backpack makers stop at a multiplier that any warehouse outgrows. A plate of vis-forged metal in the same frame should keep holding items long after theirs would burst.<BR>Each metal I have mastered should hold far more than the one before it.");
        lang.add(ENTRY + SophisticatedContent.STACK_ENTRY + ".stage_1",
                "Every stack upgrade is infused from the one below it, with four plates of the new metal. The Thaumium Stack Upgrade is infused from the highest stack upgrade of Sophisticated Backpacks or Sophisticated Storage, the Alchemical Brass one from the Thaumium one, and the Void Metal one from the Alchemical Brass one. Alchemical Brass and Thaumium add a simple mechanism and Void Metal a void seed.<BR>They work in any backpack or storage like the upgrades of Sophisticated and share their limit of stack upgrades. The multiplier of each one is set in the configuration.");

        lang.add(ENTRY + SophisticatedContent.ARCANE_ENTRY + ".title", "Arcane Workbench Upgrade");
        lang.add(ENTRY + SophisticatedContent.ARCANE_ENTRY + ".stage_0",
                "I carry my tools in a pack, but the arcane workbench still ties me to one spot. If the crafting upgrade of the backpack makers can bring a workbench along, it should be able to bring mine too.<BR>It will have to take its vis from the aura beneath my feet, since I will not carry a wand for it.");
        lang.add(ENTRY + SophisticatedContent.ARCANE_ENTRY + ".stage_1",
                "The Arcane Workbench Upgrade is made on an arcane workbench from a Crafting Upgrade, an Arcane Workbench, thaumium plates and a few crystals of each primal aspect.<BR>In a backpack or storage it opens a tab identical to the one of the Crafting Upgrade, with the same crafting grid, result slot, shift click target and refill buttons, plus six slots for the primal crystals the recipes ask for, a wand slot that pays the vis like the wand of an arcane workbench, and a charger slot that takes a workbench charger and gives its vis discount and radius. It crafts every recipe of the arcane workbench I have researched, and the vis comes from the aura of the chunk I stand in.");

        lang.add(ENTRY + SophisticatedContent.BACKPACK_ENTRY + ".title", "Magical Backpacks");
        lang.add(ENTRY + SophisticatedContent.BACKPACK_ENTRY + ".stage_0",
                "The netherite backpack is the best the backpack makers can sew. A pack lined with vis-forged metal should hold more and wear more upgrades than that.<BR>Each metal I have mastered should give a backpack that beats the one before it.");
        lang.add(ENTRY + SophisticatedContent.BACKPACK_ENTRY + ".stage_1",
                "Every backpack is crafted from the one below it, surrounded by eight plates of the new metal, and keeps all its contents and upgrades. The Thaumium Backpack is made from a Netherite Backpack, the Alchemical Brass Backpack from the Thaumium one, and the Void Metal Backpack from the Alchemical Brass one.<BR>The number of inventory slots and upgrade slots of each one is set in the configuration.");

        lang.add(ENTRY + SophisticatedContent.STORAGE_ENTRY + ".title", "Magical Storage");
        lang.add(ENTRY + SophisticatedContent.STORAGE_ENTRY + ".stage_0",
                "Chests, barrels and shulker boxes of netherite are the best the storage makers can build, and my warehouse is already bursting. Vis-forged metal should let them hold and carry far more.<BR>Each metal I have mastered should give a storage that beats the one before it.");
        lang.add(ENTRY + SophisticatedContent.STORAGE_ENTRY + ".stage_1",
                "Every barrel, limited barrel, chest and shulker box is crafted from the one below it, surrounded by eight plates of the new metal, and keeps all its contents and upgrades. The Thaumium ones are made from the Netherite ones, the Alchemical Brass ones from the Thaumium ones, and the Void Metal ones from the Alchemical Brass ones.<BR>The inventory slots, upgrade slots and slot limit multipliers of each one are set in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(STACK_TOOLTIP, "Multiplica por %s el número de pilas que caben en una ranura");
        lang.add(UNIT_KEYS[0], "billones");
        lang.add(UNIT_KEYS[1], "mil millones");
        lang.add(UNIT_KEYS[2], "millones");
        lang.add(UNIT_KEYS[3], "mil");
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            String suffix = tier.spanish();
            lang.add(ITEM + tier.stackUpgradeName(), "Mejora de pila " + suffix);
            lang.add(ITEM + tier.backpackName(), "Mochila " + suffix);
            lang.add(BLOCK + tier.backpackName(), "Mochila " + suffix);
            lang.add(BLOCK + tier.barrelName(), "Barril " + suffix);
            for (int size = 1; size <= SophisticatedConfig.LIMITED_BARRELS; size++) {
                lang.add(BLOCK + tier.limitedBarrelName(size), "Barril limitado " + suffix + " " + ROMAN[size - 1]);
            }
            lang.add(BLOCK + tier.chestName(), "Cofre " + suffix);
            lang.add(BLOCK + tier.shulkerBoxName(), "Caja de shulker " + suffix);
        }
        lang.add(ITEM + SophisticatedIntegration.ARCANE_UPGRADE_ITEM, "Mejora de mesa arcana");
        lang.add(ITEM + SophisticatedIntegration.ARCANE_UPGRADE_ITEM + ".tooltip",
                "Mesa arcana en una pestaña de mejora\nLos cristales van en sus propias ranuras y la vis sale del aura del chunk donde estás");
        lang.add("gui." + MOD + ".upgrades.arcane_workbench", "Crafteo arcano");
        lang.add("gui." + MOD + ".upgrades.arcane_workbench.tooltip", "Crafteo arcano");
        lang.add("gui." + MOD + ".upgrades.arcane_workbench.wand_slot", "Varita\nPaga la vis de los crafteos como la varita de una mesa arcana");
        lang.add("gui." + MOD + ".upgrades.arcane_workbench.charger_slot", "Cargador de mesa\nDa su descuento de vis y su radio de vis a los crafteos");
        lang.add("tooltip." + MOD + ".arcane_upgrade_charger.discount", "En una Mejora de mesa arcana: %s%% de descuento de vis");
        lang.add("tooltip." + MOD + ".arcane_upgrade_charger.radius", "En una Mejora de mesa arcana: vis de %s chunks a tu alrededor");
        lang.add(BUTTON + "shift_click_into_storage", "Shift + clic: resultado al\nalmacenamiento");
        lang.add(BUTTON + "shift_click_into_inventory", "Shift + clic: resultado al\ninventario del jugador");
        lang.add(BUTTON + "refill_crafting_grid", "Reponer la cuadrícula de crafteo");
        lang.add(BUTTON + "do_not_refill_crafting_grid", "No reponer la cuadrícula de crafteo");

        lang.add(ENTRY + SophisticatedContent.STACK_ENTRY + ".title", "Mejoras de pila mágicas");
        lang.add(ENTRY + SophisticatedContent.STACK_ENTRY + ".stage_0",
                "Las mejoras de pila de los fabricantes de mochilas se quedan en un multiplicador que cualquier almacén supera. Una placa de metal forjado con vis en el mismo marco debería seguir aguantando objetos mucho después de que las suyas revienten.<BR>Cada metal que domine debería aguantar mucho más que el anterior.");
        lang.add(ENTRY + SophisticatedContent.STACK_ENTRY + ".stage_1",
                "Cada mejora de pila se infunde a partir de la anterior, con cuatro placas del metal nuevo. La Mejora de pila de taumio se infunde a partir de la mejora de pila más alta de Sophisticated Backpacks o de Sophisticated Storage, la de latón alquímico a partir de la de taumio y la de metal del vacío a partir de la de latón alquímico. El taumio y el latón alquímico añaden un mecanismo simple y el metal del vacío una semilla del vacío.<BR>Funcionan en cualquier mochila o almacenamiento como las mejoras de Sophisticated y comparten su límite de mejoras de pila. El multiplicador de cada una se fija en la configuración.");

        lang.add(ENTRY + SophisticatedContent.ARCANE_ENTRY + ".title", "Mejora de mesa arcana");
        lang.add(ENTRY + SophisticatedContent.ARCANE_ENTRY + ".stage_0",
                "Llevo mis herramientas en una mochila, pero la mesa arcana todavía me ata a un solo sitio. Si la mejora de crafteo de los fabricantes de mochilas puede llevarse una mesa de trabajo, debería poder llevarse también la mía.<BR>Tendrá que sacar su vis del aura bajo mis pies, ya que no pienso cargar con una varita para ella.");
        lang.add(ENTRY + SophisticatedContent.ARCANE_ENTRY + ".stage_1",
                "La Mejora de mesa arcana se fabrica en una mesa arcana con una Mejora de crafteo, una Mesa arcana, placas de taumio y unos cuantos cristales de cada aspecto primal.<BR>En una mochila o almacenamiento abre una pestaña idéntica a la de la Mejora de crafteo, con la misma cuadrícula, ranura de resultado y botones de destino del shift + clic y de reposición, más seis ranuras para los cristales primales que piden las recetas, una ranura de varita que paga la vis como la varita de una mesa arcana y una ranura de cargador que admite un cargador de mesa y da su descuento y su radio de vis. Fabrica todas las recetas de la mesa arcana que haya investigado, y la vis sale del aura del chunk donde estoy.");

        lang.add(ENTRY + SophisticatedContent.BACKPACK_ENTRY + ".title", "Mochilas mágicas");
        lang.add(ENTRY + SophisticatedContent.BACKPACK_ENTRY + ".stage_0",
                "La mochila de netherita es lo mejor que saben coser los fabricantes de mochilas. Una mochila forrada de metal forjado con vis debería guardar más y llevar más mejoras que esa.<BR>Cada metal que domine debería dar una mochila mejor que la anterior.");
        lang.add(ENTRY + SophisticatedContent.BACKPACK_ENTRY + ".stage_1",
                "Cada mochila se fabrica a partir de la anterior, rodeada de ocho placas del metal nuevo, y conserva todo su contenido y sus mejoras. La Mochila de taumio se hace con una Mochila de netherita, la de latón alquímico con la de taumio y la de metal del vacío con la de latón alquímico.<BR>El número de ranuras de inventario y de mejora de cada una se fija en la configuración.");

        lang.add(ENTRY + SophisticatedContent.STORAGE_ENTRY + ".title", "Almacenamiento mágico");
        lang.add(ENTRY + SophisticatedContent.STORAGE_ENTRY + ".stage_0",
                "Los cofres, barriles y cajas de shulker de netherita son lo mejor que saben construir los fabricantes de almacenamiento, y mi almacén ya revienta. El metal forjado con vis debería permitirles guardar y llevar mucho más.<BR>Cada metal que domine debería dar un almacenamiento mejor que el anterior.");
        lang.add(ENTRY + SophisticatedContent.STORAGE_ENTRY + ".stage_1",
                "Cada barril, barril limitado, cofre y caja de shulker se fabrica a partir del anterior, rodeado de ocho placas del metal nuevo, y conserva todo su contenido y sus mejoras. Los de taumio se hacen con los de netherita, los de latón alquímico con los de taumio y los de metal del vacío con los de latón alquímico.<BR>Las ranuras de inventario, las de mejora y los multiplicadores del límite de ranura de cada uno se fijan en la configuración.");
    }
}
