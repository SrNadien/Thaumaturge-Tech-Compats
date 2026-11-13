package nadiendev.ThaumaturgeTechCompats.compats.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TagMaterials {
    private static final List<TagMaterial> ALL = new ArrayList<>();

    static {
        metal("iron", Asp.METALLUM.of(15));
        metal("copper", Asp.METALLUM.of(10), Asp.PERMUTATIO.of(5));
        metal("gold", Asp.METALLUM.of(10), Asp.DESIDERIUM.of(10), Asp.SOL.of(1));
        metal("netherite", Asp.METALLUM.of(20), Asp.IGNIS.of(10), Asp.TENEBRAE.of(5), Asp.NUCLEAR.of(4));
        metal("tin", Asp.METALLUM.of(10), Asp.VITREUS.of(5));
        metal("lead", Asp.METALLUM.of(10), Asp.PRAEMUNIO.of(5));
        metal("silver", Asp.METALLUM.of(10), Asp.LUX.of(5));
        metal("nickel", Asp.METALLUM.of(10), Asp.ORDO.of(3));
        metal("zinc", Asp.METALLUM.of(10), Asp.PERMUTATIO.of(3));
        metal("aluminum", Asp.METALLUM.of(10), Asp.AER.of(4));
        metal("osmium", Asp.METALLUM.of(12), Asp.PRAEMUNIO.of(4));
        metal("platinum", Asp.METALLUM.of(12), Asp.DESIDERIUM.of(8));
        metal("iridium", Asp.METALLUM.of(12), Asp.DESIDERIUM.of(8));
        metal("uranium", Asp.METALLUM.of(10), Asp.POTENTIA.of(10), Asp.PERDITIO.of(5), Asp.NUCLEAR.of(4));
        metal("thorium", Asp.METALLUM.of(10), Asp.POTENTIA.of(8), Asp.PERDITIO.of(4), Asp.NUCLEAR.of(4));
        metal("plutonium", Asp.METALLUM.of(10), Asp.POTENTIA.of(15), Asp.PERDITIO.of(10), Asp.NUCLEAR.of(6));
        metal("polonium", Asp.METALLUM.of(10), Asp.POTENTIA.of(12), Asp.PERDITIO.of(8), Asp.NUCLEAR.of(6));
        metal("cobalt", Asp.METALLUM.of(10), Asp.IGNIS.of(5));
        metal("titanium", Asp.METALLUM.of(12), Asp.PRAEMUNIO.of(3));
        metal("tungsten", Asp.METALLUM.of(15), Asp.IGNIS.of(5));
        metal("steel", Asp.METALLUM.of(15), Asp.ORDO.of(5));
        metal("bronze", Asp.METALLUM.of(10), Asp.PRAEMUNIO.of(5));
        metal("brass", Asp.METALLUM.of(10), Asp.PERMUTATIO.of(5), Asp.FABRICO.of(2));
        metal("invar", Asp.METALLUM.of(12), Asp.ORDO.of(5));
        metal("constantan", Asp.METALLUM.of(10), Asp.PERMUTATIO.of(4), Asp.ORDO.of(4));
        metal("electrum", Asp.METALLUM.of(10), Asp.DESIDERIUM.of(5), Asp.POTENTIA.of(5));
        metal("signalum", Asp.METALLUM.of(10), Asp.POTENTIA.of(5), Asp.SENSUS.of(5));
        metal("lumium", Asp.METALLUM.of(10), Asp.LUX.of(10), Asp.SOL.of(2));
        metal("enderium", Asp.METALLUM.of(10), Asp.ALIENIS.of(10), Asp.COSMIC.of(2));
        metal("refined_obsidian", Asp.TENEBRAE.of(10), Asp.PRAEMUNIO.of(10), Asp.METALLUM.of(5));
        metal("refined_glowstone", Asp.LUX.of(10), Asp.SENSUS.of(5), Asp.METALLUM.of(5), Asp.SOL.of(3));

        gem("ruby", Asp.VITREUS.of(15), Asp.DESIDERIUM.of(10), Asp.IGNIS.of(5));
        gem("sapphire", Asp.VITREUS.of(15), Asp.DESIDERIUM.of(10), Asp.AQUA.of(5));
        gem("peridot", Asp.VITREUS.of(15), Asp.DESIDERIUM.of(8), Asp.HERBA.of(5));
        gem("topaz", Asp.VITREUS.of(15), Asp.DESIDERIUM.of(8), Asp.LUX.of(5));
        gem("citrine", Asp.VITREUS.of(12), Asp.LUX.of(6), Asp.SENSUS.of(3));
        gem("apatite", Asp.VITREUS.of(5), Asp.TERRA.of(5));
        gem("fluorite", Asp.VITREUS.of(10), Asp.LUX.of(4));
        gem("diamond", Asp.VITREUS.of(15), Asp.DESIDERIUM.of(15));
        gem("emerald", Asp.VITREUS.of(15), Asp.DESIDERIUM.of(10));
        gem("lapis", Asp.SENSUS.of(5));
        gem("quartz", Asp.VITREUS.of(5));
        gem("amethyst", Asp.VITREUS.of(10), Asp.AURAM.of(5));

        dust("sulfur", Asp.IGNIS.of(5), Asp.ALKIMIA.of(5), Asp.PERDITIO.of(2));
        dust("niter", Asp.ALKIMIA.of(5), Asp.PERDITIO.of(3), Asp.IGNIS.of(2));
        dust("saltpeter", Asp.ALKIMIA.of(5), Asp.PERDITIO.of(3), Asp.IGNIS.of(2));
        dust("salt", Asp.AQUA.of(3), Asp.TERRA.of(3));
        dust("coal", Asp.IGNIS.of(8), Asp.POTENTIA.of(6));
        dust("charcoal", Asp.IGNIS.of(5), Asp.HERBA.of(3));
        dust("wood", Asp.HERBA.of(3));
        dust("ash", Asp.PERDITIO.of(3), Asp.IGNIS.of(1));
        dust("obsidian", Asp.TERRA.of(5), Asp.IGNIS.of(5), Asp.TENEBRAE.of(5));
        dust("blaze", Asp.IGNIS.of(10), Asp.POTENTIA.of(4), Asp.SOL.of(2));
        dust("ender_pearl", Asp.ALIENIS.of(8), Asp.MOTUS.of(10), Asp.COSMIC.of(1));
    }

    private TagMaterials() {
    }

    private static void metal(String id, AspectPart... aspects) {
        ALL.add(new TagMaterial(id, MaterialKind.METAL, List.of(aspects)));
    }

    private static void gem(String id, AspectPart... aspects) {
        ALL.add(new TagMaterial(id, MaterialKind.GEM, List.of(aspects)));
    }

    private static void dust(String id, AspectPart... aspects) {
        ALL.add(new TagMaterial(id, MaterialKind.DUST, List.of(aspects)));
    }

    public static List<TagMaterial> all() {
        return ALL;
    }

    public static TagMaterial require(String id) {
        Optional<TagMaterial> found = ALL.stream().filter(material -> material.id().equals(id)).findFirst();
        return found.orElseThrow(() -> new IllegalArgumentException("Unknown tag material " + id));
    }
}
