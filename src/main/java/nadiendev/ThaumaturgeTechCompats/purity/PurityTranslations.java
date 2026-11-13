package nadiendev.ThaumaturgeTechCompats.purity;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class PurityTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private PurityTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("research_category." + MOD + "." + PurityContent.CATEGORY, "Purity");
        lang.add("item." + MOD + "." + PurityRegistry.POTION_NAME, "Purification Splash Potion");
        lang.add("item." + MOD + "." + PurityRegistry.POTION_NAME + ".tooltip", "Cures infected creatures in the area where it breaks.");
        lang.add("message." + MOD + "." + PurityRegistry.POTION_NAME + ".disabled", "The Purification Splash Potion is disabled.");
        entry(lang, PurityContent.HEADER, "Purity",
                "Taint does not only eat the land. It eats whatever stands on it, and I cannot always afford to put down the creature it has taken. If the aura can be cleansed, the flesh should be able to be cleansed as well.",
                "Everything I learn about driving the taint out of living creatures is gathered in this tab.");
        entry(lang, PurityContent.ENTRY, "Purification Splash Potion",
                "A creature that has been infected is not lost forever, I am sure of it. The taint clings to it the way flux clings to the aura, and I have already learned how to coax flux out of the aura.<BR>A cleansing brew, steeped with the life of the world and the order that flux hates, should be able to wash the taint out of a creature, much like the old silverleaf tonics.<BR>To understand how the taint takes hold I must first observe one of its own creatures, up close.",
                "The brew works. Thrown at the ground, the potion shatters into a bright, clean mist, and every infected creature standing in it returns to its natural self, health, temper and all.<BR>It cures pets, livestock and monsters alike, and it also drives the flux sickness out of the blood before it can finish a creature off.<PAGE>Purification Splash Potion<DIV>It is brewed in the crucible from Ordo, Victus, Aqua and Lux, with an Ethereal Bloom as the catalyst.");
        lang.add("research." + MOD + "." + PurityContent.FLAG + ".title", "Observe a taint creature");

        item(lang, PurityRegistry.BIOME_POTION_NAME, "Biome Purification Potion",
                "Thrown, it cleans the taint from the land where it breaks: biome, blocks, fibres and seeds.");
        lang.add("message." + MOD + "." + PurityRegistry.BIOME_POTION_NAME + ".disabled", "The Biome Purification Potion is disabled.");
        entry(lang, PurityVariantContent.BIOME_ENTRY, "Biome Purification Potion",
                "The purification potion cures the creatures the taint has taken, but the land itself stays sick. The same flux that sickens the flesh stains the soil, the stone and the sky above them, and the aura of the chunk cannot recover while the stain remains.<BR>An ethereal bloom cleans slowly, and a seed left alone keeps feeding the stain. If a potion could carry the whole cleansing of a bloom in one breath, a single throw might undo what a seed has spread over the land.<BR>I will need a firm grasp of the aura and of alchemy before I trust a brew with that much order in it.",
                "The brew is ready. Thrown at the ground, it bursts into a pale green mist that does in one breath the work of an ethereal bloom: the tainted biome returns to what the land was, fibres, crust, soil and growths wither away, flux goo and gas wash out, and any taint seed caught in the mist is destroyed.<BR>It also draws flux out of the aura of the chunks it touches.<PAGE>Biome Purification Potion<DIV>It is brewed in the crucible from Ordo, Victus, Aqua, Lux, Auram, Praecantatio, Vitreus and Herba, with a Purification Splash Potion as the catalyst. The area, the flux it draws and what it cleans are set in the configuration.");

        item(lang, PurityRegistry.CLARITY_POTION_NAME, "Clarity Potion",
                "Drink it to wash the temporary warp out of your mind.");
        lang.add("message." + MOD + "." + PurityRegistry.CLARITY_POTION_NAME + ".disabled", "The Clarity Potion is disabled.");
        lang.add("message." + MOD + "." + PurityRegistry.CLARITY_POTION_NAME + ".nothing", "You carry no temporary warp.");
        entry(lang, PurityVariantContent.CLARITY_ENTRY, "Clarity Potion",
                "Part of the madness a thaumaturge gathers is passing, and fades with time, yet it lingers exactly when it hurts the most. Sanity soap scrubs the skin; I want something that reaches the mind itself.<BR>Cognition to hold the thought, order to put it back in its place and spirit to carry it out: a draught of the three, sweetened with the soap that already knows how to clean, should wash the passing madness away.",
                "The draught is clear as spring water and tastes of nothing. Drunk, it takes the temporary warp out of the mind at once.<BR>It does not touch the warp that has settled for good, and the drinker may feel a brief unease afterwards.<PAGE>Clarity Potion<DIV>It is brewed in the crucible from Cognitio, Ordo, Spiritus, Aqua, Victus and Alienis, with a Sanity Soap as the catalyst. How much it removes and its side effect are set in the configuration.");

        item(lang, PurityRegistry.ABSOLUTION_POTION_NAME, "Absolution Potion",
                "Drink it to purge permanent warp from your mind, at a price.");
        lang.add("message." + MOD + "." + PurityRegistry.ABSOLUTION_POTION_NAME + ".disabled", "The Absolution Potion is disabled.");
        lang.add("message." + MOD + "." + PurityRegistry.ABSOLUTION_POTION_NAME + ".nothing", "You carry no permanent warp.");
        entry(lang, PurityVariantContent.ABSOLUTION_ENTRY, "Absolution Potion",
                "They say permanent madness can never be undone. They also said taint could never be driven out of a living creature.<BR>The voidseer's pearl taught me that the mind can be opened to what lies outside; perhaps it can also be opened to let something leave. A draught far stronger than the clarity potion, fed with the rarest aspects and the gaze of an eldritch eye, might pull a single thread of the madness out from where it has settled.<BR>It will not be cheap, and I doubt it will be gentle.",
                "It works, though only a little at a time. Each draught pulls a measure of permanent warp out of the drinker and leaves them weakened for a long while.<BR>It will take many to be free, and each costs more than the finest brew I have made.<PAGE>Absolution Potion<DIV>It is brewed in the crucible from Cognitio, Ordo, Spiritus, Alienis, Praecantatio, Vacuos, Tenebrae, Victus, Auram and Perditio, with an Eldritch Eye as the catalyst. How much it removes per draught and its side effect are set in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("research_category." + MOD + "." + PurityContent.CATEGORY, "Pureza");
        lang.add("item." + MOD + "." + PurityRegistry.POTION_NAME, "Poción arrojadiza de purificación");
        lang.add("item." + MOD + "." + PurityRegistry.POTION_NAME + ".tooltip", "Cura a las criaturas infectadas en la zona donde se rompe.");
        lang.add("message." + MOD + "." + PurityRegistry.POTION_NAME + ".disabled", "La poción arrojadiza de purificación está desactivada.");
        entry(lang, PurityContent.HEADER, "Pureza",
                "La corrupción no solo devora la tierra. Devora todo lo que se alza sobre ella, y no siempre puedo permitirme sacrificar a la criatura que ha atrapado. Si el aura se puede limpiar, la carne también debería poder limpiarse.",
                "Todo lo que aprenda sobre cómo expulsar la corrupción de las criaturas vivas está reunido en esta pestaña.");
        entry(lang, PurityContent.ENTRY, "Poción arrojadiza de purificación",
                "Una criatura infectada no está perdida para siempre, estoy convencido. La corrupción se le adhiere igual que el flujo se adhiere al aura, y ya he aprendido a sacar el flujo del aura.<BR>Un brebaje purificador, empapado con la vida del mundo y el orden que el flujo detesta, debería poder lavar la corrupción de una criatura, como los viejos tónicos de hoja de plata.<BR>Para entender cómo se aferra la corrupción debo antes observar de cerca a una de sus criaturas.",
                "El brebaje funciona. Lanzada contra el suelo, la poción estalla en una niebla clara y brillante, y toda criatura infectada que esté dentro vuelve a ser la de siempre, con su salud y su carácter.<BR>Cura mascotas, ganado y monstruos por igual, y además expulsa de la sangre la enfermedad del flujo antes de que acabe con la criatura.<PAGE>Poción arrojadiza de purificación<DIV>Se elabora en el crisol con Ordo, Victus, Aqua y Lux, y una Flor Etérea como catalizador.");
        lang.add("research." + MOD + "." + PurityContent.FLAG + ".title", "Observar a una criatura de corrupción");

        item(lang, PurityRegistry.BIOME_POTION_NAME, "Poción de purificación de bioma",
                "Arrojada, limpia la corrupción de la tierra donde se rompe: bioma, bloques, fibras y semillas.");
        lang.add("message." + MOD + "." + PurityRegistry.BIOME_POTION_NAME + ".disabled", "La poción de purificación de bioma está desactivada.");
        entry(lang, PurityVariantContent.BIOME_ENTRY, "Poción de purificación de bioma",
                "La poción de purificación cura a las criaturas que la corrupción ha atrapado, pero la tierra sigue enferma. El mismo flujo que enferma la carne mancha el suelo, la piedra y el cielo sobre ellos, y el aura del chunk no se recupera mientras la mancha permanezca.<BR>Una flor etérea limpia despacio, y una semilla que se deja sola sigue alimentando la mancha. Si una poción pudiera llevar toda la limpieza de una flor en un solo aliento, un único lanzamiento quizá deshiciera lo que una semilla ha extendido por el terreno.<BR>Necesitaré dominar bien el aura y la alquimia antes de fiarme de un brebaje con tanto orden dentro.",
                "El brebaje está listo. Lanzado contra el suelo, estalla en una niebla verde pálida que hace de golpe el trabajo de una flor etérea: el bioma corrompido vuelve a ser lo que fue, las fibras, la costra, el suelo y las excrecencias se marchitan, el cieno y el gas de flujo se lavan, y toda semilla de corrupción atrapada en la niebla queda destruida.<BR>Además extrae flujo del aura de los chunks que toca.<PAGE>Poción de purificación de bioma<DIV>Se elabora en el crisol con Ordo, Victus, Aqua, Lux, Auram, Praecantatio, Vitreus y Herba, con una Poción arrojadiza de purificación como catalizador. El área, el flujo que extrae y lo que limpia se ajustan en la configuración.");

        item(lang, PurityRegistry.CLARITY_POTION_NAME, "Poción de claridad",
                "Bébela para sacar de tu mente la locura temporal.");
        lang.add("message." + MOD + "." + PurityRegistry.CLARITY_POTION_NAME + ".disabled", "La poción de claridad está desactivada.");
        lang.add("message." + MOD + "." + PurityRegistry.CLARITY_POTION_NAME + ".nothing", "No cargas locura temporal.");
        entry(lang, PurityVariantContent.CLARITY_ENTRY, "Poción de claridad",
                "Parte de la locura que reúne un taumaturgo es pasajera y se desvanece con el tiempo, pero se queda justo cuando más duele. El jabón de la cordura frota la piel; yo quiero algo que llegue a la mente misma.<BR>Cognición para sostener el pensamiento, orden para devolverlo a su sitio y espíritu para llevarlo a cabo: un trago de los tres, endulzado con el jabón que ya sabe limpiar, debería lavar la locura pasajera.",
                "El trago es claro como agua de manantial y no sabe a nada. Bebido, saca de la mente la locura temporal al instante.<BR>No toca la locura que se ha asentado para siempre, y quien la bebe puede sentir un breve malestar después.<PAGE>Poción de claridad<DIV>Se elabora en el crisol con Cognitio, Ordo, Spiritus, Aqua, Victus y Alienis, con un Jabón de la cordura como catalizador. Cuánto quita y su efecto secundario se ajustan en la configuración.");

        item(lang, PurityRegistry.ABSOLUTION_POTION_NAME, "Poción de absolución",
                "Bébela para purgar locura permanente de tu mente, a un precio.");
        lang.add("message." + MOD + "." + PurityRegistry.ABSOLUTION_POTION_NAME + ".disabled", "La poción de absolución está desactivada.");
        lang.add("message." + MOD + "." + PurityRegistry.ABSOLUTION_POTION_NAME + ".nothing", "No cargas locura permanente.");
        entry(lang, PurityVariantContent.ABSOLUTION_ENTRY, "Poción de absolución",
                "Dicen que la locura permanente no se deshace nunca. También decían que la corrupción no se podía expulsar de una criatura viva.<BR>La Perla del Vidente del Vacío me enseñó que la mente puede abrirse a lo que hay fuera; quizá también pueda abrirse para dejar salir algo. Un trago mucho más fuerte que la poción de claridad, alimentado con los aspectos más raros y la mirada de un Ojo de Eldricht, podría arrancar un solo hilo de la locura de donde se ha asentado.<BR>No será barato, y dudo que sea suave.",
                "Funciona, aunque solo poco a poco. Cada trago arranca una medida de locura permanente de quien lo bebe y lo deja debilitado un buen rato.<BR>Harán falta muchos para quedar libre, y cada uno cuesta más que el mejor brebaje que he hecho.<PAGE>Poción de absolución<DIV>Se elabora en el crisol con Cognitio, Ordo, Spiritus, Alienis, Praecantatio, Vacuos, Tenebrae, Victus, Auram y Perditio, con un Ojo de Eldricht como catalizador. Cuánto quita por trago y su efecto secundario se ajustan en la configuración.");
    }

    private static void item(LanguageProvider lang, String name, String title, String tooltip) {
        lang.add("item." + MOD + "." + name, title);
        lang.add("item." + MOD + "." + name + ".tooltip", tooltip);
    }

    private static void entry(LanguageProvider lang, String name, String title, String stage0, String stage1) {
        String base = "research." + MOD + "." + name;
        lang.add(base + ".title", title);
        lang.add(base + ".stage_0", stage0);
        lang.add(base + ".stage_1", stage1);
    }
}
