package nadiendev.ThaumaturgeTechCompats.registry;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;

public enum CreativeParent {
    MAIN("main", "Thaumaturge Tech Compats", "Thaumaturge Tech Compats");

    private final String id;
    private final String english;
    private final String spanish;

    CreativeParent(String id, String english, String spanish) {
        this.id = id;
        this.english = english;
        this.spanish = spanish;
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

    public String key() {
        return "itemGroup." + ThaumaturgeTechCompats.MOD_ID + ".tab_" + id;
    }
}
