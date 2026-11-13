package nadiendev.ThaumaturgeTechCompats.stabilizer;

import nadiendev.ThaumaturgeTechCompats.device.DeviceTier;
import net.minecraft.resources.ResourceLocation;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;

public enum StabilizerTier {
    MATRIX("matrix", "avaritia:crystal_matrix_ingot", "thaumaturge:node_stabilizer_advanced", 3.0F, 30.0F, 12, 48),
    NEUTRONIUM("neutronium", "avaritia:neutron_ingot", "thaumaturgetechcompats:matrix_node_stabilizer", 4.0F, 60.0F, 14, 64),
    INFINITY("infinity", "avaritia:infinity_ingot", "thaumaturgetechcompats:neutronium_node_stabilizer", 5.0F, 120.0F, 16, 64);

    private final String id;
    private final String ingot;
    private final String catalyst;
    private final float hardness;
    private final float resistance;
    private final int instability;
    private final int auram;

    StabilizerTier(String id, String ingot, String catalyst, float hardness, float resistance, int instability, int auram) {
        this.id = id;
        this.ingot = ingot;
        this.catalyst = catalyst;
        this.hardness = hardness;
        this.resistance = resistance;
        this.instability = instability;
        this.auram = auram;
    }

    public DeviceTier device() {
        return DeviceTier.values()[ordinal()];
    }

    public int lockLevel() {
        return device().lockLevel();
    }

    public String plate() {
        return device().plate();
    }

    public String id() {
        return id;
    }

    public String blockName() {
        return id + "_node_stabilizer";
    }

    public String ingot() {
        return ingot;
    }

    public String catalyst() {
        return catalyst;
    }

    public float hardness() {
        return hardness;
    }

    public float resistance() {
        return resistance;
    }

    public int instability() {
        return instability;
    }

    public int auram() {
        return auram;
    }

    public ResourceLocation baseTexture() {
        return ThaumaturgeTechCompats.id("textures/block/" + blockName() + ".png");
    }

    public ResourceLocation overlayTexture() {
        return ThaumaturgeTechCompats.id("textures/block/" + blockName() + "_over.png");
    }
}
