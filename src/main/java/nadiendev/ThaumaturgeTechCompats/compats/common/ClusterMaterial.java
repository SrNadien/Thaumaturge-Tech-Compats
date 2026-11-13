package nadiendev.ThaumaturgeTechCompats.compats.common;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;

import java.util.Arrays;
import java.util.List;

public enum ClusterMaterial {
    IRON("iron", "c:ores/iron", "c:raw_materials/iron", true, false),
    COPPER("copper", "c:ores/copper", "c:raw_materials/copper", true, false),
    GOLD("gold", "c:ores/gold", "c:raw_materials/gold", true, false),
    LEAD("lead", "c:ores/lead", "c:raw_materials/lead", true, false),
    TIN("tin", "c:ores/tin", "c:raw_materials/tin", true, false),
    SILVER("silver", "c:ores/silver", "c:raw_materials/silver", false, false),
    CINNABAR("cinnabar", "c:ores/cinnabar", "c:raw_materials/cinnabar", false, false),
    QUARTZ("quartz", "c:ores/quartz", null, false, false),
    OSMIUM("osmium", "c:ores/osmium", "c:raw_materials/osmium", true, true),
    URANIUM("uranium", "c:ores/uranium", "c:raw_materials/uranium", true, true);

    public static final String OWN_MOD = "mekanism";

    private final String id;
    private final String oreTag;
    private final String rawTag;
    private final boolean chain;
    private final boolean own;

    ClusterMaterial(String id, String oreTag, String rawTag, boolean chain, boolean own) {
        this.id = id;
        this.oreTag = oreTag;
        this.rawTag = rawTag;
        this.chain = chain;
        this.own = own;
    }

    public static List<ClusterMaterial> ownMaterials() {
        return Arrays.stream(values()).filter(ClusterMaterial::own).toList();
    }

    public String id() {
        return id;
    }

    public String itemName() {
        return id + "_cluster";
    }

    public String cluster() {
        if (own) {
            return ThaumaturgeTechCompats.MOD_ID + ":" + itemName();
        }
        return "thaumaturge:cluster_" + id;
    }

    public String oreTag() {
        return oreTag;
    }

    public String rawTag() {
        return rawTag;
    }

    public boolean hasRaw() {
        return rawTag != null;
    }

    public boolean hasChain() {
        return chain;
    }

    public boolean own() {
        return own;
    }

    public String requiredMod() {
        return own ? OWN_MOD : null;
    }
}
