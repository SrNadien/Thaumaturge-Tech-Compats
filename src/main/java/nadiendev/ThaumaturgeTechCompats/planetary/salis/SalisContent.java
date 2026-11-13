package nadiendev.ThaumaturgeTechCompats.planetary.salis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SalisContent implements DataProvider {
    public static final String ENTRY = "salis_mundus_variants";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String PEARL = MOD + ":extremely_primordial_pearl";
    private static final String ASPECT_NUCLEAR = MOD + ":nuclear";
    private static final String ASPECT_COSMIC = MOD + ":cosmic";
    private static final int UNITS = 4;
    private static final int COLUMN = 67;
    private static final int ROW = 0;

    private final PackOutput output;

    public SalisContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (PlanetaryTier tier : SalisIntegration.tiers()) {
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + SalisIntegration.itemName(tier) + ".json", infusion(tier)));
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY + ".json", entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats salis mundus variants";
    }

    private static JsonArray conditions() {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.feature(SalisIntegration.TOGGLE));
        return conditions;
    }

    private static String catalyst(PlanetaryTier tier) {
        int index = SalisIntegration.tiers().indexOf(tier);
        if (index == 0) {
            return SalisIntegration.BASE;
        }
        return SalisIntegration.itemId(SalisIntegration.tiers().get(index - 1));
    }

    private static JsonArray aspects(PlanetaryTier tier) {
        return switch (tier) {
            case BLAZE_CUBE -> ContentJson.aspects(
                    "thaumaturge:ignis", 24,
                    "thaumaturge:potentia", 16,
                    "thaumaturge:praecantatio", 12,
                    "thaumaturge:vinculum", 8);
            case MATRIX -> ContentJson.aspects(
                    "thaumaturge:vitreus", 24,
                    "thaumaturge:ordo", 20,
                    "thaumaturge:lux", 16,
                    "thaumaturge:praecantatio", 16,
                    "thaumaturge:praemunio", 12);
            case NEUTRONIUM -> ContentJson.aspects(
                    ASPECT_NUCLEAR, 24,
                    "thaumaturge:metallum", 24,
                    "thaumaturge:potentia", 24,
                    "thaumaturge:ordo", 16,
                    "thaumaturge:perditio", 8);
            default -> ContentJson.aspects(
                    ASPECT_COSMIC, 32,
                    "thaumaturge:alienis", 32,
                    "thaumaturge:auram", 24,
                    ASPECT_NUCLEAR, 16,
                    "thaumaturge:vacuos", 16,
                    ContentJson.ASPECT_TERMINUS, 8);
        };
    }

    private static int instability(PlanetaryTier tier) {
        return switch (tier) {
            case BLAZE_CUBE -> 4;
            case MATRIX -> 6;
            case NEUTRONIUM -> 8;
            default -> 10;
        };
    }

    private static JsonObject infusion(PlanetaryTier tier) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < UNITS; i++) {
            components.add(ContentJson.item(tier.ingot()));
        }
        JsonObject json = ContentJson.infusion(
                aspects(tier),
                ContentJson.item(catalyst(tier)),
                components,
                instability(tier),
                MOD + ":" + ENTRY,
                SalisIntegration.itemId(tier));
        json.add("result", ContentJson.result(SalisIntegration.itemId(tier), UNITS));
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject entry() {
        List<String> recipes = new ArrayList<>();
        for (PlanetaryTier tier : SalisIntegration.tiers()) {
            recipes.add(MOD + ":infusion/" + SalisIntegration.itemName(tier));
        }
        JsonObject json = ContentJson.research(
                ENTRY,
                List.of(PEARL),
                COLUMN, ROW,
                SalisIntegration.itemId(PlanetaryTier.INFINITY),
                recipes,
                2,
                ContentJson.aspects("thaumaturge:praecantatio", 6, "thaumaturge:potentia", 4, ContentJson.ASPECT_TERMINUS, 3),
                2);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        json.add("neoforge:conditions", conditions());
        return json;
    }
}
