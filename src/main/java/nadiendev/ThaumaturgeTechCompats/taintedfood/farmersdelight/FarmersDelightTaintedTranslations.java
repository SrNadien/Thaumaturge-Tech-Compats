package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class FarmersDelightTaintedTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private FarmersDelightTaintedTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(FarmersDelightKnife.taintTooltipKey(), "Its edge carries Flux Taint into every wound.");
        entry(lang, FarmersDelightTaintedContent.ENTRY_FOOD, "Tainted Farmer's Feasts",
                "The flux that spoils a field has no respect for a hearth either. If a stew simmered in the crucible takes in enough vitium, the meals of Farmer's Delight should come out heavier with hunger, and with something else besides.",
                "The soup smells wrong, and yet it smells wonderful.<PAGE>Tainted Farmer's Feasts<DIV>Fill a crucible with enough vitium and drop the meal into it as the catalyst. Beef stew, chicken soup, vegetable soup, fish stew, baked cod stew, noodle soup, fried rice, ratatouille, steak and potatoes, hamburgers, bacon sandwiches, cooked bacon, beef patties and dumplings each have their own recipe.<BR>A tainted meal fills more hunger and more saturation than the original and keeps its effects, but whoever eats it is struck by Flux Taint for a while.<PAGE>The Cooking Pot<DIV>The Cooking Pot and the Cutting Board of Farmer's Delight accept tainted ingredients too. Tainted cooked beef, carrots and baked potatoes in the pot give a tainted beef stew, steak and potatoes or vegetable soup, and fried rice if there is rice, eggs and onion beside them. A knife on the Cutting Board turns tainted cooked beef into tainted beef patties and tainted cooked porkchops into tainted cooked bacon.");
        knives(lang, "Thaumium Knife", "Alchemical Brass Knife", "Void Metal Knife",
                "A plate of thaumium, a stick and a little vis. Farmer's Delight already taught me what a good knife is for; thaumium should give it a better edge.",
                "The thaumium takes a keener edge than iron ever did, and it hums when it cuts.<PAGE>Thaumium Knife<DIV>Craft it at the arcane workbench with a thaumium plate over a stick. It is a Farmer's Delight knife in every way: it cuts on the Cutting Board and counts as a knife for any recipe that asks for one. It lasts longer, hits harder and enchants better than the iron knife.",
                "Thaumium was only the beginning. Alchemical brass, worked into the same blade, should keep an edge that no mundane steel could.",
                "The brass keeps its edge as the thaumium cannot.<PAGE>Alchemical Brass Knife<DIV>Craft it at the arcane workbench with an alchemical brass plate over a Thaumium Knife. It works like any Farmer's Delight knife and is sturdier, sharper and easier to enchant than the thaumium one.",
                "The void metal drinks everything it touches. A blade of it would cut deeper than any other, and it would not cut clean.",
                "The void drinks the light off the blade, and what it cuts does not heal clean.<PAGE>Void Metal Knife<DIV>Craft it at the arcane workbench with a void metal plate over an Alchemical Brass Knife. It is the best Farmer's Delight knife I can make, on the Cutting Board and in the hand. Sometimes a hit with it leaves Flux Taint on whatever it struck.");
        fluxKnife(lang, "Flux Knife",
                "Sneak and use it on a block to open a flux rift beside it, sized to the flux that the aura can give. It wears the blade, and it needs a pause before the next one.",
                "Use it on a block to open a flux rift beside it, sized to the flux that the aura can give. It wears the blade, and it needs a pause before the next one.",
                "Food cut with it on a Cutting Board turns into flux and is added to the air.",
                "The aura does not hold enough to open a rift here.",
                "The void metal blade does not only drink what it cuts. If I feed it vitium, it should bite into the world itself and leave a seam, and whatever it slices at the table should leave as flux in the air.",
                "A seam in the air, and the kitchen smells of rust.<PAGE>Flux Knife<DIV>Infuse a void metal knife with two Flux Crystals, Tainted Goo and a Bottle of Taint. Sneak and use it on a block to open a flux rift beside it. The rift is sized to the flux it takes from the aura of that chunk, and if the chunk does not have enough the knife opens nothing. Every rift wears the blade and the knife needs a pause before the next one.<BR>On the Cutting Board it is a Farmer's Delight knife in every way, except that the food it cuts also adds flux to the air, more for what feeds better. Depending on the configuration it may not leave the food at all.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(FarmersDelightKnife.taintTooltipKey(), "Su filo lleva Corrupción por flujo a cada herida.");
        entry(lang, FarmersDelightTaintedContent.ENTRY_FOOD, "Festines contaminados de Farmer's Delight",
                "El flujo que echa a perder un campo tampoco respeta el hogar. Si un guiso que hierve en el crisol absorbe suficiente vitium, las comidas de Farmer's Delight deberían salir más cargadas de hambre, y con algo más.",
                "La sopa huele mal, y aun así huele de maravilla.<PAGE>Festines contaminados de Farmer's Delight<DIV>Llena un crisol con suficiente vitium y echa la comida como catalizador. El estofado de carne, la sopa de pollo, la sopa de verduras, el estofado de pescado, el estofado de bacalao al horno, la sopa de fideos, el arroz frito, el ratatouille, el bistec y patatas, las hamburguesas, los sándwiches de tocino, el tocino cocido, las hamburguesas de ternera y las albóndigas tienen cada uno su receta.<BR>Una comida contaminada sacia más hambre y más saturación que la original y conserva sus efectos, pero quien la come sufre Corrupción por flujo durante un tiempo.<PAGE>La olla de cocina<DIV>La olla de cocina y la tabla de cortar de Farmer's Delight también aceptan ingredientes contaminados. Filetes cocidos, zanahorias y patatas asadas contaminados en la olla dan un estofado de carne contaminado, un bistec y patatas contaminado o una sopa de verduras contaminada, y un arroz frito contaminado si hay arroz, huevos y cebolla junto a ellos. Un cuchillo sobre la tabla de cortar convierte el filete cocido contaminado en hamburguesas de ternera contaminadas, y las chuletas de cerdo cocinadas contaminadas en tocino cocido contaminado.");
        knives(lang, "Cuchillo de taumio", "Cuchillo de latón alquímico", "Cuchillo de metal del vacío",
                "Una placa de taumio, un palo y un poco de vis. Farmer's Delight ya me enseñó para qué sirve un buen cuchillo; el taumio debería darle mejor filo.",
                "El taumio toma un filo más fino del que el hierro tuvo jamás, y zumba al cortar.<PAGE>Cuchillo de taumio<DIV>Fabrícalo en la mesa de trabajo arcana con una placa de taumio sobre un palo. Es un cuchillo de Farmer's Delight en todo: corta en la tabla de cortar y cuenta como cuchillo para cualquier receta que pida uno. Dura más, golpea más fuerte y se encanta mejor que el cuchillo de hierro.",
                "El taumio fue solo el principio. El latón alquímico, forjado en la misma hoja, debería conservar un filo que ningún acero común podría igualar.",
                "El latón conserva el filo como no puede el taumio.<PAGE>Cuchillo de latón alquímico<DIV>Fabrícalo en la mesa de trabajo arcana con una placa de latón alquímico sobre un Cuchillo de taumio. Funciona como cualquier cuchillo de Farmer's Delight y es más resistente, más afilado y más fácil de encantar que el de taumio.",
                "El metal del vacío bebe todo lo que toca. Una hoja de él cortaría más hondo que ninguna otra, y no cortaría limpio.",
                "El vacío bebe la luz de la hoja, y lo que corta no cicatriza limpio.<PAGE>Cuchillo de metal del vacío<DIV>Fabrícalo en la mesa de trabajo arcana con una placa de metal del vacío sobre un Cuchillo de latón alquímico. Es el mejor cuchillo de Farmer's Delight que puedo hacer, en la tabla de cortar y en la mano. A veces un golpe con él deja Corrupción por flujo en lo que ha herido.");
        fluxKnife(lang, "Cuchillo de flujo",
                "Agáchate y úsalo sobre un bloque para abrir una grieta de flujo a su lado, del tamaño del flujo que el aura pueda dar. Desgasta la hoja y necesita una pausa antes de la siguiente.",
                "Úsalo sobre un bloque para abrir una grieta de flujo a su lado, del tamaño del flujo que el aura pueda dar. Desgasta la hoja y necesita una pausa antes de la siguiente.",
                "La comida que se corta con él en la tabla de cortar se vuelve flujo y se suma al aire.",
                "El aura no tiene suficiente para abrir una grieta aquí.",
                "La hoja de metal del vacío no solo bebe lo que corta. Si le doy vitium, debería morder el mundo mismo y dejar una costura, y lo que rebane en la mesa debería irse como flujo al aire.",
                "Una costura en el aire, y la cocina huele a óxido.<PAGE>Cuchillo de flujo<DIV>Infunde un cuchillo de metal del vacío con dos cristales brutos de Vitium, Tainted Goo y una Botella de la Corrupción. Agáchate y úsalo sobre un bloque para abrir una grieta de flujo a su lado. La grieta tiene el tamaño del flujo que toma del aura de ese chunk, y si el chunk no tiene suficiente el cuchillo no abre nada. Cada grieta desgasta la hoja y el cuchillo necesita una pausa antes de la siguiente.<BR>En la tabla de cortar es un cuchillo de Farmer's Delight en todo, salvo que la comida que corta también suma flujo al aire, más cuanto más alimenta. Según la configuración puede que ni siquiera deje la comida.");
    }

    private static void knives(LanguageProvider lang, String thaumiumName, String brassName, String voidName,
                               String thaumiumStage0, String thaumiumStage1, String brassStage0, String brassStage1,
                               String voidStage0, String voidStage1) {
        knife(lang, FarmersDelightKnife.THAUMIUM, thaumiumName, thaumiumStage0, thaumiumStage1);
        knife(lang, FarmersDelightKnife.ALCHEMICAL_BRASS, brassName, brassStage0, brassStage1);
        knife(lang, FarmersDelightKnife.VOID_METAL, voidName, voidStage0, voidStage1);
    }

    private static void fluxKnife(LanguageProvider lang, String name, String riftSneakTooltip, String riftTooltip,
                                  String cutTooltip, String noAura, String stage0, String stage1) {
        lang.add(FluxKnife.nameKey(), name);
        lang.add(FluxKnife.RIFT_SNEAK_TOOLTIP_KEY, riftSneakTooltip);
        lang.add(FluxKnife.RIFT_TOOLTIP_KEY, riftTooltip);
        lang.add(FluxKnife.CUT_TOOLTIP_KEY, cutTooltip);
        lang.add(FluxKnife.NO_AURA_KEY, noAura);
        entry(lang, FluxKnife.ENTRY_NAME, name, stage0, stage1);
    }

    private static void knife(LanguageProvider lang, FarmersDelightKnife knife, String name, String stage0, String stage1) {
        lang.add(knife.nameKey(), name);
        entry(lang, knife.entryName(), name, stage0, stage1);
    }

    private static void entry(LanguageProvider lang, String name, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + name;
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
