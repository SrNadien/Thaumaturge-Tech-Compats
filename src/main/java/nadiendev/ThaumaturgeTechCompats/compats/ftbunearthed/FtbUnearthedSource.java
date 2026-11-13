package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed;

public enum FtbUnearthedSource {
    SAND("sands", "#c:sands"),
    GRAVEL("gravels", "#c:gravels");

    private final String id;
    private final String block;

    FtbUnearthedSource(String id, String block) {
        this.id = id;
        this.block = block;
    }

    public String id() {
        return id;
    }

    public String block() {
        return block;
    }
}
