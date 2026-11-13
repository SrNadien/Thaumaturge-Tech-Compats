package nadiendev.ThaumaturgeTechCompats.registry;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;

public enum CreativeSection {
    CORE(CreativeParent.MAIN, "avaritia", "Avaritia", "Avaritia"),
    MACHINES(CreativeParent.MAIN, "avaritia_machines", "Avar. Machines", "Avar. Máquinas"),
    DEVICES(CreativeParent.MAIN, "avaritia_devices", "Avar. Devices", "Avar. Equipos"),
    JARS(CreativeParent.MAIN, "avaritia_jars", "Avar. Jars", "Avar. Tarros"),
    MATRICES(CreativeParent.MAIN, "avaritia_matrices", "Avar. Matrices", "Avar. Matrices"),
    PEARLS(CreativeParent.MAIN, "avaritia_pearls", "Pearls & Cruc.", "Perlas y Cruc."),
    CREATE(CreativeParent.MAIN, "create", "Create", "Create"),
    ORITECH(CreativeParent.MAIN, "oritech", "Oritech", "Oritech"),
    MEKANISM(CreativeParent.MAIN, "mekanism", "Mekanism", "Mekanism"),
    FOREGOING(CreativeParent.MAIN, "foregoing", "Industrial F.", "Foregoing"),
    FTB(CreativeParent.MAIN, "ftb", "FTB Unearthed", "FTB Unearthed"),
    SOLAR_FLUX(CreativeParent.MAIN, "solarflux", "Solar Flux", "Solar Flux"),
    SOPHISTICATED(CreativeParent.MAIN, "sophisticated", "Sophisticated", "Sophisticated"),
    BOTANIA(CreativeParent.MAIN, "botania", "Botania", "Botania"),
    VOID(CreativeParent.MAIN, "void_miners", "Void Miners", "Void Miners"),
    NODES(CreativeParent.MAIN, "nodes", "Nodes & Flux", "Nodos y flujo"),
    FLUX_NETWORK(CreativeParent.MAIN, "flux_network", "Flux Network", "Red de flujo"),
    FLUX_GEAR(CreativeParent.MAIN, "flux_gear", "Flux Gear", "Equipo flujo"),
    FOOD_TAINTED(CreativeParent.MAIN, "food_tainted", "Tainted Food", "Comida corrupta"),
    FOOD_FARMERS(CreativeParent.MAIN, "food_farmers", "Farmers Delight", "Farmers Delight"),
    PURITY(CreativeParent.MAIN, "purity", "Purity", "Pureza"),
    EXTRAS(CreativeParent.MAIN, "extras", "Extras", "Extras");

    private final CreativeParent parent;
    private final String id;
    private final String english;
    private final String spanish;

    CreativeSection(CreativeParent parent, String id, String english, String spanish) {
        this.parent = parent;
        this.id = id;
        this.english = english;
        this.spanish = spanish;
    }

    public CreativeParent parent() {
        return parent;
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
        return "itemGroup." + ThaumaturgeTechCompats.MOD_ID + "." + id;
    }
}
