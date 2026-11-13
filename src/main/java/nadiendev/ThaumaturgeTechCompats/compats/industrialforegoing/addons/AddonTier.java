package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

public enum AddonTier {
    BRASS("brass", "thaumaturge:plate_brass", "Brass", "latón", 1000, 200),
    THAUMIUM("thaumium", "thaumaturge:plate_thaumium", "Thaumium", "taumio", 2000, 300),
    VOID("void", "thaumaturge:plate_void", "Void Metal", "metal del vacío", 4000, 400);

    private final String id;
    private final String plate;
    private final String english;
    private final String spanish;
    private final int deathAmount;
    private final int processingTime;

    AddonTier(String id, String plate, String english, String spanish, int deathAmount, int processingTime) {
        this.id = id;
        this.plate = plate;
        this.english = english;
        this.spanish = spanish;
        this.deathAmount = deathAmount;
        this.processingTime = processingTime;
    }

    public String id() {
        return id;
    }

    public String plate() {
        return plate;
    }

    public String english() {
        return english;
    }

    public String spanish() {
        return spanish;
    }

    public int deathAmount() {
        return deathAmount;
    }

    public int processingTime() {
        return processingTime;
    }

    public AddonTier previous() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }
}
