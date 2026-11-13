package nadiendev.ThaumaturgeTechCompats.flux.field;

public record ThrusterInput(boolean up, boolean forward) {
    public static final ThrusterInput NONE = new ThrusterInput(false, false);
}
