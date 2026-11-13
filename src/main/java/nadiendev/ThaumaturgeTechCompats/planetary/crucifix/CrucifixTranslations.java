package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class CrucifixTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String ITEM = "item." + MOD + ".";
    private static final String TOOLTIP = "tooltip." + MOD + ".star_crucifix.";
    private static final String SUBTITLE = "subtitles." + MOD + ".item.star_crucifix.";
    private static final String DEATH = "death.attack." + MOD + "." + CrucifixDamageTypes.NAME;
    private static final String ENTRY = "research." + MOD + "." + CrucifixContent.ENTRY;

    private CrucifixTranslations() {
    }

    public static void english(LanguageProvider lang) {
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            lang.add(ITEM + CrucifixRegistry.itemName(tier), tier.english() + " Star Crucifix");
        }
        lang.add("entity." + MOD + "." + CrucifixRegistry.DISPLAY_NAME, "Star Crucifix");
        lang.add(TOOLTIP + "lore", "Whatever strikes its bearer is bound in starlight, and so is whatever it is aimed at.");
        lang.add(TOOLTIP + "health", "Binds creatures of up to %s health");
        lang.add(TOOLTIP + "health_any", "Binds creatures of any health");
        lang.add(TOOLTIP + "range", "Range: %s blocks");
        lang.add(TOOLTIP + "bosses", "Kills bosses");
        lang.add(TOOLTIP + "no_bosses", "Cannot bind bosses");
        lang.add(TOOLTIP + "unbreakable", "Unbreakable");
        lang.add(TOOLTIP + "durability", "Durability: %s / %s");
        lang.add(SUBTITLE + "bind", "Star crucifix rises");
        lang.add(SUBTITLE + "chains", "Starlight chains bind");
        lang.add(SUBTITLE + "tremble", "Bound creature trembles");
        lang.add(SUBTITLE + "drag", "Creature dragged underground");
        lang.add(SUBTITLE + "banish", "Creature banished");
        lang.add(SUBTITLE + "break", "Star crucifix breaks");
        lang.add(SUBTITLE + "shatter", "Starlight chains shatter");
        lang.add(SUBTITLE + "strain", "Starlight chains strain");
        lang.add(SUBTITLE + "roar", "Creature roars");
        lang.add(SUBTITLE + "crack", "Star crucifix cracks");
        lang.add(SUBTITLE + "explode", "Star crucifix explodes");
        lang.add(DEATH, "%1$s was banished to the stars by %2$s");
        lang.add(DEATH + ".item", "%1$s was banished to the stars by %2$s using %3$s");
        lang.add(DEATH + ".player", "%1$s was banished to the stars whilst fighting %2$s");
        lang.add(ENTRY + ".title", "Star Crucifixes");
        lang.add(ENTRY + ".stage_0",
                "A cross that waits in my hand until something dares to strike me. Then starlight chains should leave it, close around the creature, lift it, and drag it under the ground where it dies. The same should happen if I only point it at a hostile creature and use it.<BR>The metals I know could give it different strengths: the weaker ones bind only small creatures and weaken with every use, the stronger ones bind almost anything and last forever.");
        lang.add(ENTRY + ".stage_1",
                "A Star Crucifix works when a creature strikes its bearer, in either hand, or when it is used while aiming at a hostile creature within its range. Starlight chains bind the creature, it rises, hangs in the air and is dragged underground, where it dies and drops its loot on the surface. Only one binding can be active for each player at a time. A creature with more maximum health than the crucifix allows, and every boss if the crucifix cannot kill bosses, resists: the chains strain, the creature roars, the chains snap one by one and the crucifix cracks and explodes, throwing its bearer away, stunned. The blow that triggered it still lands.<BR>Every crucifix is made on an infusion altar. The Copper one needs a Totem of Undying and four copper ingots, and every following one needs the crucifix before it and four units of its own material:<BR>- Iron, Gold, Diamond, Emerald and Netherite: iron ingots, gold ingots, diamonds, emeralds and netherite ingots.<BR>- Thaumium, Alchemical Brass and Void Metal: their ingots.<BR>- Blaze Cube: Avaritia blaze cubes and two Blaze Cube Salis Mundus.<BR>- Crystal Matrix: Crystal Matrix ingots and four Crystal Matrix Salis Mundus.<BR>- Neutronium: neutron ingots and six Neutronium Salis Mundus.<BR>- Infinity: infinity ingots and eight Infinity Salis Mundus.<BR>The higher the crucifix, the more unstable the infusion. The Crystal Matrix, Neutronium and Infinity crucifixes are infused like the other things of their material, on an altar whose center is the Runic Matrix of their own tier. The Neutronium and Infinity crucifixes also ask for the new essence Nuclear, and the Infinity one for Cosmic too.<BR>By default each crucifix binds creatures of up to this much maximum health: Copper 30, Iron 60, Gold 100, Diamond 200, Emerald 300, Thaumium 500, Alchemical Brass 800, Blaze Cube 1500, Crystal Matrix 3000 and Neutronium 6000. The Netherite, Void Metal and Infinity ones have no limit. The higher ones reach farther and hold the creature for less time before dragging it down.<BR>The Netherite, Void Metal and Infinity crucifixes kill bosses: bosses are bound and dragged underground like any creature, the Wither and the Ender Dragon included, without the crucifix failing. They are also unbreakable and never wear out. The others have durability and every activation wears one point, even when the creature resists: Copper has 3, Iron 5, Gold 8, Diamond 12, Emerald 16, Thaumium 32, Alchemical Brass 64, Blaze Cube 128, Crystal Matrix 256 and Neutronium 512. The Creative crucifix has no recipe and is only found in the creative tab: it binds anything, kills bosses and is unbreakable.<BR>Limits, range, binding time, durability and whether each crucifix kills bosses are set in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            lang.add(ITEM + CrucifixRegistry.itemName(tier), "Crucifijo estelar " + tier.spanish());
        }
        lang.add("entity." + MOD + "." + CrucifixRegistry.DISPLAY_NAME, "Crucifijo estelar");
        lang.add(TOOLTIP + "lore", "Lo que golpee a quien lo lleva queda atado por la luz estelar, y también aquello a lo que apunta.");
        lang.add(TOOLTIP + "health", "Ata criaturas de hasta %s de vida");
        lang.add(TOOLTIP + "health_any", "Ata criaturas de cualquier vida");
        lang.add(TOOLTIP + "range", "Alcance: %s bloques");
        lang.add(TOOLTIP + "bosses", "Mata jefes");
        lang.add(TOOLTIP + "no_bosses", "No puede atar jefes");
        lang.add(TOOLTIP + "unbreakable", "Irrompible");
        lang.add(TOOLTIP + "durability", "Durabilidad: %s / %s");
        lang.add(SUBTITLE + "bind", "Se alza el crucifijo estelar");
        lang.add(SUBTITLE + "chains", "Cadenas de luz estelar atan");
        lang.add(SUBTITLE + "tremble", "Tiembla la criatura atada");
        lang.add(SUBTITLE + "drag", "Una criatura es arrastrada bajo tierra");
        lang.add(SUBTITLE + "banish", "Criatura desterrada");
        lang.add(SUBTITLE + "break", "Se rompe el crucifijo estelar");
        lang.add(SUBTITLE + "shatter", "Se rompen las cadenas de luz");
        lang.add(SUBTITLE + "strain", "Las cadenas de luz se tensan");
        lang.add(SUBTITLE + "roar", "Una criatura ruge");
        lang.add(SUBTITLE + "crack", "El crucifijo estelar se agrieta");
        lang.add(SUBTITLE + "explode", "El crucifijo estelar estalla");
        lang.add(DEATH, "%1$s fue desterrado a las estrellas por %2$s");
        lang.add(DEATH + ".item", "%1$s fue desterrado a las estrellas por %2$s con %3$s");
        lang.add(DEATH + ".player", "%1$s fue desterrado a las estrellas mientras luchaba contra %2$s");
        lang.add(ENTRY + ".title", "Crucifijos estelares");
        lang.add(ENTRY + ".stage_0",
                "Una cruz que espera en mi mano hasta que algo se atreva a golpearme. Entonces cadenas de luz estelar deberían salir de ella, cerrarse alrededor de la criatura, alzarla y arrastrarla bajo tierra, donde muere. Lo mismo debería ocurrir si solo la apunto contra una criatura hostil y la uso.<BR>Los metales que conozco podrían darle fuerzas distintas: los más débiles atan solo criaturas pequeñas y se gastan con cada uso, los más fuertes atan casi cualquier cosa y duran para siempre.");
        lang.add(ENTRY + ".stage_1",
                "Un Crucifijo estelar actúa cuando una criatura golpea a quien lo lleva, en cualquier mano, o cuando se usa apuntando a una criatura hostil dentro de su alcance. Cadenas de luz estelar atan a la criatura, que sube, queda suspendida en el aire y es arrastrada bajo tierra, donde muere y suelta su botín en la superficie. Cada jugador solo puede tener una atadura a la vez. Una criatura con más vida máxima de la que permite el crucifijo, y todo jefe si el crucifijo no puede matar jefes, se resiste: las cadenas se tensan, la criatura ruge, las cadenas se rompen una a una y el crucifijo se agrieta y estalla, lanzando lejos a quien lo llevaba, aturdido. El golpe que lo activó llega igualmente.<BR>Cada crucifijo se fabrica en un altar de infusión. El de Cobre necesita un Tótem de la inmortalidad y cuatro lingotes de cobre, y cada siguiente necesita el crucifijo anterior y cuatro unidades de su propio material:<BR>- Hierro, Oro, Diamante, Esmeralda y Netherita: lingotes de hierro, lingotes de oro, diamantes, esmeraldas y lingotes de netherita.<BR>- Taumio, Latón alquímico y Metal del vacío: sus lingotes.<BR>- Cubo de blaze: cubos de blaze de Avaritia y dos Salis Mundus de cubo de blaze.<BR>- Matriz de cristal: lingotes de matriz de cristal y cuatro Salis Mundus de matriz de cristal.<BR>- Neutronio: lingotes de neutronio y seis Salis Mundus de neutronio.<BR>- Infinito: lingotes de infinito y ocho Salis Mundus de infinito.<BR>Cuanto más alto el crucifijo, más inestable la infusión. Los crucifijos de Matriz de cristal, Neutronio e Infinito se infunden como lo demás de su material, en un altar cuyo centro sea la Matriz rúnica de su propio nivel. Los crucifijos de Neutronio e Infinito también piden la esencia nueva Nuclear, y el de Infinito además Cósmico.<BR>Por defecto cada crucifijo ata criaturas de hasta esta vida máxima: Cobre 30, Hierro 60, Oro 100, Diamante 200, Esmeralda 300, Taumio 500, Latón alquímico 800, Cubo de blaze 1500, Matriz de cristal 3000 y Neutronio 6000. Los de Netherita, Metal del vacío e Infinito no tienen límite. Los más altos llegan más lejos y sujetan menos tiempo a la criatura antes de hundirla.<BR>Los crucifijos de Netherita, Metal del vacío e Infinito matan jefes: los jefes quedan atados y son arrastrados bajo tierra como cualquier criatura, el Wither y el Ender Dragon incluidos, sin que el crucifijo falle. Además son irrompibles y nunca se gastan. Los demás tienen durabilidad y cada activación gasta un punto, incluso cuando la criatura se resiste: Cobre tiene 3, Hierro 5, Oro 8, Diamante 12, Esmeralda 16, Taumio 32, Latón alquímico 64, Cubo de blaze 128, Matriz de cristal 256 y Neutronio 512. El crucifijo Creativo no tiene receta y solo se encuentra en la pestaña creativa: ata cualquier cosa, mata jefes y es irrompible.<BR>Los límites, el alcance, el tiempo de atadura, la durabilidad y si cada crucifijo mata jefes se ajustan en la configuración.");
    }
}
