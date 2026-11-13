package nadiendev.ThaumaturgeTechCompats.flux.gear;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class GearTranslations {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String SIPHON = PortableVoidSiphonItem.TOOLTIP;
    private static final String CANNON = DischargeCannonItem.TOOLTIP;
    private static final String SIPHON_ENTRY = "research." + MOD + "." + GearContent.SIPHON_ENTRY;
    private static final String CANNON_ENTRY = "research." + MOD + "." + GearContent.CANNON_ENTRY;

    private GearTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add("item." + MOD + "." + GearRegistry.SIPHON_NAME, "Portable Void Siphon");
        lang.add("item." + MOD + "." + GearRegistry.CANNON_NAME, "Discharge Cannon");
        lang.add(FluxGearItem.TOOLTIP_STORED, "Dimensional Flux: %s / %s DF");
        lang.add(FluxGearItem.MESSAGE_EMPTY, "Not enough Dimensional Flux");
        lang.add(SIPHON + "description", "Portable tool that absorbs matter and residual energy from a distance.");
        lang.add(SIPHON + "usage", "Hold right click: uses %s DF per second and pulls items and experience within %s blocks");
        lang.add(SIPHON + "residual", "Draws up to %s aura flux per second, worth %s DF each");
        lang.add(CANNON + "description", "Weapon that concentrates the stored energy to fire high impact projectiles.");
        lang.add(CANNON + "shot", "Hold and release right click: a shot costs %s DF and deals %s damage");
        lang.add(CANNON + "range", "Range: %s blocks, cooldown: %s s");

        lang.add(SIPHON_ENTRY + ".title", "Portable Void Siphon");
        lang.add(SIPHON_ENTRY + ".stage_0",
                "The void siphon on my land pulls stray flux out of the aura and gives me seeds in return. If I could carry its hunger in my hand, I could gather what lies about me and take the leftover energy of the aura along with it.");
        lang.add(SIPHON_ENTRY + ".stage_1",
                "The Portable Void Siphon is made on an arcane workbench from three brass plates, a void seed and a simple mechanism.<BR>It is a portable tool that absorbs matter and residual energy from a distance. While right click is held down it pulls the items lying around and the experience orbs towards you, and it uses Dimensional Flux every second to do so. At the same time it draws the flux of the aura of the chunk and turns it into Dimensional Flux of its own, which also cleans the aura a little. Without charge it does nothing, and the Environmental Charger recharges it in your pack.");

        lang.add(CANNON_ENTRY + ".title", "Discharge Cannon");
        lang.add(CANNON_ENTRY + ".stage_0",
                "Dimensional Flux is only energy, and energy only needs a mouth narrow enough. A tube of void metal, a complex mechanism and a seed of the void could squeeze a whole store of it into a single shot.");
        lang.add(CANNON_ENTRY + ".stage_1",
                "The Discharge Cannon is made on an infusion altar from a complex mechanism, four void metal plates, two void seeds and two vis resonators.<BR>It is a weapon that concentrates the stored energy to fire high impact projectiles. Hold right click to charge it and release to fire: the shot crosses the air up to its range, hurts what it hits hard and the creatures around the impact a little, and uses a fixed amount of Dimensional Flux. Without charge it will not fire, and it needs a short rest between shots.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("item." + MOD + "." + GearRegistry.SIPHON_NAME, "Sifón del Vacío portátil");
        lang.add("item." + MOD + "." + GearRegistry.CANNON_NAME, "Cañón de Descarga");
        lang.add(FluxGearItem.TOOLTIP_STORED, "Flujo Dimensional: %s / %s DF");
        lang.add(FluxGearItem.MESSAGE_EMPTY, "No hay suficiente Flujo Dimensional");
        lang.add(SIPHON + "description", "Herramienta portátil que absorbe materia y energía residual a distancia.");
        lang.add(SIPHON + "usage", "Mantén clic derecho: gasta %s DF por segundo y atrae objetos y experiencia en %s bloques");
        lang.add(SIPHON + "residual", "Extrae hasta %s de flux del aura por segundo, a %s DF cada uno");
        lang.add(CANNON + "description", "Arma que concentra la energía acumulada para disparar proyectiles de alto impacto.");
        lang.add(CANNON + "shot", "Mantén y suelta clic derecho: un disparo cuesta %s DF y hace %s de daño");
        lang.add(CANNON + "range", "Alcance: %s bloques, enfriamiento: %s s");

        lang.add(SIPHON_ENTRY + ".title", "Sifón del Vacío portátil");
        lang.add(SIPHON_ENTRY + ".stage_0",
                "El sifón del vacío de mis tierras saca el flux suelto del aura y me da semillas a cambio. Si pudiera llevar su hambre en la mano, podría recoger lo que hay a mi alrededor y llevarme también la energía sobrante del aura.");
        lang.add(SIPHON_ENTRY + ".stage_1",
                "El Sifón del Vacío portátil se fabrica en una mesa de trabajo arcana con tres placas de latón, una semilla del vacío y un mecanismo simple.<BR>Es una herramienta portátil que absorbe materia y energía residual a distancia. Mientras mantienes el clic derecho, atrae hacia ti los objetos del suelo y las esferas de experiencia, y gasta Flujo Dimensional cada segundo para ello. A la vez extrae el flux del aura del chunk y lo convierte en Flujo Dimensional propio, lo que además limpia un poco el aura. Sin carga no hace nada, y el Cargador ambiental lo recarga dentro de tu mochila.");

        lang.add(CANNON_ENTRY + ".title", "Cañón de Descarga");
        lang.add(CANNON_ENTRY + ".stage_0",
                "El Flujo Dimensional no es más que energía, y a la energía solo le hace falta una boca lo bastante estrecha. Un tubo de metal del vacío, un mecanismo complejo y una semilla del vacío podrían apretar todo un almacén en un único disparo.");
        lang.add(CANNON_ENTRY + ".stage_1",
                "El Cañón de Descarga se fabrica en un altar de infusión con un mecanismo complejo, cuatro placas de metal del vacío, dos semillas del vacío y dos resonadores de vis.<BR>Es un arma que concentra la energía acumulada para disparar proyectiles de alto impacto. Mantén el clic derecho para cargarlo y suéltalo para disparar: el disparo cruza el aire hasta su alcance, hiere con dureza a lo que alcanza y un poco a las criaturas de alrededor del impacto, y gasta una cantidad fija de Flujo Dimensional. Sin carga no dispara, y necesita un breve descanso entre disparos.");
    }
}
