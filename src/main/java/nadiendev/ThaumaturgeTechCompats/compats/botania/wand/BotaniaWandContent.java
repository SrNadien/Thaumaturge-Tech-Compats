package nadiendev.ThaumaturgeTechCompats.compats.botania.wand;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaJson;
import nadiendev.ThaumaturgeTechCompats.compats.botania.spreader.BotaniaSpreaderContent;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BotaniaWandContent implements DataProvider {
    public static final int COLUMN = BotaniaSpreaderContent.COLUMN;
    public static final int ROW = BotaniaSpreaderContent.ROW + 2;

    private static final String MOD = BotaniaJson.MOD;
    private static final String ENTRY = BotaniaWandRegistry.ENTRY;
    private static final String LIVINGWOOD_TWIG = "botania:livingwood_twig";
    private static final String DREAMWOOD_TWIG = "botania:dreamwood_twig";
    private static final String MANASTEEL_INGOT = "botania:manasteel_ingot";
    private static final String TERRASTEEL_INGOT = "botania:terrasteel_ingot";
    private static final String ELEMENTIUM_INGOT = "botania:elementium_ingot";
    private static final String MANA_DIAMOND = "botania:mana_diamond";
    private static final String PIXIE_DUST = "botania:pixie_dust";

    private final PackOutput output;

    public BotaniaWandContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "recipe/wand/part/" + BotaniaWandRod.LIVINGWOOD.itemName(), livingwoodRod()));
        tasks.add(save(cache, "recipe/infusion/" + BotaniaWandRod.DREAMWOOD.itemName(), dreamwoodRod()));
        tasks.add(save(cache, "recipe/wand/part/" + BotaniaWandCap.MANASTEEL.itemName(), manasteelCap()));
        tasks.add(save(cache, "recipe/infusion/" + BotaniaWandCap.ELEMENTIUM.itemName(), elementiumCap()));
        tasks.add(save(cache, "recipe/infusion/" + BotaniaWandCap.TERRASTEEL.itemName(), terrasteelCap()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + ENTRY, entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats botania wand";
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonArray conditions() {
        return BotaniaJson.conditions(BotaniaIntegration.WAND);
    }

    private static JsonArray items(String id, int count) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < count; i++) {
            components.add(ContentJson.item(id));
        }
        return components;
    }

    private static JsonObject livingwoodRod() {
        JsonObject key = new JsonObject();
        key.add("T", ContentJson.item(LIVINGWOOD_TWIG));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("  T", " T ", "T  "),
                MOD + ":" + ENTRY, 40, MOD + ":" + BotaniaWandRod.LIVINGWOOD.itemName());
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject manasteelCap() {
        JsonObject key = new JsonObject();
        key.add("I", ContentJson.item(MANASTEEL_INGOT));
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("III", "I I"),
                MOD + ":" + ENTRY, 60, MOD + ":" + BotaniaWandCap.MANASTEEL.itemName());
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject dreamwoodRod() {
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:herba", 16, "thaumaturge:spiritus", 16, "thaumaturge:praecantatio", 12),
                ContentJson.item(MOD + ":" + BotaniaWandRod.LIVINGWOOD.itemName()),
                items(DREAMWOOD_TWIG, 4),
                2,
                MOD + ":" + ENTRY,
                MOD + ":" + BotaniaWandRod.DREAMWOOD.itemName());
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject elementiumCap() {
        JsonArray components = items(ELEMENTIUM_INGOT, 3);
        components.add(ContentJson.item(PIXIE_DUST));
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:aer", 16, "thaumaturge:praecantatio", 16, "thaumaturge:alienis", 8),
                ContentJson.item(MOD + ":" + BotaniaWandCap.MANASTEEL.itemName()),
                components,
                3,
                MOD + ":" + ENTRY,
                MOD + ":" + BotaniaWandCap.ELEMENTIUM.itemName());
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject terrasteelCap() {
        JsonArray components = items(TERRASTEEL_INGOT, 3);
        components.add(ContentJson.item(MANA_DIAMOND));
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:potentia", 20, "thaumaturge:metallum", 16, "thaumaturge:praecantatio", 12),
                ContentJson.item(MOD + ":" + BotaniaWandCap.MANASTEEL.itemName()),
                components,
                5,
                MOD + ":" + ENTRY,
                MOD + ":" + BotaniaWandCap.TERRASTEEL.itemName());
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static String assembly(BotaniaWandCap cap, BotaniaWandRod rod) {
        return "thaumaturge:wand/assembly/" + MOD + "_" + cap.id() + "_" + MOD + "_" + rod.id();
    }

    private static JsonObject entry() {
        List<String> recipes = new ArrayList<>();
        recipes.add(MOD + ":wand/part/" + BotaniaWandRod.LIVINGWOOD.itemName());
        recipes.add(MOD + ":infusion/" + BotaniaWandRod.DREAMWOOD.itemName());
        recipes.add(MOD + ":wand/part/" + BotaniaWandCap.MANASTEEL.itemName());
        recipes.add(MOD + ":infusion/" + BotaniaWandCap.ELEMENTIUM.itemName());
        recipes.add(MOD + ":infusion/" + BotaniaWandCap.TERRASTEEL.itemName());
        for (BotaniaWandCap cap : BotaniaWandCap.values()) {
            for (BotaniaWandRod rod : BotaniaWandRod.values()) {
                recipes.add(assembly(cap, rod));
            }
        }
        return BotaniaJson.entry(
                ENTRY,
                conditions(),
                List.of(TechCategory.HEADER_ID, "thaumaturge:rod_greatwood", "thaumaturge:cap_thaumium"),
                COLUMN, ROW,
                MOD + ":" + BotaniaWandCap.TERRASTEEL.itemName(),
                recipes,
                "thaumaturge:auromancy", 2, 1,
                ContentJson.aspects("thaumaturge:praecantatio", 6, "thaumaturge:herba", 5, "thaumaturge:metallum", 4),
                2);
    }
}
