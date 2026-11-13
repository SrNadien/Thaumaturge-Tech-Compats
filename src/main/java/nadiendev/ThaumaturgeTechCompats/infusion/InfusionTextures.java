package nadiendev.ThaumaturgeTechCompats.infusion;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;

public final class InfusionTextures {
    public static final boolean RECOLORED = true;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private InfusionTextures() {
    }

    public static String pillar(MatrixVariant variant) {
        if (RECOLORED) {
            return MOD + ":block/" + variant.altarName(AltarPart.PILLAR);
        }
        return switch (variant) {
            case MATRIX -> "thaumaturge:block/pillar_normal";
            case NEUTRONIUM -> "thaumaturge:block/pillar_ancient";
            case INFINITY -> "thaumaturge:block/pillar_eldritch";
        };
    }

    public static String pedestalSide(MatrixVariant variant) {
        if (RECOLORED) {
            return MOD + ":block/" + variant.altarName(AltarPart.PEDESTAL) + "_side";
        }
        return switch (variant) {
            case MATRIX -> "thaumaturge:block/pedestal_side";
            case NEUTRONIUM -> "thaumaturge:block/pedestal_ancient_side";
            case INFINITY -> "thaumaturge:block/pedestal_eldritch_side";
        };
    }

    public static String pedestalTop(MatrixVariant variant) {
        if (RECOLORED) {
            return MOD + ":block/" + variant.altarName(AltarPart.PEDESTAL) + "_top";
        }
        return switch (variant) {
            case MATRIX -> "thaumaturge:block/pedestal_top";
            case NEUTRONIUM -> "thaumaturge:block/pedestal_ancient_top";
            case INFINITY -> "thaumaturge:block/eldritch_stone_1";
        };
    }

    public static String stone(MatrixVariant variant) {
        if (RECOLORED) {
            return MOD + ":block/" + variant.altarName(AltarPart.STONE);
        }
        return switch (variant) {
            case MATRIX -> "thaumaturge:block/arcane_stone_1";
            case NEUTRONIUM -> "thaumaturge:block/ancient_tile";
            case INFINITY -> "thaumaturge:block/eldritch_stone_1";
        };
    }
}
