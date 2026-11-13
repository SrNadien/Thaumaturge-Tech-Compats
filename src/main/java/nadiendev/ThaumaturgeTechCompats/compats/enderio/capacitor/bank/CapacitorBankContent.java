package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.bank;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOJson;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor.CapacitorContent;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CapacitorBankContent implements DataProvider {
    public static final String ENTRY = "enderio_capacitor_banks";
    public static final int COLUMN = 74;

    private static final String MOD = EnderIOJson.MOD;
    private static final int PLATES = 4;
    private static final int CAPACITORS = 4;

    private final PackOutput output;

    public CapacitorBankContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (EnderIOTier tier : EnderIOTier.values()) {
            String name = CapacitorBankSupport.blockName(tier);
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + name + ".json", infusion(tier)));
            tasks.add(ContentJson.save(output, cache, "data/" + EnderIOIntegration.MOD_ID + "/loot_table/blocks/" + name + ".json", loot(tier)));
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY + ".json", entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO capacitor banks";
    }

    private static JsonArray conditions() {
        JsonArray conditions = EnderIOJson.conditions(EnderIOIntegration.CAPACITORS);
        conditions.add(CompatJson.feature(EnderIOIntegration.CAPACITOR_BANKS));
        return conditions;
    }

    private static JsonObject infusion(EnderIOTier tier) {
        JsonArray components = new JsonArray();
        components.addAll(EnderIOJson.repeat(ContentJson.item(tier.plate()), PLATES));
        components.addAll(EnderIOJson.repeat(ContentJson.item(MOD + ":" + tier.capacitorName()), CAPACITORS));
        EnderIOTier previous = tier.previous();
        String catalyst = previous == null ? CapacitorBankSupport.VIBRANT_BANK : CapacitorBankSupport.blockId(previous);
        int scale = tier.ordinal() + 1;
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:potentia", 32 * scale, "thaumaturge:metallum", 16 * scale, "thaumaturge:machina", 16 * scale),
                ContentJson.item(catalyst),
                components,
                4 * scale,
                MOD + ":" + ENTRY,
                CapacitorBankSupport.blockId(tier));
        json.add("neoforge:conditions", conditions());
        return json;
    }

    private static JsonObject loot(EnderIOTier tier) {
        String name = CapacitorBankSupport.blockName(tier);
        JsonObject copy = new JsonObject();
        copy.addProperty("function", "minecraft:copy_components");
        copy.addProperty("source", "block_entity");
        JsonArray functions = new JsonArray();
        functions.add(copy);

        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.add("functions", functions);
        entry.addProperty("name", CapacitorBankSupport.blockId(tier));
        JsonArray entries = new JsonArray();
        entries.add(entry);

        JsonObject pool = new JsonObject();
        pool.addProperty("bonus_rolls", 0.0F);
        pool.add("entries", entries);
        pool.addProperty("rolls", 1.0F);
        JsonArray pools = new JsonArray();
        pools.add(pool);

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions());
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", EnderIOIntegration.MOD_ID + ":blocks/" + name);
        return json;
    }

    private static JsonObject entry() {
        List<String> recipes = new ArrayList<>();
        for (EnderIOTier tier : EnderIOTier.values()) {
            recipes.add(MOD + ":infusion/" + CapacitorBankSupport.blockName(tier));
        }
        return EnderIOJson.entry(
                ENTRY,
                conditions(),
                List.of(MOD + ":" + CapacitorContent.ENTRY),
                COLUMN,
                CapacitorBankSupport.blockId(EnderIOTier.VOID_METAL),
                recipes,
                EnderIOJson.CATEGORY_ARTIFICE, 2, 1,
                ContentJson.aspects("thaumaturge:potentia", 6, "thaumaturge:machina", 5, "thaumaturge:metallum", 4),
                3);
    }
}
