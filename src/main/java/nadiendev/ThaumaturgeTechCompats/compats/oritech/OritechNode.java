package nadiendev.ThaumaturgeTechCompats.compats.oritech;

public enum OritechNode {
    CINNABAR("cinnabar", "Cinnabar", "cinabrio", "thaumaturge:raw_cinnabar", "thaumaturge:ore_cinnabar", "thaumaturge:deepslate_ore_cinnabar",
            "oritech:resource_node_redstone", "oritech:still_oil_block", 400, 1),
    AMBER("amber", "Amber", "ámbar", "thaumaturge:amber", "thaumaturge:ore_amber", "thaumaturge:deepslate_ore_amber",
            "oritech:resource_node_gold", "oritech:still_fuel_block", 500, 1),
    QUARTZ("quartz", "Quartz", "cuarzo", "minecraft:quartz", "thaumaturge:ore_quartz", "thaumaturge:deepslate_ore_quartz",
            "oritech:resource_node_lapis", "oritech:still_oil_block", 400, 1);

    private final String id;
    private final String englishName;
    private final String spanishName;
    private final String product;
    private final String ore;
    private final String boulderOre;
    private final String catalyst;
    private final String overlay;
    private final int rarity;
    private final int production;

    OritechNode(String id, String englishName, String spanishName, String product, String ore, String boulderOre, String catalyst, String overlay, int rarity, int production) {
        this.id = id;
        this.englishName = englishName;
        this.spanishName = spanishName;
        this.product = product;
        this.ore = ore;
        this.boulderOre = boulderOre;
        this.catalyst = catalyst;
        this.overlay = overlay;
        this.rarity = rarity;
        this.production = production;
    }

    public static OritechNode byId(String id) {
        for (OritechNode node : values()) {
            if (node.id.equals(id)) {
                return node;
            }
        }
        return null;
    }

    public String id() {
        return id;
    }

    public String blockName() {
        return "resource_node_" + id;
    }

    public String englishName() {
        return englishName;
    }

    public String spanishName() {
        return spanishName;
    }

    public String product() {
        return product;
    }

    public String ore() {
        return ore;
    }

    public String boulderOre() {
        return boulderOre;
    }

    public String catalyst() {
        return catalyst;
    }

    public String overlay() {
        return overlay;
    }

    public int rarity() {
        return rarity;
    }

    public int production() {
        return production;
    }
}
