package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class PearlTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String TOOLTIP = PlanetaryPearlItem.TOOLTIP;
    private static final String BASE = "research." + MOD + "." + PearlContent.ENTRY_BASE;
    private static final String APEX = "research." + MOD + "." + PearlContent.ENTRY_APEX;

    private PearlTranslations() {
    }

    private static String itemKey(PlanetaryTier tier) {
        return "item." + MOD + "." + PearlRegistry.name(tier);
    }

    private static String spanishName(PlanetaryTier tier) {
        return switch (tier) {
            case INFINITY -> "Perla Endest planetaria infinita";
            case CREATIVE -> "Perla Endest planetaria creativa";
            default -> "Perla Endest planetaria " + tier.spanish();
        };
    }

    public static void english(LanguageProvider lang) {
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            lang.add(itemKey(tier), tier.english() + " Planetary Endest Pearl");
        }
        for (PlanetBody body : PlanetBody.values()) {
            lang.add(body.nameKey(), body.english());
        }
        lang.add("entity." + MOD + "." + PearlRegistry.PEARL_ENTITY_NAME, "Planetary Endest Pearl");
        lang.add("entity." + MOD + "." + PearlRegistry.PLANET_ENTITY_NAME, "Planet");
        lang.add("death.attack." + PlanetDamage.MESSAGE_ID, "%1$s was crushed by the planets");
        lang.add("death.attack." + PlanetDamage.MESSAGE_ID + ".player", "%1$s was ground to dust by the planets of %2$s");
        lang.add(TOOLTIP + "description", "Thrown like an ender pearl. Instead of a gaping void it summons planets that orbit what it hits and hurt it.");
        lang.add(TOOLTIP + "planets", "Planets: %s");
        lang.add(TOOLTIP + "bodies", "Orbiting: %s");
        lang.add(TOOLTIP + "more", ", +%s more");
        lang.add(TOOLTIP + "damage", "Damage: %s per hit, every %s s");
        lang.add(TOOLTIP + "duration", "Lasts %s s, orbit radius %s blocks");
        lang.add(TOOLTIP + "cooldown", "Cooldown: %s s");
        lang.add(TOOLTIP + "kills_bosses", "Kills bosses");
        lang.add(TOOLTIP + "boss_cap", "Hurts bosses for at most %s per hit");
        lang.add(TOOLTIP + "unbreakable", "Unbreakable");
        lang.add(TOOLTIP + "durability", "Durability: %s / %s");
        lang.add(TOOLTIP + "disabled", "Disabled");

        lang.add(BASE + ".title", "Planetary Endest Pearl");
        lang.add(BASE + ".stage_0",
                "The Endest Pearl of Avaritia opens a gaping void wherever it lands, and a void only swallows. But the Primordial Pearl taught me that a pearl is a small world, and a world can have neighbours. If I bind metals to the pearl one after another, it might call the planets themselves to circle what it strikes.");
        lang.add(BASE + ".stage_1",
                "The Planetary Endest Pearls are made on an infusion altar. The Copper one needs an Avaritia Endest Pearl and four copper ingots, and every following pearl needs the one before it and four units of its own material:<BR>- Copper, Iron, Gold, Diamond, Emerald and Netherite: copper ingots, iron ingots, gold ingots, diamonds, emeralds and netherite ingots.<BR>They are thrown with right click like an ender pearl. When one hits a creature, planets named after those of the sky, Mercury, Venus, Earth, Mars and so on, orbit it on different orbits, each with its own speed and slant, and hurt it again and again until they fade. When one hits a block, the planets orbit the closest creature within reach, and if there is none they orbit the point of impact and hurt whatever enters their reach. They never hurt the thrower, or their team and tamed animals, unless the configuration says so. Bosses take only a limited amount of damage from each planet.<BR>The Netherite pearl is the first that kills bosses: its planets ignore armor, resistance and the shield of what they hit, and it is unbreakable, so it never wears out when thrown, only held back by its cooldown. The lower pearls have durability: every throw wears one point and the pearl breaks with the last one. Copper has 3, Iron 5, Gold 8, Diamond 12 and Emerald 16 (set in the configuration).<BR>Every pearl's planets, damage, interval, duration, orbit and cooldown are set in the configuration.");

        lang.add(APEX + ".title", "The Great Orbits");
        lang.add(APEX + ".stage_0",
                "Copper and iron summon a few small planets, but the pearl still asks for more. Thaumaturgy and the metals of Avaritia could widen its orbits until the whole sky turns around a single target.");
        lang.add(APEX + ".stage_1",
                "The higher Planetary Endest Pearls are made on an infusion altar from the previous pearl and four units of their own material:<BR>- Thaumium: thaumium ingots.<BR>- Alchemical Brass: alchemical brass ingots.<BR>- Void Metal: void metal ingots.<BR>- Blaze Cube: Avaritia blaze cubes and two Blaze Cube Salis Mundus.<BR>- Crystal Matrix: Crystal Matrix ingots and four Crystal Matrix Salis Mundus.<BR>- Neutronium: neutron ingots and six Neutronium Salis Mundus.<BR>- Infinity: infinity ingots and eight Infinity Salis Mundus.<BR>Each one summons more planets, that hit harder and faster and stay longer, and the infusion grows more unstable the higher the pearl goes. They use the new essences of this addon, Cosmic, Nuclear and Sol.<BR>The Void Metal and Infinity pearls kill bosses like the Netherite one: their planets ignore armor, resistance and invulnerability, and can finish the Wither and the Ender Dragon. They are unbreakable too, so they never wear out. The Creative pearl has no recipe: it is only found in the creative tab, it kills bosses and is unbreakable as well, and it summons as many planets as the Infinity one for much longer.<BR>The Thaumium, Alchemical Brass, Blaze Cube, Crystal Matrix and Neutronium pearls have durability, with 32, 64, 128, 256 and 512 points respectively, and bosses only take a limited amount of damage from them.");
    }

    public static void spanish(LanguageProvider lang) {
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            lang.add(itemKey(tier), spanishName(tier));
        }
        for (PlanetBody body : PlanetBody.values()) {
            lang.add(body.nameKey(), body.spanish());
        }
        lang.add("entity." + MOD + "." + PearlRegistry.PEARL_ENTITY_NAME, "Perla Endest planetaria");
        lang.add("entity." + MOD + "." + PearlRegistry.PLANET_ENTITY_NAME, "Planeta");
        lang.add("death.attack." + PlanetDamage.MESSAGE_ID, "%1$s fue aplastado por los planetas");
        lang.add("death.attack." + PlanetDamage.MESSAGE_ID + ".player", "%1$s fue reducido a polvo por los planetas de %2$s");
        lang.add(TOOLTIP + "description", "Se lanza como una perla de ender. En vez de un vacío voraz, invoca planetas que orbitan lo que alcanza y lo dañan.");
        lang.add(TOOLTIP + "planets", "Planetas: %s");
        lang.add(TOOLTIP + "bodies", "En órbita: %s");
        lang.add(TOOLTIP + "more", ", +%s más");
        lang.add(TOOLTIP + "damage", "Daño: %s por golpe, cada %s s");
        lang.add(TOOLTIP + "duration", "Dura %s s, radio de órbita %s bloques");
        lang.add(TOOLTIP + "cooldown", "Enfriamiento: %s s");
        lang.add(TOOLTIP + "kills_bosses", "Mata jefes");
        lang.add(TOOLTIP + "boss_cap", "Daña a los jefes como máximo %s por golpe");
        lang.add(TOOLTIP + "unbreakable", "Irrompible");
        lang.add(TOOLTIP + "durability", "Durabilidad: %s / %s");
        lang.add(TOOLTIP + "disabled", "Desactivada");

        lang.add(BASE + ".title", "Perla Endest planetaria");
        lang.add(BASE + ".stage_0",
                "La Perla Endest de Avaritia abre un vacío voraz allí donde cae, y un vacío solo sabe tragar. Pero la Perla Primordial me enseñó que una perla es un pequeño mundo, y un mundo puede tener vecinos. Si uno a la perla metales uno tras otro, quizá llame a los propios planetas para que giren alrededor de lo que golpea.");
        lang.add(BASE + ".stage_1",
                "Las Perlas Endest planetarias se fabrican en un altar de infusión. La de cobre necesita una Perla Endest de Avaritia y cuatro lingotes de cobre, y cada perla siguiente necesita la anterior y cuatro unidades de su propio material:<BR>- Cobre, hierro, oro, diamante, esmeralda y netherita: lingotes de cobre, lingotes de hierro, lingotes de oro, diamantes, esmeraldas y lingotes de netherita.<BR>Se lanzan con clic derecho como una perla de ender. Cuando una alcanza a una criatura, planetas con los nombres de los del cielo, Mercurio, Venus, Tierra, Marte y los demás, la orbitan en órbitas distintas, cada una con su velocidad e inclinación, y la dañan una y otra vez hasta que se disipan. Cuando alcanza un bloque, los planetas orbitan a la criatura más cercana a su alcance, y si no hay ninguna orbitan el punto de impacto y dañan lo que entre en su radio. Nunca dañan a quien las lanza, ni a su equipo ni a sus animales domados, salvo que la configuración diga otra cosa. Los jefes solo reciben una cantidad limitada de daño de cada planeta.<BR>La perla de netherita es la primera que mata jefes: sus planetas ignoran la armadura, la resistencia y el escudo de lo que golpean, y es irrompible, así que nunca se gasta al lanzarla y solo la frena su enfriamiento. Las perlas inferiores tienen durabilidad: cada lanzamiento gasta un punto y la perla se rompe con el último. La de cobre tiene 3, la de hierro 5, la de oro 8, la de diamante 12 y la de esmeralda 16 (se ajusta en la configuración).<BR>Los planetas, el daño, el intervalo, la duración, la órbita y el enfriamiento de cada perla se ajustan en la configuración.");

        lang.add(APEX + ".title", "Las Grandes Órbitas");
        lang.add(APEX + ".stage_0",
                "El cobre y el hierro invocan unos pocos planetas pequeños, pero la perla aún pide más. La taumaturgia y los metales de Avaritia podrían ensanchar sus órbitas hasta que todo el cielo gire alrededor de un solo objetivo.");
        lang.add(APEX + ".stage_1",
                "Las Perlas Endest planetarias superiores se fabrican en un altar de infusión con la perla anterior y cuatro unidades de su propio material:<BR>- Taumio: lingotes de taumio.<BR>- Latón alquímico: lingotes de latón alquímico.<BR>- Metal del vacío: lingotes de metal del vacío.<BR>- Cubo de blaze: cubos de blaze de Avaritia y dos Salis Mundus de cubo de blaze.<BR>- Matriz de cristal: lingotes de matriz de cristal y cuatro Salis Mundus de matriz de cristal.<BR>- Neutronio: lingotes de neutronio y seis Salis Mundus de neutronio.<BR>- Infinito: lingotes de infinito y ocho Salis Mundus de infinito.<BR>Cada una invoca más planetas, que golpean más fuerte y más rápido y duran más, y la infusión se vuelve más inestable cuanto más alta es la perla. Usan las nuevas esencias de este addon: Cósmico, Nuclear y Sol.<BR>Las perlas de metal del vacío e infinito matan jefes igual que la de netherita: sus planetas ignoran la armadura, la resistencia y la invulnerabilidad, y pueden acabar con el Wither y el Dragón del End. También son irrompibles, así que nunca se gastan. La perla creativa no tiene receta: solo se encuentra en la pestaña creativa, también mata jefes y es irrompible, e invoca tantos planetas como la infinita durante mucho más tiempo.<BR>Las perlas de taumio, latón alquímico, cubo de blaze, matriz de cristal y neutronio tienen durabilidad, con 32, 64, 128, 256 y 512 puntos respectivamente, y los jefes solo reciben una cantidad limitada de daño de ellas.");
    }
}
