package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;

import java.util.ArrayList;
import java.util.List;

public enum FtbUnearthedLoot {
    VIS_CRYSTALS("vis_crystals", "v", CompatToggles.FTB_UNEARTHED_LOOT_VIS_CRYSTALS),
    AMBER("amber", "a", CompatToggles.FTB_UNEARTHED_LOOT_AMBER),
    RAW_CINNABAR("raw_cinnabar", "c", CompatToggles.FTB_UNEARTHED_LOOT_RAW_CINNABAR);

    public static final String ESSENTIA_CRYSTAL = "thaumaturge:essentia_crystal";
    public static final String CRYSTAL_ASPECT = "thaumaturge:crystal_aspect";
    public static final List<String> PRIMAL_ASPECTS = List.of(
            "thaumaturge:aer", "thaumaturge:aqua", "thaumaturge:ignis",
            "thaumaturge:ordo", "thaumaturge:perditio", "thaumaturge:terra");

    private final String id;
    private final String letter;
    private final String toggle;

    FtbUnearthedLoot(String id, String letter, String toggle) {
        this.id = id;
        this.letter = letter;
        this.toggle = toggle;
    }

    public String id() {
        return id;
    }

    public String letter() {
        return letter;
    }

    public String toggle() {
        return toggle;
    }

    public double baseChance(FtbUnearthedSource source) {
        return switch (this) {
            case VIS_CRYSTALS -> 0.02D;
            case AMBER -> source == FtbUnearthedSource.SAND ? 0.10D : 0.04D;
            case RAW_CINNABAR -> source == FtbUnearthedSource.GRAVEL ? 0.10D : 0.04D;
        };
    }

    public List<JsonObject> outputs(FtbUnearthedSource source, FtbUnearthedBrushTier tier) {
        double chance = Math.round(baseChance(source) * tier.lootMultiplier() * 1000.0D) / 1000.0D;
        List<JsonObject> outputs = new ArrayList<>();
        switch (this) {
            case VIS_CRYSTALS -> {
                for (String aspect : PRIMAL_ASPECTS) {
                    outputs.add(output(crystal(aspect), chance));
                }
            }
            case AMBER -> outputs.add(output(ContentJson.result("thaumaturge:amber", 1), chance));
            case RAW_CINNABAR -> outputs.add(output(ContentJson.result("thaumaturge:raw_cinnabar", 1), chance));
        }
        return outputs;
    }

    private static JsonObject crystal(String aspect) {
        JsonObject stack = ContentJson.result(ESSENTIA_CRYSTAL, 1);
        JsonObject aspectData = new JsonObject();
        aspectData.addProperty("aspect", aspect);
        JsonObject components = new JsonObject();
        components.add(CRYSTAL_ASPECT, aspectData);
        stack.add("components", components);
        return stack;
    }

    private static JsonObject output(JsonObject stack, double chance) {
        JsonObject json = new JsonObject();
        json.addProperty("chance", chance);
        json.add("item", stack);
        return json;
    }
}
