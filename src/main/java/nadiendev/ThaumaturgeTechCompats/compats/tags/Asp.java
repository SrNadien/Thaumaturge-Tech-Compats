package nadiendev.ThaumaturgeTechCompats.compats.tags;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;

import java.util.Locale;

public enum Asp {
    AER, ALIENIS, ALKIMIA, AQUA, AURAM, AVERSIO, BESTIA, COGNITIO, DESIDERIUM, EXANIMIS, FABRICO, GELUM, HERBA, HUMANUS,
    IGNIS, INSTRUMENTUM, LUX, MACHINA, METALLUM, MORTUUS, MOTUS, ORDO, PERDITIO, PERMUTATIO, POTENTIA, PRAECANTATIO,
    PRAEMUNIO, SENSUS, SPIRITUS, TENEBRAE, TERRA, VACUOS, VICTUS, VINCULUM, VITIUM, VITREUS, VOLATUS,
    SOL(ThaumaturgeTechCompats.MOD_ID), TERMINUS(ThaumaturgeTechCompats.MOD_ID), NUCLEAR(ThaumaturgeTechCompats.MOD_ID), COSMIC(ThaumaturgeTechCompats.MOD_ID);

    private final String namespace;

    Asp() {
        this("thaumaturge");
    }

    Asp(String namespace) {
        this.namespace = namespace;
    }

    public String id() {
        return namespace + ":" + name().toLowerCase(Locale.ROOT);
    }

    public AspectPart of(int amount) {
        return new AspectPart(this, amount);
    }
}
