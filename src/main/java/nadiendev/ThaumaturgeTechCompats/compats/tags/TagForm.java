package nadiendev.ThaumaturgeTechCompats.compats.tags;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum TagForm {
    INGOT("c:ingots/", CompatToggles.TAG_ASPECTS_INGOTS, 1, 1, EnumSet.of(MaterialKind.METAL)),
    NUGGET("c:nuggets/", CompatToggles.TAG_ASPECTS_NUGGETS, 1, 9, EnumSet.of(MaterialKind.METAL)),
    ORE("c:ores/", CompatToggles.TAG_ASPECTS_ORES, 1, 1, EnumSet.of(MaterialKind.METAL, MaterialKind.GEM), Asp.TERRA.of(5)),
    RAW("c:raw_materials/", CompatToggles.TAG_ASPECTS_RAW_MATERIALS, 1, 1, EnumSet.of(MaterialKind.METAL), Asp.TERRA.of(2)),
    RAW_BLOCK("c:storage_blocks/raw_", CompatToggles.TAG_ASPECTS_STORAGE_BLOCKS, 3, 1, EnumSet.of(MaterialKind.METAL), Asp.TERRA.of(6)),
    BLOCK("c:storage_blocks/", CompatToggles.TAG_ASPECTS_STORAGE_BLOCKS, 3, 1, EnumSet.of(MaterialKind.METAL, MaterialKind.GEM), Asp.ORDO.of(5)),
    GEM("c:gems/", CompatToggles.TAG_ASPECTS_GEMS, 1, 1, EnumSet.of(MaterialKind.GEM)),
    DUST("c:dusts/", CompatToggles.TAG_ASPECTS_DUSTS, 1, 1, EnumSet.of(MaterialKind.METAL, MaterialKind.GEM, MaterialKind.DUST)),
    PLATE("c:plates/", CompatToggles.TAG_ASPECTS_PARTS, 1, 1, EnumSet.of(MaterialKind.METAL), Asp.FABRICO.of(1)),
    ROD("c:rods/", CompatToggles.TAG_ASPECTS_PARTS, 1, 2, EnumSet.of(MaterialKind.METAL)),
    GEAR("c:gears/", CompatToggles.TAG_ASPECTS_PARTS, 2, 1, EnumSet.of(MaterialKind.METAL), Asp.MACHINA.of(3)),
    WIRE("c:wires/", CompatToggles.TAG_ASPECTS_PARTS, 1, 2, EnumSet.of(MaterialKind.METAL)),
    CLUMP("c:clumps/", CompatToggles.TAG_ASPECTS_INTERMEDIATES, 1, 1, EnumSet.of(MaterialKind.METAL), Asp.TERRA.of(2)),
    DIRTY_DUST("c:dirty_dusts/", CompatToggles.TAG_ASPECTS_INTERMEDIATES, 1, 1, EnumSet.of(MaterialKind.METAL), Asp.TERRA.of(2)),
    SHARD("c:shards/", CompatToggles.TAG_ASPECTS_INTERMEDIATES, 1, 1, EnumSet.of(MaterialKind.METAL), Asp.VITREUS.of(2)),
    CRYSTAL("c:crystals/", CompatToggles.TAG_ASPECTS_INTERMEDIATES, 1, 1, EnumSet.of(MaterialKind.METAL), Asp.VITREUS.of(3));

    private final String prefix;
    private final String toggle;
    private final int numerator;
    private final int denominator;
    private final Set<MaterialKind> kinds;
    private final List<AspectPart> extras;

    TagForm(String prefix, String toggle, int numerator, int denominator, Set<MaterialKind> kinds, AspectPart... extras) {
        this.prefix = prefix;
        this.toggle = toggle;
        this.numerator = numerator;
        this.denominator = denominator;
        this.kinds = kinds;
        this.extras = List.of(extras);
    }

    public String tagKey(String material) {
        return "#" + prefix + material;
    }

    public String toggle() {
        return toggle;
    }

    public boolean appliesTo(MaterialKind kind) {
        return kinds.contains(kind);
    }

    public List<AspectPart> scale(List<AspectPart> base) {
        return AspectMath.scale(base, numerator, denominator, extras);
    }
}
