package nadiendev.ThaumaturgeTechCompats.infusion;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.List;

public final class InfusionTranslations {
    private static final String BLOCK = "block.thaumaturgetechcompats.";
    private static final String INSTABILITY = "gui.thaumaturgetechcompats.instability.";
    private static final String JEI_TITLE = "gui.thaumaturgetechcompats.recipe_type.infusion";
    private static final String TOOLTIP = "tooltip.thaumaturgetechcompats.infusion_matrix.";
    private static final String MATRIX_ENTRY = "research.thaumaturgetechcompats." + InfusionContent.MATRIX_ENTRY;
    private static final String STONE_ENTRY = "research.thaumaturgetechcompats." + InfusionContent.STONE_ENTRY;
    private static final String ALTAR_ENTRY = "research.thaumaturgetechcompats." + InfusionContent.ALTAR_ENTRY;

    private InfusionTranslations() {
    }

    public static List<Component> tooltip(MatrixVariant variant) {
        List<Component> lines = new ArrayList<>();
        Component requirement = requirement(variant);
        if (requirement != null) {
            lines.add(requirement);
        }
        lines.add(Component.translatable(TOOLTIP + "base"));
        switch (variant) {
            case MATRIX -> lines.add(Component.translatable(TOOLTIP + "solar", format(InfusionConfig.number(variant, "day_multiplier"))));
            case NEUTRONIUM -> lines.add(Component.translatable(TOOLTIP + "nuclear"));
            case INFINITY -> lines.add(Component.translatable(TOOLTIP + "cosmic"));
        }
        int essentia = InfusionConfig.integer(variant, "essentia_multiplier");
        if (essentia > 1) {
            lines.add(Component.translatable(TOOLTIP + "essentia", essentia));
        }
        return lines;
    }

    public static Component requirement(MatrixVariant variant) {
        Block required = InfusionRegistry.requiredMatrix(variant);
        return required == null ? null : Component.translatable(TOOLTIP + "requirement", required.getName());
    }

    public static Component tierRequirement(Item item) {
        Block required = InfusionTiers.requiredMatrix(item);
        return required == null ? null : Component.translatable(TOOLTIP + "tier_requirement", required.getName());
    }

    public static MutableComponent instabilityName(MatrixVariant variant) {
        ChatFormatting color = switch (variant) {
            case MATRIX -> ChatFormatting.GOLD;
            case NEUTRONIUM -> ChatFormatting.GREEN;
            case INFINITY -> ChatFormatting.DARK_PURPLE;
        };
        return Component.translatable(INSTABILITY + variant.id()).withStyle(color);
    }

    public static List<Component> instabilityTooltip(MatrixVariant variant) {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.translatable(INSTABILITY + "header", instabilityName(variant)));
        for (InstabilityKind kind : InstabilityKind.values()) {
            if (kind.variant() == variant) {
                lines.add(Component.translatable(INSTABILITY + kind.registryName()).withStyle(ChatFormatting.GRAY));
            }
        }
        return lines;
    }

    public static Component jeiTitle(Block matrix) {
        return Component.translatable(JEI_TITLE, Component.translatable("gui.thaumaturge.recipe_type.infusion"), matrix.getName());
    }

    private static String format(double value) {
        return value == Math.rint(value) ? String.valueOf((long) value) : String.valueOf(value);
    }

    public static void english(LanguageProvider lang) {
        for (MatrixVariant variant : MatrixVariant.values()) {
            lang.add(BLOCK + variant.matrixName(), "Runic Matrix (" + variant.materialEnglish() + ")");
            for (StoneKind kind : StoneKind.values()) {
                lang.add(BLOCK + variant.stoneName(kind), "Infusion " + kind.english() + " Stone (" + variant.materialEnglish() + ")");
            }
            for (AltarPart part : AltarPart.values()) {
                lang.add(BLOCK + variant.altarName(part), part.english() + " (" + variant.materialEnglish() + ")");
            }
        }
        lang.add(JEI_TITLE, "%s (%s)");
        lang.add(INSTABILITY + "header", "%s instability can bring:");
        lang.add(INSTABILITY + "matrix", "Solar");
        lang.add(INSTABILITY + "neutronium", "Nuclear");
        lang.add(INSTABILITY + "infinity", "Cosmic");
        lang.add(INSTABILITY + "solar_fire", "Fires around the altar.");
        lang.add(INSTABILITY + "solar_lightning", "Lightning strikes.");
        lang.add(INSTABILITY + "solar_burn", "Burns everything alive nearby.");
        lang.add(INSTABILITY + "nuclear_radiation", "Radiation that hurts and weakens.");
        lang.add(INSTABILITY + "nuclear_contamination", "Flux poured into the aura.");
        lang.add(INSTABILITY + "nuclear_meltdown", "A meltdown that explodes.");
        lang.add(INSTABILITY + "cosmic_flux", "Flux torn open.");
        lang.add(INSTABILITY + "cosmic_warp", "Warp for the players nearby.");
        lang.add(INSTABILITY + "cosmic_summon", "Creatures from beyond.");
        lang.add(INSTABILITY + "cosmic_teleport", "Everything nearby thrown elsewhere.");
        lang.add(TOOLTIP + "requirement", "Can only be infused on an altar whose center is this Runic Matrix: %s.");
        lang.add(TOOLTIP + "tier_requirement", "Must be infused on an altar whose center is %s, which brings its own instability to the craft.");
        lang.add(TOOLTIP + "base", "Needs no symmetric altar and never recovers stability. What goes wrong is decided by the instability of the craft being infused.");
        lang.add(TOOLTIP + "solar", "Burns, sets fires and calls lightning. Crafting is %sx more unstable by day.");
        lang.add(TOOLTIP + "nuclear", "Gives off radiation, pours flux and can melt down in an explosion.");
        lang.add(TOOLTIP + "cosmic", "Tears open flux, gives warp, summons creatures from beyond and throws everything near it somewhere else.");
        lang.add(TOOLTIP + "essentia", "Counts every unit of essentia it receives as %s.");
        lang.add(MATRIX_ENTRY + ".title", "Runic Matrices of Avaritia");
        lang.add(MATRIX_ENTRY + ".stage_0",
                "The Runic Matrix keeps an infusion in order only while the altar around it is perfectly balanced. I have no wish to be bound to that symmetry.<BR>If the matrix were made of the metals that stretched my jars, it could keep its own order, and answer for it with its own disorder.");
        lang.add(MATRIX_ENTRY + ".stage_1",
                "Each Runic Matrix is made on an infusion altar, starting from the one before it:<BR>- The Runic Matrix (Crystal Matrix) needs a Runic Matrix and crystal matrix ingots.<BR>- The Runic Matrix (Neutronium) needs the Crystal Matrix one and neutron ingots.<BR>- The Runic Matrix (Infinity) needs the Neutronium one, infinity ingots and an Extremely Primordial Pearl.<BR>They infuse exactly like the Runic Matrix and sit on the same altar, but they never ask for a symmetric altar and their stability does not recover. What goes wrong is decided by the instability of the craft being infused, and it is never gentle:<BR>- The Crystal Matrix one suffers solar instability: it burns, sets fires and calls lightning, and every craft is twice as unstable by day.<BR>- The Neutronium one suffers nuclear instability: it gives off radiation, pours flux and can melt down in an explosion.<BR>- The Infinity one counts every unit of essentia it receives as twenty, and it suffers cosmic instability, the most dangerous of all: it tears open flux, gives warp, summons creatures from beyond and throws everything near it somewhere else.<BR>Each one can only be infused on an altar whose center is the tier before it: the Crystal Matrix one on a plain Runic Matrix, the Neutronium one on the Crystal Matrix one and the Infinity one on the Neutronium one.<BR>Everything else I infuse from these metals answers to the same disorder: jars, flux jars, brain boxes, workbench chargers, node stabilizers, scribing tools, foci, wand parts and the pedestals of the altar. Each of those can only be infused on an altar whose center is the Runic Matrix of its own tier, so the Crystal Matrix ones suffer solar instability, the Neutronium ones nuclear instability and the Infinity ones cosmic instability. Their tooltip says which matrix they need.<BR>How strong each of these is, and whether each tier asks for its own matrix, can be set in the configuration.");
        lang.add(STONE_ENTRY + ".title", "Runic Stones of Avaritia");
        lang.add(STONE_ENTRY + ".stage_0",
                "The Infusion Speed Stone and the Infusion Cost Stone help the altar, but only a little, and each asks something back. The metals of Avaritia should ask for less and give more.");
        lang.add(STONE_ENTRY + ".stage_1",
                "Each stone is made on an infusion altar, starting from the one before it, with four ingots of its metal and, for the Infinity ones, an Extremely Primordial Pearl:<BR>- The Crystal Matrix stones start from the Infusion Cost Stone or the Infusion Speed Stone.<BR>- The Neutronium stones start from the Crystal Matrix ones.<BR>- The Infinity stones start from the Neutronium ones.<BR>Each stone can only be infused on an altar whose Runic Matrix is the tier before it: the Crystal Matrix ones on a plain Runic Matrix, the Neutronium ones on the Crystal Matrix one and the Infinity ones on the Neutronium one.<BR>They go under the four pillars of the altar just like the plain stones, and each tier lowers the cost or the cycle time of the altar more than the one before. The altar never costs less than half, and the exact values are set in the configuration.");
        lang.add(ALTAR_ENTRY + ".title", "Pillars of Avaritia");
        lang.add(ALTAR_ENTRY + ".stage_0",
                "The pillars and the pedestals of the infusion altar are only stone, and stone is what limits it. If they were made of the metals of Avaritia, the altar would ask for less and answer faster.");
        lang.add(ALTAR_ENTRY + ".stage_1",
                "The infusion pillar stones are made at the arcane workbench, four at a time, from ingots and the stones of the tier before: the Crystal Matrix ones start from Eldritch Stone Tiles, the Neutronium ones from the Crystal Matrix ones and the Infinity ones from the Neutronium ones, with an Extremely Primordial Pearl.<BR>The pedestals are infused from the pedestal of the tier before, starting from the Eldritch Pedestal, on an altar whose center is the Runic Matrix of their own tier.<BR>The altar is built exactly like the plain one: eight stones, a pedestal and a Runic Matrix of any kind. Salis Mundus turns the stones into pillars of the same tier, and the look of the pillars follows the stone used.<BR>Each tier lowers the cost and the cycle time of the altar more than the one before, and the pillars also add stability for the plain Runic Matrix. The exact values are set in the configuration.");
    }

    public static void spanish(LanguageProvider lang) {
        for (MatrixVariant variant : MatrixVariant.values()) {
            lang.add(BLOCK + variant.matrixName(), "Matriz rúnica de " + variant.materialSpanish());
            for (StoneKind kind : StoneKind.values()) {
                lang.add(BLOCK + variant.stoneName(kind), "Runa de " + kind.spanish() + " de Infusión de " + variant.materialSpanish());
            }
            for (AltarPart part : AltarPart.values()) {
                lang.add(BLOCK + variant.altarName(part), part.spanish() + " de " + variant.materialSpanish());
            }
        }
        lang.add(JEI_TITLE, "%s (%s)");
        lang.add(INSTABILITY + "header", "La inestabilidad %s puede traer:");
        lang.add(INSTABILITY + "matrix", "Solar");
        lang.add(INSTABILITY + "neutronium", "Nuclear");
        lang.add(INSTABILITY + "infinity", "Cósmica");
        lang.add(INSTABILITY + "solar_fire", "Fuegos alrededor del altar.");
        lang.add(INSTABILITY + "solar_lightning", "Caída de rayos.");
        lang.add(INSTABILITY + "solar_burn", "Quema todo lo vivo cercano.");
        lang.add(INSTABILITY + "nuclear_radiation", "Radiación que daña y debilita.");
        lang.add(INSTABILITY + "nuclear_contamination", "Flux derramado en el aura.");
        lang.add(INSTABILITY + "nuclear_meltdown", "Una fusión que explota.");
        lang.add(INSTABILITY + "cosmic_flux", "Flux desgarrado.");
        lang.add(INSTABILITY + "cosmic_warp", "Disformidad para los jugadores cercanos.");
        lang.add(INSTABILITY + "cosmic_summon", "Criaturas del más allá.");
        lang.add(INSTABILITY + "cosmic_teleport", "Todo lo cercano lanzado a otro lugar.");
        lang.add(TOOLTIP + "requirement", "Solo se puede infundir en un altar cuyo centro sea esta Matriz rúnica: %s.");
        lang.add(TOOLTIP + "tier_requirement", "Debe infundirse en un altar cuyo centro sea %s, que aporta su propia inestabilidad al crafteo.");
        lang.add(TOOLTIP + "base", "No necesita un altar simétrico y nunca recupera estabilidad. Lo que sale mal lo decide la inestabilidad del crafteo que se está infundiendo.");
        lang.add(TOOLTIP + "solar", "Quema, prende fuegos y llama a los rayos. El crafteo es %sx más inestable de día.");
        lang.add(TOOLTIP + "nuclear", "Emite radiación, derrama flux y puede fundirse en una explosión.");
        lang.add(TOOLTIP + "cosmic", "Abre desgarros de flux, da disformidad, invoca criaturas del más allá y lanza todo lo que tiene cerca a otro lugar.");
        lang.add(TOOLTIP + "essentia", "Cuenta cada unidad de esencia que recibe como %s.");
        lang.add(MATRIX_ENTRY + ".title", "Matrices rúnicas de Avaritia");
        lang.add(MATRIX_ENTRY + ".stage_0",
                "La Matriz rúnica solo mantiene una infusión en orden mientras el altar que la rodea esté perfectamente equilibrado. No quiero estar atado a esa simetría.<BR>Si la matriz estuviera hecha con los metales que ensancharon mis frascos, podría guardar su propio orden y responder de él con su propio desorden.");
        lang.add(MATRIX_ENTRY + ".stage_1",
                "Cada Matriz rúnica se fabrica en un altar de infusión, partiendo de la anterior:<BR>- La Matriz rúnica de Matriz de Cristal necesita una Matriz rúnica y lingotes de matriz de cristal.<BR>- La Matriz rúnica de Neutronio necesita la de Matriz de Cristal y lingotes de neutrones.<BR>- La Matriz rúnica de Infinito necesita la de Neutronio, lingotes de infinito y una Perla Extremadamente Primordial.<BR>Infunden igual que la Matriz rúnica y se colocan en el mismo altar, pero nunca piden un altar simétrico y su estabilidad no se recupera. Lo que sale mal lo decide la inestabilidad del crafteo que se infunde, y nunca es suave:<BR>- La de Matriz de Cristal sufre inestabilidad solar: quema, prende fuegos y llama a los rayos, y cada crafteo es el doble de inestable de día.<BR>- La de Neutronio sufre inestabilidad nuclear: emite radiación, derrama flux y puede fundirse en una explosión.<BR>- La de Infinito cuenta cada unidad de esencia que recibe como veinte, y sufre inestabilidad cósmica, la más peligrosa de todas: abre desgarros de flux, da disformidad, invoca criaturas del más allá y lanza todo lo que tiene cerca a otro lugar.<BR>Cada una solo se puede infundir en un altar cuyo centro sea la del nivel anterior: la de Matriz de Cristal en una Matriz rúnica normal, la de Neutronio en la de Matriz de Cristal y la de Infinito en la de Neutronio.<BR>Todo lo demás que infundo con estos metales responde al mismo desorden: frascos, frascos de flux, cajas de cerebro, cargadores de mesa de trabajo, estabilizadores de nodo, herramientas de escritura, focos, piezas de varita y los pedestales del altar. Cada uno solo se puede infundir en un altar cuyo centro sea la Matriz rúnica de su propio nivel, así que los de Matriz de Cristal sufren inestabilidad solar, los de Neutronio inestabilidad nuclear y los de Infinito inestabilidad cósmica. Su descripción dice qué matriz necesitan.<BR>La fuerza de cada uno de estos efectos, y si cada nivel pide su propia matriz, se ajusta en la configuración.");
        lang.add(STONE_ENTRY + ".title", "Runas de Avaritia");
        lang.add(STONE_ENTRY + ".stage_0",
                "La Runa de Velocidad de Infusión y la Runa de Descuento de Infusión ayudan al altar, pero poco, y cada una pide algo a cambio. Los metales de Avaritia deberían pedir menos y dar más.");
        lang.add(STONE_ENTRY + ".stage_1",
                "Cada runa se fabrica en un altar de infusión, partiendo de la anterior, con cuatro lingotes de su metal y, en las de Infinito, una Perla Extremadamente Primordial:<BR>- Las runas de Matriz de Cristal parten de la Runa de Descuento o de la Runa de Velocidad de Infusión.<BR>- Las runas de Neutronio parten de las de Matriz de Cristal.<BR>- Las runas de Infinito parten de las de Neutronio.<BR>Cada runa solo se puede infundir en un altar cuya Matriz rúnica sea la del nivel anterior: las de Matriz de Cristal en una Matriz rúnica normal, las de Neutronio en la de Matriz de Cristal y las de Infinito en la de Neutronio.<BR>Se colocan bajo los cuatro pilares del altar igual que las runas normales, y cada nivel baja más el coste o el tiempo de ciclo del altar que el anterior. El altar nunca cuesta menos de la mitad, y los valores exactos se ajustan en la configuración.");
        lang.add(ALTAR_ENTRY + ".title", "Pilares de Avaritia");
        lang.add(ALTAR_ENTRY + ".stage_0",
                "Los pilares y los pedestales del altar de infusión son solo piedra, y la piedra es lo que lo limita. Si estuvieran hechos con los metales de Avaritia, el altar pediría menos y respondería más rápido.");
        lang.add(ALTAR_ENTRY + ".stage_1",
                "Las piedras de pilar de infusión se fabrican en la mesa de trabajo arcana, de cuatro en cuatro, con lingotes y las piedras del nivel anterior: las de Matriz de Cristal parten de Baldosas de piedra de Eldricht, las de Neutronio de las de Matriz de Cristal y las de Infinito de las de Neutronio, con una Perla Extremadamente Primordial.<BR>Los pedestales se infunden a partir del pedestal del nivel anterior, empezando por el Pedestal de Eldritch, en un altar cuyo centro sea la Matriz rúnica de su propio nivel.<BR>El altar se construye igual que el normal: ocho piedras, un pedestal y una Matriz rúnica de cualquier tipo. Salis Mundus convierte las piedras en pilares del mismo nivel, y el aspecto de los pilares sigue a la piedra usada.<BR>Cada nivel baja más el coste y el tiempo de ciclo del altar que el anterior, y los pilares también dan estabilidad a la Matriz rúnica normal. Los valores exactos se ajustan en la configuración.");
    }
}
