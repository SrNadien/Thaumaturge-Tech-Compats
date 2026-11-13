package nadiendev.ThaumaturgeTechCompats.infusion;

public enum StoneKind {
    COST("cost", "Cost", "Descuento", "thaumaturge:matrix_cost"),
    SPEED("speed", "Speed", "Velocidad", "thaumaturge:matrix_speed");

    private final String id;
    private final String english;
    private final String spanish;
    private final String baseStone;

    StoneKind(String id, String english, String spanish, String baseStone) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.baseStone = baseStone;
    }

    public String id() {
        return id;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public String baseStone() {
        return baseStone;
    }
}
