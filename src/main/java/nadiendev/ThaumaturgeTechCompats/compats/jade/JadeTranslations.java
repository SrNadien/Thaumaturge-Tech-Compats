package nadiendev.ThaumaturgeTechCompats.compats.jade;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class JadeTranslations {
    private static final String LINE = "jade." + ThaumaturgeTechCompats.MOD_ID + ".";
    private static final String CONFIG = "config.jade.plugin_" + ThaumaturgeTechCompats.MOD_ID;

    private JadeTranslations() {
    }

    public static void english(LanguageProvider lang) {
        lang.add(CONFIG, "Thaumaturge Tech Compats");
        lang.add(CONFIG + ".block_info", "Avaritia block information");
        lang.add(CONFIG + "." + JadeFamily.TIER.option(), "Show the tier");
        lang.add(CONFIG + "." + JadeFamily.STABILIZER.option(), "Node stabilizer details");
        lang.add(CONFIG + "." + JadeFamily.SMELTER.option(), "Essentia smelter details");
        lang.add(CONFIG + "." + JadeFamily.TRANSDUCER.option(), "Node transducer details");
        lang.add(CONFIG + "." + JadeFamily.FURNACE.option(), "Alchemical furnace details");
        lang.add(CONFIG + "." + JadeFamily.BRAIN_BOX.option(), "Mnemonic matrix details");
        lang.add(CONFIG + "." + JadeFamily.CHARGER.option(), "Workbench charger details");
        lang.add(CONFIG + ".voidminers_aura", "Magical Void Miners aura details");
        lang.add(CONFIG + ".flux_storage_core", "Flux Storage Core details");

        lang.add(LINE + "tier", "Tier: %s");
        lang.add(LINE + "tier.matrix", "Crystal Matrix");
        lang.add(LINE + "tier.neutronium", "Neutronium");
        lang.add(LINE + "tier.infinity", "Infinity");

        lang.add(LINE + "stabilizer.lock", "Lock level: %s");
        lang.add(LINE + "stabilizer.feeding", "Node feeding: %s times slower");
        lang.add(LINE + "stabilizer.settling", "Stabilization: %s times faster");

        lang.add(LINE + "smelter.interval", "Smelting interval: %s ticks (%s s)");
        lang.add(LINE + "smelter.efficiency", "Efficiency: %s%%");

        lang.add(LINE + "transducer.charge", "Charge: %s per tick");
        lang.add(LINE + "transducer.decay", "Loss: 1 point every %s ticks without redstone");
        lang.add(LINE + "transducer.decay_every_tick", "Loss: 1 point per tick without redstone");

        lang.add(LINE + "furnace.speed", "Speed: %s times faster");
        lang.add(LINE + "furnace.savings", "Savings: %s%% of heat, perditio and aqua");

        lang.add(LINE + "mnemonic_matrix.formulas", "Simultaneous formulas added: %s");

        lang.add(LINE + "workbench_charger.discount", "Vis discount: %s%%");
        lang.add(LINE + "workbench_charger.radius", "Vis radius: %s chunks");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add(CONFIG, "Thaumaturge Tech Compats");
        lang.add(CONFIG + ".block_info", "Información de los bloques de Avaritia");
        lang.add(CONFIG + "." + JadeFamily.TIER.option(), "Mostrar el nivel");
        lang.add(CONFIG + "." + JadeFamily.STABILIZER.option(), "Detalles de los estabilizadores de nodos");
        lang.add(CONFIG + "." + JadeFamily.SMELTER.option(), "Detalles de las fundidoras de esencia");
        lang.add(CONFIG + "." + JadeFamily.TRANSDUCER.option(), "Detalles de los transductores de nodos");
        lang.add(CONFIG + "." + JadeFamily.FURNACE.option(), "Detalles de los hornos de alquimia");
        lang.add(CONFIG + "." + JadeFamily.BRAIN_BOX.option(), "Detalles de las matrices mnemotécnicas");
        lang.add(CONFIG + "." + JadeFamily.CHARGER.option(), "Detalles de los cargadores de mesa");
        lang.add(CONFIG + ".voidminers_aura", "Detalles del aura de los Void Miners mágicos");
        lang.add(CONFIG + ".flux_storage_core", "Detalles del Núcleo de almacenamiento de flujo");

        lang.add(LINE + "tier", "Nivel: %s");
        lang.add(LINE + "tier.matrix", "Matriz de Cristal");
        lang.add(LINE + "tier.neutronium", "Neutronio");
        lang.add(LINE + "tier.infinity", "Infinito");

        lang.add(LINE + "stabilizer.lock", "Nivel de bloqueo: %s");
        lang.add(LINE + "stabilizer.feeding", "Alimentación del nodo: %s veces más lenta");
        lang.add(LINE + "stabilizer.settling", "Estabilización: %s veces más rápida");

        lang.add(LINE + "smelter.interval", "Intervalo de fundición: %s ticks (%s s)");
        lang.add(LINE + "smelter.efficiency", "Eficiencia: %s%%");

        lang.add(LINE + "transducer.charge", "Carga: %s por tick");
        lang.add(LINE + "transducer.decay", "Pérdida: 1 punto cada %s ticks sin redstone");
        lang.add(LINE + "transducer.decay_every_tick", "Pérdida: 1 punto por tick sin redstone");

        lang.add(LINE + "furnace.speed", "Velocidad: %s veces más rápido");
        lang.add(LINE + "furnace.savings", "Ahorro: %s%% de calor, perditio y aqua");

        lang.add(LINE + "mnemonic_matrix.formulas", "Fórmulas simultáneas añadidas: %s");

        lang.add(LINE + "workbench_charger.discount", "Descuento de vis: %s%%");
        lang.add(LINE + "workbench_charger.radius", "Radio de vis: %s chunks");
    }
}
