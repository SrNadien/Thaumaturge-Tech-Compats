package nadiendev.ThaumaturgeTechCompats.infusion;

public enum AltarPart {
    STONE("pillar_stone", "Infusion Pillar Stone", "Piedra de pilar de infusión", false),
    PILLAR("infusion_pillar", "Infusion Pillar", "Pilar de infusión", true),
    PEDESTAL("infusion_pedestal", "Pedestal", "Pedestal", true);

    private final String id;
    private final String english;
    private final String spanish;
    private final boolean modifies;

    AltarPart(String id, String english, String spanish, boolean modifies) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
        this.modifies = modifies;
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

    public boolean modifies() {
        return modifies;
    }
}
