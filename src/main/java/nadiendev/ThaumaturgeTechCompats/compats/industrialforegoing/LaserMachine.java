package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing;

public enum LaserMachine {
    DRILL("magical_laser_drill", "laser_drill", "minecraft:diamond", "thaumaturge:motus"),
    ORE_BASE("magical_ore_laser_base", "ore_laser_base", "minecraft:diamond_pickaxe", "thaumaturge:terra"),
    FLUID_BASE("magical_fluid_laser_base", "fluid_laser_base", "minecraft:bucket", "thaumaturge:aqua");

    private final String blockName;
    private final String originalName;
    private final String extraComponent;
    private final String aspect;

    LaserMachine(String blockName, String originalName, String extraComponent, String aspect) {
        this.blockName = blockName;
        this.originalName = originalName;
        this.extraComponent = extraComponent;
        this.aspect = aspect;
    }

    public String blockName() {
        return blockName;
    }

    public String original() {
        return IndustrialForegoingIntegration.MOD_ID + ":" + originalName;
    }

    public String extraComponent() {
        return extraComponent;
    }

    public String aspect() {
        return aspect;
    }

    public boolean base() {
        return this != DRILL;
    }
}
