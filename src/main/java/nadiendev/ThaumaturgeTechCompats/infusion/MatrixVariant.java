package nadiendev.ThaumaturgeTechCompats.infusion;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.Tier;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Rarity;

public enum MatrixVariant implements StringRepresentable {
    MATRIX(Tier.MATRIX, "Crystal Matrix", "Matriz de Cristal", Rarity.RARE, false,
            1.0D, 1, 6, -0.03D, 0, 0.0D, -2,
            new AltarDefaults(-0.1D, -1, 0.25D), new AltarDefaults(-0.01D, 0, 0.0D)),
    NEUTRONIUM(Tier.NEUTRONIUM, "Neutronium", "Neutronio", Rarity.EPIC, true,
            1.5D, 1, 8, -0.05D, 0, 0.0D, -3,
            new AltarDefaults(-0.15D, -2, 0.35D), new AltarDefaults(-0.015D, 0, 0.0D)),
    INFINITY(Tier.INFINITY, "Infinity", "Infinito", Rarity.EPIC, true,
            3.0D, 20, 12, -0.125D, 0, -0.01D, -4,
            new AltarDefaults(-0.2D, -3, 0.5D), new AltarDefaults(-0.02D, 0, 0.0D));

    public static final StringRepresentable.EnumCodec<MatrixVariant> CODEC = StringRepresentable.fromEnum(MatrixVariant::values);

    private final Tier tier;
    private final String materialEnglish;
    private final String materialSpanish;
    private final Rarity rarity;
    private final boolean fireResistant;
    private final double defaultLossMultiplier;
    private final int defaultEssentiaMultiplier;
    private final int defaultRadius;
    private final double defaultCostStoneCost;
    private final int defaultCostStoneCycle;
    private final double defaultSpeedStoneCost;
    private final int defaultSpeedStoneCycle;
    private final AltarDefaults defaultPillar;
    private final AltarDefaults defaultPedestal;

    MatrixVariant(Tier tier, String materialEnglish, String materialSpanish, Rarity rarity, boolean fireResistant,
                  double defaultLossMultiplier, int defaultEssentiaMultiplier,
                  int defaultRadius, double defaultCostStoneCost, int defaultCostStoneCycle, double defaultSpeedStoneCost,
                  int defaultSpeedStoneCycle, AltarDefaults defaultPillar, AltarDefaults defaultPedestal) {
        this.tier = tier;
        this.materialEnglish = materialEnglish;
        this.materialSpanish = materialSpanish;
        this.rarity = rarity;
        this.fireResistant = fireResistant;
        this.defaultLossMultiplier = defaultLossMultiplier;
        this.defaultEssentiaMultiplier = defaultEssentiaMultiplier;
        this.defaultRadius = defaultRadius;
        this.defaultCostStoneCost = defaultCostStoneCost;
        this.defaultCostStoneCycle = defaultCostStoneCycle;
        this.defaultSpeedStoneCost = defaultSpeedStoneCost;
        this.defaultSpeedStoneCycle = defaultSpeedStoneCycle;
        this.defaultPillar = defaultPillar;
        this.defaultPedestal = defaultPedestal;
    }

    public Tier tier() {
        return tier;
    }

    public String id() {
        return tier.id();
    }

    public String ingot() {
        return tier.ingot();
    }

    public String matrixName() {
        return tier.id() + "_infusion_matrix";
    }

    public String stoneName(StoneKind kind) {
        return tier.id() + "_infusion_" + kind.id() + "_stone";
    }

    public String altarName(AltarPart part) {
        return tier.id() + "_" + part.id();
    }

    public Identifier texture() {
        return Identifier.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, "textures/block/" + tier.id() + "_infuser.png");
    }

    public String materialEnglish() {
        return materialEnglish;
    }

    public String materialSpanish() {
        return materialSpanish;
    }

    public Rarity rarity() {
        return rarity;
    }

    public boolean fireResistant() {
        return fireResistant;
    }

    public double defaultLossMultiplier() {
        return defaultLossMultiplier;
    }

    public int defaultEssentiaMultiplier() {
        return defaultEssentiaMultiplier;
    }

    public int defaultRadius() {
        return defaultRadius;
    }

    public double defaultStoneCost(StoneKind kind) {
        return kind == StoneKind.COST ? defaultCostStoneCost : defaultSpeedStoneCost;
    }

    public int defaultStoneCycle(StoneKind kind) {
        return kind == StoneKind.COST ? defaultCostStoneCycle : defaultSpeedStoneCycle;
    }

    public AltarDefaults defaultAltar(AltarPart part) {
        return part == AltarPart.PILLAR ? defaultPillar : defaultPedestal;
    }

    public MatrixVariant previous() {
        return this == MATRIX ? null : values()[ordinal() - 1];
    }

    @Override
    public String getSerializedName() {
        return tier.id();
    }
}
