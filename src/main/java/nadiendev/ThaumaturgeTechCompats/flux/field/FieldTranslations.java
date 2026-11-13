package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class FieldTranslations {
    public static final String FLUX = message("flux");
    public static final String SHIELD_STATE_ON = message("shield.state.on");
    public static final String SHIELD_STATE_OFF = message("shield.state.off");
    public static final String SHIELD_DISABLED = message("shield.disabled");
    public static final String SHIELD_RADIUS = message("shield.radius");
    public static final String SHIELD_COST = message("shield.cost");
    public static final String SHIELD_SHUTDOWN = message("shield.shutdown");
    public static final String TERRAFORMER_SAME = message("terraformer.same");
    public static final String TERRAFORMER_LOADED = message("terraformer.loaded");
    public static final String TERRAFORMER_REMOVED = message("terraformer.removed");
    public static final String TERRAFORMER_NO_BAIT = message("terraformer.no_bait");
    public static final String TERRAFORMER_UNKNOWN_BIOME = message("terraformer.unknown_biome");
    public static final String TERRAFORMER_BAIT = message("terraformer.bait");
    public static final String TERRAFORMER_PROGRESS = message("terraformer.progress");
    public static final String TERRAFORMER_RADIUS = message("terraformer.radius");
    public static final String TERRAFORMER_COST = message("terraformer.cost");
    public static final String TOOLTIP_SHIELD_RADIUS = tooltip("shield.radius");
    public static final String TOOLTIP_SHIELD_COST = tooltip("shield.cost");
    public static final String TOOLTIP_SHIELD_CAPACITY = tooltip("shield.capacity");
    public static final String TOOLTIP_TERRAFORMER_RADIUS = tooltip("terraformer.radius");
    public static final String TOOLTIP_TERRAFORMER_COST = tooltip("terraformer.cost");
    public static final String TOOLTIP_TERRAFORMER_CAPACITY = tooltip("terraformer.capacity");
    public static final String TOOLTIP_THRUSTER_COST = tooltip("thruster.cost");
    public static final String TOOLTIP_THRUSTER_USE = tooltip("thruster.use");

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private FieldTranslations() {
    }

    private static String message(String path) {
        return "message." + ThaumaturgeTechCompats.MOD_ID + ".field." + path;
    }

    private static String tooltip(String path) {
        return "tooltip." + ThaumaturgeTechCompats.MOD_ID + ".field." + path;
    }

    public static String terraformerState(String id) {
        return message("terraformer.state." + id);
    }

    private static String entry(String name) {
        return "research." + MOD + "." + name;
    }

    private static String englishTier(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> "Thaumium";
            case ALCHEMICAL_BRASS -> "Alchemical Brass";
            case VOID_METAL -> "Void Metal";
        };
    }

    private static String spanishTier(FluxTier tier) {
        return switch (tier) {
            case THAUMIUM -> "taumio";
            case ALCHEMICAL_BRASS -> "latón alquímico";
            case VOID_METAL -> "metal del vacío";
        };
    }

    public static void english(LanguageProvider lang) {
        for (FluxTier tier : FluxTier.values()) {
            lang.add("block." + MOD + "." + FieldRegistry.shieldName(tier), englishTier(tier) + " Shield Generator");
            lang.add("block." + MOD + "." + FieldRegistry.terraformerName(tier), englishTier(tier) + " Arcane Terraformer");
        }
        lang.add("item." + MOD + "." + FieldRegistry.THRUSTER_NAME, "Flight Thruster");

        lang.add(FLUX, "Dimensional Flux: %s / %s DF");
        lang.add(SHIELD_STATE_ON, "The barrier is up.");
        lang.add(SHIELD_STATE_OFF, "The barrier is down. It starts again when it holds enough Dimensional Flux.");
        lang.add(SHIELD_DISABLED, "This shield generator is turned off.");
        lang.add(SHIELD_RADIUS, "Barrier radius: %s blocks");
        lang.add(SHIELD_COST, "Uses %s DF per tick: enough for %s more seconds");
        lang.add(SHIELD_SHUTDOWN, "The Shield Generator at %s ran out of Dimensional Flux and shut down.");
        lang.add(TERRAFORMER_SAME, "That bait is already loaded.");
        lang.add(TERRAFORMER_LOADED, "Bait loaded: %s will turn the land into %s.");
        lang.add(TERRAFORMER_REMOVED, "Bait removed.");
        lang.add(TERRAFORMER_NO_BAIT, "No bait loaded. Right-click with an item that stands for a biome, such as sand, snow or a sapling.");
        lang.add(TERRAFORMER_UNKNOWN_BIOME, "an unknown biome");
        lang.add(TERRAFORMER_BAIT, "Bait: %s, for %s");
        lang.add(TERRAFORMER_PROGRESS, "Progress: %s of %s columns (%s%%)");
        lang.add(TERRAFORMER_RADIUS, "Area radius: %s blocks");
        lang.add(TERRAFORMER_COST, "Uses %s DF for every 4x4 column it repaints");
        lang.add(terraformerState("no_bait"), "Idle: it has no bait.");
        lang.add(terraformerState("invalid_bait"), "Stopped: its bait does not lead to a biome it can paint.");
        lang.add(terraformerState("working"), "Repainting the area.");
        lang.add(terraformerState("waiting_flux"), "Waiting for Dimensional Flux.");
        lang.add(terraformerState("done"), "Finished: the whole area already has the biome of its bait.");
        lang.add(terraformerState("disabled"), "This terraformer is turned off.");
        lang.add(terraformerState("blocked_dimension"), "It cannot work in this dimension.");
        lang.add(TOOLTIP_SHIELD_RADIUS, "Barrier radius: %s blocks");
        lang.add(TOOLTIP_SHIELD_COST, "Uses %s DF per tick while the barrier is up");
        lang.add(TOOLTIP_SHIELD_CAPACITY, "Holds up to %s DF");
        lang.add(TOOLTIP_TERRAFORMER_RADIUS, "Repaints a radius of %s blocks");
        lang.add(TOOLTIP_TERRAFORMER_COST, "Uses %s DF for every 4x4 column");
        lang.add(TOOLTIP_TERRAFORMER_CAPACITY, "Holds up to %s DF");
        lang.add(TOOLTIP_THRUSTER_COST, "Uses %s DF for every second of thrust");
        lang.add(TOOLTIP_THRUSTER_USE, "Wear it on the chest. Hold jump in the air to fly in controlled bursts, and forward to push ahead.");

        for (FluxTier tier : FluxTier.values()) {
            String shield = entry(FieldContent.shieldEntry(tier));
            String terraformer = entry(FieldContent.terraformerEntry(tier));
            lang.add(shield + ".title", englishTier(tier) + " Shield Generator");
            lang.add(terraformer + ".title", englishTier(tier) + " Arcane Terraformer");
        }
        lang.add(entry(FieldContent.shieldEntry(FluxTier.THAUMIUM)) + ".stage_0",
                "A wall does not have to be stone. If the barrier of a mirror shield could be cast around a whole place, fed by a steady supply of dimensional flux, nothing hostile would ever reach what stands inside.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.THAUMIUM)) + ".stage_1",
                "The Thaumium Shield Generator is made on an arcane workbench from four thaumium plates, two mirrored glass, two simple mechanisms and a vis resonator.<BR>While it holds Dimensional Flux it raises a bubble around itself that destroys the projectiles of hostile mobs, pushes hostile mobs out and takes the damage out of explosions inside. Every tick of the bubble costs flux, and so does every projectile and every explosion it absorbs. When the flux runs out it shuts down, and it starts again once it has recovered enough. Right-click it to read its state.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.ALCHEMICAL_BRASS)) + ".stage_0",
                "The bubble is too small for what I want to protect. Brass carries the flux better and should let the barrier grow.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.ALCHEMICAL_BRASS)) + ".stage_1",
                "The Alchemical Brass Shield Generator is infused from a thaumium one, with four brass plates, a simple mechanism, a vis resonator and two mirrored glass.<BR>It holds more flux and projects a much wider bubble.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.VOID_METAL)) + ".stage_0",
                "Void metal does not reflect: it swallows. A barrier woven from it should stop what brass can only turn away, and reach as far as a small fortress.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.VOID_METAL)) + ".stage_1",
                "The Void Metal Shield Generator is infused from an alchemical brass one, with four void metal plates, a complex mechanism, two vis resonators and a mirrored glass.<BR>It holds the most flux and projects the widest bubble, at a price in flux per tick to match.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.THAUMIUM)) + ".stage_0",
                "Biomes are only a matter of aspects that have settled in a place. A machine that knows the aspects of a desert, or of a taiga, could press them into the land around it, column by column.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.THAUMIUM)) + ".stage_1",
                "The Thaumium Arcane Terraformer is made on an arcane workbench from four thaumium plates, grass, sand, two simple mechanisms and a vis resonator.<BR>Right-click it with an item that stands for a biome, such as sand for a desert, snow for the tundra or a sapling for a forest, and it takes the flux of the network to repaint the area around it, column by column, starting at its feet. Sneak and right-click it with an empty hand to take the bait back. Biomes and dimensions listed in the configuration are left alone. Right-click it to read its progress.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.ALCHEMICAL_BRASS)) + ".stage_0",
                "A single machine works too slowly and reaches too little. With brass in its channels it should change more land, faster.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.ALCHEMICAL_BRASS)) + ".stage_1",
                "The Alchemical Brass Arcane Terraformer is infused from a thaumium one, with four brass plates, a simple mechanism, a vis resonator, a cactus and a block of snow.<BR>It repaints a wider area, several columns at a time, and for less flux per column.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.VOID_METAL)) + ".stage_0",
                "A continent is not out of reach for a machine that does not care what it empties to do its work.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.VOID_METAL)) + ".stage_1",
                "The Void Metal Arcane Terraformer is infused from an alchemical brass one, with four void metal plates, a complex mechanism, two vis resonators and a red mushroom.<BR>It repaints the widest area, the fastest and for the least flux per column.");

        lang.add(entry(FieldContent.THRUSTER_ENTRY) + ".title", "Flight Thruster");
        lang.add(entry(FieldContent.THRUSTER_ENTRY) + ".stage_0",
                "Levitation is a trick of the void, and the void answers to controlled discharges. A module that stores dimensional flux and spends it in short bursts at my back could lift me, and keep me from falling too.");
        lang.add(entry(FieldContent.THRUSTER_ENTRY) + ".stage_1",
                "The Flight Thruster is infused from a thaumium chestplate, with three brass plates, two complex mechanisms, a void seed and two vis resonators.<BR>It is worn on the chest and holds Dimensional Flux, which an environmental charger can refill. While you hold jump in the air it thrusts you upwards, and forward as well if you hold the forward key, in bursts that need a moment to cool down. It also takes the fall out of any fall while it holds flux. Without flux it does nothing.");
    }

    public static void spanish(LanguageProvider lang) {
        for (FluxTier tier : FluxTier.values()) {
            lang.add("block." + MOD + "." + FieldRegistry.shieldName(tier), "Generador de escudo de " + spanishTier(tier));
            lang.add("block." + MOD + "." + FieldRegistry.terraformerName(tier), "Terraformador arcano de " + spanishTier(tier));
        }
        lang.add("item." + MOD + "." + FieldRegistry.THRUSTER_NAME, "Propulsor de vuelo");

        lang.add(FLUX, "Flujo Dimensional: %s / %s DF");
        lang.add(SHIELD_STATE_ON, "La barrera está activa.");
        lang.add(SHIELD_STATE_OFF, "La barrera está caída. Se reactiva cuando acumula suficiente Flujo Dimensional.");
        lang.add(SHIELD_DISABLED, "Este generador de escudo está desactivado.");
        lang.add(SHIELD_RADIUS, "Radio de la barrera: %s bloques");
        lang.add(SHIELD_COST, "Gasta %s DF por tick: alcanza para %s segundos más");
        lang.add(SHIELD_SHUTDOWN, "El Generador de escudo en %s se quedó sin Flujo Dimensional y se apagó.");
        lang.add(TERRAFORMER_SAME, "Ese cebo ya está cargado.");
        lang.add(TERRAFORMER_LOADED, "Cebo cargado: %s convertirá la tierra en %s.");
        lang.add(TERRAFORMER_REMOVED, "Cebo retirado.");
        lang.add(TERRAFORMER_NO_BAIT, "Sin cebo. Haz clic derecho con un objeto que represente un bioma, como arena, nieve o un brote.");
        lang.add(TERRAFORMER_UNKNOWN_BIOME, "un bioma desconocido");
        lang.add(TERRAFORMER_BAIT, "Cebo: %s, para %s");
        lang.add(TERRAFORMER_PROGRESS, "Progreso: %s de %s columnas (%s%%)");
        lang.add(TERRAFORMER_RADIUS, "Radio del área: %s bloques");
        lang.add(TERRAFORMER_COST, "Gasta %s DF por cada columna de 4x4 que repinta");
        lang.add(terraformerState("no_bait"), "En espera: no tiene cebo.");
        lang.add(terraformerState("invalid_bait"), "Detenido: su cebo no lleva a un bioma que pueda pintar.");
        lang.add(terraformerState("working"), "Repintando el área.");
        lang.add(terraformerState("waiting_flux"), "Esperando Flujo Dimensional.");
        lang.add(terraformerState("done"), "Terminado: toda el área ya tiene el bioma de su cebo.");
        lang.add(terraformerState("disabled"), "Este terraformador está desactivado.");
        lang.add(terraformerState("blocked_dimension"), "No puede trabajar en esta dimensión.");
        lang.add(TOOLTIP_SHIELD_RADIUS, "Radio de la barrera: %s bloques");
        lang.add(TOOLTIP_SHIELD_COST, "Gasta %s DF por tick mientras la barrera está activa");
        lang.add(TOOLTIP_SHIELD_CAPACITY, "Almacena hasta %s DF");
        lang.add(TOOLTIP_TERRAFORMER_RADIUS, "Repinta un radio de %s bloques");
        lang.add(TOOLTIP_TERRAFORMER_COST, "Gasta %s DF por cada columna de 4x4");
        lang.add(TOOLTIP_TERRAFORMER_CAPACITY, "Almacena hasta %s DF");
        lang.add(TOOLTIP_THRUSTER_COST, "Gasta %s DF por cada segundo de empuje");
        lang.add(TOOLTIP_THRUSTER_USE, "Se lleva en el pecho. Mantén el salto en el aire para volar en descargas controladas, y avanzar para empujarte hacia delante.");

        for (FluxTier tier : FluxTier.values()) {
            lang.add(entry(FieldContent.shieldEntry(tier)) + ".title", "Generador de escudo de " + spanishTier(tier));
            lang.add(entry(FieldContent.terraformerEntry(tier)) + ".title", "Terraformador arcano de " + spanishTier(tier));
        }
        lang.add(entry(FieldContent.shieldEntry(FluxTier.THAUMIUM)) + ".stage_0",
                "Un muro no tiene por qué ser de piedra. Si la barrera de un escudo espejo pudiera proyectarse alrededor de todo un lugar, alimentada por un suministro constante de flujo dimensional, nada hostil llegaría jamás a lo que hay dentro.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.THAUMIUM)) + ".stage_1",
                "El Generador de escudo de taumio se fabrica en una mesa arcana con cuatro placas de taumio, dos vidrios espejados, dos mecanismos simples y un resonador de vis.<BR>Mientras tenga Flujo Dimensional levanta una burbuja a su alrededor que destruye los proyectiles de los monstruos hostiles, empuja a los monstruos hostiles fuera y anula el daño de las explosiones en su interior. Cada tick de la burbuja cuesta flujo, y también cada proyectil y cada explosión que absorbe. Cuando se acaba el flujo se apaga, y vuelve a encenderse cuando se ha recuperado lo suficiente. Haz clic derecho para leer su estado.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.ALCHEMICAL_BRASS)) + ".stage_0",
                "La burbuja se queda pequeña para lo que quiero proteger. El latón conduce mejor el flujo y debería dejar que la barrera crezca.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.ALCHEMICAL_BRASS)) + ".stage_1",
                "El Generador de escudo de latón alquímico se infunde a partir de uno de taumio, con cuatro placas de latón, un mecanismo simple, un resonador de vis y dos vidrios espejados.<BR>Almacena más flujo y proyecta una burbuja mucho más amplia.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.VOID_METAL)) + ".stage_0",
                "El metal del vacío no refleja: se lo traga. Una barrera tejida con él debería detener lo que el latón solo sabe desviar, y llegar tan lejos como una pequeña fortaleza.");
        lang.add(entry(FieldContent.shieldEntry(FluxTier.VOID_METAL)) + ".stage_1",
                "El Generador de escudo de metal del vacío se infunde a partir de uno de latón alquímico, con cuatro placas de metal del vacío, un mecanismo complejo, dos resonadores de vis y un vidrio espejado.<BR>Almacena el máximo de flujo y proyecta la burbuja más amplia, con un gasto de flujo por tick a la altura.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.THAUMIUM)) + ".stage_0",
                "Los biomas no son más que aspectos que se han asentado en un lugar. Una máquina que conozca los aspectos de un desierto, o de una taiga, podría imprimirlos en la tierra que la rodea, columna a columna.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.THAUMIUM)) + ".stage_1",
                "El Terraformador arcano de taumio se fabrica en una mesa arcana con cuatro placas de taumio, hierba, arena, dos mecanismos simples y un resonador de vis.<BR>Haz clic derecho con un objeto que represente un bioma, como arena para un desierto, nieve para la tundra o un brote para un bosque, y usará el flujo de la red para repintar el área que lo rodea, columna a columna, empezando por sus pies. Agáchate y haz clic derecho con la mano vacía para recuperar el cebo. Los biomas y dimensiones de la lista de la configuración no se tocan. Haz clic derecho para leer su progreso.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.ALCHEMICAL_BRASS)) + ".stage_0",
                "Una sola máquina trabaja demasiado despacio y llega a muy poco. Con latón en sus canales debería cambiar más tierra, y más rápido.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.ALCHEMICAL_BRASS)) + ".stage_1",
                "El Terraformador arcano de latón alquímico se infunde a partir de uno de taumio, con cuatro placas de latón, un mecanismo simple, un resonador de vis, un cactus y un bloque de nieve.<BR>Repinta un área más amplia, varias columnas a la vez, y con menos flujo por columna.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.VOID_METAL)) + ".stage_0",
                "Un continente no queda fuera del alcance de una máquina a la que no le importa qué vacía para hacer su trabajo.");
        lang.add(entry(FieldContent.terraformerEntry(FluxTier.VOID_METAL)) + ".stage_1",
                "El Terraformador arcano de metal del vacío se infunde a partir de uno de latón alquímico, con cuatro placas de metal del vacío, un mecanismo complejo, dos resonadores de vis y un champiñón rojo.<BR>Repinta el área más amplia, la más rápida y con el menor flujo por columna.");

        lang.add(entry(FieldContent.THRUSTER_ENTRY) + ".title", "Propulsor de vuelo");
        lang.add(entry(FieldContent.THRUSTER_ENTRY) + ".stage_0",
                "La levitación es un truco del vacío, y el vacío responde a las descargas controladas. Un módulo que guarde flujo dimensional y lo gaste en ráfagas cortas a mi espalda podría elevarme, y también evitar que caiga.");
        lang.add(entry(FieldContent.THRUSTER_ENTRY) + ".stage_1",
                "El Propulsor de vuelo se infunde a partir de una pechera de taumio, con tres placas de latón, dos mecanismos complejos, una semilla del vacío y dos resonadores de vis.<BR>Se lleva en el pecho y almacena Flujo Dimensional, que un cargador ambiental puede rellenar. Mientras mantienes el salto en el aire te impulsa hacia arriba, y también hacia delante si mantienes la tecla de avance, en ráfagas que necesitan un momento para enfriarse. Además anula el daño de cualquier caída mientras conserve flujo. Sin flujo no hace nada.");
    }
}
