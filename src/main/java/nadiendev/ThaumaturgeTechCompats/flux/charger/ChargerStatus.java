package nadiendev.ThaumaturgeTechCompats.flux.charger;

public enum ChargerStatus {
    EMPTY("empty"),
    IDLE("idle"),
    CHARGING("charging");

    private final String id;

    ChargerStatus(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
