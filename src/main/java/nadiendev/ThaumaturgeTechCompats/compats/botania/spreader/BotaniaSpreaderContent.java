package nadiendev.ThaumaturgeTechCompats.compats.botania.spreader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BotaniaSpreaderContent implements DataProvider {
    public static final String ENTRY = "botania_spreaders";
    public static final int COLUMN = 48;
    public static final int ROW = 2;

    private static final String MOD = BotaniaJson.MOD;
    private static final String BASE_SPREADER = "botania:gaia_spreader";
    private static final String MECHANISM_SIMPLE = "thaumaturge:mechanism_simple";
    private static final String VOID_SEED = "thaumaturge:void_seed";

    private final PackOutput output;

    public BotaniaSpreaderContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (SpreaderTier tier : SpreaderTier.values()) {
            tasks.add(save(cache, "loot_table/blocks/" + tier.blockName(), BotaniaJson.lootTable(tier.blockName())));
            tasks.add(save(cache, "recipe/infusion/" + tier.blockName(), infusion(tier)));
        }
        tasks.add(save(cache, "thaumaturge/research_entry/" + ENTRY, entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats botania spreaders";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions(SpreaderTier tier) {
        JsonArray conditions = BotaniaJson.conditions(BotaniaIntegration.SPREADERS);
        for (SpreaderTier current = tier; current != null; current = current.previous()) {
            conditions.add(CompatJson.feature(current.toggle()));
        }
        return conditions;
    }

    private static String catalyst(SpreaderTier tier) {
        SpreaderTier previous = tier.previous();
        return previous == null ? BASE_SPREADER : MOD + ":" + previous.blockName();
    }

    private static JsonObject infusion(SpreaderTier tier) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item(tier.plate()));
        }
        JsonArray aspects;
        int instability;
        switch (tier) {
            case THAUMIUM -> {
                components.add(ContentJson.item(MECHANISM_SIMPLE));
                aspects = ContentJson.aspects("thaumaturge:praecantatio", 24, "thaumaturge:potentia", 24, "thaumaturge:auram", 16, ContentJson.ASPECT_TERMINUS, 4);
                instability = 4;
            }
            case ALCHEMICAL_BRASS -> {
                components.add(ContentJson.item(MECHANISM_SIMPLE));
                aspects = ContentJson.aspects("thaumaturge:praecantatio", 32, "thaumaturge:potentia", 32, "thaumaturge:machina", 24, ContentJson.ASPECT_TERMINUS, 8);
                instability = 7;
            }
            default -> {
                components.add(ContentJson.item(VOID_SEED));
                aspects = ContentJson.aspects("thaumaturge:praecantatio", 48, "thaumaturge:potentia", 48, "thaumaturge:vacuos", 32, ContentJson.ASPECT_TERMINUS, 24);
                instability = 10;
            }
        }
        JsonObject json = ContentJson.infusion(aspects, ContentJson.item(catalyst(tier)), components, instability, MOD + ":" + ENTRY, MOD + ":" + tier.blockName());
        json.add("neoforge:conditions", conditions(tier));
        return json;
    }

    private static JsonObject entry() {
        List<String> recipes = new ArrayList<>();
        for (SpreaderTier tier : SpreaderTier.values()) {
            recipes.add(MOD + ":infusion/" + tier.blockName());
        }
        return BotaniaJson.entry(
                ENTRY,
                BotaniaJson.conditions(BotaniaIntegration.SPREADERS),
                List.of(TechCategory.HEADER_ID, "thaumaturge:infusion"),
                COLUMN, ROW,
                MOD + ":" + SpreaderTier.VOID_METAL.blockName(),
                recipes,
                "thaumaturge:artifice", 2, 2,
                ContentJson.aspects("thaumaturge:praecantatio", 6, "thaumaturge:potentia", 5, "thaumaturge:motus", 4),
                2);
    }
}
