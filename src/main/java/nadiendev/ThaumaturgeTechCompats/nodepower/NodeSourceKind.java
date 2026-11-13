package nadiendev.ThaumaturgeTechCompats.nodepower;

public enum NodeSourceKind {
    JAR_NODE("jar_node"),
    FLUX_JAR("flux_jar"),
    RIFT_JAR("rift_jar"),
    RIFT("rift"),
    WORLD_NODE("world_node");

    private final String id;

    NodeSourceKind(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public static NodeSourceKind byId(String id) {
        for (NodeSourceKind kind : values()) {
            if (kind.id.equals(id)) {
                return kind;
            }
        }
        return null;
    }
}
