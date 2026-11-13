package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.resources.Identifier;

public enum PlanetBody {
    MERCURY("mercury", "Mercury", "Mercurio", 0.30F, 1.7F, 20.0D, false, 2.0F),
    VENUS("venus", "Venus", "Venus", 0.45F, 1.3F, -35.0D, true, 0.6F),
    EARTH("earth", "Earth", "Tierra", 0.48F, 1.1F, 15.0D, false, 3.0F),
    MARS("mars", "Mars", "Marte", 0.36F, 0.95F, 50.0D, false, 2.8F),
    JUPITER("jupiter", "Jupiter", "Júpiter", 0.85F, 0.8F, -40.0D, false, 4.5F),
    SATURN("saturn", "Saturn", "Saturno", 0.75F, 0.7F, 65.0D, false, 4.0F),
    URANUS("uranus", "Uranus", "Urano", 0.60F, 0.62F, -60.0D, true, 3.2F),
    NEPTUNE("neptune", "Neptune", "Neptuno", 0.58F, 0.55F, 25.0D, false, 3.4F),
    PLUTO("pluto", "Pluto", "Plutón", 0.24F, 0.5F, 75.0D, true, 1.0F),
    CERES("ceres", "Ceres", "Ceres", 0.26F, 1.45F, -30.0D, false, 2.2F),
    HAUMEA("haumea", "Haumea", "Haumea", 0.28F, 0.45F, 45.0D, false, 6.0F),
    ERIS("eris", "Eris", "Eris", 0.30F, 0.4F, -70.0D, true, 1.2F);

    private final String id;
    private final String english;
    private final String spanish;
    private final float size;
    private final float speed;
    private final double inclination;
    private final boolean retrograde;
    private final float spin;

    PlanetBody(String id, String english, String spanish, float size, float speed, double inclination, boolean retrograde, float spin) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.size = size;
        this.speed = speed;
        this.inclination = inclination;
        this.retrograde = retrograde;
        this.spin = spin;
    }

    public static PlanetBody of(int index) {
        PlanetBody[] bodies = values();
        return bodies[Math.floorMod(index, bodies.length)];
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

    public float size() {
        return size;
    }

    public float speed() {
        return speed;
    }

    public double inclination() {
        return inclination;
    }

    public boolean retrograde() {
        return retrograde;
    }

    public float spin() {
        return spin;
    }

    public String nameKey() {
        return "planet." + ThaumaturgeTechCompats.MOD_ID + "." + id;
    }

    public Identifier texture() {
        return Identifier.fromNamespaceAndPath(ThaumaturgeTechCompats.MOD_ID, "textures/entity/planet/" + ordinal() + ".png");
    }
}
