package nadiendev.ThaumaturgeTechCompats.flux.field;

public final class ThrusterState {
    private int heat;
    private int cooldown;

    public int heat() {
        return heat;
    }

    public void heat(int value) {
        heat = Math.max(0, value);
    }

    public int cooldown() {
        return cooldown;
    }

    public void cooldown(int value) {
        cooldown = Math.max(0, value);
    }
}
