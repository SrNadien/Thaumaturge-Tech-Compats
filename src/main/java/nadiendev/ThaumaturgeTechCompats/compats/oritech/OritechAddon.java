package nadiendev.ThaumaturgeTechCompats.compats.oritech;

public enum OritechAddon {
    SPEED("speed", "Speed Addon", "Mejora de velocidad", "oritech:machine_speed_addon", "MACHINE_SPEED_ADDON_SHAPE", "1", "oritech:magnetic_coil", "thaumaturge:aer"),
    EFFICIENCY("efficiency", "Efficiency Addon", "Mejora de eficiencia", "oritech:machine_efficiency_addon", "MACHINE_EFFICIENCY_ADDON_SHAPE", "0", "oritech:electrum_ingot", "thaumaturge:ordo"),
    CAPACITOR("capacitor", "Capacitor Addon", "Mejora de capacitor", "oritech:machine_capacitor_addon", "MACHINE_CAPACITOR_ADDON_SHAPE", "0", "oritech:energite_ingot", "thaumaturge:ignis");

    private final String id;
    private final String englishName;
    private final String spanishName;
    private final String baseBlock;
    private final String shapeField;
    private final String textureKey;
    private final String component;
    private final String crystal;

    OritechAddon(String id, String englishName, String spanishName, String baseBlock, String shapeField, String textureKey, String component, String crystal) {
        this.id = id;
        this.englishName = englishName;
        this.spanishName = spanishName;
        this.baseBlock = baseBlock;
        this.shapeField = shapeField;
        this.textureKey = textureKey;
        this.component = component;
        this.crystal = crystal;
    }

    public String id() {
        return id;
    }

    public String englishName() {
        return englishName;
    }

    public String spanishName() {
        return spanishName;
    }

    public String baseBlock() {
        return baseBlock;
    }

    public String shapeField() {
        return shapeField;
    }

    public String textureKey() {
        return textureKey;
    }

    public String component() {
        return component;
    }

    public String crystal() {
        return crystal;
    }

    public String baseName() {
        return baseBlock.substring(baseBlock.indexOf(':') + 1);
    }

    public String blockName(OritechTier tier) {
        return "oritech_" + tier.id() + "_" + id + "_addon";
    }
}
